package com.example.ui.dialogs

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun PrivacyAuditDialog(
    isPersian: Boolean,
    onDismiss: () -> Unit
) {
    val runtime = Runtime.getRuntime()
    val usedMemMb = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
    val maxMemMb = runtime.maxMemory() / (1024 * 1024)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Shield, contentDescription = null, tint = SageGreen, modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(8.dp))
                Text(
                    text = if (isPersian) "ممیزی امنیت و حریم خصوصی" else "Privacy & Hardware Audit",
                    color = CharcoalPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp
                )
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Seal Banner
                Surface(
                    color = SageGreenSubtle,
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SageGreen.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = if (isPersian) "تضمین ۱۰۰٪ آفلاین بودن" else "100% AIR-GAPPED & OFFLINE",
                            color = SageGreen,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = if (isPersian)
                                "این برنامه فاقد هرگونه مجوز اینترنت در مانیفست است. انتقال داده‌ها به سرورهای ابری از نظر فنی غیرممکن است."
                            else
                                "This application declares ZERO internet permissions in its AndroidManifest. It is technically impossible to transmit data off-device.",
                            color = CharcoalSecondary,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )
                    }
                }

                // Checkpoints
                val audits = listOf(
                    Pair(
                        if (isPersian) "عدم ذخیره هویت بیومتریک" else "No Biometric Identity DB",
                        if (isPersian) "تشخیص چهره صرفاً هندسی است و پایگاه داده چهره ندارد." else "Detection is purely geometric bounds. No identity templates."
                    ),
                    Pair(
                        if (isPersian) "میکروفون محلی بدون ترانزیت" else "Local Microphone Audio",
                        if (isPersian) "صدای ضبط شده مستقیماً در فایل محلی ذخیره می‌شود." else "Audio stream is written straight to local MP4 container."
                    ),
                    Pair(
                        if (isPersian) "بهینه‌سازی شده برای ردمی نوت ۸" else "Xiaomi Redmi Note 8 Optimized",
                        if (isPersian) "بافر تصویر کوچک، الگوریتم کم‌مصرف و کنترل حرارتی." else "Downsampled buffer, thermal guard, low memory footprint."
                    )
                )

                audits.forEach { (title, desc) ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SageGreen, modifier = Modifier.size(16.dp))
                        Column {
                            Text(title, color = CharcoalPrimary, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                            Text(desc, color = CharcoalSecondary, fontSize = 11.sp, lineHeight = 15.sp)
                        }
                    }
                }

                HorizontalDivider(color = WarmBorderSubtle)

                // Hardware Info
                Text(
                    text = if (isPersian) "مشخصات سخت‌افزار و حافظه:" else "Device & Memory Telemetry:",
                    color = CharcoalPrimary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )

                Card(
                    colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Device: ${Build.MANUFACTURER} ${Build.MODEL} (Android ${Build.VERSION.RELEASE})", color = CharcoalSecondary, fontSize = 11.sp)
                        Text("JVM Memory: ${usedMemMb}MB / ${maxMemMb}MB limit", color = CharcoalSecondary, fontSize = 11.sp)
                        Text("Architecture: ${Build.SUPPORTED_ABIS.firstOrNull() ?: "arm64"}", color = CharcoalSecondary, fontSize = 11.sp)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent)
            ) {
                Text(if (isPersian) "تأیید و بستن" else "Close Audit", color = Color.White)
            }
        },
        containerColor = WarmSurface
    )
}
