package com.example.ui.admin

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.engine.AdminManagerRepository
import com.example.model.*
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    adminRepo: AdminManagerRepository,
    onNavigateBack: () -> Unit
) {
    BackHandler { onNavigateBack() }

    val context = LocalContext.current
    val isAuthenticated by adminRepo.isAdminAuthenticated.collectAsStateWithLifecycle()
    val contracts by adminRepo.contracts.collectAsStateWithLifecycle()
    val marketerWallets by adminRepo.marketerWallets.collectAsStateWithLifecycle()
    val generatedLicenses by adminRepo.generatedLicenses.collectAsStateWithLifecycle()

    var pinInput by remember { mutableStateOf("") }
    var pinError by remember { mutableStateOf(false) }

    var selectedAdminTab by remember { mutableStateOf(0) } // 0: Contracts, 1: Code Generator, 2: Wallets, 3: Analytics
    var showNewContractDialog by remember { mutableStateOf(false) }
    var showPayoutDialog by remember { mutableStateOf<MarketerWalletProfile?>(null) }
    var payoutAmountInput by remember { mutableStateOf("") }

    // New Contract Form State
    var selectedGuildType by remember { mutableStateOf(GuildBusinessType.SALON_YAR) }
    var newBusinessName by remember { mutableStateOf("") }
    var newManagerName by remember { mutableStateOf("") }
    var newPhone by remember { mutableStateOf("") }
    var newAddress by remember { mutableStateOf("") }
    var newMarketerName by remember { mutableStateOf("مهندس علی سهرابی") }
    var newAmountToman by remember { mutableStateOf("18000000") }
    var selectedSettlementStatus by remember { mutableStateOf(ContractSettlementStatus.DIGITAL_DIRECT_NO_CHEQUE) }

    // Code Generator Form State
    var genModuleType by remember { mutableStateOf(LicenseModuleType.FULL_SUITE_ANNUAL) }
    var genTargetBusiness by remember { mutableStateOf("") }
    var genMarketerName by remember { mutableStateOf("مهندس علی سهرابی") }
    var genValidityDays by remember { mutableStateOf(365) }

    var contractsGuildFilter by remember { mutableStateOf<GuildBusinessType?>(null) }

    fun copyToClipboard(text: String, label: String = "Code") {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "کد لایسنس کپی شد: $text", Toast.LENGTH_SHORT).show()
    }

    if (!isAuthenticated) {
        // ============================================================
        // 1. ISOLATED MASTER ADMIN PIN AUTHENTICATION GATE
        // ============================================================
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF141312)),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                color = Color(0xFF1E1C1A),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.5.dp, Color(0xFFD4AF37)),
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color(0x33D4AF37)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Security,
                            contentDescription = null,
                            tint = Color(0xFFFFD54F),
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Text(
                        text = "داشبورد انحصاری مدیریت کل (Admin)",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "این بخش کاملاً ایزوله و ویژه مدیر کل سیستم‌های اصناف (سالن‌یار، مزونیار، تالاریار و...) است.",
                        fontSize = 12.sp,
                        color = Color(0xFFBDBDBD),
                        textAlign = TextAlign.Center
                    )

                    OutlinedTextField(
                        value = pinInput,
                        onValueChange = {
                            if (it.length <= 6) {
                                pinInput = it
                                pinError = false
                            }
                        },
                        label = { Text("رمز امنیتی مدیر کل (PIN)", color = Color(0xFFFFD54F)) },
                        placeholder = { Text("کد پیش‌فرض: ۹۰۹۰", color = Color.Gray) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        isError = pinError,
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFD4AF37),
                            unfocusedBorderColor = Color.Gray,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (pinError) {
                        Text(
                            text = "رمز عبور نادرست است. (کد پیش‌فرض: ۹۰۹۰)",
                            color = Color(0xFFEF5350),
                            fontSize = 11.sp
                        )
                    }

                    Button(
                        onClick = {
                            val success = adminRepo.authenticateAdmin(pinInput)
                            if (!success) {
                                pinError = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD4AF37),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.LockOpen, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("ورود به پنل مدیریت", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }

                    TextButton(onClick = onNavigateBack) {
                        Text("بازگشت به برنامه اصلی", color = Color(0xFFE0E0E0), fontSize = 12.sp)
                    }
                }
            }
        }
    } else {
        // ============================================================
        // 2. AUTHENTICATED ADMIN DASHBOARD
        // ============================================================
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = Color(0xFFD4AF37), modifier = Modifier.size(20.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = "داشبورد مدیریت کل اصناف (Admin)",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalPrimary
                                )
                            }
                            Text(
                                text = "مدیریت قراردادهای ۱ ساله • سیستم صدور کد • پورسانت ویزیتورها",
                                fontSize = 10.sp,
                                color = CharcoalSecondary
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { adminRepo.lockAdmin() }) {
                            Icon(Icons.Default.Lock, contentDescription = "Lock", tint = TerracottaAccent)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = WarmSurface)
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(WarmBackground)
            ) {
                // Top Tab Selector
                TabRow(
                    selectedTabIndex = selectedAdminTab,
                    containerColor = WarmSurface,
                    contentColor = Color(0xFFD4AF37)
                ) {
                    val tabs = listOf(
                        "قراردادها" to Icons.Default.Description,
                        "تولید کد" to Icons.Default.VpnKey,
                        "کیف پول" to Icons.Default.AccountBalanceWallet,
                        "آمار مالی" to Icons.Default.Insights
                    )
                    tabs.forEachIndexed { index, (title, icon) ->
                        Tab(
                            selected = selectedAdminTab == index,
                            onClick = { selectedAdminTab = index },
                            text = { Text(title, fontSize = 11.sp, fontWeight = if (selectedAdminTab == index) FontWeight.Bold else FontWeight.Normal) },
                            icon = { Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp)) }
                        )
                    }
                }

                when (selectedAdminTab) {
                    0 -> {
                        // -------------------------------------------------------------
                        // TAB 1: GUILD CONTRACTS (قراردادهای ۱ ساله اصناف)
                        // -------------------------------------------------------------
                        val filteredContracts = contracts.filter {
                            contractsGuildFilter == null || it.guildType == contractsGuildFilter
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Register New Contract Action Bar
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "قراردادهای فعال ۱ ساله (${filteredContracts.size}):",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalPrimary
                                )

                                Button(
                                    onClick = { showNewContractDialog = true },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37), contentColor = Color.Black),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text("ثبت قرارداد ۱ ساله", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            // Guild Filter Chips
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                item {
                                    FilterChip(
                                        selected = contractsGuildFilter == null,
                                        onClick = { contractsGuildFilter = null },
                                        label = { Text("همه اصناف (${contracts.size})", fontSize = 10.sp) }
                                    )
                                }
                                items(GuildBusinessType.values()) { guild ->
                                    val count = contracts.count { it.guildType == guild }
                                    FilterChip(
                                        selected = contractsGuildFilter == guild,
                                        onClick = { contractsGuildFilter = guild },
                                        label = { Text("${guild.shortNameFa} ($count)", fontSize = 10.sp) }
                                    )
                                }
                            }

                            // Contracts List
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(filteredContracts) { contract ->
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = WarmSurface),
                                        shape = RoundedCornerShape(12.dp),
                                        border = BorderStroke(1.dp, WarmBorder),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(14.dp),
                                            verticalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = contract.businessName,
                                                    fontSize = 13.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = CharcoalPrimary
                                                )

                                                Surface(
                                                    color = Color(contract.settlementStatus.badgeColorHex).copy(alpha = 0.15f),
                                                    shape = RoundedCornerShape(6.dp),
                                                    border = BorderStroke(1.dp, Color(contract.settlementStatus.badgeColorHex))
                                                ) {
                                                    Text(
                                                        text = contract.settlementStatus.titleFa,
                                                        color = Color(contract.settlementStatus.badgeColorHex),
                                                        fontSize = 9.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }

                                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                                Text("مدیریت: ${contract.managerName}", fontSize = 11.sp, color = CharcoalSecondary)
                                                Text("•", fontSize = 11.sp, color = CharcoalTertiary)
                                                Text("تلفن: ${contract.phone}", fontSize = 11.sp, color = CharcoalSecondary)
                                            }

                                            Text("آدرس: ${contract.address}", fontSize = 10.sp, color = CharcoalTertiary)

                                            HorizontalDivider(color = WarmBorder, modifier = Modifier.padding(vertical = 4.dp))

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column {
                                                    Text("بازاریاب / ویزیتور ثبت‌کننده:", fontSize = 9.sp, color = CharcoalTertiary)
                                                    Text(contract.marketerName, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = TerracottaAccent)
                                                }
                                                Column(horizontalAlignment = Alignment.End) {
                                                    Text("مبلغ قرارداد (۱ ساله):", fontSize = 9.sp, color = CharcoalTertiary)
                                                    Text(
                                                        text = "${String.format("%,d", contract.totalAmountToman)} تومان",
                                                        fontSize = 11.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = CharcoalPrimary
                                                    )
                                                }
                                            }

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "سهم پورسانت بازاریاب: ${String.format("%,d", contract.marketerCommissionToman)} تومان",
                                                    fontSize = 10.sp,
                                                    color = SageGreen,
                                                    fontWeight = FontWeight.SemiBold
                                                )

                                                Text(
                                                    text = "اعتبار: ${contract.startDateShamsi} تا ${contract.expireDateShamsi}",
                                                    fontSize = 9.sp,
                                                    color = CharcoalTertiary
                                                )
                                            }

                                            // License code snippet
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Color(0xFF2A2724), RoundedCornerShape(6.dp))
                                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "کد لایسنس: ${contract.licenseKey}",
                                                    fontSize = 10.sp,
                                                    color = Color(0xFFFFD54F),
                                                    fontWeight = FontWeight.Medium
                                                )

                                                IconButton(
                                                    onClick = { copyToClipboard(contract.licenseKey, "License") },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = Color.White, modifier = Modifier.size(14.dp))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        // -------------------------------------------------------------
                        // TAB 2: CODE GENERATOR (سیستم تولید کدهای امنیتی و لایسنس)
                        // -------------------------------------------------------------
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Text(
                                text = "تولید کدهای اختصاصی برای بازاریابان و فعال‌سازی ماژول‌ها:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )

                            // Generator Card
                            Card(
                                colors = CardDefaults.cardColors(containerColor = WarmSurface),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.5.dp, Color(0xFFD4AF37)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Text("نوع ماژول یا لایسنس:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = CharcoalPrimary)
                                    LicenseModuleType.values().forEach { module ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable { genModuleType = module }
                                        ) {
                                            RadioButton(
                                                selected = genModuleType == module,
                                                onClick = { genModuleType = module },
                                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFD4AF37))
                                            )
                                            Column {
                                                Text(module.titleFa, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                                Text(module.descriptionFa, fontSize = 9.sp, color = CharcoalSecondary)
                                            }
                                        }
                                    }

                                    OutlinedTextField(
                                        value = genTargetBusiness,
                                        onValueChange = { genTargetBusiness = it },
                                        label = { Text("نام واحد صنفی مقصد") },
                                        placeholder = { Text("مثال: سالن زیبایی شاین") },
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    OutlinedTextField(
                                        value = genMarketerName,
                                        onValueChange = { genMarketerName = it },
                                        label = { Text("نام بازاریاب دریافت‌کننده") },
                                        singleLine = true,
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    Button(
                                        onClick = {
                                            val newCode = adminRepo.generateNewLicense(
                                                moduleType = genModuleType,
                                                targetBusinessName = genTargetBusiness,
                                                marketerName = genMarketerName,
                                                validityDays = genValidityDays
                                            )
                                            copyToClipboard(newCode.code, "New License")
                                            genTargetBusiness = ""
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37), contentColor = Color.Black),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(44.dp)
                                    ) {
                                        Icon(Icons.Default.VpnKey, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(Modifier.width(6.dp))
                                        Text("تولید و ثبت کد جدید (Generate)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    }
                                }
                            }

                            // Generated Licenses List
                            Text(
                                text = "کدهای امنیتی تولید شده (${generatedLicenses.size}):",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )

                            generatedLicenses.forEach { lic ->
                                Surface(
                                    color = Color(0xFF1E1C1A),
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, Color(0xFFD4AF37).copy(alpha = 0.6f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = lic.code,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFFFFD54F)
                                            )

                                            IconButton(
                                                onClick = { copyToClipboard(lic.code, "Code") },
                                                modifier = Modifier.size(28.dp)
                                            ) {
                                                Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = Color.White, modifier = Modifier.size(16.dp))
                                            }
                                        }

                                        Text(lic.moduleType.titleFa, fontSize = 10.sp, color = Color.White)
                                        Text("صنف: ${lic.targetBusinessName} • ویزیتور: ${lic.marketerName}", fontSize = 9.sp, color = Color(0xFFBDBDBD))
                                        Text("توکن امنیتی: ${lic.unlockSecretToken} • اعتبار: ۳۶۵ روز", fontSize = 8.sp, color = Color(0xFF9E9E9E))
                                    }
                                }
                            }
                        }
                    }

                    2 -> {
                        // -------------------------------------------------------------
                        // TAB 3: MARKETERS WALLET & COMMISSION (کیف پول و پورسانت بازاریابان)
                        // -------------------------------------------------------------
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "مدیریت مالی و سهم پورسانت ویزیتورهای میدانی:",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )

                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(marketerWallets) { marketer ->
                                    Card(
                                        colors = CardDefaults.cardColors(containerColor = WarmSurface),
                                        shape = RoundedCornerShape(12.dp),
                                        border = BorderStroke(1.dp, WarmBorder),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(14.dp),
                                            verticalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(Icons.Default.Person, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(20.dp))
                                                    Spacer(Modifier.width(6.dp))
                                                    Text(marketer.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                                }

                                                Surface(
                                                    color = TerracottaSubtle,
                                                    shape = RoundedCornerShape(6.dp)
                                                ) {
                                                    Text("کد: ${marketer.marketerCode}", fontSize = 9.sp, color = TerracottaAccent, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                                }
                                            }

                                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                                Text("تلفن: ${marketer.phone}", fontSize = 10.sp, color = CharcoalSecondary)
                                                Text("تعداد قراردادها: ${marketer.totalContractsSigned}", fontSize = 10.sp, color = CharcoalSecondary)
                                            }

                                            HorizontalDivider(color = WarmBorder, modifier = Modifier.padding(vertical = 4.dp))

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column {
                                                    Text("کل پورسانت کسب‌شده:", fontSize = 9.sp, color = CharcoalTertiary)
                                                    Text("${String.format("%,d", marketer.totalEarnedToman)} ت", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                                }
                                                Column {
                                                    Text("تسویه شده:", fontSize = 9.sp, color = CharcoalTertiary)
                                                    Text("${String.format("%,d", marketer.paidToman)} ت", fontSize = 11.sp, color = SageGreen)
                                                }
                                                Column(horizontalAlignment = Alignment.End) {
                                                    Text("مانده طلب:", fontSize = 9.sp, color = CharcoalTertiary)
                                                    Text("${String.format("%,d", marketer.pendingToman)} ت", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaAccent)
                                                }
                                            }

                                            if (marketer.pendingToman > 0) {
                                                Button(
                                                    onClick = {
                                                        showPayoutDialog = marketer
                                                        payoutAmountInput = marketer.pendingToman.toString()
                                                    },
                                                    colors = ButtonDefaults.buttonColors(containerColor = SageGreen, contentColor = Color.White),
                                                    shape = RoundedCornerShape(8.dp),
                                                    modifier = Modifier.fillMaxWidth().height(36.dp)
                                                ) {
                                                    Icon(Icons.Default.Payment, contentDescription = null, modifier = Modifier.size(14.dp))
                                                    Spacer(Modifier.width(6.dp))
                                                    Text("تسویه حساب پورسانت (واریز)", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    3 -> {
                        // -------------------------------------------------------------
                        // TAB 4: FINANCIAL & ECOSYSTEM ANALYTICS (بیلان مالی کل)
                        // -------------------------------------------------------------
                        val totalContractVolumeToman = contracts.sumOf { it.totalAmountToman }
                        val totalCommissionPaidToman = contracts.sumOf { it.marketerCommissionToman }
                        val netSystemIncomeToman = totalContractVolumeToman - totalCommissionPaidToman

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Text("گزارش تجمیعی مالی اکوسیستم شهر توانا:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)

                            // 3 Financial Metric Cards
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    color = Color(0xFF1E1C1A),
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(1.dp, Color(0xFFD4AF37)),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("کل فروش قراردادها:", fontSize = 9.sp, color = Color(0xFFBDBDBD))
                                        Text("${String.format("%,d", totalContractVolumeToman)}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFFD54F))
                                        Text("تومان", fontSize = 9.sp, color = Color.White)
                                    }
                                }

                                Surface(
                                    color = Color(0xFF1E1C1A),
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(1.dp, SageGreen),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("سهم پورسانت ویزیتور:", fontSize = 9.sp, color = Color(0xFFBDBDBD))
                                        Text("${String.format("%,d", totalCommissionPaidToman)}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SageGreen)
                                        Text("تومان", fontSize = 9.sp, color = Color.White)
                                    }
                                }
                            }

                            Surface(
                                color = WarmSurface,
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, WarmBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text("سود خالص سیستم (بدون چک):", fontSize = 11.sp, color = CharcoalSecondary)
                                    Text("${String.format("%,d", netSystemIncomeToman)} تومان", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                    Text("تسویه تمام قراردادها به صورت نقدی آنی یا پایانه دیجیتال بدون ریسک برگشت چک انجام شده است.", fontSize = 10.sp, color = CharcoalTertiary)
                                }
                            }

                            // Breakdown by Guild
                            Text("تفکیک قراردادها بر اساس صنف:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                            GuildBusinessType.values().forEach { guild ->
                                val count = contracts.count { it.guildType == guild }
                                val amount = contracts.filter { it.guildType == guild }.sumOf { it.totalAmountToman }
                                Surface(
                                    color = WarmSurface,
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, WarmBorder),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(10.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(guild.shortNameFa, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = CharcoalPrimary)
                                        Text("$count قرارداد • ${String.format("%,d", amount)} تومان", fontSize = 10.sp, color = CharcoalSecondary)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // ============================================================
            // DIALOG: REGISTER NEW 1-YEAR CONTRACT
            // ============================================================
            if (showNewContractDialog) {
                AlertDialog(
                    onDismissRequest = { showNewContractDialog = false },
                    title = { Text("ثبت قرارداد ۱ ساله صنف جدید", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary) },
                    text = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("نوع صنف:", fontSize = 10.sp, color = CharcoalTertiary)
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                items(GuildBusinessType.values()) { guild ->
                                    val isSel = selectedGuildType == guild
                                    Surface(
                                        color = if (isSel) Color(0xFFD4AF37) else WarmSurfaceSecondary,
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.clickable {
                                            selectedGuildType = guild
                                            newAmountToman = guild.annualBaseFeeToman.toString()
                                        }
                                    ) {
                                        Text(guild.shortNameFa, fontSize = 9.sp, fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal, modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp))
                                    }
                                }
                            }

                            OutlinedTextField(value = newBusinessName, onValueChange = { newBusinessName = it }, label = { Text("نام واحد صنفی") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = newManagerName, onValueChange = { newManagerName = it }, label = { Text("نام مدیریت") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = newPhone, onValueChange = { newPhone = it }, label = { Text("شماره تماس") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                            OutlinedTextField(value = newAddress, onValueChange = { newAddress = it }, label = { Text("آدرس واحد صنفی") }, singleLine = true, modifier = Modifier.fillMaxWidth())

                            Text("بازاریاب ثبت‌کننده:", fontSize = 10.sp, color = CharcoalTertiary)
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                items(marketerWallets) { marketer ->
                                    val isSel = newMarketerName == marketer.name
                                    Surface(
                                        color = if (isSel) TerracottaAccent else WarmSurfaceSecondary,
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.clickable { newMarketerName = marketer.name }
                                    ) {
                                        Text(marketer.name, color = if (isSel) Color.White else CharcoalPrimary, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp))
                                    }
                                }
                            }

                            OutlinedTextField(
                                value = newAmountToman,
                                onValueChange = { newAmountToman = it },
                                label = { Text("مبلغ قرارداد (تومان)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Text("نوع تسویه بدون چک:", fontSize = 10.sp, color = CharcoalTertiary)
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                listOf(ContractSettlementStatus.DIGITAL_DIRECT_NO_CHEQUE to "دیجیتال مستقیم", ContractSettlementStatus.CASH_SETTLED to "نقدی آنی").forEach { (st, label) ->
                                    val isSel = selectedSettlementStatus == st
                                    Surface(
                                        color = if (isSel) SageGreen else WarmSurfaceSecondary,
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.clickable { selectedSettlementStatus = st }
                                    ) {
                                        Text(label, color = if (isSel) Color.White else CharcoalPrimary, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp))
                                    }
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                val amount = newAmountToman.toLongOrNull() ?: 18_000_000L
                                adminRepo.registerNewContract(
                                    guildType = selectedGuildType,
                                    businessName = newBusinessName.ifBlank { "واحد صنفی جدید" },
                                    managerName = newManagerName.ifBlank { "مدیریت" },
                                    phone = newPhone.ifBlank { "۰۲۱" },
                                    address = newAddress.ifBlank { "تهران" },
                                    marketerName = newMarketerName,
                                    amountToman = amount,
                                    settlementStatus = selectedSettlementStatus
                                )
                                showNewContractDialog = false
                                newBusinessName = ""
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37), contentColor = Color.Black)
                        ) {
                            Text("ثبت و صدور قرارداد", fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showNewContractDialog = false }) {
                            Text("انصراف")
                        }
                    }
                )
            }

            // ============================================================
            // DIALOG: SETTLE MARKETER PAYOUT
            // ============================================================
            if (showPayoutDialog != null) {
                val marketer = showPayoutDialog!!
                AlertDialog(
                    onDismissRequest = { showPayoutDialog = null },
                    title = { Text("تسویه حساب پورسانت بازاریاب", fontSize = 14.sp, fontWeight = FontWeight.Bold) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("نام بازاریاب: ${marketer.name}", fontSize = 11.sp, color = CharcoalPrimary)
                            Text("مانده طلب پورسانت: ${String.format("%,d", marketer.pendingToman)} تومان", fontSize = 11.sp, color = TerracottaAccent)

                            OutlinedTextField(
                                value = payoutAmountInput,
                                onValueChange = { payoutAmountInput = it },
                                label = { Text("مبلغ واریز به حساب (تومان)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                val amount = payoutAmountInput.toLongOrNull() ?: 0L
                                adminRepo.settleMarketerPayout(marketer.id, amount)
                                showPayoutDialog = null
                                Toast.makeText(context, "تسویه پورسانت با موفقیت ثبت شد ✓", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SageGreen)
                        ) {
                            Text("تایید واریز پورسانت")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showPayoutDialog = null }) {
                            Text("انصراف")
                        }
                    }
                )
            }
        }
    }
}
