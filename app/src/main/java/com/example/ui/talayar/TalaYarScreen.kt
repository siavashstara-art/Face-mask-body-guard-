package com.example.ui.talayar

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

data class BridalJewelryItem(
    val id: String,
    val title: String,
    val category: String, // "سرویس طلا", "النگو و دستبند", "حلقه ازدواج", "نقره و آینه‌شمعدان", "شمش و سکه"
    val weightGrams: Double,
    val wagePercent: Double,
    val purityKarat: Int = 18,
    val description: String,
    val isLuxuryBridal: Boolean = false
)

val defaultJewelryCatalog = listOf(
    BridalJewelryItem(
        id = "gold_set_royal",
        title = "سرویس طلای سلطنتی برلیان نوعروس",
        category = "سرویس طلا",
        weightGrams = 28.5,
        wagePercent = 14.0,
        purityKarat = 18,
        description = "سرویس کامل شامل گردنبند، دستبند و گوشواره تراش‌کاری ظریف بدون نگین اتمی، درخشش فوق‌العاده در تالار",
        isLuxuryBridal = true
    ),
    BridalJewelryItem(
        id = "gold_ring_solitaire",
        title = "حلقه ست ازدواج و سولیتر باگت",
        category = "حلقه ازدواج",
        weightGrams = 9.2,
        wagePercent = 12.0,
        purityKarat = 18,
        description = "حلقه زنانه و مردانه با حکاکی تاریخ پیوند، طلای زرد و سفید با آبکاری رادیوم درجه یک",
        isLuxuryBridal = true
    ),
    BridalJewelryItem(
        id = "silver_mirror_candelabra",
        title = "آینه و شمعدان نقره قلم‌زنی دست‌ساز شیراز",
        category = "نقره و آینه‌شمعدان",
        weightGrams = 1850.0,
        wagePercent = 22.0,
        purityKarat = 84, // 840 silver standard
        description = "آینه و شمعدان سنتی سفره عقد با عیار ۸۴۰ نقره خالص، نقوش اسلیمی برجسته ماندگار برای جهیزیه",
        isLuxuryBridal = true
    ),
    BridalJewelryItem(
        id = "gold_bangles_fusion",
        title = "تک‌پوش و النگوی دامله فیوژن تراش",
        category = "النگو و دستبند",
        weightGrams = 14.8,
        wagePercent = 9.5,
        purityKarat = 18,
        description = "النگوی بدون جوش ریخته‌گری مدرن، مقاومت مکانیکی بالا و ضد خمش",
        isLuxuryBridal = false
    ),
    BridalJewelryItem(
        id = "gold_bullion_swiss",
        title = "شمش ۵۰ گرمی سوئیسی پمپ (PAMP) با شناسنامه",
        category = "شمش و سکه",
        weightGrams = 50.0,
        wagePercent = 2.5,
        purityKarat = 24,
        description = "شمش ۹۹۹.۹ سرمایه‌گذاری با بسته‌بندی هولوگرام‌دار و سرتیفیکیت معتبر بین‌المللی",
        isLuxuryBridal = false
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TalaYarScreen(
    isPersian: Boolean = true,
    onNavigateBack: () -> Unit,
    onNavigateToStudio: () -> Unit
) {
    BackHandler { onNavigateBack() }

    var selectedTab by remember { mutableIntStateOf(0) } // 0: ماشین حساب زنده, 1: کاتالوگ طلا و نقره, 2: تعویض طلا (Trade-in), 3: شناسنامه QR
    
    // Live Gold Pricing States
    var gramPriceInput by remember { mutableStateOf("48500000") } // Rials per gram (~4.85m Tomans)
    var weightInput by remember { mutableStateOf("18.5") }
    var wagePercentInput by remember { mutableStateOf("12") }
    var profitPercentInput by remember { mutableStateOf("7") }
    var taxOnWageOnly by remember { mutableStateOf(true) } // Iranian legal formula: tax only on wage + profit

    // Trade-in States
    var oldGoldWeight by remember { mutableStateOf("12.0") }
    var newGoldWeight by remember { mutableStateOf("22.0") }
    var newGoldWage by remember { mutableStateOf("14") }

    val formatter = remember { NumberFormat.getNumberInstance(Locale.US) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (isPersian) "طلایار: سامانه طلا و جواهرات عروس" else "TalaYar Gold Assistant",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = CharcoalPrimary
                        )
                        Text(
                            text = if (isPersian) "محاسبه‌گر اتحادیه، تعویض طلا و آینه و شمعدان نقره" else "Official Gold Formula & Trade-In",
                            fontSize = 11.sp,
                            color = CharcoalSecondary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack, modifier = Modifier.testTag("btn_back_talayar")) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت", tint = CharcoalPrimary)
                    }
                },
                actions = {
                    Surface(
                        color = Color(0xFFFFD700).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFFD4AF37))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = Color(0xFFB8860B), modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("۱۸ عیار رسمی", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8B6508))
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = WarmSurface)
            )
        },
        containerColor = WarmBackground
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                // Gold Hero Banner
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmSurface),
                    border = BorderStroke(1.dp, Color(0xFFD4AF37).copy(alpha = 0.4f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFFFF9E6),
                                        Color(0xFFFFF1C6),
                                        Color(0xFFFFE8A3)
                                    )
                                )
                            )
                            .padding(16.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFFD4AF37),
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.Diamond, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                                    }
                                }
                                Spacer(Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "ویترین طلای عروس و اکسسوری پیوند",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = Color(0xFF5A4300)
                                    )
                                    Text(
                                        text = "متصل به اکوسیستم تشریفات: هم‌پوشانی خرید حلقه و طلا با آتلیه و سالن",
                                        fontSize = 11.sp,
                                        color = Color(0xFF7A5C00)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                // Tab Switcher
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = WarmSurface,
                    contentColor = CharcoalPrimary,
                    edgePadding = 0.dp
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("ماشین‌حساب طلا", fontSize = 12.sp, fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) },
                        icon = { Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(18.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("ژورنال طلا و نقره", fontSize = 12.sp, fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) },
                        icon = { Icon(Icons.Default.WorkspacePremium, contentDescription = null, modifier = Modifier.size(18.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("تعویض طلا", fontSize = 12.sp, fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal) },
                        icon = { Icon(Icons.Default.CurrencyExchange, contentDescription = null, modifier = Modifier.size(18.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        text = { Text("شناسنامه QR", fontSize = 12.sp, fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal) },
                        icon = { Icon(Icons.Default.QrCode, contentDescription = null, modifier = Modifier.size(18.dp)) }
                    )
                }
            }

            when (selectedTab) {
                0 -> {
                    // TAB 0: LIVE GOLD PRICING
                    item {
                        val gramPrice = gramPriceInput.toDoubleOrNull() ?: 48500000.0
                        val weight = weightInput.toDoubleOrNull() ?: 1.0
                        val wagePct = wagePercentInput.toDoubleOrNull() ?: 0.0
                        val profitPct = profitPercentInput.toDoubleOrNull() ?: 7.0

                        // Calculations according to Iranian Gold Union Law
                        val rawGoldValue = gramPrice * weight
                        val wageAmount = rawGoldValue * (wagePct / 100.0)
                        val totalBeforeProfit = rawGoldValue + wageAmount
                        val profitAmount = totalBeforeProfit * (profitPct / 100.0)
                        
                        // New Law: 9% Tax is applied ONLY to (Wage + Profit), not raw gold!
                        val taxBase = wageAmount + profitAmount
                        val taxAmount = taxBase * 0.09
                        val finalPriceRials = rawGoldValue + wageAmount + profitAmount + taxAmount
                        val finalPriceTomans = (finalPriceRials / 10.0).toLong()

                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurface),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(
                                    text = "فرمول رسمی اتحادیه طلا و جواهر (قانون مالیات جدید)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = "طبق قانون جدید، ۹٪ مالیات فقط به اجرت ساخت و سود طلافروش تعلق می‌گیرد و اصل طلای خام از مالیات معاف است.",
                                    fontSize = 11.sp,
                                    color = CharcoalSecondary
                                )

                                OutlinedTextField(
                                    value = gramPriceInput,
                                    onValueChange = { gramPriceInput = it },
                                    label = { Text("قیمت هر گرم طلای ۱۸ عیار خام (ریال)") },
                                    modifier = Modifier.fillMaxWidth().testTag("input_gram_price"),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    shape = RoundedCornerShape(8.dp)
                                )

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = weightInput,
                                        onValueChange = { weightInput = it },
                                        label = { Text("وزن طلا (گرم)") },
                                        modifier = Modifier.weight(1f).testTag("input_weight"),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    OutlinedTextField(
                                        value = wagePercentInput,
                                        onValueChange = { wagePercentInput = it },
                                        label = { Text("اجرت ساخت (%)") },
                                        modifier = Modifier.weight(1f).testTag("input_wage"),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                }

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = profitPercentInput,
                                        onValueChange = { profitPercentInput = it },
                                        label = { Text("سود طلافروش (%)") },
                                        modifier = Modifier.weight(1f),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    Surface(
                                        modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
                                        color = SageGreenSubtle,
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text(
                                            text = "مالیات: ۹٪ روی سود+اجرت",
                                            fontSize = 10.sp,
                                            color = SageGreen,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(12.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }

                                Divider(color = WarmBorder)

                                // Price Breakdown
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("ارزش طلای خام:", fontSize = 11.sp, color = CharcoalSecondary)
                                        Text("${formatter.format((rawGoldValue / 10).toLong())} تومان", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("اجرت ساخت (${wagePct}٪):", fontSize = 11.sp, color = CharcoalSecondary)
                                        Text("${formatter.format((wageAmount / 10).toLong())} تومان", fontSize = 11.sp, color = CharcoalPrimary)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("سود فروشنده (${profitPct}٪):", fontSize = 11.sp, color = CharcoalSecondary)
                                        Text("${formatter.format((profitAmount / 10).toLong())} تومان", fontSize = 11.sp, color = CharcoalPrimary)
                                    }
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("مالیات بر ارزش افزوده (۹٪):", fontSize = 11.sp, color = CharcoalSecondary)
                                        Text("${formatter.format((taxAmount / 10).toLong())} تومان", fontSize = 11.sp, color = CharcoalPrimary)
                                    }
                                }

                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = Color(0xFFFFF8E1),
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, Color(0xFFFFD54F))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(14.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("قیمت نهایی پرداخت مشتری", fontSize = 11.sp, color = Color(0xFF6D4C41))
                                        Text(
                                            text = "${formatter.format(finalPriceTomans)} تومان",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFB78103)
                                        )
                                        Text(
                                            text = "(معادل ${formatter.format(finalPriceRials.toLong())} ریال)",
                                            fontSize = 10.sp,
                                            color = Color(0xFF8D6E63)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // TAB 1: JEWELRY CATALOG
                    item {
                        Text(
                            text = "ژورنال تخصصی سرویس طلا و نقره جهیزیه",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = CharcoalPrimary
                        )
                    }
                    items(defaultJewelryCatalog) { item ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurface),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(item.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary)
                                    Surface(
                                        color = if (item.isLuxuryBridal) Color(0xFFFFD700).copy(alpha = 0.2f) else WarmBorder,
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = item.category,
                                            fontSize = 10.sp,
                                            color = if (item.isLuxuryBridal) Color(0xFFB8860B) else CharcoalSecondary,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Text(item.description, fontSize = 11.sp, color = CharcoalSecondary)
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    Text("وزن: ${item.weightGrams} گرم", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaAccent)
                                    Text("عیار: ${item.purityKarat}", fontSize = 11.sp, color = CharcoalSecondary)
                                    Text("اجرت: ${item.wagePercent}٪", fontSize = 11.sp, color = SageGreen)
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // TAB 2: TRADE-IN CALCULATOR
                    item {
                        val gramPrice = gramPriceInput.toDoubleOrNull() ?: 48500000.0
                        val oldWeight = oldGoldWeight.toDoubleOrNull() ?: 0.0
                        val newWeight = newGoldWeight.toDoubleOrNull() ?: 0.0
                        val newWagePct = newGoldWage.toDoubleOrNull() ?: 12.0

                        // Old gold bought at raw scrap price (raw 18k value)
                        val oldGoldCreditRials = oldWeight * gramPrice
                        // New gold price
                        val newRawValue = newWeight * gramPrice
                        val newWageAmount = newRawValue * (newWagePct / 100.0)
                        val newProfit = (newRawValue + newWageAmount) * 0.07
                        val newTax = (newWageAmount + newProfit) * 0.09
                        val newGoldTotalRials = newRawValue + newWageAmount + newProfit + newTax

                        val diffPayableRials = (newGoldTotalRials - oldGoldCreditRials).coerceAtLeast(0.0)
                        val diffPayableTomans = (diffPayableRials / 10.0).toLong()

                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurface),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(
                                    text = "محاسبه‌گر تعویض طلای دست دوم با سرویس طلای عروس",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = "ارزش طلای قدیمی با قیمت طلای خام محاسبه شده و از فاکتور سرویس جدید کسر می‌گردد.",
                                    fontSize = 11.sp,
                                    color = CharcoalSecondary
                                )

                                OutlinedTextField(
                                    value = oldGoldWeight,
                                    onValueChange = { oldGoldWeight = it },
                                    label = { Text("وزن طلای کهنه شما (گرم)") },
                                    modifier = Modifier.fillMaxWidth().testTag("input_old_weight"),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    shape = RoundedCornerShape(8.dp)
                                )

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OutlinedTextField(
                                        value = newGoldWeight,
                                        onValueChange = { newGoldWeight = it },
                                        label = { Text("وزن سرویس نو (گرم)") },
                                        modifier = Modifier.weight(1f).testTag("input_new_weight"),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    OutlinedTextField(
                                        value = newGoldWage,
                                        onValueChange = { newGoldWage = it },
                                        label = { Text("اجرت سرویس نو (%)") },
                                        modifier = Modifier.weight(1f),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                }

                                Divider(color = WarmBorder)

                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("ارزش کسر شده طلای کهنه:", fontSize = 11.sp, color = SageGreen)
                                    Text("${formatter.format((oldGoldCreditRials / 10).toLong())} تومان", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SageGreen)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text("ارزش کل سرویس نو عروس:", fontSize = 11.sp, color = CharcoalSecondary)
                                    Text("${formatter.format((newGoldTotalRials / 10).toLong())} تومان", fontSize = 11.sp, color = CharcoalPrimary)
                                }

                                Surface(
                                    modifier = Modifier.fillMaxWidth(),
                                    color = TerracottaSubtle,
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, TerracottaAccent.copy(alpha = 0.4f))
                                ) {
                                    Column(
                                        modifier = Modifier.padding(14.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text("خالص پرداختی جهت تعویض", fontSize = 11.sp, color = TerracottaAccent)
                                        Text(
                                            text = "${formatter.format(diffPayableTomans)} تومان",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TerracottaAccent
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                3 -> {
                    // TAB 3: SMART PASSPORT & QR CODE
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurface),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(
                                    text = "شناسنامه هوشمند دیجیتال و QR پاسپورت اصالت طلا",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = "این شناسنامه همراه با QR اختصاصی بر روی فاکتور رسمی چاپ شده و اصالت عیار، کد آزمایشگاه استاندارد (ری‌گیری) و مالکیت خریدار را تایید می‌کند.",
                                    fontSize = 11.sp,
                                    color = CharcoalSecondary
                                )

                                Surface(
                                    color = WarmBackground,
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, WarmBorder),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(Icons.Default.QrCode2, contentDescription = "QR Code", tint = Color(0xFFB8860B), modifier = Modifier.size(72.dp))
                                        Text("کد استعلام: TALA-9842-VIP", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary)
                                        Text("عیار ثبت شده: ۷۵۰ (۱۸ عیار استاندارد ایران)", fontSize = 11.sp, color = SageGreen)
                                        Text("کد کارگاه سازنده: T-482 (اصفهان زرین)", fontSize = 10.sp, color = CharcoalSecondary)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}
