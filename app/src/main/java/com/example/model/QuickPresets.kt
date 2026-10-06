package com.example.model

enum class AppModePreset(
    val titleEn: String,
    val titleFa: String,
    val descriptionEn: String,
    val descriptionFa: String
) {
    STANDARD("Standard Studio", "استودیو استاندارد", "Manual controls for all studio layers", "تنظیمات دستی و دلخواه تمامی لایه‌ها"),
    TRIPLE_AI_SHIELD("Triple Protection Shield", "سپر سه‌گانه هوشمند (ردیاب + تاری + سواپ)", "Face Tracking + Blur Overlay + AI Face Swap running together", "فعال‌سازی همزمان هر ۳ قابلیت: ردیابی زنده + پوشش تاری + تعویض چهره هوش مصنوعی"),
    BOUTIQUE_SELLER("Boutique Try-On", "فروشگاه و مدلینگ لباس", "Auto face hide + blurred room + price badge", "محافظت کامل هویت فروشنده + تار کردن اتاق + تگ قیمت"),
    ANONYMOUS_REPORTER("Secure Reporter", "گزارشگر امن و افشاگری", "Full face mosaic + deep voice + zero metadata", "تاری کامل چهره + تغییر تن صدا + حذف ردپای GPS"),
    FACELESS_VLOG("Faceless Vlog & Story", "استوری و ولاگ مستعار", "9:16 story guide + aesthetic avatar + clean audio", "کادر استوری ۹:۱۶ + آواتار چهره + صدای شفاف"),
    INTERVIEW_DOC("Interview Documentary", "فیلمبرداری مصاحبه‌ای", "Two-shot framing grid + speech boost + 24fps", "گرید کادربندی مصاحبه + تقویت صدای گوینده + ریتم ۲۴ فریم"),
    CINEMATIC_SHORT("Cinematic Short Film", "فیلم کوتاه سینمایی", "21:9 anamorphic bars + film LUT + cinematic cadence", "کادر عریض سینمایی ۲۱:۹ + اصلاح رنگ فیلم + ریتم سینمایی")
}

data class WatermarkConfig(
    val enabled: Boolean = false,
    val handleText: String = "@My_Shop_ID",
    val opacity: Float = 0.70f,
    val position: WatermarkPosition = WatermarkPosition.BOTTOM_RIGHT
)

enum class WatermarkPosition(val titleFa: String, val titleEn: String) {
    BOTTOM_RIGHT("پایین راست", "Bottom Right"),
    BOTTOM_LEFT("پایین چپ", "Bottom Left"),
    TOP_RIGHT("بالا راست", "Top Right"),
    CENTER_TILED("تکرار وسط تصویر (ضد سرقت قوی)", "Center Repeated")
}
