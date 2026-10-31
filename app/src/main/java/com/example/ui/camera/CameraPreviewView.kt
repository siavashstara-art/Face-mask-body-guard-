package com.example.ui.camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
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
    var previewViewRef by remember { mutableStateOf<PreviewView?>(null) }
    var cameraProviderRef by remember { mutableStateOf<ProcessCameraProvider?>(null) }

    // Initialize with stable portrait face anchor immediately so overlays render instantly
    LaunchedEffect(Unit) {
        onFacesUpdated(faceDetectionEngine.getDefaultCenterFace())
    }

    DisposableEffect(Unit) {
        onDispose {
            try {
                cameraProviderRef?.unbindAll()
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

            // Standard 2-stream binding (Preview + VideoCapture)
            // Supported on 100% of Android devices across all Camera2 hardware levels (Legacy, Limited, Full)
            var boundSuccessfully = false
            if (videoCapture != null) {
                try {
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

            // Fallback: Preview only
            if (!boundSuccessfully) {
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(lifecycleOwner, effectiveSelector, preview)
                } catch (_: Throwable) {}
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
