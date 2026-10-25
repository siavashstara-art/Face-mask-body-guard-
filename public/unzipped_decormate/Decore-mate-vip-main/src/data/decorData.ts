export type LanguageCode = 'fa' | 'ku' | 'hy' | 'en' | 'ar' | 'tr' | 'ru';
export type CurrencyCode = 'IRT' | 'USD' | 'IQD' | 'AMD' | 'AED' | 'TRY' | 'RUB';
export type LocalizedString = {
  fa: string;
  ku: string;
  en: string;
  ar: string;
  tr: string;
  ru: string;
  hy?: string;
};
export type LocalizedStringArray = {
  fa: string[];
  ku: string[];
  en: string[];
  ar: string[];
  tr: string[];
  ru: string[];
  hy?: string[];
};

export interface LanguageMeta {
  code: LanguageCode;
  label: string;
  flag: string;
  dir: 'rtl' | 'ltr';
  speechLang: string;
}

export const LANGUAGES: LanguageMeta[] = [
  { code: 'fa', label: 'FA فارسی', flag: '🇮🇷', dir: 'rtl', speechLang: 'fa-IR' },
  { code: 'ku', label: 'KU کوردی', flag: '☀️', dir: 'rtl', speechLang: 'fa-IR' },
  { code: 'hy', label: 'HY Հայերեն', flag: '🇦🇲', dir: 'ltr', speechLang: 'hy-AM' },
  { code: 'ar', label: 'AR العربية', flag: '🇦🇪', dir: 'rtl', speechLang: 'ar-AE' },
  { code: 'en', label: 'EN English', flag: '🌍', dir: 'ltr', speechLang: 'en-US' },
  { code: 'tr', label: 'TR Türkçe', flag: '🇹🇷', dir: 'ltr', speechLang: 'tr-TR' },
  { code: 'ru', label: 'RU Русский', flag: '🇷🇺', dir: 'ltr', speechLang: 'ru-RU' },
];

export interface SuggestedBrandName {
  id: string;
  persianName: string;
  englishName: string;
  kurdishName: string;
  armenianName: string;
  fullDisplay: string;
  meaning: string;
}

export const SUGGESTED_APP_NAMES: SuggestedBrandName[] = [
  {
    id: 'decormate',
    persianName: 'دکورمِیت VIP (همیار هوشمند دکوراسیون)',
    englishName: 'DecorMate VIP',
    kurdishName: 'دیکۆرمەیت VIP',
    armenianName: 'ԴեկորՄեյթ VIP (DecorMate)',
    fullDisplay: 'DecorMate VIP | دکورمِیت',
    meaning: 'نام بین‌المللی، مدرن و به‌یادماندنی (ترکیب Decor + Mate به معنای همکار و مشاور هوشمند دکوراسیون)',
  },
  {
    id: 'zarrinchoub',
    persianName: 'زرین‌چوب سلطنتی',
    englishName: 'ZarrinWood Royal',
    kurdishName: 'زێڕین دار (Zêrîn Dar)',
    armenianName: 'ԶարինՎուդ Ռոյալ (Ոսկե Փայտ)',
    fullDisplay: 'ZarrinWood Royal | زرین‌چوب',
    meaning: 'اصیل فارسی و هماهنگ با تم «چوب گردو و طلای ۲۴ عیار»؛ عالی برای نمایشگاه‌های کابینت کلاسیک و نئوکلاسیک',
  },
  {
    id: 'choubineh',
    persianName: 'چوبینه پلاس (معمار چوب و کابینت)',
    englishName: 'Choubineh Architect Pro',
    kurdishName: 'چۆبینە پڵەس',
    armenianName: 'Չուբինեհ Պրո (Ճարտարապետ)',
    fullDisplay: 'Choubineh Pro | چوبینه پلاس',
    meaning: 'نام اصیل ایرانی با حس مهندسی، نجاری مدرن و بازسازی لوکس ساختمان',
  },
  {
    id: 'zagroswood',
    persianName: 'زاگرس دکور (ویژه ایران، اقلیم کردستان و ارمنستان)',
    englishName: 'Zagros & Ararat Decor VIP',
    kurdishName: 'زاگرۆس دیکۆر (Zagros Decor)',
    armenianName: 'Արարատ և Զագրոս Դեկոր VIP',
    fullDisplay: 'Zagros Decor VIP | زاگرس دکور',
    meaning: 'بهترین انتخاب برای بازار ایران، سنندج، اربیل، سلیمانیه و صادرات به ایروان ارمنستان',
  },
  {
    id: 'kakhdecor',
    persianName: 'کاخ‌دکور (عمارت چوب و سنگ)',
    englishName: 'PalaceDecor Luxury Studio',
    kurdishName: 'کۆشک دیکۆر (Koşk Decor)',
    armenianName: 'Պալاس Դեկոր Լյուքս Ստուդիա',
    fullDisplay: 'PalaceDecor VIP | کاخ‌دکور',
    meaning: 'مناسب کلینیک‌های ساختمانی لوکس، پنت‌هاوس‌سازان و مجریان کابینت انزو و تمام چوب',
  },
];

export interface CurrencyMeta {
  code: CurrencyCode;
  label: string;
  symbol: string;
  rateFromToman: number; // 1 Toman in target currency
}

// Base unit in data is Toman (IRT). Example: 85,000,000 IRT ≈ $1,000 USD
export const CURRENCIES: Record<CurrencyCode, CurrencyMeta> = {
  IRT: { code: 'IRT', label: 'تومان (IRT)', symbol: 'تومان', rateFromToman: 1 },
  USD: { code: 'USD', label: 'USD $', symbol: '$', rateFromToman: 1 / 85000 },
  IQD: { code: 'IQD', label: 'IQD دینار', symbol: 'د.ع', rateFromToman: 1310 / 85000 },
  AMD: { code: 'AMD', label: 'AMD ֏ درام', symbol: '֏', rateFromToman: 388 / 85000 },
  AED: { code: 'AED', label: 'AED درهم', symbol: 'AED', rateFromToman: 3.67 / 85000 },
  TRY: { code: 'TRY', label: 'TRY لیر', symbol: '₺', rateFromToman: 36.5 / 85000 },
  RUB: { code: 'RUB', label: 'RUB روبل', symbol: '₽', rateFromToman: 92.0 / 85000 },
};

export function convertFromToman(amountToman: number, currency: CurrencyCode): number {
  const rate = CURRENCIES[currency].rateFromToman;
  const raw = amountToman * rate;
  if (currency === 'IRT' || currency === 'IQD' || currency === 'AMD') return Math.round(raw);
  return Math.round(raw * 10) / 10;
}

export function formatPrice(amountToman: number, currency: CurrencyCode, lang: LanguageCode = 'fa'): string {
  const converted = convertFromToman(amountToman, currency);
  const locale =
    lang === 'fa' || lang === 'ku'
      ? 'fa-IR'
      : lang === 'hy'
      ? 'hy-AM'
      : lang === 'ar'
      ? 'ar-AE'
      : lang === 'tr'
      ? 'tr-TR'
      : lang === 'ru'
      ? 'ru-RU'
      : 'en-US';

  const formattedNumber = new Intl.NumberFormat(locale, {
    maximumFractionDigits: 0,
  }).format(Math.round(converted));

  if (currency === 'IRT') {
    return `${formattedNumber} ${lang === 'fa' || lang === 'ku' || lang === 'ar' ? 'تومان' : 'IRT'}`;
  }
  if (currency === 'IQD') {
    return `${formattedNumber} ${lang === 'fa' || lang === 'ku' || lang === 'ar' ? 'دینار' : 'IQD'}`;
  }
  if (currency === 'AMD') {
    return `${formattedNumber} ֏`;
  }
  if (currency === 'USD') {
    return `$${formattedNumber}`;
  }
  if (currency === 'AED') {
    return `${formattedNumber} AED`;
  }
  if (currency === 'TRY') {
    return `₺${formattedNumber}`;
  }
  return `${formattedNumber} ₽`;
}

export interface ProjectTypeOption {
  id: string;
  unionBaseRatio: number;
  unionFormulaNote: LocalizedString;
  names: LocalizedString;
  unitLabel: LocalizedString;
}

