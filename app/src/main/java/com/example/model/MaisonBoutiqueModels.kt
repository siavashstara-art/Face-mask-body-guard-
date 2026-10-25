package com.example.model

enum class VisitorContractPlan(
    val titleFa: String,
    val subtitleFa: String,
    val descriptionFa: String,
    val badgeFa: String
) {
    PLAN_A_FULL_SUITE(
        titleFa = "پلن A (پکیج کامل نرم‌افزار + فیس‌گارد)",
        subtitleFa = "قرارداد سالانه مزون‌یار/سالن‌یار بدون چک با تسویه نقدی",
        descriptionFa = "نصب پنل اختصاصی مزون‌یار با ماژول کامل فیس‌گارد، دسترسی به بیش از ۵۰ سالن زیبایی همکار، ثبت نامحدود لباس و گالری پرو دیجیتال.",
        badgeFa = "فروش پکیج نرم‌افزار"
    ),
    PLAN_B_CROSS_REFERRAL(
        titleFa = "پلن B (شراکت و شبکه ارجاع متقابل)",
        subtitleFa = "بدون هزینه نرم‌افزار، شراکت در معرفی عروس و دریافت پورسانت",
        descriptionFa = "عضویت رایگان در شبکه ویترین مزون‌های توانا؛ سالن‌ها مشتری عروس را برای انتخاب لباس به مزون می‌فرستند و مزون مشتری را برای میکاپ به سالن ارجاع می‌دهد.",
        badgeFa = "شراکت پورسانتی"
    ),
    PLAN_C_HOME_STUDIO(
        titleFa = "پلن C (شبکه پنهان مزون‌های خانگی)",
        subtitleFa = "پوشش مزون‌های آپارتمانی و طراحان مستقل با هویت امن",
        descriptionFa = "پشتیبانی از مزون‌های بدون تابلو و طراحان خانگی جهت نمایش مدل‌ها در پرو مجازی سالن‌های همکار با کد معرف و تسویه پورسانتی آنی.",
        badgeFa = "مزون خانگی"
    )
}

data class MaisonGarmentItem(
    val id: String,
    val maisonId: String,
    val maisonName: String,
    val maisonAddress: String,
    val maisonPhone: String,
    val licenseCode: String,
    val titleFa: String,
    val category: String, // "لباس عروس VIP", "کت‌وشلوار و تاکسیدو داماد", "لباس فرمالیته و شب"
    val fabricAndCut: String,
    val rentalPriceToman: String,
    val purchasePriceToman: String,
    val celebrityFitStyle: String,
    val commissionForSalonToman: String,
    val photoUri: String? = null,
    val isVerified: Boolean = true
)

data class MaisonBoutiqueProfile(
    val id: String,
    val name: String,
    val managerName: String,
    val phone: String,
    val address: String,
    val cityDistrict: String,
    val licenseNumber: String,
    val plan: VisitorContractPlan,
    val isHomeBoutique: Boolean = false,
    val garments: List<MaisonGarmentItem> = emptyList(),
    val totalReferralsReceived: Int = 18,
    val totalCommissionEarnedToman: String = "۱۴,۲۰۰,۰۰۰ تومان"
)

data class ReferralVoucher(
    val id: String,
    val clientName: String,
    val garmentTitle: String,
    val maisonName: String,
    val maisonPhone: String,
    val maisonAddress: String,
    val referringSalonName: String,
    val discountPercent: Int = 10,
    val referralCode: String,
    val issueDate: String,
    val status: String = "صادر شده - آماده مراجعه"
)

