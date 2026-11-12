package com.quranmastery.app.domain.fsrs

import kotlin.math.exp
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round

/**
 * Pure-Kotlin FSRS v6 scheduler (simplified but mathematically sane).
 *
 * No Android dependencies. Deterministic and test-friendly:
 * all date math flows through the caller-supplied [nowMs].
 */
object FsrsV6Scheduler {

    const val DECAY: Double = -0.5
    const val FACTOR: Double = 0.9

    const val MIN_DIFFICULTY: Double = 1.0
    const val MAX_DIFFICULTY: Double = 10.0
    const val MIN_STABILITY: Double = 0.01

    /** Stability floor applied after a fuzzy page lapse. */
    const val LAPSE_STABILITY_FLOOR: Double = 0.5

    const val MILLIS_PER_DAY: Long = 24L * 60L * 60L * 1000L
    const val AGAIN_RELEARN_MILLIS: Long = 10L * 60L * 1000L
    const val NEW_AGAIN_MILLIS: Long = 5L * 60L * 1000L
    const val NEW_HARD_MILLIS: Long = 10L * 60L * 1000L

    /**
     * Standard FSRS-6 default weights w0..w20 (21 params).
     * w0..w3 init stability, w4..w7 difficulty, w8..w10 recall stability,
     * w11..w14 forget stability, w15..w16 hard/easy boost, w17..w20 short-term.
     */
    val DEFAULT_WEIGHTS: DoubleArray = doubleArrayOf(
        0.212, // w0  init S(Again)
        1.2931, // w1  init S(Hard)
        2.3065, // w2  init S(Good)
        8.2956, // w3  init S(Easy)
        6.4133, // w4  init difficulty base
        0.8334, // w5  difficulty vs rating slope
        3.0194, // w6  difficulty change step
        0.001, // w7  mean reversion weight
        1.8729, // w8  recall stability scale
        0.1666, // w9  recall stability S exponent
        0.796, // w10 recall stability R exponent
        1.4835, // w11 forget stability scale
        0.0614, // w12 forget stability D exponent
        0.2629, // w13 forget stability S exponent
        1.6483, // w14 forget stability R exponent
        0.6014, // w15 hard recall boost
        1.8729, // w16 easy recall boost
        0.5425, // w17 short-term Again
        0.0912, // w18 short-term Hard
        0.0658, // w19 short-term Good
        0.1542 // w20 short-term Easy
    )

    private fun w(i: Int, weights: DoubleArray = DEFAULT_WEIGHTS): Double = weights[i]

    /** Initial stability S0(G) = w[G-1]. */
    fun initialStability(rating: FsrsRating, weights: DoubleArray = DEFAULT_WEIGHTS): Double =
        max(MIN_STABILITY, w(rating.grade - 1, weights))

    /** Initial difficulty D0(G) = w4 - (G-3)*w5, clamped to [1,10]. */
    fun initialDifficulty(rating: FsrsRating, weights: DoubleArray = DEFAULT_WEIGHTS): Double =
        clampDifficulty(w(4, weights) - (rating.grade - 3) * w(5, weights))

    /** Forgetting curve R(t,S) = (1 + FACTOR * t / S)^DECAY. */
    fun forgettingCurve(elapsedDays: Double, stability: Double): Double {
        if (stability < MIN_STABILITY) return 0.0
        val t = max(0.0, elapsedDays)
        return (1.0 + FACTOR * t / stability).pow(DECAY)
    }

    /** Next interval in days for [stability] at [targetRetention]. */
    fun nextIntervalDays(stability: Double, targetRetention: Double): Double {
        val r = targetRetention.coerceIn(0.70, 0.99)
        val s = max(MIN_STABILITY, stability)
        return s / FACTOR * (r.pow(1.0 / DECAY) - 1.0)
    }

