package com.example.ui.facecard

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.*
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.engine.StyledQrCodeCard
import com.example.model.BusinessCardProfile
import com.example.model.PersonaType
import com.example.ui.theme.*

@Composable
fun FaceCardDashboardScreen(
    cards: List<BusinessCardProfile>,
    activeCardId: String,
    onSelectCard: (String) -> Unit,
    onShareVCard: (BusinessCardProfile) -> Unit,
    onNavigateToStudio: () -> Unit,
    onNavigateToAdmin: () -> Unit = {}
) {
    val context = LocalContext.current
    val activeCard = cards.find { it.id == activeCardId } ?: cards.first()
    var isShowingQrCode by remember { mutableStateOf(false) }
    var isNfcSimulating by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Header & Switcher Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "FaceCard",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = CharcoalPrimary
                )
                Text(
                    text = "پاسپورت هویت دیجیتال و کارت ویزیت هوشمند",
                    fontSize = 12.sp,
                    color = CharcoalSecondary
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                // Admin Dashboard Master Access (پنل انحصاری مدیریت)
                IconButton(
                    onClick = onNavigateToAdmin,
                    modifier = Modifier
                        .size(42.dp)
                        .background(Color(0xFF1E1C1A), CircleShape)
                        .border(1.dp, Color(0xFFD4AF37), CircleShape)
                ) {
                    Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin Panel", tint = Color(0xFFFFD54F))
                }

                // Quick Studio Portrait camera shortcut
                IconButton(
                    onClick = onNavigateToStudio,
                    modifier = Modifier
                        .size(42.dp)
                        .background(WarmSurface, CircleShape)
                        .border(1.dp, WarmBorder, CircleShape)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Camera Studio", tint = TerracottaAccent)
                }
            }
        }

        // Persona Switcher Bar (سالن زیبایی، بازاریاب آکادمی، ساختمان‌یار)
        Text(
            text = "کارت‌های چندگانه هویت شما (Multi-Persona):",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = CharcoalPrimary
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(cards) { card ->
                val isSelected = card.id == activeCardId
                Surface(
                    color = if (isSelected) TerracottaAccent else WarmSurface,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) TerracottaAccent else WarmBorder
                    ),
                    modifier = Modifier.clickable { onSelectCard(card.id) }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val icon = when (card.personaType) {
                            PersonaType.SALON_BEAUTY -> Icons.Default.Face
                            PersonaType.MARKETING_ACADEMY -> Icons.Default.TrendingUp
                            PersonaType.SAKHTEMAN_PASS -> Icons.Default.Apartment
                            PersonaType.CORPORATE_VIP -> Icons.Default.BusinessCenter
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isSelected) Color.White else CharcoalSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = card.personaType.titleFa.substringBefore(" ("),
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else CharcoalPrimary
                        )
                    }
                }
            }
        }

        // Interactive Digital Business Card
        val gradientColors = listOf(
            Color(activeCard.theme.startHex),
            Color(activeCard.theme.endHex)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(16.dp, RoundedCornerShape(22.dp)),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.linearGradient(gradientColors))
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Card Header with NFC chip icon and Brand
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Nfc,
                                contentDescription = "NFC Enabled",
                                tint = Color.White.copy(alpha = 0.85f),
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "NFC ACTIVE",
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        Surface(
                            color = Color.Black.copy(alpha = 0.25f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "FaceCard Passport",
                                color = Color.White,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    // Card Owner Info
                    Column {
                        Text(
                            text = activeCard.fullName,
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = activeCard.jobTitle,
                            color = Color.White.copy(alpha = 0.90f),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = activeCard.companyName,
                            color = Color.White.copy(alpha = 0.75f),
                            fontSize = 11.sp
                        )
                    }

                    // Card Footer (Phone & QR flip prompt)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = activeCard.phoneNumber,
                            color = Color.White.copy(alpha = 0.90f),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Button(
                            onClick = { isShowingQrCode = !isShowingQrCode },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(activeCard.theme.startHex)
                            ),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = if (isShowingQrCode) Icons.Default.CreditCard else Icons.Default.QrCode2,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = if (isShowingQrCode) "نمایش کارت" else "QR هوشمند",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Expanded QR View OR Action Grid
        AnimatedVisibility(visible = isShowingQrCode) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StyledQrCodeCard(
                    payload = activeCard.websiteUrl.ifEmpty { activeCard.phoneNumber },
                    title = "کارت ویزیت دیجیتال ${activeCard.fullName}",
                    accentColor = Color(activeCard.theme.startHex)
                )
            }
        }

        // Sharing & NFC Action Strip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Instant Share (vCard)
            Button(
                onClick = { onShareVCard(activeCard) },
                colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("ارسال و اشتراک سریع", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            // Simulate NFC Wave Tap
            Button(
                onClick = { isNfcSimulating = true },
                colors = ButtonDefaults.buttonColors(containerColor = SageGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Nfc, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("انتقال با لمس NFC", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        // NFC Simulation Feedback Sheet
        if (isNfcSimulating) {
            Surface(
                color = SageGreenSubtle,
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, SageGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        color = SageGreen,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.5.dp
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "آماده تبادل NFC: گوشی دیگر را نزدیک کنید",
                            fontWeight = FontWeight.Bold,
                            color = CharcoalPrimary,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "اطلاعات تماس ${activeCard.fullName} فوراً به دفترچه مخاطبین مقصد منتقل می‌شود.",
                            color = CharcoalSecondary,
                            fontSize = 10.sp
                        )
                    }
                    TextButton(onClick = { isNfcSimulating = false }) {
                        Text("بستن", color = SageGreen, fontSize = 11.sp)
                    }
                }
            }
        }

        // Quick Direct Channels (Call, WhatsApp, Telegram, Instagram, Website, Location)
        Text(
            text = "راه‌های ارتباطی و لینک‌های اختصاصی:",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = CharcoalPrimary
        )

        val channels = listOf(
            Triple(Icons.Default.Phone, "تماس مستقیم", "tel:${activeCard.phoneNumber}"),
            Triple(Icons.Default.Send, "تلگرام", "https://t.me/${activeCard.telegramId}"),
            Triple(Icons.Default.CameraAlt, "اینستاگرام", "https://instagram.com/${activeCard.instagramId}"),
            Triple(Icons.Default.Language, "وب‌سایت / پورتفولیو", activeCard.websiteUrl),
            Triple(Icons.Default.LocationOn, "مسیریابی", "geo:0,0?q=${Uri.encode(activeCard.address)}")
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            for ((icon, title, intentUri) in channels) {
                Surface(
                    color = WarmSurface,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(intentUri)).apply {
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                                context.startActivity(intent)
                            } catch (_: Exception) {}
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = TerracottaSubtle,
                                shape = CircleShape,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = title,
                                    tint = TerracottaAccent,
                                    modifier = Modifier.padding(7.dp)
                                )
                            }
                            Spacer(Modifier.width(10.dp))
                            Text(text = title, fontSize = 12.sp, color = CharcoalPrimary, fontWeight = FontWeight.Medium)
                        }

                        Icon(Icons.Default.ChevronLeft, contentDescription = null, tint = CharcoalTertiary)
                    }
                }
            }
        }

        // Card Performance & Networking Analytics
        Surface(
            color = WarmSurfaceSecondary,
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${activeCard.viewCount}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TerracottaAccent)
                    Text("بازدید کارت", fontSize = 11.sp, color = CharcoalSecondary)
                }
                VerticalDivider(modifier = Modifier.height(30.dp), color = WarmBorder)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${activeCard.shareCount}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SageGreen)
                    Text("ذخیره در مخاطبین", fontSize = 11.sp, color = CharcoalSecondary)
                }
                VerticalDivider(modifier = Modifier.height(30.dp), color = WarmBorder)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("۱۰۰٪", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                    Text("کارت سبز فعال", fontSize = 11.sp, color = CharcoalSecondary)
                }
            }
        }
    }
}
