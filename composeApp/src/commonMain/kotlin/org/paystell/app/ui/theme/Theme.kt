package org.paystell.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// PayStell brand colors
val StellarBlue = Color(0xFF3C6CFF)
val StellarDarkBlue = Color(0xFF254EE3)
val StellarLightBlue = Color(0xFF739DFF)
val DarkText = Color(0xFF1A1A1A)
val LightText = Color(0xFFF5F5F5)
val BackgroundLight = Color(0xFFFAFAFA)
val BackgroundDark = Color(0xFF121212)
val ErrorRed = Color(0xFFE53935)
val SuccessGreen = Color(0xFF4CAF50)

private val DarkColorPalette = darkColors(
    primary = StellarBlue,
    primaryVariant = StellarDarkBlue,
    secondary = StellarLightBlue,
    background = BackgroundDark,
    surface = BackgroundDark,
    onPrimary = LightText,
    onSecondary = LightText,
    onBackground = LightText,
    onSurface = LightText,
    error = ErrorRed,
    onError = LightText
)

private val LightColorPalette = lightColors(
    primary = StellarBlue,
    primaryVariant = StellarDarkBlue,
    secondary = StellarLightBlue,
    background = BackgroundLight,
    surface = BackgroundLight,
    onPrimary = DarkText,
    onSecondary = DarkText,
    onBackground = DarkText,
    onSurface = DarkText,
    error = ErrorRed,
    onError = LightText
)

@Composable
fun PayStellTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
} 