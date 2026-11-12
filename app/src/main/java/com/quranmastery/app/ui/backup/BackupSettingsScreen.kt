package com.quranmastery.app.ui.backup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BackupSettingsScreen(onDone: () -> Unit) {
    var mon by remember { mutableStateOf(true) }
    var wed by remember { mutableStateOf(true) }
    var fri by remember { mutableStateOf(true) }
    var visualRet by remember { mutableFloatStateOf(0.9f) }
    var tafseerRet by remember { mutableFloatStateOf(0.9f) }
    var msg by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Backup & Settings", style = MaterialTheme.typography.titleLarge)
        Text("Lesson days")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = mon, onClick = { mon = !mon }, label = { Text("MON") })
            FilterChip(selected = wed, onClick = { wed = !wed }, label = { Text("WED") })
            FilterChip(selected = fri, onClick = { fri = !fri }, label = { Text("FRI") })
        }
        Text("Visual target retention: ${(visualRet * 100).toInt()}%")
        Slider(value = visualRet, onValueChange = { visualRet = it }, valueRange = 0.7f..0.99f)
        Text("Tafseer target retention: ${(tafseerRet * 100).toInt()}%")
        Slider(value = tafseerRet, onValueChange = { tafseerRet = it }, valueRange = 0.7f..0.99f)
        OutlinedButton(onClick = { msg = "Export JSON placeholder" }, modifier = Modifier.fillMaxWidth()) { Text("Export JSON") }
        OutlinedButton(onClick = { msg = "Import JSON placeholder" }, modifier = Modifier.fillMaxWidth()) { Text("Import JSON") }
        if (msg.isNotEmpty()) Text(msg)
        OutlinedButton(onClick = onDone, modifier = Modifier.fillMaxWidth()) { Text("Done") }
    }
}
