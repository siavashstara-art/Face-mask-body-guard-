package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.camera.CameraHomeScreen
import com.example.ui.gallery.OfflineVideoLabScreen
import com.example.ui.permissions.CameraPermissionScreen
import com.example.ui.theme.FaceGuardTheme
import com.example.ui.theme.WarmBackground
import com.example.viewmodel.FaceGuardViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FaceGuardTheme {
                val viewModel: FaceGuardViewModel = viewModel()
                val isPersian by viewModel.isPersian.collectAsStateWithLifecycle()
                val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()

                // Dynamically switch LayoutDirection between Persian RTL and English LTR
                val layoutDirection = if (isPersian) LayoutDirection.Rtl else LayoutDirection.Ltr

                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = WarmBackground
                    ) {
                        CameraPermissionScreen(isPersian = isPersian) {
                            AnimatedContent(
                                targetState = currentScreen,
                                transitionSpec = { fadeIn() togetherWith fadeOut() },
                                label = "ScreenTransition"
                            ) { screen ->
                                when (screen) {
                                    "gallery" -> OfflineVideoLabScreen(
                                        storageManager = viewModel.storageManager,
                                        isPersian = isPersian,
                                        onNavigateBack = { viewModel.navigateTo("camera") }
                                    )
                                    else -> CameraHomeScreen(
                                        viewModel = viewModel,
                                        onNavigateToGallery = { viewModel.navigateTo("gallery") }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
