package com.example.ui.overlay

import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppModePreset
import com.example.model.BackgroundConfig
import com.example.model.BackgroundMode
import com.example.model.BlurType
import com.example.model.BundledEnvironments
import com.example.model.BundledSwapItems
import com.example.model.ConfidencePoseGuideType
import com.example.model.FaceStyleConfig
import com.example.model.FilterPreset
import com.example.model.MaisonGarmentItem
import com.example.model.PrivacyConfig
import com.example.model.PrivacyMaskType
import com.example.model.ProductCatalogConfig
import com.example.model.StoryStreamConfig
import com.example.model.SwapConfig
import com.example.model.TrackedFace
import com.example.model.WatermarkConfig
import com.example.model.WatermarkPosition
import com.example.model.WeddingDayLightTime
import com.example.ui.theme.CharcoalPrimary
import com.example.ui.theme.CharcoalSecondary
import com.example.ui.theme.CharcoalTertiary
import com.example.ui.theme.MaskCensorCharcoal
import com.example.ui.theme.MaskCensorTerracotta
import com.example.ui.theme.MutedAmber
import com.example.ui.theme.SageGreen
import com.example.ui.theme.TerracottaAccent
import com.example.ui.theme.WarmBackground
import com.example.ui.theme.WarmBorder
import com.example.ui.theme.WarmSurface
import kotlin.math.roundToInt

