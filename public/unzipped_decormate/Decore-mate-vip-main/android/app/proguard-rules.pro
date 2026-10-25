# DecorMate VIP — ProGuard & R8 Release Rules for Google Play, CafeBazaar & Myket
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod
-keepattributes SourceFile,LineNumberTable

# Keep WebView JavaScript Interface & MainActivity
-keepclassmembers class com.decormate.vip.** {
    public *;
}
-keep class com.decormate.vip.MainActivity { *; }
-dontwarn android.webkit.**
