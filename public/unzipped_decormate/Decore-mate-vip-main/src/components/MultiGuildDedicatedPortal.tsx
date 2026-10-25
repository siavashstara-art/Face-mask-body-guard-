import React, { useState } from 'react';
import {
  ShieldCheck,
  MessageCircle,
  Printer,
  MessageSquare,
  Download,
  Lock,
  Unlock,
  Sparkles,
  CheckCircle2,
  AlertTriangle,
  Scale,
  Car,
  Stethoscope,
  Gem,
  Crown,
  Layers,
  GitBranch,
  ExternalLink,
  Copy,
  Check,
  EyeOff,
  Eye,
} from 'lucide-react';
import { CurrencyCode, LanguageCode, formatPrice } from '../data/decorData';
import { TenantExtendedConfig } from './VipCommercialEnterpriseSuite';

export type ActiveGuildId =
  | 'cabinet_decor'
  | 'ceramics_luxury'
  | 'furniture_bridal'
  | 'beauty_salon'
  | 'auto_barter'
  | 'dental_aesthetic'
  | 'gold_jewelry'
  | 'wedding_venue';

export interface GuildMeta {
  id: ActiveGuildId;
  shortTitle: string;
  badgeTitle: string;
  defaultBrandName: string;
  defaultTagline: string;
  unionTitle: string;
  accentColor: string;
}

export const GUILDS_META: Record<ActiveGuildId, GuildMeta> = {
  cabinet_decor: {
    id: 'cabinet_decor',
    shortTitle: '🛋️ کابینت، کمد و دکوراسیون چوبی',
    badgeTitle: 'سامانه رسمی اتحادیه کابینت‌سازان، کمد دیواری و دکوراسیون داخلی',
    defaultBrandName: 'گالری کابینت و دکوراسیون شاهکار VIP',
    defaultTagline: 'مجری تخصصی کابینت نئوکلاسیک، انزو، تمام چوب و کلوزت‌روم با یراق بلوم اتریش',
    unionTitle: 'فرمول استاندارد ۶۰٪ زمینی + ۴۰٪ هوایی اتحادیه صنایع چوب و دکوراسیون',
    accentColor: '#4A2E1B',
  },
  ceramics_luxury: {
    id: 'ceramics_luxury',
    shortTitle: '🏛️ کاشی، سرامیک اسلب و مصالح لوکس',
    badgeTitle: 'سامانه مهندسی فروش شوروم‌های کاشی، سرامیک اسلب، سنگ بوک‌مچ و شیرآلات توکار',
    defaultBrandName: 'کلینیک سرامیک اسلب و مصالح لوکس پالاس',
    defaultTagline: 'مرجع تخصصی اسلب پرسلانی ۱۲۰×۲۴۰، سنگ مرمر بوک‌مچ، شیرآلات توکار طلایی و وال‌هنگ سوئیسی',
    unionTitle: 'فرمول استاندارد متراژ کف و بدنه + ضریب پرتی برش اسلب + چسب پرسلانی و همتراز',
    accentColor: '#1E3A3A',
  },
  furniture_bridal: {
    id: 'furniture_bridal',
    shortTitle: '🪑 گالری مبل، سرویس خواب و جهیزیه عروس (مبلیار)',
    badgeTitle: 'پکیج‌ساز هوشمند جهیزیه عروس، محاسبه متراژ پارچه و کلاف راش + سایت دائمی گالری مبل',
    defaultBrandName: 'گالری مبلمان، سرویس خواب و جهیزیه عروس رویال چوب VIP',
    defaultTagline: 'تولید و عرضه مستقیم مبلمان نئوکلاسیک، چستر، میز ناهارخوری و سرویس خواب عروس با کلاف چوب راش گرجستان و ۵ سال ضمانت فوم سرد',
    unionTitle: 'پکیج‌ساز رسمی جهیزیه عروس + محاسبه مابه‌التفاوت متراژ پارچه ترک/نانو + تقسیط چک صیادی بنفش',
    accentColor: '#3E2723',
  },
  beauty_salon: {
    id: 'beauty_salon',
    shortTitle: '💄 سالن زیبایی زنانه + سایت‌ساز + حسابداری',
    badgeTitle: 'سایت‌ساز اختصاصی سالن‌های زیبایی زنانه + ویترین صندلی/خدمات + نرم‌افزار حسابداری لاین‌ها',
    defaultBrandName: 'سالن زیبایی و عروس‌سرای VIP ملکه طلایی',
    defaultTagline: 'مرکز تخصصی رنگ و لایت، کراتین، کاشت ناخن، میکاپ عروس و فیشیال با تجهیزات VIP',
    unionTitle: 'سایت اختصاصی دائمی سالن + تخفیفات روزهای خاص + حسابداری خودکار درصد پرسنل و کسر مواد',
    accentColor: '#4A153B',
  },
  auto_barter: {
    id: 'auto_barter',
    shortTitle: '🚗 نمایشگاه خودرو، راس‌گیری چک و تهاتر ملک',
    badgeTitle: 'سامانه رسمی اتوگالری‌ها، فروش اقساطی خودرو، راس‌گیری چک صیادی و تهاتر ملک و خودرو',
    defaultBrandName: 'اتوگالری و هلدینگ تهاتر رویال موتورز VIP',
    defaultTagline: 'مرجع تخصصی فروش نقد و اقساط خودروهای وارداتی و داخلی + تهاتر هوشمند ملک و آپارتمان با خودرو',
    unionTitle: 'محاسبه‌گر رسمی اقساط خودرو، فرمول راس‌گیری چک‌های صیادی بنفش و تراز تهاتر ملک/خودرو',
    accentColor: '#112233',
  },
  dental_aesthetic: {
    id: 'dental_aesthetic',
    shortTitle: '🦷 کلینیک دندانپزشکی، ایمپلنت، لمینت و زیبایی',
    badgeTitle: 'سامانه هوشمند طرح درمان دیجیتال ایمپلنت، لمینت سرامیکی و کلینیک‌های زیبایی VIP',
    defaultBrandName: 'کلینیک تخصصی دندانپزشکی دیجیتال و طراحی لبخند دُردیس',
    defaultTagline: 'مرکز تخصصی ایمپلنت فوری سوئیسی، لمینت سرامیکی بدون تراش و زیبایی با شرایط چک صیادی',
    unionTitle: 'برگه رسمی طرح درمان دیجیتال + تقسیط چک صیادی بنفش + گارانتی ۱۰ ساله ایمپلنت و لمینت',
    accentColor: '#0F3D3E',
  },
  gold_jewelry: {
    id: 'gold_jewelry',
    shortTitle: '💎 طلافروشی، جواهرات، سکه و طلای اقساطی',
    badgeTitle: 'سامانه رسمی محاسبه مظنه لحظه‌ای طلا، اجرت، مالیات سامانه مودیان و فروش اقساطی طلا',
    defaultBrandName: 'گالری طلا و جواهرات سلطنتی زرین ۲۴ عیار',
    defaultTagline: 'تولید و عرضه مستقیم طلای ۱۸ عیار، سرویس عروس، تعویض طلای کهنه و فروش چکی صیادی',
    unionTitle: 'فرمول رسمی اتحادیه طلا و جواهر (وزن × مظنه + اجرت + سود ۷٪ + مالیات ۹٪ فقط بر اجرت و سود)',
    accentColor: '#3A230A',
  },
  wedding_venue: {
    id: 'wedding_venue',
    shortTitle: '👑 باغ‌تالار، تشریفات عروسی و مجالس لوکس',
    badgeTitle: 'سامانه هوشمند منوساز سر میز عروس و داماد، تسهیم هزینه دو خانواده و قفل ضدتورم',
    defaultBrandName: 'عمارت و باغ‌تالار تشریفات سلطنتی قصر طلایی',
    defaultTagline: 'برگزاری مجلل‌ترین مراسم عروسی، نامزدی و همایش‌های VIP با منوی سلف‌سرویس و گل‌آرایی هلندی',
    unionTitle: 'فرمول رسمی منوی پذیرایی + خدمات تشریفات + تسهیم شفاف سهم خانواده عروس و داماد',
    accentColor: '#3B182E',
  },
};

// Helper to generate a Gold-Foil Invoice PNG image on the fly and download it
export function downloadGoldFoilInvoicePng(params: {
  brandName: string;
  managerName: string;
  city: string;
  phone: string;
  guildBadge: string;
  clientName: string;
  itemTitle: string;
  itemSubtitle: string;
  totalAmountFormatted: string;
  cashDownFormatted: string;
  checkInstallmentFormatted: string;
  checkCount: number;
  sayadiStatusText: string;
  inflationLockCode: string;
  extraTechnicalLine1: string;
  extraTechnicalLine2: string;
}) {
  const canvas = document.createElement('canvas');
  canvas.width = 1080;
  canvas.height = 1350;
  const ctx = canvas.getContext('2d');
  if (!ctx) return;

  // Deep Luxury Espresso/Gold Gradient Background
  const grad = ctx.createLinearGradient(0, 0, 1080, 1350);
  grad.addColorStop(0, '#24140B');
  grad.addColorStop(0.5, '#3A2214');
  grad.addColorStop(1, '#1A0E07');
  ctx.fillStyle = grad;
  ctx.fillRect(0, 0, 1080, 1350);

  // Double 24K Gold Border
  ctx.strokeStyle = '#D4AF37';
  ctx.lineWidth = 8;
  ctx.strokeRect(36, 36, 1008, 1278);
  ctx.strokeStyle = '#F6E27A';
  ctx.lineWidth = 2;
  ctx.strokeRect(52, 52, 976, 1246);

  ctx.direction = 'rtl';
  ctx.textAlign = 'center';

  // Header Badge
  ctx.fillStyle = '#F6E27A';
  ctx.font = 'bold 24px Tahoma, sans-serif';
  ctx.fillText(params.guildBadge, 540, 115);

  // Brand Name
  ctx.fillStyle = '#FFFFFF';
  ctx.font = 'bold 46px Tahoma, sans-serif';
  ctx.fillText(`👑 ${params.brandName}`, 540, 185);

  // Manager & City
  ctx.fillStyle = '#D4AF37';
  ctx.font = 'bold 26px Tahoma, sans-serif';
  ctx.fillText(`مدیریت: ${params.managerName}  |  ${params.city}  |  تلفن: ${params.phone}`, 540, 235);

  // Divider
  ctx.strokeStyle = '#D4AF37';
  ctx.lineWidth = 2;
  ctx.beginPath();
  ctx.moveTo(90, 270);
  ctx.lineTo(990, 270);
  ctx.stroke();

  // Client & Date
  ctx.textAlign = 'right';
  ctx.fillStyle = '#FAF7F2';
  ctx.font = 'bold 28px Tahoma, sans-serif';
  ctx.fillText(`👤 خریدار / کارفرما: ${params.clientName || 'مشتری گرامی VIP'}`, 960, 330);
  ctx.fillText(`📅 تاریخ صدور: ${new Date().toLocaleDateString('fa-IR')}`, 420, 330);

  // Item Box
  ctx.fillStyle = 'rgba(255,255,255,0.08)';
  ctx.fillRect(80, 365, 920, 220);
  ctx.strokeStyle = '#D4AF37';
  ctx.strokeRect(80, 365, 920, 220);

  ctx.fillStyle = '#F6E27A';
  ctx.font = 'bold 30px Tahoma, sans-serif';
  ctx.fillText(`📌 موضوع قرارداد: ${params.itemTitle}`, 965, 420);

  ctx.fillStyle = '#FFFFFF';
  ctx.font = '24px Tahoma, sans-serif';
  ctx.fillText(params.itemSubtitle, 965, 470);
  ctx.fillStyle = '#A7F3D0';
  ctx.font = 'bold 23px Tahoma, sans-serif';
  ctx.fillText(`🔧 ${params.extraTechnicalLine1}`, 965, 518);
  ctx.fillText(`📐 ${params.extraTechnicalLine2}`, 965, 560);

  // Financial Box
  ctx.fillStyle = 'rgba(212,175,55,0.14)';
  ctx.fillRect(80, 615, 920, 320);
  ctx.strokeStyle = '#F6E27A';
  ctx.strokeRect(80, 615, 920, 320);

  ctx.fillStyle = '#F6E27A';
  ctx.font = 'bold 38px Tahoma, sans-serif';
  ctx.fillText(`💰 مبلغ کل قطعی: ${params.totalAmountFormatted}`, 965, 680);

  ctx.fillStyle = '#FFFFFF';
  ctx.font = 'bold 30px Tahoma, sans-serif';
  ctx.fillText(`💵 پیش‌پرداخت نقدی قرارداد: ${params.cashDownFormatted}`, 965, 750);

  ctx.fillStyle = '#E9D5FF';
  ctx.font = 'bold 30px Tahoma, sans-serif';
  ctx.fillText(
    `🟣 تقسیط در ${params.checkCount} فقره چک صیادی بنفش (هر چک): ${params.checkInstallmentFormatted}`,
    965,
    820
  );

  ctx.fillStyle = '#6EE7B7';
  ctx.font = 'bold 25px Tahoma, sans-serif';
  ctx.fillText(`🏦 وضعیت استعلام صیاد: ${params.sayadiStatusText}`, 965, 890);

  // Guarantee Stamp Box
  ctx.fillStyle = 'rgba(16, 185, 129, 0.18)';
  ctx.fillRect(80, 965, 920, 165);
  ctx.strokeStyle = '#34D399';
  ctx.strokeRect(80, 965, 920, 165);

  ctx.fillStyle = '#A7F3D0';
  ctx.font = 'bold 28px Tahoma, sans-serif';
  ctx.fillText(
    `🛡️ گواهی قفل ضمانت قیمت ضدتورم فعال است (کد: ${params.inflationLockCode})`,
    965,
    1025
  );
  ctx.fillStyle = '#FFFFFF';
  ctx.font = '23px Tahoma, sans-serif';
  ctx.fillText(
    'نرخ کل قرارداد از لحظه پرداخت بیعانه تا تحویل نهایی ۱۰۰٪ ثابت و بدون افزایش قیمت می‌باشد.',
    965,
    1085
  );

  // Footer
  ctx.textAlign = 'center';
  ctx.fillStyle = '#F6E27A';
  ctx.font = 'bold 24px Tahoma, sans-serif';
  ctx.fillText(
    `صادرشده توسط سامانه رسمی هوش تجاری «${params.brandName}» — تماس و واتساپ: ${params.phone}`,
    540,
    1225
  );

  const link = document.createElement('a');
  link.download = `Gold-Invoice-${Date.now()}.png`;
  link.href = canvas.toDataURL('image/png');
  link.click();
}

