package com.example.model

enum class FilterPreset(val titleEn: String, val titleFa: String) {
    NATURAL("Natural Clean", "طبیعی و شفاف"),
    SMOOTH_WARM("Warm Smoothing", "پوست لطیف و گرم"),
    BRONZE_GLOW("Sun-Kissed Bronze", "برنزه خلیجی و طلایی"),
    PORCELAIN_BRIGHT("Porcelain Bright", "روشن‌کننده و مهتابی"),
    GLAMOUR_STUDIO("Studio Glamour", "آرایش و فتوشاپ استودیویی"),
    COOL_CYBER("Cool Cyber Teal", "سایبر نئون"),
    NOIR_BW("Film Noir B&W", "سیاه و سفید سینمایی"),
    VINTAGE_SEPIA("Vintage Sepia", "سپیا کلاسیک"),
    VIVID_CONTRAST("Vivid Privacy", "کنتراست بالا"),
    AVATAR_GLOW("Stylized Avatar", "آواتار دیجیتال")
}

data class FaceStyleConfig(
    val preset: FilterPreset = FilterPreset.NATURAL,
    val skinSmoothing: Float = 0.5f,        // صاف‌سازی و محو لکه‌ها
    val blemishRemoval: Float = 0.6f,       // از بین بردن جوش، کک‌ومک، پیسی، جای لکه
    val skinToneBalance: Float = 0.0f,      // -1.0f (روشن و مهتابی) تا +1.0f (برنزه و گندمی)
    val facialSlimming: Float = 0.0f,       // -0.5f لاغرتر کردن فک و گونه، +0.5f پرتر کردن
    val lipTintIntensity: Float = 0.35f,    // رژ لب و آرایش لب
    val blushIntensity: Float = 0.25f,      // رژ گونه ملایم
    val warmth: Float = 0.0f,
    val contrast: Float = 1.0f,
    val saturation: Float = 1.0f,
    val showAlterationBadge: Boolean = false // واترمارک / بدون واترمارک (کاربر می‌تواند آزادانه خاموش یا روشن کند)
)

data class BodySilhouetteConfig(
    val enabled: Boolean = true,
    val waistContour: Float = -0.2f,     // -0.5f (لاغرتر کردن کمر و شکم) تا +0.5f (برجسته‌تر)
    val hipEnhancement: Float = 0.3f,    // برجسته‌سازی باسن و اندام در مدل‌های لاغر
    val chestEnhancement: Float = 0.25f,  // برجسته‌سازی بالاتنه و فرم‌دهی
    val shoulderContour: Float = 0.0f,   // فرم سرشانه
    val overallScale: Float = 0.0f,      // مقیاس کلی تناسب اندام
    val intensity: Float = 0.6f,         // شدت تأثیرگذاری زنده
    val isExperimental: Boolean = true,
    val limitationNoteEn: String = "Live model body contouring & shape enhancement",
    val limitationNoteFa: String = "فرم‌دهی زنده اندام و برجسته‌سازی/لاغرسازی در حین تصویربرداری"
)
