package com.example.model

enum class GuildBusinessType(
    val titleFa: String,
    val shortNameFa: String,
    val iconName: String,
    val annualBaseFeeToman: Long,
    val defaultCommissionPercent: Int
) {
    SALON_YAR(
        titleFa = "سالن‌های زیبایی و آرایشگاه بانوان و آقایان (سالن‌یار)",
        shortNameFa = "سالن‌یار",
        iconName = "Face",
        annualBaseFeeToman = 18_000_000L,
        defaultCommissionPercent = 20
    ),
    MAISON_YAR(
        titleFa = "مزون‌های لباس عروس، فرمالیته و تاکسیدو (مزونیار)",
        shortNameFa = "مزونیار",
        iconName = "Checkroom",
        annualBaseFeeToman = 24_000_000L,
        defaultCommissionPercent = 20
    ),
    TALAR_YAR(
        titleFa = "تالارها، باغ‌ها و عمارت‌های تشریفاتی (تالاریار)",
        shortNameFa = "تالاریار",
        iconName = "Celebration",
        annualBaseFeeToman = 35_000_000L,
        defaultCommissionPercent = 15
    ),
    DECOR_MATE(
        titleFa = "تشریفات، گل‌آرایی و ماشین عروس (دکورمیت)",
        shortNameFa = "دکورمیت",
        iconName = "LocalFlorist",
        annualBaseFeeToman = 15_000_000L,
        defaultCommissionPercent = 18
    ),
    DENTAL_YAR(
        titleFa = "کلینیک‌های تخصصی دندانپزشکی و کامپوزیت لبخند (دنتال‌یار)",
        shortNameFa = "دنتال‌یار",
        iconName = "MedicalServices",
        annualBaseFeeToman = 30_000_000L,
        defaultCommissionPercent = 15
    ),
    ATELIER_YAR(
        titleFa = "استودیوهای فیلمبرداری و آتلیه عروس (آتلیه‌یار)",
        shortNameFa = "آتلیه‌یار",
        iconName = "CameraAlt",
        annualBaseFeeToman = 22_000_000L,
        defaultCommissionPercent = 18
    )
}

enum class ContractSettlementStatus(
    val titleFa: String,
    val badgeColorHex: Long
) {
    CASH_SETTLED("تسویه نقدی آنی", 0xFF2E7D32),
    DIGITAL_DIRECT_NO_CHEQUE("تسویه دیجیتال بدون چک (کارت‌خوان/شبا)", 0xFF1565C0),
    COMMISSION_PENDING("در انتظار واریز سهم بازاریاب", 0xFFE65100)
}

data class GuildContractItem(
    val id: String,
    val guildType: GuildBusinessType,
    val businessName: String,
    val managerName: String,
    val phone: String,
    val address: String,
    val marketerName: String,
    val startDateShamsi: String,
    val expireDateShamsi: String,
    val totalAmountToman: Long,
    val marketerCommissionToman: Long,
    val settlementStatus: ContractSettlementStatus,
    val licenseKey: String,
    val isFaceGuardEnabled: Boolean = true,
    val isOneYearActive: Boolean = true
)

data class MarketerWalletProfile(
    val id: String,
    val name: String,
    val phone: String,
    val nationalCode: String,
    val totalContractsSigned: Int,
    val totalEarnedToman: Long,
    val paidToman: Long,
    val pendingToman: Long,
    val marketerCode: String,
    val commissionRatePercent: Int = 20
)

enum class LicenseModuleType(
    val titleFa: String,
    val prefix: String,
    val descriptionFa: String
) {
    FULL_SUITE_ANNUAL(
        titleFa = "لایسنس جامع ۱ ساله (پکیج طلایی تمام‌اصناف)",
        prefix = "LIC-ANNUAL",
        descriptionFa = "دسترسی کامل ۳۶۵ روزه به پنل مدیریت صنف، ثبت قراردادها، اتاق پرو دیجیتال و باشگاه مشتریان."
    ),
    FACEGUARD_VIRTUAL_TRYON(
        titleFa = "ماژول اختصاصی فیس‌گارد (FaceGuard AI)",
        prefix = "LIC-FACEGUARD",
        descriptionFa = "قفل‌گشایی الگوریتم پرو مجازی، تن‌خور سلبریتی‌ها و شبیه‌ساز چهره هوش مصنوعی آفلاین."
    ),
    CINEMATIC_DIRECTOR_PRO(
        titleFa = "دایرکتور سینمایی و ژست‌های هوشمند (Director Pro)",
        prefix = "LIC-DIRECTOR",
        descriptionFa = "تایم‌لاین سینمایی لحظه‌به‌لحظه، فیلترهای رنگ شامپاینی LUT و تولید ریلز ۱۵ ثانیه‌ای."
    ),
    OFFLINE_SMART_CAMERA(
        titleFa = "ماژول دوربین آفلاین و کادربندی هوشمند (Auto-Framing)",
        prefix = "LIC-CAMERA-AI",
        descriptionFa = "تقویت خودکار نور سالن، تراز افقی، لرزش‌گیر هوشمند ویدیو و فشرده‌سازی آنی محلی."
    ),
    CROSS_REFERRAL_NETWORK(
        titleFa = "شبکه ارجاع دوجانبه سالن و مزون (Referral Loop)",
        prefix = "LIC-REFERRAL",
        descriptionFa = "اتصال دوجانبه ویترین مزون به سالن زیبایی و صدور ووچرهای تخفیف هوشمند."
    )
}

data class GeneratedLicenseCode(
    val code: String,
    val moduleType: LicenseModuleType,
    val targetBusinessName: String,
    val marketerName: String,
    val issueDateShamsi: String,
    val validityDays: Int = 365,
    val isRedeemed: Boolean = false,
    val unlockSecretToken: String
)

data class SmartCameraFeatureConfig(
    val isAutoFramingEnabled: Boolean = true,
    val isLightingEnhancerEnabled: Boolean = true,
    val isStabilizerActive: Boolean = true,
    val lightingBoostLevel: Float = 0.35f,
    val horizonTiltAngle: Float = 0.4f,
    val framingStateFa: String = "کادر در زاویه طلایی قرار دارد (فاصله و تراز ایده‌آل)",
    val isLowLightEnvironment: Boolean = false
)
