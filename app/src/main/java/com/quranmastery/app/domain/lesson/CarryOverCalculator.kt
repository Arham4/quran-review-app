package com.quranmastery.app.domain.lesson

/** Outcome of a review lesson used for carry-over planning. */
enum class ReviewLessonOutcome {
    PASSED_FULL_TARGET,
    REPEAT_FULL_TARGET,
    STOPPED_EARLY_CARRYOVER
}

/** Surah:ayah coordinate (1-based). Comparable in mushaf order. */
data class AyahCoord(val surah: Int, val ayah: Int) : Comparable<AyahCoord> {
    override fun compareTo(other: AyahCoord): Int {
        val c = surah.compareTo(other.surah)
        return if (c != 0) c else ayah.compareTo(other.ayah)
    }

    override fun toString(): String = "$surah:$ayah"
}

/** Inclusive ayah range. */
data class AyahRange(
    val start: AyahCoord,
    val endInclusive: AyahCoord
) {
    override fun toString(): String = "$start-$endInclusive"
}

/** A hizb's full ayah coverage. */
data class HizbRange(
    val hizb: Int,
    val start: AyahCoord,
    val endInclusive: AyahCoord
)

/** Proposed next lesson with carry-over detail. */
data class NextLessonProposal(
    val outcome: ReviewLessonOutcome,
    val currentHizb: Int,
    val targetHizb: Int,
    /** Full target range to revise next. */
    val target: AyahRange,
    /** Unfinished remainder of the current hizb (only for STOPPED_EARLY). */
    val carryOver: AyahRange? = null
)

/**
 * Pure-Kotlin carry-over calculator. Depends only on Kotlin stdlib plus the
 * data classes in this file; the hizb table is injected as a lookup lambda so
 * there is no dependency on metadata providers.
 */
object CarryOverCalculator {

    /**
     * After passing the full target: advance to the next hizb in [enabledHizbs]
     * (sorted, wrap-around) and target its full range.
     */
    fun proposeAfterPassed(
        currentHizb: Int,
        enabledHizbs: List<Int>,
        hizbLookup: (Int) -> HizbRange?
    ): NextLessonProposal {
        val next = nextHizbInPool(currentHizb, enabledHizbs)
        val range = requireNotNull(hizbLookup(next)) { "Unknown hizb $next" }
        return NextLessonProposal(
            outcome = ReviewLessonOutcome.PASSED_FULL_TARGET,
            currentHizb = currentHizb,
            targetHizb = next,
            target = AyahRange(range.start, range.endInclusive)
        )
    }

    /** After failing / choosing repeat: keep the same hizb full range. */
    fun proposeRepeat(
        currentHizb: Int,
        hizbLookup: (Int) -> HizbRange?
    ): NextLessonProposal {
        val range = requireNotNull(hizbLookup(currentHizb)) { "Unknown hizb $currentHizb" }
        return NextLessonProposal(
            outcome = ReviewLessonOutcome.REPEAT_FULL_TARGET,
            currentHizb = currentHizb,
            targetHizb = currentHizb,
            target = AyahRange(range.start, range.endInclusive)
        )
    }

    /**
     * Stopped early at [actualStopExclusive] (first un-revised ayah).
     * Carry-over = [actualStopExclusive]..end of current hizb.
     * Combined next target = [actualStopExclusive]..end of next hizb in pool.
     *
     * @param plannedStart start of the planned lesson (kept for API clarity / logging).
     */
    fun proposeStoppedEarly(
        plannedStart: AyahCoord,
        actualStopExclusive: AyahCoord,
        currentHizb: Int,
        enabledHizbs: List<Int>,
        hizbLookup: (Int) -> HizbRange?
    ): NextLessonProposal {
        require(actualStopExclusive > plannedStart) { "actualStop must be after plannedStart" }
        val current = requireNotNull(hizbLookup(currentHizb)) { "Unknown hizb $currentHizb" }
        val next = nextHizbInPool(currentHizb, enabledHizbs)
        val nextRange = requireNotNull(hizbLookup(next)) { "Unknown hizb $next" }

        val carry = AyahRange(actualStopExclusive, current.endInclusive)
        val combined = AyahRange(actualStopExclusive, nextRange.endInclusive)
        return NextLessonProposal(
            outcome = ReviewLessonOutcome.STOPPED_EARLY_CARRYOVER,
            currentHizb = currentHizb,
            targetHizb = next,
            target = combined,
            carryOver = carry
        )
    }

    /** Next hizb in [pool] after [current], sorted ascending with wrap-around. */
    fun nextHizbInPool(current: Int, pool: List<Int>): Int {
        require(pool.isNotEmpty()) { "pool must not be empty" }
        val sorted = pool.sorted()
        val idx = sorted.indexOf(current)
        if (idx == -1) {
            // Current not in pool: pick first hizb greater than current, else wrap.
            return sorted.firstOrNull { it > current } ?: sorted.first()
        }
        return sorted[(idx + 1) % sorted.size]
    }
}
