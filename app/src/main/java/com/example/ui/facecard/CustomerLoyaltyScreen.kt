package com.example.ui.facecard

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LoyaltyStampCard
import com.example.model.LoyaltyTier
import com.example.ui.theme.*

@Composable
fun CustomerLoyaltyScreen(
    cards: List<LoyaltyStampCard>,
    onPunchStamp: (String) -> Unit
) {
    var selectedRewardCode by remember { mutableStateOf<String?>(null) }

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
                text = "باشگاه مشتریان و وفاداری",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = CharcoalPrimary
            )
            Text(
                text = "کارت‌های وفاداری دیجیتال (جایگزین کارت‌های پانچ کاغذی)",
                fontSize = 12.sp,
                color = CharcoalSecondary
            )
        }

        // Active Stamp Cards List
        for (card in cards) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, RoundedCornerShape(18.dp)),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = WarmSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Header of Card (Brand name + Tier Badge)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = card.businessName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                            Text(
                                text = card.serviceCategory,
                                fontSize = 11.sp,
                                color = CharcoalSecondary
                            )
                        }

                        Surface(
                            color = Color(card.tier.badgeColor).copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(card.tier.badgeColor))
                        ) {
                            Text(
                                text = card.tier.titleFa,
                                color = Color(card.tier.badgeColor),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    HorizontalDivider(color = WarmBorderSubtle)

                    // 10 Interactive Stamp Slots (Punch Grid)
                    Text(
                        text = "تمبرهای دریافتی (${card.filledStamps} از ${card.totalSlots}):",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = CharcoalPrimary
                    )

                    // Stamp Grid
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (i in 1..card.totalSlots) {
                            val isFilled = i <= card.filledStamps
                            val isGiftSlot = i == card.totalSlots

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                isGiftSlot && isFilled -> SageGreen
                                                isGiftSlot -> TerracottaAccent.copy(alpha = 0.25f)
                                                isFilled -> TerracottaAccent
                                                else -> WarmSurfaceSecondary
                                            }
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = if (isFilled) TerracottaAccent else WarmBorder,
                                            shape = CircleShape
                                        )
                                        .clickable { onPunchStamp(card.id) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isGiftSlot) {
                                        Icon(
                                            Icons.Default.CardGiftcard,
                                            contentDescription = "Reward",
                                            tint = if (isFilled) Color.White else TerracottaAccent,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    } else if (isFilled) {
                                        Icon(
                                            Icons.Default.Check,
                                            contentDescription = "Stamped",
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    } else {
                                        Text("$i", fontSize = 10.sp, color = CharcoalTertiary)
                                    }
                                }
                            }
                        }
                    }

                    // Reward Target Info & Action
                    Surface(
                        color = TerracottaSubtle,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Stars, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "جایزه وفاداری این دوره:",
                                    fontSize = 10.sp,
                                    color = CharcoalSecondary
                                )
                                Text(
                                    text = card.rewardTitle,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TerracottaHover
                                )
                            }
                            Button(
                                onClick = { onPunchStamp(card.id) },
                                colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text("+ ثبت تمبر", fontSize = 10.sp, color = Color.White)
                            }
                        }
                    }

                    // Balance Points & Voucher Redemption
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Diamond, contentDescription = null, tint = SageGreen, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "موجودی امتیاز: ${card.pointsBalance} XP",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SageGreen
                            )
                        }

                        TextButton(onClick = { selectedRewardCode = "GIFT-${card.id.takeLast(4)}-VIP" }) {
                            Text("دریافت کد تخفیف", fontSize = 11.sp, color = TerracottaAccent)
                        }
                    }
                }
            }
        }

        // Active Discount Vouchers List
        Text(
            text = "تخفیف‌های فعال و اختصاصی شما:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = CharcoalPrimary
        )

        val vouchers = listOf(
            Triple("تخفیف ۳۰٪ اولین نوبت سالن‌یار", "SY-FIRST30", "اعتبار تا پایان آبان"),
            Triple("۲۰٪ تخفیف خدمات رنگ و احیا", "COLOR-VIP20", "مخصوص اعضای طلایی"),
            Triple("ارسال رایگان محصولات آرایشی", "FREE-SHIP", "خرید بالای ۳۰۰ هزار تومان")
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            for ((title, code, exp) in vouchers) {
                Surface(
                    color = WarmSurface,
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                            Text(text = exp, fontSize = 10.sp, color = CharcoalSecondary)
                        }

                        Surface(
                            color = TerracottaSubtle,
                            shape = RoundedCornerShape(6.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaAccent)
                        ) {
                            Text(
                                text = code,
                                color = TerracottaAccent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }

        // Voucher Reveal Dialog
        if (selectedRewardCode != null) {
            AlertDialog(
                onDismissRequest = { selectedRewardCode = null },
                title = { Text("کد هدیه و تخفیف شما", color = CharcoalPrimary) },
                text = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("این کد را در صندوق فروشگاه یا هنگام رزرو نوبت ارائه دهید:", fontSize = 12.sp, color = CharcoalSecondary)
                        Surface(
                            color = WarmSurfaceSecondary,
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaAccent)
                        ) {
                            Text(
                                text = selectedRewardCode ?: "",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = TerracottaAccent,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { selectedRewardCode = null },
                        colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent)
                    ) {
                        Text("تایید و بستن", color = Color.White)
                    }
                }
            )
        }
    }
}
