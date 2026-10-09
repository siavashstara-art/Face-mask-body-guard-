package com.example.engine

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.camera.video.*
import androidx.core.content.ContextCompat
import com.example.model.PerformancePreset
import java.io.File

class RecordingEngine(
    private val context: Context,
    private val storageManager: StorageManager
) {

    private var activeRecording: Recording? = null
    var isRecording: Boolean = false
        private set

    var isPaused: Boolean = false
        private set

    var recordingDurationSeconds: Long = 0L
        private set

    private var videoCapture: VideoCapture<Recorder>? = null

    fun buildVideoCapture(preset: PerformancePreset = PerformancePreset.BALANCED): VideoCapture<Recorder> {
        val targetQuality = when (preset) {
            PerformancePreset.HIGH -> Quality.FHD
            PerformancePreset.LOW_REDMI -> Quality.SD
            else -> Quality.HD
        }

        // Ordered fallback list to ensure support across all hardware (Qualcomm, MediaTek, Exynos)
        val qualityList = listOf(targetQuality, Quality.HD, Quality.SD, Quality.LOWEST).distinct()
        val qualitySelector = QualitySelector.fromOrderedList(
            qualityList,
            FallbackStrategy.lowerQualityOrHigherThan(Quality.SD)
        )

        val recorder = Recorder.Builder()
            .setQualitySelector(qualitySelector)
            .build()

        val capture = VideoCapture.withOutput(recorder)
        this.videoCapture = capture
        return capture
    }

    @SuppressLint("MissingPermission")
    fun startRecording(
        outputFile: File = storageManager.createNewVideoFile(),
        enableAudio: Boolean = true,
        onEvent: (VideoRecordEvent) -> Unit
    ): File {
        // If already recording, stop first before starting new one
        if (isRecording || activeRecording != null) {
            stopRecording()
        }

        val capture = videoCapture ?: buildVideoCapture()
        outputFile.parentFile?.mkdirs()

        val outputOptions = FileOutputOptions.Builder(outputFile).build()

        // Check if RECORD_AUDIO permission is genuinely granted at runtime
        val hasAudioPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED

        val shouldEnableAudio = enableAudio && hasAudioPermission

        // Attempt 1: Start with audio if enabled & permitted
        var startedSuccessfully = false
        if (shouldEnableAudio) {
            try {
                val pending = capture.output.prepareRecording(context, outputOptions).withAudioEnabled()
                activeRecording = pending.start(ContextCompat.getMainExecutor(context)) { event ->
                    handleRecordEvent(event, onEvent)
                }
                startedSuccessfully = true
            } catch (_: Throwable) {
                // If microphone is busy, locked by another app, or audio initialization fails,
                // fall back immediately to video-only recording without crashing the app!
                startedSuccessfully = false
            }
        }

        // Attempt 2: Fallback to video-only recording
        if (!startedSuccessfully) {
            try {
                val pendingNoAudio = capture.output.prepareRecording(context, outputOptions)
                activeRecording = pendingNoAudio.start(ContextCompat.getMainExecutor(context)) { event ->
                    handleRecordEvent(event, onEvent)
                }
                startedSuccessfully = true
            } catch (t: Throwable) {
                isRecording = false
                isPaused = false
                activeRecording = null
                throw t
            }
        }

        return outputFile
    }

    private fun handleRecordEvent(event: VideoRecordEvent, onEvent: (VideoRecordEvent) -> Unit) {
        when (event) {
            is VideoRecordEvent.Start -> {
                isRecording = true
                isPaused = false
            }
            is VideoRecordEvent.Pause -> {
                isPaused = true
            }
            is VideoRecordEvent.Resume -> {
                isPaused = false
            }
            is VideoRecordEvent.Status -> {
                recordingDurationSeconds = event.recordingStats.recordedDurationNanos / 1_000_000_000L
            }
            is VideoRecordEvent.Finalize -> {
                isRecording = false
                isPaused = false
                recordingDurationSeconds = 0L
                activeRecording = null
            }
        }
        onEvent(event)
    }

    fun pauseRecording() {
        try {
            if (isRecording && !isPaused) {
                activeRecording?.pause()
            }
        } catch (_: Throwable) {}
    }

    fun resumeRecording() {
        try {
            if (isRecording && isPaused) {
                activeRecording?.resume()
            }
        } catch (_: Throwable) {}
    }

    fun stopRecording() {
        try {
            activeRecording?.stop()
        } catch (_: Throwable) {}
        activeRecording = null
        isRecording = false
        isPaused = false
        recordingDurationSeconds = 0L
    }
}
