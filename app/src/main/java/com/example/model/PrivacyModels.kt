package com.example.model

import android.graphics.RectF

enum class BlurType {
    NONE,
    PIXELATE,
    GAUSSIAN
}

enum class PrivacyMaskType {
    NONE,
    FULL_SHIELD,       // Full face dark protective shield
    EYES_VISOR,        // Sleek black sensor / privacy bar across eyes
    TWIN_ROSES,        // دو عدد گل رز سرخ شاداب روی صورت و گونه‌ها (ترند فشن و بلاگری)
    KHALIJI_GOLD_BURQA,// نقاب طلایی اصیل خلیجی (طلای ۲۴ عیار سنتی)
    KHALIJI_NIQAB,     // نقاب عربی و نفتی خلیجی با خط طلایی
    BANDARI_BURQA,     // نقاب برقع بندری سنتی طلایی/سیاه
    MASQUERADE_BALL,   // ماسک بالماسکه مجلل و پردار ونیزی
    WESTERN_LACE,      // نقاب توری مشکی کلاسیک غربی (French Lace)
    BRIDAL_ROYAL_TIARA,// تاج سلطنتی و الماس عروس روی سر خانم‌ها
    FANTASY_CROWN,     // تاج فانتزی پرنسسی روی سر مدل
    MODERN_GEOMETRIC,  // نقاب مینیمال ژئومتریک مدرن
    MOUTH_GUARD,       // Anonymizing mouth and chin bar
    ANONYMOUS_HOOD,    // Dark silhouette shroud covering head and neck
    CYBER_NEON,        // Glowing cyber-grid matrix mask
    VENETIAN_LINES,    // Slotted horizontal privacy blinds
    HEART_EYES,        // نقاب فانتزی قلب روی چشمان مدل
    CAT_MYSTERY        // نقاب گربه‌ای فانتزی بالماسکه
}

enum class BodyBlurTarget(val titleEn: String, val titleFa: String) {
    NONE("Off", "غیرفعال"),
    NECK_AND_DECOLLETE("Neck & Décolleté", "گردن و دکلته"),
    SHOULDERS_AND_ARMS("Shoulders & Arms", "شانه‌ها و بازوها"),
    TORSO_AND_CHEST("Torso & Chest", "بالاتنه و تنه لباس"),
    FULL_BODY_EXCEPT_FACE("Full Body Modesty", "کل بدن (به‌جز چهره)")
}

data class TrackedFace(
    val id: Int,
    val bounds: RectF, // Normalized 0f..1f (left, top, right, bottom)
    val confidence: Float = 0.95f,
    val eyeCenterY: Float = 0.35f,
    val mouthCenterY: Float = 0.72f,
    val smoothedBounds: RectF = bounds
)

data class ManualPrivacyZone(
    val id: String = java.util.UUID.randomUUID().toString(),
    val bounds: RectF,
    val blurType: BlurType = BlurType.PIXELATE
)

data class PrivacyConfig(
    val blurType: BlurType = BlurType.PIXELATE,
    val blurIntensity: Float = 0.8f,
    val pixelBlockSize: Int = 24, // in pixels
    val maskType: PrivacyMaskType = PrivacyMaskType.EYES_VISOR,
    val autoFaceTracking: Boolean = true,
    val multiFaceEnabled: Boolean = true,
    val safetyMarginMultiplier: Float = 1.35f, // Dynamic safety expansion if confidence dips
    val bodyBlurTarget: BodyBlurTarget = BodyBlurTarget.NONE,
    val bodyBlurIntensity: Float = 0.85f,
    val manualZones: List<ManualPrivacyZone> = emptyList()
)
