package com.example.engine

import android.annotation.SuppressLint
import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
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

    fun buildVideoCapture(preset: PerformancePreset): VideoCapture<Recorder> {
        val quality = when (preset) {
            PerformancePreset.HIGH -> Quality.FHD
            else -> Quality.HD // 720p optimal for Redmi Note 8
        }
        val fallbackStrategy = androidx.camera.video.FallbackStrategy.lowerQualityOrHigherThan(Quality.SD)
        val recorder = Recorder.Builder()
            .setQualitySelector(QualitySelector.from(quality, fallbackStrategy))
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
        val capture = videoCapture ?: throw IllegalStateException("VideoCapture is not initialized")
        val outputOptions = FileOutputOptions.Builder(outputFile).build()

        var pending = capture.output.prepareRecording(context, outputOptions)
        if (enableAudio) {
            try {
                pending = pending.withAudioEnabled()
            } catch (_: SecurityException) {
                // If audio permission is not granted, gracefully proceed with video only
            }
        }

        activeRecording = pending.start(ContextCompat.getMainExecutor(context)) { event ->
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
                }
            }
            onEvent(event)
        }

        return outputFile
    }

    fun pauseRecording() {
        if (isRecording && !isPaused) {
            activeRecording?.pause()
        }
    }

    fun resumeRecording() {
        if (isRecording && isPaused) {
            activeRecording?.resume()
        }
    }

    fun stopRecording() {
        activeRecording?.stop()
        activeRecording = null
        isRecording = false
        isPaused = false
        recordingDurationSeconds = 0L
    }
}
