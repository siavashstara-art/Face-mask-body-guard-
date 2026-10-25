package com.example.ui.talaryar

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.TalarFlashDateOffer
import com.example.model.VenuePackageItem
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TalarYarScreen(
    isPersian: Boolean,
    onNavigateBack: () -> Unit,
    onNavigateToStudio: () -> Unit
) {
    BackHandler { onNavigateBack() }

    val context = LocalContext.current
    var activeTab by remember { mutableStateOf(0) } // 0 = Flash Dates, 1 = Venue Packages, 2 = Marketer Playbook
    var activeToastMessage by remember { mutableStateOf<String?>(null) }

    // Flash dates extracted from Event-mate packages.ts
    val flashDates = remember {
        listOf(
            TalarFlashDateOffer(
                id = "flash-1",
                persianDate = "پنج‌شنبه ۲۴ مهر ۱۴۰۵",
                dayNameFa = "پنج‌شنبه طلایی VIP",
                venueNameFa = "باغ‌تالار عمارت رویال فرشته (چوب و مرمر)",
                discountPercent = 18,
                reasonBadgeFa = "آزادسازی ظرفیت VIP (۱۸٪ تخفیف)",
                capacityLeft = 1,
                giftBonusFa = "هدیه رایگان: آتش‌بازی سرد ورودی + بار اسپرسو و استیشن کافی‌شاپ",
                originalPriceFa = "۳۸۰,۰۰۰,۰۰۰ تومان",
                discountedPriceFa = "۳۱۱,۶۰۰,۰۰۰ تومان"
            ),
            TalarFlashDateOffer(
                id = "flash-2",
                persianDate = "جمعه ۲ آبان ۱۴۰۵",
                dayNameFa = "جمعه سلطنتی امپریال",
                venueNameFa = "کاخ‌تالار ورسای نیاوران (سقف گنبدی ۲۴ عیار)",
                discountPercent = 22,
                reasonBadgeFa = "تخفیف ویژه کنسلی تاریخ (۲۲٪ تخفیف)",
                capacityLeft = 1,
                giftBonusFa = "هدیه رایگان: کیک ۵ طبقه سلطنتی + مه سنگین تانگو و پیانو زنده",
                originalPriceFa = "۵۲۰,۰۰۰,۰۰۰ تومان",
                discountedPriceFa = "۴۰۵,۶۰۰,۰۰۰ تومان"
            ),
            TalarFlashDateOffer(
                id = "flash-3",
                persianDate = "سه‌شنبه ۱۲ آبان ۱۴۰۵",
                dayNameFa = "سه‌شنبه الماسی اقتصادی",
                venueNameFa = "باغ‌عمارت شیشه‌ای لواسان (ویو کوهستان و آبشار)",
                discountPercent = 28,
                reasonBadgeFa = "بیشترین تخفیف نیمه‌هفته (۲۸٪ تخفیف)",
                capacityLeft = 1,
                giftBonusFa = "هدیه رایگان: ورودی باغ‌تالار + بدون کارمزد چک صیادی ۱۰ ماهه",
                originalPriceFa = "۲۹۰,۰۰۰,۰۰۰ تومان",
                discountedPriceFa = "۲۰۸,۸۰۰,۰۰۰ تومان"
            ),
            TalarFlashDateOffer(
                id = "flash-4",
                persianDate = "پنج‌شنبه ۲۱ آبان ۱۴۰۵",
                dayNameFa = "پنج‌شنبه لاکچری پاییزی",
                venueNameFa = "تالار بین‌المللی برج ساحلی کیش",
                discountPercent = 15,
                reasonBadgeFa = "جشنواره پاییزه FBNM (۱۵٪ تخفیف)",
                capacityLeft = 2,
                giftBonusFa = "هدیه رایگان: سوئیت VIP عروس و داماد + ترانسفر لیموزین تشریفاتی",
                originalPriceFa = "۴۵۰,۰۰۰,۰۰۰ تومان",
                discountedPriceFa = "۳۸۲,۵۰۰,۰۰۰ تومان"
            )
        )
    }

    // Venue Packages from Event-mate data
    val venuePackages = remember {
        listOf(
            VenuePackageItem(
                id = "pkg-1",
                titleFa = "پکیج الماس سلطنتی (Royal Diamond Package)",
                subtitleFa = "مناسب برای ۴۰۰ الی ۶۰۰ مهمان با پذیرایی سلف‌سرویس فاخر",
                capacityFa = "ظرفیت: تا ۶۰۰ نفر",
                menuDetailsFa = listOf(
                    "باقالی‌پلو با گوشت بره تازه گوسفندی + زرشک‌پلو با مرغ زعفرانی",
                    "فسنجان با مغز گردوی خالص + جوجه کباب نگین‌دار و بیف استروگانف",
                    "بار کامل سالاد سزار، سالاد بار اندونزی و انواع دسرهای فرانسوی"
                ),
                specialFeaturesFa = listOf(
                    "سن گرد متحرک رقص با نورپردازی داینامیک هماهنگ با بیت موزیک",
                    "سیستم صوتی Line Array کنسرت + دی‌جی اختصاصی تشریفات",
                    "جایگاه سفره عقد آبشاری در باغ با گل‌آرایی طبیعی ارکیده"
                ),
                giftVouchersFa = listOf("کارت تخفیف ۳۰٪ مزون لباس عروس رویال", "معرفی VIP به سالن زیبایی شاین"),
                basePricePerGuestFa = "۲,۴۰۰,۰۰۰ تومان به ازای هر مهمان",
                locationFa = "تهران، منطقه ۱ (فرشته / نیاوران)",
                imageResId = R.drawable.salonyar_car_dance_1792912000489
            ),
            VenuePackageItem(
                id = "pkg-2",
                titleFa = "پکیج زمرد مدرن باغ‌شیشه‌ای (Emerald Garden Pavilion)",
                subtitleFa = "فضای باز رویایی با آبشار طبیعی و عمارت شیشه‌ای چهارفصل",
                capacityFa = "ظرفیت: تا ۳۵۰ نفر",
                menuDetailsFa = listOf(
                    "شیشلیک اصیل شاندیز + کباب لقمه زعفرانی",
                    "ماهی قزل‌آلا سالمون با سس لیمو و ترخون",
                    "استیشن نوشیدنی گرم و بار باریستا اختصاصی"
                ),
                specialFeaturesFa = listOf(
                    "پیاده‌روی روی پل معلق شیشه‌ای روی برکه گل نیلوفر",
                    "آتش‌بازی همزمان در لحظه ورود عروس و داماد و برش کیک"
                ),
                giftVouchersFa = listOf("کوپن ۵ میلیون تومانی گل‌آرایی دکورمیت"),
                basePricePerGuestFa = "۱,۸۵۰,۰۰۰ تومان به ازای هر مهمان",
                locationFa = "لواسان، بلوار اصلی، عمارت شیشه‌ای",
                imageResId = R.drawable.salonyar_bride_hall_1792911979418
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "سامانه تالاریار و باغ‌تالارها (EventMate)",
                                color = CharcoalPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(Modifier.width(6.dp))
                            Surface(color = TerracottaAccent, shape = RoundedCornerShape(4.dp)) {
                                Text("پلی‌بوک تشریفات", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }
                        Text(
                            text = "تاریخ‌های کنسلی با تخفیف طلایی + پکیج‌های منو و سناریوهای بازاریاب",
                            color = CharcoalSecondary,
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = CharcoalPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = WarmSurface)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(WarmBackground)
                .verticalScroll(rememberScrollState())
        ) {
            // HERO BANNER
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.salonyar_car_dance_1792912000489),
                        contentDescription = "EventMate Banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.25f),
                                        Color.Black.copy(alpha = 0.85f)
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(color = TerracottaAccent, shape = RoundedCornerShape(20.dp)) {
                            Text(
                                text = "شبکه هوشمند باغ‌تالارها و امارات مجلل تشریفات",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "تالاریار: مدیریت رزرو سانس‌ها و جشنواره تاریخ‌های طلایی",
                                color = Color.White,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "ارتباط مستقیم عروس و داماد با تالارها بدون واسطه، با هدایای VIP و اقساط چک صیادی",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // TABS SWITCHER
            Surface(
                color = WarmSurface,
                modifier = Modifier.fillMaxWidth(),
                border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val tabs = listOf(
                        Triple(0, "تاریخ‌های طلایی (Flash Dates)", Icons.Default.Bolt),
                        Triple(1, "پکیج‌ها و منوی تالار", Icons.Default.Restaurant),
                        Triple(2, "پلی‌بوک ویزیتور تالار", Icons.Default.AutoGraph)
                    )

                    tabs.forEach { (index, title, icon) ->
                        val isSelected = activeTab == index
                        Surface(
                            color = if (isSelected) TerracottaAccent else WarmSurfaceSecondary,
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) TerracottaAccent else WarmBorder
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 3.dp)
                                .clickable { activeTab = index }
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = title,
                                    tint = if (isSelected) Color.White else CharcoalPrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(Modifier.height(3.dp))
                                Text(
                                    text = title,
                                    color = if (isSelected) Color.White else CharcoalPrimary,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // Toast Message Output
            if (activeToastMessage != null) {
                Surface(
                    color = TerracottaSubtle,
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaAccent),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = activeToastMessage!!, fontSize = 11.sp, color = CharcoalPrimary, modifier = Modifier.weight(1f))
                        IconButton(onClick = { activeToastMessage = null }, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = CharcoalSecondary, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            // CONTENT
            when (activeTab) {
                // TAB 0: FLASH DATES & LAST MINUTE SLOTS
                0 -> {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "فرصت‌های طلایی تاریخ‌های کنسلی و تخفیف‌دار تالارها:",
                            style = MaterialTheme.typography.titleSmall,
                            color = CharcoalPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        flashDates.forEach { offer ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
                            ) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(text = offer.persianDate, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary)
                                            Text(text = offer.venueNameFa, fontSize = 11.sp, color = CharcoalSecondary)
                                        }

                                        Surface(color = BrickRed, shape = RoundedCornerShape(6.dp)) {
                                            Text(text = "${offer.discountPercent}٪ تخفیف", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                                        }
                                    }

                                    Surface(color = SageGreenSubtle, shape = RoundedCornerShape(6.dp)) {
                                        Text(text = offer.giftBonusFa, color = SageGreen, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(text = "قیمت پایه: ${offer.originalPriceFa}", fontSize = 10.sp, color = CharcoalSecondary)
                                            Text(text = "قیمت طلایی: ${offer.discountedPriceFa}", fontSize = 12.sp, color = TerracottaAccent, fontWeight = FontWeight.Bold)
                                        }

                                        Button(
                                            onClick = {
                                                activeToastMessage = "درخواست رزرو تاریخ (${offer.persianDate}) برای ${offer.venueNameFa} ثبت شد و کارشناس تالار با شما تماس خواهد گرفت ✓"
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text("رزرو تاریخ طلایی", fontSize = 11.sp, color = Color.White)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // TAB 1: VENUE PACKAGES & MENUS
                1 -> {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        venuePackages.forEach { pkg ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                                shape = RoundedCornerShape(14.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
                            ) {
                                Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    Text(text = pkg.titleFa, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = CharcoalPrimary)
                                    Text(text = pkg.subtitleFa, fontSize = 11.sp, color = CharcoalSecondary)

                                    Image(
                                        painter = painterResource(pkg.imageResId),
                                        contentDescription = pkg.titleFa,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxWidth().height(150.dp).clip(RoundedCornerShape(10.dp))
                                    )

                                    Text(text = "آیتم‌های منوی پذیرایی:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = CharcoalPrimary)
                                    pkg.menuDetailsFa.forEach { item ->
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Check, contentDescription = null, tint = SageGreen, modifier = Modifier.size(14.dp))
                                            Spacer(Modifier.width(6.dp))
                                            Text(text = item, fontSize = 11.sp, color = CharcoalPrimary)
                                        }
                                    }

                                    HorizontalDivider(color = WarmBorderSubtle)

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = pkg.basePricePerGuestFa, fontSize = 12.sp, color = TerracottaAccent, fontWeight = FontWeight.Bold)
                                        Button(
                                            onClick = { onNavigateToStudio() },
                                            colors = ButtonDefaults.buttonColors(containerColor = SageGreen),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(14.dp))
                                            Spacer(Modifier.width(4.dp))
                                            Text("پرو مجازی صحنه تالار", fontSize = 10.sp, color = Color.White)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // TAB 2: MARKETER & VISITOR PLAYBOOK
                2 -> {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Surface(
                            color = TerracottaSubtle,
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaAccent.copy(alpha = 0.3f))
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(
                                    text = "پلی‌بوک بازاریاب و ویزیتور تالارهای پذیرایی (EventMate)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = "۱. ورود به دفتر مدیریت تالار با تبلت و دموی زنده کاتالوگ\n" +
                                            "۲. نشان دادن جشنواره تاریخ‌های خالی تالار و پر کردن آن‌ها با تخفیف‌های هدفمند\n" +
                                            "۳. عقد قرارداد ۱ ساله و اتصال به سامانه ارجاع سالن‌های زیبایی و مزون‌های همکار\n" +
                                            "۴. تسویه نقدی و دیجیتال پورسانت ویزیتور بدون کارمزد چک",
                                    fontSize = 11.sp,
                                    color = CharcoalSecondary
                                )
                            }
                        }

                        Button(
                            onClick = {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "پرزنت پلتفرم تالاریار توانا برای مدیران تالارها و باغ‌های تشریفاتی سراسر کشور.")
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "اشتراک‌گذاری پلی‌بوک تالار"))
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("ارسال پلی‌بوک و کاتالوگ برای مدیران تالارها", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