@Composable
fun PrivacyCameraOverlay(
    trackedFaces: List<TrackedFace>,
    privacyConfig: PrivacyConfig,
    backgroundConfig: BackgroundConfig,
    faceStyleConfig: FaceStyleConfig,
    isPersian: Boolean,
    modifier: Modifier = Modifier,
    storyStreamConfig: StoryStreamConfig = StoryStreamConfig(),
    catalogConfig: ProductCatalogConfig = ProductCatalogConfig(),
    swapConfig: SwapConfig = SwapConfig(),
    watermarkConfig: WatermarkConfig = WatermarkConfig(),
    currentAppMode: AppModePreset = AppModePreset.STANDARD,
    selectedLightTime: WeddingDayLightTime = WeddingDayLightTime.NATURAL_GARDEN_DAY,
    selectedPoseGuide: ConfidencePoseGuideType = ConfidencePoseGuideType.GRAND_ENTRY,
    isPoseGuideVisible: Boolean = false,
    activeMaisonGarment: MaisonGarmentItem? = null,
    onTapAddManualZone: (Offset) -> Unit = {}
) {
    // Load virtual background image if selected
    val activeEnv = remember(backgroundConfig.selectedEnvId) {
        BundledEnvironments.items.find { it.id == backgroundConfig.selectedEnvId }
            ?: BundledEnvironments.items.first()
    }
    val virtualBgBitmap = ImageBitmap.imageResource(activeEnv.drawableResId)

    // Load Face Swap Avatar and Body Swap Mannequin
    val activeAvatar = remember(swapConfig.selectedAvatarId) {
        BundledSwapItems.avatars.find { it.id == swapConfig.selectedAvatarId }
            ?: BundledSwapItems.avatars.first()
    }
    val avatarBitmap = ImageBitmap.imageResource(activeAvatar.drawableResId)

    val activeMannequin = remember(swapConfig.selectedBodyId) {
        BundledSwapItems.mannequins.find { it.id == swapConfig.selectedBodyId }
            ?: BundledSwapItems.mannequins.first()
    }
    val mannequinBitmap = ImageBitmap.imageResource(activeMannequin.drawableResId)

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onTapAddManualZone(offset)
                }
            }
    ) {
        // Main Overlay Canvas for Real-time Privacy & FX
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height

            // 1. Render Background Studio FX if enabled
            renderBackgroundLayer(
                canvasW = canvasW,
                canvasH = canvasH,
                bgConfig = backgroundConfig,
                bgBitmap = virtualBgBitmap
            )

            // 2. Render Color Grading / Face Style filter overlay
            renderStyleFilter(
                canvasW = canvasW,
                canvasH = canvasH,
                styleConfig = faceStyleConfig
            )

            // 2b. Render Body Swap Mannequin if enabled (فرم طاقچه‌ای گرد باسن و لیفت سینه)
            if (swapConfig.bodySwapEnabled) {
                val bWidth = (canvasW * 0.72f * swapConfig.bodyScale).roundToInt()
                val bHeight = (canvasH * 0.58f * swapConfig.bodyScale).roundToInt()
                val bLeft = ((canvasW - bWidth) / 2f).roundToInt()
                val bTop = (canvasH * 0.36f + swapConfig.bodyOffsetY).roundToInt()

                drawImage(
                    image = mannequinBitmap,
                    dstOffset = androidx.compose.ui.unit.IntOffset(bLeft, bTop),
                    dstSize = androidx.compose.ui.unit.IntSize(bWidth, bHeight),
                    alpha = swapConfig.bodyBlendAlpha
                )

                // Sculpted Shelf-Butt and Bust Lift accent highlights if configured
                if (swapConfig.shelfButtContour > 0.8f || swapConfig.bustLiftFirmness > 0.8f) {
                    val hipCenterY = bTop + bHeight * 0.74f
                    val bustCenterY = bTop + bHeight * 0.30f
                    val centerX = canvasW / 2f
                    val hipSpan = (bWidth * 0.44f * swapConfig.shelfButtContour).coerceAtMost(bWidth * 0.52f)
                    val bustSpan = (bWidth * 0.38f * swapConfig.bustLiftFirmness).coerceAtMost(bWidth * 0.48f)

                    // Bust Lift Firmness curve (لیفت سینه به سمت بالا)
                    if (swapConfig.bustLiftFirmness > 0.8f) {
                        val bustColor = TerracottaAccent.copy(alpha = (0.28f * swapConfig.bodyBlendAlpha).coerceIn(0.1f, 0.4f))
                        drawArc(
                            color = bustColor,
                            startAngle = 10f,
                            sweepAngle = 160f,
                            useCenter = false,
                            topLeft = Offset(centerX - bustSpan, bustCenterY - 14f),
                            size = Size(bustSpan, 28f),
                            style = Stroke(width = 2.2f)
                        )
                        drawArc(
                            color = bustColor,
                            startAngle = 10f,
                            sweepAngle = 160f,
                            useCenter = false,
                            topLeft = Offset(centerX, bustCenterY - 14f),
                            size = Size(bustSpan, 28f),
                            style = Stroke(width = 2.2f)
                        )
                    }

                    // Shelf-Butt High Brazilian Contour curve (طاقچه‌ای و گرد کردن باسن)
                    if (swapConfig.shelfButtContour > 0.8f) {
                        val hipColor = TerracottaAccent.copy(alpha = (0.32f * swapConfig.bodyBlendAlpha).coerceIn(0.1f, 0.45f))
                        // Shelf ledge (خط بالای طاقچه)
                        drawArc(
                            color = hipColor,
                            startAngle = 190f,
                            sweepAngle = 160f,
                            useCenter = false,
                            topLeft = Offset(centerX - hipSpan, hipCenterY - 18f),
                            size = Size(hipSpan * 2f, 36f),
                            style = Stroke(width = 2.5f)
                        )
                    }
                }
            }

            // 3. Render Privacy for all detected faces
            val facesToRender = if (privacyConfig.autoFaceTracking && trackedFaces.isNotEmpty()) {
                if (privacyConfig.multiFaceEnabled) trackedFaces else listOf(trackedFaces.first())
            } else emptyList()

            for (face in facesToRender) {
                // Apply dynamic safety margin expansion:
                val margin = if (face.confidence < 0.85f) {
                    privacyConfig.safetyMarginMultiplier * 1.25f
                } else {
                    privacyConfig.safetyMarginMultiplier
                }

                val centerX = face.smoothedBounds.centerX() * canvasW
                val centerY = face.smoothedBounds.centerY() * canvasH
                val faceW = (face.smoothedBounds.width() * canvasW * margin)
                val faceH = (face.smoothedBounds.height() * canvasH * margin)

                val left = (centerX - faceW / 2f).coerceAtLeast(0f)
                val top = (centerY - faceH / 2f).coerceAtLeast(0f)
                val right = (centerX + faceW / 2f).coerceAtMost(canvasW)
                val bottom = (centerY + faceH / 2f).coerceAtMost(canvasH)

                val faceRect = RectF(left, top, right, bottom)

                // 2b. Live Beauty Retouch & Blemish Removal (رتوش، حذف جوش، کک و مک، لک و پیسی)
                if (faceStyleConfig.blemishRemoval > 0.05f || faceStyleConfig.skinSmoothing > 0.05f || faceStyleConfig.skinToneBalance != 0f) {
                    drawFacialBeautyAndBlemishTreatment(
                        rect = faceRect,
                        blemishRemoval = faceStyleConfig.blemishRemoval,
                        skinSmoothing = faceStyleConfig.skinSmoothing,
                        skinToneBalance = faceStyleConfig.skinToneBalance
                    )
                }

                // 2c. Digital Makeup: Lip Tint & Blush (آرایش لب و رژگونه)
                if (faceStyleConfig.lipTintIntensity > 0.05f || faceStyleConfig.blushIntensity > 0.05f) {
                    drawDigitalMakeup(
                        rect = faceRect,
                        eyeRatio = face.eyeCenterY,
                        mouthRatio = face.mouthCenterY,
                        lipTint = faceStyleConfig.lipTintIntensity,
                        blush = faceStyleConfig.blushIntensity
                    )
                }

                // 3a. Blur / Pixelation layer
                when (privacyConfig.blurType) {
                    BlurType.PIXELATE -> {
                        drawPixelatedFace(
                            rect = faceRect,
                            blockSize = privacyConfig.pixelBlockSize.toFloat(),
                            intensity = privacyConfig.blurIntensity
                        )
                    }
                    BlurType.GAUSSIAN -> {
                        drawGaussianBlurredFace(
                            rect = faceRect,
                            intensity = privacyConfig.blurIntensity
                        )
                    }
                    BlurType.NONE -> { /* No blur requested */ }
                }

                // 3b. Privacy Mask layer
                drawPrivacyMask(
                    rect = faceRect,
                    maskType = privacyConfig.maskType,
                    eyeRatio = face.eyeCenterY,
                    mouthRatio = face.mouthCenterY
                )

                // 3c. Face Swap Avatar layer
                if (swapConfig.faceSwapEnabled) {
                    val fLeft = faceRect.left.roundToInt()
                    val fTop = faceRect.top.roundToInt()
                    val fWidth = faceRect.width().roundToInt().coerceAtLeast(10)
                    val fHeight = faceRect.height().roundToInt().coerceAtLeast(10)

                    drawImage(
                        image = avatarBitmap,
                        dstOffset = androidx.compose.ui.unit.IntOffset(fLeft, fTop),
                        dstSize = androidx.compose.ui.unit.IntSize(fWidth, fHeight),
                        alpha = swapConfig.faceBlendAlpha
                    )
                }
            }

            // 4. Render Manual user-placed Privacy Zones
            for (zone in privacyConfig.manualZones) {
                val zLeft = zone.bounds.left * canvasW
                val zTop = zone.bounds.top * canvasH
                val zW = zone.bounds.width() * canvasW
                val zH = zone.bounds.height() * canvasH
                val zRect = RectF(zLeft, zTop, zLeft + zW, zTop + zH)

                drawPixelatedFace(
                    rect = zRect,
                    blockSize = 20f,
                    intensity = 0.9f
                )
                drawRoundRect(
                    color = TerracottaAccent.copy(alpha = 0.8f),
                    topLeft = Offset(zLeft, zTop),
                    size = Size(zW, zH),
                    cornerRadius = CornerRadius(6f, 6f),
                    style = Stroke(width = 1.5f)
                )
            }

            // 4b. Cinematic 21:9 Aspect Ratio Letterboxing
            if (currentAppMode == AppModePreset.CINEMATIC_SHORT) {
                val barH = canvasH * 0.11f
                drawRect(color = Color.Black, topLeft = Offset(0f, 0f), size = Size(canvasW, barH))
                drawRect(color = Color.Black, topLeft = Offset(0f, canvasH - barH), size = Size(canvasW, barH))
            }

            // 4c. Documentary Interview Framing Grid (Rule of Thirds)
            if (currentAppMode == AppModePreset.INTERVIEW_DOC) {
                val gridColor = Color.White.copy(alpha = 0.25f)
                drawLine(gridColor, Offset(canvasW * 0.333f, 0f), Offset(canvasW * 0.333f, canvasH), strokeWidth = 1.5f)
                drawLine(gridColor, Offset(canvasW * 0.666f, 0f), Offset(canvasW * 0.666f, canvasH), strokeWidth = 1.5f)
                drawLine(gridColor, Offset(0f, canvasH * 0.333f), Offset(canvasW, canvasH * 0.333f), strokeWidth = 1.5f)
                drawLine(gridColor, Offset(0f, canvasH * 0.666f), Offset(canvasW, canvasH * 0.666f), strokeWidth = 1.5f)
            }

            // 4d. Wedding Day Light Time Machine Ambient Filter (ماشین زمان نور روز واقعه)
            if (selectedLightTime != WeddingDayLightTime.NATURAL_GARDEN_DAY) {
                drawRect(
                    color = Color(selectedLightTime.filterColorHex),
                    topLeft = Offset(0f, 0f),
                    size = Size(canvasW, canvasH)
                )
            }

            // 4e. Confidence Pose Guide & Golden Ratio Lines (خطوط طلایی ژست‌های بدون استرس)
            if (isPoseGuideVisible) {
                val goldenColor = Color(0xFFE5A93C).copy(alpha = 0.55f)
                drawLine(goldenColor, Offset(canvasW * 0.38f, 0f), Offset(canvasW * 0.38f, canvasH), strokeWidth = 1.6f)
                drawLine(goldenColor, Offset(canvasW * 0.62f, 0f), Offset(canvasW * 0.62f, canvasH), strokeWidth = 1.6f)
                drawOval(
                    color = goldenColor,
                    topLeft = Offset(canvasW * 0.22f, canvasH * 0.12f),
                    size = Size(canvasW * 0.56f, canvasH * 0.52f),
                    style = Stroke(width = 2.0f)
                )
            }
        }

        // 4f. Partner Maison Boutique Badge on Camera Viewfinder
        if (activeMaisonGarment != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 14.dp, top = 82.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(WarmSurface.copy(alpha = 0.92f))
                    .border(1.dp, TerracottaAccent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(TerracottaAccent))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "🏛️ ${activeMaisonGarment.maisonName} (پرو رسمی)",
                        fontSize = 10.sp,
                        color = CharcoalPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 5. Clean, Professional Alteration Transparency Badge (Warm, restrained, NOT neon)
        if (faceStyleConfig.showAlterationBadge &&
            (faceStyleConfig.preset != FilterPreset.NATURAL ||
             privacyConfig.maskType != PrivacyMaskType.NONE ||
             backgroundConfig.mode != BackgroundMode.ORIGINAL)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(WarmSurface.copy(alpha = 0.92f))
                    .border(1.dp, WarmBorder, RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(TerracottaAccent)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = if (isPersian) "تغییر دیجیتالی اعمال شده" else "Digitally Altered",
                        color = CharcoalPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // 6. Live Broadcast Status Badge
        if (storyStreamConfig.isLiveStreaming) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFC24134))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(7.dp).clip(CircleShape).background(Color.White))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "LIVE • ${storyStreamConfig.selectedPlatform.displayName}",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 7. Story Headline / Sticker Overlay
        if (storyStreamConfig.storyHeadline.isNotBlank()) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 130.dp, start = 24.dp, end = 24.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(WarmSurface.copy(alpha = 0.94f))
                    .border(1.dp, WarmBorder, RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = storyStreamConfig.storyHeadline,
                    color = CharcoalPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // 8. Boutique & Underwear Product Price Tag
        if (catalogConfig.showPriceBadge) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(WarmSurface.copy(alpha = 0.94f))
                    .border(1.dp, WarmBorder, RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = catalogConfig.productName,
                        color = CharcoalPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = catalogConfig.price,
                            color = TerracottaAccent,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "• ${catalogConfig.sizes}",
                            color = CharcoalSecondary,
                            fontSize = 11.sp
                        )
                    }
                    Text(
                        text = catalogConfig.telegramChannel,
                        color = CharcoalTertiary,
                        fontSize = 10.sp
                    )
                }
            }
        }

        // 9. Anti-Theft Brand Watermark
        if (watermarkConfig.enabled && watermarkConfig.handleText.isNotBlank()) {
            val align = when (watermarkConfig.position) {
                WatermarkPosition.BOTTOM_RIGHT -> Alignment.BottomEnd
                WatermarkPosition.BOTTOM_LEFT -> Alignment.BottomStart
                WatermarkPosition.TOP_RIGHT -> Alignment.TopEnd
                WatermarkPosition.CENTER_TILED -> Alignment.Center
            }
            Box(
                modifier = Modifier
                    .align(align)
                    .padding(if (watermarkConfig.position == WatermarkPosition.CENTER_TILED) 0.dp else 24.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.Black.copy(alpha = watermarkConfig.opacity * 0.7f))
                    .border(1.dp, Color.White.copy(alpha = watermarkConfig.opacity * 0.4f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(5.dp).clip(CircleShape).background(TerracottaAccent))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = watermarkConfig.handleText,
                        color = Color.White.copy(alpha = watermarkConfig.opacity),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// ----------------- Canvas Rendering Helpers -----------------

private fun DrawScope.renderBackgroundLayer(
    canvasW: Float,
    canvasH: Float,
    bgConfig: BackgroundConfig,
    bgBitmap: ImageBitmap?
) {
    when (bgConfig.mode) {
        BackgroundMode.ORIGINAL -> {
            // Nothing to draw over camera stream
        }
        BackgroundMode.SOLID_COLOR -> {
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(bgConfig.solidColor).copy(alpha = 0.80f),
                        Color(bgConfig.solidColor).copy(alpha = 0.95f)
                    ),
                    center = Offset(canvasW / 2f, canvasH / 2f),
                    radius = canvasW
                )
            )
        }
        BackgroundMode.CHROMA_KEY, BackgroundMode.VIRTUAL_IMAGE -> {
            if (bgBitmap != null) {
                val alpha = if (bgConfig.mode == BackgroundMode.CHROMA_KEY) 0.88f else 0.82f
                drawImage(
                    image = bgBitmap,
                    dstSize = androidx.compose.ui.unit.IntSize(canvasW.roundToInt(), canvasH.roundToInt()),
                    alpha = alpha
                )
            }
        }
        BackgroundMode.BLUR -> {
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.Transparent,
                        CharcoalPrimary.copy(alpha = 0.55f)
                    ),
                    center = Offset(canvasW / 2f, canvasH * 0.45f),
                    radius = canvasW * 0.75f
                )
            )
        }
    }
}

