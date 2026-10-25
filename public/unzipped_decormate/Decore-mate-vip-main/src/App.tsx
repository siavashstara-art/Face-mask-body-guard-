import React, { useEffect, useState } from 'react';
import {
  Calculator,
  Volume2,
  VolumeX,
  Accessibility,
  Brain,
  Contrast,
  Type as TypeIcon,
  CheckCircle2,
  Sparkles,
  Camera,
  MessageCircle,
  Smartphone,
  Rocket,
  Crown,
  Handshake,
  Bot,
  Send,
  ShieldCheck,
  Layers,
  Eye,
  EyeOff,
  ArrowDown,
  Ruler,
  FileCheck2,
  Building2,
} from 'lucide-react';
import {
  CurrencyCode,
  LanguageCode,
  LANGUAGES,
  CURRENCIES,
  PROJECT_TYPES,
  MATERIALS,
  SHOWCASE_PRODUCTS,
  UI_STRINGS,
  ShowcaseProduct,
  formatPrice,
} from './data/decorData';
import { Dashboard } from './components/Dashboard';
import { StoryMakerModal } from './components/StoryMakerModal';
import {
  WhiteLabelAffiliateSection,
  CustomBrandConfig,
} from './components/WhiteLabelAffiliateSection';
import {
  VipCommercialEnterpriseSuite,
  TenantExtendedConfig,
} from './components/VipCommercialEnterpriseSuite';
import {
  SecretVisitorControlBar,
  DedicatedGuildPortal,
  ActiveGuildId,
  downloadGoldFoilInvoicePng,
} from './components/MultiGuildDedicatedPortal';
import { TavanaAmbassadorAcademySimulator } from './components/TavanaAmbassadorAcademySimulator';
import {
  GithubPushModal,
  VipSubscriptionModal,
  AffiliateWhiteLabelModal,
  PWAInstallModal,
} from './components/Modals';
import { usePWAInstall } from './hooks/usePWAInstall';

const makeSlugFromBrand = (text: string) =>
  text
    .trim()
    .toLowerCase()
    .replace(/\s+/g, '-')
    .replace(/[^a-z0-9\u0600-\u06FF-]/g, '') || 'default-decor-vip';

const DEFAULT_TENANT_CONFIG: TenantExtendedConfig = {
  slug: 'default-decor-vip',
  brandName: 'DecorMate VIP | دکورمِیت',
  managerName: 'مدیریت مجموعه دکورمِیت',
  tagline: 'اکوسیستم آفرینش | شهر جدید نیومتاورسیتی جهان | توان استیج FBNM',
  phone: '0912-000-0000',
  whatsapp: '989120000000',
  instagram: '@DecorMate.VIP',
  city: 'تهران · اربیل · دبی · استانبول',
  priceMultiplier: 1.0,
  refCode: 'VIP-25',
  licenseMode: 'visitor_demo',
};

