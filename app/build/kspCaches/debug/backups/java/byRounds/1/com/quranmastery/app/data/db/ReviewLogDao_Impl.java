package com.quranmastery.app.data.db;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
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
public final class ReviewLogDao_Impl implements ReviewLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FsrsReviewLogEntity> __insertionAdapterOfFsrsReviewLogEntity;

  public ReviewLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFsrsReviewLogEntity = new EntityInsertionAdapter<FsrsReviewLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `fsrs_review_log` (`logId`,`cardId`,`cardType`,`rating`,`reviewedAtMs`,`scheduledDays`,`stabilityBefore`,`stabilityAfter`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FsrsReviewLogEntity entity) {
        statement.bindLong(1, entity.getLogId());
        statement.bindString(2, entity.getCardId());
        statement.bindString(3, entity.getCardType());
        statement.bindLong(4, entity.getRating());
        statement.bindLong(5, entity.getReviewedAtMs());
        statement.bindLong(6, entity.getScheduledDays());
        statement.bindDouble(7, entity.getStabilityBefore());
        statement.bindDouble(8, entity.getStabilityAfter());
      }
    };
  }

  @Override
  public Object insert(final FsrsReviewLogEntity log,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfFsrsReviewLogEntity.insertAndReturnId(log);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object forCard(final String cardId, final int limit,
      final Continuation<? super List<FsrsReviewLogEntity>> $completion) {
    final String _sql = "SELECT * FROM fsrs_review_log WHERE cardId = ? ORDER BY reviewedAtMs DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, cardId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FsrsReviewLogEntity>>() {
      @Override
      @NonNull
      public List<FsrsReviewLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLogId = CursorUtil.getColumnIndexOrThrow(_cursor, "logId");
          final int _cursorIndexOfCardId = CursorUtil.getColumnIndexOrThrow(_cursor, "cardId");
          final int _cursorIndexOfCardType = CursorUtil.getColumnIndexOrThrow(_cursor, "cardType");
          final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
          final int _cursorIndexOfReviewedAtMs = CursorUtil.getColumnIndexOrThrow(_cursor, "reviewedAtMs");
          final int _cursorIndexOfScheduledDays = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduledDays");
          final int _cursorIndexOfStabilityBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "stabilityBefore");
          final int _cursorIndexOfStabilityAfter = CursorUtil.getColumnIndexOrThrow(_cursor, "stabilityAfter");
          final List<FsrsReviewLogEntity> _result = new ArrayList<FsrsReviewLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FsrsReviewLogEntity _item;
            final long _tmpLogId;
            _tmpLogId = _cursor.getLong(_cursorIndexOfLogId);
            final String _tmpCardId;
            _tmpCardId = _cursor.getString(_cursorIndexOfCardId);
            final String _tmpCardType;
            _tmpCardType = _cursor.getString(_cursorIndexOfCardType);
            final int _tmpRating;
            _tmpRating = _cursor.getInt(_cursorIndexOfRating);
            final long _tmpReviewedAtMs;
            _tmpReviewedAtMs = _cursor.getLong(_cursorIndexOfReviewedAtMs);
            final int _tmpScheduledDays;
            _tmpScheduledDays = _cursor.getInt(_cursorIndexOfScheduledDays);
            final float _tmpStabilityBefore;
            _tmpStabilityBefore = _cursor.getFloat(_cursorIndexOfStabilityBefore);
            final float _tmpStabilityAfter;
            _tmpStabilityAfter = _cursor.getFloat(_cursorIndexOfStabilityAfter);
            _item = new FsrsReviewLogEntity(_tmpLogId,_tmpCardId,_tmpCardType,_tmpRating,_tmpReviewedAtMs,_tmpScheduledDays,_tmpStabilityBefore,_tmpStabilityAfter);
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
  public Object count(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM fsrs_review_log";
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