export const PROJECT_TYPES: ProjectTypeOption[] = [
  {
    id: 'kitchen_cabinet',
    unionBaseRatio: 1.0,
    names: {
      fa: 'کابینت آشپزخانه (فرمول ۶۰٪ زمینی + ۴۰٪ هوایی اتحادیه)',
      ku: 'کابینەی چێشتخانە (فۆرمۆلی فەرمی ٦٠٪ زەوی + ٤٠٪ دیواری)',
      hy: 'Խոհանոցի կահույք (Պաշտոնական բանաձև 60% ստորին + 40% վերին)',
      en: 'Kitchen Cabinet (Union 60% Base + 40% Wall Formula)',
      ar: 'خزائن المطبخ (معادلة الاتحاد 60٪ سفلي + 40٪ علوي)',
      tr: 'Mutfak Dolabı (%60 Alt + %40 Üst Standart Formül)',
      ru: 'Кухонный гарнитур (Формула 60% низ + 40% верх)',
    },
    unitLabel: {
      fa: 'متر طول',
      ku: 'مەتری درێژی',
      hy: 'Գծային մետր',
      en: 'Linear Meter',
      ar: 'متر طولي',
      tr: 'Metretül',
      ru: 'Пог. метр',
    },
    unionFormulaNote: {
      fa: '۶۰٪ کابینت زمینی (عمق ۶۰) + ۴۰٪ کابینت هوایی (ارتفاع ۹۰) + صفحه شرکتی ۵ سانتی ضدآب + لولا آرام‌بند بلوم اتریش',
      ku: '٦٠٪ کابینەی خوارەوە (قووڵی ٦٠) + ٤٠٪ کابینەی سەرەوە + سەفحەی ٥ سانتی دژەئاو + یەراقی بلومی نەمسا',
      hy: '60% ստորին պահարան + 40% վերին պահարան + 5սմ ջրակայուն երեսպատում + Blum Ավստրիա ծխնիներ',
      en: '60% Base Cabinet (60cm depth) + 40% Wall Cabinet (90cm height) + 5cm Waterproof Countertop + Blum Austria Soft-Close Hardware',
      ar: '60٪ خزانة سفلية + 40٪ خزانة علوية + سطح 5 سم مقاوم للماء + مفصلات بلوم النمساوية',
      tr: '%60 Alt Dolap + %40 Üst Dolap + 5cm Su Geçirmez Tezgah + Blum Avusturya Frenli Menteşe',
      ru: '60% нижние базы + 40% верхние шкафы + столешница 5 см + фурнитура Blum Австрия',
    },
  },
  {
    id: 'wardrobe_sliding',
    unionBaseRatio: 0.72,
    names: {
      fa: 'کمد دیواری ریلی / باکسی مدرن (مترمربع چهارچوب و بدنه)',
      ku: 'کانتۆر و کۆمێدی دیواری سکەدار / بۆکسی مۆدێرن',
      hy: 'Սահող / Ներկառուցված զգեստապահարան (Ամբողջական կորպուս)',
      en: 'Sliding / Box Wardrobe (Full Frame & Internal Box)',
      ar: 'خزانة حائط سحاب / بوكس حديثة',
      tr: 'Sürgülü / Gövdeli Gardırop Sistemi',
      ru: 'Шкаф-купе / Встроенный гардероб',
    },
    unitLabel: {
      fa: 'متر مربع',
      ku: 'مەتر چوارگۆشە',
      hy: 'Քառ. մետր',
      en: 'Sq. Meter',
      ar: 'متر مربع',
      tr: 'Metrekare',
      ru: 'Кв. метр',
    },
    unionFormulaNote: {
      fa: 'بدنه کامل ملامینه پویا گرید A + ریل سنگین آلومینیوم طلایی ۸۰ کیلویی + کشوهای مخفی با ریل تاندم بلوم',
      ku: 'بدنەی تەواو مێلامینەی پلە یەک + سکەی ئەلەمنیۆمی زێڕین ٨٠ کیلۆیی + چەکمەجەی شاراوەی بلوم',
      hy: 'Ամբողջական կորպուս + 80կգ ոսկեգույն ալյումինե ռելսեր + Blum թաքնված դարակներ',
      en: 'Full Grade-A Body + 80kg Heavy-Duty Brushed Gold Aluminum Track + Blum Tandem Hidden Drawers',
      ar: 'هيكل كامل درجة أولى + سكة ألمنيوم ذهبية تتحمل 80 كجم + أدراج مخفية بلوم',
      tr: 'Tam Gövde + 80kg Kapasiteli Altın Alüminyum Ray + Blum Gizli Çekmece',
      ru: 'Полный корпус + алюминиевый профиль золото 80 кг + скрытые ящики Blum',
    },
  },
  {
    id: 'walkin_closet',
    unionBaseRatio: 0.94,
    names: {
      fa: 'کلوزت‌روم اشرافی (Walk-in Closet) + جزیره جواهرات و اکسسوری',
      ku: 'ژووری جلوبەرگی شاهانە (Walk-in Closet) + دوورگەی خشڵ و کاتژمێر',
      hy: 'Լյուքս հանդերձարան (Walk-in Closet) + Զարդերի կղզյակ',
      en: 'Luxury Walk-in Closet + Jewelry Island & LED Shelves',
      ar: 'غرفة ملابس فاخرة (Walk-in Closet) مع جزيرة إكسسوارات',
      tr: 'Lüks Giyinme Odası (Walk-in Closet) + Aksesuar Adası',
      ru: 'Гардеробная комната класса люкс + остров для аксессуаров',
    },
    unitLabel: {
      fa: 'متر مربع',
      ku: 'مەتر چوارگۆشە',
      hy: 'Քառ. մետր',
      en: 'Sq. Meter',
      ar: 'متر مربع',
      tr: 'Metrekare',
      ru: 'Кв. метр',
    },
    unionFormulaNote: {
      fa: 'شامل درب‌های شیشه‌ای فریم شامپاینی، لاین نوری سنسوردار ۳۰۰۰ کلوین، رگال آسانسوری و کشوی مخمل جواهرات',
      ku: 'دەرگای شووشەیی چوارچێوەی شامپاینی + ڕووناکی هەستیار ٣٠٠٠ کلوین + چەکمەجەی مەخمەلی خشڵ',
      hy: 'Շամպայն ապակե դռներ, 3000K սենսորային LED լուսավորություն և թավշյա զարդերի դարակներ',
      en: 'Includes Champagne Glass Doors, 3000K Sensor LED Lines, Pull-Down Hanger Lift & Velvet Jewelry Dividers',
      ar: 'يشمل أبواب زجاجية بإطار شامبانيا، إضاءة حساسة 3000 كلفن، علاقة ملابس هيدروليكية وأدراج مخملية',
      tr: 'Şampanya Cam Kapaklar, 3000K Sensörlü LED, Asansörlü Askılık ve Kadife Mücevher Çekmecesi Dahil',
      ru: 'Витрины в профиле шампань, сенсорная LED-подсветка 3000K, пантограф и бархатные органайзеры',
    },
  },
  {
    id: 'parquet_wall',
    unionBaseRatio: 0.35,
    names: {
      fa: 'پارکت لمینت AC5 و دیوارکوب ترمووود / چوب گردو (تی‌وی وال)',
      ku: 'پارکێتی لامینێت AC5 و دیوارپۆشی تێرمۆوود و داری گوێز (TV Wall)',
      hy: 'AC5 Լամինատե մանրահատակ և ընկույզի փայտից TV պատի պանել',
      en: 'AC5 Parquet Laminate & Walnut Acoustic TV Wall Panel',
      ar: 'باركيه لامينت AC5 وتكسيات جدران خشب الجوز',
      tr: 'AC5 Laminat Parke & Ceviz TV Ünitesi Duvar Paneli',
      ru: 'Ламинат паркет AC5 и стеновые панели из ореха',
    },
    unitLabel: {
      fa: 'متر مربع',
      ku: 'مەتر چوارگۆشە',
      hy: 'Քառ. մետր',
      en: 'Sq. Meter',
      ar: 'متر مربع',
      tr: 'Metrekare',
      ru: 'Кв. метр',
    },
    unionFormulaNote: {
      fa: 'شامل فوم سایلنت ۲ میل، قرنیز پی‌وی‌سی مغزدار ۹ سانتی، زیرسازی کامل و نصب تخصصی با ضمانت ۱۰ ساله',
      ku: 'فۆمی بێدەنگ ٢ میل + قەرنیزی ٩ سانتی + ژێرسازی تەواو و بەستنی پسپۆڕانە بە گەرەنتی ١٠ ساڵە',
      hy: 'Ներառում է 2մմ ձայնամեկուսիչ շերտ, 9սմ շրիշակ և 10 տարվա երաշխիքով տեղադրում',
      en: 'Includes 2mm Silent Underlayment, 9cm Core Skirting Board, Subfloor Prep & 10-Year Certified Installation',
      ar: 'يشمل فوم عازل للصوت 2 مم، نعلات 9 سم، تجهيز الأرضية وتركيب احترافي بضمان 10 سنوات',
      tr: '2mm Sessiz Şilte, 9cm Süpürgelik, Zemin Hazırlığı ve 10 Yıl Garantili Profesyonel Montaj',
      ru: 'Включает тихую подложку 2 мм, плинтус 9 см, подготовку основания и монтаж с гарантией 10 лет',
    },
  },
  {
    id: 'drywall_lighting',
    unionBaseRatio: 0.28,
    names: {
      fa: 'کناف ایران (K+)، سقف کاذب دکوراتیو و لاین نوری مگنتی',
      ku: 'سەقفی مەغریبی و کەناف (K+) و هێڵی ڕووناکی موگناتیسی',
      hy: 'Դեկորատիվ գիպսակարտոնե առաստաղ (Knauf) և մագնիսական LED լուսավորություն',
      en: 'Decorative Drywall Ceiling & Magnetic Linear Lighting',
      ar: 'أسقف جبس بورد ديكورية وإضاءة خطية مغناطيسية',
      tr: 'Dekoratif Alçıpan Tavan & Manyetik Lineer Aydınlatma',
      ru: 'Декоративный потолок и магнитное трековое освещение',
    },
    unitLabel: {
      fa: 'متر مربع',
      ku: 'مەتر چوارگۆشە',
      hy: 'Քառ. մետր',
      en: 'Sq. Meter',
      ar: 'متر مربع',
      tr: 'Metrekare',
      ru: 'Кв. метр',
    },
    unionFormulaNote: {
      fa: 'سازه‌های گالوانیزه استاندارد ۶۰، پنل RG ضدحریق، بتونه‌کاری درزگیر ماستیک و پروفیل آلومینیوم لاین نوری',
      ku: 'ئاسنی گەلڤانیزەی ستاندارد + پەنێلی دژەئاگر و شێ + ماستیک و پرۆفیلی ئەلەمنیۆمی ڕووناکی',
      hy: 'Ցինկապատ պրոֆիլներ, հրակայուն պանելներ, մաստիկա և ալյումինե LED պրոֆիլներ',
      en: 'Standard Galvanized Framing, Moisture/Fire Resistant Board, Mastic Joint Finishing & Aluminum LED Profiles',
      ar: 'هياكل مجلفنة قياسية، ألواح مقاومة للرطوبة والحريق، معجون ماستيك وبروفايل ألمنيوم للإضاءة',
      tr: 'Standart Galvaniz Profil, Suya/Yangına Dayanıklı Panel, Mastik Dolgu ve Alüminyum LED Profil',
      ru: 'Оцинкованный профиль, влагостойкий ГКЛ, мастичная шпатлевка и алюминиевые LED-профили',
    },
  },
  {
    id: 'full_renovation',
    unionBaseRatio: 1.85,
    names: {
      fa: 'بازسازی کامل VIP ساختمان (کابینت + کمد + کف + سقف + سنگ اسلب)',
      ku: 'نۆژەنکردنەوەی تەواوی بینا VIP (کابینە + کانتۆر + عەرز + سەقف + مەڕمەڕ)',
      hy: 'Բնակարանի ամբողջական VIP վերանորոգում (Խոհանոց + Հատակ + Առաստաղ + Մարմար)',
      en: 'Full Turnkey VIP Renovation (Kitchen + Floors + Ceiling + Slab)',
      ar: 'تجديد شامل VIP للمباني (مطبخ + خزائن + أرضيات + أسقف + رخام)',
      tr: 'Anahtar Teslim VIP Tam Tadilat (Mutfak + Zemin + Tavan + Mermer)',
      ru: 'Полная VIP-реновация под ключ (Кухня + Пол + Потолок + Слэбы)',
    },
    unitLabel: {
      fa: 'متر مربع زیربنا',
      ku: 'مەتر چوارگۆشەی بینا',
      hy: 'Քառ. մետր տարածք',
      en: 'Sq. Meter Area',
      ar: 'متر مربع مساحة',
      tr: 'Metrekare Alan',
      ru: 'Кв. метр площади',
    },
    unionFormulaNote: {
      fa: 'مدیریت پیمان کامل با برنامه زمان‌بندی دقیق، طراحی 3D Max رایگان، نظارت مهندس معمار و تحویل کلید در ۴۵ روز کاری',
      ku: 'بەڕێوەبردنی تەواوی پڕۆژە + دیزاینی 3D Max بەخۆڕایی + سەرپەرشتی ئەندازیاری تەلارسازی و ڕادەستکردن لە ٤٥ ڕۆژدا',
      hy: 'Ամբողջական նախագծի կառավարում, անվճար 3D Max դիզայն, ճարտարապետական վերահսկողություն և հանձնում 45 օրում',
      en: 'Full Turnkey Contract Management, Free 3D Max Rendering, Senior Architect Supervision & 45-Day Key Handover',
      ar: 'إدارة عقود متكاملة، تصميم ثلاثي الأبعاد مجاني، إشراف مهندس معماري وتسليم المفتاح خلال 45 يوماً',
      tr: 'Tam Sözleşmeli Proje Yönetimi, Ücretsiz 3D Max Tasarım, Mimar Denetimi ve 45 Günde Anahtar Teslim',
      ru: 'Полное управление проектом, бесплатный 3D Max дизайн, авторский надзор и сдача под ключ за 45 дней',
    },
  },
  {
    id: 'porcelain_slab_tile',
    unionBaseRatio: 0.42,
    names: {
      fa: 'کاشی و سرامیک اسلب پرسلانی کف و بدنه (۱۲۰×۲۴۰ و ۱۰۰×۱۰۰ + چسب پرسلانی و همتراز)',
      ku: 'کاشی و سیرامیکی سلابی پۆرسەلانی عەرز و دیوار (١٢٠×٢٤٠ + چەسپی پۆرسەلان و هاوتەراز)',
      hy: 'Ճենապակյա սալիկներ և Կերամոգրանիտ (120x240 սլեբ + սոսինձ և հարթեցման համակարգ)',
      en: 'Porcelain Slab & Large-Format Tiles (120x240 & 100x100 + Adhesive & Leveling)',
      ar: 'سيراميك وبورسلان ألواح كبيرة (120×240 + غراء بورسلان ونظام تسوية)',
      tr: 'Porselen Slab & Büyük Ebat Seramik (120x240 + Porselen Yapıştırıcı ve Derz Artısı)',
      ru: 'Крупноформатный керамогранит и слэбы (120x240 + клей и СВП)',
    },
    unitLabel: {
      fa: 'متر مربع',
      ku: 'مەتر چوارگۆشە',
      hy: 'Քառ. մետր',
      en: 'Sq. Meter',
      ar: 'متر مربع',
      tr: 'Metrekare',
      ru: 'Кв. метр',
    },
    unionFormulaNote: {
      fa: 'محاسبه دقیق متراژ با ضریب پرتی برش اسلب (۸٪)، چسب پودری پرسلانی ۲۰ کیلویی، کلیپس و گوه همتراز و بندکشی آنتی‌باکتریال',
      ku: 'ئەژمارکردنی وردی مەتر چوارگۆشە بە ڕێژەی فیراری بڕین (٨٪)، چەسپی پۆرسەلانی ٢٠ کیلۆیی و کلیپسی هاوتەراز',
      hy: 'Ճշգրիտ մակերեսի հաշվարկ՝ 8% կտրման կորստով, 20կգ սոսինձով և հակաբակտերիալ կարանյութով',
      en: 'Exact Area with 8% Slab Cut-Waste Factor, 20kg Flexible Porcelain Adhesive, Leveling Clips & Epoxy Grout',
      ar: 'حساب المساحة مع نسبة هدر القص 8٪، غراء بورسلان 20 كجم، كليبسات تسوية وحشو فواصل إيبوكسي',
      tr: '%8 Kesim Firesi Dahil Net Metraj, 20kg Esnek Porselen Yapıştırıcısı, Seviye Tespit Klipsi ve Epoksi Derz',
      ru: 'Расчет площади с учетом 8% подрезки, клея 20 кг, системы выравнивания (СВП) и эпоксидной затирки',
    },
  },
  {
    id: 'natural_stone_slab',
    unionBaseRatio: 0.78,
    names: {
      fa: 'سنگ اسلب طبیعی بوک‌مچ و فورمچ (مرمر، کوارتزیت، تراورتن لابی، جزیره و بین‌کابینتی)',
      ku: 'بەردی سلابی سروشتی بووک-مەچ (مەڕمەڕ، کوارتزیت و تراڤێرتینی لۆبی و نێوان کابینە)',
      hy: 'Բնական քարե սլեբներ Bookmatch (Մարմար, Կվարցիտ և Տրավերտին)',
      en: 'Natural Bookmatched Stone Slabs (Marble, Quartzite & Lobby Travertine)',
      ar: 'ألواح الرخام والحجر الطبيعي بوكماتش (رخام، كوارتزيت وترافرتين)',
      tr: 'Doğal Bookmatch Mermer & Kuvarsit Plaka (Lobi, Ada ve Tezgah Arası)',
      ru: 'Натуральные каменные слэбы Bookmatch (Мрамор, Кварцит и Травертин)',
    },
    unitLabel: {
      fa: 'متر مربع',
      ku: 'مەتر چوارگۆشە',
      hy: 'Քառ. մետր',
      en: 'Sq. Meter',
      ar: 'متر مربع',
      tr: 'Metrekare',
      ru: 'Кв. метр',
    },
    unionFormulaNote: {
      fa: 'سنگ اسلب ۲ سانتی ساب ایتالیایی با توری و رزین اپوکسی پشت‌سنگ، حمل با جرثقیل/خرک مخصوص و نصب اسکوپ مهندسی',
      ku: 'بەردی سلابی ٢ سانتی بە پۆلیشی ئیتاڵی و تۆڕ و ڕێزینی ئێپۆکسی، گواستنەوەی تایبەت و بەستنی ئەندازیاری',
      hy: '2սմ հաստությամբ իտալական փայլեցված սլեբ, էպոքսիդային ամրացում և մասնագիտացված տեղադրում',
      en: '2cm Italian-Polished Slab with Fiberglass Mesh & Epoxy Backing, A-Frame Transport & Anchored Installation',
      ar: 'ألواح سماكة 2 سم صقل إيطالي مع شبكة وإيبوكسي خلفي، نقل خاص وتركيب ميكانيكي آمن',
      tr: '2cm İtalyan Cilalı Fileli & Epoksili Plaka, Özel Sehpalı Nakliye ve Mekanik Ankrajlı Montaj',
      ru: 'Слэб 2 см итальянской полировки с армирующей сеткой, спецдоставка и скрытый монтаж',
    },
  },
  {
    id: 'luxury_sanitary_faucets',
    unionBaseRatio: 0.64,
    names: {
      fa: 'پکیج مصالح لوکس و کلینیک ساختمانی (شیرآلات توکار طلایی، وال‌هنگ، روشویی سنگی و دوش پیانویی)',
      ku: 'پاکێجی کەرەستەی بیناسازی لوکس (شیرئالاتی ناودیواری زێڕین، واڵ-هەنگ و دەستشۆری بەردین)',
      hy: 'Լյուքս սանտեխնիկայի փաթեթ (Ներկառուցվող ոսկեգույն ծորակներ, Wall-Hung և քարե լվացարան)',
      en: 'Luxury Sanitaryware & Built-in Faucet Package (PVD Gold Mixers, Wall-Hung WC & Stone Vanity)',
      ar: 'باقة المواد الصحية الفاخرة (خلاطات مدفونة ذهبية، مرحاض معلق ومغسلة رخامية)',
      tr: 'Lüks Ankastre Batarya, Gömme Rezervuar (Wall-Hung) ve Doğal Taş Lavabo Paketi',
      ru: 'Пакет элитной сантехники (Встроенные золотые смесители, инсталляция и каменная раковина)',
    },
    unitLabel: {
      fa: 'سرویس / پکیج کامل',
      ku: 'پاکێجی تەواو',
      hy: 'Ամբողջական փաթեթ',
      en: 'Full Suite / Unit',
      ar: 'طقم حمام كامل',
      tr: 'Tam Banyo Seti',
      ru: 'Комплект санузла',
    },
    unionFormulaNote: {
      fa: 'شامل مغزی توکار برنجی، فلاش‌تانک توکار سوئیسی/آلمانی، فرنگی وال‌هنگ، روشویی اسلب پرسلانی و آینه بک‌لایت ضدبخار',
      ku: 'ناوکی برنجی ناودیوار، فلاشتانکی سویسری، تەوالێتی واڵ-هەنگ، دەستشۆری سلاب و ئاوێنەی دژەهەڵم',
      hy: 'Ներառում է ներկառուցվող արույրե մեխանիզմ, շվեյցարական բաք, կախովի զուգարանակոնք և հակամառախուղ հայելի',
      en: 'Includes Solid Brass Concealed Box, Swiss Cistern Frame, Rimless Wall-Hung Bowl, Slab Vanity & Anti-Fog LED Mirror',
      ar: 'يشمل قلب نحاسي مدفون، خزان سويسري مخفي، كرسي معلق، مغسلة سلاب ومرآة مضادة للضباب',
      tr: 'Pirinç Ankastre Gövde, İsviçre Gömme Rezervuar, Kanalsız Asma Klozet, Slab Lavabo ve Buğu Önleyici Ayna',
      ru: 'Латунный скрытый блок, швейцарская инсталляция, безободковый унитаз, раковина из слэба и зеркало с подогревом',
    },
  },
];

export interface MaterialOption {
  id: string;
  basePricePerMeterToman: number;
  wholesaleSheetToman: number;
  warrantyYears: number;
  names: LocalizedString;
  specs: LocalizedString;
}

