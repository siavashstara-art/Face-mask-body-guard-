package com.example.ui.facecard

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MarketingAcademyLeadStats
import com.example.model.SakhtemanYarAccessPass
import com.example.model.SalonYarClientSession
import com.example.ui.theme.*

@Composable
fun EcosystemHubScreen(
    salonSession: SalonYarClientSession,
    sakhtemanPass: SakhtemanYarAccessPass,
    marketingStats: MarketingAcademyLeadStats,
    onSimulateDoorUnlock: () -> Unit
) {
    var isDoorUnlocked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header
        Column {
            Text(
                text = "اکوسیستم یکپارچه توانا",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = CharcoalPrimary
            )
            Text(
                text = "اتصال مستقیم FaceCard به سالن‌یار، ساختمان‌یار و آکادمی مارکتینگ",
                fontSize = 12.sp,
                color = CharcoalSecondary
            )
        }

        // ============================================
        // 1. SALONYAR INTEGRATION (سالن‌یار)
        // ============================================
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = WarmSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = TerracottaSubtle,
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.Spa, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.padding(8.dp))
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "سالن‌یار (SalonYar)",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                            Text(
                                text = "سامانه نوبت‌دهی و پرونده هوشمند زیبایی",
                                fontSize = 10.sp,
                                color = CharcoalSecondary
                            )
                        }
                    }

                    Surface(
                        color = SageGreenSubtle,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "متصل ✓",
                            color = SageGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                HorizontalDivider(color = WarmBorderSubtle)

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "نوبت فعال شما:",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CharcoalPrimary
                    )
                    Surface(
                        color = WarmSurfaceSecondary,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = salonSession.salonName,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = "زمان: ${salonSession.nextAppointmentDate}",
                                fontSize = 11.sp,
                                color = TerracottaAccent,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "خدمات: ${salonSession.serviceType}",
                                fontSize = 11.sp,
                                color = CharcoalSecondary
                            )
                            Text(
                                text = "متخصص: ${salonSession.beautyMasterName}",
                                fontSize = 10.sp,
                                color = CharcoalTertiary
                            )
                        }
                    }
                }

                Button(
                    onClick = { /* Open booking URL */ },
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("رزرو نوبت جدید در سالن‌یار", fontSize = 12.sp, color = Color.White)
                }
            }
        }

        // ============================================
        // 2. MARKETING ACADEMY BRIDGE (آکادمی مارکتینگ)
        // ============================================
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = WarmSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = SageGreenSubtle,
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = SageGreen, modifier = Modifier.padding(8.dp))
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "آکادمی مارکتینگ سیاوش",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                            Text(
                                text = "رصد لیدها و توزیع کارت‌های هوشمند",
                                fontSize = 10.sp,
                                color = CharcoalSecondary
                            )
                        }
                    }

                    Surface(
                        color = SageGreenSubtle,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "سطح نخبگان",
                            color = SageGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                HorizontalDivider(color = WarmBorderSubtle)

                // Stats Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("${marketingStats.totalClicks}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                        Text("کلیک روی کارت", fontSize = 10.sp, color = CharcoalSecondary)
                    }
                    VerticalDivider(modifier = Modifier.height(30.dp), color = WarmBorder)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("${marketingStats.registeredLeads}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SageGreen)
                        Text("لیدهای جذب‌شده", fontSize = 10.sp, color = CharcoalSecondary)
                    }
                    VerticalDivider(modifier = Modifier.height(30.dp), color = WarmBorder)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(marketingStats.conversionRate, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TerracottaAccent)
                        Text("نرخ تبدیل (CR)", fontSize = 10.sp, color = CharcoalSecondary)
                    }
                }

                Surface(
                    color = WarmSurfaceSecondary,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("درآمد و پاداش بازاریابی:", fontSize = 11.sp, color = CharcoalSecondary)
                        Text(marketingStats.commissionBalanceToman, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SageGreen)
                    }
                }
            }
        }

        // ============================================
        // 3. SAKHTEMANYAR SYNERGY (ساختمان‌یار)
        // ============================================
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(8.dp, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = WarmSurface),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = Color(0xFF1A2332).copy(alpha = 0.1f),
                            shape = CircleShape,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(Icons.Default.Apartment, contentDescription = null, tint = Color(0xFF1A2332), modifier = Modifier.padding(8.dp))
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "ساختمان‌یار (SakhtemanYar)",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                            Text(
                                text = "کارت تردد دیجیتال و مجوز ساکنان",
                                fontSize = 10.sp,
                                color = CharcoalSecondary
                            )
                        }
                    }

                    Surface(
                        color = SageGreenSubtle,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = sakhtemanPass.unitNumber,
                            color = SageGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                HorizontalDivider(color = WarmBorderSubtle)

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("مجتمع: ${sakhtemanPass.buildingName}", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = CharcoalPrimary)
                    Text("وضعیت شارژ: ${sakhtemanPass.maintenanceBalanceStatus}", fontSize = 11.sp, color = SageGreen)
                    Text("مجوز ورود: ${sakhtemanPass.smartDoorStatus}", fontSize = 11.sp, color = CharcoalSecondary)
                }

                // Smart Door Opener Button (NFC / QR Simulation)
                Button(
                    onClick = {
                        isDoorUnlocked = true
                        onSimulateDoorUnlock()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDoorUnlocked) SageGreen else Color(0xFF1A2332)
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (isDoorUnlocked) Icons.Default.LockOpen else Icons.Default.Key,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = if (isDoorUnlocked) "درب ورودی باز شد ✓" else "بازگشایی هوشمند درب با FaceCard",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
