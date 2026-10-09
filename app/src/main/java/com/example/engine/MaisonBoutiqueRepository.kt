package com.example.engine

import android.content.Context
import android.content.Intent
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class MaisonBoutiqueRepository(private val context: Context) {

    private val _maisons = MutableStateFlow<List<MaisonBoutiqueProfile>>(BundledMaisonData.verifiedMaisonProfiles)
    val maisons: StateFlow<List<MaisonBoutiqueProfile>> = _maisons.asStateFlow()

    private val _garments = MutableStateFlow<List<MaisonGarmentItem>>(BundledMaisonData.realMaisonGarments)
    val garments: StateFlow<List<MaisonGarmentItem>> = _garments.asStateFlow()

    private val _activeGarment = MutableStateFlow<MaisonGarmentItem?>(BundledMaisonData.realMaisonGarments.firstOrNull())
    val activeGarment: StateFlow<MaisonGarmentItem?> = _activeGarment.asStateFlow()

    private val _referralVouchers = MutableStateFlow<List<ReferralVoucher>>(
        listOf(
            ReferralVoucher(
                id = "ref-101",
                clientName = "خانم سارا کریمی (عروس)",
                garmentTitle = "لباس عروس پرنسسی سوپر رویال با دانتل فرانسه",
                maisonName = "مزون رویال کوتور (شهدخت)",
                maisonPhone = "۰۲۱-۲۲۸۰۴۵۱۰",
                maisonAddress = "نیاوران، سه راه یاسر، پلاک ۲۴",
                referringSalonName = "سالن زیبایی VIP مونا",
                discountPercent = 10,
                referralCode = "REF-BRIDE-9921",
                issueDate = "۱۴۰۳/۰۷/۱۸",
                status = "صادر شده - آماده مراجعه"
            ),
            ReferralVoucher(
                id = "ref-102",
                clientName = "آقای علیرضا باقری (داماد)",
                garmentTitle = "تاکسیدو دامادی یقه شال ساتن ایتالیایی",
                maisonName = "خانه تاکسیدو و پوشاک دامادی دیپلمات",
                maisonPhone = "۰۲۱-۸۸۷۶۳۲۹۰",
                maisonAddress = "جردن، نبش گلشهر، برج آیتا",
                referringSalonName = "آکادمی و سالن پیرایش VIP دیپلمات",
                discountPercent = 10,
                referralCode = "REF-GROOM-8430",
                issueDate = "۱۴۰۳/۰۷/۱۶",
                status = "تایید شده در مزون (تسویه پورسانت)"
            )
        )
    )
    val referralVouchers: StateFlow<List<ReferralVoucher>> = _referralVouchers.asStateFlow()

    // Visitor Onboarding Stats
    private val _visitorStats = MutableStateFlow(
        mapOf(
            "totalMaisonContracts" to "۳۴ مزون فعال",
            "monthlyReferralVolume" to "۱۸۵ ارجاع عروس و داماد",
            "commissionSettledToman" to "۲۴۸,۰۰۰,۰۰۰ تومان",
            "activeVisitorName" to "نماینده ارشد میدانی شهر توانا"
        )
    )
    val visitorStats: StateFlow<Map<String, String>> = _visitorStats.asStateFlow()

    fun selectGarment(id: String) {
        val selected = _garments.value.find { it.id == id }
        if (selected != null) {
            _activeGarment.value = selected
        }
    }

    fun generateReferralVoucher(
        clientName: String,
        garment: MaisonGarmentItem,
        salonName: String
    ): ReferralVoucher {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmm", Locale.US).format(Date())
        val randomSuffix = (1000..9999).random()
        val refCode = "REF-${garment.category.take(3).uppercase()}-$randomSuffix"

        val voucher = ReferralVoucher(
            id = "ref-$timeStamp",
            clientName = clientName.ifBlank { "مشتری VIP سالن" },
            garmentTitle = garment.titleFa,
            maisonName = garment.maisonName,
            maisonPhone = garment.maisonPhone,
            maisonAddress = garment.maisonAddress,
            referringSalonName = salonName.ifBlank { "سالن زیبایی همکار توانا" },
            discountPercent = 10,
            referralCode = refCode,
            issueDate = "امروز (${SimpleDateFormat("yyyy/MM/dd", Locale.US).format(Date())})",
            status = "صادر شده - آماده مراجعه به مزون"
        )

        _referralVouchers.value = listOf(voucher) + _referralVouchers.value
        return voucher
    }

    fun registerNewMaison(profile: MaisonBoutiqueProfile) {
        _maisons.value = _maisons.value + profile
    }

    fun addNewGarment(garment: MaisonGarmentItem) {
        _garments.value = listOf(garment) + _garments.value
        _activeGarment.value = garment
    }

    fun shareVoucherViaIntent(voucher: ReferralVoucher) {
        val text = buildString {
            append("✨ معرفی‌نامه رسمی پرو لباس عروس و داماد (اکوسیستم توانا)\n")
            append("━━━━━━━━━━━━━━━\n")
            append("👤 نام مشتری: ${voucher.clientName}\n")
            append("👗 لباس انتخابی: ${voucher.garmentTitle}\n")
            append("🏛️ مزون مقصد: ${voucher.maisonName}\n")
            append("📍 آدرس مزون: ${voucher.maisonAddress}\n")
            append("📞 شماره تماس و هماهنگی: ${voucher.maisonPhone}\n")
            append("💈 صادرکننده: ${voucher.referringSalonName}\n")
            append("🎟️ کد معرف تخفیف ۱۰٪: ${voucher.referralCode}\n")
            append("━━━━━━━━━━━━━━━\n")
            append("این پیام به منزله نوبت قطعی پرو و رزرو تخفیف نقدی در مزون است.")
        }

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "معرفی‌نامه اختصاصی مزون")
            putExtra(Intent.EXTRA_TEXT, text)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        try {
            context.startActivity(Intent.createChooser(intent, "ارسال معرفی‌نامه مزون").apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        } catch (_: Exception) {}
    }
}
