package com.example.engine

import android.content.Context
import android.content.Intent
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class FaceCardRepository(private val context: Context) {

    private val _cards = MutableStateFlow(
        listOf(
            BusinessCardProfile(
                id = "card-salon",
                personaType = PersonaType.SALON_BEAUTY,
                fullName = "مونا رضایی",
                jobTitle = "مدیر ارشد و مدرس لاین تخصصی کراتین و احیا",
                companyName = "مجموعه زیبایی سالن‌یار (شعبه نیاوران)",
                phoneNumber = "+98 912 345 6789",
                email = "mona.beauty@salonyar.ir",
                instagramId = "mona_beauty_salon",
                telegramId = "salonyar_mona",
                whatsappNumber = "+989123456789",
                websiteUrl = "https://salonyar.ir/mona",
                address = "تهران، خیابان نیاوران، مجتمع تجاری نارون، طبقه ۳",
                bookingUrl = "https://salonyar.ir/book/mona",
                bio = "ارائه خدمات فوق‌تخصصی زیبایی، رنگ، هایلایت و احیای بیولوژیک مو با محصولات ارگانیک سوئیسی.",
                theme = CardGradientTheme.TERRACOTTA_GOLD,
                viewCount = 384,
                shareCount = 92,
                isPrimary = true
            ),
            BusinessCardProfile(
                id = "card-marketer",
                personaType = PersonaType.MARKETING_ACADEMY,
                fullName = "سیاوش حمیری",
                jobTitle = "استراتژیست ارشد رشد و عضو آکادمی مارکتینگ",
                companyName = "آکادمی بین‌المللی مارکتینگ و هوش تجاری",
                phoneNumber = "+98 935 888 1234",
                email = "siavashhamiri@gmail.com",
                instagramId = "siavash_marketing_pro",
                telegramId = "siavash_growth",
                whatsappNumber = "+989358881234",
                websiteUrl = "https://marketingacademy.ir/siavash",
                address = "تهران، مرکز نوآوری و فناوری‌های نوین",
                bookingUrl = "https://marketingacademy.ir/consult/siavash",
                bio = "طراحی سیستم‌های جذب لید خودکار، بهینه‌سازی نرخ تبدیل و تدوین کمپین‌های برندینگ برای پزشکان، سالن‌ها و مجتمع‌های لوکس.",
                theme = CardGradientTheme.EMERALD_SAGE,
                viewCount = 1240,
                shareCount = 310,
                isPrimary = false
            ),
            BusinessCardProfile(
                id = "card-sakhteman",
                personaType = PersonaType.SAKHTEMAN_PASS,
                fullName = "سیاوش حمیری",
                jobTitle = "عضو هیئت مدیره و ساکن واحد ۳۰۴",
                companyName = "برج مسکونی باغ آسمان (ساختمان‌یار)",
                phoneNumber = "+98 912 999 4567",
                email = "resident.sky304@sakhtemanyar.ir",
                instagramId = "",
                telegramId = "bagh_aseman_channel",
                whatsappNumber = "+989129994567",
                websiteUrl = "https://sakhtemanyar.ir/sky-garden",
                address = "تهران، اقدسیه، خیابان سپند، برج باغ آسمان",
                bookingUrl = "",
                bio = "کارت شناسایی دیجیتال ساکن و مجوز تردد هوشمند درب‌های مجتمع مسکونی باغ آسمان متصل به سامانه ساختمان‌یار.",
                theme = CardGradientTheme.ROYAL_NAVY_CYBER,
                viewCount = 76,
                shareCount = 14,
                isPrimary = false
            )
        )
    )
    val cards: StateFlow<List<BusinessCardProfile>> = _cards.asStateFlow()

    private val _activeCardId = MutableStateFlow("card-salon")
    val activeCardId: StateFlow<String> = _activeCardId.asStateFlow()

    private val _loyaltyStampCards = MutableStateFlow(
        listOf(
            LoyaltyStampCard(
                id = "stamp-salon",
                businessName = "سالن زیبایی VIP مونا",
                serviceCategory = "خدمات رنگ، لایت و کراتین",
                totalSlots = 10,
                filledStamps = 7,
                rewardTitle = "۵۰٪ تخفیف رنگ و براشینگ رایگان",
                tier = LoyaltyTier.GOLD,
                pointsBalance = 850
            ),
            LoyaltyStampCard(
                id = "stamp-clinic",
                businessName = "کلینیک مراقبت پوست و فیشیال پرنس",
                serviceCategory = "پکیج‌های درمانی فیشیال و پاکسازی",
                totalSlots = 8,
                filledStamps = 6,
                rewardTitle = "یک جلسه ماساژ و هایدرودرمی هدیه",
                tier = LoyaltyTier.SILVER,
                pointsBalance = 420
            )
        )
    )
    val loyaltyStampCards: StateFlow<List<LoyaltyStampCard>> = _loyaltyStampCards.asStateFlow()

    private val _salonSession = MutableStateFlow(SalonYarClientSession())
    val salonSession: StateFlow<SalonYarClientSession> = _salonSession.asStateFlow()

    private val _sakhtemanPass = MutableStateFlow(SakhtemanYarAccessPass())
    val sakhtemanPass: StateFlow<SakhtemanYarAccessPass> = _sakhtemanPass.asStateFlow()

    private val _marketingStats = MutableStateFlow(MarketingAcademyLeadStats())
    val marketingStats: StateFlow<MarketingAcademyLeadStats> = _marketingStats.asStateFlow()

    fun selectActiveCard(cardId: String) {
        _activeCardId.value = cardId
    }

    fun punchStamp(cardId: String) {
        val currentList = _loyaltyStampCards.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == cardId }
        if (index != -1) {
            val item = currentList[index]
            val newFilled = (item.filledStamps + 1).coerceAtMost(item.totalSlots)
            val newPoints = item.pointsBalance + 50
            currentList[index] = item.copy(filledStamps = newFilled, pointsBalance = newPoints)
            _loyaltyStampCards.value = currentList
        }
    }

    fun exportAndShareVCard(card: BusinessCardProfile) {
        try {
            val vCardContent = card.generateVCardString()
            val file = File(context.cacheDir, "${card.fullName.replace(" ", "_")}.vcf")
            file.writeText(vCardContent)

            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/x-vcard"
                putExtra(Intent.EXTRA_TEXT, "${card.fullName} - ${card.jobTitle}\n${card.companyName}\n${card.phoneNumber}\n${card.websiteUrl}")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(Intent.createChooser(sendIntent, "اشتراک‌گذاری کارت ویزیت FaceCard").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        } catch (_: Exception) {}
    }
}
