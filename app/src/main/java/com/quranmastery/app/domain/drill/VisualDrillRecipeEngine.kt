package com.quranmastery.app.domain.drill

import com.quranmastery.app.domain.fsrs.FsrsCardState

/** Which visual-imprint protocol to run for a page. */
enum class DrillProtocol {
    LAPSED_DEEP_IMPRINT,
    YOUNG_CONSOLIDATING,
    MATURE_AUDIT
}

/** Chime cue associated with a drill step. */
enum class DrillChimeMode {
    START,
    EYES_CLOSED,
    COMPLETE
}

/** One drill step: stare/read with repetition count. */
data class DrillStep(
    val description: String,
    val reps: Int,
    val stareMs: Long,
    val chimeMode: DrillChimeMode
)

/** Full recipe for one page drill. */
data class DrillRecipe(
    val protocol: DrillProtocol,
    val steps: List<DrillStep>,
    val totalReps: Int,
    val estSeconds: Int
)

/**
 * Pure-Kotlin visual drill recipe engine. No Android dependencies.
 *
 * Routing:
 * - LAPSED_DEEP_IMPRINT if state == RELEARNING or stability < 3
 * - MATURE_AUDIT if state == REVIEW and stability > 30
 * - else YOUNG_CONSOLIDATING
 */
object VisualDrillRecipeEngine {

    fun recipeFor(stability: Double, state: FsrsCardState): DrillRecipe =
        when (protocolFor(stability, state)) {
            DrillProtocol.LAPSED_DEEP_IMPRINT -> lapsedRecipe()
            DrillProtocol.YOUNG_CONSOLIDATING -> youngRecipe()
            DrillProtocol.MATURE_AUDIT -> matureRecipe()
        }

    fun protocolFor(stability: Double, state: FsrsCardState): DrillProtocol =
        when {
            state == FsrsCardState.RELEARNING || stability < 3.0 -> DrillProtocol.LAPSED_DEEP_IMPRINT
            state == FsrsCardState.REVIEW && stability > 30.0 -> DrillProtocol.MATURE_AUDIT
            else -> DrillProtocol.YOUNG_CONSOLIDATING
        }

    /** 3 steps / 9 reps deep imprint for lapsed pages. */
    fun lapsedRecipe(): DrillRecipe {
        val steps = listOf(
            DrillStep(
                description = "Slow stare: trace each line eyes-open",
                reps = 3,
                stareMs = 2000L,
                chimeMode = DrillChimeMode.START
            ),
            DrillStep(
                description = "Eyes closed: recite from visual memory",
                reps = 3,
                stareMs = 0L,
                chimeMode = DrillChimeMode.EYES_CLOSED
            ),
            DrillStep(
                description = "Eyes open verify plus final imprint stare",
                reps = 3,
                stareMs = 2000L,
                chimeMode = DrillChimeMode.COMPLETE
            )
        )
        return DrillRecipe(
            protocol = DrillProtocol.LAPSED_DEEP_IMPRINT,
            steps = steps,
            totalReps = 9,
            estSeconds = estimateSeconds(steps)
        )
    }

    /** 2 steps / 5 reps consolidation for young pages. */
    fun youngRecipe(): DrillRecipe {
        val steps = listOf(
            DrillStep(
                description = "Focused stare plus recite",
                reps = 3,
                stareMs = 2000L,
                chimeMode = DrillChimeMode.START
            ),
            DrillStep(
                description = "Eyes closed recall plus verify",
                reps = 2,
                stareMs = 0L,
                chimeMode = DrillChimeMode.COMPLETE
            )
        )
        return DrillRecipe(
            protocol = DrillProtocol.YOUNG_CONSOLIDATING,
            steps = steps,
            totalReps = 5,
            estSeconds = estimateSeconds(steps)
        )
    }

    /** 1 step / 2 reps quick audit for mature pages. */
    fun matureRecipe(): DrillRecipe {
        val steps = listOf(
            DrillStep(
                description = "Quick visual audit recite",
                reps = 2,
                stareMs = 2000L,
                chimeMode = DrillChimeMode.COMPLETE
            )
        )
        return DrillRecipe(
            protocol = DrillProtocol.MATURE_AUDIT,
            steps = steps,
            totalReps = 2,
            estSeconds = estimateSeconds(steps)
        )
    }

    private fun estimateSeconds(steps: List<DrillStep>): Int {
        val perRepSeconds = 8
        var total = 0
        for (s in steps) {
            total += s.reps * perRepSeconds + (s.stareMs * s.reps / 1000).toInt()
        }
        return total
    }
}
