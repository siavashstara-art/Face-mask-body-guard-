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
    val faceCardRepo = com.example.engine.FaceCardRepository(application)
    val ecosystemController = com.example.engine.EcosystemMasterController(application)
    val maisonRepo = com.example.engine.MaisonBoutiqueRepository(application)

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

    private val _currentAppMode = MutableStateFlow(AppModePreset.STANDARD)
    val currentAppMode: StateFlow<AppModePreset> = _currentAppMode.asStateFlow()

    private val _watermarkConfig = MutableStateFlow(WatermarkConfig())
    val watermarkConfig: StateFlow<WatermarkConfig> = _watermarkConfig.asStateFlow()

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

    private val _appLanguage = MutableStateFlow(AppLanguage.PERSIAN)
    val appLanguage: StateFlow<AppLanguage> = _appLanguage.asStateFlow()

    private val _dualCameraConfig = MutableStateFlow(DualCameraConfig())
    val dualCameraConfig: StateFlow<DualCameraConfig> = _dualCameraConfig.asStateFlow()

    private val _screenRecordConfig = MutableStateFlow(ScreenRecordModeConfig())
    val screenRecordConfig: StateFlow<ScreenRecordModeConfig> = _screenRecordConfig.asStateFlow()

    private val _isSimpleMode = MutableStateFlow(false)
    val isSimpleMode: StateFlow<Boolean> = _isSimpleMode.asStateFlow()

    private val _showPrivacyAudit = MutableStateFlow(false)
    val showPrivacyAudit: StateFlow<Boolean> = _showPrivacyAudit.asStateFlow()

    // Wedding Day Stress Relief & Light Simulator states
    private val _selectedLightTime = MutableStateFlow(WeddingDayLightTime.NATURAL_GARDEN_DAY)
    val selectedLightTime: StateFlow<WeddingDayLightTime> = _selectedLightTime.asStateFlow()

    private val _selectedPoseGuide = MutableStateFlow(ConfidencePoseGuideType.GRAND_ENTRY)
    val selectedPoseGuide: StateFlow<ConfidencePoseGuideType> = _selectedPoseGuide.asStateFlow()

    private val _isPoseGuideVisible = MutableStateFlow(false)
    val isPoseGuideVisible: StateFlow<Boolean> = _isPoseGuideVisible.asStateFlow()

    fun updateLightTime(time: WeddingDayLightTime) {
        _selectedLightTime.value = time
    }

    fun updatePoseGuide(pose: ConfidencePoseGuideType) {
        _selectedPoseGuide.value = pose
    }

    fun togglePoseGuide(visible: Boolean) {
        _isPoseGuideVisible.value = visible
    }

    private val _currentScreen = MutableStateFlow("facecard") // "facecard", "camera", "gallery"
    val currentScreen: StateFlow<String> = _currentScreen.asStateFlow()

    private val _faceCardTab = MutableStateFlow("cards") // "cards", "loyalty", "ecosystem"
    val faceCardTab: StateFlow<String> = _faceCardTab.asStateFlow()

    fun selectFaceCardTab(tab: String) {
        _faceCardTab.value = tab
    }

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

    fun updateWatermarkConfig(config: WatermarkConfig) {
        _watermarkConfig.value = config
    }

    fun applyAppModePreset(preset: AppModePreset) {
        _currentAppMode.value = preset
        when (preset) {
            AppModePreset.TRIPLE_AI_SHIELD -> {
                // Activate ALL THREE simultaneously:
                // 1. Face Detection active
                // 2. Blur & Privacy Mask active
                // 3. Face Swap & Body Silhouette active
                _privacyConfig.value = _privacyConfig.value.copy(
                    autoFaceTracking = true,
                    blurType = BlurType.GAUSSIAN,
                    blurIntensity = 0.85f,
                    maskType = PrivacyMaskType.EYES_VISOR,
                    safetyMarginMultiplier = 1.40f
                )
                _swapConfig.value = _swapConfig.value.copy(
                    faceSwapEnabled = true,
                    selectedAvatarId = "glamour",
                    faceBlendAlpha = 0.95f,
                    bodySwapEnabled = true,
                    selectedBodyId = "hourglass",
                    tripleShieldEnforced = true
                )
                _silhouetteConfig.value = _silhouetteConfig.value.copy(
                    enabled = true,
                    waistContour = -0.15f
                )
                _backgroundConfig.value = _backgroundConfig.value.copy(
                    mode = BackgroundMode.BLUR
                )
                _statusMessage.value = if (_isPersian.value)
                    "سپر سه‌گانه هوشمند فعال شد (ردیاب زنده + تاری چهره + سواپ مدل)"
                else
                    "Triple Protection active (Face Detection + Blur + Face Swap)"
            }
            AppModePreset.BOUTIQUE_SELLER -> {
                _privacyConfig.value = _privacyConfig.value.copy(
                    autoFaceTracking = true,
                    maskType = PrivacyMaskType.EYES_VISOR,
                    safetyMarginMultiplier = 1.35f
                )
                _backgroundConfig.value = _backgroundConfig.value.copy(
                    mode = BackgroundMode.BLUR
                )
                _catalogConfig.value = _catalogConfig.value.copy(
                    showPriceBadge = true
                )
                _watermarkConfig.value = _watermarkConfig.value.copy(
                    enabled = true
                )
                _statusMessage.value = if (_isPersian.value)
                    "حالت بوتیک و مدلینگ فعال شد (چهره و اتاق پنهان شدند)"
                else
                    "Boutique Seller mode applied (Face & room anonymized)"
            }
            AppModePreset.ANONYMOUS_REPORTER -> {
                _privacyConfig.value = _privacyConfig.value.copy(
                    autoFaceTracking = true,
                    blurType = BlurType.PIXELATE,
                    pixelBlockSize = 26,
                    maskType = PrivacyMaskType.FULL_SHIELD,
                    safetyMarginMultiplier = 1.5f
                )
                _backgroundConfig.value = _backgroundConfig.value.copy(
                    mode = BackgroundMode.BLUR
                )
                _voiceConfig.value = _voiceConfig.value.copy(
                    pitchProfile = PitchProfile.DEEP_GUARD,
                    noiseReduction = true
                )
                _statusMessage.value = if (_isPersian.value)
                    "حالت گزارشگر امن فعال شد (تاری کامل + صدای بم + حذف ردپای GPS)"
                else
                    "Secure Reporter mode applied (Mosaic + Deep voice + Zero metadata)"
            }
            AppModePreset.FACELESS_VLOG -> {
                _storyStreamConfig.value = _storyStreamConfig.value.copy(
                    showStoryGuide916 = true
                )
                _swapConfig.value = _swapConfig.value.copy(
                    faceSwapEnabled = true,
                    selectedAvatarId = "hollywood"
                )
                _statusMessage.value = if (_isPersian.value)
                    "حالت استوری و ولاگ مستعار فعال شد"
                else
                    "Faceless Vlog & Story mode applied"
            }
            AppModePreset.INTERVIEW_DOC -> {
                _performancePreset.value = PerformancePreset.LOW_REDMI
                _voiceConfig.value = _voiceConfig.value.copy(
                    pitchProfile = PitchProfile.NATURAL,
                    micGain = 1.35f,
                    noiseReduction = true
                )
                _faceStyleConfig.value = _faceStyleConfig.value.copy(
                    preset = FilterPreset.SMOOTH_WARM
                )
                _statusMessage.value = if (_isPersian.value)
                    "حالت فیلمبرداری مصاحبه‌ای فعال شد (۲۴ فریم + کادربندی گفتگو)"
                else
                    "Documentary Interview mode applied (24fps + Dialogue Focus)"
            }
            AppModePreset.CINEMATIC_SHORT -> {
                _performancePreset.value = PerformancePreset.LOW_REDMI
                _faceStyleConfig.value = _faceStyleConfig.value.copy(
                    preset = FilterPreset.VINTAGE_SEPIA,
                    contrast = 0.35f,
                    warmth = 0.15f
                )
                _statusMessage.value = if (_isPersian.value)
                    "حالت فیلم کوتاه سینمایی فعال شد (کادر عریض ۲۱:۹ + اصلاح رنگ سینمایی)"
                else
                    "Cinematic Short Film mode applied (21:9 Anamorphic + Cinema LUT)"
            }
            AppModePreset.STANDARD -> {
                _statusMessage.value = if (_isPersian.value)
                    "حالت استاندارد استودیو بازگردانده شد"
                else
                    "Standard studio mode restored"
            }
        }
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
        cycleLanguage()
    }

    fun cycleLanguage() {
        val next = when (_appLanguage.value) {
            AppLanguage.PERSIAN -> AppLanguage.ARABIC
            AppLanguage.ARABIC -> AppLanguage.ENGLISH
            AppLanguage.ENGLISH -> AppLanguage.PERSIAN
        }
        setLanguage(next)
    }

    fun setLanguage(lang: AppLanguage) {
        _appLanguage.value = lang
        _isPersian.value = (lang == AppLanguage.PERSIAN)
        _statusMessage.value = when (lang) {
            AppLanguage.PERSIAN -> "زبان برنامه: فارسی"
            AppLanguage.ARABIC -> "تم تغيير لغة التطبيق إلى العربية"
            AppLanguage.ENGLISH -> "Language changed to English"
        }
    }

    fun toggleDualCamera() {
        val current = _dualCameraConfig.value
        val newState = !current.enabled
        _dualCameraConfig.value = current.copy(enabled = newState)
        _statusMessage.value = when (_appLanguage.value) {
            AppLanguage.PERSIAN -> if (newState) "حالت دو دوربینه همزمان (PiP) فعال شد" else "حالت دو دوربینه غیرفعال شد"
            AppLanguage.ARABIC -> if (newState) "تم تفعيل الكاميرا المزدوجة المتزامنة (صورة داخل صورة)" else "تم إيقاف الكاميرا المزدوجة"
            AppLanguage.ENGLISH -> if (newState) "Dual-Camera PiP enabled" else "Dual-Camera disabled"
        }
    }

    fun updateDualCameraConfig(config: DualCameraConfig) {
        _dualCameraConfig.value = config
    }

    fun toggleScreenRecording() {
        val current = _screenRecordConfig.value
        val newState = !current.isRecordingScreen
        _screenRecordConfig.value = current.copy(isRecordingScreen = newState)
        _statusMessage.value = when (_appLanguage.value) {
            AppLanguage.PERSIAN -> if (newState) "ضبط صفحه با فیلتر صدا آغاز شد" else "ضبط صفحه ذخیره و متوقف شد"
            AppLanguage.ARABIC -> if (newState) "بدأ تسجيل الشاشة مع تغيير نبرة الصوت" else "تم حفظ تسجيل الشاشة وإيقافه"
            AppLanguage.ENGLISH -> if (newState) "Screen recording with Voice Changer started" else "Screen recording saved"
        }
    }

    fun updateScreenRecordConfig(config: ScreenRecordModeConfig) {
        _screenRecordConfig.value = config
    }

    fun toggleSimpleMode() {
        val newState = !_isSimpleMode.value
        _isSimpleMode.value = newState
        _statusMessage.value = when (_appLanguage.value) {
            AppLanguage.PERSIAN -> if (newState) "حالت فوق‌العاده ساده و روان (ویژه ردمی نوت ۸) فعال شد" else "حالت استودیو پیشرفته فعال شد"
            AppLanguage.ARABIC -> if (newState) "تم تفعيل الوضع البسيط والسلس (خاص بـ Redmi Note 8)" else "تم تفعيل وضع الاستوديو المتقدم"
            AppLanguage.ENGLISH -> if (newState) "Ultra-Simple Mode enabled for Redmi Note 8" else "Advanced Studio Mode enabled"
        }
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

    fun toggleMuteVideo() {
        val newMuted = !_voiceConfig.value.isMuted
        _voiceConfig.value = _voiceConfig.value.copy(isMuted = newMuted)
        _statusMessage.value = if (_isPersian.value) {
            if (newMuted) "صدای فیلمبرداری قطع شد (بی‌صدا / Mute)" else "صدای فیلمبرداری فعال شد"
        } else {
            if (newMuted) "Video recording muted (Silent)" else "Video recording unmuted"
        }
    }

    fun toggleRecording(onComplete: (Boolean) -> Unit = {}) {
        if (_isRecording.value) {
            try {
                recordingEngine.stopRecording()
            } catch (_: Throwable) {}
            _isRecording.value = false
            _recordingDurationSec.value = 0L
            _statusMessage.value = if (_isPersian.value) "ویدیو با موفقیت در گالری ذخیره شد" else "Video saved to local gallery"
            onComplete(false)
        } else {
            try {
                val shouldRecordAudio = !_voiceConfig.value.isMuted
                recordingEngine.startRecording(
                    enableAudio = shouldRecordAudio,
                    onEvent = { event ->
                        when (event) {
                            is androidx.camera.video.VideoRecordEvent.Start -> {
                                _isRecording.value = true
                                _statusMessage.value = if (_isPersian.value) "فیلمبرداری امن آغاز شد" else "Private recording started"
                            }
                            is androidx.camera.video.VideoRecordEvent.Finalize -> {
                                _isRecording.value = false
                                _recordingDurationSec.value = 0L
                                if (event.hasError()) {
                                    _statusMessage.value = if (_isPersian.value) "ضبط متوقف شد" else "Recording stopped"
                                } else {
                                    _statusMessage.value = if (_isPersian.value) "ویدیو با موفقیت ذخیره شد" else "Video saved to gallery"
                                }
                            }
                            is androidx.camera.video.VideoRecordEvent.Status -> {
                                _recordingDurationSec.value = recordingEngine.recordingDurationSeconds
                            }
                        }
                    }
                )
                _isRecording.value = true
                onComplete(true)
            } catch (t: Throwable) {
                _isRecording.value = false
                _statusMessage.value = if (_isPersian.value) "خطا در شروع ضبط" else "Failed to start recording"
            }
        }
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }
}
