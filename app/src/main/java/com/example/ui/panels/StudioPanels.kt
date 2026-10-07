package com.example.ui.panels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun PrivacyStudioPanel(
    privacyConfig: PrivacyConfig,
    isPersian: Boolean,
    onUpdate: (PrivacyConfig) -> Unit,
    onClearManualZones: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(WarmSurface)
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isPersian) "حفاظت و پوشش چهره" else "Face Privacy & Obfuscation",
                style = MaterialTheme.typography.titleMedium,
                color = CharcoalPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Surface(
                color = SageGreenSubtle,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = if (isPersian) "پردازش محلی" else "On-Device",
                    style = MaterialTheme.typography.labelSmall,
                    color = SageGreen,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        // Blur Type Selection
        Text(
            text = if (isPersian) "روش پوشش چهره:" else "Obfuscation Method:",
            style = MaterialTheme.typography.bodyMedium,
            color = CharcoalSecondary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BlurType.values().forEach { type ->
                val selected = privacyConfig.blurType == type
                val label = when (type) {
                    BlurType.NONE -> if (isPersian) "غیرفعال" else "Off"
                    BlurType.PIXELATE -> if (isPersian) "پیکسل (موزاییک)" else "Pixelate"
                    BlurType.GAUSSIAN -> if (isPersian) "مات (بلر)" else "Gaussian"
                }
                FilterChip(
                    selected = selected,
                    onClick = { onUpdate(privacyConfig.copy(blurType = type)) },
                    label = { Text(label, fontSize = 12.sp, fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = TerracottaAccent,
                        selectedLabelColor = Color.White,
                        containerColor = WarmSurfaceSecondary,
                        labelColor = CharcoalPrimary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = if (selected) TerracottaAccent else WarmBorder,
                        enabled = true,
                        selected = selected
                    ),
                    modifier = Modifier.height(40.dp)
                )
            }
        }

        // Pixel Size Slider
        if (privacyConfig.blurType == BlurType.PIXELATE) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        if (isPersian) "اندازه بلوک‌های پیکسل:" else "Pixel Block Size:",
                        style = MaterialTheme.typography.bodySmall,
                        color = CharcoalSecondary
                    )
                    Text(
                        "${privacyConfig.pixelBlockSize} px",
                        style = MaterialTheme.typography.bodySmall,
                        color = TerracottaAccent,
                        fontWeight = FontWeight.Medium
                    )
                }
                Slider(
                    value = privacyConfig.pixelBlockSize.toFloat(),
                    onValueChange = { onUpdate(privacyConfig.copy(pixelBlockSize = it.toInt())) },
                    valueRange = 12f..56f,
                    colors = SliderDefaults.colors(
                        thumbColor = TerracottaAccent,
                        activeTrackColor = TerracottaAccent,
                        inactiveTrackColor = WarmBorder
                    )
                )
            }
        }

        // Blur Intensity Slider
        if (privacyConfig.blurType != BlurType.NONE) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        if (isPersian) "تراکم و شدت پوشش:" else "Privacy Density:",
                        style = MaterialTheme.typography.bodySmall,
                        color = CharcoalSecondary
                    )
                    Text(
                        "${(privacyConfig.blurIntensity * 100).toInt()}%",
                        style = MaterialTheme.typography.bodySmall,
                        color = TerracottaAccent,
                        fontWeight = FontWeight.Medium
                    )
                }
                Slider(
                    value = privacyConfig.blurIntensity,
                    onValueChange = { onUpdate(privacyConfig.copy(blurIntensity = it)) },
                    valueRange = 0.2f..1.0f,
                    colors = SliderDefaults.colors(
                        thumbColor = TerracottaAccent,
                        activeTrackColor = TerracottaAccent,
                        inactiveTrackColor = WarmBorder
                    )
                )
            }
        }

        // Privacy Masks
        HorizontalDivider(color = WarmBorderSubtle)
        Text(
            text = if (isPersian) "ماسک‌های حفاظتی چهره:" else "Security Privacy Masks:",
            style = MaterialTheme.typography.bodyMedium,
            color = CharcoalSecondary
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(PrivacyMaskType.values()) { mask ->
                val selected = privacyConfig.maskType == mask
                val name = when (mask) {
                    PrivacyMaskType.NONE -> if (isPersian) "بدون ماسک" else "None"
                    PrivacyMaskType.EYES_VISOR -> if (isPersian) "نوار چشم (سنسور)" else "Eyes Visor"
                    PrivacyMaskType.BANDARI_BURQA -> if (isPersian) "نقاب برقع بندری (سنتی)" else "Bandari Burqa"
                    PrivacyMaskType.KHALIJI_NIQAB -> if (isPersian) "نقاب نفتی خلیجی (عربی)" else "Khaliji Niqab"
                    PrivacyMaskType.FANTASY_CROWN -> if (isPersian) "تاج فانتزی پرنسسی" else "Princess Crown"
                    PrivacyMaskType.MASQUERADE_BALL -> if (isPersian) "ماسک بالماسکه مجلل" else "Masquerade"
                    PrivacyMaskType.HEART_EYES -> if (isPersian) "نقاب قلب روی چشم‌ها" else "Heart Eyes"
                    PrivacyMaskType.CAT_MYSTERY -> if (isPersian) "نقاب گربه‌ای رازآلود" else "Cat Mystery"
                    PrivacyMaskType.FULL_SHIELD -> if (isPersian) "سپر کامل صورت" else "Full Shield"
                    PrivacyMaskType.MOUTH_GUARD -> if (isPersian) "ماسک دهان" else "Mouth Guard"
                    PrivacyMaskType.ANONYMOUS_HOOD -> if (isPersian) "سیلوئت ناشناس" else "Silhouette"
                    PrivacyMaskType.CYBER_NEON -> if (isPersian) "ماتریکس خطی" else "Line Grid"
                    PrivacyMaskType.VENETIAN_LINES -> if (isPersian) "کرکره امنیتی" else "Venetian"
                }
                Card(
                    modifier = Modifier
                        .clickable { onUpdate(privacyConfig.copy(maskType = mask)) }
                        .border(
                            width = 1.dp,
                            color = if (selected) TerracottaAccent else WarmBorder,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selected) TerracottaSubtle else WarmSurface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = name,
                            color = if (selected) TerracottaHover else CharcoalPrimary,
                            fontSize = 12.sp,
                            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
                        )
                    }
                }
            }
        }

        // Safety Margin Multiplier
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isPersian) "حاشیه محافظ حرکتی (جلوگیری از نشتی):" else "Motion Safety Margin:",
                    style = MaterialTheme.typography.bodySmall,
                    color = CharcoalSecondary
                )
                Text(
                    text = "${String.format("%.2f", privacyConfig.safetyMarginMultiplier)}x",
                    style = MaterialTheme.typography.bodySmall,
                    color = SageGreen,
                    fontWeight = FontWeight.Medium
                )
            }
            Slider(
                value = privacyConfig.safetyMarginMultiplier,
                onValueChange = { onUpdate(privacyConfig.copy(safetyMarginMultiplier = it)) },
                valueRange = 1.0f..2.0f,
                colors = SliderDefaults.colors(
                    thumbColor = SageGreen,
                    activeTrackColor = SageGreen,
                    inactiveTrackColor = WarmBorder
                )
            )
            Text(
                text = if (isPersian)
                    "در صورت چرخش یا حرکت سریع، حاشیه ماسک گسترده‌تر می‌شود تا چهره هرگز آشکار نگردد."
                else
                    "Expands the protective mask boundary dynamically during rapid head movement to preserve anonymity.",
                style = MaterialTheme.typography.labelSmall,
                color = CharcoalTertiary
            )
        }

        // Multi-face tracking toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isPersian) "پوشش هم‌زمان تمامی چهره‌ها" else "Multi-Face Protection",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CharcoalPrimary
                )
                Text(
                    text = if (isPersian) "اعمال پوشش بر روی تمامی چهره‌های موجود در تصویر" else "Apply protection to every detected face in frame",
                    style = MaterialTheme.typography.labelSmall,
                    color = CharcoalSecondary
                )
            }
            Switch(
                checked = privacyConfig.multiFaceEnabled,
                onCheckedChange = { onUpdate(privacyConfig.copy(multiFaceEnabled = it)) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = TerracottaAccent,
                    uncheckedThumbColor = CharcoalSecondary,
                    uncheckedTrackColor = WarmSurfaceSecondary
                )
            )
        }

        // Manual Zones count and clear
        if (privacyConfig.manualZones.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isPersian) "نواحی دستی: ${privacyConfig.manualZones.size}" else "Manual Zones: ${privacyConfig.manualZones.size}",
                    color = MutedAmber,
                    style = MaterialTheme.typography.bodySmall
                )
                TextButton(onClick = onClearManualZones) {
                    Text(if (isPersian) "پاکسازی نواحی" else "Clear Zones", color = BrickRed)
                }
            }
        }
    }
}