enum class WeddingReceptionScene(
    val titleFa: String,
    val subtitleFa: String,
    val atmosphereFa: String,
    val visualPromptFa: String,
    val guestMoodFa: String
) {
    GRAND_ENTRY_RECEPTION(
        titleFa = "ورود باشکوه به سالن و باشگاه",
        subtitleFa = "استقبال پرشور مهمانان، فشفشه سرد و مه سنگین",
        atmosphereFa = "نورافکن‌های متحرک، صدای تشویق و کف زدن مهمانان، فرش قرمز ورودی",
        visualPromptFa = "عروس و داماد دست در دست، با لبخند پیروزمندانه از میان دو ردیف مهمانان ایستاده عبور می‌کنند.",
        guestMoodFa = "شور و هیجان مهمانان و تشویق با فشفشه‌های درخشان"
    ),
    FIRST_DANCE_STAGE(
        titleFa = "استیج رقص تانگو زیر نورپردازی مهتابی",
        subtitleFa = "شمع‌آرایی رویایی و ریزش گلبرگ‌ها",
        atmosphereFa = "نور موضعی رمانتیک، مه سبک ابریشمی روی زمین، حلقه مهمانان دور استیج",
        visualPromptFa = "نگاه عاشقانه دونفره، چرخش آهسته لباس پرنسسی عروس و دست داماد روی کمر عروس.",
        guestMoodFa = "سکوت تحسین‌آمیز و نگاه‌های پر از احساس مهمانان"
    ),
    GARDEN_PERGOLA_VILLA(
        titleFa = "باغ‌عمارت و لوکیشن عکاسی فضای باز",
        subtitleFa = "آلاچیق گل‌آرایی‌شده با رزهای سفید و آب‌نما",
        atmosphereFa = "نور ملایم ساعت طلایی (غروب)، نسیم ملایم در دنباله حریر تور عروس",
        visualPromptFa = "قدم‌زدن دونفره روی سنگ‌فرش باغ با پس‌زمینه درختان بید مجنون و چراغ‌های رشته‌ای.",
        guestMoodFa = "لحظات آرامش‌بخش خانوادگی و عکس‌های ژورنالی"
    ),
    MIRRORED_SOFREH_AGHD(
        titleFa = "جایگاه عقد کریستالی و آینه‌کاری",
        subtitleFa = "ظروف نقره، قند سابی روی سر با تور دانتل و شمع‌های لاله",
        atmosphereFa = "شکوه سنتی-مدرن با آینه بخت بزرگ و بازتاب درخشان الماس‌های تاج عروس",
        visualPromptFa = "نگاه در آینه بخت، گرفتن دست یکدیگر با حلقه ازدواج در مرکز کادر.",
        guestMoodFa = "شادی خانواده‌ها، کل کشیدن و آرزوی خوشبختی"
    )
}

object BundledMaisonData {

    val verifiedMaisonProfiles = listOf(
        MaisonBoutiqueProfile(
            id = "maison-royal-niavaran",
            name = "مزون رویال کوتور (شهدخت)",
            managerName = "سرکار خانم مهندس شریفی",
            phone = "۰۲۱-۲۲۸۰۴۵۱۰",
            address = "تهران، نیاوران، سه راه یاسر، پلاک ۲۴، زنگ ۲",
            cityDistrict = "منطقه ۱ نیاوران",
            licenseNumber = "صنف-۹۸۴۲۱/الف",
            plan = VisitorContractPlan.PLAN_A_FULL_SUITE,
            isHomeBoutique = false,
            totalReferralsReceived = 34,
            totalCommissionEarnedToman = "۳۲,۵۰۰,۰۰۰ تومان"
        ),
        MaisonBoutiqueProfile(
            id = "maison-diplomat-jordan",
            name = "خانه تاکسیدو و پوشاک دامادی دیپلمات",
            managerName = "آقای فرزاد رادمنش",
            phone = "۰۲۱-۸۸۷۶۳۲۹۰",
            address = "تهران، جردن (نلسون ماندلا)، نبش خیابان گلشهر، برج آیتا، طبقه همکف تجاری",
            cityDistrict = "منطقه ۳ جردن",
            licenseNumber = "صنف-۷۱۲۰۴/پ",
            plan = VisitorContractPlan.PLAN_A_FULL_SUITE,
            isHomeBoutique = false,
            totalReferralsReceived = 28,
            totalCommissionEarnedToman = "۲۴,۸۰۰,۰۰۰ تومان"
        ),
        MaisonBoutiqueProfile(
            id = "maison-elysee-home",
            name = "استودیو طراحی و دوخت الیزه (مزون VIP)",
            managerName = "خانم نیلوفر افشار",
            phone = "۰۹۱۲-۳۴۰۷۸۹۰",
            address = "تهران، فرمانیه، خیابان سنبل، مجتمع کاکتوس (مزون خصوصی)",
            cityDistrict = "فرمانیه (مزون آپارتمانی)",
            licenseNumber = "شناسه هنری مد و لباس ۹۴۱۰۲",
            plan = VisitorContractPlan.PLAN_C_HOME_STUDIO,
            isHomeBoutique = true,
            totalReferralsReceived = 19,
            totalCommissionEarnedToman = "۱۸,۱۰۰,۰۰۰ تومان"
        )
    )

