package com.quranmastery.app.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class VisualDao_Impl implements VisualDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<VisualZoneCardEntity> __insertionAdapterOfVisualZoneCardEntity;

  private final EntityDeletionOrUpdateAdapter<VisualZoneCardEntity> __updateAdapterOfVisualZoneCardEntity;

  private final SharedSQLiteStatement __preparedStmtOfEnablePages;

  private final SharedSQLiteStatement __preparedStmtOfDisablePages;

  public VisualDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVisualZoneCardEntity = new EntityInsertionAdapter<VisualZoneCardEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `visual_zone_cards` (`id`,`pageNumber`,`zone`,`lineStart`,`lineEnd`,`juzNumber`,`hizbNumber`,`isEnabled`,`fsrsState`,`stability`,`difficulty`,`elapsedDays`,`scheduledDays`,`reps`,`lapses`,`lastReviewMs`,`dueMs`,`anchorNotes`,`lineStartWordsJson`,`hardLineNumber`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VisualZoneCardEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindLong(2, entity.getPageNumber());
        statement.bindString(3, entity.getZone());
        statement.bindLong(4, entity.getLineStart());
        statement.bindLong(5, entity.getLineEnd());
        statement.bindLong(6, entity.getJuzNumber());
        statement.bindLong(7, entity.getHizbNumber());
        final int _tmp = entity.isEnabled() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindString(9, entity.getFsrsState());
        statement.bindDouble(10, entity.getStability());
        statement.bindDouble(11, entity.getDifficulty());
        statement.bindLong(12, entity.getElapsedDays());
        statement.bindLong(13, entity.getScheduledDays());
        statement.bindLong(14, entity.getReps());
        statement.bindLong(15, entity.getLapses());
        statement.bindLong(16, entity.getLastReviewMs());
        statement.bindLong(17, entity.getDueMs());
        statement.bindString(18, entity.getAnchorNotes());
        statement.bindString(19, entity.getLineStartWordsJson());
        statement.bindLong(20, entity.getHardLineNumber());
      }
    };
    this.__updateAdapterOfVisualZoneCardEntity = new EntityDeletionOrUpdateAdapter<VisualZoneCardEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `visual_zone_cards` SET `id` = ?,`pageNumber` = ?,`zone` = ?,`lineStart` = ?,`lineEnd` = ?,`juzNumber` = ?,`hizbNumber` = ?,`isEnabled` = ?,`fsrsState` = ?,`stability` = ?,`difficulty` = ?,`elapsedDays` = ?,`scheduledDays` = ?,`reps` = ?,`lapses` = ?,`lastReviewMs` = ?,`dueMs` = ?,`anchorNotes` = ?,`lineStartWordsJson` = ?,`hardLineNumber` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final VisualZoneCardEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindLong(2, entity.getPageNumber());
        statement.bindString(3, entity.getZone());
        statement.bindLong(4, entity.getLineStart());
        statement.bindLong(5, entity.getLineEnd());
        statement.bindLong(6, entity.getJuzNumber());
        statement.bindLong(7, entity.getHizbNumber());
        final int _tmp = entity.isEnabled() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindString(9, entity.getFsrsState());
        statement.bindDouble(10, entity.getStability());
        statement.bindDouble(11, entity.getDifficulty());
        statement.bindLong(12, entity.getElapsedDays());
        statement.bindLong(13, entity.getScheduledDays());
        statement.bindLong(14, entity.getReps());
        statement.bindLong(15, entity.getLapses());
        statement.bindLong(16, entity.getLastReviewMs());
        statement.bindLong(17, entity.getDueMs());
        statement.bindString(18, entity.getAnchorNotes());
        statement.bindString(19, entity.getLineStartWordsJson());
        statement.bindLong(20, entity.getHardLineNumber());
        statement.bindString(21, entity.getId());
      }
    };
    this.__preparedStmtOfEnablePages = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE visual_zone_cards SET isEnabled = 1 WHERE pageNumber BETWEEN ? AND ?";
        return _query;
      }
    };
    this.__preparedStmtOfDisablePages = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE visual_zone_cards SET isEnabled = 0 WHERE pageNumber BETWEEN ? AND ?";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final VisualZoneCardEntity card,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVisualZoneCardEntity.insert(card);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertAll(final List<VisualZoneCardEntity> cards,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVisualZoneCardEntity.insert(cards);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final VisualZoneCardEntity card,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfVisualZoneCardEntity.handle(card);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object enablePages(final int fromPage, final int toPage,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfEnablePages.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, fromPage);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, toPage);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfEnablePages.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object disablePages(final int fromPage, final int toPage,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDisablePages.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, fromPage);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, toPage);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDisablePages.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object byId(final String id,
      final Continuation<? super VisualZoneCardEntity> $completion) {
    final String _sql = "SELECT * FROM visual_zone_cards WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<VisualZoneCardEntity>() {
      @Override
      @Nullable
      public VisualZoneCardEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "pageNumber");
          final int _cursorIndexOfZone = CursorUtil.getColumnIndexOrThrow(_cursor, "zone");
          final int _cursorIndexOfLineStart = CursorUtil.getColumnIndexOrThrow(_cursor, "lineStart");
          final int _cursorIndexOfLineEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "lineEnd");
          final int _cursorIndexOfJuzNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "juzNumber");
          final int _cursorIndexOfHizbNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "hizbNumber");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final int _cursorIndexOfFsrsState = CursorUtil.getColumnIndexOrThrow(_cursor, "fsrsState");
          final int _cursorIndexOfStability = CursorUtil.getColumnIndexOrThrow(_cursor, "stability");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfElapsedDays = CursorUtil.getColumnIndexOrThrow(_cursor, "elapsedDays");
          final int _cursorIndexOfScheduledDays = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledDays");
          final int _cursorIndexOfReps = CursorUtil.getColumnIndexOrThrow(_cursor, "reps");
          final int _cursorIndexOfLapses = CursorUtil.getColumnIndexOrThrow(_cursor, "lapses");
          final int _cursorIndexOfLastReviewMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewMs");
          final int _cursorIndexOfDueMs = CursorUtil.getColumnIndexOrThrow(_cursor, "dueMs");
          final int _cursorIndexOfAnchorNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "anchorNotes");
          final int _cursorIndexOfLineStartWordsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "lineStartWordsJson");
          final int _cursorIndexOfHardLineNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "hardLineNumber");
          final VisualZoneCardEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
            final String _tmpZone;
            _tmpZone = _cursor.getString(_cursorIndexOfZone);
            final int _tmpLineStart;
            _tmpLineStart = _cursor.getInt(_cursorIndexOfLineStart);
            final int _tmpLineEnd;
            _tmpLineEnd = _cursor.getInt(_cursorIndexOfLineEnd);
            final int _tmpJuzNumber;
            _tmpJuzNumber = _cursor.getInt(_cursorIndexOfJuzNumber);
            final int _tmpHizbNumber;
            _tmpHizbNumber = _cursor.getInt(_cursorIndexOfHizbNumber);
            final boolean _tmpIsEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp != 0;
            final String _tmpFsrsState;
            _tmpFsrsState = _cursor.getString(_cursorIndexOfFsrsState);
            final float _tmpStability;
            _tmpStability = _cursor.getFloat(_cursorIndexOfStability);
            final float _tmpDifficulty;
            _tmpDifficulty = _cursor.getFloat(_cursorIndexOfDifficulty);
            final int _tmpElapsedDays;
            _tmpElapsedDays = _cursor.getInt(_cursorIndexOfElapsedDays);
            final int _tmpScheduledDays;
            _tmpScheduledDays = _cursor.getInt(_cursorIndexOfScheduledDays);
            final int _tmpReps;
            _tmpReps = _cursor.getInt(_cursorIndexOfReps);
            final int _tmpLapses;
            _tmpLapses = _cursor.getInt(_cursorIndexOfLapses);
            final long _tmpLastReviewMs;
            _tmpLastReviewMs = _cursor.getLong(_cursorIndexOfLastReviewMs);
            final long _tmpDueMs;
            _tmpDueMs = _cursor.getLong(_cursorIndexOfDueMs);
            final String _tmpAnchorNotes;
            _tmpAnchorNotes = _cursor.getString(_cursorIndexOfAnchorNotes);
            final String _tmpLineStartWordsJson;
            _tmpLineStartWordsJson = _cursor.getString(_cursorIndexOfLineStartWordsJson);
            final int _tmpHardLineNumber;
            _tmpHardLineNumber = _cursor.getInt(_cursorIndexOfHardLineNumber);
            _result = new VisualZoneCardEntity(_tmpId,_tmpPageNumber,_tmpZone,_tmpLineStart,_tmpLineEnd,_tmpJuzNumber,_tmpHizbNumber,_tmpIsEnabled,_tmpFsrsState,_tmpStability,_tmpDifficulty,_tmpElapsedDays,_tmpScheduledDays,_tmpReps,_tmpLapses,_tmpLastReviewMs,_tmpDueMs,_tmpAnchorNotes,_tmpLineStartWordsJson,_tmpHardLineNumber);
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
  public Object byPage(final int page,
      final Continuation<? super List<VisualZoneCardEntity>> $completion) {
    final String _sql = "SELECT * FROM visual_zone_cards WHERE pageNumber = ? ORDER BY zone";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, page);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VisualZoneCardEntity>>() {
      @Override
      @NonNull
      public List<VisualZoneCardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "pageNumber");
          final int _cursorIndexOfZone = CursorUtil.getColumnIndexOrThrow(_cursor, "zone");
          final int _cursorIndexOfLineStart = CursorUtil.getColumnIndexOrThrow(_cursor, "lineStart");
          final int _cursorIndexOfLineEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "lineEnd");
          final int _cursorIndexOfJuzNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "juzNumber");
          final int _cursorIndexOfHizbNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "hizbNumber");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final int _cursorIndexOfFsrsState = CursorUtil.getColumnIndexOrThrow(_cursor, "fsrsState");
          final int _cursorIndexOfStability = CursorUtil.getColumnIndexOrThrow(_cursor, "stability");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfElapsedDays = CursorUtil.getColumnIndexOrThrow(_cursor, "elapsedDays");
          final int _cursorIndexOfScheduledDays = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledDays");
          final int _cursorIndexOfReps = CursorUtil.getColumnIndexOrThrow(_cursor, "reps");
          final int _cursorIndexOfLapses = CursorUtil.getColumnIndexOrThrow(_cursor, "lapses");
          final int _cursorIndexOfLastReviewMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewMs");
          final int _cursorIndexOfDueMs = CursorUtil.getColumnIndexOrThrow(_cursor, "dueMs");
          final int _cursorIndexOfAnchorNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "anchorNotes");
          final int _cursorIndexOfLineStartWordsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "lineStartWordsJson");
          final int _cursorIndexOfHardLineNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "hardLineNumber");
          final List<VisualZoneCardEntity> _result = new ArrayList<VisualZoneCardEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VisualZoneCardEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
            final String _tmpZone;
            _tmpZone = _cursor.getString(_cursorIndexOfZone);
            final int _tmpLineStart;
            _tmpLineStart = _cursor.getInt(_cursorIndexOfLineStart);
            final int _tmpLineEnd;
            _tmpLineEnd = _cursor.getInt(_cursorIndexOfLineEnd);
            final int _tmpJuzNumber;
            _tmpJuzNumber = _cursor.getInt(_cursorIndexOfJuzNumber);
            final int _tmpHizbNumber;
            _tmpHizbNumber = _cursor.getInt(_cursorIndexOfHizbNumber);
            final boolean _tmpIsEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp != 0;
            final String _tmpFsrsState;
            _tmpFsrsState = _cursor.getString(_cursorIndexOfFsrsState);
            final float _tmpStability;
            _tmpStability = _cursor.getFloat(_cursorIndexOfStability);
            final float _tmpDifficulty;
            _tmpDifficulty = _cursor.getFloat(_cursorIndexOfDifficulty);
            final int _tmpElapsedDays;
            _tmpElapsedDays = _cursor.getInt(_cursorIndexOfElapsedDays);
            final int _tmpScheduledDays;
            _tmpScheduledDays = _cursor.getInt(_cursorIndexOfScheduledDays);
            final int _tmpReps;
            _tmpReps = _cursor.getInt(_cursorIndexOfReps);
            final int _tmpLapses;
            _tmpLapses = _cursor.getInt(_cursorIndexOfLapses);
            final long _tmpLastReviewMs;
            _tmpLastReviewMs = _cursor.getLong(_cursorIndexOfLastReviewMs);
            final long _tmpDueMs;
            _tmpDueMs = _cursor.getLong(_cursorIndexOfDueMs);
            final String _tmpAnchorNotes;
            _tmpAnchorNotes = _cursor.getString(_cursorIndexOfAnchorNotes);
            final String _tmpLineStartWordsJson;
            _tmpLineStartWordsJson = _cursor.getString(_cursorIndexOfLineStartWordsJson);
            final int _tmpHardLineNumber;
            _tmpHardLineNumber = _cursor.getInt(_cursorIndexOfHardLineNumber);
            _item = new VisualZoneCardEntity(_tmpId,_tmpPageNumber,_tmpZone,_tmpLineStart,_tmpLineEnd,_tmpJuzNumber,_tmpHizbNumber,_tmpIsEnabled,_tmpFsrsState,_tmpStability,_tmpDifficulty,_tmpElapsedDays,_tmpScheduledDays,_tmpReps,_tmpLapses,_tmpLastReviewMs,_tmpDueMs,_tmpAnchorNotes,_tmpLineStartWordsJson,_tmpHardLineNumber);
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
  public Object dueCards(final long nowMs, final int limit,
      final Continuation<? super List<VisualZoneCardEntity>> $completion) {
    final String _sql = "SELECT * FROM visual_zone_cards WHERE isEnabled = 1 AND dueMs <= ? ORDER BY dueMs ASC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, nowMs);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<VisualZoneCardEntity>>() {
      @Override
      @NonNull
      public List<VisualZoneCardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "pageNumber");
          final int _cursorIndexOfZone = CursorUtil.getColumnIndexOrThrow(_cursor, "zone");
          final int _cursorIndexOfLineStart = CursorUtil.getColumnIndexOrThrow(_cursor, "lineStart");
          final int _cursorIndexOfLineEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "lineEnd");
          final int _cursorIndexOfJuzNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "juzNumber");
          final int _cursorIndexOfHizbNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "hizbNumber");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final int _cursorIndexOfFsrsState = CursorUtil.getColumnIndexOrThrow(_cursor, "fsrsState");
          final int _cursorIndexOfStability = CursorUtil.getColumnIndexOrThrow(_cursor, "stability");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfElapsedDays = CursorUtil.getColumnIndexOrThrow(_cursor, "elapsedDays");
          final int _cursorIndexOfScheduledDays = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledDays");
          final int _cursorIndexOfReps = CursorUtil.getColumnIndexOrThrow(_cursor, "reps");
          final int _cursorIndexOfLapses = CursorUtil.getColumnIndexOrThrow(_cursor, "lapses");
          final int _cursorIndexOfLastReviewMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewMs");
          final int _cursorIndexOfDueMs = CursorUtil.getColumnIndexOrThrow(_cursor, "dueMs");
          final int _cursorIndexOfAnchorNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "anchorNotes");
          final int _cursorIndexOfLineStartWordsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "lineStartWordsJson");
          final int _cursorIndexOfHardLineNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "hardLineNumber");
          final List<VisualZoneCardEntity> _result = new ArrayList<VisualZoneCardEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final VisualZoneCardEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
            final String _tmpZone;
            _tmpZone = _cursor.getString(_cursorIndexOfZone);
            final int _tmpLineStart;
            _tmpLineStart = _cursor.getInt(_cursorIndexOfLineStart);
            final int _tmpLineEnd;
            _tmpLineEnd = _cursor.getInt(_cursorIndexOfLineEnd);
            final int _tmpJuzNumber;
            _tmpJuzNumber = _cursor.getInt(_cursorIndexOfJuzNumber);
            final int _tmpHizbNumber;
            _tmpHizbNumber = _cursor.getInt(_cursorIndexOfHizbNumber);
            final boolean _tmpIsEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp != 0;
            final String _tmpFsrsState;
            _tmpFsrsState = _cursor.getString(_cursorIndexOfFsrsState);
            final float _tmpStability;
            _tmpStability = _cursor.getFloat(_cursorIndexOfStability);
            final float _tmpDifficulty;
            _tmpDifficulty = _cursor.getFloat(_cursorIndexOfDifficulty);
            final int _tmpElapsedDays;
            _tmpElapsedDays = _cursor.getInt(_cursorIndexOfElapsedDays);
            final int _tmpScheduledDays;
            _tmpScheduledDays = _cursor.getInt(_cursorIndexOfScheduledDays);
            final int _tmpReps;
            _tmpReps = _cursor.getInt(_cursorIndexOfReps);
            final int _tmpLapses;
            _tmpLapses = _cursor.getInt(_cursorIndexOfLapses);
            final long _tmpLastReviewMs;
            _tmpLastReviewMs = _cursor.getLong(_cursorIndexOfLastReviewMs);
            final long _tmpDueMs;
            _tmpDueMs = _cursor.getLong(_cursorIndexOfDueMs);
            final String _tmpAnchorNotes;
            _tmpAnchorNotes = _cursor.getString(_cursorIndexOfAnchorNotes);
            final String _tmpLineStartWordsJson;
            _tmpLineStartWordsJson = _cursor.getString(_cursorIndexOfLineStartWordsJson);
            final int _tmpHardLineNumber;
            _tmpHardLineNumber = _cursor.getInt(_cursorIndexOfHardLineNumber);
            _item = new VisualZoneCardEntity(_tmpId,_tmpPageNumber,_tmpZone,_tmpLineStart,_tmpLineEnd,_tmpJuzNumber,_tmpHizbNumber,_tmpIsEnabled,_tmpFsrsState,_tmpStability,_tmpDifficulty,_tmpElapsedDays,_tmpScheduledDays,_tmpReps,_tmpLapses,_tmpLastReviewMs,_tmpDueMs,_tmpAnchorNotes,_tmpLineStartWordsJson,_tmpHardLineNumber);
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
  public Object dueCount(final long nowMs, final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM visual_zone_cards WHERE isEnabled = 1 AND dueMs <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, nowMs);
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

  @Override
  public Object count(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM visual_zone_cards";
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