private fun DrawScope.renderStyleFilter(
    canvasW: Float,
    canvasH: Float,
    styleConfig: FaceStyleConfig
) {
    when (styleConfig.preset) {
        FilterPreset.NATURAL -> {}
        FilterPreset.SMOOTH_WARM -> {
            drawRect(
                color = Color(0xFFF0A060).copy(alpha = 0.08f * (1f + styleConfig.warmth))
            )
        }
        FilterPreset.BRONZE_GLOW -> {
            drawRect(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFFD49A6A).copy(alpha = 0.15f),
                        Color(0xFFB87333).copy(alpha = 0.12f)
                    )
                )
            )
        }
        FilterPreset.PORCELAIN_BRIGHT -> {
            drawRect(
                brush = Brush.radialGradient(
                    listOf(
                        Color(0xFFFFFFFF).copy(alpha = 0.12f),
                        Color(0xFFF5F0FF).copy(alpha = 0.06f)
                    ),
                    center = Offset(canvasW / 2f, canvasH * 0.4f),
                    radius = canvasW * 0.8f
                )
            )
        }
        FilterPreset.GLAMOUR_STUDIO -> {
            drawRect(
                brush = Brush.radialGradient(
                    listOf(
                        Color(0xFFFFE4E1).copy(alpha = 0.10f),
                        Color(0xFF800020).copy(alpha = 0.05f)
                    ),
                    center = Offset(canvasW / 2f, canvasH * 0.4f),
                    radius = canvasW * 0.9f
                )
            )
        }
        FilterPreset.COOL_CYBER -> {
            drawRect(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF5A7B8C).copy(alpha = 0.12f),
                        Color(0xFF4A5F6E).copy(alpha = 0.10f)
                    )
                )
            )
        }
        FilterPreset.NOIR_BW -> {
            drawRect(
                color = CharcoalPrimary.copy(alpha = 0.40f)
            )
        }
        FilterPreset.VINTAGE_SEPIA -> {
            drawRect(
                color = Color(0xFF8B6B48).copy(alpha = 0.16f)
            )
        }
        FilterPreset.VIVID_CONTRAST -> {
            drawRect(
                brush = Brush.radialGradient(
                    listOf(
                        Color.Transparent,
                        CharcoalPrimary.copy(alpha = 0.20f)
                    )
                )
            )
        }
        FilterPreset.AVATAR_GLOW -> {
            drawRect(
                brush = Brush.linearGradient(
                    listOf(
                        TerracottaAccent.copy(alpha = 0.08f),
                        SageGreen.copy(alpha = 0.08f)
                    )
                )
            )
        }
    }
}

