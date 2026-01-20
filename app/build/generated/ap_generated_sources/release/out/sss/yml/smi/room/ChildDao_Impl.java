package sss.yml.smi.room;

import androidx.annotation.NonNull;
import androidx.collection.LongSparseArray;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import java.lang.Class;
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
import javax.annotation.processing.Generated;
import kotlin.Unit;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class ChildDao_Impl implements ChildDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Child> __insertAdapterOfChild;

  private final EntityInsertAdapter<ChildWpData> __insertAdapterOfChildWpData;

  private final EntityDeleteOrUpdateAdapter<Child> __deleteAdapterOfChild;

  private final EntityDeleteOrUpdateAdapter<ChildWpData> __deleteAdapterOfChildWpData;

  private final EntityDeleteOrUpdateAdapter<Child> __updateAdapterOfChild;

  private final EntityDeleteOrUpdateAdapter<ChildWpData> __updateAdapterOfChildWpData;

  public ChildDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfChild = new EntityInsertAdapter<Child>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `child` (`id`,`regNo`,`name`,`sex`,`dob`,`maturity`,`guardian`,`tp`,`note`,`fht`,`mht`,`deleted`,`accessTime`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Child entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getRegNo() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getRegNo());
        }
        if (entity.getName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getName());
        }
        if (entity.getSex() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getSex());
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
          statement.bindText(7, entity.getGuardian());
        }
        if (entity.getTp() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getTp());
        }
        if (entity.getNote() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getNote());
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
    this.__insertAdapterOfChildWpData = new EntityInsertAdapter<ChildWpData>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `childWpData` (`id`,`childId`,`mDate`,`wt`,`ht`,`note`,`ageDays`,`ageYY`,`ageMM`,`ageDD`,`wfaZ`,`hfaZ`,`wfhZ`,`wfaStatus`,`hfaStatus`,`wfhStatus`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final ChildWpData entity) {
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
          statement.bindText(6, entity.getNote());
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
          statement.bindText(14, entity.getWfaStatus());
        }
        if (entity.getHfaStatus() == null) {
          statement.bindNull(15);
        } else {
          statement.bindText(15, entity.getHfaStatus());
        }
        if (entity.getWfhStatus() == null) {
          statement.bindNull(16);
        } else {
          statement.bindText(16, entity.getWfhStatus());
        }
      }
    };
    this.__deleteAdapterOfChild = new EntityDeleteOrUpdateAdapter<Child>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `child` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Child entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deleteAdapterOfChildWpData = new EntityDeleteOrUpdateAdapter<ChildWpData>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `childWpData` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final ChildWpData entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfChild = new EntityDeleteOrUpdateAdapter<Child>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `child` SET `id` = ?,`regNo` = ?,`name` = ?,`sex` = ?,`dob` = ?,`maturity` = ?,`guardian` = ?,`tp` = ?,`note` = ?,`fht` = ?,`mht` = ?,`deleted` = ?,`accessTime` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Child entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getRegNo() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getRegNo());
        }
        if (entity.getName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getName());
        }
        if (entity.getSex() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getSex());
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
          statement.bindText(7, entity.getGuardian());
        }
        if (entity.getTp() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getTp());
        }
        if (entity.getNote() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getNote());
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
    this.__updateAdapterOfChildWpData = new EntityDeleteOrUpdateAdapter<ChildWpData>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `childWpData` SET `id` = ?,`childId` = ?,`mDate` = ?,`wt` = ?,`ht` = ?,`note` = ?,`ageDays` = ?,`ageYY` = ?,`ageMM` = ?,`ageDD` = ?,`wfaZ` = ?,`hfaZ` = ?,`wfhZ` = ?,`wfaStatus` = ?,`hfaStatus` = ?,`wfhStatus` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final ChildWpData entity) {
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
          statement.bindText(6, entity.getNote());
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
          statement.bindText(14, entity.getWfaStatus());
        }
        if (entity.getHfaStatus() == null) {
          statement.bindNull(15);
        } else {
          statement.bindText(15, entity.getHfaStatus());
        }
        if (entity.getWfhStatus() == null) {
          statement.bindNull(16);
        } else {
          statement.bindText(16, entity.getWfhStatus());
        }
        statement.bindLong(17, entity.getId());
      }
    };
  }

  @Override
  public Long insert(final Child obj) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfChild.insertAndReturnId(_connection, obj);
    });
  }

  @Override
  public void insert(final ChildWpData childWpData) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfChildWpData.insert(_connection, childWpData);
      return null;
    });
  }

  @Override
  public void delete(final Child obj) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfChild.handle(_connection, obj);
      return null;
    });
  }

  @Override
  public void delete(final ChildWpData childWpData) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfChildWpData.handle(_connection, childWpData);
      return null;
    });
  }

  @Override
  public void deleteAll(final ChildWpData... childWpData) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfChildWpData.handleMultiple(_connection, childWpData);
      return null;
    });
  }

  @Override
  public void update(final Child obj) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfChild.handle(_connection, obj);
      return null;
    });
  }

  @Override
  public void update(final ChildWpData childWpData) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfChildWpData.handle(_connection, childWpData);
      return null;
    });
  }

  @Override
  public LiveData<List<ChildData>> getChildData(final String name, final boolean delete) {
    final String _sql = "SELECT * FROM child WHERE name LIKE ? AND deleted = ? order by name";
    return __db.getInvalidationTracker().createLiveData(new String[] {"childWpData",
        "child"}, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (name == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, name);
        }
        _argIndex = 2;
        final int _tmp = delete ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfRegNo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "regNo");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfSex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sex");
        final int _columnIndexOfDob = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dob");
        final int _columnIndexOfMaturity = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maturity");
        final int _columnIndexOfGuardian = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "guardian");
        final int _columnIndexOfTp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "tp");
        final int _columnIndexOfNote = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "note");
        final int _columnIndexOfFht = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fht");
        final int _columnIndexOfMht = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mht");
        final int _columnIndexOfDeleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "deleted");
        final int _columnIndexOfAccessTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "accessTime");
        final LongSparseArray<ArrayList<ChildWpData>> _collectionChildWpData = new LongSparseArray<ArrayList<ChildWpData>>();
        while (_stmt.step()) {
          final Long _tmpKey;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpKey = null;
          } else {
            _tmpKey = _stmt.getLong(_columnIndexOfId);
          }
          if (_tmpKey != null) {
            if (!_collectionChildWpData.containsKey(_tmpKey)) {
              _collectionChildWpData.put(_tmpKey, new ArrayList<ChildWpData>());
            }
          }
        }
        _stmt.reset();
        __fetchRelationshipchildWpDataAssssYmlSmiRoomChildWpData(_connection, _collectionChildWpData);
        final List<ChildData> _result = new ArrayList<ChildData>();
        while (_stmt.step()) {
          final ChildData _item;
          final Child _tmpChild;
          if (!(_stmt.isNull(_columnIndexOfId) && _stmt.isNull(_columnIndexOfRegNo) && _stmt.isNull(_columnIndexOfName) && _stmt.isNull(_columnIndexOfSex) && _stmt.isNull(_columnIndexOfDob) && _stmt.isNull(_columnIndexOfMaturity) && _stmt.isNull(_columnIndexOfGuardian) && _stmt.isNull(_columnIndexOfTp) && _stmt.isNull(_columnIndexOfNote) && _stmt.isNull(_columnIndexOfFht) && _stmt.isNull(_columnIndexOfMht) && _stmt.isNull(_columnIndexOfDeleted) && _stmt.isNull(_columnIndexOfAccessTime))) {
            _tmpChild = new Child();
            final int _tmpId;
            _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
            _tmpChild.setId(_tmpId);
            final String _tmpRegNo;
            if (_stmt.isNull(_columnIndexOfRegNo)) {
              _tmpRegNo = null;
            } else {
              _tmpRegNo = _stmt.getText(_columnIndexOfRegNo);
            }
            _tmpChild.setRegNo(_tmpRegNo);
            final String _tmpName;
            if (_stmt.isNull(_columnIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _stmt.getText(_columnIndexOfName);
            }
            _tmpChild.setName(_tmpName);
            final String _tmpSex;
            if (_stmt.isNull(_columnIndexOfSex)) {
              _tmpSex = null;
            } else {
              _tmpSex = _stmt.getText(_columnIndexOfSex);
            }
            _tmpChild.setSex(_tmpSex);
            final Date _tmpDob;
            final Long _tmp_1;
            if (_stmt.isNull(_columnIndexOfDob)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _stmt.getLong(_columnIndexOfDob);
            }
            _tmpDob = DateConverter.fromTimestamp(_tmp_1);
            _tmpChild.setDob(_tmpDob);
            final int _tmpMaturity;
            _tmpMaturity = (int) (_stmt.getLong(_columnIndexOfMaturity));
            _tmpChild.setMaturity(_tmpMaturity);
            final String _tmpGuardian;
            if (_stmt.isNull(_columnIndexOfGuardian)) {
              _tmpGuardian = null;
            } else {
              _tmpGuardian = _stmt.getText(_columnIndexOfGuardian);
            }
            _tmpChild.setGuardian(_tmpGuardian);
            final String _tmpTp;
            if (_stmt.isNull(_columnIndexOfTp)) {
              _tmpTp = null;
            } else {
              _tmpTp = _stmt.getText(_columnIndexOfTp);
            }
            _tmpChild.setTp(_tmpTp);
            final String _tmpNote;
            if (_stmt.isNull(_columnIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _stmt.getText(_columnIndexOfNote);
            }
            _tmpChild.setNote(_tmpNote);
            final double _tmpFht;
            _tmpFht = _stmt.getDouble(_columnIndexOfFht);
            _tmpChild.setFht(_tmpFht);
            final double _tmpMht;
            _tmpMht = _stmt.getDouble(_columnIndexOfMht);
            _tmpChild.setMht(_tmpMht);
            final boolean _tmpDeleted;
            final int _tmp_2;
            _tmp_2 = (int) (_stmt.getLong(_columnIndexOfDeleted));
            _tmpDeleted = _tmp_2 != 0;
            _tmpChild.setDeleted(_tmpDeleted);
            final Date _tmpAccessTime;
            final Long _tmp_3;
            if (_stmt.isNull(_columnIndexOfAccessTime)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _stmt.getLong(_columnIndexOfAccessTime);
            }
            _tmpAccessTime = DateConverter.fromTimestamp(_tmp_3);
            _tmpChild.setAccessTime(_tmpAccessTime);
          } else {
            _tmpChild = null;
          }
          final ArrayList<ChildWpData> _tmpChildWpDataCollection;
          final Long _tmpKey_1;
          if (_stmt.isNull(_columnIndexOfId)) {
            _tmpKey_1 = null;
          } else {
            _tmpKey_1 = _stmt.getLong(_columnIndexOfId);
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
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<Child> getChild(final int childId, final boolean delete) {
    final String _sql = "SELECT * FROM child where id = ? and deleted = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"child"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, childId);
        _argIndex = 2;
        final int _tmp = delete ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfRegNo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "regNo");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfSex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sex");
        final int _columnIndexOfDob = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dob");
        final int _columnIndexOfMaturity = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maturity");
        final int _columnIndexOfGuardian = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "guardian");
        final int _columnIndexOfTp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "tp");
        final int _columnIndexOfNote = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "note");
        final int _columnIndexOfFht = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fht");
        final int _columnIndexOfMht = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mht");
        final int _columnIndexOfDeleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "deleted");
        final int _columnIndexOfAccessTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "accessTime");
        final Child _result;
        if (_stmt.step()) {
          _result = new Child();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final String _tmpRegNo;
          if (_stmt.isNull(_columnIndexOfRegNo)) {
            _tmpRegNo = null;
          } else {
            _tmpRegNo = _stmt.getText(_columnIndexOfRegNo);
          }
          _result.setRegNo(_tmpRegNo);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _result.setName(_tmpName);
          final String _tmpSex;
          if (_stmt.isNull(_columnIndexOfSex)) {
            _tmpSex = null;
          } else {
            _tmpSex = _stmt.getText(_columnIndexOfSex);
          }
          _result.setSex(_tmpSex);
          final Date _tmpDob;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfDob)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfDob);
          }
          _tmpDob = DateConverter.fromTimestamp(_tmp_1);
          _result.setDob(_tmpDob);
          final int _tmpMaturity;
          _tmpMaturity = (int) (_stmt.getLong(_columnIndexOfMaturity));
          _result.setMaturity(_tmpMaturity);
          final String _tmpGuardian;
          if (_stmt.isNull(_columnIndexOfGuardian)) {
            _tmpGuardian = null;
          } else {
            _tmpGuardian = _stmt.getText(_columnIndexOfGuardian);
          }
          _result.setGuardian(_tmpGuardian);
          final String _tmpTp;
          if (_stmt.isNull(_columnIndexOfTp)) {
            _tmpTp = null;
          } else {
            _tmpTp = _stmt.getText(_columnIndexOfTp);
          }
          _result.setTp(_tmpTp);
          final String _tmpNote;
          if (_stmt.isNull(_columnIndexOfNote)) {
            _tmpNote = null;
          } else {
            _tmpNote = _stmt.getText(_columnIndexOfNote);
          }
          _result.setNote(_tmpNote);
          final double _tmpFht;
          _tmpFht = _stmt.getDouble(_columnIndexOfFht);
          _result.setFht(_tmpFht);
          final double _tmpMht;
          _tmpMht = _stmt.getDouble(_columnIndexOfMht);
          _result.setMht(_tmpMht);
          final boolean _tmpDeleted;
          final int _tmp_2;
          _tmp_2 = (int) (_stmt.getLong(_columnIndexOfDeleted));
          _tmpDeleted = _tmp_2 != 0;
          _result.setDeleted(_tmpDeleted);
          final Date _tmpAccessTime;
          final Long _tmp_3;
          if (_stmt.isNull(_columnIndexOfAccessTime)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfAccessTime);
          }
          _tmpAccessTime = DateConverter.fromTimestamp(_tmp_3);
          _result.setAccessTime(_tmpAccessTime);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Child getChild(final String regNo) {
    final String _sql = "SELECT * FROM child where regNo = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (regNo == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, regNo);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfRegNo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "regNo");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfSex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sex");
        final int _columnIndexOfDob = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dob");
        final int _columnIndexOfMaturity = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maturity");
        final int _columnIndexOfGuardian = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "guardian");
        final int _columnIndexOfTp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "tp");
        final int _columnIndexOfNote = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "note");
        final int _columnIndexOfFht = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fht");
        final int _columnIndexOfMht = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mht");
        final int _columnIndexOfDeleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "deleted");
        final int _columnIndexOfAccessTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "accessTime");
        final Child _result;
        if (_stmt.step()) {
          _result = new Child();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final String _tmpRegNo;
          if (_stmt.isNull(_columnIndexOfRegNo)) {
            _tmpRegNo = null;
          } else {
            _tmpRegNo = _stmt.getText(_columnIndexOfRegNo);
          }
          _result.setRegNo(_tmpRegNo);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _result.setName(_tmpName);
          final String _tmpSex;
          if (_stmt.isNull(_columnIndexOfSex)) {
            _tmpSex = null;
          } else {
            _tmpSex = _stmt.getText(_columnIndexOfSex);
          }
          _result.setSex(_tmpSex);
          final Date _tmpDob;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDob)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDob);
          }
          _tmpDob = DateConverter.fromTimestamp(_tmp);
          _result.setDob(_tmpDob);
          final int _tmpMaturity;
          _tmpMaturity = (int) (_stmt.getLong(_columnIndexOfMaturity));
          _result.setMaturity(_tmpMaturity);
          final String _tmpGuardian;
          if (_stmt.isNull(_columnIndexOfGuardian)) {
            _tmpGuardian = null;
          } else {
            _tmpGuardian = _stmt.getText(_columnIndexOfGuardian);
          }
          _result.setGuardian(_tmpGuardian);
          final String _tmpTp;
          if (_stmt.isNull(_columnIndexOfTp)) {
            _tmpTp = null;
          } else {
            _tmpTp = _stmt.getText(_columnIndexOfTp);
          }
          _result.setTp(_tmpTp);
          final String _tmpNote;
          if (_stmt.isNull(_columnIndexOfNote)) {
            _tmpNote = null;
          } else {
            _tmpNote = _stmt.getText(_columnIndexOfNote);
          }
          _result.setNote(_tmpNote);
          final double _tmpFht;
          _tmpFht = _stmt.getDouble(_columnIndexOfFht);
          _result.setFht(_tmpFht);
          final double _tmpMht;
          _tmpMht = _stmt.getDouble(_columnIndexOfMht);
          _result.setMht(_tmpMht);
          final boolean _tmpDeleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfDeleted));
          _tmpDeleted = _tmp_1 != 0;
          _result.setDeleted(_tmpDeleted);
          final Date _tmpAccessTime;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfAccessTime)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfAccessTime);
          }
          _tmpAccessTime = DateConverter.fromTimestamp(_tmp_2);
          _result.setAccessTime(_tmpAccessTime);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Child getChildByName(final String name) {
    final String _sql = "SELECT * FROM child WHERE upper(name) = upper(?)";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (name == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, name);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfRegNo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "regNo");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfSex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sex");
        final int _columnIndexOfDob = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dob");
        final int _columnIndexOfMaturity = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "maturity");
        final int _columnIndexOfGuardian = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "guardian");
        final int _columnIndexOfTp = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "tp");
        final int _columnIndexOfNote = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "note");
        final int _columnIndexOfFht = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "fht");
        final int _columnIndexOfMht = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mht");
        final int _columnIndexOfDeleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "deleted");
        final int _columnIndexOfAccessTime = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "accessTime");
        final Child _result;
        if (_stmt.step()) {
          _result = new Child();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final String _tmpRegNo;
          if (_stmt.isNull(_columnIndexOfRegNo)) {
            _tmpRegNo = null;
          } else {
            _tmpRegNo = _stmt.getText(_columnIndexOfRegNo);
          }
          _result.setRegNo(_tmpRegNo);
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          _result.setName(_tmpName);
          final String _tmpSex;
          if (_stmt.isNull(_columnIndexOfSex)) {
            _tmpSex = null;
          } else {
            _tmpSex = _stmt.getText(_columnIndexOfSex);
          }
          _result.setSex(_tmpSex);
          final Date _tmpDob;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDob)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDob);
          }
          _tmpDob = DateConverter.fromTimestamp(_tmp);
          _result.setDob(_tmpDob);
          final int _tmpMaturity;
          _tmpMaturity = (int) (_stmt.getLong(_columnIndexOfMaturity));
          _result.setMaturity(_tmpMaturity);
          final String _tmpGuardian;
          if (_stmt.isNull(_columnIndexOfGuardian)) {
            _tmpGuardian = null;
          } else {
            _tmpGuardian = _stmt.getText(_columnIndexOfGuardian);
          }
          _result.setGuardian(_tmpGuardian);
          final String _tmpTp;
          if (_stmt.isNull(_columnIndexOfTp)) {
            _tmpTp = null;
          } else {
            _tmpTp = _stmt.getText(_columnIndexOfTp);
          }
          _result.setTp(_tmpTp);
          final String _tmpNote;
          if (_stmt.isNull(_columnIndexOfNote)) {
            _tmpNote = null;
          } else {
            _tmpNote = _stmt.getText(_columnIndexOfNote);
          }
          _result.setNote(_tmpNote);
          final double _tmpFht;
          _tmpFht = _stmt.getDouble(_columnIndexOfFht);
          _result.setFht(_tmpFht);
          final double _tmpMht;
          _tmpMht = _stmt.getDouble(_columnIndexOfMht);
          _result.setMht(_tmpMht);
          final boolean _tmpDeleted;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfDeleted));
          _tmpDeleted = _tmp_1 != 0;
          _result.setDeleted(_tmpDeleted);
          final Date _tmpAccessTime;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfAccessTime)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfAccessTime);
          }
          _tmpAccessTime = DateConverter.fromTimestamp(_tmp_2);
          _result.setAccessTime(_tmpAccessTime);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<String>> getChildName(final boolean delete) {
    final String _sql = "SELECT name FROM child WHERE deleted = ? order by name";
    return __db.getInvalidationTracker().createLiveData(new String[] {"child"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final int _tmp = delete ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        final List<String> _result = new ArrayList<String>();
        while (_stmt.step()) {
          final String _item;
          if (_stmt.isNull(0)) {
            _item = null;
          } else {
            _item = _stmt.getText(0);
          }
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public Integer getChildId(final String name) {
    final String _sql = "SELECT id FROM child WHERE upper(name) = upper(?) ";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (name == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, name);
        }
        final Integer _result;
        if (_stmt.step()) {
          if (_stmt.isNull(0)) {
            _result = null;
          } else {
            _result = (int) (_stmt.getLong(0));
          }
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public void deleteData(final int childId) {
    final String _sql = "DELETE FROM ChildWpData WHERE childId =? ";
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, childId);
        _stmt.step();
        return null;
      } finally {
        _stmt.close();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipchildWpDataAssssYmlSmiRoomChildWpData(
      @NonNull final SQLiteConnection _connection,
      @NonNull final LongSparseArray<ArrayList<ChildWpData>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > 999) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (_tmpMap) -> {
        __fetchRelationshipchildWpDataAssssYmlSmiRoomChildWpData(_connection, _tmpMap);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = new StringBuilder();
    _stringBuilder.append("SELECT `id`,`childId`,`mDate`,`wt`,`ht`,`note`,`ageDays`,`ageYY`,`ageMM`,`ageDD`,`wfaZ`,`hfaZ`,`wfhZ`,`wfaStatus`,`hfaStatus`,`wfhStatus` FROM `childWpData` WHERE `childId` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final SQLiteStatement _stmt = _connection.prepare(_sql);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    try {
      final int _itemKeyIndex = SQLiteStatementUtil.getColumnIndex(_stmt, "childId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _columnIndexOfId = 0;
      final int _columnIndexOfChildId = 1;
      final int _columnIndexOfMDate = 2;
      final int _columnIndexOfWt = 3;
      final int _columnIndexOfHt = 4;
      final int _columnIndexOfNote = 5;
      final int _columnIndexOfAgeDays = 6;
      final int _columnIndexOfAgeYY = 7;
      final int _columnIndexOfAgeMM = 8;
      final int _columnIndexOfAgeDD = 9;
      final int _columnIndexOfWfaZ = 10;
      final int _columnIndexOfHfaZ = 11;
      final int _columnIndexOfWfhZ = 12;
      final int _columnIndexOfWfaStatus = 13;
      final int _columnIndexOfHfaStatus = 14;
      final int _columnIndexOfWfhStatus = 15;
      while (_stmt.step()) {
        final long _tmpKey;
        _tmpKey = _stmt.getLong(_itemKeyIndex);
        final ArrayList<ChildWpData> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final ChildWpData _item_1;
          _item_1 = new ChildWpData();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item_1.setId(_tmpId);
          final int _tmpChildId;
          _tmpChildId = (int) (_stmt.getLong(_columnIndexOfChildId));
          _item_1.setChildId(_tmpChildId);
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfMDate)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfMDate);
          }
          _item_1.mDate = DateConverter.fromTimestamp(_tmp);
          final double _tmpWt;
          _tmpWt = _stmt.getDouble(_columnIndexOfWt);
          _item_1.setWt(_tmpWt);
          final double _tmpHt;
          _tmpHt = _stmt.getDouble(_columnIndexOfHt);
          _item_1.setHt(_tmpHt);
          final String _tmpNote;
          if (_stmt.isNull(_columnIndexOfNote)) {
            _tmpNote = null;
          } else {
            _tmpNote = _stmt.getText(_columnIndexOfNote);
          }
          _item_1.setNote(_tmpNote);
          final float _tmpAgeDays;
          _tmpAgeDays = (float) (_stmt.getDouble(_columnIndexOfAgeDays));
          _item_1.setAgeDays(_tmpAgeDays);
          final int _tmpAgeYY;
          _tmpAgeYY = (int) (_stmt.getLong(_columnIndexOfAgeYY));
          _item_1.setAgeYY(_tmpAgeYY);
          final int _tmpAgeMM;
          _tmpAgeMM = (int) (_stmt.getLong(_columnIndexOfAgeMM));
          _item_1.setAgeMM(_tmpAgeMM);
          final int _tmpAgeDD;
          _tmpAgeDD = (int) (_stmt.getLong(_columnIndexOfAgeDD));
          _item_1.setAgeDD(_tmpAgeDD);
          final double _tmpWfaZ;
          _tmpWfaZ = _stmt.getDouble(_columnIndexOfWfaZ);
          _item_1.setWfaZ(_tmpWfaZ);
          final double _tmpHfaZ;
          _tmpHfaZ = _stmt.getDouble(_columnIndexOfHfaZ);
          _item_1.setHfaZ(_tmpHfaZ);
          final double _tmpWfhZ;
          _tmpWfhZ = _stmt.getDouble(_columnIndexOfWfhZ);
          _item_1.setWfhZ(_tmpWfhZ);
          final String _tmpWfaStatus;
          if (_stmt.isNull(_columnIndexOfWfaStatus)) {
            _tmpWfaStatus = null;
          } else {
            _tmpWfaStatus = _stmt.getText(_columnIndexOfWfaStatus);
          }
          _item_1.setWfaStatus(_tmpWfaStatus);
          final String _tmpHfaStatus;
          if (_stmt.isNull(_columnIndexOfHfaStatus)) {
            _tmpHfaStatus = null;
          } else {
            _tmpHfaStatus = _stmt.getText(_columnIndexOfHfaStatus);
          }
          _item_1.setHfaStatus(_tmpHfaStatus);
          final String _tmpWfhStatus;
          if (_stmt.isNull(_columnIndexOfWfhStatus)) {
            _tmpWfhStatus = null;
          } else {
            _tmpWfhStatus = _stmt.getText(_columnIndexOfWfhStatus);
          }
          _item_1.setWfhStatus(_tmpWfhStatus);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _stmt.close();
    }
  }
}