export const MATERIALS: MaterialOption[] = [
  {
    id: 'highgloss_agt',
    basePricePerMeterToman: 9800000,
    wholesaleSheetToman: 4250000,
    warrantyYears: 5,
    names: {
      fa: 'هایگلاس ترک AGT اورجینال (براق آینه‌ای / سوپرمات ضدلک)',
      ku: 'هایگڵاسی تورکی AGT ئۆرجیناڵ (بریقەدار / سووپەرماتی دژەپەڵە)',
      hy: 'Օրիգինալ թուրքական AGT High-Gloss և Սուպեր-Մատ (Հակամատնահետք)',
      en: 'Original Turkish AGT High-Gloss & Soft-Touch Super Matte',
      ar: 'هاي غلوس تركي AGT أصلي (لامع مرآة / سوبر مات مضاد للبصمات)',
      tr: 'Orijinal AGT High-Gloss & Soft-Touch Süper Mat Panel',
      ru: 'Оригинальный турецкий AGT High-Gloss и Супермат',
    },
    specs: {
      fa: 'مغزی MDF دانسیته بالا، نوار PVC همرنگ با چسب گرانول هنکل آلمان، مقاومت بالا در برابر خط‌وخش',
      ku: 'ناوکی MDF چڕی بەرز، نەواری PVC هاوڕەنگ بە چەسپی هێنکڵی ئەڵمانی، بەرگری بەرز لە دژی شوخت',
      hy: 'Բարձր խտության MDF, գերմանական Henkel PUR եզրային ժապավեն, քերծվածքներից պաշտպանված',
      en: 'High-Density MDF Core, Henkel German PUR Edge Banding, Anti-Fingerprint & Scratch Resistant',
      ar: 'قلب MDF عالي الكثافة، شريط حواف بغراء هينكل الألماني، مقاوم للخدش والبصمات',
      tr: 'Yüksek Yoğunluklu MDF Gövde, Henkel Alman PUR Kenar Bandı, Çizilmeye ve Parmak İzine Dayanıklı',
      ru: 'МДФ высокой плотности, немецкая кромка PUR Henkel, защита от царапин и отпечатков',
    },
  },
  {
    id: 'neoclassic_poly',
    basePricePerMeterToman: 14500000,
    wholesaleSheetToman: 6400000,
    warrantyYears: 8,
    names: {
      fa: 'نئوکلاسیک اشرافی (ابزار ظریف CNC + رنگ پلی‌اورتان ترک / روکش کره‌ای)',
      ku: 'نیۆکلاسیکی شاهانە (نەخشەی ناسکی CNC + بۆیاخی پۆلیئۆریتان / ڕووکەشی کۆری)',
      hy: 'Թագավորական Նեոկլասիկ (Նուրբ CNC փորագրություն + պոլիուրեթանային ներկ)',
      en: 'Royal Neoclassical (Precision CNC Frame + Turkish Polyurethane)',
      ar: 'نيوكلاسيك ملكي (حفر CNC دقيق + طلاء بولي يوريثان تركي)',
      tr: 'Asil Neoklasik (Hassas CNC Çerçeve + İpek Mat Poliüretan Boya)',
      ru: 'Королевская Неоклассика (ЧПУ фрезеровка + турецкий полиуретан)',
    },
    specs: {
      fa: 'پرفروش‌ترین ترند سال ۱۴۰۵، ستون‌های سرستون منبت ظریف، رنگ پلی‌اورتان ۲ جزئی ضدآب و ضدزردی',
      ku: 'پڕفرۆشترین مۆدێلی ئەمساڵ، بۆیاخی پۆلیئۆریتانی دژەئاو و دژەزەردبوون بە گەرەنتی ٨ ساڵە',
      hy: 'Տարվա ամենավաճառվող թրենդը, երկբաղադրիչ ջրակայուն պոլիուրեթանային ծածկույթ՝ 8 տարի երաշխիքով',
      en: 'Top Architectural Trend, Slim Shaker Profile, Two-Component Waterproof Non-Yellowing Polyurethane Finish',
      ar: 'الأكثر طلباً هذا العام، إطار شيكر أنيق، طلاء بولي يوريثان مقاوم للماء والاصفرار',
      tr: 'Yılın En Popüler Trendi, İnce Shaker Çerçeve, Sararmayan Çift Bileşenli Su Geçirmez Poliüretan',
      ru: 'Главный тренд года, изящная рамка Шейкер, двухкомпонентная водостойкая эмаль без пожелтения',
    },
  },
  {
    id: 'membrane_vacuum',
    basePricePerMeterToman: 11900000,
    wholesaleSheetToman: 5100000,
    warrantyYears: 6,
    names: {
      fa: 'ممبران کلاسیک سلطنتی (روکش ۱۶ میل کره‌ای + چسب دو جزئی هنکل)',
      ku: 'مێمبرانی کلاسیکی شاهانە (ڕووکەشی ١٦ میلی کۆری + چەسپی هێنکڵی ئەڵمانی)',
      hy: 'Դասական Թագավորական Մեմբրան (Կորեական վակուումային ծածկույթ + Henkel սոսինձ)',
      en: 'Classic Royal Membrane (Korean Vacuum Foil + Henkel Adhesive)',
      ar: 'ممبران كلاسيك ملكي (روكش كوري 16 مم + غراء هينكل ثنائي)',
      tr: 'Klasik Royal Membran (Kore Vakum Folyo + Henkel Çift Bileşenli Tutkal)',
      ru: 'Классическая Королевская Мембрана (Корейская пленка + клей Henkel)',
    },
    specs: {
      fa: 'تاج و زیرچراغ و پاخور منبت‌کاری کلاسیک، پرس وکیوم حرارتی استاندارد، دستگیره‌های سرامیکی طلایی',
      ku: 'تاج و ستوونی هەڵکۆڵراوی کلاسیک، پرێسی ڤاکیومی گەرمی ستاندارد و دەسکی سیرامیکی زێڕین',
      hy: 'Դասական փորագրված քիվեր, բարձր ճնշման ջերմավակուումային մամլում, ոսկեգույն կերամիկական բռնակներ',
      en: 'Carved Classic Crown Molding & Plinth, High-Pressure Thermal Vacuum Press, Gold Ceramic Handles',
      ar: 'تاج وإضاءة سفلية منحوتة كلاسيكية، كبس حراري عالي الضغط، مقابض سيراميك ذهبية',
      tr: 'Oymalı Klasik Taç ve Işık Bandı, Yüksek Basınçlı Termal Vakum Pres, Altın Seramik Kulplar',
      ru: 'Резной карниз и цоколь, термовакуумное прессование высокого давления, золотые керамические ручки',
    },
  },
  {
    id: 'enzo_polyurethane',
    basePricePerMeterToman: 17800000,
    wholesaleSheetToman: 7900000,
    warrantyYears: 10,
    names: {
      fa: 'پلی‌اورتان انزو ایتالیایی (ورق ۲۵ میل پولیشی آینه‌ای و مات مخملی)',
      ku: 'پۆلیئۆریتانی ئێنزۆی ئیتاڵی (تەختەی ٢٥ میل پۆلیشی ئاوێنەیی و ماتی مەخمەلی)',
      hy: 'Իտալական Էնզո (Enzo) Պոլիուրեթան (25մմ CNC սալիկ, հայելային փայլ և թավշյա մատ)',
      en: 'Italian Enzo Polyurethane (25mm CNC Slab, Mirror Polish & Velvet Matte)',
      ar: 'إنزو بولي يوريثان إيطالي (ألواح 25 مم مصقولة ومات مخملي)',
      tr: 'İtalyan Enzo Poliüretan (25mm CNC Gövde, Ayna Parlak & Kadife Mat)',
      ru: 'Итальянский Полиуретан Enzo (плита 25 мм, зеркальный глянец и вельвет)',
    },
    specs: {
      fa: 'ضخامت درب ۲۵ میلی‌متر، ۴ لایه آستر و رنگ پلی‌اورتان ایتالیایی، بدون هیچ‌گونه درز در لبه‌ها (Seamless)',
      ku: 'ئەستووری دەرگا ٢٥ میلیمەتر، ٤ چین بۆیاخی ئیتاڵی، بەتەواوی بێ درز و دژەئاو لە لێوارەکاندا',
      hy: '25մմ դռան հաստություն, 4 շերտ իտալական պոլիուրեթանային ներկ, 100% անկար և խոնավադիմացկուն եզրեր',
      en: '25mm Door Thickness, 4-Coat Italian Polyurethane Paint, 100% Seamless Moisture-Proof Edges',
      ar: 'سماكة الباب 25 مم، 4 طبقات طلاء إيطالي، حواف بدون فواصل نهائياً مقاومة للرطوبة 100٪',
      tr: '25mm Kapak Kalınlığı, 4 Kat İtalyan Poliüretan Boya, %100 Eksiz Neme Dayanıklı Kenarlar',
      ru: 'Толщина фасада 25 мм, 4 слоя итальянской эмали, 100% бесшовные влагостойкие торцы',
    },
  },
  {
    id: 'solid_walnut_oak',
    basePricePerMeterToman: 23500000,
    wholesaleSheetToman: 10800000,
    warrantyYears: 15,
    names: {
      fa: 'تمام چوب طبیعی گردو آمریکایی و بلوط گرم + طلای ۲۴ عیار (VIP Custom)',
      ku: 'تەواو داری سروشتی گوێزی ئەمریکی و بەڕوو + زێڕی ٢٤ عەیار (VIP Custom)',
      hy: 'Բնական ամերիկյան ընկույզի և կաղնու փայտ + 24K ոսկեգույն դետալներ (VIP Custom)',
      en: 'Solid American Walnut & Warm Oak Wood + 24K Gold Brass Inlay',
      ar: 'خشب الجوز الأمريكي الطبيعي والبلوط الدافئ + تطعيم ذهب عيار 24',
      tr: 'Masif Amerikan Ceviz & Sıcak Meşe Ağacı + 24 Ayar Altın Pirinç Detay',
      ru: 'Массив американского ореха и теплого дуба + инкрустация латунью 24К',
    },
    specs: {
      fa: 'کلاف چوب طبیعی خشک‌کن رفته، روغن گیاهی ازمو آلمان (Osmo)، یراق‌آلات برقی Servo-Drive بلوم اتریش',
      ku: 'داری سروشتی وشککراوەی کوورە، ڕۆنی گیایی Osmo ی ئەڵمانی و یەراقی کارەبایی Servo-Drive ی بلوم',
      hy: 'Չորացված բնական փայտ, գերմանական Osmo բնական յուղ, Blum Servo-Drive էլեկտրական մեխանիզմներ',
      en: 'Kiln-Dried Solid Timber Frame, German Osmo Natural Wood Oil, Blum Austria Servo-Drive Electric Hardware',
      ar: 'إطار خشب طبيعي مجفف بالأفران، زيت أوزمو الألماني الطبيعي، مفصلات كهربائية بلوم النمساوية',
      tr: 'Fırınlanmış Masif Ahşap Çerçeve, Alman Osmo Doğal Ahşap Yağı, Blum Servo-Drive Elektrikli Donanım',
      ru: 'Камерная сушка массива, немецкое масло Osmo, электрические приводы Blum Servo-Drive Австрия',
    },
  },
  {
    id: 'porcelain_calacatta_slab',
    basePricePerMeterToman: 4600000,
    wholesaleSheetToman: 2950000,
    warrantyYears: 12,
    names: {
      fa: 'سرامیک اسلب پرسلان سوپرپولیش ۱۲۰×۲۴۰ و ۱۰۰×۱۰۰ (طرح کلکته گلد، پیترا گری و مارکینا)',
      ku: 'سیرامیکی سلابی پۆرسەلانی سووپەرپۆلیش ١٢٠×٢٤٠ (نەخشی کەلکەتە گۆڵد و پێترا گرەی)',
      hy: 'Կերամոգրանիտ սլեբ 120x240 Սուպեր-Փայլ (Calacatta Gold & Pietra Grey)',
      en: 'Super-Polished 120x240 Porcelain Slab (Calacatta Gold, Pietra Grey & Marquina)',
      ar: 'بورسلان سلاب سوبر بوليش 120×240 (كالكوتا جولد وبيترا جراي)',
      tr: 'Süper Parlak 120x240 Porselen Slab (Calacatta Gold & Pietra Grey)',
      ru: 'Суперполированный керамогранит слэб 120x240 (Calacatta Gold и Pietra Grey)',
    },
    specs: {
      fa: 'جذب آب زیر ۰.۱٪ (ضدیخ‌زدگی و ضداسید)، رکتیفای لیزری بدون بند، لعاب کریستال ضدخش به همراه چسب پرسلانی الیاف‌دار',
      ku: 'مژینی ئاو ژێر ٠.١٪، بڕینی لەیزەری بێ درز، لعابی کریستاڵی دژەشوخت لەگەڵ چەسپی پۆرسەلانی',
      hy: 'Ջրի կլանում <0.1%, լազերային ռեկտիֆիկացիա, բյուրեղյա փայլ և ճկուն սոսինձ',
      en: '<0.1% Water Absorption, Laser-Rectified Zero-Joint Edges, Crystal Glaze + Fiber-Reinforced Adhesive',
      ar: 'امتصاص ماء أقل من 0.1٪، قص ليزر بدون فواصل، طبقة كريستال مضادة للخدش مع غراء مسلح بالألياف',
      tr: '<%0.1 Su Emme, Lazer Rektifiyeli Sıfır Derz Kenar, Kristal Sır + Elyaflı Yapıştırıcı',
      ru: 'Водопоглощение <0.1%, лазерная ректификация, кристальная глазурь и армированный клей',
    },
  },
  {
    id: 'bookmatch_marble_quartz',
    basePricePerMeterToman: 12800000,
    wholesaleSheetToman: 8400000,
    warrantyYears: 15,
    names: {
      fa: 'سنگ اسلب طبیعی مرمر، کوارتزیت و تراورتن بوک‌مچ / صفحه کوارتز توتم و سایلستون',
      ku: 'بەردی سلابی سروشتی مەڕمەڕ و کوارتزیتی بووک-مەچ / سەفحەی کوارتزی سایلستۆن',
      hy: 'Բնական մարմարե և կվարցիտե Bookmatch սլեբ / Silestone կվարց',
      en: 'Natural Bookmatched Marble & Quartzite Slab / Silestone Luxury Quartz',
      ar: 'رخام وكوارتزيت طبيعي بوكماتش / كوارتز سايلستون الإسباني الفاخر',
      tr: 'Doğal Bookmatch Mermer & Kuvarsit Plaka / Silestone Lüks Kuvars',
      ru: 'Натуральный мрамор и кварцит Bookmatch / Кварцевый агломерат Silestone',
    },
    specs: {
      fa: 'فرآوری رزین اپوکسی UV ایتالیایی، قابلیت عبور نور (Backlit) برای مرمر، مقاومت حرارتی و آنتی‌باکتریال ۱۰۰٪',
      ku: 'پرۆسێسی ڕێزینی ئێپۆکسی ئیتاڵی، توانای تێپەڕبوونی ڕووناکی (Backlit) و دژەبەکتریای ١٠٠٪',
      hy: 'Իտալական UV էպոքսիդային մշակում, լուսաթափանց (Backlit) մարմար և 100% հակաբակտերիալ մակերես',
      en: 'Italian UV Epoxy Treatment, Translucent Backlit Marble Option, 100% Heat & Stain Resistant',
      ar: 'معالجة إيبوكسي إيطالي، قابلية إضاءة خلفية للرخام، مقاوم للحرارة والبقع 100٪',
      tr: 'İtalyan UV Epoksi İşlem, Arkadan Aydınlatmalı (Backlit) Mermer Seçeneği, %100 Leke ve Isı Direnci',
      ru: 'Итальянская UV-эпоксидная обработка, светопрозрачный мрамор Backlit, 100% защита от пятен',
    },
  },
  {
    id: 'luxury_builtin_sanitary',
    basePricePerMeterToman: 19500000,
    wholesaleSheetToman: 14200000,
    warrantyYears: 10,
    names: {
      fa: 'پکیج VIP شیرآلات توکار طلای مات (PVD)، وال‌هنگ گبریت سوئیس و روشویی اسلب',
      ku: 'پاکێجی VIP شیرئالاتی ناودیواری زێڕینی مات (PVD)، واڵ-هەنگی گێبریتی سویسری و دەستشۆری سلاب',
      hy: 'VIP ներկառուցվող PVD ոսկեգույն ծորակներ, շվեյցարական Geberit Wall-Hung և սլեբ լվացարան',
      en: 'VIP Brushed Gold PVD Built-in Faucets, Swiss Geberit Wall-Hung & Porcelain Slab Vanity',
      ar: 'طقم VIP خلاطات مدفونة ذهب مطفي PVD، جيبريت سويسري معلق ومغسلة سلاب بورسلان',
      tr: 'VIP Fırçalanmış Altın PVD Ankastre Batarya, İsviçre Geberit Gömme Rezervuar & Slab Lavabo',
      ru: 'VIP комплект: встроенные смесители золото PVD, инсталляция Geberit Швейцария и раковина из слэба',
    },
    specs: {
      fa: 'آبکاری PVD ضداسید و ضدجرم با ۱۰ سال ضمانت تعویض بی قید و شرط، کارتریج سرامیکی سدام اسپانیا و سیفون مخفی',
      ku: 'ئاوکاری PVD دژەئەسید بە ١٠ ساڵ گەرەنتی گۆڕینی بێ مەرج و کارتریجی سیرامیکی ئیسپانی',
      hy: 'Հակաթթվային PVD ծածկույթ՝ 10 տարվա անվերապահ երաշխիքով և իսպանական Sedal քارتրիջով',
      en: 'Acid-Resistant PVD Coating with 10-Year Replacement Warranty, Spanish Sedal Cartridge & Hidden Drain',
      ar: 'طلاء PVD مقاوم للأحماض والكلس بضمان استبدال 10 سنوات، قلب سيراميك إسباني ومصرف مخفي',
      tr: '10 Yıl Birebir Değişim Garantili Asit Dirençli PVD Kaplama, İspanyol Sedal Kartuş ve Gizli Süzgeç',
      ru: 'Покрытие PVD с гарантией замены 10 лет, испанский картридж Sedal и скрытый слив',
    },
  },
];

export interface ShowcaseProduct {
  id: string;
  code: string;
  image: string;
  category: string;
  pricePerMeterToman: number;
  wholesaleColleaguePriceToman: number;
  deliveryDays: number;
  warrantyYears: number;
  title: LocalizedString;
  subtitle: LocalizedString;
  materialsUsed: LocalizedString;
}

