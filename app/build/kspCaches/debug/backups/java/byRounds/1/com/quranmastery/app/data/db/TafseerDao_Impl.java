package com.quranmastery.app.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
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
public final class TafseerDao_Impl implements TafseerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TafseerClusterCardEntity> __insertionAdapterOfTafseerClusterCardEntity;

  private final EntityDeletionOrUpdateAdapter<TafseerClusterCardEntity> __updateAdapterOfTafseerClusterCardEntity;

  private final SharedSQLiteStatement __preparedStmtOfPromoteStaged;

  public TafseerDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTafseerClusterCardEntity = new EntityInsertionAdapter<TafseerClusterCardEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `tafseer_cluster_cards` (`id`,`surahNumber`,`ayahStart`,`ayahEnd`,`pageNumber`,`hizbNumber`,`theme`,`bulletsJson`,`isEnabled`,`isStaged`,`fsrsState`,`stability`,`difficulty`,`reps`,`lapses`,`lastReviewMs`,`dueMs`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TafseerClusterCardEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindLong(2, entity.getSurahNumber());
        statement.bindLong(3, entity.getAyahStart());
        statement.bindLong(4, entity.getAyahEnd());
        statement.bindLong(5, entity.getPageNumber());
        statement.bindLong(6, entity.getHizbNumber());
        statement.bindString(7, entity.getTheme());
        statement.bindString(8, entity.getBulletsJson());
        final int _tmp = entity.isEnabled() ? 1 : 0;
        statement.bindLong(9, _tmp);
        final int _tmp_1 = entity.isStaged() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        statement.bindString(11, entity.getFsrsState());
        statement.bindDouble(12, entity.getStability());
        statement.bindDouble(13, entity.getDifficulty());
        statement.bindLong(14, entity.getReps());
        statement.bindLong(15, entity.getLapses());
        statement.bindLong(16, entity.getLastReviewMs());
        statement.bindLong(17, entity.getDueMs());
      }
    };
    this.__updateAdapterOfTafseerClusterCardEntity = new EntityDeletionOrUpdateAdapter<TafseerClusterCardEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tafseer_cluster_cards` SET `id` = ?,`surahNumber` = ?,`ayahStart` = ?,`ayahEnd` = ?,`pageNumber` = ?,`hizbNumber` = ?,`theme` = ?,`bulletsJson` = ?,`isEnabled` = ?,`isStaged` = ?,`fsrsState` = ?,`stability` = ?,`difficulty` = ?,`reps` = ?,`lapses` = ?,`lastReviewMs` = ?,`dueMs` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TafseerClusterCardEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindLong(2, entity.getSurahNumber());
        statement.bindLong(3, entity.getAyahStart());
        statement.bindLong(4, entity.getAyahEnd());
        statement.bindLong(5, entity.getPageNumber());
        statement.bindLong(6, entity.getHizbNumber());
        statement.bindString(7, entity.getTheme());
        statement.bindString(8, entity.getBulletsJson());
        final int _tmp = entity.isEnabled() ? 1 : 0;
        statement.bindLong(9, _tmp);
        final int _tmp_1 = entity.isStaged() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        statement.bindString(11, entity.getFsrsState());
        statement.bindDouble(12, entity.getStability());
        statement.bindDouble(13, entity.getDifficulty());
        statement.bindLong(14, entity.getReps());
        statement.bindLong(15, entity.getLapses());
        statement.bindLong(16, entity.getLastReviewMs());
        statement.bindLong(17, entity.getDueMs());
        statement.bindString(18, entity.getId());
      }
    };
    this.__preparedStmtOfPromoteStaged = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE tafseer_cluster_cards SET isEnabled = 1, isStaged = 0 WHERE isStaged = 1";
        return _query;
      }
    };
  }

  @Override
  public Object upsert(final TafseerClusterCardEntity card,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTafseerClusterCardEntity.insert(card);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertAll(final List<TafseerClusterCardEntity> cards,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTafseerClusterCardEntity.insert(cards);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final TafseerClusterCardEntity card,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTafseerClusterCardEntity.handle(card);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object promoteStaged(final Continuation<? super Integer> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfPromoteStaged.acquire();
        try {
          __db.beginTransaction();
          try {
            final Integer _result = _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfPromoteStaged.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object dueCards(final long nowMs, final int limit,
      final Continuation<? super List<TafseerClusterCardEntity>> $completion) {
    final String _sql = "SELECT * FROM tafseer_cluster_cards WHERE isEnabled = 1 AND dueMs <= ? ORDER BY dueMs ASC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, nowMs);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TafseerClusterCardEntity>>() {
      @Override
      @NonNull
      public List<TafseerClusterCardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSurahNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "surahNumber");
          final int _cursorIndexOfAyahStart = CursorUtil.getColumnIndexOrThrow(_cursor, "ayahStart");
          final int _cursorIndexOfAyahEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "ayahEnd");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "pageNumber");
          final int _cursorIndexOfHizbNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "hizbNumber");
          final int _cursorIndexOfTheme = CursorUtil.getColumnIndexOrThrow(_cursor, "theme");
          final int _cursorIndexOfBulletsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "bulletsJson");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final int _cursorIndexOfIsStaged = CursorUtil.getColumnIndexOrThrow(_cursor, "isStaged");
          final int _cursorIndexOfFsrsState = CursorUtil.getColumnIndexOrThrow(_cursor, "fsrsState");
          final int _cursorIndexOfStability = CursorUtil.getColumnIndexOrThrow(_cursor, "stability");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfReps = CursorUtil.getColumnIndexOrThrow(_cursor, "reps");
          final int _cursorIndexOfLapses = CursorUtil.getColumnIndexOrThrow(_cursor, "lapses");
          final int _cursorIndexOfLastReviewMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewMs");
          final int _cursorIndexOfDueMs = CursorUtil.getColumnIndexOrThrow(_cursor, "dueMs");
          final List<TafseerClusterCardEntity> _result = new ArrayList<TafseerClusterCardEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TafseerClusterCardEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final int _tmpSurahNumber;
            _tmpSurahNumber = _cursor.getInt(_cursorIndexOfSurahNumber);
            final int _tmpAyahStart;
            _tmpAyahStart = _cursor.getInt(_cursorIndexOfAyahStart);
            final int _tmpAyahEnd;
            _tmpAyahEnd = _cursor.getInt(_cursorIndexOfAyahEnd);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
            final int _tmpHizbNumber;
            _tmpHizbNumber = _cursor.getInt(_cursorIndexOfHizbNumber);
            final String _tmpTheme;
            _tmpTheme = _cursor.getString(_cursorIndexOfTheme);
            final String _tmpBulletsJson;
            _tmpBulletsJson = _cursor.getString(_cursorIndexOfBulletsJson);
            final boolean _tmpIsEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp != 0;
            final boolean _tmpIsStaged;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsStaged);
            _tmpIsStaged = _tmp_1 != 0;
            final String _tmpFsrsState;
            _tmpFsrsState = _cursor.getString(_cursorIndexOfFsrsState);
            final float _tmpStability;
            _tmpStability = _cursor.getFloat(_cursorIndexOfStability);
            final float _tmpDifficulty;
            _tmpDifficulty = _cursor.getFloat(_cursorIndexOfDifficulty);
            final int _tmpReps;
            _tmpReps = _cursor.getInt(_cursorIndexOfReps);
            final int _tmpLapses;
            _tmpLapses = _cursor.getInt(_cursorIndexOfLapses);
            final long _tmpLastReviewMs;
            _tmpLastReviewMs = _cursor.getLong(_cursorIndexOfLastReviewMs);
            final long _tmpDueMs;
            _tmpDueMs = _cursor.getLong(_cursorIndexOfDueMs);
            _item = new TafseerClusterCardEntity(_tmpId,_tmpSurahNumber,_tmpAyahStart,_tmpAyahEnd,_tmpPageNumber,_tmpHizbNumber,_tmpTheme,_tmpBulletsJson,_tmpIsEnabled,_tmpIsStaged,_tmpFsrsState,_tmpStability,_tmpDifficulty,_tmpReps,_tmpLapses,_tmpLastReviewMs,_tmpDueMs);
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
    final String _sql = "SELECT COUNT(*) FROM tafseer_cluster_cards WHERE isEnabled = 1 AND dueMs <= ?";
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
    final String _sql = "SELECT COUNT(*) FROM tafseer_cluster_cards";
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

  @Override
  public Object staged(final Continuation<? super List<TafseerClusterCardEntity>> $completion) {
    final String _sql = "SELECT * FROM tafseer_cluster_cards WHERE isStaged = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<TafseerClusterCardEntity>>() {
      @Override
      @NonNull
      public List<TafseerClusterCardEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSurahNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "surahNumber");
          final int _cursorIndexOfAyahStart = CursorUtil.getColumnIndexOrThrow(_cursor, "ayahStart");
          final int _cursorIndexOfAyahEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "ayahEnd");
          final int _cursorIndexOfPageNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "pageNumber");
          final int _cursorIndexOfHizbNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "hizbNumber");
          final int _cursorIndexOfTheme = CursorUtil.getColumnIndexOrThrow(_cursor, "theme");
          final int _cursorIndexOfBulletsJson = CursorUtil.getColumnIndexOrThrow(_cursor, "bulletsJson");
          final int _cursorIndexOfIsEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "isEnabled");
          final int _cursorIndexOfIsStaged = CursorUtil.getColumnIndexOrThrow(_cursor, "isStaged");
          final int _cursorIndexOfFsrsState = CursorUtil.getColumnIndexOrThrow(_cursor, "fsrsState");
          final int _cursorIndexOfStability = CursorUtil.getColumnIndexOrThrow(_cursor, "stability");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfReps = CursorUtil.getColumnIndexOrThrow(_cursor, "reps");
          final int _cursorIndexOfLapses = CursorUtil.getColumnIndexOrThrow(_cursor, "lapses");
          final int _cursorIndexOfLastReviewMs = CursorUtil.getColumnIndexOrThrow(_cursor, "lastReviewMs");
          final int _cursorIndexOfDueMs = CursorUtil.getColumnIndexOrThrow(_cursor, "dueMs");
          final List<TafseerClusterCardEntity> _result = new ArrayList<TafseerClusterCardEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TafseerClusterCardEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final int _tmpSurahNumber;
            _tmpSurahNumber = _cursor.getInt(_cursorIndexOfSurahNumber);
            final int _tmpAyahStart;
            _tmpAyahStart = _cursor.getInt(_cursorIndexOfAyahStart);
            final int _tmpAyahEnd;
            _tmpAyahEnd = _cursor.getInt(_cursorIndexOfAyahEnd);
            final int _tmpPageNumber;
            _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
            final int _tmpHizbNumber;
            _tmpHizbNumber = _cursor.getInt(_cursorIndexOfHizbNumber);
            final String _tmpTheme;
            _tmpTheme = _cursor.getString(_cursorIndexOfTheme);
            final String _tmpBulletsJson;
            _tmpBulletsJson = _cursor.getString(_cursorIndexOfBulletsJson);
            final boolean _tmpIsEnabled;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsEnabled);
            _tmpIsEnabled = _tmp != 0;
            final boolean _tmpIsStaged;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsStaged);
            _tmpIsStaged = _tmp_1 != 0;
            final String _tmpFsrsState;
            _tmpFsrsState = _cursor.getString(_cursorIndexOfFsrsState);
            final float _tmpStability;
            _tmpStability = _cursor.getFloat(_cursorIndexOfStability);
            final float _tmpDifficulty;
            _tmpDifficulty = _cursor.getFloat(_cursorIndexOfDifficulty);
            final int _tmpReps;
            _tmpReps = _cursor.getInt(_cursorIndexOfReps);
            final int _tmpLapses;
            _tmpLapses = _cursor.getInt(_cursorIndexOfLapses);
            final long _tmpLastReviewMs;
            _tmpLastReviewMs = _cursor.getLong(_cursorIndexOfLastReviewMs);
            final long _tmpDueMs;
            _tmpDueMs = _cursor.getLong(_cursorIndexOfDueMs);
            _item = new TafseerClusterCardEntity(_tmpId,_tmpSurahNumber,_tmpAyahStart,_tmpAyahEnd,_tmpPageNumber,_tmpHizbNumber,_tmpTheme,_tmpBulletsJson,_tmpIsEnabled,_tmpIsStaged,_tmpFsrsState,_tmpStability,_tmpDifficulty,_tmpReps,_tmpLapses,_tmpLastReviewMs,_tmpDueMs);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