export default function App() {
  // Language & Currency State
  const [lang, setLang] = useState<LanguageCode>('fa');
  const [currency, setCurrency] = useState<CurrencyCode>('IRT');

  // Accessibility & ADHD State
  const [showAccessMenu, setShowAccessMenu] = useState(false);
  const [adhdFocusMode, setAdhdFocusMode] = useState(false);
  const [highContrast, setHighContrast] = useState(false);
  const [fontScale, setFontScale] = useState<'normal' | 'large' | 'xlarge'>('normal');
  const [largeTouchTargets, setLargeTouchTargets] = useState(false);
  const [isSpeaking, setIsSpeaking] = useState(false);

  // ADHD Step-by-Step Wizard Tracker
  const [adhdStep, setAdhdStep] = useState<1 | 2 | 3>(1);

  // Multi-Guild Chameleon & Secret Visitor Lock State (Supports both internal IDs & ForoshYar ProductIds)
  const [activeGuild, setActiveGuild] = useState<ActiveGuildId>(() => {
    if (typeof window !== 'undefined') {
      const rawGuild = new URLSearchParams(window.location.search).get('guild');
      const productIdMap: Record<string, ActiveGuildId> = {
        DECORMATE: 'cabinet_decor',
        SLABMATE: 'ceramics_luxury',
        FURNIMATE: 'furniture_bridal',
        SALONMATE: 'beauty_salon',
        AUTOBARTER: 'auto_barter',
        TANARA: 'dental_aesthetic',
        TALAYAR: 'gold_jewelry',
        EVENTMATE: 'wedding_venue',
      };
      if (rawGuild && productIdMap[rawGuild.toUpperCase()]) {
        return productIdMap[rawGuild.toUpperCase()];
      }
      const g = rawGuild as ActiveGuildId;
      if (
        g &&
        [
          'cabinet_decor',
          'ceramics_luxury',
          'furniture_bridal',
          'beauty_salon',
          'auto_barter',
          'dental_aesthetic',
          'gold_jewelry',
          'wedding_venue',
        ].includes(g)
      ) {
        return g;
      }
    }
    return 'cabinet_decor';
  });

  // Visitor Secret Lock: When false (Client Clean View), other guilds & visitor training are hidden so the buyer sees a 100% pure app!
  const [visitorModeUnlocked, setVisitorModeUnlocked] = useState<boolean>(() => {
    if (typeof window !== 'undefined') {
      const mode = new URLSearchParams(window.location.search).get('mode');
      if (mode === 'client') return false;
      if (mode === 'visitor') return true;
    }
    return true;
  });

  const [externalRepos, setExternalRepos] = useState<Record<string, string>>(() => {
    try {
      const raw = localStorage.getItem('decormate_external_repos_v1');
      return raw ? JSON.parse(raw) : {};
    } catch {
      return {};
    }
  });

  const handleUpdateExternalRepos = (next: Record<string, string>) => {
    setExternalRepos(next);
    try {
      localStorage.setItem('decormate_external_repos_v1', JSON.stringify(next));
    } catch {}
  };

  // Isolated White-Label Tenant & URL Skin State (?tenant=...&manager=...&city=...&phone=...&ref=...)
  const [tenantConfig, setTenantConfig] = useState<TenantExtendedConfig>(() => {
    try {
      if (typeof window !== 'undefined') {
        const params = new URLSearchParams(window.location.search);
        const urlTenant = params.get('tenant');
        const urlManager = params.get('manager');
        const urlCity = params.get('city');
        const urlPhone = params.get('phone');
        const urlTagline = params.get('tagline');
        const urlMultiplier = params.get('multiplier');
        const urlRef = params.get('ref');

        if (urlTenant) {
          const slug = makeSlugFromBrand(urlTenant);
          const existingTenantRaw = localStorage.getItem(`decormate_tenant_cfg_${slug}`);
          const existingTenant = existingTenantRaw ? JSON.parse(existingTenantRaw) : {};
          const phoneVal = urlPhone || existingTenant.phone || DEFAULT_TENANT_CONFIG.phone;
          const merged: TenantExtendedConfig = {
            ...DEFAULT_TENANT_CONFIG,
            ...existingTenant,
            slug,
            brandName: urlTenant,
            managerName: urlManager || existingTenant.managerName || 'مدیریت محترم واحد صنفی',
            city: urlCity || existingTenant.city || 'تهران',
            phone: phoneVal,
            whatsapp: phoneVal.replace(/\D/g, '').replace(/^0/, '98') || '989120000000',
            tagline: urlTagline || existingTenant.tagline || DEFAULT_TENANT_CONFIG.tagline,
            priceMultiplier: urlMultiplier
              ? Number(urlMultiplier) || 1.0
              : existingTenant.priceMultiplier || 1.0,
            refCode: urlRef || existingTenant.refCode || 'VIP-25',
            licenseMode: 'visitor_demo',
          };
          localStorage.setItem(`decormate_tenant_cfg_${slug}`, JSON.stringify(merged));
          localStorage.setItem('decormate_active_slug_v1', slug);
          return merged;
        }

        const activeSlug = localStorage.getItem('decormate_active_slug_v1') || 'default-decor-vip';
        const savedTenant = localStorage.getItem(`decormate_tenant_cfg_${activeSlug}`);
        if (savedTenant) {
          return { ...DEFAULT_TENANT_CONFIG, ...JSON.parse(savedTenant) };
        }
      }
      const savedLegacy = localStorage.getItem('decormate_brand_config_v1');
      return savedLegacy
        ? { ...DEFAULT_TENANT_CONFIG, ...JSON.parse(savedLegacy) }
        : DEFAULT_TENANT_CONFIG;
    } catch {
      return DEFAULT_TENANT_CONFIG;
    }
  });

  const brandConfig: CustomBrandConfig = tenantConfig;

  const handleUpdateTenantConfig = (newCfg: TenantExtendedConfig) => {
    const slug = makeSlugFromBrand(newCfg.brandName || newCfg.slug);
    const normalized: TenantExtendedConfig = { ...newCfg, slug };
    setTenantConfig(normalized);
    try {
      localStorage.setItem(`decormate_tenant_cfg_${slug}`, JSON.stringify(normalized));
      localStorage.setItem('decormate_active_slug_v1', slug);
      localStorage.setItem('decormate_brand_config_v1', JSON.stringify(normalized));
    } catch {}
  };

  const handleUpdateBrandConfig = (newCfg: CustomBrandConfig) => {
    handleUpdateTenantConfig({
      ...tenantConfig,
      ...newCfg,
      slug: makeSlugFromBrand(newCfg.brandName),
    });
  };

  const handleResetBrandConfig = () => {
    setTenantConfig(DEFAULT_TENANT_CONFIG);
    try {
      localStorage.setItem('decormate_active_slug_v1', DEFAULT_TENANT_CONFIG.slug);
      localStorage.removeItem('decormate_brand_config_v1');
    } catch {}
  };

  // Sync active tenant name with document.title automatically
  useEffect(() => {
    if (typeof document !== 'undefined') {
      document.title = `${tenantConfig.brandName} | پیش‌فاکتور اتحادیه، چک صیادی و دکوراسیون (${tenantConfig.city})`;
    }
  }, [tenantConfig.brandName, tenantConfig.city]);

  // Calculator State
  const [projectTypeId, setProjectTypeId] = useState<string>(PROJECT_TYPES[0].id);
  const [materialId, setMaterialId] = useState<string>(MATERIALS[1].id); // Default Neoclassical
  const [lengthMeters, setLengthMeters] = useState<number>(8);
  const [ceilingHeightCm, setCeilingHeightCm] = useState<number>(280);
  const [downPaymentPct, setDownPaymentPct] = useState<number>(30); // Default 30% Cash Down Payment
  const [checkMonths, setCheckMonths] = useState<number>(6); // Supports 3 to 12 Purple Sayadi Checks
  const [includeBlumAndSlab, setIncludeBlumAndSlab] = useState<boolean>(true);
  // Central Bank Purple Sayadi Check Color Inquiry Simulator State
  const [sayadiCheckId, setSayadiCheckId] = useState<string>('9104827563019482');
  const [sayadiStatus, setSayadiStatus] = useState<'white' | 'yellow' | 'red'>('white');
  // Inflation-Shield Price Lock Guarantee State
  const [inflationLockActive, setInflationLockActive] = useState<boolean>(true);
  const [inflationLockCode] = useState<string>('INF-SHIELD-1405-884');
  // Cost-Split Slider State (Between Partners / Builder & Owner / Families)
  const [partnerASharePct, setPartnerASharePct] = useState<number>(50);
  const [clientNameInput, setClientNameInput] = useState<string>('');
  const [savedQuotes, setSavedQuotes] = useState<
    Array<{
      id: string;
      clientName: string;
      projectTypeId: string;
      materialId: string;
      lengthMeters: number;
      ceilingHeightCm: number;
      checkMonths: number;
      totalCashToman: number;
      createdAt: string;
    }>
  >(() => {
    try {
      const raw = localStorage.getItem(`decormate_tenant_quotes_${tenantConfig.slug}`);
      if (raw) return JSON.parse(raw);
      const fallbackRaw = localStorage.getItem('decormate_offline_quotes_v1');
      return fallbackRaw ? JSON.parse(fallbackRaw) : [];
    } catch {
      return [];
    }
  });

  // Reload isolated quotes when tenant slug changes
  useEffect(() => {
    try {
      const raw = localStorage.getItem(`decormate_tenant_quotes_${tenantConfig.slug}`);
      setSavedQuotes(raw ? JSON.parse(raw) : []);
    } catch {
      setSavedQuotes([]);
    }
  }, [tenantConfig.slug]);

  // Showcase State
  const [categoryFilter, setCategoryFilter] = useState<string>('all');
  const [showWholesaleOnCards, setShowWholesaleOnCards] = useState<boolean>(true);
  const [brokenImages, setBrokenImages] = useState<Record<string, boolean>>({});
  const [storyModalProduct, setStoryModalProduct] = useState<ShowcaseProduct | null>(null);

  // Modals State
  const [githubModalOpen, setGithubModalOpen] = useState(false);
  const [vipModalOpen, setVipModalOpen] = useState(false);
  const [affiliateModalOpen, setAffiliateModalOpen] = useState(false);
  const [pwaModalOpen, setPwaModalOpen] = useState(false);

  // AI Architect State
  const [aiPrompt, setAiPrompt] = useState('');
  const [aiMode, setAiMode] = useState<'design' | 'caption'>('design');
  const [aiReply, setAiReply] = useState<string>('');
  const [aiEngineBadge, setAiEngineBadge] = useState<string>('');
  const [aiLoading, setAiLoading] = useState(false);

  const { install: triggerPWAInstall } = usePWAInstall();

  // Sync HTML dir, lang, and Accessibility classes
  const currentLangMeta = LANGUAGES.find((l) => l.code === lang) || LANGUAGES[0];
  const isRtl = currentLangMeta.dir === 'rtl';
  const t = UI_STRINGS[lang] || UI_STRINGS.fa;

  useEffect(() => {
    const htmlEl = document.documentElement;
    htmlEl.dir = currentLangMeta.dir;
    htmlEl.lang = currentLangMeta.code;

    htmlEl.classList.remove('font-scale-normal', 'font-scale-large', 'font-scale-xlarge');
    htmlEl.classList.add(`font-scale-${fontScale}`);

    if (highContrast) {
      htmlEl.classList.add('high-contrast-mode');
    } else {
      htmlEl.classList.remove('high-contrast-mode');
    }

    if (adhdFocusMode) {
      htmlEl.classList.add('adhd-focus-mode');
    } else {
      htmlEl.classList.remove('adhd-focus-mode');
    }

    if (largeTouchTargets || fontScale !== 'normal') {
      htmlEl.classList.add('large-touch-targets');
    } else {
      htmlEl.classList.remove('large-touch-targets');
    }
  }, [lang, currentLangMeta, fontScale, highContrast, adhdFocusMode, largeTouchTargets]);

  // Text-to-Speech (TTS) Screen Reader helper
  const speakText = (textToSpeak: string) => {
    if (typeof window === 'undefined' || !('speechSynthesis' in window)) return;
    window.speechSynthesis.cancel();
    const utterance = new SpeechSynthesisUtterance(textToSpeak);
    utterance.lang = currentLangMeta.speechLang;
    utterance.rate = 0.95;
    utterance.onstart = () => setIsSpeaking(true);
    utterance.onend = () => setIsSpeaking(false);
    utterance.onerror = () => setIsSpeaking(false);
    window.speechSynthesis.speak(utterance);
  };

  const stopSpeaking = () => {
    if (typeof window !== 'undefined' && 'speechSynthesis' in window) {
      window.speechSynthesis.cancel();
      setIsSpeaking(false);
    }
  };

  // Union Formula Calculation Logic
  const selectedProject =
    PROJECT_TYPES.find((p) => p.id === projectTypeId) || PROJECT_TYPES[0];
  const selectedMaterial =
    MATERIALS.find((m) => m.id === materialId) || MATERIALS[0];

  // Height coefficient: standard ceiling is 270cm. Every 10cm above 270cm adds 3.5% for stepped crown/upper cabinet height
  const heightCoefficient =
    ceilingHeightCm > 270 ? 1 + ((ceilingHeightCm - 270) / 10) * 0.035 : 1.0;

  // Countertop 5cm & Blum Austria package adds 12%
  const hardwareCoefficient = includeBlumAndSlab ? 1.12 : 1.0;

  const unitPriceToman = Math.round(
    selectedMaterial.basePricePerMeterToman *
      selectedProject.unionBaseRatio *
      heightCoefficient *
      hardwareCoefficient *
      brandConfig.priceMultiplier
  );

  const totalGrossToman = Math.round(unitPriceToman * lengthMeters);
  const baseCabinet60Toman = Math.round(totalGrossToman * 0.6);
  const wallCabinet40Toman = Math.round(totalGrossToman * 0.4);

  // Cash price has 5% special discount
  const totalCashPriceToman = Math.round(totalGrossToman * 0.95);
  // Configurable Down Payment (e.g. 30% or 40%) + Remaining balance split across 3..12 Purple Sayadi checks
  const downPayment40Toman = Math.round(totalGrossToman * (downPaymentPct / 100));
  const remaining60Toman = totalGrossToman - downPayment40Toman;
  const eachCheckAmountToman = Math.round(remaining60Toman / Math.max(1, checkMonths));

  // Cost-Split calculation (Between Party A & Party B / Builder & Owner)
  const partnerBSharePct = 100 - partnerASharePct;
  const partnerATotalToman = Math.round(totalGrossToman * (partnerASharePct / 100));
  const partnerBTotalToman = totalGrossToman - partnerATotalToman;

  // Generate Solar Hijri Due Date Schedule Table for Purple Sayadi Checks
  const sayadiCheckSchedule = Array.from({ length: checkMonths }, (_, idx) => {
    const dueDate = new Date();
    dueDate.setMonth(dueDate.getMonth() + idx + 1);
    let solarDateStr = `${idx + 1} ماه بعد`;
    try {
      solarDateStr = dueDate.toLocaleDateString('fa-IR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
      });
    } catch {}
    return {
      checkNumber: idx + 1,
      dueDateFa: solarDateStr,
      serialCode: `${sayadiCheckId.slice(0, 8)}-${101 + idx}`,
      amountToman: eachCheckAmountToman,
    };
  });

  const sayadiStatusMeta =
    sayadiStatus === 'white'
      ? {
          badge: 'وضعیت سفید بانک مرکزی (بدون چک برگشتی)',
          score: '895 / 1000 (رتبه اعتباری A+ عالی)',
          colorClass: 'bg-emerald-50 border-emerald-400 text-emerald-950',
        }
      : sayadiStatus === 'yellow'
      ? {
          badge: 'وضعیت زرد بانک مرکزی (۱ فقره سابقه تسویه‌شده)',
          score: '645 / 1000 (رتبه اعتباری B — نیاز به ضامن)',
          colorClass: 'bg-amber-50 border-amber-400 text-amber-950',
        }
      : {
          badge: 'وضعیت قرمز بانک مرکزی (دارای چک برگشتی فعال)',
          score: '310 / 1000 (رتبه C — فقط تسویه نقدی مجاز است)',
          colorClass: 'bg-red-50 border-red-400 text-red-950',
        };

  // Engineering Sheet & Hardware Estimator (Creative Value-Add)
  const estimatedMdfSheets = Math.max(2, Math.ceil(lengthMeters * 1.65));
  const estimatedBlumHinges = Math.max(6, Math.ceil(lengthMeters * 4.5));
  const estimatedTandemDrawers = Math.max(2, Math.ceil(lengthMeters * 0.8));
  const estimatedCountertopMeters = Math.round(lengthMeters * 0.65 * 10) / 10;

  const handleReadInvoiceAloud = () => {
    const projectTitle = selectedProject.names[lang] || selectedProject.names.fa;
    const matTitle = selectedMaterial.names[lang] || selectedMaterial.names.fa;
    const cashFormatted = formatPrice(totalCashPriceToman, currency, lang);
    const downFormatted = formatPrice(downPayment40Toman, currency, lang);
    const checkFormatted = formatPrice(eachCheckAmountToman, currency, lang);

    const speechText =
      lang === 'fa' || lang === 'ku'
        ? `پیش‌فاکتور رسمی ${brandConfig.brandName}. پروژه ${projectTitle}، با متریال ${matTitle}، به متراژ ${lengthMeters} متر. مبلغ کل نقد با پنج درصد تخفیف: ${cashFormatted}. شرایط اقساط چک صیادی بنفش: پیش‌پرداخت نقد ${downPaymentPct} درصد معادل ${downFormatted}، و ${checkMonths} فقره چک صیادی ماهانه هر کدام به مبلغ ${checkFormatted}.`
        : `${brandConfig.brandName} Official Pre-Invoice. ${projectTitle}, material ${matTitle}, length ${lengthMeters} meters. Total cash price: ${cashFormatted}. Down payment: ${downFormatted}, plus ${checkMonths} monthly checks of ${checkFormatted} each.`;

    speakText(speechText);
  };

  const handleSendInvoiceToWhatsApp = () => {
    const projectTitle = selectedProject.names[lang] || selectedProject.names.fa;
    const matTitle = selectedMaterial.names[lang] || selectedMaterial.names.fa;
    const scheduleLines = sayadiCheckSchedule
      .slice(0, 6)
      .map(
        (c) =>
          `   • چک ${c.checkNumber} (${c.dueDateFa}): ${formatPrice(c.amountToman, currency, lang)}`
      )
      .join('\n');

    const msg = `👑 *پیش‌فاکتور رسمی طلاکوب سامانه هوشمند ${brandConfig.brandName}*
👤 مدیریت: ${tenantConfig.managerName} | 📍 شهر: ${tenantConfig.city}
━━━━━━━━━━━━━━━━━━
📐 *نوع پروژه:* ${projectTitle}
🪵 *متریال انتخابی:* ${matTitle}
📏 *متراژ:* ${lengthMeters} (${selectedProject.unitLabel[lang] || selectedProject.unitLabel.fa}) | *ارتفاع سقف:* ${ceilingHeightCm} سم
🔩 *یراق بلوم اتریش و صفحه کوارتز ۵ سانتی:* ${includeBlumAndSlab ? 'بله (فول اتریشی)' : 'استاندارد پایه'}
📦 *تخمین مهندسی متریال:* ${estimatedMdfSheets} ورق MDF + ${estimatedBlumHinges} لولا بلوم + ${estimatedCountertopMeters} متر صفحه
━━━━━━━━━━━━━━━━━━
💰 *مبلغ کل نقد (با ۵٪ تخفیف تسویه نقد):* ${formatPrice(totalCashPriceToman, currency, lang)}
💳 *پیش‌پرداخت نقدی قرارداد (${downPaymentPct}٪):* ${formatPrice(downPayment40Toman, currency, lang)}
🟣 *تقسیط چک صیادی بنفش (${checkMonths} فقره ماهانه):* هر چک ${formatPrice(eachCheckAmountToman, currency, lang)}
📅 *جدول سررسید چک‌های صیادی بنفش:*
${scheduleLines}${checkMonths > 6 ? `\n   • ... و ${checkMonths - 6} فقره چک ماهانه دیگر` : ''}
━━━━━━━━━━━━━━━━━━
🏦 *گواهی استعلام چک صیادی بانک مرکزی:* ${sayadiStatusMeta.badge} (شناسه: ${sayadiCheckId})
🛡️ *گواهی قفل ضدتورم ورق و یراق:* ${
      inflationLockActive
        ? `فعال (کد رهگیری ${inflationLockCode} — تثبیت ۱۰۰٪ نرخ ورق، کوارتز و یراق بلوم از لحظه بیعانه تا تحویل)`
        : 'غیرفعال'
    }
⚖️ *تسهیم شفاف هزینه:* سهم طرف اول (${partnerASharePct}٪): ${formatPrice(
      partnerATotalToman,
      currency,
      lang
    )} | سهم طرف دوم (${partnerBSharePct}٪): ${formatPrice(partnerBTotalToman, currency, lang)}
🎖️ *کد سفیر / مرجع:* ${tenantConfig.refCode}
━━━━━━━━━━━━━━━━━━
📞 تماس و هماهنگی بازدید سه‌بعدی: ${brandConfig.phone}`;

    const cleanWa = brandConfig.whatsapp.replace(/\D/g, '') || '989120000000';
    window.location.href = `https://wa.me/${cleanWa}?text=${encodeURIComponent(msg)}`;
  };

  const handleInstallClick = async () => {
    const outcome = await triggerPWAInstall();
    if (outcome === 'guide_needed') {
      setPwaModalOpen(true);
    }
  };

  const handleSaveOfflineQuote = () => {
    const newEntry = {
      id: String(Date.now()),
      clientName: clientNameInput.trim() || `پروژه مشتری (${lengthMeters} متر)`,
      projectTypeId,
      materialId,
      lengthMeters,
      ceilingHeightCm,
      checkMonths,
      totalCashToman: totalCashPriceToman,
      createdAt: new Date().toLocaleDateString('fa-IR'),
    };
    const updated = [newEntry, ...savedQuotes].slice(0, 12);
    setSavedQuotes(updated);
    setClientNameInput('');
    try {
      localStorage.setItem(`decormate_tenant_quotes_${tenantConfig.slug}`, JSON.stringify(updated));
      localStorage.setItem('decormate_offline_quotes_v1', JSON.stringify(updated));
    } catch {}
  };

  const handleDeleteOfflineQuote = (id: string) => {
    const updated = savedQuotes.filter((q) => q.id !== id);
    setSavedQuotes(updated);
    try {
      localStorage.setItem(`decormate_tenant_quotes_${tenantConfig.slug}`, JSON.stringify(updated));
      localStorage.setItem('decormate_offline_quotes_v1', JSON.stringify(updated));
    } catch {}
  };

  const handleAskAiArchitect = async (customPromptText?: string, customMode?: 'design' | 'caption') => {
    const finalPrompt = customPromptText ?? aiPrompt;
    const finalMode = customMode ?? aiMode;
    if (!finalPrompt.trim()) return;

    setAiLoading(true);
    try {
      const res = await fetch('/api/ai-assistant', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          prompt: finalPrompt,
          mode: finalMode,
          lang,
        }),
      });
      const data = await res.json();
      setAiReply(data.reply || '');
      setAiEngineBadge(
        data.engine === 'cloud-gemini'
          ? '⚡ اتوماسیون ابری Gemini Server (Zero-Touch Vault)'
          : '🏛️ موتور معمار هوشمند خودکار سرور (بدون نیاز به دخالت دستی کلید)'
      );
    } catch {
      // 100% Offline Device Fallback (when in Airplane Mode / No Internet)
      const fallbackText =
        finalMode === 'caption'
          ? `✨ **کپشن اختصاصی اینستاگرام (تولید شده توسط موتور آفلاین ${brandConfig.brandName}):**\n👑 اجرای تخصصی کابینت‌های لوکس چوب گردو و نئوکلاسیک با یراق بلوم اتریش و ۱۰ سال ضمانت کتبی اتحادیه.\n📐 محاسبه آنی متراژ (۶۰٪ زمینی + ۴۰٪ هوایی) + شرایط اقساط با چک صیادی ۱ تا ۶ ماهه!\n📞 تماس و مشاوره: ${brandConfig.phone}`
          : `🏛️ **مشاوره تخصصی معمار هوشمند آفلاین (${brandConfig.brandName}):**\n۱. **ترکیب رنگ پیشنهادی:** بدنه و جزیره چوب گردو گرم (#4A2E1B) + کابینت هوایی سفید صدفی مات (#FAF7F2) + صفحه کوارتز رگه‌طلایی (#D4AF37).\n۲. **نورپردازی استاندارد:** لاین نوری مخفی ۳۰۰۰ کلوین زیر کابینت هوایی با CRI بالای ۹۰.\n۳. **اقساط اتحادیه:** ۴۰٪ پیش‌پرداخت + ۶۰٪ اقساط ۱ تا ۶ ماهه با چک صیادی معتبر.`;
      setAiReply(fallbackText);
      setAiEngineBadge('📴 موتور معمار هوشمند ۱۰۰٪ آفلاین دستگاه (بدون نیاز به اینترنت و کلید)');
    } finally {
      setAiLoading(false);
    }
  };

  const filteredProducts = SHOWCASE_PRODUCTS.filter((item) => {
    if (categoryFilter === 'all') return true;
    if (categoryFilter === 'neoclassic') return item.category === 'neoclassic' || item.category === 'wood';
    if (categoryFilter === 'modern') return item.category === 'enzo' || item.category === 'modern' || item.category === 'membrane';
    if (categoryFilter === 'closet') return item.category === 'closet' || item.category === 'wardrobe';
    if (categoryFilter === 'renovation') return item.category === 'wall_tv' || item.category === 'renovation';
    return true;
  });

  return (
    <div className="min-h-screen bg-[#FAF7F2] text-[#2A1A10] flex flex-col">
      {/* Top Royal Ecosystem Bar */}
      <div className="bg-[#4A2E1B] text-[#FAF7F2] border-b border-[#D4AF37]/50 px-4 py-2 text-xs">
        <div className="max-w-[1440px] mx-auto flex flex-wrap items-center justify-between gap-2">
          <div className="flex items-center gap-2 font-bold text-[#F6E27A]">
            <Crown className="w-4 h-4 text-[#D4AF37] shrink-0" />
            <span>{brandConfig.tagline || t.subtitleEcosystem}</span>
          </div>

          {/* 6-Language & 6-Currency Instant Switchers */}
          <div className="flex flex-wrap items-center gap-2">
            {/* Language Switcher: FA | KU | AR | EN | TR | RU */}
            <div
              className="flex items-center bg-[#352012] border border-[#D4AF37]/40 rounded-lg p-0.5"
              role="group"
              aria-label="Language Switcher"
            >
              {LANGUAGES.map((l) => (
                <button
                  key={l.code}
                  onClick={() => setLang(l.code)}
                  aria-label={`Switch language to ${l.label}`}
                  className={`px-2 py-1 rounded-md text-[11px] font-bold transition-colors cursor-pointer whitespace-nowrap ${
                    lang === l.code
                      ? 'bg-[#D4AF37] text-[#2A1A10]'
                      : 'text-[#FAF7F2]/85 hover:text-white'
                  }`}
                >
                  {l.flag} {l.code.toUpperCase()}
                </button>
              ))}
              <button
                type="button"
                onClick={() => setLang('en')}
                className={`px-2.5 py-1 rounded-md text-[11px] font-extrabold transition-colors cursor-pointer whitespace-nowrap border ${
                  lang === 'en'
                    ? 'bg-[#D4AF37] text-[#2A1A10] border-white'
                    : 'bg-white/10 text-[#F6E27A] border-[#D4AF37]/40 hover:bg-white/20'
                }`}
              >
                🇬🇧 English (International)
              </button>
              <span className="hidden xl:inline-flex items-center px-2 py-0.5 rounded bg-emerald-900/60 text-emerald-200 border border-emerald-500/40 text-[10px] font-bold">
                🌐 هماهنگ با ۱۰ زبان و گویش فروشیار (فارسی، گیلکی، بلوچی، کوردی، آذری، لری، عربی، ترکی، روسی، EN)
              </span>
            </div>

            {/* Currency Switcher: IRT | USD | IQD | AED | TRY | RUB */}
            <div
              className="flex items-center bg-[#352012] border border-[#D4AF37]/40 rounded-lg p-0.5"
              role="group"
              aria-label="Currency Switcher"
            >
              {(Object.keys(CURRENCIES) as CurrencyCode[]).map((cCode) => (
                <button
                  key={cCode}
                  onClick={() => setCurrency(cCode)}
                  aria-label={`Switch currency to ${CURRENCIES[cCode].label}`}
                  className={`px-2 py-1 rounded-md text-[11px] font-mono-tabular font-bold transition-colors cursor-pointer whitespace-nowrap ${
                    currency === cCode
                      ? 'bg-emerald-600 text-white'
                      : 'text-[#FAF7F2]/85 hover:text-white'
                  }`}
                >
                  {CURRENCIES[cCode].label}
                </button>
              ))}
            </div>
          </div>
        </div>
      </div>

      {/* Main Sticky Navigation Header */}
      <header className="sticky top-0 z-40 bg-white/95 backdrop-blur-md border-b border-[#E6DEC8] px-4 sm:px-8 py-3 shadow-xs">
        <div className="max-w-[1440px] mx-auto flex flex-wrap items-center justify-between gap-3">
          {/* Zone 1: Brand Title */}
          <a
            href="#top"
            className="text-lg sm:text-xl font-extrabold tracking-tight text-[#4A2E1B] flex items-center gap-2"
          >
            <span className="w-3 h-3 rounded-full bg-[#D4AF37] inline-block shrink-0" />
            <span>{brandConfig.brandName}</span>
          </a>

          {/* Zone 2: Clean Text Navigation Links + VIP Commercial Playbook Quick Links */}
          <nav
            aria-label="Main Navigation"
            className="hidden xl:flex flex-wrap items-center gap-4 text-xs font-semibold text-[#6F4E37]"
          >
            <a href="#calculator" className="hover:text-[#4A2E1B] hover:underline underline-offset-4 font-bold text-[#4A2E1B]">
              📐 {t.navCalculator}
            </a>
            <a href="#deliverables" className="px-2.5 py-1 rounded-lg bg-amber-50 border border-[#D4AF37] text-[#4A2E1B] font-extrabold hover:bg-[#D4AF37]/20">
              🎁 دستاوردهای خریدار و ROI (#deliverables)
            </a>
            <a href="#visitor-playbook" className="px-2.5 py-1 rounded-lg bg-emerald-50 border border-emerald-400 text-emerald-900 font-extrabold hover:bg-emerald-100">
              🧭 راهنمای ویزیتورها (#visitor-playbook)
            </a>
            <a href="#golden-formula" className="hover:text-[#4A2E1B] hover:underline underline-offset-4 text-amber-800 font-bold">
              🏆 فرمول طلایی فروش
            </a>
            <a href="#invitation-letter" className="hover:text-[#4A2E1B] hover:underline underline-offset-4 text-emerald-700 font-bold">
              📜 دعوت‌نامه طلاکوب و شبا ۲۵٪
            </a>
            <a href="#showcase" className="hover:text-[#4A2E1B] hover:underline underline-offset-4">
              {t.navShowcase}
            </a>
            <a href="#whitelabel-visitor-hub" className="hover:text-[#4A2E1B] hover:underline underline-offset-4">
              شخصی‌سازی برند
            </a>
          </nav>

          {/* Zone 3: Primary Action Buttons & Accessibility Panel Trigger */}
          <div className="flex flex-wrap items-center gap-2">
            {/* ♿ Accessibility & ADHD Floating Menu Trigger */}
            <div className="relative">
              <button
                onClick={() => setShowAccessMenu(!showAccessMenu)}
                aria-expanded={showAccessMenu}
                aria-label="Accessibility and ADHD Focus Menu"
                className={`flex items-center gap-1.5 px-3 py-2 rounded-xl text-xs font-bold border transition-colors cursor-pointer whitespace-nowrap ${
                  adhdFocusMode || highContrast || fontScale !== 'normal'
                    ? 'bg-emerald-600 text-white border-emerald-700'
                    : 'bg-[#FAF7F2] text-[#4A2E1B] border-[#D4AF37] hover:bg-[#4A2E1B] hover:text-[#F6E27A]'
                }`}
              >
                <Accessibility className="w-4 h-4 shrink-0" />
                <span>{t.accessibilityBtn}</span>
              </button>

              {showAccessMenu && (
                <div
                  role="menu"
                  aria-label="Accessibility and ADHD Controls"
                  className={`absolute top-full mt-2 ${
                    isRtl ? 'left-0' : 'right-0'
                  } w-80 sm:w-96 bg-white border-2 border-[#4A2E1B] rounded-2xl shadow-2xl p-4 z-50 space-y-3.5 text-xs`}
                >
                  <div className="flex items-center justify-between border-b border-[#E6DEC8] pb-2">
                    <span className="font-bold text-sm text-[#4A2E1B]">
                      ♿ پنل دسترسی‌پذیری، کم‌بینایان و تمرکز ADHD
                    </span>
                    <button
                      onClick={() => setShowAccessMenu(false)}
                      className="text-[#6F4E37] hover:text-[#4A2E1B] font-bold px-2 py-0.5"
                    >
                      ✕
                    </button>
                  </div>

                  {/* A) ADHD Focus Mode */}
                  <div className="p-3 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8]">
                    <div className="flex items-center justify-between mb-1">
                      <span className="font-bold text-[#4A2E1B] flex items-center gap-1.5">
                        <Brain className="w-4 h-4 text-emerald-600" />
                        <span>{t.adhdModeTitle}</span>
                      </span>
                      <button
                        onClick={() => setAdhdFocusMode(!adhdFocusMode)}
                        className={`px-3 py-1 rounded-lg font-bold cursor-pointer ${
                          adhdFocusMode
                            ? 'bg-emerald-600 text-white'
                            : 'bg-white border border-[#E6DEC8] text-[#4A2E1B]'
                        }`}
                      >
                        {adhdFocusMode ? '✓ فعال' : 'فعال‌سازی'}
                      </button>
                    </div>
                    <p className="text-[11px] text-[#6F4E37] leading-relaxed">{t.adhdModeDesc}</p>
                  </div>

                  {/* B) Persian Text-to-Speech Screen Reader */}
                  <div className="p-3 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8]">
                    <div className="flex items-center justify-between mb-1">
                      <span className="font-bold text-[#4A2E1B] flex items-center gap-1.5">
                        <Volume2 className="w-4 h-4 text-[#D4AF37]" />
                        <span>{t.ttsTitle}</span>
                      </span>
                      <button
                        onClick={() =>
                          isSpeaking ? stopSpeaking() : handleReadInvoiceAloud()
                        }
                        className="px-3 py-1 rounded-lg bg-[#4A2E1B] text-[#F6E27A] font-bold cursor-pointer"
                      >
                        {isSpeaking ? 'توقف صدا' : '🔊 تست خوانش'}
                      </button>
                    </div>
                    <p className="text-[11px] text-[#6F4E37] leading-relaxed">{t.ttsDesc}</p>
                  </div>

                  {/* C) Font Size & 48px+ Large Touch Targets */}
                  <div className="p-3 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8]">
                    <div className="font-bold text-[#4A2E1B] flex items-center gap-1.5 mb-2">
                      <TypeIcon className="w-4 h-4 text-[#4A2E1B]" />
                      <span>{t.fontSizeTitle}</span>
                    </div>
                    <div className="grid grid-cols-3 gap-1.5">
                      {(
                        [
                          { id: 'normal', label: 'عادی (100%)' },
                          { id: 'large', label: 'درشت (115%)' },
                          { id: 'xlarge', label: 'فوق‌درشت (130%)' },
                        ] as const
                      ).map((fs) => (
                        <button
                          key={fs.id}
                          onClick={() => {
                            setFontScale(fs.id);
                            setLargeTouchTargets(fs.id !== 'normal');
                          }}
                          className={`py-2 px-2 rounded-lg font-bold text-[11px] border cursor-pointer ${
                            fontScale === fs.id
                              ? 'bg-[#4A2E1B] text-[#F6E27A] border-[#4A2E1B]'
                              : 'bg-white text-[#2A1A10] border-[#E6DEC8]'
                          }`}
                        >
                          {fs.label}
                        </button>
                      ))}
                    </div>
                  </div>

                  {/* D) High-Contrast Mode */}
                  <div className="p-3 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8] flex items-center justify-between">
                    <span className="font-bold text-[#4A2E1B] flex items-center gap-1.5">
                      <Contrast className="w-4 h-4 text-[#4A2E1B]" />
                      <span>{t.highContrastTitle}</span>
                    </span>
                    <button
                      onClick={() => setHighContrast(!highContrast)}
                      className={`px-3 py-1 rounded-lg font-bold cursor-pointer ${
                        highContrast
                          ? 'bg-black text-yellow-300'
                          : 'bg-white border border-[#E6DEC8] text-[#4A2E1B]'
                      }`}
                    >
                      {highContrast ? '✓ فعال' : 'فعال‌سازی'}
                    </button>
                  </div>
                </div>
              )}
            </div>

            {/* 1-Click PWA Install Button */}
            <button
              onClick={handleInstallClick}
              className="flex items-center gap-1.5 px-3 py-2 rounded-xl bg-[#4A2E1B] hover:bg-[#352012] text-[#F6E27A] text-xs font-bold transition-colors cursor-pointer whitespace-nowrap"
            >
              <Smartphone className="w-4 h-4 shrink-0" />
              <span>{t.btnInstallPWA}</span>
            </button>

            {/* Direct GitHub Push & Auto APK/AAB Button */}
            <button
              onClick={() => setGithubModalOpen(true)}
              className="flex items-center gap-1.5 px-3 py-2 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white text-xs font-bold transition-colors cursor-pointer whitespace-nowrap"
            >
              <Rocket className="w-4 h-4 shrink-0" />
              <span>{t.btnGithubApk}</span>
            </button>

            {/* VIP Tiers Button */}
            <button
              onClick={() => setVipModalOpen(true)}
              className="hidden sm:flex items-center gap-1.5 px-3 py-2 rounded-xl bg-[#D4AF37] hover:bg-[#c59f2d] text-[#2A1A10] text-xs font-bold transition-colors cursor-pointer whitespace-nowrap"
            >
              <Crown className="w-4 h-4 shrink-0" />
              <span>{t.btnVipPlans}</span>
            </button>
          </div>
        </div>
      </header>

      {/* Secret Visitor Multi-Guild Bar (Locks into 100% Clean Buyer View with 1 Click so Buyer Never Sees Other Guilds!) */}
      <SecretVisitorControlBar
        activeGuild={activeGuild}
        onSelectGuild={(g) => {
          setActiveGuild(g);
          if (g === 'ceramics_luxury') {
            setProjectTypeId('porcelain_slab_tile');
            setMaterialId('porcelain_calacatta_slab');
            setCategoryFilter('ceramics_slab');
          } else if (g === 'cabinet_decor') {
            setProjectTypeId('kitchen_cabinet');
            setMaterialId('neoclassic_poly');
            setCategoryFilter('all');
          }
        }}
        visitorModeUnlocked={visitorModeUnlocked}
        onToggleVisitorMode={() => setVisitorModeUnlocked((v) => !v)}
        tenantConfig={tenantConfig}
        onUpdateTenantConfig={handleUpdateTenantConfig}
        externalRepos={externalRepos}
        onUpdateExternalRepos={handleUpdateExternalRepos}
      />

      <main id="top" className="flex-1">
        {/* Dedicated 1-Click Accessibility (Disability/Low-Vision/Motor), ADHD Focus & Automated Gradle APK/AAB Bar */}
        <section
          aria-label="نوار دسترسی سریع ویژه معلولان، کم‌بینایان، تمرکز ADHD و خروجی خودکار اندروید"
          className="px-4 sm:px-8 pt-4 max-w-[1440px] mx-auto"
        >
          <div className="hc-card bg-white border-2 border-[#D4AF37] rounded-2xl p-3.5 flex flex-wrap items-center justify-between gap-3 shadow-xs">
            <div className="flex items-center gap-2 text-xs font-extrabold text-[#4A2E1B]">
              <Accessibility className="w-5 h-5 text-emerald-600 shrink-0" />
              <span>دسترسی سریع ویژه معلولان، تمرکز ADHD و اتوماسیون امن:</span>
            </div>

            <div className="flex flex-wrap items-center gap-2">
              <button
                type="button"
                onClick={() => setAdhdFocusMode(!adhdFocusMode)}
                className={`px-3 py-2 rounded-xl text-xs font-bold border flex items-center gap-1.5 cursor-pointer transition-colors ${
                  adhdFocusMode
                    ? 'bg-emerald-600 text-white border-emerald-700'
                    : 'bg-[#FAF7F2] text-[#4A2E1B] border-[#E6DEC8] hover:border-[#4A2E1B]'
                }`}
              >
                <Brain className="w-4 h-4 shrink-0" />
                <span>{adhdFocusMode ? '✓ حالت تمرکز ADHD فعال' : '🧠 حالت تمرکز ویژه ADHD'}</span>
              </button>

              <button
                type="button"
                onClick={() => (isSpeaking ? stopSpeaking() : handleReadInvoiceAloud())}
                className={`px-3 py-2 rounded-xl text-xs font-bold border flex items-center gap-1.5 cursor-pointer transition-colors ${
                  isSpeaking
                    ? 'bg-amber-500 text-black border-amber-600'
                    : 'bg-[#FAF7F2] text-[#4A2E1B] border-[#E6DEC8] hover:border-[#4A2E1B]'
                }`}
              >
                {isSpeaking ? <VolumeX className="w-4 h-4 shrink-0" /> : <Volume2 className="w-4 h-4 shrink-0" />}
                <span>{isSpeaking ? 'توقف خوانش صوتی' : '🔊 خوانش صوتی پیش‌فاکتور (کم‌بینایان)'}</span>
              </button>

              <button
                type="button"
                onClick={() => {
                  const nextScale =
                    fontScale === 'normal' ? 'large' : fontScale === 'large' ? 'xlarge' : 'normal';
                  setFontScale(nextScale);
                  setLargeTouchTargets(nextScale !== 'normal');
                }}
                className={`px-3 py-2 rounded-xl text-xs font-bold border flex items-center gap-1.5 cursor-pointer transition-colors ${
                  fontScale !== 'normal'
                    ? 'bg-[#4A2E1B] text-[#F6E27A] border-[#4A2E1B]'
                    : 'bg-[#FAF7F2] text-[#4A2E1B] border-[#E6DEC8] hover:border-[#4A2E1B]'
                }`}
              >
                <TypeIcon className="w-4 h-4 shrink-0" />
                <span>
                  🔍 فونت و دکمه لمسی درشت ({fontScale === 'normal' ? 'عادی' : fontScale === 'large' ? 'درشت ۱۱۵٪' : 'فوق‌درشت ۱۳۰٪'})
                </span>
              </button>

              <button
                type="button"
                onClick={() => setHighContrast(!highContrast)}
                className={`px-3 py-2 rounded-xl text-xs font-bold border flex items-center gap-1.5 cursor-pointer transition-colors ${
                  highContrast
                    ? 'bg-black text-yellow-300 border-yellow-400'
                    : 'bg-[#FAF7F2] text-[#4A2E1B] border-[#E6DEC8] hover:border-[#4A2E1B]'
                }`}
              >
                <Contrast className="w-4 h-4 shrink-0" />
                <span>{highContrast ? '✓ کنتراست بالا فعال' : '🌓 کنتراست بالا'}</span>
              </button>

              <button
                type="button"
                onClick={() => setGithubModalOpen(true)}
                className="px-3.5 py-2 rounded-xl text-xs font-bold bg-[#4A2E1B] text-[#F6E27A] border border-[#D4AF37] flex items-center gap-1.5 cursor-pointer"
              >
                <Rocket className="w-4 h-4 text-[#D4AF37] shrink-0" />
                <span>📦 فایل‌های Gradle 8.5 + امضای ریلیز APK/AAB گیت‌هاب</span>
              </button>
            </div>
          </div>
        </section>

        {/* Dedicated Isolated Portal when another guild (Beauty Salon, Auto, Dental, Gold, Wedding) is selected */}
        {activeGuild !== 'cabinet_decor' && activeGuild !== 'ceramics_luxury' ? (
          <DedicatedGuildPortal
            activeGuild={activeGuild}
            tenantConfig={tenantConfig}
            currency={currency}
            lang={lang}
            externalRepoUrl={
              activeGuild === 'auto_barter'
                ? externalRepos.auto
                : activeGuild === 'dental_aesthetic'
                ? externalRepos.dental
                : activeGuild === 'wedding_venue'
                ? externalRepos.wedding
                : undefined
            }
          />
        ) : (
          <>
            {/* 3-Second Hook-Driven Hero Banner (Hidden or Simplified in ADHD Calm Mode) */}
            {!adhdFocusMode && (
          <section
            aria-label="Instant 10-Second Kitchen Cabinet & Wardrobe Price Hook"
            className="px-4 sm:px-8 pt-6 pb-4 max-w-[1440px] mx-auto"
          >
            <div className="hc-card relative overflow-hidden rounded-3xl bg-gradient-to-l from-[#4A2E1B] via-[#382213] to-[#24150B] text-white border-2 border-[#D4AF37] p-6 sm:p-10 shadow-lg">
              <div className="max-w-4xl space-y-4">
                <div className="inline-flex items-center gap-2 text-xs sm:text-sm font-bold text-[#F6E27A]">
                  <Sparkles className="w-4 h-4 text-[#D4AF37]" />
                  <span>{t.hookBannerBadge}</span>
                </div>

                <h1 className="text-2xl sm:text-4xl lg:text-5xl font-extrabold leading-tight text-[#FAF7F2] text-balance">
                  {t.hookBannerTitle}
                </h1>

                <p className="text-xs sm:text-base text-[#FAF7F2]/85 max-w-3xl leading-relaxed">
                  بر اساس ضرایب رسمی اتحادیه کابینت‌سازان (۶۰٪ زمینی + ۴۰٪ هوایی + صفحه شرکتی ۵ سانتی + یراق‌آلات بلوم اتریش) به همراه جدول اقساط چک صیادی ۱ تا ۶ ماهه و تخمین دقیق تعداد ورق MDF مصرفی.
                </p>

                <div className="pt-2 flex flex-wrap items-center gap-3">
                  <a
                    href="#calculator"
                    className="inline-flex items-center gap-2.5 px-6 py-4 rounded-2xl bg-[#D4AF37] hover:bg-[#F6E27A] text-[#2A1A10] font-extrabold text-sm sm:text-base shadow-xl transition-transform active:scale-98 cursor-pointer"
                  >
                    <span>{t.hookBannerCTA}</span>
                    <ArrowDown className="w-5 h-5 shrink-0" />
                  </a>

                  <button
                    onClick={() => setAffiliateModalOpen(true)}
                    className="inline-flex items-center gap-2 px-4 py-4 rounded-2xl bg-white/10 hover:bg-white/20 border border-[#D4AF37]/50 text-[#FAF7F2] font-bold text-xs sm:text-sm transition-colors cursor-pointer"
                  >
                    <Handshake className="w-4 h-4 text-[#D4AF37]" />
                    <span>{t.btnAffiliate}</span>
                  </button>
                </div>
              </div>
            </div>
          </section>
        )}

        {/* Section 1: Magic Union Meterage & Sayadi Check Calculator */}
        <section
          id="calculator"
          aria-label="Official Union Cabinet & Renovation Calculator"
          className="py-8 px-4 sm:px-8 max-w-[1440px] mx-auto"
        >
          <div className="hc-card bg-white border-2 border-[#E6DEC8] rounded-3xl p-6 sm:p-8 shadow-xs">
            {/* Calculator Header */}
            <div className="flex flex-col lg:flex-row lg:items-center justify-between gap-4 border-b border-[#E6DEC8] pb-5 mb-6">
              <div>
                <div className="text-xs font-bold text-emerald-700 mb-1 flex items-center gap-1.5">
                  <ShieldCheck className="w-4 h-4" />
                  <span>
                    تاییدشده طبق فرمول استاندارد اتحادیه · نمایشگاه فعال: {brandConfig.brandName}
                  </span>
                </div>
                <h2 className="text-xl sm:text-3xl font-extrabold text-[#4A2E1B]">
                  {t.calcHeading}
                </h2>
                <p className="text-xs sm:text-sm text-[#6F4E37] mt-1">{t.calcSubheading}</p>
              </div>

              {/* ADHD Mode Quick Toggle right on the calculator */}
              <div className="flex items-center gap-2 self-start lg:self-auto">
                <button
                  onClick={() => setAdhdFocusMode(!adhdFocusMode)}
                  className={`flex items-center gap-2 px-4 py-2.5 rounded-xl text-xs font-bold border transition-colors cursor-pointer ${
                    adhdFocusMode
                      ? 'bg-emerald-600 text-white border-emerald-700'
                      : 'bg-[#FAF7F2] text-[#4A2E1B] border-[#D4AF37]'
                  }`}
                >
                  <Brain className="w-4 h-4" />
                  <span>
                    {adhdFocusMode
                      ? '✓ حالت آرام و گام‌به‌گام ADHD فعال است'
                      : '🧠 فعال‌سازی حالت گام‌به‌گام ویژه تمرکز ADHD'}
                  </span>
                </button>
              </div>
            </div>

            {/* ADHD Step-by-Step Progress Bar with Green Checkmarks */}
            {adhdFocusMode && (
              <div className="mb-8 p-4 rounded-2xl bg-emerald-50/70 border border-emerald-200">
                <div className="text-xs font-bold text-emerald-900 mb-3">
                  🧠 راهنمای گام‌به‌گام بدون شلوغی بصری (ADHD Calm Step-by-Step):
                </div>
                <div className="grid grid-cols-1 sm:grid-cols-3 gap-3">
                  {[
                    { step: 1 as const, title: 'گام ۱: انتخاب نوع پروژه و متریال' },
                    { step: 2 as const, title: 'گام ۲: تعیین متراژ و ارتفاع سقف' },
                    { step: 3 as const, title: 'گام ۳: مشاهده پیش‌فاکتور و چک صیادی' },
                  ].map((s) => (
                    <button
                      key={s.step}
                      onClick={() => setAdhdStep(s.step)}
                      className={`flex items-center justify-between p-3.5 rounded-xl border text-xs font-bold cursor-pointer ${
                        adhdStep === s.step
                          ? 'bg-[#4A2E1B] text-[#F6E27A] border-[#4A2E1B]'
                          : 'bg-white text-emerald-900 border-emerald-300'
                      }`}
                    >
                      <span>{s.title}</span>
                      <CheckCircle2 className="w-5 h-5 text-emerald-500 shrink-0" />
                    </button>
                  ))}
                </div>
              </div>
            )}

            <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 items-start">
              {/* Left Column: Interactive Controls */}
              <div className="lg:col-span-7 space-y-6">
                {/* Step 1: Project Type & Material */}
                {(!adhdFocusMode || adhdStep === 1) && (
                  <div className="space-y-5 p-5 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8]">
                    <div>
                      <label className="flex items-center justify-between text-xs sm:text-sm font-bold text-[#4A2E1B] mb-2">
                        <span>{t.projectTypeLabel}</span>
                        <span className="text-emerald-600 flex items-center gap-1 text-xs">
                          <CheckCircle2 className="w-4 h-4" /> گام ۱
                        </span>
                      </label>
                      <div className="grid grid-cols-1 sm:grid-cols-2 gap-2.5">
                        {PROJECT_TYPES.map((pt) => {
                          const active = pt.id === projectTypeId;
                          return (
                            <button
                              key={pt.id}
                              type="button"
                              onClick={() => setProjectTypeId(pt.id)}
                              className={`text-right p-3.5 rounded-xl border text-xs sm:text-sm font-bold transition-colors cursor-pointer flex items-start justify-between gap-2 ${
                                active
                                  ? 'bg-[#4A2E1B] text-white border-[#D4AF37]'
                                  : 'bg-white text-[#2A1A10] border-[#E6DEC8] hover:border-[#4A2E1B]'
                              }`}
                            >
                              <span>{pt.names[lang] || pt.names.fa}</span>
                              {active && (
                                <CheckCircle2 className="w-4 h-4 text-[#F6E27A] shrink-0 mt-0.5" />
                              )}
                            </button>
                          );
                        })}
                      </div>
                    </div>

                    <div>
                      <label className="block text-xs sm:text-sm font-bold text-[#4A2E1B] mb-2">
                        {t.materialTypeLabel}
                      </label>
                      <div className="space-y-2">
                        {MATERIALS.map((mat) => {
                          const active = mat.id === materialId;
                          const adjPrice = Math.round(
                            mat.basePricePerMeterToman *
                              selectedProject.unionBaseRatio *
                              brandConfig.priceMultiplier
                          );
                          return (
                            <button
                              key={mat.id}
                              type="button"
                              onClick={() => setMaterialId(mat.id)}
                              className={`w-full text-right p-3.5 rounded-xl border transition-colors cursor-pointer flex flex-col sm:flex-row sm:items-center justify-between gap-2 ${
                                active
                                  ? 'bg-[#4A2E1B] text-white border-[#D4AF37]'
                                  : 'bg-white text-[#2A1A10] border-[#E6DEC8] hover:border-[#4A2E1B]'
                              }`}
                            >
                              <div>
                                <div className="text-xs sm:text-sm font-bold flex items-center gap-1.5">
                                  {active && (
                                    <CheckCircle2 className="w-4 h-4 text-emerald-400 shrink-0" />
                                  )}
                                  <span>{mat.names[lang] || mat.names.fa}</span>
                                </div>
                                <div
                                  className={`text-[11px] mt-0.5 ${
                                    active ? 'text-[#FAF7F2]/80' : 'text-[#6F4E37]'
                                  }`}
                                >
                                  {mat.specs[lang] || mat.specs.fa}
                                </div>
                              </div>
                              <div
                                className={`text-xs sm:text-sm font-mono-tabular font-extrabold shrink-0 ${
                                  active ? 'text-[#F6E27A]' : 'text-[#4A2E1B]'
                                }`}
                              >
                                {formatPrice(adjPrice, currency, lang)} / متر
                              </div>
                            </button>
                          );
                        })}
                      </div>
                    </div>

                    {adhdFocusMode && (
                      <button
                        type="button"
                        onClick={() => setAdhdStep(2)}
                        className="w-full py-3 rounded-xl bg-emerald-600 text-white font-bold text-sm cursor-pointer"
                      >
                        تایید گام ۱ و رفتن به گام ۲ (تعیین متراژ و ارتفاع) ←
                      </button>
                    )}
                  </div>
                )}

                {/* Step 2: Dimensions, Ceiling Height & Check Installment Months */}
                {(!adhdFocusMode || adhdStep === 2) && (
                  <div className="space-y-5 p-5 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8]">
                    <div>
                      <div className="flex items-center justify-between text-xs sm:text-sm font-bold text-[#4A2E1B] mb-2">
                        <span>
                          {t.lengthLabel} ({selectedProject.unitLabel[lang] || selectedProject.unitLabel.fa})
                        </span>
                        <span className="font-mono-tabular text-base text-emerald-700">
                          {lengthMeters} {selectedProject.unitLabel[lang] || selectedProject.unitLabel.fa}
                        </span>
                      </div>
                      <input
                        type="range"
                        min={2}
                        max={45}
                        step={0.5}
                        value={lengthMeters}
                        onChange={(e) => setLengthMeters(Number(e.target.value))}
                        aria-label={t.lengthLabel}
                        className="w-full accent-[#4A2E1B] cursor-pointer"
                      />
                      <div className="flex justify-between text-[11px] font-mono-tabular text-[#6F4E37] mt-1">
                        <span>2m</span>
                        <span>10m (آشپزخانه استاندارد)</span>
                        <span>25m</span>
                        <span>45m (ویلایی)</span>
                      </div>
                    </div>

                    <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                      <div>
                        <div className="flex items-center justify-between text-xs font-bold text-[#4A2E1B] mb-1.5">
                          <span>{t.heightLabel}</span>
                          <span className="font-mono-tabular text-[#4A2E1B]">
                            {ceilingHeightCm} cm
                          </span>
                        </div>
                        <input
                          type="range"
                          min={250}
                          max={360}
                          step={5}
                          value={ceilingHeightCm}
                          onChange={(e) => setCeilingHeightCm(Number(e.target.value))}
                          aria-label={t.heightLabel}
                          className="w-full accent-[#4A2E1B] cursor-pointer"
                        />
                      </div>

                      <div>
                        <div className="flex items-center justify-between text-xs font-bold text-[#4A2E1B] mb-1.5">
                          <span>درصد پیش‌پرداخت نقدی قرارداد:</span>
                          <span className="font-mono-tabular text-[#4A2E1B]">{downPaymentPct}% نقد</span>
                        </div>
                        <div className="grid grid-cols-4 gap-1 mb-3">
                          {[30, 35, 40, 50].map((pct) => (
                            <button
                              key={pct}
                              type="button"
                              onClick={() => setDownPaymentPct(pct)}
                              className={`py-1.5 rounded-lg font-mono-tabular text-xs font-bold border cursor-pointer ${
                                downPaymentPct === pct
                                  ? 'bg-[#4A2E1B] text-[#F6E27A] border-[#D4AF37]'
                                  : 'bg-white text-[#4A2E1B] border-[#E6DEC8]'
                              }`}
                            >
                              {pct}%
                            </button>
                          ))}
                        </div>

                        <div className="flex items-center justify-between text-xs font-bold text-[#4A2E1B] mb-1.5">
                          <span>🟣 تقسیط چک صیادی بنفش (۳ تا ۱۲ فقره):</span>
                          <span className="font-mono-tabular text-purple-800">
                            {checkMonths} فقره چک صیادی بنفش
                          </span>
                        </div>
                        <div className="grid grid-cols-4 sm:grid-cols-8 gap-1">
                          {[1, 2, 3, 4, 6, 8, 10, 12].map((m) => (
                            <button
                              key={m}
                              type="button"
                              onClick={() => setCheckMonths(m)}
                              className={`py-2 rounded-lg font-mono-tabular text-xs font-bold border cursor-pointer ${
                                checkMonths === m
                                  ? 'bg-purple-800 text-white border-purple-900'
                                  : 'bg-white text-[#4A2E1B] border-[#E6DEC8]'
                              }`}
                            >
                              {m} چک
                            </button>
                          ))}
                        </div>
                      </div>
                    </div>

                    <div className="pt-1 space-y-3">
                      <button
                        type="button"
                        onClick={() => setIncludeBlumAndSlab(!includeBlumAndSlab)}
                        className={`w-full p-3 rounded-xl border text-xs font-bold flex items-center justify-between cursor-pointer ${
                          includeBlumAndSlab
                            ? 'bg-emerald-50 border-emerald-400 text-emerald-950'
                            : 'bg-white border-[#E6DEC8] text-[#6F4E37]'
                        }`}
                      >
                        <span>
                          🔩 پکیج صفحه کوارتز ۵ سانتی ضدآب + لولا و جک آرام‌بند بلوم اتریش (Blum)
                        </span>
                        <span>{includeBlumAndSlab ? '✓ محاسبه شده' : '+ افزودن به پیش‌فاکتور'}</span>
                      </button>

                      {/* Inflation-Shield Price Lock Guarantee Button */}
                      <div className="p-3.5 rounded-xl bg-amber-50/90 border-2 border-[#D4AF37] flex flex-col sm:flex-row sm:items-center justify-between gap-3">
                        <div className="space-y-0.5">
                          <div className="text-xs font-extrabold text-[#4A2E1B] flex items-center gap-1.5">
                            <ShieldCheck className="w-4 h-4 text-emerald-700" />
                            <span>🛡️ قفل ضمانت قیمت ضدتورم ورق، چوب و یراق (Inflation-Shield Guarantee)</span>
                          </div>
                          <p className="text-[11px] text-[#6F4E37]">
                            تثبیت ۱۰۰٪ نرخ ورق MDF، صفحه کوارتز و یراق بلوم اتریش از لحظه پرداخت بیعانه تا روز تحویل نهایی (کد گواهی: {inflationLockCode})
                          </p>
                        </div>
                        <button
                          type="button"
                          onClick={() => setInflationLockActive(!inflationLockActive)}
                          className={`px-3.5 py-2 rounded-xl text-xs font-extrabold whitespace-nowrap cursor-pointer ${
                            inflationLockActive
                              ? 'bg-emerald-600 text-white'
                              : 'bg-white border border-[#4A2E1B] text-[#4A2E1B]'
                          }`}
                        >
                          {inflationLockActive ? '🔒 قفل ضدتورم فعال است' : 'فعال‌سازی قفل ضدتورم'}
                        </button>
                      </div>

                      {/* Live Central Bank Sayadi Check Color Inquiry Simulator */}
                      <div className="p-4 rounded-xl bg-white border-2 border-purple-300 space-y-2.5">
                        <div className="flex flex-wrap items-center justify-between gap-2">
                          <span className="text-xs font-extrabold text-purple-950">
                            🟣 شبیه‌ساز زنده «استعلام رنگ چک صیادی بانک مرکزی (سفید / زرد / قرمز)»:
                          </span>
                          <span className="text-[11px] font-mono-tabular font-bold text-purple-800">
                            {sayadiStatusMeta.score}
                          </span>
                        </div>
                        <div className="grid grid-cols-1 sm:grid-cols-12 gap-2 items-center">
                          <input
                            type="text"
                            dir="ltr"
                            maxLength={16}
                            value={sayadiCheckId}
                            onChange={(e) => setSayadiCheckId(e.target.value.replace(/\D/g, ''))}
                            placeholder="شناسه ۱۶ رقمی چک صیادی..."
                            className="sm:col-span-5 px-3 py-2 rounded-lg bg-[#FAF7F2] border border-purple-300 font-mono-tabular text-xs font-bold text-purple-950"
                          />
                          <div className="sm:col-span-7 grid grid-cols-3 gap-1.5 text-[11px]">
                            <button
                              type="button"
                              onClick={() => setSayadiStatus('white')}
                              className={`py-2 px-2 rounded-lg font-bold border cursor-pointer ${
                                sayadiStatus === 'white'
                                  ? 'bg-emerald-600 text-white border-emerald-700'
                                  : 'bg-emerald-50 text-emerald-900 border-emerald-200'
                              }`}
                            >
                              ⚪ وضعیت سفید
                            </button>
                            <button
                              type="button"
                              onClick={() => setSayadiStatus('yellow')}
                              className={`py-2 px-2 rounded-lg font-bold border cursor-pointer ${
                                sayadiStatus === 'yellow'
                                  ? 'bg-amber-500 text-black border-amber-600'
                                  : 'bg-amber-50 text-amber-900 border-amber-200'
                              }`}
                            >
                              🟡 وضعیت زرد
                            </button>
                            <button
                              type="button"
                              onClick={() => setSayadiStatus('red')}
                              className={`py-2 px-2 rounded-lg font-bold border cursor-pointer ${
                                sayadiStatus === 'red'
                                  ? 'bg-red-600 text-white border-red-700'
                                  : 'bg-red-50 text-red-900 border-red-200'
                              }`}
                            >
                              🔴 وضعیت قرمز
                            </button>
                          </div>
                        </div>
                        <div className={`p-2.5 rounded-lg border text-xs font-bold ${sayadiStatusMeta.colorClass}`}>
                          ✓ گواهی استعلام ثبت‌شده در پیش‌فاکتور: {sayadiStatusMeta.badge} — امتیاز: {sayadiStatusMeta.score}
                        </div>
                      </div>

                      {/* Transparent Cost-Split Slider (Between Partners / Builder & Owner / Families) */}
                      <div className="p-3.5 rounded-xl bg-white border border-[#E6DEC8] space-y-2">
                        <div className="flex items-center justify-between text-xs font-bold text-[#4A2E1B]">
                          <span>⚖️ اسلایدر تقسیم و تسهیم شفاف هزینه (بین شرکا، سازنده و مالک یا خانواده‌ها):</span>
                          <span className="font-mono-tabular text-emerald-700">
                            {partnerASharePct}% / {partnerBSharePct}%
                          </span>
                        </div>
                        <input
                          type="range"
                          min={10}
                          max={90}
                          step={5}
                          value={partnerASharePct}
                          onChange={(e) => setPartnerASharePct(Number(e.target.value))}
                          className="w-full accent-[#4A2E1B] cursor-pointer"
                        />
                        <div className="grid grid-cols-2 gap-2 text-[11px]">
                          <div className="p-2 rounded-lg bg-[#FAF7F2] border border-[#E6DEC8]">
                            <span className="text-[#6F4E37] block">سهم طرف اول / سازنده ({partnerASharePct}%):</span>
                            <strong className="font-mono-tabular text-[#4A2E1B]">
                              {formatPrice(partnerATotalToman, currency, lang)}
                            </strong>
                          </div>
                          <div className="p-2 rounded-lg bg-[#FAF7F2] border border-[#E6DEC8]">
                            <span className="text-[#6F4E37] block">سهم طرف دوم / خریدار ({partnerBSharePct}%):</span>
                            <strong className="font-mono-tabular text-emerald-700">
                              {formatPrice(partnerBTotalToman, currency, lang)}
                            </strong>
                          </div>
                        </div>
                      </div>
                    </div>

                    {adhdFocusMode && (
                      <div className="flex gap-2">
                        <button
                          type="button"
                          onClick={() => setAdhdStep(1)}
                          className="px-4 py-3 rounded-xl bg-white border border-[#E6DEC8] text-[#4A2E1B] font-bold text-xs cursor-pointer"
                        >
                          → بازگشت به گام ۱
                        </button>
                        <button
                          type="button"
                          onClick={() => setAdhdStep(3)}
                          className="flex-1 py-3 rounded-xl bg-emerald-600 text-white font-bold text-sm cursor-pointer"
                        >
                          مشاهده پیش‌فاکتور نهایی و اقساط چک صیادی ←
                        </button>
                      </div>
                    )}
                  </div>
                )}
              </div>

              {/* Right Column: Instant Pre-Invoice & Sayadi Check Breakdown */}
              {(!adhdFocusMode || adhdStep === 3) && (
                <div className="lg:col-span-5 bg-[#FAF7F2] border-2 border-[#D4AF37] rounded-2xl p-6 space-y-5">
                  <div className="flex items-center justify-between border-b border-[#E6DEC8] pb-3">
                    <div>
                      <span className="text-[11px] font-bold text-[#6F4E37] block">
                        پیش‌فاکتور رسمی اتحادیه · {brandConfig.brandName}
                      </span>
                      <h3 className="text-base sm:text-lg font-extrabold text-[#4A2E1B] flex items-center gap-1.5">
                        <FileCheck2 className="w-5 h-5 text-[#D4AF37]" />
                        <span>{t.unionBreakdownTitle}</span>
                      </h3>
                    </div>
                    <button
                      type="button"
                      onClick={isSpeaking ? stopSpeaking : handleReadInvoiceAloud}
                      aria-label={t.readInvoiceVoiceBtn}
                      className="flex items-center gap-1.5 px-3 py-2 rounded-xl bg-[#4A2E1B] text-[#F6E27A] text-xs font-bold cursor-pointer"
                    >
                      {isSpeaking ? <VolumeX className="w-4 h-4" /> : <Volume2 className="w-4 h-4" />}
                      <span>{isSpeaking ? 'توقف' : t.readInvoiceVoiceBtn}</span>
                    </button>
                  </div>

                  {/* Union 60/40 Breakdown */}
                  <div className="space-y-2.5 text-xs bg-white p-4 rounded-xl border border-[#E6DEC8]">
                    <div className="flex justify-between">
                      <span className="text-[#6F4E37]">سهم کابینت زمینی و بدنه (۶۰٪ اتحادیه):</span>
                      <strong className="font-mono-tabular text-[#2A1A10]">
                        {formatPrice(baseCabinet60Toman, currency, lang)}
                      </strong>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-[#6F4E37]">سهم کابینت هوایی، تاج و پاخور (۴۰٪ اتحادیه):</span>
                      <strong className="font-mono-tabular text-[#2A1A10]">
                        {formatPrice(wallCabinet40Toman, currency, lang)}
                      </strong>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-[#6F4E37]">قیمت واحد هر متر با ضرایب انتخابی:</span>
                      <strong className="font-mono-tabular text-[#4A2E1B]">
                        {formatPrice(unitPriceToman, currency, lang)}
                      </strong>
                    </div>
                    <div className="pt-2 border-t border-[#E6DEC8] text-[11px] text-[#6F4E37]">
                      {selectedProject.unionFormulaNote[lang] || selectedProject.unionFormulaNote.fa}
                    </div>
                  </div>

                  {/* Creative Engineering Estimator: Exact MDF Sheets & Hardware Count */}
                  <div className="p-3.5 rounded-xl bg-white border border-[#E6DEC8]">
                    <div className="text-xs font-bold text-[#4A2E1B] mb-2 flex items-center gap-1.5">
                      <Ruler className="w-4 h-4 text-[#D4AF37]" />
                      <span>برآورد مهندسی اقلام و ورق مصرفی پروژه شما (ضدضایعات):</span>
                    </div>
                    <div className="grid grid-cols-2 sm:grid-cols-4 gap-2 text-center font-mono-tabular">
                      <div className="p-2 rounded-lg bg-[#FAF7F2]">
                        <div className="text-sm font-extrabold text-[#4A2E1B]">
                          {estimatedMdfSheets} ورق
                        </div>
                        <div className="text-[10px] text-[#6F4E37]">ورق MDF کامل</div>
                      </div>
                      <div className="p-2 rounded-lg bg-[#FAF7F2]">
                        <div className="text-sm font-extrabold text-[#4A2E1B]">
                          {estimatedBlumHinges} عدد
                        </div>
                        <div className="text-[10px] text-[#6F4E37]">لولا آرام‌بند بلوم</div>
                      </div>
                      <div className="p-2 rounded-lg bg-[#FAF7F2]">
                        <div className="text-sm font-extrabold text-[#4A2E1B]">
                          {estimatedTandemDrawers} ست
                        </div>
                        <div className="text-[10px] text-[#6F4E37]">ریل کشو تاندم</div>
                      </div>
                      <div className="p-2 rounded-lg bg-[#FAF7F2]">
                        <div className="text-sm font-extrabold text-[#4A2E1B]">
                          {estimatedCountertopMeters} متر
                        </div>
                        <div className="text-[10px] text-[#6F4E37]">صفحه ۵ سانتی</div>
                      </div>
                    </div>
                  </div>

                  {/* Cash Total & Sayadi Check Installments */}
                  <div className="space-y-3">
                    <div className="p-4 rounded-xl bg-[#4A2E1B] text-white">
                      <div className="text-xs text-[#F6E27A] mb-1">{t.cashPriceLabel}</div>
                      <div className="text-2xl sm:text-3xl font-extrabold font-mono-tabular">
                        {formatPrice(totalCashPriceToman, currency, lang)}
                      </div>
                    </div>

                    <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                      <div className="p-3.5 rounded-xl bg-white border border-[#E6DEC8]">
                        <div className="text-[11px] text-[#6F4E37] mb-1">
                          پیش‌پرداخت نقدی قرارداد ({downPaymentPct}%):
                        </div>
                        <div className="text-base font-extrabold font-mono-tabular text-[#4A2E1B]">
                          {formatPrice(downPayment40Toman, currency, lang)}
                        </div>
                      </div>

                      <div className="p-3.5 rounded-xl bg-purple-50 border border-purple-300">
                        <div className="text-[11px] text-purple-950 mb-1">
                          🟣 مبلغ هر چک صیادی بنفش ({checkMonths} فقره):
                        </div>
                        <div className="text-base font-extrabold font-mono-tabular text-purple-900">
                          {formatPrice(eachCheckAmountToman, currency, lang)}
                        </div>
                      </div>
                    </div>

                    {/* Solar Hijri Due Date Schedule Table for Purple Sayadi Checks */}
                    <div className="p-3.5 rounded-xl bg-white border border-purple-300 space-y-2">
                      <div className="flex items-center justify-between text-xs font-extrabold text-purple-950">
                        <span>📅 جدول تاریخ‌های سررسید شمسی چک‌های صیادی بنفش:</span>
                        <span className="text-[10px] font-mono-tabular text-emerald-700">
                          {inflationLockActive ? `🔒 قفل ضدتورم: ${inflationLockCode}` : 'نرخ روز'}
                        </span>
                      </div>
                      <div className="max-h-36 overflow-y-auto">
                        <table className="w-full text-right text-[11px] border-collapse">
                          <thead>
                            <tr className="bg-purple-900 text-white">
                              <th className="p-1.5 rounded-tr-lg">ردیف چک</th>
                              <th className="p-1.5">سررسید شمسی</th>
                              <th className="p-1.5">شناسه صیادی</th>
                              <th className="p-1.5 rounded-tl-lg">مبلغ چک</th>
                            </tr>
                          </thead>
                          <tbody>
                            {sayadiCheckSchedule.map((chk) => (
                              <tr key={chk.checkNumber} className="border-b border-purple-100">
                                <td className="p-1.5 font-bold text-purple-900">
                                  چک صیادی #{chk.checkNumber}
                                </td>
                                <td className="p-1.5 font-mono-tabular">{chk.dueDateFa}</td>
                                <td className="p-1.5 font-mono-tabular text-[#6F4E37]" dir="ltr">
                                  {chk.serialCode}
                                </td>
                                <td className="p-1.5 font-mono-tabular font-bold text-emerald-800">
                                  {formatPrice(chk.amountToman, currency, lang)}
                                </td>
                              </tr>
                            ))}
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>

                  {/* Action Buttons & 1-Click Offline Client Quote Archive */}
                  <div className="space-y-2.5 pt-1">
                    <div className="grid grid-cols-1 sm:grid-cols-12 gap-2">
                      <button
                        type="button"
                        onClick={handleSendInvoiceToWhatsApp}
                        className="sm:col-span-8 py-4 px-4 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-bold text-xs sm:text-sm shadow-md flex items-center justify-center gap-2 transition-colors cursor-pointer"
                      >
                        <MessageCircle className="w-5 h-5 shrink-0" />
                        <span>شلیک ۱-کلیکی پیش‌فاکتور طلاکوب به واتساپ مشتری و مدیر</span>
                      </button>
                      <button
                        type="button"
                        onClick={() => window.print()}
                        className="sm:col-span-4 py-4 px-3 rounded-xl bg-[#4A2E1B] hover:bg-[#352012] text-[#F6E27A] border border-[#D4AF37] font-bold text-xs shadow-md flex items-center justify-center gap-1.5 transition-colors cursor-pointer"
                      >
                        <FileCheck2 className="w-4 h-4 text-[#D4AF37] shrink-0" />
                        <span>چاپ / PDF رسمی</span>
                      </button>
                    </div>

                    {/* Anti-Sanction Direct SMS & Gold-Foil PNG Download Buttons */}
                    <div className="grid grid-cols-1 sm:grid-cols-2 gap-2">
                      <button
                        type="button"
                        onClick={() => {
                          const cleanSms = brandConfig.phone.replace(/\D/g, '') || '09120000000';
                          const pName = selectedProject.names[lang] || selectedProject.names.fa;
                          const mName = selectedMaterial.names[lang] || selectedMaterial.names.fa;
                          const smsBody = `پیش‌فاکتور رسمی ${brandConfig.brandName} (${brandConfig.city})\nپروژه: ${pName} (${lengthMeters} متر - ${mName})\nمبلغ کل: ${formatPrice(totalCashPriceToman, currency, lang)}\nپیش‌پرداخت (${downPaymentPct}%): ${formatPrice(downPayment40Toman, currency, lang)}\nهر چک صیادی (${checkMonths} فقره): ${formatPrice(eachCheckAmountToman, currency, lang)}\nقفل ضدتورم: ${inflationLockCode}`;
                          window.location.href = `sms:${cleanSms}?body=${encodeURIComponent(smsBody)}`;
                        }}
                        className="py-3 px-3 rounded-xl bg-amber-500 hover:bg-amber-400 text-[#2A1A10] font-extrabold text-xs flex items-center justify-center gap-1.5 cursor-pointer"
                      >
                        <span>📩 ارسال پیش‌فاکتور با پیامک عادی (بدون فیلتر و اینترنت)</span>
                      </button>

                      <button
                        type="button"
                        onClick={() => {
                          const pName = selectedProject.names[lang] || selectedProject.names.fa;
                          const mName = selectedMaterial.names[lang] || selectedMaterial.names.fa;
                          const uLabel =
                            selectedProject.unitLabel[lang] || selectedProject.unitLabel.fa;
                          downloadGoldFoilInvoicePng({
                            brandName: brandConfig.brandName,
                            managerName: tenantConfig.managerName,
                            city: brandConfig.city,
                            phone: brandConfig.phone,
                            guildBadge: 'پیش‌فاکتور رسمی اتحادیه کابینت، دکوراسیون و سرامیک اسلب',
                            clientName: clientNameInput || 'مشتری گرامی VIP',
                            itemTitle: `${pName} (${lengthMeters} ${uLabel})`,
                            itemSubtitle: mName,
                            totalAmountFormatted: formatPrice(totalCashPriceToman, currency, lang),
                            cashDownFormatted: formatPrice(downPayment40Toman, currency, lang),
                            checkInstallmentFormatted: formatPrice(eachCheckAmountToman, currency, lang),
                            checkCount: checkMonths,
                            sayadiStatusText:
                              sayadiStatus === 'white'
                                ? 'وضعیت سفید بانک مرکزی (تایید خوش‌حسابی A+)'
                                : sayadiStatus === 'yellow'
                                ? 'وضعیت زرد (نیاز به ضامن)'
                                : 'وضعیت قرمز',
                            inflationLockCode,
                            extraTechnicalLine1: `برآورد مهندسی: ${estimatedMdfSheets} ورق کامل / اسلب | ${estimatedBlumHinges} عدد یراق/همتراز`,
                            extraTechnicalLine2: `سهم طرف اول (${partnerASharePct}%): ${formatPrice(partnerATotalToman, currency, lang)} | طرف دوم (${partnerBSharePct}%): ${formatPrice(partnerBTotalToman, currency, lang)}`,
                          });
                        }}
                        className="py-3 px-3 rounded-xl bg-[#D4AF37] hover:bg-[#F6E27A] text-[#2A1A10] font-extrabold text-xs flex items-center justify-center gap-1.5 cursor-pointer"
                      >
                        <span>🖼️ دانلود عکس فاکتور طلاکوب (مناسب گالری، ایتا، بله و تلگرام)</span>
                      </button>
                    </div>

                    {/* Offline Client Pre-Invoice CRM Box */}
                    <div className="p-3.5 rounded-xl bg-white border border-[#E6DEC8] space-y-2.5">
                      <div className="text-xs font-bold text-[#4A2E1B]">
                        📂 ذخیره در دفترچه آفلاین پیش‌فاکتورهای مشتریان (بدون نیاز به اینترنت):
                      </div>
                      <div className="flex gap-2">
                        <input
                          type="text"
                          value={clientNameInput}
                          onChange={(e) => setClientNameInput(e.target.value)}
                          placeholder="نام مشتری یا واحد (مثلاً: پروژه الهیه - آقای رضایی)"
                          className="flex-1 px-3 py-2 rounded-lg border border-[#E6DEC8] bg-[#FAF7F2] text-xs"
                        />
                        <button
                          type="button"
                          onClick={handleSaveOfflineQuote}
                          className="px-3.5 py-2 rounded-lg bg-[#4A2E1B] text-[#F6E27A] font-bold text-xs whitespace-nowrap cursor-pointer"
                        >
                          + ذخیره آفلاین
                        </button>
                      </div>

                      {savedQuotes.length > 0 && (
                        <div className="space-y-1.5 max-h-36 overflow-y-auto pt-1">
                          {savedQuotes.map((sq) => (
                            <div
                              key={sq.id}
                              className="flex items-center justify-between p-2 rounded-lg bg-[#FAF7F2] border border-[#E6DEC8] text-[11px]"
                            >
                              <button
                                type="button"
                                onClick={() => {
                                  setProjectTypeId(sq.projectTypeId);
                                  setMaterialId(sq.materialId);
                                  setLengthMeters(sq.lengthMeters);
                                  setCeilingHeightCm(sq.ceilingHeightCm);
                                  setCheckMonths(sq.checkMonths);
                                }}
                                className="text-right font-bold text-[#4A2E1B] hover:underline cursor-pointer"
                              >
                                {sq.clientName} ({sq.lengthMeters}m) —{' '}
                                <span className="font-mono-tabular text-emerald-700">
                                  {formatPrice(sq.totalCashToman, currency, lang)}
                                </span>
                              </button>
                              <button
                                type="button"
                                onClick={() => handleDeleteOfflineQuote(sq.id)}
                                className="text-red-700 hover:text-red-900 px-1.5 font-bold cursor-pointer"
                                aria-label="حذف پیش‌فاکتور"
                              >
                                ✕
                              </button>
                            </div>
                          ))}
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              )}
            </div>
          </div>
        </section>

        {/* Section 2: 10 Real Luxury Portfolio Showcase + Wholesale Prices + 1-Click Story Maker */}
        <section
          id="showcase"
          aria-label="10 Real Luxury Kitchen and Interior Showcase"
          className="py-10 px-4 sm:px-8 max-w-[1440px] mx-auto"
        >
          <div className="flex flex-col lg:flex-row lg:items-end justify-between gap-4 border-b border-[#E6DEC8] pb-5 mb-8">
            <div>
              <div className="text-xs font-bold text-[#6F4E37] mb-1">
                ویترین رسمی محصولات با قابلیت مخفی‌سازی قیمت همکار + استوری‌ساز ۱۰۸۰×۱۹۲۰
              </div>
              <h2 className="text-2xl sm:text-3xl font-extrabold text-[#4A2E1B]">
                {t.showcaseHeading}
              </h2>
              <p className="text-xs sm:text-sm text-[#6F4E37] mt-1">{t.showcaseSubheading}</p>
            </div>

            <div className="flex flex-wrap items-center gap-2">
              {/* Toggle Colleague Wholesale Visibility (For Showroom Owners presenting to Retail Customers) */}
              <button
                type="button"
                onClick={() => setShowWholesaleOnCards(!showWholesaleOnCards)}
                className={`flex items-center gap-1.5 px-3.5 py-2 rounded-xl text-xs font-bold border transition-colors cursor-pointer ${
                  showWholesaleOnCards
                    ? 'bg-[#4A2E1B] text-[#F6E27A] border-[#4A2E1B]'
                    : 'bg-white text-[#6F4E37] border-[#E6DEC8]'
                }`}
              >
                {showWholesaleOnCards ? (
                  <>
                    <EyeOff className="w-4 h-4" />
                    <span>مخفی کردن قیمت عمده همکار (نمایش به مشتری)</span>
                  </>
                ) : (
                  <>
                    <Eye className="w-4 h-4" />
                    <span>نمایش قیمت عمده ورق و یراق همکار</span>
                  </>
                )}
              </button>
            </div>
          </div>

          {/* Interactive Category Filter Bar */}
          <div className="flex flex-wrap items-center gap-2 mb-8 p-1.5 bg-white border border-[#E6DEC8] rounded-2xl w-fit">
            {[
              { id: 'all', label: 'همه ۱۲ نمونه کار لوکس (کابینت + اسلب)' },
              { id: 'ceramics_slab', label: '🏛️ کاشی، سرامیک اسلب و شیرآلات توکار' },
              { id: 'neoclassic', label: 'نئوکلاسیک و چوب گردو' },
              { id: 'modern', label: 'پلی‌اورتان انزو، هایگلاس و ممبران' },
              { id: 'closet', label: 'کلوزت‌روم و کمد ریلی' },
              { id: 'renovation', label: 'تی‌وی وال و بازسازی کامل' },
            ].map((tab) => (
              <button
                key={tab.id}
                onClick={() => setCategoryFilter(tab.id)}
                className={`px-4 py-2 rounded-xl text-xs font-bold transition-colors cursor-pointer whitespace-nowrap ${
                  categoryFilter === tab.id
                    ? 'bg-[#4A2E1B] text-[#F6E27A] shadow-xs'
                    : 'text-[#6F4E37] hover:text-[#4A2E1B]'
                }`}
              >
                {tab.label}
              </button>
            ))}
          </div>

          {/* 10 Showcase Cards Grid */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-7">
            {filteredProducts.map((product) => {
              const retailAdjusted = Math.round(
                product.pricePerMeterToman * brandConfig.priceMultiplier
              );
              const wholesaleAdjusted = product.wholesaleColleaguePriceToman;
              const retailStr = formatPrice(retailAdjusted, currency, lang);
              const wholesaleStr = formatPrice(wholesaleAdjusted, currency, lang);
              const titleStr = product.title[lang] || product.title.fa;
              const subStr = product.subtitle[lang] || product.subtitle.fa;

              return (
                <article
                  key={product.id}
                  className="hc-card bg-white border border-[#E6DEC8] rounded-2xl overflow-hidden flex flex-col justify-between transition-transform duration-150 hover:-translate-y-0.5"
                >
                  <div>
                    {/* Resilient Image Container with Zero-Broken-Image Fallback */}
                    <div className="relative h-60 w-full bg-[#352012] overflow-hidden">
                      {!brokenImages[product.id] ? (
                        <img
                          src={product.image}
                          alt={titleStr}
                          referrerPolicy="no-referrer"
                          loading="lazy"
                          onError={() =>
                            setBrokenImages((prev) => ({ ...prev, [product.id]: true }))
                          }
                          className="w-full h-full object-cover"
                        />
                      ) : (
                        <div className="w-full h-full flex flex-col items-center justify-center p-6 text-center bg-gradient-to-br from-[#4A2E1B] to-[#24150B] text-[#F6E27A]">
                          <Layers className="w-10 h-10 mb-2 text-[#D4AF37]" />
                          <span className="text-sm font-bold">{titleStr}</span>
                        </div>
                      )}
                      <div className="absolute inset-0 bg-gradient-to-t from-black/70 via-transparent to-transparent" />

                      <div className="absolute bottom-3 right-3 left-3 flex items-center justify-between text-xs text-white font-mono-tabular">
                        <span>
                          {product.code} · {product.warrantyYears}Y Warranty
                        </span>
                        <button
                          type="button"
                          onClick={() =>
                            speakText(`${titleStr}. ${t.retailPricePerMeter} ${retailStr}`)
                          }
                          aria-label={`خوانش صوتی ${titleStr}`}
                          className="p-2 rounded-lg bg-[#4A2E1B]/90 hover:bg-[#4A2E1B] text-[#F6E27A] border border-[#D4AF37]/50 cursor-pointer"
                        >
                          <Volume2 className="w-4 h-4" />
                        </button>
                      </div>
                    </div>

                    {/* Card Content */}
                    <div className="p-5 space-y-3">
                      <div className="text-xs text-[#6F4E37]">
                        {product.materialsUsed[lang] || product.materialsUsed.fa} · تحویل{' '}
                        {product.deliveryDays} روزه
                      </div>

                      <h3 className="text-base font-bold text-[#4A2E1B] leading-snug">
                        {titleStr}
                      </h3>

                      <p className="text-xs text-[#6F4E37] leading-relaxed">{subStr}</p>

                      {/* Pricing Box */}
                      <div className="pt-2 space-y-2 border-t border-[#E6DEC8]">
                        <div className="flex items-center justify-between">
                          <span className="text-xs font-medium text-[#6F4E37]">
                            {t.retailPricePerMeter}
                          </span>
                          <strong className="text-base font-extrabold font-mono-tabular text-[#4A2E1B]">
                            {retailStr}
                          </strong>
                        </div>

                        {showWholesaleOnCards && (
                          <div className="flex items-center justify-between p-2.5 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8]">
                            <span className="text-[11px] font-bold text-[#6F4E37]">
                              {t.colleagueWholesalePrice}
                            </span>
                            <strong className="text-xs font-extrabold font-mono-tabular text-emerald-700">
                              {wholesaleStr}
                            </strong>
                          </div>
                        )}
                      </div>
                    </div>
                  </div>

                  {/* Card Footer Actions: 1-Click HD Story Maker & WhatsApp Inquiry */}
                  <div className="p-5 pt-0 space-y-2">
                    <button
                      type="button"
                      onClick={() => setStoryModalProduct(product)}
                      className="w-full py-2.5 px-4 rounded-xl bg-[#4A2E1B] hover:bg-[#352012] text-[#F6E27A] font-bold text-xs flex items-center justify-center gap-2 transition-colors cursor-pointer"
                    >
                      <Camera className="w-4 h-4 text-[#D4AF37]" />
                      <span>{t.makeStoryBtn}</span>
                    </button>

                    <button
                      type="button"
                      onClick={() => {
                        const msg = `👑 سلام، از طریق ویترین ${brandConfig.brandName}، استعلام قیمت و سفارش مدل «${titleStr}» (کد ${product.code}) با قیمت هر متر ${retailStr} و شرایط اقساط چک صیادی را دارم.`;
                        const cleanWa = brandConfig.whatsapp.replace(/\D/g, '') || '989120000000';
                        window.location.href = `https://wa.me/${cleanWa}?text=${encodeURIComponent(
                          msg
                        )}`;
                      }}
                      className="w-full py-2.5 px-4 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-bold text-xs flex items-center justify-center gap-2 transition-colors cursor-pointer"
                    >
                      <MessageCircle className="w-4 h-4" />
                      <span>{t.orderWhatsappBtn}</span>
                    </button>
                  </div>
                </article>
              );
            })}
          </div>
        </section>

        {/* Section 3: Recharts Business Intelligence Dashboard */}
        <Dashboard currency={currency} lang={lang} adhdMode={adhdFocusMode} />

        {/* Section 4: Server-Side AI Master Architect + Offline Smart Architect Engine */}
        <section
          id="ai-architect"
          aria-label="Smart Interior Architect AI Assistant"
          className="py-10 px-4 sm:px-8 max-w-[1440px] mx-auto"
        >
          <div className="hc-card bg-white border-2 border-[#E6DEC8] rounded-3xl p-6 sm:p-8">
            <div className="flex flex-col lg:flex-row lg:items-center justify-between gap-4 border-b border-[#E6DEC8] pb-5 mb-6">
              <div>
                <div className="text-xs font-bold text-emerald-700 mb-1">
                  امنیت ۱۰۰٪ سمت سرور (server.ts) · مجهز به موتور معمار هوشمند آفلاین بدون خطا
                </div>
                <h2 className="text-2xl sm:text-3xl font-extrabold text-[#4A2E1B] flex items-center gap-2.5">
                  <Bot className="w-7 h-7 text-[#D4AF37]" />
                  <span>مشاور معمار هوشمند دکوراسیون، ست‌کننده رنگ چوب و سنگ + کپشن‌نویس اینستاگرام</span>
                </h2>
              </div>
              {aiEngineBadge && (
                <div className="text-xs font-bold text-[#4A2E1B] bg-[#FAF7F2] border border-[#D4AF37] px-3.5 py-2 rounded-xl">
                  {aiEngineBadge}
                </div>
              )}
            </div>

            {/* Quick Architect Prompt Templates */}
            <div className="flex flex-wrap gap-2 mb-5">
              {[
                {
                  label: '🎨 ست کردن رنگ چوب گردو با سنگ اسلب مرمر و نورپردازی',
                  mode: 'design' as const,
                  prompt:
                    'بهترین ترکیب رنگ چوب گردو و بلوط گرم با سنگ اسلب مرمر و نورپردازی مخفی برای آشپزخانه لوکس چیست؟',
                },
                {
                  label: '📐 بهینه‌سازی آشپزخانه کوچک و کمد دیواری ریلی',
                  mode: 'design' as const,
                  prompt:
                    'برای آشپزخانه کوچک و اتاق‌خواب کم‌جا چه نوع کابینت پله‌ای و کمد دیواری ریلی پیشنهاد می‌دهید؟',
                },
                {
                  label: '📸 تولید کپشن قلاب‌انداز اینستاگرام با شرایط چک صیادی',
                  mode: 'caption' as const,
                  prompt:
                    'یک کپشن فوق‌العاده جذاب و پرفروش اینستاگرام برای معرفی کابینت نئوکلاسیک چوب گردو با شرایط چک صیادی بنویس.',
                },
              ].map((q, idx) => (
                <button
                  key={idx}
                  type="button"
                  onClick={() => {
                    setAiMode(q.mode);
                    setAiPrompt(q.prompt);
                    handleAskAiArchitect(q.prompt, q.mode);
                  }}
                  className="px-3.5 py-2 rounded-xl bg-[#FAF7F2] hover:bg-[#4A2E1B] hover:text-[#F6E27A] border border-[#E6DEC8] text-xs font-bold text-[#4A2E1B] transition-colors cursor-pointer"
                >
                  {q.label}
                </button>
              ))}
            </div>

            <div className="flex flex-col sm:flex-row gap-3">
              <input
                type="text"
                value={aiPrompt}
                onChange={(e) => setAiPrompt(e.target.value)}
                placeholder="سوال معماری خود (مثلاً: ست کردن کابینت سبز زیتونی با صفحه چوب گردو یا نوشتن کپشن استوری) را بنویسید..."
                className="flex-1 rounded-xl border border-[#E6DEC8] bg-[#FAF7F2] px-4 py-3 text-sm text-[#2A1A10] focus:border-[#4A2E1B] focus:outline-none"
              />
              <button
                type="button"
                onClick={() => handleAskAiArchitect()}
                disabled={aiLoading}
                className="px-6 py-3 rounded-xl bg-[#4A2E1B] hover:bg-[#352012] text-[#F6E27A] font-bold text-sm flex items-center justify-center gap-2 transition-colors cursor-pointer shrink-0"
              >
                <Send className="w-4 h-4" />
                <span>{aiLoading ? 'در حال تحلیل معماری...' : 'دریافت مشاوره / کپشن'}</span>
              </button>
            </div>

            {aiReply && (
              <div className="mt-5 p-5 rounded-2xl bg-[#FAF7F2] border border-[#D4AF37] text-xs sm:text-sm text-[#2A1A10] whitespace-pre-line leading-relaxed">
                <div className="flex items-center justify-between mb-2 pb-2 border-b border-[#E6DEC8]">
                  <span className="font-bold text-[#4A2E1B]">پاسخ تخصصی معمار هوشمند:</span>
                  <button
                    type="button"
                    onClick={() => speakText(aiReply)}
                    className="inline-flex items-center gap-1 text-xs font-bold text-[#4A2E1B] hover:text-emerald-700 cursor-pointer"
                  >
                    <Volume2 className="w-4 h-4" />
                    <span>خوانش صوتی پاسخ</span>
                  </button>
                </div>
                {aiReply}
              </div>
            )}
          </div>
        </section>
          </>
        )}

        {/* Section 5 & 6: Visitor Training, 25% Sheba Affiliate & Multi-Tenant Architecture
            (AUTOMATICALLY HIDDEN WHEN VISITOR MODE IS LOCKED SO THE BUYER SEES A 100% PURE, UNCLUTTERED APP!) */}
        {visitorModeUnlocked ? (
          <>
            <TavanaAmbassadorAcademySimulator currency={currency} lang={lang} />

            <VipCommercialEnterpriseSuite
              tenantConfig={tenantConfig}
              onUpdateTenantConfig={handleUpdateTenantConfig}
              currency={currency}
              lang={lang}
            />

            <WhiteLabelAffiliateSection
              brandConfig={brandConfig}
              onUpdateBrandConfig={handleUpdateBrandConfig}
              onResetBrandConfig={handleResetBrandConfig}
              currency={currency}
              lang={lang}
            />
          </>
        ) : (
          <div className="max-w-[1440px] mx-auto px-4 sm:px-8 py-4 flex justify-end">
            <button
              type="button"
              onClick={() => setVisitorModeUnlocked(true)}
              className="px-3.5 py-2 rounded-xl bg-[#FAF7F2] hover:bg-[#4A2E1B] text-[#6F4E37] hover:text-[#F6E27A] border border-[#E6DEC8] text-[11px] font-bold cursor-pointer transition-colors"
            >
              🔓 کلید مخفی سفیر فروش: باز کردن پنل تغییر صنف (۷ صنف)، آموزش ویزیتور و پورسانت ۲۵٪ شبا
            </button>
          </div>
        )}
      </main>

      {/* Clean Luxury Footer */}
      <footer className="bg-[#2A1A10] text-[#FAF7F2] border-t-2 border-[#D4AF37] py-8 px-4 sm:px-8 mt-12">
        <div className="max-w-[1440px] mx-auto flex flex-col md:flex-row items-center justify-between gap-4 text-xs">
          <div>
            <div className="font-bold text-sm text-[#F6E27A]">{brandConfig.brandName}</div>
            <div className="text-[#FAF7F2]/75 mt-1">
              اکوسیستم آفرینش | شهر جدید نیومتاورسیتی جهان | توان استیج FBNM
            </div>
          </div>

          <div className="flex flex-wrap items-center gap-3">
            <button
              onClick={() => setVipModalOpen(true)}
              className="px-3.5 py-2 rounded-lg bg-[#4A2E1B] border border-[#D4AF37]/50 text-[#F6E27A] font-bold cursor-pointer"
            >
              👑 جدول اشتراک VIP (سطوح ۱ تا ۵)
            </button>
            <button
              onClick={() => setAffiliateModalOpen(true)}
              className="px-3.5 py-2 rounded-lg bg-emerald-700 text-white font-bold cursor-pointer"
            >
              🤝 ثبت‌نام ویزیتور (۲۵٪ پورسانت نقد) و سفارش White-Label
            </button>
            <button
              onClick={() => setGithubModalOpen(true)}
              className="px-3.5 py-2 rounded-lg bg-white/10 text-white font-bold cursor-pointer"
            >
              🤖 خروجی اندروید APK / AAB
            </button>
          </div>
        </div>
      </footer>

      {/* Interactive Modals */}
      <StoryMakerModal
        product={storyModalProduct}
        onClose={() => setStoryModalProduct(null)}
        currency={currency}
        lang={lang}
        defaultBrandName={brandConfig.brandName}
        defaultPhone={brandConfig.phone}
        defaultInstagram={brandConfig.instagram}
        priceMultiplier={brandConfig.priceMultiplier}
      />

      <GithubPushModal
        isOpen={githubModalOpen}
        onClose={() => setGithubModalOpen(false)}
      />

      <VipSubscriptionModal
        isOpen={vipModalOpen}
        onClose={() => setVipModalOpen(false)}
        currency={currency}
        lang={lang}
        whatsappNumber={brandConfig.whatsapp}
      />

      <AffiliateWhiteLabelModal
        isOpen={affiliateModalOpen}
        onClose={() => setAffiliateModalOpen(false)}
        whatsappNumber={brandConfig.whatsapp}
      />

      <PWAInstallModal
        isOpen={pwaModalOpen}
        onClose={() => setPwaModalOpen(false)}
        onOpenGithubModal={() => setGithubModalOpen(true)}
      />
    </div>
  );
}
