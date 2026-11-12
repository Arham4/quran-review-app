package com.quranmastery.app.ui.lesson

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
    val candidateFuzzy = remember { listOf(562, 563, 564, 565, 566, 567, 568, 569, 570, 571) }
    var savedMsg by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Post-Lesson Log", style = MaterialTheme.typography.titleLarge)
        Text("Hizb outcome")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("PASSED", "REPEAT", "STOPPED_EARLY", "MANUAL").forEach { o ->
                FilterChip(selected = outcome == o, onClick = { outcome = o }, label = { Text(o) })
            }
        }
        Text("Sabak range (Surah:Ayah pickers)")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = startSurah, onValueChange = { startSurah = it }, label = { Text("Start Surah") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = startAyah, onValueChange = { startAyah = it }, label = { Text("Start Ayah") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = endSurah, onValueChange = { endSurah = it }, label = { Text("End Surah") }, modifier = Modifier.weight(1f))
            OutlinedTextField(value = endAyah, onValueChange = { endAyah = it }, label = { Text("End Ayah") }, modifier = Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = sabakOutcome == "PASSED", onClick = { sabakOutcome = "PASSED" }, label = { Text("Sabak Pass") })
            FilterChip(selected = sabakOutcome == "REPEAT", onClick = { sabakOutcome = "REPEAT" }, label = { Text("Sabak Repeat") })
        }
        if (outcome == "MANUAL") {
            Text("Manual override cursor")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = manualSurah, onValueChange = { manualSurah = it }, label = { Text("Surah") }, modifier = Modifier.weight(1f))
                OutlinedTextField(value = manualAyah, onValueChange = { manualAyah = it }, label = { Text("Ayah") }, modifier = Modifier.weight(1f))
            }
        }
        Text("Fuzzy pages 562-571")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(candidateFuzzy) { p ->
                FilterChip(
                    selected = fuzzyPages.contains(p),
                    onClick = { if (fuzzyPages.contains(p)) fuzzyPages.remove(p) else fuzzyPages.add(p) },
                    label = { Text("P$p") }
                )
            }
        }
        if (savedMsg.isNotEmpty()) Text(savedMsg)
        Button(onClick = { savedMsg = "Saved: $outcome sabak $startSurah:$startAyah-$endSurah:$endAyah fuzzy=$fuzzyPages" }, modifier = Modifier.fillMaxWidth()) {
            Text("Save log")
        }
        OutlinedButton(onClick = onDone, modifier = Modifier.fillMaxWidth()) { Text("Done") }
    }
}
