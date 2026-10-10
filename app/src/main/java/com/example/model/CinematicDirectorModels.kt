package com.example.model

enum class CinematicScenarioType(
    val titleFa: String,
    val subtitleFa: String,
    val totalDurationSec: Int,
    val defaultLut: CinematicLutFilter,
    val descriptionFa: String
) {
    SALON_AND_VEIL(
        titleFa = "شینیون، میکاپ و تور عروس (آینه سالن)",
        subtitleFa = "۴ پلان سینمایی جلوه میکاپ، براشینگ مو و لبخند در آینه",
        totalDurationSec = 15,
        defaultLut = CinematicLutFilter.CHAMPAGNE_GOLD,
        descriptionFa = "راهنمای لحظه‌به‌لحظه برای تصویربرداری از کار دست میکاپ‌آرتیست در سالن زیبایی با کادربندی ویژه آینه و بافت مو."
    ),
    MAISON_360_SPIN(
        titleFa = "پرو ۳۶۰ درجه و نمایش دنباله لباس مزون",
        subtitleFa = "چرخش ژورنالی، نمایش دانتل و ژپون با خطوط شبح متحرک",
        totalDurationSec = 15,
        defaultLut = CinematicLutFilter.VINTAGE_35MM,
        descriptionFa = "هدایت قدم‌به‌قدم عروس برای چرخش آرام، نمایش بالاتنه، کمر کرست و دنباله رویایی لباس مزون بدون خطای ژست."
    ),
    LUXURY_CAR_KEYS(
        titleFa = "سوییچ خودروی لوکس و ماشین گل‌آرایی‌شده",
        subtitleFa = "جلوه حلقه نامزدی، سوییچ ماشین عروس و خروج از سالن",
        totalDurationSec = 15,
        defaultLut = CinematicLutFilter.ROYAL_EMERALD,
        descriptionFa = "پلان‌های کلوزآپ دست‌ها، لمس نشان خودرو، درخشش نگین حلقه و خروج پرانرژی همراه با سوییچ خودروی عروس."
    ),
    GRAND_ENTRY_HALL(
        titleFa = "ورود باشکوه به عمارت و تالار (Grand Entry)",
        subtitleFa = "قدم‌زدن تشریفاتی دونفره، آتش‌بازی سرد و استقرار در استیج",
        totalDurationSec = 15,
        defaultLut = CinematicLutFilter.CHAMPAGNE_GOLD,
        descriptionFa = "تایم‌لاین گام‌های موزون دونفره، زاویه دسته‌گل، نگاه عاشقانه و ژست فینال روی استیج رقص تالار."
    )
}

enum class GhostPoseShapeType {
    BRIDE_MIRROR_VEIL,
    DRESS_360_SPIN,
    CAR_KEYS_RING,
    GRAND_ENTRY_ROYAL
}

enum class CinematicLutFilter(
    val titleFa: String,
    val colorOverlayHex: Long,
    val descriptionFa: String,
    val badgeFa: String
) {
    CHAMPAGNE_GOLD(
        titleFa = "طلایی شامپاینی لوکس (Champagne Gold)",
        colorOverlayHex = 0x22E0A96D,
        descriptionFa = "گرمی رویایی، افزایش درخشش پارچه ساتن و دانتل، و نرمی فوق‌العاده پوست صورت در نور تالار.",
        badgeFa = "پیش‌فرض عروس"
    ),
    VINTAGE_35MM(
        titleFa = "نگاتیو ۳۵ میلی‌متری کداک (Kodak 35mm)",
        colorOverlayHex = 0x25C99A65,
        descriptionFa = "حس و حال نوستالژیک فیلم‌های کلاسیک اروپایی با تونالیته کهربایی و کنتراست لطیف.",
        badgeFa = "ژورنالی فرمالیته"
    ),
    ROYAL_EMERALD(
        titleFa = "زمردی اشرافی (Royal Emerald)",
        colorOverlayHex = 0x1A1B4D3E,
        descriptionFa = "سایه‌های عمیق با جلوه سنگ‌های قیمتی و کریستال‌های لوستر تالار؛ مناسب ماشین عروس و شب.",
        badgeFa = "محیط تالار و خودرو"
    ),
    PURE_VELVET(
        titleFa = "مخملی سیاه و سفید دراماتیک (Velvet B&W)",
        colorOverlayHex = 0x242B2B2B,
        descriptionFa = "سیاه‌وسفید های‌کنتراست با درخشش سفید تور و سنگ‌دوزی لباس‌ها، مانند تیزرهای برتر هالیوود.",
        badgeFa = "هنری و خاص"
    )
}

