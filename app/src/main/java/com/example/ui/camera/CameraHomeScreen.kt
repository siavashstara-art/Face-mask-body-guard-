package com.example.ui.camera

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.ActiveStudioTab
import com.example.ui.dialogs.PrivacyAuditDialog
import com.example.ui.overlay.PrivacyCameraOverlay
import com.example.ui.panels.*
import com.example.ui.theme.*
import com.example.viewmodel.FaceGuardViewModel

@Composable
fun CameraHomeScreen(
    viewModel: FaceGuardViewModel,
    onNavigateToGallery: () -> Unit
) {
    val privacyConfig by viewModel.privacyConfig.collectAsStateWithLifecycle()
    val bgConfig by viewModel.backgroundConfig.collectAsStateWithLifecycle()
    val faceStyleConfig by viewModel.faceStyleConfig.collectAsStateWithLifecycle()
    val silhouetteConfig by viewModel.silhouetteConfig.collectAsStateWithLifecycle()
    val voiceConfig by viewModel.voiceConfig.collectAsStateWithLifecycle()
    val performancePreset by viewModel.performancePreset.collectAsStateWithLifecycle()
    val activeTab by viewModel.activeTab.collectAsStateWithLifecycle()
    val trackedFaces by viewModel.trackedFaces.collectAsStateWithLifecycle()
    val cameraSelector by viewModel.cameraSelector.collectAsStateWithLifecycle()
    val isRecording by viewModel.isRecording.collectAsStateWithLifecycle()
    val durationSec by viewModel.recordingDurationSec.collectAsStateWithLifecycle()
    val isPersian by viewModel.isPersian.collectAsStateWithLifecycle()
    val showAudit by viewModel.showPrivacyAudit.collectAsStateWithLifecycle()
    val statusMsg by viewModel.statusMessage.collectAsStateWithLifecycle()

    var overlayWidth by remember { mutableStateOf(1080f) }
    var overlayHeight by remember { mutableStateOf(1920f) }

    // Build CameraX VideoCapture once
    val videoCapture = remember(performancePreset) {
        viewModel.recordingEngine.buildVideoCapture(performancePreset)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkCharcoalBackground)
            .onGloballyPositioned { coords ->
                overlayWidth = coords.size.width.toFloat()
                overlayHeight = coords.size.height.toFloat()
            }
    ) {
        // 1. CameraX Preview Layer
        CameraPreviewView(
            cameraSelector = cameraSelector,
            videoCapture = videoCapture,
            faceDetectionEngine = viewModel.faceDetectionEngine,
            onFacesUpdated = { viewModel.onFacesDetected(it) },
            modifier = Modifier.fillMaxSize()
        )

        // 2. Privacy & Visual Overlay Layer
        PrivacyCameraOverlay(
            trackedFaces = trackedFaces,
            privacyConfig = privacyConfig,
            backgroundConfig = bgConfig,
            faceStyleConfig = faceStyleConfig,
            isPersian = isPersian,
            onTapAddManualZone = { offset ->
                viewModel.addManualPrivacyZone(offset, overlayWidth, overlayHeight)
            },
            modifier = Modifier.fillMaxSize()
        )

        // 3. Top Controls HUD Bar (Warm, refined, minimal)
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xD9181615), Color.Transparent)
                    )
                )
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Offline Seal Badge
                Surface(
                    color = WarmSurface.copy(alpha = 0.92f),
                    shape = RoundedCornerShape(20.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
                    modifier = Modifier.clickable { viewModel.setShowPrivacyAudit(true) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(CircleShape)
                                .background(SageGreen)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = if (isPersian) "آفلاین • بدون اتصال ابری" else "100% OFFLINE",
                            color = CharcoalPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Controls (Language Toggle + Camera Flip)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Language Toggle
                    Surface(
                        color = WarmSurface.copy(alpha = 0.92f),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
                        modifier = Modifier.clickable { viewModel.toggleLanguage() }
                    ) {
                        Text(
                            text = if (isPersian) "EN" else "فارسی",
                            color = CharcoalPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }

                    // Flip Camera
                    IconButton(
                        onClick = { viewModel.toggleCameraFacing() },
                        modifier = Modifier
                            .size(38.dp)
                            .background(WarmSurface.copy(alpha = 0.92f), CircleShape)
                            .border(1.dp, WarmBorder, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.FlipCameraAndroid,
                            contentDescription = "Switch Camera",
                            tint = CharcoalPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Secondary Info Strip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = WarmSurface.copy(alpha = 0.88f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Face, contentDescription = null, tint = CharcoalSecondary, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = if (isPersian)
                                "چهره‌های محافظت‌شده: ${if (privacyConfig.autoFaceTracking) trackedFaces.size else 0}"
                            else
                                "Protected Faces: ${if (privacyConfig.autoFaceTracking) trackedFaces.size else 0}",
                            color = CharcoalPrimary,
                            fontSize = 11.sp
                        )
                    }
                }

                // Recording Status Tag
                if (isRecording) {
                    val minutes = durationSec / 60
                    val seconds = durationSec % 60
                    val timerStr = String.format("%02d:%02d", minutes, seconds)
                    Surface(
                        color = BrickRed,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "REC $timerStr",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // Notification Banner
        if (statusMsg != null) {
            Surface(
                color = WarmSurface,
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 110.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SageGreen, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(statusMsg ?: "", color = CharcoalPrimary, fontSize = 12.sp)
                }
            }
        }

        // 4. Studio Control Panel Drawer & Shutter Controls
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            // Expandable Studio Tab Content
            AnimatedVisibility(
                visible = activeTab != ActiveStudioTab.NONE,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                Surface(
                    color = WarmSurface,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 340.dp)
                ) {
                    when (activeTab) {
                        ActiveStudioTab.PRIVACY -> PrivacyStudioPanel(
                            privacyConfig = privacyConfig,
                            isPersian = isPersian,
                            onUpdate = { viewModel.updatePrivacyConfig(it) },
                            onClearManualZones = { viewModel.clearManualZones() }
                        )
                        ActiveStudioTab.BACKGROUND -> BackgroundStudioPanel(
                            bgConfig = bgConfig,
                            isPersian = isPersian,
                            onUpdate = { viewModel.updateBackgroundConfig(it) }
                        )
                        ActiveStudioTab.STYLE -> FaceStyleStudioPanel(
                            styleConfig = faceStyleConfig,
                            isPersian = isPersian,
                            onUpdate = { viewModel.updateFaceStyleConfig(it) }
                        )
                        ActiveStudioTab.BODY -> BodySilhouetteStudioPanel(
                            silhouetteConfig = silhouetteConfig,
                            isPersian = isPersian,
                            onUpdate = { viewModel.updateSilhouetteConfig(it) }
                        )
                        ActiveStudioTab.VOICE -> VoiceStudioPanel(
                            voiceConfig = voiceConfig,
                            isPersian = isPersian,
                            onUpdate = { viewModel.updateVoiceConfig(it) }
                        )
                        ActiveStudioTab.SETTINGS -> SettingsPerformancePanel(
                            preset = performancePreset,
                            isPersian = isPersian,
                            onPresetChange = { viewModel.setPerformancePreset(it) },
                            onShowPrivacyAudit = { viewModel.setShowPrivacyAudit(true) }
                        )
                        ActiveStudioTab.NONE -> {}
                    }
                }
            }

            // Bottom Bar: Studio Tab Selectors + Shutter Controls
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color(0xE6181615))
                        )
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Studio Tab Selector Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val tabs = listOf(
                        Triple(ActiveStudioTab.PRIVACY, if (isPersian) "حریم خصوصی" else "Privacy", Icons.Default.Shield),
                        Triple(ActiveStudioTab.BACKGROUND, if (isPersian) "پس‌زمینه" else "Background", Icons.Default.Landscape),
                        Triple(ActiveStudioTab.STYLE, if (isPersian) "استایل" else "Style", Icons.Default.AutoAwesome),
                        Triple(ActiveStudioTab.BODY, if (isPersian) "اندام" else "Body", Icons.Default.AccessibilityNew),
                        Triple(ActiveStudioTab.VOICE, if (isPersian) "صدا" else "Voice", Icons.Default.Mic),
                        Triple(ActiveStudioTab.SETTINGS, if (isPersian) "تنظیمات" else "Settings", Icons.Default.Tune)
                    )

                    tabs.forEach { (tab, title, icon) ->
                        val isSelected = activeTab == tab
                        Surface(
                            color = if (isSelected) TerracottaAccent else WarmSurface.copy(alpha = 0.92f),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) TerracottaAccent else WarmBorder
                            ),
                            modifier = Modifier
                                .clickable { viewModel.setActiveTab(tab) }
                                .padding(2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = title,
                                    tint = if (isSelected) Color.White else CharcoalPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = title,
                                    color = if (isSelected) Color.White else CharcoalPrimary,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }

                // Shutter / Record Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Gallery Button
                    IconButton(
                        onClick = onNavigateToGallery,
                        modifier = Modifier
                            .size(50.dp)
                            .background(WarmSurface.copy(alpha = 0.92f), CircleShape)
                            .border(1.dp, WarmBorder, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.VideoLibrary,
                            contentDescription = "Gallery",
                            tint = CharcoalPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    // Shutter Button (Solid terracotta / brick red when recording)
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clickable { viewModel.toggleRecording() },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .border(
                                    width = 3.dp,
                                    color = if (isRecording) BrickRed else WarmBorder,
                                    shape = CircleShape
                                )
                        )
                        Box(
                            modifier = Modifier
                                .size(if (isRecording) 26.dp else 56.dp)
                                .clip(if (isRecording) RoundedCornerShape(6.dp) else CircleShape)
                                .background(if (isRecording) BrickRed else TerracottaAccent)
                        )
                    }

                    // Quick Privacy Toggle
                    IconButton(
                        onClick = {
                            val nextMask = if (privacyConfig.maskType == com.example.model.PrivacyMaskType.NONE) {
                                com.example.model.PrivacyMaskType.EYES_VISOR
                            } else {
                                com.example.model.PrivacyMaskType.NONE
                            }
                            viewModel.updatePrivacyConfig(privacyConfig.copy(maskType = nextMask))
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .background(WarmSurface.copy(alpha = 0.92f), CircleShape)
                            .border(
                                1.dp,
                                if (privacyConfig.maskType != com.example.model.PrivacyMaskType.NONE) TerracottaAccent else WarmBorder,
                                CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = if (privacyConfig.maskType != com.example.model.PrivacyMaskType.NONE) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Toggle Mask",
                            tint = if (privacyConfig.maskType != com.example.model.PrivacyMaskType.NONE) TerracottaAccent else CharcoalSecondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }

    // Privacy Audit Dialog
    if (showAudit) {
        PrivacyAuditDialog(
            isPersian = isPersian,
            onDismiss = { viewModel.setShowPrivacyAudit(false) }
        )
    }
}
