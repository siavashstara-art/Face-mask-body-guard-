package com.example.ui.panels

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.example.model.StoryStreamConfig
import com.example.model.StreamPlatform
import com.example.ui.theme.*

@Composable
fun StoryLiveStudioPanel(
    config: StoryStreamConfig,
    isPersian: Boolean,
    onUpdate: (StoryStreamConfig) -> Unit,
    onToggleStream: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

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
                text = if (isPersian) "استوری‌ساز و پخش زنده (RTMP)" else "Story & Live Broadcast",
                style = MaterialTheme.typography.titleMedium,
                color = CharcoalPrimary,
                fontWeight = FontWeight.SemiBold
            )
            Surface(
                color = if (config.isLiveStreaming) BrickRedSubtle else SageGreenSubtle,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = if (config.isLiveStreaming) (if (isPersian) "در حال پخش" else "LIVE") else (if (isPersian) "آماده اتصال" else "READY"),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (config.isLiveStreaming) BrickRed else SageGreen,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }
        }

        // 1. Instagram Story Formatter
        Card(
            colors = CardDefaults.cardColors(containerColor = WarmSurfaceSecondary),
            shape = RoundedCornerShape(10.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, WarmBorder)
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isPersian) "راهنمای ابعاد استوری ۹:۱۶ اینستاگرام" else "Instagram 9:16 Story Guide",
                        style = MaterialTheme.typography.bodyMedium,
                        color = CharcoalPrimary,
                        fontWeight = FontWeight.Medium
                    )
                    Switch(
                        checked = config.showStoryGuide916,
                        onCheckedChange = { onUpdate(config.copy(showStoryGuide916 = it)) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = TerracottaAccent
                        )
                    )
                }

                OutlinedTextField(
                    value = config.storyHeadline,
                    onValueChange = { onUpdate(config.copy(storyHeadline = it)) },
                    label = { Text(if (isPersian) "متن تیتر استوری" else "Story Headline / Sticker") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TerracottaAccent,
                        unfocusedBorderColor = WarmBorder,
                        focusedLabelColor = TerracottaAccent
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                // Quick Share button
                Button(
                    onClick = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, config.storyHeadline.ifBlank { "Recorded with FaceGuard Studio" })
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, if (isPersian) "اشتراک در اینستاگرام یا پلتفرم‌ها" else "Share to Stories / Platforms")
                        context.startActivity(shareIntent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(if (isPersian) "اشتراک‌گذاری مستقیم در اینستاگرام" else "Share Story Directly", color = Color.White)
                }
            }
        }

        HorizontalDivider(color = WarmBorderSubtle)

        // 2. Live Broadcast Platform (YouTube, Discord, Telegram)
        Text(
            text = if (isPersian) "پلتفرم پخش زنده:" else "Live Broadcast Platform:",
            style = MaterialTheme.typography.bodyMedium,
            color = CharcoalSecondary
        )

        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            items(StreamPlatform.values()) { platform ->
                val selected = config.selectedPlatform == platform
                Card(
                    modifier = Modifier
                        .clickable {
                            onUpdate(
                                config.copy(
                                    selectedPlatform = platform,
                                    rtmpUrl = platform.defaultRtmpServer
                                )
                            )
                        }
                        .border(
                            width = 1.dp,
                            color = if (selected) TerracottaAccent else WarmBorder,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = if (selected) TerracottaSubtle else WarmSurface)
                ) {
                    Text(
                        text = platform.displayName,
                        color = if (selected) TerracottaHover else CharcoalPrimary,
                        fontSize = 12.sp,
                        fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }

        // RTMP Server URL
        OutlinedTextField(
            value = config.rtmpUrl,
            onValueChange = { onUpdate(config.copy(rtmpUrl = it)) },
            label = { Text(if (isPersian) "آدرس سرور RTMP" else "RTMP Server URL") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TerracottaAccent,
                unfocusedBorderColor = WarmBorder,
                focusedLabelColor = TerracottaAccent
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Stream Key
        OutlinedTextField(
            value = config.streamKey,
            onValueChange = { onUpdate(config.copy(streamKey = it)) },
            label = { Text(if (isPersian) "کلید پخش (Stream Key)" else "Stream Key") },
            placeholder = { Text(if (isPersian) "کلید پخش اختصاصی یوتیوب/دیسکورد" else "e.g. live_xxxx_xxxx") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TerracottaAccent,
                unfocusedBorderColor = WarmBorder,
                focusedLabelColor = TerracottaAccent
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Start / Stop Stream Button
        Button(
            onClick = onToggleStream,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (config.isLiveStreaming) BrickRed else SageGreen
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                if (config.isLiveStreaming) Icons.Default.Stop else Icons.Default.Podcasts,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = if (config.isLiveStreaming)
                    (if (isPersian) "قطع پخش زنده" else "Stop Live Stream")
                else
                    (if (isPersian) "شروع پخش زنده به ${config.selectedPlatform.displayName}" else "Start Live Broadcast to ${config.selectedPlatform.displayName}"),
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