data class CinematicDirectorScene(
    val sceneNumber: Int,
    val titleFa: String,
    val subtitleFa: String,
    val directorCueFa: String,
    val startSec: Int,
    val endSec: Int,
    val poseShape: GhostPoseShapeType,
    val motionTipFa: String
)

object CinematicTimelineRegistry {
    val salonScenes = listOf(
        CinematicDirectorScene(
            sceneNumber = 1,
            titleFa = "پلان ۱: نگاه در آینه و لبخند اولیه",
            subtitleFa = "ثانیه ۰ تا ۴ • زاویه سه‌رخ به آینه",
            directorCueFa = "آرام در آینه نگاه کنید، با دو انگشت گوشه تور را بگیرید و لبخند ملایم بزنید.",
            startSec = 0,
            endSec = 4,
            poseShape = GhostPoseShapeType.BRIDE_MIRROR_VEIL,
            motionTipFa = "سر را خیلی آهسته ۵ درجه به چپ متمایل کنید تا انعکاس نور سالن روی شینیون بیفتد."
        ),
        CinematicDirectorScene(
            sceneNumber = 2,
            titleFa = "پلان ۲: رقص نور روی اتو و شینیون",
            subtitleFa = "ثانیه ۴ تا ۸ • زاویه پشت سر و موها",
            directorCueFa = "سر را به آرامی بچرخانید؛ اجازه دهید دست میکاپ‌آرتیست رژ یا برس را در کادر تنظیم کند.",
            startSec = 4,
            endSec = 8,
            poseShape = GhostPoseShapeType.BRIDE_MIRROR_VEIL,
            motionTipFa = "چشم‌ها را یک لحظه ببندید و آرام باز کنید تا مژه‌ها و کانتورینگ چشم در فوکوس بنشیند."
        ),
        CinematicDirectorScene(
            sceneNumber = 3,
            titleFa = "پلان ۳: جلوه جواهرات و تاج سر",
            subtitleFa = "ثانیه ۸ تا ۱۲ • کلوزآپ نیم‌رخ",
            directorCueFa = "دست چپ را نزدیک گونه و گوشواره ببرید، چانه کمی رو به بالا و نگاه محو به آینه.",
            startSec = 8,
            endSec = 12,
            poseShape = GhostPoseShapeType.BRIDE_MIRROR_VEIL,
            motionTipFa = "تنفس آرام شکمی؛ شانه‌ها پایین و بدون انقباض باشد."
        ),
        CinematicDirectorScene(
            sceneNumber = 4,
            titleFa = "پلان ۴: فینال آماده‌شدن و نگاه به لنز",
            subtitleFa = "ثانیه ۱۲ تا ۱۵ • نمای مستقیم رو به دوربین",
            directorCueFa = "چرخش مستقیم رو به لنز، یک لبخند شاداب با اعتماد‌به‌نفس کامل و مکث برای فریم فینال!",
            startSec = 12,
            endSec = 15,
            poseShape = GhostPoseShapeType.BRIDE_MIRROR_VEIL,
            motionTipFa = "سه ثانیه بدون حرکت لبخند را حفظ کنید؛ این فریم کاور ریلز اینستاگرام خواهد شد."
        )
    )

