package com.quranmastery.app.ui.visual

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.quranmastery.app.ui.components.MadaniPageSchematic
import kotlinx.coroutines.delay

@Composable
fun VisualDrillScreen(onDone: () -> Unit) {
    var index by remember { mutableIntStateOf(0) }
    var staring by remember { mutableStateOf(true) }
    var anchor by remember { mutableStateOf("") }
    var graded by remember { mutableStateOf("") }
    val queue = remember { listOf("P562_TOP", "P562_MIDDLE", "P562_BOTTOM", "P563_TOP") }

    val target = if (staring) 1f else 0f
    val progress by animateFloatAsState(targetValue = target, label = "stare")

    LaunchedEffect(index) {
        staring = true
        delay(2000)
        staring = false
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Visual Drill (${index + 1}/${queue.size})", style = MaterialTheme.typography.titleLarge)
        Text("Card ${queue[index]}  |  look away and recall line positions")
        if (staring) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CircularProgressIndicator(progress = { progress })
                Text("2s stare ring — memorize the zone")
            }
        } else {
            Text("Recall now: which lines hold the hard anchor?")
        }
        MadaniPageSchematic(pageNumber = 562 + (index / 3), zone = "TOP", hardLine = 3)
        OutlinedTextField(value = anchor, onValueChange = { anchor = it }, label = { Text("Anchor notes") }, modifier = Modifier.fillMaxWidth())
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Again", "Hard", "Good", "Easy").forEach { g ->
                OutlinedButton(onClick = { graded = g }, modifier = Modifier.weight(1f)) { Text(g) }
            }
        }
        if (graded.isNotEmpty()) Text("Graded $graded for ${queue[index]}")
        Button(
            onClick = {
                graded = ""
                anchor = ""
                if (index < queue.lastIndex) index++ else onDone()
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text(if (index < queue.lastIndex) "Next card" else "Finish") }
    }
}
