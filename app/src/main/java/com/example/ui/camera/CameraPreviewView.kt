package com.example.ui.camera

import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.VideoCapture
import androidx.camera.video.Recorder
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
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

    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { ctx ->
            val previewView = PreviewView(ctx).apply {
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                scaleType = PreviewView.ScaleType.FILL_CENTER
            }

            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }

                // Image analysis for real-time offline face detection
                var frameCounter = 0
                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also { analysis ->
                        analysis.setAnalyzer(cameraExecutor) { imageProxy ->
                            try {
                                // Sample every 3rd frame to conserve CPU/RAM on Redmi Note 8
                                if (++frameCounter % 3 == 0) {
                                    val bitmap = imageProxy.toBitmap()
                                    val rotationDegrees = imageProxy.imageInfo.rotationDegrees
                                    val rotatedBitmap = if (rotationDegrees != 0) {
                                        val matrix = Matrix().apply { postRotate(rotationDegrees.toFloat()) }
                                        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
                                    } else {
                                        bitmap
                                    }

                                    CoroutineScope(Dispatchers.Default).launch {
                                        val isFront = cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA
                                        val detected = faceDetectionEngine.detectFacesOnBitmap(
                                            sourceBitmap = rotatedBitmap,
                                            isFrontCamera = isFront
                                        )
                                        onFacesUpdated(detected)
                                    }
                                }
                            } catch (_: Throwable) {
                                // Gracefully ignore decoding glitches on legacy hardware
                            } finally {
                                imageProxy.close()
                            }
                        }
                    }

                try {
                    cameraProvider.unbindAll()
                    if (videoCapture != null) {
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalysis,
                            videoCapture
                        )
                    } else {
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalysis
                        )
                    }
                } catch (_: Exception) {
                    // Fallback to preview only if device limits binding
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview
                        )
                    } catch (_: Exception) {}
                }
            }, ContextCompat.getMainExecutor(ctx))

            previewView
        }
    )
}