private fun DrawScope.drawPixelatedFace(
    rect: RectF,
    blockSize: Float,
    intensity: Float
) {
    val block = blockSize.coerceAtLeast(8f)
    var y = rect.top
    var rowIndex = 0

    val primaryShade = Color(0xFF282522)
    val secondaryShade = Color(0xFF383430)
    val tertiaryShade = Color(0xFF46413C)

    while (y < rect.bottom) {
        var x = rect.left
        var colIndex = 0
        val blockH = (block).coerceAtMost(rect.bottom - y)

        while (x < rect.right) {
            val blockW = (block).coerceAtMost(rect.right - x)
            val fill = when ((rowIndex + colIndex) % 3) {
                0 -> primaryShade.copy(alpha = 0.94f * intensity)
                1 -> secondaryShade.copy(alpha = 0.92f * intensity)
                else -> tertiaryShade.copy(alpha = 0.88f * intensity)
            }

            drawRoundRect(
                color = fill,
                topLeft = Offset(x, y),
                size = Size(blockW, blockH),
                cornerRadius = CornerRadius(2f, 2f)
            )
            x += block
            colIndex++
        }
        y += block
        rowIndex++
    }

    // Clean, subtle boundary around pixelated face
    drawRoundRect(
        color = WarmBorder.copy(alpha = 0.8f),
        topLeft = Offset(rect.left, rect.top),
        size = Size(rect.width(), rect.height()),
        cornerRadius = CornerRadius(8f, 8f),
        style = Stroke(width = 1.5f)
    )
}

