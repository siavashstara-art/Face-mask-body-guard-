package com.example

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Rational
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.AppLanguage
import com.example.ui.camera.CameraHomeScreen
import com.example.ui.gallery.OfflineVideoLabScreen
import com.example.ui.permissions.CameraPermissionScreen
import com.example.ui.stealth.StealthCalculatorScreen
import com.example.ui.theme.FaceGuardTheme
import com.example.ui.theme.WarmBackground
import com.example.viewmodel.FaceGuardViewModel

class MainActivity : ComponentActivity() {

    private val isInPipState = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FaceGuardTheme {
                val viewModel: FaceGuardViewModel = viewModel()
                val appLanguage by viewModel.appLanguage.collectAsStateWithLifecycle()
                val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
                val isPip by isInPipState
                var isStealthCalculatorActive by remember { mutableStateOf(false) }

                // Dynamically switch LayoutDirection: Persian and Arabic are RTL, English is LTR
                val layoutDirection = when (appLanguage) {
                    AppLanguage.PERSIAN, AppLanguage.ARABIC -> LayoutDirection.Rtl
                    AppLanguage.ENGLISH -> LayoutDirection.Ltr
                }

                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = WarmBackground
                    ) {
                        if (isStealthCalculatorActive) {
                            // Peerless Feature: 100% Real Working Fake Calculator Vault
                            StealthCalculatorScreen(
                                language = appLanguage,
                                secretPin = "1234",
                                onUnlockStudio = { isStealthCalculatorActive = false }
                            )
                        } else {
                            CameraPermissionScreen(isPersian = appLanguage == AppLanguage.PERSIAN) {
                                AnimatedContent(
                                    targetState = currentScreen,
                                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                                    label = "ScreenTransition"
                                ) { screen ->
                                    when (screen) {
                                        "gallery" -> OfflineVideoLabScreen(
                                            storageManager = viewModel.storageManager,
                                            isPersian = appLanguage == AppLanguage.PERSIAN,
                                            onNavigateBack = { viewModel.navigateTo("camera") }
                                        )
                                        else -> CameraHomeScreen(
                                            viewModel = viewModel,
                                            onNavigateToGallery = { viewModel.navigateTo("gallery") },
                                            isInPipMode = isPip,
                                            onEnterPip = { enterStudioPipMode() },
                                            onTriggerStealth = { isStealthCalculatorActive = true }
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

    private fun enterStudioPipMode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val params = PictureInPictureParams.Builder()
                .setAspectRatio(Rational(9, 16))
                .build()
            enterPictureInPictureMode(params)
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        isInPipState.value = isInPictureInPictureMode
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val params = PictureInPictureParams.Builder()
                .setAspectRatio(Rational(9, 16))
                .build()
            enterPictureInPictureMode(params)
        }
    }
}
