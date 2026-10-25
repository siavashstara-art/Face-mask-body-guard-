package com.example.model

/**
 * Real Flash Date and Last-Minute Slot Offer from Event-mate.
 */
data class TalarFlashDateOffer(
    val id: String,
    val persianDate: String,
    val dayNameFa: String,
    val venueNameFa: String,
    val discountPercent: Int,
    val reasonBadgeFa: String,
    val capacityLeft: Int,
    val giftBonusFa: String,
    val originalPriceFa: String,
    val discountedPriceFa: String
)

/**
 * Wedding Venue & Garden Package from Event-mate.
 */
data class VenuePackageItem(
    val id: String,
    val titleFa: String,
    val subtitleFa: String,
    val capacityFa: String,
    val menuDetailsFa: List<String>,
    val specialFeaturesFa: List<String>,
    val giftVouchersFa: List<String>,
    val basePricePerGuestFa: String,
    val locationFa: String,
    val imageResId: Int
)
