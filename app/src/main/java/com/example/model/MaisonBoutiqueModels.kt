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

    val realMaisonGarments = listOf(
        MaisonGarmentItem(
            id = "g-bride-01",
            maisonId = "maison-royal-niavaran",
            maisonName = "مزون رویال کوتور (شهدخت)",
            maisonAddress = "نیاوران، سه راه یاسر، پلاک ۲۴",
            maisonPhone = "۰۲۱-۲۲۸۰۴۵۱۰",
            licenseCode = "صنف-۹۸۴۲۱/الف",
            titleFa = "لباس عروس پرنسسی سوپر رویال با دانتل فرانسه و گن‌دوزی ترک",
            category = "لباس عروس VIP",
            fabricAndCut = "دانتل دست‌دوز شانتون، آستر ابریشم خالص، فنردوزی ۱۵ خطی جهت باریک نشان دادن کمر",
            rentalPriceToman = "۱۹,۵۰۰,۰۰۰ تومان",
            purchasePriceToman = "۶۲,۰۰۰,۰۰۰ تومان",
            celebrityFitStyle = "تن‌خور سبک پرنسس گریس کلی و آدری هپبورن (شکوه کلاسیک)",
            commissionForSalonToman = "۲,۵۰۰,۰۰۰ تومان"
        ),
        MaisonGarmentItem(
            id = "g-bride-02",
            maisonId = "maison-royal-niavaran",
            maisonName = "مزون رویال کوتور (شهدخت)",
            maisonAddress = "نیاوران، سه راه یاسر، پلاک ۲۴",
            maisonPhone = "۰۲۱-۲۲۸۰۴۵۱۰",
            licenseCode = "صنف-۹۸۴۲۱/الف",
            titleFa = "لباس عروس مدل ماهی اسلیم‌فیت مرواریددوزی با شنل حریر دنباله‌دار",
            category = "لباس عروس VIP",
            fabricAndCut = "ساتن آمریکایی میساکی، سنگ‌دوزی کریستال زیرو، پشت بنددار قابل تنظیم",
            rentalPriceToman = "۱۶,۰۰۰,۰۰۰ تومان",
            purchasePriceToman = "۴۸,۰۰۰,۰۰۰ تومان",
            celebrityFitStyle = "تن‌خور سبک زندایا و بلا حدید (مدرن و بسیار کشیده)",
            commissionForSalonToman = "۲,۰۰۰,۰۰۰ تومان"
        ),
        MaisonGarmentItem(
            id = "g-groom-01",
            maisonId = "maison-diplomat-jordan",
            maisonName = "خانه تاکسیدو و پوشاک دامادی دیپلمات",
            maisonAddress = "جردن، نبش گلشهر، برج آیتا",
            maisonPhone = "۰۲۱-۸۸۷۶۳۲۹۰",
            licenseCode = "صنف-۷۱۲۰۴/پ",
            titleFa = "تاکسیدو دامادی یقه شال ساتن ایتالیایی با جلیقه و دکمه‌سردست طلاکوپ",
            category = "کت‌وشلوار و تاکسیدو داماد",
            fabricAndCut = "پارچه ویتاله باربریس ایتالیا سوپر ۱۶۰ پشم خالص، دوخت تمام صنعتی با تن‌خور دراپ ۷",
            rentalPriceToman = "۱۲,۰۰۰,۰۰۰ تومان",
            purchasePriceToman = "۳۸,۰۰۰,۰۰۰ تومان",
            celebrityFitStyle = "تن‌خور سبک دنیل کریگ (جیمز باند) و دیوید بکهام",
            commissionForSalonToman = "۱,۸۰۰,۰۰۰ تومان"
        ),
        MaisonGarmentItem(
            id = "g-groom-02",
            maisonId = "maison-diplomat-jordan",
            maisonName = "خانه تاکسیدو و پوشاک دامادی دیپلمات",
            maisonAddress = "جردن، نبش گلشهر، برج آیتا",
            maisonPhone = "۰۲۱-۸۸۷۶۳۲۹۰",
            licenseCode = "صنف-۷۱۲۰۴/پ",
            titleFa = "کت اسموکینگ سفید عاجی سلطنتی با شلوار مشکی مات و ساسبند ساتن",
            category = "کت‌وشلوار و تاکسیدو داماد",
            fabricAndCut = "کرپ ابریشم دانه درشت با دکمه‌های روکش‌دار پارچه‌ای و پاپیون مخمل ابریشم",
            rentalPriceToman = "۱۴,۵۰۰,۰۰۰ تومان",
            purchasePriceToman = "۴۲,۰۰۰,۰۰۰ تومان",
            celebrityFitStyle = "تن‌خور هالیوودی فرش قرمز مت گالا",
            commissionForSalonToman = "۲,۲۰۰,۰۰۰ تومان"
        ),
        MaisonGarmentItem(
            id = "g-form-01",
            maisonId = "maison-elysee-home",
            maisonName = "استودیو طراحی و دوخت الیزه (مزون VIP)",
            maisonAddress = "فرمانیه، خیابان سنبل، مجتمع کاکتوس",
            maisonPhone = "۰۹۱۲-۳۴۰۷۸۹۰",
            licenseCode = "شناسه هنری مد و لباس ۹۴۱۰۲",
            titleFa = "پیراهن فرمالیته شاین صورتی مایل به صدفی با چاک بلند و آستین افتاده",
            category = "لباس فرمالیته و شب",
            fabricAndCut = "حریر شاین زری‌بافت ترک با پلیسه‌دوزی تمام دست‌دوز و کاپ سینه‌دار",
            rentalPriceToman = "۹,۵۰۰,۰۰۰ تومان",
            purchasePriceToman = "۲۹,۰۰۰,۰۰۰ تومان",
            celebrityFitStyle = "تن‌خور سبک تیلور سوئیفت و کندال جنر (ایده‌آل عکاسی کویر و ساحل)",
            commissionForSalonToman = "۱,۲۰۰,۰۰۰ تومان"
        )
    )
}