    private val brideStyles = listOf(
        "پرنسسی سوپر رویال با دانتل فرانسه و گن‌دوزی ترک",
        "مدل ماهی اسلیم‌فیت مرواریددوزی با شنل حریر دنباله‌دار",
        "اسکارلت کلوش شاین نقره‌ای با کریستال سواروسکی",
        "مینیمال اروپایی ساتن آمریکایی میساکی با یقه قایقی",
        "وینتیج ویکتوریایی با آستین کلوش گیپور دست‌بافت",
        "بوهو ساحلی سبک با حریر شاین و چاک نامتقارن",
        "دکلته کاپ‌دار با کمر فنردوزی ۱۸ خطی فرانسوی",
        "مدل ای-لاین (A-Line) با شکوفه‌های بهاری سه‌بعدی برجسته",
        "رویال کلوش با دامن طبقاتی تور ارگانزا و دنباله ۲ متری",
        "پف‌دار اشرافی با بالاتنه سنگ‌دوزی تمام دست‌دوز"
    )

    private val groomStyles = listOf(
        "تاکسیدو یقه شال ساتن ایتالیایی با جلیقه دبل برست",
        "کت اسموکینگ سفید عاجی سلطنتی با شلوار مشکی مات",
        "کت‌وشلوار کلاسیک مشکی سوپر ۱۶۰ ویتاله باربریس ایتالیا",
        "سرمه‌ای دیپلمات با جلیقه طوسی روشن و دکمه‌های نقره‌ای",
        "تاکسیدو مخمل یقه آرشال زرشکی شاهانه با ساسبند ساتن",
        "کت‌وشلوار طوسی فیلی ایتالیایی با دوخت تمام دست‌دوز",
        "اسموکینگ مشکی براق با پاپیون ابریشم و پیراهن پین‌تاک",
        "کت شتری اشرافی فرمالیته با شلوار کرم عاجی و پوشت هماهنگ"
    )

