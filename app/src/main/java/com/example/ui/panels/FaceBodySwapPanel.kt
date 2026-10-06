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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BundledSwapItems
import com.example.model.SwapConfig
import com.example.ui.theme.*

@Composable
fun FaceBodySwapPanel(
    config: SwapConfig,
    isPersian: Boolean,
    onUpdate: (SwapConfig) -> Unit,
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
                text = if (isPersian) "استودیو تعویض چهره و اندام (Swap)" else "Face & Body Swap Studio",
                style = MaterialTheme.typography.titleMedium,
                color = CharcoalPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Surface(
                color = SageGreenSubtle,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = if (isPersian) "آفلاین و بلادرنگ" else "Real-time Offline",
                    style = MaterialTheme.typography.labelSmall,
                    color = SageGreen,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        // ============================================
        // 1. FACE SWAP (تعویض چهره با مدل‌ها و آواتارها)
        // ============================================
        Card(
            colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
            shape = RoundedCornerShape(12.dp),
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
                            text = if (isPersian) "تعویض خودکار چهره (Face Swap)" else "Face Swap on Live Camera",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CharcoalPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (isPersian) "انطباق هوشمند مدل انتخابی روی چهره شما" else "Smart morphing onto detected facial bounds",
                            style = MaterialTheme.typography.labelSmall,
                            color = CharcoalSecondary
                        )
                    }
                    Switch(
                        checked = config.faceSwapEnabled,
                        onCheckedChange = { onUpdate(config.copy(faceSwapEnabled = it)) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = TerracottaAccent
                        )
                    )
                }

                if (config.faceSwapEnabled) {
                    Text(
                        text = if (isPersian) "انتخاب مدل یا پرسونای چهره:" else "Select Face Persona:",
                        style = MaterialTheme.typography.bodySmall,
                        color = CharcoalSecondary
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(BundledSwapItems.avatars) { avatar ->
                            val isSelected = config.selectedAvatarId == avatar.id
                            Card(
                                modifier = Modifier
                                    .width(130.dp)
                                    .clickable { onUpdate(config.copy(selectedAvatarId = avatar.id)) }
                                    .border(
                                        width = 1.dp,
                                        color = if (isSelected) TerracottaAccent else WarmBorder,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) TerracottaSubtle else WarmSurface
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(id = avatar.drawableResId),
                                        contentDescription = avatar.titleEn,
                                        modifier = Modifier.size(54.dp)
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = if (isPersian) avatar.titleFa else avatar.titleEn,
                                        color = if (isSelected) TerracottaHover else CharcoalPrimary,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }

                    // Blend slider
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(if (isPersian) "میزان ترکیب چهره (Opacity):" else "Face Swap Opacity:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                            Text("${(config.faceBlendAlpha * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = TerracottaAccent, fontWeight = FontWeight.Medium)
                        }
                        Slider(
                            value = config.faceBlendAlpha,
                            onValueChange = { onUpdate(config.copy(faceBlendAlpha = it)) },
                            valueRange = 0.4f..1.0f,
                            colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
                        )
                    }
                }
            }
        }

        // ============================================
        // 2. BODY SWAP (تعویض اندام با مانکن‌های مد)
        // ============================================
        Card(
            colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
            shape = RoundedCornerShape(12.dp),
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
                            text = if (isPersian) "تعویض اندام با مانکن (Body Swap)" else "Body / Mannequin Swap",
                            style = MaterialTheme.typography.bodyMedium,
                            color = CharcoalPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (isPersian) "نمایش محصول روی مانکن‌های تجاری بوتیک" else "Dress product onto virtual boutique mannequin",
                            style = MaterialTheme.typography.labelSmall,
                            color = CharcoalSecondary
                        )
                    }
                    Switch(
                        checked = config.bodySwapEnabled,
                        onCheckedChange = { onUpdate(config.copy(bodySwapEnabled = it)) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = TerracottaAccent
                        )
                    )
                }

                if (config.bodySwapEnabled) {
                    Text(
                        text = if (isPersian) "انتخاب نوع مانکن یا اندام:" else "Select Mannequin Model:",
                        style = MaterialTheme.typography.bodySmall,
                        color = CharcoalSecondary
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(BundledSwapItems.mannequins) { item ->
                            val isSelected = config.selectedBodyId == item.id
                            Card(
                                modifier = Modifier
                                    .width(130.dp)
                                    .clickable { onUpdate(config.copy(selectedBodyId = item.id)) }
                                    .border(
                                        width = 1.dp,
                                        color = if (isSelected) TerracottaAccent else WarmBorder,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected) TerracottaSubtle else WarmSurface
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(id = item.drawableResId),
                                        contentDescription = item.titleEn,
                                        modifier = Modifier.size(54.dp),
                                        contentScale = ContentScale.Fit
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = if (isPersian) item.titleFa else item.titleEn,
                                        color = if (isSelected) TerracottaHover else CharcoalPrimary,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }

                    // Mannequin scale
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(if (isPersian) "مقیاس و ابعاد مانکن:" else "Mannequin Scale:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                            Text("${String.format("%.2f", config.bodyScale)}x", style = MaterialTheme.typography.bodySmall, color = TerracottaAccent, fontWeight = FontWeight.Medium)
                        }
                        Slider(
                            value = config.bodyScale,
                            onValueChange = { onUpdate(config.copy(bodyScale = it)) },
                            valueRange = 0.7f..1.4f,
                            colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
                        )
                    }

                    // Mannequin Vertical Offset
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(if (isPersian) "تنظیم ارتفاع مانکن:" else "Vertical Alignment:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                            Text("${config.bodyOffsetY.toInt()} px", style = MaterialTheme.typography.bodySmall, color = TerracottaAccent, fontWeight = FontWeight.Medium)
                        }
                        Slider(
                            value = config.bodyOffsetY,
                            onValueChange = { onUpdate(config.copy(bodyOffsetY = it)) },
                            valueRange = -150f..150f,
                            colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
                        )
                    }

                    // Body Opacity
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(if (isPersian) "شفافیت مانکن (Blend):" else "Mannequin Blend Opacity:", style = MaterialTheme.typography.bodySmall, color = CharcoalSecondary)
                            Text("${(config.bodyBlendAlpha * 100).toInt()}%", style = MaterialTheme.typography.bodySmall, color = TerracottaAccent, fontWeight = FontWeight.Medium)
                        }
                        Slider(
                            value = config.bodyBlendAlpha,
                            onValueChange = { onUpdate(config.copy(bodyBlendAlpha = it)) },
                            valueRange = 0.4f..1.0f,
                            colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
                        )
                    }
                }
            }
        }
    }
}