private fun DrawScope.drawGaussianBlurredFace(
    rect: RectF,
    intensity: Float
) {
    val cx = rect.centerX()
    val cy = rect.centerY()
    val rx = rect.width() / 2f
    val ry = rect.height() / 2f

    val frostedBrush = Brush.radialGradient(
        colors = listOf(
            CharcoalPrimary.copy(alpha = 0.95f * intensity),
            Color(0xFF383430).copy(alpha = 0.82f * intensity),
            Color(0xFF5A544D).copy(alpha = 0.45f * intensity),
            Color.Transparent
        ),
        center = Offset(cx, cy),
        radius = rx.coerceAtLeast(ry) * 1.15f
    )

    drawOval(
        brush = frostedBrush,
        topLeft = Offset(rect.left, rect.top),
        size = Size(rect.width(), rect.height())
    )

    drawOval(
        color = SageGreen.copy(alpha = 0.7f),
        topLeft = Offset(rect.left, rect.top),
        size = Size(rect.width(), rect.height()),
        style = Stroke(width = 1.5f)
    )
}

private fun DrawScope.drawPrivacyMask(
    rect: RectF,
    maskType: PrivacyMaskType,
    eyeRatio: Float,
    mouthRatio: Float
) {
    val rw = rect.width()
    val rh = rect.height()

    when (maskType) {
        PrivacyMaskType.NONE -> {}

        PrivacyMaskType.EYES_VISOR -> {
            val eyeCenterY = rect.top + rh * eyeRatio
            val barHeight = rh * 0.22f
            val barTop = eyeCenterY - barHeight / 2f
            val barLeft = rect.left - rw * 0.08f
            val barRight = rect.right + rw * 0.08f
            val barWidth = barRight - barLeft

            // Matte charcoal censor bar
            drawRoundRect(
                color = MaskCensorCharcoal,
                topLeft = Offset(barLeft, barTop),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(4f, 4f)
            )

            // Subtle warm terracotta edge lines
            drawLine(
                color = MaskCensorTerracotta,
                start = Offset(barLeft, barTop),
                end = Offset(barRight, barTop),
                strokeWidth = 1.5f
            )
            drawLine(
                color = MaskCensorTerracotta,
                start = Offset(barLeft, barTop + barHeight),
                end = Offset(barRight, barTop + barHeight),
                strokeWidth = 1.5f
            )
        }

        PrivacyMaskType.MOUTH_GUARD -> {
            val mouthCenterY = rect.top + rh * mouthRatio
            val guardHeight = rh * 0.26f
            val guardTop = mouthCenterY - guardHeight * 0.4f
            val guardLeft = rect.left + rw * 0.08f
            val guardWidth = rw * 0.84f

            drawRoundRect(
                color = MaskCensorCharcoal,
                topLeft = Offset(guardLeft, guardTop),
                size = Size(guardWidth, guardHeight),
                cornerRadius = CornerRadius(10f, 10f)
            )
            drawRoundRect(
                color = WarmBorder,
                topLeft = Offset(guardLeft, guardTop),
                size = Size(guardWidth, guardHeight),
                cornerRadius = CornerRadius(10f, 10f),
                style = Stroke(width = 1.5f)
            )
        }

        PrivacyMaskType.FULL_SHIELD -> {
            val path = Path().apply {
                val cx = rect.centerX()
                moveTo(cx, rect.top)
                lineTo(rect.right, rect.top + rh * 0.3f)
                lineTo(rect.right - rw * 0.1f, rect.bottom - rh * 0.1f)
                lineTo(cx, rect.bottom + rh * 0.05f)
                lineTo(rect.left + rw * 0.1f, rect.bottom - rh * 0.1f)
                lineTo(rect.left, rect.top + rh * 0.3f)
                close()
            }

            drawPath(
                path = path,
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF262320).copy(alpha = 0.96f),
                        Color(0xFF383430).copy(alpha = 0.94f)
                    )
                )
            )
            drawPath(
                path = path,
                color = TerracottaAccent.copy(alpha = 0.8f),
                style = Stroke(width = 1.5f)
            )
        }

        PrivacyMaskType.ANONYMOUS_HOOD -> {
            drawOval(
                brush = Brush.radialGradient(
                    listOf(
                        CharcoalPrimary,
                        Color(0xFF262320).copy(alpha = 0.98f)
                    ),
                    center = Offset(rect.centerX(), rect.centerY()),
                    radius = rw * 0.65f
                ),
                topLeft = Offset(rect.left - rw * 0.15f, rect.top - rh * 0.2f),
                size = Size(rw * 1.3f, rh * 1.4f)
            )
        }

        PrivacyMaskType.CYBER_NEON -> {
            // Clean minimal geometric matrix (non-neon)
            val cx = rect.centerX()
            val cy = rect.centerY()
            drawOval(
                color = Color(0xFF221F1C).copy(alpha = 0.90f),
                topLeft = Offset(rect.left, rect.top),
                size = Size(rw, rh)
            )
            for (step in 1..4) {
                val offsetH = rh * (step * 0.2f)
                drawLine(
                    color = TerracottaAccent.copy(alpha = 0.5f),
                    start = Offset(rect.left, rect.top + offsetH),
                    end = Offset(rect.right, rect.top + offsetH),
                    strokeWidth = 1f
                )
            }
            drawCircle(
                color = TerracottaAccent,
                radius = 4f,
                center = Offset(cx, cy)
            )
        }

        PrivacyMaskType.VENETIAN_LINES -> {
            val slotCount = 10
            val slotHeight = rh / slotCount
            for (i in 0 until slotCount) {
                if (i % 2 == 0) {
                    val sTop = rect.top + i * slotHeight
                    drawRect(
                        color = CharcoalPrimary.copy(alpha = 0.96f),
                        topLeft = Offset(rect.left - rw * 0.05f, sTop),
                        size = Size(rw * 1.1f, slotHeight)
                    )
                }
            }
            drawRoundRect(
                color = WarmBorder,
                topLeft = Offset(rect.left, rect.top),
                size = Size(rw, rh),
                cornerRadius = CornerRadius(6f, 6f),
                style = Stroke(width = 1f)
            )
        }

        // ۱. نقاب سنتی برقع بندری (طلایی و مشکی اصیل هرمزگان/جنوب)
        PrivacyMaskType.BANDARI_BURQA -> {
            val eyeCenterY = rect.top + rh * eyeRatio
            val cx = rect.centerX()
            val burqaWidth = rw * 0.95f
            val burqaTop = eyeCenterY - rh * 0.12f
            val burqaBottom = rect.bottom - rh * 0.05f

            // پیشانی‌بند و تیغه عمودی برقع
            val burqaPath = Path().apply {
                moveTo(cx, burqaTop)
                lineTo(cx + burqaWidth * 0.5f, burqaTop + rh * 0.08f)
                lineTo(cx + burqaWidth * 0.45f, eyeCenterY + rh * 0.15f)
                lineTo(cx + burqaWidth * 0.35f, burqaBottom)
                lineTo(cx, burqaBottom - rh * 0.02f)
                lineTo(cx - burqaWidth * 0.35f, burqaBottom)
                lineTo(cx - burqaWidth * 0.45f, eyeCenterY + rh * 0.15f)
                lineTo(cx - burqaWidth * 0.5f, burqaTop + rh * 0.08f)
                close()
            }
            // رنگ طلایی/مشکی زربافت برقع
            drawPath(
                path = burqaPath,
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF2E2416).copy(alpha = 0.96f),
                        Color(0xFF5A4422).copy(alpha = 0.94f),
                        Color(0xFF1E1710).copy(alpha = 0.98f)
                    )
                )
            )
            // نوار طلا و خط برجسته سنتی بندری
            drawPath(
                path = burqaPath,
                color = Color(0xFFD4AF37),
                style = Stroke(width = 2.5f)
            )
            // تیغه چوبی/فلزی روی بینی برقع
            drawLine(
                color = Color(0xFFD4AF37),
                start = Offset(cx, burqaTop),
                end = Offset(cx, burqaBottom),
                strokeWidth = 3f
            )
        }

        // ۲. نقاب نفتی خلیجی (عربی شیک با روبند تیره)
        PrivacyMaskType.KHALIJI_NIQAB -> {
            val eyeCenterY = rect.top + rh * eyeRatio
            val niqabTop = eyeCenterY + rh * 0.08f
            val niqabBottom = rect.bottom + rh * 0.12f
            val nWidth = rw * 1.15f
            val nLeft = rect.left - rw * 0.075f

            // پارچه تیره نفتی مجلل با سایه
            drawRoundRect(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF1C1917).copy(alpha = 0.97f),
                        Color(0xFF292524).copy(alpha = 0.95f),
                        Color(0xFF0C0A09).copy(alpha = 0.98f)
                    )
                ),
                topLeft = Offset(nLeft, niqabTop),
                size = Size(nWidth, niqabBottom - niqabTop),
                cornerRadius = CornerRadius(12f, 12f)
            )
            // حاشیه طلایی خلیجی بالای نقاب
            drawLine(
                brush = Brush.horizontalGradient(
                    listOf(Color(0xFFC5A059), Color(0xFFF3E5AB), Color(0xFFC5A059))
                ),
                start = Offset(nLeft, niqabTop),
                end = Offset(nLeft + nWidth, niqabTop),
                strokeWidth = 2.5f
            )
        }

        // ۳. تاج فانتزی پرنسسی روی سر مدل (حفظ پرستیژ و زیبایی ژورنالی)
        PrivacyMaskType.FANTASY_CROWN -> {
            val cx = rect.centerX()
            val crownWidth = rw * 0.75f
            val crownBaseY = rect.top - rh * 0.02f
            val crownHeight = rh * 0.32f
            val crownPeakY = crownBaseY - crownHeight

            val crownPath = Path().apply {
                moveTo(cx - crownWidth / 2f, crownBaseY)
                lineTo(cx - crownWidth / 2f, crownBaseY - crownHeight * 0.6f)
                lineTo(cx - crownWidth * 0.25f, crownBaseY - crownHeight * 0.3f)
                lineTo(cx, crownPeakY) // قله وسط
                lineTo(cx + crownWidth * 0.25f, crownBaseY - crownHeight * 0.3f)
                lineTo(cx + crownWidth / 2f, crownBaseY - crownHeight * 0.6f)
                lineTo(cx + crownWidth / 2f, crownBaseY)
                close()
            }
            drawPath(
                path = crownPath,
                brush = Brush.verticalGradient(
                    listOf(Color(0xFFFFDF73), Color(0xFFD4AF37), Color(0xFFA67C1E))
                )
            )
            drawPath(
                path = crownPath,
                color = Color(0xFFFFF4D0),
                style = Stroke(width = 2f)
            )
            // نگین‌های الماس فانتزی روی ۵ قله
            drawCircle(Color(0xFF80D8FF), radius = 5f, center = Offset(cx, crownPeakY))
            drawCircle(Color(0xFFFF80AB), radius = 4f, center = Offset(cx - crownWidth / 2f, crownBaseY - crownHeight * 0.6f))
            drawCircle(Color(0xFFFF80AB), radius = 4f, center = Offset(cx + crownWidth / 2f, crownBaseY - crownHeight * 0.6f))
        }

        // ۴. ماسک بالماسکه مجلل و پردار (کارناوال ونیزی و مدلینگ لاکچری)
        PrivacyMaskType.MASQUERADE_BALL -> {
            val eyeCenterY = rect.top + rh * eyeRatio
            val cx = rect.centerX()
            val mWidth = rw * 1.1f
            val mHeight = rh * 0.38f
            val mTop = eyeCenterY - mHeight * 0.5f

            val maskPath = Path().apply {
                moveTo(cx, eyeCenterY - mHeight * 0.15f) // پل بینی
                cubicTo(
                    cx - mWidth * 0.25f, mTop,
                    rect.left - rw * 0.1f, mTop - rh * 0.05f,
                    rect.left - rw * 0.08f, eyeCenterY + mHeight * 0.4f
                )
                cubicTo(
                    cx - mWidth * 0.25f, eyeCenterY + mHeight * 0.5f,
                    cx - mWidth * 0.1f, eyeCenterY + mHeight * 0.2f,
                    cx, eyeCenterY + mHeight * 0.25f
                )
                cubicTo(
                    cx + mWidth * 0.1f, eyeCenterY + mHeight * 0.2f,
                    cx + mWidth * 0.25f, eyeCenterY + mHeight * 0.5f,
                    rect.right + rw * 0.08f, eyeCenterY + mHeight * 0.4f
                )
                cubicTo(
                    rect.right + rw * 0.1f, mTop - rh * 0.05f,
                    cx + mWidth * 0.25f, mTop,
                    cx, eyeCenterY - mHeight * 0.15f
                )
                close()
            }
            drawPath(
                path = maskPath,
                brush = Brush.radialGradient(
                    listOf(Color(0xFF4A154B), Color(0xFF1E1026), Color(0xFF0F0714)),
                    center = Offset(cx, eyeCenterY),
                    radius = mWidth * 0.6f
                )
            )
            drawPath(
                path = maskPath,
                color = Color(0xFFE0B0FF),
                style = Stroke(width = 2f)
            )
        }

        // ۵. نقاب فانتزی قلب روی چشمان مدل (عدم شناسایی چهره با استایل ترند)
        PrivacyMaskType.HEART_EYES -> {
            val eyeCenterY = rect.top + rh * eyeRatio
            val cx = rect.centerX()
            val heartRadius = rw * 0.22f
            val leftEyeX = cx - rw * 0.22f
            val rightEyeX = cx + rw * 0.22f

            // دو قلب صورتی/سرخ روی دو چشم
            listOf(leftEyeX, rightEyeX).forEach { eyeX ->
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(Color(0xFFFF4081), Color(0xFFC2185B)),
                        center = Offset(eyeX, eyeCenterY),
                        radius = heartRadius
                    ),
                    radius = heartRadius,
                    center = Offset(eyeX, eyeCenterY)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.8f),
                    radius = heartRadius,
                    center = Offset(eyeX, eyeCenterY),
                    style = Stroke(width = 2f)
                )
            }
        }

        // ۶. نقاب گربه‌ای فانتزی بالماسکه (Cat Mystery)
        PrivacyMaskType.CAT_MYSTERY -> {
            val eyeCenterY = rect.top + rh * eyeRatio
            val cx = rect.centerX()
            val mWidth = rw * 1.05f
            val mTop = eyeCenterY - rh * 0.2f

            // نقاب با گوش‌های گربه در بالا
            val catPath = Path().apply {
                moveTo(cx, eyeCenterY - rh * 0.05f)
                lineTo(cx - mWidth * 0.25f, mTop)
                lineTo(cx - mWidth * 0.45f, mTop - rh * 0.2f) // گوش چپ گربه
                lineTo(rect.left, eyeCenterY)
                lineTo(cx - mWidth * 0.2f, eyeCenterY + rh * 0.18f)
                lineTo(cx, eyeCenterY + rh * 0.08f)
                lineTo(cx + mWidth * 0.2f, eyeCenterY + rh * 0.18f)
                lineTo(rect.right, eyeCenterY)
                lineTo(cx + mWidth * 0.45f, mTop - rh * 0.2f) // گوش راست گربه
                lineTo(cx + mWidth * 0.25f, mTop)
                close()
            }
            drawPath(
                path = catPath,
                color = Color(0xFF1F1D1B).copy(alpha = 0.96f)
            )
            drawPath(
                path = catPath,
                color = Color(0xFFE2C9A5),
                style = Stroke(width = 2f)
            )
        }
    }
}