    /** Recall stability update after a successful review. */
    fun nextRecallStability(
        stability: Double,
        difficulty: Double,
        retrievability: Double,
        rating: FsrsRating,
        weights: DoubleArray = DEFAULT_WEIGHTS
    ): Double {
        val s = max(MIN_STABILITY, stability)
        val d = clampDifficulty(difficulty)
        val r = retrievability.coerceIn(0.01, 1.0)
        val hardPenalty = if (rating == FsrsRating.Hard) w(15, weights) else 1.0
        val easyBonus = if (rating == FsrsRating.Easy) w(16, weights) else 1.0
        val ratingFactor = hardPenalty * easyBonus
        val boost = exp(w(8, weights)) *
            (11.0 - d) *
            s.pow(w(9, weights)) *
            (exp((1.0 - r) * w(10, weights)) - 1.0) *
            ratingFactor
        return max(MIN_STABILITY, s * (1.0 + max(0.0, boost)))
    }

    /** Forget stability update after a lapse (Again). */
    fun nextForgetStability(
        stability: Double,
        difficulty: Double,
        retrievability: Double,
        weights: DoubleArray = DEFAULT_WEIGHTS
    ): Double {
        val s = max(MIN_STABILITY, stability)
        val d = clampDifficulty(difficulty)
        val r = retrievability.coerceIn(0.01, 1.0)
        val value = w(11, weights) *
            d.pow(-w(12, weights)) *
            ((s + 1.0).pow(w(13, weights)) - 1.0) *
            exp((1.0 - r) * w(14, weights))
        return max(MIN_STABILITY, value)
    }

    /** Next difficulty with linear step + mean reversion toward w4. */
    fun nextDifficulty(
        difficulty: Double,
        rating: FsrsRating,
        weights: DoubleArray = DEFAULT_WEIGHTS
    ): Double {
        val d = if (difficulty <= 0.0) initialDifficulty(rating, weights) else clampDifficulty(difficulty)
        val stepped = d - w(6, weights) * (rating.grade - 3)
        val clamped = clampDifficulty(stepped)
        // Mean reversion toward base difficulty w4 (weight w7 is tiny by default).
        val reverted = (1.0 - w(7, weights)) * clamped + w(7, weights) * w(4, weights)
        return clampDifficulty(reverted)
    }

    fun clampDifficulty(d: Double): Double = min(MAX_DIFFICULTY, max(MIN_DIFFICULTY, d))

    /**
     * Schedule the next review for [card] with [rating] at [nowMs].
     *
     * @param targetRetention desired retention in (0,1), default 0.86.
     */
    fun schedule(
        card: FsrsCardData,
        rating: FsrsRating,
        nowMs: Long,
        targetRetention: Double = 0.86,
        weights: DoubleArray = DEFAULT_WEIGHTS
    ): FsrsReviewResult {
        val elapsedDays = if (card.lastReviewMs > 0L) {
            max(0.0, (nowMs - card.lastReviewMs).toDouble() / MILLIS_PER_DAY)
        } else {
            0.0
        }
        val retrievability = if (card.state == FsrsCardState.NEW || card.stability < MIN_STABILITY) {
            1.0
        } else {
            forgettingCurve(elapsedDays, card.stability)
        }

        val isFirst = card.reps == 0 || card.state == FsrsCardState.NEW
        val nextDifficulty = if (isFirst && card.difficulty <= 0.0) {
            initialDifficulty(rating, weights)
        } else {
            nextDifficulty(if (card.difficulty <= 0.0) initialDifficulty(rating, weights) else card.difficulty, rating, weights)
        }

        return if (isFirst) {
            scheduleNewCard(card, rating, nowMs, nextDifficulty, targetRetention, weights)
        } else {
            scheduleReview(card, rating, nowMs, elapsedDays, retrievability, nextDifficulty, targetRetention, weights)
        }
    }

