package com.quranmastery.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

// 15-line wireframe schematic. Zero Arabic text: only line bars + L1..L15 labels + zone chip.
@Composable
fun MadaniPageSchematic(
    modifier: Modifier = Modifier,
    pageNumber: Int,
    zone: String,
    hardLine: Int = 3,
    showSurahHeader: Boolean = true
) {
    val scheme = MaterialTheme.colorScheme
    val zoneColor: Color = when (zone.uppercase()) {
        "TOP" -> scheme.primary
        "MIDDLE" -> scheme.secondary
        else -> scheme.tertiary
    }
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(340.dp)
    ) {
        val w = size.width
        val h = size.height
        // Page background
        drawRoundRect(
            color = Color(0x22FFFFFF),
            topLeft = Offset(8f, 8f),
            size = Size(w - 16f, h - 16f),
            cornerRadius = CornerRadius(24f, 24f)
        )
        // Zone chip area (top-right rectangle)
        drawRoundRect(
            color = zoneColor,
            topLeft = Offset(w - 260f, 20f),
            size = Size(220f, 56f),
            cornerRadius = CornerRadius(16f, 16f)
        )
        drawIntoCanvas { canvas ->
            val paint = android.graphics.Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 34f
                isFakeBoldText = true
            }
            canvas.nativeCanvas.drawText("P$pageNumber  $zone", w - 240f, 58f, paint)
        }
        // 15 lines
        val top = 100f
        val lineGap = (h - top - 24f) / 15f
        for (i in 1..15) {
            val y = top + (i - 1) * lineGap
            val inZone = when (zone.uppercase()) {
                "TOP" -> i in 1..5
                "MIDDLE" -> i in 6..10
                else -> i in 11..15
            }
            val barColor = if (i == hardLine) Color(0xFFE5484D) else if (inZone) zoneColor else Color(0xFF8A9A90)
            // Surah header bars: lines 1-2 drawn shorter/centered when enabled
            val isHeader = showSurahHeader && (i == 1 || i == 2)
            val barW = if (isHeader) w * 0.5f else w - 64f
            val barX = if (isHeader) (w - barW) / 2f else 32f
            drawRoundRect(
                color = barColor.copy(alpha = if (inZone || i == hardLine) 1f else 0.45f),
                topLeft = Offset(barX, y),
                size = Size(barW, lineGap * 0.45f),
                cornerRadius = CornerRadius(8f, 8f)
            )
            drawIntoCanvas { canvas ->
                val paint = android.graphics.Paint().apply {
                    color = android.graphics.Color.GRAY
                    textSize = 22f
                }
                canvas.nativeCanvas.drawText("L$i", 4f, y + lineGap * 0.45f, paint)
            }
        }
    }
}