export const SHOWCASE_PRODUCTS: ShowcaseProduct[] = [
  {
    id: 'prod-1',
    code: 'DM-VIP-101',
    image: 'https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=1000&q=80',
    category: 'neoclassic',
    pricePerMeterToman: 15800000,
    wholesaleColleaguePriceToman: 6900000,
    deliveryDays: 18,
    warrantyYears: 10,
    title: {
      fa: 'آشپزخانه سلطنتی «چوب گردو و طلای ۲۴ عیار» با جزیره اسلب مرمر',
      ku: 'چێشتخانەی شاهانەی «داری گوێز و زێڕی ٢٤ عەیار» لەگەڵ دوورگەی مەڕمەڕ',
      en: 'Royal Walnut & 24K Gold Kitchen with Calacatta Marble Island',
      ar: 'مطبخ خشب الجوز والذهب عيار 24 الملكي مع جزيرة رخام كالكوتا',
      tr: 'Kraliyet Ceviz & 24 Ayar Altın Mutfak ve Calacatta Mermer Ada',
      ru: 'Королевская кухня «Орех и Золото 24К» с мраморным островом',
    },
    subtitle: {
      fa: 'ترکیب روکش طبیعی گردو آمریکایی، کابینت هوایی پلی‌اورتان عاجی و یراق بلوم اتریش',
      ku: 'تێکەڵەی ڕووکەشی سروشتی گوێزی ئەمریکی، کابینەی سەرەوەی پۆلیئۆریتان و یەراقی بلومی نەمسا',
      en: 'American Walnut Veneer, Warm Ivory Polyurethane Upper Units & Blum Hardware',
      ar: 'قشرة الجوز الأمريكي الطبيعي، خزائن علوية بولي يوريثان عاجي ومفصلات بلوم',
      tr: 'Amerikan Ceviz Kaplama, Fildişi Poliüretan Üst Dolaplar ve Blum Donanım',
      ru: 'Шпон американского ореха, верхние фасады цвета слоновой кости и Blum',
    },
    materialsUsed: {
      fa: 'چوب گردو + اسلب کوارتز طلایی + بلوم اتریش',
      ku: 'داری گوێز + کوارتزی زێڕین + بلومی نەمسا',
      en: 'Walnut Wood + Gold Quartz Slab + Blum Austria',
      ar: 'خشب الجوز + كوارتز ذهبي + بلوم النمسا',
      tr: 'Ceviz Ağacı + Altın Kuvars + Blum Avusturya',
      ru: 'Орех + Золотой кварц + Blum Австрия',
    },
  },
  {
    id: 'prod-2',
    code: 'DM-VIP-102',
    image: 'https://images.unsplash.com/photo-1556911220-bff31c812dba?auto=format&fit=crop&w=1000&q=80',
    category: 'enzo',
    pricePerMeterToman: 17900000,
    wholesaleColleaguePriceToman: 7850000,
    deliveryDays: 21,
    warrantyYears: 10,
    title: {
      fa: 'کابینت انزو پلی‌اورتان سفید-صدفی با ویترین شیشه‌ای شامپاینی',
      ku: 'کابینەی ئێنزۆ پۆلیئۆریتانی سپی-مرواری لەگەڵ ڤیترینی شووشەیی شامپاینی',
      en: 'Enzo Pearl-White Polyurethane Kitchen with Champagne Glass Vitrines',
      ar: 'مطبخ إنزو بولي يوريثان أبيض لؤلؤي مع واجهات زجاج شامبانيا',
      tr: 'Enzo İnci Beyazı Poliüretan Mutfak & Şampanya Cam Vitrinler',
      ru: 'Кухня Enzo жемчужно-белый полиуретан с витринами шампань',
    },
    subtitle: {
      fa: 'ورق ۲۵ میل CNC بدون درز، لاین نوری سنسوردار ۳۰۰۰ کلوین و صفحه کورین سامسونگ',
      ku: 'تەختەی ٢٥ میل CNC بێ درز، ڕووناکی هەستیار و سەفحەی کۆرینی سامسۆنگ',
      en: '25mm Seamless CNC Doors, 3000K Sensor Linear LED & Samsung Corian Countertop',
      ar: 'أبواب 25 مم بدون فواصل، إضاءة خطية حساسة وسطح كوريان سامسونج',
      tr: '25mm Eksiz CNC Kapaklar, 3000K Sensörlü LED ve Samsung Corian Tezgah',
      ru: 'Бесшовные фасады 25 мм, сенсорная подсветка 3000K и столешница Corian',
    },
    materialsUsed: {
      fa: 'انزو ۲۵ میل ایتالیایی + سنگ کورین + فریم شامپاینی',
      ku: 'ئێنزۆی ٢٥ میلی ئیتاڵی + بەردی کۆرین + چوارچێوەی شامپاینی',
      en: '25mm Italian Enzo + Corian Stone + Champagne Frame',
      ar: 'إنزو 25 مم إيطالي + حجر كوريان + إطار شامبانيا',
      tr: '25mm İtalyan Enzo + Corian Taş + Şampanya Çerçeve',
      ru: 'Итальянский Enzo 25 мм + Corian + профиль шампань',
    },
  },
  {
    id: 'prod-3',
    code: 'DM-VIP-103',
    image: 'https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?auto=format&fit=crop&w=1000&q=80',
    category: 'modern',
    pricePerMeterToman: 10200000,
    wholesaleColleaguePriceToman: 4400000,
    deliveryDays: 12,
    warrantyYears: 6,
    title: {
      fa: 'کابینت مدرن دوپله تا سقف هایگلاس AGT ترک و بلوط طبیعی',
      ku: 'کابینەی مۆدێرنی دووپلە تا سەقف هایگڵاسی AGT تورکی و داری بەڕوو',
      en: 'Full-Height Stepped Modern AGT High-Gloss & Warm Oak Kitchen',
      ar: 'مطبخ مودرن متدرج للسقف هاي غلوس AGT تركي وبلوط دافئ',
      tr: 'Tavana Kadar Kademeli Modern AGT High-Gloss & Sıcak Meşe Mutfak',
      ru: 'Двухуровневая современная кухня под потолок AGT и теплый дуб',
    },
    subtitle: {
      fa: 'افزایش ۴۲٪ فضای ذخیره‌سازی با کابینت هوایی پله‌ای، دستگیره مخفی مگنتی تیپ‌آن',
      ku: 'زیادکردنی ٤٢٪ جێگای هەڵگرتن بە کابینەی سەرەوەی دووپلە و دەسکی شاراوەی موگناتیسی',
      en: '+42% Storage Capacity with Stepped Upper Cabinets & Tip-On Push-to-Open',
      ar: 'زيادة 42٪ في مساحة التخزين مع خزائن علوية متدرجة وفتح باللمس',
      tr: 'Kademeli Üst Dolaplarla +%42 Depolama Alanı & Bas-Aç Mekanizma',
      ru: '+42% места для хранения с антресолями под потолок и системой Tip-On',
    },
    materialsUsed: {
      fa: 'هایگلاس AGT ترک + فوق‌برجسته بلوط + مگنت بلوم',
      ku: 'هایگڵاسی AGT تورکی + بەڕووی سێ ڕەهەندی + موگناتیسی بلوم',
      en: 'Turkish AGT High-Gloss + Sync Oak + Blum Tip-On',
      ar: 'هاي غلوس AGT تركي + بلوط بارز + مگنت بلوم',
      tr: 'AGT High-Gloss + Senkronize Meşe + Blum Tip-On',
      ru: 'Турецкий AGT + синхро-дуб + Blum Tip-On',
    },
  },
  {
    id: 'prod-4',
    code: 'DM-VIP-104',
    image: 'https://images.unsplash.com/photo-1595526114035-0d45ed16cfbf?auto=format&fit=crop&w=1000&q=80',
    category: 'closet',
    pricePerMeterToman: 13400000,
    wholesaleColleaguePriceToman: 5900000,
    deliveryDays: 14,
    warrantyYears: 8,
    title: {
      fa: 'کلوزت‌روم اشرافی (Walk-in Closet) با جزیره ساعت و جواهرات',
      ku: 'ژووری جلوبەرگی شاهانە (Walk-in Closet) لەگەڵ دوورگەی کاتژمێر و خشڵ',
      en: 'Bespoke Walnut Walk-in Closet with Watch & Jewelry Glass Island',
      ar: 'غرفة ملابس فاخرة من خشب الجوز مع جزيرة زجاجية للساعات والمجوهرات',
      tr: 'Özel Tasarım Ceviz Giyinme Odası & Cam Saat/Mücevher Adası',
      ru: 'Эксклюзивная гардеробная из ореха с островом для часов и украшений',
    },
    subtitle: {
      fa: 'کشوهای چرم و مخمل دست‌دوز، درب‌های شیشه‌ای دودی و نورپردازی خطی هوشمند',
      ku: 'چەکمەجەی چەرم و مەخمەلی دەستچن، دەرگای شووشەی دووکەڵی و ڕووناکی هێڵی زیرەک',
      en: 'Hand-Stitched Leather & Velvet Drawers, Smoked Glass Doors & Smart LED',
      ar: 'أدراج جلد ومخمل يدوية الصنع، أبواب زجاج مدخن وإضاءة ذكية',
      tr: 'El Dikimi Deri ve Kadife Çekmeceler, Füme Cam Kapaklar ve Akıllı LED',
      ru: 'Ящики в коже и бархате, фасады из тонированного стекла и умный свет',
    },
    materialsUsed: {
      fa: 'ملامینه تکسچر گردو + شیشه سکوریت برنز + چرم طبیعی',
      ku: 'مێلامینەی نەخشی گوێز + شووشەی سیکۆریتی برۆنز + چەرمی سروشتی',
      en: 'Walnut Texture + Bronze Tempered Glass + Natural Leather',
      ar: 'ملمس خشب الجوز + زجاج سيكوريت برونزي + جلد طبيعي',
      tr: 'Ceviz Dokulu Gövde + Bronz Temperli Cam + Doğal Deri',
      ru: 'Текстура ореха + закаленное бронзовое стекло + кожа',
    },
  },
  {
    id: 'prod-5',
    code: 'DM-VIP-105',
    image: 'https://images.unsplash.com/photo-1600566753376-12c8ab7fb75b?auto=format&fit=crop&w=1000&q=80',
    category: 'neoclassic',
    pricePerMeterToman: 14900000,
    wholesaleColleaguePriceToman: 6500000,
    deliveryDays: 17,
    warrantyYears: 8,
    title: {
      fa: 'کابینت نئوکلاسیک سبز زیتونی و چوب بلوط با شیرآلات طلایی مات',
      ku: 'کابینەی نیۆکلاسیکی سەوزی زەیتوونی و داری بەڕوو بە دەسکی زێڕینی مات',
      en: 'Olive-Sage Neoclassical & Warm Oak Kitchen with Brushed Brass Hardware',
      ar: 'مطبخ نيوكلاسيك أخضر زيتوني وبلوط دافئ مع إكسسوارات ذهبية مطفية',
      tr: 'Zeytin Yeşili Neoklasik & Sıcak Meşe Mutfak, Fırçalanmış Altın Detaylar',
      ru: 'Неоклассическая кухня оливковый шалфей и дуб с матовой латунью',
    },
    subtitle: {
      fa: 'طراحی آرامش‌بخش بیوفیلیک، سوپرمارکت ریلی قدی و صفحه سنگ طبیعی اسلب',
      ku: 'دیزاینی ئارامبەخشی سروشتی، سووپەرمارکێتی سکەداری باڵابەرز و بەردی سروشتی',
      en: 'Calm Biophilic Palette, Full-Height Pull-Out Pantry & Natural Stone Slab',
      ar: 'تصميم هادئ مريح للأعصاب، خزانة مؤن سحاب كاملة وسطح حجر طبيعي',
      tr: 'Dingin Biyofilik Tasarım, Boy Kiler Mekanizması ve Doğal Taş Tezgah',
      ru: 'Спокойная биофильная палитра, колонна-карго и натуральный камень',
    },
    materialsUsed: {
      fa: 'پلی‌اورتان سبز زیتونی + بلوط گرم + دستگیره برنجی',
      ku: 'پۆلیئۆریتانی سەوزی زەیتوونی + داری بەڕوو + دەسکی برنجی',
      en: 'Olive Polyurethane + Warm Oak + Solid Brass Pulls',
      ar: 'بولي يوريثان زيتوني + بلوط دافئ + مقابض نحاسية',
      tr: 'Zeytin Poliüretan + Sıcak Meşe + Pirinç Kulplar',
      ru: 'Оливковый полиуретан + теплый дуб + латунные ручки',
    },
  },
  {
    id: 'prod-6',
    code: 'DM-VIP-106',
    image: 'https://images.unsplash.com/photo-1600210492486-724fe5c67fb0?auto=format&fit=crop&w=1000&q=80',
    category: 'wall_tv',
    pricePerMeterToman: 6800000,
    wholesaleColleaguePriceToman: 3100000,
    deliveryDays: 7,
    warrantyYears: 10,
    title: {
      fa: 'تی‌وی وال (TV Wall) اسلب مرمر و ترمووود فنلاندی با شومینه خطی',
      ku: 'دیواری تەلەفزیۆن (TV Wall) مەڕمەڕ و تێرمۆوودی فینلەندی بە شۆمینەی هێڵی',
      en: 'Bookmatched Marble & Finnish Thermowood TV Wall with Linear Fireplace',
      ar: 'جدار تلفزيون رخام بوكماتش وخشب ثيرموود فنلندي مع مدفأة خطية',
      tr: 'Bookmatch Mermer & Fin Termowood TV Ünitesi ve Lineer Şömine',
      ru: 'ТВ-зона из мрамора Bookmatch и финского термодерева с биокамином',
    },
    subtitle: {
      fa: 'اجرای بدون تخریب در ۴۸ ساعت، کانال مخفی کابل‌ها و لاین نوری بک‌لایت طلایی',
      ku: 'جێبەجێکردن بەبێ ڕووخاندن لە ٤٨ کاتژمێردا، شاردنەوەی تەواوی وایەرەکان و ڕووناکی زێڕین',
      en: 'Dust-Free 48-Hour Installation, Hidden Cable Management & Warm Gold Backlight',
      ar: 'تركيب نظيف خلال 48 ساعة، إخفاء كامل للكابلات وإضاءة خلفية ذهبية دافئة',
      tr: '48 Saatte Tozsuz Montaj, Gizli Kablo Kanalı ve Sıcak Altın Arka Aydınlatma',
      ru: 'Чистый монтаж за 48 часов, скрытый кабель-канал и теплая золотая подсветка',
    },
    materialsUsed: {
      fa: 'ترمووود مغزدار + ماربل شیت UV / اسلب پرسلان + کنسول چوبی',
      ku: 'تێرمۆوودی ئەسڵی + ماڕبڵ شیت و مەڕمەڕ + مێزی کۆنسۆڵی دار',
      en: 'Solid Thermowood + Porcelain/UV Marble Slab + Floating Console',
      ar: 'خشب ثيرموود + ألواح رخام بورسلان + كونسول عائم',
      tr: 'Masif Termowood + Porselen Mermer Plaka + Asma Konsol',
      ru: 'Термодерево + керамогранит слэб + подвесная консоль',
    },
  },
  {
    id: 'prod-7',
    code: 'DM-VIP-107',
    image: 'https://images.unsplash.com/photo-1600585152220-90363fe7e115?auto=format&fit=crop&w=1000&q=80',
    category: 'wood',
    pricePerMeterToman: 22900000,
    wholesaleColleaguePriceToman: 10400000,
    deliveryDays: 25,
    warrantyYears: 15,
    title: {
      fa: 'آشپزخانه تمام چوب گردو آمریکایی دست‌ساز (کالکشن نیومتاورسیتی)',
      ku: 'چێشتخانەی تەواو داری گوێزی ئەمریکی دەستکرد (کۆڵێکشنی نیۆمێتاڤێرسیتی)',
      en: 'Handcrafted Solid American Walnut Kitchen (NeoMetaverCity Edition)',
      ar: 'مطبخ خشب الجوز الأمريكي الطبيعي المصنوع يدوياً (إصدار نيوميتافيرسيتي)',
      tr: 'El Yapımı Masif Amerikan Ceviz Mutfak (NeoMetaverCity Koleksiyonu)',
      ru: 'Кухня ручной работы из массива американского ореха (NeoMetaverCity)',
    },
    subtitle: {
      fa: 'پوشش روغن گیاهی ضدآب Osmo آلمان، اتصال فاق و زبانه سنتی-مهندسی و سنگ گرانیت',
      ku: 'ڕووپۆشی ڕۆنی گیایی دژەئاوی Osmo ی ئەڵمانی و بەردی گرانیتی سروشتی',
      en: 'German Osmo Waterproof Oil Finish, Precision Dovetail Joinery & Granite Top',
      ar: 'طلاء زيت أوزمو الألماني المقاوم للماء، تعشيق خشبي هندسي وسطح جرانيت',
      tr: 'Alman Osmo Su Geçirmez Doğal Yağ, Hassas Kırlangıç Geçme ve Granit Tezgah',
      ru: 'Немецкое водостойкое масло Osmo, столярное соединение «ласточкин хвост»',
    },
    materialsUsed: {
      fa: 'چوب گردو ۱۰۰٪ طبیعی + یراق Servo-Drive بلوم + سنگ گرانیت',
      ku: 'داری گوێزی ١٠٠٪ سروشتی + یەراقی کارەبایی بلوم + بەردی گرانیت',
      en: '100% Solid Walnut + Blum Servo-Drive + Natural Granite',
      ar: 'خشب جوز طبيعي 100٪ + بلوم سيرفو درايف + جرانيت طبيعي',
      tr: '%100 Masif Ceviz + Blum Servo-Drive + Doğal Granit',
      ru: '100% массив ореха + Blum Servo-Drive + натуральный гранит',
    },
  },
  {
    id: 'prod-8',
    code: 'DM-VIP-108',
    image: 'https://images.unsplash.com/photo-1616486338812-3dadae4b4ace?auto=format&fit=crop&w=1000&q=80',
    category: 'wardrobe',
    pricePerMeterToman: 9100000,
    wholesaleColleaguePriceToman: 3950000,
    deliveryDays: 10,
    warrantyYears: 7,
    title: {
      fa: 'کمد دیواری ریلی آینه برنز و قاب طلایی مات با میز آرایش مخفی',
      ku: 'کانتۆری دیواری سکەدار بە ئاوێنەی برۆنز و چوارچێوەی زێڕینی مات',
      en: 'Bronze Mirror Sliding Wardrobe in Brushed Gold Frame + Hidden Vanity',
      ar: 'خزانة حائط سحاب بمرآة برونزية وإطار ذهبي مطفي مع تسريحة مخفية',
      tr: 'Bronz Aynalı & Fırçalanmış Altın Çerçeveli Sürgülü Gardırop + Gizli Makyaj Masası',
      ru: 'Шкаф-купе с бронзовым зеркалом в золотом профиле и скрытым туалетным столиком',
    },
    subtitle: {
      fa: 'مناسب اتاق‌خواب‌های مدرن، ریل آرام‌بند دوطرفه ترک و طبقه‌بندی مهندسی‌شده',
      ku: 'گونجاو بۆ ژووری نووستنی مۆدێرن، سکەی ئارامبەندی دوولایەنەی تورکی و دابەشکاری ناوەوە',
      en: 'Ideal for Compact Bedrooms, Dual Soft-Close Turkish Track & Modular Shelving',
      ar: 'مثالي لغرف النوم الحديثة، سكة تركية بإغلاق ناعم مزدوج وتقسيم داخلي ذكي',
      tr: 'Modern Yatak Odaları İçin İdeal, Çift Yönlü Frenli Ray ve Modüler İç Tasarım',
      ru: 'Идеально для спален, двусторонний доводчик и эргономичное наполнение',
    },
    materialsUsed: {
      fa: 'آینه نقره/برنز ۴ میل + فریم آلومینیوم آنادایز طلایی + MDF ایزوفام',
      ku: 'ئاوێنەی برۆنزی ٤ میل + چوارچێوەی ئەلەمنیۆمی زێڕین + MDF پلە یەک',
      en: '4mm Bronze Mirror + Gold Anodized Aluminum + Grade-A MDF',
      ar: 'مرآة برونز 4 مم + ألمنيوم مؤكسد ذهبي + MDF درجة أولى',
      tr: '4mm Bronz Ayna + Altın Eloksal Alüminyum + 1. Sınıf MDF',
      ru: 'Бронзовое зеркало 4 мм + анодированный золотой профиль + МДФ',
    },
  },
  {
    id: 'prod-9',
    code: 'DM-VIP-109',
    image: 'https://images.unsplash.com/photo-1600607687939-ce8a6c25118c?auto=format&fit=crop&w=1000&q=80',
    category: 'renovation',
    pricePerMeterToman: 18500000,
    wholesaleColleaguePriceToman: 8900000,
    deliveryDays: 35,
    warrantyYears: 10,
    title: {
      fa: 'پکیج بازسازی کامل پنت‌هاوس و ویلا (پارکت جناغی بلوط + کناف + کابینت)',
      ku: 'پاکێجی نۆژەنکردنەوەی تەواوی ڤێلا و پێنتهاوس (پارکێتی بەڕوو + کەناف + کابینە)',
      en: 'Penthouse & Villa Turnkey Renovation (Herringbone Oak + Drywall + Kitchen)',
      ar: 'باقة تجديد البنتهاوس والفلل الكاملة (باركيه متعرج + جبس + مطبخ)',
      tr: 'Penthouse & Villa Tam Tadilat Paketi (Balıksırtı Meşe Parke + Tavan + Mutfak)',
      ru: 'Полная реновация пентхауса и виллы (Английская елка дуб + Потолки + Кухня)',
    },
    subtitle: {
      fa: 'هماهنگی ۱۰۰٪ رنگ پارکت جناغی با جزیره آشپزخانه و درب‌های فریم‌لس قدی',
      ku: 'هاوئاهەنگی ١٠٠٪ ڕەنگی پارکێت لەگەڵ دوورگەی چێشتخانە و دەرگای بێ چوارچێوە',
      en: '100% Color-Matched Herringbone Parquet, Kitchen Island & Full-Height Frameless Doors',
      ar: 'تناسق ألوان 100٪ بين الباركيه المتعرج وجزيرة المطبخ والأبواب المخفية الإطار',
      tr: 'Balıksırtı Parke, Mutfak Adası ve Gizli Kasalı Kapılarda %100 Renk Uyumu',
      ru: '100% попадание в тон паркета елочкой, кухонного острова и дверей скрытого монтажа',
    },
    materialsUsed: {
      fa: 'پارکت جناغی مهندسی‌شده + درب فریم‌لس + کابینت نئوکلاسیک',
      ku: 'پارکێتی ئەندازیاری داری بەڕوو + دەرگای شاراوە + کابینەی نیۆکلاسیک',
      en: 'Engineered Herringbone Oak + Concealed Doors + Neoclassical Kitchen',
      ar: 'باركيه بلوط هندسي + أبواب مخفية + مطبخ نيوكلاسيك',
      tr: 'Lamine Balıksırtı Meşe + Gizli Kasa Kapı + Neoklasik Mutfak',
      ru: 'Инженерная доска дуб + двери Invisible + неоклассическая кухня',
    },
  },
  {
    id: 'prod-10',
    code: 'DM-VIP-110',
    image: 'https://images.unsplash.com/photo-1600566753190-17f0baa2a6c3?auto=format&fit=crop&w=1000&q=80',
    category: 'membrane',
    pricePerMeterToman: 12300000,
    wholesaleColleaguePriceToman: 5300000,
    deliveryDays: 15,
    warrantyYears: 6,
    title: {
      fa: 'کابینت ممبران سلطنتی سفید-طلایی با سرستون منبت و هود مخفی',
      ku: 'کابینەی مێمبرانی شاهانەی سپی-زێڕین بە ستوونی هەڵکۆڵراو و هوودی شاراوە',
      en: 'Royal White & Gold Classic Membrane Kitchen with Carved Columns',
      ar: 'مطبخ ممبران ملكي أبيض وذهبي مع أعمدة منحوتة وشفاط مخفي',
      tr: 'Beyaz & Altın Varaklı Kraliyet Klasik Membran Mutfak',
      ru: 'Классическая кухня Мембрана Бело-Золотая с резными пилястрами',
    },
    subtitle: {
      fa: 'پتینه طلایی دست‌ساز روی گل‌ها و سرستون‌ها، صفحه شرکتی ۵ سانتی طرح سنگ مرمر',
      ku: 'پەتینەی زێڕینی دەستکرد لەسەر گوڵ و ستوونەکان، سەفحەی ٥ سانتی نەخشی مەڕمەڕ',
      en: 'Hand-Brushed Gold Patina on Carvings, 5cm Marble-Look Waterproof Countertop',
      ar: 'تعتيق ذهبي يدوي على النقوش والأعمدة، سطح 5 سم بمظهر الرخام مقاوم للماء',
      tr: 'Oymalarda El İşçiliği Altın Patina, 5cm Mermer Desenli Su Geçirmez Tezgah',
      ru: 'Ручная золотая патина на резном декоре, влагостойкая столешница 5 см под мрамор',
    },
    materialsUsed: {
      fa: 'روکش ممبران کره‌ای + چسب هنکل آلمان + پتینه طلایی ۲۴ عیار',
      ku: 'ڕووکەشی مێمبرانی کۆری + چەسپی هێنکڵی ئەڵمانی + پەتینەی زێڕین',
      en: 'Korean Membrane Foil + German Henkel Glue + 24K Gold Patina',
      ar: 'روكش ممبران كوري + غراء هينكل الألماني + تعتيق ذهبي عيار 24',
      tr: 'Kore Membran Folyo + Alman Henkel Tutkal + 24 Ayar Altın Patina',
      ru: 'Корейская мембрана + немецкий клей Henkel + золотая патина 24К',
    },
  },
  {
    id: 'prod-11',
    code: 'DM-VIP-111',
    image: 'https://images.unsplash.com/photo-1600566752355-35792bedcfea?auto=format&fit=crop&w=1000&q=80',
    category: 'ceramics_slab',
    pricePerMeterToman: 4850000,
    wholesaleColleaguePriceToman: 3150000,
    deliveryDays: 5,
    warrantyYears: 15,
    title: {
      fa: 'سرامیک اسلب پرسلانی بوک‌مچ ۱۲۰×۲۴۰ «کلکته گلد و پیترا گری» (کف، لابی و سرویس VIP)',
      ku: 'سیرامیکی سلابی پۆرسەلانی بووک-مەچ ١٢٠×٢٤٠ «کەلکەتە گۆڵد و پێترا گرەی»',
      en: 'Bookmatched 120x240 Calacatta Gold & Pietra Grey Porcelain Slab Suite',
      ar: 'بورسلان سلاب بوكماتش 120×240 «كالكوتا جولد وبيترا جراي» للأرضيات واللوبي',
      tr: 'Bookmatch 120x240 Calacatta Gold & Pietra Grey Lüks Porselen Slab',
      ru: 'Керамогранит слэб Bookmatch 120x240 «Calacatta Gold и Pietra Grey»',
    },
    subtitle: {
      fa: 'همراه با چسب پرسلانی الیاف‌دار، کلیپس همتراز، برش فارسی‌بر ۴۵ درجه و ارسال فوری از انبار مرکزی',
      ku: 'لەگەڵ چەسپی پۆرسەلانی، کلیپسی هاوتەراز، بڕینی ٤٥ پلە و ناردنی خێرا لە کۆگای سەرەکییەوە',
      en: 'Includes Fiber Porcelain Adhesive, Leveling System, 45° Miter Cut & Direct Warehouse Dispatch',
      ar: 'شامل غراء بورسلان مسلح، نظام تسوية، قص زاوية 45 درجة وشحن فوري من المستودع المركزي',
      tr: 'Elyaflı Porselen Yapıştırıcısı, Seviye Klipsi, 45° Gönye Kesim ve Depodan Anında Sevkiyat',
      ru: 'Включает армированный клей, СВП, запил под 45° и мгновенную отгрузку со склада',
    },
    materialsUsed: {
      fa: 'پرسلان سوپرپولیش رکتیفای + چسب C2TE + همتراز لیزری',
      ku: 'پۆرسەلانی سووپەرپۆلیش + چەسپی C2TE + هاوتەرازی لەیزەری',
      en: 'Super-Polished Rectified Porcelain + C2TE Adhesive + Laser Leveling',
      ar: 'بورسلان سوبر بوليش قص ليزر + غراء C2TE + تسوية ليزر',
      tr: 'Süper Parlak Rektifiyeli Porselen + C2TE Yapıştırıcı + Lazer Terazi',
      ru: 'Ректифицированный керамогранит + клей C2TE + лазерная укладка',
    },
  },
  {
    id: 'prod-12',
    code: 'DM-VIP-112',
    image: 'https://images.unsplash.com/photo-1584622650111-993a426fbf0a?auto=format&fit=crop&w=1000&q=80',
    category: 'ceramics_slab',
    pricePerMeterToman: 19800000,
    wholesaleColleaguePriceToman: 14500000,
    deliveryDays: 7,
    warrantyYears: 10,
    title: {
      fa: 'پکیج کلینیک مصالح لوکس: شیرآلات توکار طلای مات PVD + وال‌هنگ سوئیسی + روشویی اسلب',
      ku: 'پاکێجی کلینیکی کەرەستەی لوکس: شیرئالاتی ناودیواری زێڕینی مات + واڵ-هەنگی سویسری + دەستشۆری سلاب',
      en: 'Luxury Sanitaryware Package: Brushed Gold PVD Built-in Mixers + Swiss Wall-Hung + Slab Vanity',
      ar: 'باقة المواد الصحية الفاخرة: خلاطات مدفونة ذهب مطفي + مرحاض معلق سويسري + مغسلة سلاب',
      tr: 'Lüks Yapı Paketi: Fırçalanmış Altın PVD Ankastre Batarya + İsviçre Asma Klozet + Slab Lavabo',
      ru: 'Элитный пакет: встроенные смесители золото PVD + швейцарская инсталляция + раковина из слэба',
    },
    subtitle: {
      fa: 'ست کامل حمام و سرویس مستر با دوش پیانویی دیجیتال، فلاش‌تانک توکار گبریت و آینه تاچ ضدبخار',
      ku: 'سێتی تەواوی حەمام و ماستەر بە دووشی پیانۆیی دیجیتاڵ، فلاشتانکی گێبریت و ئاوێنەی تاچی دژەهەڵم',
      en: 'Complete Master Bath Suite with Digital Piano Rain Shower, Geberit Cistern & Anti-Fog Touch Mirror',
      ar: 'طقم حمام ماستر كامل مع دش بيانو رقمي، خزان جيبريت مخفي ومرآة لمس مضادة للضباب',
      tr: 'Dijital Piyano Yağmur Duş, Geberit Gömme Rezervuar ve Buğu Önleyici Dokunmatik Aynalı Tam Set',
      ru: 'Полный комплект мастер-ванной с тропическим душем, инсталляцией Geberit и сенсорным зеркалом',
    },
    materialsUsed: {
      fa: 'شیرآلات برنجی PVD + گبریت سوئیس + روشویی سنگ اسلب طبیعی',
      ku: 'شیرئالاتی برنجی PVD + گێبریتی سویسری + دەستشۆری بەردی سلاب',
      en: 'Solid Brass PVD Mixers + Swiss Geberit + Natural Stone Slab Vanity',
      ar: 'خلاطات نحاس PVD + جيبريت سويسري + مغسلة حجر سلاب طبيعي',
      tr: 'Masif Pirinç PVD Batarya + İsviçre Geberit + Doğal Taş Slab Lavabo',
      ru: 'Латунные смесители PVD + Geberit Швейцария + каменная раковина',
    },
  },
];

