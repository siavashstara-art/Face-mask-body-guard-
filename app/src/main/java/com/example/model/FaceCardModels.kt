package com.example.model

enum class PersonaType(
    val titleFa: String,
    val titleAr: String,
    val titleEn: String,
    val defaultRoleFa: String,
    val defaultBrandFa: String
) {
    SALON_BEAUTY(
        titleFa = "سالن زیبایی و کلینیک (سالن‌یار)",
        titleAr = "صالون التجميل والعيادة",
        titleEn = "Salon & Clinic (SalonYar)",
        defaultRoleFa = "مدیر ارشد لاین رنگ و کراتین",
        defaultBrandFa = "سالن زیبایی وی‌آی‌پی مونا"
    ),
    MARKETING_ACADEMY(
        titleFa = "بازاریاب و مشاور رشد (آکادمی سیاوش)",
        titleAr = "مسوق ومستشار النمو",
        titleEn = "Growth Marketer (Academy)",
        defaultRoleFa = "استراتژیست رشد و لید مارکتینگ",
        defaultBrandFa = "آکادمی بین‌المللی مارکتینگ"
    ),
    SAKHTEMAN_PASS(
        titleFa = "مدیر و ساکن مجتمع (ساختمان‌یار)",
        titleAr = "إدارة وسكان المجمع (ساختمان‌یار)",
        titleEn = "Resident & Manager (SakhtemanYar)",
        defaultRoleFa = "رئیس هیئت مدیره برج مسکونی",
        defaultBrandFa = "برج مجلل پارسه (واحد ۳۰۴)"
    ),
    CORPORATE_VIP(
        titleFa = "کسب‌وکار شرکتی و مدیرعامل",
        titleAr = "الأعمال التجارية والشركات",
        titleEn = "Corporate & Executive",
        defaultRoleFa = "مدیر توسعه کسب‌وکار و سرمایه‌گذاری",
        defaultBrandFa = "هلدینگ تجاری توانا"
    )
}

enum class CardGradientTheme(val displayName: String, val startHex: Long, val endHex: Long) {
    TERRACOTTA_GOLD("تراکوتا و رزگلد", 0xFFC85A32, 0xFFE07A5F),
    EMERALD_SAGE("زمردی و سبز مریم‌گلی", 0xFF2D5A3C, 0xFF4A7C59),
    ROYAL_NAVY_CYBER("سورمه‌ای متالیک و نقره‌ای", 0xFF1A2332, 0xFF2C3E55),
    OBSIDIAN_GOLD("مشکی آبنوس و طلایی لوکس", 0xFF141416, 0xFF2A2825)
}

data class BusinessCardProfile(
    val id: String,
    val personaType: PersonaType,
    val fullName: String,
    val jobTitle: String,
    val companyName: String,
    val phoneNumber: String,
    val email: String,
    val instagramId: String,
    val telegramId: String,
    val whatsappNumber: String,
    val websiteUrl: String,
    val address: String,
    val bookingUrl: String,
    val bio: String,
    val theme: CardGradientTheme,
    val viewCount: Int = 142,
    val shareCount: Int = 38,
    val isPrimary: Boolean = false
) {
    fun generateVCardString(): String {
        return """
            BEGIN:VCARD
            VERSION:3.0
            FN:$fullName
            TITLE:$jobTitle
            ORG:$companyName
            TEL;TYPE=CELL:$phoneNumber
            EMAIL:$email
            URL:$websiteUrl
            NOTE:$bio | FaceCard Digital Passport
            END:VCARD
        """.trimIndent()
    }
}

enum class LoyaltyTier(val titleFa: String, val badgeColor: Long, val discountRate: String) {
    BRONZE("برنزی (عضو تازه)", 0xFFCD7F32, "۵٪ تخفیف"),
    SILVER("نقره‌ای (وفادار)", 0xFFC0C0C0, "۱۰٪ تخفیف"),
    GOLD("طلایی (مشتری دائم)", 0xFFFFD700, "۱۵٪ تخفیف"),
    DIAMOND("الماس وی‌آی‌پی", 0xFF00CED1, "۲۵٪ تخفیف + هدیه ویژه")
}

data class LoyaltyStampCard(
    val id: String,
    val businessName: String,
    val serviceCategory: String,
    val totalSlots: Int = 10,
    val filledStamps: Int = 7,
    val rewardTitle: String = "یک جلسه خدمات رایگان یا ۵۰٪ تخفیف",
    val tier: LoyaltyTier = LoyaltyTier.GOLD,
    val pointsBalance: Int = 850,
    val lastVisitDate: String = "۱۴۰۳/۰۷/۱۵"
)

data class DiscountVoucher(
    val code: String,
    val title: String,
    val discountPercent: Int,
    val expiryDate: String,
    val isUsed: Boolean = false
)

data class SalonYarClientSession(
    val salonName: String = "سالن زیبایی تخصصی مونا",
    val customerCode: String = "SY-4892",
    val nextAppointmentDate: String = "سه‌شنبه ۱۸ مهر - ساعت ۱۶:۳۰",
    val serviceType: String = "رنگ و لایت حرفه‌ای + کوتاهی ژورنالی",
    val beautyMasterName: String = "خانم سارا کریمی",
    val pointsEarned: Int = 350
)

data class SakhtemanYarAccessPass(
    val buildingName: String = "برج مسکونی باغ آسمان",
    val unitNumber: String = "واحد ۱۲ (طبقه ۴)",
    val residentName: String = "سیاوش حمیری",
    val digitalQrAccessKey: String = "PASS-SKY-9824-OK",
    val maintenanceBalanceStatus: String = "تسویه شده (بدون بدهی)",
    val smartDoorStatus: String = "مجوز تردد خودکار فعال است"
)

data class MarketingAcademyLeadStats(
    val affiliateCode: String = "SIAVASH-ACADEMY-77",
    val totalClicks: Int = 584,
    val registeredLeads: Int = 42,
    val conversionRate: String = "۷.۲٪",
    val commissionBalanceToman: String = "۴,۸۵۰,۰۰۰ تومان"
)
