package com.example.ui.gallery

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.engine.StorageManager
import com.example.model.VideoItem
import com.example.ui.theme.*
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfflineVideoLabScreen(
    storageManager: StorageManager,
    isPersian: Boolean,
    onNavigateBack: () -> Unit
) {
    BackHandler { onNavigateBack() }

    val coroutineScope = rememberCoroutineScope()
    var savedVideos by remember { mutableStateOf<List<VideoItem>>(emptyList()) }
    var selectedVideo by remember { mutableStateOf<VideoItem?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var videoViewInstance by remember { mutableStateOf<VideoView?>(null) }

    // Lab tools state
    var showExportDialog by remember { mutableStateOf(false) }
    var exportName by remember { mutableStateOf("") }
    var showDeleteConfirmDialog by remember { mutableStateOf<VideoItem?>(null) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var isSplitCompareMode by remember { mutableStateOf(false) }

    fun reloadVideos() {
        coroutineScope.launch {
            savedVideos = storageManager.getSavedVideos()
            if (selectedVideo == null && savedVideos.isNotEmpty()) {
                selectedVideo = savedVideos.first()
            }
        }
    }

    LaunchedEffect(Unit) {
        reloadVideos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = if (isPersian) "آزمایشگاه محلی ویدیو" else "Offline Video Lab",
                            color = CharcoalPrimary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp
                        )
                        Text(
                            text = if (isPersian) "مدیریت محلی فایل‌های ذخیره شده" else "Local processing • Zero cloud storage",
                            color = CharcoalSecondary,
                            fontSize = 12.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = CharcoalPrimary
                        )
                    }
                },
                actions = {
                    Surface(
                        color = SageGreenSubtle,
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SageGreen.copy(alpha = 0.3f)),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.Lock, contentDescription = null, tint = SageGreen, modifier = Modifier.size(13.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = if (isPersian) "آفلاین" else "OFFLINE",
                                color = SageGreen,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = WarmSurface)
            )
        },
        containerColor = WarmBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Status banner if any
            if (statusMessage != null) {
                Surface(
                    color = SageGreenSubtle,
                    border = androidx.compose.foundation.BorderStroke(1.dp, SageGreen.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(statusMessage ?: "", color = SageGreen, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        IconButton(onClick = { statusMessage = null }, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = SageGreen, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            if (selectedVideo != null) {
                val currentVideo = selectedVideo!!
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .background(Color(0xFF1E1C1A))
                ) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { ctx ->
                            VideoView(ctx).apply {
                                val mc = MediaController(ctx)
                                mc.setAnchorView(this)
                                setMediaController(mc)
                                setVideoURI(Uri.parse(currentVideo.uri))
                                setOnPreparedListener { mp ->
                                    mp.isLooping = true
                                    start()
                                    isPlaying = true
                                }
                                videoViewInstance = this
                            }
                        },
                        update = { view ->
                            view.setVideoURI(Uri.parse(currentVideo.uri))
                            view.start()
                            isPlaying = true
                        }
                    )

                    // Video Name Badge
                    Surface(
                        color = Color.Black.copy(alpha = 0.65f),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(10.dp)
                    ) {
                        Text(
                            text = currentVideo.name,
                            color = Color.White,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }

                    if (isSplitCompareMode) {
                        Surface(
                            color = TerracottaAccent,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = if (isPersian) "حالت مقایسه فعال" else "Compare Active",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                // Video Lab Actions Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(WarmSurface)
                        .border(1.dp, WarmBorder)
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Export Button
                    IconButton(onClick = {
                        exportName = currentVideo.name.substringBeforeLast(".") + "_protected"
                        showExportDialog = true
                    }) {
                        Icon(Icons.Default.FileDownload, contentDescription = "Export", tint = TerracottaAccent)
                    }

                    // Compare Mode Toggle
                    IconButton(onClick = { isSplitCompareMode = !isSplitCompareMode }) {
                        Icon(
                            Icons.Default.Compare,
                            contentDescription = "Compare",
                            tint = if (isSplitCompareMode) TerracottaAccent else CharcoalSecondary
                        )
                    }

                    // Delete Video Button
                    IconButton(onClick = { showDeleteConfirmDialog = currentVideo }) {
                        Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = BrickRed)
                    }
                }
            } else {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .background(WarmSurfaceSecondary),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.VideocamOff, contentDescription = null, tint = CharcoalTertiary, modifier = Modifier.size(40.dp))
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = if (isPersian) "هیچ ویدیویی ثبت نشده است" else "No recordings saved yet",
                            color = CharcoalPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = if (isPersian) "برای ثبت اولین ویدیوی خصوصی کلید ضبط را لمس کنید" else "Tap record on camera screen to create your first video",
                            color = CharcoalSecondary,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // List of Saved Videos
            Text(
                text = if (isPersian) "ویدیوهای ضبط شده در دستگاه (${savedVideos.size}):" else "Local Device Recordings (${savedVideos.size}):",
                style = MaterialTheme.typography.titleSmall,
                color = CharcoalSecondary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(savedVideos) { item ->
                    val isSelected = item.id == selectedVideo?.id
                    val dateFormatted = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US).format(Date(item.timestamp))
                    val sizeMb = String.format("%.1f MB", item.sizeBytes / (1024f * 1024f))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedVideo = item }
                            .border(
                                width = 1.dp,
                                color = if (isSelected) TerracottaAccent else WarmBorder,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) TerracottaSubtle else WarmSurface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .background(WarmSurfaceSecondary, RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = if (isSelected) TerracottaAccent else CharcoalSecondary
                                    )
                                }
                                Spacer(Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = item.name,
                                        color = CharcoalPrimary,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 13.sp,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = "$dateFormatted • $sizeMb",
                                        color = CharcoalSecondary,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            IconButton(onClick = { showDeleteConfirmDialog = item }) {
                                Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = CharcoalTertiary)
                            }
                        }
                    }
                }
            }
        }
    }

    // Export Dialog
    if (showExportDialog && selectedVideo != null) {
        val target = selectedVideo!!
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = {
                Text(
                    if (isPersian) "خروجی محلی ویدیو (MP4)" else "Local MP4 Export",
                    color = CharcoalPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        if (isPersian) "این فایل به صورت مستقیم در حافظه محلی ذخیره شده و هیچ‌گونه اتصال اینترنتی برقرار نمی‌شود."
                        else "Video will be exported locally to your device storage without any cloud or internet transfer.",
                        color = CharcoalSecondary,
                        fontSize = 12.sp
                    )
                    OutlinedTextField(
                        value = exportName,
                        onValueChange = { exportName = it },
                        label = { Text(if (isPersian) "نام فایل خروجی" else "Export File Name") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = TerracottaAccent,
                            unfocusedBorderColor = WarmBorder,
                            focusedLabelColor = TerracottaAccent
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val exported = storageManager.exportVideo(target, exportName)
                            showExportDialog = false
                            statusMessage = if (exported != null) {
                                if (isPersian) "ویدیو با موفقیت ذخیره شد: ${exported.name}" else "Saved locally: ${exported.name}"
                            } else {
                                if (isPersian) "خطا در استخراج ویدیو" else "Export failed"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent)
                ) {
                    Text(if (isPersian) "ذخیره در حافظه" else "Export Locally", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text(if (isPersian) "انصراف" else "Cancel", color = CharcoalSecondary)
                }
            },
            containerColor = WarmSurface
        )
    }

    // Delete Confirmation Dialog
    if (showDeleteConfirmDialog != null) {
        val toDelete = showDeleteConfirmDialog!!
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = null },
            icon = { Icon(Icons.Default.Warning, contentDescription = null, tint = BrickRed) },
            title = {
                Text(
                    if (isPersian) "تأیید حذف ویدیو" else "Confirm Deletion",
                    color = BrickRed,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Text(
                    text = if (isPersian)
                        "آیا از حذف ویدیوی '${toDelete.name}' از حافظه دستگاه اطمینان دارید؟"
                    else
                        "Are you sure you want to delete '${toDelete.name}' from local device storage?",
                    color = CharcoalPrimary,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val success = storageManager.deleteVideo(toDelete)
                            showDeleteConfirmDialog = null
                            if (selectedVideo?.id == toDelete.id) {
                                selectedVideo = null
                            }
                            reloadVideos()
                            statusMessage = if (success) {
                                if (isPersian) "ویدیو حذف شد" else "Video deleted"
                            } else {
                                if (isPersian) "خطا در حذف ویدیو" else "Delete error"
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BrickRed)
                ) {
                    Text(if (isPersian) "حذف قطعی" else "Delete", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmDialog = null }) {
                    Text(if (isPersian) "لغو" else "Cancel", color = CharcoalSecondary)
                }
            },
            containerColor = WarmSurface
        )
    }
}