    private fun scheduleNewCard(
        card: FsrsCardData,
        rating: FsrsRating,
        nowMs: Long,
        nextDifficulty: Double,
        targetRetention: Double,
        weights: DoubleArray
    ): FsrsReviewResult {
        val stability = initialStability(rating, weights)
        return when (rating) {
            FsrsRating.Again -> {
                val next = card.copy(
                    stability = stability,
                    difficulty = nextDifficulty,
                    elapsedDays = 0,
                    scheduledDays = 0,
                    reps = card.reps + 1,
                    lapses = card.lapses,
                    state = FsrsCardState.LEARNING,
                    lastReviewMs = nowMs,
                    dueMs = nowMs + NEW_AGAIN_MILLIS
                )
                FsrsReviewResult(next, NEW_AGAIN_MILLIS.toDouble() / MILLIS_PER_DAY, 1.0)
            }
            FsrsRating.Hard -> {
                val next = card.copy(
                    stability = stability,
                    difficulty = nextDifficulty,
                    elapsedDays = 0,
                    scheduledDays = 0,
                    reps = card.reps + 1,
                    lapses = card.lapses,
                    state = FsrsCardState.LEARNING,
                    lastReviewMs = nowMs,
                    dueMs = nowMs + NEW_HARD_MILLIS
                )
                FsrsReviewResult(next, NEW_HARD_MILLIS.toDouble() / MILLIS_PER_DAY, 1.0)
            }
            FsrsRating.Good, FsrsRating.Easy -> {
                val interval = max(1.0, round(nextIntervalDays(stability, targetRetention)).toDouble())
                val next = card.copy(
                    stability = stability,
                    difficulty = nextDifficulty,
                    elapsedDays = 0,
                    scheduledDays = interval.toInt(),
                    reps = card.reps + 1,
                    lapses = card.lapses,
                    state = FsrsCardState.REVIEW,
                    lastReviewMs = nowMs,
                    dueMs = nowMs + (interval * MILLIS_PER_DAY).toLong()
                )
                FsrsReviewResult(next, interval, 1.0)
            }
        }
    }

    private fun scheduleReview(
        card: FsrsCardData,
        rating: FsrsRating,
        nowMs: Long,
        elapsedDays: Double,
        retrievability: Double,
        nextDifficulty: Double,
        targetRetention: Double,
        weights: DoubleArray
    ): FsrsReviewResult {
        return when (card.state) {
            FsrsCardState.LEARNING -> scheduleLearning(card, rating, nowMs, nextDifficulty, targetRetention, weights)
            FsrsCardState.REVIEW, FsrsCardState.RELEARNING, FsrsCardState.NEW ->
                scheduleMature(card, rating, nowMs, elapsedDays, retrievability, nextDifficulty, targetRetention, weights)
        }
    }

    private fun scheduleLearning(
        card: FsrsCardData,
        rating: FsrsRating,
        nowMs: Long,
        nextDifficulty: Double,
        targetRetention: Double,
        weights: DoubleArray
    ): FsrsReviewResult {
        return when (rating) {
            FsrsRating.Again -> {
                val s = max(MIN_STABILITY, initialStability(rating, weights))
                val next = card.copy(
                    stability = s,
                    difficulty = nextDifficulty,
                    elapsedDays = 0,
                    scheduledDays = 0,
                    reps = card.reps + 1,
                    lapses = card.lapses,
                    state = FsrsCardState.LEARNING,
                    lastReviewMs = nowMs,
                    dueMs = nowMs + NEW_AGAIN_MILLIS
                )
                FsrsReviewResult(next, NEW_AGAIN_MILLIS.toDouble() / MILLIS_PER_DAY, 1.0)
            }
            FsrsRating.Hard -> {
                val s = max(MIN_STABILITY, (card.stability + initialStability(rating, weights)) / 2.0)
                val next = card.copy(
                    stability = s,
                    difficulty = nextDifficulty,
                    elapsedDays = 0,
                    scheduledDays = 0,
                    reps = card.reps + 1,
                    lapses = card.lapses,
                    state = FsrsCardState.LEARNING,
                    lastReviewMs = nowMs,
                    dueMs = nowMs + NEW_HARD_MILLIS
                )
                FsrsReviewResult(next, NEW_HARD_MILLIS.toDouble() / MILLIS_PER_DAY, 1.0)
            }
            FsrsRating.Good, FsrsRating.Easy -> {
                val base = if (card.stability < MIN_STABILITY) initialStability(rating, weights) else card.stability
                val r = forgettingCurve(0.0, base)
                val s = nextRecallStability(base, nextDifficulty, r, rating, weights)
                val interval = max(1.0, round(nextIntervalDays(s, targetRetention)).toDouble())
                val next = card.copy(
                    stability = s,
                    difficulty = nextDifficulty,
                    elapsedDays = 0,
                    scheduledDays = interval.toInt(),
                    reps = card.reps + 1,
                    lapses = card.lapses,
                    state = FsrsCardState.REVIEW,
                    lastReviewMs = nowMs,
                    dueMs = nowMs + (interval * MILLIS_PER_DAY).toLong()
                )
                FsrsReviewResult(next, interval, r)
            }
        }
    }

