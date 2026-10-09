package com.example.ui.maison

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.engine.MaisonBoutiqueRepository
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun MaisonBoutiqueHubView(
    maisonRepo: MaisonBoutiqueRepository,
    onNavigateToStudio: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val maisons by maisonRepo.maisons.collectAsStateWithLifecycle()
    val garments by maisonRepo.garments.collectAsStateWithLifecycle()
    val activeGarment by maisonRepo.activeGarment.collectAsStateWithLifecycle()
    val vouchers by maisonRepo.referralVouchers.collectAsStateWithLifecycle()
    val stats by maisonRepo.visitorStats.collectAsStateWithLifecycle()

    var activeSubSection by remember { mutableStateOf("fitting_room") } // "fitting_room", "referral_loop", "visitor_field"

    var selectedCategoryFilter by remember { mutableStateOf("همه") } // "همه", "لباس عروس VIP", "کت‌وشلوار و تاکسیدو داماد", "لباس فرمالیته و شب"

    // Dialog State for New Voucher
    var showVoucherDialog by remember { mutableStateOf(false) }
    var clientNameInput by remember { mutableStateOf("") }
    var referringSalonInput by remember { mutableStateOf("سالن زیبایی VIP مونا") }
    var newlyCreatedVoucher by remember { mutableStateOf<ReferralVoucher?>(null) }

    // Dialog State for Visitor Onboarding New Maison
    var showNewMaisonDialog by remember { mutableStateOf(false) }
    var newMaisonName by remember { mutableStateOf("") }
    var newMaisonManager by remember { mutableStateOf("") }
    var newMaisonPhone by remember { mutableStateOf("") }
    var newMaisonAddress by remember { mutableStateOf("") }
    var newMaisonLicense by remember { mutableStateOf("") }
    var newMaisonPlan by remember { mutableStateOf(VisitorContractPlan.PLAN_A_FULL_SUITE) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(WarmSurface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
                    modifier = Modifier.size(42.dp)
                ) {
                    Icon(
                        Icons.Default.Checkroom,
                        contentDescription = null,
                        tint = TerracottaAccent,
                        modifier = Modifier.padding(10.dp)
                    )
                }
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        text = "شبکه مزون‌های لباس عروس و داماد",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = CharcoalPrimary
                    )
                    Text(
                        text = "پرو دیجیتال سلبریتی + ویترین اشتراکی + چرخه ارجاع سالن و مزون",
                        fontSize = 11.sp,
                        color = CharcoalSecondary
                    )
                }
            }

            Surface(
                color = TerracottaAccent,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "${garments.size} لباس آماده پرو",
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }

        // Sub-Navigation Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(WarmSurfaceSecondary, RoundedCornerShape(12.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val tabs = listOf(
                "fitting_room" to "اتاق پرو دیجیتال",
                "referral_loop" to "چرخه ارجاع و نوبت",
                "visitor_field" to "پورتال ویزیتور و پلن‌ها"
            )

            tabs.forEach { (key, label) ->
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
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        modifier = Modifier.padding(vertical = 8.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        when (activeSubSection) {
            // ============================================
            // 1. DIGITAL FITTING ROOM & REAL MAISON GARMENTS
            // ============================================
            "fitting_room" -> {
                // Category Filter Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val categories = listOf("همه", "لباس عروس VIP", "کت‌وشلوار و تاکسیدو داماد", "لباس فرمالیته و شب")
                    categories.forEach { cat ->
                        val isSelected = selectedCategoryFilter == cat
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedCategoryFilter = cat },
                            label = { Text(cat, fontSize = 10.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = TerracottaAccent,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }

                val filteredGarments = if (selectedCategoryFilter == "همه") {
                    garments
                } else {
                    garments.filter { it.category == selectedCategoryFilter }
                }

                // Garment Cards List
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    filteredGarments.forEach { garment ->
                        val isSelected = activeGarment?.id == garment.id
                        Card(
                            colors = CardDefaults.cardColors(containerColor = if (isSelected) TerracottaSubtle else WarmSurfaceSecondary),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, if (isSelected) TerracottaAccent else WarmBorder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { maisonRepo.selectGarment(garment.id) }
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Surface(
                                        color = TerracottaAccent.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = garment.category,
                                            color = TerracottaAccent,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }

                                    Surface(
                                        color = SageGreenSubtle,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "پورسانت سالن: ${garment.commissionForSalonToman}",
                                            color = SageGreen,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = garment.titleFa,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalPrimary
                                )

                                Surface(
                                    color = WarmSurface,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                        Text(
                                            text = "🏛️ ${garment.maisonName} (پروانه: ${garment.licenseCode})",
                                            fontSize = 10.sp,
                                            color = CharcoalPrimary,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = "📍 ${garment.maisonAddress} • 📞 ${garment.maisonPhone}",
                                            fontSize = 10.sp,
                                            color = CharcoalSecondary
                                        )
                                        Text(
                                            text = "⭐ ${garment.celebrityFitStyle}",
                                            fontSize = 10.sp,
                                            color = TerracottaAccent,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = "🧵 ${garment.fabricAndCut}",
                                            fontSize = 9.sp,
                                            color = CharcoalSecondary
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(text = "کرایه: ${garment.rentalPriceToman}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaAccent)
                                        Text(text = "خرید: ${garment.purchasePriceToman}", fontSize = 9.sp, color = CharcoalSecondary)
                                    }

                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                        OutlinedButton(
                                            onClick = onNavigateToStudio,
                                            border = BorderStroke(1.dp, TerracottaAccent),
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Icon(Icons.Default.CameraAlt, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(14.dp))
                                            Spacer(Modifier.width(4.dp))
                                            Text("پرو در دوربین", fontSize = 10.sp, color = TerracottaAccent)
                                        }

                                        Button(
                                            onClick = {
                                                maisonRepo.selectGarment(garment.id)
                                                showVoucherDialog = true
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                        ) {
                                            Icon(Icons.Default.ConfirmationNumber, contentDescription = null, modifier = Modifier.size(14.dp))
                                            Spacer(Modifier.width(4.dp))
                                            Text("معرفی‌نامه مزون", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ============================================
            // 2. CROSS-REFERRAL CYCLE & VOUCHERS
            // ============================================
            "referral_loop" -> {
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
                                text = "چرخه ارجاع خودکار (سالن ⟷ مزون)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                            Icon(Icons.Default.SyncAlt, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(20.dp))
                        }
                        Text(
                            text = "وقتی عروس در سالن زیبایی لباس مزون را پرو و انتخاب کرد، سیستم مستقیماً مسیر مراجعه به مزون را با تخفیف ۱۰٪ هموار می‌کند و از آن طرف، مزون تمام مشتریان عروس خود را برای شینیون و میکاپ به سالن شما ارجاع می‌دهد.",
                            fontSize = 11.sp,
                            color = CharcoalSecondary,
                            lineHeight = 16.sp
                        )
                    }
                }

                // Vouchers Issued List
                Text(
                    text = "معرفی‌نامه‌ها و نوبت‌های صادرشده اخیر:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = CharcoalPrimary
                )

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    vouchers.forEach { voucher ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = voucher.clientName,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CharcoalPrimary
                                    )
                                    Surface(
                                        color = SageGreenSubtle,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = voucher.status,
                                            color = SageGreen,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = "👗 ${voucher.garmentTitle}",
                                    fontSize = 11.sp,
                                    color = CharcoalPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "🏛️ مقصد: ${voucher.maisonName} (تلفن: ${voucher.maisonPhone})",
                                    fontSize = 10.sp,
                                    color = CharcoalSecondary
                                )
                                Text(
                                    text = "📍 آدرس: ${voucher.maisonAddress}",
                                    fontSize = 10.sp,
                                    color = CharcoalSecondary
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "کد تخفیف ۱۰٪: ${voucher.referralCode}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TerracottaAccent
                                    )

                                    IconButton(
                                        onClick = { maisonRepo.shareVoucherViaIntent(voucher) },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(Icons.Default.Share, contentDescription = "Share", tint = TerracottaAccent, modifier = Modifier.size(18.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ============================================
            // 3. VISITOR FIELD AGENT PORTAL & 3 PLANS
            // ============================================
            "visitor_field" -> {
                Card(
                    colors = CardDefaults.cardColors(containerColor = TerracottaSubtle),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, TerracottaAccent.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "اسکریپت ویزیتورهای میدانی شهر توانا",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = TerracottaAccent
                            )
                            Icon(Icons.Default.SupportAgent, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(20.dp))
                        }
                        Text(
                            text = "«سلام! من نیامدم چیزی به شما بفروشم که برایتان هزینه داشته باشد؛ من آمده‌ام یک کانال درآمدی جدید و یک جریان دائمی مشتری به کسب‌وکار شما تزریق کنم.»",
                            fontSize = 11.sp,
                            color = CharcoalPrimary,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 16.sp
                        )
                    }
                }

                // 3 Plans Breakdown
                Text(
                    text = "سه لایه نفوذ ویزیتور در صورت مقاومت کاسب:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = CharcoalPrimary
                )

                VisitorContractPlan.values().forEach { plan ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, WarmBorder)
                    ) {
                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = plan.titleFa, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                Surface(
                                    color = TerracottaAccent,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(text = plan.badgeFa, color = Color.White, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                }
                            }
                            Text(text = plan.subtitleFa, fontSize = 10.sp, color = TerracottaAccent, fontWeight = FontWeight.Medium)
                            Text(text = plan.descriptionFa, fontSize = 10.sp, color = CharcoalSecondary, lineHeight = 15.sp)
                        }
                    }
                }

                // Onboarding Action Button
                Button(
                    onClick = { showNewMaisonDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.AddBusiness, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("ثبت مزون/بوتیک جدید در میدان (ویزیتور)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                // Partner Maisons Overview
                Text(
                    text = "مزون‌های طرف قرارداد در شبکه (${maisons.size} مزون):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = CharcoalPrimary
                )

                maisons.forEach { m ->
                    Surface(
                        color = WarmSurfaceSecondary,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = m.name, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                Text(text = "مدیریت: ${m.managerName} • ${m.cityDistrict}", fontSize = 10.sp, color = CharcoalSecondary)
                                Text(text = "پروانه: ${m.licenseNumber}", fontSize = 9.sp, color = CharcoalSecondary)
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Surface(
                                    color = SageGreenSubtle,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(text = "${m.totalReferralsReceived} ارجاع عروس", color = SageGreen, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                }
                                Spacer(Modifier.height(2.dp))
                                Text(text = m.totalCommissionEarnedToman, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TerracottaAccent)
                            }
                        }
                    }
                }
            }
        }
    }

    // ============================================
    // DIALOG 1: GENERATE REFERRAL VOUCHER
    // ============================================
    if (showVoucherDialog && activeGarment != null) {
        val garment = activeGarment!!
        AlertDialog(
            onDismissRequest = { showVoucherDialog = false },
            title = {
                Text(
                    text = "صدور معرفی‌نامه پرو اختصاصی به مزون",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = CharcoalPrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "لباس انتخابی: ${garment.titleFa}",
                        fontSize = 11.sp,
                        color = TerracottaAccent,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "مزون مقصد: ${garment.maisonName}\nآدرس: ${garment.maisonAddress}",
                        fontSize = 10.sp,
                        color = CharcoalSecondary
                    )

                    OutlinedTextField(
                        value = clientNameInput,
                        onValueChange = { clientNameInput = it },
                        label = { Text("نام مشتری / عروس / داماد") },
                        placeholder = { Text("مثال: خانم الهام تهرانی") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = referringSalonInput,
                        onValueChange = { referringSalonInput = it },
                        label = { Text("نام سالن زیبایی صادرکننده") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Surface(
                        color = SageGreenSubtle,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "تخفیف ۱۰٪ نقدی + حق ارجاع پورسانت برای سالن ثبت خواهد شد.",
                            fontSize = 10.sp,
                            color = SageGreen,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val voucher = maisonRepo.generateReferralVoucher(
                            clientName = clientNameInput,
                            garment = garment,
                            salonName = referringSalonInput
                        )
                        newlyCreatedVoucher = voucher
                        showVoucherDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent)
                ) {
                    Text("صدور و ثبت معرفی‌نامه")
                }
            },
            dismissButton = {
                TextButton(onClick = { showVoucherDialog = false }) {
                    Text("انصراف", color = CharcoalSecondary)
                }
            }
        )
    }

    // ============================================
    // DIALOG 2: ONBOARD NEW MAISON (FIELD VISITOR)
    // ============================================
    if (showNewMaisonDialog) {
        AlertDialog(
            onDismissRequest = { showNewMaisonDialog = false },
            title = {
                Text(
                    text = "ثبت مزون/بوتیک جدید توسط ویزیتور",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = CharcoalPrimary
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    OutlinedTextField(
                        value = newMaisonName,
                        onValueChange = { newMaisonName = it },
                        label = { Text("نام مزون یا بوتیک") },
                        placeholder = { Text("مثال: مزون پرنسس پالاس") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newMaisonManager,
                        onValueChange = { newMaisonManager = it },
                        label = { Text("نام مدیریت") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newMaisonPhone,
                        onValueChange = { newMaisonPhone = it },
                        label = { Text("شماره تماس ثابت/همراه") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newMaisonAddress,
                        onValueChange = { newMaisonAddress = it },
                        label = { Text("آدرس دقیق مزون") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newMaisonLicense,
                        onValueChange = { newMaisonLicense = it },
                        label = { Text("شماره پروانه صنفی / شناسه") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(text = "پلن قرارداد ویزیتور:", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    VisitorContractPlan.values().forEach { p ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { newMaisonPlan = p }
                        ) {
                            RadioButton(selected = newMaisonPlan == p, onClick = { newMaisonPlan = p })
                            Spacer(Modifier.width(4.dp))
                            Text(text = p.titleFa, fontSize = 10.sp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newMaisonName.isNotBlank()) {
                            val newProfile = MaisonBoutiqueProfile(
                                id = "maison-${System.currentTimeMillis()}",
                                name = newMaisonName,
                                managerName = newMaisonManager.ifBlank { "مدیریت مزون" },
                                phone = newMaisonPhone.ifBlank { "۰۲۱-۸۸۰۰۰۰۰۰" },
                                address = newMaisonAddress.ifBlank { "تهران" },
                                cityDistrict = "ثبت میدانی ویزیتور",
                                licenseNumber = newMaisonLicense.ifBlank { "در حال استعلام صنفی" },
                                plan = newMaisonPlan,
                                isHomeBoutique = newMaisonPlan == VisitorContractPlan.PLAN_C_HOME_STUDIO
                            )
                            maisonRepo.registerNewMaison(newProfile)

                            // Add a sample garment for this new maison automatically
                            maisonRepo.addNewGarment(
                                MaisonGarmentItem(
                                    id = "g-${System.currentTimeMillis()}",
                                    maisonId = newProfile.id,
                                    maisonName = newProfile.name,
                                    maisonAddress = newProfile.address,
                                    maisonPhone = newProfile.phone,
                                    licenseCode = newProfile.licenseNumber,
                                    titleFa = "کالکشن جدید اختصاصی ${newProfile.name}",
                                    category = "لباس عروس VIP",
                                    fabricAndCut = "طراحی اختصاصی مزون با دانتل و تور شاین ترکیه",
                                    rentalPriceToman = "۱۵,۰۰۰,۰۰۰ تومان",
                                    purchasePriceToman = "۴۵,۰۰۰,۰۰۰ تومان",
                                    celebrityFitStyle = "تن‌خور ژورنالی اروپایی",
                                    commissionForSalonToman = "۲,۰۰۰,۰۰۰ تومان"
                                )
                            )

                            showNewMaisonDialog = false
                            newMaisonName = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent)
                ) {
                    Text("ثبت و پیوست به اکوسیستم")
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewMaisonDialog = false }) {
                    Text("انصراف", color = CharcoalSecondary)
                }
            }
        )
    }
}