@Composable
fun BackgroundStudioPanel(
    bgConfig: BackgroundConfig,
    isPersian: Boolean,
    onUpdate: (BackgroundConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(WarmSurface)
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isPersian) "استودیو پس‌زمینه و پرده سبز" else "Virtual Background & Chroma Key",
                style = MaterialTheme.typography.titleMedium,
                color = CharcoalPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Surface(
                color = SageGreenSubtle,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = if (isPersian) "آفلاین" else "100% Offline",
                    style = MaterialTheme.typography.labelSmall,
                    color = SageGreen,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        // Background Mode Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BackgroundMode.values().forEach { mode ->
                val selected = bgConfig.mode == mode
                val label = when (mode) {
                    BackgroundMode.ORIGINAL -> if (isPersian) "اصلی" else "Original"
                    BackgroundMode.BLUR -> if (isPersian) "بلر پس‌زمینه" else "Blur BG"
                    BackgroundMode.SOLID_COLOR -> if (isPersian) "تک‌رنگ استودیو" else "Solid Color"
                    BackgroundMode.CHROMA_KEY -> if (isPersian) "پرده سبز" else "Chroma Key"
                    BackgroundMode.VIRTUAL_IMAGE -> if (isPersian) "محیط مجازی" else "Virtual Sets"
                }
                FilterChip(
                    selected = selected,
                    onClick = { onUpdate(bgConfig.copy(mode = mode)) },
                    label = { Text(label, fontSize = 11.sp, fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = TerracottaAccent,
                        selectedLabelColor = Color.White,
                        containerColor = WarmSurfaceSecondary,
                        labelColor = CharcoalPrimary
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = if (selected) TerracottaAccent else WarmBorder,
                        enabled = true,
                        selected = selected
                    )
                )
            }
        }

        // Virtual Environments Gallery
        if (bgConfig.mode == BackgroundMode.VIRTUAL_IMAGE || bgConfig.mode == BackgroundMode.CHROMA_KEY) {
            Text(
                text = if (isPersian) "محیط‌های همراه برنامه:" else "Bundled Virtual Environments:",
                style = MaterialTheme.typography.bodyMedium,
                color = CharcoalSecondary
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(BundledEnvironments.items) { env ->
                    val isSelected = bgConfig.selectedEnvId == env.id
                    Card(
                        modifier = Modifier
                            .width(135.dp)
                            .clickable { onUpdate(bgConfig.copy(selectedEnvId = env.id)) }
                            .border(
                                width = 1.dp,
                                color = if (isSelected) TerracottaAccent else WarmBorder,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary)
                    ) {
                        Column {
                            Image(
                                painter = painterResource(id = env.drawableResId),
                                contentDescription = env.titleEn,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(72.dp),
                                contentScale = ContentScale.Crop
                            )
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = if (isPersian) env.titleFa else env.titleEn,
                                    color = if (isSelected) TerracottaHover else CharcoalPrimary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1
                                )
                                Text(
                                    text = if (isPersian) env.descriptionFa else env.descriptionEn,
                                    color = CharcoalTertiary,
                                    fontSize = 9.sp,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }
        }

        // Chroma Key Fine-tuning Sliders
        if (bgConfig.mode == BackgroundMode.CHROMA_KEY) {
            HorizontalDivider(color = WarmBorderSubtle)
            Text(
                text = if (isPersian) "تنظیمات پرده سبز (کروماکی):" else "Chroma Key Calibration:",
                style = MaterialTheme.typography.bodyMedium,
                color = SageGreen,
                fontWeight = FontWeight.Medium
            )

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(if (isPersian) "میزان حساسیت رنگ سبز:" else "Color Tolerance:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                    Text("${(bgConfig.chromaTolerance * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = SageGreen, fontWeight = FontWeight.Medium)
                }
                Slider(
                    value = bgConfig.chromaTolerance,
                    onValueChange = { onUpdate(bgConfig.copy(chromaTolerance = it)) },
                    valueRange = 0.1f..0.8f,
                    colors = SliderDefaults.colors(thumbColor = SageGreen, activeTrackColor = SageGreen, inactiveTrackColor = WarmBorder)
                )
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(if (isPersian) "نرمی حاشیه‌ها (حفظ مو و بدن):" else "Edge Softness:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                    Text("${(bgConfig.chromaSoftness * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = SageGreen, fontWeight = FontWeight.Medium)
                }
                Slider(
                    value = bgConfig.chromaSoftness,
                    onValueChange = { onUpdate(bgConfig.copy(chromaSoftness = it)) },
                    valueRange = 0.05f..0.4f,
                    colors = SliderDefaults.colors(thumbColor = SageGreen, activeTrackColor = SageGreen, inactiveTrackColor = WarmBorder)
                )
            }
        }
    }
}

@Composable
fun FaceStyleStudioPanel(
    styleConfig: FaceStyleConfig,
    isPersian: Boolean,
    onUpdate: (FaceStyleConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(WarmSurface)
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isPersian) "استایل چهره و رنگ‌بندی" else "Face Style & Color Grading",
                style = MaterialTheme.typography.titleMedium,
                color = CharcoalPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Surface(
                color = WarmSurfaceSecondary,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = if (isPersian) "قابل بازگشت" else "Non-destructive",
                    style = MaterialTheme.typography.labelSmall,
                    color = CharcoalSecondary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        // Filter Presets
        Text(
            text = if (isPersian) "تم رنگی و استایل انتخابی:" else "Artistic Look / Filter:",
            style = MaterialTheme.typography.bodyMedium,
            color = CharcoalSecondary
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(FilterPreset.values()) { preset ->
                val selected = styleConfig.preset == preset
                Card(
                    modifier = Modifier
                        .clickable { onUpdate(styleConfig.copy(preset = preset)) }
                        .border(
                            width = 1.dp,
                            color = if (selected) TerracottaAccent else WarmBorder,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selected) TerracottaSubtle else WarmSurface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isPersian) preset.titleFa else preset.titleEn,
                            color = if (selected) TerracottaHover else CharcoalPrimary,
                            fontSize = 12.sp,
                            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
                        )
                    }
                }
            }
        }

        // Blemish, Freckles, Spots, Acne & Scar Removal Slider (فتوشاپ و روتوش جای لک و جوش)
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    if (isPersian) "پاکسازی لک، جوش، کک‌ومک و پیسی:" else "Blemish, Spots & Acne Removal:",
                    style = MaterialTheme.typography.bodySmall,
                    color = CharcoalSecondary
                )
                Text(
                    "${(styleConfig.blemishRemoval * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = TerracottaAccent,
                    fontWeight = FontWeight.Medium
                )
            }
            Slider(
                value = styleConfig.blemishRemoval,
                onValueChange = { onUpdate(styleConfig.copy(blemishRemoval = it)) },
                valueRange = 0f..1.0f,
                colors = SliderDefaults.colors(
                    thumbColor = TerracottaAccent,
                    activeTrackColor = TerracottaAccent,
                    inactiveTrackColor = WarmBorder
                )
            )
        }

        // Skin Tone Brightening / Bronzing Slider (روشن‌کننده و برنزه‌کردن پوست)
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val toneLabel = when {
                    styleConfig.skinToneBalance < -0.15f -> if (isPersian) "روشن‌کننده و مهتابی" else "Bright Porcelain"
                    styleConfig.skinToneBalance > 0.15f -> if (isPersian) "برنزه خلیجی و گرم" else "Sunkissed Bronze"
                    else -> if (isPersian) "طبیعی" else "Natural"
                }
                Text(
                    if (isPersian) "تنظیم رنگ پوست (روشن/برنزه): $toneLabel" else "Skin Tone (Bright/Bronze): $toneLabel",
                    style = MaterialTheme.typography.bodySmall,
                    color = CharcoalSecondary
                )
                Text(
                    String.format("%.1f", styleConfig.skinToneBalance),
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedAmber,
                    fontWeight = FontWeight.Medium
                )
            }
            Slider(
                value = styleConfig.skinToneBalance,
                onValueChange = { onUpdate(styleConfig.copy(skinToneBalance = it)) },
                valueRange = -1.0f..1.0f,
                colors = SliderDefaults.colors(
                    thumbColor = MutedAmber,
                    activeTrackColor = MutedAmber,
                    inactiveTrackColor = WarmBorder
                )
            )
        }

        // Skin Smoothing Slider
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(if (isPersian) "لطافت و صاف‌سازی بافت پوست:" else "Skin Texture Smoothing:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                Text("${(styleConfig.skinSmoothing * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = SageGreen, fontWeight = FontWeight.Medium)
            }
            Slider(
                value = styleConfig.skinSmoothing,
                onValueChange = { onUpdate(styleConfig.copy(skinSmoothing = it)) },
                valueRange = 0f..1.0f,
                colors = SliderDefaults.colors(thumbColor = SageGreen, activeTrackColor = SageGreen, inactiveTrackColor = WarmBorder)
            )
        }

        // Facial Slimming / Contouring Slider (لاغرسازی و زاویه‌سازی فک و صورت)
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val slimLabel = when {
                    styleConfig.facialSlimming < -0.1f -> if (isPersian) "لاغرتر و زاویه‌دار" else "Slimmer V-Line"
                    styleConfig.facialSlimming > 0.1f -> if (isPersian) "گونه پرتر" else "Fuller Cheeks"
                    else -> if (isPersian) "طبیعی" else "Natural"
                }
                Text(if (isPersian) "زاویه‌سازی و فرم فک و صورت: $slimLabel" else "Facial Slimming & V-Line: $slimLabel", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                Text("${(styleConfig.facialSlimming * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = TerracottaAccent, fontWeight = FontWeight.Medium)
            }
            Slider(
                value = styleConfig.facialSlimming,
                onValueChange = { onUpdate(styleConfig.copy(facialSlimming = it)) },
                valueRange = -0.5f..0.5f,
                colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
            )
        }

        // Makeup / Lip Tint & Blush Sliders (آرایش لب و گونه دیجیتال)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(if (isPersian) "رژ لب:" else "Lip Tint:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                    Text("${(styleConfig.lipTintIntensity * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = TerracottaAccent)
                }
                Slider(
                    value = styleConfig.lipTintIntensity,
                    onValueChange = { onUpdate(styleConfig.copy(lipTintIntensity = it)) },
                    valueRange = 0f..1.0f,
                    colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(if (isPersian) "رژ گونه:" else "Blush:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                    Text("${(styleConfig.blushIntensity * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = MutedAmber)
                }
                Slider(
                    value = styleConfig.blushIntensity,
                    onValueChange = { onUpdate(styleConfig.copy(blushIntensity = it)) },
                    valueRange = 0f..1.0f,
                    colors = SliderDefaults.colors(thumbColor = MutedAmber, activeTrackColor = MutedAmber, inactiveTrackColor = WarmBorder)
                )
            }
        }

        // Warmth / Coolness Slider
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(if (isPersian) "دمای رنگ نوری (Warmth):" else "Color Warmth:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                Text(String.format("%.1f", styleConfig.warmth), style = MaterialTheme.typography.bodySmall, color = CharcoalPrimary, fontWeight = FontWeight.Medium)
            }
            Slider(
                value = styleConfig.warmth,
                onValueChange = { onUpdate(styleConfig.copy(warmth = it)) },
                valueRange = -1.0f..1.0f,
                colors = SliderDefaults.colors(thumbColor = CharcoalPrimary, activeTrackColor = CharcoalPrimary, inactiveTrackColor = WarmBorder)
            )
        }

        // Watermark Freedom: With Watermark or Without Watermark Toggle (با یا بدون واتر مارک)
        Surface(
            color = WarmSurfaceSecondary,
            shape = RoundedCornerShape(10.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isPersian) "انتخاب حالت با واتر مارک / بدون واتر مارک" else "Watermark Mode (With / Without)",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CharcoalPrimary,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (styleConfig.showAlterationBadge) {
                            if (isPersian) "حالت فعال: برچسب اصالت و امضای دیجیتالی نمایش داده می‌شود" else "Active: Verification badge visible"
                        } else {
                            if (isPersian) "حالت آزاد (بدون واتر مارک): خروجی ۱۰۰٪ تمیز، بدون هیچ لوگو یا برچسب" else "Clean Mode: 100% No watermark"
                        },
                        style = MaterialTheme.typography.labelSmall,
                        color = if (styleConfig.showAlterationBadge) TerracottaAccent else SageGreen
                    )
                }
                Switch(
                    checked = styleConfig.showAlterationBadge,
                    onCheckedChange = { onUpdate(styleConfig.copy(showAlterationBadge = it)) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = TerracottaAccent,
                        uncheckedThumbColor = CharcoalSecondary,
                        uncheckedTrackColor = WarmBorder
                    )
                )
            }
        }
    }
}

