package com.quranmastery.app

import com.quranmastery.app.domain.lesson.AyahCoord
import com.quranmastery.app.domain.lesson.CarryOverCalculator
import com.quranmastery.app.domain.lesson.HizbRange
import com.quranmastery.app.domain.lesson.ReviewLessonOutcome
import org.junit.Assert.assertEquals
import org.junit.Test

class CarryOverCalculatorTest {

    private val hizbTable: Map<Int, HizbRange> = mapOf(
        53 to HizbRange(53, AyahCoord(51, 1), AyahCoord(54, 55)),
        54 to HizbRange(54, AyahCoord(55, 1), AyahCoord(58, 40)),
        55 to HizbRange(55, AyahCoord(58, 41), AyahCoord(62, 11)),
        56 to HizbRange(56, AyahCoord(62, 12), AyahCoord(66, 12)),
        57 to HizbRange(57, AyahCoord(67, 1), AyahCoord(71, 28)),
        58 to HizbRange(58, AyahCoord(72, 1), AyahCoord(77, 50)),
        59 to HizbRange(59, AyahCoord(78, 1), AyahCoord(87, 22)),
        60 to HizbRange(60, AyahCoord(88, 1), AyahCoord(114, 6))
    )
    private val lookup: (Int) -> HizbRange? = { hizbTable[it] }
    private val pool = listOf(53, 54, 55, 56, 57, 58, 59, 60)

    @Test
    fun `passed Hizb57 advances to Hizb58 full range`() {
        val proposal = CarryOverCalculator.proposeAfterPassed(57, pool, lookup)
        assertEquals(ReviewLessonOutcome.PASSED_FULL_TARGET, proposal.outcome)
        assertEquals(58, proposal.targetHizb)
        assertEquals("72:1-77:50", proposal.target.toString())
    }

    @Test
    fun `repeat keeps Hizb57 full range`() {
        val proposal = CarryOverCalculator.proposeRepeat(57, lookup)
        assertEquals(ReviewLessonOutcome.REPEAT_FULL_TARGET, proposal.outcome)
        assertEquals(57, proposal.targetHizb)
        assertEquals("67:1-71:28", proposal.target.toString())
    }

    @Test
    fun `stopped at 69-1 gives carry 69-1 to 71-28 and combined to 77-50`() {
        val proposal = CarryOverCalculator.proposeStoppedEarly(
            plannedStart = AyahCoord(67, 1),
            actualStopExclusive = AyahCoord(69, 1),
            currentHizb = 57,
            enabledHizbs = pool,
            hizbLookup = lookup
        )
        assertEquals(ReviewLessonOutcome.STOPPED_EARLY_CARRYOVER, proposal.outcome)
        assertEquals("69:1-71:28", proposal.carryOver.toString())
        assertEquals("69:1-77:50", proposal.target.toString())
    }

    @Test
    fun `wrap around Hizb60 goes to Hizb53`() {
        val proposal = CarryOverCalculator.proposeAfterPassed(60, pool, lookup)
        assertEquals(53, proposal.targetHizb)
        assertEquals("51:1-54:55", proposal.target.toString())
    }
}
