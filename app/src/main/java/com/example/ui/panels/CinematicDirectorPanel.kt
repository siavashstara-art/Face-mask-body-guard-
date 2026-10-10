package com.example.ui.panels

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun CinematicDirectorPanel(
    config: CinematicDirectorConfig,
    onUpdateConfig: (CinematicDirectorConfig) -> Unit,
    activeGarment: MaisonGarmentItem? = null,
    isPersian: Boolean = true,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val scenes = remember(config.selectedScenario) {
        CinematicTimelineRegistry.getScenesForScenario(config.selectedScenario)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(WarmSurface)
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Header with Master Switch
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.MovieFilter,
                        contentDescription = null,
                        tint = Color(0xFFD4AF37),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "دایرکتور سینمایی لحظه‌به‌لحظه (Ghost Guide)",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = CharcoalPrimary
                    )
                }
                Text(
                    text = "هدایت قدم‌به‌قدم ژست عروس و داماد + تیزر ریلز ۱۵ ثانیه‌ای اینستاگرام",
                    fontSize = 11.sp,
                    color = CharcoalSecondary
                )
            }

            Switch(
                checked = config.isEnabled,
                onCheckedChange = { onUpdateConfig(config.copy(isEnabled = it)) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFFD4AF37)
                )
            )
        }

        if (config.isEnabled) {
            // 2. Scenario Selection Cards
            Text(
                text = "انتخاب سناریوی کارگردانی (۱۵ ثانیه):",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = CharcoalPrimary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CinematicScenarioType.values().forEach { scenario ->
                    val isSelected = config.selectedScenario == scenario
                    Surface(
                        color = if (isSelected) Color(0xFFFFF8E7) else WarmSurfaceSecondary,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(
                            1.5.dp,
                            if (isSelected) Color(0xFFD4AF37) else WarmBorder
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                onUpdateConfig(
                                    config.copy(
                                        selectedScenario = scenario,
                                        selectedLut = scenario.defaultLut,
                                        currentSceneIndex = 0
                                    )
                                )
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            val icon = when (scenario) {
                                CinematicScenarioType.SALON_AND_VEIL -> Icons.Default.Face
                                CinematicScenarioType.MAISON_360_SPIN -> Icons.Default.RotateRight
                                CinematicScenarioType.LUXURY_CAR_KEYS -> Icons.Default.VpnKey
                                CinematicScenarioType.GRAND_ENTRY_HALL -> Icons.Default.Celebration
                            }
                            Icon(
                                icon,
                                contentDescription = null,
                                tint = if (isSelected) Color(0xFFD4AF37) else CharcoalSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = when (scenario) {
                                    CinematicScenarioType.SALON_AND_VEIL -> "آینه سالن"
                                    CinematicScenarioType.MAISON_360_SPIN -> "پرو ۳۶۰°"
                                    CinematicScenarioType.LUXURY_CAR_KEYS -> "سوییچ ماشین"
                                    CinematicScenarioType.GRAND_ENTRY_HALL -> "ورود تالار"
                                },
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) CharcoalPrimary else CharcoalSecondary
                            )
                        }
                    }
                }
            }

            // 3. Scenario Timeline Plans (Interactive 4-Scene Selector)
            Surface(
                color = Color(0xFF1E1C1A),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFD4AF37).copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🎬 پلان‌های ۴گانه سناریو (لمس برای تست):",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFE082)
                        )

                        Text(
                            text = "تایم کلی: ۱۵ ثانیه",
                            fontSize = 10.sp,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }

                    // 4 Plan Chips Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        scenes.forEachIndexed { index, scene ->
                            val isSelected = config.currentSceneIndex == index
                            Surface(
                                color = if (isSelected) Color(0xFFD4AF37) else Color(0x33FFFFFF),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        onUpdateConfig(config.copy(currentSceneIndex = index))
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "پلان ${index + 1}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.Black else Color.White
                                    )
                                    Text(
                                        text = "${scene.startSec}-${scene.endSec}s",
                                        fontSize = 8.sp,
                                        color = if (isSelected) Color.Black.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }
                    }

                    // Active Scene Live Guidance Card
                    val currentScene = scenes.getOrElse(config.currentSceneIndex) { scenes.first() }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0x33000000), RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFFFFD54F).copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = currentScene.titleFa,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFD54F)
                        )
                        Text(
                            text = "📢 دستور: ${currentScene.directorCueFa}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Text(
                            text = "💡 نکته ژست: ${currentScene.motionTipFa}",
                            fontSize = 10.sp,
                            color = Color(0xFFBDBDBD)
                        )
                    }
                }
            }

            // 4. Color Grading (LUT) Selection
            Text(
                text = "فیلتر رنگ سینمایی (Cinematic LUT):",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = CharcoalPrimary
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(CinematicLutFilter.values()) { lut ->
                    val isSelected = config.selectedLut == lut
                    Surface(
                        color = if (isSelected) Color(0xFFFFF8E7) else WarmSurfaceSecondary,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) Color(0xFFD4AF37) else WarmBorder
                        ),
                        modifier = Modifier.clickable {
                            onUpdateConfig(config.copy(selectedLut = lut))
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(Color(lut.colorOverlayHex).copy(alpha = 1f))
                            )
                            Spacer(Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = lut.titleFa,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = lut.badgeFa,
                                    fontSize = 8.sp,
                                    color = TerracottaAccent
                                )
                            }
                        }
                    }
                }
            }

            // 5. Director Controls & Options
            Text(
                text = "تنظیمات کارگردانی و قالب خروجی:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = CharcoalPrimary
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Auto Advance Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "هدایت خودکار پلان‌ها با تایمر ضبط",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CharcoalPrimary
                        )
                        Text(
                            text = "تعویض خودکار شبح راهنما و دستورات کارگردان هر ۴ ثانیه حین ضبط",
                            fontSize = 9.sp,
                            color = CharcoalSecondary
                        )
                    }
                    Switch(
                        checked = config.autoAdvanceWithTimer,
                        onCheckedChange = { onUpdateConfig(config.copy(autoAdvanceWithTimer = it)) },
                        colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFFD4AF37))
                    )
                }

                // Slow-Mo 60fps simulation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "اسلوموشن سینمایی (Cinematic Slow-Mo)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CharcoalPrimary
                        )
                        Text(
                            text = "تنظیم کادنس آرامش‌بخش برای جلوه رویایی لباس و تور عروس",
                            fontSize = 9.sp,
                            color = CharcoalSecondary
                        )
                    }
                    Switch(
                        checked = config.isSlowMotionSimulated,
                        onCheckedChange = { onUpdateConfig(config.copy(isSlowMotionSimulated = it)) },
                        colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFFD4AF37))
                    )
                }

                // Instagram Reel 9:16 safe frame
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "کادر امن ریلز اینستاگرام (۹:۱۶ Safe Area)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CharcoalPrimary
                        )
                        Text(
                            text = "نمایش خطوط راهنما برای جلوگیری از پوشیده‌شدن سر و پا با آیکون‌های اینستاگرام",
                            fontSize = 9.sp,
                            color = CharcoalSecondary
                        )
                    }
                    Switch(
                        checked = config.showInstagramReelFrame,
                        onCheckedChange = { onUpdateConfig(config.copy(showInstagramReelFrame = it)) },
                        colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFFD4AF37))
                    )
                }

                // Triple Branding Watermark
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "واتر‌مارک سه‌گانه بیزینس (مزون + سالن + ووچر)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CharcoalPrimary
                        )
                        Text(
                            text = "تبدیل ویدیوی مشتری به ابزار تبلیغاتی ویروسی در شبکه‌های اجتماعی",
                            fontSize = 9.sp,
                            color = CharcoalSecondary
                        )
                    }
                    Switch(
                        checked = config.showTripleBrandingWatermark,
                        onCheckedChange = { onUpdateConfig(config.copy(showTripleBrandingWatermark = it)) },
                        colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFFD4AF37))
                    )
                }
            }

            // 6. Ghost Silhouette Transparency Slider
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "شفافیت شبح طلایی راهنما:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = CharcoalPrimary
                    )
                    Text(
                        text = "${(config.ghostAlpha * 100).toInt()}%",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD4AF37)
                    )
                }
                Slider(
                    value = config.ghostAlpha,
                    onValueChange = { onUpdateConfig(config.copy(ghostAlpha = it)) },
                    valueRange = 0.15f..0.85f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFD4AF37),
                        activeTrackColor = Color(0xFFD4AF37)
                    )
                )
            }
        }
    }
}
