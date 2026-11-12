package com.quranmastery.app.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(
    onPostLesson: () -> Unit,
    onVisual: () -> Unit,
    onTafseer: () -> Unit,
    onPromotion: () -> Unit,
    onBackup: () -> Unit
) {
    var advance by remember { mutableStateOf(true) }
    var visualDue by remember { mutableIntStateOf(12) }
    var tafseerDue by remember { mutableIntStateOf(4) }
    val fuzzyPages = remember { mutableStateListOf(562, 565, 571) }
    val hizbs = remember { (53..60).toList() }
    var selectedHizb by remember { mutableIntStateOf(57) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Next Lesson Target", style = MaterialTheme.typography.titleMedium)
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(if (advance) "Hizb 58 (ADVANCE)" else "Hizb 57 (REPEAT)")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = advance, onClick = { advance = true }, label = { Text("Advance Hizb 58") })
                    FilterChip(selected = !advance, onClick = { advance = false }, label = { Text("Repeat Hizb 57") })
                }
                Text("Sabak cursor: Surah 2 Ayah 34 (window 2:1-33 done)")
            }
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Daily FSRS due", style = MaterialTheme.typography.titleMedium)
                Text("Visual due: $visualDue   Tafseer due: $tafseerDue")
            }
        }

        Text("Hizb rotation ribbon 53-60", style = MaterialTheme.typography.titleMedium)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(hizbs) { h ->
                FilterChip(
                    selected = h == selectedHizb,
                    onClick = { selectedHizb = h },
                    label = { Text("H$h") }
                )
            }
        }

        Text("Fuzzy flags (quick chips)", style = MaterialTheme.typography.titleMedium)
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(fuzzyPages) { p ->
                AssistChip(onClick = { fuzzyPages.remove(p) }, label = { Text("P$p tap to clear") })
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = onPostLesson, modifier = Modifier.fillMaxWidth()) { Text("Log Lesson") }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = onVisual, modifier = Modifier.weight(1f)) { Text("Visual Drill") }
            OutlinedButton(onClick = onTafseer, modifier = Modifier.weight(1f)) { Text("Tafseer") }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = onPromotion, modifier = Modifier.weight(1f)) { Text("Promotion") }
            OutlinedButton(onClick = onBackup, modifier = Modifier.weight(1f)) { Text("Backup") }
        }
    }
}
