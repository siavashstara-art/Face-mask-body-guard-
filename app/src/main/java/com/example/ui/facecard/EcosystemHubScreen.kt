package com.example.ui.facecard

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.R
import com.example.engine.EcosystemMasterController
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun EcosystemHubScreen(
    ecosystemController: EcosystemMasterController,
    maisonRepo: com.example.engine.MaisonBoutiqueRepository,
    salonSession: SalonYarClientSession,
    sakhtemanPass: SakhtemanYarAccessPass,
    marketingStats: MarketingAcademyLeadStats,
    onSimulateDoorUnlock: () -> Unit = {},
    onNavigateToStudio: () -> Unit = {},
    onNavigateToScreen: (String) -> Unit = {}
) {
    val systemStatus by ecosystemController.systemStatus.collectAsStateWithLifecycle()
    val modules by ecosystemController.modules.collectAsStateWithLifecycle()
    val lastMiniTryOn by ecosystemController.lastMiniTryOn.collectAsStateWithLifecycle()
    val lastEnvironmentComposite by ecosystemController.lastEnvironmentComposite.collectAsStateWithLifecycle()
    val lastCinematicHallScene by ecosystemController.lastCinematicHallScene.collectAsStateWithLifecycle()
    val auditLogs by ecosystemController.recentAuditLogs.collectAsStateWithLifecycle()

    var isDoorUnlocked by remember { mutableStateOf(false) }

    // Sub-tab within Ecosystem: Default to Super App Portals
    var activeSubTab by remember { mutableStateOf("super_app_portals") } // "super_app_portals", "master_modules", "maison_network", "portals"

    // Mini Try-On UI State
    var selectedGender by remember { mutableStateOf("girl") } // "girl", "boy"
    var selectedItemIndex by remember { mutableIntStateOf(1) }

    // Environment Composite UI State
    var selectedCompositeMode by remember { mutableStateOf("salon") } // "salon", "car"
    var selectedSalonTarget by remember { mutableStateOf(EcosystemCatalogData.salonPresets[0]) }
    var selectedCarTarget by remember { mutableStateOf(EcosystemCatalogData.luxuryCarPresets[0]) }

    // Cinematic Hall UI State
    var selectedSceneType by remember { mutableStateOf("grand_entry") } // "grand_entry", "first_dance"
    var selectedHall by remember { mutableStateOf(EcosystemCatalogData.weddingHalls[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WarmBackground)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Master Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "اکوسیستم یکپارچه توانا (FaceCard B2B)",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = CharcoalPrimary
                )
                Text(
                    text = "مستر کنترلر ماژول‌های مینی‌عروس، محیط سالن، خودروهای رؤیایی و تالار سینمایی",
                    fontSize = 11.sp,
                    color = CharcoalSecondary
                )
            }
            Surface(
                color = SageGreenSubtle,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, SageGreen.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(SageGreen))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "V2.0 فعال",
                        color = SageGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Sub-Tab Switcher
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(WarmSurfaceSecondary, RoundedCornerShape(12.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable { activeSubTab = "super_app_portals" },
                color = if (activeSubTab == "super_app_portals") TerracottaAccent else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "سوپراپلیکیشن اصناف",
                    color = if (activeSubTab == "super_app_portals") Color.White else CharcoalPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable { activeSubTab = "master_modules" },
                color = if (activeSubTab == "master_modules") TerracottaAccent else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "ماژول‌های B2B",
                    color = if (activeSubTab == "master_modules") Color.White else CharcoalPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            Surface(
                modifier = Modifier
                    .weight(1.1f)
                    .clickable { activeSubTab = "maison_network" },
                color = if (activeSubTab == "maison_network") TerracottaAccent else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "شبکه مزون‌ها",
                    color = if (activeSubTab == "maison_network") Color.White else CharcoalPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clickable { activeSubTab = "portals" },
                color = if (activeSubTab == "portals") TerracottaAccent else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "سامانه‌های سالن/املاک",
                    color = if (activeSubTab == "portals") Color.White else CharcoalPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        if (activeSubTab == "super_app_portals") {
            // Super App Specialty Trade Portals Section
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Surface(
                    color = TerracottaSubtle,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, TerracottaAccent.copy(alpha = 0.3f))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Store, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(32.dp))
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "سوپراپلیکیشن تخصصی اصناف زیبایی و تشریفات",
                                style = MaterialTheme.typography.titleSmall,
                                color = CharcoalPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "درگاه‌های اختصاصی سالن‌های زیبایی، مزون‌ها، تالارها، دکوراسیون، کلینیک‌ها و دندانپزشکان زیبایی با ارجاع متقابل و درآمد مشترک.",
                                style = MaterialTheme.typography.bodySmall,
                                color = CharcoalSecondary
                            )
                        }
                    }
                }

                // 1. SALONYAR PORTAL CARD
                Card(
                    colors = CardDefaults.cardColors(containerColor = WarmSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("۱. سامانه سالن‌یار (سالن‌های زیبایی و آرایشگاه‌ها)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary)
                            Surface(color = TerracottaAccent, shape = RoundedCornerShape(4.dp)) {
                                Text("فعال در منو", color = Color.White, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }
                        Image(
                            painter = painterResource(R.drawable.salonyar_banner_1792911967921),
                            contentDescription = "SalonYar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().height(140.dp).clip(RoundedCornerShape(10.dp))
                        )
                        Text("• سیستم مشتری برگردان با یادآوری خودکار ریشه‌گیری مو، کاشت ناخن، پاکسازی پوست و بن سالگرد ازدواج\n• شخصی‌سازی فی‌البداهه بنر سالن برای ویزیتور و بازاریاب در محل\n• مشاوره تصویری مو و شینیون برای مشتریان دودل و مردد با آنالیز فرم چهره", fontSize = 11.sp, color = CharcoalSecondary)
                        Button(
                            onClick = { onNavigateToScreen("salonyar") },
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.ContentCut, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("ورود به استودیوی سالن‌یار", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 2. MAISONYAR PORTAL CARD
                Card(
                    colors = CardDefaults.cardColors(containerColor = WarmSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("۲. سامانه مزون‌یار (لباس عروس، نامزدی و کت‌وشلوار)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary)
                            Surface(color = SageGreen, shape = RoundedCornerShape(4.dp)) {
                                Text("فعال در منو", color = Color.White, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }
                        Image(
                            painter = painterResource(R.drawable.maisonyar_catalog_1792912622872),
                            contentDescription = "MaisonYar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().height(140.dp).clip(RoundedCornerShape(10.dp))
                        )
                        Text("• ثبت ژورنال B2B لباس‌های عروس و کت‌وشلوار جهت اجاره و فروش\n• مزون خانگی با استتار چهره (گل رز/بلر) و تغییر نسبت‌های اندام (چاق/لاغر) جهت حفظ حریم خصوصی مدل خانگی\n• شبکه ارجاع دوطرفه عروس‌ها بین سالن و مزون با پورسانت مشترک", fontSize = 11.sp, color = CharcoalSecondary)
                        Button(
                            onClick = { onNavigateToScreen("maisonyar") },
                            colors = ButtonDefaults.buttonColors(containerColor = SageGreen),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Checkroom, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("ورود به استودیوی مزون‌یار", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 3. TALARYAR & VENUE PORTAL CARD
                Card(
                    colors = CardDefaults.cardColors(containerColor = WarmSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("۳. سامانه تالاریار و تشریفات (تالارها، باغ‌ها و خودرو عروس)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary)
                        Image(
                            painter = painterResource(R.drawable.salonyar_car_dance_1792912000489),
                            contentDescription = "TalarYar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().height(140.dp).clip(RoundedCornerShape(10.dp))
                        )
                        Text("• پرو و فتوشاپ دونفره عروس و داماد در صحنه سوار شدن به ماشین عروس، سن گرد تالار و ورود باشکوه\n• تقویم رزرو هوشمند سانس‌های تالار و کاتالوگ تشریفات غذا و پذیرایی", fontSize = 11.sp, color = CharcoalSecondary)
                        Button(
                            onClick = { onNavigateToScreen("talaryar") },
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Celebration, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("ورود به استودیوی تالاریار و تاریخ‌های طلایی", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 4. DECORMATE PORTAL CARD
                Card(
                    colors = CardDefaults.cardColors(containerColor = WarmSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("۴. سامانه دکورمیت (DecorMate - دکوراسیون، گل‌آرایی و سفره عقد)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary)
                        Image(
                            painter = painterResource(R.drawable.decormate_wedding_stage_1792912657518),
                            contentDescription = "DecorMate",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().height(140.dp).clip(RoundedCornerShape(10.dp))
                        )
                        Text("• طراحی ۳ بعدی و پرو تعاملی جایگاه عروس و داماد، جایگاه سفره عقد، آرک‌های گل طبیعی و نورپردازی تالار\n• کاتالوگ دیزاینرهای تشریفات، کلوزت‌روم، کابینت انزو و محاسبه‌گر متراژ", fontSize = 11.sp, color = CharcoalSecondary)
                        Button(
                            onClick = { onNavigateToScreen("decormate") },
                            colors = ButtonDefaults.buttonColors(containerColor = SageGreen),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Weekend, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("ورود به استودیوی دکورمیت VIP", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 5. CLINICYAR PORTAL CARD
                Card(
                    colors = CardDefaults.cardColors(containerColor = WarmSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("۵. سامانه کلینیک‌یار (کلینیک‌های زیبایی، لیزر، پوست و جوانسازی)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary)
                        Image(
                            painter = painterResource(R.drawable.clinic_yar_skincare_1792912645797),
                            contentDescription = "ClinicYar",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().height(140.dp).clip(RoundedCornerShape(10.dp))
                        )
                        Text("• پرونده دیجیتال زیباجویان، یادآور جلسات شارژ لیزر و پاکسازی پوست\n• شبیه‌ساز تزریق ژل، بوتاکس و هیدراتاسیون تخصصی پوست قبل از درمان", fontSize = 11.sp, color = CharcoalSecondary)
                        Button(
                            onClick = { onNavigateToStudio() },
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Face, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("تست پرو و شبیه‌ساز پوست و زیبایی", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 6. DENTAL BEAUTY YAR PORTAL CARD
                Card(
                    colors = CardDefaults.cardColors(containerColor = WarmSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("۶. سامانه دندانپزشکان زیبایی (DentalBeauty Yar - اصلاح طرح لبخند)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary)
                        Image(
                            painter = painterResource(R.drawable.dental_beauty_smile_1792912633400),
                            contentDescription = "DentalBeauty",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().height(140.dp).clip(RoundedCornerShape(10.dp))
                        )
                        Text("• شبیه‌ساز اصلاح طرح لبخند روی عکس واقعی زیباجو قبل از تراش دندان\n• پیش‌نمایش رنگ‌های کامپوزیت و لمینت (سفید هالیوودی، طبیعی، بلیچ) جهت مشاوره دندانپزشک با بیمار", fontSize = 11.sp, color = CharcoalSecondary)
                        Button(
                            onClick = { onNavigateToStudio() },
                            colors = ButtonDefaults.buttonColors(containerColor = SageGreen),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("تست شبیه‌ساز اصلاح طرح لبخند", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 7. TALAYAR PORTAL CARD
                Card(
                    colors = CardDefaults.cardColors(containerColor = WarmSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("۷. سامانه طلایار (سرویس طلای عروس و آینه شمعدان نقره)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary)
                            Surface(color = Color(0xFFD4AF37), shape = RoundedCornerShape(4.dp)) {
                                Text("طلای عروس", color = Color.White, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontWeight = FontWeight.Bold)
                            }
                        }
                        Text("• فرمول زنده و قانونی اتحادیه طلا و جواهر (محاسبه ۹٪ مالیات تنها روی سود و اجرت ساخت)\n• محاسبه‌گر تعویض طلای دست‌دوم با سرویس نو عروس و کسر فاکتوری\n• ژورنال آینه و شمعدان نقره عیار ۸۴۰ و شمش‌های طلای سرمایه‌گذاری با کد QR", fontSize = 11.sp, color = CharcoalSecondary)
                        Button(
                            onClick = { onNavigateToScreen("talayar") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB8860B)),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Diamond, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("ورود به استودیوی طلایار و محاسبه‌گر قیمت", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // 8. FARSHBAZAR PORTAL CARD
                Card(
                    colors = CardDefaults.cardColors(containerColor = WarmSurface),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("۸. سامانه فرش‌بازار (فرش و قالی جهیزیه عروس و داماد)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = CharcoalPrimary)
                            Surface(color = Color(0xFF8B0000), shape = RoundedCornerShape(4.dp)) {
                                Text("جهیزیه خانه نو", color = Color.White, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontWeight = FontWeight.Bold)
                            }
                        }
                        Text("• قالی‌های اصیل دستباف تبریز، اصفهان، کاشان و قشقایی با سرتیفیکیت رج‌شمار\n• شبیه‌ساز پرو تعاملی فرش روی کفپوش سالن پذیرایی خانه نو (پارکت، سرامیک، مرمر)\n• طرح تسهیلات اقساطی جهیزیه ۲۴ ماهه برای زوج‌های معرفی‌شده از تالارها و سالن‌ها", fontSize = 11.sp, color = CharcoalSecondary)
                        Button(
                            onClick = { onNavigateToScreen("farshbazar") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Layers, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("ورود به گالری و شبیه‌ساز فرش‌بازار", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else if (activeSubTab == "master_modules") {
            // ============================================
            // 0. SYSTEM HEALTH & VALIDATION BANNER
            // ============================================
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = SageGreen, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "اعتبارسنجی سیستم (System Validation):",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                        }
                        Text(
                            text = "Clean Architecture v2.0",
                            fontSize = 10.sp,
                            color = CharcoalSecondary
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ModuleHealthBadge(title = "مینی‌استودیو", active = modules.miniStudio)
                        ModuleHealthBadge(title = "پس‌زمینه سالن", active = modules.salonBackground)
                        ModuleHealthBadge(title = "خودروهای رؤیایی", active = modules.dreamyCars)
                        ModuleHealthBadge(title = "تالار سینمایی", active = modules.cinematicHall)
                    }
                }
            }

            // ============================================
            // 1. MINI-BRIDE & PAGEBOY VIRTUAL TRY-ON MODULE
            // ============================================
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmSurface),
                border = BorderStroke(1.dp, WarmBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                                Icon(Icons.Default.ChildCare, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.padding(8.dp))
                            }
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "۱. پرو مجازی مینی‌عروس و ساقدوش کودک",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = "ماژول تخصصی ست نوزاد و کودک با لباس‌های مینیاتوری",
                                    fontSize = 10.sp,
                                    color = CharcoalSecondary
                                )
                            }
                        }

                        Surface(
                            color = TerracottaAccent,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "۴۵ مدل کاتالوگ",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    // Gender Switcher: Girl (25) vs Boy (20)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedGender == "girl",
                            onClick = {
                                selectedGender = "girl"
                                selectedItemIndex = 1
                            },
                            label = { Text("دخترانه: لباس عروس مینیاتوری (۲۵ مدل)", fontSize = 11.sp) },
                            leadingIcon = { Icon(Icons.Default.Female, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = TerracottaAccent,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        FilterChip(
                            selected = selectedGender == "boy",
                            onClick = {
                                selectedGender = "boy"
                                selectedItemIndex = 1
                            },
                            label = { Text("پسرانه: تاکسیدو ساقدوش (۲۰ مدل)", fontSize = 11.sp) },
                            leadingIcon = { Icon(Icons.Default.Male, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = TerracottaAccent,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Horizontal Catalog Selector
                    val currentCatalog = if (selectedGender == "girl") {
                        EcosystemCatalogData.miniBrideGirls
                    } else {
                        EcosystemCatalogData.pageboyBoys
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(currentCatalog) { item ->
                            val isSelected = selectedItemIndex == item.index
                            Surface(
                                color = if (isSelected) TerracottaAccent else WarmSurfaceSecondary,
                                shape = RoundedCornerShape(10.dp),
                                border = BorderStroke(1.dp, if (isSelected) TerracottaAccent else WarmBorder),
                                modifier = Modifier.clickable { selectedItemIndex = item.index }
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "#${item.index}",
                                        color = if (isSelected) Color.White else TerracottaAccent,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = item.styleBadge,
                                        color = if (isSelected) Color.White else CharcoalPrimary,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }

                    // Selected Item Details
                    val activeItem = currentCatalog.getOrNull(selectedItemIndex - 1)
                    if (activeItem != null) {
                        Surface(
                            color = WarmSurfaceSecondary,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = activeItem.titleFa,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = "جزئیات دوخت: ${activeItem.descriptionFa}",
                                    fontSize = 11.sp,
                                    color = CharcoalSecondary
                                )
                            }
                        }
                    }

                    // Process Action Button
                    Button(
                        onClick = {
                            ecosystemController.processMiniTryOn(selectedGender, selectedItemIndex)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("ست کردن مجازی کودک (processMiniTryOn)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }

                    // Result Display
                    if (lastMiniTryOn != null && lastMiniTryOn?.gender == selectedGender) {
                        Surface(
                            color = SageGreenSubtle,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, SageGreen.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SageGreen, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "${lastMiniTryOn?.selectedItem}: ${lastMiniTryOn?.message}",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = SageGreen
                                    )
                                    Text(
                                        text = "وضعیت ماژول: ${lastMiniTryOn?.status} • بدون خطای مقیاس‌گذاری چهره کودک",
                                        fontSize = 9.sp,
                                        color = CharcoalSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ============================================
            // 2. SALON ENVIRONMENT & DREAMY CAR SCENARIOS
            // ============================================
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmSurface),
                border = BorderStroke(1.dp, WarmBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                                Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.padding(8.dp))
                            }
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "۲. محیط لوکس سالن و خودروهای رؤیایی",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = "کامپوزیت رئال عروس در محیط سالن و سناریوهای ماشین عروس",
                                    fontSize = 10.sp,
                                    color = CharcoalSecondary
                                )
                            }
                        }
                    }

                    // Mode Toggle: Salon vs Car
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = selectedCompositeMode == "salon",
                            onClick = { selectedCompositeMode = "salon" },
                            label = { Text("محیط لوکس سالن (با واترمارک)", fontSize = 11.sp) },
                            leadingIcon = { Icon(Icons.Default.Spa, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = TerracottaAccent,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        FilterChip(
                            selected = selectedCompositeMode == "car",
                            onClick = { selectedCompositeMode = "car" },
                            label = { Text("ماشین عروس رؤیایی (سوییچ در دست)", fontSize = 11.sp) },
                            leadingIcon = { Icon(Icons.Default.DirectionsCar, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = TerracottaAccent,
                                selectedLabelColor = Color.White
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Target Choices
                    val targetsList = if (selectedCompositeMode == "salon") {
                        EcosystemCatalogData.salonPresets
                    } else {
                        EcosystemCatalogData.luxuryCarPresets
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = if (selectedCompositeMode == "salon") "انتخاب فضای سالن زیبایی:" else "انتخاب سناریوی خودروی عروس:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CharcoalPrimary
                        )

                        targetsList.forEach { target ->
                            val isSelected = if (selectedCompositeMode == "salon") selectedSalonTarget == target else selectedCarTarget == target
                            Surface(
                                color = if (isSelected) TerracottaSubtle else WarmSurfaceSecondary,
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, if (isSelected) TerracottaAccent else WarmBorder),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        if (selectedCompositeMode == "salon") {
                                            selectedSalonTarget = target
                                        } else {
                                            selectedCarTarget = target
                                        }
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = {
                                            if (selectedCompositeMode == "salon") selectedSalonTarget = target else selectedCarTarget = target
                                        },
                                        colors = RadioButtonDefaults.colors(selectedColor = TerracottaAccent)
                                    )
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        text = target,
                                        fontSize = 11.sp,
                                        color = if (isSelected) CharcoalPrimary else CharcoalSecondary,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }

                    // Execute Composite Action
                    Button(
                        onClick = {
                            val activeTarget = if (selectedCompositeMode == "salon") selectedSalonTarget else selectedCarTarget
                            ecosystemController.processEnvironmentComposite(selectedCompositeMode, activeTarget)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Layers, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (selectedCompositeMode == "salon") "ترکیب با محیط سالن + واترمارک" else "رندر سناریوی ماشین عروس (سوییچ در دست)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Result Display
                    if (lastEnvironmentComposite != null && lastEnvironmentComposite?.mode == selectedCompositeMode) {
                        Surface(
                            color = SageGreenSubtle,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, SageGreen.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SageGreen, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = lastEnvironmentComposite?.message ?: "",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = SageGreen
                                    )
                                    Text(
                                        text = "هدف کامپوزیت: ${lastEnvironmentComposite?.target}",
                                        fontSize = 9.sp,
                                        color = CharcoalSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // ============================================
            // 3. CINEMATIC WEDDING HALL SCENARIOS MODULE
            // ============================================
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmSurface),
                border = BorderStroke(1.dp, WarmBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
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
                                Icon(Icons.Default.Videocam, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.padding(8.dp))
                            }
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "۳. سناریوهای سینمایی تالار عروسی",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = "لحظه ورود باشکوه و رقص دونفره ارکستر با نورپردازی داینامیک",
                                    fontSize = 10.sp,
                                    color = CharcoalSecondary
                                )
                            }
                        }
                    }

                    // Scene Type Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedSceneType = "grand_entry" },
                            color = if (selectedSceneType == "grand_entry") TerracottaAccent else WarmSurfaceSecondary,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, if (selectedSceneType == "grand_entry") TerracottaAccent else WarmBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.Celebration,
                                    contentDescription = null,
                                    tint = if (selectedSceneType == "grand_entry") Color.White else TerracottaAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "ورود باشکوه (grand_entry)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (selectedSceneType == "grand_entry") Color.White else CharcoalPrimary
                                )
                                Text(
                                    text = "عمق میدان و تشویق مهمانان",
                                    fontSize = 9.sp,
                                    color = if (selectedSceneType == "grand_entry") Color.White.copy(alpha = 0.8f) else CharcoalSecondary
                                )
                            }
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedSceneType = "first_dance" },
                            color = if (selectedSceneType == "first_dance") TerracottaAccent else WarmSurfaceSecondary,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, if (selectedSceneType == "first_dance") TerracottaAccent else WarmBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.MusicNote,
                                    contentDescription = null,
                                    tint = if (selectedSceneType == "first_dance") Color.White else TerracottaAccent,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "رقص دونفره (first_dance)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (selectedSceneType == "first_dance") Color.White else CharcoalPrimary
                                )
                                Text(
                                    text = "سن گرد با نورپردازی ارکستر",
                                    fontSize = 9.sp,
                                    color = if (selectedSceneType == "first_dance") Color.White.copy(alpha = 0.8f) else CharcoalSecondary
                                )
                            }
                        }
                    }

                    // Hall Venue Selector
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "انتخاب عمارت و تالار عروسی:",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = CharcoalPrimary
                        )

                        EcosystemCatalogData.weddingHalls.forEach { hall ->
                            val isSelected = selectedHall == hall
                            Surface(
                                color = if (isSelected) TerracottaSubtle else WarmSurfaceSecondary,
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, if (isSelected) TerracottaAccent else WarmBorder),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedHall = hall }
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { selectedHall = hall },
                                        colors = RadioButtonDefaults.colors(selectedColor = TerracottaAccent)
                                    )
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        text = hall,
                                        fontSize = 11.sp,
                                        color = if (isSelected) CharcoalPrimary else CharcoalSecondary,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }

                    // Process Action Button
                    Button(
                        onClick = {
                            ecosystemController.processCinematicHallScene(selectedSceneType, selectedHall)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.MovieFilter, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("رندر سناریوی سینمایی تالار (processCinematicHallScene)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }

                    // Result Display
                    if (lastCinematicHallScene != null && lastCinematicHallScene?.sceneType == selectedSceneType) {
                        Surface(
                            color = SageGreenSubtle,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, SageGreen.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SageGreen, modifier = Modifier.size(18.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        text = lastCinematicHallScene?.message ?: "",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SageGreen
                                    )
                                }
                                Text(
                                    text = "سناریو: ${lastCinematicHallScene?.scene} در ${lastCinematicHallScene?.hall}",
                                    fontSize = 10.sp,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = "تست کیفیت: ${lastCinematicHallScene?.qualityCheck}",
                                    fontSize = 9.sp,
                                    color = CharcoalSecondary
                                )
                            }
                        }
                    }
                }
            }

            // ============================================
            // 4. LIVE AUDIT LOG FEED
            // ============================================
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
                        Text(
                            text = "گزارش زنده فعالیت‌های مستر کنترلر (Audit Log)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = CharcoalPrimary
                        )
                        Icon(Icons.Default.History, contentDescription = null, tint = CharcoalSecondary, modifier = Modifier.size(16.dp))
                    }

                    auditLogs.take(5).forEach { log ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(TerracottaAccent))
                            Spacer(Modifier.width(8.dp))
                            Text(text = log, fontSize = 10.sp, color = CharcoalSecondary)
                        }
                    }
                }
            }

            // Button to open studio camera
            OutlinedButton(
                onClick = onNavigateToStudio,
                border = BorderStroke(1.dp, TerracottaAccent),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("ورود به استودیو دوربین برای تست زنده سناریوها", color = TerracottaAccent, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

        } else if (activeSubTab == "maison_network") {
            com.example.ui.maison.MaisonBoutiqueHubView(
                maisonRepo = maisonRepo,
                onNavigateToStudio = onNavigateToStudio,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            // ============================================
            // PORTALS TAB (سالن‌یار، ساختمان‌یار و آکادمی)
            // ============================================

            // 1. SALONYAR INTEGRATION
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmSurface),
                border = BorderStroke(1.dp, WarmBorder)
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
                                    text = "استاد زیبایی: ${salonSession.beautyMasterName} • کد: ${salonSession.customerCode}",
                                    fontSize = 10.sp,
                                    color = CharcoalSecondary
                                )
                            }
                        }
                    }
                }
            }

            // 2. SAKHTEMANYAR INTEGRATION
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmSurface),
                border = BorderStroke(1.dp, WarmBorder)
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
                                Icon(Icons.Default.Apartment, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.padding(8.dp))
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
                                    text = "پاسپورت تردد هوشمند و مدیریت شارژ مجتمع",
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
                                text = "معتبر ✓",
                                color = SageGreen,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    HorizontalDivider(color = WarmBorderSubtle)

                    Surface(
                        color = WarmSurfaceSecondary,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "${sakhtemanPass.buildingName} - ${sakhtemanPass.unitNumber}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = CharcoalPrimary
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = "نام ساکن: ${sakhtemanPass.residentName}",
                                fontSize = 11.sp,
                                color = CharcoalSecondary
                            )
                            Text(
                                text = "وضعیت شارژ: ${sakhtemanPass.maintenanceBalanceStatus}",
                                fontSize = 11.sp,
                                color = SageGreen,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Button(
                        onClick = {
                            isDoorUnlocked = true
                            onSimulateDoorUnlock()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isDoorUnlocked) SageGreen else TerracottaAccent
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            if (isDoorUnlocked) Icons.Default.LockOpen else Icons.Default.QrCodeScanner,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (isDoorUnlocked) "درب ورودی باز شد ✓" else "بازگشایی هوشمند درب مجتمع (NFC / QR)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // 3. MARKETING ACADEMY INTEGRATION
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = WarmSurface),
                border = BorderStroke(1.dp, WarmBorder)
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
                                Icon(Icons.Default.TrendingUp, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.padding(8.dp))
                            }
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "آکادمی مارکتینگ و رشد سیاوش",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = "آمار معرفی، جذب لید و درآمد معرف",
                                    fontSize = 10.sp,
                                    color = CharcoalSecondary
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = WarmBorderSubtle)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "بازدید لینک", fontSize = 10.sp, color = CharcoalSecondary)
                            Text(text = "${marketingStats.totalClicks} کلیک", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = CharcoalPrimary)
                        }
                        Column {
                            Text(text = "لیدهای ثبت‌شده", fontSize = 10.sp, color = CharcoalSecondary)
                            Text(text = "${marketingStats.registeredLeads} نفر", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TerracottaAccent)
                        }
                        Column {
                            Text(text = "نرخ تبدیل", fontSize = 10.sp, color = CharcoalSecondary)
                            Text(text = marketingStats.conversionRate, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SageGreen)
                        }
                    }

                    Surface(
                        color = SageGreenSubtle,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "موجودی پورسانت قابل برداشت:", fontSize = 11.sp, color = SageGreen)
                            Text(
                                text = marketingStats.commissionBalanceToman,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SageGreen
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ModuleHealthBadge(title: String, active: Boolean) {
    Surface(
        color = if (active) SageGreenSubtle else WarmBorderSubtle,
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .clip(CircleShape)
                    .background(if (active) SageGreen else Color.Gray)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = title,
                fontSize = 9.sp,
                fontWeight = FontWeight.Medium,
                color = if (active) SageGreen else CharcoalSecondary
            )
        }
    }
}
