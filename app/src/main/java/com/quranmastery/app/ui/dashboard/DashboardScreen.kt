package com.quranmastery.app.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.quranmastery.app.ui.components.GoldCard
import com.quranmastery.app.ui.components.MadaniChip
import com.quranmastery.app.ui.components.MadaniPrimaryButton
import com.quranmastery.app.ui.components.MadaniScaffold
import com.quranmastery.app.ui.components.MadaniSecondaryButton
import com.quranmastery.app.ui.components.MadaniTag
import com.quranmastery.app.ui.components.SectionHeader

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

    MadaniScaffold(title = "Quran Mastery") {
        GoldCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                if (advance) "Hizb 58 · Advance" else "Hizb 57 · Repeat",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                if (advance) "Al-Jinn 72:1 – Al-Mursalat 77:50 · Pages 572–581"
                else "Al-Mulk 67:1 – Nuh 71:28 · Pages 562–571",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MadaniChip(selected = advance, label = "Advance Hizb 58", onClick = { advance = true })
                MadaniChip(selected = !advance, label = "Repeat Hizb 57", onClick = { advance = false })
            }
        }

        GoldCard(modifier = Modifier.fillMaxWidth()) {
            SectionHeader("Sabak · New Memorization")
            Text(
                "Next up: Al-Baqarah 2:34 (Page 6, Line 10)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                "Memorized: 2:1–33 · Pages 2–6",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GoldCard(modifier = Modifier.weight(1f)) {
                SectionHeader("Visual due")
                Text(
                    "$visualDue",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            GoldCard(modifier = Modifier.weight(1f)) {
                SectionHeader("Tafseer due")
                Text(
                    "$tafseerDue",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        SectionHeader("Hizb rotation · 53–60")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(hizbs) { h ->
                MadaniChip(
                    selected = h == selectedHizb,
                    label = "H$h",
                    onClick = { selectedHizb = h }
                )
            }
        }

        if (fuzzyPages.isNotEmpty()) {
            SectionHeader("Fuzzy pages · tap to clear")
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(fuzzyPages.toList()) { p ->
                    MadaniTag(label = "Page $p", onRemove = { fuzzyPages.remove(p) })
                }
            }
        }

        MadaniPrimaryButton(label = "Log Lesson", onClick = onPostLesson)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                MadaniSecondaryButton(label = "Visual Drill", onClick = onVisual)
            }
            Column(modifier = Modifier.weight(1f)) {
                MadaniSecondaryButton(label = "Tafseer", onClick = onTafseer)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                MadaniSecondaryButton(label = "Promotion", onClick = onPromotion)
            }
            Column(modifier = Modifier.weight(1f)) {
                MadaniSecondaryButton(label = "Backup", onClick = onBackup)
            }
        }
    }
}
