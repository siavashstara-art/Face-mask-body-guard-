package com.example.ui.maisonyar

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.*

data class MaisonGarmentItem(
    val id: String,
    val titleFa: String,
    val maisonName: String,
    val priceRentFa: String,
    val priceSaleFa: String,
    val addressFa: String,
    val phone: String,
    val category: String, // "BRIDAL_GOWN", "GROOM_SUIT", "HOME_MAISON"
    val isHomeMaison: Boolean = false,
    val privacyShieldApplied: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaisonYarScreen(
    isPersian: Boolean,
    onNavigateBack: () -> Unit,
    onNavigateToStudio: () -> Unit
) {
    BackHandler { onNavigateBack() }

    val context = LocalContext.current

    // Active Tab: 0 = B2B Boutique Catalog, 1 = Home-Based Maison Privacy & Body Reshaping, 2 = Cross-Ecosystem Revenue Sharing
    var activeTab by remember { mutableStateOf(0) }

    // Sample Maison Inventory Items
    var maisonInventory by remember {
        mutableStateOf(
            listOf(
                MaisonGarmentItem(
                    id = "g1",
                    titleFa = "لباس عروس پرنسسی اسکارلت لایه‌دار با تور فرانسوی",
                    maisonName = "مزون رویال پرنسس پالاس",
                    priceRentFa = "۱۲,۵۰۰,۰۰۰ تومان",
                    priceSaleFa = "۳۸,۰۰۰,۰۰۰ تومان",
                    addressFa = "تهران، جردن، بالاتر از اسفندیار، برج رویال، طبقه ۳",
                    phone = "۰۹۱۲۱۱۱۱۱۱۱ - ۰۲۱۲۲۰۲۰۰۰",
                    category = "BRIDAL_GOWN"
                ),
                MaisonGarmentItem(
                    id = "g2",
                    titleFa = "کت‌وشلوار تاکسیدو دامادی یقه آرشال ابریشم ایتالیایی",
                    maisonName = "مزون جنتلمن هئوس",
                    priceRentFa = "۶,۰۰۰,۰۰۰ تومان",
                    priceSaleFa = "۱۸,۵۰۰,۰۰۰ تومان",
                    addressFa = "تهران، فرشته، خیابان بیدار، پلاک ۲۴",
                    phone = "۰۹۱۲۲۲۲۲۲۲۲",
                    category = "GROOM_SUIT"
                ),
                MaisonGarmentItem(
                    id = "g3",
                    titleFa = "لباس شب و نامزدی گیپور دست‌دوز (مزون خانگی با حریم اندام)",
                    maisonName = "مزون خانگی بانو مریم",
                    priceRentFa = "۴,۵۰۰,۰۰۰ تومان",
                    priceSaleFa = "۱۲,۰۰۰,۰۰۰ تومان",
                    addressFa = "تهران، نازی‌آباد (تحویل حضوری با هماهنگی)",
                    phone = "۰۹۱۲۳۳۳۳۳۳۳",
                    category = "HOME_MAISON",
                    isHomeMaison = true,
                    privacyShieldApplied = true
                )
            )
        )
    }

    // Home-Based Maison Privacy Sliders State
    var faceShieldMask by remember { mutableStateOf("گل رز طبیعی روی چهره (Dual Roses)") }
    var bodySlimFactor by remember { mutableStateOf(0.75f) } // 0.5 to 1.0
    var waistWidthFactor by remember { mutableStateOf(0.80f) }
    var isAnonymized by remember { mutableStateOf(true) }

    // Add new garment dialog state
    var showAddGarmentDialog by remember { mutableStateOf(false) }
    var newTitle by remember { mutableStateOf("") }
    var newMaisonName by remember { mutableStateOf("") }
    var newRentPrice by remember { mutableStateOf("") }
    var newPhone by remember { mutableStateOf("") }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            // Garment image selected
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isPersian) "سامانه مزون‌یار اختصاصی (MaisonYar)" else "MaisonYar Bridal Hub",
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
                                    text = if (isPersian) "فروش و اجاره B2B" else "Bridal B2B",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = if (isPersian) "ثبت ژورنال مزون‌ها + مزون خانگی با استتار اندام + پورسانت ارجاع" else "Maison Catalog + Home Privacy Shield + Referral Revenue",
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
            // HERO BANNER FOR MAISONYAR
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
                        painter = painterResource(R.drawable.maisonyar_catalog_1792912622872),
                        contentDescription = "Maison Catalog Hero",
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
                        Surface(
                            color = TerracottaAccent,
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = if (isPersian) "شبکه سراسری مزون‌های عروس و داماد" else "Bridal & Suit Network",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        Column {
                            Text(
                                text = if (isPersian) "مزون‌های طرف قرارداد و ثبت ژورنال" else "Maison B2B Inventory & Referral",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isPersian) "عروس خانم‌ها در سالن آرایشگاه لباس شما را پرو مجازی می‌کنند و مستقیماً جهت فروش/اجاره به شما ارجاع داده می‌شوند."
                                else "Salons fit brides virtually with your dresses & refer buyers directly to your boutique!",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // NAVIGATION TABS
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
                        Triple(0, if (isPersian) "کاتالوگ و ثبت لباس" else "Catalog", Icons.Default.Checkroom),
                        Triple(1, if (isPersian) "مزون خانگی و حریم اندام" else "Home Privacy", Icons.Default.Shield),
                        Triple(2, if (isPersian) "درآمد ارجاع دوطرفه" else "Revenue Share", Icons.Default.MonetizationOn)
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

            when (activeTab) {
                // TAB 0: B2B BOUTIQUE CATALOG & UPLOAD
                0 -> {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Button(
                            onClick = {
                                photoPickerLauncher.launch(
                                    androidx.activity.result.PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                                showAddGarmentDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = if (isPersian) "ثبت لباس عروس یا کت‌وشلوار جدید در گالری مزون‌ها" else "Upload New Dress / Suit to Maison Catalog",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = if (isPersian) "لباس‌های فعال در آرشیو پرو مجازی سالن‌ها:" else "Active Dresses in Virtual Try-On Catalog:",
                            style = MaterialTheme.typography.titleMedium,
                            color = CharcoalPrimary,
                            fontWeight = FontWeight.Bold
                        )

                        maisonInventory.forEach { garment ->
                            Card(
                                colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
                            ) {
                                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = garment.titleFa,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = CharcoalPrimary,
                                            modifier = Modifier.weight(1f)
                                        )

                                        if (garment.isHomeMaison) {
                                            Surface(color = TerracottaAccent, shape = RoundedCornerShape(4.dp)) {
                                                Text("مزون خانگی (حریم محفوظ)", color = Color.White, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                            }
                                        }
                                    }

                                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                        Text(text = "قیمت اجاره: ${garment.priceRentFa}", fontSize = 11.sp, color = TerracottaAccent, fontWeight = FontWeight.Bold)
                                        Text(text = "قیمت فروش: ${garment.priceSaleFa}", fontSize = 11.sp, color = SageGreen, fontWeight = FontWeight.Bold)
                                    }

                                    Text(text = "نام مزون: ${garment.maisonName} • تلفن: ${garment.phone}", fontSize = 10.sp, color = CharcoalSecondary)
                                    Text(text = "آدرس: ${garment.addressFa}", fontSize = 10.sp, color = CharcoalSecondary)

                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = { onNavigateToStudio() },
                                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(14.dp))
                                            Spacer(Modifier.width(4.dp))
                                            Text("تست پرو روی مشتری سالن", fontSize = 10.sp, color = Color.White)
                                        }

                                        Button(
                                            onClick = {
                                                val sendIntent = Intent().apply {
                                                    action = Intent.ACTION_SEND
                                                    putExtra(Intent.EXTRA_TEXT, "معرفی عروس جهت خرید/اجاره لباس: ${garment.titleFa}\nاز طرف برنامه سالن‌یار به ${garment.maisonName}\nتلفن: ${garment.phone}")
                                                    type = "text/plain"
                                                }
                                                context.startActivity(Intent.createChooser(sendIntent, "ارسال کارت معرفی به مزون"))
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = SageGreen),
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(14.dp))
                                            Spacer(Modifier.width(4.dp))
                                            Text("معرفی عروس به مزون", fontSize = 10.sp, color = Color.White)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // TAB 1: HOME-BASED MAISON PRIVACY & BODY RESHAPING ("مزون خانگی و حریم اندام")
                1 -> {
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
                                Icon(Icons.Default.ShieldMoon, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(32.dp))
                                Spacer(Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = if (isPersian) "سیستم استتار چهره و تغییر نسبت‌های اندام (ویژه مزون‌های خانگی)" else "Home-Based Maison Body & Face Privacy Shield",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = CharcoalPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = if (isPersian) "عزیزانی که در خانه خیاطی و مزون دارند می‌توانند با خیال راحت لباس را تن خود کنند؛ سیستم چهره آن‌ها را کاملاً می‌پوشاند و تناسب اندام را به گونه‌ای چاق/لاغر می‌کند که حتی آشنایان هم متوجه نشوند مدل خود اوست!"
                                        else "Home dressmakers can model their own creations! Face is shielded and body proportions are reshaped so relatives cannot identify the model.",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = CharcoalSecondary
                                    )
                                }
                            }
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
                        ) {
                            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text(
                                    text = if (isPersian) "تنظیمات حریم خصوصی چهره مدل خانگی:" else "Face Shielding Type:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = CharcoalPrimary
                                )

                                val masks = listOf(
                                    "گل رز طبیعی روی چهره (Dual Roses)",
                                    "نقاب بالماسکه و برقع خلیجی",
                                    "بلر و تارکننده سنگین چهره (Heavy Blur)",
                                    "پیکسل‌سازی ضد شناسایی (Pixelate)"
                                )

                                masks.forEach { mask ->
                                    val selected = faceShieldMask == mask
                                    Surface(
                                        color = if (selected) TerracottaSubtle else WarmSurface,
                                        shape = RoundedCornerShape(8.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, if (selected) TerracottaAccent else WarmBorder),
                                        modifier = Modifier.fillMaxWidth().clickable { faceShieldMask = mask }
                                    ) {
                                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                            RadioButton(selected = selected, onClick = { faceShieldMask = mask }, colors = RadioButtonDefaults.colors(selectedColor = TerracottaAccent))
                                            Spacer(Modifier.width(6.dp))
                                            Text(text = mask, fontSize = 11.sp, color = CharcoalPrimary)
                                        }
                                    }
                                }

                                HorizontalDivider(color = WarmBorderSubtle)

                                Text(
                                    text = if (isPersian) "تغییر نسبت اندام (چاق/لاغر کردن برای عدم شناسایی):" else "Body Reshaping & Proportion Adjustment:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = CharcoalPrimary
                                )

                                Text(text = "ضریب ظرافت/لاغری اندام: ${(bodySlimFactor * 100).toInt()}٪", fontSize = 10.sp, color = CharcoalSecondary)
                                Slider(
                                    value = bodySlimFactor,
                                    onValueChange = { bodySlimFactor = it },
                                    valueRange = 0.5f..1.2f,
                                    colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent)
                                )

                                Text(text = "تعدیل انحنای دور کمر و پهلو: ${(waistWidthFactor * 100).toInt()}٪", fontSize = 10.sp, color = CharcoalSecondary)
                                Slider(
                                    value = waistWidthFactor,
                                    onValueChange = { waistWidthFactor = it },
                                    valueRange = 0.6f..1.1f,
                                    colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent)
                                )

                                Button(
                                    onClick = { onNavigateToStudio() },
                                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(Icons.Default.AutoFixHigh, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        text = if (isPersian) "تست زنده عکاسی خانگی با استتار چهره و اندام" else "Test Live Home Photography with Privacy",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                // TAB 2: CROSS-ECOSYSTEM REVENUE SHARING ("درآمد ارجاع دوطرفه")
                2 -> {
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
                                Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = SageGreen, modifier = Modifier.size(32.dp))
                                Spacer(Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = if (isPersian) "چرخه درآمدی متقابل (مزون ↔ سالن زیبایی ↔ برنامه)" else "Mutual Referral Profit Sharing Cycle",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = CharcoalPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = if (isPersian) "مزون‌ها عروس‌ها را به سالن‌های همکار معرفی می‌کنند و سالن‌ها لباس‌های مزون را پرو مجازی می‌کنند. هر دو طرف از اجاره/فروش لباس و خدمات آرایشگاه سود پورسانتی می‌برند!"
                                        else "Dual-sided referral loop: Salons refer brides for dress rental & get commission; Maisons refer brides to beauty salons!",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = CharcoalSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Add Garment Dialog
    if (showAddGarmentDialog) {
        AlertDialog(
            onDismissRequest = { showAddGarmentDialog = false },
            title = { Text(if (isPersian) "ثبت لباس جدید در کاتالوگ مزون" else "Add New Garment to Catalog") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(value = newTitle, onValueChange = { newTitle = it }, label = { Text("عنوان لباس / کت‌وشلوار") })
                    OutlinedTextField(value = newMaisonName, onValueChange = { newMaisonName = it }, label = { Text("نام مزون") })
                    OutlinedTextField(value = newRentPrice, onValueChange = { newRentPrice = it }, label = { Text("قیمت اجاره (تومان)") })
                    OutlinedTextField(value = newPhone, onValueChange = { newPhone = it }, label = { Text("تلفن هماهنگی") })
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTitle.isNotBlank()) {
                            val newItem = MaisonGarmentItem(
                                id = "g_${System.currentTimeMillis()}",
                                titleFa = newTitle,
                                maisonName = newMaisonName.ifBlank { "مزون اختصاصی" },
                                priceRentFa = "${newRentPrice.ifBlank { "۱۰,۰۰۰,۰۰۰" }} تومان",
                                priceSaleFa = "توافقی",
                                addressFa = "ثبت‌شده توسط بازاریاب در محل",
                                phone = newPhone.ifBlank { "۰۹۱۲۰۰۰۰۰۰۰" },
                                category = "BRIDAL_GOWN"
                            )
                            maisonInventory = listOf(newItem) + maisonInventory
                        }
                        showAddGarmentDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent)
                ) {
                    Text("ثبت در آرشیو", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddGarmentDialog = false }) {
                    Text("انصراف")
                }
            }
        )
    }
}
