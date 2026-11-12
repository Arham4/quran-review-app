package com.quranmastery.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CurriculumDao {
    @Query("SELECT * FROM curriculum_state WHERE id = 1")
    suspend fun getState(): CurriculumStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertState(state: CurriculumStateEntity)

    @Query("SELECT * FROM review_plan WHERE id = 1")
    suspend fun getPlan(): ReviewPlanEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPlan(plan: ReviewPlanEntity)
}

@Dao
interface LessonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: DailyLessonLogEntity): Long

    @Query("SELECT * FROM daily_lesson_log ORDER BY createdAtMs DESC LIMIT :limit")
    suspend fun recentLogs(limit: Int = 30): List<DailyLessonLogEntity>

    @Query("SELECT * FROM daily_lesson_log WHERE dateIso = :dateIso LIMIT 1")
    suspend fun logForDate(dateIso: String): DailyLessonLogEntity?

    @Query("SELECT COUNT(*) FROM daily_lesson_log")
    suspend fun count(): Int
}

@Dao
interface VisualDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(card: VisualZoneCardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(cards: List<VisualZoneCardEntity>)

    @Update
    suspend fun update(card: VisualZoneCardEntity)

    @Query("SELECT * FROM visual_zone_cards WHERE id = :id LIMIT 1")
    suspend fun byId(id: String): VisualZoneCardEntity?

    @Query("SELECT * FROM visual_zone_cards WHERE pageNumber = :page ORDER BY zone")
    suspend fun byPage(page: Int): List<VisualZoneCardEntity>

    @Query("SELECT * FROM visual_zone_cards WHERE isEnabled = 1 AND dueMs <= :nowMs ORDER BY dueMs ASC LIMIT :limit")
    suspend fun dueCards(nowMs: Long, limit: Int = 50): List<VisualZoneCardEntity>

    @Query("SELECT COUNT(*) FROM visual_zone_cards WHERE isEnabled = 1 AND dueMs <= :nowMs")
    suspend fun dueCount(nowMs: Long): Int

    @Query("SELECT COUNT(*) FROM visual_zone_cards")
    suspend fun count(): Int

    @Query("UPDATE visual_zone_cards SET isEnabled = 1 WHERE pageNumber BETWEEN :fromPage AND :toPage")
    suspend fun enablePages(fromPage: Int, toPage: Int)

    @Query("UPDATE visual_zone_cards SET isEnabled = 0 WHERE pageNumber BETWEEN :fromPage AND :toPage")
    suspend fun disablePages(fromPage: Int, toPage: Int)
}

@Dao
interface TafseerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(card: TafseerClusterCardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(cards: List<TafseerClusterCardEntity>)

    @Update
    suspend fun update(card: TafseerClusterCardEntity)

    @Query("SELECT * FROM tafseer_cluster_cards WHERE isEnabled = 1 AND dueMs <= :nowMs ORDER BY dueMs ASC LIMIT :limit")
    suspend fun dueCards(nowMs: Long, limit: Int = 50): List<TafseerClusterCardEntity>

    @Query("SELECT COUNT(*) FROM tafseer_cluster_cards WHERE isEnabled = 1 AND dueMs <= :nowMs")
    suspend fun dueCount(nowMs: Long): Int

    @Query("SELECT COUNT(*) FROM tafseer_cluster_cards")
    suspend fun count(): Int

    @Query("SELECT * FROM tafseer_cluster_cards WHERE isStaged = 1")
    suspend fun staged(): List<TafseerClusterCardEntity>

    @Query("UPDATE tafseer_cluster_cards SET isEnabled = 1, isStaged = 0 WHERE isStaged = 1")
    suspend fun promoteStaged(): Int
}

@Dao
interface ReviewLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: FsrsReviewLogEntity): Long

    @Query("SELECT * FROM fsrs_review_log WHERE cardId = :cardId ORDER BY reviewedAtMs DESC LIMIT :limit")
    suspend fun forCard(cardId: String, limit: Int = 20): List<FsrsReviewLogEntity>

    @Query("SELECT COUNT(*) FROM fsrs_review_log")
    suspend fun count(): Int
}

@Dao
interface FuzzyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun flag(flag: FuzzyPageFlagEntity)

    @Query("DELETE FROM fuzzy_page_flags WHERE pageNumber = :page")
    suspend fun clear(page: Int)

    @Query("SELECT * FROM fuzzy_page_flags ORDER BY pageNumber")
    suspend fun all(): List<FuzzyPageFlagEntity>

    @Query("SELECT COUNT(*) FROM fuzzy_page_flags")
    suspend fun count(): Int
}
