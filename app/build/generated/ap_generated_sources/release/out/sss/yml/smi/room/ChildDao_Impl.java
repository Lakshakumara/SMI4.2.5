package sss.yml.smi.room;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ChildDao_Impl implements ChildDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Child> __insertionAdapterOfChild;

  private final EntityInsertionAdapter<ChildWpData> __insertionAdapterOfChildWpData;

  private final EntityDeletionOrUpdateAdapter<Child> __deletionAdapterOfChild;

  private final EntityDeletionOrUpdateAdapter<ChildWpData> __deletionAdapterOfChildWpData;

  private final EntityDeletionOrUpdateAdapter<Child> __updateAdapterOfChild;

  private final EntityDeletionOrUpdateAdapter<ChildWpData> __updateAdapterOfChildWpData;

  private final SharedSQLiteStatement __preparedStmtOfDeleteData;

  public ChildDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChild = new EntityInsertionAdapter<Child>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `child` (`id`,`regNo`,`name`,`sex`,`dob`,`maturity`,`guardian`,`tp`,`note`,`fht`,`mht`,`deleted`,`accessTime`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Child entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getRegNo() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getRegNo());
        }
        if (entity.getName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getName());
        }
        if (entity.getSex() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSex());
        }
        final Long _tmp = DateConverter.dateToTimestamp(entity.getDob());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp);
        }
        statement.bindLong(6, entity.getMaturity());
        if (entity.getGuardian() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getGuardian());
        }
        if (entity.getTp() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getTp());
        }
        if (entity.getNote() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getNote());
        }
        statement.bindDouble(10, entity.getFht());
        statement.bindDouble(11, entity.getMht());
        final int _tmp_1 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        final Long _tmp_2 = DateConverter.dateToTimestamp(entity.getAccessTime());
        if (_tmp_2 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_2);
        }
      }
    };
    this.__insertionAdapterOfChildWpData = new EntityInsertionAdapter<ChildWpData>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `childWpData` (`id`,`childId`,`mDate`,`wt`,`ht`,`note`,`ageDays`,`ageYY`,`ageMM`,`ageDD`,`wfaZ`,`hfaZ`,`wfhZ`,`wfaStatus`,`hfaStatus`,`wfhStatus`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ChildWpData entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getChildId());
        final Long _tmp = DateConverter.dateToTimestamp(entity.mDate);
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        statement.bindDouble(4, entity.getWt());
        statement.bindDouble(5, entity.getHt());
        if (entity.getNote() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNote());
        }
        statement.bindDouble(7, entity.getAgeDays());
        statement.bindLong(8, entity.getAgeYY());
        statement.bindLong(9, entity.getAgeMM());
        statement.bindLong(10, entity.getAgeDD());
        statement.bindDouble(11, entity.getWfaZ());
        statement.bindDouble(12, entity.getHfaZ());
        statement.bindDouble(13, entity.getWfhZ());
        if (entity.getWfaStatus() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getWfaStatus());
        }
        if (entity.getHfaStatus() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getHfaStatus());
        }
        if (entity.getWfhStatus() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getWfhStatus());
        }
      }
    };
    this.__deletionAdapterOfChild = new EntityDeletionOrUpdateAdapter<Child>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `child` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Child entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfChildWpData = new EntityDeletionOrUpdateAdapter<ChildWpData>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `childWpData` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ChildWpData entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfChild = new EntityDeletionOrUpdateAdapter<Child>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `child` SET `id` = ?,`regNo` = ?,`name` = ?,`sex` = ?,`dob` = ?,`maturity` = ?,`guardian` = ?,`tp` = ?,`note` = ?,`fht` = ?,`mht` = ?,`deleted` = ?,`accessTime` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Child entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getRegNo() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getRegNo());
        }
        if (entity.getName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getName());
        }
        if (entity.getSex() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getSex());
        }
        final Long _tmp = DateConverter.dateToTimestamp(entity.getDob());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp);
        }
        statement.bindLong(6, entity.getMaturity());
        if (entity.getGuardian() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getGuardian());
        }
        if (entity.getTp() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getTp());
        }
        if (entity.getNote() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getNote());
        }
        statement.bindDouble(10, entity.getFht());
        statement.bindDouble(11, entity.getMht());
        final int _tmp_1 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(12, _tmp_1);
        final Long _tmp_2 = DateConverter.dateToTimestamp(entity.getAccessTime());
        if (_tmp_2 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_2);
        }
        statement.bindLong(14, entity.getId());
      }
    };
    this.__updateAdapterOfChildWpData = new EntityDeletionOrUpdateAdapter<ChildWpData>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `childWpData` SET `id` = ?,`childId` = ?,`mDate` = ?,`wt` = ?,`ht` = ?,`note` = ?,`ageDays` = ?,`ageYY` = ?,`ageMM` = ?,`ageDD` = ?,`wfaZ` = ?,`hfaZ` = ?,`wfhZ` = ?,`wfaStatus` = ?,`hfaStatus` = ?,`wfhStatus` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final ChildWpData entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getChildId());
        final Long _tmp = DateConverter.dateToTimestamp(entity.mDate);
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        statement.bindDouble(4, entity.getWt());
        statement.bindDouble(5, entity.getHt());
        if (entity.getNote() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNote());
        }
        statement.bindDouble(7, entity.getAgeDays());
        statement.bindLong(8, entity.getAgeYY());
        statement.bindLong(9, entity.getAgeMM());
        statement.bindLong(10, entity.getAgeDD());
        statement.bindDouble(11, entity.getWfaZ());
        statement.bindDouble(12, entity.getHfaZ());
        statement.bindDouble(13, entity.getWfhZ());
        if (entity.getWfaStatus() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getWfaStatus());
        }
        if (entity.getHfaStatus() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getHfaStatus());
        }
        if (entity.getWfhStatus() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getWfhStatus());
        }
        statement.bindLong(17, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteData = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM ChildWpData WHERE childId =? ";
        return _query;
      }
    };
  }

  @Override
  public Long insert(final Child obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Long _result = __insertionAdapterOfChild.insertAndReturnId(obj);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(final ChildWpData childWpData) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfChildWpData.insert(childWpData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Child obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfChild.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final ChildWpData childWpData) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfChildWpData.handle(childWpData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll(final ChildWpData... childWpData) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfChildWpData.handleMultiple(childWpData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Child obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfChild.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final ChildWpData childWpData) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfChildWpData.handle(childWpData);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteData(final int childId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteData.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, childId);
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteData.release(_stmt);
    }
  }

  @Override
  public LiveData<List<ChildData>> getChildData(final String name, final boolean delete) {
    final String _sql = "SELECT * FROM child WHERE name LIKE ? AND deleted = ? order by name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    _argIndex = 2;
    final int _tmp = delete ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    return __db.getInvalidationTracker().createLiveData(new String[] {"childWpData",
        "child"}, true, new Callable<List<ChildData>>() {
      @Override
      @Nullable
      public List<ChildData> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfRegNo = CursorUtil.getColumnIndexOrThrow(_cursor, "regNo");
            final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
            final int _cursorIndexOfSex = CursorUtil.getColumnIndexOrThrow(_cursor, "sex");
            final int _cursorIndexOfDob = CursorUtil.getColumnIndexOrThrow(_cursor, "dob");
            final int _cursorIndexOfMaturity = CursorUtil.getColumnIndexOrThrow(_cursor, "maturity");
            final int _cursorIndexOfGuardian = CursorUtil.getColumnIndexOrThrow(_cursor, "guardian");
            final int _cursorIndexOfTp = CursorUtil.getColumnIndexOrThrow(_cursor, "tp");
            final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
            final int _cursorIndexOfFht = CursorUtil.getColumnIndexOrThrow(_cursor, "fht");
            final int _cursorIndexOfMht = CursorUtil.getColumnIndexOrThrow(_cursor, "mht");
            final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
            final int _cursorIndexOfAccessTime = CursorUtil.getColumnIndexOrThrow(_cursor, "accessTime");
            final LongSparseArray<ArrayList<ChildWpData>> _collectionChildWpData = new LongSparseArray<ArrayList<ChildWpData>>();
            while (_cursor.moveToNext()) {
              final Long _tmpKey;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey = null;
              } else {
                _tmpKey = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey != null) {
                if (!_collectionChildWpData.containsKey(_tmpKey)) {
                  _collectionChildWpData.put(_tmpKey, new ArrayList<ChildWpData>());
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipchildWpDataAssssYmlSmiRoomChildWpData(_collectionChildWpData);
            final List<ChildData> _result = new ArrayList<ChildData>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final ChildData _item;
              final Child _tmpChild;
              if (!(_cursor.isNull(_cursorIndexOfId) && _cursor.isNull(_cursorIndexOfRegNo) && _cursor.isNull(_cursorIndexOfName) && _cursor.isNull(_cursorIndexOfSex) && _cursor.isNull(_cursorIndexOfDob) && _cursor.isNull(_cursorIndexOfMaturity) && _cursor.isNull(_cursorIndexOfGuardian) && _cursor.isNull(_cursorIndexOfTp) && _cursor.isNull(_cursorIndexOfNote) && _cursor.isNull(_cursorIndexOfFht) && _cursor.isNull(_cursorIndexOfMht) && _cursor.isNull(_cursorIndexOfDeleted) && _cursor.isNull(_cursorIndexOfAccessTime))) {
                _tmpChild = new Child();
                final int _tmpId;
                _tmpId = _cursor.getInt(_cursorIndexOfId);
                _tmpChild.setId(_tmpId);
                final String _tmpRegNo;
                if (_cursor.isNull(_cursorIndexOfRegNo)) {
                  _tmpRegNo = null;
                } else {
                  _tmpRegNo = _cursor.getString(_cursorIndexOfRegNo);
                }
                _tmpChild.setRegNo(_tmpRegNo);
                final String _tmpName;
                if (_cursor.isNull(_cursorIndexOfName)) {
                  _tmpName = null;
                } else {
                  _tmpName = _cursor.getString(_cursorIndexOfName);
                }
                _tmpChild.setName(_tmpName);
                final String _tmpSex;
                if (_cursor.isNull(_cursorIndexOfSex)) {
                  _tmpSex = null;
                } else {
                  _tmpSex = _cursor.getString(_cursorIndexOfSex);
                }
                _tmpChild.setSex(_tmpSex);
                final Date _tmpDob;
                final Long _tmp_1;
                if (_cursor.isNull(_cursorIndexOfDob)) {
                  _tmp_1 = null;
                } else {
                  _tmp_1 = _cursor.getLong(_cursorIndexOfDob);
                }
                _tmpDob = DateConverter.fromTimestamp(_tmp_1);
                _tmpChild.setDob(_tmpDob);
                final int _tmpMaturity;
                _tmpMaturity = _cursor.getInt(_cursorIndexOfMaturity);
                _tmpChild.setMaturity(_tmpMaturity);
                final String _tmpGuardian;
                if (_cursor.isNull(_cursorIndexOfGuardian)) {
                  _tmpGuardian = null;
                } else {
                  _tmpGuardian = _cursor.getString(_cursorIndexOfGuardian);
                }
                _tmpChild.setGuardian(_tmpGuardian);
                final String _tmpTp;
                if (_cursor.isNull(_cursorIndexOfTp)) {
                  _tmpTp = null;
                } else {
                  _tmpTp = _cursor.getString(_cursorIndexOfTp);
                }
                _tmpChild.setTp(_tmpTp);
                final String _tmpNote;
                if (_cursor.isNull(_cursorIndexOfNote)) {
                  _tmpNote = null;
                } else {
                  _tmpNote = _cursor.getString(_cursorIndexOfNote);
                }
                _tmpChild.setNote(_tmpNote);
                final double _tmpFht;
                _tmpFht = _cursor.getDouble(_cursorIndexOfFht);
                _tmpChild.setFht(_tmpFht);
                final double _tmpMht;
                _tmpMht = _cursor.getDouble(_cursorIndexOfMht);
                _tmpChild.setMht(_tmpMht);
                final boolean _tmpDeleted;
                final int _tmp_2;
                _tmp_2 = _cursor.getInt(_cursorIndexOfDeleted);
                _tmpDeleted = _tmp_2 != 0;
                _tmpChild.setDeleted(_tmpDeleted);
                final Date _tmpAccessTime;
                final Long _tmp_3;
                if (_cursor.isNull(_cursorIndexOfAccessTime)) {
                  _tmp_3 = null;
                } else {
                  _tmp_3 = _cursor.getLong(_cursorIndexOfAccessTime);
                }
                _tmpAccessTime = DateConverter.fromTimestamp(_tmp_3);
                _tmpChild.setAccessTime(_tmpAccessTime);
              } else {
                _tmpChild = null;
              }
              final ArrayList<ChildWpData> _tmpChildWpDataCollection;
              final Long _tmpKey_1;
              if (_cursor.isNull(_cursorIndexOfId)) {
                _tmpKey_1 = null;
              } else {
                _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              }
              if (_tmpKey_1 != null) {
                _tmpChildWpDataCollection = _collectionChildWpData.get(_tmpKey_1);
              } else {
                _tmpChildWpDataCollection = new ArrayList<ChildWpData>();
              }
              _item = new ChildData();
              _item.child = _tmpChild;
              _item.childWpData = _tmpChildWpDataCollection;
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<Child> getChild(final int childId, final boolean delete) {
    final String _sql = "SELECT * FROM child where id = ? and deleted = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, childId);
    _argIndex = 2;
    final int _tmp = delete ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    return __db.getInvalidationTracker().createLiveData(new String[] {"child"}, false, new Callable<Child>() {
      @Override
      @Nullable
      public Child call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfRegNo = CursorUtil.getColumnIndexOrThrow(_cursor, "regNo");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfSex = CursorUtil.getColumnIndexOrThrow(_cursor, "sex");
          final int _cursorIndexOfDob = CursorUtil.getColumnIndexOrThrow(_cursor, "dob");
          final int _cursorIndexOfMaturity = CursorUtil.getColumnIndexOrThrow(_cursor, "maturity");
          final int _cursorIndexOfGuardian = CursorUtil.getColumnIndexOrThrow(_cursor, "guardian");
          final int _cursorIndexOfTp = CursorUtil.getColumnIndexOrThrow(_cursor, "tp");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfFht = CursorUtil.getColumnIndexOrThrow(_cursor, "fht");
          final int _cursorIndexOfMht = CursorUtil.getColumnIndexOrThrow(_cursor, "mht");
          final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
          final int _cursorIndexOfAccessTime = CursorUtil.getColumnIndexOrThrow(_cursor, "accessTime");
          final Child _result;
          if (_cursor.moveToFirst()) {
            _result = new Child();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpRegNo;
            if (_cursor.isNull(_cursorIndexOfRegNo)) {
              _tmpRegNo = null;
            } else {
              _tmpRegNo = _cursor.getString(_cursorIndexOfRegNo);
            }
            _result.setRegNo(_tmpRegNo);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            _result.setName(_tmpName);
            final String _tmpSex;
            if (_cursor.isNull(_cursorIndexOfSex)) {
              _tmpSex = null;
            } else {
              _tmpSex = _cursor.getString(_cursorIndexOfSex);
            }
            _result.setSex(_tmpSex);
            final Date _tmpDob;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDob)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDob);
            }
            _tmpDob = DateConverter.fromTimestamp(_tmp_1);
            _result.setDob(_tmpDob);
            final int _tmpMaturity;
            _tmpMaturity = _cursor.getInt(_cursorIndexOfMaturity);
            _result.setMaturity(_tmpMaturity);
            final String _tmpGuardian;
            if (_cursor.isNull(_cursorIndexOfGuardian)) {
              _tmpGuardian = null;
            } else {
              _tmpGuardian = _cursor.getString(_cursorIndexOfGuardian);
            }
            _result.setGuardian(_tmpGuardian);
            final String _tmpTp;
            if (_cursor.isNull(_cursorIndexOfTp)) {
              _tmpTp = null;
            } else {
              _tmpTp = _cursor.getString(_cursorIndexOfTp);
            }
            _result.setTp(_tmpTp);
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            _result.setNote(_tmpNote);
            final double _tmpFht;
            _tmpFht = _cursor.getDouble(_cursorIndexOfFht);
            _result.setFht(_tmpFht);
            final double _tmpMht;
            _tmpMht = _cursor.getDouble(_cursorIndexOfMht);
            _result.setMht(_tmpMht);
            final boolean _tmpDeleted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfDeleted);
            _tmpDeleted = _tmp_2 != 0;
            _result.setDeleted(_tmpDeleted);
            final Date _tmpAccessTime;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfAccessTime)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfAccessTime);
            }
            _tmpAccessTime = DateConverter.fromTimestamp(_tmp_3);
            _result.setAccessTime(_tmpAccessTime);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Child getChild(final String regNo) {
    final String _sql = "SELECT * FROM child where regNo = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (regNo == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, regNo);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfRegNo = CursorUtil.getColumnIndexOrThrow(_cursor, "regNo");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfSex = CursorUtil.getColumnIndexOrThrow(_cursor, "sex");
      final int _cursorIndexOfDob = CursorUtil.getColumnIndexOrThrow(_cursor, "dob");
      final int _cursorIndexOfMaturity = CursorUtil.getColumnIndexOrThrow(_cursor, "maturity");
      final int _cursorIndexOfGuardian = CursorUtil.getColumnIndexOrThrow(_cursor, "guardian");
      final int _cursorIndexOfTp = CursorUtil.getColumnIndexOrThrow(_cursor, "tp");
      final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
      final int _cursorIndexOfFht = CursorUtil.getColumnIndexOrThrow(_cursor, "fht");
      final int _cursorIndexOfMht = CursorUtil.getColumnIndexOrThrow(_cursor, "mht");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final int _cursorIndexOfAccessTime = CursorUtil.getColumnIndexOrThrow(_cursor, "accessTime");
      final Child _result;
      if (_cursor.moveToFirst()) {
        _result = new Child();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpRegNo;
        if (_cursor.isNull(_cursorIndexOfRegNo)) {
          _tmpRegNo = null;
        } else {
          _tmpRegNo = _cursor.getString(_cursorIndexOfRegNo);
        }
        _result.setRegNo(_tmpRegNo);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final String _tmpSex;
        if (_cursor.isNull(_cursorIndexOfSex)) {
          _tmpSex = null;
        } else {
          _tmpSex = _cursor.getString(_cursorIndexOfSex);
        }
        _result.setSex(_tmpSex);
        final Date _tmpDob;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDob)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDob);
        }
        _tmpDob = DateConverter.fromTimestamp(_tmp);
        _result.setDob(_tmpDob);
        final int _tmpMaturity;
        _tmpMaturity = _cursor.getInt(_cursorIndexOfMaturity);
        _result.setMaturity(_tmpMaturity);
        final String _tmpGuardian;
        if (_cursor.isNull(_cursorIndexOfGuardian)) {
          _tmpGuardian = null;
        } else {
          _tmpGuardian = _cursor.getString(_cursorIndexOfGuardian);
        }
        _result.setGuardian(_tmpGuardian);
        final String _tmpTp;
        if (_cursor.isNull(_cursorIndexOfTp)) {
          _tmpTp = null;
        } else {
          _tmpTp = _cursor.getString(_cursorIndexOfTp);
        }
        _result.setTp(_tmpTp);
        final String _tmpNote;
        if (_cursor.isNull(_cursorIndexOfNote)) {
          _tmpNote = null;
        } else {
          _tmpNote = _cursor.getString(_cursorIndexOfNote);
        }
        _result.setNote(_tmpNote);
        final double _tmpFht;
        _tmpFht = _cursor.getDouble(_cursorIndexOfFht);
        _result.setFht(_tmpFht);
        final double _tmpMht;
        _tmpMht = _cursor.getDouble(_cursorIndexOfMht);
        _result.setMht(_tmpMht);
        final boolean _tmpDeleted;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfDeleted);
        _tmpDeleted = _tmp_1 != 0;
        _result.setDeleted(_tmpDeleted);
        final Date _tmpAccessTime;
        final Long _tmp_2;
        if (_cursor.isNull(_cursorIndexOfAccessTime)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getLong(_cursorIndexOfAccessTime);
        }
        _tmpAccessTime = DateConverter.fromTimestamp(_tmp_2);
        _result.setAccessTime(_tmpAccessTime);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Child getChildByName(final String name) {
    final String _sql = "SELECT * FROM child WHERE upper(name) = upper(?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfRegNo = CursorUtil.getColumnIndexOrThrow(_cursor, "regNo");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfSex = CursorUtil.getColumnIndexOrThrow(_cursor, "sex");
      final int _cursorIndexOfDob = CursorUtil.getColumnIndexOrThrow(_cursor, "dob");
      final int _cursorIndexOfMaturity = CursorUtil.getColumnIndexOrThrow(_cursor, "maturity");
      final int _cursorIndexOfGuardian = CursorUtil.getColumnIndexOrThrow(_cursor, "guardian");
      final int _cursorIndexOfTp = CursorUtil.getColumnIndexOrThrow(_cursor, "tp");
      final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
      final int _cursorIndexOfFht = CursorUtil.getColumnIndexOrThrow(_cursor, "fht");
      final int _cursorIndexOfMht = CursorUtil.getColumnIndexOrThrow(_cursor, "mht");
      final int _cursorIndexOfDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "deleted");
      final int _cursorIndexOfAccessTime = CursorUtil.getColumnIndexOrThrow(_cursor, "accessTime");
      final Child _result;
      if (_cursor.moveToFirst()) {
        _result = new Child();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpRegNo;
        if (_cursor.isNull(_cursorIndexOfRegNo)) {
          _tmpRegNo = null;
        } else {
          _tmpRegNo = _cursor.getString(_cursorIndexOfRegNo);
        }
        _result.setRegNo(_tmpRegNo);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        _result.setName(_tmpName);
        final String _tmpSex;
        if (_cursor.isNull(_cursorIndexOfSex)) {
          _tmpSex = null;
        } else {
          _tmpSex = _cursor.getString(_cursorIndexOfSex);
        }
        _result.setSex(_tmpSex);
        final Date _tmpDob;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfDob)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfDob);
        }
        _tmpDob = DateConverter.fromTimestamp(_tmp);
        _result.setDob(_tmpDob);
        final int _tmpMaturity;
        _tmpMaturity = _cursor.getInt(_cursorIndexOfMaturity);
        _result.setMaturity(_tmpMaturity);
        final String _tmpGuardian;
        if (_cursor.isNull(_cursorIndexOfGuardian)) {
          _tmpGuardian = null;
        } else {
          _tmpGuardian = _cursor.getString(_cursorIndexOfGuardian);
        }
        _result.setGuardian(_tmpGuardian);
        final String _tmpTp;
        if (_cursor.isNull(_cursorIndexOfTp)) {
          _tmpTp = null;
        } else {
          _tmpTp = _cursor.getString(_cursorIndexOfTp);
        }
        _result.setTp(_tmpTp);
        final String _tmpNote;
        if (_cursor.isNull(_cursorIndexOfNote)) {
          _tmpNote = null;
        } else {
          _tmpNote = _cursor.getString(_cursorIndexOfNote);
        }
        _result.setNote(_tmpNote);
        final double _tmpFht;
        _tmpFht = _cursor.getDouble(_cursorIndexOfFht);
        _result.setFht(_tmpFht);
        final double _tmpMht;
        _tmpMht = _cursor.getDouble(_cursorIndexOfMht);
        _result.setMht(_tmpMht);
        final boolean _tmpDeleted;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfDeleted);
        _tmpDeleted = _tmp_1 != 0;
        _result.setDeleted(_tmpDeleted);
        final Date _tmpAccessTime;
        final Long _tmp_2;
        if (_cursor.isNull(_cursorIndexOfAccessTime)) {
          _tmp_2 = null;
        } else {
          _tmp_2 = _cursor.getLong(_cursorIndexOfAccessTime);
        }
        _tmpAccessTime = DateConverter.fromTimestamp(_tmp_2);
        _result.setAccessTime(_tmpAccessTime);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<String>> getChildName(final boolean delete) {
    final String _sql = "SELECT name FROM child WHERE deleted = ? order by name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final int _tmp = delete ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    return __db.getInvalidationTracker().createLiveData(new String[] {"child"}, false, new Callable<List<String>>() {
      @Override
      @Nullable
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Integer getChildId(final String name) {
    final String _sql = "SELECT id FROM child WHERE upper(name) = upper(?) ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Integer _result;
      if (_cursor.moveToFirst()) {
        if (_cursor.isNull(0)) {
          _result = null;
        } else {
          _result = _cursor.getInt(0);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipchildWpDataAssssYmlSmiRoomChildWpData(
      @NonNull final LongSparseArray<ArrayList<ChildWpData>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshipchildWpDataAssssYmlSmiRoomChildWpData(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`childId`,`mDate`,`wt`,`ht`,`note`,`ageDays`,`ageYY`,`ageMM`,`ageDD`,`wfaZ`,`hfaZ`,`wfhZ`,`wfaStatus`,`hfaStatus`,`wfhStatus` FROM `childWpData` WHERE `childId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "childId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfChildId = 1;
      final int _cursorIndexOfMDate = 2;
      final int _cursorIndexOfWt = 3;
      final int _cursorIndexOfHt = 4;
      final int _cursorIndexOfNote = 5;
      final int _cursorIndexOfAgeDays = 6;
      final int _cursorIndexOfAgeYY = 7;
      final int _cursorIndexOfAgeMM = 8;
      final int _cursorIndexOfAgeDD = 9;
      final int _cursorIndexOfWfaZ = 10;
      final int _cursorIndexOfHfaZ = 11;
      final int _cursorIndexOfWfhZ = 12;
      final int _cursorIndexOfWfaStatus = 13;
      final int _cursorIndexOfHfaStatus = 14;
      final int _cursorIndexOfWfhStatus = 15;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        final ArrayList<ChildWpData> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final ChildWpData _item_1;
          _item_1 = new ChildWpData();
          final int _tmpId;
          _tmpId = _cursor.getInt(_cursorIndexOfId);
          _item_1.setId(_tmpId);
          final int _tmpChildId;
          _tmpChildId = _cursor.getInt(_cursorIndexOfChildId);
          _item_1.setChildId(_tmpChildId);
          final Long _tmp;
          if (_cursor.isNull(_cursorIndexOfMDate)) {
            _tmp = null;
          } else {
            _tmp = _cursor.getLong(_cursorIndexOfMDate);
          }
          _item_1.mDate = DateConverter.fromTimestamp(_tmp);
          final double _tmpWt;
          _tmpWt = _cursor.getDouble(_cursorIndexOfWt);
          _item_1.setWt(_tmpWt);
          final double _tmpHt;
          _tmpHt = _cursor.getDouble(_cursorIndexOfHt);
          _item_1.setHt(_tmpHt);
          final String _tmpNote;
          if (_cursor.isNull(_cursorIndexOfNote)) {
            _tmpNote = null;
          } else {
            _tmpNote = _cursor.getString(_cursorIndexOfNote);
          }
          _item_1.setNote(_tmpNote);
          final float _tmpAgeDays;
          _tmpAgeDays = _cursor.getFloat(_cursorIndexOfAgeDays);
          _item_1.setAgeDays(_tmpAgeDays);
          final int _tmpAgeYY;
          _tmpAgeYY = _cursor.getInt(_cursorIndexOfAgeYY);
          _item_1.setAgeYY(_tmpAgeYY);
          final int _tmpAgeMM;
          _tmpAgeMM = _cursor.getInt(_cursorIndexOfAgeMM);
          _item_1.setAgeMM(_tmpAgeMM);
          final int _tmpAgeDD;
          _tmpAgeDD = _cursor.getInt(_cursorIndexOfAgeDD);
          _item_1.setAgeDD(_tmpAgeDD);
          final double _tmpWfaZ;
          _tmpWfaZ = _cursor.getDouble(_cursorIndexOfWfaZ);
          _item_1.setWfaZ(_tmpWfaZ);
          final double _tmpHfaZ;
          _tmpHfaZ = _cursor.getDouble(_cursorIndexOfHfaZ);
          _item_1.setHfaZ(_tmpHfaZ);
          final double _tmpWfhZ;
          _tmpWfhZ = _cursor.getDouble(_cursorIndexOfWfhZ);
          _item_1.setWfhZ(_tmpWfhZ);
          final String _tmpWfaStatus;
          if (_cursor.isNull(_cursorIndexOfWfaStatus)) {
            _tmpWfaStatus = null;
          } else {
            _tmpWfaStatus = _cursor.getString(_cursorIndexOfWfaStatus);
          }
          _item_1.setWfaStatus(_tmpWfaStatus);
          final String _tmpHfaStatus;
          if (_cursor.isNull(_cursorIndexOfHfaStatus)) {
            _tmpHfaStatus = null;
          } else {
            _tmpHfaStatus = _cursor.getString(_cursorIndexOfHfaStatus);
          }
          _item_1.setHfaStatus(_tmpHfaStatus);
          final String _tmpWfhStatus;
          if (_cursor.isNull(_cursorIndexOfWfhStatus)) {
            _tmpWfhStatus = null;
          } else {
            _tmpWfhStatus = _cursor.getString(_cursorIndexOfWfhStatus);
          }
          _item_1.setWfhStatus(_tmpWfhStatus);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
