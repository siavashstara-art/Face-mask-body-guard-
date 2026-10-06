package com.example.ui.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.OpenWith
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.DualCameraConfig
import com.example.model.InsetCorner
import com.example.ui.theme.CharcoalPrimary
import com.example.ui.theme.SageGreen
import com.example.ui.theme.TerracottaAccent
import com.example.ui.theme.WarmBorder
import com.example.ui.theme.WarmSurface

@Composable
fun DualCameraInsetView(
    config: DualCameraConfig,
    language: AppLanguage,
    onFlipPrimarySecondary: () -> Unit,
    onCycleCorner: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (!config.enabled) return

    val alignment = when (config.corner) {
        InsetCorner.TOP_RIGHT -> Alignment.TopEnd
        InsetCorner.TOP_LEFT -> Alignment.TopStart
        InsetCorner.BOTTOM_RIGHT -> Alignment.BottomEnd
        InsetCorner.BOTTOM_LEFT -> Alignment.BottomStart
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = alignment
    ) {
        // Floating Inset Camera Window (Picture-in-Picture)
        Card(
            modifier = Modifier
                .padding(16.dp)
                .width(130.dp)
                .height(180.dp)
                .shadow(12.dp, RoundedCornerShape(14.dp))
                .border(2.dp, TerracottaAccent, RoundedCornerShape(14.dp)),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Black)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Secondary viewfinder simulation (styled preview)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1E1C1A))
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top header in Inset Window
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = SageGreen,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = if (config.isFrontPrimary) "REAR" else "FRONT",
                                color = Color.White,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }

                        IconButton(
                            onClick = onClose,
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close Dual Cam", tint = Color.White, modifier = Modifier.size(14.dp))
                        }
                    }

                    // Center Live Inset Icon / Tag
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            color = TerracottaAccent.copy(alpha = 0.2f),
                            shape = CircleShape,
                            border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaAccent)
                        ) {
                            Icon(
                                Icons.Default.Cameraswitch,
                                contentDescription = null,
                                tint = TerracottaAccent,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(24.dp)
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = when (language) {
                                AppLanguage.PERSIAN -> if (config.isFrontPrimary) "دوربین دوم (پشت)" else "دوربین دوم (جلو)"
                                AppLanguage.ARABIC -> if (config.isFrontPrimary) "الكاميرا الخلفية" else "الكاميرا الأمامية"
                                AppLanguage.ENGLISH -> if (config.isFrontPrimary) "Rear Viewfinder" else "Front Viewfinder"
                            },
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // Inset Window Bottom Action bar (Flip & Move corner)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Flip primary/secondary
                        Surface(
                            color = WarmSurface.copy(alpha = 0.85f),
                            shape = CircleShape,
                            modifier = Modifier
                                .size(26.dp)
                                .clickable { onFlipPrimarySecondary() }
                        ) {
                            Icon(
                                Icons.Default.Cameraswitch,
                                contentDescription = "Flip",
                                tint = CharcoalPrimary,
                                modifier = Modifier.padding(4.dp)
                            )
                        }

                        // Relocate Corner
                        Surface(
                            color = WarmSurface.copy(alpha = 0.85f),
                            shape = CircleShape,
                            modifier = Modifier
                                .size(26.dp)
                                .clickable { onCycleCorner() }
                        ) {
                            Icon(
                                Icons.Default.OpenWith,
                                contentDescription = "Move Corner",
                                tint = CharcoalPrimary,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
