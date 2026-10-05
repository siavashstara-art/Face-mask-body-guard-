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
            text = if (isPersian) "تم رنگی انتخابی:" else "Artistic Look / Filter:",
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

        // Skin Smoothing Slider
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(if (isPersian) "لطافت پوست (Skin Smoothing):" else "Skin Smoothing:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                Text("${(styleConfig.skinSmoothing * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = TerracottaAccent, fontWeight = FontWeight.Medium)
            }
            Slider(
                value = styleConfig.skinSmoothing,
                onValueChange = { onUpdate(styleConfig.copy(skinSmoothing = it)) },
                valueRange = 0f..1.0f,
                colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
            )
        }

        // Warmth / Coolness Slider
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(if (isPersian) "دمای رنگ (Warmth):" else "Color Warmth:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                Text(String.format("%.1f", styleConfig.warmth), style = MaterialTheme.typography.bodySmall, color = MutedAmber, fontWeight = FontWeight.Medium)
            }
            Slider(
                value = styleConfig.warmth,
                onValueChange = { onUpdate(styleConfig.copy(warmth = it)) },
                valueRange = -1.0f..1.0f,
                colors = SliderDefaults.colors(thumbColor = MutedAmber, activeTrackColor = MutedAmber, inactiveTrackColor = WarmBorder)
            )
        }

        // Alteration Label Watermark Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isPersian) "نشانگر شفافیت تغییر دیجیتالی" else "Digitally Altered Badge",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CharcoalPrimary
                )
                Text(
                    text = if (isPersian) "نمایش برچسب شفافیت برای جلوه‌های دیجیتالی" else "Transparently indicates digital camera enhancement",
                    style = MaterialTheme.typography.labelSmall,
                    color = CharcoalSecondary
                )
            }
            Switch(
                checked = styleConfig.showAlterationBadge,
                onCheckedChange = { onUpdate(styleConfig.copy(showAlterationBadge = it)) },
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
                    Text(if (isPersian) "تنظیم کانتور دور کمر:" else "Waist Contour:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
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
                    Text(if (isPersian) "تنظیم کانتور شانه‌ها:" else "Shoulder Contour:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                    Text("${(silhouetteConfig.shoulderContour * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = TerracottaAccent, fontWeight = FontWeight.Medium)
                }
                Slider(
                    value = silhouetteConfig.shoulderContour,
                    onValueChange = { onUpdate(silhouetteConfig.copy(shoulderContour = it)) },
                    valueRange = -0.5f..0.5f,
                    colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
                )
            }

            OutlinedButton(
                onClick = { onUpdate(silhouetteConfig.copy(waistContour = 0f, shoulderContour = 0f, overallScale = 0f)) },
                border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isPersian) "بازنشانی پارامترها" else "Reset Parameters", color = CharcoalPrimary)
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
                text = if (isPersian) "استودیو صدای خصوصی" else "Private Voice Studio",
                style = MaterialTheme.typography.titleMedium,
                color = CharcoalPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Surface(
                color = SageGreenSubtle,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = if (isPersian) "صدا ۱۰۰٪ محلی" else "Local Audio",
                    color = SageGreen,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        // Voice Profile
        Text(
            text = if (isPersian) "پروفایل تن صدا:" else "Voice Profile:",
            style = MaterialTheme.typography.bodyMedium,
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
                            text = if (isPersian) profile.titleFa else profile.titleEn,
                            color = if (selected) TerracottaHover else CharcoalPrimary,
                            fontSize = 12.sp,
                            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
                        )
                    }
                }
            }
        }

        // Mic Gain Slider
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(if (isPersian) "تقویت میکروفون (Gain):" else "Microphone Gain:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                Text("${String.format("%.1f", voiceConfig.micGain)}x", style = MaterialTheme.typography.bodySmall, color = TerracottaAccent, fontWeight = FontWeight.Medium)
            }
            Slider(
                value = voiceConfig.micGain,
                onValueChange = { onUpdate(voiceConfig.copy(micGain = it)) },
                valueRange = 0.5f..2.0f,
                colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
            )
        }

        // Noise Reduction Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isPersian) "حذف نویز محیطی" else "Offline Noise Suppression",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CharcoalPrimary
                )
                Text(
                    text = if (isPersian) "کاهش صدای محیط بدون ارسال داده صوتی" else "Clean audio capture on device",
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

@Composable
fun SettingsPerformancePanel(
    preset: PerformancePreset,
    isPersian: Boolean,
    onPresetChange: (PerformancePreset) -> Unit,
    onShowPrivacyAudit: () -> Unit,
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

        // Privacy Audit Button
        OutlinedButton(
            onClick = onShowPrivacyAudit,
            modifier = Modifier.fillMaxWidth(),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
        ) {
            Icon(Icons.Default.VerifiedUser, contentDescription = null, tint = SageGreen, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(
                text = if (isPersian) "مشاهده ممیزی امنیت و حریم خصوصی" else "Inspect Offline Privacy Seal",
                color = CharcoalPrimary
            )
        }
    }
}
