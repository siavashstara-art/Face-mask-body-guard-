import React, { useState, useEffect } from 'react';
import {
  ShieldCheck,
  Lock,
  Unlock,
  Share2,
  Copy,
  Check,
  TrendingUp,
  Award,
  CheckSquare,
  Square,
  Clock,
  Sparkles,
  FileText,
  Printer,
  MessageCircle,
  CreditCard,
  PlusCircle,
  Building2,
  Compass,
  Flame,
  Users,
  ChevronLeft,
  ExternalLink,
  AlertTriangle,
} from 'lucide-react';
import { CurrencyCode, LanguageCode, formatPrice } from '../data/decorData';
import { CustomBrandConfig } from './WhiteLabelAffiliateSection';

export interface TenantExtendedConfig extends CustomBrandConfig {
  slug: string;
  managerName: string;
  refCode: string;
  licenseMode: 'visitor_demo' | 'commercial_pro';
}

interface VipCommercialEnterpriseSuiteProps {
  tenantConfig: TenantExtendedConfig;
  onUpdateTenantConfig: (cfg: TenantExtendedConfig) => void;
  currency: CurrencyCode;
  lang: LanguageCode;
}

interface ShebaSaleRecord {
  id: string;
  dateFa: string;
  shopName: string;
  managerName: string;
  city: string;
  planName: string;
  totalLicenseToman: number;
  downPaymentCashToman: number;
  adPackageToman: number;
  visitor25CommissionToman: number;
  shebaNumber: string;
  status: 'تسویه آنی از پیش‌پرداخت نقد' | 'واریز شبا تایید شد';
}

const TEHRAN_AND_PROVINCES_STATS = [
  { region: 'تهران — شهرک صنعتی چهاردانگه و یافت‌آباد', activeUnits: '۸,۴۰۰+ کارگاه و نمایشگاه', potential25: 'بیش از ۲ میلیارد تومان پورسانت بالقوه' },
  { region: 'تهران — سهروردی، شریعتی، الهیه و فرشته', activeUnits: '۳,۲۰۰+ شوروم لوکس و شرکت معماری', potential25: 'بیشترین خریدار لایسنس VIP ۹۶ میلیونی' },
  { region: 'تهران — شهرک صنعتی کمرد، جاجرود و خاوران', activeUnits: '۵,۶۰۰+ کارخانه برش، CNC و کابینت‌سازی', potential25: 'خریدار قطعی سیستم مناقصه تایم خالی و ورق' },
  { region: 'کلان‌شهرها (مشهد، اصفهان، شیراز، تبریز، کرج، اهواز)', activeUnits: '۲۴,۰۰۰+ واحد صنفی فعال چوب و دکوراسیون', potential25: 'بازار بکر بدون رقیب برای سفیران استانی' },
  { region: 'غرب کشور، اقلیم کردستان (اربیل/سلیمانیه) و ایروان ارمنستان', activeUnits: '۶,۵۰۰+ نمایشگاه و شرکت بازسازی صادراتی', potential25: 'فروش دلاری/دیناری/درام با کاتالوگ ۷ زبانه' },
];

const FIVE_COMMON_OBJECTIONS = [
  {
    id: 'obj-1',
    objection: '۱. «الان بازار راکد است و مشتری کم شده، نمی‌خواهم هزینه اضافی کنم!»',
    answer:
      'جناب مدیر، اتفاقاً چون بازار رقابتی شده، مشتری به ۵ نمایشگاه سر می‌زند و از جایی می‌خرد که در ۱۰ ثانیه به او «پیش‌فاکتور شفاف + قسط چک صیادی + قفل ضدتورم ورق» بدهد! با این برنامه، فقط ۱ پروژه کابینت اضافه در کل سال بگیرید، ۵ برابر کل پول برنامه درجا برمی‌گردد و اگر در ماه اول فروشتان بیشتر نشد، چک‌هایتان عودت داده می‌شود.',
  },
  {
    id: 'obj-2',
    objection: '۲. «من خودم با ماشین‌حساب و کاغذ در ۵ دقیقه برای مشتری حساب می‌کنم!»',
    answer:
      'وقتی روی کاغذ می‌نویسید، مشتری حس می‌کند قیمت حدودی است و می‌رود با رقیب چانه می‌زند؛ اما وقتی در ۳۰ ثانیه یک پیش‌فاکتور رسمی طلاکوب با لوگوی خودتان، تاریخ دقیق سررسید چک‌های صیادی، تاییدیه رنگ چک و گواهی قفل ضدتورم ورق و یراق به واتساپش شلیک می‌کنید، مشتری شما را معتبرترین برند شهر می‌بیند.',
  },
  {
    id: 'obj-3',
    objection: '۳. «۴۸ میلیون یا ۹۶ میلیون تومان برای یک نرم‌افزار زیاد است!»',
    answer:
      'شما قرار نیست ۴۸ میلیون نقد بدهید! شما فقط ۱۲ میلیون تومان پیش‌پرداخت می‌دهید (کمتر از پول ۲ ورق هایگلاس!) و الباقی را ۲ فقره چک صیادی ۱۸ میلیونی می‌دهید. در عوض، ۸ ماژول کامل شامل استوری‌ساز اینستاگرام، رندر ۳۶۰ درجه، سیستم استعلام چک صیادی و اپ اختصاصی اندروید و آیفون به نام برند خودتان تحویل می‌گیرید.',
  },
  {
    id: 'obj-4',
    objection: '۴. «ما ادمین یا مهندس کامپیوتر نداریم که با این برنامه کار کند!»',
    answer:
      'این سامانه مخصوص مدیران پرمشغله طراحی شده است؛ کلیدهای هوش مصنوعی ۱۰۰٪ در سرور اتوماسیون شده‌اند، بدون اینترنت هم کار می‌کند و حتی حالت ساده ۳ مرحله‌ای و خوانش صوتی دارد. در کمتر از ۳۰ ثانیه با گوشی خودتان فاکتور و استوری می‌سازید.',
  },
  {
    id: 'obj-5',
    objection: '۵. «نکند قیمت‌های همکاری و اطلاعات مشتریان من را همکاران دیگر ببینند؟»',
    answer:
      'معماری این سامانه ۱۰۰٪ ایزوله (Isolated White-Label Tenant) است؛ اطلاعات، ضرایب قیمت کارگاه و آرشیو پیش‌فاکتورهای شما در فضای اختصاصی برند خودتان قفل می‌شود و هیچ همکاری به قیمت‌ها یا مشتریان شما دسترسی ندارد.',
  },
];

const DELIVERABLE_MODULES = [
  {
    id: 'del-1',
    title: '۱. اپلیکیشن اختصاصی ایزوله با نام، لوگو و دامنه نمایشگاه شما (Web + PWA + APK/AAB)',
    standaloneValueToman: 45000000,
    desc: 'تحویل نسخه کامل اندروید (قابل انتشار در کافه‌بازار، مایکت و گوگل‌پلی) و وب‌اپلیکیشن آیفون با نام برند و شماره واتساپ انحصاری شما بدون نام هیچ رقیبی.',
  },
  {
    id: 'del-2',
    title: '۲. موتور پیش‌فاکتور ۰.۱ ثانیه‌ای اتحادیه + تقسیط ۳ تا ۱۲ چک صیادی بنفش با تاریخ سررسید شمسی',
    standaloneValueToman: 28000000,
    desc: 'محاسبه آنی ۶۰٪ زمینی + ۴۰٪ هوایی، صفحه کوارتز، یراق بلوم، تعداد دقیق ورق MDF مصرفی و صدور جدول سررسید چک‌های صیادی.',
  },
  {
    id: 'del-3',
    title: '۳. سامانه استعلام زنده وضعیت رنگ چک صیادی (سفید/زرد/قرمز) + گواهی قفل ضدتورم ورق و یراق',
    standaloneValueToman: 22000000,
    desc: 'بررسی اعتبار چک مشتری سر میز قرارداد و صدور برگه «قفل ضمانت قیمت ورق و یراق در برابر تورم» برای جلب اعتماد ۱۰۰٪ کارفرما.',
  },
  {
    id: 'del-4',
    title: '۴. استودیو استوری‌ساز خودکار ۱۰۸۰×۱۹۲۰ طلاکوب اینستاگرام + کپشن‌نویس معمار هوشمند',
    standaloneValueToman: 18000000,
    desc: 'تبدیل هر نمونه کار کابینت و کمد در ۱ کلیک به پوستر استوری استاندارد با کادر طلای ۲۴ عیار، قیمت، شرایط چک صیادی و شماره تماس شما (جایگزین ادمین ۱۵ میلیونی ماهانه!).',
  },
  {
    id: 'del-5',
    title: '۵. استودیو رندر تعاملی ۳۶۰ درجه آشپزخانه و شبیه‌ساز زنده ست کردن چوب گردو، سنگ اسلب و لاین نوری',
    standaloneValueToman: 32000000,
    desc: 'نمایش زنده ترکیب رنگ کابینت هوایی، جزیره چوب گردو، سنگ کلکته و دمای نور ۳۰۰۰ کلوین قبل از برش ورق برای حذف تردید مشتری.',
  },
  {
    id: 'del-6',
    title: '۶. سامانه «مناقصه و حراج تایم‌های خالی کارگاه و دستگاه CNC» (Flash Capacity Monetizer)',
    standaloneValueToman: 25000000,
    desc: 'تبدیل روزهای خلوت کارگاه و ظرفیت خالی دستگاه برش/وکیوم به پول نقد از طریق اعلام اسلات‌های تخفیف‌دار فوری به سازندگان و همکاران.',
  },
  {
    id: 'del-7',
    title: '۷. کاتالوگ بین‌المللی ۷ زبانه (فارسی، کوردی، ارمنی، عربی، انگلیسی، ترکی، روسی) و ۷ ارزی زنده',
    standaloneValueToman: 24000000,
    desc: 'آماده جذب پروژه‌های صادراتی و ویلاسازی در ایران، اقلیم کردستان عراق (دینار)، ارمنستان (درام)، امارات (درهم) و ترکیه (لیر).',
  },
  {
    id: 'del-8',
    title: '۸. داشبورد هوش تجاری (BI)، اسلایدر تسهیم هزینه بین شرکا و دفترچه CRM آفلاین مشتریان',
    standaloneValueToman: 19000000,
    desc: 'تحلیل حاشیه سود کارگاه در برابر نمایشگاه، تقسیم شفاف فاکتور بین سازنده و مالک، و ذخیره آفلاین فاکتورها در ساختمان‌های بدون اینترنت.',
  },
];