@Composable
fun BodySilhouetteStudioPanel(
    silhouetteConfig: BodySilhouetteConfig,
    isPersian: Boolean,
    onUpdate: (BodySilhouetteConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(WarmSurface)
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isPersian) "استودیو فرم اندام (سیلوئت)" else "Body Silhouette Studio",
                style = MaterialTheme.typography.titleMedium,
                color = CharcoalPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Surface(
                color = MutedAmberSubtle,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = if (isPersian) "حالت تجربی" else "Experimental",
                    color = MutedAmber,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        // Redmi Note 8 Engineering Limitation Notice
        Card(
            colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(Icons.Default.Info, contentDescription = null, tint = MutedAmber, modifier = Modifier.size(20.dp))
                Text(
                    text = if (isPersian) silhouetteConfig.limitationNoteFa else silhouetteConfig.limitationNoteEn,
                    style = MaterialTheme.typography.bodySmall,
                    color = CharcoalSecondary
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isPersian) "فعال‌سازی افکت فرم اندام" else "Enable Silhouette Effect",
                style = MaterialTheme.typography.bodyMedium,
                color = CharcoalPrimary
            )
            Switch(
                checked = silhouetteConfig.enabled,
                onCheckedChange = { onUpdate(silhouetteConfig.copy(enabled = it)) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = TerracottaAccent,
                    uncheckedThumbColor = CharcoalSecondary,
                    uncheckedTrackColor = WarmSurfaceSecondary
                )
            )
        }

        if (silhouetteConfig.enabled) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val waistLabel = when {
                        silhouetteConfig.waistContour < -0.1f -> if (isPersian) "لاغرسازی و باریک‌کردن کمر" else "Slender Waist"
                        silhouetteConfig.waistContour > 0.1f -> if (isPersian) "پهن‌تر کردن دور کمر" else "Wider Waist"
                        else -> if (isPersian) "طبیعی" else "Natural"
                    }
                    Text(if (isPersian) "تنظیم کانتور کمر (لاغری/چاقی): $waistLabel" else "Waist Contour: $waistLabel", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                    Text("${(silhouetteConfig.waistContour * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = TerracottaAccent, fontWeight = FontWeight.Medium)
                }
                Slider(
                    value = silhouetteConfig.waistContour,
                    onValueChange = { onUpdate(silhouetteConfig.copy(waistContour = it)) },
                    valueRange = -0.5f..0.5f,
                    colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
                )
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(if (isPersian) "برجسته‌سازی اندام و باسن (ویژه مانکن لاغر):" else "Hip & Curves Enhancement:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                    Text("${(silhouetteConfig.hipEnhancement * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = TerracottaAccent, fontWeight = FontWeight.Medium)
                }
                Slider(
                    value = silhouetteConfig.hipEnhancement,
                    onValueChange = { onUpdate(silhouetteConfig.copy(hipEnhancement = it)) },
                    valueRange = 0f..0.8f,
                    colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
                )
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(if (isPersian) "فرم‌دهی بالاتنه و سینه:" else "Chest & Upper Body Contour:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                    Text("${(silhouetteConfig.chestEnhancement * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = MutedAmber, fontWeight = FontWeight.Medium)
                }
                Slider(
                    value = silhouetteConfig.chestEnhancement,
                    onValueChange = { onUpdate(silhouetteConfig.copy(chestEnhancement = it)) },
                    valueRange = 0f..0.8f,
                    colors = SliderDefaults.colors(thumbColor = MutedAmber, activeTrackColor = MutedAmber, inactiveTrackColor = WarmBorder)
                )
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(if (isPersian) "تنظیم کانتور و عرض شانه‌ها:" else "Shoulder Contour:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                    Text("${(silhouetteConfig.shoulderContour * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = SageGreen, fontWeight = FontWeight.Medium)
                }
                Slider(
                    value = silhouetteConfig.shoulderContour,
                    onValueChange = { onUpdate(silhouetteConfig.copy(shoulderContour = it)) },
                    valueRange = -0.5f..0.5f,
                    colors = SliderDefaults.colors(thumbColor = SageGreen, activeTrackColor = SageGreen, inactiveTrackColor = WarmBorder)
                )
            }

            OutlinedButton(
                onClick = { onUpdate(silhouetteConfig.copy(waistContour = 0f, hipEnhancement = 0f, chestEnhancement = 0f, shoulderContour = 0f, overallScale = 0f)) },
                border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isPersian) "بازنشانی پارامترهای اندام" else "Reset Body Parameters", color = CharcoalPrimary)
            }
        }
    }
}