// ----------------- Helper: Beauty Retouch & Blemish Eraser -----------------
private fun DrawScope.drawFacialBeautyAndBlemishTreatment(
    rect: RectF,
    blemishRemoval: Float,
    skinSmoothing: Float,
    skinToneBalance: Float
) {
    val cx = rect.centerX()
    val cy = rect.centerY()
    val rx = rect.width() / 2f
    val ry = rect.height() / 2f

    // انتخاب تنالیته رنگ پوست: منفی = روشن و مهتابی | مثبت = برنزه خلیجی
    val baseTone = when {
        skinToneBalance < -0.1f -> {
            // روشن‌کننده مهتابی
            Color(0xFFFFF5EE).copy(alpha = 0.22f * kotlin.math.abs(skinToneBalance))
        }
        skinToneBalance > 0.1f -> {
            // برنزه طلایی گرم خلیجی
            Color(0xFFD29054).copy(alpha = 0.25f * skinToneBalance)
        }
        else -> Color.Transparent
    }

    if (baseTone != Color.Transparent) {
        drawOval(
            color = baseTone,
            topLeft = Offset(rect.left, rect.top),
            size = Size(rect.width(), rect.height())
        )
    }

    // لایه لطافت، فتوشاپ و پاکسازی جای جوش و لک (Soft Focus Diffusion)
    val diffuseAlpha = (blemishRemoval * 0.28f + skinSmoothing * 0.20f).coerceIn(0f, 0.45f)
    if (diffuseAlpha > 0.02f) {
        drawOval(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFDE8D7).copy(alpha = diffuseAlpha),
                    Color(0xFFE8C8B0).copy(alpha = diffuseAlpha * 0.6f),
                    Color.Transparent
                ),
                center = Offset(cx, cy),
                radius = rx.coerceAtLeast(ry)
            ),
            topLeft = Offset(rect.left, rect.top),
            size = Size(rect.width(), rect.height())
        )
    }
}

