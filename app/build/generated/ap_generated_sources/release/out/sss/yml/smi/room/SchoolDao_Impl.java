package sss.yml.smi.room;

import androidx.annotation.NonNull;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class SchoolDao_Impl implements SchoolDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<School> __insertAdapterOfSchool;

  private final EntityDeleteOrUpdateAdapter<School> __deleteAdapterOfSchool;

  private final EntityDeleteOrUpdateAdapter<School> __updateAdapterOfSchool;

  public SchoolDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfSchool = new EntityInsertAdapter<School>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `school` (`censusNo`,`name`,`address`,`province`,`district`,`zone`,`division`,`level`,`span`,`male`,`female`,`selected`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final School entity) {
        statement.bindLong(1, entity.getCensusNo());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getAddress());
        }
        if (entity.getProvince() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getProvince());
        }
        if (entity.getDistrict() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getDistrict());
        }
        if (entity.getZone() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getZone());
        }
        if (entity.getDivision() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getDivision());
        }
        if (entity.getLevel() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getLevel());
        }
        if (entity.getSpan() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getSpan());
        }
        statement.bindLong(10, entity.getMale());
        statement.bindLong(11, entity.getFemale());
        final int _tmp = entity.isSelected() ? 1 : 0;
        statement.bindLong(12, _tmp);
      }
    };
    this.__deleteAdapterOfSchool = new EntityDeleteOrUpdateAdapter<School>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `school` WHERE `censusNo` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final School entity) {
        statement.bindLong(1, entity.getCensusNo());
      }
    };
    this.__updateAdapterOfSchool = new EntityDeleteOrUpdateAdapter<School>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `school` SET `censusNo` = ?,`name` = ?,`address` = ?,`province` = ?,`district` = ?,`zone` = ?,`division` = ?,`level` = ?,`span` = ?,`male` = ?,`female` = ?,`selected` = ? WHERE `censusNo` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final School entity) {
        statement.bindLong(1, entity.getCensusNo());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getName());
        }
        if (entity.getAddress() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getAddress());
        }
        if (entity.getProvince() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getProvince());
        }
        if (entity.getDistrict() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getDistrict());
        }
        if (entity.getZone() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getZone());
        }
        if (entity.getDivision() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getDivision());
        }
        if (entity.getLevel() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getLevel());
        }
        if (entity.getSpan() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getSpan());
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
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfSchool.insertAndReturnId(_connection, obj);
    });
  }

  @Override
  public void delete(final School obj) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfSchool.handle(_connection, obj);
      return null;
    });
  }

  @Override
  public void update(final School obj) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfSchool.handle(_connection, obj);
      return null;
    });
  }

  @Override
  public School getSchool(final int censusNo) {
    final String _sql = "SELECT * FROM school where censusNo = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, censusNo);
        final int _columnIndexOfCensusNo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "censusNo");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfAddress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "address");
        final int _columnIndexOfProvince = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "province");
        final int _columnIndexOfDistrict = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "district");
        final int _columnIndexOfZone = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "zone");
        final int _columnIndexOfDivision = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "division");
        final int _columnIndexOfLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "level");
        final int _columnIndexOfSpan = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "span");
        final int _columnIndexOfMale = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "male");
        final int _columnIndexOfFemale = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "female");
        final int _columnIndexOfSelected = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "selected");
        final School _result;
        if (_stmt.step()) {
          final int _tmpCensusNo;
          _tmpCensusNo = (int) (_stmt.getLong(_columnIndexOfCensusNo));
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpAddress;
          if (_stmt.isNull(_columnIndexOfAddress)) {
            _tmpAddress = null;
          } else {
            _tmpAddress = _stmt.getText(_columnIndexOfAddress);
          }
          final String _tmpProvince;
          if (_stmt.isNull(_columnIndexOfProvince)) {
            _tmpProvince = null;
          } else {
            _tmpProvince = _stmt.getText(_columnIndexOfProvince);
          }
          final String _tmpDistrict;
          if (_stmt.isNull(_columnIndexOfDistrict)) {
            _tmpDistrict = null;
          } else {
            _tmpDistrict = _stmt.getText(_columnIndexOfDistrict);
          }
          final String _tmpZone;
          if (_stmt.isNull(_columnIndexOfZone)) {
            _tmpZone = null;
          } else {
            _tmpZone = _stmt.getText(_columnIndexOfZone);
          }
          final String _tmpDivision;
          if (_stmt.isNull(_columnIndexOfDivision)) {
            _tmpDivision = null;
          } else {
            _tmpDivision = _stmt.getText(_columnIndexOfDivision);
          }
          final String _tmpLevel;
          if (_stmt.isNull(_columnIndexOfLevel)) {
            _tmpLevel = null;
          } else {
            _tmpLevel = _stmt.getText(_columnIndexOfLevel);
          }
          final String _tmpSpan;
          if (_stmt.isNull(_columnIndexOfSpan)) {
            _tmpSpan = null;
          } else {
            _tmpSpan = _stmt.getText(_columnIndexOfSpan);
          }
          final int _tmpMale;
          _tmpMale = (int) (_stmt.getLong(_columnIndexOfMale));
          final int _tmpFemale;
          _tmpFemale = (int) (_stmt.getLong(_columnIndexOfFemale));
          final boolean _tmpSelected;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfSelected));
          _tmpSelected = _tmp != 0;
          _result = new School(_tmpCensusNo,_tmpName,_tmpAddress,_tmpProvince,_tmpDistrict,_tmpZone,_tmpDivision,_tmpLevel,_tmpSpan,_tmpMale,_tmpFemale,_tmpSelected);
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
  public School getSchool(final String district, final String name) {
    final String _sql = "SELECT * FROM school where district = ? and upper(name) like upper(?)";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (district == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, district);
        }
        _argIndex = 2;
        if (name == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, name);
        }
        final int _columnIndexOfCensusNo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "censusNo");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfAddress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "address");
        final int _columnIndexOfProvince = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "province");
        final int _columnIndexOfDistrict = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "district");
        final int _columnIndexOfZone = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "zone");
        final int _columnIndexOfDivision = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "division");
        final int _columnIndexOfLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "level");
        final int _columnIndexOfSpan = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "span");
        final int _columnIndexOfMale = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "male");
        final int _columnIndexOfFemale = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "female");
        final int _columnIndexOfSelected = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "selected");
        final School _result;
        if (_stmt.step()) {
          final int _tmpCensusNo;
          _tmpCensusNo = (int) (_stmt.getLong(_columnIndexOfCensusNo));
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpAddress;
          if (_stmt.isNull(_columnIndexOfAddress)) {
            _tmpAddress = null;
          } else {
            _tmpAddress = _stmt.getText(_columnIndexOfAddress);
          }
          final String _tmpProvince;
          if (_stmt.isNull(_columnIndexOfProvince)) {
            _tmpProvince = null;
          } else {
            _tmpProvince = _stmt.getText(_columnIndexOfProvince);
          }
          final String _tmpDistrict;
          if (_stmt.isNull(_columnIndexOfDistrict)) {
            _tmpDistrict = null;
          } else {
            _tmpDistrict = _stmt.getText(_columnIndexOfDistrict);
          }
          final String _tmpZone;
          if (_stmt.isNull(_columnIndexOfZone)) {
            _tmpZone = null;
          } else {
            _tmpZone = _stmt.getText(_columnIndexOfZone);
          }
          final String _tmpDivision;
          if (_stmt.isNull(_columnIndexOfDivision)) {
            _tmpDivision = null;
          } else {
            _tmpDivision = _stmt.getText(_columnIndexOfDivision);
          }
          final String _tmpLevel;
          if (_stmt.isNull(_columnIndexOfLevel)) {
            _tmpLevel = null;
          } else {
            _tmpLevel = _stmt.getText(_columnIndexOfLevel);
          }
          final String _tmpSpan;
          if (_stmt.isNull(_columnIndexOfSpan)) {
            _tmpSpan = null;
          } else {
            _tmpSpan = _stmt.getText(_columnIndexOfSpan);
          }
          final int _tmpMale;
          _tmpMale = (int) (_stmt.getLong(_columnIndexOfMale));
          final int _tmpFemale;
          _tmpFemale = (int) (_stmt.getLong(_columnIndexOfFemale));
          final boolean _tmpSelected;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfSelected));
          _tmpSelected = _tmp != 0;
          _result = new School(_tmpCensusNo,_tmpName,_tmpAddress,_tmpProvince,_tmpDistrict,_tmpZone,_tmpDivision,_tmpLevel,_tmpSpan,_tmpMale,_tmpFemale,_tmpSelected);
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
  public List<School> getSchoolList(final boolean deleted) {
    final String _sql = "SELECT * FROM school where selected = ?";
    return DBUtil.performBlocking(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        final int _tmp = deleted ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        final int _columnIndexOfCensusNo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "censusNo");
        final int _columnIndexOfName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "name");
        final int _columnIndexOfAddress = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "address");
        final int _columnIndexOfProvince = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "province");
        final int _columnIndexOfDistrict = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "district");
        final int _columnIndexOfZone = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "zone");
        final int _columnIndexOfDivision = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "division");
        final int _columnIndexOfLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "level");
        final int _columnIndexOfSpan = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "span");
        final int _columnIndexOfMale = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "male");
        final int _columnIndexOfFemale = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "female");
        final int _columnIndexOfSelected = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "selected");
        final List<School> _result = new ArrayList<School>();
        while (_stmt.step()) {
          final School _item;
          final int _tmpCensusNo;
          _tmpCensusNo = (int) (_stmt.getLong(_columnIndexOfCensusNo));
          final String _tmpName;
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName);
          }
          final String _tmpAddress;
          if (_stmt.isNull(_columnIndexOfAddress)) {
            _tmpAddress = null;
          } else {
            _tmpAddress = _stmt.getText(_columnIndexOfAddress);
          }
          final String _tmpProvince;
          if (_stmt.isNull(_columnIndexOfProvince)) {
            _tmpProvince = null;
          } else {
            _tmpProvince = _stmt.getText(_columnIndexOfProvince);
          }
          final String _tmpDistrict;
          if (_stmt.isNull(_columnIndexOfDistrict)) {
            _tmpDistrict = null;
          } else {
            _tmpDistrict = _stmt.getText(_columnIndexOfDistrict);
          }
          final String _tmpZone;
          if (_stmt.isNull(_columnIndexOfZone)) {
            _tmpZone = null;
          } else {
            _tmpZone = _stmt.getText(_columnIndexOfZone);
          }
          final String _tmpDivision;
          if (_stmt.isNull(_columnIndexOfDivision)) {
            _tmpDivision = null;
          } else {
            _tmpDivision = _stmt.getText(_columnIndexOfDivision);
          }
          final String _tmpLevel;
          if (_stmt.isNull(_columnIndexOfLevel)) {
            _tmpLevel = null;
          } else {
            _tmpLevel = _stmt.getText(_columnIndexOfLevel);
          }
          final String _tmpSpan;
          if (_stmt.isNull(_columnIndexOfSpan)) {
            _tmpSpan = null;
          } else {
            _tmpSpan = _stmt.getText(_columnIndexOfSpan);
          }
          final int _tmpMale;
          _tmpMale = (int) (_stmt.getLong(_columnIndexOfMale));
          final int _tmpFemale;
          _tmpFemale = (int) (_stmt.getLong(_columnIndexOfFemale));
          final boolean _tmpSelected;
          final int _tmp_1;
          _tmp_1 = (int) (_stmt.getLong(_columnIndexOfSelected));
          _tmpSelected = _tmp_1 != 0;
          _item = new School(_tmpCensusNo,_tmpName,_tmpAddress,_tmpProvince,_tmpDistrict,_tmpZone,_tmpDivision,_tmpLevel,_tmpSpan,_tmpMale,_tmpFemale,_tmpSelected);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
