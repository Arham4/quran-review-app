package com.quranmastery.app.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "curriculum_state")
data class CurriculumStateEntity(
    @PrimaryKey val id: Int = 1,
    val sabakWindowStartSurah: Int = 2,
    val sabakWindowStartAyah: Int = 1,
    val sabakWindowEndSurah: Int = 2,
    val sabakWindowEndAyah: Int = 33,
    val sabakNextCursorSurah: Int = 2,
    val sabakNextCursorAyah: Int = 34,
    val currentHizbNumber: Int = 57,
    val enabledHizbsCsv: String = "53,54,55,56,57,58,59,60",
    val lessonDaysCsv: String = "MON,WED,FRI",
    val visualTargetRetention: Float = 0.9f,
    val tafseerTargetRetention: Float = 0.9f
)

@Entity(tableName = "review_plan")
data class ReviewPlanEntity(
    @PrimaryKey val id: Int = 1,
    // Locked next-lesson target: ADVANCE means move to nextHizbNumber, REPEAT means redo current.
    val mode: String = "ADVANCE",
    val nextHizbNumber: Int = 58,
    val repeatHizbNumber: Int = 57,
    val lockedAtMs: Long = 0L,
    val note: String = "default advance Hizb58"
)

@Entity(tableName = "daily_lesson_log")
data class DailyLessonLogEntity(
    @PrimaryKey(autoGenerate = true) val logId: Long = 0L,
    val dateIso: String = "",
    val hizbNumber: Int = 0,
    val hizbOutcome: String = "PASSED",
    val sabakStartSurah: Int = 0,
    val sabakStartAyah: Int = 0,
    val sabakEndSurah: Int = 0,
    val sabakEndAyah: Int = 0,
    val sabakOutcome: String = "PASSED",
    val fuzzyPagesCsv: String = "",
    val createdAtMs: Long = 0L
)

@Entity(tableName = "visual_zone_cards")
data class VisualZoneCardEntity(
    @PrimaryKey val id: String = "",
    val pageNumber: Int = 1,
    val zone: String = "TOP",
    val lineStart: Int = 1,
    val lineEnd: Int = 5,
    val juzNumber: Int = 1,
    val hizbNumber: Int = 1,
    val isEnabled: Boolean = false,
    // FSRS state
    val fsrsState: String = "NEW",
    val stability: Float = 0f,
    val difficulty: Float = 0f,
    val elapsedDays: Int = 0,
    val scheduledDays: Int = 0,
    val reps: Int = 0,
    val lapses: Int = 0,
    val lastReviewMs: Long = 0L,
    val dueMs: Long = 0L,
    val anchorNotes: String = "",
    val lineStartWordsJson: String = "[]",
    val hardLineNumber: Int = 3
)

@Entity(tableName = "fuzzy_page_flags")
data class FuzzyPageFlagEntity(
    @PrimaryKey val pageNumber: Int = 1,
    val reason: String = "FUZZY",
    val createdAtMs: Long = 0L
)

@Entity(tableName = "tafseer_cluster_cards")
data class TafseerClusterCardEntity(
    @PrimaryKey val id: String = "",
    val surahNumber: Int = 1,
    val ayahStart: Int = 1,
    val ayahEnd: Int = 1,
    val pageNumber: Int = 1,
    val hizbNumber: Int = 1,
    val theme: String = "",
    val bulletsJson: String = "[]",
    val isEnabled: Boolean = false,
    val isStaged: Boolean = false,
    // FSRS state
    val fsrsState: String = "NEW",
    val stability: Float = 0f,
    val difficulty: Float = 0f,
    val reps: Int = 0,
    val lapses: Int = 0,
    val lastReviewMs: Long = 0L,
    val dueMs: Long = 0L
)

@Entity(tableName = "fsrs_review_log")
data class FsrsReviewLogEntity(
    @PrimaryKey(autoGenerate = true) val logId: Long = 0L,
    val cardId: String = "",
    val cardType: String = "VISUAL",
    val rating: Int = 3,
    val reviewedAtMs: Long = 0L,
    val scheduledDays: Int = 0,
    val stabilityBefore: Float = 0f,
    val stabilityAfter: Float = 0f
)
