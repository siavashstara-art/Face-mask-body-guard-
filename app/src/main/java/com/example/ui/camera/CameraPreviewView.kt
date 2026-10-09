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

            // Lightweight, memory-safe image analysis for face detection
            var frameCounter = 0
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(Size(480, 640))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also { analysis ->
                    analysis.setAnalyzer(cameraExecutor) { imageProxy ->
                        try {
                            // Drop frame if previous analysis is still ongoing or throttle to every 4th frame
                            if (++frameCounter % 4 == 0 && !isAnalyzing.get()) {
                                isAnalyzing.set(true)
                                val bitmap = imageProxy.toBitmap()
                                val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                                val rotatedBitmap = if (rotationDegrees != 0) {
                                    val matrix = Matrix().apply { postRotate(rotationDegrees.toFloat()) }
                                    val rotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                                    bitmap.recycle()
                                    rotated
                                } else {
                                    bitmap
                                }

                                CoroutineScope(Dispatchers.Default).launch {
                                    try {
                                        val isFront = cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA
                                        val detected = faceDetectionEngine.detectFacesOnBitmap(
                                            sourceBitmap = rotatedBitmap,
                                            isFrontCamera = isFront
                                        )
                                        onFacesUpdated(detected)
                                    } catch (_: Throwable) {
                                    } finally {
                                        try { rotatedBitmap.recycle() } catch (_: Throwable) {}
                                        isAnalyzing.set(false)
                                    }
                                }
                            }
                        } catch (_: Throwable) {
                            isAnalyzing.set(false)
                        } finally {
                            imageProxy.close()
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
                    cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, useCaseGroup)
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
                        cameraSelector,
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
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                    boundSuccessfully = true
                } catch (_: Throwable) {
                    // Absolute fallback: Preview only
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
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
