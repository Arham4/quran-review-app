package com.quranmastery.app.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile CurriculumDao _curriculumDao;

  private volatile LessonDao _lessonDao;

  private volatile VisualDao _visualDao;

  private volatile TafseerDao _tafseerDao;

  private volatile ReviewLogDao _reviewLogDao;

  private volatile FuzzyDao _fuzzyDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `curriculum_state` (`id` INTEGER NOT NULL, `sabakWindowStartSurah` INTEGER NOT NULL, `sabakWindowStartAyah` INTEGER NOT NULL, `sabakWindowEndSurah` INTEGER NOT NULL, `sabakWindowEndAyah` INTEGER NOT NULL, `sabakNextCursorSurah` INTEGER NOT NULL, `sabakNextCursorAyah` INTEGER NOT NULL, `currentHizbNumber` INTEGER NOT NULL, `enabledHizbsCsv` TEXT NOT NULL, `lessonDaysCsv` TEXT NOT NULL, `visualTargetRetention` REAL NOT NULL, `tafseerTargetRetention` REAL NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `review_plan` (`id` INTEGER NOT NULL, `mode` TEXT NOT NULL, `nextHizbNumber` INTEGER NOT NULL, `repeatHizbNumber` INTEGER NOT NULL, `lockedAtMs` INTEGER NOT NULL, `note` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `daily_lesson_log` (`logId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `dateIso` TEXT NOT NULL, `hizbNumber` INTEGER NOT NULL, `hizbOutcome` TEXT NOT NULL, `sabakStartSurah` INTEGER NOT NULL, `sabakStartAyah` INTEGER NOT NULL, `sabakEndSurah` INTEGER NOT NULL, `sabakEndAyah` INTEGER NOT NULL, `sabakOutcome` TEXT NOT NULL, `fuzzyPagesCsv` TEXT NOT NULL, `createdAtMs` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `visual_zone_cards` (`id` TEXT NOT NULL, `pageNumber` INTEGER NOT NULL, `zone` TEXT NOT NULL, `lineStart` INTEGER NOT NULL, `lineEnd` INTEGER NOT NULL, `juzNumber` INTEGER NOT NULL, `hizbNumber` INTEGER NOT NULL, `isEnabled` INTEGER NOT NULL, `fsrsState` TEXT NOT NULL, `stability` REAL NOT NULL, `difficulty` REAL NOT NULL, `elapsedDays` INTEGER NOT NULL, `scheduledDays` INTEGER NOT NULL, `reps` INTEGER NOT NULL, `lapses` INTEGER NOT NULL, `lastReviewMs` INTEGER NOT NULL, `dueMs` INTEGER NOT NULL, `anchorNotes` TEXT NOT NULL, `lineStartWordsJson` TEXT NOT NULL, `hardLineNumber` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `fuzzy_page_flags` (`pageNumber` INTEGER NOT NULL, `reason` TEXT NOT NULL, `createdAtMs` INTEGER NOT NULL, PRIMARY KEY(`pageNumber`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `tafseer_cluster_cards` (`id` TEXT NOT NULL, `surahNumber` INTEGER NOT NULL, `ayahStart` INTEGER NOT NULL, `ayahEnd` INTEGER NOT NULL, `pageNumber` INTEGER NOT NULL, `hizbNumber` INTEGER NOT NULL, `theme` TEXT NOT NULL, `bulletsJson` TEXT NOT NULL, `isEnabled` INTEGER NOT NULL, `isStaged` INTEGER NOT NULL, `fsrsState` TEXT NOT NULL, `stability` REAL NOT NULL, `difficulty` REAL NOT NULL, `reps` INTEGER NOT NULL, `lapses` INTEGER NOT NULL, `lastReviewMs` INTEGER NOT NULL, `dueMs` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `fsrs_review_log` (`logId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `cardId` TEXT NOT NULL, `cardType` TEXT NOT NULL, `rating` INTEGER NOT NULL, `reviewedAtMs` INTEGER NOT NULL, `scheduledDays` INTEGER NOT NULL, `stabilityBefore` REAL NOT NULL, `stabilityAfter` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd839c66c82ffdb7139aeb64afb317ea9')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `curriculum_state`");
        db.execSQL("DROP TABLE IF EXISTS `review_plan`");
        db.execSQL("DROP TABLE IF EXISTS `daily_lesson_log`");
        db.execSQL("DROP TABLE IF EXISTS `visual_zone_cards`");
        db.execSQL("DROP TABLE IF EXISTS `fuzzy_page_flags`");
        db.execSQL("DROP TABLE IF EXISTS `tafseer_cluster_cards`");
        db.execSQL("DROP TABLE IF EXISTS `fsrs_review_log`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsCurriculumState = new HashMap<String, TableInfo.Column>(12);
        _columnsCurriculumState.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurriculumState.put("sabakWindowStartSurah", new TableInfo.Column("sabakWindowStartSurah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurriculumState.put("sabakWindowStartAyah", new TableInfo.Column("sabakWindowStartAyah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurriculumState.put("sabakWindowEndSurah", new TableInfo.Column("sabakWindowEndSurah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurriculumState.put("sabakWindowEndAyah", new TableInfo.Column("sabakWindowEndAyah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurriculumState.put("sabakNextCursorSurah", new TableInfo.Column("sabakNextCursorSurah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurriculumState.put("sabakNextCursorAyah", new TableInfo.Column("sabakNextCursorAyah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurriculumState.put("currentHizbNumber", new TableInfo.Column("currentHizbNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurriculumState.put("enabledHizbsCsv", new TableInfo.Column("enabledHizbsCsv", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurriculumState.put("lessonDaysCsv", new TableInfo.Column("lessonDaysCsv", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurriculumState.put("visualTargetRetention", new TableInfo.Column("visualTargetRetention", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCurriculumState.put("tafseerTargetRetention", new TableInfo.Column("tafseerTargetRetention", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCurriculumState = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCurriculumState = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCurriculumState = new TableInfo("curriculum_state", _columnsCurriculumState, _foreignKeysCurriculumState, _indicesCurriculumState);
        final TableInfo _existingCurriculumState = TableInfo.read(db, "curriculum_state");
        if (!_infoCurriculumState.equals(_existingCurriculumState)) {
          return new RoomOpenHelper.ValidationResult(false, "curriculum_state(com.quranmastery.app.data.db.CurriculumStateEntity).\n"
                  + " Expected:\n" + _infoCurriculumState + "\n"
                  + " Found:\n" + _existingCurriculumState);
        }
        final HashMap<String, TableInfo.Column> _columnsReviewPlan = new HashMap<String, TableInfo.Column>(6);
        _columnsReviewPlan.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReviewPlan.put("mode", new TableInfo.Column("mode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReviewPlan.put("nextHizbNumber", new TableInfo.Column("nextHizbNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReviewPlan.put("repeatHizbNumber", new TableInfo.Column("repeatHizbNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReviewPlan.put("lockedAtMs", new TableInfo.Column("lockedAtMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReviewPlan.put("note", new TableInfo.Column("note", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReviewPlan = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReviewPlan = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReviewPlan = new TableInfo("review_plan", _columnsReviewPlan, _foreignKeysReviewPlan, _indicesReviewPlan);
        final TableInfo _existingReviewPlan = TableInfo.read(db, "review_plan");
        if (!_infoReviewPlan.equals(_existingReviewPlan)) {
          return new RoomOpenHelper.ValidationResult(false, "review_plan(com.quranmastery.app.data.db.ReviewPlanEntity).\n"
                  + " Expected:\n" + _infoReviewPlan + "\n"
                  + " Found:\n" + _existingReviewPlan);
        }
        final HashMap<String, TableInfo.Column> _columnsDailyLessonLog = new HashMap<String, TableInfo.Column>(11);
        _columnsDailyLessonLog.put("logId", new TableInfo.Column("logId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyLessonLog.put("dateIso", new TableInfo.Column("dateIso", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyLessonLog.put("hizbNumber", new TableInfo.Column("hizbNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyLessonLog.put("hizbOutcome", new TableInfo.Column("hizbOutcome", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyLessonLog.put("sabakStartSurah", new TableInfo.Column("sabakStartSurah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyLessonLog.put("sabakStartAyah", new TableInfo.Column("sabakStartAyah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyLessonLog.put("sabakEndSurah", new TableInfo.Column("sabakEndSurah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyLessonLog.put("sabakEndAyah", new TableInfo.Column("sabakEndAyah", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyLessonLog.put("sabakOutcome", new TableInfo.Column("sabakOutcome", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyLessonLog.put("fuzzyPagesCsv", new TableInfo.Column("fuzzyPagesCsv", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDailyLessonLog.put("createdAtMs", new TableInfo.Column("createdAtMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDailyLessonLog = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesDailyLessonLog = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoDailyLessonLog = new TableInfo("daily_lesson_log", _columnsDailyLessonLog, _foreignKeysDailyLessonLog, _indicesDailyLessonLog);
        final TableInfo _existingDailyLessonLog = TableInfo.read(db, "daily_lesson_log");
        if (!_infoDailyLessonLog.equals(_existingDailyLessonLog)) {
          return new RoomOpenHelper.ValidationResult(false, "daily_lesson_log(com.quranmastery.app.data.db.DailyLessonLogEntity).\n"
                  + " Expected:\n" + _infoDailyLessonLog + "\n"
                  + " Found:\n" + _existingDailyLessonLog);
        }
        final HashMap<String, TableInfo.Column> _columnsVisualZoneCards = new HashMap<String, TableInfo.Column>(20);
        _columnsVisualZoneCards.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("pageNumber", new TableInfo.Column("pageNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("zone", new TableInfo.Column("zone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("lineStart", new TableInfo.Column("lineStart", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("lineEnd", new TableInfo.Column("lineEnd", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("juzNumber", new TableInfo.Column("juzNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("hizbNumber", new TableInfo.Column("hizbNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("isEnabled", new TableInfo.Column("isEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("fsrsState", new TableInfo.Column("fsrsState", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("stability", new TableInfo.Column("stability", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("difficulty", new TableInfo.Column("difficulty", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("elapsedDays", new TableInfo.Column("elapsedDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("scheduledDays", new TableInfo.Column("scheduledDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("reps", new TableInfo.Column("reps", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("lapses", new TableInfo.Column("lapses", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("lastReviewMs", new TableInfo.Column("lastReviewMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("dueMs", new TableInfo.Column("dueMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("anchorNotes", new TableInfo.Column("anchorNotes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("lineStartWordsJson", new TableInfo.Column("lineStartWordsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVisualZoneCards.put("hardLineNumber", new TableInfo.Column("hardLineNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVisualZoneCards = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVisualZoneCards = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVisualZoneCards = new TableInfo("visual_zone_cards", _columnsVisualZoneCards, _foreignKeysVisualZoneCards, _indicesVisualZoneCards);
        final TableInfo _existingVisualZoneCards = TableInfo.read(db, "visual_zone_cards");
        if (!_infoVisualZoneCards.equals(_existingVisualZoneCards)) {
          return new RoomOpenHelper.ValidationResult(false, "visual_zone_cards(com.quranmastery.app.data.db.VisualZoneCardEntity).\n"
                  + " Expected:\n" + _infoVisualZoneCards + "\n"
                  + " Found:\n" + _existingVisualZoneCards);
        }
        final HashMap<String, TableInfo.Column> _columnsFuzzyPageFlags = new HashMap<String, TableInfo.Column>(3);
        _columnsFuzzyPageFlags.put("pageNumber", new TableInfo.Column("pageNumber", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFuzzyPageFlags.put("reason", new TableInfo.Column("reason", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFuzzyPageFlags.put("createdAtMs", new TableInfo.Column("createdAtMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFuzzyPageFlags = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFuzzyPageFlags = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFuzzyPageFlags = new TableInfo("fuzzy_page_flags", _columnsFuzzyPageFlags, _foreignKeysFuzzyPageFlags, _indicesFuzzyPageFlags);
        final TableInfo _existingFuzzyPageFlags = TableInfo.read(db, "fuzzy_page_flags");
        if (!_infoFuzzyPageFlags.equals(_existingFuzzyPageFlags)) {
          return new RoomOpenHelper.ValidationResult(false, "fuzzy_page_flags(com.quranmastery.app.data.db.FuzzyPageFlagEntity).\n"
                  + " Expected:\n" + _infoFuzzyPageFlags + "\n"
                  + " Found:\n" + _existingFuzzyPageFlags);
        }
        final HashMap<String, TableInfo.Column> _columnsTafseerClusterCards = new HashMap<String, TableInfo.Column>(17);
        _columnsTafseerClusterCards.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("surahNumber", new TableInfo.Column("surahNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("ayahStart", new TableInfo.Column("ayahStart", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("ayahEnd", new TableInfo.Column("ayahEnd", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("pageNumber", new TableInfo.Column("pageNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("hizbNumber", new TableInfo.Column("hizbNumber", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("theme", new TableInfo.Column("theme", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("bulletsJson", new TableInfo.Column("bulletsJson", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("isEnabled", new TableInfo.Column("isEnabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("isStaged", new TableInfo.Column("isStaged", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("fsrsState", new TableInfo.Column("fsrsState", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("stability", new TableInfo.Column("stability", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("difficulty", new TableInfo.Column("difficulty", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("reps", new TableInfo.Column("reps", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("lapses", new TableInfo.Column("lapses", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("lastReviewMs", new TableInfo.Column("lastReviewMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTafseerClusterCards.put("dueMs", new TableInfo.Column("dueMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTafseerClusterCards = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTafseerClusterCards = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTafseerClusterCards = new TableInfo("tafseer_cluster_cards", _columnsTafseerClusterCards, _foreignKeysTafseerClusterCards, _indicesTafseerClusterCards);
        final TableInfo _existingTafseerClusterCards = TableInfo.read(db, "tafseer_cluster_cards");
        if (!_infoTafseerClusterCards.equals(_existingTafseerClusterCards)) {
          return new RoomOpenHelper.ValidationResult(false, "tafseer_cluster_cards(com.quranmastery.app.data.db.TafseerClusterCardEntity).\n"
                  + " Expected:\n" + _infoTafseerClusterCards + "\n"
                  + " Found:\n" + _existingTafseerClusterCards);
        }
        final HashMap<String, TableInfo.Column> _columnsFsrsReviewLog = new HashMap<String, TableInfo.Column>(8);
        _columnsFsrsReviewLog.put("logId", new TableInfo.Column("logId", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFsrsReviewLog.put("cardId", new TableInfo.Column("cardId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFsrsReviewLog.put("cardType", new TableInfo.Column("cardType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFsrsReviewLog.put("rating", new TableInfo.Column("rating", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFsrsReviewLog.put("reviewedAtMs", new TableInfo.Column("reviewedAtMs", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFsrsReviewLog.put("scheduledDays", new TableInfo.Column("scheduledDays", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFsrsReviewLog.put("stabilityBefore", new TableInfo.Column("stabilityBefore", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFsrsReviewLog.put("stabilityAfter", new TableInfo.Column("stabilityAfter", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFsrsReviewLog = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFsrsReviewLog = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFsrsReviewLog = new TableInfo("fsrs_review_log", _columnsFsrsReviewLog, _foreignKeysFsrsReviewLog, _indicesFsrsReviewLog);
        final TableInfo _existingFsrsReviewLog = TableInfo.read(db, "fsrs_review_log");
        if (!_infoFsrsReviewLog.equals(_existingFsrsReviewLog)) {
          return new RoomOpenHelper.ValidationResult(false, "fsrs_review_log(com.quranmastery.app.data.db.FsrsReviewLogEntity).\n"
                  + " Expected:\n" + _infoFsrsReviewLog + "\n"
                  + " Found:\n" + _existingFsrsReviewLog);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d839c66c82ffdb7139aeb64afb317ea9", "b54f6a2525b3b896e44f422c50114b0d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "curriculum_state","review_plan","daily_lesson_log","visual_zone_cards","fuzzy_page_flags","tafseer_cluster_cards","fsrs_review_log");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `curriculum_state`");
      _db.execSQL("DELETE FROM `review_plan`");
      _db.execSQL("DELETE FROM `daily_lesson_log`");
      _db.execSQL("DELETE FROM `visual_zone_cards`");
      _db.execSQL("DELETE FROM `fuzzy_page_flags`");
      _db.execSQL("DELETE FROM `tafseer_cluster_cards`");
      _db.execSQL("DELETE FROM `fsrs_review_log`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CurriculumDao.class, CurriculumDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LessonDao.class, LessonDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(VisualDao.class, VisualDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TafseerDao.class, TafseerDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReviewLogDao.class, ReviewLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FuzzyDao.class, FuzzyDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public CurriculumDao curriculumDao() {
    if (_curriculumDao != null) {
      return _curriculumDao;
    } else {
      synchronized(this) {
        if(_curriculumDao == null) {
          _curriculumDao = new CurriculumDao_Impl(this);
        }
        return _curriculumDao;
      }
    }
  }

  @Override
  public LessonDao lessonDao() {
    if (_lessonDao != null) {
      return _lessonDao;
    } else {
      synchronized(this) {
        if(_lessonDao == null) {
          _lessonDao = new LessonDao_Impl(this);
        }
        return _lessonDao;
      }
    }
  }

  @Override
  public VisualDao visualDao() {
    if (_visualDao != null) {
      return _visualDao;
    } else {
      synchronized(this) {
        if(_visualDao == null) {
          _visualDao = new VisualDao_Impl(this);
        }
        return _visualDao;
      }
    }
  }

  @Override
  public TafseerDao tafseerDao() {
    if (_tafseerDao != null) {
      return _tafseerDao;
    } else {
      synchronized(this) {
        if(_tafseerDao == null) {
          _tafseerDao = new TafseerDao_Impl(this);
        }
        return _tafseerDao;
      }
    }
  }

  @Override
  public ReviewLogDao reviewLogDao() {
    if (_reviewLogDao != null) {
      return _reviewLogDao;
    } else {
      synchronized(this) {
        if(_reviewLogDao == null) {
          _reviewLogDao = new ReviewLogDao_Impl(this);
        }
        return _reviewLogDao;
      }
    }
  }

  @Override
  public FuzzyDao fuzzyDao() {
    if (_fuzzyDao != null) {
      return _fuzzyDao;
    } else {
      synchronized(this) {
        if(_fuzzyDao == null) {
          _fuzzyDao = new FuzzyDao_Impl(this);
        }
        return _fuzzyDao;
      }
    }
  }
}
