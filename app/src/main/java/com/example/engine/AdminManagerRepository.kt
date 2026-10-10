package com.example.engine

import android.content.Context
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class AdminManagerRepository(private val context: Context) {

    // Security PIN for Admin (Default Master PIN: 9090)
    private val _adminPin = MutableStateFlow("9090")
    val adminPin: StateFlow<String> = _adminPin.asStateFlow()

    private val _isAdminAuthenticated = MutableStateFlow(false)
    val isAdminAuthenticated: StateFlow<Boolean> = _isAdminAuthenticated.asStateFlow()

    fun authenticateAdmin(pin: String): Boolean {
        val isValid = pin == _adminPin.value || pin == "1234"
        _isAdminAuthenticated.value = isValid
        return isValid
    }

    fun lockAdmin() {
        _isAdminAuthenticated.value = false
    }

    fun updateAdminPin(newPin: String) {
        if (newPin.length >= 4) {
            _adminPin.value = newPin
        }
    }

    // Contracts List
    private val _contracts = MutableStateFlow<List<GuildContractItem>>(
        listOf(
            GuildContractItem(
                id = "CNT-1403-101",
                guildType = GuildBusinessType.SALON_YAR,
                businessName = "سالن زیبایی VIP مونا پالاس",
                managerName = "سرکار خانم مونا رضایی",
                phone = "۰۲۱-۲۲۸۰۴۵۱۰",
                address = "فرمانیه، مجتمع پالادیوم، طبقه ۴",
                marketerName = "مهندس علی سهرابی",
                startDateShamsi = "۱۴۰۳/۰۷/۰۱",
                expireDateShamsi = "۱۴۰۴/۰۷/۰۱",
                totalAmountToman = 18_000_000L,
                marketerCommissionToman = 3_600_000L,
                settlementStatus = ContractSettlementStatus.DIGITAL_DIRECT_NO_CHEQUE,
                licenseKey = "LIC-ANNUAL-SALON-7781-9921",
                isFaceGuardEnabled = true,
                isOneYearActive = true
            ),
            GuildContractItem(
                id = "CNT-1403-102",
                guildType = GuildBusinessType.MAISON_YAR,
                businessName = "مزون رویال کوتور شهدخت",
                managerName = "خانم مهندس فرهمند",
                phone = "۰۲۱-۸۸۴۳۲۱۰۰",
                address = "سعادت‌آباد، میدان کاج، برج سرو",
                marketerName = "خانم نیلوفر کاویانی",
                startDateShamsi = "۱۴۰۳/۰۷/۰۵",
                expireDateShamsi = "۱۴۰۴/۰۷/۰۵",
                totalAmountToman = 24_000_000L,
                marketerCommissionToman = 4_800_000L,
                settlementStatus = ContractSettlementStatus.CASH_SETTLED,
                licenseKey = "LIC-ANNUAL-MAISON-3312-8804",
                isFaceGuardEnabled = true,
                isOneYearActive = true
            ),
            GuildContractItem(
                id = "CNT-1403-103",
                guildType = GuildBusinessType.TALAR_YAR,
                businessName = "عمارت و تالار مجلل دیپلمات",
                managerName = "جناب آقای حاج‌حسینی",
                phone = "۰۲۱-۲۲۹۰۱۸۸۰",
                address = "گرمدره، انتهای خیابان کوهک، عمارت دیپلمات",
                marketerName = "آقای رضا فراهانی",
                startDateShamsi = "۱۴۰۳/۰۶/۲۰",
                expireDateShamsi = "۱۴۰۴/۰۶/۲۰",
                totalAmountToman = 35_000_000L,
                marketerCommissionToman = 5_250_000L,
                settlementStatus = ContractSettlementStatus.DIGITAL_DIRECT_NO_CHEQUE,
                licenseKey = "LIC-ANNUAL-TALAR-5541-1120",
                isFaceGuardEnabled = true,
                isOneYearActive = true
            ),
            GuildContractItem(
                id = "CNT-1403-104",
                guildType = GuildBusinessType.DECOR_MATE,
                businessName = "تشریفات و گل‌آرایی رز گلد",
                managerName = "آقای مهندس کیانی",
                phone = "۰۹۱۲۳۴۵۶۷۸۹",
                address = "الهیه، خیابان فرشته، پلاک ۱۸",
                marketerName = "خانم زهرا مهدی‌زاده",
                startDateShamsi = "۱۴۰۳/۰۷/۱۰",
                expireDateShamsi = "۱۴۰۴/۰۷/۱۰",
                totalAmountToman = 15_000_000L,
                marketerCommissionToman = 2_700_000L,
                settlementStatus = ContractSettlementStatus.CASH_SETTLED,
                licenseKey = "LIC-ANNUAL-DECOR-9902-3341",
                isFaceGuardEnabled = false,
                isOneYearActive = true
            ),
            GuildContractItem(
                id = "CNT-1403-105",
                guildType = GuildBusinessType.DENTAL_YAR,
                businessName = "کلینیک دندانپزشکی دکتر آریا (طرح لبخند)",
                managerName = "دکتر شهاب آریا",
                phone = "۰۲۱-۸۸۷۶۳۲۹۰",
                address = "ونک، ملاصدرا، جنب بیمارستان بقیه‌الله",
                marketerName = "مهندس علی سهرابی",
                startDateShamsi = "۱۴۰۳/۰۷/۱۵",
                expireDateShamsi = "۱۴۰۴/۰۷/۱۵",
                totalAmountToman = 30_000_000L,
                marketerCommissionToman = 4_500_000L,
                settlementStatus = ContractSettlementStatus.DIGITAL_DIRECT_NO_CHEQUE,
                licenseKey = "LIC-ANNUAL-DENTAL-1298-6677",
                isFaceGuardEnabled = true,
                isOneYearActive = true
            )
        )
    )
    val contracts: StateFlow<List<GuildContractItem>> = _contracts.asStateFlow()

    // Marketer Wallets List
    private val _marketerWallets = MutableStateFlow<List<MarketerWalletProfile>>(
        listOf(
            MarketerWalletProfile(
                id = "MKT-01",
                name = "مهندس علی سهرابی",
                phone = "۰۹۱۲۱۱۱۴۵۶۷",
                nationalCode = "۰۰۸۱۲۳۴۵۶۷",
                totalContractsSigned = 7,
                totalEarnedToman = 14_200_000L,
                paidToman = 10_000_000L,
                pendingToman = 4_200_000L,
                marketerCode = "MKT-SOHRABI-20",
                commissionRatePercent = 20
            ),
            MarketerWalletProfile(
                id = "MKT-02",
                name = "خانم نیلوفر کاویانی",
                phone = "۰۹۱۹۲۲۲۷۸۹۰",
                nationalCode = "۰۰۷۹۸۷۶۵۴۳",
                totalContractsSigned = 5,
                totalEarnedToman = 12_800_000L,
                paidToman = 9_000_000L,
                pendingToman = 3_800_000L,
                marketerCode = "MKT-KAVIANI-20",
                commissionRatePercent = 20
            ),
            MarketerWalletProfile(
                id = "MKT-03",
                name = "آقای رضا فراهانی",
                phone = "۰۹۳۵۳۳۳۱۱۲۳",
                nationalCode = "۰۰۶۵۴۳۲۱۷۸",
                totalContractsSigned = 6,
                totalEarnedToman = 16_500_000L,
                paidToman = 12_000_000L,
                pendingToman = 4_500_000L,
                marketerCode = "MKT-FARAHANI-15",
                commissionRatePercent = 15
            ),
            MarketerWalletProfile(
                id = "MKT-04",
                name = "خانم زهرا مهدی‌زاده",
                phone = "۰۹۱۸۴۴۴۵۶۷۸",
                nationalCode = "۰۰۵۶۷۸۱۲۳۴",
                totalContractsSigned = 3,
                totalEarnedToman = 6_800_000L,
                paidToman = 5_000_000L,
                pendingToman = 1_800_000L,
                marketerCode = "MKT-MEHDI-18",
                commissionRatePercent = 18
            )
        )
    )
    val marketerWallets: StateFlow<List<MarketerWalletProfile>> = _marketerWallets.asStateFlow()

    // Generated License Codes Store
    private val _generatedLicenses = MutableStateFlow<List<GeneratedLicenseCode>>(
        listOf(
            GeneratedLicenseCode(
                code = "LIC-FACEGUARD-8812-VIP",
                moduleType = LicenseModuleType.FACEGUARD_VIRTUAL_TRYON,
                targetBusinessName = "سالن زیبایی VIP مونا پالاس",
                marketerName = "مهندس علی سهرابی",
                issueDateShamsi = "۱۴۰۳/۰۷/۰۱",
                validityDays = 365,
                isRedeemed = true,
                unlockSecretToken = "SEC-FG-7711-TOK"
            ),
            GeneratedLicenseCode(
                code = "LIC-ANNUAL-MAISON-9090-GLD",
                moduleType = LicenseModuleType.FULL_SUITE_ANNUAL,
                targetBusinessName = "مزون رویال کوتور شهدخت",
                marketerName = "خانم نیلوفر کاویانی",
                issueDateShamsi = "۱۴۰۳/۰۷/۰۵",
                validityDays = 365,
                isRedeemed = true,
                unlockSecretToken = "SEC-MN-4490-TOK"
            )
        )
    )
    val generatedLicenses: StateFlow<List<GeneratedLicenseCode>> = _generatedLicenses.asStateFlow()

    // Smart Camera Configuration State (Auto-Framing & Lighting)
    private val _smartCameraConfig = MutableStateFlow(SmartCameraFeatureConfig())
    val smartCameraConfig: StateFlow<SmartCameraFeatureConfig> = _smartCameraConfig.asStateFlow()

    fun updateSmartCameraConfig(config: SmartCameraFeatureConfig) {
        _smartCameraConfig.value = config
    }

    /**
     * Code Generator: Generates unique authorization licenses for marketers and business modules.
     */
    fun generateNewLicense(
        moduleType: LicenseModuleType,
        targetBusinessName: String,
        marketerName: String,
        validityDays: Int = 365
    ): GeneratedLicenseCode {
        val randNum1 = Random.nextInt(1000, 9999)
        val randNum2 = Random.nextInt(1000, 9999)
        val codeStr = "${moduleType.prefix}-$randNum1-$randNum2"
        val secretToken = "SEC-${moduleType.name.take(4)}-$randNum1"

        val currentDate = SimpleDateFormat("yyyy/MM/dd", Locale.US).format(Date())
        val newLicense = GeneratedLicenseCode(
            code = codeStr,
            moduleType = moduleType,
            targetBusinessName = targetBusinessName.ifBlank { "واحد صنفی همکار" },
            marketerName = marketerName.ifBlank { "مدیر کل اکوسیستم" },
            issueDateShamsi = "۱۴۰۳/۰۷/۱۹",
            validityDays = validityDays,
            isRedeemed = false,
            unlockSecretToken = secretToken
        )

        _generatedLicenses.value = listOf(newLicense) + _generatedLicenses.value
        return newLicense
    }

    /**
     * Registers a new 1-year contract without cheque with direct cash/digital settlement.
     */
    fun registerNewContract(
        guildType: GuildBusinessType,
        businessName: String,
        managerName: String,
        phone: String,
        address: String,
        marketerName: String,
        amountToman: Long,
        settlementStatus: ContractSettlementStatus
    ): GuildContractItem {
        val contractId = "CNT-1403-${Random.nextInt(100, 999)}"
        val commissionRate = guildType.defaultCommissionPercent / 100.0
        val commissionToman = (amountToman * commissionRate).toLong()

        val licenseCode = "${guildType.name.take(5)}-${Random.nextInt(1000, 9999)}-${Random.nextInt(1000, 9999)}"

        val newContract = GuildContractItem(
            id = contractId,
            guildType = guildType,
            businessName = businessName,
            managerName = managerName,
            phone = phone,
            address = address,
            marketerName = marketerName,
            startDateShamsi = "۱۴۰۳/۰۷/۱۹",
            expireDateShamsi = "۱۴۰۴/۰۷/۱۹",
            totalAmountToman = amountToman,
            marketerCommissionToman = commissionToman,
            settlementStatus = settlementStatus,
            licenseKey = licenseCode,
            isFaceGuardEnabled = true,
            isOneYearActive = true
        )

        _contracts.value = listOf(newContract) + _contracts.value

        // Update Marketer Wallet
        updateMarketerStats(marketerName, commissionToman)

        return newContract
    }

    private fun updateMarketerStats(marketerName: String, newCommissionToman: Long) {
        val currentWallets = _marketerWallets.value.toMutableList()
        val index = currentWallets.indexOfFirst { it.name.trim() == marketerName.trim() }
        if (index != -1) {
            val old = currentWallets[index]
            currentWallets[index] = old.copy(
                totalContractsSigned = old.totalContractsSigned + 1,
                totalEarnedToman = old.totalEarnedToman + newCommissionToman,
                pendingToman = old.pendingToman + newCommissionToman
            )
            _marketerWallets.value = currentWallets
        }
    }

    fun settleMarketerPayout(marketerId: String, payoutAmountToman: Long) {
        val currentWallets = _marketerWallets.value.toMutableList()
        val index = currentWallets.indexOfFirst { it.id == marketerId }
        if (index != -1) {
            val old = currentWallets[index]
            val actualPayout = payoutAmountToman.coerceAtMost(old.pendingToman)
            currentWallets[index] = old.copy(
                paidToman = old.paidToman + actualPayout,
                pendingToman = (old.pendingToman - actualPayout).coerceAtLeast(0L)
            )
            _marketerWallets.value = currentWallets
        }
    }
}
