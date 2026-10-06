package com.example.ui.panels

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ProductCatalogConfig
import com.example.ui.theme.*

@Composable
fun CatalogStudioPanel(
    config: ProductCatalogConfig,
    isPersian: Boolean,
    onUpdate: (ProductCatalogConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var copiedFeedback by remember { mutableStateOf(false) }

    fun buildTelegramCaption(): String {
        return buildString {
            append("🛍️ ${config.productName}\n")
            append("━━━━━━━━━━━━━━━\n")
            append("💰 قیمت: ${config.price}\n")
            append("📏 سایزبندی: ${config.sizes}\n")
            append("🧵 جنس پارچه: ${config.fabricType}\n")
            append("━━━━━━━━━━━━━━━\n")
            append("📲 ثبت سفارش و پشتیبانی: ${config.telegramChannel}\n")
            append("✨ عکاسی شده با استودیو بوتیک فیس‌گارد")
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(WarmSurface)
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isPersian) "استودیو کاتالوگ و فروشگاه تلگرام" else "Boutique Catalog & Telegram Studio",
                style = MaterialTheme.typography.titleMedium,
                color = CharcoalPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Surface(
                color = SageGreenSubtle,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = if (isPersian) "آماده ارسال" else "Telegram Ready",
                    style = MaterialTheme.typography.labelSmall,
                    color = SageGreen,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        // Toggles
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isPersian) "نمایش برچسب قیمت روی تصویر" else "Show Price Badge on Frame",
                style = MaterialTheme.typography.bodyMedium,
                color = CharcoalPrimary
            )
            Switch(
                checked = config.showPriceBadge,
                onCheckedChange = { onUpdate(config.copy(showPriceBadge = it)) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = TerracottaAccent
                )
            )
        }

        // Product Name
        OutlinedTextField(
            value = config.productName,
            onValueChange = { onUpdate(config.copy(productName = it)) },
            label = { Text(if (isPersian) "نام یا مدل محصول" else "Product Title") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TerracottaAccent,
                unfocusedBorderColor = WarmBorder,
                focusedLabelColor = TerracottaAccent
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Price & Sizes in a Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = config.price,
                onValueChange = { onUpdate(config.copy(price = it)) },
                label = { Text(if (isPersian) "قیمت" else "Price") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TerracottaAccent,
                    unfocusedBorderColor = WarmBorder,
                    focusedLabelColor = TerracottaAccent
                ),
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = config.sizes,
                onValueChange = { onUpdate(config.copy(sizes = it)) },
                label = { Text(if (isPersian) "سایزبندی" else "Sizes") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = TerracottaAccent,
                    unfocusedBorderColor = WarmBorder,
                    focusedLabelColor = TerracottaAccent
                ),
                modifier = Modifier.weight(1f)
            )
        }

        // Fabric Material & Telegram Channel
        OutlinedTextField(
            value = config.fabricType,
            onValueChange = { onUpdate(config.copy(fabricType = it)) },
            label = { Text(if (isPersian) "جنس پارچه و توضیحات" else "Fabric & Description") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TerracottaAccent,
                unfocusedBorderColor = WarmBorder,
                focusedLabelColor = TerracottaAccent
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = config.telegramChannel,
            onValueChange = { onUpdate(config.copy(telegramChannel = it)) },
            label = { Text(if (isPersian) "آیدی کانال یا پشتیبانی تلگرام" else "Telegram Channel / ID") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TerracottaAccent,
                unfocusedBorderColor = WarmBorder,
                focusedLabelColor = TerracottaAccent
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Action Buttons: Share to Telegram & Copy Caption
        Button(
            onClick = {
                val caption = buildTelegramCaption()
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, caption)
                    type = "text/plain"
                    `package` = "org.telegram.messenger"
                }
                try {
                    context.startActivity(sendIntent)
                } catch (_: Exception) {
                    // Fallback to generic share chooser if Telegram app is not specifically named
                    val fallbackIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, caption)
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(fallbackIntent, if (isPersian) "ارسال پست به تلگرام" else "Share to Telegram"))
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(if (isPersian) "ارسال مستقیم پست به تلگرام" else "Share Post to Telegram", color = Color.White)
        }

        OutlinedButton(
            onClick = {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Telegram Caption", buildTelegramCaption())
                clipboard.setPrimaryClip(clip)
                copiedFeedback = true
            },
            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.ContentCopy, contentDescription = null, tint = CharcoalPrimary, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text(
                text = if (copiedFeedback)
                    (if (isPersian) "کپشن کپی شد ✓" else "Caption Copied ✓")
                else
                    (if (isPersian) "کپی کردن متن تبلیغاتی تلگرام" else "Copy Telegram Caption"),
                color = CharcoalPrimary
            )
        }
    }
}
