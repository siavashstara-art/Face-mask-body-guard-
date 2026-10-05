package com.example.ui.permissions

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionScreen(
    isPersian: Boolean,
    onPermissionsGranted: @Composable () -> Unit
) {
    val permissionState: MultiplePermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    )

    if (permissionState.allPermissionsGranted) {
        onPermissionsGranted()
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(WarmBackground)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = WarmSurface),
                shape = RoundedCornerShape(20.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(TerracottaSubtle, CircleShape)
                            .border(1.dp, TerracottaAccent.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Shield,
                            contentDescription = null,
                            tint = TerracottaAccent,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Text(
                        text = if (isPersian) "استودیو فیس‌گارد" else "FaceGuard Studio",
                        color = CharcoalPrimary,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )

                    Surface(
                        color = SageGreenSubtle,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (isPersian) "دوربین کاملاً خصوصی و آفلاین" else "Private Offline Camera",
                            color = SageGreen,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = if (isPersian)
                            "برای فعال‌سازی پیش‌نمایش تصویر، پوشش‌های حریم خصوصی، تار کردن چهره و ضبط ویدیوی محلی، فیس‌گارد نیاز به دسترسی دوربین و میکروفون دارد.\n\nتمامی پردازش‌ها به صورت ۱۰۰٪ آفلاین بر روی دستگاه شما انجام شده و هیچ داده‌ای ارسال نمی‌شود."
                        else
                            "To enable real-time viewfinder preview, privacy masks, face blurring, and local recording, FaceGuard requires camera and microphone permissions.\n\nAll media processing runs 100% locally on your device with zero cloud connectivity.",
                        color = CharcoalSecondary,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )

                    Button(
                        onClick = { permissionState.launchMultiplePermissionRequest() },
                        colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = if (isPersian) "اعطای دسترسی و ورود به برنامه" else "Grant Permissions & Enter",
                            color = Color.White,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
