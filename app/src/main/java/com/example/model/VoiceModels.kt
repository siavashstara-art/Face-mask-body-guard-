package com.example.model

enum class PitchProfile(
    val titleEn: String,
    val titleFa: String,
    val descriptionFa: String,
    val pitchFactor: Float,
    val isNatural: Boolean = true
) {
    NATURAL("Original Natural", "صدای اصلی و بدون تغییر", "صدای بدون دستکاری و کاملاً طبیعی", 1.0f, true),
    WARM_ACOUSTIC("Warm Natural Tone", "طبیعی گرم و آکوستیک", "تغییر صدای ارگانیک و لطیف، کاملاً بدون حالت مصنوعی یا رباتیک", 0.96f, true),
    CLARITY_PODCAST("Crystal Clear Speech", "طبیعی شفاف و رسا", "افزایش وضوح گفتار با حفظ رنگ و ارتعاش طبیعی حنجره", 1.04f, true),
    GENTLE_AIR("Gentle Soft Voice", "طبیعی نرم و ملایم", "نرم‌کننده فرکانس‌های تیز با حس طبیعی و دوستانه", 1.08f, true),
    DEEP_CONFIDENT("Deep Resonant Voice", "طبیعی باوقار و عمیق", "بم طبیعی و آرامش‌بخش بدون اغراق مصنوعی", 0.91f, true),
    DEEP_GUARD("Deep Guard (Privacy)", "بم حفاظتی حریم خصوصی", "پنهان‌سازی هویت با فرکانس پایین", 0.82f, false),
    CHARACTER_CYBER("Cyber Anonymous", "ناشناس دیجیتال", "تغییر صدای محافظتی برای وب", 0.75f, false)
}

data class VoiceConfig(
    val isMuted: Boolean = false, // قطع کامل صدای ویدیو هنگام فیلمبرداری (Mute)
    val pitchProfile: PitchProfile = PitchProfile.NATURAL,
    val micGain: Float = 1.0f,
    val noiseReduction: Boolean = true,
    val naturalAcousticSmoothing: Boolean = true, // فیلتر طبیعی‌سازی بدون حالت بم یا رباتیک
    val syncOffsetMs: Int = 0, // Calibration for audio/video sync
    val privacySealEn: String = "Zero Cloud Audio: Microphone stream processed 100% locally on device",
    val privacySealFa: String = "صدای آفلاین: صدای میکروفون منحصراً روی سخت‌افزار گوشی پردازش می‌شود"
)

