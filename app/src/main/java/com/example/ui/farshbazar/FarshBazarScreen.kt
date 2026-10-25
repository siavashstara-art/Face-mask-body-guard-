package com.example.ui.farshbazar

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import java.text.NumberFormat
import java.util.Locale
import kotlinx.coroutines.delay

data class CarpetItem(
    val id: String,
    val title: String,
    val origin: String, // "تبریز", "کاشان", "اصفهان", "قشقایی"
    val type: String, // "دستباف تمام ابریشم", "ماشینی ۱۵۰۰ شانه", "دستباف پشم و گل ابریشم"
    val reedsDensity: String, // "۵۰ رج", "۱۵۰۰ شانه تراکم ۴۵۰۰", "۷۰ رج ریزبافت"
    val basePriceTomans: Long, // 12-meter base price
    val description: String,
    val primaryColor: Color
)

val defaultCarpetCatalog = listOf(
    CarpetItem(
        id = "carpet_tabriz_silk",
        title = "فرش دستباف اعلای تبریز طرح سالاری و لچک‌ترنج",
        origin = "تبریز",
        type = "دستباف چله و گل ابریشم",
        reedsDensity = "۶۰ رج اعلا با پشم مرینوس",
        basePriceTomans = 285_000_000L,
        description = "شاهکار هنر تبریز، رنگ‌آمیزی گیاهی و سایه‌روشن ابریشمی، مناسب سالن اصلی پذیرایی جهیزیه عروس",
        primaryColor = Color(0xFF8B0000)
    ),
    CarpetItem(
        id = "carpet_isfahan_sheikhsafi",
        title = "قالی دستباف اصفهان طرح شیخ صفی و گنبد مسجد امام",
        origin = "اصفهان",
        type = "دستباف کرک و ابریشم طبیعی",
        reedsDensity = "۷۰ رج استادبافت",
        basePriceTomans = 340_000_000L,
        description = "اصالت سنتی نقش‌جهان، چله ابریشم اصل، ماندگاری صدساله و ارزش افزوده سرمایه‌گذاری برای خانه نو",
        primaryColor = Color(0xFF1B365D)
    ),
    CarpetItem(
        id = "carpet_kashan_1200",
        title = "فرش کاشان ۱۲۰۰ شانه گل‌برجسته طرح افشان سلطنتی",
        origin = "کاشان",
        type = "ماشینی دستباف‌گونه اکریلیک هیت‌ست",
        reedsDensity = "۱۲۰۰ شانه تراکم ۳۶۰۰",
        basePriceTomans = 32_500_000L,
        description = "ضد پرز و حساسیت با نخ بایر آلمان، زیردست فوق‌العاده نرم با بافت گل‌برجسته های‌بالک",
        primaryColor = Color(0xFF4A6B82)
    ),
    CarpetItem(
        id = "carpet_qashqai_nomadic",
        title = "قالیچه عشایری قشقایی شیراز طرح شاه‌عباسی و گبه",
        origin = "قشقایی",
        type = "دستباف تمام پشم با رنگرزی سنتی",
        reedsDensity = "بافت ذهنی ایلیاتی با پشم بهاره",
        basePriceTomans = 48_000_000L,
        description = "نقوش زنده و گرم ایلیاتی، حس زندگی و گرما در نشیمن و دکوراسیون تلفیقی مدرن-سنتی",
        primaryColor = Color(0xFFD97706)
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarshBazarScreen(
    isPersian: Boolean = true,
    onNavigateBack: () -> Unit,
    onNavigateToStudio: () -> Unit
) {
    BackHandler { onNavigateBack() }

    var selectedTab by remember { mutableIntStateOf(0) } // 0: کاتالوگ فرش جهیزیه, 1: پرو در سالن, 2: رج‌شمار هوشمند دوربین, 3: طرح اقساطی جهیزیه
    var selectedCarpet by remember { mutableStateOf(defaultCarpetCatalog[0]) }
    var selectedSize by remember { mutableStateOf("12 متری") }
    var selectedFloorType by remember { mutableStateOf("پارکت چوبی گرم") }

    // Raj Counter Camera State
    var macroZoom by remember { mutableFloatStateOf(5f) }
    var isScanningKnots by remember { mutableStateOf(false) }
    var scanCompleted by remember { mutableStateOf(false) }
    var detectedRajCount by remember { mutableIntStateOf(65) } // Knots in 7 cm
    var detectedKnotType by remember { mutableStateOf("گره ترکی متقارن (قلاب‌باف تبریز)") }
    var detectedPurity by remember { mutableStateOf("چله ابریشم ۱۰۰٪ طبیعی با پشم مرینوس") }

    val formatter = remember { NumberFormat.getNumberInstance(Locale.US) }

    val sizeMultiplier = when (selectedSize) {
        "12 متری" -> 1.0
        "9 متری" -> 0.78
        "6 متری" -> 0.52
        else -> 0.35
    }

    LaunchedEffect(isScanningKnots) {
        if (isScanningKnots) {
            delay(2200)
            isScanningKnots = false
            scanCompleted = true
            detectedRajCount = (55..75).random()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (isPersian) "فرش‌بازار بین‌المللی" else "FarshBazar International",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = CharcoalPrimary
                        )
                        Text(
                            text = "هر فرش‌فروش در سراسر جهان، یک غرفه (پشتیبانی از ۱۷ زبان)",
                            fontSize = 10.sp,
                            color = CharcoalSecondary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack, modifier = Modifier.testTag("btn_back_farshbazar")) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت", tint = CharcoalPrimary)
                    }
                },
                actions = {
                    Surface(
                        color = TerracottaSubtle,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, TerracottaAccent.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Language, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("۱۷ زبان", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaAccent)
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
                // MEMORIAL DEDICATION BANNER (ادای احترام و یادبود)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E7)),
                    border = BorderStroke(1.5.dp, Color(0xFFD4AF37))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.VolunteerActivism, contentDescription = null, tint = Color(0xFFB8860B), modifier = Modifier.size(22.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "به یاد پدر مرحومم حاج حسین آقای علی‌میری",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5A4300)
                            )
                        }
                        Text(
                            text = "از تاجران بسیار خوشنام، سرشناس و پیشکسوت صنعت فرش ایران و جهان",
                            fontSize = 11.sp,
                            color = Color(0xFF7A5C00),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            item {
                // INTERNATIONAL PLATFORM DISCLAIMER BANNER
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmSurface),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "پیش‌نمایش دموی پلتفرم بین‌المللی فرش‌بازار",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                        }
                        Text(
                            text = "• شعار اصلی پلتفرم: «هر فرش‌فروش در سراسر جهان، یک غرفه»\n• توسعه داده شده به دست ناشر: آقای اکبر علی‌میری (سیاوش)\n• سامانه بین‌المللی تجارت و صادرات فرش با پشتیبانی هوشمند از ۱۷ زبان زنده دنیا",
                            fontSize = 11.sp,
                            color = CharcoalSecondary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            item {
                // OFFICIAL BUSINESS LOCATION & HEADQUARTERS
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                    border = BorderStroke(1.dp, SageGreen.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = SageGreen, modifier = Modifier.size(28.dp))
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "محل کار و گالری اصلی:",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SageGreen
                            )
                            Text(
                                text = "بازار فرش ایران، خیابان خیام، بازار فرش ایران، پلاک ۴۸، فرش حاج حسین علی‌میری و پسران.",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            item {
                // DEVELOPER PORTFOLIO & OTHER PRODUCTS BY AKBAR ALIMIRI (SIAVASH)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = WarmSurface),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Apps, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "سایر نرم‌افزارها و محصولات توسعه‌دهنده (آقای اکبر علی‌میری - سیاوش)",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                        }
                        
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(WarmBackground, RoundedCornerShape(8.dp))
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("• فرش‌بازار لینک (پلتفرم ارتباطی و شبکه اصناف فرش)", fontSize = 11.sp, color = CharcoalPrimary)
                            Text("• وی نکسوس (V-Nexus): خدمات هوش مصنوعی احساس‌محور", fontSize = 11.sp, color = CharcoalPrimary)
                            Text("• ساختمان‌یار: اپلیکیشن جامع مدیریت املاک و ساختمان‌ها", fontSize = 11.sp, color = CharcoalPrimary)
                            Text("• خدمات‌یار: معرفی خدمات فنی ساختمانی (لوله، تاسیسات، برق و...)", fontSize = 11.sp, color = CharcoalPrimary)
                            Text("• توانا روتر (Tavana Router) و توانافرک (TavanaFark): ساخت برنامه‌ها و فروشگاه‌های اینترنتی", fontSize = 11.sp, color = CharcoalPrimary)
                            Text("• گیم‌های اینترنتی و بازی‌های تعاملی تحت وب", fontSize = 11.sp, color = CharcoalPrimary)
                            Text("• ده‌ها برنامه کاربردی تولید شده جهت سهولت کار کدنویسان و برنامه‌نویسان", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SageGreen)
                        }
                    }
                }
            }

            item {
                // Tab Selection
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = WarmSurface,
                    contentColor = CharcoalPrimary,
                    edgePadding = 0.dp
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("کاتالوگ فرش جهیزیه", fontSize = 11.sp, fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal) },
                        icon = { Icon(Icons.Default.GridOn, contentDescription = null, modifier = Modifier.size(18.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("پرو در سالن خانه", fontSize = 11.sp, fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal) },
                        icon = { Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(18.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        text = { Text("رج‌شمار با دوربین", fontSize = 11.sp, fontWeight = if (selectedTab == 2) FontWeight.Bold else FontWeight.Normal) },
                        icon = { Icon(Icons.Default.CenterFocusStrong, contentDescription = null, modifier = Modifier.size(18.dp)) }
                    )
                    Tab(
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        text = { Text("طرح اقساطی", fontSize = 11.sp, fontWeight = if (selectedTab == 3) FontWeight.Bold else FontWeight.Normal) },
                        icon = { Icon(Icons.Default.CreditScore, contentDescription = null, modifier = Modifier.size(18.dp)) }
                    )
                }
            }

            when (selectedTab) {
                0 -> {
                    // TAB 0: CARPET CATALOG
                    item {
                        Text(
                            text = "فرش‌های دستباف و ماشینی منتخب جهیزیه نوعروس",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = CharcoalPrimary
                        )
                    }
                    items(defaultCarpetCatalog) { carpet ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurface),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(carpet.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary)
                                    Surface(
                                        color = carpet.primaryColor.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = carpet.origin,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = carpet.primaryColor,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                // Stylized Carpet Representation
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(carpet.primaryColor.copy(alpha = 0.85f))
                                        .border(2.dp, Color(0xFFFFD700).copy(alpha = 0.6f), RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("طرح سنتی و اصیل ایرانی", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        Text("${carpet.reedsDensity} - ${carpet.type}", color = Color.White.copy(alpha = 0.9f), fontSize = 10.sp)
                                    }
                                }

                                Text(carpet.description, fontSize = 11.sp, color = CharcoalSecondary)

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("قیمت تخمینی ۱۲ متری:", fontSize = 10.sp, color = CharcoalSecondary)
                                        Text(
                                            text = "${formatter.format(carpet.basePriceTomans)} تومان",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TerracottaAccent
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            selectedCarpet = carpet
                                            selectedTab = 1
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Text("پرو در سالن خانه", fontSize = 11.sp, color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }

                1 -> {
                    // TAB 1: INTERACTIVE ROOM PREVIEW SIMULATOR
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurface),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(
                                    text = "شبیه‌ساز سه‌بعدی پرو فرش در سالن پذیرایی خانه نو",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = "کفپوش سالن و ابعاد فرش را تغییر دهید تا تناسب آن با فضای نشیمن و چیدمان جهیزیه را مشاهده کنید.",
                                    fontSize = 11.sp,
                                    color = CharcoalSecondary
                                )

                                // Floor Type Selector
                                Text("جنس و رنگ کفپوش سالن پذیرایی:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = CharcoalPrimary)
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    listOf("پارکت چوبی گرم", "سرامیک کرم لوکس", "سنگ مرمر سفید").forEach { floor ->
                                        Surface(
                                            modifier = Modifier.weight(1f).clickable { selectedFloorType = floor },
                                            color = if (selectedFloorType == floor) TerracottaAccent else WarmSurfaceSecondary,
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text(
                                                text = floor,
                                                fontSize = 10.sp,
                                                color = if (selectedFloorType == floor) Color.White else CharcoalPrimary,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(vertical = 8.dp),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }

                                // Size Selector
                                Text("ابعاد فرش مورد نظر:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = CharcoalPrimary)
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    listOf("12 متری", "9 متری", "6 متری", "قالیچه 4 متری").forEach { size ->
                                        Surface(
                                            modifier = Modifier.weight(1f).clickable { selectedSize = size },
                                            color = if (selectedSize == size) SageGreen else WarmSurfaceSecondary,
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text(
                                                text = size,
                                                fontSize = 10.sp,
                                                color = if (selectedSize == size) Color.White else CharcoalPrimary,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(vertical = 8.dp),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }

                                val floorColor = when (selectedFloorType) {
                                    "پارکت چوبی گرم" -> Color(0xFF8D6E63)
                                    "سرامیک کرم لوکس" -> Color(0xFFF5F5DC)
                                    else -> Color(0xFFE0E0E0)
                                }

                                val carpetWidthFraction = when (selectedSize) {
                                    "12 متری" -> 0.9f
                                    "9 متری" -> 0.78f
                                    "6 متری" -> 0.65f
                                    else -> 0.5f
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(floorColor)
                                        .border(1.dp, WarmBorder, RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(carpetWidthFraction)
                                            .fillMaxHeight(0.75f)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(selectedCarpet.primaryColor)
                                            .border(2.dp, Color(0xFFFFD700).copy(alpha = 0.7f), RoundedCornerShape(6.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(selectedCarpet.title, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                                            Text("$selectedSize روی $selectedFloorType", color = Color.White.copy(alpha = 0.9f), fontSize = 9.sp)
                                        }
                                    }
                                }

                                val calculatedPrice = (selectedCarpet.basePriceTomans * sizeMultiplier).toLong()
                                Surface(
                                    color = SageGreenSubtle,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("قیمت ابعاد انتخابی ($selectedSize):", fontSize = 11.sp, color = CharcoalSecondary)
                                        Text("${formatter.format(calculatedPrice)} تومان", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SageGreen)
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    // TAB 2: RAJ COUNTER WITH CAMERA (آنالیز و رج‌شمار هوشمند قالی با دوربین موبایل)
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurface),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CenterFocusStrong, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(20.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        text = "رج‌شمار هوشمند قالی با دوربین موبایل",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CharcoalPrimary
                                    )
                                }
                                Text(
                                    text = "دوربین را در فاصله ۱۰ سانتی‌متری پشت قالی بگیرید. کادر ۷ سانتی‌متری دقیقاً تراکم گره‌ها (رج‌شمار تبریز/اصفهان) را اسکن می‌کند.",
                                    fontSize = 11.sp,
                                    color = CharcoalSecondary
                                )

                                // Zoom Control
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("بزرگ‌نمایی لنز ماکرو: ${macroZoom.toInt()}X", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                        listOf(1f, 3f, 5f, 10f).forEach { zoom ->
                                            Surface(
                                                modifier = Modifier.clickable { macroZoom = zoom },
                                                color = if (macroZoom == zoom) TerracottaAccent else WarmSurfaceSecondary,
                                                shape = RoundedCornerShape(6.dp)
                                            ) {
                                                Text("${zoom.toInt()}X", color = if (macroZoom == zoom) Color.White else CharcoalPrimary, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                            }
                                        }
                                    }
                                }

                                // Simulated Camera Viewfinder with 7cm Ruler Grid Overlay
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(Color(0xFF2C2C2C))
                                        .border(2.dp, if (isScanningKnots) TerracottaAccent else SageGreen, RoundedCornerShape(12.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    // Woven Knots Pattern Effect
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(
                                                Brush.radialGradient(
                                                    colors = listOf(
                                                        Color(0xFF8B0000).copy(alpha = 0.7f),
                                                        Color(0xFF4A0000)
                                                    )
                                                )
                                            )
                                    )

                                    // 7 cm Grid Box Frame
                                    Box(
                                        modifier = Modifier
                                            .size(120.dp)
                                            .border(2.dp, Color(0xFFFFD700), RoundedCornerShape(4.dp))
                                            .background(Color.White.copy(alpha = 0.1f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text("کادر ۷ سانتی‌متری", color = Color(0xFFFFD700), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                            Text("واحد استاندارد رج‌شمار", color = Color.White.copy(alpha = 0.8f), fontSize = 8.sp)
                                        }
                                    }

                                    if (isScanningKnots) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            CircularProgressIndicator(color = Color(0xFFFFD700), modifier = Modifier.size(36.dp))
                                            Spacer(Modifier.height(8.dp))
                                            Text("در حال پردازش گره‌ها و آنالیز تار و پود...", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }

                                Button(
                                    onClick = { isScanningKnots = true },
                                    enabled = !isScanningKnots,
                                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                                    modifier = Modifier.fillMaxWidth().testTag("btn_scan_raj"),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.CameraEnhance, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text(if (isScanningKnots) "در حال اسکن..." else "اسکن و آنالیز رج‌شمار قالی", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                if (scanCompleted) {
                                    Surface(
                                        color = SageGreenSubtle,
                                        shape = RoundedCornerShape(10.dp),
                                        border = BorderStroke(1.dp, SageGreen.copy(alpha = 0.4f)),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("تراکم رج‌شمار شناسایی‌شده:", fontSize = 11.sp, color = CharcoalSecondary)
                                                Text("$detectedRajCount رج (بسیار ریزبافت)", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SageGreen)
                                            }
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("نوع گره قالی:", fontSize = 11.sp, color = CharcoalSecondary)
                                                Text(detectedKnotType, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                            }
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("جنس چله و تاروپود:", fontSize = 11.sp, color = CharcoalSecondary)
                                                Text(detectedPurity, fontSize = 11.sp, color = CharcoalPrimary)
                                            }
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("تعداد گره در متر مربع:", fontSize = 11.sp, color = CharcoalSecondary)
                                                Text("${formatter.format(((detectedRajCount / 0.07) * (detectedRajCount / 0.07)).toLong())} گره/m²", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TerracottaAccent)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                3 -> {
                    // TAB 3: INSTALLMENT & DOWRY FINANCING
                    item {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurface),
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(
                                    text = "طرح تسهیلات جهیزیه عروس و داماد (اقساط بدون ضامن)",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = "امکان خرید ست قالی‌های سالن پذیرایی و اتاق خواب با چک صیادی و اقساط ۱۲ تا ۲۴ ماهه برای زوج‌های معرفی شده از سالن‌ها و تالارهای همکار.",
                                    fontSize = 11.sp,
                                    color = CharcoalSecondary
                                )

                                Surface(
                                    color = Color(0xFFF3E5F5),
                                    shape = RoundedCornerShape(10.dp),
                                    border = BorderStroke(1.dp, Color(0xFFBA68C8))
                                ) {
                                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                        Text("• پیش‌پرداخت: ۳۰٪ نقدی", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6A1B9A))
                                        Text("• بازپرداخت: تا ۲۴ ماه با کارمزد مصوب اتحادیه", fontSize = 11.sp, color = CharcoalPrimary)
                                        Text("• تخفیف نقدی خرید جهیزیه کامل: ۱۵٪ هدیه اختصاصی", fontSize = 11.sp, color = SageGreen)
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
