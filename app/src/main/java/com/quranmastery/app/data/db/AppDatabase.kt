package com.quranmastery.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        CurriculumStateEntity::class,
        ReviewPlanEntity::class,
        DailyLessonLogEntity::class,
        VisualZoneCardEntity::class,
        FuzzyPageFlagEntity::class,
        TafseerClusterCardEntity::class,
        FsrsReviewLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun curriculumDao(): CurriculumDao
    abstract fun lessonDao(): LessonDao
    abstract fun visualDao(): VisualDao
    abstract fun tafseerDao(): TafseerDao
    abstract fun reviewLogDao(): ReviewLogDao
    abstract fun fuzzyDao(): FuzzyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "quran_mastery.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
