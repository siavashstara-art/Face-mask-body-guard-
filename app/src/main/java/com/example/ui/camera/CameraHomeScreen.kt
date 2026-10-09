package com.example.ui.camera

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.model.*
import com.example.ui.dialogs.PrivacyAuditDialog
import com.example.ui.overlay.PrivacyCameraOverlay
import com.example.ui.panels.*
import com.example.ui.theme.*
import com.example.viewmodel.FaceGuardViewModel

@Composable
fun CameraHomeScreen(
    viewModel: FaceGuardViewModel,
    onNavigateToGallery: () -> Unit,
    isInPipMode: Boolean = false,
    onEnterPip: () -> Unit = {},
    onTriggerStealth: () -> Unit = {},
    onNavigateBackToFaceCard: () -> Unit = {}
) {
    val privacyConfig by viewModel.privacyConfig.collectAsStateWithLifecycle()
    val bgConfig by viewModel.backgroundConfig.collectAsStateWithLifecycle()
    val faceStyleConfig by viewModel.faceStyleConfig.collectAsStateWithLifecycle()
    val silhouetteConfig by viewModel.silhouetteConfig.collectAsStateWithLifecycle()
    val voiceConfig by viewModel.voiceConfig.collectAsStateWithLifecycle()
    val performancePreset by viewModel.performancePreset.collectAsStateWithLifecycle()
    val storyStreamConfig by viewModel.storyStreamConfig.collectAsStateWithLifecycle()
    val catalogConfig by viewModel.catalogConfig.collectAsStateWithLifecycle()
    val swapConfig by viewModel.swapConfig.collectAsStateWithLifecycle()
    val currentAppMode by viewModel.currentAppMode.collectAsStateWithLifecycle()
    val watermarkConfig by viewModel.watermarkConfig.collectAsStateWithLifecycle()
    val activeTab by viewModel.activeTab.collectAsStateWithLifecycle()
    val trackedFaces by viewModel.trackedFaces.collectAsStateWithLifecycle()
    val cameraSelector by viewModel.cameraSelector.collectAsStateWithLifecycle()
    val isRecording by viewModel.isRecording.collectAsStateWithLifecycle()
    val durationSec by viewModel.recordingDurationSec.collectAsStateWithLifecycle()
    val appLanguage by viewModel.appLanguage.collectAsStateWithLifecycle()
    val dualCameraConfig by viewModel.dualCameraConfig.collectAsStateWithLifecycle()
    val screenRecordConfig by viewModel.screenRecordConfig.collectAsStateWithLifecycle()
    val isSimpleMode by viewModel.isSimpleMode.collectAsStateWithLifecycle()
    val showAudit by viewModel.showPrivacyAudit.collectAsStateWithLifecycle()
    val statusMsg by viewModel.statusMessage.collectAsStateWithLifecycle()

    val selectedLightTime by viewModel.selectedLightTime.collectAsStateWithLifecycle()
    val selectedPoseGuide by viewModel.selectedPoseGuide.collectAsStateWithLifecycle()
    val isPoseGuideVisible by viewModel.isPoseGuideVisible.collectAsStateWithLifecycle()
    val activeMaisonGarment by viewModel.maisonRepo.activeGarment.collectAsStateWithLifecycle()

    val isPersian = appLanguage == AppLanguage.PERSIAN
    val isArabic = appLanguage == AppLanguage.ARABIC

    BackHandler {
        onNavigateBackToFaceCard()
    }

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
            isPersian = isPersian || isArabic,
            storyStreamConfig = storyStreamConfig,
            catalogConfig = catalogConfig,
            swapConfig = swapConfig,
            watermarkConfig = watermarkConfig,
            currentAppMode = currentAppMode,
            selectedLightTime = selectedLightTime,
            selectedPoseGuide = selectedPoseGuide,
            isPoseGuideVisible = isPoseGuideVisible,
            activeMaisonGarment = activeMaisonGarment,
            onTapAddManualZone = { offset ->
                viewModel.addManualPrivacyZone(offset, overlayWidth, overlayHeight)
            },
            modifier = Modifier.fillMaxSize()
        )

        // 2b. Secondary Inset Camera (Dual-Camera Picture-in-Picture)
        if (dualCameraConfig.enabled && !isInPipMode) {
            DualCameraInsetView(
                config = dualCameraConfig,
                language = appLanguage,
                onFlipPrimarySecondary = { viewModel.toggleCameraFacing() },
                onCycleCorner = {
                    val nextCorner = when (dualCameraConfig.corner) {
                        InsetCorner.TOP_RIGHT -> InsetCorner.BOTTOM_RIGHT
                        InsetCorner.BOTTOM_RIGHT -> InsetCorner.BOTTOM_LEFT
                        InsetCorner.BOTTOM_LEFT -> InsetCorner.TOP_LEFT
                        InsetCorner.TOP_LEFT -> InsetCorner.TOP_RIGHT
                    }
                    viewModel.updateDualCameraConfig(dualCameraConfig.copy(corner = nextCorner))
                },
                onClose = { viewModel.toggleDualCamera() }
            )
        }

        // If in Android System Picture-in-Picture mode, hide all chrome controls
        if (isInPipMode) {
            // Minimal PiP Tag
            Surface(
                color = Color.Black.copy(alpha = 0.6f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(8.dp)
            ) {
                Text(
                    text = "FaceGuard PiP",
                    color = Color.White,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            return@Box
        }

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
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Row 1: Badges & System Tools (AI Mode, Language, PiP, Dual Cam, Screen Rec, Simple Mode)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Group: Back to Cards + AI Status + Simple Mode Pill
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Back to FaceCard Dashboard
                    IconButton(
                        onClick = onNavigateBackToFaceCard,
                        modifier = Modifier
                            .size(34.dp)
                            .background(WarmSurface.copy(alpha = 0.92f), CircleShape)
                            .border(1.dp, WarmBorder, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.CreditCard,
                            contentDescription = "Back to FaceCard",
                            tint = TerracottaAccent,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Hybrid AI Status Badge (Offline Local vs Online Cloud)
                    val isOfflineAi = swapConfig.aiExecutionMode == AiEngineExecutionMode.OFFLINE_EDGE_LOCAL
                    Surface(
                        color = WarmSurface.copy(alpha = 0.92f),
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isOfflineAi) WarmBorder else TerracottaAccent
                        ),
                        modifier = Modifier.clickable {
                            val nextMode = if (isOfflineAi) {
                                AiEngineExecutionMode.ONLINE_CLOUD_HYBRID
                            } else {
                                AiEngineExecutionMode.OFFLINE_EDGE_LOCAL
                            }
                            viewModel.updateSwapConfig(swapConfig.copy(aiExecutionMode = nextMode))
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(if (isOfflineAi) SageGreen else TerracottaAccent)
                            )
                            Spacer(Modifier.width(5.dp))
                            Text(
                                text = when (appLanguage) {
                                    AppLanguage.PERSIAN -> if (isOfflineAi) "آفلاین (امن)" else "آنلاین (ابری)"
                                    AppLanguage.ARABIC -> if (isOfflineAi) "أوفلاين (آمن)" else "أونلاين (سحابي)"
                                    AppLanguage.ENGLISH -> if (isOfflineAi) "OFFLINE (SAFE)" else "ONLINE (CLOUD)"
                                },
                                color = CharcoalPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Simple Mode Toggle Button (حالت فوق‌العاده ساده و روان)
                    Surface(
                        color = if (isSimpleMode) SageGreenSubtle else WarmSurface.copy(alpha = 0.88f),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSimpleMode) SageGreen else WarmBorder
                        ),
                        modifier = Modifier.clickable { viewModel.toggleSimpleMode() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                        ) {
                            Icon(
                                Icons.Default.Bolt,
                                contentDescription = null,
                                tint = if (isSimpleMode) SageGreen else CharcoalSecondary,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(Modifier.width(3.dp))
                            Text(
                                text = when (appLanguage) {
                                    AppLanguage.PERSIAN -> if (isSimpleMode) "ساده (ردمی)" else "ساده"
                                    AppLanguage.ARABIC -> if (isSimpleMode) "بسيط (ردمي)" else "بسيط"
                                    AppLanguage.ENGLISH -> if (isSimpleMode) "Simple (Redmi)" else "Simple"
                                },
                                color = if (isSimpleMode) SageGreen else CharcoalPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Right Group: Tools (Language, PiP, Dual Cam, Screen Rec, Flip)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 3-way Language Switcher (فارسی / العربية / English)
                    Surface(
                        color = WarmSurface.copy(alpha = 0.92f),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
                        modifier = Modifier.clickable { viewModel.cycleLanguage() }
                    ) {
                        Text(
                            text = appLanguage.shortBadge,
                            color = CharcoalPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                        )
                    }

                    // Video Audio Mute / Unmute Button (دکمه میوت کردن مستقیم فیلمبرداری)
                    IconButton(
                        onClick = { viewModel.toggleMuteVideo() },
                        modifier = Modifier
                            .size(34.dp)
                            .background(
                                if (voiceConfig.isMuted) BrickRedSubtle else WarmSurface.copy(alpha = 0.92f),
                                CircleShape
                            )
                            .border(
                                1.dp,
                                if (voiceConfig.isMuted) BrickRed else WarmBorder,
                                CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = if (voiceConfig.isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                            contentDescription = if (voiceConfig.isMuted) "Unmute Audio" else "Mute Video Audio",
                            tint = if (voiceConfig.isMuted) BrickRed else TerracottaAccent,
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    // Picture-in-Picture Button (تصویر در تصویر سیستم)
                    IconButton(
                        onClick = onEnterPip,
                        modifier = Modifier
                            .size(34.dp)
                            .background(WarmSurface.copy(alpha = 0.92f), CircleShape)
                            .border(1.dp, WarmBorder, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.PictureInPictureAlt,
                            contentDescription = "Picture-in-Picture",
                            tint = CharcoalPrimary,
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    // Dual Camera PiP Toggle Button (فیلمبرداری دو دوربینه)
                    IconButton(
                        onClick = { viewModel.toggleDualCamera() },
                        modifier = Modifier
                            .size(34.dp)
                            .background(
                                if (dualCameraConfig.enabled) TerracottaSubtle else WarmSurface.copy(alpha = 0.92f),
                                CircleShape
                            )
                            .border(
                                1.dp,
                                if (dualCameraConfig.enabled) TerracottaAccent else WarmBorder,
                                CircleShape
                            )
                    ) {
                        Icon(
                            Icons.Default.Cameraswitch,
                            contentDescription = "Dual-Camera PiP",
                            tint = if (dualCameraConfig.enabled) TerracottaAccent else CharcoalPrimary,
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    // Screen Recording with Voice Changer Button (ضبط صفحه با تغییر صدا)
                    IconButton(
                        onClick = { viewModel.toggleScreenRecording() },
                        modifier = Modifier
                            .size(34.dp)
                            .background(
                                if (screenRecordConfig.isRecordingScreen) BrickRed else WarmSurface.copy(alpha = 0.92f),
                                CircleShape
                            )
                            .border(
                                1.dp,
                                if (screenRecordConfig.isRecordingScreen) BrickRed else WarmBorder,
                                CircleShape
                            )
                    ) {
                        Icon(
                            Icons.Default.ScreenShare,
                            contentDescription = "Screen Record Voice",
                            tint = if (screenRecordConfig.isRecordingScreen) Color.White else CharcoalPrimary,
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    // Flip Camera
                    IconButton(
                        onClick = { viewModel.toggleCameraFacing() },
                        modifier = Modifier
                            .size(34.dp)
                            .background(WarmSurface.copy(alpha = 0.92f), CircleShape)
                            .border(1.dp, WarmBorder, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.FlipCameraAndroid,
                            contentDescription = "Switch Camera",
                            tint = CharcoalPrimary,
                            modifier = Modifier.size(17.dp)
                        )
                    }

                    // Emergency Stealth Disguise Button (دکمه استتار اضطراری ماشین حساب)
                    IconButton(
                        onClick = onTriggerStealth,
                        modifier = Modifier
                            .size(34.dp)
                            .background(WarmSurface.copy(alpha = 0.92f), CircleShape)
                            .border(1.dp, WarmBorder, CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Calculate,
                            contentDescription = "Stealth Calculator",
                            tint = TerracottaAccent,
                            modifier = Modifier.size(17.dp)
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
                            text = when (appLanguage) {
                                AppLanguage.PERSIAN -> "چهره‌های محافظت‌شده: ${if (privacyConfig.autoFaceTracking) trackedFaces.size else 0}"
                                AppLanguage.ARABIC -> "الوجوه المحمية: ${if (privacyConfig.autoFaceTracking) trackedFaces.size else 0}"
                                AppLanguage.ENGLISH -> "Protected Faces: ${if (privacyConfig.autoFaceTracking) trackedFaces.size else 0}"
                            },
                            color = CharcoalPrimary,
                            fontSize = 11.sp
                        )
                    }
                }

                // Screen Recording or Camera Recording Status Tag
                if (isRecording || screenRecordConfig.isRecordingScreen) {
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
                                text = if (screenRecordConfig.isRecordingScreen) "SCREEN REC $timerStr" else "REC $timerStr",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // 1-Tap Quick Modes Bar
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(AppModePreset.values()) { preset ->
                    val isSelected = currentAppMode == preset
                    Surface(
                        color = if (isSelected) TerracottaAccent else WarmSurface.copy(alpha = 0.88f),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) TerracottaAccent else WarmBorder
                        ),
                        modifier = Modifier.clickable { viewModel.applyAppModePreset(preset) }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            if (isSelected) {
                                Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(Color.White))
                                Spacer(Modifier.width(5.dp))
                            }
                            Text(
                                text = when (appLanguage) {
                                    AppLanguage.PERSIAN -> preset.titleFa
                                    AppLanguage.ARABIC -> when (preset) {
                                        AppModePreset.STANDARD -> "استوديو قياسي"
                                        AppModePreset.TRIPLE_AI_SHIELD -> "الدرع الثلاثي الذكي (تتبع + طمس + تبديل)"
                                        AppModePreset.BOUTIQUE_SELLER -> "متجر وتجربة ملابس"
                                        AppModePreset.ANONYMOUS_REPORTER -> "مراسل آمن ومجهول"
                                        AppModePreset.FACELESS_VLOG -> "فلوغ وقصة بدون وجه"
                                        AppModePreset.INTERVIEW_DOC -> "تصوير المقابلات"
                                        AppModePreset.CINEMATIC_SHORT -> "فيلم سينمائي قصير"
                                    }
                                    AppLanguage.ENGLISH -> preset.titleEn
                                },
                                color = if (isSelected) Color.White else CharcoalPrimary,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
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
                    .padding(top = 135.dp, start = 16.dp, end = 16.dp)
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

        // 4. Bottom Controls: Either Simple Mode or Full Studio Tabs
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            if (isSimpleMode) {
                // ============================================
                // ULTRA-SIMPLE MODE (ساده، روان و بدون لگ برای ردمی نوت ۸)
                // ============================================
                Surface(
                    color = WarmSurface,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Quick Action Buttons Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 1. Triple Shield Button
                            Button(
                                onClick = { viewModel.applyAppModePreset(AppModePreset.TRIPLE_AI_SHIELD) },
                                colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text(
                                        text = when (appLanguage) {
                                            AppLanguage.PERSIAN -> "سپر ۳تایی"
                                            AppLanguage.ARABIC -> "الدرع الثلاثي"
                                            AppLanguage.ENGLISH -> "Triple Shield"
                                        },
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }

                            Spacer(Modifier.width(8.dp))

                            // 2. Dual Camera Quick Button
                            Button(
                                onClick = { viewModel.toggleDualCamera() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (dualCameraConfig.enabled) SageGreen else WarmSurfaceSecondary,
                                    contentColor = if (dualCameraConfig.enabled) Color.White else CharcoalPrimary
                                ),
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (dualCameraConfig.enabled) SageGreen else WarmBorder),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Cameraswitch, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text(
                                        text = when (appLanguage) {
                                            AppLanguage.PERSIAN -> "دوربین ۲تایی"
                                            AppLanguage.ARABIC -> "كاميرا مزدوجة"
                                            AppLanguage.ENGLISH -> "Dual Cam"
                                        },
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }

                        // Shutter Row in Simple Mode
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = onNavigateToGallery,
                                modifier = Modifier
                                    .size(52.dp)
                                    .background(WarmSurfaceSecondary, CircleShape)
                                    .border(1.dp, WarmBorder, CircleShape)
                            ) {
                                Icon(Icons.Default.VideoLibrary, contentDescription = "Gallery", tint = CharcoalPrimary)
                            }

                            // Big Record Button
                            Box(
                                modifier = Modifier
                                    .size(76.dp)
                                    .clickable { viewModel.toggleRecording() },
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(CircleShape)
                                        .border(3.dp, if (isRecording) BrickRed else WarmBorder, CircleShape)
                                )
                                Box(
                                    modifier = Modifier
                                        .size(if (isRecording) 28.dp else 60.dp)
                                        .clip(if (isRecording) RoundedCornerShape(6.dp) else CircleShape)
                                        .background(if (isRecording) BrickRed else TerracottaAccent)
                                )
                            }

                            // Quick Mask Toggle Button
                            IconButton(
                                onClick = {
                                    val nextMask = if (privacyConfig.maskType == PrivacyMaskType.NONE) {
                                        PrivacyMaskType.EYES_VISOR
                                    } else {
                                        PrivacyMaskType.NONE
                                    }
                                    viewModel.updatePrivacyConfig(privacyConfig.copy(maskType = nextMask))
                                },
                                modifier = Modifier
                                    .size(52.dp)
                                    .background(WarmSurfaceSecondary, CircleShape)
                                    .border(1.dp, if (privacyConfig.maskType != PrivacyMaskType.NONE) TerracottaAccent else WarmBorder, CircleShape)
                            ) {
                                Icon(
                                    imageVector = if (privacyConfig.maskType != PrivacyMaskType.NONE) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Mask",
                                    tint = if (privacyConfig.maskType != PrivacyMaskType.NONE) TerracottaAccent else CharcoalSecondary
                                )
                            }
                        }
                    }
                }
            } else {
                // ============================================
                // ADVANCED STUDIO TABS & SHUTTER CONTROLS
                // ============================================
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
                                isPersian = isPersian || isArabic,
                                onUpdate = { viewModel.updatePrivacyConfig(it) },
                                onClearManualZones = { viewModel.clearManualZones() }
                            )
                            ActiveStudioTab.BACKGROUND -> BackgroundStudioPanel(
                                bgConfig = bgConfig,
                                isPersian = isPersian || isArabic,
                                onUpdate = { viewModel.updateBackgroundConfig(it) }
                            )
                            ActiveStudioTab.STYLE -> FaceStyleStudioPanel(
                                styleConfig = faceStyleConfig,
                                isPersian = isPersian || isArabic,
                                onUpdate = { viewModel.updateFaceStyleConfig(it) }
                            )
                            ActiveStudioTab.BODY -> BodySilhouetteStudioPanel(
                                silhouetteConfig = silhouetteConfig,
                                isPersian = isPersian || isArabic,
                                onUpdate = { viewModel.updateSilhouetteConfig(it) }
                            )
                            ActiveStudioTab.VOICE -> VoiceStudioPanel(
                                voiceConfig = voiceConfig,
                                isPersian = isPersian || isArabic,
                                onUpdate = { viewModel.updateVoiceConfig(it) }
                            )
                            ActiveStudioTab.SWAP -> FaceBodySwapPanel(
                                config = swapConfig,
                                isPersian = isPersian || isArabic,
                                onUpdate = { viewModel.updateSwapConfig(it) },
                                onActivateTripleShield = { viewModel.applyAppModePreset(AppModePreset.TRIPLE_AI_SHIELD) }
                            )
                            ActiveStudioTab.STORY_STREAM -> StoryLiveStudioPanel(
                                config = storyStreamConfig,
                                isPersian = isPersian || isArabic,
                                onUpdate = { viewModel.updateStoryStreamConfig(it) },
                                onToggleStream = { viewModel.toggleLiveStream() }
                            )
                            ActiveStudioTab.CATALOG -> CatalogStudioPanel(
                                config = catalogConfig,
                                isPersian = isPersian || isArabic,
                                onUpdate = { viewModel.updateCatalogConfig(it) }
                            )
                            ActiveStudioTab.WEDDING_STRESS_RELIEF -> WeddingStressReliefPanel(
                                selectedLightTime = selectedLightTime,
                                onSelectLightTime = { viewModel.updateLightTime(it) },
                                selectedPoseGuide = selectedPoseGuide,
                                onSelectPoseGuide = { viewModel.updatePoseGuide(it) },
                                isPoseGuideVisible = isPoseGuideVisible,
                                onTogglePoseGuide = { viewModel.togglePoseGuide(it) }
                            )
                            ActiveStudioTab.MAISON_TRYON -> com.example.ui.maison.MaisonBoutiqueHubView(
                                maisonRepo = viewModel.maisonRepo,
                                onNavigateToStudio = {}
                            )
                            ActiveStudioTab.SETTINGS -> SettingsPerformancePanel(
                                preset = performancePreset,
                                isPersian = isPersian || isArabic,
                                onPresetChange = { viewModel.setPerformancePreset(it) },
                                onShowPrivacyAudit = { viewModel.setShowPrivacyAudit(true) },
                                watermarkConfig = watermarkConfig,
                                onWatermarkUpdate = { viewModel.updateWatermarkConfig(it) }
                            )
                            ActiveStudioTab.NONE -> {}
                        }
                    }
                }

                // Studio Bottom Tabs Bar
                Surface(
                    color = WarmSurface.copy(alpha = 0.96f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
                ) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val tabs = listOf(
                            Triple(
                                ActiveStudioTab.SWAP,
                                when (appLanguage) {
                                    AppLanguage.PERSIAN -> "مدلینگ و سواپ"
                                    AppLanguage.ARABIC -> "الموديل والتبديل"
                                    AppLanguage.ENGLISH -> "AI & Swap"
                                },
                                Icons.Default.AutoAwesome
                            ),
                            Triple(
                                ActiveStudioTab.PRIVACY,
                                when (appLanguage) {
                                    AppLanguage.PERSIAN -> "حریم چهره"
                                    AppLanguage.ARABIC -> "خصوصية الوجه"
                                    AppLanguage.ENGLISH -> "Face Shield"
                                },
                                Icons.Default.Shield
                            ),
                            Triple(
                                ActiveStudioTab.BODY,
                                when (appLanguage) {
                                    AppLanguage.PERSIAN -> "تغییر اندام"
                                    AppLanguage.ARABIC -> "تعديل القوام"
                                    AppLanguage.ENGLISH -> "Body Shape"
                                },
                                Icons.Default.AccessibilityNew
                            ),
                            Triple(
                                ActiveStudioTab.BACKGROUND,
                                when (appLanguage) {
                                    AppLanguage.PERSIAN -> "اتاق و پس‌زمینه"
                                    AppLanguage.ARABIC -> "الغرفة والخلفية"
                                    AppLanguage.ENGLISH -> "Background"
                                },
                                Icons.Default.Image
                            ),
                            Triple(
                                ActiveStudioTab.VOICE,
                                when (appLanguage) {
                                    AppLanguage.PERSIAN -> "فیلتر صدا"
                                    AppLanguage.ARABIC -> "تغيير الصوت"
                                    AppLanguage.ENGLISH -> "Voice FX"
                                },
                                Icons.Default.Mic
                            ),
                            Triple(
                                ActiveStudioTab.CATALOG,
                                when (appLanguage) {
                                    AppLanguage.PERSIAN -> "کاتالوگ فروش"
                                    AppLanguage.ARABIC -> "كتالوج المبيعات"
                                    AppLanguage.ENGLISH -> "Catalog"
                                },
                                Icons.Default.Sell
                            ),
                            Triple(
                                ActiveStudioTab.STORY_STREAM,
                                when (appLanguage) {
                                    AppLanguage.PERSIAN -> "استوری و لایو"
                                    AppLanguage.ARABIC -> "ستوري وبث"
                                    AppLanguage.ENGLISH -> "Story Live"
                                },
                                Icons.Default.LiveTv
                            ),
                            Triple(
                                ActiveStudioTab.WEDDING_STRESS_RELIEF,
                                when (appLanguage) {
                                    AppLanguage.PERSIAN -> "آرامش عروسی و نور"
                                    AppLanguage.ARABIC -> "هدوء العرس والنور"
                                    AppLanguage.ENGLISH -> "Wedding Zen"
                                },
                                Icons.Default.VolunteerActivism
                            ),
                            Triple(
                                ActiveStudioTab.MAISON_TRYON,
                                when (appLanguage) {
                                    AppLanguage.PERSIAN -> "پرو مزون و سلبریتی"
                                    AppLanguage.ARABIC -> "برو الميزون والمشاهير"
                                    AppLanguage.ENGLISH -> "Maison Fitting"
                                },
                                Icons.Default.Checkroom
                            ),
                            Triple(
                                ActiveStudioTab.SETTINGS,
                                when (appLanguage) {
                                    AppLanguage.PERSIAN -> "تنظیمات"
                                    AppLanguage.ARABIC -> "الإعدادات"
                                    AppLanguage.ENGLISH -> "Settings"
                                },
                                Icons.Default.Settings
                            )
                        )

                        items(tabs) { (tab, title, icon) ->
                            val isSelected = activeTab == tab
                            Surface(
                                color = if (isSelected) TerracottaAccent else WarmSurfaceSecondary,
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
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
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

                    // Shutter Button
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
                            val nextMask = if (privacyConfig.maskType == PrivacyMaskType.NONE) {
                                PrivacyMaskType.EYES_VISOR
                            } else {
                                PrivacyMaskType.NONE
                            }
                            viewModel.updatePrivacyConfig(privacyConfig.copy(maskType = nextMask))
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .background(WarmSurface.copy(alpha = 0.92f), CircleShape)
                            .border(
                                1.dp,
                                if (privacyConfig.maskType != PrivacyMaskType.NONE) TerracottaAccent else WarmBorder,
                                CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = if (privacyConfig.maskType != PrivacyMaskType.NONE) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Toggle Mask",
                            tint = if (privacyConfig.maskType != PrivacyMaskType.NONE) TerracottaAccent else CharcoalSecondary,
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
            isPersian = isPersian || isArabic,
            onDismiss = { viewModel.setShowPrivacyAudit(false) }
        )
    }
}