    val maisonScenes = listOf(
        CinematicDirectorScene(
            sceneNumber = 1,
            titleFa = "پلان ۱: نمایش بالاتنه و دانتل سینه",
            subtitleFa = "ثانیه ۰ تا ۴ • نمای مدیوم ایستاده",
            directorCueFa = "ایستادن استوار، دو دست روی کمر یا دسته‌گل فرضی، نمایش گن‌دوزی و سنگ‌های بالاتنه.",
            startSec = 0,
            endSec = 4,
            poseShape = GhostPoseShapeType.DRESS_360_SPIN,
            motionTipFa = "قفسه سینه بالا، شکم داخل و قامت کاملاً راست."
        ),
        CinematicDirectorScene(
            sceneNumber = 2,
            titleFa = "پلان ۲: شروع چرخش آرام ۱۸۰ درجه",
            subtitleFa = "ثانیه ۴ تا ۸ • چرخش با دامن کلوش",
            directorCueFa = "اکنون به آرامی روی پنجه پا نیم‌دور بچرخید تا ژپون و ریزش پارچه دامن در حرکت دیده شود.",
            startSec = 4,
            endSec = 8,
            poseShape = GhostPoseShapeType.DRESS_360_SPIN,
            motionTipFa = "با ملایمت گوشه دامن را رها کنید تا موج پارچه ثبت شود."
        ),
        CinematicDirectorScene(
            sceneNumber = 3,
            titleFa = "پلان ۳: نمایش دنباله بلند پشت لباس",
            subtitleFa = "ثانیه ۸ تا ۱۲ • نمای پشت با نگاه از روی شانه",
            directorCueFa = "پشت به لنز بمانید، سر را آرام از روی شانه راست برگردانید و به دوربین نگاه کنید.",
            startSec = 8,
            endSec = 12,
            poseShape = GhostPoseShapeType.DRESS_360_SPIN,
            motionTipFa = "دنباله لباس روی زمین پهن باشد؛ امتداد تور روی دامن به وضوح دیده می‌شود."
        ),
        CinematicDirectorScene(
            sceneNumber = 4,
            titleFa = "پلان ۴: چرخش کامل و استقرار فینال",
            subtitleFa = "ثانیه ۱۲ تا ۱۵ • نمای تمام‌قد ژورنالی",
            directorCueFa = "چرخش پایانی رو به دوربین، قرار دادن دست راست روی کمر و مکث در قاب شبح طلایی.",
            startSec = 12,
            endSec = 15,
            poseShape = GhostPoseShapeType.DRESS_360_SPIN,
            motionTipFa = "ژست سوپراستار مزون؛ تایید نهایی تن‌خور لباس برای قرارداد."
        )
    )

    val carKeyScenes = listOf(
        CinematicDirectorScene(
            sceneNumber = 1,
            titleFa = "پلان ۱: کلوزآپ سوییچ و حلقه ازدواج",
            subtitleFa = "ثانیه ۰ تا ۴ • فوکوس روی دست‌ها",
            directorCueFa = "سوییچ خودروی لوکس را بین دو انگشت بگیرید، حلقه ازدواج روبه‌روی لنز بدرخشد.",
            startSec = 0,
            endSec = 4,
            poseShape = GhostPoseShapeType.CAR_KEYS_RING,
            motionTipFa = "دست را ثابت نگه دارید تا رفلکس نور روی نشان خودرو و نگین حلقه بیفتد."
        ),
        CinematicDirectorScene(
            sceneNumber = 2,
            titleFa = "پلان ۲: پیوند دست‌های عروس و داماد",
            subtitleFa = "ثانیه ۴ تا ۸ • دست در دست با سوییچ",
            directorCueFa = "عروس دست خود را روی دست داماد بگذارد، همزمان سوییچ و دسته‌گل مینیاتوری در قاب.",
            startSec = 4,
            endSec = 8,
            poseShape = GhostPoseShapeType.CAR_KEYS_RING,
            motionTipFa = "نوازش ملایم انگشتان و انتقال انرژی مثبت پیوند مشترک."
        ),
        CinematicDirectorScene(
            sceneNumber = 3,
            titleFa = "پلان ۳: درِ خودرو و گل‌آرایی کاپوت",
            subtitleFa = "ثانیه ۸ تا ۱۲ • نمای نیمه‌باز کنار خودرو",
            directorCueFa = "داماد درِ خودرو را باز نگه داشته و عروس در آستانه ورود با لبخند نگاه می‌کند.",
            startSec = 8,
            endSec = 12,
            poseShape = GhostPoseShapeType.CAR_KEYS_RING,
            motionTipFa = "تور عروس را طوری هدایت کنید که روی دستگیره خودرو گیر نکند."
        ),
        CinematicDirectorScene(
            sceneNumber = 4,
            titleFa = "پلان ۴: فینال حرکت خودرو به سمت تالار",
            subtitleFa = "ثانیه ۱۲ تا ۱۵ • حرکت به سوی رویاها",
            directorCueFa = "نگاه عاشقانه دونفره از پشت شیشه ماشین گل‌آرایی‌شده به لنز دوربین؛ لبخند فینال!",
            startSec = 12,
            endSec = 15,
            poseShape = GhostPoseShapeType.CAR_KEYS_RING,
            motionTipFa = "دست تکان دادن ملایم به نشانه آغاز سفر زندگی مشترک."
        )
    )

