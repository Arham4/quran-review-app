package com.quranmastery.app.ui.tafseer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TafseerRecallScreen(onDone: () -> Unit) {
    var index by remember { mutableIntStateOf(0) }
    var revealed by remember { mutableStateOf(false) }
    val cards = remember {
        listOf(
            Triple("Surah 51:1-10", "P520 H53", "Adh-Dhariyat opening theme"),
            Triple("Surah 55:1-16", "P533 H55", "Ar-Rahman blessings theme"),
            Triple("Surah 57:1-10", "P545 H57", "Al-Hadid charity theme")
        )
    }
    val (ref, loc, theme) = cards[index % cards.size]

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Tafseer Recall", style = MaterialTheme.typography.titleLarge)
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(ref, style = MaterialTheme.typography.titleMedium)
                Text("$loc  |  $theme")
                if (revealed) {
                    Text("- Bullet 1: main point of passage")
                    Text("- Bullet 2: link to next passage")
                    Text("- Bullet 3: action point")
                } else {
                    Text("Front only — recall bullets, then reveal.")
                }
            }
        }
        Button(onClick = { revealed = !revealed }, modifier = Modifier.fillMaxWidth()) {
            Text(if (revealed) "Hide" else "Reveal bullets")
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Again", "Hard", "Good", "Easy").forEach { g ->
                OutlinedButton(
                    onClick = {
                        revealed = false
                        if (index < 20) index++
                    },
                    modifier = Modifier.weight(1f)
                ) { Text(g) }
            }
        }
        OutlinedButton(onClick = { /* import/edit placeholder */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Import / Edit clusters (placeholder)")
        }
        OutlinedButton(onClick = onDone, modifier = Modifier.fillMaxWidth()) { Text("Done") }
    }
}
