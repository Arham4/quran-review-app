package com.quranmastery.app

import com.quranmastery.app.domain.fsrs.FsrsCardData
import com.quranmastery.app.domain.fsrs.FsrsCardState
import com.quranmastery.app.domain.fsrs.FsrsRating
import com.quranmastery.app.domain.fsrs.FsrsV6Scheduler
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.abs

class FsrsV6SchedulerTest {

    private val nowMs = 1_700_000_000_000L

    @Test
    fun `default weights have 21 params`() {
        assertEquals(21, FsrsV6Scheduler.DEFAULT_WEIGHTS.size)
    }

    @Test
    fun `initial stability ordered Again less than Hard less than Good less than Easy`() {
        val a = FsrsV6Scheduler.initialStability(FsrsRating.Again)
        val h = FsrsV6Scheduler.initialStability(FsrsRating.Hard)
        val g = FsrsV6Scheduler.initialStability(FsrsRating.Good)
        val e = FsrsV6Scheduler.initialStability(FsrsRating.Easy)
        assertTrue(a < h)
        assertTrue(h < g)
        assertTrue(g < e)
    }

    @Test
    fun `initial difficulty ordered Again hardest`() {
        val a = FsrsV6Scheduler.initialDifficulty(FsrsRating.Again)
        val e = FsrsV6Scheduler.initialDifficulty(FsrsRating.Easy)
        assertTrue(a > e)
        assertTrue(a in 1.0..10.0)
        assertTrue(e in 1.0..10.0)
    }

    @Test
    fun `forgetting curve decays with time and grows with stability`() {
        val r0 = FsrsV6Scheduler.forgettingCurve(0.0, 5.0)
        assertEquals(1.0, r0, 1e-9)
        val rLate = FsrsV6Scheduler.forgettingCurve(10.0, 5.0)
        assertTrue(rLate < 1.0 && rLate > 0.0)
        val rStable = FsrsV6Scheduler.forgettingCurve(10.0, 50.0)
        assertTrue(rStable > rLate)
    }

    @Test
    fun `new card Good graduates to REVIEW with interval of at least 1 day`() {
        val card = FsrsCardData(state = FsrsCardState.NEW)
        val res = FsrsV6Scheduler.schedule(card, FsrsRating.Good, nowMs)
        assertEquals(FsrsCardState.REVIEW, res.card.state)
        assertTrue(res.intervalDays >= 1.0)
        assertTrue(res.card.dueMs > nowMs)
        assertEquals(1, res.card.reps)
    }

    @Test
    fun `new card Again stays in LEARNING with short due`() {
        val card = FsrsCardData(state = FsrsCardState.NEW)
        val res = FsrsV6Scheduler.schedule(card, FsrsRating.Again, nowMs)
        assertEquals(FsrsCardState.LEARNING, res.card.state)
        assertEquals(nowMs + FsrsV6Scheduler.NEW_AGAIN_MILLIS, res.card.dueMs)
    }

    @Test
    fun `review Again moves to RELEARNING and bumps lapses`() {
        val card = FsrsCardData(
            stability = 10.0,
            difficulty = 5.0,
            reps = 5,
            lapses = 1,
            state = FsrsCardState.REVIEW,
            lastReviewMs = nowMs - FsrsV6Scheduler.MILLIS_PER_DAY * 5
        )
        val res = FsrsV6Scheduler.schedule(card, FsrsRating.Again, nowMs)
        assertEquals(FsrsCardState.RELEARNING, res.card.state)
        assertEquals(2, res.card.lapses)
        assertEquals(6, res.card.reps)
    }

    @Test
    fun `higher stability yields longer interval at same retention`() {
        val low = FsrsV6Scheduler.nextIntervalDays(5.0, 0.86)
        val high = FsrsV6Scheduler.nextIntervalDays(50.0, 0.86)
        assertTrue(high > low)
    }

    @Test
    fun `lower target retention yields longer interval`() {
        val strict = FsrsV6Scheduler.nextIntervalDays(10.0, 0.95)
        val lax = FsrsV6Scheduler.nextIntervalDays(10.0, 0.80)
        assertTrue(lax > strict)
    }

    @Test
    fun `recall stability grows after Good review`() {
        val s = FsrsV6Scheduler.nextRecallStability(10.0, 5.0, 0.86, FsrsRating.Good)
        assertTrue(s > 10.0)
    }

    @Test
    fun `injectFuzzyPageLapse forces RELEARNING lapses plus one and stability floor`() {
        val card = FsrsCardData(
            stability = 20.0,
            difficulty = 5.0,
            reps = 4,
            lapses = 0,
            state = FsrsCardState.REVIEW,
            lastReviewMs = nowMs - FsrsV6Scheduler.MILLIS_PER_DAY * 10
        )
        val res = FsrsV6Scheduler.injectFuzzyPageLapse(card, nowMs)
        assertEquals(FsrsCardState.RELEARNING, res.card.state)
        assertEquals(1, res.card.lapses)
        assertTrue(res.card.stability >= FsrsV6Scheduler.LAPSE_STABILITY_FLOOR)
    }

    @Test
    fun `scheduler is deterministic for same inputs`() {
        val card = FsrsCardData(
            stability = 8.0,
            difficulty = 6.0,
            reps = 3,
            state = FsrsCardState.REVIEW,
            lastReviewMs = nowMs - FsrsV6Scheduler.MILLIS_PER_DAY * 4
        )
        val r1 = FsrsV6Scheduler.schedule(card, FsrsRating.Good, nowMs)
        val r2 = FsrsV6Scheduler.schedule(card, FsrsRating.Good, nowMs)
        assertEquals(r1, r2)
        assertTrue(abs(r1.intervalDays - r2.intervalDays) < 1e-9)
    }
}
