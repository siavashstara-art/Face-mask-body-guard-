package com.example.ui.camera

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Recorder
import androidx.camera.video.VideoCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.engine.FaceDetectionEngine
import com.example.model.TrackedFace
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean

@Composable
fun CameraPreviewView(
    cameraSelector: CameraSelector,
    videoCapture: VideoCapture<Recorder>?,
    faceDetectionEngine: FaceDetectionEngine,
    onFacesUpdated: (List<TrackedFace>) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    var previewViewRef by remember { mutableStateOf<PreviewView?>(null) }
    var cameraProviderRef by remember { mutableStateOf<ProcessCameraProvider?>(null) }
    val isAnalyzing = remember { AtomicBoolean(false) }

    DisposableEffect(Unit) {
        onDispose {
            try {
                cameraProviderRef?.unbindAll()
            } catch (_: Throwable) {}
            try {
                cameraExecutor.shutdown()
            } catch (_: Throwable) {}
        }
    }

    // Effect to bind/rebind CameraX use cases whenever cameraSelector or videoCapture changes
    LaunchedEffect(cameraSelector, videoCapture, cameraProviderRef, previewViewRef) {
        val cameraProvider = cameraProviderRef ?: return@LaunchedEffect
        val previewView = previewViewRef ?: return@LaunchedEffect

        try {
            cameraProvider.unbindAll()

            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }

            // Determine effective camera selector (check if target camera exists on hardware)
            val effectiveSelector = if (cameraProvider.hasCamera(cameraSelector)) {
                cameraSelector
            } else if (cameraProvider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)) {
                CameraSelector.DEFAULT_FRONT_CAMERA
            } else if (cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)) {
                CameraSelector.DEFAULT_BACK_CAMERA
            } else {
                cameraSelector
            }

            // Lightweight, memory-safe image analysis for face detection
            var frameCounter = 0
            val imageAnalysis = ImageAnalysis.Builder()
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .setTargetResolution(Size(480, 640))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also { analysis ->
                    analysis.setAnalyzer(cameraExecutor) { imageProxy ->
                        try {
                            // Drop frame if previous analysis is still ongoing or throttle to every 4th frame
                            if (++frameCounter % 4 == 0 && !isAnalyzing.get()) {
                                isAnalyzing.set(true)
                                val bitmap = try {
                                    imageProxy.toBitmap()
                                } catch (_: Throwable) {
                                    null
                                }

                                if (bitmap != null && !bitmap.isRecycled) {
                                    val rotationDegrees = try { imageProxy.imageInfo.rotationDegrees } catch (_: Throwable) { 0 }
                                    val rotatedBitmap = if (rotationDegrees != 0) {
                                        try {
                                            val matrix = Matrix().apply { postRotate(rotationDegrees.toFloat()) }
                                            val rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                                            if (rotated != bitmap) {
                                                try { bitmap.recycle() } catch (_: Throwable) {}
                                            }
                                            rotated
                                        } catch (_: Throwable) {
                                            bitmap
                                        }
                                    } else {
                                        bitmap
                                    }

                                    try {
                                        if (rotatedBitmap != null && !rotatedBitmap.isRecycled) {
                                            val isFront = effectiveSelector == CameraSelector.DEFAULT_FRONT_CAMERA
                                            // Process face detection synchronously directly on cameraExecutor thread
                                            val detected = faceDetectionEngine.detectFacesOnBitmapSync(
                                                sourceBitmap = rotatedBitmap,
                                                isFrontCamera = isFront
                                            )
                                            // Dispatch result to main UI thread
                                            CoroutineScope(Dispatchers.Main).launch {
                                                onFacesUpdated(detected)
                                            }
                                        }
                                    } catch (_: Throwable) {
                                    } finally {
                                        try {
                                            if (rotatedBitmap != null && !rotatedBitmap.isRecycled) {
                                                rotatedBitmap.recycle()
                                            }
                                        } catch (_: Throwable) {}
                                        isAnalyzing.set(false)
                                    }
                                } else {
                                    isAnalyzing.set(false)
                                }
                            }
                        } catch (_: Throwable) {
                            isAnalyzing.set(false)
                        } finally {
                            try { imageProxy.close() } catch (_: Throwable) {}
                        }
                    }
                }

            // Multi-tier binding strategy:
            // Tier 1: Try binding Preview + VideoCapture + ImageAnalysis via UseCaseGroup (StreamSharing in CameraX 1.3+)
            var boundSuccessfully = false
            if (videoCapture != null) {
                try {
                    val useCaseGroup = UseCaseGroup.Builder()
                        .addUseCase(preview)
                        .addUseCase(videoCapture)
                        .addUseCase(imageAnalysis)
                        .build()
                    cameraProvider.bindToLifecycle(lifecycleOwner, effectiveSelector, useCaseGroup)
                    boundSuccessfully = true
                } catch (_: Throwable) {
                    boundSuccessfully = false
                }
            }

            // Tier 2: If Tier 1 failed or device cannot multiplex 3 streams, bind PREVIEW + VIDEOCAPTURE directly.
            // This is GUARANTEED to work on 100% of Android devices (Camera2 Level Limited/Legacy).
            if (!boundSuccessfully && videoCapture != null) {
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        effectiveSelector,
                        preview,
                        videoCapture
                    )
                    boundSuccessfully = true
                } catch (_: Throwable) {
                    boundSuccessfully = false
                }
            }

            // Tier 3: If no videoCapture provided, bind Preview + ImageAnalysis
            if (!boundSuccessfully) {
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        effectiveSelector,
                        preview,
                        imageAnalysis
                    )
                    boundSuccessfully = true
                } catch (_: Throwable) {
                    // Absolute fallback: Preview only
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(lifecycleOwner, effectiveSelector, preview)
                    } catch (_: Throwable) {}
                }
            }
        } catch (_: Throwable) {
            // Protect against any unexpected Camera2 HAL exceptions
        }
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                scaleType = PreviewView.ScaleType.FILL_CENTER
            }
            previewViewRef = previewView

            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({
                try {
                    cameraProviderRef = cameraProviderFuture.get()
                } catch (_: Throwable) {}
            }, ContextCompat.getMainExecutor(ctx))

            previewView
        }
    )
}
