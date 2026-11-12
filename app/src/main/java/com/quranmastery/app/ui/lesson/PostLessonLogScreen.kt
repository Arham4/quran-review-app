package com.quranmastery.app.ui.lesson

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.quranmastery.app.ui.components.GoldCard
import com.quranmastery.app.ui.components.MadaniChip
import com.quranmastery.app.ui.components.MadaniPrimaryButton
import com.quranmastery.app.ui.components.MadaniScaffold
import com.quranmastery.app.ui.components.MadaniSecondaryButton
import com.quranmastery.app.ui.components.SectionHeader

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PostLessonLogScreen(onDone: () -> Unit) {
    var outcome by remember { mutableStateOf("PASSED") }
    var sabakOutcome by remember { mutableStateOf("PASSED") }
    var startSurah by remember { mutableStateOf("2") }
    var startAyah by remember { mutableStateOf("1") }
    var endSurah by remember { mutableStateOf("2") }
    var endAyah by remember { mutableStateOf("33") }
    var manualSurah by remember { mutableStateOf("2") }
    var manualAyah by remember { mutableStateOf("34") }
    val fuzzyPages = remember { mutableStateListOf<Int>() }
    val candidateFuzzy = remember { (562..571).toList() }
    var savedMsg by remember { mutableStateOf("") }

    MadaniScaffold(title = "Log Lesson", onBack = onDone) {
        GoldCard(modifier = Modifier.fillMaxWidth()) {
            SectionHeader("Review outcome · Hizb 57")
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "PASSED" to "Passed full Hizb",
                    "REPEAT" to "Repeat Hizb",
                    "STOPPED_EARLY" to "Stopped early",
                    "MANUAL" to "Manual range"
                ).forEach { (value, label) ->
                    MadaniChip(selected = outcome == value, label = label, onClick = { outcome = value })
                }
            }
            Text(
                when (outcome) {
                    "PASSED" -> "Next lesson advances to Hizb 58 (Al-Jinn – Al-Mursalat)."
                    "REPEAT" -> "Hizb 57 stays locked for next lesson."
                    "STOPPED_EARLY" -> "Unfinished pages carry over + Hizb 58."
                    else -> "Pick any custom start below."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        GoldCard(modifier = Modifier.fillMaxWidth()) {
            SectionHeader("Sabak memorized this lesson")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = startSurah, onValueChange = { startSurah = it },
                    label = { Text("Start S") }, modifier = Modifier.weight(1f), singleLine = true
                )
                OutlinedTextField(
                    value = startAyah, onValueChange = { startAyah = it },
                    label = { Text("Start A") }, modifier = Modifier.weight(1f), singleLine = true
                )
                OutlinedTextField(
                    value = endSurah, onValueChange = { endSurah = it },
                    label = { Text("End S") }, modifier = Modifier.weight(1f), singleLine = true
                )
                OutlinedTextField(
                    value = endAyah, onValueChange = { endAyah = it },
                    label = { Text("End A") }, modifier = Modifier.weight(1f), singleLine = true
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MadaniChip(
                    selected = sabakOutcome == "PASSED",
                    label = "Sabak passed",
                    onClick = { sabakOutcome = "PASSED" }
                )
                MadaniChip(
                    selected = sabakOutcome == "REPEAT",
                    label = "Repeat sabak",
                    onClick = { sabakOutcome = "REPEAT" }
                )
            }
        }

        if (outcome == "MANUAL") {
            GoldCard(modifier = Modifier.fillMaxWidth()) {
                SectionHeader("Manual override start")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = manualSurah, onValueChange = { manualSurah = it },
                        label = { Text("Surah") }, modifier = Modifier.weight(1f), singleLine = true
                    )
                    OutlinedTextField(
                        value = manualAyah, onValueChange = { manualAyah = it },
                        label = { Text("Ayah") }, modifier = Modifier.weight(1f), singleLine = true
                    )
                }
            }
        }

        GoldCard(modifier = Modifier.fillMaxWidth()) {
            SectionHeader("Fuzzy pages · Hizb 57 (562–571)")
            Text(
                "Flagged pages get an FSRS Again lapse before next lesson.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                candidateFuzzy.forEach { p ->
                    MadaniChip(
                        selected = fuzzyPages.contains(p),
                        label = "P$p",
                        onClick = { if (fuzzyPages.contains(p)) fuzzyPages.remove(p) else fuzzyPages.add(p) }
                    )
                }
            }
        }

        if (savedMsg.isNotEmpty()) {
            GoldCard(modifier = Modifier.fillMaxWidth()) { Text(savedMsg) }
        }
        MadaniPrimaryButton(
            label = "Save log",
            onClick = {
                savedMsg = "Saved: review=$outcome · sabak $startSurah:$startAyah–$endSurah:$endAyah ($sabakOutcome) · fuzzy=${fuzzyPages.sorted()}"
            }
        )
        MadaniSecondaryButton(label = "Back to dashboard", onClick = onDone)
    }
}
