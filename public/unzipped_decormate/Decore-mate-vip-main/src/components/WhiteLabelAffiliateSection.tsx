import React, { useState } from 'react';
import {
  Store,
  Handshake,
  Sparkles,
  CheckCircle2,
  Sliders,
  Calculator,
  Award,
  Lightbulb,
  ShieldCheck,
  RotateCcw,
  Save,
  Copy,
  Check,
} from 'lucide-react';
import {
  CurrencyCode,
  LanguageCode,
  formatPrice,
  SUGGESTED_APP_NAMES,
  RELATED_GUILDS_DATA,
} from '../data/decorData';

export interface CustomBrandConfig {
  brandName: string;
  tagline: string;
  phone: string;
  whatsapp: string;
  instagram: string;
  city: string;
  priceMultiplier: number; // e.g., 1.0 = standard union, 1.1 = +10% luxury showroom, 0.92 = -8% direct workshop
}

interface WhiteLabelAffiliateSectionProps {
  brandConfig: CustomBrandConfig;
  onUpdateBrandConfig: (newConfig: CustomBrandConfig) => void;
  onResetBrandConfig: () => void;
  currency: CurrencyCode;
  lang: LanguageCode;
}

export const WhiteLabelAffiliateSection: React.FC<WhiteLabelAffiliateSectionProps> = ({
  brandConfig,
  onUpdateBrandConfig,
  onResetBrandConfig,
  currency,
  lang,
}) => {
  const [activeSubTab, setActiveSubTab] = useState<
    'personalize' | 'guilds' | 'visitor25' | 'critique'
  >('personalize');
  const [savedNotice, setSavedNotice] = useState(false);
  const [copiedLink, setCopiedLink] = useState(false);

  // Live 3-Zone Kitchen Material & Lighting Visualizer State (Creative Feature)
  const [upperColor, setUpperColor] = useState({
    name: 'سفید صدفی مات انزو (#FAF7F2)',
    hex: '#FAF7F2',
    textHex: '#4A2E1B',
  });
  const [slabStyle, setSlabStyle] = useState({
    name: 'سنگ اسلب مرمر کلکته طلایی (Calacatta Gold)',
    bg: 'linear-gradient(135deg, #FFFFFF 0%, #F4EFE6 45%, #D4AF37 50%, #FAF7F2 100%)',
  });
  const [baseWood, setBaseWood] = useState({
    name: 'چوب طبیعی گردو آمریکایی (#4A2E1B)',
    hex: '#4A2E1B',
  });
  const [ledKelvin, setLedKelvin] = useState<'3000K' | '4000K' | '6000K'>('3000K');

  // Visitor 25% Guaranteed Income Simulator state
  const [monthlyAppSales, setMonthlyAppSales] = useState<number>(8);
  const [avgPackagePriceToman, setAvgPackagePriceToman] = useState<number>(14900000); // Default Tier 4 / Custom White-Label
  const [monthlyLeadReferrals, setMonthlyLeadReferrals] = useState<number>(6);

  // Calculations for Visitor Income
  const direct25CommissionToman = Math.round(monthlyAppSales * avgPackagePriceToman * 0.25);
  const recurring10BonusToman = Math.round(monthlyAppSales * avgPackagePriceToman * 0.1);
  const cabinetProjectReferralFeeToman = monthlyLeadReferrals * 3500000; // Avg 3.5M Toman per cabinet contract referral
  const guaranteedBaseFloorToman = monthlyAppSales >= 4 ? 12000000 : 6000000; // Guaranteed base floor for active visitors
  const totalMonthlyVisitorIncomeToman =
    direct25CommissionToman + recurring10BonusToman + cabinetProjectReferralFeeToman;

  const handleSavePersonalization = (e: React.FormEvent) => {
    e.preventDefault();
    setSavedNotice(true);
    setTimeout(() => setSavedNotice(false), 3000);
  };

  const handleCopyShareableConfig = () => {
    const summaryText = `👑 سامانه اختصاصی دکوراسیون و محاسبه متراژ: ${brandConfig.brandName}
📍 شهر: ${brandConfig.city} | 📞 تماس و واتساپ: ${brandConfig.phone}
📸 اینستاگرام: ${brandConfig.instagram}
📐 ضریب قیمت کارگاه: ${Math.round(brandConfig.priceMultiplier * 100)}% نرخ پایه اتحادیه`;
    navigator.clipboard?.writeText(summaryText).catch(() => {});
    setCopiedLink(true);
    setTimeout(() => setCopiedLink(false), 2500);
  };

  return (
    <section
      id="whitelabel-visitor-hub"
      aria-label="White-Label Personalization, 25% Visitor Income & Architectural Critique"
      className="py-10 px-4 sm:px-8 max-w-[1440px] mx-auto"
    >
      <div className="hc-card bg-white border-2 border-[#D4AF37] rounded-3xl p-6 sm:p-8 shadow-sm">
        {/* Top Section Header & Segmented Controls */}
        <div className="flex flex-col lg:flex-row lg:items-center justify-between gap-4 border-b border-[#E6DEC8] pb-6 mb-6">
          <div>
            <div className="text-xs font-bold text-[#6F4E37] mb-1">
              قابلیت شخصی‌سازی ۱۰۰٪ آفلاین (White-Label) · سیستم درآمد تضمینی ویزیتورها (۲۵٪ سود خالص) · نقد و نوآوری مهندسی
            </div>
            <h2 className="text-2xl sm:text-3xl font-bold text-[#4A2E1B] flex items-center gap-2.5">
              <Store className="w-7 h-7 text-[#D4AF37] shrink-0" />
              <span>مرکز شخصی‌سازی برند برای فعالین صنعت چوب + سازوکار درآمد ۲۵٪ ویزیتورها</span>
            </h2>
          </div>

          <div className="flex flex-wrap items-center gap-1.5 p-1.5 bg-[#FAF7F2] border border-[#E6DEC8] rounded-xl">
            <button
              onClick={() => setActiveSubTab('personalize')}
              className={`px-3.5 py-2.5 rounded-lg text-xs sm:text-sm font-bold transition-colors cursor-pointer whitespace-nowrap ${
                activeSubTab === 'personalize'
                  ? 'bg-[#4A2E1B] text-[#F6E27A] shadow-sm'
                  : 'text-[#4A2E1B] hover:bg-[#E6DEC8]/40'
              }`}
            >
              🎨 ۱. شخصی‌سازی زنده برای هر مغازه‌دار
            </button>
            <button
              onClick={() => setActiveSubTab('guilds')}
              className={`px-3.5 py-2.5 rounded-lg text-xs sm:text-sm font-bold transition-colors cursor-pointer whitespace-nowrap ${
                activeSubTab === 'guilds'
                  ? 'bg-emerald-700 text-white shadow-sm'
                  : 'text-emerald-800 hover:bg-emerald-50'
              }`}
            >
              📢 ۲. دعوت از ویزیتورها (۲۵٪ سود) + مشاغل مرتبط
            </button>
            <button
              onClick={() => setActiveSubTab('visitor25')}
              className={`px-3.5 py-2.5 rounded-lg text-xs sm:text-sm font-bold transition-colors cursor-pointer whitespace-nowrap ${
                activeSubTab === 'visitor25'
                  ? 'bg-[#4A2E1B] text-[#F6E27A] shadow-sm'
                  : 'text-[#4A2E1B] hover:bg-[#E6DEC8]/40'
              }`}
            >
              💰 ۳. ماشین‌حساب درآمد ۲۵٪ ویزیتورها
            </button>
            <button
              onClick={() => setActiveSubTab('critique')}
              className={`px-3.5 py-2.5 rounded-lg text-xs sm:text-sm font-bold transition-colors cursor-pointer whitespace-nowrap ${
                activeSubTab === 'critique'
                  ? 'bg-[#4A2E1B] text-[#F6E27A] shadow-sm'
                  : 'text-[#4A2E1B] hover:bg-[#E6DEC8]/40'
              }`}
            >
              💡 ۴. نقد مهندسی و شبیه‌ساز خلاقانه رنگ
            </button>
          </div>
        </div>

        {/* High-Impact Visitor Callout Banner Always Visible at Top of Hub */}
        <div className="mb-6 p-4 sm:p-5 rounded-2xl bg-gradient-to-l from-[#4A2E1B] via-[#382213] to-[#24150B] text-white border border-[#D4AF37] flex flex-col lg:flex-row lg:items-center justify-between gap-4">
          <div className="space-y-1">
            <div className="text-xs font-extrabold text-[#F6E27A] flex items-center gap-1.5">
              <Handshake className="w-4 h-4 text-[#D4AF37]" />
              <span>فراخوان رسمی دعوت از ویزیتورها و بازاریابان حضوری/تلفنی سراسر کشور (ایران، اقلیم کردستان، ارمنستان، دبی و ترکیه):</span>
            </div>
            <p className="text-xs sm:text-sm text-[#FAF7F2]/95 leading-relaxed">
              این اپلیکیشن قابلیت **شخصی‌سازی فوری در ۵ ثانیه برای هر مغازه‌دار، نمایشگاه‌دار، کابینت‌ساز، فروشنده پارکت، یراق‌آلات، سنگ اسلب و کناف** را دارد! شما می‌توانید در حضور هر مغازه‌دار، برنامه را به نام مغازه او شخصی‌سازی کرده و از فروش هر نسخه اختصاصی، **۲۵٪ پورسانت نقد و تسویه درجا** دریافت کنید.
            </p>
          </div>
          <button
            type="button"
            onClick={() => setActiveSubTab('guilds')}
            className="px-4 py-3 rounded-xl bg-[#D4AF37] hover:bg-[#F6E27A] text-[#2A1A10] font-extrabold text-xs whitespace-nowrap cursor-pointer shrink-0"
          >
            مشاهده ۸ شغل خریدار اپ + شخصی‌سازی ۱-کلیکی ←
          </button>
        </div>

        {/* SUB-TAB 1: LIVE WHITE-LABEL PERSONALIZATION FOR CABINET MAKERS & SHOWROOMS */}
        {activeSubTab === 'personalize' && (
          <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 items-start">
            <form onSubmit={handleSavePersonalization} className="lg:col-span-7 space-y-4">
              <div className="p-4 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8] mb-2">
                <h3 className="text-base font-bold text-[#4A2E1B] mb-1">
                  🛠️ پنل شخصی‌سازی فوری ویژه کابینت‌سازان، نمایشگاه‌داران و شرکت‌های بازسازی (ذخیره آفلاین)
                </h3>
                <p className="text-xs text-[#6F4E37] leading-relaxed mb-3">
                  هر تغییری که در این بخش اعمال کنید، **بلافاصله در هدر برنامه، پیش‌فاکتورهای چاپی، دکمه‌های واتساپ، ضرایب قیمت ماشین‌حساب و پوسترهای استوری‌ساز** جایگزین می‌شود و بدون نیاز به اینترنت در حافظه دستگاه باقی می‌ماند.
                </p>

                <div className="text-xs font-bold text-[#4A2E1B] mb-2 flex items-center gap-1.5">
                  <Sparkles className="w-4 h-4 text-[#D4AF37]" />
                  <span>پیشنهاد نام‌های اصیل فارسی، انگلیسی و کوردی برای برنامه (با ۱ کلیک نام کل برنامه را تغییر دهید):</span>
                </div>
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
                  {SUGGESTED_APP_NAMES.map((item) => {
                    const isSelected = brandConfig.brandName === item.fullDisplay;
                    return (
                      <button
                        key={item.id}
                        type="button"
                        onClick={() =>
                          onUpdateBrandConfig({ ...brandConfig, brandName: item.fullDisplay })
                        }
                        className={`text-right p-2.5 rounded-xl border transition-colors cursor-pointer ${
                          isSelected
                            ? 'bg-[#4A2E1B] text-white border-[#D4AF37]'
                            : 'bg-white text-[#2A1A10] border-[#E6DEC8] hover:border-[#4A2E1B]'
                        }`}
                      >
                        <div className={`text-xs font-bold ${isSelected ? 'text-[#F6E27A]' : 'text-[#4A2E1B]'}`}>
                          {item.fullDisplay}
                        </div>
                        <div className={`text-[11px] mt-0.5 ${isSelected ? 'text-white/90' : 'text-[#6F4E37]'}`}>
                          کوردی: {item.kurdishName} · 🇦🇲 HY: {item.armenianName} · EN: {item.englishName}
                        </div>
                        <div className={`text-[10px] mt-1 leading-snug ${isSelected ? 'text-white/80' : 'text-[#6F4E37]'}`}>
                          {item.meaning}
                        </div>
                      </button>
                    );
                  })}
                </div>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                <div>
                  <label className="block text-xs font-bold text-[#4A2E1B] mb-1.5">
                    نام اختصاصی برند / کارگاه / نمایشگاه شما:
                  </label>
                  <input
                    type="text"
                    value={brandConfig.brandName}
                    onChange={(e) =>
                      onUpdateBrandConfig({ ...brandConfig, brandName: e.target.value })
                    }
                    placeholder="مثلاً: صنایع چوب و کابینت شاهکار"
                    className="w-full px-3.5 py-2.5 rounded-xl border border-[#E6DEC8] bg-[#FAF7F2] text-sm font-bold text-[#2A1A10] focus:outline-none focus:border-[#4A2E1B]"
                  />
                </div>

                <div>
                  <label className="block text-xs font-bold text-[#4A2E1B] mb-1.5">
                    شهر / منطقه فعالیت نمایشگاه:
                  </label>
                  <input
                    type="text"
                    value={brandConfig.city}
                    onChange={(e) =>
                      onUpdateBrandConfig({ ...brandConfig, city: e.target.value })
                    }
                    placeholder="مثلاً: تهران - شهرک صنعتی چهاردانگه / الهیه"
                    className="w-full px-3.5 py-2.5 rounded-xl border border-[#E6DEC8] bg-[#FAF7F2] text-sm text-[#2A1A10] focus:outline-none focus:border-[#4A2E1B]"
                  />
                </div>

                <div>
                  <label className="block text-xs font-bold text-[#4A2E1B] mb-1.5">
                    شماره تماس کارگاه (درج در پیش‌فاکتور و استوری):
                  </label>
                  <input
                    type="text"
                    dir="ltr"
                    value={brandConfig.phone}
                    onChange={(e) =>
                      onUpdateBrandConfig({ ...brandConfig, phone: e.target.value })
                    }
                    placeholder="09121234567"
                    className="w-full px-3.5 py-2.5 rounded-xl border border-[#E6DEC8] bg-[#FAF7F2] text-sm font-mono-tabular text-[#2A1A10] focus:outline-none focus:border-[#4A2E1B]"
                  />
                </div>

                <div>
                  <label className="block text-xs font-bold text-[#4A2E1B] mb-1.5">
                    شماره واتساپ دریافت سفارش (با کد کشور):
                  </label>
                  <input
                    type="text"
                    dir="ltr"
                    value={brandConfig.whatsapp}
                    onChange={(e) =>
                      onUpdateBrandConfig({ ...brandConfig, whatsapp: e.target.value })
                    }
                    placeholder="989121234567"
                    className="w-full px-3.5 py-2.5 rounded-xl border border-[#E6DEC8] bg-[#FAF7F2] text-sm font-mono-tabular text-[#2A1A10] focus:outline-none focus:border-[#4A2E1B]"
                  />
                </div>

                <div>
                  <label className="block text-xs font-bold text-[#4A2E1B] mb-1.5">
                    آیدی پیج اینستاگرام نمایشگاه:
                  </label>
                  <input
                    type="text"
                    dir="ltr"
                    value={brandConfig.instagram}
                    onChange={(e) =>
                      onUpdateBrandConfig({ ...brandConfig, instagram: e.target.value })
                    }
                    placeholder="@DecorMate.VIP"
                    className="w-full px-3.5 py-2.5 rounded-xl border border-[#E6DEC8] bg-[#FAF7F2] text-sm font-mono-tabular text-[#2A1A10] focus:outline-none focus:border-[#4A2E1B]"
                  />
                </div>

                <div>
                  <label className="block text-xs font-bold text-[#4A2E1B] mb-1.5">
                    شعار تبلیغاتی بالای پیش‌فاکتور:
                  </label>
                  <input
                    type="text"
                    value={brandConfig.tagline}
                    onChange={(e) =>
                      onUpdateBrandConfig({ ...brandConfig, tagline: e.target.value })
                    }
                    placeholder="مجری تخصصی کابینت‌های لوکس با ضمانت ۱۰ ساله اتحادیه"
                    className="w-full px-3.5 py-2.5 rounded-xl border border-[#E6DEC8] bg-[#FAF7F2] text-sm text-[#2A1A10] focus:outline-none focus:border-[#4A2E1B]"
                  />
                </div>
              </div>

              {/* Custom Workshop Price Multiplier Slider */}
              <div className="p-4 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8]">
                <div className="flex items-center justify-between mb-2">
                  <label className="text-xs font-bold text-[#4A2E1B] flex items-center gap-1.5">
                    <Sliders className="w-4 h-4 text-[#D4AF37]" />
                    <span>تنظیم ضریب قیمت اختصاصی کارگاه شما نسبت به نرخ پایه اتحادیه:</span>
                  </label>
                  <span className="font-mono-tabular text-sm font-bold text-emerald-700">
                    {brandConfig.priceMultiplier === 1
                      ? '۱۰۰٪ (دقیقاً نرخ پایه اتحادیه)'
                      : brandConfig.priceMultiplier > 1
                      ? `+${Math.round((brandConfig.priceMultiplier - 1) * 100)}% بالاتر از پایه (کیفیت سفارشی VIP)`
                      : `-${Math.round((1 - brandConfig.priceMultiplier) * 100)}% تخفیف رقابتی کارگاه`}
                  </span>
                </div>
                <input
                  type="range"
                  min={0.8}
                  max={1.35}
                  step={0.05}
                  value={brandConfig.priceMultiplier}
                  onChange={(e) =>
                    onUpdateBrandConfig({
                      ...brandConfig,
                      priceMultiplier: parseFloat(e.target.value),
                    })
                  }
                  className="w-full accent-[#4A2E1B] cursor-pointer"
                />
                <div className="flex justify-between text-[11px] text-[#6F4E37] mt-1 font-mono-tabular">
                  <span>-20% (پخش مستقیم کارگاه)</span>
                  <span>100% (نرخ رسمی اتحادیه)</span>
                  <span>+35% (لوکس سفارشی فرشته/الهیه)</span>
                </div>
              </div>

              <div className="flex flex-wrap items-center gap-3 pt-2">
                <button
                  type="submit"
                  className="px-6 py-3 rounded-xl bg-[#4A2E1B] hover:bg-[#352012] text-[#F6E27A] font-bold text-sm flex items-center gap-2 transition-colors cursor-pointer"
                >
                  <Save className="w-4 h-4" />
                  <span>ذخیره دائمی برند در حافظه آفلاین دستگاه</span>
                </button>

                <button
                  type="button"
                  onClick={handleCopyShareableConfig}
                  className="px-4 py-3 rounded-xl bg-white border border-[#4A2E1B] text-[#4A2E1B] hover:bg-[#FAF7F2] font-bold text-xs sm:text-sm flex items-center gap-2 transition-colors cursor-pointer"
                >
                  {copiedLink ? <Check className="w-4 h-4 text-emerald-600" /> : <Copy className="w-4 h-4" />}
                  <span>{copiedLink ? 'کپی شد!' : 'کپی کارت ویزیت دیجیتال'}</span>
                </button>

                <button
                  type="button"
                  onClick={onResetBrandConfig}
                  className="px-4 py-3 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8] text-[#6F4E37] hover:text-[#4A2E1B] font-medium text-xs flex items-center gap-1.5 transition-colors cursor-pointer"
                >
                  <RotateCcw className="w-4 h-4" />
                  <span>بازگشت به نام پیش‌فرض DecorMate VIP</span>
                </button>
              </div>

              {savedNotice && (
                <div className="p-3 rounded-xl bg-emerald-50 border border-emerald-300 text-emerald-800 text-xs font-bold flex items-center gap-2">
                  <CheckCircle2 className="w-4 h-4 shrink-0" />
                  <span>
                    مشخصات برند «{brandConfig.brandName}» با موفقیت در کل برنامه، پیش‌فاکتورها و استوری‌ساز اعمال و به صورت آفلاین ذخیره شد!
                  </span>
                </div>
              )}
            </form>

            {/* Live Brand Card Preview & Visitor 5-Second Pitch Tip */}
            <div className="lg:col-span-5 space-y-4">
              <div className="p-6 rounded-2xl bg-gradient-to-br from-[#4A2E1B] via-[#3A2314] to-[#24140B] text-white border-2 border-[#D4AF37] shadow-lg">
                <div className="text-xs text-[#F6E27A] font-medium mb-1">
                  پیش‌نمایش زنده سربرگ اختصاصی شما در پیش‌فاکتور و استوری:
                </div>
                <h4 className="text-2xl font-bold text-white mb-1">{brandConfig.brandName}</h4>
                <p className="text-xs text-[#E6DEC8] mb-4">{brandConfig.tagline}</p>

                <div className="space-y-2 text-xs border-t border-[#D4AF37]/30 pt-4 font-mono-tabular">
                  <div className="flex justify-between">
                    <span className="text-[#E6DEC8]">شهر و منطقه:</span>
                    <span className="font-bold text-[#F6E27A]">{brandConfig.city}</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-[#E6DEC8]">تلفن مستقیم کارگاه:</span>
                    <span className="font-bold text-white" dir="ltr">{brandConfig.phone}</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-[#E6DEC8]">اینستاگرام رسمی:</span>
                    <span className="font-bold text-[#F6E27A]" dir="ltr">{brandConfig.instagram}</span>
                  </div>
                  <div className="flex justify-between">
                    <span className="text-[#E6DEC8]">ضریب قیمت فعال:</span>
                    <span className="font-bold text-emerald-400">
                      {Math.round(brandConfig.priceMultiplier * 100)}% نرخ پایه اتحادیه
                    </span>
                  </div>
                </div>
              </div>

              <div className="p-5 rounded-2xl bg-[#FAF7F2] border border-[#D4AF37]">
                <h4 className="text-sm font-bold text-[#4A2E1B] mb-2 flex items-center gap-2">
                  <Sparkles className="w-4 h-4 text-[#D4AF37]" />
                  <span>تکنیک طلایی ۵ ثانیه‌ای ویژه ویزیتورها برای فروش قطعی در نمایشگاه:</span>
                </h4>
                <p className="text-xs text-[#6F4E37] leading-relaxed">
                  وقتی وارد یک نمایشگاه کابینت یا کارگاه چوب می‌شوید، قبل از هر توضیحی، در همین فرم بالا **نام گالری و شماره موبایل صاحب نمایشگاه** را تایپ کنید! سپس گوشی یا تبلت را به او بدهید تا ببیند پیش‌فاکتور اتحادیه، اقساط چک صیادی و پوستر استوری اینستاگرام **با نام و برند خودش** آماده است. این کار نرخ خرید درجا را به بالای **۸۰٪** می‌رساند!
                </p>
              </div>
            </div>
          </div>
        )}

        {/* SUB-TAB 2: INVITE VISITORS (25% COMMISSION) & 8 RELATED PROFESSIONS WHO MUST BUY THIS APP */}
        {activeSubTab === 'guilds' && (
          <div className="space-y-6">
            <div className="p-5 rounded-2xl bg-emerald-50 border-2 border-emerald-500 text-emerald-950 space-y-2">
              <h3 className="text-base sm:text-lg font-extrabold flex items-center gap-2">
                <Handshake className="w-6 h-6 text-emerald-700 shrink-0" />
                <span>
                  🤝 دعوت رسمی از ویزیتورهای حرفه‌ای: چگونه با شخصی‌سازی این اپ برای مغازه‌داران، از هر فروش ۲۵٪ سود نقد کسب کنید؟
                </span>
              </h3>
              <p className="text-xs sm:text-sm leading-relaxed">
                هر مغازه‌دار، نمایشگاه‌دار یا استادکار در حوزه ساختمان و دکوراسیون، روزانه ده‌ها مشتری را به دلیل **نداشتن ابزار محاسبه فوری متراژ، نداشتن جدول اقساط چک صیادی و نداشتن استوری‌ساز حرفه‌ای** از دست می‌دهد. شما به عنوان ویزیتور، وارد هر یک از **۸ صنف زیر** شوید، با زدن دکمه **«⚡ شخصی‌سازی فوری برای این صنف»** برنامه را با نام همان مغازه به او نشان دهید و درجا **۲۵٪ کل مبلغ فروش برنامه** را به عنوان پورسانت نقد دریافت نمایید!
              </p>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
              {RELATED_GUILDS_DATA.map((guild) => {
                const isApplied = brandConfig.brandName === guild.sampleBrandName;
                return (
                  <div
                    key={guild.id}
                    className={`p-5 rounded-2xl border-2 flex flex-col justify-between gap-4 transition-colors ${
                      isApplied
                        ? 'bg-[#4A2E1B] text-white border-[#D4AF37]'
                        : 'bg-[#FAF7F2] text-[#2A1A10] border-[#E6DEC8]'
                    }`}
                  >
                    <div className="space-y-2.5">
                      <h4
                        className={`text-sm sm:text-base font-extrabold ${
                          isApplied ? 'text-[#F6E27A]' : 'text-[#4A2E1B]'
                        }`}
                      >
                        {guild.guildTitle}
                      </h4>

                      <div
                        className={`p-3 rounded-xl text-xs leading-relaxed ${
                          isApplied
                            ? 'bg-white/10 text-white'
                            : 'bg-white border border-[#E6DEC8] text-[#2A1A10]'
                        }`}
                      >
                        <strong className={isApplied ? 'text-[#F6E27A]' : 'text-emerald-700'}>
                          🔔 چرا این صنف حتماً باید این برنامه را بخرد؟{' '}
                        </strong>
                        {guild.whyMustBuy}
                      </div>

                      <div
                        className={`text-xs leading-relaxed ${
                          isApplied ? 'text-[#E6DEC8]' : 'text-[#6F4E37]'
                        }`}
                      >
                        <strong>💼 تکنیک فروش ویزیتور (پورسانت ۲۵٪): </strong>
                        {guild.visitorPitchTip}
                      </div>
                    </div>

                    <button
                      type="button"
                      onClick={() => {
                        onUpdateBrandConfig({
                          ...brandConfig,
                          brandName: guild.sampleBrandName,
                          tagline: guild.sampleTagline,
                          priceMultiplier: guild.priceMultiplier,
                        });
                      }}
                      className={`w-full py-3 px-4 rounded-xl font-bold text-xs flex items-center justify-center gap-2 cursor-pointer transition-colors ${
                        isApplied
                          ? 'bg-[#D4AF37] text-[#2A1A10]'
                          : 'bg-[#4A2E1B] hover:bg-[#352012] text-[#F6E27A]'
                      }`}
                    >
                      <Sparkles className="w-4 h-4 shrink-0" />
                      <span>
                        {isApplied
                          ? '✓ کل برنامه برای این صنف شخصی‌سازی شد (در هدر و فاکتور ببینید)'
                          : '⚡ شخصی‌سازی ۱-کلیکی کل برنامه برای این صنف (تست زنده ویزیتور)'}
                      </span>
                    </button>
                  </div>
                );
              })}
            </div>
          </div>
        )}

        {/* SUB-TAB 3: 25% GUARANTEED VISITOR INCOME MECHANISM & CALCULATOR */}
        {activeSubTab === 'visitor25' && (
          <div className="space-y-6">
            <div className="grid grid-cols-1 lg:grid-cols-12 gap-6 items-start">
              {/* Interactive Visitor Commission Simulator */}
              <div className="lg:col-span-7 bg-[#FAF7F2] border border-[#E6DEC8] rounded-2xl p-6 space-y-5">
                <h3 className="text-lg font-bold text-[#4A2E1B] flex items-center gap-2">
                  <Calculator className="w-5 h-5 text-[#D4AF37]" />
                  <span>ماشین‌حساب درآمد تضمینی ویزیتورها و کارشناسان فروش (۲۵٪ سهم خالص از فروش اپ)</span>
                </h3>

                <div>
                  <div className="flex justify-between text-xs font-bold text-[#4A2E1B] mb-1.5">
                    <span>۱. تعداد فروش اپلیکیشن شخصی‌سازی‌شده (White-Label / VIP) در ماه:</span>
                    <span className="font-mono-tabular text-emerald-700">{monthlyAppSales} فروش در ماه</span>
                  </div>
                  <input
                    type="range"
                    min={1}
                    max={30}
                    value={monthlyAppSales}
                    onChange={(e) => setMonthlyAppSales(Number(e.target.value))}
                    className="w-full accent-[#4A2E1B] cursor-pointer"
                  />
                  <div className="flex justify-between text-[11px] text-[#6F4E37] mt-1">
                    <span>۱ فروش (پارهوقت)</span>
                    <span>۱۰ فروش (استاندارد)</span>
                    <span>۳۰ فروش (ویزیتور حرفه‌ای شهرک‌های صنعتی)</span>
                  </div>
                </div>

                <div>
                  <label className="block text-xs font-bold text-[#4A2E1B] mb-1.5">
                    ۲. میانگین سطح پکیج فروخته‌شده به کابینت‌سازان یا شرکت‌های معماری:
                  </label>
                  <select
                    value={avgPackagePriceToman}
                    onChange={(e) => setAvgPackagePriceToman(Number(e.target.value))}
                    className="w-full px-3.5 py-2.5 rounded-xl border border-[#E6DEC8] bg-white text-sm font-bold text-[#4A2E1B]"
                  >
                    <option value={3900000}>سطح ۲: پکیج طلایی نمایشگاه — ۳,۹۰۰,۰۰۰ تومان (پورسانت هر فروش: ۹۷۵,۰۰۰ تومان)</option>
                    <option value={7500000}>سطح ۳: پکیج پلاتینیوم شرکت معماری — ۷,۵۰۰,۰۰۰ تومان (پورسانت هر فروش: ۱,۸۷۵,۰۰۰ تومان)</option>
                    <option value={14900000}>سطح ۴: پکیج الماس + اپ اختصاصی — ۱۴,۹۰۰,۰۰۰ تومان (پورسانت هر فروش: ۳,۷۲۵,۰۰۰ تومان)</option>
                    <option value={29000000}>سطح ۵: پکیج رویال فول White-Label — ۲۹,۰۰۰,۰۰۰ تومان (پورسانت هر فروش: ۷,۲۵۰,۰۰۰ تومان)</option>
                  </select>
                </div>

                <div>
                  <div className="flex justify-between text-xs font-bold text-[#4A2E1B] mb-1.5">
                    <span>۳. تعداد معرفی پروژه اجرایی کابینت/بازسازی در ماه (پورسانت معرفی پروژه):</span>
                    <span className="font-mono-tabular text-emerald-700">{monthlyLeadReferrals} پروژه در ماه</span>
                  </div>
                  <input
                    type="range"
                    min={0}
                    max={20}
                    value={monthlyLeadReferrals}
                    onChange={(e) => setMonthlyLeadReferrals(Number(e.target.value))}
                    className="w-full accent-[#4A2E1B] cursor-pointer"
                  />
                </div>

                {/* Breakdown Boxes */}
                <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 pt-2">
                  <div className="bg-white p-4 rounded-xl border border-[#E6DEC8]">
                    <div className="text-[11px] text-[#6F4E37]">پورسانت نقد ۲۵٪ فروش اپ:</div>
                    <div className="text-base font-bold text-[#4A2E1B] font-mono-tabular mt-1">
                      {formatPrice(direct25CommissionToman, currency, lang)}
                    </div>
                  </div>
                  <div className="bg-white p-4 rounded-xl border border-[#E6DEC8]">
                    <div className="text-[11px] text-[#6F4E37]">سهم ۱۰٪ تمدید سالانه (مادام‌العمر):</div>
                    <div className="text-base font-bold text-[#4A2E1B] font-mono-tabular mt-1">
                      {formatPrice(recurring10BonusToman, currency, lang)}
                    </div>
                  </div>
                  <div className="bg-white p-4 rounded-xl border border-[#E6DEC8]">
                    <div className="text-[11px] text-[#6F4E37]">پورسانت معرفی پروژه اجرایی:</div>
                    <div className="text-base font-bold text-[#4A2E1B] font-mono-tabular mt-1">
                      {formatPrice(cabinetProjectReferralFeeToman, currency, lang)}
                    </div>
                  </div>
                </div>

                <div className="p-5 rounded-2xl bg-[#4A2E1B] text-white flex flex-col sm:flex-row sm:items-center justify-between gap-4 border border-[#D4AF37]">
                  <div>
                    <div className="text-xs text-[#F6E27A]">مجموع درآمد ماهانه ویزیتور (تسویه آنی):</div>
                    <div className="text-2xl sm:text-3xl font-extrabold text-white font-mono-tabular mt-1">
                      {formatPrice(totalMonthlyVisitorIncomeToman, currency, lang)}
                    </div>
                    <div className="text-[11px] text-[#E6DEC8] mt-1">
                      + کف درآمد تضمینی پایه ویژه ویزیتورهای فعال: حداقل {formatPrice(guaranteedBaseFloorToman, currency, lang)} در ماه
                    </div>
                  </div>
                  <a
                    href={`https://wa.me/${brandConfig.whatsapp}?text=${encodeURIComponent(
                      `سلام، جهت ثبت‌نام به عنوان ویزیتور رسمی و نماینده فروش اپ شخصی‌سازی‌شده DecorMate VIP (با پورسانت ۲۵٪ نقد و درآمد تضمینی) پیام می‌دهم.`
                    )}`}
                    target="_blank"
                    rel="noreferrer"
                    className="px-5 py-3.5 rounded-xl bg-emerald-600 hover:bg-emerald-500 text-white font-bold text-xs sm:text-sm whitespace-nowrap text-center transition-colors"
                  >
                    📲 دریافت کد نمایندگی در واتساپ
                  </a>
                </div>
              </div>

              {/* 4 Pillars of Guaranteed Visitor Income Mechanism */}
              <div className="lg:col-span-5 space-y-3.5">
                <h4 className="text-base font-bold text-[#4A2E1B] flex items-center gap-2">
                  <ShieldCheck className="w-5 h-5 text-emerald-600" />
                  <span>تشریح ۴ ستون سازوکار درآمد تضمینی ویزیتورها:</span>
                </h4>

                <div className="p-4 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8]">
                  <div className="font-bold text-sm text-[#4A2E1B] mb-1">
                    ۱. پورسانت ۲۵٪ نقد و درجا از فروش هر نسخه شخصی‌سازی‌شده (White-Label)
                  </div>
                  <p className="text-xs text-[#6F4E37] leading-relaxed">
                    به محض اینکه یک کابینت‌ساز، نمایشگاه دکوراسیون یا شرکت بازسازی، نسخه اختصاصی برنامه را با برند و دامنه خودش سفارش دهد، **۲۵٪ کل مبلغ فاکتور در همان لحظه** به حساب ویزیتور واریز می‌شود (یا ویزیتور ۲۵٪ را مستقیماً از مشتری به عنوان پیش‌پرداخت کارگزاری دریافت می‌کند).
                  </p>
                </div>

                <div className="p-4 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8]">
                  <div className="font-bold text-sm text-[#4A2E1B] mb-1">
                    ۲. کف درآمد تضمینی ماهانه (Guaranteed Income Floor)
                  </div>
                  <p className="text-xs text-[#6F4E37] leading-relaxed">
                    ویزیتورهایی که در ماه حداقل ۲۰ کارگاه یا نمایشگاه را در سامانه ثبت و پرزنت کنند، حتی در ماه اول شروع کار، مشمول **کف درآمد تضمینی پایه** می‌شوند تا با خیال آسوده بازار شهر خود را پوشش دهند.
                  </p>
                </div>

                <div className="p-4 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8]">
                  <div className="font-bold text-sm text-[#4A2E1B] mb-1">
                    ۳. درآمد رسوبی و مادام‌العمر ۱۰٪ از تمدیدها و ارتقای اشتراک
                  </div>
                  <p className="text-xs text-[#6F4E37] leading-relaxed">
                    هر کارگاهی که توسط کد معرف شما ثبت شود، برای همیشه در پنل شما قفل می‌شود و در تمام تمدیدهای ماهانه و سالانه بعدی، **۱۰٪ سهم ثابت غیرفعال (Passive Income)** به شما تعلق می‌گیرد.
                  </p>
                </div>

                <div className="p-4 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8]">
                  <div className="font-bold text-sm text-[#4A2E1B] mb-1">
                    ۴. انحصار منطقه‌ای بازارهای بکر (شهرک‌های صنعتی و راسته‌های کابینت)
                  </div>
                  <p className="text-xs text-[#6F4E37] leading-relaxed">
                    اعطای نمایندگی انحصاری در قطب‌های چوب و کابینت (چهاردانگه، یافت‌آباد، کمرد، جاجرود، سهروردی، شیراز، مشهد، تبریز، اصفهان و بازارهای صادراتی دبی و اربیل).
                  </p>
                </div>
              </div>
            </div>
          </div>
        )}

        {/* SUB-TAB 4: ARCHITECTURAL CRITIQUE & CREATIVE INNOVATION PROPOSALS + LIVE 3-ZONE VISUALIZER */}
        {activeSubTab === 'critique' && (
          <div className="space-y-6">
            {/* Creative Feature: Live 3-Zone Kitchen Wood, Stone Slab & Kelvin Lighting Visualizer */}
            <div className="p-6 rounded-2xl bg-[#FAF7F2] border-2 border-[#D4AF37] space-y-5">
              <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2 border-b border-[#E6DEC8] pb-3">
                <div>
                  <span className="text-xs font-bold text-emerald-700">
                    ✨ پیشنهاد خلاقانه پیاده‌سازی‌شده (Live Architectural Material Studio)
                  </span>
                  <h3 className="text-lg font-extrabold text-[#4A2E1B]">
                    🎨 شبیه‌ساز زنده هارمونی رنگ کابینت هوایی، سنگ اسلب بین‌کابینتی، چوب جزیره و دمای نورپردازی
                  </h3>
                </div>
                <span className="text-xs font-mono-tabular bg-white px-3 py-1.5 rounded-lg border border-[#E6DEC8] text-[#4A2E1B] font-bold">
                  نور فعال: {ledKelvin} CRI 95+
                </span>
              </div>

              <div className="grid grid-cols-1 lg:grid-cols-12 gap-6 items-center">
                {/* Controls */}
                <div className="lg:col-span-6 space-y-4 text-xs">
                  <div>
                    <div className="font-bold text-[#4A2E1B] mb-1.5">۱. انتخاب رنگ کابینت هوایی (Upper Cabinets):</div>
                    <div className="grid grid-cols-2 gap-2">
                      {[
                        { name: 'سفید صدفی مات انزو (#FAF7F2)', hex: '#FAF7F2', textHex: '#4A2E1B' },
                        { name: 'کرم کاپوچینو نئوکلاسیک (#EFE6D5)', hex: '#EFE6D5', textHex: '#4A2E1B' },
                        { name: 'سبز مریم‌گلی سلطنتی (#3B5249)', hex: '#3B5249', textHex: '#FAF7F2' },
                        { name: 'طوسی ابری سوپرمات AGT (#D8DADF)', hex: '#D8DADF', textHex: '#2A1A10' },
                      ].map((c) => (
                        <button
                          key={c.hex}
                          type="button"
                          onClick={() => setUpperColor(c)}
                          className={`p-2.5 rounded-xl border text-right font-bold flex items-center gap-2 cursor-pointer ${
                            upperColor.hex === c.hex
                              ? 'border-2 border-[#4A2E1B] bg-white'
                              : 'border-[#E6DEC8] bg-white/70'
                          }`}
                        >
                          <span
                            className="w-4 h-4 rounded-full border border-black/20 shrink-0"
                            style={{ backgroundColor: c.hex }}
                          />
                          <span>{c.name}</span>
                        </button>
                      ))}
                    </div>
                  </div>

                  <div>
                    <div className="font-bold text-[#4A2E1B] mb-1.5">۲. انتخاب سنگ صفحه و بین‌کابینتی (Countertop & Slab):</div>
                    <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
                      {[
                        {
                          name: 'مرمر کلکته رگه‌طلایی (Calacatta Gold)',
                          bg: 'linear-gradient(135deg, #FFFFFF 0%, #F4EFE6 45%, #D4AF37 50%, #FAF7F2 100%)',
                        },
                        {
                          name: 'اسلب مارکینا مشکی-طلایی (Black Marquina)',
                          bg: 'linear-gradient(135deg, #1A1A1A 0%, #2C221E 48%, #D4AF37 51%, #141414 100%)',
                        },
                      ].map((s) => (
                        <button
                          key={s.name}
                          type="button"
                          onClick={() => setSlabStyle(s)}
                          className={`p-2.5 rounded-xl border text-right font-bold cursor-pointer ${
                            slabStyle.name === s.name
                              ? 'border-2 border-[#4A2E1B] bg-white'
                              : 'border-[#E6DEC8] bg-white/70'
                          }`}
                        >
                          {s.name}
                        </button>
                      ))}
                    </div>
                  </div>

                  <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                    <div>
                      <div className="font-bold text-[#4A2E1B] mb-1.5">۳. چوب کابینت زمینی و جزیره:</div>
                      <div className="flex gap-2">
                        {[
                          { name: 'گردو آمریکایی', hex: '#4A2E1B' },
                          { name: 'بلوط گرم', hex: '#7C5333' },
                          { name: 'مشکی سوپرمات', hex: '#22201E' },
                        ].map((bw) => (
                          <button
                            key={bw.hex}
                            type="button"
                            onClick={() => setBaseWood(bw)}
                            className={`flex-1 py-2 px-2 rounded-lg border font-bold cursor-pointer ${
                              baseWood.hex === bw.hex
                                ? 'bg-[#4A2E1B] text-[#F6E27A] border-[#D4AF37]'
                                : 'bg-white text-[#4A2E1B] border-[#E6DEC8]'
                            }`}
                          >
                            {bw.name}
                          </button>
                        ))}
                      </div>
                    </div>

                    <div>
                      <div className="font-bold text-[#4A2E1B] mb-1.5">۴. دمای لاین نوری زیر کابینت:</div>
                      <div className="flex gap-1.5">
                        {(['3000K', '4000K', '6000K'] as const).map((k) => (
                          <button
                            key={k}
                            type="button"
                            onClick={() => setLedKelvin(k)}
                            className={`flex-1 py-2 rounded-lg border font-mono-tabular font-bold cursor-pointer ${
                              ledKelvin === k
                                ? 'bg-amber-500 text-black border-amber-600'
                                : 'bg-white text-[#4A2E1B] border-[#E6DEC8]'
                            }`}
                          >
                            {k}
                          </button>
                        ))}
                      </div>
                    </div>
                  </div>
                </div>

                {/* Live Architectural Elevation Render */}
                <div className="lg:col-span-6 rounded-2xl overflow-hidden border-2 border-[#4A2E1B] shadow-md bg-white">
                  {/* Upper Cabinets (40% Union) */}
                  <div
                    className="h-24 px-4 flex items-center justify-between border-b-2 border-[#D4AF37]"
                    style={{ backgroundColor: upperColor.hex, color: upperColor.textHex }}
                  >
                    <span className="text-xs font-extrabold">کابینت هوایی (۴۰٪ اتحادیه): {upperColor.name}</span>
                    <span className="text-[11px] font-mono-tabular opacity-80">Blum Soft-Close</span>
                  </div>

                  {/* Under-Cabinet LED Line Glow */}
                  <div
                    className="h-2.5 w-full transition-all"
                    style={{
                      background:
                        ledKelvin === '3000K'
                          ? 'linear-gradient(90deg, #F59E0B, #FDE68A, #F59E0B)'
                          : ledKelvin === '4000K'
                          ? 'linear-gradient(90deg, #FEF3C7, #FFFFFF, #FEF3C7)'
                          : 'linear-gradient(90deg, #BAE6FD, #F0F9FF, #BAE6FD)',
                    }}
                  />

                  {/* Backsplash & 5cm Slab Countertop */}
                  <div
                    className="h-20 px-4 flex items-center justify-between border-b-4 border-[#D4AF37]"
                    style={{ background: slabStyle.bg }}
                  >
                    <span className="px-2.5 py-1 rounded-md bg-black/65 text-[#F6E27A] text-xs font-bold">
                      بین‌کابینتی و صفحه ۵ سانتی: {slabStyle.name}
                    </span>
                    <span className="px-2 py-0.5 rounded bg-black/65 text-white text-[11px] font-mono-tabular">
                      LED {ledKelvin}
                    </span>
                  </div>

                  {/* Base Cabinets & Walnut Island (60% Union) */}
                  <div
                    className="h-28 px-4 flex items-center justify-between text-white"
                    style={{ backgroundColor: baseWood.hex }}
                  >
                    <div>
                      <div className="text-xs font-extrabold text-[#F6E27A]">
                        کابینت زمینی و جزیره (۶۰٪ اتحادیه): {baseWood.name}
                      </div>
                      <div className="text-[11px] text-white/80 mt-0.5">
                        دستگیره و پاخور طلای ۲۴ عیار (#D4AF37) · برند: {brandConfig.brandName}
                      </div>
                    </div>
                    <span className="text-xs font-mono-tabular px-2.5 py-1 rounded-lg bg-black/30 border border-[#D4AF37]/50 text-[#F6E27A]">
                      10Y Warranty
                    </span>
                  </div>
                </div>
              </div>
            </div>

            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="p-6 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8] space-y-4">
              <h3 className="text-lg font-bold text-[#4A2E1B] flex items-center gap-2">
                <Award className="w-5 h-5 text-[#D4AF37]" />
                <span>نقد مهندسی و کالبدشکافی نقاط قوت و چالش‌های بازار دکوراسیون</span>
              </h3>
              <ul className="space-y-3 text-xs sm:text-sm text-[#2A1A10] leading-relaxed">
                <li className="p-3 bg-white rounded-xl border border-[#E6DEC8]">
                  <strong className="text-[#4A2E1B] block mb-1">
                    ۱. نقد برنامه‌های سنتی کابینت‌سازی (چرا مشتریان فراری می‌شوند؟):
                  </strong>
                  اکثر نرم‌افزارهای فعلی بازار یا بیش از حد مهندسی و پیچیده هستند (مثل CutMaster که مشتری عادی متوجه آن نمی‌شود) یا فقط یک کاتالوگ عکس بدون قیمت‌اند. **نقطه قوت DecorMate VIP** این است که در ۱۰ ثانیه اول، «قیمت شفاف متراژ اتحادیه + قسط ماهانه چک صیادی» را به مشتری نهایی می‌دهد و همزمان «تعداد ورق MDF و یراق مصرفی» را به استادکار نشان می‌دهد.
                </li>
                <li className="p-3 bg-white rounded-xl border border-[#E6DEC8]">
                  <strong className="text-[#4A2E1B] block mb-1">
                    ۲. چالش نبود اینترنت در پروژه‌های ساختمانی نیمه‌کاره (حل شده با معماری آفلاین):
                  </strong>
                  کابینت‌سازان اغلب در زیرزمین‌ها یا ساختمان‌های نوساز بدون آنتن متراژگیری می‌کنند. به همین دلیل، کل موتور محاسبه متراژ، ذخیره پیش‌فاکتورها، هوش مصنوعی معمار، شخصی‌سازی برند و استوری‌ساز ۱۰۸۰×۱۹۲۰ در این نسخه **۱۰۰٪ آفلاین** طراحی شده است.
                </li>
                <li className="p-3 bg-white rounded-xl border border-[#E6DEC8]">
                  <strong className="text-[#4A2E1B] block mb-1">
                    ۳. نقد روانشناختی فروش (اهمیت حالت تمرکز ADHD و خوانش صوتی):
                  </strong>
                  بسیاری از کارفرمایان هنگام دیدن اعداد چندصد میلیونی بازسازی دچار استرس و سردرگمی تصمیم‌گیری می‌شوند. افزودن «حالت تمرکز گام‌به‌گام ADHD» و «خوانش صوتی پیش‌فاکتور»، اصطکاک ذهنی مشتری را از بین برده و اعتماد به نفس خرید را دوچندان می‌کند.
                </li>
              </ul>
            </div>

            <div className="p-6 rounded-2xl bg-[#FAF7F2] border border-[#D4AF37] space-y-4">
              <h3 className="text-lg font-bold text-[#4A2E1B] flex items-center gap-2">
                <Lightbulb className="w-5 h-5 text-[#D4AF37]" />
                <span>۴ قابلیت خلاقانه و پول‌ساز که در همین نسخه برایتان پیاده‌سازی کردیم:</span>
              </h3>
              <ul className="space-y-3 text-xs sm:text-sm text-[#2A1A10] leading-relaxed">
                <li className="p-3 bg-white rounded-xl border border-[#E6DEC8]">
                  <strong className="text-emerald-700 block mb-1">
                    ✨ نوآوری ۱: تخمین‌گر هوشمند تعداد ورق MDF و یراق بلوم در پیش‌فاکتور (BOM Estimator):
                  </strong>
                  در ماشین‌حساب بالا، علاوه بر قیمت مشتری، سیستم به صورت خودکار محاسبه می‌کند که برای متراژ واردشده دقیقاً **چند ورق ام‌دی‌اف ۱۸۳×۳۶۶**، **چند عدد لولا آرام‌بند بلوم** و **چند متر صفحه ۵ سانتی** نیاز است!
                </li>
                <li className="p-3 bg-white rounded-xl border border-[#E6DEC8]">
                  <strong className="text-emerald-700 block mb-1">
                    ✨ نوآوری ۲: دفترچه آرشیو آفلاین پیش‌فاکتورهای مشتریان (Offline CRM):
                  </strong>
                  کابینت‌ساز می‌تواند سر ساختمان بدون اینترنت، پیش‌فاکتور آقای رضایی یا خانم محمدی را با یک کلیک ذخیره کند و هر زمان خواست با یک کلیک آن را بازیابی یا چاپ نماید.
                </li>
                <li className="p-3 bg-white rounded-xl border border-[#E6DEC8]">
                  <strong className="text-emerald-700 block mb-1">
                    ✨ نوآوری ۳: موتور شخصی‌سازی آنی ۵ ثانیه‌ای (Live White-Label Demo):
                  </strong>
                  به جای اینکه ویزیتور به کابینت‌ساز بگوید «قرار است برایتان برنامه بسازیم»، در ۵ ثانیه اسم نمایشگاه او را وارد می‌کند و برنامه آماده را جلوی چشمش تحویل می‌دهد.
                </li>
                <li className="p-3 bg-white rounded-xl border border-[#E6DEC8]">
                  <strong className="text-emerald-700 block mb-1">
                    ✨ نوآوری ۴: رندر گرافیکی آفلاین استوری‌ساز (Zero-Internet Canvas Art):
                  </strong>
                  حتی اگر اینترنت به کلی قطع باشد، استوری‌ساز یک طرح معماری چوب گردو و اسلب مرمر با طلای ۲۴ عیار به صورت برداری روی پوستر ۱۰۸۰×۱۹۲۰ ترسیم می‌کند تا هیچ‌گاه عکس شکسته ایجاد نشود.
                </li>
              </ul>
            </div>
            </div>
          </div>
        )}
      </div>
    </section>
  );
};
