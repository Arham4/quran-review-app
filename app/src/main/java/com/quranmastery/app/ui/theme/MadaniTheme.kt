package com.quranmastery.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Midnight Masjid OLED (dark)
private val MidnightBg = Color(0xFF0B1410)
private val MidnightSurface = Color(0xFF15241C)
private val MidnightGold = Color(0xFFE5C158)
private val MidnightMint = Color(0xFF34D399)
private val MidnightOnBg = Color(0xFFF1EFE6)
private val MidnightOnSurface = Color(0xFFDDE7DF)

// Madani Cream (light)
private val CreamBg = Color(0xFFFBF7EE)
private val CreamSurface = Color(0xFFFFFFFF)
private val CreamGreen = Color(0xFF0E4D34)
private val CreamGold = Color(0xFFB8860B)
private val CreamOnBg = Color(0xFF1A2B23)

private val DarkScheme = darkColorScheme(
    primary = MidnightGold,
    onPrimary = Color(0xFF0B1410),
    secondary = MidnightMint,
    onSecondary = Color(0xFF0B1410),
    tertiary = MidnightMint,
    background = MidnightBg,
    onBackground = MidnightOnBg,
    surface = MidnightSurface,
    onSurface = MidnightOnSurface,
    surfaceVariant = Color(0xFF1E3328),
    onSurfaceVariant = Color(0xFFB9C9BE)
)

private val LightScheme = lightColorScheme(
    primary = CreamGreen,
    onPrimary = Color.White,
    secondary = CreamGold,
    onSecondary = Color.White,
    tertiary = CreamGreen,
    background = CreamBg,
    onBackground = CreamOnBg,
    surface = CreamSurface,
    onSurface = CreamOnBg,
    surfaceVariant = Color(0xFFF0E8D5),
    onSurfaceVariant = Color(0xFF4A5A50)
)

@Composable
fun MadaniAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkScheme else LightScheme,
        content = content
    )
}
