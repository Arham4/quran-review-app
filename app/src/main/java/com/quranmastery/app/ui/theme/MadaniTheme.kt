package com.quranmastery.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

// Midnight Masjid OLED (dark, default)
val MidnightBg = Color(0xFF0B1410)
val MidnightSurface = Color(0xFF15241C)
val MidnightSurfaceHigh = Color(0xFF1E3328)
val MidnightGold = Color(0xFFE5C158)
val MidnightGoldDim = Color(0xFF8A7132)
val MidnightMint = Color(0xFF34D399)
val MidnightOnBg = Color(0xFFF1EFE6)
val MidnightOnSurface = Color(0xFFDDE7DF)
val MidnightMuted = Color(0xFF9DB3A4)
val GradeAgain = Color(0xFFE5484D)
val GradeHard = Color(0xFFF5A524)
val GradeGood = Color(0xFF34D399)
val GradeEasy = Color(0xFF6CB8FF)

// Madani Cream Paper (light)
val CreamBg = Color(0xFFFBF7EE)
val CreamSurface = Color(0xFFFFFFFF)
val CreamSurfaceHigh = Color(0xFFF0E8D5)
val CreamGreen = Color(0xFF0E4D34)
val CreamGold = Color(0xFFB8860B)
val CreamOnBg = Color(0xFF1A2B23)
val CreamMuted = Color(0xFF5A6B60)

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
    surfaceVariant = MidnightSurfaceHigh,
    onSurfaceVariant = MidnightMuted,
    outline = MidnightGoldDim,
    outlineVariant = Color(0xFF2A4234)
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
    surfaceVariant = CreamSurfaceHigh,
    onSurfaceVariant = CreamMuted,
    outline = CreamGold,
    outlineVariant = Color(0xFFE4D7BE)
)

/** App-wide theme switch. The app is dark-first; the toggle lives in Settings. */
object ThemeState {
    var dark by mutableStateOf(true)
}

/** Gold hairline used on every card border. */
val GoldHairline = RoundedCornerShape(20.dp)

@Composable
fun MadaniAppTheme(
    darkTheme: Boolean = ThemeState.dark,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkScheme else LightScheme,
        content = content
    )
}