@Composable
fun VoiceStudioPanel(
    voiceConfig: VoiceConfig,
    isPersian: Boolean,
    onUpdate: (VoiceConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(WarmSurface)
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isPersian) "استودیو صدای طبیعی و خصوصی" else "Natural & Private Voice Studio",
                style = MaterialTheme.typography.titleMedium,
                color = CharcoalPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Surface(
                color = if (voiceConfig.isMuted) BrickRedSubtle else SageGreenSubtle,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = if (voiceConfig.isMuted) (if (isPersian) "بی‌صدا (Muted)" else "Muted") else (if (isPersian) "صدا ۱۰۰٪ محلی" else "Local Audio"),
                    color = if (voiceConfig.isMuted) BrickRed else SageGreen,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        // 1. MUTE VIDEO TOGGLE (میوت کردن کامل صدای فیلم)
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (voiceConfig.isMuted) BrickRedSubtle else WarmSurfaceSecondary
            ),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (voiceConfig.isMuted) BrickRed else WarmBorder
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = if (voiceConfig.isMuted) Icons.Default.MicOff else Icons.Default.Mic,
                        contentDescription = null,
                        tint = if (voiceConfig.isMuted) BrickRed else TerracottaAccent,
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = if (isPersian) "میوت کردن صدای فیلم (ضبط بی‌صدا)" else "Mute Video Recording",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = CharcoalPrimary
                        )
                        Text(
                            text = if (isPersian)
                                "فیلمبرداری کاملاً بدون صدا انجام می‌شود و هیچ فایل صوتی ذخیره نمی‌گردد."
                            else
                                "Record video silently without any audio track saved.",
                            style = MaterialTheme.typography.bodySmall,
                            color = CharcoalSecondary
                        )
                    }
                }
                Switch(
                    checked = voiceConfig.isMuted,
                    onCheckedChange = { onUpdate(voiceConfig.copy(isMuted = it)) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = BrickRed,
                        uncheckedThumbColor = CharcoalSecondary,
                        uncheckedTrackColor = WarmSurfaceSecondary
                    )
                )
            }
        }

        if (!voiceConfig.isMuted) {
            // 2. Natural Acoustic Smoothing
            Card(
                colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isPersian) "طبیعی‌سازی ارگانیک صدا (بدون حالت بم یا رباتیک)" else "Organic Natural Voice Acoustic",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = CharcoalPrimary
                        )
                        Text(
                            text = if (isPersian)
                                "حفظ فرکانس‌های زنده تار صوتی برای داشتن تغییری کاملاً نامحسوس و طبیعی"
                            else
                                "Preserves natural human formant and throat resonances without robotic artifacts.",
                            style = MaterialTheme.typography.bodySmall,
                            color = CharcoalSecondary
                        )
                    }
                    Switch(
                        checked = voiceConfig.naturalAcousticSmoothing,
                        onCheckedChange = { onUpdate(voiceConfig.copy(naturalAcousticSmoothing = it)) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = TerracottaAccent,
                            uncheckedThumbColor = CharcoalSecondary,
                            uncheckedTrackColor = WarmSurfaceSecondary
                        )
                    )
                }
            }

            // 3. Voice Profile Selection
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = if (isPersian) "تن و جنس صدای انتخابی (تغییر صدای طبیعی):" else "Voice Character Profile:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = CharcoalSecondary
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(PitchProfile.values()) { profile ->
                        val selected = voiceConfig.pitchProfile == profile
                        Card(
                            modifier = Modifier
                                .clickable { onUpdate(voiceConfig.copy(pitchProfile = profile)) }
                                .border(
                                    width = 1.dp,
                                    color = if (selected) TerracottaAccent else WarmBorder,
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selected) TerracottaSubtle else WarmSurfaceSecondary
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    if (profile.isNatural) {
                                        Surface(
                                            color = SageGreenSubtle,
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = if (isPersian) "طبیعی" else "Natural",
                                                color = SageGreen,
                                                fontSize = 9.sp,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                            )
                                        }
                                    }
                                    Text(
                                        text = if (isPersian) profile.titleFa else profile.titleEn,
                                        color = if (selected) TerracottaHover else CharcoalPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                                    )
                                }
                                Text(
                                    text = if (isPersian) profile.descriptionFa else "Frequency factor: ${profile.pitchFactor}x",
                                    color = CharcoalSecondary,
                                    fontSize = 10.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 4. Mic Gain Slider
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(if (isPersian) "تنظیم بلندی میکروفون (Gain):" else "Microphone Gain:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                    Text("${String.format("%.1f", voiceConfig.micGain)}x", style = MaterialTheme.typography.bodySmall, color = TerracottaAccent, fontWeight = FontWeight.Medium)
                }
                Slider(
                    value = voiceConfig.micGain,
                    onValueChange = { onUpdate(voiceConfig.copy(micGain = it)) },
                    valueRange = 0.5f..2.0f,
                    colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
                )
            }

            // 5. Noise Reduction Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isPersian) "حذف نویز محیطی بدون تغییر صدا" else "Offline Noise Suppression",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CharcoalPrimary
                    )
                    Text(
                        text = if (isPersian) "پالایش صدای پس‌زمینه و صدای باد" else "Clean audio capture on device",
                        style = MaterialTheme.typography.labelSmall,
                        color = CharcoalSecondary
                    )
                }
                Switch(
                    checked = voiceConfig.noiseReduction,
                    onCheckedChange = { onUpdate(voiceConfig.copy(noiseReduction = it)) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = TerracottaAccent,
                        uncheckedThumbColor = CharcoalSecondary,
                        uncheckedTrackColor = WarmSurfaceSecondary
                    )
                )
            }
        }
    }
}