export const VipCommercialEnterpriseSuite: React.FC<VipCommercialEnterpriseSuiteProps> = ({
  tenantConfig,
  onUpdateTenantConfig,
  currency,
  lang,
}) => {
  // 1. 10-Second Visitor Tablet Demo URL Builder state
  const [demoBusiness, setDemoBusiness] = useState(tenantConfig.brandName);
  const [demoManager, setDemoManager] = useState(tenantConfig.managerName);
  const [demoCity, setDemoCity] = useState(tenantConfig.city);
  const [demoPhone, setDemoPhone] = useState(tenantConfig.phone);
  const [demoTagline, setDemoTagline] = useState(tenantConfig.tagline);
  const [demoMultiplier, setDemoMultiplier] = useState<number>(tenantConfig.priceMultiplier || 1.0);
  const [demoRef, setDemoRef] = useState(tenantConfig.refCode);
  const [copiedDemoUrl, setCopiedDemoUrl] = useState(false);

  useEffect(() => {
    setDemoBusiness(tenantConfig.brandName);
    setDemoManager(tenantConfig.managerName);
    setDemoCity(tenantConfig.city);
    setDemoPhone(tenantConfig.phone);
    setDemoTagline(tenantConfig.tagline);
    setDemoMultiplier(tenantConfig.priceMultiplier || 1.0);
    setDemoRef(tenantConfig.refCode);
  }, [tenantConfig]);

  // 2. Specialized Guild Extras: 360° Kitchen View & Workshop Empty-Slot Tender
  const [panoAngle, setPanoAngle] = useState<number>(180);
  const [panoZone, setPanoZone] = useState<'island' | 'cooking' | 'tall_pantry' | 'coffee_bar'>('island');
  const [workshopSlots, setWorkshopSlots] = useState([
    {
      id: 'slot-1',
      title: 'ظرفیت خالی خط برش، نوار PVC هنکل و مونتاژ کابینت نئوکلاسیک (تحویل ۱۰ روزه)',
      days: 'شنبه تا چهارشنبه هفته آینده (ظرفیت ۲ دست آشپزخانه کامل)',
      normalFeeToman: 14500000,
      flashFeeToman: 12400000,
      discountPct: 15,
      reserved: false,
    },
    {
      id: 'slot-2',
      title: 'تایم خالی دستگاه CNC و رنگ پلی‌اورتان انزو ۲۵ میل (ویژه همکاران و سازندگان)',
      days: 'دوشنبه تا پنجشنبه (ظرفیت ۳۵ مترمربع درب و ستون)',
      normalFeeToman: 17800000,
      flashFeeToman: 14900000,
      discountPct: 16,
      reserved: false,
    },
    {
      id: 'slot-3',
      title: 'اسلات ویژه اجرای کمد دیواری ریلی و کلوزت‌روم (تیم نصب ارشد کارگاه)',
      days: 'آخر هفته جاری (ظرفیت ۲۰ مترمربع)',
      normalFeeToman: 9800000,
      flashFeeToman: 8300000,
      discountPct: 15,
      reserved: false,
    },
  ]);

  // 3. ROI Calculator State (#deliverables)
  const [extraMonthlyContracts, setExtraMonthlyContracts] = useState<number>(2);
  const [avgContractAmountToman, setAvgContractAmountToman] = useState<number>(160000000); // 160M Toman kitchen project
  const [netProfitMarginPct, setNetProfitMarginPct] = useState<number>(28); // 28% net margin
  const [selectedLicenseTierToman, setSelectedLicenseTierToman] = useState<number>(48000000); // 48M Base or 96M VIP

  const monthlyExtraNetProfitToman = Math.round(
    extraMonthlyContracts * avgContractAmountToman * (netProfitMarginPct / 100)
  );
  const annualExtraNetProfitToman = monthlyExtraNetProfitToman * 12;
  const roiMultiplier = Math.max(
    1,
    Math.round((annualExtraNetProfitToman / selectedLicenseTierToman) * 10) / 10
  );
  const paybackDays = Math.max(
    3,
    Math.round((selectedLicenseTierToman / Math.max(1, monthlyExtraNetProfitToman)) * 30)
  );

  // 4. Visitor 60-Second Doorstep Checklist (#visitor-playbook)
  const [checklist, setChecklist] = useState<Record<string, boolean>>({
    step1: true,
    step2: true,
    step3: false,
    step4: false,
    step5: false,
  });
  const [copiedObjectionId, setCopiedObjectionId] = useState<string | null>(null);

  // 5. Ambassador Sheba & Official Gold-Foil Invitation Letter (#invitation-letter)
  const [shebaNumber, setShebaNumber] = useState<string>('IR820540102680020817909002');
  const [saleShopName, setSaleShopName] = useState<string>('');
  const [saleManagerName, setSaleManagerName] = useState<string>('');
  const [salePlanType, setSalePlanType] = useState<'base48' | 'vip96'>('base48');
  const [saleAdPackageToman, setSaleAdPackageToman] = useState<number>(8000000);
  const [salesLedger, setSalesLedger] = useState<ShebaSaleRecord[]>(() => {
    try {
      const saved = localStorage.getItem('decormate_sheba_ledger_v1');
      if (saved) return JSON.parse(saved);
    } catch {}
    return [
      {
        id: 'sale-101',
        dateFa: '۱۴۰۵/۰۷/۰۴',
        shopName: 'گالری کابینت و چوب پاسارگاد',
        managerName: 'حاج مهدی رضایی',
        city: 'تهران - سهروردی شمالی',
        planName: 'لایسنس تجاری VIP (۹۶ میلیون تومان)',
        totalLicenseToman: 96000000,
        downPaymentCashToman: 24000000,
        adPackageToman: 12000000,
        visitor25CommissionToman: 27000000, // 24M (25% of 96M) + 3M (25% of 12M ad)
        shebaNumber: 'IR820540102680020817909002',
        status: 'تسویه آنی از پیش‌پرداخت نقد',
      },
      {
        id: 'sale-102',
        dateFa: '۱۴۰۵/۰۷/۰۵',
        shopName: 'صنایع چوب و کمد دیواری شاهکار',
        managerName: 'مهندس کامران احمدی',
        city: 'تهران - شهرک صنعتی چهاردانگه',
        planName: 'لایسنس تجاری پایه (۴۸ میلیون تومان)',
        totalLicenseToman: 48000000,
        downPaymentCashToman: 12000000,
        adPackageToman: 0,
        visitor25CommissionToman: 12000000, // 12M (25% of 48M settled immediately from 12M down payment!)
        shebaNumber: 'IR820540102680020817909002',
        status: 'واریز شبا تایید شد',
      },
    ];
  });

  const makeSlug = (text: string) =>
    text
      .trim()
      .toLowerCase()
      .replace(/\s+/g, '-')
      .replace(/[^a-z0-9\u0600-\u06FF-]/g, '') || 'default-shop';

  const generatedDemoUrl = `${
    typeof window !== 'undefined' ? window.location.origin + window.location.pathname : 'https://decormate.vip/'
  }?tenant=${encodeURIComponent(demoBusiness)}&manager=${encodeURIComponent(
    demoManager
  )}&city=${encodeURIComponent(demoCity)}&phone=${encodeURIComponent(
    demoPhone
  )}&tagline=${encodeURIComponent(demoTagline)}&multiplier=${encodeURIComponent(
    String(demoMultiplier)
  )}&ref=${encodeURIComponent(demoRef)}`;

  const handleApply10SecTabletSkin = (e: React.FormEvent) => {
    e.preventDefault();
    const slug = makeSlug(demoBusiness);
    const updated: TenantExtendedConfig = {
      ...tenantConfig,
      slug,
      brandName: demoBusiness || 'گالری کابینت و دکوراسیون شما',
      managerName: demoManager || 'جناب مدیر محترم',
      city: demoCity || 'تهران',
      phone: demoPhone || '09120000000',
      whatsapp: (demoPhone || '989120000000').replace(/\D/g, '').replace(/^0/, '98'),
      tagline: demoTagline || tenantConfig.tagline,
      priceMultiplier: Number(demoMultiplier) || 1.0,
      refCode: demoRef || 'VIP-101',
    };
    onUpdateTenantConfig(updated);

    if (typeof window !== 'undefined') {
      const params = new URLSearchParams(window.location.search);
      params.set('tenant', updated.brandName);
      params.set('manager', updated.managerName);
      params.set('city', updated.city);
      params.set('phone', updated.phone);
      params.set('tagline', updated.tagline);
      params.set('multiplier', String(updated.priceMultiplier));
      params.set('ref', updated.refCode);
      const newUrl = `${window.location.pathname}?${params.toString()}`;
      window.history.replaceState({}, '', newUrl);
    }
  };

  const handleCopyDemoLink = () => {
    navigator.clipboard?.writeText(generatedDemoUrl).catch(() => {});
    setCopiedDemoUrl(true);
    setTimeout(() => setCopiedDemoUrl(false), 2500);
  };

  const handleRecordNewLicenseSale = (e: React.FormEvent) => {
    e.preventDefault();
    const licensePrice = salePlanType === 'vip96' ? 96000000 : 48000000;
    const cashDown = salePlanType === 'vip96' ? 24000000 : 12000000;
    const commission25 = Math.round(licensePrice * 0.25 + saleAdPackageToman * 0.25);

    const newRecord: ShebaSaleRecord = {
      id: `sale-${Date.now()}`,
      dateFa: new Date().toLocaleDateString('fa-IR'),
      shopName: saleShopName.trim() || tenantConfig.brandName,
      managerName: saleManagerName.trim() || tenantConfig.managerName,
      city: tenantConfig.city,
      planName:
        salePlanType === 'vip96'
          ? 'لایسنس تجاری VIP (۹۶ میلیون تومان)'
          : 'لایسنس تجاری پایه (۴۸ میلیون تومان)',
      totalLicenseToman: licensePrice,
      downPaymentCashToman: cashDown,
      adPackageToman: saleAdPackageToman,
      visitor25CommissionToman: commission25,
      shebaNumber,
      status: 'تسویه آنی از پیش‌پرداخت نقد',
    };

    const nextLedger = [newRecord, ...salesLedger];
    setSalesLedger(nextLedger);
    setSaleShopName('');
    setSaleManagerName('');
    try {
      localStorage.setItem('decormate_sheba_ledger_v1', JSON.stringify(nextLedger));
    } catch {}
  };

  const handleSendInvitationToWhatsApp = () => {
    const cleanWa = tenantConfig.whatsapp.replace(/\D/g, '') || '989120000000';
    const text = `👑 *دعوت‌نامه رسمی طلاکوب VIP — سامانه هوشمند دکوراسیون و کابینت*
━━━━━━━━━━━━━━━━━━
جناب آقای / سرکار خانم *${tenantConfig.managerName}*
مدیریت محترم مجموعه *«${tenantConfig.brandName}»* (${tenantConfig.city})

با سلام و احترام؛
بدین‌وسیله نسخه پیش‌نمایش اختصاصی سامانه هوشمند محاسبه متراژ اتحادیه، تقسیط چک صیادی بنفش، قفل ضدتورم ورق و یراق و استوری‌ساز اینستاگرام که به صورت ایزوله با نام و برند مجموعه شما (*${tenantConfig.brandName}*) پیکربندی شده است، تقدیم حضور می‌گردد:

🔗 *لینک ورود مستقیم به سامانه اختصاصی شما:*
${generatedDemoUrl}

💎 *مزایای فعال‌سازی لایسنس تجاری مجموعه شما:*
✅ محاسبه ۰.۱ ثانیه‌ای قیمت کابینت، کمد دیواری، صفحه کوارتز و یراق بلوم سر میز مشتری
✅ صدور جدول سررسید چک صیادی بنفش + استعلام رنگ چک + گواهی قفل ضدتورم
✅ شرایط ویژه پرداخت بدون ریسک: فقط ۱۲ میلیون تومان پیش‌پرداخت + ۲ فقره چک صیادی ۱۸ میلیون تومانی (با ضمانت کتبی بازگشت چک‌ها در صورت عدم افزایش فروش در ماه اول!)

🎖️ کد سفیر و مشاور رسمی شما: *${tenantConfig.refCode}*`;

    window.location.href = `https://wa.me/${cleanWa}?text=${encodeURIComponent(text)}`;
  };

  const totalVisitorEarnedToman = salesLedger.reduce(
    (sum, item) => sum + item.visitor25CommissionToman,
    0
  );

  return (
    <div className="space-y-12">
      {/* =====================================================================
          PART 1: ISOLATED WHITE-LABEL TENANT SKIN & 4 SECURITY LOCKS BANNER
      ====================================================================== */}
      <section
        id="isolated-tenant-architecture"
        aria-label="Isolated White-Label Tenant Architecture and 10-Second Visitor Tablet Skin"
        className="px-4 sm:px-8 pt-4 max-w-[1440px] mx-auto"
      >
        <div className="hc-card rounded-3xl bg-gradient-to-l from-[#2A1A10] via-[#3D2516] to-[#24140B] text-white border-2 border-[#D4AF37] p-5 sm:p-7 shadow-xl space-y-5">
          {/* Active Tenant Status Bar */}
          <div className="flex flex-wrap items-center justify-between gap-3 border-b border-[#D4AF37]/30 pb-4">
            <div className="flex flex-wrap items-center gap-2.5">
              <span className="px-3 py-1 rounded-lg bg-[#D4AF37] text-[#2A1A10] text-xs font-extrabold font-mono-tabular">
                TENANT SLUG: {tenantConfig.slug}
              </span>
              <span className="text-xs sm:text-sm font-bold text-[#F6E27A]">
                🏛️ واحد صنفی فعال: «{tenantConfig.brandName}» | مدیریت: {tenantConfig.managerName} | شهر: {tenantConfig.city}
              </span>
              <span className="px-2.5 py-1 rounded-md bg-emerald-600/30 border border-emerald-400 text-emerald-200 text-[11px] font-mono-tabular">
                کد سفیر: ?ref={tenantConfig.refCode}
              </span>
            </div>

            <div className="flex items-center gap-2">
              <button
                type="button"
                onClick={() =>
                  onUpdateTenantConfig({
                    ...tenantConfig,
                    licenseMode:
                      tenantConfig.licenseMode === 'visitor_demo'
                        ? 'commercial_pro'
                        : 'visitor_demo',
                  })
                }
                className={`px-3.5 py-1.5 rounded-xl text-xs font-bold flex items-center gap-1.5 cursor-pointer border transition-colors ${
                  tenantConfig.licenseMode === 'commercial_pro'
                    ? 'bg-emerald-600 text-white border-emerald-400'
                    : 'bg-amber-500/20 text-[#F6E27A] border-[#D4AF37]'
                }`}
              >
                {tenantConfig.licenseMode === 'commercial_pro' ? (
                  <>
                    <Unlock className="w-3.5 h-3.5" />
                    <span>وضعیت: لایسنس تجاری دائمی (خالص و بدون واترمارک)</span>
                  </>
                ) : (
                  <>
                    <Lock className="w-3.5 h-3.5" />
                    <span>وضعیت: نسخه دموی موقت تبلت ویزیتور (کلیک برای مقایسه با نسخه دائمی)</span>
                  </>
                )}
              </button>
            </div>
          </div>

          {/* 10-Second Visitor URL Skin Form */}
          <form onSubmit={handleApply10SecTabletSkin} className="space-y-3">
            <div className="text-xs font-bold text-[#F6E27A] flex items-center justify-between flex-wrap gap-2">
              <span>
                ⚡ نوار پیش‌نمایش ۱۰ ثانیه‌ای روی تبلت ویزیتور (Isolated White-Label URL Skin) — ذخیره ایزوله در localStorage بر اساس شناسه (Slug):
              </span>
              <span className="text-[11px] text-emerald-300 font-mono-tabular" dir="ltr">
                ?tenant=...&manager=...&city=...&phone=...&ref=...
              </span>
            </div>

            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-2.5">
              <input
                type="text"
                value={demoBusiness}
                onChange={(e) => setDemoBusiness(e.target.value)}
                placeholder="نام کسب‌وکار (مثلاً: کابینت شاهکار)"
                className="px-3 py-2.5 rounded-xl bg-white/10 border border-[#D4AF37]/50 text-white placeholder-white/50 text-xs font-bold focus:outline-none focus:border-[#F6E27A]"
              />
              <input
                type="text"
                value={demoManager}
                onChange={(e) => setDemoManager(e.target.value)}
                placeholder="نام مدیر (مثلاً: حاج علی رضایی)"
                className="px-3 py-2.5 rounded-xl bg-white/10 border border-[#D4AF37]/50 text-white placeholder-white/50 text-xs font-bold focus:outline-none focus:border-[#F6E27A]"
              />
              <input
                type="text"
                value={demoCity}
                onChange={(e) => setDemoCity(e.target.value)}
                placeholder="شهر / راسته (مثلاً: تهران - یافت‌آباد)"
                className="px-3 py-2.5 rounded-xl bg-white/10 border border-[#D4AF37]/50 text-white placeholder-white/50 text-xs focus:outline-none focus:border-[#F6E27A]"
              />
              <input
                type="text"
                dir="ltr"
                value={demoPhone}
                onChange={(e) => setDemoPhone(e.target.value)}
                placeholder="شماره واتساپ: 0912..."
                className="px-3 py-2.5 rounded-xl bg-white/10 border border-[#D4AF37]/50 text-white placeholder-white/50 text-xs font-mono-tabular focus:outline-none focus:border-[#F6E27A]"
              />
              <input
                type="text"
                value={demoTagline}
                onChange={(e) => setDemoTagline(e.target.value)}
                placeholder="شعار برند (مثلاً: مجری تخصصی کابینت نئوکلاسیک)"
                className="px-3 py-2.5 rounded-xl bg-white/10 border border-[#D4AF37]/50 text-white placeholder-white/50 text-xs focus:outline-none focus:border-[#F6E27A]"
              />
              <select
                value={demoMultiplier}
                onChange={(e) => setDemoMultiplier(Number(e.target.value))}
                aria-label="ضریب قیمت واحد صنفی"
                className="px-3 py-2.5 rounded-xl bg-[#2A1A10] border border-[#D4AF37]/50 text-[#F6E27A] text-xs font-bold font-mono-tabular focus:outline-none focus:border-[#F6E27A]"
              >
                <option value={0.9}>ضریب قیمت: ۰.۹ (۱۰٪ تخفیف کارگاهی)</option>
                <option value={0.95}>ضریب قیمت: ۰.۹۵ (۵٪ تخفیف همکاری)</option>
                <option value={1.0}>ضریب قیمت: ۱.۰ (نرخ استاندارد اتحادیه)</option>
                <option value={1.08}>ضریب قیمت: ۱.۰۸ (+۸٪ سفارشی لوکس)</option>
                <option value={1.15}>ضریب قیمت: ۱.۱۵ (+۱۵٪ سوپرلوکس)</option>
              </select>
              <input
                type="text"
                dir="ltr"
                value={demoRef}
                onChange={(e) => setDemoRef(e.target.value)}
                placeholder="کد سفیر: VIP-101"
                className="px-3 py-2.5 rounded-xl bg-white/10 border border-[#D4AF37]/50 text-[#F6E27A] placeholder-white/50 text-xs font-mono-tabular font-bold focus:outline-none focus:border-[#F6E27A]"
              />
              <div className="flex gap-1.5">
                <button
                  type="submit"
                  className="flex-1 py-2.5 px-3 rounded-xl bg-[#D4AF37] hover:bg-[#F6E27A] text-[#2A1A10] font-extrabold text-xs cursor-pointer whitespace-nowrap"
                >
                  اعمال آنی ۱۰ ثانیه‌ای
                </button>
                <button
                  type="button"
                  onClick={handleCopyDemoLink}
                  title="کپی لینک دموی اختصاصی با پارامترهای URL"
                  className="py-2.5 px-3 rounded-xl bg-emerald-600 hover:bg-emerald-500 text-white font-bold text-xs flex items-center gap-1 cursor-pointer"
                >
                  {copiedDemoUrl ? <Check className="w-4 h-4" /> : <Copy className="w-4 h-4" />}
                </button>
              </div>
            </div>
          </form>

          {/* 4 Security Locks Explaining Difference Between Temporary Visitor Demo vs. Permanent Commercial License */}
          <div className="pt-2">
            <div className="text-xs font-bold text-[#F6E27A] mb-2.5 flex items-center gap-1.5">
              <ShieldCheck className="w-4 h-4 text-[#D4AF37]" />
              <span>🔒 ۴ قفل امنیتی تفاوت «نسخه دموی موقت تبلت ویزیتور» با «لایسنس تجاری دائمی واحد صنفی»:</span>
            </div>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-3 text-xs">
              <div className="p-3.5 rounded-2xl bg-white/10 border border-[#D4AF37]/40 space-y-1">
                <div className="font-extrabold text-[#F6E27A]">۱. قفل واترمارک و بنر فروش ویزیتوری</div>
                <p className="text-white/85 text-[11px] leading-relaxed">
                  در نسخه دموی موقت، واترمارک «پیش‌نمایش دموی سفیر فروش» و بخش‌های آموزش ویزیتور نمایش داده می‌شود؛ پس از خرید لایسنس، نسخه ۱۰۰٪ خالص فقط برای مشتریان شما باز می‌شود.
                </p>
              </div>
              <div className="p-3.5 rounded-2xl bg-white/10 border border-[#D4AF37]/40 space-y-1">
                <div className="font-extrabold text-[#F6E27A]">۲. قفل نرخ‌های پایه سرور و پنل ادمین</div>
                <p className="text-white/85 text-[11px] leading-relaxed">
                  در نسخه دمو، نرخ‌های پایه ورق و یراق روی سرور مرکزی قفل است؛ در لایسنس تجاری دائمی، پنل مدیریت اختصاصی قیمت کارگاه، لوگو و دامنه انحصاری تحویل مدیر می‌شود.
                </p>
              </div>
              <div className="p-3.5 rounded-2xl bg-white/10 border border-[#D4AF37]/40 space-y-1">
                <div className="font-extrabold text-[#F6E27A]">۳. قفل کد سفیر (?ref={tenantConfig.refCode})</div>
                <p className="text-white/85 text-[11px] leading-relaxed">
                  کد معرف ویزیتور در لینک دمو و پیش‌فاکتورها حک شده تا ۲۵٪ پورسانت شبا (۱۲ تا ۲۴ میلیون تومان) به صورت تضمینی به نام ویزیتور ثبت و قفل شود.
                </p>
              </div>
              <div className="p-3.5 rounded-2xl bg-white/10 border border-[#D4AF37]/40 space-y-1">
                <div className="font-extrabold text-[#F6E27A]">۴. ایزولاسیون کامل اطلاعات (Zero-Leak)</div>
                <p className="text-white/85 text-[11px] leading-relaxed">
                  اطلاعات هر واحد صنفی بر اساس شناسه (`decormate_tenant_{tenantConfig.slug}`) ایزوله شده و هیچ نمایشگاه یا کارگاهی به قیمت‌ها یا مشتریان رقیب دسترسی ندارد.
                </p>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* =====================================================================
          PART 2: SPECIALIZED CABINET & DECORATION EXTRAS:
          A) 360° Interactive Kitchen Studio  B) Flash Workshop Empty-Slot Tender
      ====================================================================== */}
      <section
        id="cabinet-guild-specials"
        aria-label="360 Degree Kitchen Render Studio and Workshop Flash Capacity Tender"
        className="px-4 sm:px-8 max-w-[1440px] mx-auto"
      >
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-6">
          {/* A) 360° Interactive Kitchen & Island Render Viewer */}
          <div className="lg:col-span-6 hc-card bg-white border-2 border-[#E6DEC8] rounded-3xl p-6 space-y-4">
            <div className="flex items-center justify-between border-b border-[#E6DEC8] pb-3">
              <div>
                <span className="text-xs font-bold text-emerald-700">
                  ویژگی تخصصی صنف کابینت و دکوراسیون · نمایش به کارفرما سر میز قرارداد
                </span>
                <h3 className="text-lg font-extrabold text-[#4A2E1B] flex items-center gap-2">
                  <Compass className="w-5 h-5 text-[#D4AF37]" />
                  <span>🔄 استودیو رندر ۳۶۰ درجه تعاملی آشپزخانه و کلوزت‌روم ({tenantConfig.brandName})</span>
                </h3>
              </div>
              <span className="px-2.5 py-1 rounded-lg bg-[#4A2E1B] text-[#F6E27A] font-mono-tabular text-xs font-bold">
                زاویه دید: {panoAngle}°
              </span>
            </div>

            {/* Interactive 360 Viewport Canvas */}
            <div className="relative h-64 rounded-2xl overflow-hidden border-2 border-[#D4AF37] bg-[#1E1109]">
              <img
                src={
                  panoZone === 'island'
                    ? 'https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=1200&q=80'
                    : panoZone === 'cooking'
                    ? 'https://images.unsplash.com/photo-1556911220-e15b29be8c8f?auto=format&fit=crop&w=1200&q=80'
                    : panoZone === 'tall_pantry'
                    ? 'https://images.unsplash.com/photo-1556909212-d5b604d0c90d?auto=format&fit=crop&w=1200&q=80'
                    : 'https://images.unsplash.com/photo-1618221195710-dd6b41faaea6?auto=format&fit=crop&w=1200&q=80'
                }
                alt="360 Kitchen Panorama"
                referrerPolicy="no-referrer"
                className="w-full h-full object-cover transition-transform duration-300"
                style={{
                  transform: `scale(1.18) translateX(${((panoAngle - 180) / 180) * 14}%)`,
                }}
              />
              <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent" />

              {/* Interactive Hotspots */}
              <div className="absolute top-3 right-3 left-3 flex flex-wrap justify-between gap-2">
                <span className="px-3 py-1 rounded-lg bg-black/75 text-[#F6E27A] border border-[#D4AF37]/50 text-xs font-bold">
                  📍 نقطه کانونی:{' '}
                  {panoZone === 'island'
                    ? 'جزیره اسلب مرمر کلکته + چوب گردو'
                    : panoZone === 'cooking'
                    ? 'دیوار پخت‌وپز + صفحه کوارتز ۵ سانتی ضدحرارت'
                    : panoZone === 'tall_pantry'
                    ? 'کابینت ایستاده سوپرمارکت بلوم + فر توکار'
                    : 'بار قهوه (Coffee Bar) + ویترین شیشه‌ای شامپاینی ۳۰۰۰ کلوین'}
                </span>
              </div>

              <div className="absolute bottom-3 right-3 left-3 space-y-2">
                <input
                  type="range"
                  min={0}
                  max={360}
                  value={panoAngle}
                  onChange={(e) => setPanoAngle(Number(e.target.value))}
                  aria-label="چرخش ۳۶۰ درجه نمای آشپزخانه"
                  className="w-full accent-[#D4AF37] cursor-pointer"
                />
                <div className="flex justify-between text-[11px] text-white/90 font-mono-tabular">
                  <span>0° (ضلع شرقی)</span>
                  <span>180° (نمای مرکزی جزیره)</span>
                  <span>360° (ضلع غربی و کلوزت)</span>
                </div>
              </div>
            </div>

            <div className="grid grid-cols-2 sm:grid-cols-4 gap-2">
              {[
                { id: 'island', label: '۱. نمای جزیره اسلب' },
                { id: 'cooking', label: '۲. ضلع پخت و هود مخفی' },
                { id: 'tall_pantry', label: '۳. سوپرمارکت و قدی' },
                { id: 'coffee_bar', label: '۴. کافی‌بار و کلوزت' },
              ].map((z) => (
                <button
                  key={z.id}
                  type="button"
                  onClick={() => setPanoZone(z.id as any)}
                  className={`py-2 px-2.5 rounded-xl text-xs font-bold border cursor-pointer ${
                    panoZone === z.id
                      ? 'bg-[#4A2E1B] text-[#F6E27A] border-[#D4AF37]'
                      : 'bg-[#FAF7F2] text-[#4A2E1B] border-[#E6DEC8]'
                  }`}
                >
                  {z.label}
                </button>
              ))}
            </div>
          </div>

          {/* B) Workshop Empty-Slot Tender / Flash Capacity Monetizer */}
          <div className="lg:col-span-6 hc-card bg-white border-2 border-[#E6DEC8] rounded-3xl p-6 space-y-4">
            <div className="flex items-center justify-between border-b border-[#E6DEC8] pb-3">
              <div>
                <span className="text-xs font-bold text-amber-700">
                  تبدیل روزهای خلوت کارگاه و دستگاه CNC به سود نقد (Flash Capacity Tender)
                </span>
                <h3 className="text-lg font-extrabold text-[#4A2E1B] flex items-center gap-2">
                  <Flame className="w-5 h-5 text-amber-600" />
                  <span>⚡ مناقصه و حراج تایم‌های خالی کارگاه و خط تولید ({tenantConfig.brandName})</span>
                </h3>
              </div>
            </div>

            <p className="text-xs text-[#6F4E37] leading-relaxed">
              وقتی خط برش، دستگاه CNC یا تیم نصب کارگاه در میانه هفته ظرفیت خالی دارد، به جای خواب سرمایه و حقوق کارگر، اسلات‌های خالی را با **۱۵٪ تخفیف تکمیل ظرفیت** برای سازندگان و مشتریان به مزایده فوری بگذارید:
            </p>

            <div className="space-y-3">
              {workshopSlots.map((slot) => (
                <div
                  key={slot.id}
                  className="p-3.5 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8] flex flex-col sm:flex-row sm:items-center justify-between gap-3"
                >
                  <div className="space-y-1">
                    <div className="text-xs font-extrabold text-[#4A2E1B]">{slot.title}</div>
                    <div className="text-[11px] text-[#6F4E37] flex items-center gap-1.5">
                      <Clock className="w-3.5 h-3.5 text-[#D4AF37]" />
                      <span>{slot.days}</span>
                    </div>
                    <div className="flex items-center gap-3 text-xs font-mono-tabular">
                      <span className="line-through text-[#6F4E37]">
                        {formatPrice(slot.normalFeeToman, currency, lang)}
                      </span>
                      <strong className="text-emerald-700">
                        نرخ مناقصه تایم خالی: {formatPrice(slot.flashFeeToman, currency, lang)} / متر (-{slot.discountPct}%)
                      </strong>
                    </div>
                  </div>

                  <button
                    type="button"
                    onClick={() => {
                      setWorkshopSlots((prev) =>
                        prev.map((s) => (s.id === slot.id ? { ...s, reserved: !s.reserved } : s))
                      );
                    }}
                    className={`px-3.5 py-2.5 rounded-xl font-bold text-xs whitespace-nowrap cursor-pointer ${
                      slot.reserved
                        ? 'bg-emerald-600 text-white'
                        : 'bg-[#4A2E1B] hover:bg-[#352012] text-[#F6E27A]'
                    }`}
                  >
                    {slot.reserved ? '✓ رزرو شد (ارسال به واتساپ)' : 'رزرو فوری این تایم خالی'}
                  </button>
                </div>
              ))}
            </div>
          </div>
        </div>
      </section>

      {/* =====================================================================
          PART 3: #deliverables — WHAT SHOP OWNERS GET + LIVE ROI CALCULATOR
      ====================================================================== */}
      <section
        id="deliverables"
        aria-label="What Cabinet and Decoration Business Owners Receive and ROI Calculator"
        className="px-4 sm:px-8 max-w-[1440px] mx-auto scroll-mt-20"
      >
        <div className="hc-card bg-white border-2 border-[#D4AF37] rounded-3xl p-6 sm:p-8 space-y-8">
          <div className="border-b border-[#E6DEC8] pb-5">
            <div className="text-xs font-bold text-emerald-700 mb-1">
              شفافیت ۱۰۰٪ دستاوردهای خرید لایسنس تجاری · ویژه صاحبان نمایشگاه کابینت، کمد دیواری، دکوراسیون و بازسازی
            </div>
            <h2 className="text-2xl sm:text-3xl font-extrabold text-[#4A2E1B] flex items-center gap-2.5">
              <Award className="w-7 h-7 text-[#D4AF37] shrink-0" />
              <span>🎁 صاحبان این صنف در صورت خرید برنامه دقیقاً چه چیزی به دست می‌آورند؟ (#deliverables)</span>
            </h2>
          </div>

          {/* Interactive ROI Calculator */}
          <div className="p-6 rounded-2xl bg-[#FAF7F2] border-2 border-emerald-500/50 grid grid-cols-1 lg:grid-cols-12 gap-6 items-center">
            <div className="lg:col-span-7 space-y-4">
              <h3 className="text-base sm:text-lg font-extrabold text-[#4A2E1B] flex items-center gap-2">
                <TrendingUp className="w-5 h-5 text-emerald-600" />
                <span>📈 ماشین‌حساب زنده بازگشت سرمایه (ROI Calculator) ویژه مدیر نمایشگاه / کارگاه:</span>
              </h3>
              <p className="text-xs text-[#6F4E37] leading-relaxed">
                با اسلایدرهای زیر ببینید که **سود خالص فقط ۱ تا ۲ قرارداد کابینت اضافه در ماه** (که به لطف پیش‌فاکتور ۱۰ ثانیه‌ای طلاکوب، استوری‌ساز و جدول چک صیادی جذب می‌شوند)، **بیش از ۳ تا ۱۰ برابر کل هزینه لایسنس یک‌ساله برنامه** است!
              </p>

              <div className="space-y-3 text-xs">
                <div>
                  <div className="flex justify-between font-bold text-[#4A2E1B] mb-1">
                    <span>۱. تعداد قرارداد کابینت / کمد اضافه در ماه به کمک این سامانه:</span>
                    <span className="font-mono-tabular text-emerald-700">
                      {extraMonthlyContracts} قرارداد اضافه در ماه
                    </span>
                  </div>
                  <input
                    type="range"
                    min={1}
                    max={10}
                    value={extraMonthlyContracts}
                    onChange={(e) => setExtraMonthlyContracts(Number(e.target.value))}
                    className="w-full accent-[#4A2E1B] cursor-pointer"
                  />
                </div>

                <div>
                  <div className="flex justify-between font-bold text-[#4A2E1B] mb-1">
                    <span>۲. میانگین مبلغ هر قرارداد کابینت و دکوراسیون:</span>
                    <span className="font-mono-tabular text-[#4A2E1B]">
                      {formatPrice(avgContractAmountToman, currency, lang)}
                    </span>
                  </div>
                  <input
                    type="range"
                    min={60000000}
                    max={450000000}
                    step={10000000}
                    value={avgContractAmountToman}
                    onChange={(e) => setAvgContractAmountToman(Number(e.target.value))}
                    className="w-full accent-[#4A2E1B] cursor-pointer"
                  />
                </div>

                <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                  <div>
                    <div className="flex justify-between font-bold text-[#4A2E1B] mb-1">
                      <span>۳. حاشیه سود خالص کارگاه/نمایشگاه:</span>
                      <span className="font-mono-tabular text-emerald-700">{netProfitMarginPct}%</span>
                    </div>
                    <input
                      type="range"
                      min={15}
                      max={45}
                      value={netProfitMarginPct}
                      onChange={(e) => setNetProfitMarginPct(Number(e.target.value))}
                      className="w-full accent-[#4A2E1B] cursor-pointer"
                    />
                  </div>

                  <div>
                    <label className="block font-bold text-[#4A2E1B] mb-1">نوع لایسنس انتخابی:</label>
                    <div className="flex gap-2">
                      <button
                        type="button"
                        onClick={() => setSelectedLicenseTierToman(48000000)}
                        className={`flex-1 py-2 rounded-xl font-bold text-xs border cursor-pointer ${
                          selectedLicenseTierToman === 48000000
                            ? 'bg-[#4A2E1B] text-[#F6E27A] border-[#D4AF37]'
                            : 'bg-white text-[#4A2E1B] border-[#E6DEC8]'
                        }`}
                      >
                        پایه (۴۸ میلیون)
                      </button>
                      <button
                        type="button"
                        onClick={() => setSelectedLicenseTierToman(96000000)}
                        className={`flex-1 py-2 rounded-xl font-bold text-xs border cursor-pointer ${
                          selectedLicenseTierToman === 96000000
                            ? 'bg-[#4A2E1B] text-[#F6E27A] border-[#D4AF37]'
                            : 'bg-white text-[#4A2E1B] border-[#E6DEC8]'
                        }`}
                      >
                        VIP کامل (۹۶ میلیون)
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div className="lg:col-span-5 p-5 rounded-2xl bg-[#4A2E1B] text-white border-2 border-[#D4AF37] space-y-3">
              <div className="text-xs text-[#F6E27A] font-bold">
                نتیجه سودآوری خالص برای «{tenantConfig.brandName}»:
              </div>
              <div className="p-3 rounded-xl bg-white/10 flex justify-between items-center text-xs">
                <span>سود خالص اضافه در هر ماه:</span>
                <strong className="font-mono-tabular text-base text-emerald-300">
                  {formatPrice(monthlyExtraNetProfitToman, currency, lang)}
                </strong>
              </div>
              <div className="p-3 rounded-xl bg-white/10 flex justify-between items-center text-xs">
                <span>سود خالص اضافه در یک سال:</span>
                <strong className="font-mono-tabular text-lg text-[#F6E27A]">
                  {formatPrice(annualExtraNetProfitToman, currency, lang)}
                </strong>
              </div>
              <div className="grid grid-cols-2 gap-2 pt-1">
                <div className="p-3 rounded-xl bg-emerald-600/30 border border-emerald-400 text-center">
                  <div className="text-[11px] text-emerald-200">ضریب بازگشت سرمایه سالانه</div>
                  <div className="text-xl font-extrabold font-mono-tabular text-white">
                    {roiMultiplier} برابر (x)
                  </div>
                </div>
                <div className="p-3 rounded-xl bg-amber-500/25 border border-[#D4AF37] text-center">
                  <div className="text-[11px] text-[#F6E27A]">زمان تسویه کل پول برنامه</div>
                  <div className="text-xl font-extrabold font-mono-tabular text-white">
                    فقط {paybackDays} روز!
                  </div>
                </div>
              </div>
            </div>
          </div>

          {/* 8 Tangible Deliverable Cards with Rial/Toman Values */}
          <div>
            <h3 className="text-lg font-extrabold text-[#4A2E1B] mb-4">
              📦 ۸ دستاورد و ماژول ملموس تحویلی به خریدار برنامه (به ارزش واقعی بیش از ۲۱۳ میلیون تومان در صورت سفارش جداگانه):
            </h3>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
              {DELIVERABLE_MODULES.map((mod) => (
                <div
                  key={mod.id}
                  className="p-4 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8] flex flex-col justify-between gap-3"
                >
                  <div className="space-y-2">
                    <div className="text-xs font-extrabold text-[#4A2E1B] leading-snug">
                      {mod.title}
                    </div>
                    <p className="text-[11px] text-[#6F4E37] leading-relaxed">{mod.desc}</p>
                  </div>
                  <div className="pt-2 border-t border-[#E6DEC8] flex items-center justify-between text-xs">
                    <span className="text-[#6F4E37]">ارزش مستقل ماژول:</span>
                    <strong className="font-mono-tabular text-emerald-700">
                      {formatPrice(mod.standaloneValueToman, currency, lang)}
                    </strong>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Comparison Table: Traditional vs. Smart Business */}
          <div className="overflow-x-auto">
            <h3 className="text-lg font-extrabold text-[#4A2E1B] mb-3">
              ⚖️ جدول مقایسه «کابینت‌سازی و نمایشگاه سنتی (با کاغذ و ماشین‌حساب)» در برابر «واحد صنفی مجهز به سامانه هوشمند شما»:
            </h3>
            <table className="w-full text-right border-collapse rounded-2xl overflow-hidden text-xs">
              <thead>
                <tr className="bg-[#4A2E1B] text-[#F6E27A]">
                  <th className="p-3.5 border border-[#D4AF37]/30">شاخص عملکرد فروش و مدیریت</th>
                  <th className="p-3.5 border border-[#D4AF37]/30">❌ روش سنتی (دفترچه، کاغذ و ماشین‌حساب دستی)</th>
                  <th className="p-3.5 border border-[#D4AF37]/30">✅ مجهز به سامانه هوشمند «{tenantConfig.brandName}»</th>
                </tr>
              </thead>
              <tbody className="bg-[#FAF7F2]">
                <tr className="border-b border-[#E6DEC8]">
                  <td className="p-3 font-bold text-[#4A2E1B]">۱. زمان اعلام قیمت و پیش‌فاکتور سر میز</td>
                  <td className="p-3 text-red-800">۱۵ تا ۳۰ دقیقه حساب‌وکتاب دستی، خطای محاسباتی و تردید مشتری</td>
                  <td className="p-3 text-emerald-800 font-bold">۰.۱ ثانیه! صدور پیش‌فاکتور رسمی ۶۰/۴۰ اتحادیه با تفکیک ورق و یراق بلوم</td>
                </tr>
                <tr className="border-b border-[#E6DEC8] bg-white">
                  <td className="p-3 font-bold text-[#4A2E1B]">۲. فروش اقساطی و اطمینان از چک مشتری</td>
                  <td className="p-3 text-red-800">ریسک چک برگشتی، اشتباه در تاریخ سررسید و ترس از نوسان قیمت ورق</td>
                  <td className="p-3 text-emerald-800 font-bold">تولید جدول سررسید شمسی ۳ تا ۱۲ چک صیادی بنفش + استعلام رنگ چک + قفل ضدتورم</td>
                </tr>
                <tr className="border-b border-[#E6DEC8]">
                  <td className="p-3 font-bold text-[#4A2E1B]">۳. خروجی به مشتری هنگام خروج از نمایشگاه</td>
                  <td className="p-3 text-red-800">یک تکه کاغذ بی‌نام‌ونشان که مشتری آن را گم می‌کند</td>
                  <td className="p-3 text-emerald-800 font-bold">شلیک آنی پیش‌فاکتور طلاکوب با نام و لوگوی شما به واتساپ مشتری و مدیر</td>
                </tr>
                <tr className="border-b border-[#E6DEC8] bg-white">
                  <td className="p-3 font-bold text-[#4A2E1B]">۴. تولید محتوا و استوری اینستاگرام</td>
                  <td className="p-3 text-red-800">نیاز به ادمین ۱۵ تا ۲۰ میلیون تومانی در ماه یا پیج راکد</td>
                  <td className="p-3 text-emerald-800 font-bold">تولید ۱-کلیکی پوستر استوری ۱۰۸۰×۱۹۲۰ طلاکوب با کپشن آماده در ۱۰ ثانیه</td>
                </tr>
                <tr>
                  <td className="p-3 font-bold text-[#4A2E1B]">۵. روزهای خلوت کارگاه و دستگاه برش/CNC</td>
                  <td className="p-3 text-red-800">خواب سرمایه، پرداخت حقوق ثابت کارگر و اجاره بدون ورودی</td>
                  <td className="p-3 text-emerald-800 font-bold">سیستم مناقصه تایم‌های خالی کارگاه (Flash Capacity) و تبدیل ظرفیت خالی به پول نقد</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </section>

      {/* =====================================================================
          PART 4: #visitor-playbook & #golden-formula — VISITOR PLAYBOOK & 3 GOLDEN RULES
      ====================================================================== */}
      <section
        id="visitor-playbook"
        aria-label="Visitor Sales Playbook and 3-Part Golden Sales Formula"
        className="px-4 sm:px-8 max-w-[1440px] mx-auto scroll-mt-20"
      >
        <div className="hc-card bg-white border-2 border-[#4A2E1B] rounded-3xl p-6 sm:p-8 space-y-8">
          <div className="border-b border-[#E6DEC8] pb-5">
            <div className="text-xs font-bold text-emerald-700 mb-1">
              کتابچه راهنمای عملی سفیران فروش · تضمین درآمد هفتگی از هفته اول کاری
            </div>
            <h2 className="text-2xl sm:text-3xl font-extrabold text-[#4A2E1B]">
              🧭 راهکار و راهنمای عملی ویزیتورها و بازاریابان + آموزش فرمول طلایی فروش (#visitor-playbook)
            </h2>
          </div>

          {/* 60-Second Doorstep Checklist Before Entering Manager's Office */}
          <div className="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
            <div className="lg:col-span-6 p-5 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8] space-y-3">
              <h3 className="text-base font-extrabold text-[#4A2E1B] flex items-center gap-2">
                <CheckSquare className="w-5 h-5 text-emerald-600" />
                <span>⏱️ چک‌لیست ۶۰ ثانیه‌ای ویزیتور پشت درِ واحد صنفی (قبل از ورود به دفتر مدیریت):</span>
              </h3>
              <div className="space-y-2 text-xs">
                {[
                  {
                    key: 'step1',
                    text: '۱. نگاه به تابلوی سردرِ نمایشگاه/کارگاه و وارد کردن نام دقیق برند و نام مدیر در نوار بالای تبلت (۱۰ ثانیه).',
                  },
                  {
                    key: 'step2',
                    text: '۲. وارد کردن شماره موبایل یا واتساپ روی تابلوی مغازه در فیلد تلفن تا دکمه واتساپ روی خط خودِ مدیر تنظیم شود.',
                  },
                  {
                    key: 'step3',
                    text: '۳. تنظیم روشنایی صفحه تبلت/گوشی روی ۱۰۰٪ و باز گذاشتن صفحه روی «ماشین‌حساب متراژ و چک صیادی بنفش».',
                  },
                  {
                    key: 'step4',
                    text: '۴. بررسی درج شدن کد سفیر خودتان (?ref=' + tenantConfig.refCode + ') در نوار بالا جهت قفل شدن ۲۵٪ پورسانت شبا.',
                  },
                  {
                    key: 'step5',
                    text: '۵. ورود با لبخند و جمله کلیدی: «جناب مدیر، سامانه اختصاصی پیش‌فاکتور اتحادیه و چک صیادی را به نام برند خودتان آماده کرده‌ام تا در ۳۰ ثانیه روی گوشی خودتان تست کنید!»',
                  },
                ].map((item) => (
                  <button
                    key={item.key}
                    type="button"
                    onClick={() =>
                      setChecklist((prev) => ({ ...prev, [item.key]: !prev[item.key] }))
                    }
                    className="w-full text-right p-3 rounded-xl bg-white border border-[#E6DEC8] flex items-start gap-2.5 cursor-pointer"
                  >
                    {checklist[item.key] ? (
                      <CheckSquare className="w-4 h-4 text-emerald-600 shrink-0 mt-0.5" />
                    ) : (
                      <Square className="w-4 h-4 text-[#6F4E37] shrink-0 mt-0.5" />
                    )}
                    <span className={checklist[item.key] ? 'font-bold text-[#4A2E1B]' : 'text-[#6F4E37]'}>
                      {item.text}
                    </span>
                  </button>
                ))}
              </div>
            </div>

            {/* 3-Step 10-Minute Meeting Priority Guide */}
            <div className="lg:col-span-6 p-5 rounded-2xl bg-[#FAF7F2] border border-[#D4AF37] space-y-3">
              <h3 className="text-base font-extrabold text-[#4A2E1B] flex items-center gap-2">
                <Clock className="w-5 h-5 text-[#D4AF37]" />
                <span>⏳ راهنمای ۳ مرحله‌ای اولویت معرفی قابلیت‌ها در جلسه ۱۰ دقیقه‌ای (جلوگیری از گیج شدن مدیر سنتی):</span>
              </h3>
              <div className="space-y-3 text-xs">
                <div className="p-3.5 rounded-xl bg-white border-r-4 border-emerald-600">
                  <div className="font-extrabold text-emerald-800 mb-1">
                    گام اول (دقیقه ۱ تا ۳ جلسه): فقط معرفی ۳ قابلیت اصلی و پول‌ساز!
                  </div>
                  <p className="text-[#6F4E37] leading-relaxed">
                    اصلاً وارد جزئیات فنی نشوید! فقط ۱) نام برند خودش در بالای صفحه، ۲) محاسبه ۰.۱ ثانیه‌ای ۱۰ متر کابینت نئوکلاسیک و ۳) ارسال فوری فاکتور طلاکوب به واتساپ گوشی خودش را نشان دهید.
                  </p>
                </div>

                <div className="p-3.5 rounded-xl bg-white border-r-4 border-[#D4AF37]">
                  <div className="font-extrabold text-[#4A2E1B] mb-1">
                    گام دوم (دقیقه ۳ تا ۶ جلسه): معرفی قابلیت‌های مالی، چک صیادی و تایم‌های خالی
                  </div>
                  <p className="text-[#6F4E37] leading-relaxed">
                    حالا «جدول سررسید چک صیادی بنفش + استعلام رنگ چک»، «قفل ضمانت قیمت ورق و یراق در برابر تورم» و «مناقصه تایم‌های خالی کارگاه CNC» را نشان دهید تا ببیند چطور جلوی ضرر او را می‌گیرد.
                  </p>
                </div>

                <div className="p-3.5 rounded-xl bg-white border-r-4 border-[#4A2E1B]">
                  <div className="font-extrabold text-[#4A2E1B] mb-1">
                    گام سوم (دقیقه ۷ تا ۱۰ جلسه): معرفی امکانات ادمین، استوری‌ساز و بستن قرارداد اقساطی
                  </div>
                  <p className="text-[#6F4E37] leading-relaxed">
                    در پایان، «استوری‌ساز ۱-کلیکی اینستاگرام» را نشان داده و بلافاصله پیشنهاد بی‌ردخور «۱۲ میلیون پیش‌پرداخت نقد + ۲ چک صیادی ۱۸ میلیونی با ضمانت بازگشت» را مطرح و قرارداد را ببندید.
                  </p>
                </div>
              </div>
            </div>
          </div>

          {/* #golden-formula: The 3-Part Golden Formula for Guaranteed Sales from Week 1 */}
          <div id="golden-formula" className="p-6 rounded-3xl bg-gradient-to-l from-[#4A2E1B] via-[#352012] to-[#24140B] text-white border-2 border-[#D4AF37] space-y-5 scroll-mt-24">
            <div className="flex flex-wrap items-center justify-between gap-2 border-b border-[#D4AF37]/30 pb-3">
              <h3 className="text-lg sm:text-xl font-extrabold text-[#F6E27A] flex items-center gap-2">
                <Sparkles className="w-6 h-6 text-[#D4AF37]" />
                <span>🏆 آموزش «فرمول طلایی ۳گانه فروش قطعی از هفته اول» (#golden-formula)</span>
              </h3>
              <span className="px-3 py-1 rounded-lg bg-emerald-600 text-white text-xs font-bold">
                نرخ تبدیل حضوری: بالای ۷۵٪
              </span>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-xs">
              <div className="p-4 rounded-2xl bg-white/10 border border-[#D4AF37]/50 space-y-2">
                <div className="text-sm font-extrabold text-[#F6E27A]">
                  ۱) اصل اول (پشت درِ واحد صنفی — ثانیه صفر)
                </div>
                <p className="text-white/90 leading-relaxed">
                  قبل از ورود به نمایشگاه، نام واحد صنفی و نام مدیر را در نوار بالای تبلت وارد کنید. وقتی تبلت را جلوی مدیر می‌گیرید و در **ثانیه اول نام برند و شعار خودش را بالای سامانه می‌بیند**، حس مالکیت فوری (Endowment Effect) ایجاد شده و گارد دفاعی او کاملاً فرو می‌ریزد.
                </p>
              </div>

              <div className="p-4 rounded-2xl bg-white/10 border border-[#D4AF37]/50 space-y-2">
                <div className="text-sm font-extrabold text-[#F6E27A]">
                  ۲) اصل دوم (دقیقه ۲ جلسه — شلیک واتساپ)
                </div>
                <p className="text-white/90 leading-relaxed">
                  در ۳۰ ثانیه یک پیش‌فاکتور نمونه (مثلاً ۱۲ متر کابینت نئوکلاسیک با ۶ چک صیادی و قفل ضدتورم) بسازید و دکمه **«ارسال به واتساپ»** را بزنید تا پیام روی گوشی خودِ مدیر نمایشگاه بیاید! **شنیدن صدای نوتیفیکیشن واتساپ و دیدن فاکتور طلاکوب با نام خودش = لحظه قطعی تصمیم خرید!**
                </p>
              </div>

              <div className="p-4 rounded-2xl bg-emerald-900/50 border-2 border-emerald-400 space-y-2">
                <div className="text-sm font-extrabold text-emerald-300">
                  ۳) اصل سوم (دقیقه ۸ جلسه — شکستن مقاومت مالی)
                </div>
                <p className="text-white/95 leading-relaxed">
                  پیشنهاد فروش لایسنس پایه (**۴۸ میلیون تومان**) یا VIP (**۹۶ میلیون تومان**) به روش اقساطی بدون ریسک:
                  <strong className="block text-[#F6E27A] mt-1">
                    «فقط ۱۲ میلیون تومان پیش‌پرداخت نقدی (که درجا کل ۲۵٪ پورسانت شما یعنی ۱۲ میلیون تومان را تسویه می‌کند!) + ۲ فقره چک صیادی ۱۸ میلیون تومانی با ضمانت کتبی بازگشت چک‌ها در صورت عدم افزایش فروش در ماه اول!»
                  </strong>
                </p>
              </div>
            </div>
          </div>

          {/* Ready Objection Handlers (1-Click Copy) & Guild Statistics */}
          <div className="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
            <div className="lg:col-span-7 space-y-3">
              <h3 className="text-base font-extrabold text-[#4A2E1B]">
                🛡️ بانک پاسخ‌های آماده به ۵ بهانه رایج مدیران صنف کابینت و دکوراسیون (کپی ۱-کلیکی):
              </h3>
              <div className="space-y-2.5">
                {FIVE_COMMON_OBJECTIONS.map((obj) => (
                  <div
                    key={obj.id}
                    className="p-4 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8] space-y-2"
                  >
                    <div className="flex items-center justify-between gap-2">
                      <strong className="text-xs sm:text-sm text-red-900">{obj.objection}</strong>
                      <button
                        type="button"
                        onClick={() => {
                          navigator.clipboard?.writeText(obj.answer).catch(() => {});
                          setCopiedObjectionId(obj.id);
                          setTimeout(() => setCopiedObjectionId(null), 2000);
                        }}
                        className="px-2.5 py-1 rounded-lg bg-[#4A2E1B] text-[#F6E27A] text-[11px] font-bold flex items-center gap-1 cursor-pointer shrink-0"
                      >
                        {copiedObjectionId === obj.id ? (
                          <>
                            <Check className="w-3.5 h-3.5 text-emerald-400" />
                            <span>کپی شد</span>
                          </>
                        ) : (
                          <>
                            <Copy className="w-3.5 h-3.5" />
                            <span>کپی پاسخ</span>
                          </>
                        )}
                      </button>
                    </div>
                    <p className="text-xs text-[#2A1A10] leading-relaxed">{obj.answer}</p>
                  </div>
                ))}
              </div>
            </div>

            <div className="lg:col-span-5 space-y-3">
              <h3 className="text-base font-extrabold text-[#4A2E1B]">
                📊 آمار تعداد واحدهای فعال صنف چوب، کابینت و دکوراسیون در تهران و شهرستان‌ها:
              </h3>
              <div className="space-y-2.5">
                {TEHRAN_AND_PROVINCES_STATS.map((st, idx) => (
                  <div
                    key={idx}
                    className="p-3.5 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8] space-y-1 text-xs"
                  >
                    <div className="font-extrabold text-[#4A2E1B] flex items-center justify-between">
                      <span>{st.region}</span>
                      <span className="font-mono-tabular text-emerald-700">{st.activeUnits}</span>
                    </div>
                    <div className="text-[11px] text-[#6F4E37]">{st.potential25}</div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* =====================================================================
          PART 5: #invitation-letter — AMBASSADOR FINANCIAL HUB, GOLD-FOIL INVITATION & 25% SHEBA LEDGER
      ====================================================================== */}
      <section
        id="invitation-letter"
        aria-label="Official VIP Gold-Foil Invitation Letter and 25% Sheba Commission Settlement Hub"
        className="px-4 sm:px-8 pb-8 max-w-[1440px] mx-auto scroll-mt-20"
      >
        <div className="hc-card bg-white border-2 border-[#D4AF37] rounded-3xl p-6 sm:p-8 space-y-8">
          <div className="border-b border-[#E6DEC8] pb-5">
            <div className="text-xs font-bold text-[#6F4E37] mb-1">
              هاب مالی سفیران فروش · تسویه آنی ۲۵٪ پورسانت نقدی از پیش‌پرداخت (۱۲ تا ۲۴ میلیون تومان در هر فروش)
            </div>
            <h2 className="text-2xl sm:text-3xl font-extrabold text-[#4A2E1B]">
              📜 دعوت‌نامه رسمی طلاکوب VIP و جدول تسویه ۲۵٪ پورسانت شبا (#invitation-letter)
            </h2>
          </div>

          <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 items-start">
            {/* Left: Gold-Foil Official VIP Invitation Letter */}
            <div className="lg:col-span-6 space-y-4">
              <div className="p-6 rounded-3xl bg-gradient-to-br from-[#2A1A10] via-[#3A2314] to-[#1E1109] text-white border-4 border-[#D4AF37] shadow-xl space-y-4">
                <div className="flex items-center justify-between border-b border-[#D4AF37]/40 pb-3">
                  <div>
                    <div className="text-[11px] text-[#F6E27A] font-bold">
                      اکوسیستم آفرینش | شهر جدید نیومتاورسیتی جهان | توان استیج FBNM
                    </div>
                    <h3 className="text-lg font-extrabold text-white mt-0.5">
                      👑 دعوت‌نامه رسمی اختصاصی ارتقای هوشمند واحد صنفی
                    </h3>
                  </div>
                  <span className="px-2.5 py-1 rounded-lg bg-[#D4AF37] text-[#2A1A10] text-xs font-extrabold font-mono-tabular">
                    VIP INVITE
                  </span>
                </div>

                <div className="space-y-3 text-xs leading-relaxed text-[#FAF7F2]/95">
                  <p>
                    محضر گرامی جناب آقای / سرکار خانم <strong className="text-[#F6E27A]">{tenantConfig.managerName}</strong>
                    <br />
                    مدیریت محترم مجموعه فاخر <strong className="text-[#F6E27A]">«{tenantConfig.brandName}»</strong> — شهر <strong className="text-[#F6E27A]">{tenantConfig.city}</strong>
                  </p>
                  <p>
                    با سلام و تقدیم احترام؛ احتراماً به استحضار می‌رساند نسخه اختصاصی و ایزوله **«سامانه جامع مهندسی فروش، پیش‌فاکتور ۰.۱ ثانیه‌ای اتحادیه، تقسیط چک صیادی بنفش، قفل ضدتورم ورق و یراق و استودیو استوری‌ساز طلاکوب»** به نام و برند تجاری مجموعه شما پیکربندی و آماده بهره‌برداری گردیده است.
                  </p>
                  <div className="p-3 rounded-xl bg-black/35 border border-[#D4AF37]/50 space-y-1 font-mono-tabular text-[11px]">
                    <div>🔗 آدرس اختصاصی سامانه شما: <span dir="ltr" className="text-[#F6E27A]">{generatedDemoUrl}</span></div>
                    <div>💳 شرایط فعال‌سازی لایسنس دائمی: ۱۲ میلیون تومان پیش‌پرداخت + ۲ چک صیادی ۱۸ میلیونی (با ضمانت بازگشت ۱۰۰٪ در ماه اول)</div>
                    <div>🎖️ کد سفیر رسمی ثبت‌شده: <strong className="text-emerald-300">{tenantConfig.refCode}</strong></div>
                  </div>
                </div>

                <div className="flex flex-wrap gap-2.5 pt-2">
                  <button
                    type="button"
                    onClick={handleSendInvitationToWhatsApp}
                    className="flex-1 py-3 px-4 rounded-xl bg-emerald-600 hover:bg-emerald-500 text-white font-bold text-xs flex items-center justify-center gap-2 cursor-pointer"
                  >
                    <MessageCircle className="w-4 h-4" />
                    <span>ارسال دعوت‌نامه طلاکوب به واتساپ مدیر</span>
                  </button>
                  <button
                    type="button"
                    onClick={() => window.print()}
                    className="py-3 px-4 rounded-xl bg-[#D4AF37] hover:bg-[#F6E27A] text-[#2A1A10] font-extrabold text-xs flex items-center justify-center gap-1.5 cursor-pointer"
                  >
                    <Printer className="w-4 h-4" />
                    <span>چاپ دعوت‌نامه رسمی (A4)</span>
                  </button>
                </div>
              </div>
            </div>

            {/* Right: Ambassador Sheba Registration & 1-Click Sale Recording Form */}
            <div className="lg:col-span-6 p-6 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8] space-y-4">
              <h3 className="text-base sm:text-lg font-extrabold text-[#4A2E1B] flex items-center gap-2">
                <CreditCard className="w-5 h-5 text-[#D4AF37]" />
                <span>🏦 ثبت شماره شبا سفیر و ثبت ۱-کلیکی فروش لایسنس (تسویه ۲۵٪ درجا):</span>
              </h3>

              <form onSubmit={handleRecordNewLicenseSale} className="space-y-3 text-xs">
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                  <div>
                    <label className="block font-bold text-[#4A2E1B] mb-1">
                      شماره شبا بانکی ویزیتور / سفیر (جهت واریز ۲۵٪):
                    </label>
                    <input
                      type="text"
                      dir="ltr"
                      value={shebaNumber}
                      onChange={(e) => setShebaNumber(e.target.value)}
                      placeholder="IR820540102680020817909002"
                      className="w-full px-3 py-2.5 rounded-xl bg-white border border-[#E6DEC8] font-mono-tabular font-bold text-[#4A2E1B]"
                    />
                  </div>
                  <div>
                    <label className="block font-bold text-[#4A2E1B] mb-1">
                      کد سفیر فعال شما در لینک (?ref=):
                    </label>
                    <input
                      type="text"
                      dir="ltr"
                      value={tenantConfig.refCode}
                      onChange={(e) =>
                        onUpdateTenantConfig({ ...tenantConfig, refCode: e.target.value })
                      }
                      className="w-full px-3 py-2.5 rounded-xl bg-white border border-[#E6DEC8] font-mono-tabular font-bold text-emerald-700"
                    />
                  </div>
                </div>

                <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                  <div>
                    <label className="block font-bold text-[#4A2E1B] mb-1">
                      نام واحد صنفی خریدار:
                    </label>
                    <input
                      type="text"
                      value={saleShopName}
                      onChange={(e) => setSaleShopName(e.target.value)}
                      placeholder={tenantConfig.brandName}
                      className="w-full px-3 py-2.5 rounded-xl bg-white border border-[#E6DEC8]"
                    />
                  </div>
                  <div>
                    <label className="block font-bold text-[#4A2E1B] mb-1">
                      نام مدیر واحد صنفی:
                    </label>
                    <input
                      type="text"
                      value={saleManagerName}
                      onChange={(e) => setSaleManagerName(e.target.value)}
                      placeholder={tenantConfig.managerName}
                      className="w-full px-3 py-2.5 rounded-xl bg-white border border-[#E6DEC8]"
                    />
                  </div>
                </div>

                <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                  <div>
                    <label className="block font-bold text-[#4A2E1B] mb-1">
                      سطح لایسنس فروخته‌شده:
                    </label>
                    <select
                      value={salePlanType}
                      onChange={(e) => setSalePlanType(e.target.value as 'base48' | 'vip96')}
                      className="w-full px-3 py-2.5 rounded-xl bg-white border border-[#E6DEC8] font-bold text-[#4A2E1B]"
                    >
                      <option value="base48">لایسنس پایه — ۴۸ میلیون تومان (پورسانت ۲۵٪ شما: ۱۲ میلیون نقد!)</option>
                      <option value="vip96">لایسنس VIP — ۹۶ میلیون تومان (پورسانت ۲۵٪ شما: ۲۴ میلیون نقد!)</option>
                    </select>
                  </div>

                  <div>
                    <label className="block font-bold text-[#4A2E1B] mb-1">
                      بسته تبلیغات و رپورتاژ جانبی (۲۵٪ سهم سفیر):
                    </label>
                    <select
                      value={saleAdPackageToman}
                      onChange={(e) => setSaleAdPackageToman(Number(e.target.value))}
                      className="w-full px-3 py-2.5 rounded-xl bg-white border border-[#E6DEC8] font-bold text-[#4A2E1B]"
                    >
                      <option value={0}>بدون بسته تبلیغاتی اضافه (۰ تومان)</option>
                      <option value={8000000}>بسته تبلیغات منطقه‌ای — ۸ میلیون (+۲ میلیون پورسانت شما)</option>
                      <option value={16000000}>بسته تبلیغات VIP استانی — ۱۶ میلیون (+۴ میلیون پورسانت شما)</option>
                    </select>
                  </div>
                </div>

                <button
                  type="submit"
                  className="w-full py-3.5 px-5 rounded-xl bg-[#4A2E1B] hover:bg-[#352012] text-[#F6E27A] font-extrabold text-xs sm:text-sm flex items-center justify-center gap-2 cursor-pointer"
                >
                  <PlusCircle className="w-4 h-4 text-[#D4AF37]" />
                  <span>
                    ثبت ۱-کلیکی فروش لایسنس و محاسبه آنی ۲۵٪ پورسانت شبا (
                    {formatPrice(
                      (salePlanType === 'vip96' ? 24000000 : 12000000) +
                        Math.round(saleAdPackageToman * 0.25),
                      currency,
                      lang
                    )}
                    )
                  </span>
                </button>
              </form>
            </div>
          </div>

          {/* Live Financial Ledger Table of 25% Sheba Settlements */}
          <div className="space-y-3">
            <div className="flex flex-wrap items-center justify-between gap-2">
              <h3 className="text-base sm:text-lg font-extrabold text-[#4A2E1B]">
                📊 جدول گزارش مالی زنده محاسبه و تسویه ۲۵٪ پورسانت نقدی ویزیتور (شبا):
              </h3>
              <div className="px-4 py-2 rounded-xl bg-emerald-600 text-white text-xs font-extrabold font-mono-tabular">
                مجموع پورسانت ۲۵٪ ثبت‌شده شما: {formatPrice(totalVisitorEarnedToman, currency, lang)}
              </div>
            </div>

            <div className="overflow-x-auto">
              <table className="w-full text-right border-collapse rounded-2xl overflow-hidden text-xs">
                <thead>
                  <tr className="bg-[#4A2E1B] text-[#F6E27A]">
                    <th className="p-3">تاریخ</th>
                    <th className="p-3">نام واحد صنفی و مدیر</th>
                    <th className="p-3">نوع لایسنس</th>
                    <th className="p-3">پیش‌پرداخت نقد مشتری</th>
                    <th className="p-3">بسته تبلیغات</th>
                    <th className="p-3">پورسانت ۲۵٪ خالص ویزیتور</th>
                    <th className="p-3">شماره شبا و وضعیت تسویه</th>
                  </tr>
                </thead>
                <tbody className="bg-[#FAF7F2]">
                  {salesLedger.map((row) => (
                    <tr key={row.id} className="border-b border-[#E6DEC8]">
                      <td className="p-3 font-mono-tabular">{row.dateFa}</td>
                      <td className="p-3 font-bold text-[#4A2E1B]">
                        {row.shopName} ({row.managerName} — {row.city})
                      </td>
                      <td className="p-3">{row.planName}</td>
                      <td className="p-3 font-mono-tabular">
                        {formatPrice(row.downPaymentCashToman, currency, lang)}
                      </td>
                      <td className="p-3 font-mono-tabular">
                        {formatPrice(row.adPackageToman, currency, lang)}
                      </td>
                      <td className="p-3 font-mono-tabular font-extrabold text-emerald-700">
                        {formatPrice(row.visitor25CommissionToman, currency, lang)}
                      </td>
                      <td className="p-3 font-mono-tabular text-[11px]">
                        <span className="block text-[#4A2E1B] font-bold" dir="ltr">
                          {row.shebaNumber}
                        </span>
                        <span className="text-emerald-700 font-bold">✓ {row.status}</span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};
