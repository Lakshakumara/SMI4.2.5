package sss.yml.smi.room;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SchoolDao_Impl implements SchoolDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<School> __insertionAdapterOfSchool;

  private final EntityDeletionOrUpdateAdapter<School> __deletionAdapterOfSchool;

  private final EntityDeletionOrUpdateAdapter<School> __updateAdapterOfSchool;

  public SchoolDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSchool = new EntityInsertionAdapter<School>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `school` (`censusNo`,`name`,`address`,`province`,`district`,`zone`,`division`,`level`,`span`,`male`,`female`,`selected`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final School entity) {
        statement.bindLong(1, entity.getCensusNo());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAddress());
        }
        if (entity.getProvince() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProvince());
        }
        if (entity.getDistrict() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDistrict());
        }
        if (entity.getZone() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getZone());
        }
        if (entity.getDivision() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDivision());
        }
        if (entity.getLevel() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getLevel());
        }
        if (entity.getSpan() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getSpan());
        }
        statement.bindLong(10, entity.getMale());
        statement.bindLong(11, entity.getFemale());
        final int _tmp = entity.isSelected() ? 1 : 0;
        statement.bindLong(12, _tmp);
      }
    };
    this.__deletionAdapterOfSchool = new EntityDeletionOrUpdateAdapter<School>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `school` WHERE `censusNo` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final School entity) {
        statement.bindLong(1, entity.getCensusNo());
      }
    };
    this.__updateAdapterOfSchool = new EntityDeletionOrUpdateAdapter<School>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `school` SET `censusNo` = ?,`name` = ?,`address` = ?,`province` = ?,`district` = ?,`zone` = ?,`division` = ?,`level` = ?,`span` = ?,`male` = ?,`female` = ?,`selected` = ? WHERE `censusNo` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final School entity) {
        statement.bindLong(1, entity.getCensusNo());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getAddress());
        }
        if (entity.getProvince() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getProvince());
        }
        if (entity.getDistrict() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getDistrict());
        }
        if (entity.getZone() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getZone());
        }
        if (entity.getDivision() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDivision());
        }
        if (entity.getLevel() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getLevel());
        }
        if (entity.getSpan() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getSpan());
        }
        statement.bindLong(10, entity.getMale());
        statement.bindLong(11, entity.getFemale());
        final int _tmp = entity.isSelected() ? 1 : 0;
        statement.bindLong(12, _tmp);
        statement.bindLong(13, entity.getCensusNo());
      }
    };
  }

  @Override
  public Long insert(final School obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Long _result = __insertionAdapterOfSchool.insertAndReturnId(obj);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final School obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSchool.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final School obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfSchool.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public School getSchool(final int censusNo) {
    final String _sql = "SELECT * FROM school where censusNo = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, censusNo);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCensusNo = CursorUtil.getColumnIndexOrThrow(_cursor, "censusNo");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfProvince = CursorUtil.getColumnIndexOrThrow(_cursor, "province");
      final int _cursorIndexOfDistrict = CursorUtil.getColumnIndexOrThrow(_cursor, "district");
      final int _cursorIndexOfZone = CursorUtil.getColumnIndexOrThrow(_cursor, "zone");
      final int _cursorIndexOfDivision = CursorUtil.getColumnIndexOrThrow(_cursor, "division");
      final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
      final int _cursorIndexOfSpan = CursorUtil.getColumnIndexOrThrow(_cursor, "span");
      final int _cursorIndexOfMale = CursorUtil.getColumnIndexOrThrow(_cursor, "male");
      final int _cursorIndexOfFemale = CursorUtil.getColumnIndexOrThrow(_cursor, "female");
      final int _cursorIndexOfSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "selected");
      final School _result;
      if (_cursor.moveToFirst()) {
        final int _tmpCensusNo;
        _tmpCensusNo = _cursor.getInt(_cursorIndexOfCensusNo);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpAddress;
        if (_cursor.isNull(_cursorIndexOfAddress)) {
          _tmpAddress = null;
        } else {
          _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        }
        final String _tmpProvince;
        if (_cursor.isNull(_cursorIndexOfProvince)) {
          _tmpProvince = null;
        } else {
          _tmpProvince = _cursor.getString(_cursorIndexOfProvince);
        }
        final String _tmpDistrict;
        if (_cursor.isNull(_cursorIndexOfDistrict)) {
          _tmpDistrict = null;
        } else {
          _tmpDistrict = _cursor.getString(_cursorIndexOfDistrict);
        }
        final String _tmpZone;
        if (_cursor.isNull(_cursorIndexOfZone)) {
          _tmpZone = null;
        } else {
          _tmpZone = _cursor.getString(_cursorIndexOfZone);
        }
        final String _tmpDivision;
        if (_cursor.isNull(_cursorIndexOfDivision)) {
          _tmpDivision = null;
        } else {
          _tmpDivision = _cursor.getString(_cursorIndexOfDivision);
        }
        final String _tmpLevel;
        if (_cursor.isNull(_cursorIndexOfLevel)) {
          _tmpLevel = null;
        } else {
          _tmpLevel = _cursor.getString(_cursorIndexOfLevel);
        }
        final String _tmpSpan;
        if (_cursor.isNull(_cursorIndexOfSpan)) {
          _tmpSpan = null;
        } else {
          _tmpSpan = _cursor.getString(_cursorIndexOfSpan);
        }
        final int _tmpMale;
        _tmpMale = _cursor.getInt(_cursorIndexOfMale);
        final int _tmpFemale;
        _tmpFemale = _cursor.getInt(_cursorIndexOfFemale);
        final boolean _tmpSelected;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfSelected);
        _tmpSelected = _tmp != 0;
        _result = new School(_tmpCensusNo,_tmpName,_tmpAddress,_tmpProvince,_tmpDistrict,_tmpZone,_tmpDivision,_tmpLevel,_tmpSpan,_tmpMale,_tmpFemale,_tmpSelected);
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
  public School getSchool(final String district, final String name) {
    final String _sql = "SELECT * FROM school where district = ? and upper(name) like upper(?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (district == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, district);
    }
    _argIndex = 2;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCensusNo = CursorUtil.getColumnIndexOrThrow(_cursor, "censusNo");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfProvince = CursorUtil.getColumnIndexOrThrow(_cursor, "province");
      final int _cursorIndexOfDistrict = CursorUtil.getColumnIndexOrThrow(_cursor, "district");
      final int _cursorIndexOfZone = CursorUtil.getColumnIndexOrThrow(_cursor, "zone");
      final int _cursorIndexOfDivision = CursorUtil.getColumnIndexOrThrow(_cursor, "division");
      final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
      final int _cursorIndexOfSpan = CursorUtil.getColumnIndexOrThrow(_cursor, "span");
      final int _cursorIndexOfMale = CursorUtil.getColumnIndexOrThrow(_cursor, "male");
      final int _cursorIndexOfFemale = CursorUtil.getColumnIndexOrThrow(_cursor, "female");
      final int _cursorIndexOfSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "selected");
      final School _result;
      if (_cursor.moveToFirst()) {
        final int _tmpCensusNo;
        _tmpCensusNo = _cursor.getInt(_cursorIndexOfCensusNo);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpAddress;
        if (_cursor.isNull(_cursorIndexOfAddress)) {
          _tmpAddress = null;
        } else {
          _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        }
        final String _tmpProvince;
        if (_cursor.isNull(_cursorIndexOfProvince)) {
          _tmpProvince = null;
        } else {
          _tmpProvince = _cursor.getString(_cursorIndexOfProvince);
        }
        final String _tmpDistrict;
        if (_cursor.isNull(_cursorIndexOfDistrict)) {
          _tmpDistrict = null;
        } else {
          _tmpDistrict = _cursor.getString(_cursorIndexOfDistrict);
        }
        final String _tmpZone;
        if (_cursor.isNull(_cursorIndexOfZone)) {
          _tmpZone = null;
        } else {
          _tmpZone = _cursor.getString(_cursorIndexOfZone);
        }
        final String _tmpDivision;
        if (_cursor.isNull(_cursorIndexOfDivision)) {
          _tmpDivision = null;
        } else {
          _tmpDivision = _cursor.getString(_cursorIndexOfDivision);
        }
        final String _tmpLevel;
        if (_cursor.isNull(_cursorIndexOfLevel)) {
          _tmpLevel = null;
        } else {
          _tmpLevel = _cursor.getString(_cursorIndexOfLevel);
        }
        final String _tmpSpan;
        if (_cursor.isNull(_cursorIndexOfSpan)) {
          _tmpSpan = null;
        } else {
          _tmpSpan = _cursor.getString(_cursorIndexOfSpan);
        }
        final int _tmpMale;
        _tmpMale = _cursor.getInt(_cursorIndexOfMale);
        final int _tmpFemale;
        _tmpFemale = _cursor.getInt(_cursorIndexOfFemale);
        final boolean _tmpSelected;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfSelected);
        _tmpSelected = _tmp != 0;
        _result = new School(_tmpCensusNo,_tmpName,_tmpAddress,_tmpProvince,_tmpDistrict,_tmpZone,_tmpDivision,_tmpLevel,_tmpSpan,_tmpMale,_tmpFemale,_tmpSelected);
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
  public List<School> getSchoolList(final boolean deleted) {
    final String _sql = "SELECT * FROM school where selected = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final int _tmp = deleted ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfCensusNo = CursorUtil.getColumnIndexOrThrow(_cursor, "censusNo");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "address");
      final int _cursorIndexOfProvince = CursorUtil.getColumnIndexOrThrow(_cursor, "province");
      final int _cursorIndexOfDistrict = CursorUtil.getColumnIndexOrThrow(_cursor, "district");
      final int _cursorIndexOfZone = CursorUtil.getColumnIndexOrThrow(_cursor, "zone");
      final int _cursorIndexOfDivision = CursorUtil.getColumnIndexOrThrow(_cursor, "division");
      final int _cursorIndexOfLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
      final int _cursorIndexOfSpan = CursorUtil.getColumnIndexOrThrow(_cursor, "span");
      final int _cursorIndexOfMale = CursorUtil.getColumnIndexOrThrow(_cursor, "male");
      final int _cursorIndexOfFemale = CursorUtil.getColumnIndexOrThrow(_cursor, "female");
      final int _cursorIndexOfSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "selected");
      final List<School> _result = new ArrayList<School>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final School _item;
        final int _tmpCensusNo;
        _tmpCensusNo = _cursor.getInt(_cursorIndexOfCensusNo);
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpAddress;
        if (_cursor.isNull(_cursorIndexOfAddress)) {
          _tmpAddress = null;
        } else {
          _tmpAddress = _cursor.getString(_cursorIndexOfAddress);
        }
        final String _tmpProvince;
        if (_cursor.isNull(_cursorIndexOfProvince)) {
          _tmpProvince = null;
        } else {
          _tmpProvince = _cursor.getString(_cursorIndexOfProvince);
        }
        final String _tmpDistrict;
        if (_cursor.isNull(_cursorIndexOfDistrict)) {
          _tmpDistrict = null;
        } else {
          _tmpDistrict = _cursor.getString(_cursorIndexOfDistrict);
        }
        final String _tmpZone;
        if (_cursor.isNull(_cursorIndexOfZone)) {
          _tmpZone = null;
        } else {
          _tmpZone = _cursor.getString(_cursorIndexOfZone);
        }
        final String _tmpDivision;
        if (_cursor.isNull(_cursorIndexOfDivision)) {
          _tmpDivision = null;
        } else {
          _tmpDivision = _cursor.getString(_cursorIndexOfDivision);
        }
        final String _tmpLevel;
        if (_cursor.isNull(_cursorIndexOfLevel)) {
          _tmpLevel = null;
        } else {
          _tmpLevel = _cursor.getString(_cursorIndexOfLevel);
        }
        final String _tmpSpan;
        if (_cursor.isNull(_cursorIndexOfSpan)) {
          _tmpSpan = null;
        } else {
          _tmpSpan = _cursor.getString(_cursorIndexOfSpan);
        }
        final int _tmpMale;
        _tmpMale = _cursor.getInt(_cursorIndexOfMale);
        final int _tmpFemale;
        _tmpFemale = _cursor.getInt(_cursorIndexOfFemale);
        final boolean _tmpSelected;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfSelected);
        _tmpSelected = _tmp_1 != 0;
        _item = new School(_tmpCensusNo,_tmpName,_tmpAddress,_tmpProvince,_tmpDistrict,_tmpZone,_tmpDivision,_tmpLevel,_tmpSpan,_tmpMale,_tmpFemale,_tmpSelected);
        _result.add(_item);
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
}
