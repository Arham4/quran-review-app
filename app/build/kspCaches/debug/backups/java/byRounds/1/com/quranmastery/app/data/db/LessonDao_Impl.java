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
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class LessonDao_Impl implements LessonDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<DailyLessonLogEntity> __insertionAdapterOfDailyLessonLogEntity;

  public LessonDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDailyLessonLogEntity = new EntityInsertionAdapter<DailyLessonLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `daily_lesson_log` (`logId`,`dateIso`,`hizbNumber`,`hizbOutcome`,`sabakStartSurah`,`sabakStartAyah`,`sabakEndSurah`,`sabakEndAyah`,`sabakOutcome`,`fuzzyPagesCsv`,`createdAtMs`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyLessonLogEntity entity) {
        statement.bindLong(1, entity.getLogId());
        statement.bindString(2, entity.getDateIso());
        statement.bindLong(3, entity.getHizbNumber());
        statement.bindString(4, entity.getHizbOutcome());
        statement.bindLong(5, entity.getSabakStartSurah());
        statement.bindLong(6, entity.getSabakStartAyah());
        statement.bindLong(7, entity.getSabakEndSurah());
        statement.bindLong(8, entity.getSabakEndAyah());
        statement.bindString(9, entity.getSabakOutcome());
        statement.bindString(10, entity.getFuzzyPagesCsv());
        statement.bindLong(11, entity.getCreatedAtMs());
      }
    };
  }

  @Override
  public Object insertLog(final DailyLessonLogEntity log,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfDailyLessonLogEntity.insertAndReturnId(log);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object recentLogs(final int limit,
      final Continuation<? super List<DailyLessonLogEntity>> $completion) {
    final String _sql = "SELECT * FROM daily_lesson_log ORDER BY createdAtMs DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<DailyLessonLogEntity>>() {
      @Override
      @NonNull
      public List<DailyLessonLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLogId = CursorUtil.getColumnIndexOrThrow(_cursor, "logId");
          final int _cursorIndexOfDateIso = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIso");
          final int _cursorIndexOfHizbNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "hizbNumber");
          final int _cursorIndexOfHizbOutcome = CursorUtil.getColumnIndexOrThrow(_cursor, "hizbOutcome");
          final int _cursorIndexOfSabakStartSurah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakStartSurah");
          final int _cursorIndexOfSabakStartAyah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakStartAyah");
          final int _cursorIndexOfSabakEndSurah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakEndSurah");
          final int _cursorIndexOfSabakEndAyah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakEndAyah");
          final int _cursorIndexOfSabakOutcome = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakOutcome");
          final int _cursorIndexOfFuzzyPagesCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "fuzzyPagesCsv");
          final int _cursorIndexOfCreatedAtMs = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtMs");
          final List<DailyLessonLogEntity> _result = new ArrayList<DailyLessonLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final DailyLessonLogEntity _item;
            final long _tmpLogId;
            _tmpLogId = _cursor.getLong(_cursorIndexOfLogId);
            final String _tmpDateIso;
            _tmpDateIso = _cursor.getString(_cursorIndexOfDateIso);
            final int _tmpHizbNumber;
            _tmpHizbNumber = _cursor.getInt(_cursorIndexOfHizbNumber);
            final String _tmpHizbOutcome;
            _tmpHizbOutcome = _cursor.getString(_cursorIndexOfHizbOutcome);
            final int _tmpSabakStartSurah;
            _tmpSabakStartSurah = _cursor.getInt(_cursorIndexOfSabakStartSurah);
            final int _tmpSabakStartAyah;
            _tmpSabakStartAyah = _cursor.getInt(_cursorIndexOfSabakStartAyah);
            final int _tmpSabakEndSurah;
            _tmpSabakEndSurah = _cursor.getInt(_cursorIndexOfSabakEndSurah);
            final int _tmpSabakEndAyah;
            _tmpSabakEndAyah = _cursor.getInt(_cursorIndexOfSabakEndAyah);
            final String _tmpSabakOutcome;
            _tmpSabakOutcome = _cursor.getString(_cursorIndexOfSabakOutcome);
            final String _tmpFuzzyPagesCsv;
            _tmpFuzzyPagesCsv = _cursor.getString(_cursorIndexOfFuzzyPagesCsv);
            final long _tmpCreatedAtMs;
            _tmpCreatedAtMs = _cursor.getLong(_cursorIndexOfCreatedAtMs);
            _item = new DailyLessonLogEntity(_tmpLogId,_tmpDateIso,_tmpHizbNumber,_tmpHizbOutcome,_tmpSabakStartSurah,_tmpSabakStartAyah,_tmpSabakEndSurah,_tmpSabakEndAyah,_tmpSabakOutcome,_tmpFuzzyPagesCsv,_tmpCreatedAtMs);
            _result.add(_item);
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
  public Object logForDate(final String dateIso,
      final Continuation<? super DailyLessonLogEntity> $completion) {
    final String _sql = "SELECT * FROM daily_lesson_log WHERE dateIso = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, dateIso);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<DailyLessonLogEntity>() {
      @Override
      @Nullable
      public DailyLessonLogEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLogId = CursorUtil.getColumnIndexOrThrow(_cursor, "logId");
          final int _cursorIndexOfDateIso = CursorUtil.getColumnIndexOrThrow(_cursor, "dateIso");
          final int _cursorIndexOfHizbNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "hizbNumber");
          final int _cursorIndexOfHizbOutcome = CursorUtil.getColumnIndexOrThrow(_cursor, "hizbOutcome");
          final int _cursorIndexOfSabakStartSurah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakStartSurah");
          final int _cursorIndexOfSabakStartAyah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakStartAyah");
          final int _cursorIndexOfSabakEndSurah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakEndSurah");
          final int _cursorIndexOfSabakEndAyah = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakEndAyah");
          final int _cursorIndexOfSabakOutcome = CursorUtil.getColumnIndexOrThrow(_cursor, "sabakOutcome");
          final int _cursorIndexOfFuzzyPagesCsv = CursorUtil.getColumnIndexOrThrow(_cursor, "fuzzyPagesCsv");
          final int _cursorIndexOfCreatedAtMs = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtMs");
          final DailyLessonLogEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpLogId;
            _tmpLogId = _cursor.getLong(_cursorIndexOfLogId);
            final String _tmpDateIso;
            _tmpDateIso = _cursor.getString(_cursorIndexOfDateIso);
            final int _tmpHizbNumber;
            _tmpHizbNumber = _cursor.getInt(_cursorIndexOfHizbNumber);
            final String _tmpHizbOutcome;
            _tmpHizbOutcome = _cursor.getString(_cursorIndexOfHizbOutcome);
            final int _tmpSabakStartSurah;
            _tmpSabakStartSurah = _cursor.getInt(_cursorIndexOfSabakStartSurah);
            final int _tmpSabakStartAyah;
            _tmpSabakStartAyah = _cursor.getInt(_cursorIndexOfSabakStartAyah);
            final int _tmpSabakEndSurah;
            _tmpSabakEndSurah = _cursor.getInt(_cursorIndexOfSabakEndSurah);
            final int _tmpSabakEndAyah;
            _tmpSabakEndAyah = _cursor.getInt(_cursorIndexOfSabakEndAyah);
            final String _tmpSabakOutcome;
            _tmpSabakOutcome = _cursor.getString(_cursorIndexOfSabakOutcome);
            final String _tmpFuzzyPagesCsv;
            _tmpFuzzyPagesCsv = _cursor.getString(_cursorIndexOfFuzzyPagesCsv);
            final long _tmpCreatedAtMs;
            _tmpCreatedAtMs = _cursor.getLong(_cursorIndexOfCreatedAtMs);
            _result = new DailyLessonLogEntity(_tmpLogId,_tmpDateIso,_tmpHizbNumber,_tmpHizbOutcome,_tmpSabakStartSurah,_tmpSabakStartAyah,_tmpSabakEndSurah,_tmpSabakEndAyah,_tmpSabakOutcome,_tmpFuzzyPagesCsv,_tmpCreatedAtMs);
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
  public Object count(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM daily_lesson_log";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
