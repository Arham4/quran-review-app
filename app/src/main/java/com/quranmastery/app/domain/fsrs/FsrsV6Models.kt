package com.quranmastery.app.domain.fsrs

/** FSRS card learning state. */
enum class FsrsCardState {
    NEW,
    LEARNING,
    REVIEW,
    RELEARNING
}

/** FSRS review rating. Ordinal values match FSRS grades 1..4. */
enum class FsrsRating(val grade: Int) {
    Again(1),
    Hard(2),
    Good(3),
    Easy(4);

    companion object {
        fun fromGrade(grade: Int): FsrsRating =
            entries.first { it.grade == grade }
    }
}

/** Persistable FSRS memory state for one card. */
data class FsrsCardData(
    val stability: Double = 0.0,
    val difficulty: Double = 0.0,
    val elapsedDays: Int = 0,
    val scheduledDays: Int = 0,
    val reps: Int = 0,
    val lapses: Int = 0,
    val state: FsrsCardState = FsrsCardState.NEW,
    val lastReviewMs: Long = 0L,
    val dueMs: Long = 0L
)

/** Result of scheduling one review. */
data class FsrsReviewResult(
    val card: FsrsCardData,
    val intervalDays: Double,
    val retrievability: Double
)
