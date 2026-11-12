package com.quranmastery.app.ui.visual

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.quranmastery.app.ui.components.GoldCard
import com.quranmastery.app.ui.components.GradeRow
import com.quranmastery.app.ui.components.MadaniPrimaryButton
import com.quranmastery.app.ui.components.MadaniScaffold
import com.quranmastery.app.ui.components.MadaniSecondaryButton
import com.quranmastery.app.ui.components.MadaniPageSchematic
import com.quranmastery.app.ui.components.SectionHeader
import kotlinx.coroutines.delay

@Composable
fun VisualDrillScreen(onDone: () -> Unit) {
    var index by remember { mutableIntStateOf(0) }
    var staring by remember { mutableStateOf(true) }
    var anchor by remember { mutableStateOf("") }
    var graded by remember { mutableStateOf("") }
    val queue = remember {
        listOf(
            Triple("P562_TOP", 562, "TOP"),
            Triple("P562_MIDDLE", 562, "MIDDLE"),
            Triple("P562_BOTTOM", 562, "BOTTOM"),
            Triple("P563_TOP", 563, "TOP")
        )
    }

    val progress by animateFloatAsState(targetValue = if (staring) 1f else 0f, label = "stare")

    LaunchedEffect(index) {
        staring = true
        delay(2000)
        staring = false
    }

    val (cardId, page, zone) = queue[index]

    MadaniScaffold(title = "Visual Drill", onBack = onDone) {
        GoldCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectionHeader("Card ${index + 1} of ${queue.size}")
                Text(
                    cardId,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            if (staring) {
                Text("Stare at the $zone zone on your Mushaf…")
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text(
                    "Eyes closed — picture the zone, then grade yourself.",
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        GoldCard(modifier = Modifier.fillMaxWidth()) {
            MadaniPageSchematic(pageNumber = page, zone = zone, hardLine = 3)
        }

        OutlinedTextField(
            value = anchor,
            onValueChange = { anchor = it },
            label = { Text("Anchor notes (line-start words, quirks)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false
        )

        SectionHeader("Grade this card")
        GradeRow(onGrade = { graded = it })
        if (graded.isNotEmpty()) {
            Text(
                "Graded $graded for $cardId",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        MadaniPrimaryButton(
            label = if (index < queue.lastIndex) "Next card" else "Finish drill",
            onClick = {
                graded = ""
                anchor = ""
                if (index < queue.lastIndex) index++ else onDone()
            }
        )
        MadaniSecondaryButton(label = "Exit drill", onClick = onDone)
    }
}