export interface VIPTier {
  level: number;
  badge: string;
  priceMonthlyToman: number;
  title: LocalizedString;
  targetAudience: LocalizedString;
  features: LocalizedStringArray;
}

export const VIP_TIERS: VIPTier[] = [
  {
    level: 1,
    badge: 'SILVER VIP',
    priceMonthlyToman: 1900000,
    title: {
      fa: 'سطح ۱: اشتراک نقره‌ای (کارگاه و نصاب مستقل)',
      ku: 'ئاستی ١: پاکێجی زیوی (کارگە و وەستای سەربەخۆ)',
      en: 'Tier 1: Silver Craftsman (Independent Workshop)',
      ar: 'المستوى 1: الفضي (ورشة وفني مستقل)',
      tr: 'Seviye 1: Gümüş Paket (Bağımsız Atölye & Usta)',
      ru: 'Уровень 1: Серебряный (Частный мастер и цех)',
    },
    targetAudience: {
      fa: 'ویژه کابینت‌سازان، کمدسازان و نصابان مستقل',
      ku: 'تایبەت بە وەستایانی کابینە، دارتاش و دامەزرێنەرانی سەربەخۆ',
      en: 'For independent cabinet makers & installers',
      ar: 'لصانعي الخزائن والفنيين المستقلين',
      tr: 'Bağımsız mobilya ustaları ve montajcılar için',
      ru: 'Для частных мебельщиков и установщиков',
    },
    features: {
      fa: [
        'صدور نامحدود پیش‌فاکتور اتحادیه و اقساط چک صیادی با نام کارگاه شما',
        'استوری‌ساز HD اینستاگرام (۳۰ پوستر در ماه با لوگو و تلفن شما)',
        'دسترسی به لیست قیمت روز عمده ورق MDF و یراق‌آلات همکاران',
      ],
      ku: [
        'دەرکردنی بێسنووری پێش‌فاکتۆر و قیستی چەک بە ناوی کارگەکەت',
        'دروستکەری ستۆری HD ئینستاگرام (٣٠ پۆستەر لە مانگێکدا بە ناو و ژمارەکەت)',
        'لیستی نرخی ڕۆژانەی کۆفرۆشی تەختەی MDF و یەراق بۆ هاوکاران',
      ],
      en: [
        'Unlimited Union Invoices & Check Installment Plans with your workshop name',
        'HD Instagram Story Maker (30 custom posters/month)',
        'Live wholesale MDF sheet & Blum hardware colleague price list',
      ],
      ar: [
        'إصدار غير محدود للفواتير وجدولة الشيكات باسم ورشتك',
        'صانع ستوري إنستغرام HD (30 ملصق شهرياً)',
        'قائمة أسعار الجملة لألواح MDF والمفصلات للزملاء',
      ],
      tr: [
        'Atölye adınızla sınırsız teklif ve taksit planı oluşturma',
        'HD Instagram Hikaye Oluşturucu (Ayda 30 poster)',
        'Güncel toptan MDF plaka ve Blum aksesuar fiyat listesi',
      ],
      ru: [
        'Безлимитные сметы и расчет рассрочки с именем вашего цеха',
        'HD генератор сторис Instagram (30 постеров в месяц)',
        'Оптовые цены на плиты МДФ и фурнитуру Blum для коллег',
      ],
    },
  },
  {
    level: 2,
    badge: 'GOLD SHOWROOM',
    priceMonthlyToman: 3900000,
    title: {
      fa: 'سطح ۲: اشتراک طلایی (نمایشگاه دکوراسیون و کابینت)',
      ku: 'ئاستی ٢: پاکێجی زێڕین (پێشانگای دیکۆرات و کابینە)',
      en: 'Tier 2: Gold Showroom (Kitchen & Interior Studio)',
      ar: 'المستوى 2: الذهبي (معرض مطابخ وديكور)',
      tr: 'Seviye 2: Altın Showroom (Mutfak & Dekorasyon Mağazası)',
      ru: 'Уровень 2: Золотой Шоурум (Салон кухонь и декора)',
    },
    targetAudience: {
      fa: 'ویژه نمایشگاه‌های کابینت، پارکت و دکوراسیون داخلی',
      ku: 'تایبەت بە پێشانگاکانی کابینە، پارکێت و دیکۆراتی ناوەوە',
      en: 'For kitchen showrooms & interior retail stores',
      ar: 'لمعارض المطابخ والباركيه والديكور الداخلي',
      tr: 'Mutfak showroomları ve iç mimarlık mağazaları için',
      ru: 'Для кухонных салонов и студий интерьера',
    },
    features: {
      fa: [
        'تمامی امکانات سطح ۱ + استوری‌ساز نامحدود ۱۰۸۰×۱۹۲۰ طلایی',
        'دستیار هوش مصنوعی معمار (ست کردن رنگ چوب و سنگ + کپشن‌نویسی)',
        'دریافت سرنخ مشتریان (Leads) منطقه جغرافیایی نمایشگاه شما',
      ],
      ku: [
        'هەموو تایبەتمەندییەکانی ئاستی ١ + ستۆری‌سازی بێسنووری ١٠٨٠×١٩٢٠',
        'یاریدەدەری زیرەکی تەلارسازی AI (ڕێکخستنی ڕەنگی دار و بەرد + نووسینی کەپشن)',
        'وەرگرتنی داواکاری کڕیارانی ناوچەکەت ڕاستەوخۆ بۆ واتسئەپەکەت',
      ],
      en: [
        'All Tier 1 features + Unlimited 1080×1920 Gold Story Maker',
        'AI Architect Assistant (Wood/Stone matching + Instagram captions)',
        'Regional customer leads routed directly to your WhatsApp',
      ],
      ar: [
        'جميع ميزات المستوى 1 + صانع ستوري غير محدود',
        'مساعد المعماري الذكي (تنسيق ألوان الخشب والرخام + كتابة المحتوى)',
        'تحويل طلبات العملاء في منطقتك مباشرة إلى واتساب معرضك',
      ],
      tr: [
        'Seviye 1 + Sınırsız 1080×1920 Altın Hikaye Oluşturucu',
        'Yapay Zeka Mimar Asistanı (Ahşap/Taş uyumu + Instagram metinleri)',
        'Bölgenizdeki müşteri taleplerinin doğrudan WhatsApp hattınıza yönlendirilmesi',
      ],
      ru: [
        'Все функции Уровня 1 + Безлимитный генератор сторис 1080×1920',
        'ИИ-Архитектор (подбор дерева и камня + тексты для Instagram)',
        'Заявки клиентов из вашего региона напрямую в WhatsApp',
      ],
    },
  },
  {
    level: 3,
    badge: 'PLATINUM ARCHITECT',
    priceMonthlyToman: 7500000,
    title: {
      fa: 'سطح ۳: پلاتینیوم (شرکت‌های معماری و بازسازی ساختمان)',
      ku: 'ئاستی ٣: پلاتینیۆم (کۆمپانیاکانی تەلارسازی و نۆژەنکردنەوە)',
      en: 'Tier 3: Platinum Architecture & Renovation Firm',
      ar: 'المستوى 3: البلاتيني (شركات الهندسة المعمارية والتجديد)',
      tr: 'Seviye 3: Platin (Mimarlık ve Tadilat Şirketleri)',
      ru: 'Уровень 3: Платиновый (Архитектурные бюро и реновация)',
    },
    targetAudience: {
      fa: 'ویژه دفاتر مهندسی معماری، پیمانکاران بازسازی و طراحان',
      ku: 'تایبەت بە نووسینگەکانی ئەندازیاری، بەڵێندەران و دیزاینەران',
      en: 'For architecture firms & turnkey renovation contractors',
      ar: 'للمكاتب الهندسية ومقاولي التجديد الشامل',
      tr: 'Mimarlık ofisleri ve anahtar teslim tadilat firmaları için',
      ru: 'Для архитектурных бюро и генподрядчиков ремонта',
    },
    features: {
      fa: [
        'داشبورد کامل هوش تجاری (BI) و تحلیل سود کارگاه در برابر نمایشگاه',
        'کاتالوگ ۶ زبانه (فارسی/کردی/عربی/انگلیسی/ترکی/روسی) و ۶ ارزی زنده',
        'پنل اختصاصی مدیریت ویزیتورها با پورسانت خودکار ۲۵٪',
      ],
      ku: [
        'داشبۆردی تەواوی زیرەکی بازرگانی (BI) و شیکردنەوەی قازانجی کارگە و پێشانگا',
        'کەتەلۆگی ٦ زمانە (کوردی/فارسی/عەرەبی/ئینگلیزی/تورکی) و ٦ دراوی زیندوو',
        'پەنێلی تایبەتی بەڕێوەبردنی مەندووب و ڤیزیتۆرەکان بە پاداشتی ٢٥٪',
      ],
      en: [
        'Full BI Analytics Dashboard & Workshop vs. Showroom Margin Calculator',
        '6-Language & 6-Currency Interactive Catalog for international clients',
        'Visitor & Sales Rep Management with automated 25% commission tracking',
      ],
      ar: [
        'لوحة ذكاء الأعمال الكاملة وتحليل هامش ربح الورشة والمعرض',
        'كتالوج تفاعلي بـ 6 لغات و6 عملات للعملاء الدوليين',
        'إدارة المندوبين مع تتبع عمولة 25٪ تلقائياً',
      ],
      tr: [
        'Tam İş Zekası (BI) Paneli ve Atölye/Showroom Kar Marjı Analizi',
        'Uluslararası müşteriler için 6 Dil ve 6 Para Birimli Katalog',
        '%25 Otomatik Komisyon Takip Sistemi ile Satış Temsilcisi Yönetimi',
      ],
      ru: [
        'Полная BI-аналитика и расчет маржинальности цеха и шоурума',
        'Интерактивный каталог на 6 языках и в 6 валютах для VIP-клиентов',
        'Управление агентами продаж с авто-расчетом комиссии 25%',
      ],
    },
  },
  {
    level: 4,
    badge: 'DIAMOND FACTORY',
    priceMonthlyToman: 14900000,
    title: {
      fa: 'سطح ۴: الماس (کارخانجات تولید چوب، ورق MDF و پخش عمده)',
      ku: 'ئاستی ٤: ئەڵماس (کارگەکانی بەرهەمهێنانی دار، MDF و کۆفرۆشی)',
      en: 'Tier 4: Diamond Factory & Wholesale Distributor',
      ar: 'المستوى 4: الماسي (مصانع الأخشاب وتجار الجملة)',
      tr: 'Seviye 4: Elmas (Ahşap Fabrikaları ve Toptan Dağıtım)',
      ru: 'Уровень 4: Бриллиантовый (Фабрики и оптовые дистрибьюторы)',
    },
    targetAudience: {
      fa: 'ویژه کارخانجات چوب، واردکنندگان یراق و بنکداران MDF',
      ku: 'تایبەت بە کارگەکانی دار، هاوردەکارانی یەراق و بازرگانانی MDF',
      en: 'For MDF manufacturers, timber mills & hardware importers',
      ar: 'لمصانع الأخشاب ومستوردي المفصلات وتجار الجملة',
      tr: 'MDF üreticileri, kereste fabrikaları ve aksesuar ithalatçıları için',
      ru: 'Для мебельных фабрик и импортеров плит МДФ и фурнитуры',
    },
    features: {
      fa: [
        'نمایش مستقیم محصولات و ورق‌های شما در ویترین همکاران سراسر کشور',
        'خروجی اختصاصی اندروید (APK و AAB) با برند کارخانه شما',
        'اتصال مستقیم انبار به سیستم استعلام قیمت لحظه‌ای ۶ ارزی (تومان/دینار/دلار/درهم/لیر/روبل)',
      ],
      ku: [
        'پیشاندانی ڕاستەوخۆی بەرهەم و تەختەکانتان لە ڤیترینی هاوکاران لە سەرتاسەری وڵات',
        'ئەپڵیکەیشنی تایبەتی ئەندرۆید (APK و AAB) بە براندی کارگەکەتان',
        'بەستنەوەی کۆگا بە سیستەمی نرخی چرکەیی ٦ دراو (تۆمان/دینار/دۆلار/لیرە/درهەم)',
      ],
      en: [
        'Direct product placement in nationwide colleague wholesale showcase',
        'Dedicated Android APK & AAB build with your factory branding',
        'Live inventory sync with 6-currency real-time pricing engine',
      ],
      ar: [
        'عرض مباشر لمنتجاتك وألواحك في واجهة الجملة للزملاء',
        'تطبيق أندرويد خاص (APK & AAB) بعلامة مصنعك التجارية',
        'ربط المخزون بنظام التسعير الفوري بـ 6 عملات',
      ],
      tr: [
        'Ürünlerinizin ülke çapındaki meslektaş vitrininde doğrudan sergilenmesi',
        'Fabrika markanızla özel Android APK ve AAB uygulaması',
        '6 para birimli canlı fiyatlandırma motoru ile stok entegrasyonu',
      ],
      ru: [
        'Прямое размещение вашей продукции в оптовой витрине партнеров',
        'Собственное Android-приложение (APK и AAB) под брендом фабрики',
        'Синхронизация склада с мультивалютным прайсом в реальном времени',
      ],
    },
  },
  {
    level: 5,
    badge: 'ROYAL FBNM METAVERCITY',
    priceMonthlyToman: 29000000,
    title: {
      fa: 'سطح ۵: رویال نیومتاورسیتی جهان (White-Label کامل + توان استیج FBNM)',
      ku: 'ئاستی ٥: ڕۆیاڵ نیۆمێتاڤێرسیتی جیهان (White-Label تەواو + FBNM)',
      en: 'Tier 5: Royal NeoMetaverCity Global Enterprise (Full White-Label)',
      ar: 'المستوى 5: رويال نيوميتافيرسيتي العالمي (White-Label كامل)',
      tr: 'Seviye 5: Royal NeoMetaverCity Global Kurumsal (Tam White-Label)',
      ru: 'Уровень 5: Royal NeoMetaverCity Глобальный (Полный White-Label)',
    },
    targetAudience: {
      fa: 'ویژه هلدینگ‌های بین‌المللی ساختمانی در ایران، اقلیم کردستان، دبی، استانبول و مسکو',
      ku: 'تایبەت بە هۆڵدینگە نێودەوڵەتییەکان لە ئێران، هەرێمی کوردستان، دوبەی و ئەستەنبوڵ',
      en: 'For international construction holdings in Tehran, Erbil, Dubai, Istanbul & Moscow',
      ar: 'للشركات القابضة الدولية في طهران وأربيل ودبي وإسطنبول وموسكو',
      tr: 'Tahran, Erbil, Dubai, İstanbul ve Moskova merkezli uluslararası holdingler için',
      ru: 'Для международных строительных холдингов (Тегеран, Эрбиль, Дубай, Стамбул, Москва)',
    },
    features: {
      fa: [
        'تحویل سورس کامل و اپلیکیشن اختصاصی White-Label روی دامنه و سرور شما',
        'انتشار رسمی در گوگل‌پلی، اپ‌استور، کافه‌بازار و مایکت با نام برند شما',
        'غرفه دائمی VIP در شهر جدید نیومتاورسیتی جهان | اکوسیستم آفرینش | توان استیج FBNM',
      ],
      ku: [
        'ڕادەستکردنی سۆرسی تەواو و ئەپڵیکەیشنی تایبەتی White-Label لەسەر دۆمەین و سێرڤەری خۆتان',
        'بڵاوکردنەوەی فەرمی لە گووگڵ پلەی، کافەبازاڕ و مایکێت بە ناوی براندی خۆتان',
        'پێشانگای هەمیشەیی VIP لە شاری نوێی نیۆمێتاڤێرسیتی جیهان | توان ستەیج FBNM',
      ],
      en: [
        'Full White-Label platform deployed on your custom domain & servers',
        'Official publishing on Google Play, CafeBazaar & Myket under your brand',
        'Permanent VIP Pavilion in NeoMetaverCity Global | FBNM Ecosystem',
      ],
      ar: [
        'منصة White-Label كاملة على نطاقك وخوادمك الخاصة',
        'نشر رسمي في جوجل بلاي وكافيه بازار باسم علامتك التجارية',
        'جناح VIP دائم في مدينة نيوميتافيرسيتي العالمية | نظام FBNM',
      ],
      tr: [
        'Kendi alan adınız ve sunucunuzda tam White-Label platform kurulumu',
        'Markanızla Google Play, CafeBazaar ve Myket mağazalarında resmi yayın',
        'NeoMetaverCity Global | FBNM Ekosisteminde kalıcı VIP Pavilyon',
      ],
      ru: [
        'Полная White-Label платформа на вашем домене и серверах',
        'Официальная публикация в Google Play, CafeBazaar и Myket под вашим брендом',
        'Постоянный VIP-павильон в NeoMetaverCity Global | Экосистема FBNM',
      ],
    },
  },
];

