package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val RetroPlayColorScheme = lightColorScheme(
    primary = PlayGreenHeader,
    onPrimary = Color.White,
    primaryContainer = PlayGreenButton,
    onPrimaryContainer = Color.White,
    secondary = HoloBlue,
    onSecondary = Color.White,
    tertiary = HoloOrange,
    background = PlayStoreBackground,
    onBackground = PlayTextDark,
    surface = PlayCardBackground,
    onSurface = PlayTextDark,
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = PlayTextMuted,
    outline = PlayCardBorder
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = RetroPlayColorScheme,
        typography = Typography,
        content = content
    )
}
