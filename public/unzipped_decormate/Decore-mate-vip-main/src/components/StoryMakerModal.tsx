import React, { useEffect, useRef, useState } from 'react';
import { X, Download, Sparkles, Phone, Store, Instagram, CheckCircle2 } from 'lucide-react';
import { CurrencyCode, LanguageCode, ShowcaseProduct, formatPrice } from '../data/decorData';

interface StoryMakerModalProps {
  product: ShowcaseProduct | null;
  onClose: () => void;
  currency: CurrencyCode;
  lang: LanguageCode;
  defaultBrandName: string;
  defaultPhone: string;
  defaultInstagram: string;
  priceMultiplier: number;
}

export const StoryMakerModal: React.FC<StoryMakerModalProps> = ({
  product,
  onClose,
  currency,
  lang,
  defaultBrandName,
  defaultPhone,
  defaultInstagram,
  priceMultiplier,
}) => {
  const canvasRef = useRef<HTMLCanvasElement | null>(null);
  const [workshopName, setWorkshopName] = useState(defaultBrandName || 'گالری کابینت و دکوراسیون رویال');
  const [phoneNumber, setPhoneNumber] = useState(defaultPhone || '0912-000-0000');
  const [instagramId, setInstagramId] = useState(defaultInstagram || '@DecorMate.VIP');
  const [installmentBadge, setInstallmentBadge] = useState('اقساط ویژه با چک صیادی ۱ تا ۶ ماهه بدون بهره');
  const [isRendering, setIsRendering] = useState(false);

  useEffect(() => {
    if (defaultBrandName) setWorkshopName(defaultBrandName);
    if (defaultPhone) setPhoneNumber(defaultPhone);
    if (defaultInstagram) setInstagramId(defaultInstagram);
  }, [defaultBrandName, defaultPhone, defaultInstagram]);

  useEffect(() => {
    if (!product) return;
    renderStoryCanvas();
  }, [product, workshopName, phoneNumber, instagramId, installmentBadge, currency, lang, priceMultiplier]);

  if (!product) return null;

  const adjustedRetailPrice = Math.round(product.pricePerMeterToman * priceMultiplier);
  const adjustedWholesalePrice = Math.round(product.wholesaleColleaguePriceToman * priceMultiplier);

  const drawProceduralLuxuryKitchenArt = (ctx: CanvasRenderingContext2D, x: number, y: number, w: number, h: number) => {
    // Offline-first procedural architectural luxury kitchen illustration (Walnut + Calacatta Marble + Warm LED)
    const bgGrad = ctx.createLinearGradient(x, y, x + w, y + h);
    bgGrad.addColorStop(0, '#3A2314');
    bgGrad.addColorStop(0.5, '#4A2E1B');
    bgGrad.addColorStop(1, '#24140B');
    ctx.fillStyle = bgGrad;
    ctx.fillRect(x, y, w, h);

    // Subtle vertical walnut slats
    ctx.strokeStyle = 'rgba(212, 175, 55, 0.16)';
    ctx.lineWidth = 2;
    for (let sx = x + 30; sx < x + w; sx += 36) {
      ctx.beginPath();
      ctx.moveTo(sx, y);
      ctx.lineTo(sx, y + h * 0.58);
      ctx.stroke();
    }

    // Upper Neoclassical Ivory Cabinets
    ctx.fillStyle = '#FAF7F2';
    const cabW = (w - 120) / 4;
    for (let i = 0; i < 4; i++) {
      const cx = x + 40 + i * (cabW + 12);
      const cy = y + 50;
      const ch = h * 0.36;
      ctx.fillRect(cx, cy, cabW, ch);
      ctx.strokeStyle = '#D4AF37';
      ctx.lineWidth = 3;
      ctx.strokeRect(cx + 12, cy + 12, cabW - 24, ch - 24);
    }

    // Warm 3000K Under-Cabinet LED Glow
    const ledGrad = ctx.createLinearGradient(x, y + h * 0.44, x, y + h * 0.56);
    ledGrad.addColorStop(0, 'rgba(246, 226, 122, 0.65)');
    ledGrad.addColorStop(1, 'rgba(246, 226, 122, 0)');
    ctx.fillStyle = ledGrad;
    ctx.fillRect(x + 30, y + h * 0.43, w - 60, h * 0.12);

    // Calacatta Marble Island Slab
    ctx.fillStyle = '#F5F2EB';
    ctx.fillRect(x + 70, y + h * 0.58, w - 140, h * 0.34);
    ctx.strokeStyle = '#D4AF37';
    ctx.lineWidth = 4;
    ctx.strokeRect(x + 70, y + h * 0.58, w - 140, h * 0.34);

    // Gold Veins on Marble
    ctx.strokeStyle = 'rgba(212, 175, 55, 0.45)';
    ctx.lineWidth = 2.5;
    ctx.beginPath();
    ctx.moveTo(x + 90, y + h * 0.64);
    ctx.bezierCurveTo(x + 260, y + h * 0.72, x + 450, y + h * 0.61, x + w - 90, y + h * 0.85);
    ctx.stroke();
  };

  const renderStoryCanvas = () => {
    const canvas = canvasRef.current;
    if (!canvas || !product) return;
    const ctx = canvas.getContext('2d');
    if (!ctx) return;

    setIsRendering(true);

    const drawPosterLayout = (loadedImg?: HTMLImageElement) => {
      const W = 1080;
      const H = 1920;
      canvas.width = W;
      canvas.height = H;

      // 1. Background Ivory-Walnut Royal Gradient
      const bgGrad = ctx.createLinearGradient(0, 0, 0, H);
      bgGrad.addColorStop(0, '#2B190E');
      bgGrad.addColorStop(0.22, '#4A2E1B');
      bgGrad.addColorStop(0.75, '#3A2314');
      bgGrad.addColorStop(1, '#1E1109');
      ctx.fillStyle = bgGrad;
      ctx.fillRect(0, 0, W, H);

      // 2. Outer 24K Gold Double Border Frame
      ctx.strokeStyle = '#D4AF37';
      ctx.lineWidth = 6;
      ctx.strokeRect(36, 36, W - 72, H - 72);
      ctx.strokeStyle = 'rgba(212, 175, 55, 0.4)';
      ctx.lineWidth = 2;
      ctx.strokeRect(52, 52, W - 104, H - 104);

      // 3. Top Ecosystem & Brand Kicker
      ctx.fillStyle = '#F6E27A';
      ctx.font = 'bold 28px Vazirmatn, sans-serif';
      ctx.textAlign = 'center';
      ctx.fillText('اکوسیستم آفرینش | شهر جدید نیومتاورسیتی جهان | توان استیج FBNM', W / 2, 115);

      // Workshop / Showroom Title
      ctx.fillStyle = '#FFFFFF';
      ctx.font = 'bold 54px Vazirmatn, sans-serif';
      ctx.fillText(workshopName, W / 2, 195);

      ctx.fillStyle = '#D4AF37';
      ctx.font = 'bold 30px Vazirmatn, sans-serif';
      ctx.fillText(`کد مدل: ${product.code}  ·  ضمانت کتبی ${product.warrantyYears} ساله اتحادیه`, W / 2, 252);

      // 4. Portfolio Image Frame (or Offline Procedural Art fallback)
      const imgX = 80;
      const imgY = 295;
      const imgW = W - 160;
      const imgH = 740;

      ctx.save();
      ctx.beginPath();
      ctx.roundRect(imgX, imgY, imgW, imgH, 28);
      ctx.clip();

      if (loadedImg) {
        ctx.drawImage(loadedImg, imgX, imgY, imgW, imgH);
      } else {
        drawProceduralLuxuryKitchenArt(ctx, imgX, imgY, imgW, imgH);
      }
      ctx.restore();

      // Gold border around image
      ctx.strokeStyle = '#D4AF37';
      ctx.lineWidth = 5;
      ctx.beginPath();
      ctx.roundRect(imgX, imgY, imgW, imgH, 28);
      ctx.stroke();

      // 5. Product Title & Material Card (Ivory Card on Walnut)
      const cardX = 80;
      const cardY = 1075;
      const cardW = W - 160;
      const cardH = 490;

      ctx.fillStyle = '#FAF7F2';
      ctx.beginPath();
      ctx.roundRect(cardX, cardY, cardW, cardH, 28);
      ctx.fill();

      ctx.strokeStyle = '#D4AF37';
      ctx.lineWidth = 4;
      ctx.stroke();

      ctx.fillStyle = '#4A2E1B';
      ctx.font = 'bold 40px Vazirmatn, sans-serif';
      ctx.fillText(product.title[lang] || product.title.fa, W / 2, cardY + 75, cardW - 60);

      ctx.fillStyle = '#6F4E37';
      ctx.font = '30px Vazirmatn, sans-serif';
      ctx.fillText(product.materialsUsed[lang] || product.materialsUsed.fa, W / 2, cardY + 135, cardW - 60);

      // Divider line
      ctx.strokeStyle = '#E6DEC8';
      ctx.lineWidth = 3;
      ctx.beginPath();
      ctx.moveTo(cardX + 60, cardY + 175);
      ctx.lineTo(cardX + cardW - 60, cardY + 175);
      ctx.stroke();

      // Retail Price Box
      ctx.fillStyle = '#4A2E1B';
      ctx.font = 'bold 32px Vazirmatn, sans-serif';
      ctx.fillText('قیمت اجرا (هر متر طول استاندارد اتحادیه):', W / 2, cardY + 235);

      ctx.fillStyle = '#059669';
      ctx.font = 'bold 62px Vazirmatn, monospace';
      ctx.fillText(formatPrice(adjustedRetailPrice, currency, lang), W / 2, cardY + 315);

      // Colleague Wholesale Price info
      ctx.fillStyle = '#6F4E37';
      ctx.font = 'bold 28px Vazirmatn, sans-serif';
      ctx.fillText(
        `قیمت عمده ورق و یراق همکار: ${formatPrice(adjustedWholesalePrice, currency, lang)} | تحویل ${product.deliveryDays} روزه`,
        W / 2,
        cardY + 380,
        cardW - 60
      );

      // Sayadi Check Pill Banner
      ctx.fillStyle = '#4A2E1B';
      ctx.beginPath();
      ctx.roundRect(cardX + 50, cardY + 410, cardW - 100, 62, 16);
      ctx.fill();

      ctx.fillStyle = '#F6E27A';
      ctx.font = 'bold 30px Vazirmatn, sans-serif';
      ctx.fillText(`✨ ${installmentBadge}`, W / 2, cardY + 450, cardW - 120);

      // 6. Bottom Contact & Call-To-Action Footer
      ctx.fillStyle = '#D4AF37';
      ctx.beginPath();
      ctx.roundRect(80, 1605, W - 160, 195, 24);
      ctx.fill();

      ctx.fillStyle = '#2A1A10';
      ctx.font = 'bold 36px Vazirmatn, sans-serif';
      ctx.fillText(`📞 مشاوره و بازدید رایگان: ${phoneNumber}`, W / 2, 1675);

      ctx.fillStyle = '#4A2E1B';
      ctx.font = 'bold 32px Vazirmatn, sans-serif';
      ctx.fillText(`اینستاگرام: ${instagramId}  |  محاسبه آنلاین در DecorMate VIP`, W / 2, 1745);

      ctx.fillStyle = '#E6DEC8';
      ctx.font = '24px Vazirmatn, sans-serif';
      ctx.fillText('طراحی شده با موتور استوری‌ساز آفلاین DecorMate VIP — بدون نیاز به اینترنت', W / 2, 1850);

      setIsRendering(false);
    };

    // Try loading image with crossOrigin; if offline or blocked, immediately draw procedural kitchen art!
    const img = new Image();
    img.crossOrigin = 'anonymous';
    img.onload = () => drawPosterLayout(img);
    img.onerror = () => drawPosterLayout(undefined);
    img.src = product.image;
  };

  const handleDownloadStory = () => {
    const canvas = canvasRef.current;
    if (!canvas) return;
    const dataUrl = canvas.toDataURL('image/png');
    const link = document.createElement('a');
    link.download = `DecorMate-Story-${product.code}.png`;
    link.href = dataUrl;
    link.click();
  };

  return (
    <div
      className="fixed inset-0 z-50 flex items-center justify-center bg-black/75 p-4 overflow-y-auto"
      role="dialog"
      aria-modal="true"
      aria-label="Instagram Story Maker 1080x1920"
    >
      <div className="bg-[#FAF7F2] border-2 border-[#D4AF37] rounded-2xl max-w-4xl w-full p-5 sm:p-7 shadow-2xl my-auto max-h-[92vh] overflow-y-auto">
        <div className="flex items-center justify-between border-b border-[#E6DEC8] pb-4 mb-5">
          <div>
            <span className="text-xs font-semibold text-[#6F4E37]">
              پوسترساز و استوری‌ساز ۱۰۰٪ آفلاین (HTML5 Canvas HD 1080×1920)
            </span>
            <h3 className="text-xl font-bold text-[#4A2E1B] flex items-center gap-2">
              <Sparkles className="w-5 h-5 text-[#D4AF37]" />
              <span>ساخت استوری طلایی اینستاگرام و واتساپ با برند شخصی شما</span>
            </h3>
          </div>
          <button
            onClick={onClose}
            aria-label="بستن پنجره استوری‌ساز"
            className="p-2 rounded-lg bg-white border border-[#E6DEC8] text-[#4A2E1B] hover:bg-[#4A2E1B] hover:text-white transition-colors"
          >
            <X className="w-5 h-5" />
          </button>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-12 gap-6 items-start">
          {/* Customization Controls */}
          <div className="md:col-span-6 space-y-4 bg-white p-5 rounded-xl border border-[#E6DEC8]">
            <div>
              <label className="block text-xs font-bold text-[#4A2E1B] mb-1.5 flex items-center gap-1.5">
                <Store className="w-4 h-4 text-[#D4AF37]" />
                <span>نام کارگاه / نمایشگاه / شرکت معماری شما:</span>
              </label>
              <input
                type="text"
                value={workshopName}
                onChange={(e) => setWorkshopName(e.target.value)}
                className="w-full px-3.5 py-2.5 rounded-lg border border-[#E6DEC8] bg-[#FAF7F2] text-sm font-medium text-[#2A1A10] focus:outline-none focus:border-[#4A2E1B]"
              />
            </div>

            <div>
              <label className="block text-xs font-bold text-[#4A2E1B] mb-1.5 flex items-center gap-1.5">
                <Phone className="w-4 h-4 text-[#D4AF37]" />
                <span>شماره تماس و واتساپ جهت درج روی پوستر:</span>
              </label>
              <input
                type="text"
                dir="ltr"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
                className="w-full px-3.5 py-2.5 rounded-lg border border-[#E6DEC8] bg-[#FAF7F2] text-sm font-mono-tabular text-[#2A1A10] focus:outline-none focus:border-[#4A2E1B]"
              />
            </div>

            <div>
              <label className="block text-xs font-bold text-[#4A2E1B] mb-1.5 flex items-center gap-1.5">
                <Instagram className="w-4 h-4 text-[#D4AF37]" />
                <span>آیدی پیج اینستاگرام یا وب‌سایت:</span>
              </label>
              <input
                type="text"
                dir="ltr"
                value={instagramId}
                onChange={(e) => setInstagramId(e.target.value)}
                className="w-full px-3.5 py-2.5 rounded-lg border border-[#E6DEC8] bg-[#FAF7F2] text-sm font-mono-tabular text-[#2A1A10] focus:outline-none focus:border-[#4A2E1B]"
              />
            </div>

            <div>
              <label className="block text-xs font-bold text-[#4A2E1B] mb-1.5">
                متن پیشنهاد اقساط چک صیادی روی پوستر:
              </label>
              <input
                type="text"
                value={installmentBadge}
                onChange={(e) => setInstallmentBadge(e.target.value)}
                className="w-full px-3.5 py-2.5 rounded-lg border border-[#E6DEC8] bg-[#FAF7F2] text-sm text-[#2A1A10] focus:outline-none focus:border-[#4A2E1B]"
              />
            </div>

            <div className="p-3.5 rounded-xl bg-[#FAF7F2] border border-[#E6DEC8] text-xs text-[#6F4E37] space-y-1">
              <div className="font-bold text-[#4A2E1B] flex items-center gap-1.5">
                <CheckCircle2 className="w-4 h-4 text-emerald-600 shrink-0" />
                <span>عملکرد ۱۰۰٪ آفلاین تضمین‌شده:</span>
              </div>
              <p>
                حتی در صورت قطع کامل اینترنت، موتور گرافیکی داخلی بوم (Canvas) طرح سه‌بعدی چوب گردو و سنگ مرمر را بدون افت کیفیت تولید می‌کند.
              </p>
            </div>

            <button
              onClick={handleDownloadStory}
              className="w-full py-3.5 px-5 rounded-xl bg-emerald-600 hover:bg-emerald-700 text-white font-bold text-sm flex items-center justify-center gap-2 shadow-md transition-colors cursor-pointer"
            >
              <Download className="w-5 h-5" />
              <span>دانلود فوری پوستر استوری ۱۰۸۰×۱۹۲۰ (PNG HD)</span>
            </button>
          </div>

          {/* Live 9:16 Story Canvas Preview */}
          <div className="md:col-span-6 flex flex-col items-center">
            <div className="text-xs font-semibold text-[#6F4E37] mb-2">
              پیش‌نمایش زنده استوری (ابعاد واقعی ۱۰۸۰×۱۹۲۰ پیکسل):
            </div>
            <div className="w-full max-w-[290px] aspect-[9/16] rounded-2xl overflow-hidden border-2 border-[#D4AF37] shadow-xl bg-[#2B190E] relative">
              <canvas
                ref={canvasRef}
                className="w-full h-full object-contain"
              />
              {isRendering && (
                <div className="absolute inset-0 bg-black/40 flex items-center justify-center text-white text-xs font-bold">
                  در حال رندر گرافیک طلایی...
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
