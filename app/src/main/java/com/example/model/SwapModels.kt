package com.example.model

import com.example.R

enum class AiEngineExecutionMode(
    val titleEn: String,
    val titleFa: String,
    val badgeEn: String,
    val badgeFa: String,
    val descriptionEn: String,
    val descriptionFa: String
) {
    OFFLINE_EDGE_LOCAL(
        titleEn = "Offline Local Edge AI",
        titleFa = "هوش مصنوعی آفلاین و محلی (پیش‌فرض امن)",
        badgeEn = "100% Offline (Safe)",
        badgeFa = "۱۰۰٪ آفلاین (امن و بدون نیاز به اینترنت)",
        descriptionEn = "Zero internet required. Camera, masks, burqas, body contouring, voice mute, and privacy blur run strictly on-device with zero cloud traffic.",
        descriptionFa = "بدون نیاز به اینترنت. نقاب‌ها، برقع بندری، ردیاب چهره، مات‌سازی، تغییر/قطع صدا و اصلاح اندام کاملاً آفلاین روی پردازنده گوشی اجرا می‌شوند."
    ),
    ONLINE_CLOUD_HYBRID(
        titleEn = "Online Cloud AI Generator",
        titleFa = "چهره مجازی ابری (ویژه سالن‌های زیبایی)",
        badgeEn = "Only Online Feature",
        badgeFa = "تنها قابلیت آنلاین برنامه (ویژه سالن)",
        descriptionEn = "Only online feature: Generates photorealistic non-existent virtual AI faces for salon clients who decline face exposure, preserving their styled hair & ironed locks in portfolio.",
        descriptionFa = "تنها قابلیت آنلاین: تعویض چهره مشتری با چهره مجازی هوش مصنوعی برای مدیر آرایشگاه در مواردی که مشتری اجازه نمایش چهره نمی‌دهد (حفظ کامل مدل مو، اتوی مو، رنگ و کار دست آرایشگر در آرشیو)."
    )
}

data class FaceSwapAvatar(
    val id: String,
    val titleEn: String,
    val titleFa: String,
    val drawableResId: Int,
    val descriptionEn: String,
    val descriptionFa: String,
    val isOnlineGenerated: Boolean = false
)

data class BodySwapMannequin(
    val id: String,
    val titleEn: String,
    val titleFa: String,
    val drawableResId: Int,
    val descriptionEn: String,
    val descriptionFa: String
)

object BundledSwapItems {
    val avatars = listOf(
        FaceSwapAvatar(
            id = "salon_chic",
            titleEn = "Salon AI Virtual Model (Chic)",
            titleFa = "چهره مجازی سالن زیبایی (مخصوص اتو و براشینگ)",
            drawableResId = R.drawable.ic_avatar_salon_chic,
            descriptionEn = "Photorealistic synthetic AI face for beauty parlor archive. Keeps client hair and style while masking real face.",
            descriptionFa = "چهره مجازی هوش مصنوعی برای آرشیو آرایشگاه؛ موها، اتو و رنگ مو کاملاً حفظ و چهره واقعی مشتری پنهان می‌شود.",
            isOnlineGenerated = true
        ),
        FaceSwapAvatar(
            id = "salon_glam",
            titleEn = "Salon AI Glamour Face (Balayage)",
            titleFa = "چهره مجازی ژورنالی (مخصوص شینیون و بالیاژ)",
            drawableResId = R.drawable.ic_avatar_salon_glam,
            descriptionEn = "High-end salon synthetic persona for clients who refuse identity exposure in portfolio.",
            descriptionFa = "چهره غیرواقعی ژورنالی برای مشتریانی که اجازه نمایش چهره نمی‌دهند؛ مناسب شینیون، کراتین و لایت.",
            isOnlineGenerated = true
        ),
        FaceSwapAvatar(
            id = "glamour",
            titleEn = "Paris Haute Couture Supermodel",
            titleFa = "سوپرمدل فشن پاریس (محبوب برای ژورنال)",
            drawableResId = R.drawable.ic_avatar_glamour,
            descriptionEn = "High-fashion glamour model with sunglasses and styled lips",
            descriptionFa = "مدل شیک با عینک دودی فانتزی و آرایش نچرال جهت محافظت کامل از هویت"
        ),
        FaceSwapAvatar(
            id = "minimalist",
            titleEn = "Minimalist Visor Persona",
            titleFa = "مدل مینیمال با نوار محافظتی",
            drawableResId = R.drawable.ic_avatar_minimalist,
            descriptionEn = "Sleek contemporary boutique model with modern visor",
            descriptionFa = "مدل مدرن مینیمال با نوار سنسور شیک روی چشم‌ها"
        ),
        FaceSwapAvatar(
            id = "hollywood",
            titleEn = "Hollywood Retro Glamour",
            titleFa = "مدل کلاسیک سینمای قدیم",
            drawableResId = R.drawable.ic_avatar_hollywood,
            descriptionEn = "Vintage cinema glamour persona with glasses and lips",
            descriptionFa = "پرسونای جذاب هالیوود کلاسیک با عینک و لب‌های وینتیج"
        ),
        FaceSwapAvatar(
            id = "mannequin",
            titleEn = "Studio Fashion Mannequin",
            titleFa = "مانکن فشن استودیویی",
            drawableResId = R.drawable.ic_avatar_mannequin,
            descriptionEn = "Minimalist sculpted boutique display head",
            descriptionFa = "سرمجسمه مینیمال و شیک ویترین بوتیک"
        ),
        FaceSwapAvatar(
            id = "cyber",
            titleEn = "Cyber Anonymous Persona",
            titleFa = "آواتار سایبر ناشناس",
            drawableResId = R.drawable.ic_avatar_cyber,
            descriptionEn = "Polygonal geometric persona with glowing visor",
            descriptionFa = "آواتار هندسی مدرن با وایزر محافظتی"
        )
    )

