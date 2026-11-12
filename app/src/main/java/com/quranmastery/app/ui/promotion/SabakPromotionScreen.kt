package com.quranmastery.app.ui.promotion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SabakPromotionScreen(onDone: () -> Unit) {
    var msg by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Sabak Promotion", style = MaterialTheme.typography.titleLarge)
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Staged Baqarah pages 2-49 (clusters TBQ_01..04, Surah 2:34+)")
                Text("Promoting enables staged tafseer cards for review.")
            }
        }
        Button(onClick = { msg = "Promoted staged Baqarah clusters (placeholder, wire to TafseerDao.promoteStaged())" }, modifier = Modifier.fillMaxWidth()) {
            Text("Promote staged pages")
        }
        OutlinedButton(onClick = { msg = "JSON backup export placeholder" }, modifier = Modifier.fillMaxWidth()) {
            Text("Export JSON backup")
        }
        OutlinedButton(onClick = { msg = "JSON backup import placeholder" }, modifier = Modifier.fillMaxWidth()) {
            Text("Import JSON backup")
        }
        if (msg.isNotEmpty()) Text(msg)
        OutlinedButton(onClick = onDone, modifier = Modifier.fillMaxWidth()) { Text("Done") }
    }
}
