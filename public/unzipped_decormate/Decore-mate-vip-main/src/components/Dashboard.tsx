import React from 'react';
import {
  AreaChart,
  Area,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  PieChart,
  Pie,
  Cell,
  BarChart,
  Bar,
  Legend,
} from 'recharts';
import { TrendingUp, PieChart as PieIcon, BarChart3, Award, Layers } from 'lucide-react';
import { CurrencyCode, LanguageCode, convertFromToman, formatPrice, CURRENCIES } from '../data/decorData';

interface DashboardProps {
  currency: CurrencyCode;
  lang: LanguageCode;
  adhdMode?: boolean;
}

const MONTHLY_DEMAND_RAW = [
  { monthFa: 'فروردین', monthEn: 'Apr', neoclassic: 42, enzo: 28, highgloss: 55, renovation: 30 },
  { monthFa: 'اردیبهشت', monthEn: 'May', neoclassic: 56, enzo: 34, highgloss: 61, renovation: 38 },
  { monthFa: 'خرداد', monthEn: 'Jun', neoclassic: 68, enzo: 41, highgloss: 64, renovation: 45 },
  { monthFa: 'تیر', monthEn: 'Jul', neoclassic: 82, enzo: 52, highgloss: 70, renovation: 59 },
  { monthFa: 'مرداد', monthEn: 'Aug', neoclassic: 91, enzo: 60, highgloss: 74, renovation: 67 },
  { monthFa: 'شهریور', monthEn: 'Sep', neoclassic: 115, enzo: 78, highgloss: 85, renovation: 88 },
];

const POPULAR_MATERIALS_DATA = [
  { nameFa: 'نئوکلاسیک سفید-طلایی و گردو', nameEn: 'Neoclassical Walnut/Gold', value: 36, color: '#4A2E1B' },
  { nameFa: 'چوب طبیعی گردو و بلوط گرم', nameEn: 'Solid Walnut & Warm Oak', value: 24, color: '#D4AF37' },
  { nameFa: 'هایگلاس ترک AGT سوپرمات', nameEn: 'Turkish AGT High-Gloss', value: 20, color: '#8C6239' },
  { nameFa: 'پلی‌اورتان انزو ایتالیایی', nameEn: 'Italian Enzo Polyurethane', value: 12, color: '#059669' },
  { nameFa: 'ممبران کلاسیک سلطنتی', nameEn: 'Classic Royal Membrane', value: 8, color: '#A68A64' },
];

const MARGIN_COMPARISON_RAW = [
  {
    itemFa: 'هایگلاس AGT',
    itemEn: 'AGT High-Gloss',
    workshopCostToman: 6200000,
    showroomPriceToman: 9800000,
    colleagueProfitToman: 3600000,
  },
  {
    itemFa: 'نئوکلاسیک',
    itemEn: 'Neoclassical',
    workshopCostToman: 9100000,
    showroomPriceToman: 14500000,
    colleagueProfitToman: 5400000,
  },
  {
    itemFa: 'ممبران کره‌ای',
    itemEn: 'Membrane',
    workshopCostToman: 7400000,
    showroomPriceToman: 11900000,
    colleagueProfitToman: 4500000,
  },
  {
    itemFa: 'انزو ۲۵ میل',
    itemEn: 'Enzo 25mm',
    workshopCostToman: 11200000,
    showroomPriceToman: 17800000,
    colleagueProfitToman: 6600000,
  },
  {
    itemFa: 'چوب گردو VIP',
    itemEn: 'Solid Walnut',
    workshopCostToman: 14600000,
    showroomPriceToman: 23500000,
    colleagueProfitToman: 8900000,
  },
];

