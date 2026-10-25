package com.example.ui.decormate

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
import com.example.ui.theme.*

data class DecorItem(
    val id: String,
    val titleFa: String,
    val categoryFa: String,
    val unitLabelFa: String,
    val basePriceFa: String,
    val specsFa: String,
    val unionFormulaNoteFa: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecorMateScreen(
    isPersian: Boolean,
    onNavigateBack: () -> Unit,
    onNavigateToStudio: () -> Unit
) {
    BackHandler { onNavigateBack() }

    val context = LocalContext.current
    var activeTab by remember { mutableStateOf(0) } // 0 = Wedding Stages, 1 = Luxury Interior, 2 = Metrage Calculator
    var activeToastMessage by remember { mutableStateOf<String?>(null) }

    // Live metrage calculator state
    var selectedItemIndex by remember { mutableIntStateOf(0) }
    var inputMeters by remember { mutableStateOf("15") }

    // Decor categories from Decore-mate decorData.ts
    val decorItems = remember {
        listOf(
            DecorItem(
                id = "wedding_stage_flower",
                titleFa = "جایگاه اشرافی عروس و داماد + آرک گل طبیعی ارکیده و لیلیوم",
                categoryFa = "دکوراسیون تشریفات و عقد",
                unitLabelFa = "پکیج کامل جایگاه",
                basePriceFa = "۳۵,۰۰۰,۰۰۰ تومان",
                specsFa = "استیج مخمل با حاشیه آینه برنزی، ۳ آرک هلالی گل طبیعی و شمعدان‌های کریستال بوهمیا",
                unionFormulaNoteFa = "طراحی اختصاصی با گل‌های تازه روز و نورپردازی زاویه‌دار عکاسی آتلیه"
            ),
            DecorItem(
                id = "sofreh_aghd_crystal",
                titleFa = "سفره عقد سلطنتی نقره و آینه برنز با ظروف دست‌ساز آنتیک",
                categoryFa = "دکوراسیون تشریفات و عقد",
                unitLabelFa = "سفره کامل",
                basePriceFa = "۲۸,۰۰۰,۰۰۰ تومان",
                specsFa = "جام عسل کریستال، قرآن طلاکوب با رحل منبت، بادام و گردوی مرواریددوزی شده",
                unionFormulaNoteFa = "چیدمان سفره بر روی آب یا آینه با امکان انتخاب تم سنتی یا مدرن"
            ),
            DecorItem(
                id = "walkin_closet",
                titleFa = "کلوزت‌روم اشرافی (Walk-in Closet) + جزیره جواهرات و ساعت",
                categoryFa = "معماری داخلی لوکس",
                unitLabelFa = "متر مربع",
                basePriceFa = "۸,۹۰۰,۰۰۰ تومان",
                specsFa = "درب‌های شیشه‌ای فریم شامپاینی، لاین نوری سنسوردار ۳۰۰۰ کلوین، رگال آسانسوری و کشوی مخمل جواهرات",
                unionFormulaNoteFa = "شامل ریل‌های مخفی بلوم اتریش و فریم آلومینیوم آنودایز طلایی"
            ),
            DecorItem(
                id = "cabinet_enzo",
                titleFa = "کابینت انزو پولیشی ۲۵ میل و نئوکلاسیک رنگ پلی‌اورتان ضدخش",
                categoryFa = "معماری داخلی لوکس",
                unitLabelFa = "متر طول استاندارد",
                basePriceFa = "۱۴,۵۰۰,۰۰۰ تومان",
                specsFa = "۶۰٪ یونیت زمینی + ۴۰٪ هوایی + صفحه سنگ کوارتز یا کورین + لولاهای آرام‌بند بلوم",
                unionFormulaNoteFa = "فرمول محاسبه رسمی اتحادیه درودگران با ضریب ۱.۰ برای یونیت‌های استاندارد"
            ),
            DecorItem(
                id = "tv_wall_thermo",
                titleFa = "دیوارکوب لوکس تی‌وی وال (TV Wall) با چوب گردو و سنگ اسلب نورگذر",
                categoryFa = "معماری داخلی لوکس",
                unitLabelFa = "متر مربع مساحت",
                basePriceFa = "۶,۲۰۰,۰۰۰ تومان",
                specsFa = "ترمووود فنلاندی گرید A + لاین نوری مگنتی توکار + باکس معلق کنسول صوتی",
                unionFormulaNoteFa = "زیرسازی چوب روس اشباع‌شده با ۱۰ سال ضمانت کتبی ضدتابیدگی"
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
                                text = "سامانه دکورمیت VIP (DecorMate)",
                                color = CharcoalPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(Modifier.width(6.dp))
                            Surface(color = TerracottaAccent, shape = RoundedCornerShape(4.dp)) {
                                Text("همیار دکوراسیون", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }
                        Text(
                            text = "جایگاه عقد، گل‌آرایی تالار، کلوزت‌روم، کابینت انزو و محاسبه‌گر متراژ",
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
                        painter = painterResource(R.drawable.decormate_wedding_stage_1792912657518),
                        contentDescription = "DecorMate Banner",
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
                                text = "طراحی ۳ بعدی جایگاه عروس و دکوراسیون داخلی لوکس",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "دکورمیت: مهندسی چوب، گل‌آرایی و سازه‌های تشریفاتی",
                                color = Color.White,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "پرو تعاملی جایگاه با لباس عروس و محاسبه شفاف قیمت بر اساس فرمول اتحادیه",
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
                        Triple(0, "جایگاه عقد و تالار", Icons.Default.Celebration),
                        Triple(1, "معماری و دکوراسیون", Icons.Default.Weekend),
                        Triple(2, "محاسبه‌گر متراژ", Icons.Default.Calculate)
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
                // TAB 0: WEDDING STAGES & SOFREH AGHD
                0 -> {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        decorItems.filter { it.categoryFa.contains("عقد") }.forEach { item ->
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
                                        Text(text = item.titleFa, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary, modifier = Modifier.weight(1f))
                                        Surface(color = TerracottaAccent, shape = RoundedCornerShape(4.dp)) {
                                            Text(text = item.basePriceFa, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                        }
                                    }

                                    Text(text = "مشخصات متریال: ${item.specsFa}", fontSize = 11.sp, color = CharcoalSecondary)
                                    Text(text = item.unionFormulaNoteFa, fontSize = 10.sp, color = SageGreen, fontWeight = FontWeight.Medium)

                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = { onNavigateToStudio() },
                                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(14.dp))
                                            Spacer(Modifier.width(4.dp))
                                            Text("پرو مجازی جایگاه با عروس", fontSize = 10.sp, color = Color.White)
                                        }

                                        Button(
                                            onClick = {
                                                activeToastMessage = "درخواست رزرو جایگاه (${item.titleFa}) ثبت شد ✓"
                                            },
                                            colors = ButtonDefaults.buttonColors(containerColor = SageGreen),
                                            modifier = Modifier.weight(1f),
                                            shape = RoundedCornerShape(8.dp)
                                        ) {
                                            Text("استعلام و رزرو", fontSize = 10.sp, color = Color.White)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // TAB 1: LUXURY INTERIOR & CLOSETS
                1 -> {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        decorItems.filter { !it.categoryFa.contains("عقد") }.forEach { item ->
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
                                        Text(text = item.titleFa, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary, modifier = Modifier.weight(1f))
                                        Surface(color = SageGreen, shape = RoundedCornerShape(4.dp)) {
                                            Text(text = item.basePriceFa, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                                        }
                                    }

                                    Text(text = "مشخصات فنی: ${item.specsFa}", fontSize = 11.sp, color = CharcoalSecondary)
                                    Text(text = "فرمول اتحادیه: ${item.unionFormulaNoteFa}", fontSize = 10.sp, color = TerracottaHover)
                                }
                            }
                        }
                    }
                }

                // TAB 2: LIVE METRAGE & UNION CALCULATOR
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
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = "محاسبه‌گر متراژ و پیش‌فاکتور رسمی دکورمیت (بر اساس فرمول اتحادیه)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = "با وارد کردن متراژ، قیمت دقیق نهایی به همراه یراق‌آلات، دستمزد نصب و هزینه حمل به طور خودکار استخراج می‌شود.",
                                    fontSize = 11.sp,
                                    color = CharcoalSecondary
                                )
                            }
                        }

                        Text(text = "انتخاب آیتم دکوراسیون:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)

                        val interiorItems = decorItems.filter { !it.categoryFa.contains("عقد") }
                        interiorItems.forEachIndexed { index, item ->
                            val isSelected = selectedItemIndex == index
                            Surface(
                                color = if (isSelected) TerracottaSubtle else WarmSurfaceSecondary,
                                shape = RoundedCornerShape(8.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) TerracottaAccent else WarmBorder),
                                modifier = Modifier.fillMaxWidth().clickable { selectedItemIndex = index }
                            ) {
                                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(selected = isSelected, onClick = { selectedItemIndex = index }, colors = RadioButtonDefaults.colors(selectedColor = TerracottaAccent))
                                    Spacer(Modifier.width(6.dp))
                                    Column {
                                        Text(text = item.titleFa, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                        Text(text = "قیمت پایه: ${item.basePriceFa} بر حسب ${item.unitLabelFa}", fontSize = 10.sp, color = CharcoalSecondary)
                                    }
                                }
                            }
                        }

                        OutlinedTextField(
                            value = inputMeters,
                            onValueChange = { inputMeters = it.filter { ch -> ch.isDigit() } },
                            label = { Text("متراژ مورد نظر (متر)") },
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = TerracottaAccent, unfocusedBorderColor = WarmBorder),
                            modifier = Modifier.fillMaxWidth()
                        )

                        val meters = inputMeters.toIntOrNull() ?: 1
                        val selectedDecor = interiorItems.getOrNull(selectedItemIndex) ?: interiorItems[0]
                        val unitPriceMillions = when (selectedDecor.id) {
                            "walkin_closet" -> 8.9
                            "cabinet_enzo" -> 14.5
                            "tv_wall_thermo" -> 6.2
                            else -> 5.0
                        }
                        val totalPrice = (meters * unitPriceMillions).toInt()

                        Card(
                            colors = CardDefaults.cardColors(containerColor = WarmSurface),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaAccent)
                        ) {
                            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(text = "پیش‌فاکتور برآوردی رسمی:", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary)
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "متراژ محاسبه‌شده:", fontSize = 11.sp, color = CharcoalSecondary)
                                    Text(text = "$meters ${selectedDecor.unitLabelFa}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(text = "مبلغ کل تقریبی:", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                                    Text(text = "$totalPrice,۰۰۰,۰۰۰ تومان", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TerracottaAccent)
                                }
                                Text(text = "شامل ضمانت‌نامه کتبی ۱۰ ساله + طراحی 3D Max رایگان قبل از برش", fontSize = 10.sp, color = SageGreen)
                            }
                        }
                    }
                }
            }
        }
    }
}
