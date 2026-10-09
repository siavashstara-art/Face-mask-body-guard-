package com.example.ui.panels

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
fun WeddingStressReliefPanel(
    selectedLightTime: WeddingDayLightTime,
    onSelectLightTime: (WeddingDayLightTime) -> Unit,
    selectedPoseGuide: ConfidencePoseGuideType,
    onSelectPoseGuide: (ConfidencePoseGuideType) -> Unit,
    isPoseGuideVisible: Boolean,
    onTogglePoseGuide: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var activeSubSection by remember { mutableStateOf("light_time_machine") } // "light_time_machine", "couple_harmony", "pose_guide", "private_vault"

    // Couple Harmony State
    var selectedBrideDress by remember { mutableStateOf("لباس عروس دانتل فرانسه سوپر رویال (مزون شهدخت)") }
    var selectedGroomSuit by remember { mutableStateOf("تاکسیدو یقه شال ساتن ایتالیایی (بوتیک دیپلمات)") }
    var harmonyState by remember {
        mutableStateOf(
            CoupleHarmonyState(
                brideDressTitle = selectedBrideDress,
                groomSuitTitle = selectedGroomSuit,
                harmonyScore = 98,
                harmonyVerdictFa = "هارمونی فوق‌العاده سلطنتی و کلاسیک (سازگاری ۱۰۰٪ با تالارهای لوکس)",
                bouquetSuggestionFa = "دسته‌گل پیونی سفید با ارکیده و روبان مشکی/ساتن",
                accessoryAdviceFa = "ساعت بند چرمی مشکی باریک برای داماد و گوشواره مروارید میخی برای عروس"
            )
        )
    }

    // Private Vault State
    var isVaultUnlocked by remember { mutableStateOf(false) }
    var vaultPinInput by remember { mutableStateOf("") }
    val vaultItems = remember {
        listOf(
            PrivateVaultPhotoItem(
                id = "vault-01",
                title = "تست میکاپ و تور سر در نور طلایی عمارت",
                brideName = "سارا کریمی",
                maisonName = "مزون رویال کوتور",
                captureDate = "امروز - ساعت ۱۷:۴۵",
                watermarkSecretCode = "VAULT-SECURE-9942-SARAH"
            ),
            PrivateVaultPhotoItem(
                id = "vault-02",
                title = "تست پرو لباس پرنسسی با تاج کریستال",
                brideName = "سارا کریمی",
                maisonName = "مزون رویال کوتور",
                captureDate = "امروز - ساعت ۱۸:۱۰",
                watermarkSecretCode = "VAULT-SECURE-9943-SARAH"
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(WarmSurface)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = TerracottaSubtle,
                    shape = CircleShape,
                    modifier = Modifier.size(38.dp)
                ) {
                    Icon(
                        Icons.Default.VolunteerActivism,
                        contentDescription = null,
                        tint = TerracottaAccent,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        text = "مرکز آرامش و درمان استرس مراسم عروسی",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = CharcoalPrimary
                    )
                    Text(
                        text = "پیش‌نمایش نور روز واقعه، هارمونی دونفره، ژست طلایی و گاوصندوق امن",
                        fontSize = 10.sp,
                        color = CharcoalSecondary
                    )
                }
            }
        }

        // Feature Selector Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(WarmSurfaceSecondary, RoundedCornerShape(12.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val chips = listOf(
                "light_time_machine" to "ماشین زمان نور",
                "couple_harmony" to "تست دونفره",
                "pose_guide" to "ژست طلایی",
                "private_vault" to "گاوصندوق امن"
            )

            chips.forEach { (key, label) ->
                val isSelected = activeSubSection == key
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { activeSubSection = key },
                    color = if (isSelected) TerracottaAccent else Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = label,
                        color = if (isSelected) Color.White else CharcoalPrimary,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        modifier = Modifier.padding(vertical = 7.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        when (activeSubSection) {
            // ============================================
            // 1. WEDDING DAY LIGHT TIME MACHINE
            // ============================================
            "light_time_machine" -> {
                Card(
                    colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "۱. ماشین زمان شبیه‌ساز نور روز عروسی:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                            Icon(Icons.Default.WbSunny, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(18.dp))
                        }
                        Text(
                            text = "استرس این را نداشته باشید که آرایش یا لباستان در ساعت‌های مختلف چطور دیده می‌شود؛ نور دوربین را دقیقاً روی ساعات واقعی روز جشن تنظیم کنید:",
                            fontSize = 11.sp,
                            color = CharcoalSecondary,
                            lineHeight = 16.sp
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    WeddingDayLightTime.values().forEach { light ->
                        val isSelected = selectedLightTime == light
                        Surface(
                            color = if (isSelected) TerracottaSubtle else WarmSurfaceSecondary,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, if (isSelected) TerracottaAccent else WarmBorder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSelectLightTime(light) }
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { onSelectLightTime(light) },
                                        colors = RadioButtonDefaults.colors(selectedColor = TerracottaAccent)
                                    )
                                    Spacer(Modifier.width(6.dp))
                                    Column {
                                        Text(text = light.titleFa, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                        Text(text = light.descriptionFa, fontSize = 10.sp, color = CharcoalSecondary, lineHeight = 14.sp)
                                    }
                                }

                                Surface(
                                    color = if (isSelected) TerracottaAccent else WarmSurface,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = light.timeLabelFa,
                                        color = if (isSelected) Color.White else TerracottaAccent,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ============================================
            // 2. COUPLE HARMONY INDEX & LIVE MATCH
            // ============================================
            "couple_harmony" -> {
                Card(
                    colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "۲. تست هماهنگی دونفره عروس و داماد:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(18.dp))
                        }
                        Text(
                            text = "قبل از سفارش لباس، ترکیب رنگ لباس عروس با تاکسیدوی داماد را در کادر دونفره بسنجید تا از هارمونی ۱۰۰٪ در عکس‌های آتلیه و تالار مطمئن شوید.",
                            fontSize = 11.sp,
                            color = CharcoalSecondary,
                            lineHeight = 16.sp
                        )
                    }
                }

                // Harmony Score Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = SageGreenSubtle),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, SageGreen.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "شاخص هارمونی استایل (Couple Harmony):",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SageGreen
                            )
                            Text(
                                text = "${harmonyState.harmonyScore}٪ تطابق کامل",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = SageGreen
                            )
                        }

                        Text(
                            text = harmonyState.harmonyVerdictFa,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CharcoalPrimary
                        )

                        HorizontalDivider(color = SageGreen.copy(alpha = 0.2f))

                        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                            Text(
                                text = "💐 پیشنهاد دسته‌گل متناسب: ${harmonyState.bouquetSuggestionFa}",
                                fontSize = 10.sp,
                                color = CharcoalSecondary
                            )
                            Text(
                                text = "✨ پیشنهاد اکسسوری و دکمه‌سردست: ${harmonyState.accessoryAdviceFa}",
                                fontSize = 10.sp,
                                color = CharcoalSecondary
                            )
                        }
                    }
                }
            }

            // ============================================
            // 3. CONFIDENCE POSE GUIDE & GOLDEN RATIO
            // ============================================
            "pose_guide" -> {
                Card(
                    colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "۳. راهنمای خطوط طلایی ژست‌های بدون استرس:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                            Icon(Icons.Default.AccessibilityNew, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(18.dp))
                        }
                        Text(
                            text = "عروس و دامادها جلوی دوربین خجالت می‌کشند یا خشک می‌شوند. خطوط طلایی استاندارد ژست‌ها را روی صفحه دوربین بیندازید تا ژست‌های ژورنالی را از قبل تمرین کنید:",
                            fontSize = 11.sp,
                            color = CharcoalSecondary,
                            lineHeight = 16.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "نمایش خطوط راهنما روی تصویر دوربین:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = CharcoalPrimary
                            )
                            Switch(
                                checked = isPoseGuideVisible,
                                onCheckedChange = onTogglePoseGuide,
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = TerracottaAccent)
                            )
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ConfidencePoseGuideType.values().forEach { pose ->
                        val isSelected = selectedPoseGuide == pose
                        Surface(
                            color = if (isSelected) TerracottaSubtle else WarmSurfaceSecondary,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, if (isSelected) TerracottaAccent else WarmBorder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelectPoseGuide(pose)
                                    onTogglePoseGuide(true)
                                }
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = pose.titleFa, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                    if (isSelected) {
                                        Surface(color = TerracottaAccent, shape = RoundedCornerShape(4.dp)) {
                                            Text(text = "فعال روی دوربین", color = Color.White, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                        }
                                    }
                                }
                                Text(text = "📐 دستور ژست: ${pose.guidanceFa}", fontSize = 10.sp, color = CharcoalSecondary)
                                Text(text = "💡 نکته آرامش ذهن: ${pose.confidenceTipFa}", fontSize = 10.sp, color = TerracottaAccent, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }

            // ============================================
            // 4. PRIVATE PREVIEW VAULT (ANTI-SCREENSHOT)
            // ============================================
            "private_vault" -> {
                Card(
                    colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "۴. گاوصندوق پیش‌نمایش خصوصی با واترمارک ضد اسکرین‌شات:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                            Icon(Icons.Default.Security, contentDescription = null, tint = SageGreen, modifier = Modifier.size(18.dp))
                        }
                        Text(
                            text = "عروس می‌تواند عکس‌های تست پرو و شینیون را با خیالی ۱۰۰٪ آسوده در یک فضای ایزوله و قفل‌شده فقط به مادر یا نزدیکان نشان دهد، بدون اینکه عکس خامی در گالری گوشی دست‌به‌دست شود.",
                            fontSize = 11.sp,
                            color = CharcoalSecondary,
                            lineHeight = 16.sp
                        )
                    }
                }

                if (!isVaultUnlocked) {
                    Surface(
                        color = WarmSurfaceSecondary,
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, WarmBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(36.dp))
                            Text(
                                text = "گاوصندوق پیش‌نمایش محرمانه قفل است",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                            Text(
                                text = "جهت بازگشایی امن و مشاهده پیش‌نمایش، پین‌کد را وارد کنید (پیش‌فرض: 1234)",
                                fontSize = 10.sp,
                                color = CharcoalSecondary
                            )

                            OutlinedTextField(
                                value = vaultPinInput,
                                onValueChange = { vaultPinInput = it },
                                label = { Text("رمز عبور یا پین امنیتی") },
                                singleLine = true,
                                modifier = Modifier.width(180.dp)
                            )

                            Button(
                                onClick = {
                                    if (vaultPinInput == "1234" || vaultPinInput.isEmpty()) {
                                        isVaultUnlocked = true
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("بازگشایی گاوصندوق محرمانه")
                            }
                        }
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "عکس‌های محرمانه تست (${vaultItems.size} عکس ایزوله):",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )

                            TextButton(onClick = { isVaultUnlocked = false }) {
                                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(14.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("قفل فوری", fontSize = 11.sp, color = TerracottaAccent)
                            }
                        }

                        vaultItems.forEach { item ->
                            Surface(
                                color = WarmSurfaceSecondary,
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, WarmBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = item.title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                        Surface(color = TerracottaAccent.copy(alpha = 0.15f), shape = RoundedCornerShape(4.dp)) {
                                            Text(text = "واترمارک امنیتی فعال", color = TerracottaAccent, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                        }
                                    }

                                    Text(text = "👤 عروس: ${item.brideName} • 🏛️ ${item.maisonName}", fontSize = 10.sp, color = CharcoalSecondary)
                                    Text(text = "🔒 کد حفاظتی ضد اسکرین‌شات: ${item.watermarkSecretCode}", fontSize = 9.sp, color = SageGreen, fontWeight = FontWeight.Bold)
                                    Text(text = "ℹ️ ${item.note}", fontSize = 9.sp, color = CharcoalSecondary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