export const UI_STRINGS: Record<LanguageCode, {
  subtitleEcosystem: string;
  hookBannerBadge: string;
  hookBannerTitle: string;
  hookBannerCTA: string;
  accessibilityBtn: string;
  adhdModeTitle: string;
  adhdModeDesc: string;
  ttsTitle: string;
  ttsDesc: string;
  fontSizeTitle: string;
  highContrastTitle: string;
  navCalculator: string;
  navShowcase: string;
  navDashboard: string;
  navAiArchitect: string;
  btnInstallPWA: string;
  btnGithubApk: string;
  btnVipPlans: string;
  btnAffiliate: string;
  calcHeading: string;
  calcSubheading: string;
  projectTypeLabel: string;
  materialTypeLabel: string;
  lengthLabel: string;
  heightLabel: string;
  checkMonthsLabel: string;
  unionBreakdownTitle: string;
  cashPriceLabel: string;
  downPaymentLabel: string;
  monthlyCheckLabel: string;
  sendWhatsappBtn: string;
  readInvoiceVoiceBtn: string;
  showcaseHeading: string;
  showcaseSubheading: string;
  retailPricePerMeter: string;
  colleagueWholesalePrice: string;
  makeStoryBtn: string;
  orderWhatsappBtn: string;
}> = {
  fa: {
    subtitleEcosystem: 'اکوسیستم آفرینش | شهر جدید نیومتاورسیتی جهان | توان استیج FBNM',
    hookBannerBadge: 'پیشنهاد ویژه ۳ ثانیه اول ورود — محاسبه طبق نرخ مصوب اتحادیه ۱۴۰۵',
    hookBannerTitle: 'قیمت دقیق کابینت، کمد دیواری و بازسازی منزل خود را همین الان در ۱۰ ثانیه حساب کنید!',
    hookBannerCTA: '📐 محاسبه ۱۰ ثانیه‌ای قیمت کابینت آشپزخانه و کمد دیواری شما + اقساط چک صیادی',
    accessibilityBtn: '♿ دسترسی‌پذیری و تمرکز (ADHD & Accessibility)',
    adhdModeTitle: '🧠 حالت تمرکز ویژه ADHD (ADHD Focus Mode)',
    adhdModeDesc: 'حذف انیمیشن‌های اضافی و بنرهای فرعی + نمایش گام‌به‌گام با تیک سبز و چیدمان آرامش‌بخش',
    ttsTitle: '🔊 خوانش صوتی فارسی قیمت‌ها و پیش‌فاکتور (TTS)',
    ttsDesc: 'قرائت صوتی مشخصات محصول و مبلغ نهایی پیش‌فاکتور برای افراد کم‌بینا',
    fontSizeTitle: '🔍 بزرگ‌نمایی فونت و دکمه‌های لمسی بزرگ (48px+)',
    highContrastTitle: '🌓 حالت کنتراست بالا (High-Contrast Mode)',
    navCalculator: 'ماشین‌حساب متراژ و چک صیادی',
    navShowcase: 'ویترین ۱۰ نمونه کار و استوری‌ساز',
    navDashboard: 'داشبورد هوش تجاری (BI)',
    navAiArchitect: 'معمار هوشمند AI',
    btnInstallPWA: '📲 نصب ۱-کلیکی اپ (iOS / Android)',
    btnGithubApk: '🤖 ساخت خودکار APK/AAB و پوش گیت‌هاب',
    btnVipPlans: '👑 اشتراک VIP (سطوح ۱ تا ۵)',
    btnAffiliate: '🤝 همکاری ۲۵٪ پورسانت و White-Label',
    calcHeading: '📐 ماشین‌حساب جادویی متراژ و پیش‌فاکتور آنی کابینت، کمد و بازسازی',
    calcSubheading: 'محاسبه دقیق بر اساس ضرایب رسمی اتحادیه (۶۰٪ کابینت زمینی + ۴۰٪ هوایی + صفحه ۵ سانتی + یراق بلوم اتریش) و تقسیط با چک صیادی',
    projectTypeLabel: '۱. انتخاب نوع پروژه دکوراسیون یا بازسازی:',
    materialTypeLabel: '۲. انتخاب جنس ورق، رنگ و متریال مصرفی:',
    lengthLabel: '۳. متراژ طول یا مساحت پروژه:',
    heightLabel: '۴. ارتفاع سقف محل اجرا (سانتی‌متر):',
    checkMonthsLabel: '۵. تعداد ماه‌های اقساط چک صیادی (۱ تا ۶ ماهه):',
    unionBreakdownTitle: 'جدول تفکیک مهندسی و ضرایب رسمی اتحادیه:',
    cashPriceLabel: 'مبلغ کل نقد (با ۵٪ تخفیف ویژه تسویه نقدی):',
    downPaymentLabel: 'پیش‌پرداخت نقد هنگام عقد قرارداد (۴۰٪):',
    monthlyCheckLabel: 'مبلغ هر فقره چک صیادی ماهانه:',
    sendWhatsappBtn: 'ارسال مستقیم پیش‌فاکتور به واتساپ کابینت‌ساز',
    readInvoiceVoiceBtn: '🔊 خوانش صوتی پیش‌فاکتور',
    showcaseHeading: '🏛️ ویترین لوکس ۱۰ نمونه کار واقعی + قیمت همکار و استوری‌ساز ۱-کلیکی',
    showcaseSubheading: 'مقایسه شفاف قیمت هر متر با قیمت عمده ورق MDF و یراق برای همکاران + تولید فوری پوستر استوری ۱۰۸۰×۱۹۲۰ اینستاگرام',
    retailPricePerMeter: 'قیمت اجرا (هر متر):',
    colleagueWholesalePrice: 'قیمت عمده ورق و یراق همکار:',
    makeStoryBtn: '📸 ساخت استوری HD با نام کارگاه شما',
    orderWhatsappBtn: 'ثبت سفارش / استعلام واتساپ',
  },
  ku: {
    subtitleEcosystem: 'ئیکۆسیستەمی ئافراندن | شاری نوێی نیۆمێتاڤێرسیتی جیهان | توان ستەیج FBNM',
    hookBannerBadge: 'ئۆفەری تایبەت — هەژمارکردنی دەستبەجێ بەپێی نرخی فەرمی یەکێتی دارتاشان',
    hookBannerTitle: 'نرخی وردی کابینەی چێشتخانە، کانتۆر و نۆژەنکردنەوەی ماڵەکەت لە ١٠ چرکەدا بزانە!',
    hookBannerCTA: '📐 هەژمارکردنی ١٠ چرکەیی نرخی کابینە و کانتۆری دیواری + قیستی چەک',
    accessibilityBtn: '♿ دەستڕاگەیشتن و تەرکیز (ADHD & Accessibility)',
    adhdModeTitle: '🧠 دۆخی تەرکیزی تایبەت بە ADHD (هەنگاو بە هەنگاو)',
    adhdModeDesc: 'لادانی جووڵە زیادەکان و پیشاندانی هەنگاو بە هەنگاوی هەژمارکردن بە نیشانەی سەوز',
    ttsTitle: '🔊 خوێندنەوەی دەنگی نرخەکان و پێش‌فاکتۆر (TTS)',
    ttsDesc: 'خوێندنەوەی دەنگی تایبەتمەندی کاڵا و بڕی کۆتایی فاکتۆر بۆ کەمبینایان',
    fontSizeTitle: '🔍 گەورەکردنی فۆنت و دوگمەی گەورەی دەستلێدان (48px+)',
    highContrastTitle: '🌓 دۆخی کۆنتراستی بەرز (High-Contrast)',
    navCalculator: 'ژمێریاری مەتر و قیستی چەک',
    navShowcase: 'ڤیترینی ١٠ نموونەکار و ستۆری‌ساز',
    navDashboard: 'داشبۆردی زیرەکی بازرگانی (BI)',
    navAiArchitect: 'ئەندازیاری زیرەک AI',
    btnInstallPWA: '📲 دابەزاندنی ١-کلیکی ئەپ (iOS / Android)',
    btnGithubApk: '🤖 دروستکردنی خۆکاری APK/AAB لە گیت‌هاب',
    btnVipPlans: '👑 بەشداری VIP (ئاستی ١ تا ٥)',
    btnAffiliate: '🤝 هاوکاری ٢٥٪ قازانج و White-Label',
    calcHeading: '📐 ژمێریاری جادوویی مەتر و پێش‌فاکتۆری دەستبەجێی کابینە، کانتۆر و نۆژەنکردنەوە',
    calcSubheading: 'هەژمارکردنی ورد بەپێی فۆرمۆلی فەرمی (٦٠٪ کابینەی خوارەوە + ٤٠٪ سەرەوە + سەفحەی ٥ سانتی + یەراقی بلومی نەمسا) و قیستی ١ تا ٦ مانگە',
    projectTypeLabel: '١. هەڵبژاردنی جۆری پڕۆژەی دیکۆرات یان نۆژەنکردنەوە:',
    materialTypeLabel: '٢. هەڵبژاردنی جۆری دار، تەختەی MDF و ڕووکەش:',
    lengthLabel: '٣. درێژی یان ڕووبەری پڕۆژە (بە مەتر):',
    heightLabel: '٤. بەرزی سەقفی شوێنەکە (سانتیمەتر):',
    checkMonthsLabel: '٥. ژمارەی مانگەکانی قیستی چەک (١ تا ٦ مانگ):',
    unionBreakdownTitle: 'خشتەی وردەکاری ئەندازیاری و ستانداردی یەکێتی:',
    cashPriceLabel: 'کۆی گشتی نرخی نەقد (لەگەڵ ٥٪ داشکاندنی تایبەت):',
    downPaymentLabel: 'پێشەکی نەقد لە کاتی گرێبەست (٤٠٪):',
    monthlyCheckLabel: 'بڕی هەر چەکێکی مانگانە:',
    sendWhatsappBtn: 'ناردنی ڕاستەوخۆی پێش‌فاکتۆر بۆ واتسئەپی وەستا',
    readInvoiceVoiceBtn: '🔊 خوێندنەوەی دەنگی فاکتۆر',
    showcaseHeading: '🏛️ ڤیترینی شاهانەی ١٠ نموونەکاری ڕاستەقینە + نرخی کۆفرۆشی هاوکار و ستۆری‌ساز',
    showcaseSubheading: 'بەراوردی شەفافی نرخی هەر مەترێک لەگەڵ نرخی کۆفرۆشی تەختەی MDF و یەراق + دروستکردنی پۆستەری ستۆری ١٠٨٠×١٩٢٠',
    retailPricePerMeter: 'نرخی جێبەجێکردن (هەر مەترێک):',
    colleagueWholesalePrice: 'نرخی کۆفرۆشی MDF و یەراق بۆ هاوکار:',
    makeStoryBtn: '📸 دروستکردنی ستۆری HD بە ناوی پێشانگاکەت',
    orderWhatsappBtn: 'داواکردن لە ڕێگەی واتسئەپ',
  },
  en: {
    subtitleEcosystem: 'Creation Ecosystem | New NeoMetaverCity World | FBNM Stage Power',
    hookBannerBadge: 'Instant 10-Second Union Estimator — Official 2026 Architectural Formula',
    hookBannerTitle: 'Calculate Your Exact Kitchen Cabinet, Wardrobe & Renovation Cost in 10 Seconds!',
    hookBannerCTA: '📐 10-Second Kitchen Cabinet & Wardrobe Calculator + Sayadi Check Installments',
    accessibilityBtn: '♿ ADHD & Accessibility Focus Panel',
    adhdModeTitle: '🧠 ADHD Focus Mode (Calm Step-by-Step Layout)',
    adhdModeDesc: 'Hides extra animations & secondary banners; enables step-by-step green checkmarks',
    ttsTitle: '🔊 Voice Screen Reader for Prices & Invoices (TTS)',
    ttsDesc: 'Reads product specs and final invoice totals aloud via Web Speech API',
    fontSizeTitle: '🔍 Font Magnification & 48px+ Large Touch Targets',
    highContrastTitle: '🌓 High-Contrast Mode (WCAG AAA Visibility)',
    navCalculator: 'Union Calculator & Installments',
    navShowcase: '10 Luxury Showcase & Story Maker',
    navDashboard: 'BI Market Dashboard',
    navAiArchitect: 'AI Master Architect',
    btnInstallPWA: '📲 1-Click Install (iOS / Android)',
    btnGithubApk: '🤖 Direct GitHub Push & APK/AAB Build',
    btnVipPlans: '👑 VIP Plans (Tiers 1–5)',
    btnAffiliate: '🤝 25% Affiliate & White-Label',
    calcHeading: '📐 Magic Union Meterage & Instant Pre-Invoice Calculator',
    calcSubheading: 'Calculated per official union coefficients (60% Base + 40% Wall Cabinet + 5cm Top + Blum Hardware) with 1–6 Month Check Installments',
    projectTypeLabel: '1. Select Interior or Renovation Project Type:',
    materialTypeLabel: '2. Select Wood, MDF Sheet & Finish Material:',
    lengthLabel: '3. Project Length or Area (Meters):',
    heightLabel: '4. Ceiling Height (cm):',
    checkMonthsLabel: '5. Sayadi Check Installment Duration (1 to 6 Months):',
    unionBreakdownTitle: 'Official Union Engineering Breakdown:',
    cashPriceLabel: 'Total Cash Price (Includes 5% Cash Settlement Discount):',
    downPaymentLabel: 'Contract Down Payment (40%):',
    monthlyCheckLabel: 'Monthly Sayadi Check Amount:',
    sendWhatsappBtn: 'Send Instant Pre-Invoice to Cabinet Maker WhatsApp',
    readInvoiceVoiceBtn: '🔊 Read Invoice Aloud',
    showcaseHeading: '🏛️ 10 Real Luxury Portfolio Works + Colleague Wholesale & 1-Click Story Maker',
    showcaseSubheading: 'Transparent retail price per meter vs. colleague MDF/hardware wholesale rates + 1080×1920 HD Instagram Story generator',
    retailPricePerMeter: 'Retail Executed Price / Meter:',
    colleagueWholesalePrice: 'Colleague MDF & Hardware Wholesale:',
    makeStoryBtn: '📸 Create HD Story with Your Brand',
    orderWhatsappBtn: 'Inquire via WhatsApp',
  },
  ar: {
    subtitleEcosystem: 'منظومة الإبداع | مدينة نيوميتافيرسيتي العالمية الجديدة | قوة منصة FBNM',
    hookBannerBadge: 'حاسبة فورية خلال 10 ثوانٍ — وفق معادلة الاتحاد الرسمية',
    hookBannerTitle: 'احسب التكلفة الدقيقة لخزائن المطبخ والدولاب وتجديد منزلك في 10 ثوانٍ!',
    hookBannerCTA: '📐 حساب فوري خلال 10 ثوانٍ لسعر خزائن المطبخ والدولاب + أقساط الشيكات',
    accessibilityBtn: '♿ إمكانية الوصول والتركيز (ADHD & Accessibility)',
    adhdModeTitle: '🧠 وضع التركيز الخاص بـ ADHD (تخطيط هادئ خطوة بخطوة)',
    adhdModeDesc: 'إخفاء الحركات الإضافية واللافتات الفرعية وعرض الخطوات بعلامة صح خضراء',
    ttsTitle: '🔊 القارئ الصوتي للأسعار والفواتير (TTS)',
    ttsDesc: 'قراءة صوتية واضحة لمواصفات المنتج والمبلغ النهائي لضعاف البصر',
    fontSizeTitle: '🔍 تكبير الخط وأزرار اللمس الكبيرة (48px+)',
    highContrastTitle: '🌓 وضع التباين العالي (High-Contrast)',
    navCalculator: 'حاسبة الأمتار والأقساط',
    navShowcase: 'معرض 10 أعمال وصانع الستوري',
    navDashboard: 'لوحة ذكاء الأعمال (BI)',
    navAiArchitect: 'المعماري الذكي AI',
    btnInstallPWA: '📲 تثبيت بنقرة واحدة (iOS / Android)',
    btnGithubApk: '🤖 بناء APK/AAB والرفع إلى GitHub',
    btnVipPlans: '👑 اشتراك VIP (المستويات 1-5)',
    btnAffiliate: '🤝 عمولة 25٪ وتطبيق White-Label',
    calcHeading: '📐 الحاسبة السحرية الفورية للأمتار والفاتورة المبدئية والأقساط',
    calcSubheading: 'حساب دقيق وفق معاملات الاتحاد (60٪ خزائن سفلية + 40٪ علوية + سطح 5 سم + مفصلات بلوم النمساوية) وتقسيط 1 إلى 6 أشهر',
    projectTypeLabel: '1. اختر نوع مشروع الديكور أو التجديد:',
    materialTypeLabel: '2. اختر نوع الخشب والخامات:',
    lengthLabel: '3. الطول أو المساحة بالمتر:',
    heightLabel: '4. ارتفاع السقف (سم):',
    checkMonthsLabel: '5. عدد أشهر التقسيط بالشيكات (1 إلى 6 أشهر):',
    unionBreakdownTitle: 'تفصيل المعاملات الهندسية الرسمية للاتحاد:',
    cashPriceLabel: 'إجمالي السعر النقدي (شامل خصم 5٪ للدفع النقدي):',
    downPaymentLabel: 'الدفعة الأولى عند التعاقد (40٪):',
    monthlyCheckLabel: 'قيمة الشيك الشهري:',
    sendWhatsappBtn: 'إرسال الفاتورة المبدئية مباشرة إلى واتساب الورشة',
    readInvoiceVoiceBtn: '🔊 قراءة صوتية للفاتورة',
    showcaseHeading: '🏛️ معرض 10 أعمال فاخرة حقيقية + أسعار الجملة للزملاء وصانع ستوري HD',
    showcaseSubheading: 'مقارنة شفافة لسعر المتر مع أسعار الجملة لألواح MDF والمفصلات + تصميم بوستر ستوري 1080×1920',
    retailPricePerMeter: 'سعر التنفيذ للمتر:',
    colleagueWholesalePrice: 'سعر الجملة للزملاء (ألواح ومفصلات):',
    makeStoryBtn: '📸 تصميم ستوري HD باسم معرضك',
    orderWhatsappBtn: 'طلب عبر واتساب',
  },
  tr: {
    subtitleEcosystem: 'Yaratılış Ekosistemi | Yeni NeoMetaverCity Dünya Şehri | FBNM Sahne Gücü',
    hookBannerBadge: '10 Saniyede Anında Fiyat Hesaplama — Resmi Birlik Formülü',
    hookBannerTitle: 'Mutfak Dolabı, Gardırop ve Tadilat Fiyatınızı 10 Saniyede Hesaplayın!',
    hookBannerCTA: '📐 10 Saniyede Mutfak Dolabı & Gardırop Fiyat Hesaplama + Çek Taksit Planı',
    accessibilityBtn: '♿ Erişilebilirlik & DEHB Odak (ADHD)',
    adhdModeTitle: '🧠 DEHB Özel Odak Modu (Adım Adım Sakin Görünüm)',
    adhdModeDesc: 'Ekstra animasyonları gizler, yeşil onay işaretli adım adım hesaplama sunar',
    ttsTitle: '🔊 Fiyat ve Fatura Sesli Okuma (TTS)',
    ttsDesc: 'Görme engelliler için ürün ve fatura tutarını sesli okur',
    fontSizeTitle: '🔍 Yazı Büyüklüğü & Büyük Dokunmatik Butonlar (48px+)',
    highContrastTitle: '🌓 Yüksek Kontrast Modu (High-Contrast)',
    navCalculator: 'Metraj & Taksit Hesaplayıcı',
    navShowcase: '10 Lüks Vitrin & Hikaye Üretici',
    navDashboard: 'İş Zekası (BI) Paneli',
    navAiArchitect: 'AI Uzman Mimar',
    btnInstallPWA: '📲 1-Tıkla Uygulamayı Kur (iOS/Android)',
    btnGithubApk: '🤖 GitHub Doğrudan Push & APK/AAB',
    btnVipPlans: '👑 VIP Paketler (Seviye 1–5)',
    btnAffiliate: '🤝 %25 Komisyon & White-Label',
    calcHeading: '📐 Sihirli Metraj, Ön Fatura ve Taksit Hesaplama Motoru',
    calcSubheading: 'Resmi birlik katsayılarına göre (%60 Alt + %40 Üst Dolap + 5cm Tezgah + Blum Avusturya) ve 1–6 Ay Çek Taksit Seçeneği',
    projectTypeLabel: '1. Proje veya Tadilat Türünü Seçin:',
    materialTypeLabel: '2. Ahşap, MDF ve Yüzey Materyalini Seçin:',
    lengthLabel: '3. Proje Uzunluğu veya Alanı (Metre):',
    heightLabel: '4. Tavan Yüksekliği (cm):',
    checkMonthsLabel: '5. Çek Taksit Vadesi (1 - 6 Ay):',
    unionBreakdownTitle: 'Resmi Mühendislik ve Maliyet Dağılımı:',
    cashPriceLabel: 'Toplam Nakit Fiyat (%5 Peşin İndirimi Dahil):',
    downPaymentLabel: 'Sözleşme Peşinatı (%40):',
    monthlyCheckLabel: 'Aylık Çek Taksit Tutarı:',
    sendWhatsappBtn: 'Ön Faturayı Doğrudan Ustaya WhatsApp ile Gönder',
    readInvoiceVoiceBtn: '🔊 Faturayı Sesli Oku',
    showcaseHeading: '🏛️ 10 Gerçek Lüks Portfolyo + Toptan Meslektaş Fiyatı & 1-Tıkla Story',
    showcaseSubheading: 'Perakende metretül fiyatı ile toptan MDF/aksesuar karşılaştırması + 1080×1920 HD Instagram Hikaye üretici',
    retailPricePerMeter: 'Uygulama Metre Fiyatı:',
    colleagueWholesalePrice: 'Meslektaş Toptan MDF & Aksesuar:',
    makeStoryBtn: '📸 Mağaza Adınızla HD Story İndir',
    orderWhatsappBtn: 'WhatsApp Sipariş',
  },
  ru: {
    subtitleEcosystem: 'Экосистема Созидания | Новый Мировой Город NeoMetaverCity | Мощь FBNM Stage',
    hookBannerBadge: 'Мгновенный расчет за 10 секунд — Официальная формула гильдии',
    hookBannerTitle: 'Рассчитайте точную стоимость кухни, шкафа-купе и ремонта за 10 секунд!',
    hookBannerCTA: '📐 Расчет стоимости кухни и гардеробной за 10 секунд + Рассрочка по чекам',
    accessibilityBtn: '♿ Доступность и Фокус (СДВГ / ADHD)',
    adhdModeTitle: '🧠 Режим фокусировки СДВГ (Спокойный пошаговый вид)',
    adhdModeDesc: 'Скрывает лишнюю анимацию и баннеры, включает пошаговый расчет с галочками',
    ttsTitle: '🔊 Голосовое озвучивание цен и сметы (TTS)',
    ttsDesc: 'Озвучивает характеристики и итоговую сумму для слабовидящих пользователей',
    fontSizeTitle: '🔍 Крупный шрифт и большие кнопки (48px+)',
    highContrastTitle: '🌓 Режим высокой контрастности',
    navCalculator: 'Калькулятор погонажа и рассрочки',
    navShowcase: '10 VIP работ и Генератор Сторис',
    navDashboard: 'BI Аналитика рынка',
    navAiArchitect: 'ИИ-Архитектор',
    btnInstallPWA: '📲 Установка в 1 клик (iOS / Android)',
    btnGithubApk: '🤖 Прямой Push в GitHub и сборка APK/AAB',
    btnVipPlans: '👑 VIP Тарифы (Уровни 1–5)',
    btnAffiliate: '🤝 Комиссия 25% и White-Label',
    calcHeading: '📐 Умный калькулятор погонажа, предсметы и рассрочки',
    calcSubheading: 'Расчет по нормативам гильдии (60% нижние базы + 40% верх + столешница 5 см + Blum Австрия) и рассрочка от 1 до 6 месяцев',
    projectTypeLabel: '1. Выберите тип проекта или реновации:',
    materialTypeLabel: '2. Выберите массив дерева, МДФ или эмаль:',
    lengthLabel: '3. Длина или площадь проекта (метры):',
    heightLabel: '4. Высота потолка (см):',
    checkMonthsLabel: '5. Срок рассрочки по чекам (от 1 до 6 месяцев):',
    unionBreakdownTitle: 'Инженерная детализация по стандарту гильдии:',
    cashPriceLabel: 'Итоговая цена за наличные (со скидкой 5%):',
    downPaymentLabel: 'Аванс при подписании договора (40%):',
    monthlyCheckLabel: 'Сумма ежемесячного платежа по чеку:',
    sendWhatsappBtn: 'Отправить смету мастеру в WhatsApp',
    readInvoiceVoiceBtn: '🔊 Озвучить смету голосом',
    showcaseHeading: '🏛️ Витрина 10 реальных VIP-проектов + Оптовый прайс и Генератор Сторис',
    showcaseSubheading: 'Розничная цена за метр и оптовая стоимость плит МДФ/фурнитуры для коллег + HD постер 1080×1920 для Instagram',
    retailPricePerMeter: 'Розничная цена за метр:',
    colleagueWholesalePrice: 'Оптовая цена МДФ и фурнитуры (коллегам):',
    makeStoryBtn: '📸 Скачать HD Сторис с вашим брендом',
    orderWhatsappBtn: 'Заказать в WhatsApp',
  },
  hy: {
    subtitleEcosystem: 'Արարման Էկոհամակարգ | Նոր ՆեոՄետավերՍիթի Համաշխարհային Քաղաք | FBNM Հզորություն',
    hookBannerBadge: 'Ակնթարթային հաշվարկ 10 վայրկյանում — Պաշտոնական ճարտարապետական բանաձև',
    hookBannerTitle: 'Հաշվարկեք խոհանոցի կահույքի, պահարանների և վերանորոգման ճշգրիտ արժեքը 10 վայրկյանում:',
    hookBannerCTA: '📐 Խոհանոցի կահույքի և պահարանների արժեքի 10-վայրկյանանոց հաշվիչ + Ապառիկ պլան',
    accessibilityBtn: '♿ Հասանելիություն և ADHD Կենտրոնացում',
    adhdModeTitle: '🧠 ADHD Կենտրոնացման Ռեժիմ (Հանգիստ քայլ առ քայլ տեսք)',
    adhdModeDesc: 'Թաքցնում է ավելորդ անիմացիաները և ցուցադրում քայլ առ քայլ հաշվարկը կանաչ նշումներով',
    ttsTitle: '🔊 Գների և նախահաշվի ձայնային ընթերցում (TTS)',
    ttsDesc: 'Ձայնային ընթերցում տեսողության խնդիրներ ունեցող օգտատերերի համար',
    fontSizeTitle: '🔍 Տառաչափի մեծացում և մեծ կոճակներ (48px+)',
    highContrastTitle: '🌓 Բարձր կոնտրաստի ռեժիմ (High-Contrast)',
    navCalculator: 'Մետրաժի և Ապառիկի Հաշվիչ',
    navShowcase: '10 Լյուքս Նմուշներ և Story Ստեղծող',
    navDashboard: 'Բիզնես Վերլուծություն (BI)',
    navAiArchitect: 'AI Ճարտարապետ',
    btnInstallPWA: '📲 1-Կլիկով Տեղադրել Հավելվածը (iOS / Android)',
    btnGithubApk: '🤖 GitHub Ավտո-Ռելիզ և APK/AAB Կառուցում',
    btnVipPlans: '👑 VIP Փաթեթներ (Մակարդակ 1–5)',
    btnAffiliate: '🤝 25% Միջնորդավճար և White-Label',
    calcHeading: '📐 Կահույքի, Պահարանների և Վերանորոգման Մետրաժի և Նախահաշվի Հաշվիչ',
    calcSubheading: 'Ճշգրիտ հաշվարկ պաշտոնական գործակիցներով (60% ստորին + 40% վերին պահարան + 5սմ երեսպատում + Blum Ավստրիա) և 1–6 ամիս ապառիկ',
    projectTypeLabel: '1. Ընտրեք նախագծի կամ վերանորոգման տեսակը.',
    materialTypeLabel: '2. Ընտրեք փայտի, MDF-ի և երեսպատման տեսակը.',
    lengthLabel: '3. Նախագծի երկարությունը կամ մակերեսը (մետր).',
    heightLabel: '4. Առաստաղի բարձրությունը (սմ).',
    checkMonthsLabel: '5. Ապառիկ վճարման ամիսների քանակը (1-ից 6 ամիս).',
    unionBreakdownTitle: 'Ճարտարապետական և ինժեներական արժեքի բաշխում.',
    cashPriceLabel: 'Ընդհանուր կանխիկ արժեքը (ներառյալ 5% զեղչ).',
    downPaymentLabel: 'Պայմանագրի կանխավճար (40%).',
    monthlyCheckLabel: 'Ամսական վճարման գումարը.',
    sendWhatsappBtn: 'Ուղարկել նախահաշիվը WhatsApp-ով',
    readInvoiceVoiceBtn: '🔊 Ձայնային ընթերցել նախահաշիվը',
    showcaseHeading: '🏛️ 10 Իրական Լյուքս Աշխատանքներ + Մեծածախ Գներ և 1-Կլիկ Story Ստեղծող',
    showcaseSubheading: 'Մանրածախ և մեծածախ MDF/Blum գների համեմատություն + 1080×1920 HD Instagram Story պաստառի ստեղծում',
    retailPricePerMeter: 'Կատարման գինը (1 մետր).',
    colleagueWholesalePrice: 'Գործընկերոջ մեծածախ գին (MDF և ֆուրնիտուրա).',
    makeStoryBtn: '📸 Ստեղծել HD Story Ձեր Բրենդով',
    orderWhatsappBtn: 'Պատվիրել WhatsApp-ով',
  },
};

