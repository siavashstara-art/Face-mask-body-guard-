package com.example.ui.overlay

import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
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
import com.example.model.BackgroundConfig
import com.example.model.BackgroundMode
import com.example.model.BlurType
import com.example.model.BundledEnvironments
import com.example.model.FaceStyleConfig
import com.example.model.FilterPreset
import com.example.model.PrivacyConfig
import com.example.model.PrivacyMaskType
import com.example.model.TrackedFace
import com.example.ui.theme.CharcoalPrimary
import com.example.ui.theme.CharcoalSecondary
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
    onTapAddManualZone: (Offset) -> Unit = {}
) {
    // Load virtual background image if selected
    val activeEnv = remember(backgroundConfig.selectedEnvId) {
        BundledEnvironments.items.find { it.id == backgroundConfig.selectedEnvId }
            ?: BundledEnvironments.items.first()
    }
    val virtualBgBitmap = ImageBitmap.imageResource(activeEnv.drawableResId)

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
    }
}