    val hallScenes = listOf(
        CinematicDirectorScene(
            sceneNumber = 1,
            titleFa = "پلان ۱: ایستادن در ورودی تشریفات تالار",
            subtitleFa = "ثانیه ۰ تا ۴ • قامت مقتدرانه جلوی در ورودی",
            directorCueFa = "دست عروس در حلقه بازوی داماد، نگاه‌ها رو به جلو، آماده برای آغاز قدم‌زدن فرش قرمز.",
            startSec = 0,
            endSec = 4,
            poseShape = GhostPoseShapeType.GRAND_ENTRY_ROYAL,
            motionTipFa = "نفس عمیق؛ قدم اول را هماهنگ با ضرب‌آهنگ موسیقی بردارید."
        ),
        CinematicDirectorScene(
            sceneNumber = 2,
            titleFa = "پلان ۲: گام‌های آرام روی فرش قرمز و مه سرد",
            subtitleFa = "ثانیه ۴ تا ۸ • حرکت آهسته اسلوموشن",
            directorCueFa = "با طمأنینه قدم بردارید، نگاه‌ها ۳ متر جلوتر، لبخند پر از آرامش به مهمانان خیالی.",
            startSec = 4,
            endSec = 8,
            poseShape = GhostPoseShapeType.GRAND_ENTRY_ROYAL,
            motionTipFa = "سرعت قدم‌ها یک‌سوم حالت معمولی باشد تا جلوه سینمایی اسلوموشن بی‌نقص شود."
        ),
        CinematicDirectorScene(
            sceneNumber = 3,
            titleFa = "پلان ۳: ورود به پیست رقص و چرخش دونفره",
            subtitleFa = "ثانیه ۸ تا ۱۲ • قرارگیری در مرکز سن رقص",
            directorCueFa = "دست داماد روی گودی کمر عروس، دست دیگر بالا، چرخش آرام دونفره زیر نور متمرکز لوستر.",
            startSec = 8,
            endSec = 12,
            poseShape = GhostPoseShapeType.GRAND_ENTRY_ROYAL,
            motionTipFa = "فاصله نگاه‌ها حفظ شود؛ زاویه دسته‌گل موازی ناف باشد نه روی سینه."
        ),
        CinematicDirectorScene(
            sceneNumber = 4,
            titleFa = "پلان ۴: بوسه بر پیشانی و سلام پایانی",
            subtitleFa = "ثانیه ۱۲ تا ۱۵ • فینال احساسی ورود",
            directorCueFa = "داماد پیشانی عروس را می‌بوسد، عروس پلک‌ها را با لبخند می‌بندد؛ تشویق و فریم طلایی!",
            startSec = 12,
            endSec = 15,
            poseShape = GhostPoseShapeType.GRAND_ENTRY_ROYAL,
            motionTipFa = "مکث کامل دونفره برای پایان فیلمبرداری کلیپ ورودی."
        )
    )

    fun getScenesForScenario(scenario: CinematicScenarioType): List<CinematicDirectorScene> {
        return when (scenario) {
            CinematicScenarioType.SALON_AND_VEIL -> salonScenes
            CinematicScenarioType.MAISON_360_SPIN -> maisonScenes
            CinematicScenarioType.LUXURY_CAR_KEYS -> carKeyScenes
            CinematicScenarioType.GRAND_ENTRY_HALL -> hallScenes
        }
    }
}

data class CinematicDirectorConfig(
    val isEnabled: Boolean = false,
    val selectedScenario: CinematicScenarioType = CinematicScenarioType.SALON_AND_VEIL,
    val currentSceneIndex: Int = 0,
    val autoAdvanceWithTimer: Boolean = true,
    val isSlowMotionSimulated: Boolean = true,
    val selectedLut: CinematicLutFilter = CinematicLutFilter.CHAMPAGNE_GOLD,
    val showGhostSilhouette: Boolean = true,
    val ghostAlpha: Float = 0.45f,
    val showTripleBrandingWatermark: Boolean = true,
    val showInstagramReelFrame: Boolean = true,
    val partnerMaisonName: String = "مزون رویال پرنسس پالاس",
    val partnerSalonName: String = "سالن زیبایی شاین VIP",
    val partnerHallName: String = "عمارت و تالار دیپلمات",
    val referralDiscountCode: String = "FACEGUARD-REEL-20",
    val countdownToNextCue: Int = 4
)
