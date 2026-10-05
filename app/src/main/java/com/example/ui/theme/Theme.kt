package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Warm Light-First Color Scheme
private val LightColorScheme = lightColorScheme(
    primary = TerracottaAccent,
    onPrimary = Color.White,
    primaryContainer = TerracottaSubtle,
    onPrimaryContainer = TerracottaHover,
    secondary = SageGreen,
    onSecondary = Color.White,
    secondaryContainer = SageGreenSubtle,
    onSecondaryContainer = SageGreen,
    tertiary = MutedAmber,
    onTertiary = Color.White,
    background = WarmBackground,
    onBackground = CharcoalPrimary,
    surface = WarmSurface,
    onSurface = CharcoalPrimary,
    surfaceVariant = WarmSurfaceSecondary,
    onSurfaceVariant = CharcoalSecondary,
    outline = WarmBorder,
    outlineVariant = WarmBorderSubtle,
    error = BrickRed,
    onError = Color.White,
    errorContainer = BrickRedSubtle,
    onErrorContainer = BrickRed
)

// Warm Charcoal-based Dark Theme (NO navy or blue)
private val DarkColorScheme = darkColorScheme(
    primary = DarkTerracottaAccent,
    onPrimary = Color.Black,
    primaryContainer = TerracottaAccent.copy(alpha = 0.25f),
    onPrimaryContainer = DarkTerracottaAccent,
    secondary = DarkSageGreen,
    onSecondary = Color.Black,
    secondaryContainer = SageGreen.copy(alpha = 0.25f),
    onSecondaryContainer = DarkSageGreen,
    tertiary = MutedAmber,
    onTertiary = Color.Black,
    background = DarkCharcoalBackground,
    onBackground = DarkTextPrimary,
    surface = DarkCharcoalSurface,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkCharcoalSurfaceVariant,
    onSurfaceVariant = DarkTextSecondary,
    outline = DarkCharcoalBorder,
    outlineVariant = DarkCharcoalSurfaceSecondary,
    error = BrickRed,
    onError = Color.White,
    errorContainer = BrickRed.copy(alpha = 0.25f),
    onErrorContainer = Color(0xFFFFB4AB)
)

@Composable
fun FaceGuardTheme(
    darkTheme: Boolean = false, // Light-first by default as instructed
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    FaceGuardTheme(darkTheme = darkTheme, content = content)
}
