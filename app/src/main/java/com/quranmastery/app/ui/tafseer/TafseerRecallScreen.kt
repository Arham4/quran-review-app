package com.quranmastery.app.ui.tafseer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.quranmastery.app.ui.components.SectionHeader

@Composable
fun TafseerRecallScreen(onDone: () -> Unit) {
    var index by remember { mutableIntStateOf(0) }
    var revealed by remember { mutableStateOf(false) }
    var graded by remember { mutableStateOf("") }
    val cards = remember {
        listOf(
            Triple("Surah 51:1-10", "P520 · H53", "Adh-Dhariyat opening theme"),
            Triple("Surah 55:1-16", "P533 · H55", "Ar-Rahman blessings theme"),
            Triple("Surah 57:1-10", "P545 · H57", "Al-Hadid charity theme")
        )
    }
    val (ref, loc, theme) = cards[index % cards.size]

    MadaniScaffold(title = "Tafseer Recall", onBack = onDone) {
        GoldCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SectionHeader("Cluster ${index % cards.size + 1} of ${cards.size}")
                Text(loc, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
            Text(
                ref,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                theme,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            AnimatedVisibility(visible = revealed) {
                androidx.compose.foundation.layout.Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("• Main point of the passage")
                    Text("• Link to the next passage")
                    Text("• Action point for memorization")
                }
            }
            if (!revealed) {
                Text(
                    "Recall the bullets first, then reveal.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        MadaniPrimaryButton(
            label = if (revealed) "Hide bullets" else "Reveal bullets",
            onClick = { revealed = !revealed }
        )

        SectionHeader("Grade this cluster")
        GradeRow(onGrade = {
            graded = it
            revealed = false
            if (index < 20) index++
        })
        if (graded.isNotEmpty()) {
            Text(
                "Graded $graded",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        MadaniSecondaryButton(label = "Import / edit clusters (soon)", onClick = { })
        MadaniSecondaryButton(label = "Back to dashboard", onClick = onDone)
    }
}