    val realMaisonGarments: List<MaisonGarmentItem> = buildList {
        // ۱. ۵۰ عدد لباس عروس VIP
        for (i in 1..50) {
            val styleIdx = (i - 1) % brideStyles.size
            val baseStyle = brideStyles[styleIdx]
            val variant = when ((i - 1) / brideStyles.size) {
                0 -> "کالکشن رویال ۲۰۲۵"
                1 -> "نسخه لیمیتد الماس"
                2 -> "طراحی مزون میلان"
                3 -> "سفارشی مروارید پاریس"
                else -> "پرویموم سفیر مد"
            }
            val rentalPrice = 14 + (i % 9) * 2
            val buyPrice = rentalPrice * 3 + 10
            val commission = 1800000 + (i % 7) * 200000

            add(
                MaisonGarmentItem(
                    id = "g-bride-${String.format("%02d", i)}",
                    maisonId = if (i % 2 == 0) "maison-royal-niavaran" else "maison-elysee-home",
                    maisonName = if (i % 2 == 0) "مزون رویال کوتور (شهدخت)" else "استودیو الیزه VIP",
                    maisonAddress = if (i % 2 == 0) "نیاوران، سه راه یاسر، پلاک ۲۴" else "فرمانیه، مجتمع کاکتوس",
                    maisonPhone = if (i % 2 == 0) "۰۲۱-۲۲۸۰۴۵۱۰" else "۰۹۱۲-۳۴۰۷۸۹۰",
                    licenseCode = "صنف-۹۸۴۲۱/الف",
                    titleFa = "لباس عروس کد B-$i: $baseStyle ($variant)",
                    category = "لباس عروس VIP",
                    fabricAndCut = "دانتل اصل فرانسه، آستر ابریشم، گن‌دوزی ترک با فنر استخوان وال جهت باریک‌سازی دور کمر",
                    rentalPriceToman = "$rentalPrice,۵۰۰,۰۰۰ تومان",
                    purchasePriceToman = "$buyPrice,۰۰۰,۰۰۰ تومان",
                    celebrityFitStyle = when (i % 4) {
                        0 -> "تن‌خور گریس کلی و آدری هپبورن (شکوه کلاسیک)"
                        1 -> "تن‌خور بلا حدید و زندایا (کشیده و مدرن)"
                        2 -> "تن‌خور مارگو رابی (پرنسسی و درخشان)"
                        else -> "تن‌خور کیت میدلتون (سلطنتی و باوقار)"
                    },
                    commissionForSalonToman = "${String.format("%,d", commission)} تومان"
                )
            )
        }

        // ۲. ۳۵ عدد کت‌وشلوار و تاکسیدو داماد
        for (j in 1..35) {
            val styleIdx = (j - 1) % groomStyles.size
            val baseStyle = groomStyles[styleIdx]
            val variant = when ((j - 1) / groomStyles.size) {
                0 -> "سری دیپلمات میلان"
                1 -> "کوتور ناپولی ایتالیا"
                2 -> "فرش قرمز کن"
                3 -> "بلک‌تای لندنی"
                else -> "اشرافی درباری"
            }
            val rentalPrice = 10 + (j % 6) * 2
            val buyPrice = rentalPrice * 3 + 8
            val commission = 1400000 + (j % 5) * 200000

            add(
                MaisonGarmentItem(
                    id = "g-groom-${String.format("%02d", j)}",
                    maisonId = "maison-diplomat-jordan",
                    maisonName = "خانه تاکسیدو و پوشاک دامادی دیپلمات",
                    maisonAddress = "جردن، نبش گلشهر، برج آیتا",
                    maisonPhone = "۰۲۱-۸۸۷۶۳۲۹۰",
                    licenseCode = "صنف-۷۱۲۰۴/پ",
                    titleFa = "پوشاک داماد کد G-$j: $baseStyle ($variant)",
                    category = "کت‌وشلوار و تاکسیدو داماد",
                    fabricAndCut = "پارچه ویتاله باربریس یا لورو پیانا ایتالیا پشم سوپر ۱۶۰، آستر ابریشم طرح‌دار، تن‌خور اسلیم دراپ ۷",
                    rentalPriceToman = "$rentalPrice,۰۰۰,۰۰۰ تومان",
                    purchasePriceToman = "$buyPrice,۰۰۰,۰۰۰ تومان",
                    celebrityFitStyle = when (j % 4) {
                        0 -> "تن‌خور دنیل کریگ (جیمز باند مامور ۰۰۷)"
                        1 -> "تن‌خور دیوید بکهام (کت‌وشلوار انگلیسی شیک)"
                        2 -> "تن‌خور لئوناردو دی‌کاپریو (اسموکینگ گتسبی بزرگ)"
                        else -> "تن‌خور تیموتی شالامی (مدرن و فشن آوانگارد)"
                    },
                    commissionForSalonToman = "${String.format("%,d", commission)} تومان"
                )
            )
        }

        // ۳. ۱۵ عدد لباس فرمالیته و شب
        for (k in 1..15) {
            val rentalPrice = 8 + (k % 4) * 2
            val buyPrice = rentalPrice * 3
            val commission = 1100000 + (k % 4) * 150000

            add(
                MaisonGarmentItem(
                    id = "g-form-${String.format("%02d", k)}",
                    maisonId = "maison-elysee-home",
                    maisonName = "استودیو الیزه VIP",
                    maisonAddress = "فرمانیه، مجتمع کاکتوس",
                    maisonPhone = "۰۹۱۲-۳۴۰۷۸۹۰",
                    licenseCode = "شناسه هنری ۹۴۱۰۲",
                    titleFa = "پیراهن فرمالیته و شب کد F-$k: حریر شاین زری‌بافت و چاک‌دار",
                    category = "لباس فرمالیته و شب",
                    fabricAndCut = "حریر ترک ارگانزا با کاپ سینه دست‌ساز و پلیسه هنری",
                    rentalPriceToman = "$rentalPrice,۵۰۰,۰۰۰ تومان",
                    purchasePriceToman = "$buyPrice,۰۰۰,۰۰۰ تومان",
                    celebrityFitStyle = "تن‌خور کندال جنر و تیلور سوئیفت",
                    commissionForSalonToman = "${String.format("%,d", commission)} تومان"
                )
            )
        }
    }
}