    private fun scheduleMature(
        card: FsrsCardData,
        rating: FsrsRating,
        nowMs: Long,
        elapsedDays: Double,
        retrievability: Double,
        nextDifficulty: Double,
        targetRetention: Double,
        weights: DoubleArray
    ): FsrsReviewResult {
        if (rating == FsrsRating.Again) {
            val s = nextForgetStability(card.stability, nextDifficulty, retrievability, weights)
            val next = card.copy(
                stability = max(MIN_STABILITY, s),
                difficulty = nextDifficulty,
                elapsedDays = elapsedDays.toInt(),
                scheduledDays = 0,
                reps = card.reps + 1,
                lapses = card.lapses + 1,
                state = FsrsCardState.RELEARNING,
                lastReviewMs = nowMs,
                dueMs = nowMs + AGAIN_RELEARN_MILLIS
            )
            return FsrsReviewResult(next, AGAIN_RELEARN_MILLIS.toDouble() / MILLIS_PER_DAY, retrievability)
        }
        val s = nextRecallStability(card.stability, nextDifficulty, retrievability, rating, weights)
        val rawInterval = nextIntervalDays(s, targetRetention)
        val interval = max(1.0, round(rawInterval).toDouble())
        val nextState = if (card.state == FsrsCardState.RELEARNING && rating == FsrsRating.Hard) {
            FsrsCardState.RELEARNING
        } else {
            FsrsCardState.REVIEW
        }
        val next = card.copy(
            stability = s,
            difficulty = nextDifficulty,
            elapsedDays = elapsedDays.toInt(),
            scheduledDays = interval.toInt(),
            reps = card.reps + 1,
            lapses = card.lapses,
            state = nextState,
            lastReviewMs = nowMs,
            dueMs = nowMs + (interval * MILLIS_PER_DAY).toLong()
        )
        return FsrsReviewResult(next, interval, retrievability)
    }

    /**
     * Fuzzy page lapse injection: forces an Again lapse, bumps [FsrsCardData.lapses]
     * and floors stability at [LAPSE_STABILITY_FLOOR].
     */
    fun injectFuzzyPageLapse(
        card: FsrsCardData,
        nowMs: Long,
        targetRetention: Double = 0.86,
        weights: DoubleArray = DEFAULT_WEIGHTS
    ): FsrsReviewResult {
        // Force mature-path lapse even for NEW/LEARNING cards.
        val reviewLike = if (card.state == FsrsCardState.NEW || card.state == FsrsCardState.LEARNING) {
            card.copy(
                stability = max(card.stability, initialStability(FsrsRating.Good, weights)),
                difficulty = if (card.difficulty <= 0.0) initialDifficulty(FsrsRating.Good, weights) else card.difficulty,
                state = FsrsCardState.REVIEW,
                lastReviewMs = if (card.lastReviewMs == 0L) nowMs - MILLIS_PER_DAY else card.lastReviewMs
            )
        } else {
            card
        }
        val lapsed = schedule(reviewLike, FsrsRating.Again, nowMs, targetRetention, weights)
        val floored = max(lapsed.card.stability, LAPSE_STABILITY_FLOOR)
        val fixed = lapsed.card.copy(
            stability = floored,
            lapses = max(lapsed.card.lapses, card.lapses + 1),
            state = FsrsCardState.RELEARNING
        )
        return lapsed.copy(card = fixed)
    }
}
