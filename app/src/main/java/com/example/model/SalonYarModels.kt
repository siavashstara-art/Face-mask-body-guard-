package com.example.model

import android.graphics.Bitmap

/**
 * Live customization profile for SalonYar visitor sales pitch.
 */
data class SalonPitchProfile(
    val salonName: String = "سالن زیبایی شاین VIP",
    val managerName: String = "سرکار خانم مریم حسینی",
    val phoneNumber: String = "۰۹۱۲۳۴۵۶۷۸۹ - ۰۲۱۸۸۸۸۸۸۸۸",
    val address: String = "تهران، سعادت‌آباد، صرافان شمالی، پلاک ۱۲",
    val instagramHandle: String = "@shine_beauty_vip",
    val websiteUrl: String = "www.shinebeauty.ir",
    val customLogoUri: String? = null,
    val customBitmap: Bitmap? = null
)

/**
 * Automated Customer Retention & Return Campaign rule.
 */
data class CustomerRetentionCampaign(
    val id: String,
    val titleFa: String,
    val titleEn: String,
    val intervalDays: Int,
    val sampleMessageFa: String,
    val sampleMessageEn: String,
    val discountPercent: Int,
    val isEnabled: Boolean = true
)

/**
 * Visual Capability Feature Showcase Item with visual example photos.
 */
data class SalonVisualFeature(
    val id: String,
    val titleFa: String,
    val titleEn: String,
    val subtitleFa: String,
    val descriptionFa: String,
    val imageResId: Int,
    val category: String, // "BRIDE_GROOM_PHOTOSHOP", "MAKEUP_SHINION", "DOUBTFUL_CLIENT", "BEFORE_AFTER"
    val highlightsFa: List<String>
)
