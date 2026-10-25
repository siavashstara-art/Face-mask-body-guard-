package com.example.engine

import android.graphics.Bitmap
import android.graphics.PointF
import android.graphics.RectF
import android.media.FaceDetector
import com.example.model.TrackedFace
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * 100% Offline Local Face Detection Engine.
 * Optimized specifically for low-resource devices like Xiaomi Redmi Note 8.
 * Uses Android native FaceDetector on downscaled RGB_565 buffers with zero cloud telemetry.
 */
class FaceDetectionEngine {

    private val maxFaces = 5
    private var lastFaces: List<TrackedFace> = emptyList()

    // Smooth movement history (Exponential Moving Average)
    private val smoothedBoundsMap = mutableMapOf<Int, RectF>()

    suspend fun detectFacesOnBitmap(
        sourceBitmap: Bitmap,
        isFrontCamera: Boolean = true
    ): List<TrackedFace> = withContext(Dispatchers.Default) {
        detectFacesOnBitmapSync(sourceBitmap, isFrontCamera)
    }

    /**
     * Synchronous face detection method to be called directly on background camera thread.
     * Prevents async race conditions and recycles temporary buffers immediately to prevent OOM crashes.
     */
    fun detectFacesOnBitmapSync(
        sourceBitmap: Bitmap,
        isFrontCamera: Boolean = true
    ): List<TrackedFace> {
        return try {
            if (sourceBitmap.isRecycled) return lastFaces

            // Downscale to 320px width to keep memory consumption low on budget devices like Redmi Note 8
            val targetWidth = 320
            if (sourceBitmap.width <= 0 || sourceBitmap.height <= 0) return lastFaces

            val scale = targetWidth.toFloat() / sourceBitmap.width
            val targetHeight = (sourceBitmap.height * scale).toInt()

            // FaceDetector requires even width and height
            val evenWidth = if (targetWidth % 2 == 0) targetWidth else targetWidth - 1
            val evenHeight = if (targetHeight % 2 == 0) targetHeight else targetHeight - 1

            if (evenWidth <= 0 || evenHeight <= 0) return lastFaces

            val scaledBitmap = try {
                Bitmap.createScaledBitmap(sourceBitmap, evenWidth, evenHeight, false)
            } catch (_: Throwable) {
                null
            } ?: return lastFaces

            val rgb565Bitmap = try {
                if (scaledBitmap.config == Bitmap.Config.RGB_565) {
                    scaledBitmap
                } else {
                    val copy = scaledBitmap.copy(Bitmap.Config.RGB_565, false)
                    if (scaledBitmap != copy) {
                        try { scaledBitmap.recycle() } catch (_: Throwable) {}
                    }
                    copy
                }
            } catch (_: Throwable) {
                try { scaledBitmap.recycle() } catch (_: Throwable) {}
                null
            } ?: return lastFaces

            val detectedList = mutableListOf<TrackedFace>()

            try {
                if (!rgb565Bitmap.isRecycled) {
                    val faceDetector = FaceDetector(evenWidth, evenHeight, maxFaces)
                    val facesArray = arrayOfNulls<FaceDetector.Face>(maxFaces)
                    val numFaces = faceDetector.findFaces(rgb565Bitmap, facesArray)

                    for (i in 0 until numFaces) {
                        val face = facesArray[i] ?: continue
                        val midPoint = PointF()
                        face.getMidPoint(midPoint)
                        val eyeDistance = face.eyesDistance()
                        val confidence = face.confidence()

                        // Calculate bounding box relative to scaled image
                        val faceWidth = eyeDistance * 2.2f
                        val faceHeight = eyeDistance * 3.0f

                        var left = (midPoint.x - faceWidth / 2f) / evenWidth
                        var top = (midPoint.y - faceHeight * 0.45f) / evenHeight
                        var right = (midPoint.x + faceWidth / 2f) / evenWidth
                        var bottom = (midPoint.y + faceHeight * 0.55f) / evenHeight

                        // Clamp to [0, 1]
                        left = left.coerceIn(0f, 1f)
                        top = top.coerceIn(0f, 1f)
                        right = right.coerceIn(0f, 1f)
                        bottom = bottom.coerceIn(0f, 1f)

                        // If front camera, handle horizontal mirroring
                        val finalLeft = if (isFrontCamera) 1f - right else left
                        val finalRight = if (isFrontCamera) 1f - left else right

                        val rawBounds = RectF(finalLeft, top, finalRight, bottom)

                        // Smooth with exponential moving average to eliminate jitter
                        val prev = smoothedBoundsMap[i] ?: rawBounds
                        val alpha = 0.35f
                        val smoothLeft = prev.left + alpha * (rawBounds.left - prev.left)
                        val smoothTop = prev.top + alpha * (rawBounds.top - prev.top)
                        val smoothRight = prev.right + alpha * (rawBounds.right - prev.right)
                        val smoothBottom = prev.bottom + alpha * (rawBounds.bottom - prev.bottom)

                        val smoothed = RectF(smoothLeft, smoothTop, smoothRight, smoothBottom)
                        smoothedBoundsMap[i] = smoothed

                        detectedList.add(
                            TrackedFace(
                                id = i,
                                bounds = rawBounds,
                                confidence = confidence,
                                eyeCenterY = 0.32f,
                                mouthCenterY = 0.72f,
                                smoothedBounds = smoothed
                            )
                        )
                    }
                }
            } catch (_: Throwable) {
            } finally {
                try {
                    if (!rgb565Bitmap.isRecycled) {
                        rgb565Bitmap.recycle()
                    }
                } catch (_: Throwable) {}
            }

            if (detectedList.isNotEmpty()) {
                lastFaces = detectedList
            }
            detectedList
        } catch (_: Throwable) {
            // Graceful fallback to cached faces without crashing
            lastFaces
        }
    }

    /**
     * Fallback or preview anchor: When camera analysis is idle or initializing,
     * provides a safe center face tracking box so the user immediately sees the active
     * privacy mask in action.
     */
    fun getDefaultCenterFace(): List<TrackedFace> {
        val centerBounds = RectF(0.25f, 0.20f, 0.75f, 0.75f)
        return listOf(
            TrackedFace(
                id = 0,
                bounds = centerBounds,
                confidence = 0.98f,
                eyeCenterY = 0.36f,
                mouthCenterY = 0.70f,
                smoothedBounds = centerBounds
            )
        )
    }

    fun reset() {
        smoothedBoundsMap.clear()
        lastFaces = emptyList()
    }
}