interface SecretVisitorControlBarProps {
  activeGuild: ActiveGuildId;
  onSelectGuild: (g: ActiveGuildId) => void;
  visitorModeUnlocked: boolean;
  onToggleVisitorMode: () => void;
  tenantConfig: TenantExtendedConfig;
  onUpdateTenantConfig: (cfg: TenantExtendedConfig) => void;
  externalRepos: Record<string, string>;
  onUpdateExternalRepos: (repos: Record<string, string>) => void;
}

export const SecretVisitorControlBar: React.FC<SecretVisitorControlBarProps> = ({
  activeGuild,
  onSelectGuild,
  visitorModeUnlocked,
  onToggleVisitorMode,
  tenantConfig,
  onUpdateTenantConfig,
  externalRepos,
  onUpdateExternalRepos,
}) => {
  const [showRepoBridge, setShowRepoBridge] = useState(false);

  // When Visitor Mode is LOCKED (Clean Client Mode), we only show a tiny, discreet lock icon in the corner
  // so the shop owner / customer NEVER realizes this app supports other guilds or has visitor sales training!
  if (!visitorModeUnlocked) {
    return (
      <div className="bg-[#24140B] text-[#FAF7F2] border-b border-[#D4AF37]/30 px-4 sm:px-8 py-1.5 text-xs">
        <div className="max-w-[1440px] mx-auto flex items-center justify-between gap-2">
          <div className="flex items-center gap-2 text-[11px] text-[#F6E27A] font-bold">
            <ShieldCheck className="w-4 h-4 text-emerald-400 shrink-0" />
            <span>{GUILDS_META[activeGuild].badgeTitle}</span>
          </div>

          {/* Discreet Secret Key for the Visitor */}
          <button
            type="button"
            onClick={onToggleVisitorMode}
            title="کلید مخفی سفیر فروش (باز کردن تغییر صنف و پنل ۲۵٪ پورسانت)"
            className="px-2.5 py-1 rounded-lg bg-white/5 hover:bg-white/15 border border-white/10 text-white/60 hover:text-[#F6E27A] text-[11px] flex items-center gap-1 cursor-pointer transition-colors"
          >
            <Lock className="w-3 h-3" />
            <span>پنل مدیریت سیستم</span>
          </button>
        </div>
      </div>
    );
  }

  // When Visitor Mode is UNLOCKED: Full Multi-Guild Chameleon Switcher + 10-Second Tablet Skin + External Repo Bridge!
  return (
    <div className="bg-gradient-to-l from-[#1A0F08] via-[#2D1A0E] to-[#1A0F08] text-white border-b-2 border-[#D4AF37] px-4 sm:px-8 py-3 text-xs space-y-3 shadow-xl">
      <div className="max-w-[1440px] mx-auto flex flex-wrap items-center justify-between gap-2 border-b border-[#D4AF37]/30 pb-2.5">
        <div className="flex items-center gap-2">
          <span className="px-2.5 py-1 rounded-lg bg-emerald-600 text-white font-extrabold text-[11px] flex items-center gap-1">
            <Unlock className="w-3.5 h-3.5" />
            <span>حالت مخفی ویزیتور فعال است (مخصوص بازاریاب)</span>
          </span>
          <span className="text-[11px] text-[#F6E27A] font-bold">
            💡 قبل از نشان دادن تبلت به خریدار، صنف را انتخاب کرده و دکمه «قفل و مخفی‌سازی (نمای خالص خریدار)» را بزنید تا خریدار متوجه صنوف دیگر نشود!
          </span>
        </div>

        <div className="flex flex-wrap items-center gap-2">
          <button
            type="button"
            onClick={() => setShowRepoBridge(!showRepoBridge)}
            className="px-3 py-1.5 rounded-xl bg-white/10 hover:bg-white/20 border border-[#D4AF37]/50 text-[#F6E27A] font-bold text-xs flex items-center gap-1.5 cursor-pointer"
          >
            <GitBranch className="w-3.5 h-3.5" />
            <span>🔗 اتصال به ریپوزیتوری‌های اختصاصی شما (خودرو / دندانپزشکی / تالار)</span>
          </button>

          <button
            type="button"
            onClick={onToggleVisitorMode}
            className="px-4 py-1.5 rounded-xl bg-amber-500 hover:bg-amber-400 text-[#1A0F08] font-extrabold text-xs flex items-center gap-1.5 cursor-pointer shadow-md"
          >
            <EyeOff className="w-4 h-4" />
            <span>🔒 قفل و مخفی‌سازی (نمای ۱۰۰٪ خالص و خلوت مخصوص خریدار)</span>
          </button>
        </div>
      </div>

      {/* 7-Guild Chameleon Selector */}
      <div className="max-w-[1440px] mx-auto space-y-2">
        <div className="text-[11px] font-bold text-emerald-300">
          🦎 انتخابگر ۷ صنف پول‌ساز (تغییر قیافه ۱۰۰٪ اختصاصی کل برنامه در ۱ ثانیه بدون دیده شدن سایر صنوف توسط خریدار):
        </div>
        <div className="grid grid-cols-2 sm:grid-cols-4 lg:grid-cols-7 gap-2">
          {(Object.keys(GUILDS_META) as ActiveGuildId[]).map((gKey) => {
            const g = GUILDS_META[gKey];
            const isActive = activeGuild === gKey;
            return (
              <button
                key={gKey}
                type="button"
                onClick={() => {
                  onSelectGuild(gKey);
                  onUpdateTenantConfig({
                    ...tenantConfig,
                    brandName: g.defaultBrandName,
                    tagline: g.defaultTagline,
                  });
                }}
                className={`p-2.5 rounded-xl text-right font-bold text-xs border transition-all cursor-pointer ${
                  isActive
                    ? 'bg-[#D4AF37] text-[#1A0F08] border-white shadow-md scale-[1.01]'
                    : 'bg-white/10 text-white hover:bg-white/20 border-[#D4AF37]/40'
                }`}
              >
                <div className="truncate">{g.shortTitle}</div>
                <div
                  className={`text-[10px] mt-0.5 truncate ${
                    isActive ? 'text-[#1A0F08]/80 font-extrabold' : 'text-white/65'
                  }`}
                >
                  {isActive ? '✓ صنف فعال فعلی' : 'کلیک برای سوییچ آنی'}
                </div>
              </button>
            );
          })}
        </div>
      </div>

      {/* Optional Bridge to User's Existing Separate GitHub Repos / Apps */}
      {showRepoBridge && (
        <div className="max-w-[1440px] mx-auto p-4 rounded-2xl bg-black/50 border border-[#D4AF37] space-y-3">
          <div className="flex items-center justify-between">
            <div className="font-extrabold text-[#F6E27A] text-xs">
              🔗 پل یکپارچه‌ساز ریپوزیتوری‌های گیت‌هاب و برنامه‌های تخصصی ساخته‌شده توسط شما:
            </div>
            <span className="text-[11px] text-emerald-300">
              می‌توانید آدرس گیت‌هاب یا لینک اپ‌های جداگانه خودتان را هم در اینجا متصل و ذخیره کنید
            </span>
          </div>
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 text-xs">
            <div>
              <label className="block text-white/80 mb-1">
                🚗 لینک ریپوزیتوری / اپ خودرو و تهاتر ملک شما:
              </label>
              <input
                type="text"
                dir="ltr"
                value={externalRepos.auto || ''}
                onChange={(e) =>
                  onUpdateExternalRepos({ ...externalRepos, auto: e.target.value })
                }
                placeholder="https://github.com/your-username/auto-barter-vip"
                className="w-full px-3 py-2 rounded-xl bg-white/10 border border-[#D4AF37]/50 text-white font-mono-tabular"
              />
            </div>
            <div>
              <label className="block text-white/80 mb-1">
                🦷 لینک ریپوزیتوری / اپ کلینیک زیبایی و دندانپزشکی شما:
              </label>
              <input
                type="text"
                dir="ltr"
                value={externalRepos.dental || ''}
                onChange={(e) =>
                  onUpdateExternalRepos({ ...externalRepos, dental: e.target.value })
                }
                placeholder="https://github.com/your-username/dental-clinic-vip"
                className="w-full px-3 py-2 rounded-xl bg-white/10 border border-[#D4AF37]/50 text-white font-mono-tabular"
              />
            </div>
            <div>
              <label className="block text-white/80 mb-1">
                👑 لینک ریپوزیتوری / اپ تالار عروسی و تشریفات شما:
              </label>
              <input
                type="text"
                dir="ltr"
                value={externalRepos.wedding || ''}
                onChange={(e) =>
                  onUpdateExternalRepos({ ...externalRepos, wedding: e.target.value })
                }
                placeholder="https://github.com/your-username/wedding-palace-vip"
                className="w-full px-3 py-2 rounded-xl bg-white/10 border border-[#D4AF37]/50 text-white font-mono-tabular"
              />
            </div>
          </div>
        </div>
      )}

      {/* 10-Second Visitor Top Bar (Only shown when Visitor Mode is UNLOCKED so Client View stays 100% clean!) */}
      <div className="max-w-[1440px] mx-auto pt-2 border-t border-[#D4AF37]/30 space-y-2">
        <div className="flex flex-wrap items-center justify-between gap-2">
          <div className="flex flex-wrap items-center gap-1.5">
            <a
              href="#isolated-tenant-architecture"
              className="px-2.5 py-1 rounded-lg bg-[#D4AF37] text-[#2A1A10] font-extrabold"
            >
              ⚡ ۱. نوار ۱۰ ثانیه‌ای تبلت ویزیتور
            </a>
            <a
              href="#calculator"
              className="px-2.5 py-1 rounded-lg bg-purple-800 text-white font-bold"
            >
              🟣 ۲. ماشین‌حساب + چک صیادی + پیامک و تصویر فاکتور
            </a>
            <a
              href="#deliverables"
              className="px-2.5 py-1 rounded-lg bg-emerald-700 text-white font-bold"
            >
              🎁 ۳. دستاوردهای خریدار + ROI (#deliverables)
            </a>
            <a
              href="#visitor-playbook"
              className="px-2.5 py-1 rounded-lg bg-white/15 text-[#F6E27A] font-bold"
            >
              🧭 ۴. راهنمای ویزیتورها (#visitor-playbook)
            </a>
            <a
              href="#golden-formula"
              className="px-2.5 py-1 rounded-lg bg-amber-500/25 text-[#F6E27A] font-bold"
            >
              🏆 فرمول طلایی فروش (#golden-formula)
            </a>
            <a
              href="#invitation-letter"
              className="px-2.5 py-1 rounded-lg bg-white/15 text-emerald-300 font-bold"
            >
              📜 ۵. دعوت‌نامه طلاکوب + شبا ۲۵٪ (#invitation-letter)
            </a>
            <a
              href="#tavana-academy-simulator"
              className="px-2.5 py-1 rounded-lg bg-[#D4AF37] text-[#1A0F08] font-extrabold"
            >
              🎓 ۶. آکادمی سفیران توانا سیتی (A++ تا C)
            </a>
          </div>
          <span className="text-[11px] font-mono-tabular text-[#F6E27A]">
            شناسه ایزوله: {tenantConfig.slug} | کد سفیر: ?ref={tenantConfig.refCode}
          </span>
        </div>

        <div className="grid grid-cols-2 sm:grid-cols-4 lg:grid-cols-7 gap-1.5 items-center">
          <input
            type="text"
            value={tenantConfig.brandName}
            onChange={(e) =>
              onUpdateTenantConfig({ ...tenantConfig, brandName: e.target.value })
            }
            placeholder="نام کسب‌وکار..."
            className="px-2.5 py-1.5 rounded-lg bg-white/10 border border-[#D4AF37]/50 text-white text-[11px] font-bold"
          />
          <input
            type="text"
            value={tenantConfig.managerName}
            onChange={(e) =>
              onUpdateTenantConfig({ ...tenantConfig, managerName: e.target.value })
            }
            placeholder="نام مدیر..."
            className="px-2.5 py-1.5 rounded-lg bg-white/10 border border-[#D4AF37]/50 text-white text-[11px] font-bold"
          />
          <input
            type="text"
            value={tenantConfig.city}
            onChange={(e) => onUpdateTenantConfig({ ...tenantConfig, city: e.target.value })}
            placeholder="شهر / منطقه..."
            className="px-2.5 py-1.5 rounded-lg bg-white/10 border border-[#D4AF37]/50 text-white text-[11px]"
          />
          <input
            type="text"
            dir="ltr"
            value={tenantConfig.phone}
            onChange={(e) =>
              onUpdateTenantConfig({
                ...tenantConfig,
                phone: e.target.value,
                whatsapp: e.target.value.replace(/\D/g, '').replace(/^0/, '98') || '989120000000',
              })
            }
            placeholder="تلفن/واتساپ: 0912..."
            className="px-2.5 py-1.5 rounded-lg bg-white/10 border border-[#D4AF37]/50 text-white text-[11px] font-mono-tabular"
          />
          <input
            type="text"
            value={tenantConfig.tagline}
            onChange={(e) =>
              onUpdateTenantConfig({ ...tenantConfig, tagline: e.target.value })
            }
            placeholder="شعار برند..."
            className="px-2.5 py-1.5 rounded-lg bg-white/10 border border-[#D4AF37]/50 text-white text-[11px]"
          />
          <select
            value={tenantConfig.priceMultiplier}
            onChange={(e) =>
              onUpdateTenantConfig({
                ...tenantConfig,
                priceMultiplier: Number(e.target.value) || 1.0,
              })
            }
            className="px-2 py-1.5 rounded-lg bg-[#2A1A10] border border-[#D4AF37]/50 text-[#F6E27A] text-[11px] font-bold font-mono-tabular"
          >
            <option value={0.9}>ضریب قیمت: ۰.۹ (-۱۰٪)</option>
            <option value={0.95}>ضریب قیمت: ۰.۹۵ (-۵٪)</option>
            <option value={1.0}>ضریب قیمت: ۱.۰ (پایه)</option>
            <option value={1.08}>ضریب قیمت: ۱.۰۸ (+۸٪)</option>
            <option value={1.15}>ضریب قیمت: ۱.۱۵ (+۱۵٪)</option>
          </select>
          <input
            type="text"
            dir="ltr"
            value={tenantConfig.refCode}
            onChange={(e) =>
              onUpdateTenantConfig({ ...tenantConfig, refCode: e.target.value })
            }
            placeholder="کد سفیر: VIP-25"
            className="px-2.5 py-1.5 rounded-lg bg-emerald-900/40 border border-emerald-400/50 text-emerald-200 text-[11px] font-mono-tabular font-bold"
          />
        </div>
      </div>
    </div>
  );
};

// ============================================================================
// DEDICATED ISOLATED FULL-PAGE PORTALS FOR OTHER GUILDS
// (Auto Gallery + Barter, Dental + Implant/Laminate, Gold + Jewelry, Wedding Venue)
// ============================================================================
interface DedicatedGuildViewProps {
  activeGuild: ActiveGuildId;
  tenantConfig: TenantExtendedConfig;
  currency: CurrencyCode;
  lang: LanguageCode;
  externalRepoUrl?: string;
}

export const DedicatedGuildPortal: React.FC<DedicatedGuildViewProps> = ({
  activeGuild,
  tenantConfig,
  currency,
  lang,
  externalRepoUrl,
}) => {
  const [clientName, setClientName] = useState('جناب مهندس رادمان');
  const [downPaymentPct, setDownPaymentPct] = useState(40);
  const [checkCount, setCheckCount] = useState(6);
  const [sayadiStatus, setSayadiStatus] = useState<'white' | 'yellow' | 'red'>('white');
  const [sayadiId, setSayadiId] = useState('9104827563019482');
  const [inflationLocked, setInflationLocked] = useState(true);
  const [partnerSplitPct, setPartnerSplitPct] = useState(50);

  // 1. Auto Gallery & Property/Car Barter State
  const [carModel, setCarModel] = useState('فونیکس تیگو ۸ پرومکس IE / هیوندای توسان ۲۰۲۴');
  const [carPriceToman, setCarPriceToman] = useState(3450000000); // 3.45 Billion Toman
  const [monthlyInterestPct, setMonthlyInterestPct] = useState(3.5);
  const [barterPropertyValueToman, setBarterPropertyValueToman] = useState(5200000000); // 5.2 Billion Toman Apartment/Villa
  const [barterMode, setBarterMode] = useState(true);
  const [paintCondition, setPaintCondition] = useState('بی‌رنگ و بدون خط‌وخش — شاسی پلمپ (تایید کارشناسی رسمی)');

  // 2. Dental Clinic (Implant & Laminate) State
  const [implantUnits, setImplantUnits] = useState(4);
  const [implantBrandPriceToman, setImplantBrandPriceToman] = useState(24500000); // Straumann Swiss per unit
  const [laminateUnits, setLaminateUnits] = useState(8);
  const [laminateUnitPriceToman, setLaminateUnitPriceToman] = useState(11500000); // IPS e.max Ceramic
  const [includeBoneGraftAnd3DScan, setIncludeBoneGraftAnd3DScan] = useState(true);

  // 3. Gold & Jewelry State
  const [goldWeightGrams, setGoldWeightGrams] = useState(28.5);
  const [gold18kRateToman, setGold18kRateToman] = useState(4650000); // per gram
  const [makingFeePct, setMakingFeePct] = useState(14); // 14% Ojrat
  const [sellerProfitPct, setSellerProfitPct] = useState(7); // 7% Union Profit
  const [oldGoldTradeInGrams, setOldGoldTradeInGrams] = useState(6.0);

  // 4. Wedding Venue & Luxury Events State
  const [guestCount, setGuestCount] = useState(250);
  const [menuPerGuestToman, setMenuPerGuestToman] = useState(1650000);
  const [flowerAndMusicPackageToman, setFlowerAndMusicPackageToman] = useState(95000000);

  // 5. Women's Beauty Salon (Website Builder + Gallery + Special Day Discounts + Salon Accounting)
  const [salonServiceTitle, setSalonServiceTitle] = useState(
    'پکیج VIP میکاپ و شینیون عروس + لایت آمبره، کراتین ابریشمی و کاشت ناخن ژل'
  );
  const [salonGrossAmountToman, setSalonGrossAmountToman] = useState(18500000);
  const [salonMaterialCostToman, setSalonMaterialCostToman] = useState(2500000); // Deducted before split!
  const [stylistSharePct, setStylistSharePct] = useState(60); // 60% stylist, 40% salon owner
  const [specialDayDiscountPct, setSpecialDayDiscountPct] = useState(20); // e.g. Golden Tuesdays 20% off
  const [stylistName, setStylistName] = useState('سرکار خانم نیلوفر راد (مستر لایت و عروس)');
  const [salonLedger, setSalonLedger] = useState([
    {
      id: 'sl-1',
      dateFa: '۱۴۰۵/۰۷/۰۷',
      line: 'لاین رنگ و لایت و آمبره برزیلی',
      stylist: 'خانم نیلوفر راد',
      grossToman: 9500000,
      materialToman: 1500000,
      stylistShareToman: 4800000,
      salonNetToman: 3200000,
    },
    {
      id: 'sl-2',
      dateFa: '۱۴۰۵/۰۷/۰۷',
      line: 'پکیج VIP میکاپ و شینیون عروس',
      stylist: 'خانم سارا کاظمی',
      grossToman: 24000000,
      materialToman: 3000000,
      stylistShareToman: 12600000,
      salonNetToman: 8400000,
    },
    {
      id: 'sl-3',
      dateFa: '۱۴۰۵/۰۷/۰۷',
      line: 'لاین کاشت ناخن پودر/ژل و پدیکور VIP',
      stylist: 'خانم مریم تهرانی',
      grossToman: 1850000,
      materialToman: 250000,
      stylistShareToman: 960000,
      salonNetToman: 640000,
    },
  ]);

  // 6. Furniture, Bedding & Bridal Dowry Gallery State (FURNIMATE / مبلیار)
  const [sofaSetTitle, setSofaSetTitle] = useState(
    'مبلمان ۸ نفره نئوکلاسیک فرانسوی / چستر ایتالیایی'
  );
  const [sofaBasePriceToman, setSofaBasePriceToman] = useState(88000000);
  const [woodFrameType, setWoodFrameType] = useState('کلاف تمام چوب راش گرجستان + رنگ پلی‌اورتان ورق طلا');
  const [fabricMeters, setFabricMeters] = useState(32); // 32 meters of fabric for 8-seater + dining
  const [fabricDiffPerMeterToman, setFabricDiffPerMeterToman] = useState(380000); // Imported Turkish/Nano fabric upgrade per meter
  const [includeDiningTable8, setIncludeDiningTable8] = useState(true);
  const [includeBridalBedroomSet, setIncludeBridalBedroomSet] = useState(true);
  const [includeConsoleAndTvStand, setIncludeConsoleAndTvStand] = useState(true);
  const [bridalDowryDiscountPct, setBridalDowryDiscountPct] = useState(12);

  // Compute Guild-Specific Totals & Technical Details
  let itemTitle = '';
  let itemSubtitle = '';
  let totalCashToman = 0;
  let extraLine1 = '';
  let extraLine2 = '';

  if (activeGuild === 'auto_barter') {
    const baseCar = Math.round(carPriceToman * (tenantConfig.priceMultiplier || 1));
    const downCash = Math.round(baseCar * (downPaymentPct / 100));
    const principalRemaining = baseCar - downCash;
    const totalInterest = Math.round(principalRemaining * (monthlyInterestPct / 100) * checkCount);
    totalCashToman = baseCar + totalInterest;
    // Ras-e Check (Weighted Average Maturity of Equal Monthly Checks = (N + 1) / 2 months = (N + 1) * 15 days)
    const rasMonths = ((checkCount + 1) / 2).toFixed(1);
    const rasDays = Math.round(((checkCount + 1) / 2) * 30);
    const barterDiff = barterPropertyValueToman - baseCar;

    itemTitle = `قرارداد فروش / تهاتر خودرو: ${carModel}`;
    itemSubtitle = `وضعیت بدنه و کارشناسی: ${paintCondition}`;
    extraLine1 = `راس دقیق ${checkCount} فقره چک صیادی: ${rasMonths} ماه (${rasDays} روز) | کارمزد کل اقساط: ${formatPrice(totalInterest, currency, lang)}`;
    extraLine2 = barterMode
      ? `تراز تهاتر ملک (${formatPrice(barterPropertyValueToman, currency, lang)}) با خودرو: ${
          barterDiff >= 0
            ? `مابه‌التفاوت سرانه پرداختی به مالک ملک: ${formatPrice(barterDiff, currency, lang)}`
            : `مابه‌التفاوت دریافتی از مالک ملک: ${formatPrice(Math.abs(barterDiff), currency, lang)}`
        }`
      : `قیمت نقد خودرو: ${formatPrice(baseCar, currency, lang)} | اقساط با سند در رهن تا تسویه نهایی`;
  } else if (activeGuild === 'dental_aesthetic') {
    const implantSum = implantUnits * implantBrandPriceToman;
    const laminateSum = laminateUnits * laminateUnitPriceToman;
    const scanAndGraft = includeBoneGraftAnd3DScan ? 16500000 : 0;
    totalCashToman = Math.round(
      (implantSum + laminateSum + scanAndGraft) * (tenantConfig.priceMultiplier || 1)
    );
    itemTitle = `طرح درمان دیجیتال: ${implantUnits} واحد ایمپلنت + ${laminateUnits} واحد لمینت سرامیکی`;
    itemSubtitle = `شامل ضمانت‌نامه کتبی ۱۰ ساله ایمپلنت سوئیسی و طراحی لبخند دیجیتال (DSD)`;
    extraLine1 = `هزینه ${implantUnits} واحد ایمپلنت (با روکش زیرکونیا): ${formatPrice(implantSum, currency, lang)} | ${laminateUnits} واحد لمینت: ${formatPrice(laminateSum, currency, lang)}`;
    extraLine2 = includeBoneGraftAnd3DScan
      ? 'شامل اسکن سه‌بعدی داخل دهانی، پودر استخوان بیوممتیک و جراحی بدون درد با لیزر'
      : 'بدون نیاز به پیوند استخوان اضافه — قالب‌گیری دیجیتال';
  } else if (activeGuild === 'gold_jewelry') {
    const rawGoldPrice = goldWeightGrams * gold18kRateToman;
    const ojratAmount = rawGoldPrice * (makingFeePct / 100);
    const profitAmount = (rawGoldPrice + ojratAmount) * (sellerProfitPct / 100);
    // Official Iranian Tax Law: 9% VAT ONLY on (Ojrat + Profit), NOT on raw gold weight!
    const vat9OnFeeAndProfit = (ojratAmount + profitAmount) * 0.09;
    const oldGoldCredit = oldGoldTradeInGrams * (gold18kRateToman * 0.985);
    totalCashToman = Math.max(
      1000000,
      Math.round(
        (rawGoldPrice + ojratAmount + profitAmount + vat9OnFeeAndProfit - oldGoldCredit) *
          (tenantConfig.priceMultiplier || 1)
      )
    );
    itemTitle = `فاکتور رسمی طلای ۱۸ عیار (وزن: ${goldWeightGrams} گرم — اجرت: ${makingFeePct}٪)`;
    itemSubtitle = `محاسبه دقیق طبق قانون سامانه مودیان (۹٪ مالیات فقط روی اجرت و سود، معافیت اصل طلا)`;
    extraLine1 = `ارزش طلای خام: ${formatPrice(rawGoldPrice, currency, lang)} | اجرت (${makingFeePct}٪) + سود (${sellerProfitPct}٪): ${formatPrice(ojratAmount + profitAmount, currency, lang)}`;
    extraLine2 = `مالیات ۹٪ اجرت و سود: ${formatPrice(vat9OnFeeAndProfit, currency, lang)} | کسر طلای کهنه (${oldGoldTradeInGrams} گرم): -${formatPrice(oldGoldCredit, currency, lang)}`;
  } else if (activeGuild === 'beauty_salon') {
    const discountedGross = Math.round(
      salonGrossAmountToman * (1 - specialDayDiscountPct / 100) * (tenantConfig.priceMultiplier || 1)
    );
    const netAfterMaterial = Math.max(0, discountedGross - salonMaterialCostToman);
    const stylistCut = Math.round(netAfterMaterial * (stylistSharePct / 100));
    const salonOwnerCut = netAfterMaterial - stylistCut;
    totalCashToman = discountedGross;
    itemTitle = `${salonServiceTitle} (تخفیف روزهای خاص: ${specialDayDiscountPct}٪)`;
    itemSubtitle = `متخصص لاین: ${stylistName} | کسر خودکار هزینه مواد مصرفی قبل از تقسیم درصد`;
    extraLine1 = `هزینه مواد مصرفی کسرشده: ${formatPrice(salonMaterialCostToman, currency, lang)} | خالص قابل تقسیم: ${formatPrice(netAfterMaterial, currency, lang)}`;
    extraLine2 = `سهم خالص پرسنل (${stylistSharePct}٪): ${formatPrice(stylistCut, currency, lang)} | سود خالص مدیریت سالن (${100 - stylistSharePct}٪): ${formatPrice(salonOwnerCut, currency, lang)}`;
  } else if (activeGuild === 'furniture_bridal') {
    const diningCost = includeDiningTable8 ? 42000000 : 0;
    const bedroomCost = includeBridalBedroomSet ? 48000000 : 0;
    const consoleCost = includeConsoleAndTvStand ? 24000000 : 0;
    const fabricUpgradeTotal = fabricMeters * fabricDiffPerMeterToman;
    const rawPackageTotal =
      sofaBasePriceToman + diningCost + bedroomCost + consoleCost + fabricUpgradeTotal;
    totalCashToman = Math.round(
      rawPackageTotal *
        (1 - bridalDowryDiscountPct / 100) *
        (tenantConfig.priceMultiplier || 1)
    );
    itemTitle = `پکیج جهیزیه و مبلمان: ${sofaSetTitle} (${woodFrameType})`;
    itemSubtitle = `شامل گواهی کتبی ۵ سال ضمانت فوم سرد یورتان و کلاف چوب راش + ${bridalDowryDiscountPct}٪ هدیه جهیزیه عروس`;
    extraLine1 = `متراژ پارچه مصرفی: ${fabricMeters} متر (مابه‌التفاوت پارچه نانو/ترک: ${formatPrice(fabricUpgradeTotal, currency, lang)})`;
    extraLine2 = `اقلام همراه: ${includeDiningTable8 ? '✓ ناهارخوری ست ' : ''}${includeBridalBedroomSet ? '✓ سرویس خواب کامل عروس ' : ''}${includeConsoleAndTvStand ? '✓ آینه کنسول و میز TV' : ''}`;
  } else {
    // wedding_venue
    const menuTotal = guestCount * menuPerGuestToman;
    totalCashToman = Math.round(
      (menuTotal + flowerAndMusicPackageToman) * (tenantConfig.priceMultiplier || 1)
    );
    itemTitle = `قرارداد تشریفات عروسی VIP (${guestCount} مهمان + گل‌آرایی و موزیک زنده)`;
    itemSubtitle = `شامل قفل ضدتورم نرخ گوشت، برنج و گل طبیعی از لحظه عقد قرارداد تا شب مراسم`;
    extraLine1 = `هزینه منوی پذیرایی (${guestCount} نفر): ${formatPrice(menuTotal, currency, lang)} | پکیج دیزاین، گل‌آرایی و نورپردازی: ${formatPrice(flowerAndMusicPackageToman, currency, lang)}`;
    extraLine2 = `سهم خانواده اول (${partnerSplitPct}٪): ${formatPrice(
      Math.round(totalCashToman * (partnerSplitPct / 100)),
      currency,
      lang
    )} | سهم خانواده دوم (${100 - partnerSplitPct}٪): ${formatPrice(
      Math.round(totalCashToman * ((100 - partnerSplitPct) / 100)),
      currency,
      lang
    )}`;
  }

  const downCashToman = Math.round(totalCashToman * (downPaymentPct / 100));
  const remainingForChecksToman = Math.max(0, totalCashToman - downCashToman);
  const eachCheckToman = Math.round(remainingForChecksToman / checkCount);

  const sayadiBadgeText =
    sayadiStatus === 'white'
      ? 'وضعیت سفید بانک مرکزی (امتیاز خوش‌حسابی A+ تایید شد)'
      : sayadiStatus === 'yellow'
      ? 'وضعیت زرد (نیاز به ضامن معتبر)'
      : 'وضعیت قرمز (عدم تایید چک)';

  const buildInvoiceText = () => {
    return `👑 *پیش‌فاکتور رسمی طلاکوب — ${tenantConfig.brandName}*
━━━━━━━━━━━━━━━━━━
🏛️ *واحد صنفی:* ${tenantConfig.brandName} (${tenantConfig.city})
👤 *مدیریت:* ${tenantConfig.managerName} | 📞 ${tenantConfig.phone}
👤 *خریدار / متقاضی:* ${clientName || 'مشتری گرامی VIP'}
📌 *موضوع قرارداد:* ${itemTitle}
📝 *مشخصات:* ${itemSubtitle}
🔧 ${extraLine1}
📐 ${extraLine2}
━━━━━━━━━━━━━━━━━━
💰 *مبلغ کل قطعی:* ${formatPrice(totalCashToman, currency, lang)}
💵 *پیش‌پرداخت نقد (${downPaymentPct}٪):* ${formatPrice(downCashToman, currency, lang)}
🟣 *اقساط در ${checkCount} فقره چک صیادی بنفش (هر چک):* ${formatPrice(eachCheckToman, currency, lang)}
🏦 *استعلام صیاد (${sayadiId}):* ${sayadiBadgeText}
🛡️ *قفل ضدتورم:* ${inflationLocked ? 'فعال و تضمین‌شده تا تحویل نهایی (INF-SHIELD-1405)' : 'غیرفعال'}
⚖️ *تسهیم هزینه:* طرف اول (${partnerSplitPct}٪): ${formatPrice(
      Math.round(totalCashToman * (partnerSplitPct / 100)),
      currency,
      lang
    )} | طرف دوم (${100 - partnerSplitPct}٪): ${formatPrice(
      Math.round(totalCashToman * ((100 - partnerSplitPct) / 100)),
      currency,
      lang
    )}`;
  };

  const handleSendWhatsApp = () => {
    const cleanPhone = tenantConfig.whatsapp.replace(/\D/g, '') || '989120000000';
    window.location.href = `https://wa.me/${cleanPhone}?text=${encodeURIComponent(
      buildInvoiceText()
    )}`;
  };

  const handleSendSMS = () => {
    const cleanSmsPhone = tenantConfig.phone.replace(/\D/g, '') || '09120000000';
    window.location.href = `sms:${cleanSmsPhone}?body=${encodeURIComponent(buildInvoiceText())}`;
  };

  const handleDownloadPng = () => {
    downloadGoldFoilInvoicePng({
      brandName: tenantConfig.brandName,
      managerName: tenantConfig.managerName,
      city: tenantConfig.city,
      phone: tenantConfig.phone,
      guildBadge: GUILDS_META[activeGuild].badgeTitle,
      clientName,
      itemTitle,
      itemSubtitle,
      totalAmountFormatted: formatPrice(totalCashToman, currency, lang),
      cashDownFormatted: formatPrice(downCashToman, currency, lang),
      checkInstallmentFormatted: formatPrice(eachCheckToman, currency, lang),
      checkCount,
      sayadiStatusText: sayadiBadgeText,
      inflationLockCode: 'INF-SHIELD-1405-VIP',
      extraTechnicalLine1: extraLine1,
      extraTechnicalLine2: extraLine2,
    });
  };

  return (
    <section
      id="calculator"
      aria-label={GUILDS_META[activeGuild].badgeTitle}
      className="py-8 px-4 sm:px-8 max-w-[1440px] mx-auto space-y-8"
    >
      {/* Guild-Dedicated Hero Banner */}
      <div className="hc-card rounded-3xl bg-gradient-to-l from-[#24140B] via-[#382213] to-[#1A0E07] text-white border-2 border-[#D4AF37] p-6 sm:p-10 shadow-xl space-y-4">
        <div className="flex flex-wrap items-center justify-between gap-2">
          <span className="px-3 py-1 rounded-xl bg-[#D4AF37] text-[#24140B] text-xs font-extrabold">
            {GUILDS_META[activeGuild].badgeTitle}
          </span>
          {externalRepoUrl && (
            <a
              href={externalRepoUrl}
              target="_blank"
              rel="noreferrer"
              className="px-3 py-1 rounded-xl bg-emerald-600/30 border border-emerald-400 text-emerald-200 text-xs font-bold flex items-center gap-1.5"
            >
              <GitBranch className="w-3.5 h-3.5" />
              <span>متصل به ریپوزیتوری اختصاصی: {externalRepoUrl}</span>
              <ExternalLink className="w-3.5 h-3.5" />
            </a>
          )}
        </div>
        <h1 className="text-2xl sm:text-4xl font-extrabold text-[#FAF7F2]">
          {tenantConfig.brandName} — {tenantConfig.city}
        </h1>
        <p className="text-xs sm:text-base text-[#F6E27A]">{tenantConfig.tagline}</p>
      </div>

      {/* Guild-Specific Interactive Calculator & Sayadi Check Suite */}
      <div className="hc-card bg-white border-2 border-[#D4AF37] rounded-3xl p-6 sm:p-8 grid grid-cols-1 lg:grid-cols-12 gap-8 items-start">
        {/* Left Column: Inputs tailored 100% to the active guild */}
        <div className="lg:col-span-7 space-y-5">
          <div className="border-b border-[#E6DEC8] pb-4">
            <div className="text-xs font-bold text-emerald-700 mb-1">
              {GUILDS_META[activeGuild].unionTitle}
            </div>
            <h2 className="text-xl sm:text-2xl font-extrabold text-[#4A2E1B]">
              {activeGuild === 'auto_barter' &&
                '🚗 ماشین‌حساب اقساط خودرو، راس‌گیری چک صیادی و تهاتر هوشمند ملک و خودرو'}
              {activeGuild === 'dental_aesthetic' &&
                '🦷 محاسبه‌گر طرح درمان دیجیتال ایمپلنت، لمینت سرامیکی و تقسیط چک صیادی'}
              {activeGuild === 'gold_jewelry' &&
                '💎 ماشین‌حساب مظنه طلای ۱۸ عیار، اجرت، مالیات ۹٪ سامانه مودیان و تعویض طلای کهنه'}
              {activeGuild === 'wedding_venue' &&
                '👑 منوساز زنده سر میز عروس و داماد + تسهیم هزینه دو خانواده و چک صیادی'}
              {activeGuild === 'beauty_salon' &&
                '💄 سایت‌ساز اختصاصی سالن زیبایی زنانه + تخفیف روزهای خاص + حسابداری لاین‌ها'}
              {activeGuild === 'furniture_bridal' &&
                '🪑 پکیج‌ساز هوشمند مبلمان، سرویس خواب و جهیزیه عروس (مبلیار) + محاسبه متراژ پارچه و چک صیادی'}
            </h2>
          </div>

          <div>
            <label className="block text-xs font-bold text-[#4A2E1B] mb-1">
              نام خریدار / متقاضی محترم (جهت درج در پیش‌فاکتور طلاکوب):
            </label>
            <input
              type="text"
              value={clientName}
              onChange={(e) => setClientName(e.target.value)}
              className="w-full px-4 py-2.5 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8] text-sm font-bold text-[#4A2E1B]"
            />
          </div>

          {/* A) AUTO GALLERY & BARTER INPUTS */}
          {activeGuild === 'auto_barter' && (
            <div className="space-y-4 p-4 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8]">
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                <div>
                  <label className="block text-xs font-bold text-[#4A2E1B] mb-1">
                    مدل خودروی مورد معامله:
                  </label>
                  <input
                    type="text"
                    value={carModel}
                    onChange={(e) => setCarModel(e.target.value)}
                    className="w-full px-3 py-2 rounded-xl bg-white border border-[#E6DEC8] text-xs font-bold"
                  />
                </div>
                <div>
                  <label className="block text-xs font-bold text-[#4A2E1B] mb-1">
                    وضعیت کارشناسی رنگ و شاسی:
                  </label>
                  <input
                    type="text"
                    value={paintCondition}
                    onChange={(e) => setPaintCondition(e.target.value)}
                    className="w-full px-3 py-2 rounded-xl bg-white border border-[#E6DEC8] text-xs"
                  />
                </div>
              </div>

              <div>
                <div className="flex justify-between text-xs font-bold text-[#4A2E1B] mb-1">
                  <span>قیمت روز خودرو (نقدی):</span>
                  <span className="font-mono-tabular text-emerald-700">
                    {formatPrice(carPriceToman, currency, lang)}
                  </span>
                </div>
                <input
                  type="range"
                  min={500000000}
                  max={15000000000}
                  step={50000000}
                  value={carPriceToman}
                  onChange={(e) => setCarPriceToman(Number(e.target.value))}
                  className="w-full accent-[#4A2E1B] cursor-pointer"
                />
              </div>

              <div>
                <div className="flex justify-between text-xs font-bold text-[#4A2E1B] mb-1">
                  <span>نرخ کارمزد ماهانه اقساط عرف بازار:</span>
                  <span className="font-mono-tabular">{monthlyInterestPct}٪ در ماه</span>
                </div>
                <input
                  type="range"
                  min={1.5}
                  max={6}
                  step={0.5}
                  value={monthlyInterestPct}
                  onChange={(e) => setMonthlyInterestPct(Number(e.target.value))}
                  className="w-full accent-[#4A2E1B] cursor-pointer"
                />
              </div>

              <div className="pt-2 border-t border-[#E6DEC8] space-y-2">
                <div className="flex items-center justify-between">
                  <span className="text-xs font-extrabold text-[#4A2E1B]">
                    🔄 موتور هوشمند تهاتر ملک / آپارتمان / زمین با خودرو:
                  </span>
                  <button
                    type="button"
                    onClick={() => setBarterMode(!barterMode)}
                    className={`px-3 py-1 rounded-lg text-xs font-bold cursor-pointer ${
                      barterMode ? 'bg-emerald-600 text-white' : 'bg-gray-200 text-gray-700'
                    }`}
                  >
                    {barterMode ? '✓ تهاتر ملک فعال است' : 'فعال‌سازی تهاتر ملک'}
                  </button>
                </div>
                {barterMode && (
                  <div>
                    <div className="flex justify-between text-xs font-bold text-[#4A2E1B] mb-1">
                      <span>ارزش کارشناسی ملک / آپارتمان طرف مقابل:</span>
                      <span className="font-mono-tabular text-purple-800">
                        {formatPrice(barterPropertyValueToman, currency, lang)}
                      </span>
                    </div>
                    <input
                      type="range"
                      min={1000000000}
                      max={25000000000}
                      step={100000000}
                      value={barterPropertyValueToman}
                      onChange={(e) => setBarterPropertyValueToman(Number(e.target.value))}
                      className="w-full accent-purple-700 cursor-pointer"
                    />
                  </div>
                )}
              </div>
            </div>
          )}

          {/* B) DENTAL CLINIC (IMPLANT & LAMINATE) INPUTS */}
          {activeGuild === 'dental_aesthetic' && (
            <div className="space-y-4 p-4 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8]">
              <div>
                <div className="flex justify-between text-xs font-bold text-[#4A2E1B] mb-1">
                  <span>تعداد واحد ایمپلنت دیجیتال (همراه با روکش تمام سرامیک زیرکونیا):</span>
                  <span className="font-mono-tabular text-emerald-700">{implantUnits} واحد</span>
                </div>
                <input
                  type="range"
                  min={0}
                  max={16}
                  value={implantUnits}
                  onChange={(e) => setImplantUnits(Number(e.target.value))}
                  className="w-full accent-[#4A2E1B] cursor-pointer"
                />
              </div>

              <div className="grid grid-cols-3 gap-2">
                {[
                  { label: 'ایمپلنت کره‌ای درجه ۱ (۱۶.۵ م)', price: 16500000 },
                  { label: 'ایمپلنت آلمانی/آمریکایی (۲۱ م)', price: 21000000 },
                  { label: 'ایمپلنت اشترومن سوئیس (۲۴.۵ م)', price: 24500000 },
                ].map((b) => (
                  <button
                    key={b.price}
                    type="button"
                    onClick={() => setImplantBrandPriceToman(b.price)}
                    className={`p-2.5 rounded-xl text-[11px] font-bold border cursor-pointer ${
                      implantBrandPriceToman === b.price
                        ? 'bg-[#4A2E1B] text-[#F6E27A] border-[#D4AF37]'
                        : 'bg-white text-[#4A2E1B] border-[#E6DEC8]'
                    }`}
                  >
                    {b.label}
                  </button>
                ))}
              </div>

              <div>
                <div className="flex justify-between text-xs font-bold text-[#4A2E1B] mb-1">
                  <span>تعداد واحد لمینت سرامیکی (IPS e.max) / اصلاح طرح لبخند:</span>
                  <span className="font-mono-tabular text-purple-800">{laminateUnits} واحد</span>
                </div>
                <input
                  type="range"
                  min={0}
                  max={20}
                  value={laminateUnits}
                  onChange={(e) => setLaminateUnits(Number(e.target.value))}
                  className="w-full accent-purple-700 cursor-pointer"
                />
              </div>

              <button
                type="button"
                onClick={() => setIncludeBoneGraftAnd3DScan(!includeBoneGraftAnd3DScan)}
                className={`w-full p-3 rounded-xl text-xs font-bold border flex items-center justify-between cursor-pointer ${
                  includeBoneGraftAnd3DScan
                    ? 'bg-emerald-50 border-emerald-400 text-emerald-900'
                    : 'bg-white border-[#E6DEC8] text-[#6F4E37]'
                }`}
              >
                <span>شامل اسکن سه‌بعدی داخل دهانی (3D Scanner) + پودر استخوان و جراحی لیزر</span>
                <span>{includeBoneGraftAnd3DScan ? '✓ فعال' : 'غیرفعال'}</span>
              </button>
            </div>
          )}

          {/* C) GOLD & JEWELRY INPUTS */}
          {activeGuild === 'gold_jewelry' && (
            <div className="space-y-4 p-4 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8]">
              <div>
                <div className="flex justify-between text-xs font-bold text-[#4A2E1B] mb-1">
                  <span>وزن طلای انتخابی (گرم طلای ۱۸ عیار ۷۵۰):</span>
                  <span className="font-mono-tabular text-emerald-700">{goldWeightGrams} گرم</span>
                </div>
                <input
                  type="range"
                  min={1}
                  max={150}
                  step={0.5}
                  value={goldWeightGrams}
                  onChange={(e) => setGoldWeightGrams(Number(e.target.value))}
                  className="w-full accent-[#4A2E1B] cursor-pointer"
                />
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 text-xs">
                <div>
                  <label className="block font-bold text-[#4A2E1B] mb-1">
                    نرخ لحظه‌ای هر گرم ۱۸ عیار:
                  </label>
                  <input
                    type="number"
                    value={gold18kRateToman}
                    onChange={(e) => setGold18kRateToman(Number(e.target.value) || 4650000)}
                    className="w-full px-3 py-2 rounded-xl bg-white border border-[#E6DEC8] font-mono-tabular font-bold"
                  />
                </div>
                <div>
                  <label className="block font-bold text-[#4A2E1B] mb-1">اجرت ساخت (%):</label>
                  <input
                    type="number"
                    value={makingFeePct}
                    onChange={(e) => setMakingFeePct(Number(e.target.value) || 0)}
                    className="w-full px-3 py-2 rounded-xl bg-white border border-[#E6DEC8] font-mono-tabular font-bold"
                  />
                </div>
                <div>
                  <label className="block font-bold text-[#4A2E1B] mb-1">
                    وزن طلای کهنه معاوضی (گرم):
                  </label>
                  <input
                    type="number"
                    value={oldGoldTradeInGrams}
                    onChange={(e) => setOldGoldTradeInGrams(Number(e.target.value) || 0)}
                    className="w-full px-3 py-2 rounded-xl bg-white border border-[#E6DEC8] font-mono-tabular font-bold text-emerald-700"
                  />
                </div>
              </div>
            </div>
          )}

          {/* D) WEDDING VENUE INPUTS */}
          {activeGuild === 'wedding_venue' && (
            <div className="space-y-4 p-4 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8]">
              <div>
                <div className="flex justify-between text-xs font-bold text-[#4A2E1B] mb-1">
                  <span>تعداد مهمانان مراسم:</span>
                  <span className="font-mono-tabular text-emerald-700">{guestCount} نفر</span>
                </div>
                <input
                  type="range"
                  min={50}
                  max={800}
                  step={10}
                  value={guestCount}
                  onChange={(e) => setGuestCount(Number(e.target.value))}
                  className="w-full accent-[#4A2E1B] cursor-pointer"
                />
              </div>

              <div>
                <div className="flex justify-between text-xs font-bold text-[#4A2E1B] mb-1">
                  <span>نرخ منوی سلف‌سرویس به ازای هر نفر:</span>
                  <span className="font-mono-tabular">
                    {formatPrice(menuPerGuestToman, currency, lang)}
                  </span>
                </div>
                <input
                  type="range"
                  min={650000}
                  max={4500000}
                  step={50000}
                  value={menuPerGuestToman}
                  onChange={(e) => setMenuPerGuestToman(Number(e.target.value))}
                  className="w-full accent-[#4A2E1B] cursor-pointer"
                />
              </div>
            </div>
          )}

          {/* E) WOMEN'S BEAUTY SALON (WEBSITE BUILDER + ACCOUNTING + SPECIAL DAY DISCOUNTS) */}
          {activeGuild === 'beauty_salon' && (
            <div className="space-y-4 p-4 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8]">
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs">
                <div>
                  <label className="block font-bold text-[#4A2E1B] mb-1">
                    پکیج خدمات زیبایی / عروس انتخابی:
                  </label>
                  <input
                    type="text"
                    value={salonServiceTitle}
                    onChange={(e) => setSalonServiceTitle(e.target.value)}
                    className="w-full px-3 py-2 rounded-xl bg-white border border-[#E6DEC8] font-bold"
                  />
                </div>
                <div>
                  <label className="block font-bold text-[#4A2E1B] mb-1">
                    نام آرایشگر / متخصص لاین:
                  </label>
                  <input
                    type="text"
                    value={stylistName}
                    onChange={(e) => setStylistName(e.target.value)}
                    className="w-full px-3 py-2 rounded-xl bg-white border border-[#E6DEC8]"
                  />
                </div>
              </div>

              <div>
                <div className="flex justify-between text-xs font-bold text-[#4A2E1B] mb-1">
                  <span>مبلغ پایه خدمات سالن (قبل از تخفیف روز خاص):</span>
                  <span className="font-mono-tabular text-emerald-700">
                    {formatPrice(salonGrossAmountToman, currency, lang)}
                  </span>
                </div>
                <input
                  type="range"
                  min={500000}
                  max={65000000}
                  step={500000}
                  value={salonGrossAmountToman}
                  onChange={(e) => setSalonGrossAmountToman(Number(e.target.value))}
                  className="w-full accent-[#4A2E1B] cursor-pointer"
                />
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 text-xs">
                <div>
                  <div className="flex justify-between font-bold text-[#4A2E1B] mb-1">
                    <span>🎁 تخفیف روز خاص:</span>
                    <span className="font-mono-tabular text-amber-700">{specialDayDiscountPct}٪</span>
                  </div>
                  <input
                    type="range"
                    min={0}
                    max={45}
                    step={5}
                    value={specialDayDiscountPct}
                    onChange={(e) => setSpecialDayDiscountPct(Number(e.target.value))}
                    className="w-full accent-amber-600 cursor-pointer"
                  />
                </div>

                <div>
                  <div className="flex justify-between font-bold text-[#4A2E1B] mb-1">
                    <span>🧴 کسر هزینه مواد:</span>
                    <span className="font-mono-tabular text-red-700">
                      {formatPrice(salonMaterialCostToman, currency, lang)}
                    </span>
                  </div>
                  <input
                    type="range"
                    min={0}
                    max={10000000}
                    step={250000}
                    value={salonMaterialCostToman}
                    onChange={(e) => setSalonMaterialCostToman(Number(e.target.value))}
                    className="w-full accent-red-600 cursor-pointer"
                  />
                </div>

                <div>
                  <div className="flex justify-between font-bold text-[#4A2E1B] mb-1">
                    <span>✂️ سهم آرایشگر:</span>
                    <span className="font-mono-tabular text-purple-800">{stylistSharePct}٪</span>
                  </div>
                  <input
                    type="range"
                    min={30}
                    max={80}
                    step={5}
                    value={stylistSharePct}
                    onChange={(e) => setStylistSharePct(Number(e.target.value))}
                    className="w-full accent-purple-700 cursor-pointer"
                  />
                </div>
              </div>

              <button
                type="button"
                onClick={() => {
                  const discounted = Math.round(
                    salonGrossAmountToman * (1 - specialDayDiscountPct / 100)
                  );
                  const net = Math.max(0, discounted - salonMaterialCostToman);
                  const sty = Math.round(net * (stylistSharePct / 100));
                  const sal = net - sty;
                  setSalonLedger([
                    {
                      id: `sl-${Date.now()}`,
                      dateFa: new Date().toLocaleDateString('fa-IR'),
                      line: salonServiceTitle,
                      stylist: stylistName,
                      grossToman: discounted,
                      materialToman: salonMaterialCostToman,
                      stylistShareToman: sty,
                      salonNetToman: sal,
                    },
                    ...salonLedger,
                  ]);
                }}
                className="w-full py-2.5 px-4 rounded-xl bg-[#4A153B] hover:bg-[#350D29] text-[#F6E27A] font-extrabold text-xs cursor-pointer"
              >
                + ثبت فوری این فاکتور در دفتر کل حسابداری لاین‌های آرایشگاه
              </button>
            </div>
          )}

          {/* F) FURNITURE, BEDDING & BRIDAL DOWRY PACKAGE BUILDER (FURNIMATE / مبلیار) */}
          {activeGuild === 'furniture_bridal' && (
            <div className="space-y-4 p-4 rounded-2xl bg-[#FAF7F2] border border-[#E6DEC8]">
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 text-xs">
                <div>
                  <label className="block font-bold text-[#4A2E1B] mb-1">
                    مدل ست مبلمان انتخابی (۷ / ۸ / ۹ نفره):
                  </label>
                  <input
                    type="text"
                    value={sofaSetTitle}
                    onChange={(e) => setSofaSetTitle(e.target.value)}
                    className="w-full px-3 py-2 rounded-xl bg-white border border-[#E6DEC8] font-bold"
                  />
                </div>
                <div>
                  <label className="block font-bold text-[#4A2E1B] mb-1">
                    جنس کلاف چوب و نوع رنگ (راش گرجستان / گردو / پلی‌اورتان):
                  </label>
                  <input
                    type="text"
                    value={woodFrameType}
                    onChange={(e) => setWoodFrameType(e.target.value)}
                    className="w-full px-3 py-2 rounded-xl bg-white border border-[#E6DEC8]"
                  />
                </div>
              </div>

              <div>
                <div className="flex justify-between text-xs font-bold text-[#4A2E1B] mb-1">
                  <span>قیمت پایه ست مبلمان (با پارچه استاندارد ایرانی/چینی):</span>
                  <span className="font-mono-tabular text-emerald-700">
                    {formatPrice(sofaBasePriceToman, currency, lang)}
                  </span>
                </div>
                <input
                  type="range"
                  min={35000000}
                  max={280000000}
                  step={2000000}
                  value={sofaBasePriceToman}
                  onChange={(e) => setSofaBasePriceToman(Number(e.target.value))}
                  className="w-full accent-[#4A2E1B] cursor-pointer"
                />
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-3 gap-3 text-xs">
                <div>
                  <div className="flex justify-between font-bold text-[#4A2E1B] mb-1">
                    <span>🧵 متراژ کل پارچه:</span>
                    <span className="font-mono-tabular text-purple-800">{fabricMeters} متر</span>
                  </div>
                  <input
                    type="range"
                    min={18}
                    max={55}
                    step={1}
                    value={fabricMeters}
                    onChange={(e) => setFabricMeters(Number(e.target.value))}
                    className="w-full accent-purple-700 cursor-pointer"
                  />
                </div>

                <div>
                  <div className="flex justify-between font-bold text-[#4A2E1B] mb-1">
                    <span>مابه‌التفاوت هر متر پارچه ترک/نانو:</span>
                    <span className="font-mono-tabular text-amber-800">
                      {formatPrice(fabricDiffPerMeterToman, currency, lang)}
                    </span>
                  </div>
                  <input
                    type="range"
                    min={0}
                    max={1500000}
                    step={50000}
                    value={fabricDiffPerMeterToman}
                    onChange={(e) => setFabricDiffPerMeterToman(Number(e.target.value))}
                    className="w-full accent-amber-600 cursor-pointer"
                  />
                </div>

                <div>
                  <div className="flex justify-between font-bold text-[#4A2E1B] mb-1">
                    <span>🎁 تخفیف پکیج جهیزیه عروس:</span>
                    <span className="font-mono-tabular text-emerald-700">
                      {bridalDowryDiscountPct}٪
                    </span>
                  </div>
                  <input
                    type="range"
                    min={0}
                    max={25}
                    step={2}
                    value={bridalDowryDiscountPct}
                    onChange={(e) => setBridalDowryDiscountPct(Number(e.target.value))}
                    className="w-full accent-emerald-600 cursor-pointer"
                  />
                </div>
              </div>

              <div className="grid grid-cols-1 sm:grid-cols-3 gap-2 text-xs">
                <button
                  type="button"
                  onClick={() => setIncludeDiningTable8(!includeDiningTable8)}
                  className={`p-2.5 rounded-xl font-bold border cursor-pointer ${
                    includeDiningTable8
                      ? 'bg-emerald-50 border-emerald-500 text-emerald-950'
                      : 'bg-white border-[#E6DEC8] text-[#6F4E37]'
                  }`}
                >
                  {includeDiningTable8
                    ? '✓ میز ناهارخوری ۶/۸ نفره ست (+۴۲ م)'
                    : '+ افزودن میز ناهارخوری ست'}
                </button>
                <button
                  type="button"
                  onClick={() => setIncludeBridalBedroomSet(!includeBridalBedroomSet)}
                  className={`p-2.5 rounded-xl font-bold border cursor-pointer ${
                    includeBridalBedroomSet
                      ? 'bg-emerald-50 border-emerald-500 text-emerald-950'
                      : 'bg-white border-[#E6DEC8] text-[#6F4E37]'
                  }`}
                >
                  {includeBridalBedroomSet
                    ? '✓ سرویس خواب عروس و تشک رویال (+۴۸ م)'
                    : '+ افزودن سرویس خواب عروس'}
                </button>
                <button
                  type="button"
                  onClick={() => setIncludeConsoleAndTvStand(!includeConsoleAndTvStand)}
                  className={`p-2.5 rounded-xl font-bold border cursor-pointer ${
                    includeConsoleAndTvStand
                      ? 'bg-emerald-50 border-emerald-500 text-emerald-950'
                      : 'bg-white border-[#E6DEC8] text-[#6F4E37]'
                  }`}
                >
                  {includeConsoleAndTvStand
                    ? '✓ آینه کنسول، جاکفشی و میز TV (+۲۴ م)'
                    : '+ افزودن آینه کنسول و میز TV'}
                </button>
              </div>
            </div>
          )}

          {/* Shared Sayadi Check & Down Payment Controls */}
          <div className="p-4 rounded-2xl bg-purple-50/70 border border-purple-300 space-y-3">
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
              <div>
                <div className="flex justify-between text-xs font-bold text-purple-950 mb-1">
                  <span>درصد پیش‌پرداخت نقدی:</span>
                  <span className="font-mono-tabular">{downPaymentPct}٪</span>
                </div>
                <input
                  type="range"
                  min={15}
                  max={70}
                  step={5}
                  value={downPaymentPct}
                  onChange={(e) => setDownPaymentPct(Number(e.target.value))}
                  className="w-full accent-purple-800 cursor-pointer"
                />
              </div>

              <div>
                <div className="flex justify-between text-xs font-bold text-purple-950 mb-1">
                  <span>تعداد چک‌های صیادی بنفش (۳ تا ۱۲ ماهه):</span>
                  <span className="font-mono-tabular">{checkCount} فقره چک</span>
                </div>
                <input
                  type="range"
                  min={3}
                  max={12}
                  step={1}
                  value={checkCount}
                  onChange={(e) => setCheckCount(Number(e.target.value))}
                  className="w-full accent-purple-800 cursor-pointer"
                />
              </div>
            </div>

            {/* Cost Split Slider */}
            <div className="pt-2 border-t border-purple-200">
              <div className="flex justify-between text-xs font-bold text-purple-950 mb-1">
                <span>⚖️ اسلایدر تسهیم هزینه بین طرفین / دو شریک / دو خانواده:</span>
                <span className="font-mono-tabular">
                  {partnerSplitPct}٪ به {100 - partnerSplitPct}٪
                </span>
              </div>
              <input
                type="range"
                min={10}
                max={90}
                step={5}
                value={partnerSplitPct}
                onChange={(e) => setPartnerSplitPct(Number(e.target.value))}
                className="w-full accent-[#4A2E1B] cursor-pointer"
              />
            </div>
          </div>
        </div>

        {/* Right Column: Live Gold-Foil Official Invoice + 1-Click WhatsApp, SMS, PNG & Print */}
        <div className="lg:col-span-5 rounded-3xl bg-gradient-to-b from-[#2A1A10] via-[#382213] to-[#1E1109] text-white border-2 border-[#D4AF37] p-6 space-y-4 shadow-xl">
          <div className="flex items-center justify-between border-b border-[#D4AF37]/30 pb-3">
            <div>
              <div className="text-[11px] text-[#F6E27A] font-bold">
                پیش‌فاکتور رسمی طلاکوب (محاسبه در ۰.۱ ثانیه)
              </div>
              <h3 className="text-lg font-extrabold text-white">{tenantConfig.brandName}</h3>
            </div>
            <span className="px-2.5 py-1 rounded-lg bg-[#D4AF37] text-[#24140B] text-xs font-extrabold font-mono-tabular">
              VIP QUOTE
            </span>
          </div>

          <div className="space-y-2 text-xs">
            <div className="p-3 rounded-xl bg-white/10 border border-[#D4AF37]/40 space-y-1">
              <div className="font-extrabold text-[#F6E27A]">{itemTitle}</div>
              <div className="text-white/85 text-[11px]">{itemSubtitle}</div>
              <div className="text-emerald-300 text-[11px] font-bold pt-1">{extraLine1}</div>
              <div className="text-amber-200 text-[11px]">{extraLine2}</div>
            </div>

            <div className="p-4 rounded-2xl bg-[#D4AF37]/20 border-2 border-[#D4AF37] flex items-center justify-between">
              <span className="font-bold text-sm text-[#F6E27A]">مبلغ کل قطعی قرارداد:</span>
              <strong className="text-xl sm:text-2xl font-extrabold font-mono-tabular text-white">
                {formatPrice(totalCashToman, currency, lang)}
              </strong>
            </div>

            <div className="grid grid-cols-2 gap-2.5">
              <div className="p-3 rounded-xl bg-white/10 border border-white/15">
                <div className="text-[11px] text-white/75">پیش‌پرداخت نقد ({downPaymentPct}٪):</div>
                <div className="text-sm font-extrabold font-mono-tabular text-emerald-300 mt-0.5">
                  {formatPrice(downCashToman, currency, lang)}
                </div>
              </div>
              <div className="p-3 rounded-xl bg-purple-900/50 border border-purple-400">
                <div className="text-[11px] text-purple-200">
                  هر چک صیادی ({checkCount} فقره):
                </div>
                <div className="text-sm font-extrabold font-mono-tabular text-white mt-0.5">
                  {formatPrice(eachCheckToman, currency, lang)}
                </div>
              </div>
            </div>

            {/* Multi-Channel Output Buttons: WhatsApp + Direct SMS (No Internet/VPN Needed!) + Gold PNG Image + Print */}
            <div className="pt-2 space-y-2">
              <button
                type="button"
                onClick={handleSendWhatsApp}
                className="w-full py-3.5 px-4 rounded-xl bg-emerald-600 hover:bg-emerald-500 text-white font-extrabold text-xs sm:text-sm flex items-center justify-center gap-2 cursor-pointer shadow-lg"
              >
                <MessageCircle className="w-4 h-4" />
                <span>ارسال ۱-کلیکی پیش‌فاکتور طلاکوب به واتساپ مشتری و مدیر</span>
              </button>

              <div className="grid grid-cols-1 sm:grid-cols-3 gap-2">
                <button
                  type="button"
                  onClick={handleSendSMS}
                  className="py-2.5 px-3 rounded-xl bg-amber-500 hover:bg-amber-400 text-[#1A0F08] font-extrabold text-xs flex items-center justify-center gap-1.5 cursor-pointer"
                >
                  <MessageSquare className="w-4 h-4 shrink-0" />
                  <span>📩 ارسال با پیامک (بدون فیلتر)</span>
                </button>

                <button
                  type="button"
                  onClick={handleDownloadPng}
                  className="py-2.5 px-3 rounded-xl bg-[#D4AF37] hover:bg-[#F6E27A] text-[#1A0F08] font-extrabold text-xs flex items-center justify-center gap-1.5 cursor-pointer"
                >
                  <Download className="w-4 h-4 shrink-0" />
                  <span>🖼️ دانلود عکس فاکتور طلاکوب</span>
                </button>

                <button
                  type="button"
                  onClick={() => window.print()}
                  className="py-2.5 px-3 rounded-xl bg-white/15 hover:bg-white/25 text-white font-bold text-xs flex items-center justify-center gap-1.5 cursor-pointer"
                >
                  <Printer className="w-4 h-4 shrink-0" />
                  <span>🖨️ چاپ / PDF رسمی</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* SPECIAL DEDICATED MODULE FOR WOMEN'S BEAUTY SALON:
          1) Shared Showcase & Permanent Salon Website Builder (Exterior, Chairs, Equipment, Prices, Special Day Discounts, Address & Phone)
          2) Full Beauty Salon Line & Stylist Commission Accounting Software */}
      {activeGuild === 'beauty_salon' && (
        <div className="space-y-8">
          {/* Part 1: Beauty Salon Website Builder & Live Visual Showcase */}
          <div className="hc-card bg-white border-2 border-[#D4AF37] rounded-3xl p-6 sm:p-8 space-y-6 shadow-md">
            <div className="flex flex-col lg:flex-row lg:items-center justify-between gap-4 border-b border-[#E6DEC8] pb-4">
              <div>
                <span className="px-3 py-1 rounded-xl bg-[#4A153B] text-[#F6E27A] text-xs font-extrabold">
                  🌐 صفحه مشترک و سایت‌ساز اختصاصی آرایشگاه‌های زنانه (تبدیل آنی به سایت دائمی سالن پس از خرید)
                </span>
                <h3 className="text-xl sm:text-2xl font-extrabold text-[#4A2E1B] mt-2">
                  ویترین اختصاصی «{tenantConfig.brandName}» — نمای بیرون، صندلی‌ها، تجهیزات، قیمت خدمات و تخفیف روزهای خاص
                </h3>
                <p className="text-xs text-[#6F4E37] mt-1">
                  📍 آدرس و لوکیشن سالن: {tenantConfig.city} | 📞 تلفن رزرو نوبت: {tenantConfig.phone} | 📸 اینستاگرام: {tenantConfig.instagram}
                </p>
              </div>
              <div className="px-4 py-3 rounded-2xl bg-emerald-50 border-2 border-emerald-500 text-emerald-950 text-xs font-extrabold">
                🎁 جشنواره روزهای خاص فعال: «سه‌شنبه‌ها و چهارشنبه‌های طلایی با {specialDayDiscountPct}٪ تخفیف ویژه رزرو آنلاین»
              </div>
            </div>

            {/* 4-Card Salon Visual Tour: Exterior, VIP Chairs, Equipment/Sanitation, Bridal & Service Menu */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-5">
              {[
                {
                  badge: '🏛️ نمای بیرون و ورودی VIP سالن',
                  title: 'تابلو، سردر مجلل و پارکینگ اختصاصی مشتریان',
                  desc: `نمای بیرونی و ورودی اختصاصی ${tenantConfig.brandName} در ${tenantConfig.city} با دسترسی آسان و محیط کاملاً امن و آرام.`,
                  priceTag: 'بازدید حضوری همه روزه ۱۰ الی ۲۰',
                  img: 'https://images.unsplash.com/photo-1560066984-138dadb4c035?auto=format&fit=crop&w=900&q=80',
                },
                {
                  badge: '💺 صندلی‌های VIP و محیط داخلی سالن',
                  title: 'صندلی‌های برقی ارگونومیک رنگ، لایت و میکاپ عروس',
                  desc: 'طراحی داخلی نئوکلاسیک طلاکوب با صندلی‌های VIP، نورپردازی استاندارد رینگ‌لایت و تهویه مطبوع اختصاصی هر لاین.',
                  priceTag: `رزرو صندلی VIP با ${specialDayDiscountPct}٪ تخفیف روز خاص`,
                  img: 'https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?auto=format&fit=crop&w=900&q=80',
                },
                {
                  badge: '🔬 تجهیزات مدرن و استریل اتوکلاو',
                  title: 'دستگاه‌های فیشیال، مایکرومیست کراتین و پک استریل',
                  desc: 'مجهز به جدیدترین دستگاه‌های اوزون‌تراپی مو، اتوکلاو بیمارستانی استریل ابزار ناخن و برندهای اورجینال اولاپلکس و لورآل.',
                  priceTag: 'تضمین ۱۰۰٪ اصالت متریال مصرفی',
                  img: 'https://images.unsplash.com/photo-1516975080664-ed2fc6a32937?auto=format&fit=crop&w=900&q=80',
                },
                {
                  badge: '👰 منوی قیمت خدمات و پکیج عروس',
                  title: 'تعرفه شفاف خدمات + تخفیف روزهای خاص و جشنواره‌ها',
                  desc: 'میکاپ و شینیون عروس VIP، آمبره و سامبره، پروتئین و کراتین ابریشمی، کاشت ناخن ژل و طراحی مژه با قفل قیمت.',
                  priceTag: `شروع پکیج‌ها با ${specialDayDiscountPct}٪ هدیه روز خاص`,
                  img: 'https://images.unsplash.com/photo-1487412947147-5cebf100ffc2?auto=format&fit=crop&w=900&q=80',
                },
              ].map((card, i) => (
                <div
                  key={i}
                  className="rounded-2xl border border-[#E6DEC8] bg-[#FAF7F2] overflow-hidden flex flex-col justify-between shadow-xs"
                >
                  <div>
                    <div className="h-44 w-full bg-[#4A153B] relative overflow-hidden">
                      <img
                        src={card.img}
                        alt={card.title}
                        referrerPolicy="no-referrer"
                        loading="lazy"
                        className="w-full h-full object-cover"
                      />
                      <span className="absolute top-2.5 right-2.5 px-2.5 py-1 rounded-lg bg-[#4A153B]/90 text-[#F6E27A] text-[10px] font-extrabold border border-[#D4AF37]">
                        {card.badge}
                      </span>
                    </div>
                    <div className="p-4 space-y-2">
                      <h4 className="text-xs sm:text-sm font-extrabold text-[#4A2E1B]">
                        {card.title}
                      </h4>
                      <p className="text-[11px] text-[#6F4E37] leading-relaxed">{card.desc}</p>
                    </div>
                  </div>
                  <div className="px-4 pb-4">
                    <div className="p-2 rounded-xl bg-white border border-[#D4AF37] text-center text-[11px] font-extrabold text-[#4A153B]">
                      {card.priceTag}
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>

          {/* Part 2: Beauty Salon Line & Stylist Commission Accounting Software */}
          <div className="hc-card bg-white border-2 border-[#4A153B] rounded-3xl p-6 sm:p-8 space-y-5 shadow-md">
            <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-3 border-b border-[#E6DEC8] pb-4">
              <div>
                <span className="px-3 py-1 rounded-xl bg-emerald-700 text-white text-xs font-extrabold">
                  📊 نرم‌افزار حسابداری تخصصی خدمات و لاین‌های آرایشگاهی (تقدیمی به خریدار)
                </span>
                <h3 className="text-lg sm:text-2xl font-extrabold text-[#4A2E1B] mt-2">
                  دفتر کل تسویه روزانه پرسنل، کسر خودکار هزینه مواد مصرفی و سود خالص سالن
                </h3>
                <p className="text-xs text-[#6F4E37]">
                  فرمول ضدضرر سالن‌دار: ابتدا هزینه مواد (پودر دکلره، رنگ، کراتین، ژل) از مبلغ دریافتی کسر شده و سپس درصد توافقی بین آرایشگر و مدیریت سالن تقسیم می‌شود.
                </p>
              </div>
              <div className="grid grid-cols-2 gap-2 text-xs">
                <div className="p-3 rounded-xl bg-purple-50 border border-purple-300">
                  <div className="text-[10px] text-purple-900">جمع سهم پرسنل (درصدگیر):</div>
                  <strong className="font-mono-tabular text-sm text-purple-950">
                    {formatPrice(
                      salonLedger.reduce((acc, r) => acc + r.stylistShareToman, 0),
                      currency,
                      lang
                    )}
                  </strong>
                </div>
                <div className="p-3 rounded-xl bg-emerald-50 border border-emerald-400">
                  <div className="text-[10px] text-emerald-900">سود خالص صندوق سالن:</div>
                  <strong className="font-mono-tabular text-sm text-emerald-800">
                    {formatPrice(
                      salonLedger.reduce((acc, r) => acc + r.salonNetToman, 0),
                      currency,
                      lang
                    )}
                  </strong>
                </div>
              </div>
            </div>

            <div className="overflow-x-auto">
              <table className="w-full text-right text-xs border-collapse">
                <thead>
                  <tr className="bg-[#FAF7F2] border-b-2 border-[#D4AF37] text-[#4A2E1B]">
                    <th className="p-3">تاریخ</th>
                    <th className="p-3">لاین / خدمت انجام‌شده</th>
                    <th className="p-3">نام آرایشگر / متخصص</th>
                    <th className="p-3">دریافتی از مشتری</th>
                    <th className="p-3">کسر هزینه مواد</th>
                    <th className="p-3">سهم خالص آرایشگر</th>
                    <th className="p-3">سود خالص سالن</th>
                  </tr>
                </thead>
                <tbody>
                  {salonLedger.map((row) => (
                    <tr key={row.id} className="border-b border-[#E6DEC8] hover:bg-[#FAF7F2]/60">
                      <td className="p-3 font-mono-tabular">{row.dateFa}</td>
                      <td className="p-3 font-bold text-[#4A2E1B]">{row.line}</td>
                      <td className="p-3">{row.stylist}</td>
                      <td className="p-3 font-mono-tabular font-bold">
                        {formatPrice(row.grossToman, currency, lang)}
                      </td>
                      <td className="p-3 font-mono-tabular text-red-700">
                        -{formatPrice(row.materialToman, currency, lang)}
                      </td>
                      <td className="p-3 font-mono-tabular font-bold text-purple-800">
                        {formatPrice(row.stylistShareToman, currency, lang)}
                      </td>
                      <td className="p-3 font-mono-tabular font-extrabold text-emerald-700">
                        {formatPrice(row.salonNetToman, currency, lang)}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      )}

      {/* DEDICATED FURNITURE & BRIDAL DOWRY WEBSITE SHOWCASE (FURNIMATE / مبلیار) */}
      {activeGuild === 'furniture_bridal' && (
        <div className="hc-card bg-white border-2 border-[#D4AF37] rounded-3xl p-6 sm:p-8 space-y-6 shadow-md">
          <div className="flex flex-col lg:flex-row lg:items-center justify-between gap-4 border-b border-[#E6DEC8] pb-4">
            <div>
              <span className="px-3 py-1 rounded-xl bg-[#3E2723] text-[#F6E27A] text-xs font-extrabold">
                🪑 کاتالوگ آنلاین و سایت‌ساز دائمی گالری مبلمان و جهیزیه عروس (مبلیار — FurniMate)
              </span>
              <h3 className="text-xl sm:text-2xl font-extrabold text-[#4A2E1B] mt-2">
                ویترین اختصاصی «{tenantConfig.brandName}» — مبلمان نئوکلاسیک، سرویس خواب عروس، ناهارخوری و کالیته پارچه ترک
              </h3>
            </div>
            <div className="px-4 py-3 rounded-2xl bg-emerald-50 border-2 border-emerald-500 text-emerald-950 text-xs font-extrabold">
              🛡️ ضمانت‌نامه کتبی ۵ ساله کلاف چوب راش گرجستان و فوم سرد یورتان ویژه زوج‌های جوان ({bridalDowryDiscountPct}٪ هدیه جهیزیه)
            </div>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-5">
            {[
              {
                badge: '🛋️ ست مبلمان نئوکلاسیک و چستر',
                title: 'مبل ۸ نفره نئوکلاسیک فرانسوی و چستر ایتالیایی',
                desc: 'کلاف ۱۰۰٪ چوب راش گرجستان خشک‌کن‌رفته، فوم سرد ۱۳ سانتی شرکتی و دوخت صنعتی درجه یک.',
                priceTag: '۵ سال ضمانت بی‌قیدوشرط کلاف و فوم',
                img: 'https://images.unsplash.com/photo-1555041469-a586c61ea9bc?auto=format&fit=crop&w=900&q=80',
              },
              {
                badge: '🍽️ میز ناهارخوری و آینه کنسول ست',
                title: 'ست غذاخوری ۶ و ۸ نفره صفحه چوب طبیعی و سنگ اسلب',
                desc: 'رنگ پلی‌اورتان ضدخش، صندلی‌های ارگونومیک لمسه‌دوزی‌شده همراه با آینه کنسول و میز تلویزیون ست.',
                priceTag: 'تولید در رنگ چوب و پارچه دلخواه مشتری',
                img: 'https://images.unsplash.com/photo-1617806118233-18e1de247200?auto=format&fit=crop&w=900&q=80',
              },
              {
                badge: '🛏️ سرویس خواب کامل عروس و تشک رویال',
                title: 'تخت دو نفره کاپیتوناژ، میز آرایش، پاتختی و تشک طبی فنرپاکتی',
                desc: 'طراحی مجلل ویژه جهیزیه عروس با جک باکس‌دار، نورپردازی مخفی و تشک طبی-فنری ۱۰ سال ضمانت.',
                priceTag: `تخفیف ویژه جهیزیه عروس: ${bridalDowryDiscountPct}٪`,
                img: 'https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=900&q=80',
              },
              {
                badge: '🧵 کالیته پارچه‌های نانو و شانل ترک',
                title: 'بیش از ۱۲۰ رنگ پارچه ضدلک نانو، بوکله، مازراتی و شانل یزد و ترک',
                desc: 'محاسبه شفاف متراژ پارچه مصرفی در پیش‌فاکتور طلاکوب بدون یک ریال هزینه پنهان در زمان تحویل.',
                priceTag: 'قفل قیمت چوب و پارچه از لحظه بیعانه',
                img: 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?auto=format&fit=crop&w=900&q=80',
              },
            ].map((card, i) => (
              <div
                key={i}
                className="rounded-2xl border border-[#E6DEC8] bg-[#FAF7F2] overflow-hidden flex flex-col justify-between shadow-xs"
              >
                <div>
                  <div className="h-44 w-full bg-[#3E2723] relative overflow-hidden">
                    <img
                      src={card.img}
                      alt={card.title}
                      referrerPolicy="no-referrer"
                      loading="lazy"
                      className="w-full h-full object-cover"
                    />
                    <span className="absolute top-2.5 right-2.5 px-2.5 py-1 rounded-lg bg-[#3E2723]/90 text-[#F6E27A] text-[10px] font-extrabold border border-[#D4AF37]">
                      {card.badge}
                    </span>
                  </div>
                  <div className="p-4 space-y-2">
                    <h4 className="text-xs sm:text-sm font-extrabold text-[#4A2E1B]">
                      {card.title}
                    </h4>
                    <p className="text-[11px] text-[#6F4E37] leading-relaxed">{card.desc}</p>
                  </div>
                </div>
                <div className="px-4 pb-4">
                  <div className="p-2 rounded-xl bg-white border border-[#D4AF37] text-center text-[11px] font-extrabold text-[#3E2723]">
                    {card.priceTag}
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* IRRESISTIBLE UPSELL OFFER FOR ALL GUILDS: Turn This Page into Buyer's Permanent Dedicated Website + License */}
      <div className="hc-card rounded-3xl bg-gradient-to-l from-[#1A0E07] via-[#2D190E] to-[#1A0E07] text-white border-2 border-[#D4AF37] p-6 sm:p-8 space-y-4 shadow-xl">
        <div className="flex flex-wrap items-center justify-between gap-2">
          <span className="px-3 py-1 rounded-xl bg-emerald-600 text-white text-xs font-extrabold">
            🚀 پیشنهاد ویژه تحویل سایت اختصاصی دائمی + نرم‌افزار محاسبه و حسابداری
          </span>
          <span className="text-xs font-mono-tabular text-[#F6E27A]">
            بدون هزینه طراحی سایت ۵۰ میلیونی — تحویل فوری در همان جلسه!
          </span>
        </div>
        <h3 className="text-lg sm:text-2xl font-extrabold text-[#F6E27A]">
          علاوه بر لایسنس برنامه، همین صفحه با برند، عکس‌ها، قیمت‌ها و لوکیشن «{tenantConfig.brandName}» به سایت دائمی مجموعه شما تبدیل می‌شود!
        </h3>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 text-xs">
          <div className="p-4 rounded-2xl bg-white/10 border border-[#D4AF37]/40 space-y-1.5">
            <div className="font-extrabold text-emerald-300">
              ۱. سطح پایه (هدیه رایگان روی لایسنس):
            </div>
            <p className="text-white/85 leading-relaxed">
              تبدیل آنی همین صفحه به سایت اختصاصی دائمی شما روی آدرس ابری اختصاصی همراه با QR Code طلاکوب رومیزی و لینک بیو اینستاگرام (تحویل ۱ دقیقه‌ای).
            </p>
          </div>
          <div className="p-4 rounded-2xl bg-[#D4AF37]/15 border-2 border-[#D4AF37] space-y-1.5">
            <div className="font-extrabold text-[#F6E27A]">
              ۲. سطح پرچمدار (اتصال دامنه .com / .ir اختصاصی):
            </div>
            <p className="text-white/90 leading-relaxed">
              اتصال مستقیم به دامنه رسمی برند شما + گواهی امنیتی SSL + سئوی محلی گوگل تا مشتریان شهر شما با جستجو در گوگل مستقیماً وارد سایت و ماشین‌حساب شما شوند.
            </p>
          </div>
          <div className="p-4 rounded-2xl bg-white/10 border border-[#D4AF37]/40 space-y-1.5">
            <div className="font-extrabold text-purple-300">
              ۳. سطح VIP Enterprise (گوگل‌مپ + اپلیکیشن + حسابداری):
            </div>
            <p className="text-white/85 leading-relaxed">
              ثبت پین رسمی مجموعه در نقشه گوگل (Google Maps) + اپلیکیشن اندروید APK اختصاصی با نام برند شما + نرم‌افزار حسابداری و دفتر چک صیادی.
            </p>
          </div>
        </div>
      </div>
    </section>
  );
};