@Composable
fun SettingsPerformancePanel(
    preset: PerformancePreset,
    isPersian: Boolean,
    onPresetChange: (PerformancePreset) -> Unit,
    onShowPrivacyAudit: () -> Unit,
    modifier: Modifier = Modifier,
    watermarkConfig: WatermarkConfig = WatermarkConfig(),
    onWatermarkUpdate: (WatermarkConfig) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(WarmSurface)
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (isPersian) "پروفایل عملکرد و سخت‌افزار" else "Performance & Hardware Profile",
            style = MaterialTheme.typography.titleMedium,
            color = CharcoalPrimary,
            fontWeight = FontWeight.SemiBold
        )

        // Presets List
        PerformancePreset.values().forEach { item ->
            val isSelected = preset == item
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPresetChange(item) }
                    .border(
                        width = 1.dp,
                        color = if (isSelected) TerracottaAccent else WarmBorder,
                        shape = RoundedCornerShape(10.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) TerracottaSubtle else WarmSurface
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isPersian) item.labelFa else item.labelEn,
                                color = if (isSelected) TerracottaHover else CharcoalPrimary,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                            if (item.recommendedForRedmi) {
                                Spacer(Modifier.width(6.dp))
                                Surface(
                                    color = SageGreenSubtle,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = if (isPersian) "توصیه ردمی نوت ۸" else "Redmi Optimal",
                                        color = SageGreen,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = "${item.resolutionWidth}x${item.resolutionHeight} @ ${item.fps} FPS",
                            color = CharcoalSecondary,
                            fontSize = 12.sp
                        )
                    }
                    RadioButton(
                        selected = isSelected,
                        onClick = { onPresetChange(item) },
                        colors = RadioButtonDefaults.colors(selectedColor = TerracottaAccent)
                    )
                }
            }
        }

        HorizontalDivider(color = WarmBorderSubtle)

        // Anti-Theft Brand Watermark Section
        Card(
            colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
            shape = RoundedCornerShape(10.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isPersian) "واترمارک و ضد سرقت عکس" else "Anti-Theft Watermark",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CharcoalPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (isPersian) "درج نام کانال برای جلوگیری از کپی توسط رقبا" else "Stamps your channel to protect shop images",
                            style = MaterialTheme.typography.labelSmall,
                            color = CharcoalSecondary
                        )
                    }
                    Switch(
                        checked = watermarkConfig.enabled,
                        onCheckedChange = { onWatermarkUpdate(watermarkConfig.copy(enabled = it)) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = TerracottaAccent
                        )
                    )
                }

                if (watermarkConfig.enabled) {
                    OutlinedTextField(
                        value = watermarkConfig.handleText,
                        onValueChange = { onWatermarkUpdate(watermarkConfig.copy(handleText = it)) },
                        label = { Text(if (isPersian) "آیدی پیج یا کانال" else "Channel / Page ID") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TerracottaAccent,
                            unfocusedBorderColor = WarmBorder,
                            focusedLabelColor = TerracottaAccent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Position Selector
                    Text(
                        text = if (isPersian) "موقعیت قرارگیری روی تصویر:" else "Watermark Position:",
                        style = MaterialTheme.typography.bodySmall,
                        color = CharcoalSecondary
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        WatermarkPosition.values().forEach { pos ->
                            val selected = watermarkConfig.position == pos
                            FilterChip(
                                selected = selected,
                                onClick = { onWatermarkUpdate(watermarkConfig.copy(position = pos)) },
                                label = { Text(if (isPersian) pos.titleFa else pos.titleEn, fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = TerracottaAccent,
                                    selectedLabelColor = Color.White
                                ),
                                border = FilterChipDefaults.filterChipBorder(
                                    borderColor = if (selected) TerracottaAccent else WarmBorder,
                                    enabled = true,
                                    selected = selected
                                )
                            )
                        }
                    }
                }
            }
        }

        // Privacy Audit Button
        OutlinedButton(
            onClick = onShowPrivacyAudit,
            modifier = Modifier.fillMaxWidth(),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
        ) {
            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = SageGreen, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(
                text = if (isPersian) "مشاهده ممیزی امنیت و حریم خصوصی (بدون GPS)" else "Inspect Offline & Zero-GPS Seal",
                color = CharcoalPrimary
            )
        }
    }
}
