package com.quranmastery.app.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CurriculumDao_Impl implements CurriculumDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CurriculumStateEntity> __insertionAdapterOfCurriculumStateEntity;

  private final EntityInsertionAdapter<ReviewPlanEntity> __insertionAdapterOfReviewPlanEntity;

  public CurriculumDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCurriculumStateEntity = new EntityInsertionAdapter<CurriculumStateEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `curriculum_state` (`id`,`sabakWindowStartSurah`,`sabakWindowStartAyah`,`sabakWindowEndSurah`,`sabakWindowEndAyah`,`sabakNextCursorSurah`,`sabakNextCursorAyah`,`currentHizbNumber`,`enabledHizbsCsv`,`lessonDaysCsv`,`visualTargetRetention`,`tafseerTargetRetention`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CurriculumStateEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSabakWindowStartSurah());
        statement.bindLong(3, entity.getSabakWindowStartAyah());
        statement.bindLong(4, entity.getSabakWindowEndSurah());
        statement.bindLong(5, entity.getSabakWindowEndAyah());
        statement.bindLong(6, entity.getSabakNextCursorSurah());
        statement.bindLong(7, entity.getSabakNextCursorAyah());
        statement.bindLong(8, entity.getCurrentHizbNumber());
        statement.bindString(9, entity.getEnabledHizbsCsv());
        statement.bindString(10, entity.getLessonDaysCsv());
        statement.bindDouble(11, entity.getVisualTargetRetention());
        statement.bindDouble(12, entity.getTafseerTargetRetention());
      }
    };
    this.__insertionAdapterOfReviewPlanEntity = new EntityInsertionAdapter<ReviewPlanEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `review_plan` (`id`,`mode`,`nextHizbNumber`,`repeatHizbNumber`,`lockedAtMs`,`note`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ReviewPlanEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getMode());
        statement.bindLong(3, entity.getNextHizbNumber());
        statement.bindLong(4, entity.getRepeatHizbNumber());
        statement.bindLong(5, entity.getLockedAtMs());
        statement.bindString(6, entity.getNote());
      }
    };
  }

  @Override
  public Object upsertState(final CurriculumStateEntity state,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCurriculumStateEntity.insert(state);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertPlan(final ReviewPlanEntity plan,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfReviewPlanEntity.insert(plan);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getState(final Continuation<? super CurriculumStateEntity> $completion) {
    final String _sql = "SELECT * FROM curriculum_state WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CurriculumStateEntity>() {
      @Override
      @Nullable
      public CurriculumStateEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSabakWindowStartSurah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakWindowStartSurah");
          final int _cursorIndexOfSabakWindowStartAyah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakWindowStartAyah");
          final int _cursorIndexOfSabakWindowEndSurah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakWindowEndSurah");
          final int _cursorIndexOfSabakWindowEndAyah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakWindowEndAyah");
          final int _cursorIndexOfSabakNextCursorSurah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakNextCursorSurah");
          final int _cursorIndexOfSabakNextCursorAyah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakNextCursorAyah");
          final int _cursorIndexOfCurrentHizbNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "currentHizbNumber");
          final int _cursorIndexOfEnabledHizbsCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "enabledHizbsCsv");
          final int _cursorIndexOfLessonDaysCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "lessonDaysCsv");
          final int _cursorIndexOfVisualTargetRetention = CursorUtil.getColumnIndexOrThrow(_cursor, "visualTargetRetention");
          final int _cursorIndexOfTafseerTargetRetention = CursorUtil.getColumnIndexOrThrow(_cursor, "tafseerTargetRetention");
          final CurriculumStateEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpSabakWindowStartSurah;
            _tmpSabakWindowStartSurah = _cursor.getInt(_cursorIndexOfSabakWindowStartSurah);
            final int _tmpSabakWindowStartAyah;
            _tmpSabakWindowStartAyah = _cursor.getInt(_cursorIndexOfSabakWindowStartAyah);
            final int _tmpSabakWindowEndSurah;
            _tmpSabakWindowEndSurah = _cursor.getInt(_cursorIndexOfSabakWindowEndSurah);
            final int _tmpSabakWindowEndAyah;
            _tmpSabakWindowEndAyah = _cursor.getInt(_cursorIndexOfSabakWindowEndAyah);
            final int _tmpSabakNextCursorSurah;
            _tmpSabakNextCursorSurah = _cursor.getInt(_cursorIndexOfSabakNextCursorSurah);
            final int _tmpSabakNextCursorAyah;
            _tmpSabakNextCursorAyah = _cursor.getInt(_cursorIndexOfSabakNextCursorAyah);
            final int _tmpCurrentHizbNumber;
            _tmpCurrentHizbNumber = _cursor.getInt(_cursorIndexOfCurrentHizbNumber);
            final String _tmpEnabledHizbsCsv;
            _tmpEnabledHizbsCsv = _cursor.getString(_cursorIndexOfEnabledHizbsCsv);
            final String _tmpLessonDaysCsv;
            _tmpLessonDaysCsv = _cursor.getString(_cursorIndexOfLessonDaysCsv);
            final float _tmpVisualTargetRetention;
            _tmpVisualTargetRetention = _cursor.getFloat(_cursorIndexOfVisualTargetRetention);
            final float _tmpTafseerTargetRetention;
            _tmpTafseerTargetRetention = _cursor.getFloat(_cursorIndexOfTafseerTargetRetention);
            _result = new CurriculumStateEntity(_tmpId,_tmpSabakWindowStartSurah,_tmpSabakWindowStartAyah,_tmpSabakWindowEndSurah,_tmpSabakWindowEndAyah,_tmpSabakNextCursorSurah,_tmpSabakNextCursorAyah,_tmpCurrentHizbNumber,_tmpEnabledHizbsCsv,_tmpLessonDaysCsv,_tmpVisualTargetRetention,_tmpTafseerTargetRetention);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPlan(final Continuation<? super ReviewPlanEntity> $completion) {
    final String _sql = "SELECT * FROM review_plan WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ReviewPlanEntity>() {
      @Override
      @Nullable
      public ReviewPlanEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMode = CursorUtil.getColumnIndexOrThrow(_cursor, "mode");
          final int _cursorIndexOfNextHizbNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "nextHizbNumber");
          final int _cursorIndexOfRepeatHizbNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "repeatHizbNumber");
          final int _cursorIndexOfLockedAtMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lockedAtMs");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final ReviewPlanEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpMode;
            _tmpMode = _cursor.getString(_cursorIndexOfMode);
            final int _tmpNextHizbNumber;
            _tmpNextHizbNumber = _cursor.getInt(_cursorIndexOfNextHizbNumber);
            final int _tmpRepeatHizbNumber;
            _tmpRepeatHizbNumber = _cursor.getInt(_cursorIndexOfRepeatHizbNumber);
            final long _tmpLockedAtMs;
            _tmpLockedAtMs = _cursor.getLong(_cursorIndexOfLockedAtMs);
            final String _tmpNote;
            _tmpNote = _cursor.getString(_cursorIndexOfNote);
            _result = new ReviewPlanEntity(_tmpId,_tmpMode,_tmpNextHizbNumber,_tmpRepeatHizbNumber,_tmpLockedAtMs,_tmpNote);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
