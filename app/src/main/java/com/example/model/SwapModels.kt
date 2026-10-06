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
        badgeFa = "۱۰۰٪ آفلاین (امن و بدون اینترنت)",
        descriptionEn = "Zero internet required. Runs strictly on-device on Redmi Note 8 with zero cloud data transmission.",
        descriptionFa = "بدون نیاز به اینترنت. پردازش کاملاً داخلی روی پردازنده گوشی بدون آپلود داده‌ها؛ بدون لگ و باتری‌محور."
    ),
    ONLINE_CLOUD_HYBRID(
        titleEn = "Online Cloud AI Generator",
        titleFa = "هوش مصنوعی آنلاین و ابری",
        badgeEn = "Cloud AI Active",
        badgeFa = "سرویس ابری هوشمند متصل",
        descriptionEn = "When connected to internet, accesses enhanced AI models, unlimited photorealistic faces, and deep styling.",
        descriptionFa = "با اتصال به اینترنت، مدل‌های نامحدود، چهره‌های واقع‌گرایانه فوتورئال و ژورنال‌های پیشرفته فعال می‌شوند."
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
    val selectedBodyId: String = "hourglass",
    val bodyBlendAlpha: Float = 0.88f,
    val bodyScale: Float = 1.0f,
    val bodyOffsetY: Float = 0.0f,
    val aiExecutionMode: AiEngineExecutionMode = AiEngineExecutionMode.OFFLINE_EDGE_LOCAL,
    val tripleShieldEnforced: Boolean = false // Enforces Face Detection + Blur Overlay + Swap simultaneously
)
