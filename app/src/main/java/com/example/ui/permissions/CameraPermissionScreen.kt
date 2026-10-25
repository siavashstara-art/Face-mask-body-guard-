package com.example.ui.permissions

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    onNavigateBack: () -> Unit = {},
    onPermissionsGranted: @Composable () -> Unit
) {
    var allowSimulationFallback by remember { mutableStateOf(false) }

    BackHandler {
        onNavigateBack()
    }

    val permissionState: MultiplePermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    )

    // تقاضای خودکار دسترسی بدون معطلی در لحظه ورود به برنامه (Zero-click prompt)
    LaunchedEffect(Unit) {
        if (!permissionState.allPermissionsGranted) {
            try {
                permissionState.launchMultiplePermissionRequest()
            } catch (_: Throwable) {}
        }
    }

    // بررسی اینکه آیا دسترسی دوربین داده شده است یا کاربر حالت شبیه‌ساز را انتخاب کرده
    val hasCameraPermission = permissionState.permissions.any {
        it.permission == Manifest.permission.CAMERA && it.status.let { status ->
            status is com.google.accompanist.permissions.PermissionStatus.Granted
        }
    }

    if (permissionState.allPermissionsGranted || hasCameraPermission || allowSimulationFallback) {
        onPermissionsGranted()
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(WarmBackground)
                .statusBarsPadding()
                .padding(20.dp)
        ) {
            // دکمه بازگشت به داشبورد اصلی
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(42.dp)
                    .background(WarmSurface, CircleShape)
                    .border(1.dp, WarmBorder, CircleShape)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = CharcoalPrimary
                )
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = WarmSurface),
                shape = RoundedCornerShape(24.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(TerracottaSubtle, CircleShape)
                            .border(1.dp, TerracottaAccent.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = null,
                            tint = TerracottaAccent,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Text(
                        text = if (isPersian) "استودیو ضبط و ژست‌های فیس‌گارد" else "FaceGuard Studio & Director",
                        color = CharcoalPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Surface(
                        color = SageGreenSubtle,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (isPersian) "۱۰۰٪ امن، بدون اینترنت و ذخیره فقط در گوشی" else "100% Offline & Private Local Storage",
                            color = SageGreen,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Text(
                        text = if (isPersian)
                            "برای فیلمبرداری از ژست‌ها، پرو لباس و ساخت کلیپ‌های اختصاصی مزون و سالن، به دسترسی دوربین و میکروفون نیاز است.\n\nاگر به دوربین دسترسی ندهید، می‌توانید در حالت شبیه‌ساز بصری استودیو وارد شوید."
                        else
                            "To record cinematic poses and preview maison garments, camera and microphone access are used.\n\nAll media processing runs 100% locally on your device.",
                        color = CharcoalSecondary,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 19.sp
                    )

                    // دکمه اصلی اعطای دسترسی
                    Button(
                        onClick = {
                            try {
                                permissionState.launchMultiplePermissionRequest()
                            } catch (_: Throwable) {
                                allowSimulationFallback = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(Icons.Default.Shield, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = if (isPersian) "اعطای دسترسی دوربین و میکروفون" else "Grant Camera & Mic Access",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                    }

                    // دکمه ورود در حالت شبیه‌ساز استودیو (بدون توقف یا گیر کردن برنامه)
                    OutlinedButton(
                        onClick = { allowSimulationFallback = true },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = CharcoalPrimary),
                        border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp), tint = TerracottaAccent)
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = if (isPersian) "ورود مستقیم به استودیو (حالت شبیه‌ساز ژست‌ها)" else "Enter Studio (Simulation Mode)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