// ----------------- Helper: Digital Makeup (Lip Tint & Blush) -----------------
private fun DrawScope.drawDigitalMakeup(
    rect: RectF,
    eyeRatio: Float,
    mouthRatio: Float,
    lipTint: Float,
    blush: Float
) {
    val rw = rect.width()
    val rh = rect.height()
    val cx = rect.centerX()

    // ۱. رژ گونه ملایم و طبیعی روی دو طرف گونه
    if (blush > 0.05f) {
        val cheekY = rect.top + rh * (eyeRatio + 0.12f)
        val leftCheekX = cx - rw * 0.28f
        val rightCheekX = cx + rw * 0.28f
        val blushRadius = rw * 0.18f

        listOf(leftCheekX, rightCheekX).forEach { cheekX ->
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFFE57373).copy(alpha = 0.22f * blush),
                        Color(0xFFFFCDD2).copy(alpha = 0.10f * blush),
                        Color.Transparent
                    ),
                    center = Offset(cheekX, cheekY),
                    radius = blushRadius
                ),
                radius = blushRadius,
                center = Offset(cheekX, cheekY)
            )
        }
    }

    // ۲. رژ لب براق و ژورنالی روی لب‌ها
    if (lipTint > 0.05f) {
        val mouthY = rect.top + rh * mouthRatio
        val lipWidth = rw * 0.32f
        val lipHeight = rh * 0.10f

        drawOval(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFC2185B).copy(alpha = 0.40f * lipTint),
                    Color(0xFFE91E63).copy(alpha = 0.22f * lipTint),
                    Color.Transparent
                ),
                center = Offset(cx, mouthY),
                radius = lipWidth / 2f
            ),
            topLeft = Offset(cx - lipWidth / 2f, mouthY - lipHeight / 2f),
            size = Size(lipWidth, lipHeight)
        )
    }
}
