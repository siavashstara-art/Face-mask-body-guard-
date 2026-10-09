package com.example.model

enum class WeddingDayLightTime(
    val titleFa: String,
    val timeLabelFa: String,
    val descriptionFa: String,
    val filterColorHex: Long,
    val ambientIntensity: Float
) {
    NATURAL_GARDEN_DAY(
        titleFa = "نور روز باغ و عمارت",
        timeLabelFa = "۱۱:۰۰ ظهر",
        descriptionFa = "شبیه‌ساز نور طبیعی ملایم خورشید در محوطه باز باغ و عکاسی عمارت بدون سایه‌های تند.",
        filterColorHex = 0x1AFFF8E7,
        ambientIntensity = 0.95f
    ),
    GOLDEN_HOUR_SUNSET(
        titleFa = "ساعت طلایی غروب (Golden Hour)",
        timeLabelFa = "۱۸:۳۰ غروب",
        descriptionFa = "نور گرم کهربایی و پرتوهای طلایی خورشید غروب؛ بهترین زمان عکاسی رمانتیک و جلوه درخشان میکاپ.",
        filterColorHex = 0x2EE07A5F,
        ambientIntensity = 1.05f
    ),
    DYNAMIC_DANCE_FLOOR(
        titleFa = "نورپردازی رقص و مه سالن",
        timeLabelFa = "۲۱:۳۰ شب",
        descriptionFa = "افکت نورپردازی داینامیک استیج، بارهای لیزری ملایم و مه غلیظ رقص تالار با حفظ کانتور چهره.",
        filterColorHex = 0x256A1B9A,
        ambientIntensity = 0.90f
    ),
    STUDIO_DIRECT_FLASH(
        titleFa = "عکاسی شبانه و فلاش آتلیه",
        timeLabelFa = "۲۳:۰۰ شب",
        descriptionFa = "شبیه‌ساز فلاش پرتابل و نور دیفیوزر چتری آتلیه برای بررسی رفلکس پودر فیکس و درخشش تور عروس.",
        filterColorHex = 0x12FFFFFF,
        ambientIntensity = 1.10f
    )
}

enum class ConfidencePoseGuideType(
    val titleFa: String,
    val guidanceFa: String,
    val confidenceTipFa: String,
    val iconName: String
) {
    GRAND_ENTRY(
        titleFa = "ورود باشکوه به سالن",
        guidanceFa = "قامت کاملاً کشیده، دست چپ عروس روی ساعد داماد، سر بالا با لبخند آرام رو به جمعیت.",
        confidenceTipFa = "نفس عمیق بکشید؛ قدم‌ها را آرام بردارید و نگاهتان را ۳ متر جلوتر از پاها نگه دارید.",
        iconName = "Celebration"
    ),
    FIRST_DANCE(
        titleFa = "رقص رمانتیک دونفره",
        guidanceFa = "دست راست داماد روی گودی کمر عروس، دست چپ بالا در زاویه ۹۰ درجه، نگاه صمیمی چشم در چشم.",
        confidenceTipFa = "شانه‌ها را آزاد بگذارید؛ موسیقی را احساس کنید و نیازی به عجله در چرخش‌ها نیست.",
        iconName = "MusicNote"
    ),
    FOREHEAD_KISS(
        titleFa = "بوسه بر پیشانی و نوازش",
        guidanceFa = "داماد دست بر شانه یا گونه عروس دارد و پیشانی را می‌بوسد؛ عروس چشمانش را با آرامش می‌بندد.",
        confidenceTipFa = "طبیعی‌ترین حس عکس‌های عروسی در بستن ملایم پلک‌ها و لبخند درونی شکل می‌گیرد.",
        iconName = "Favorite"
    ),
    ROYAL_PORTRAIT(
        titleFa = "پرتره سلطنتی ژورنالی",
        guidanceFa = "ایستادن سه‌رخ، زاویه چانه کمی رو به پایین، دسته‌گل موازی ناف نه روی سینه، شانه متقارن.",
        confidenceTipFa = "دسته‌گل را سفت نگیرید؛ انگشت‌ها را رها بگذارید تا خطوط دست‌ها در عکس ظریف بیفتد.",
        iconName = "Star"
    )
}

data class CoupleHarmonyState(
    val brideDressTitle: String = "لباس عروس دانتل فرانسه سوپر رویال",
    val groomSuitTitle: String = "تاکسیدو ایتالیایی یقه شال مشکی با پاپیون",
    val harmonyScore: Int = 98,
    val harmonyVerdictFa: String = "هارمونی فوق‌العاده سلطنتی و کلاسیک (سازگاری ۱۰۰٪ با تالارهای لوکس)",
    val bouquetSuggestionFa: String = "دسته‌گل پیونی سفید با شکوفه ارکیده مینیاتوری و روبان مشکی/ساتن",
    val accessoryAdviceFa: String = "ساعت مچی بند چرمی مشکی باریک برای داماد و گوشواره مروارید میخی برای عروس"
)

data class PrivateVaultPhotoItem(
    val id: String,
    val title: String,
    val brideName: String,
    val maisonName: String,
    val captureDate: String,
    val watermarkSecretCode: String,
    val isLocked: Boolean = true,
    val note: String = "پیش‌نمایش ایزوله و ضد اسکرین‌شات - فقط جهت نظرخواهی از همراهان محرم"
)