export const Dashboard: React.FC<DashboardProps> = ({ currency, lang, adhdMode = false }) => {
  const isRtl = lang === 'fa' || lang === 'ar';
  const currencySymbol = CURRENCIES[currency].symbol;

  const demandData = MONTHLY_DEMAND_RAW.map((row) => ({
    name: isRtl ? row.monthFa : row.monthEn,
    neoclassic: row.neoclassic,
    enzo: row.enzo,
    highgloss: row.highgloss,
    renovation: row.renovation,
  }));

  const marginData = MARGIN_COMPARISON_RAW.map((row) => ({
    name: isRtl ? row.itemFa : row.itemEn,
    workshopCost: Math.round(convertFromToman(row.workshopCostToman, currency)),
    showroomPrice: Math.round(convertFromToman(row.showroomPriceToman, currency)),
    profit: Math.round(convertFromToman(row.colleagueProfitToman, currency)),
    rawWorkshopToman: row.workshopCostToman,
    rawShowroomToman: row.showroomPriceToman,
    rawProfitToman: row.colleagueProfitToman,
  }));

  return (
    <section
      id="bi-dashboard"
      aria-label="Business Intelligence Decoration Market Dashboard"
      className="py-10 px-4 sm:px-8 max-w-[1440px] mx-auto"
    >
      {/* Header */}
      <div className="mb-8 flex flex-col md:flex-row md:items-end justify-between gap-4 border-b border-[#E6DEC8] pb-5">
        <div>
          <div className="text-xs font-semibold tracking-wide text-[#6F4E37] mb-1">
            {isRtl
              ? 'هوش تجاری بازار کابینت و دکوراسیون · تحلیل زنده ۵ ارزی'
              : 'Decoration Market Business Intelligence · Live 5-Currency Analytics'}
          </div>
          <h2 className="text-2xl sm:text-3xl font-bold text-[#4A2E1B] flex items-center gap-2.5">
            <BarChart3 className="w-7 h-7 text-[#D4AF37] shrink-0" />
            <span>
              {isRtl
                ? 'داشبورد هوش تجاری (BI) و تحلیل سود کارگاه و نمایشگاه'
                : 'Market BI Dashboard & Workshop vs. Showroom Margin Analytics'}
            </span>
          </h2>
        </div>
        <div className="flex flex-wrap items-center gap-4 text-xs text-[#6F4E37] font-mono-tabular">
          <span>
            {isRtl ? 'واحد پول فعال نمودارها:' : 'Active Chart Currency:'}{' '}
            <strong className="text-[#4A2E1B]">{CURRENCIES[currency].label}</strong>
          </span>
          <span aria-hidden="true">·</span>
          <span>
            {isRtl ? 'میانگین حاشیه سود خالص همکار: ۳۷.۸٪' : 'Avg Colleague Net Margin: 37.8%'}
          </span>
        </div>
      </div>

      {/* KPI Summary Strip */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 mb-8">
        <div className="hc-card bg-white border border-[#E6DEC8] rounded-xl p-5">
          <div className="text-xs text-[#6F4E37] mb-1">
            {isRtl ? 'پرتقاضاترین سبک سال ۱۴۰۵' : 'Top Requested Style 2026'}
          </div>
          <div className="text-lg font-bold text-[#4A2E1B]">
            {isRtl ? 'نئوکلاسیک چوب گردو و طلایی' : 'Walnut & Gold Neoclassical'}
          </div>
          <div className="mt-2 text-xs text-emerald-700 font-medium font-mono-tabular">
            +44.2% {isRtl ? 'رشد سفارش ماهانه' : 'Monthly Order Growth'}
          </div>
        </div>

        <div className="hc-card bg-white border border-[#E6DEC8] rounded-xl p-5">
          <div className="text-xs text-[#6F4E37] mb-1">
            {isRtl ? 'میانگین سود هر پروژه ۱۰ متری کابینت' : 'Avg Profit per 10m Kitchen Project'}
          </div>
          <div className="text-lg font-bold text-[#4A2E1B] font-mono-tabular">
            {formatPrice(54000000, currency, lang)}
          </div>
          <div className="mt-2 text-xs text-[#6F4E37]">
            {isRtl ? 'اختلاف قیمت تمام‌شده کارگاه و نمایشگاه' : 'Workshop Cost vs Showroom Retail'}
          </div>
        </div>

        <div className="hc-card bg-white border border-[#E6DEC8] rounded-xl p-5">
          <div className="text-xs text-[#6F4E37] mb-1">
            {isRtl ? 'نرخ تبدیل فروش با اقساط چک صیادی' : 'Sayadi Check Installment Conversion'}
          </div>
          <div className="text-lg font-bold text-emerald-700 font-mono-tabular">
            78.5% {isRtl ? 'موفقیت قرارداد' : 'Close Rate'}
          </div>
          <div className="mt-2 text-xs text-[#6F4E37]">
            {isRtl ? 'افزایش ۲.۴ برابری فروش نسبت به حالت نقد' : '2.4x higher close rate vs cash-only'}
          </div>
        </div>

        <div className="hc-card bg-white border border-[#E6DEC8] rounded-xl p-5">
          <div className="text-xs text-[#6F4E37] mb-1">
            {isRtl ? 'پورسانت خالص ویزیتور از فروش اپ' : 'Visitor App Sales Commission'}
          </div>
          <div className="text-lg font-bold text-[#4A2E1B] font-mono-tabular">
            25% {isRtl ? 'تسویه آنی + پاداش' : 'Instant Payout'}
          </div>
          <div className="mt-2 text-xs text-[#6F4E37]">
            {isRtl ? 'تا سقف ۷,۲۵۰,۰۰۰ تومان در هر فروش سطح ۵' : 'Up to 25% per White-Label / VIP sale'}
          </div>
        </div>
      </div>

      {/* 3 Core Recharts Visualizations */}
      <div className="grid grid-cols-1 lg:grid-cols-12 gap-6">
        {/* 1. AreaChart: Monthly Demand Trend */}
        <div className="hc-card lg:col-span-7 bg-white border border-[#E6DEC8] rounded-2xl p-6">
          <div className="flex items-center justify-between mb-4">
            <div>
              <h3 className="text-base sm:text-lg font-bold text-[#4A2E1B] flex items-center gap-2">
                <TrendingUp className="w-5 h-5 text-[#D4AF37]" />
                <span>
                  {isRtl
                    ? '۱. روند تقاضای ماهانه انواع کابینت و بازسازی ساختمان'
                    : '1. Monthly Market Demand Trend by Cabinet & Renovation Type'}
                </span>
              </h3>
              <p className="text-xs text-[#6F4E37] mt-0.5">
                {isRtl
                  ? 'بر حسب شاخص تعداد پروژه‌های ثبت‌شده در اکوسیستم دکورمِیت'
                  : 'Indexed by registered turnkey projects across the DecorMate ecosystem'}
              </p>
            </div>
          </div>

          <div className="h-[300px] w-full" dir="ltr">
            <ResponsiveContainer width="100%" height="100%">
              <AreaChart data={demandData} margin={{ top: 10, right: 20, left: 0, bottom: 0 }}>
                <defs>
                  <linearGradient id="colorNeo" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="5%" stopColor="#4A2E1B" stopOpacity={0.45} />
                    <stop offset="95%" stopColor="#4A2E1B" stopOpacity={0.02} />
                  </linearGradient>
                  <linearGradient id="colorEnzo" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="5%" stopColor="#D4AF37" stopOpacity={0.55} />
                    <stop offset="95%" stopColor="#D4AF37" stopOpacity={0.05} />
                  </linearGradient>
                  <linearGradient id="colorReno" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="5%" stopColor="#059669" stopOpacity={0.4} />
                    <stop offset="95%" stopColor="#059669" stopOpacity={0.02} />
                  </linearGradient>
                </defs>
                <CartesianGrid strokeDasharray="3 3" stroke="#EFE8D8" />
                <XAxis dataKey="name" stroke="#6F4E37" fontSize={12} />
                <YAxis stroke="#6F4E37" fontSize={12} />
                <Tooltip
                  contentStyle={{
                    backgroundColor: '#FFFFFF',
                    borderColor: '#D4AF37',
                    borderRadius: '12px',
                    color: '#2A1A10',
                    fontSize: '12px',
                  }}
                />
                <Legend wrapperStyle={{ fontSize: '12px' }} />
                <Area
                  type="monotone"
                  dataKey="neoclassic"
                  name={isRtl ? 'نئوکلاسیک و چوب گردو' : 'Neoclassical & Walnut'}
                  stroke="#4A2E1B"
                  strokeWidth={2.5}
                  fillOpacity={1}
                  fill="url(#colorNeo)"
                  isAnimationActive={!adhdMode}
                />
                <Area
                  type="monotone"
                  dataKey="enzo"
                  name={isRtl ? 'پلی‌اورتان انزو' : 'Enzo Polyurethane'}
                  stroke="#D4AF37"
                  strokeWidth={2.5}
                  fillOpacity={1}
                  fill="url(#colorEnzo)"
                  isAnimationActive={!adhdMode}
                />
                <Area
                  type="monotone"
                  dataKey="renovation"
                  name={isRtl ? 'بازسازی کامل VIP' : 'Full VIP Renovation'}
                  stroke="#059669"
                  strokeWidth={2}
                  fillOpacity={1}
                  fill="url(#colorReno)"
                  isAnimationActive={!adhdMode}
                />
              </AreaChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* 2. PieChart: Most Popular Materials & Colors of the Year */}
        <div className="hc-card lg:col-span-5 bg-white border border-[#E6DEC8] rounded-2xl p-6">
          <h3 className="text-base sm:text-lg font-bold text-[#4A2E1B] flex items-center gap-2 mb-1">
            <PieIcon className="w-5 h-5 text-[#D4AF37]" />
            <span>
              {isRtl
                ? '۲. سهم بازار محبوب‌ترین متریال‌ها و رنگ‌های سال'
                : '2. Market Share of Top Materials & Finishes'}
            </span>
          </h3>
          <p className="text-xs text-[#6F4E37] mb-4">
            {isRtl
              ? 'چوب طبیعی گردو، نئوکلاسیک سفید-طلایی، هایگلاس AGT و انزو'
              : 'Walnut Wood, White-Gold Neoclassical, AGT High-Gloss & Enzo'}
          </p>

          <div className="h-[220px] w-full" dir="ltr">
            <ResponsiveContainer width="100%" height="100%">
              <PieChart>
                <Pie
                  data={POPULAR_MATERIALS_DATA}
                  cx="50%"
                  cy="50%"
                  innerRadius={52}
                  outerRadius={82}
                  paddingAngle={3}
                  dataKey="value"
                  nameKey={isRtl ? 'nameFa' : 'nameEn'}
                  isAnimationActive={!adhdMode}
                >
                  {POPULAR_MATERIALS_DATA.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={entry.color} />
                  ))}
                </Pie>
                <Tooltip
                  formatter={(val: any) => [`${val}%`, isRtl ? 'سهم بازار' : 'Market Share']}
                  contentStyle={{
                    backgroundColor: '#FFFFFF',
                    borderColor: '#D4AF37',
                    borderRadius: '10px',
                    fontSize: '12px',
                  }}
                />
              </PieChart>
            </ResponsiveContainer>
          </div>

          <div className="space-y-2 mt-2">
            {POPULAR_MATERIALS_DATA.map((item) => (
              <div key={item.nameEn} className="flex items-center justify-between text-xs">
                <div className="flex items-center gap-2">
                  <span
                    className="w-3 h-3 rounded-sm shrink-0"
                    style={{ backgroundColor: item.color }}
                  />
                  <span className="text-[#2A1A10] font-medium">
                    {isRtl ? item.nameFa : item.nameEn}
                  </span>
                </div>
                <span className="font-mono-tabular font-bold text-[#4A2E1B]">{item.value}%</span>
              </div>
            ))}
          </div>
        </div>

        {/* 3. BarChart: Workshop Cost vs Showroom Selling Price & Colleague Profit Margin */}
        <div className="hc-card lg:col-span-12 bg-white border border-[#E6DEC8] rounded-2xl p-6">
          <div className="flex flex-col sm:flex-row sm:items-center justify-between gap-2 mb-5">
            <div>
              <h3 className="text-base sm:text-lg font-bold text-[#4A2E1B] flex items-center gap-2">
                <Layers className="w-5 h-5 text-[#D4AF37]" />
                <span>
                  {isRtl
                    ? `۳. مقایسه قیمت تمام‌شده کارگاه با قیمت فروش نمایشگاه و حاشیه سود همکار (${currencySymbol})`
                    : `3. Workshop Production Cost vs. Showroom Retail Price & Colleague Profit (${currencySymbol})`}
                </span>
              </h3>
              <p className="text-xs text-[#6F4E37] mt-0.5">
                {isRtl
                  ? 'قیمت‌ها بر اساس ۱ متر طول استاندارد اتحادیه به صورت آنی به ارز انتخابی شما تبدیل شده‌اند'
                  : 'Prices per 1 standard union linear meter dynamically converted to your selected currency'}
              </p>
            </div>
            <div className="text-xs font-mono-tabular text-emerald-700 font-semibold">
              {isRtl ? 'بروزرسانی آنی نرخ ارز: ۰.۰۱ ثانیه' : 'Real-Time Currency Sync: 0.01s'}
            </div>
          </div>

          <div className="h-[320px] w-full" dir="ltr">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={marginData} margin={{ top: 10, right: 25, left: 15, bottom: 5 }}>
                <CartesianGrid strokeDasharray="3 3" stroke="#EFE8D8" />
                <XAxis dataKey="name" stroke="#4A2E1B" fontSize={12} />
                <YAxis stroke="#6F4E37" fontSize={11} />
                <Tooltip
                  formatter={(value: any) => [
                    `${new Intl.NumberFormat().format(Number(value))} ${currencySymbol}`,
                    '',
                  ]}
                  contentStyle={{
                    backgroundColor: '#FFFFFF',
                    borderColor: '#D4AF37',
                    borderRadius: '12px',
                    fontSize: '12px',
                  }}
                />
                <Legend wrapperStyle={{ fontSize: '12px' }} />
                <Bar
                  dataKey="workshopCost"
                  name={isRtl ? 'قیمت تمام‌شده کارگاه' : 'Workshop Production Cost'}
                  fill="#6F4E37"
                  radius={[6, 6, 0, 0]}
                  isAnimationActive={!adhdMode}
                />
                <Bar
                  dataKey="showroomPrice"
                  name={isRtl ? 'قیمت فروش نمایشگاه (اتحادیه)' : 'Showroom Retail Price'}
                  fill="#4A2E1B"
                  radius={[6, 6, 0, 0]}
                  isAnimationActive={!adhdMode}
                />
                <Bar
                  dataKey="profit"
                  name={isRtl ? 'حاشیه سود خالص همکار' : 'Colleague Net Profit Margin'}
                  fill="#059669"
                  radius={[6, 6, 0, 0]}
                  isAnimationActive={!adhdMode}
                />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>
      </div>
    </section>
  );
};