export interface RelatedGuildItem {
  id: string;
  guildTitle: string;
  whyMustBuy: string;
  visitorPitchTip: string;
  sampleBrandName: string;
  sampleTagline: string;
  priceMultiplier: number;
}

export const RELATED_GUILDS_DATA: RelatedGuildItem[] = [
  {
    id: 'cabinet-workshop',
    guildTitle: '۱. کارگاه‌ها و نمایشگاه‌های کابینت آشپزخانه و کمد دیواری',
    whyMustBuy:
      'جلوگیری از فرار مشتری به خاطر قیمت‌های مبهم؛ صدور پیش‌فاکتور رسمی ۶۰/۴۰ اتحادیه و اقساط چک صیادی در ۱۰ ثانیه جلوی چشم مشتری.',
    visitorPitchTip: 'اسم کارگاهشان را بزنید و دکمه پیش‌فاکتور چک صیادی را نشان دهید؛ درجا خرید می‌کنند!',
    sampleBrandName: 'صنایع چوب و کابینت شاهکار | Shahkar Cabinet VIP',
    sampleTagline: 'مجری تخصصی کابینت نئوکلاسیک، انزو و چوب گردو با ضمانت ۱۰ ساله اتحادیه',
    priceMultiplier: 1.0,
  },
  {
    id: 'mdf-hardware-shop',
    guildTitle: '۲. مغازه‌داران یراق‌آلات، ورق MDF، هایگلاس و صفحه کابینت',
    whyMustBuy:
      'ارائه لیست قیمت روز عمده همکار + محاسبه خودکار تعداد ورق MDF و تعداد لولا بلوم مورد نیاز هر پروژه برای نصابان و مشتریان.',
    visitorPitchTip: 'بخش «برآوردگر تعداد ورق MDF و لولا بلوم» و دکمه «قیمت عمده همکار» را به مغازه‌دار نشان دهید.',
    sampleBrandName: 'پخش ورق و یراق‌آلات مرکزی | Blum & AGT Center',
    sampleTagline: 'نمایندگی رسمی ورق‌های هایگلاس AGT ترک و یراق‌آلات اورجینال بلوم اتریش',
    priceMultiplier: 0.9,
  },
  {
    id: 'parquet-wallpaper',
    guildTitle: '۳. فروشگاه‌های پارکت لمینت، کاغذدیواری، ترمووود و ماربل‌شیت',
    whyMustBuy:
      'محاسبه آنی متراژ کف و دیوار به همراه فوم سایلنت و قرنیز + ساخت استوری‌های ۱۰۸۰×۱۹۲۰ اینستاگرامی با نام فروشگاه در ۱ کلیک.',
    visitorPitchTip: 'در ماشین‌حساب گزینه «پارکت لمینت AC5 و دیوارکوب ترمووود» را انتخاب و استوری‌ساز را اجرا کنید.',
    sampleBrandName: 'گالری پارکت و دکوراسیون آرتا | Arta Floor & Wall',
    sampleTagline: 'مرکز تخصصی پارکت لمینت AC5، دیوارپوش ترمووود و تی‌وی وال مدرن',
    priceMultiplier: 0.95,
  },
  {
    id: 'stone-slab-quartz',
    guildTitle: '۴. نمایشگاه‌های سنگ اسلب، کوارتز، کورین و سرامیک پرسلان',
    whyMustBuy:
      'ست کردن هوشمند رنگ سنگ کلکته و کوارتز با چوب گردو و کابینت برای معماران و سازندگان ساختمان + پیش‌فاکتور ۷ ارزی.',
    visitorPitchTip: 'بخش «معمار هوشمند ست‌کننده رنگ چوب و سنگ اسلب» را به صاحب نمایشگاه سنگ نشان دهید.',
    sampleBrandName: 'عمارت سنگ اسلب و کوارتز رویال | Royal Slab & Quartz',
    sampleTagline: 'تولید و اجرای تخصصی صفحات کوارتز، کورین و اسلب مرمر کلکته طلایی',
    priceMultiplier: 1.15,
  },
  {
    id: 'drywall-lighting',
    guildTitle: '۵. مجریان کناف (K+)، سقف کاذب، لاین نوری و برق ساختمان',
    whyMustBuy:
      'تبدیل استعلام‌های تلفنی به قرارداد قطعی با محاسبه دقیق مترمربع کناف، پنل ضدحریق و لاین نوری مگنتی ۳۰۰۰ کلوین.',
    visitorPitchTip: 'ردیف «کناف ایران و لاین نوری مگنتی» را در ماشین‌حساب به آن‌ها نشان دهید.',
    sampleBrandName: 'مهندسی کناف و نورپردازی مدرن | Modern Knauf & LED',
    sampleTagline: 'اجرای تخصصی سقف کاذب دکوراتیو، لاین نوری مخفی و نورپردازی هوشمند',
    priceMultiplier: 1.0,
  },
  {
    id: 'renovation-architects',
    guildTitle: '۶. دفاتر مهندسی معماری، پیمانکاران بازسازی و انبوه‌سازان',
    whyMustBuy:
      'ارائه پرستیژ بین‌المللی با کاتالوگ ۷ زبانه (فارسی، کوردی، ارمنی، عربی، انگلیسی، ترکی، روسی) و داشبورد هوش تجاری BI.',
    visitorPitchTip: 'این صنف بهترین خریدار پکیج‌های سطح ۳ تا ۵ (White-Label کامل) با بالاترین پورسانت ۲۵٪ برای ویزیتور است!',
    sampleBrandName: 'هلدینگ معماری و بازسازی عمارت | Emarat Architecture VIP',
    sampleTagline: 'طراحی سه‌بعدی و بازسازی کامل VIP ساختمان با مدیریت پیمان ۴۵ روزه',
    priceMultiplier: 1.2,
  },
  {
    id: 'kitchen-appliances',
    guildTitle: '۷. فروشگاه‌های هود، سینک، گاز صفحه‌ای، شیرآلات طلایی و فر توکار',
    whyMustBuy:
      'هر خریدار کابینت همزمان خریدار سینک، هود و شیرآلات است؛ این اپ مشتریان در حال بازسازی را مستقیماً به واتساپ فروشگاه وصل می‌کند.',
    visitorPitchTip: 'به فروشنده بگویید با داشتن این اپ، قبل از رقبا به مشتریانی که در حال متراژگیری آشپزخانه هستند می‌رسد.',
    sampleBrandName: 'کلینیک تجهیزات آشپزخانه و شیرآلات طلایی | Gold Kitchen Hub',
    sampleTagline: 'بورس سینک‌های گرانیتی، شیرآلات طلایی مات و تجهیزات توکار آشپزخانه لوکس',
    priceMultiplier: 1.0,
  },
  {
    id: 'furniture-wood',
    guildTitle: '۸. نمایشگاه‌های مبلمان، سرویس چوب، درب ضدسرقت و کلوزت‌روم',
    whyMustBuy:
      'محاسبه فوری قیمت کلوزت‌روم اشرافی و کمد ریلی + ساخت روزانه ده‌ها پوستر استوری استاندارد با کادر طلایی و شماره گالری.',
    visitorPitchTip: 'بخش «کلوزت‌روم اشرافی + جزیره جواهرات» را با نام گالری مبل آن‌ها شخصی‌سازی کنید.',
    sampleBrandName: 'گالری مبلمان و کلوزت‌روم ولیعصر | Valiasr Wood & Closet',
    sampleTagline: 'طراح و تولیدکننده کلوزت‌روم، سرویس چوب گردو و درب‌های تمام چوب',
    priceMultiplier: 1.1,
  },
];

