package com.example.viewmodel

import android.app.Application
import android.graphics.RectF
import androidx.camera.core.CameraSelector
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.engine.FaceDetectionEngine
import com.example.engine.RecordingEngine
import com.example.engine.StorageManager
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FaceGuardViewModel(application: Application) : AndroidViewModel(application) {

    val storageManager = StorageManager(application)
    val recordingEngine = RecordingEngine(application, storageManager)
    val faceDetectionEngine = FaceDetectionEngine()

    // Config states
    private val _privacyConfig = MutableStateFlow(PrivacyConfig())
    val privacyConfig: StateFlow<PrivacyConfig> = _privacyConfig.asStateFlow()

    private val _backgroundConfig = MutableStateFlow(BackgroundConfig())
    val backgroundConfig: StateFlow<BackgroundConfig> = _backgroundConfig.asStateFlow()

    private val _faceStyleConfig = MutableStateFlow(FaceStyleConfig())
    val faceStyleConfig: StateFlow<FaceStyleConfig> = _faceStyleConfig.asStateFlow()

    private val _silhouetteConfig = MutableStateFlow(BodySilhouetteConfig())
    val silhouetteConfig: StateFlow<BodySilhouetteConfig> = _silhouetteConfig.asStateFlow()

    private val _voiceConfig = MutableStateFlow(VoiceConfig())
    val voiceConfig: StateFlow<VoiceConfig> = _voiceConfig.asStateFlow()

    private val _performancePreset = MutableStateFlow(PerformancePreset.LOW_REDMI)
    val performancePreset: StateFlow<PerformancePreset> = _performancePreset.asStateFlow()

    private val _storyStreamConfig = MutableStateFlow(StoryStreamConfig())
    val storyStreamConfig: StateFlow<StoryStreamConfig> = _storyStreamConfig.asStateFlow()

    private val _catalogConfig = MutableStateFlow(ProductCatalogConfig())
    val catalogConfig: StateFlow<ProductCatalogConfig> = _catalogConfig.asStateFlow()

    private val _swapConfig = MutableStateFlow(SwapConfig())
    val swapConfig: StateFlow<SwapConfig> = _swapConfig.asStateFlow()

    private val _activeTab = MutableStateFlow(ActiveStudioTab.NONE)
    val activeTab: StateFlow<ActiveStudioTab> = _activeTab.asStateFlow()

    private val _trackedFaces = MutableStateFlow<List<TrackedFace>>(faceDetectionEngine.getDefaultCenterFace())
    val trackedFaces: StateFlow<List<TrackedFace>> = _trackedFaces.asStateFlow()

    private val _cameraSelector = MutableStateFlow(CameraSelector.DEFAULT_FRONT_CAMERA)
    val cameraSelector: StateFlow<CameraSelector> = _cameraSelector.asStateFlow()

    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()

    private val _recordingDurationSec = MutableStateFlow(0L)
    val recordingDurationSec: StateFlow<Long> = _recordingDurationSec.asStateFlow()

    private val _isPersian = MutableStateFlow(true) // Persian RTL default as requested by user
    val isPersian: StateFlow<Boolean> = _isPersian.asStateFlow()

    private val _showPrivacyAudit = MutableStateFlow(false)
    val showPrivacyAudit: StateFlow<Boolean> = _showPrivacyAudit.asStateFlow()

    private val _currentScreen = MutableStateFlow("camera") // "camera" or "gallery"
    val currentScreen: StateFlow<String> = _currentScreen.asStateFlow()

    // Status snackbar/toast
    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    fun updatePrivacyConfig(config: PrivacyConfig) {
        _privacyConfig.value = config
    }

    fun updateBackgroundConfig(config: BackgroundConfig) {
        _backgroundConfig.value = config
    }

    fun updateFaceStyleConfig(config: FaceStyleConfig) {
        _faceStyleConfig.value = config
    }

    fun updateSilhouetteConfig(config: BodySilhouetteConfig) {
        _silhouetteConfig.value = config
    }

    fun updateVoiceConfig(config: VoiceConfig) {
        _voiceConfig.value = config
    }

    fun setPerformancePreset(preset: PerformancePreset) {
        _performancePreset.value = preset
    }

    fun updateStoryStreamConfig(config: StoryStreamConfig) {
        _storyStreamConfig.value = config
    }

    fun updateCatalogConfig(config: ProductCatalogConfig) {
        _catalogConfig.value = config
    }

    fun updateSwapConfig(config: SwapConfig) {
        _swapConfig.value = config
    }

    fun toggleLiveStream() {
        val current = _storyStreamConfig.value
        val newState = !current.isLiveStreaming
        _storyStreamConfig.value = current.copy(isLiveStreaming = newState)
        _statusMessage.value = if (newState) {
            if (_isPersian.value) "اتصال پخش زنده به ${current.selectedPlatform.displayName} برقرار شد" else "Connected to ${current.selectedPlatform.displayName}"
        } else {
            if (_isPersian.value) "پخش زنده متوقف شد" else "Live stream ended"
        }
    }

    fun setActiveTab(tab: ActiveStudioTab) {
        _activeTab.value = if (_activeTab.value == tab) ActiveStudioTab.NONE else tab
    }

    fun toggleCameraFacing() {
        _cameraSelector.value = if (_cameraSelector.value == CameraSelector.DEFAULT_FRONT_CAMERA) {
            CameraSelector.DEFAULT_BACK_CAMERA
        } else {
            CameraSelector.DEFAULT_FRONT_CAMERA
        }
        faceDetectionEngine.reset()
    }

    fun toggleLanguage() {
        _isPersian.value = !_isPersian.value
    }

    fun setShowPrivacyAudit(show: Boolean) {
        _showPrivacyAudit.value = show
    }

    fun navigateTo(screen: String) {
        _currentScreen.value = screen
    }

    fun onFacesDetected(faces: List<TrackedFace>) {
        if (faces.isNotEmpty()) {
            _trackedFaces.value = faces
        } else {
            // Keep default center anchor if tracking momentarily loses face
            if (_trackedFaces.value.isEmpty()) {
                _trackedFaces.value = faceDetectionEngine.getDefaultCenterFace()
            }
        }
    }

    fun addManualPrivacyZone(tapOffset: Offset, canvasWidth: Float, canvasHeight: Float) {
        if (canvasWidth <= 0 || canvasHeight <= 0) return
        val normX = (tapOffset.x / canvasWidth).coerceIn(0.1f, 0.9f)
        val normY = (tapOffset.y / canvasHeight).coerceIn(0.1f, 0.9f)
        val boxW = 0.2f
        val boxH = 0.15f
        val rect = RectF(
            (normX - boxW / 2).coerceAtLeast(0f),
            (normY - boxH / 2).coerceAtLeast(0f),
            (normX + boxW / 2).coerceAtMost(1f),
            (normY + boxH / 2).coerceAtMost(1f)
        )
        val newZone = ManualPrivacyZone(bounds = rect, blurType = _privacyConfig.value.blurType)
        _privacyConfig.value = _privacyConfig.value.copy(
            manualZones = _privacyConfig.value.manualZones + newZone
        )
    }

    fun clearManualZones() {
        _privacyConfig.value = _privacyConfig.value.copy(manualZones = emptyList())
    }

    fun toggleRecording(onComplete: (Boolean) -> Unit = {}) {
        if (_isRecording.value) {
            recordingEngine.stopRecording()
            _isRecording.value = false
            _recordingDurationSec.value = 0L
            _statusMessage.value = if (_isPersian.value) "ویدیو در گالری ذخیره شد" else "Video saved to local gallery"
            onComplete(false)
        } else {
            try {
                recordingEngine.startRecording(
                    enableAudio = true,
                    onEvent = { event ->
                        _isRecording.value = recordingEngine.isRecording
                        _recordingDurationSec.value = recordingEngine.recordingDurationSeconds
                    }
                )
                _isRecording.value = true
                onComplete(true)
            } catch (e: Exception) {
                _statusMessage.value = if (_isPersian.value) "خطا در شروع ضبط" else "Failed to start recording"
            }
        }
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }
}
