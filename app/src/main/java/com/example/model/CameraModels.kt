package com.example.model

enum class PerformancePreset(
    val labelEn: String,
    val labelFa: String,
    val resolutionWidth: Int,
    val resolutionHeight: Int,
    val fps: Int,
    val recommendedForRedmi: Boolean
) {
    LOW_REDMI("Redmi Guard (720p/24fps)", "حالت بهینه ردمی (720p/24fps)", 1280, 720, 24, true),
    BALANCED("Balanced (720p/30fps)", "متعادل (720p/30fps)", 1280, 720, 30, false),
    HIGH("High Quality (1080p/30fps)", "کیفیت بالا (1080p/30fps)", 1920, 1080, 30, false)
}

enum class ActiveStudioTab {
    NONE,
    PRIVACY,
    BACKGROUND,
    STYLE,
    BODY,
    VOICE,
    SWAP,
    STORY_STREAM,
    CATALOG,
    SETTINGS
}

enum class StreamPlatform(val displayName: String, val defaultRtmpServer: String) {
    YOUTUBE("YouTube Live", "rtmp://a.rtmp.youtube.com/live2"),
    DISCORD("Discord / Virtual Stream", "rtmp://localhost:1935/live"),
    TELEGRAM("Telegram Live", "rtmp://live.telegram.org/stream/"),
    CUSTOM_RTMP("Custom RTMP Server", "")
}

data class StoryStreamConfig(
    val showStoryGuide916: Boolean = false,
    val storyHeadline: String = "",
    val storySubtext: String = "",
    val selectedPlatform: StreamPlatform = StreamPlatform.YOUTUBE,
    val rtmpUrl: String = "rtmp://a.rtmp.youtube.com/live2",
    val streamKey: String = "",
    val isLiveStreaming: Boolean = false,
    val streamBitrateKbps: Int = 2500
)

data class ProductCatalogConfig(
    val productName: String = "شورت تک نخی اعلا",
    val price: String = "۹۵,۰۰۰ تومان",
    val sizes: String = "M / L / XL",
    val fabricType: String = "۱۰۰٪ پنبه ضد حساسیت",
    val telegramChannel: String = "@my_boutique_shop",
    val showPriceBadge: Boolean = true,
    val showMannequinGuide: Boolean = true
)

data class VideoItem(
    val id: String,
    val uri: String,
    val absolutePath: String,
    val name: String,
    val durationMs: Long,
    val sizeBytes: Long,
    val timestamp: Long,
    val appliedPrivacySummary: String,
    val isProcessed: Boolean = false
)
