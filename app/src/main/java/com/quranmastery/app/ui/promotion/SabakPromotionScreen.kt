package com.quranmastery.app.ui.promotion

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.quranmastery.app.ui.components.GoldCard
import com.quranmastery.app.ui.components.MadaniPrimaryButton
import com.quranmastery.app.ui.components.MadaniScaffold
import com.quranmastery.app.ui.components.MadaniSecondaryButton
import com.quranmastery.app.ui.components.SectionHeader

@Composable
fun SabakPromotionScreen(onDone: () -> Unit) {
    var msg by remember { mutableStateOf("") }
    MadaniScaffold(title = "Sabak Promotion", onBack = onDone) {
        GoldCard(modifier = Modifier.fillMaxWidth()) {
            SectionHeader("Staged · Al-Baqarah pages 2–49")
            Text(
                "Clusters TBQ_01–04 · Surah 2:34 onward",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                "Promoting moves stabilized pages, Hizbs and clusters into the daily FSRS queues.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        MadaniPrimaryButton(
            label = "Promote staged pages",
            onClick = { msg = "Promotion queued — wire to TafseerDao.promoteStaged() next." }
        )
        if (msg.isNotEmpty()) {
            GoldCard(modifier = Modifier.fillMaxWidth()) { Text(msg) }
        }
        MadaniSecondaryButton(label = "Back to dashboard", onClick = onDone)
    }
}
