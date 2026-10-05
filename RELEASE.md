# FaceGuard Studio — Release & Deployment Guide

> **Application Name:** FaceGuard Studio — Private Offline Camera  
> **Package ID (`applicationId`):** `com.tavana.faceguard`  
> **Target Device Architecture:** Universal Android (ARM64 / ARMv7 / x86_64), optimized for Xiaomi Redmi Note 8 (Android 11 / MIUI)  
> **Minimum SDK:** Android 7.0 (API Level 24)  
> **Target SDK:** Android 16 (API Level 36)  
> **Privacy Guarantee:** 100% On-Device Processing • Zero Cloud Telemetry • No Internet Permission

---

## 1. Keystore Generation

To publish FaceGuard Studio on the Google Play Store or distribute signed production APKs, you must generate a cryptographic signing key using the standard Java `keytool` utility.

### Generating the Upload Keystore

Run the following command in your terminal (Linux, macOS, or Windows Git Bash / WSL):

```bash
keytool -genkey -v \
  -keystore my-upload-key.jks \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -alias upload
```

### Parameter Details:
- `-keystore my-upload-key.jks`: File path and name for the generated keystore.
- `-keyalg RSA`: Encryption algorithm standard accepted by Google Play.
- `-keysize 2048`: Key bit strength (2048-bit minimum required by Play Console).
- `-validity 10000`: Valid for ~27 years (Google Play requires validity past October 22, 2033).
- `-alias upload`: The entry alias name matching `app/build.gradle.kts`.

> **Security Warning:**  
> Never commit `my-upload-key.jks` or passwords to version control. Ensure `*.jks` and `*.keystore` are included in your `.gitignore` file. Always maintain an encrypted backup of this keystore; losing it prevents publishing future updates for the same package ID.

---

## 2. Environment Variables Configuration

The project's `app/build.gradle.kts` uses environment variables to configure release signing securely without storing hard-coded credentials in the repository:

```kotlin
signingConfigs {
  create("release") {
    val keystorePath = System.getenv("KEYSTORE_PATH") ?: "${rootDir}/my-upload-key.jks"
    storeFile = file(keystorePath)
    storePassword = System.getenv("STORE_PASSWORD")
    keyAlias = "upload"
    keyPassword = System.getenv("KEY_PASSWORD")
  }
}
```

### Setting Environment Variables Locally

#### Linux & macOS (Bash / Zsh):
```bash
export KEYSTORE_PATH="/absolute/path/to/my-upload-key.jks"
export STORE_PASSWORD="your_keystore_password"
export KEY_PASSWORD="your_key_password"
```

#### Windows (Command Prompt):
```cmd
set KEYSTORE_PATH=C:\path\to\my-upload-key.jks
set STORE_PASSWORD=your_keystore_password
set KEY_PASSWORD=your_key_password
```

#### Windows (PowerShell):
```powershell
$env:KEYSTORE_PATH="C:\path\to\my-upload-key.jks"
$env:STORE_PASSWORD="your_keystore_password"
$env:KEY_PASSWORD="your_key_password"
```

---

## 3. Versioning Updates in `app/build.gradle.kts`

Before creating a new release or uploading an update to the Google Play Console, increment the version parameters in `app/build.gradle.kts`:

```kotlin
defaultConfig {
  applicationId = "com.tavana.faceguard"
  minSdk = 24
  targetSdk = 36
  versionCode = 1        // Increment by 1 with every single Play Store upload (e.g. 1 -> 2 -> 3)
  versionName = "1.0.0"  // User-facing semantic version string (e.g. "1.0.0" -> "1.0.1")
  
  testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}
```

### Versioning Rules:
- **`versionCode`:** A positive integer. Google Play requires each uploaded build artifact to have a strictly higher `versionCode` than the previous release.
- **`versionName`:** Standard Semantic Versioning (`MAJOR.MINOR.PATCH`). Displayed in Google Play and in app settings.

---

## 4. Executing Build Commands

You can build production and testing artifacts using the Gradle build system.

### A. Build Release Android App Bundle (AAB) — For Google Play Store
The Android App Bundle is the required format for publishing on Google Play. It optimizes download size per device architecture.

```bash
# Using standard Gradle
gradle :app:bundleRelease

# Or using the Gradle wrapper
./gradlew :app:bundleRelease
```

- **Output Artifact Location:**  
  `app/build/outputs/bundle/release/app-release.aab`

---

### B. Build Release APK — For Direct Testing & Sideloading
If you need a signed `.apk` file to install directly onto a physical test device (e.g. Xiaomi Redmi Note 8):

```bash
# Using standard Gradle
gradle :app:assembleRelease

# Or using the Gradle wrapper
./gradlew :app:assembleRelease
```

- **Output Artifact Location:**  
  `app/build/outputs/apk/release/app-release.apk`

---

### C. Build Debug APK — For Rapid Development
To generate a debug APK signed with the default debug keystore:

```bash
gradle :app:assembleDebug
# Or
./gradlew :app:assembleDebug
```

- **Output Artifact Location:**  
  `app/build/outputs/apk/debug/app-debug.apk`

---

## 5. Automated GitHub Actions CI/CD Workflow

A continuous integration workflow is configured at `.github/workflows/build-apk.yml`.

### Configuring GitHub Secrets for Signed Releases:
If you want GitHub Actions to sign your releases automatically, add the following secrets under **Repository Settings → Secrets and variables → Actions**:
1. `KEYSTORE_BASE64`: Base64-encoded string of your `my-upload-key.jks` (`base64 -w 0 my-upload-key.jks`).
2. `STORE_PASSWORD`: Your keystore password.
3. `KEY_PASSWORD`: Your private key password.

When configured, every push to `main` generates and uploads production artifacts directly under the GitHub Actions Run summary.

---

## 6. Google Play Store Submission Checklist

1. **Target API Level:** Complies with Android 14/15/16 (API 34-36) requirement.
2. **Zero Internet Permissions:** Confirm `AndroidManifest.xml` does **not** include `android.permission.INTERNET`. This provides an airtight privacy audit badge.
3. **Hardware Features:** `CAMERA` and `RECORD_AUDIO` permissions are requested gracefully at runtime via Compose.
4. **App Details:**
   - **Title (<= 30 chars):** `FaceGuard Studio`
   - **Short Description:** `Private offline camera with real-time face blur and virtual sets.`
   - **Category:** Photography / Video Players & Editors
   - **Content Rating:** Suitable for all audiences (No user data collected, no ads).
   - **Data Safety Section:** Select "No data collected or shared" as all processing is strictly on-device.
