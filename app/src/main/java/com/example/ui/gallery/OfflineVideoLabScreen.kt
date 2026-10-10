package com.example.ui.gallery

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.R
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

    // Lab tools states
    var showExportDialog by remember { mutableStateOf(false) }
    var exportName by remember { mutableStateOf("") }
    var showCompressDialog by remember { mutableStateOf(false) }
    var selectedCompressPreset by remember { mutableStateOf(com.example.engine.CompressionQualityPreset.MAX_COMPRESSION) }
    var isCompressing by remember { mutableStateOf(false) }
    var compressProgress by remember { mutableStateOf(0f) }
    var showDeleteConfirmDialog by remember { mutableStateOf<VideoItem?>(null) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    var isSplitCompareMode by remember { mutableStateOf(false) }

    // Auto-Reel Generator states
    var showAutoReelDialog by remember { mutableStateOf(false) }
    var reelDurationSec by remember { mutableStateOf(15) }
    var selectedReelLut by remember { mutableStateOf(com.example.model.CinematicLutFilter.CHAMPAGNE_GOLD) }
    var isReelSlowMo by remember { mutableStateOf(true) }
    var isReelWatermark by remember { mutableStateOf(true) }
    var reelMaisonName by remember { mutableStateOf("مزون رویال پرنسس پالاس") }
    var reelSalonName by remember { mutableStateOf("سالن زیبایی شاین VIP") }
    var reelVoucherCode by remember { mutableStateOf("FACEGUARD-REEL-20") }
    var reelMusicMood by remember { mutableStateOf("پیانو رمانتیک مراسم") }
    var isGeneratingReel by remember { mutableStateOf(false) }
    var reelProgress by remember { mutableStateOf(0f) }

    // Video Post-Processing Editor Dialog state
    var showEditorDialog by remember { mutableStateOf(false) }
    var editorTab by remember { mutableStateOf(0) } // 0: Trim, 1: Face/Swap, 2: Audio/Dubbing, 3: Subtitle, 4: Merge
    var trimStartSec by remember { mutableStateOf(0f) }
    var trimEndSec by remember { mutableStateOf(10f) }
    var postBlurActive by remember { mutableStateOf(false) }
    var postFaceSwapActive by remember { mutableStateOf(false) }
    var selectedSwapAvatarRes by remember { mutableStateOf(R.drawable.ic_avatar_hollywood) }
    
    // Audio, Voiceover & Music state
    var muteOriginalAudio by remember { mutableStateOf(false) }
    var postDenoiseAudio by remember { mutableStateOf(true) }
    var postPitchFactor by remember { mutableStateOf(0.85f) } // Deep pitch default
    var selectedMusicIndex by remember { mutableStateOf(0) } // 0: None, 1: Boutique, 2: Cinematic, 3: Lo-Fi
    var musicVolume by remember { mutableStateOf(0.65f) }
    var isVoiceDubbingActive by remember { mutableStateOf(false) }
    var dubbingDurationSec by remember { mutableStateOf(0) }

    // Subtitle & Caption state
    var subtitleText by remember { mutableStateOf("") }
    var subtitleFontSize by remember { mutableStateOf(13f) }
    var subtitlePosition by remember { mutableStateOf(0) } // 0: Bottom, 1: Center, 2: Top

    var mergeTargetVideo by remember { mutableStateOf<VideoItem?>(null) }

    // Standard Zero-Permission Video Picker
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            coroutineScope.launch {
                statusMessage = if (isPersian) "در حال وارد کردن ویدیو از حافظه گوشی..." else "Importing video from device..."
                val imported = storageManager.importVideoFromUri(uri)
                if (imported != null) {
                    savedVideos = storageManager.getSavedVideos()
                    selectedVideo = imported
                    statusMessage = if (isPersian) "ویدیو وارد شد و آماده تدوین است ✓" else "Video imported & ready for lab processing ✓"
                } else {
                    statusMessage = if (isPersian) "خطا در بارگذاری فایل ویدیو" else "Could not load video"
                }
            }
        }
    }

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
                            text = if (isPersian) "آزمایشگاه تدوین ویدیوی آفلاین" else "Offline Video Studio & Lab",
                            color = CharcoalPrimary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 17.sp
                        )
                        Text(
                            text = if (isPersian) "تدوین، صداگذاری، زیرنویس و فیس‌سواپ محلی" else "Trim, Dubbing, Subtitle & Privacy Editing",
                            color = CharcoalSecondary,
                            fontSize = 11.sp
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
                    // Import Video from Device Gallery Button
                    OutlinedButton(
                        onClick = {
                            videoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                            )
                        },
                        colors = ButtonDefaults.outlinedButtonColors(containerColor = WarmSurfaceSecondary),
                        border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaAccent),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = TerracottaAccent, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(if (isPersian) "وارد کردن از گالری" else "Import Gallery", color = TerracottaAccent, fontSize = 11.sp, fontWeight = FontWeight.Medium)
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
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(statusMessage ?: "", color = SageGreen, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        IconButton(onClick = { statusMessage = null }, modifier = Modifier.size(24.dp)) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = SageGreen, modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }

            if (selectedVideo != null) {
                val currentVideo = selectedVideo!!
                val videoDurationSec = (currentVideo.durationMs / 1000f).coerceAtLeast(1f)

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
                                    if (muteOriginalAudio) {
                                        mp.setVolume(0f, 0f)
                                    }
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

                    // Post-processing visual overlays during playback
                    if (postBlurActive) {
                        Surface(
                            color = Color(0xCC1E1C1A),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, TerracottaAccent),
                            modifier = Modifier
                                .size(90.dp, 100.dp)
                                .align(Alignment.Center)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = if (isPersian) "تاری چهره" else "BLURRED",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    if (postFaceSwapActive) {
                        Image(
                            painter = painterResource(id = selectedSwapAvatarRes),
                            contentDescription = "Swap Overlay",
                            modifier = Modifier
                                .size(95.dp)
                                .align(Alignment.Center)
                        )
                    }

                    // Subtitle / Caption live preview
                    if (subtitleText.isNotBlank()) {
                        val subAlign = when (subtitlePosition) {
                            1 -> Alignment.Center
                            2 -> Alignment.TopCenter
                            else -> Alignment.BottomCenter
                        }
                        Surface(
                            color = Color.Black.copy(alpha = 0.80f),
                            shape = RoundedCornerShape(6.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.35f)),
                            modifier = Modifier
                                .align(subAlign)
                                .padding(horizontal = 14.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = subtitleText,
                                color = Color.White,
                                fontSize = subtitleFontSize.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

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

                    // Audio & Dubbing Badges
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        if (muteOriginalAudio) {
                            Surface(color = BrickRed, shape = RoundedCornerShape(4.dp)) {
                                Text("بی‌صدا (Mute)", color = Color.White, fontSize = 9.sp, modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp))
                            }
                        }
                        if (isVoiceDubbingActive) {
                            Surface(color = BrickRed, shape = RoundedCornerShape(4.dp)) {
                                Text("REC گفتار", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp))
                            }
                        }
                    }
                }

                // Video Lab Actions Toolbar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(WarmSurface)
                        .border(1.dp, WarmBorder)
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                        // Open Video Editor Suite Button
                        Button(
                            onClick = {
                                trimStartSec = 0f
                                trimEndSec = videoDurationSec
                                showEditorDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.AutoFixHigh, contentDescription = null, modifier = Modifier.size(15.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(if (isPersian) "تدوین" else "Edit", fontSize = 11.sp, color = Color.White)
                        }

                        // Auto-Reel Generator Button
                        Button(
                            onClick = { showAutoReelDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF1E1C1A),
                                contentColor = Color(0xFFFFD54F)
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD4AF37)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(Icons.Default.MovieFilter, contentDescription = null, modifier = Modifier.size(15.dp), tint = Color(0xFFFFD54F))
                            Spacer(Modifier.width(4.dp))
                            Text(if (isPersian) "تیزر ریلز ۱۵s" else "Auto-Reel", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        // Video Compressor Button (فشرده‌سازی داخل برنامه‌ای و امن)
                        IconButton(onClick = { showCompressDialog = true }) {
                            Icon(Icons.Default.Compress, contentDescription = "Compress", tint = TerracottaAccent)
                        }

                        // Export with Zero Metadata Button
                        IconButton(onClick = {
                            exportName = currentVideo.name.substringBeforeLast(".") + "_clean"
                            showExportDialog = true
                        }) {
                            Icon(Icons.Default.FileDownload, contentDescription = "Export", tint = SageGreen)
                        }

                        // Split Compare Mode Toggle
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
                        Icon(Icons.Default.VideocamOff, contentDescription = null, tint = CharcoalTertiary, modifier = Modifier.size(36.dp))
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = if (isPersian) "هیچ ویدیویی انتخاب نشده است" else "No video selected",
                            color = CharcoalPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = if (isPersian) "ویدیوهای ضبط‌شده را لمس کنید یا از گالری فایل وارد کنید" else "Select a recording or import an external video",
                            color = CharcoalSecondary,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // List of Saved Videos
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isPersian) "کتابخانه ویدیوهای محلی (${savedVideos.size}):" else "Local Video Library (${savedVideos.size}):",
                    style = MaterialTheme.typography.titleSmall,
                    color = CharcoalSecondary
                )
                Text(
                    text = if (isPersian) "۱۰۰٪ بدون ردیابی و GPS" else "Zero-GPS Scrubbed",
                    fontSize = 10.sp,
                    color = SageGreen,
                    fontWeight = FontWeight.Medium
                )
            }

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
                                        text = "$dateFormatted • $sizeMb • ${item.appliedPrivacySummary}",
                                        color = CharcoalSecondary,
                                        fontSize = 11.sp,
                                        maxLines = 1
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

    // ============================================================
    // Comprehensive Video Editor & Post-Processing Dialog
    // ============================================================
    if (showEditorDialog && selectedVideo != null) {
        val currentVideo = selectedVideo!!
        val maxDurationSec = (currentVideo.durationMs / 1000f).coerceAtLeast(1f)

        AlertDialog(
            onDismissRequest = { showEditorDialog = false },
            title = {
                Text(
                    text = if (isPersian) "استودیو تدوین، صداگذاری و زیرنویس" else "Video Lab Post-Processor",
                    color = CharcoalPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Editor Tab Row (Trim, Face/Swap, Audio/Dubbing, Subtitles, Merge)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        val tabs = listOf(
                            if (isPersian) "برش" else "Trim",
                            if (isPersian) "سانسور" else "Face",
                            if (isPersian) "صدا و دوبله" else "Audio",
                            if (isPersian) "زیرنویس" else "Subtitle",
                            if (isPersian) "ترکیب" else "Merge"
                        )
                        tabs.forEachIndexed { index, label ->
                            val selected = editorTab == index
                            Surface(
                                color = if (selected) TerracottaAccent else WarmSurfaceSecondary,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { editorTab = index }
                            ) {
                                Text(
                                    text = label,
                                    color = if (selected) Color.White else CharcoalPrimary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(vertical = 6.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    HorizontalDivider(color = WarmBorderSubtle)

                    // 1. Trim Tab
                    if (editorTab == 0) {
                        Text(
                            text = if (isPersian) "برش و زمان‌بندی ویدیو (ثانیه):" else "Trim Video Duration (Seconds):",
                            style = MaterialTheme.typography.bodySmall,
                            color = CharcoalSecondary
                        )
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("شروع: ${trimStartSec.toInt()}s", fontSize = 11.sp, color = CharcoalPrimary)
                            Text("پایان: ${trimEndSec.toInt()}s", fontSize = 11.sp, color = CharcoalPrimary)
                        }
                        Slider(
                            value = trimStartSec,
                            onValueChange = { trimStartSec = it.coerceAtMost(trimEndSec - 1f) },
                            valueRange = 0f..maxDurationSec,
                            colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent)
                        )
                        Slider(
                            value = trimEndSec,
                            onValueChange = { trimEndSec = it.coerceAtLeast(trimStartSec + 1f) },
                            valueRange = 0f..maxDurationSec,
                            colors = SliderDefaults.colors(thumbColor = SageGreen, activeTrackColor = SageGreen)
                        )
                    }

                    // 2. Face Blur & Swap Tab
                    if (editorTab == 1) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(if (isPersian) "ماسک تاری روی چهره" else "Face Blur Overlay", fontSize = 12.sp, color = CharcoalPrimary)
                            Switch(
                                checked = postBlurActive,
                                onCheckedChange = { postBlurActive = it },
                                colors = SwitchDefaults.colors(checkedTrackColor = TerracottaAccent)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(if (isPersian) "فیس‌سواپ آواتار روی ویدیو" else "Face Swap Avatar", fontSize = 12.sp, color = CharcoalPrimary)
                            Switch(
                                checked = postFaceSwapActive,
                                onCheckedChange = { postFaceSwapActive = it },
                                colors = SwitchDefaults.colors(checkedTrackColor = TerracottaAccent)
                            )
                        }

                        if (postFaceSwapActive) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                                FilterChip(
                                    selected = selectedSwapAvatarRes == R.drawable.ic_avatar_hollywood,
                                    onClick = { selectedSwapAvatarRes = R.drawable.ic_avatar_hollywood },
                                    label = { Text("هالیوود", fontSize = 10.sp) }
                                )
                                FilterChip(
                                    selected = selectedSwapAvatarRes == R.drawable.ic_avatar_mannequin,
                                    onClick = { selectedSwapAvatarRes = R.drawable.ic_avatar_mannequin },
                                    label = { Text("مانکن", fontSize = 10.sp) }
                                )
                                FilterChip(
                                    selected = selectedSwapAvatarRes == R.drawable.ic_avatar_cyber,
                                    onClick = { selectedSwapAvatarRes = R.drawable.ic_avatar_cyber },
                                    label = { Text("سایبر", fontSize = 10.sp) }
                                )
                            }
                        }
                    }

                    // 3. Audio, Music & Dubbing Tab
                    if (editorTab == 2) {
                        // Mute original video audio toggle
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(if (isPersian) "قطع صدای اصلی ویدیو (Mute)" else "Mute Original Video Audio", fontSize = 12.sp, color = CharcoalPrimary)
                            Switch(
                                checked = muteOriginalAudio,
                                onCheckedChange = { muteOriginalAudio = it },
                                colors = SwitchDefaults.colors(checkedTrackColor = BrickRed)
                            )
                        }

                        // Background Music Selector
                        Text(
                            text = if (isPersian) "موزیک پس‌زمینه:" else "Background Music:",
                            fontSize = 11.sp,
                            color = CharcoalSecondary
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                            val musicOptions = listOf(
                                if (isPersian) "بدون موزیک" else "None",
                                if (isPersian) "ملایم بوتیک" else "Ambient",
                                if (isPersian) "سینمایی" else "Cinema",
                                if (isPersian) "لو-فای" else "Lo-Fi"
                            )
                            musicOptions.forEachIndexed { i, title ->
                                FilterChip(
                                    selected = selectedMusicIndex == i,
                                    onClick = { selectedMusicIndex = i },
                                    label = { Text(title, fontSize = 9.sp) }
                                )
                            }
                        }

                        // Voice Dubbing / Voiceover Recorder
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (isPersian) "ضبط صدای گفتار و دوبله" else "Record Voiceover Dubbing",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = CharcoalPrimary
                                )
                                Text(
                                    text = if (isVoiceDubbingActive)
                                        (if (isPersian) "در حال ضبط میکروفون روی ویدیو..." else "Recording voice...")
                                    else
                                        (if (isPersian) "صحبت کردن مستقیم روی ویدیو" else "Speak over video"),
                                    fontSize = 10.sp,
                                    color = if (isVoiceDubbingActive) BrickRed else CharcoalSecondary
                                )
                            }
                            IconButton(
                                onClick = {
                                    isVoiceDubbingActive = !isVoiceDubbingActive
                                },
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(if (isVoiceDubbingActive) BrickRed else TerracottaSubtle, CircleShape)
                            ) {
                                Icon(
                                    Icons.Default.Mic,
                                    contentDescription = "Voiceover",
                                    tint = if (isVoiceDubbingActive) Color.White else TerracottaAccent,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        // Denoise and Pitch
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(if (isPersian) "حذف نویز پس‌زمینه صدا" else "Denoise Background Audio", fontSize = 12.sp, color = CharcoalPrimary)
                            Switch(
                                checked = postDenoiseAudio,
                                onCheckedChange = { postDenoiseAudio = it },
                                colors = SwitchDefaults.colors(checkedTrackColor = SageGreen)
                            )
                        }

                        Text(
                            text = if (isPersian) "تغییر تن صدا (Pitch): ${(postPitchFactor * 100).toInt()}%" else "Voice Pitch: ${(postPitchFactor * 100).toInt()}%",
                            fontSize = 11.sp,
                            color = CharcoalSecondary
                        )
                        Slider(
                            value = postPitchFactor,
                            onValueChange = { postPitchFactor = it },
                            valueRange = 0.6f..1.4f,
                            colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent)
                        )
                    }

                    // 4. Subtitle & Caption Tab
                    if (editorTab == 3) {
                        OutlinedTextField(
                            value = subtitleText,
                            onValueChange = { subtitleText = it },
                            label = { Text(if (isPersian) "متن زیرنویس و کپشن ویدیو" else "Subtitle & Caption Text") },
                            placeholder = { Text(if (isPersian) "مثال: حراج ویژه ست لباس زیر نخی اعلا" else "e.g. Special boutique collection") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = TerracottaAccent,
                                unfocusedBorderColor = WarmBorder,
                                focusedLabelColor = TerracottaAccent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Text(
                            text = if (isPersian) "موقعیت زیرنویس روی تصویر:" else "Subtitle Position:",
                            fontSize = 11.sp,
                            color = CharcoalSecondary
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            val posLabels = listOf(
                                if (isPersian) "پایین" else "Bottom",
                                if (isPersian) "وسط" else "Center",
                                if (isPersian) "بالا" else "Top"
                            )
                            posLabels.forEachIndexed { i, title ->
                                FilterChip(
                                    selected = subtitlePosition == i,
                                    onClick = { subtitlePosition = i },
                                    label = { Text(title, fontSize = 10.sp) }
                                )
                            }
                        }

                        Text(
                            text = if (isPersian) "اندازه قلم زیرنویس: ${subtitleFontSize.toInt()} sp" else "Font Size: ${subtitleFontSize.toInt()} sp",
                            fontSize = 11.sp,
                            color = CharcoalSecondary
                        )
                        Slider(
                            value = subtitleFontSize,
                            onValueChange = { subtitleFontSize = it },
                            valueRange = 10f..22f,
                            colors = SliderDefaults.colors(thumbColor = TerracottaAccent, activeTrackColor = TerracottaAccent)
                        )
                    }

                    // 5. Merge Tab
                    if (editorTab == 4) {
                        Text(
                            text = if (isPersian) "انتخاب ویدیوی دوم برای ادغام و ترکیب:" else "Select Second Video to Merge:",
                            fontSize = 11.sp,
                            color = CharcoalSecondary
                        )
                        savedVideos.filter { it.id != currentVideo.id }.forEach { other ->
                            val isChosen = mergeTargetVideo?.id == other.id
                            Surface(
                                color = if (isChosen) TerracottaSubtle else WarmSurfaceSecondary,
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { mergeTargetVideo = other }
                                    .padding(vertical = 2.dp)
                            ) {
                                Text(
                                    text = "🔗 ${other.name}",
                                    fontSize = 11.sp,
                                    color = if (isChosen) TerracottaHover else CharcoalPrimary,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            if (editorTab == 4 && mergeTargetVideo != null) {
                                statusMessage = if (isPersian) "در حال ادغام دو ویدیو..." else "Merging videos..."
                                val merged = storageManager.mergeVideos(currentVideo, mergeTargetVideo!!)
                                if (merged != null) {
                                    savedVideos = storageManager.getSavedVideos()
                                    selectedVideo = merged
                                    statusMessage = if (isPersian) "ویدیوهای انتخابی با موفقیت ادغام شدند ✓" else "Videos merged successfully ✓"
                                }
                            } else {
                                statusMessage = if (isPersian) "در حال اعمال تنظیمات و ذخیره قطعه جدید..." else "Applying audio & edits..."
                                val effectDesc = buildString {
                                    append("Trim: ${trimStartSec.toInt()}s-${trimEndSec.toInt()}s")
                                    if (muteOriginalAudio) append(" • Muted")
                                    if (selectedMusicIndex > 0) append(" • Music")
                                    if (isVoiceDubbingActive) append(" • Dubbed")
                                    if (subtitleText.isNotBlank()) append(" • Subtitle")
                                    if (postBlurActive) append(" • Blur")
                                    if (postFaceSwapActive) append(" • Swap")
                                    if (postDenoiseAudio) append(" • Denoised")
                                }
                                val edited = storageManager.trimAndSaveVideo(
                                    videoItem = currentVideo,
                                    startMs = (trimStartSec * 1000).toLong(),
                                    endMs = (trimEndSec * 1000).toLong(),
                                    effectSummary = effectDesc
                                )
                                if (edited != null) {
                                    savedVideos = storageManager.getSavedVideos()
                                    selectedVideo = edited
                                    statusMessage = if (isPersian) "قطعه جدید تدوین و صداگذاری‌شده در کتابخانه ذخیره شد ✓" else "Clean edited clip with audio saved ✓"
                                }
                            }
                            showEditorDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent)
                ) {
                    Text(if (isPersian) "اعمال و صدور قطعه جدید" else "Apply & Save Clip", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditorDialog = false }) {
                    Text(if (isPersian) "انصراف" else "Cancel", color = CharcoalSecondary)
                }
            }
        )
    }

    // Export Dialog (Zero Metadata Guaranteed)
    if (showExportDialog && selectedVideo != null) {
        val currentVideo = selectedVideo!!
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = {
                Text(
                    text = if (isPersian) "صدور ویدیو با حذف کامل ردپای GPS" else "Export Sanitized Video",
                    color = CharcoalPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = if (isPersian) "فایل با حذف هرگونه متادیتای موقعیت، سریال دستگاه و زمان ضبط صادر می‌شود." else "Strips all GPS tags, device signatures, and metadata.",
                        color = CharcoalSecondary,
                        fontSize = 12.sp
                    )
                    OutlinedTextField(
                        value = exportName,
                        onValueChange = { exportName = it },
                        label = { Text(if (isPersian) "نام فایل خروجی" else "Output Filename") },
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
                            val exported = storageManager.exportVideo(currentVideo, exportName)
                            statusMessage = if (exported != null) {
                                if (isPersian) "ویدیو با موفقیت و بدون متادیتا در گالری ذخیره شد ✓" else "Exported to device storage ✓"
                            } else {
                                if (isPersian) "خطا در ذخیره فایل" else "Export failed"
                            }
                            showExportDialog = false
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent)
                ) {
                    Text(if (isPersian) "ذخیره نهایی" else "Save Clean Copy", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text(if (isPersian) "انصراف" else "Cancel", color = CharcoalSecondary)
                }
            }
        )
    }

    // Video Compressor Dialog (100% Offline & Private)
    if (showCompressDialog && selectedVideo != null) {
        val currentVideo = selectedVideo!!
        val originalSizeMb = String.format("%.1f", currentVideo.sizeBytes / (1024f * 1024f))

        AlertDialog(
            onDismissRequest = { if (!isCompressing) showCompressDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Compress, contentDescription = null, tint = TerracottaAccent)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = if (isPersian) "کمپرسور امن داخل‌برنامه‌ای" else "Private Video Compressor",
                        color = CharcoalPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = if (isPersian)
                            "فشرده‌سازی ۱۰۰٪ محلی روی گوشی بدون نیاز به ابزارهای آنلاین یا غریبه. حجم فعلی: $originalSizeMb مگابایت"
                        else
                            "100% on-device compression with zero external upload. Current size: $originalSizeMb MB",
                        color = CharcoalSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    // Presets
                    com.example.engine.CompressionQualityPreset.values().forEach { preset ->
                        val isSelected = selectedCompressPreset == preset
                        Surface(
                            color = if (isSelected) TerracottaSubtle else WarmSurfaceSecondary,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) TerracottaAccent else WarmBorder
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !isCompressing) {
                                    selectedCompressPreset = preset
                                }
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = if (isPersian) preset.titleFa else preset.titleEn,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isSelected) TerracottaHover else CharcoalPrimary
                                    )
                                    Surface(
                                        color = if (isSelected) TerracottaAccent else WarmBorder,
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = "-${preset.estimatedReductionPercent}٪",
                                            color = if (isSelected) Color.White else CharcoalSecondary,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Spacer(Modifier.height(3.dp))
                                Text(
                                    text = if (isPersian) preset.descriptionFa else preset.descriptionEn,
                                    fontSize = 10.sp,
                                    color = CharcoalTertiary
                                )
                            }
                        }
                    }

                    // Progress Bar during compression
                    if (isCompressing) {
                        Spacer(Modifier.height(4.dp))
                        LinearProgressIndicator(
                            progress = { compressProgress },
                            color = TerracottaAccent,
                            trackColor = WarmBorder,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                        )
                        Text(
                            text = if (isPersian)
                                "در حال فشرده‌سازی: ${(compressProgress * 100).toInt()}٪"
                            else
                                "Compressing: ${(compressProgress * 100).toInt()}%",
                            fontSize = 11.sp,
                            color = TerracottaAccent,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (!isCompressing) {
                            isCompressing = true
                            compressProgress = 0f
                            coroutineScope.launch {
                                val (compressedVideo, result) = storageManager.compressVideo(
                                    videoItem = currentVideo,
                                    preset = selectedCompressPreset,
                                    onProgress = { p -> compressProgress = p }
                                )
                                isCompressing = false
                                showCompressDialog = false
                                if (compressedVideo != null) {
                                    val savedMb = String.format("%.1f", (result.originalSizeBytes - result.compressedSizeBytes) / (1024f * 1024f))
                                    statusMessage = if (isPersian)
                                        "فشرده‌سازی انجام شد: $savedMb مگابایت ذخیره شد (${result.savedPercentage}٪ کاهش حجم) ✓"
                                    else
                                        "Compression complete: saved $savedMb MB (${result.savedPercentage}%) ✓"
                                    reloadVideos()
                                    selectedVideo = compressedVideo
                                } else {
                                    statusMessage = if (isPersian) "خطا در فشرده‌سازی ویدیو" else "Compression failed"
                                }
                            }
                        }
                    },
                    enabled = !isCompressing,
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaAccent)
                ) {
                    Text(
                        text = if (isCompressing) (if (isPersian) "در حال پردازش..." else "Processing...") else (if (isPersian) "شروع فشرده‌سازی" else "Start Compression"),
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                if (!isCompressing) {
                    TextButton(onClick = { showCompressDialog = false }) {
                        Text(if (isPersian) "انصراف" else "Cancel", color = CharcoalSecondary)
                    }
                }
            }
        )
    }

    // Delete confirmation dialog
    if (showDeleteConfirmDialog != null) {
        val toDelete = showDeleteConfirmDialog!!
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = null },
            title = { Text(if (isPersian) "حذف قطعی ویدیو؟" else "Delete Video?", color = CharcoalPrimary) },
            text = { Text(if (isPersian) "این فایل به طور دائم از حافظه دستگاه پاک خواهد شد." else "Permanently deletes this recording from local storage.", color = CharcoalSecondary, fontSize = 12.sp) },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val success = storageManager.deleteVideo(toDelete)
                            if (success) {
                                if (selectedVideo?.id == toDelete.id) {
                                    selectedVideo = null
                                }
                                reloadVideos()
                                statusMessage = if (isPersian) "ویدیو حذف شد" else "Video deleted"
                            }
                            showDeleteConfirmDialog = null
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BrickRed)
                ) {
                    Text(if (isPersian) "حذف" else "Delete", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmDialog = null }) {
                    Text(if (isPersian) "انصراف" else "Cancel", color = CharcoalSecondary)
                }
            }
        )
    }

    // ============================================================
    // Auto-Reel & Instagram Teaser Generator Dialog (100% Offline)
    // ============================================================
    if (showAutoReelDialog && selectedVideo != null) {
        val currentVideo = selectedVideo!!

        AlertDialog(
            onDismissRequest = { if (!isGeneratingReel) showAutoReelDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.MovieFilter, contentDescription = null, tint = Color(0xFFD4AF37), modifier = Modifier.size(22.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = if (isPersian) "تولید تیزر ریلز اینستاگرام (آفلاین)" else "Auto-Reel Generator (Offline)",
                        color = CharcoalPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
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
                    Text(
                        text = if (isPersian)
                            "تولید خودکار تیزر ۱۵ ثانیه‌ای با برش ریتمیک، فیلتر رنگ سینمایی، اسلوموشن و واتر‌مارک سه‌گانه برند."
                        else
                            "Automated 15s social teaser with cinematic LUT, slow-mo cadence, and multi-brand watermark.",
                        fontSize = 11.sp,
                        color = CharcoalSecondary
                    )

                    // Mock 9:16 Reel Teaser Preview Frame
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF141312))
                            .border(1.5.dp, Color(0xFFD4AF37), RoundedCornerShape(12.dp))
                    ) {
                        // LUT tint
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(selectedReelLut.colorOverlayHex))
                        )

                        // Center Reel Badge
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Default.PlayCircle, contentDescription = null, tint = Color(0xFFFFD54F), modifier = Modifier.size(36.dp))
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = "پیش‌نمایش تیزر ${reelDurationSec}s",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Lut: ${selectedReelLut.titleFa} • $reelMusicMood",
                                color = Color(0xFFFFE082),
                                fontSize = 9.sp
                            )
                        }

                        // Top Maison Watermark in preview
                        if (isReelWatermark) {
                            Surface(
                                color = Color.Black.copy(alpha = 0.75f),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = "🏛️ $reelMaisonName",
                                    color = Color(0xFFFFD54F),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }

                            // Bottom Salon & Voucher Watermark in preview
                            Surface(
                                color = Color.Black.copy(alpha = 0.85f),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = "✨ $reelSalonName • 🎟️ کد تخفیف: $reelVoucherCode",
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    // Duration Selection (15s vs 30s)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(if (isPersian) "مدت زمان تیزر:" else "Duration:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = CharcoalPrimary)
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            listOf(15 to "۱۵ ثانیه (ریلز استاندارد)", 30 to "۳۰ ثانیه (استوری کامل)").forEach { (dur, label) ->
                                val isSel = reelDurationSec == dur
                                Surface(
                                    color = if (isSel) Color(0xFFD4AF37) else WarmSurfaceSecondary,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.clickable { reelDurationSec = dur }
                                ) {
                                    Text(
                                        text = label,
                                        fontSize = 10.sp,
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSel) Color.Black else CharcoalPrimary,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }
                    }

                    // LUT Selection Chips
                    Text(if (isPersian) "فیلتر رنگ سینمایی تیزر:" else "Color Grade:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = CharcoalPrimary)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        com.example.model.CinematicLutFilter.values().forEach { lut ->
                            val isSel = selectedReelLut == lut
                            Surface(
                                color = if (isSel) Color(0xFFD4AF37) else WarmSurfaceSecondary,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedReelLut = lut }
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = when (lut) {
                                            com.example.model.CinematicLutFilter.CHAMPAGNE_GOLD -> "شامپاینی"
                                            com.example.model.CinematicLutFilter.VINTAGE_35MM -> "کداک ۳۵"
                                            com.example.model.CinematicLutFilter.ROYAL_EMERALD -> "زمردی"
                                            com.example.model.CinematicLutFilter.PURE_VELVET -> "مخملی"
                                        },
                                        fontSize = 9.sp,
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSel) Color.Black else CharcoalPrimary
                                    )
                                }
                            }
                        }
                    }

                    // Options Checkboxes
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(if (isPersian) "اسلوموشن سینمایی (Cadence 60fps)" else "Slow-Mo Pacing", fontSize = 11.sp, color = CharcoalPrimary)
                        Switch(
                            checked = isReelSlowMo,
                            onCheckedChange = { isReelSlowMo = it },
                            colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFFD4AF37))
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(if (isPersian) "واتر‌مارک سه‌گانه بیزینس و کد تخفیف" else "Triple Brand Watermark", fontSize = 11.sp, color = CharcoalPrimary)
                        Switch(
                            checked = isReelWatermark,
                            onCheckedChange = { isReelWatermark = it },
                            colors = SwitchDefaults.colors(checkedTrackColor = Color(0xFFD4AF37))
                        )
                    }

                    // Music mood
                    Text(if (isPersian) "موسیقی متن آفلاین پیشنهادی:" else "Audio Mood:", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = CharcoalPrimary)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("پیانو رمانتیک مراسم", "هاوس شیک تالار", "آکوستیک لایت مزون").forEach { mood ->
                            val isSel = reelMusicMood == mood
                            Surface(
                                color = if (isSel) Color(0xFFD4AF37) else WarmSurfaceSecondary,
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { reelMusicMood = mood }
                            ) {
                                Text(
                                    text = mood,
                                    fontSize = 9.sp,
                                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSel) Color.Black else CharcoalPrimary,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    // Generation Progress
                    if (isGeneratingReel) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            LinearProgressIndicator(
                                progress = { reelProgress },
                                modifier = Modifier.fillMaxWidth(),
                                color = Color(0xFFD4AF37)
                            )
                            Text(
                                text = if (isPersian) "در حال تولید و تدوین تیزر اینستاگرام... ${(reelProgress * 100).toInt()}%" else "Rendering Instagram Reel... ${(reelProgress * 100).toInt()}%",
                                fontSize = 10.sp,
                                color = CharcoalSecondary
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            isGeneratingReel = true
                            reelProgress = 0.2f
                            kotlinx.coroutines.delay(400)
                            reelProgress = 0.5f
                            kotlinx.coroutines.delay(400)
                            reelProgress = 0.85f
                            kotlinx.coroutines.delay(300)

                            val reelVideo = storageManager.generateInstagramReel(
                                sourceVideo = currentVideo,
                                targetDurationSec = reelDurationSec,
                                lutFilter = selectedReelLut.titleFa,
                                maisonName = reelMaisonName,
                                salonName = reelSalonName,
                                discountCode = reelVoucherCode,
                                musicMood = reelMusicMood
                            )

                            reelProgress = 1.0f
                            isGeneratingReel = false
                            showAutoReelDialog = false

                            if (reelVideo != null) {
                                reloadVideos()
                                selectedVideo = reelVideo
                                statusMessage = if (isPersian)
                                    "تیزر ریلز اینستاگرام با موفقیت تولید و در گالری ذخیره شد ✓"
                                else
                                    "Instagram Reel generated & saved to local gallery ✓"
                            } else {
                                statusMessage = if (isPersian) "خطا در تولید تیزر" else "Failed to generate reel"
                            }
                        }
                    },
                    enabled = !isGeneratingReel,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E1C1A),
                        contentColor = Color(0xFFFFD54F)
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD4AF37))
                ) {
                    Text(
                        text = if (isGeneratingReel) (if (isPersian) "در حال رندر..." else "Rendering...") else (if (isPersian) "تولید و ذخیره تیزر در گالری" else "Generate & Save Reel"),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            },
            dismissButton = {
                if (!isGeneratingReel) {
                    TextButton(onClick = { showAutoReelDialog = false }) {
                        Text(if (isPersian) "انصراف" else "Cancel", color = CharcoalSecondary)
                    }
                }
            }
        )
    }
}
