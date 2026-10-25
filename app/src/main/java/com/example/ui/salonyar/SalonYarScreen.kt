package com.example.ui.salonyar

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.CustomerRetentionCampaign
import com.example.model.SalonPitchProfile
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalonYarScreen(
    isPersian: Boolean,
    onNavigateBack: () -> Unit,
    onNavigateToStudio: () -> Unit
) {
    BackHandler { onNavigateBack() }

    val context = LocalContext.current

    // Live pitch profile editable by visitor/marketer on the spot in the salon
    var pitchProfile by remember {
        mutableStateOf(
            SalonPitchProfile(
                salonName = "سالن زیبایی شاین VIP",
                managerName = "سرکار خانم مریم حسینی",
                phoneNumber = "۰۹۱۲۳۴۵۶۷۸۹ - ۰۲۱۸۸۸۸۸۸۸۸",
                address = "تهران، سعادت‌آباد، صرافان شمالی، پلاک ۱۲",
                instagramHandle = "@shine_beauty_vip"
            )
        )
    }

    var selectedSalonImageUri by remember { mutableStateOf<Uri?>(null) }
    var capturedSalonBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Photo picker for salon photo / logo
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            selectedSalonImageUri = uri
            try {
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
                } else {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                }
                capturedSalonBitmap = bitmap
            } catch (_: Throwable) {}
        }
    }

    // Active Tab in SalonYar: 0 = On-the-Spot Personalizer, 1 = Customer Retention, 2 = Visual Feature Demos
    var activeTab by remember { mutableStateOf(0) }

    // Retention Campaigns
    val retentionCampaigns = remember {
        listOf(
            CustomerRetentionCampaign(
                id = "color_touchup",
                titleFa = "یادآور تمدید رنگ و ریشه مو (دوره ۲۸ روزه)",
                titleEn = "Hair Color & Root Touch-up Cycle (28 Days)",
                intervalDays = 28,
                sampleMessageFa = "سلام بانوی عزیز! ۲۸ روز از ریشه‌گیری موهای زیباتون در سالن شاین VIP گذشت. برای رزرو نوبت ریشه‌گیری با ۱۰٪ تخفیف کلیک کنید.",
                sampleMessageEn = "Hi Dear! 28 days passed since your root touch-up at Shine Salon VIP. Click to book your refit with 10% OFF.",
                discountPercent = 10
            ),
            CustomerRetentionCampaign(
                id = "nail_refill",
                titleFa = "یادآور ترمیم کاشت ناخن (دوره ۲۱ روزه)",
                titleEn = "Nail Extension & Refill Cycle (21 Days)",
                intervalDays = 21,
                sampleMessageFa = "زمان ترمیم ناخن‌های نازنینتون رسید! جهت حفظ زیبایی ناخن‌ها و رزرو نوبت اختصاصی در سالن شاین VIP پاسخ دهید.",
                sampleMessageEn = "Time for your nail refill! Reply to book your VIP slot at Shine Salon.",
                discountPercent = 12
            ),
            CustomerRetentionCampaign(
                id = "facial_skincare",
                titleFa = "یادآور پاکسازی و هیدراتاسیون پوست (دوره ۳۰ روزه)",
                titleEn = "Facial Skincare & Hydration Cycle (30 Days)",
                intervalDays = 30,
                sampleMessageFa = "پوست شما نیازمند شارژ و آبرسانی ماهانه است. بن تخفیف هیدراتاسیون پوست در سالن شاین VIP برای شما فعال شد.",
                sampleMessageEn = "Your monthly skincare hydration is ready at Shine Salon VIP.",
                discountPercent = 15
            ),
            CustomerRetentionCampaign(
                id = "wedding_anniversary",
                titleFa = "بن هدیه سالگرد ازدواج و تولد عروس",
                titleEn = "Bridal Wedding Anniversary & Birthday Voucher",
                intervalDays = 365,
                sampleMessageFa = "سالگرد پیوند قشنگتون مبارک! هدیه سالن شاین VIP به شما: بن ۳۰٪ تخفیف ویژه شینیون و آرایش مجلسی.",
                sampleMessageEn = "Happy Anniversary! Enjoy 30% OFF makeup & styling at Shine Salon VIP.",
                discountPercent = 30
            ),
            CustomerRetentionCampaign(
                id = "loyalty_cashback",
                titleFa = "باشگاه مشتریان و تمبرهای دیجیتال خدمات سالن",
                titleEn = "Digital Cashback & Loyalty Stamp Card",
                intervalDays = 14,
                sampleMessageFa = "تبریک! با ۴ بار مراجعه به سالن شاین VIP، مراجعه پنجم شما شامل براشینگ و شینیون رایگان گردید.",
                sampleMessageEn = "Congrats! Your 5th visit includes free styling & blowdry.",
                discountPercent = 100
            )
        )
    }

    var activeToastMessage by remember { mutableStateOf<String?>(null) }

    // Before/After interactive view state
    var showAfterState by remember { mutableStateOf(true) }

    // Doubtful client advisor selection state
    var selectedHairStyleAdvisor by remember { mutableStateOf("کوپ مصری لایه‌دار + رنگ بالیاژ نسکافه‌ای") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isPersian) "سامانه سالن‌یار اختصاصی (SalonYar)" else "SalonYar Beauty Hub",
                                color = CharcoalPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                            Spacer(Modifier.width(6.dp))
                            Surface(
                                color = TerracottaAccent,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = if (isPersian) "ویژه ویزیتور و بازاریاب" else "Marketer Pitch Mode",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = if (isPersian) "سیستم مشتری برگردان + شخصی‌سازی در محل + نمونه‌های تصویری" else "Retention Engine + Instant Branding + Visual Demos",
                            color = CharcoalSecondary,
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = CharcoalPrimary
                        )
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
            // 1. DYNAMIC BRANDED SALON HEADER BANNER (Instant Wow-Factor for Pitch)
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
                        .height(200.dp)
                ) {
                    // Captured or Default Salon Photo Background
                    if (capturedSalonBitmap != null) {
                        Image(
                            bitmap = capturedSalonBitmap!!.asImageBitmap(),
                            contentDescription = "Custom Salon Photo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.salonyar_banner_1792911967921),
                            contentDescription = "Salon Banner",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // Luxury Dark Gradient Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.35f),
                                        Color.Black.copy(alpha = 0.85f)
                                    )
                                )
                            )
                    )

                    // Salon Custom Branded Details Overlay
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = TerracottaAccent,
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Icon(Icons.Default.Verified, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text(
                                        text = if (isPersian) "نسخه اختصاصی سالن‌یار" else "Custom Salon Edition",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Surface(
                                color = Color.White.copy(alpha = 0.25f),
                                shape = CircleShape
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Icon(Icons.Default.Storefront, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text(
                                        text = pitchProfile.instagramHandle,
                                        color = Color.White,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }

                        Column {
                            Text(
                                text = pitchProfile.salonName,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = "مدیریت: ${pitchProfile.managerName}",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Phone, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(12.dp))
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = pitchProfile.phoneNumber,
                                    color = Color.White.copy(alpha = 0.85f),
                                    fontSize = 11.sp
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Place, contentDescription = null, tint = SageGreen, modifier = Modifier.size(12.dp))
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = pitchProfile.address,
                                    color = Color.White.copy(alpha = 0.8f),
                                    fontSize = 10.sp,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }

            // 2. MAIN SALONYAR TABS (Personalize Pitch / Retention Engine / Visual Demos)
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
                        Triple(0, if (isPersian) "شخصی‌سازی در محل" else "Instant Branding", Icons.Default.EditNote),
                        Triple(1, if (isPersian) "سیستم مشتری برگردان" else "Client Retention", Icons.Default.Autorenew),
                        Triple(2, if (isPersian) "دویوی تصویری امکانات" else "Visual Demos", Icons.Default.PhotoLibrary)
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
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(10.dp))

            // TAB CONTENT DISPLAY
            when (activeTab) {
                // TAB 0: ON-THE-SPOT SALON BRANDING CUSTOMIZER
                0 -> {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Surface(
                            color = TerracottaSubtle,
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaAccent.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.TouchApp, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(24.dp))
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    text = if (isPersian) "ویژه‌ی بازاریاب و ویزیتور: مشخصات سالن زیبایی را همین جا وارد کنید تا کل نرم‌افزار در برابر چشمان صاحب سالن شخصی‌سازی شود!"
                                    else "Marketer Tool: Customize salon profile in real time to impress the salon owner during sales pitch!",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = CharcoalPrimary
                                )
                            }
                        }

                        // Photo Capture Button
                        Button(
                            onClick = {
                                photoPickerLauncher.launch(
                                    androidx.activity.result.PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.AddAPhoto, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = if (isPersian) "عکاسی فوری از سالن یا بارگذاری لوگوی آرایشگاه" else "Capture Salon Photo / Upload Logo",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        OutlinedTextField(
                            value = pitchProfile.salonName,
                            onValueChange = { pitchProfile = pitchProfile.copy(salonName = it) },
                            label = { Text(if (isPersian) "نام سالن زیبایی / آرایشگاه" else "Salon Name") },
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = TerracottaAccent, unfocusedBorderColor = WarmBorder),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = pitchProfile.managerName,
                            onValueChange = { pitchProfile = pitchProfile.copy(managerName = it) },
                            label = { Text(if (isPersian) "نام مدیریت سالن" else "Manager Name") },
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = TerracottaAccent, unfocusedBorderColor = WarmBorder),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = pitchProfile.phoneNumber,
                            onValueChange = { pitchProfile = pitchProfile.copy(phoneNumber = it) },
                            label = { Text(if (isPersian) "شماره تلفن‌های سالن" else "Phone Numbers") },
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = TerracottaAccent, unfocusedBorderColor = WarmBorder),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = pitchProfile.address,
                            onValueChange = { pitchProfile = pitchProfile.copy(address = it) },
                            label = { Text(if (isPersian) "آدرس دقیق سالن" else "Salon Address") },
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = TerracottaAccent, unfocusedBorderColor = WarmBorder),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = pitchProfile.instagramHandle,
                            onValueChange = { pitchProfile = pitchProfile.copy(instagramHandle = it) },
                            label = { Text(if (isPersian) "صفحه اینستاگرام / وب‌سایت" else "Instagram / Website") },
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = TerracottaAccent, unfocusedBorderColor = WarmBorder),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Quick Share / Pitch Order Action
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(
                                            Intent.EXTRA_TEXT,
                                            "پیش‌نمایش اختصاصی سالن‌یار برای ${pitchProfile.salonName}\nمدیریت: ${pitchProfile.managerName}\nتلفن: ${pitchProfile.phoneNumber}\nاینستاگرام: ${pitchProfile.instagramHandle}"
                                        )
                                        type = "text/plain"
                                    }
                                    val shareIntent = Intent.createChooser(sendIntent, if (isPersian) "ارسال دموی سالن برای مدیر" else "Share Salon Pitch Demo")
                                    context.startActivity(shareIntent)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = SageGreen),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(6.dp))
                                Text(if (isPersian) "ارسال دموی بنر در واتساپ" else "Share Banner Demo", fontSize = 11.sp, color = Color.White)
                            }

                            Button(
                                onClick = { onNavigateToStudio() },
                                colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(6.dp))
                                Text(if (isPersian) "تست دوربین و پرو با برند سالن" else "Test Camera Try-On", fontSize = 11.sp, color = Color.White)
                            }
                        }
                    }
                }

                // TAB 1: AUTOMATED CUSTOMER RETENTION ENGINE ("سیستم مشتری برگردان")
                1 -> {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Surface(
                            color = SageGreenSubtle,
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, SageGreen.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Autorenew, contentDescription = null, tint = SageGreen, modifier = Modifier.size(28.dp))
                                Spacer(Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = if (isPersian) "سیستم هوشمند مشتری برگردان سالن‌یار" else "SalonYar Client Retention Engine",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = CharcoalPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = if (isPersian) "بازگشت خودکار مشتریان قبلی بدون نیاز به پیگیری دستی! افزایش ۴۰ درصدی درآمد ماهانه سالن با یادآوری دوره‌ای خدمات."
                                        else "Automated client return cycles: Hair color touchup, nail refill, skincare hydration & anniversary coupons.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = CharcoalSecondary
                                    )
                                }
                            }
                        }

                        // Simulated Toast Message Output
                        if (activeToastMessage != null) {
                            Surface(
                                color = TerracottaSubtle,
                                shape = RoundedCornerShape(10.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaAccent)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.MarkChatRead, contentDescription = null, tint = TerracottaAccent)
                                        Spacer(Modifier.width(8.dp))
                                        Text(text = activeToastMessage!!, fontSize = 11.sp, color = CharcoalPrimary)
                                    }
                                    IconButton(
                                        onClick = { activeToastMessage = null },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Close, contentDescription = null, tint = CharcoalSecondary, modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }

                        Text(
                            text = if (isPersian) "سناریوهای فعال بازگشت خودکار مشتری:" else "Active Client Return Cycles:",
                            style = MaterialTheme.typography.titleMedium,
                            color = CharcoalPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        retentionCampaigns.forEach { campaign ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Default.Alarm, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(18.dp))
                                            Spacer(Modifier.width(6.dp))
                                            Text(
                                                text = if (isPersian) campaign.titleFa else campaign.titleEn,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = CharcoalPrimary
                                            )
                                        }

                                        Surface(
                                            color = SageGreen,
                                            shape = RoundedCornerShape(6.dp)
                                        ) {
                                            Text(
                                                text = "${campaign.discountPercent}٪ تخفیف",
                                                color = Color.White,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }

                                    Text(
                                        text = "متن پیام هوشمند: \"${if (isPersian) campaign.sampleMessageFa else campaign.sampleMessageEn}\"",
                                        fontSize = 11.sp,
                                        color = CharcoalSecondary
                                    )

                                    Button(
                                        onClick = {
                                            activeToastMessage = "پیام مشتری برگردان برای سناریوی (${campaign.titleFa}) با نام ${pitchProfile.salonName} به طور شبیه‌سازی‌شده ارسال شد ✓"
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = WarmSurface),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaAccent),
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(Icons.Default.Send, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(14.dp))
                                        Spacer(Modifier.width(6.dp))
                                        Text(
                                            text = if (isPersian) "تست ارسال پیام هوشمند مشتری برگردان" else "Simulate Sending Retention Message",
                                            fontSize = 11.sp,
                                            color = TerracottaAccent,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // TAB 2: VISUAL FEATURE DEMOS WITH EXAMPLES & BEFORE/AFTER PHOTOS
                2 -> {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            color = TerracottaSubtle,
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaAccent.copy(alpha = 0.3f))
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.PhotoLibrary, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(28.dp))
                                Spacer(Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = if (isPersian) "کاتالوگ تصویری امکانات سالن‌یار برای بازاریابی" else "Visual Feature Demos for Salon Owners",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = CharcoalPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = if (isPersian) "صاحب سالن قبل از خرید، تمام پروها، فتوشاپ‌های دونفره عروس و داماد، شینیون و مشاوره قبل و بعد را با عکس‌های واقعی می‌بیند."
                                        else "Real photo visual demos: Bride & groom scenes, virtual makeup, hair updo & before/after compare.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = CharcoalSecondary
                                    )
                                }
                            }
                        }

                        // DEMO CARD 1: BRIDE & GROOM VIRTUAL FITTING & PHOTOSHOP SCENES
                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                            shape = RoundedCornerShape(14.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(
                                    text = if (isPersian) "۱. پرو و فتوشاپ دونفره عروس و داماد در صحنه‌های تالار و ماشین" else "1. Bride & Groom Virtual Fitting in Wedding Scenes",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = CharcoalPrimary,
                                    fontWeight = FontWeight.Bold
                                )

                                Image(
                                    painter = painterResource(R.drawable.salonyar_car_dance_1792912000489),
                                    contentDescription = "Bride Groom Scenes",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                )

                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SageGreen, modifier = Modifier.size(16.dp))
                                        Spacer(Modifier.width(6.dp))
                                        Text(if (isPersian) "سوار شدن به ماشین عروس (Car Scene): فتوشاپ دونفره با لباس مزون." else "Bridal Car Scene Fitting", fontSize = 11.sp, color = CharcoalPrimary)
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SageGreen, modifier = Modifier.size(16.dp))
                                        Spacer(Modifier.width(6.dp))
                                        Text(if (isPersian) "رقص روی سن گرد تالار (Stage Dance): تصویرسازی رقص با نورپردازی سالن." else "Stage Dance Simulation", fontSize = 11.sp, color = CharcoalPrimary)
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SageGreen, modifier = Modifier.size(16.dp))
                                        Spacer(Modifier.width(6.dp))
                                        Text(if (isPersian) "ورود باشکوه به تالار (Grand Entry): پرو لباس و آرایش همراه با تشویق مهمانان." else "Grand Entrance Scene", fontSize = 11.sp, color = CharcoalPrimary)
                                    }
                                }
                            }
                        }

                        // DEMO CARD 2: MAKEUP, HAIRSTYLE UPDO (شینیون) & BRIDAL DRESS
                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                            shape = RoundedCornerShape(14.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(
                                    text = if (isPersian) "۲. شینیون، آرایش مجازی و پرو ۵۰ لباس عروس در سالن" else "2. Virtual Hair Updo, Makeup & 50 Bridal Gowns",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = CharcoalPrimary,
                                    fontWeight = FontWeight.Bold
                                )

                                Image(
                                    painter = painterResource(R.drawable.salonyar_bride_hall_1792911979418),
                                    contentDescription = "Bridal Hall Entry",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                )

                                Text(
                                    text = if (isPersian) "عروس بدون تحمل فرسودگی پرو سنتی، تنها با یک عکس در سالن می‌تواند تمام مدل‌های شینیون، آرایش و ۵۰ لباس عروس را روی چهره و قامت خود پرو کند!"
                                    else "Try on 50 bridal dresses, hairstyles & makeup instantly from a single portrait photo!",
                                    fontSize = 11.sp,
                                    color = CharcoalSecondary
                                )
                            }
                        }

                        // DEMO CARD 3: DOUBTFUL CLIENT CONSULTATION ADVISOR ("مشاوره مشتریان دودل و مردد")
                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                            shape = RoundedCornerShape(14.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.HelpOutline, contentDescription = null, tint = TerracottaAccent)
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        text = if (isPersian) "۳. سیستم مشاوره تصویری مشتریان دودل و مردد" else "3. Doubtful Client Consultation Advisor",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = CharcoalPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Surface(
                                    color = WarmSurface,
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
                                ) {
                                    Text(
                                        text = if (isPersian) "پاسخ به سوال همیشگی مشتریان: «چه مدلی بزنم؟ کوپ کوتاه یا بلند؟ چه رنگ مویی بهم میاد؟» با پیش‌نمایش تصویری روی چهره خود مشتری قبل از کوتاه کردن!"
                                        else "Solve client doubt when they ask 'What haircut suits me? Short or long? What hair color?'. Show live preview before cutting!",
                                        fontSize = 11.sp,
                                        color = CharcoalPrimary,
                                        modifier = Modifier.padding(10.dp)
                                    )
                                }

                                Text(
                                    text = if (isPersian) "انتخاب مدل مو برای تست پیش‌نمایش:" else "Select Hairstyle for Live Preview Test:",
                                    fontSize = 11.sp,
                                    color = CharcoalSecondary,
                                    fontWeight = FontWeight.Bold
                                )

                                val hairStyles = listOf(
                                    "کوپ مصری لایه‌دار + رنگ بالیاژ نسکافه‌ای",
                                    "مدل شگی مدرن + لایت کنفی صدف",
                                    "کوپ پیکسی کوتاه فانتزی + آمبره دودی",
                                    "شینیون خطی فرانسوی + هایلایت طلایی"
                                )

                                hairStyles.forEach { style ->
                                    val isSelected = selectedHairStyleAdvisor == style
                                    Surface(
                                        color = if (isSelected) TerracottaSubtle else WarmSurface,
                                        shape = RoundedCornerShape(8.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) TerracottaAccent else WarmBorder),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { selectedHairStyleAdvisor = style }
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(10.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(
                                                selected = isSelected,
                                                onClick = { selectedHairStyleAdvisor = style },
                                                colors = RadioButtonDefaults.colors(selectedColor = TerracottaAccent)
                                            )
                                            Spacer(Modifier.width(6.dp))
                                            Text(text = style, fontSize = 11.sp, color = if (isSelected) TerracottaHover else CharcoalPrimary, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                                        }
                                    }
                                }
                            }
                        }

                        // DEMO CARD 4: INTERACTIVE BEFORE & AFTER COMPARISON SHOWCASE
                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                            shape = RoundedCornerShape(14.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = if (isPersian) "۴. مقایسه تصویری قبل و بعد (Before / After)" else "4. Interactive Before & After Showcase",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = CharcoalPrimary,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Surface(
                                        color = if (showAfterState) TerracottaAccent else CharcoalSecondary,
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier.clickable { showAfterState = !showAfterState }
                                    ) {
                                        Text(
                                            text = if (showAfterState) "حالت: بعد از پرو و شینیون (AFTER)" else "حالت: قبل از آرایش (BEFORE)",
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Image(
                                    painter = painterResource(R.drawable.salonyar_before_after_1792911989794),
                                    contentDescription = "Before After Compare",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(190.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                )

                                Button(
                                    onClick = { showAfterState = !showAfterState },
                                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.Compare, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        text = if (isPersian) "سویچ تعاملی بین قبل و بعد پرو در حضور مشتری سالن" else "Toggle Interactive Before/After View",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
