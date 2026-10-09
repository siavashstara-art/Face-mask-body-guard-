package com.example.model

data class EcosystemModuleStatus(
    val miniStudio: Boolean = true,
    val salonBackground: Boolean = true,
    val dreamyCars: Boolean = true,
    val cinematicHall: Boolean = true
)

data class MiniTryOnCatalogItem(
    val index: Int,
    val gender: String, // "girl" or "boy"
    val titleFa: String,
    val titleEn: String,
    val descriptionFa: String,
    val styleBadge: String
)

data class MiniTryOnResult(
    val status: String,
    val module: String,
    val gender: String,
    val itemIndex: Int,
    val selectedItem: String,
    val titleFa: String,
    val message: String
)

data class EnvironmentCompositeResult(
    val status: String,
    val module: String,
    val mode: String, // "salon" or "car"
    val target: String,
    val message: String
)

data class CinematicHallSceneResult(
    val status: String,
    val module: String,
    val sceneType: String, // "grand_entry" or "first_dance"
    val hall: String,
    val scene: String,
    val qualityCheck: String,
    val message: String
)

object EcosystemCatalogData {

    // 25 Mini-Bride Dresses for Girls (لباس عروس مینیاتوری نوزاد و کودک)
    val miniBrideGirls: List<MiniTryOnCatalogItem> = (1..25).map { idx ->
        val (style, badge) = when (idx % 5) {
            1 -> "پرنسسی پف‌دار با دانتل فرانسوی و تور ابریشم" to "پرنسسی رویال"
            2 -> "دکلته مینیاتوری با مرواریددوزی دست‌دوز و شنل حریر" to "مرواریددوزی"
            3 -> "آستین پفی وینتیج با پاپیون ارگانزا پشت کمر" to "وینتیج اروپایی"
            4 -> "دامن طبقاتی توتو شاین‌دار با پولک‌های کریستالی نقره‌ای" to "کریستال شاین"
            else -> "طرح سلطنتی با سنگ‌دوزی سواروسکی و تاج گل شکوفه" to "سلطنتی VIP"
        }
        MiniTryOnCatalogItem(
            index = idx,
            gender = "girl",
            titleFa = "لباس عروس مینیاتوری دخترانه کد $idx ($badge)",
            titleEn = "Mini-Bride Dress #$idx ($badge)",
            descriptionFa = style,
            styleBadge = badge
        )
    }

    // 20 Pageboy Suits for Boys (کت‌وشلوار و تاکسیدو مینیاتوری ساقدوش)
    val pageboyBoys: List<MiniTryOnCatalogItem> = (1..20).map { idx ->
        val (style, badge) = when (idx % 4) {
            1 -> "تاکسیدو کلاسیک مشکی با پاپیون ساتن و یقه آرشال ابریشمی" to "تاکسیدو مشکی"
            2 -> "کت‌وشلوار سه‌تکه سرمه‌ای با جلیقه و زنجیر ساعت طلایی" to "سه‌تکه سرمه‌ای"
            3 -> "کت اسموکینگ سفید با شلوار مشکی و پاپیون مخمل زرشکی" to "اسموکینگ سفید"
            else -> "کت اسپرت چهارخانه طوسی پشمی با کراوات باریک شیک" to "اشرافی مدرن"
        }
        MiniTryOnCatalogItem(
            index = idx,
            gender = "boy",
            titleFa = "کت‌وشلوار ساقدوش پسرانه کد $idx ($badge)",
            titleEn = "Pageboy Suit #$idx ($badge)",
            descriptionFa = style,
            styleBadge = badge
        )
    }

    // Luxury Salon Environments
    val salonPresets = listOf(
        "سالن رویال VIP نیاوران با آینه‌کاری فرانسوی و نورپردازی گرم",
        "لاین اختصاصی عروس با استیج میکاپ و لوسترهای کریستال باکارات",
        "اتاق عکاسی پرتره با مبلمان مخمل رزگلد و گچ‌بری باروک",
        "استودیو لایت و میکاپ سالن با رینگ‌لایت سینمایی و دکور مرمر",
        "فضای VIP تحویل عروس با بک‌گراند گل‌آرایی طبیعی و واترمارک طلایی"
    )

    // Luxury Wedding Car Scenarios
    val luxuryCarPresets = listOf(
        "مرسدس بنز S-Class سفید صدفی با گل‌آرایی ارکیده و لیلیوم (سوییچ در دست)",
        "رولزرویس فانتوم کلاسیک وینتیج مشکی-طلایی با روبان‌دوزی ساتن",
        "پورشه پانامرا توربو با تزئین گل‌های رز هلندی و پیاده‌سازی عروس و داماد",
        "ب‌ام‌و سری ۷ با نورپردازی داینامیک شب و دسته گل عروس",
        "بنتلی کانتیننتال کانورتیبل کروک با تزئین پیونی صورتی در محوطه باغ"
    )

    // Cinematic Wedding Halls
    val weddingHalls = listOf(
        "عمارت و تالار مجلل دانیال (شمس‌آباد)",
        "باغ‌تالار قصر کیان (احمدآباد مستوفی)",
        "عمارت باشکوه والا (گرمدره)",
        "تالار رویال پالاس (اقدسیه تهران)",
        "باغ‌عمارت ورسای (شهریار)"
    )
}