    val mannequins = listOf(
        BodySwapMannequin(
            id = "shelf_bbl",
            titleEn = "Brazilian Shelf Butt & Lifted Bust (VIP)",
            titleFa = "باسن طاقچه‌ای گرد برزیلی + لیفت سینه (VIP)",
            drawableResId = R.drawable.ic_mannequin_shelf_bbl,
            descriptionEn = "High shelf-butt contour with perky lifted bust and snatched waist. Elegant curves without bulkiness.",
            descriptionFa = "باسن گرد و طاقچه‌ای لیفت شده (بدون چاقی نامتناسب)، سینه لیفت‌شده و خوش‌فرم، کمر باریک زنبوری مخصوص لباس شب، عروس و لباس زیر."
        ),
        BodySwapMannequin(
            id = "hourglass",
            titleEn = "Luxury Hourglass Swimwear Form",
            titleFa = "مانکن ساعت شنی لباس زیر و شنا",
            drawableResId = R.drawable.ic_mannequin_hourglass,
            descriptionEn = "Sculpted slim waist & contoured hips tailored for underwear display",
            descriptionFa = "تنه ساعت شنی با خط کمر باریک مخصوص نمایش لباس زیر و مایو"
        ),
        BodySwapMannequin(
            id = "boutique",
            titleEn = "Luxury Boutique Torso",
            titleFa = "مانکن بوتیک و لباس خواب",
            drawableResId = R.drawable.ic_mannequin_boutique,
            descriptionEn = "Full hourglass mannequin tailored for swimwear and lingerie",
            descriptionFa = "تنه مانکن ساعت شنی استاندارد مخصوص لباس زیر و شورت"
        ),
        BodySwapMannequin(
            id = "tailor",
            titleEn = "Vintage Tailor Form",
            titleFa = "مانکن خیاطی کلاسیک",
            drawableResId = R.drawable.ic_mannequin_tailor,
            descriptionEn = "Professional dress form with guide stitching and stand",
            descriptionFa = "مانکن خیاطی با خطوط راهنمای اندازه و دوخت"
        ),
        BodySwapMannequin(
            id = "athletic",
            titleEn = "Athletic Fit Silhouette",
            titleFa = "فرم اندام فیتنس و ورزشی",
            drawableResId = R.drawable.ic_mannequin_athletic,
            descriptionEn = "Modern toned contour with dark terracotta aesthetic",
            descriptionFa = "سیلوئت متناسب ورزشی با زمینه تیره تراکوتا"
        )
    )
}

data class SwapConfig(
    val faceSwapEnabled: Boolean = false,
    val selectedAvatarId: String = "glamour",
    val faceBlendAlpha: Float = 0.95f,
    val bodySwapEnabled: Boolean = false,
    val selectedBodyId: String = "shelf_bbl",
    val bodyBlendAlpha: Float = 0.88f,
    val bodyScale: Float = 1.0f,
    val bodyOffsetY: Float = 0.0f,
    val shelfButtContour: Float = 0.90f,   // فرم‌دهی و گردی طاقچه‌ای باسن برزیلی (بدون چاقی بی‌ریخت)
    val bustLiftFirmness: Float = 0.88f,   // لیفت و فرم‌دهی زیبا و مشتری‌پسند سینه
    val waistTaperRatio: Float = 0.80f,    // باریک‌سازی کمر زنبوری متناسب
    val aiExecutionMode: AiEngineExecutionMode = AiEngineExecutionMode.OFFLINE_EDGE_LOCAL,
    val tripleShieldEnforced: Boolean = false // Enforces Face Detection + Blur Overlay + Swap simultaneously
)
