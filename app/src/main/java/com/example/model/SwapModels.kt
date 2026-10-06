package com.example.model

import com.example.R

data class FaceSwapAvatar(
    val id: String,
    val titleEn: String,
    val titleFa: String,
    val drawableResId: Int,
    val descriptionEn: String,
    val descriptionFa: String
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
            id = "boutique",
            titleEn = "Luxury Boutique Torso",
            titleFa = "مانکن بوتیک و لباس زیر",
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
    val selectedAvatarId: String = "hollywood",
    val faceBlendAlpha: Float = 0.95f,
    val bodySwapEnabled: Boolean = false,
    val selectedBodyId: String = "boutique",
    val bodyBlendAlpha: Float = 0.88f,
    val bodyScale: Float = 1.0f,
    val bodyOffsetY: Float = 0.0f
)
