package com.quranmastery.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.graphics.Color
import com.quranmastery.app.ui.navigation.AppNavigation
import com.quranmastery.app.ui.theme.MadaniAppTheme
import com.quranmastery.app.ui.theme.ThemeState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )
        setContent {
            // Read once per composition so toggling ThemeState.dark recomposes.
            val dark = ThemeState.dark
            MadaniAppTheme(darkTheme = dark) {
                AppNavigation()
            }
        }
    }
}
