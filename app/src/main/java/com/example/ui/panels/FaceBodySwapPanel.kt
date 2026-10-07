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
import com.example.model.AiEngineExecutionMode
import com.example.model.BundledSwapItems
import com.example.model.SwapConfig
import com.example.ui.theme.*

@Composable
fun FaceBodySwapPanel(
    config: SwapConfig,
    isPersian: Boolean,
    onUpdate: (SwapConfig) -> Unit,
    onActivateTripleShield: () -> Unit = {},
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
                text = if (isPersian) "استودیو هوش مصنوعی، چهره و اندام" else "AI Model & Swap Studio",
                style = MaterialTheme.typography.titleMedium,
                color = CharcoalPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Surface(
                color = if (config.aiExecutionMode == AiEngineExecutionMode.OFFLINE_EDGE_LOCAL) SageGreenSubtle else TerracottaSubtle,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = if (isPersian) {
                        if (config.aiExecutionMode == AiEngineExecutionMode.OFFLINE_EDGE_LOCAL) "پردازش آفلاین محلی" else "هوش مصنوعی آنلاین"
                    } else {
                        if (config.aiExecutionMode == AiEngineExecutionMode.OFFLINE_EDGE_LOCAL) "Local Edge AI" else "Cloud AI Active"
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = if (config.aiExecutionMode == AiEngineExecutionMode.OFFLINE_EDGE_LOCAL) SageGreen else TerracottaAccent,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        // ============================================
        // 0. AI EXECUTION MODE (حالت آفلاین / آنلاین)
        // ============================================
        Card(
            colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = if (isPersian) "حالت اجرای موتور هوش مصنوعی:" else "AI Engine Execution Mode:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CharcoalPrimary,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Offline Mode Chip
                    val isOffline = config.aiExecutionMode == AiEngineExecutionMode.OFFLINE_EDGE_LOCAL
                    Surface(
                        color = if (isOffline) SageGreenSubtle else WarmSurface,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isOffline) SageGreen else WarmBorder
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onUpdate(config.copy(aiExecutionMode = AiEngineExecutionMode.OFFLINE_EDGE_LOCAL))
                            }
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.CloudOff,
                                    contentDescription = null,
                                    tint = if (isOffline) SageGreen else CharcoalSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text = if (isPersian) "تماماً آفلاین (بدون اینترنت)" else "100% Offline (Local)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isOffline) SageGreen else CharcoalPrimary
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = if (isPersian) "نقاب‌ها، برقع، مات‌سازی، میوت صدا و اصلاح اندام بدون ۱ بایت اینترنت روی گوشی کار می‌کنند." else "All masks, audio mute, and body tools run strictly offline.",
                                fontSize = 10.sp,
                                color = CharcoalSecondary,
                                lineHeight = 14.sp
                            )
                        }
                    }

                    // Online Mode Chip (The ONLY online feature)
                    val isOnline = config.aiExecutionMode == AiEngineExecutionMode.ONLINE_CLOUD_HYBRID
                    Surface(
                        color = if (isOnline) TerracottaSubtle else WarmSurface,
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isOnline) TerracottaAccent else WarmBorder
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onUpdate(config.copy(aiExecutionMode = AiEngineExecutionMode.ONLINE_CLOUD_HYBRID))
                            }
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.CloudQueue,
                                    contentDescription = null,
                                    tint = if (isOnline) TerracottaAccent else CharcoalSecondary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text = if (isPersian) "آنلاین ابری (ویژه سالن)" else "Online Salon Cloud",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (isOnline) TerracottaAccent else CharcoalPrimary
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = if (isPersian) "تنها قابلیت آنلاین: تعویض چهره مشتری با چهره مجازی جهت آرشیو سالن و ژورنال" else "Only online feature: Generates synthetic faces for salon archive.",
                                fontSize = 10.sp,
                                color = CharcoalSecondary,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }

                // Dedicated Salon Explanatory Banner
                Surface(
                    color = WarmSurfaceVariant,
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            Icons.Default.FaceRetouchingNatural,
                            contentDescription = null,
                            tint = TerracottaAccent,
                            modifier = Modifier
                                .size(18.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (isPersian)
                                "💡 ویژه مدیران سالن زیبایی: وقتی مو و صورت مشتری را درست کرده‌اید اما او اجازه انتشار چهره نمی‌دهد، با اتصال به اینترنت چهره او با یک چهره مجازی و غیرواقعی هوش مصنوعی جایگزین می‌شود؛ اما مدل مو، شینیون، اتو، رنگ و هنر دست شما کاملاً دست‌نخورده در عکس و آرشیو باقی می‌ماند. بدون اینترنت نیز تمام نقاب‌ها و امکانات آفلاین در دسترس هستند."
                            else
                                "Salon Archiving Note: If a client declines face exposure, online AI generates a synthetic virtual face while keeping their ironed hair, curls, and styling 100% intact.",
                            fontSize = 11.sp,
                            color = CharcoalPrimary,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // ============================================
        // 0.1 TRIPLE SHIELD SHORTCUT (فعالسازی هر ۳ باهم)
        // ============================================
        Card(
            colors = CardDefaults.cardColors(
                containerColor = if (config.tripleShieldEnforced) TerracottaSubtle else WarmSurfaceSecondary
            ),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (config.tripleShieldEnforced) TerracottaAccent else WarmBorder
            )
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Icon(
                            Icons.Default.Security,
                            contentDescription = null,
                            tint = TerracottaAccent,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(
                                text = if (isPersian) "سپر سه‌گانه هوشمند (All-in-One)" else "Triple Protection Shield",
                                style = MaterialTheme.typography.bodyMedium,
                                color = CharcoalPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = if (isPersian)
                                    "ردیابی خودکار + تاری چهره + سواپ مدل همزمان"
                                else
                                    "Face Tracking + Blur + Face Swap simultaneously",
                                style = MaterialTheme.typography.labelSmall,
                                color = CharcoalSecondary
                            )
                        }
                    }
                    Button(
                        onClick = {
                            val next = !config.tripleShieldEnforced
                            onUpdate(config.copy(tripleShieldEnforced = next))
                            if (next) onActivateTripleShield()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (config.tripleShieldEnforced) TerracottaAccent else WarmSurface,
                            contentColor = if (config.tripleShieldEnforced) Color.White else CharcoalPrimary
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (config.tripleShieldEnforced) TerracottaAccent else WarmBorder),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = if (isPersian) (if (config.tripleShieldEnforced) "فعال است" else "فعال‌سازی ۳تایی") else (if (config.tripleShieldEnforced) "Active" else "Enable All 3"),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Text(
                    text = if (isPersian)
                        "توصیه ویژه برای مدلینگ لباس زیر: ردیاب زنده سر شما را تعقیب می‌کند، لایه زیرین چهره را مات نگه می‌دارد و مدل جذاب هوش مصنوعی روی آن قرار می‌گیرد تا خانواده هرگز شما را نشناسند."
                    else
                        "Recommended for swimwear/underwear modeling: live tracking anchors the blur while placing aesthetic AI persona over it for zero identity leakage.",
                    style = MaterialTheme.typography.labelSmall,
                    color = CharcoalTertiary,
                    lineHeight = 15.sp
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
                            text = if (isPersian) "انطباق مدل هوش مصنوعی انتخابی روی چهره شما" else "Smart morphing onto detected facial bounds",
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
                        text = if (isPersian) "انتخاب مدل هوش مصنوعی (زنانه / تجاری):" else "Select AI Model Persona:",
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
                                    .width(140.dp)
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
                                        modifier = Modifier.size(56.dp)
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(
                                        text = if (isPersian) avatar.titleFa else avatar.titleEn,
                                        color = if (isSelected) TerracottaHover else CharcoalPrimary,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 2
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
                            text = if (isPersian) "نمایش لباس روی مانکن‌های تجاری ساعت شنی" else "Dress product onto virtual boutique mannequin",
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
                        text = if (isPersian) "انتخاب مانکن (مخصوص لباس زیر و مایو):" else "Select Mannequin Model:",
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
                                    .width(135.dp)
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
                                        maxLines = 2
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

                    HorizontalDivider(color = WarmBorderSubtle)

                    // 1. Shelf-Butt Contour (طاقچه‌ای و گرد کردن باسن بدون چاقی بی‌ریخت)
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (isPersian) "🍑 فرم‌دهی گرد و طاقچه‌ای باسن (Shelf Butt):" else "🍑 Brazilian Shelf-Butt Contour:",
                                style = MaterialTheme.typography.bodySmall,
                                color = CharcoalPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${(config.shelfButtContour * 100).toInt()}%",
                                style = MaterialTheme.typography.bodySmall,
                                color = TerracottaAccent,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Slider(
                            value = config.shelfButtContour,
                            onValueChange = { onUpdate(config.copy(shelfButtContour = it)) },
                            valueRange = 0.5f..1.5f,
                            colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
                        )
                        Text(
                            text = if (isPersian)
                                "بدون چاقی بی‌ریخت یا افتادگی؛ باسن کاملاً گرد، سفت و طاقچه‌ای از بالا لیفت می‌شود تا تن‌خور لباس شب و لباس زیر بی‌نقص شود."
                            else
                                "Sculpts high round shelf curvature without bulky fat, lifting upwards for flawless gown/swimwear fit.",
                            fontSize = 10.sp,
                            color = CharcoalSecondary,
                            lineHeight = 14.sp
                        )
                    }

                    // 2. Sculpted Bust Lift (لیفت و فرم‌دهی جذاب و مشتری‌پسند سینه)
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (isPersian) "✨ لیفت و برجسته‌سازی شکیل سینه (Bust Lift):" else "✨ Sculpted Lifted Bust Contour:",
                                style = MaterialTheme.typography.bodySmall,
                                color = CharcoalPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${(config.bustLiftFirmness * 100).toInt()}%",
                                style = MaterialTheme.typography.bodySmall,
                                color = TerracottaAccent,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Slider(
                            value = config.bustLiftFirmness,
                            onValueChange = { onUpdate(config.copy(bustLiftFirmness = it)) },
                            valueRange = 0.5f..1.5f,
                            colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
                        )
                        Text(
                            text = if (isPersian)
                                "لیفت رو به بالا و متقارن کردن سینه مخصوص یقه‌های دکلته لباس عروس و ست‌های کاپ‌دار لباس زیر."
                            else
                                "Perky upward cleavage sculpting tailored for decollete bridal gowns and luxury lingerie cups.",
                            fontSize = 10.sp,
                            color = CharcoalSecondary,
                            lineHeight = 14.sp
                        )
                    }

                    // 3. Waist Snatch (کمر باریک ساعت شنی)
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (isPersian) "⏳ باریک‌سازی کمر زنبوری (Waist Snatch):" else "⏳ Hourglass Snapped Waist:",
                                style = MaterialTheme.typography.bodySmall,
                                color = CharcoalPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${(config.waistTaperRatio * 100).toInt()}%",
                                style = MaterialTheme.typography.bodySmall,
                                color = TerracottaAccent,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Slider(
                            value = config.waistTaperRatio,
                            onValueChange = { onUpdate(config.copy(waistTaperRatio = it)) },
                            valueRange = 0.5f..1.0f,
                            colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent, inactiveTrackColor = WarmBorder)
                        )
                    }
                }
            }
        }
    }
}
