package com.quranmastery.app.ui.backup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.quranmastery.app.ui.components.GoldCard
import com.quranmastery.app.ui.components.MadaniChip
import com.quranmastery.app.ui.components.MadaniScaffold
import com.quranmastery.app.ui.components.MadaniSecondaryButton
import com.quranmastery.app.ui.components.SectionHeader
import com.quranmastery.app.ui.theme.ThemeState

@Composable
fun BackupSettingsScreen(onDone: () -> Unit) {
    var mon by remember { mutableStateOf(true) }
    var wed by remember { mutableStateOf(true) }
    var fri by remember { mutableStateOf(true) }
    var visualRet by remember { mutableFloatStateOf(0.9f) }
    var tafseerRet by remember { mutableFloatStateOf(0.9f) }
    var msg by remember { mutableStateOf("") }

    MadaniScaffold(title = "Settings & Backup", onBack = onDone) {
        GoldCard(modifier = Modifier.fillMaxWidth()) {
            SectionHeader("Appearance")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Midnight Masjid dark theme")
                Switch(checked = ThemeState.dark, onCheckedChange = { ThemeState.dark = it })
            }
        }

        GoldCard(modifier = Modifier.fillMaxWidth()) {
            SectionHeader("Lesson days")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MadaniChip(selected = mon, label = "MON", onClick = { mon = !mon })
                MadaniChip(selected = wed, label = "WED", onClick = { wed = !wed })
                MadaniChip(selected = fri, label = "FRI", onClick = { fri = !fri })
            }
        }

        GoldCard(modifier = Modifier.fillMaxWidth()) {
            SectionHeader("FSRS target retention")
            Text("Visual: ${(visualRet * 100).toInt()}%")
            Slider(value = visualRet, onValueChange = { visualRet = it }, valueRange = 0.7f..0.99f)
            Text("Tafseer: ${(tafseerRet * 100).toInt()}%")
            Slider(value = tafseerRet, onValueChange = { tafseerRet = it }, valueRange = 0.7f..0.99f)
        }

        GoldCard(modifier = Modifier.fillMaxWidth()) {
            SectionHeader("Backup")
            Text(
                "Full JSON export / import of lessons, FSRS states and notes.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        MadaniSecondaryButton(label = "Export JSON", onClick = { msg = "Export coming next." })
        MadaniSecondaryButton(label = "Import JSON", onClick = { msg = "Import coming next." })
        if (msg.isNotEmpty()) {
            GoldCard(modifier = Modifier.fillMaxWidth()) { Text(msg) }
        }
        MadaniSecondaryButton(label = "Back to dashboard", onClick = onDone)
    }
}
