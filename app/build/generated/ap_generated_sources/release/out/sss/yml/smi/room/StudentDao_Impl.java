package sss.yml.smi.room;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
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
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class StudentDao_Impl implements StudentDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Student> __insertAdapterOfStudent;

  private final EntityDeleteOrUpdateAdapter<Student> __deleteAdapterOfStudent;

  private final EntityDeleteOrUpdateAdapter<Student> __updateAdapterOfStudent;

  public StudentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfStudent = new EntityInsertAdapter<Student>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `student` (`id`,`censusNo`,`refId`,`userName`,`grade`,`dob`,`mdate`,`sex`,`height`,`weight`,`ageY`,`ageM`,`ageD`,`bmi`,`wtZ`,`htZ`,`wtStatus`,`wtColor`,`htStatus`,`htColor`,`wtPos`,`htPos`,`docRef`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCensusNo());
        statement.bindLong(3, entity.getRefId());
        if (entity.getUserName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getUserName());
        }
        if (entity.getGrade() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getGrade());
        }
        final Long _tmp = DateConverter.dateToTimestamp(entity.getDob());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp);
        }
        final Long _tmp_1 = DateConverter.dateToTimestamp(entity.getMdate());
        if (_tmp_1 == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp_1);
        }
        if (entity.getSex() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getSex());
        }
        statement.bindDouble(9, entity.getHeight());
        statement.bindDouble(10, entity.getWeight());
        statement.bindLong(11, entity.getAgeY());
        statement.bindLong(12, entity.getAgeM());
        statement.bindLong(13, entity.getAgeD());
        statement.bindDouble(14, entity.getBmi());
        statement.bindDouble(15, entity.getWtZ());
        statement.bindDouble(16, entity.getHtZ());
        if (entity.getWtStatus() == null) {
          statement.bindNull(17);
        } else {
          statement.bindText(17, entity.getWtStatus());
        }
        statement.bindLong(18, entity.getWtColor());
        if (entity.getHtStatus() == null) {
          statement.bindNull(19);
        } else {
          statement.bindText(19, entity.getHtStatus());
        }
        statement.bindLong(20, entity.getHtColor());
        if (entity.getWtPos() == null) {
          statement.bindNull(21);
        } else {
          statement.bindText(21, entity.getWtPos());
        }
        if (entity.getHtPos() == null) {
          statement.bindNull(22);
        } else {
          statement.bindText(22, entity.getHtPos());
        }
        if (entity.getDocRef() == null) {
          statement.bindNull(23);
        } else {
          statement.bindText(23, entity.getDocRef());
        }
      }
    };
    this.__deleteAdapterOfStudent = new EntityDeleteOrUpdateAdapter<Student>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `student` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfStudent = new EntityDeleteOrUpdateAdapter<Student>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `student` SET `id` = ?,`censusNo` = ?,`refId` = ?,`userName` = ?,`grade` = ?,`dob` = ?,`mdate` = ?,`sex` = ?,`height` = ?,`weight` = ?,`ageY` = ?,`ageM` = ?,`ageD` = ?,`bmi` = ?,`wtZ` = ?,`htZ` = ?,`wtStatus` = ?,`wtColor` = ?,`htStatus` = ?,`htColor` = ?,`wtPos` = ?,`htPos` = ?,`docRef` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCensusNo());
        statement.bindLong(3, entity.getRefId());
        if (entity.getUserName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getUserName());
        }
        if (entity.getGrade() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getGrade());
        }
        final Long _tmp = DateConverter.dateToTimestamp(entity.getDob());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp);
        }
        final Long _tmp_1 = DateConverter.dateToTimestamp(entity.getMdate());
        if (_tmp_1 == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp_1);
        }
        if (entity.getSex() == null) {
          statement.bindNull(8);
        } else {
          statement.bindText(8, entity.getSex());
        }
        statement.bindDouble(9, entity.getHeight());
        statement.bindDouble(10, entity.getWeight());
        statement.bindLong(11, entity.getAgeY());
        statement.bindLong(12, entity.getAgeM());
        statement.bindLong(13, entity.getAgeD());
        statement.bindDouble(14, entity.getBmi());
        statement.bindDouble(15, entity.getWtZ());
        statement.bindDouble(16, entity.getHtZ());
        if (entity.getWtStatus() == null) {
          statement.bindNull(17);
        } else {
          statement.bindText(17, entity.getWtStatus());
        }
        statement.bindLong(18, entity.getWtColor());
        if (entity.getHtStatus() == null) {
          statement.bindNull(19);
        } else {
          statement.bindText(19, entity.getHtStatus());
        }
        statement.bindLong(20, entity.getHtColor());
        if (entity.getWtPos() == null) {
          statement.bindNull(21);
        } else {
          statement.bindText(21, entity.getWtPos());
        }
        if (entity.getHtPos() == null) {
          statement.bindNull(22);
        } else {
          statement.bindText(22, entity.getHtPos());
        }
        if (entity.getDocRef() == null) {
          statement.bindNull(23);
        } else {
          statement.bindText(23, entity.getDocRef());
        }
        statement.bindLong(24, entity.getId());
      }
    };
  }

  @Override
  public Long insert(final Student obj) {
    return DBUtil.performBlocking(__db, false, true, (_connection) -> {
      return __insertAdapterOfStudent.insertAndReturnId(_connection, obj);
    });
  }

  @Override
  public void delete(final Student obj) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfStudent.handle(_connection, obj);
      return null;
    });
  }

  @Override
  public void update(final Student obj) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfStudent.handle(_connection, obj);
      return null;
    });
  }

  @Override
  public LiveData<Student> getStudent(final int id) {
    final String _sql = "SELECT * FROM student where id = ?";
    return __db.getInvalidationTracker().createLiveData(new String[] {"student"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfCensusNo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "censusNo");
        final int _columnIndexOfRefId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "refId");
        final int _columnIndexOfUserName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userName");
        final int _columnIndexOfGrade = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "grade");
        final int _columnIndexOfDob = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dob");
        final int _columnIndexOfMdate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mdate");
        final int _columnIndexOfSex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sex");
        final int _columnIndexOfHeight = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "height");
        final int _columnIndexOfWeight = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "weight");
        final int _columnIndexOfAgeY = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ageY");
        final int _columnIndexOfAgeM = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ageM");
        final int _columnIndexOfAgeD = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ageD");
        final int _columnIndexOfBmi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bmi");
        final int _columnIndexOfWtZ = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtZ");
        final int _columnIndexOfHtZ = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htZ");
        final int _columnIndexOfWtStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtStatus");
        final int _columnIndexOfWtColor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtColor");
        final int _columnIndexOfHtStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htStatus");
        final int _columnIndexOfHtColor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htColor");
        final int _columnIndexOfWtPos = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtPos");
        final int _columnIndexOfHtPos = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htPos");
        final int _columnIndexOfDocRef = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "docRef");
        final Student _result;
        if (_stmt.step()) {
          _result = new Student();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _result.setId(_tmpId);
          final int _tmpCensusNo;
          _tmpCensusNo = (int) (_stmt.getLong(_columnIndexOfCensusNo));
          _result.setCensusNo(_tmpCensusNo);
          final int _tmpRefId;
          _tmpRefId = (int) (_stmt.getLong(_columnIndexOfRefId));
          _result.setRefId(_tmpRefId);
          final String _tmpUserName;
          if (_stmt.isNull(_columnIndexOfUserName)) {
            _tmpUserName = null;
          } else {
            _tmpUserName = _stmt.getText(_columnIndexOfUserName);
          }
          _result.setUserName(_tmpUserName);
          final String _tmpGrade;
          if (_stmt.isNull(_columnIndexOfGrade)) {
            _tmpGrade = null;
          } else {
            _tmpGrade = _stmt.getText(_columnIndexOfGrade);
          }
          _result.setGrade(_tmpGrade);
          final Date _tmpDob;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDob)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDob);
          }
          _tmpDob = DateConverter.fromTimestamp(_tmp);
          _result.setDob(_tmpDob);
          final Date _tmpMdate;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfMdate)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfMdate);
          }
          _tmpMdate = DateConverter.fromTimestamp(_tmp_1);
          _result.setMdate(_tmpMdate);
          final String _tmpSex;
          if (_stmt.isNull(_columnIndexOfSex)) {
            _tmpSex = null;
          } else {
            _tmpSex = _stmt.getText(_columnIndexOfSex);
          }
          _result.setSex(_tmpSex);
          final double _tmpHeight;
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight);
          _result.setHeight(_tmpHeight);
          final double _tmpWeight;
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight);
          _result.setWeight(_tmpWeight);
          final int _tmpAgeY;
          _tmpAgeY = (int) (_stmt.getLong(_columnIndexOfAgeY));
          _result.setAgeY(_tmpAgeY);
          final int _tmpAgeM;
          _tmpAgeM = (int) (_stmt.getLong(_columnIndexOfAgeM));
          _result.setAgeM(_tmpAgeM);
          final int _tmpAgeD;
          _tmpAgeD = (int) (_stmt.getLong(_columnIndexOfAgeD));
          _result.setAgeD(_tmpAgeD);
          final double _tmpBmi;
          _tmpBmi = _stmt.getDouble(_columnIndexOfBmi);
          _result.setBmi(_tmpBmi);
          final double _tmpWtZ;
          _tmpWtZ = _stmt.getDouble(_columnIndexOfWtZ);
          _result.setWtZ(_tmpWtZ);
          final double _tmpHtZ;
          _tmpHtZ = _stmt.getDouble(_columnIndexOfHtZ);
          _result.setHtZ(_tmpHtZ);
          final String _tmpWtStatus;
          if (_stmt.isNull(_columnIndexOfWtStatus)) {
            _tmpWtStatus = null;
          } else {
            _tmpWtStatus = _stmt.getText(_columnIndexOfWtStatus);
          }
          _result.setWtStatus(_tmpWtStatus);
          final int _tmpWtColor;
          _tmpWtColor = (int) (_stmt.getLong(_columnIndexOfWtColor));
          _result.setWtColor(_tmpWtColor);
          final String _tmpHtStatus;
          if (_stmt.isNull(_columnIndexOfHtStatus)) {
            _tmpHtStatus = null;
          } else {
            _tmpHtStatus = _stmt.getText(_columnIndexOfHtStatus);
          }
          _result.setHtStatus(_tmpHtStatus);
          final int _tmpHtColor;
          _tmpHtColor = (int) (_stmt.getLong(_columnIndexOfHtColor));
          _result.setHtColor(_tmpHtColor);
          final String _tmpWtPos;
          if (_stmt.isNull(_columnIndexOfWtPos)) {
            _tmpWtPos = null;
          } else {
            _tmpWtPos = _stmt.getText(_columnIndexOfWtPos);
          }
          _result.setWtPos(_tmpWtPos);
          final String _tmpHtPos;
          if (_stmt.isNull(_columnIndexOfHtPos)) {
            _tmpHtPos = null;
          } else {
            _tmpHtPos = _stmt.getText(_columnIndexOfHtPos);
          }
          _result.setHtPos(_tmpHtPos);
          final String _tmpDocRef;
          if (_stmt.isNull(_columnIndexOfDocRef)) {
            _tmpDocRef = null;
          } else {
            _tmpDocRef = _stmt.getText(_columnIndexOfDocRef);
          }
          _result.setDocRef(_tmpDocRef);
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
  public LiveData<List<Student>> getStudentList() {
    final String _sql = "SELECT * FROM student";
    return __db.getInvalidationTracker().createLiveData(new String[] {"student"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfCensusNo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "censusNo");
        final int _columnIndexOfRefId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "refId");
        final int _columnIndexOfUserName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userName");
        final int _columnIndexOfGrade = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "grade");
        final int _columnIndexOfDob = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dob");
        final int _columnIndexOfMdate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mdate");
        final int _columnIndexOfSex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sex");
        final int _columnIndexOfHeight = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "height");
        final int _columnIndexOfWeight = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "weight");
        final int _columnIndexOfAgeY = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ageY");
        final int _columnIndexOfAgeM = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ageM");
        final int _columnIndexOfAgeD = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ageD");
        final int _columnIndexOfBmi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bmi");
        final int _columnIndexOfWtZ = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtZ");
        final int _columnIndexOfHtZ = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htZ");
        final int _columnIndexOfWtStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtStatus");
        final int _columnIndexOfWtColor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtColor");
        final int _columnIndexOfHtStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htStatus");
        final int _columnIndexOfHtColor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htColor");
        final int _columnIndexOfWtPos = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtPos");
        final int _columnIndexOfHtPos = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htPos");
        final int _columnIndexOfDocRef = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "docRef");
        final List<Student> _result = new ArrayList<Student>();
        while (_stmt.step()) {
          final Student _item;
          _item = new Student();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpCensusNo;
          _tmpCensusNo = (int) (_stmt.getLong(_columnIndexOfCensusNo));
          _item.setCensusNo(_tmpCensusNo);
          final int _tmpRefId;
          _tmpRefId = (int) (_stmt.getLong(_columnIndexOfRefId));
          _item.setRefId(_tmpRefId);
          final String _tmpUserName;
          if (_stmt.isNull(_columnIndexOfUserName)) {
            _tmpUserName = null;
          } else {
            _tmpUserName = _stmt.getText(_columnIndexOfUserName);
          }
          _item.setUserName(_tmpUserName);
          final String _tmpGrade;
          if (_stmt.isNull(_columnIndexOfGrade)) {
            _tmpGrade = null;
          } else {
            _tmpGrade = _stmt.getText(_columnIndexOfGrade);
          }
          _item.setGrade(_tmpGrade);
          final Date _tmpDob;
          final Long _tmp;
          if (_stmt.isNull(_columnIndexOfDob)) {
            _tmp = null;
          } else {
            _tmp = _stmt.getLong(_columnIndexOfDob);
          }
          _tmpDob = DateConverter.fromTimestamp(_tmp);
          _item.setDob(_tmpDob);
          final Date _tmpMdate;
          final Long _tmp_1;
          if (_stmt.isNull(_columnIndexOfMdate)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfMdate);
          }
          _tmpMdate = DateConverter.fromTimestamp(_tmp_1);
          _item.setMdate(_tmpMdate);
          final String _tmpSex;
          if (_stmt.isNull(_columnIndexOfSex)) {
            _tmpSex = null;
          } else {
            _tmpSex = _stmt.getText(_columnIndexOfSex);
          }
          _item.setSex(_tmpSex);
          final double _tmpHeight;
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight);
          _item.setHeight(_tmpHeight);
          final double _tmpWeight;
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight);
          _item.setWeight(_tmpWeight);
          final int _tmpAgeY;
          _tmpAgeY = (int) (_stmt.getLong(_columnIndexOfAgeY));
          _item.setAgeY(_tmpAgeY);
          final int _tmpAgeM;
          _tmpAgeM = (int) (_stmt.getLong(_columnIndexOfAgeM));
          _item.setAgeM(_tmpAgeM);
          final int _tmpAgeD;
          _tmpAgeD = (int) (_stmt.getLong(_columnIndexOfAgeD));
          _item.setAgeD(_tmpAgeD);
          final double _tmpBmi;
          _tmpBmi = _stmt.getDouble(_columnIndexOfBmi);
          _item.setBmi(_tmpBmi);
          final double _tmpWtZ;
          _tmpWtZ = _stmt.getDouble(_columnIndexOfWtZ);
          _item.setWtZ(_tmpWtZ);
          final double _tmpHtZ;
          _tmpHtZ = _stmt.getDouble(_columnIndexOfHtZ);
          _item.setHtZ(_tmpHtZ);
          final String _tmpWtStatus;
          if (_stmt.isNull(_columnIndexOfWtStatus)) {
            _tmpWtStatus = null;
          } else {
            _tmpWtStatus = _stmt.getText(_columnIndexOfWtStatus);
          }
          _item.setWtStatus(_tmpWtStatus);
          final int _tmpWtColor;
          _tmpWtColor = (int) (_stmt.getLong(_columnIndexOfWtColor));
          _item.setWtColor(_tmpWtColor);
          final String _tmpHtStatus;
          if (_stmt.isNull(_columnIndexOfHtStatus)) {
            _tmpHtStatus = null;
          } else {
            _tmpHtStatus = _stmt.getText(_columnIndexOfHtStatus);
          }
          _item.setHtStatus(_tmpHtStatus);
          final int _tmpHtColor;
          _tmpHtColor = (int) (_stmt.getLong(_columnIndexOfHtColor));
          _item.setHtColor(_tmpHtColor);
          final String _tmpWtPos;
          if (_stmt.isNull(_columnIndexOfWtPos)) {
            _tmpWtPos = null;
          } else {
            _tmpWtPos = _stmt.getText(_columnIndexOfWtPos);
          }
          _item.setWtPos(_tmpWtPos);
          final String _tmpHtPos;
          if (_stmt.isNull(_columnIndexOfHtPos)) {
            _tmpHtPos = null;
          } else {
            _tmpHtPos = _stmt.getText(_columnIndexOfHtPos);
          }
          _item.setHtPos(_tmpHtPos);
          final String _tmpDocRef;
          if (_stmt.isNull(_columnIndexOfDocRef)) {
            _tmpDocRef = null;
          } else {
            _tmpDocRef = _stmt.getText(_columnIndexOfDocRef);
          }
          _item.setDocRef(_tmpDocRef);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Student>> getStudentList(final int censusNo, final Date from, final Date to,
      final String grade) {
    final String _sql = "SELECT * FROM student where censusNo=? and mdate BETWEEN ? and ? and upper(grade) like upper(?)";
    return __db.getInvalidationTracker().createLiveData(new String[] {"student"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, censusNo);
        _argIndex = 2;
        final Long _tmp = DateConverter.dateToTimestamp(from);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 3;
        final Long _tmp_1 = DateConverter.dateToTimestamp(to);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
        _argIndex = 4;
        if (grade == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, grade);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfCensusNo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "censusNo");
        final int _columnIndexOfRefId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "refId");
        final int _columnIndexOfUserName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userName");
        final int _columnIndexOfGrade = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "grade");
        final int _columnIndexOfDob = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dob");
        final int _columnIndexOfMdate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mdate");
        final int _columnIndexOfSex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sex");
        final int _columnIndexOfHeight = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "height");
        final int _columnIndexOfWeight = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "weight");
        final int _columnIndexOfAgeY = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ageY");
        final int _columnIndexOfAgeM = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ageM");
        final int _columnIndexOfAgeD = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ageD");
        final int _columnIndexOfBmi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bmi");
        final int _columnIndexOfWtZ = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtZ");
        final int _columnIndexOfHtZ = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htZ");
        final int _columnIndexOfWtStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtStatus");
        final int _columnIndexOfWtColor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtColor");
        final int _columnIndexOfHtStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htStatus");
        final int _columnIndexOfHtColor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htColor");
        final int _columnIndexOfWtPos = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtPos");
        final int _columnIndexOfHtPos = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htPos");
        final int _columnIndexOfDocRef = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "docRef");
        final List<Student> _result = new ArrayList<Student>();
        while (_stmt.step()) {
          final Student _item;
          _item = new Student();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpCensusNo;
          _tmpCensusNo = (int) (_stmt.getLong(_columnIndexOfCensusNo));
          _item.setCensusNo(_tmpCensusNo);
          final int _tmpRefId;
          _tmpRefId = (int) (_stmt.getLong(_columnIndexOfRefId));
          _item.setRefId(_tmpRefId);
          final String _tmpUserName;
          if (_stmt.isNull(_columnIndexOfUserName)) {
            _tmpUserName = null;
          } else {
            _tmpUserName = _stmt.getText(_columnIndexOfUserName);
          }
          _item.setUserName(_tmpUserName);
          final String _tmpGrade;
          if (_stmt.isNull(_columnIndexOfGrade)) {
            _tmpGrade = null;
          } else {
            _tmpGrade = _stmt.getText(_columnIndexOfGrade);
          }
          _item.setGrade(_tmpGrade);
          final Date _tmpDob;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfDob)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfDob);
          }
          _tmpDob = DateConverter.fromTimestamp(_tmp_2);
          _item.setDob(_tmpDob);
          final Date _tmpMdate;
          final Long _tmp_3;
          if (_stmt.isNull(_columnIndexOfMdate)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfMdate);
          }
          _tmpMdate = DateConverter.fromTimestamp(_tmp_3);
          _item.setMdate(_tmpMdate);
          final String _tmpSex;
          if (_stmt.isNull(_columnIndexOfSex)) {
            _tmpSex = null;
          } else {
            _tmpSex = _stmt.getText(_columnIndexOfSex);
          }
          _item.setSex(_tmpSex);
          final double _tmpHeight;
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight);
          _item.setHeight(_tmpHeight);
          final double _tmpWeight;
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight);
          _item.setWeight(_tmpWeight);
          final int _tmpAgeY;
          _tmpAgeY = (int) (_stmt.getLong(_columnIndexOfAgeY));
          _item.setAgeY(_tmpAgeY);
          final int _tmpAgeM;
          _tmpAgeM = (int) (_stmt.getLong(_columnIndexOfAgeM));
          _item.setAgeM(_tmpAgeM);
          final int _tmpAgeD;
          _tmpAgeD = (int) (_stmt.getLong(_columnIndexOfAgeD));
          _item.setAgeD(_tmpAgeD);
          final double _tmpBmi;
          _tmpBmi = _stmt.getDouble(_columnIndexOfBmi);
          _item.setBmi(_tmpBmi);
          final double _tmpWtZ;
          _tmpWtZ = _stmt.getDouble(_columnIndexOfWtZ);
          _item.setWtZ(_tmpWtZ);
          final double _tmpHtZ;
          _tmpHtZ = _stmt.getDouble(_columnIndexOfHtZ);
          _item.setHtZ(_tmpHtZ);
          final String _tmpWtStatus;
          if (_stmt.isNull(_columnIndexOfWtStatus)) {
            _tmpWtStatus = null;
          } else {
            _tmpWtStatus = _stmt.getText(_columnIndexOfWtStatus);
          }
          _item.setWtStatus(_tmpWtStatus);
          final int _tmpWtColor;
          _tmpWtColor = (int) (_stmt.getLong(_columnIndexOfWtColor));
          _item.setWtColor(_tmpWtColor);
          final String _tmpHtStatus;
          if (_stmt.isNull(_columnIndexOfHtStatus)) {
            _tmpHtStatus = null;
          } else {
            _tmpHtStatus = _stmt.getText(_columnIndexOfHtStatus);
          }
          _item.setHtStatus(_tmpHtStatus);
          final int _tmpHtColor;
          _tmpHtColor = (int) (_stmt.getLong(_columnIndexOfHtColor));
          _item.setHtColor(_tmpHtColor);
          final String _tmpWtPos;
          if (_stmt.isNull(_columnIndexOfWtPos)) {
            _tmpWtPos = null;
          } else {
            _tmpWtPos = _stmt.getText(_columnIndexOfWtPos);
          }
          _item.setWtPos(_tmpWtPos);
          final String _tmpHtPos;
          if (_stmt.isNull(_columnIndexOfHtPos)) {
            _tmpHtPos = null;
          } else {
            _tmpHtPos = _stmt.getText(_columnIndexOfHtPos);
          }
          _item.setHtPos(_tmpHtPos);
          final String _tmpDocRef;
          if (_stmt.isNull(_columnIndexOfDocRef)) {
            _tmpDocRef = null;
          } else {
            _tmpDocRef = _stmt.getText(_columnIndexOfDocRef);
          }
          _item.setDocRef(_tmpDocRef);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<Student>> getStudentList(final int censusNo, final Date from, final Date to,
      final String grade, final int order) {
    final String _sql = "SELECT * FROM student where censusNo=? and mdate BETWEEN ? and ? and upper(grade) like upper(?) ORDER BY CASE WHEN ? = 0 THEN id END ASC, CASE WHEN ? = 1 THEN id END DESC, CASE WHEN ? = 2 THEN bmi END ASC, CASE WHEN ? = 3 THEN bmi END DESC, CASE WHEN ? = 4 THEN sex END DESC";
    return __db.getInvalidationTracker().createLiveData(new String[] {"student"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, censusNo);
        _argIndex = 2;
        final Long _tmp = DateConverter.dateToTimestamp(from);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 3;
        final Long _tmp_1 = DateConverter.dateToTimestamp(to);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
        _argIndex = 4;
        if (grade == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, grade);
        }
        _argIndex = 5;
        _stmt.bindLong(_argIndex, order);
        _argIndex = 6;
        _stmt.bindLong(_argIndex, order);
        _argIndex = 7;
        _stmt.bindLong(_argIndex, order);
        _argIndex = 8;
        _stmt.bindLong(_argIndex, order);
        _argIndex = 9;
        _stmt.bindLong(_argIndex, order);
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfCensusNo = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "censusNo");
        final int _columnIndexOfRefId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "refId");
        final int _columnIndexOfUserName = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "userName");
        final int _columnIndexOfGrade = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "grade");
        final int _columnIndexOfDob = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "dob");
        final int _columnIndexOfMdate = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "mdate");
        final int _columnIndexOfSex = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "sex");
        final int _columnIndexOfHeight = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "height");
        final int _columnIndexOfWeight = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "weight");
        final int _columnIndexOfAgeY = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ageY");
        final int _columnIndexOfAgeM = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ageM");
        final int _columnIndexOfAgeD = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "ageD");
        final int _columnIndexOfBmi = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "bmi");
        final int _columnIndexOfWtZ = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtZ");
        final int _columnIndexOfHtZ = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htZ");
        final int _columnIndexOfWtStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtStatus");
        final int _columnIndexOfWtColor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtColor");
        final int _columnIndexOfHtStatus = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htStatus");
        final int _columnIndexOfHtColor = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htColor");
        final int _columnIndexOfWtPos = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "wtPos");
        final int _columnIndexOfHtPos = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "htPos");
        final int _columnIndexOfDocRef = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "docRef");
        final List<Student> _result = new ArrayList<Student>();
        while (_stmt.step()) {
          final Student _item;
          _item = new Student();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final int _tmpCensusNo;
          _tmpCensusNo = (int) (_stmt.getLong(_columnIndexOfCensusNo));
          _item.setCensusNo(_tmpCensusNo);
          final int _tmpRefId;
          _tmpRefId = (int) (_stmt.getLong(_columnIndexOfRefId));
          _item.setRefId(_tmpRefId);
          final String _tmpUserName;
          if (_stmt.isNull(_columnIndexOfUserName)) {
            _tmpUserName = null;
          } else {
            _tmpUserName = _stmt.getText(_columnIndexOfUserName);
          }
          _item.setUserName(_tmpUserName);
          final String _tmpGrade;
          if (_stmt.isNull(_columnIndexOfGrade)) {
            _tmpGrade = null;
          } else {
            _tmpGrade = _stmt.getText(_columnIndexOfGrade);
          }
          _item.setGrade(_tmpGrade);
          final Date _tmpDob;
          final Long _tmp_2;
          if (_stmt.isNull(_columnIndexOfDob)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = _stmt.getLong(_columnIndexOfDob);
          }
          _tmpDob = DateConverter.fromTimestamp(_tmp_2);
          _item.setDob(_tmpDob);
          final Date _tmpMdate;
          final Long _tmp_3;
          if (_stmt.isNull(_columnIndexOfMdate)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = _stmt.getLong(_columnIndexOfMdate);
          }
          _tmpMdate = DateConverter.fromTimestamp(_tmp_3);
          _item.setMdate(_tmpMdate);
          final String _tmpSex;
          if (_stmt.isNull(_columnIndexOfSex)) {
            _tmpSex = null;
          } else {
            _tmpSex = _stmt.getText(_columnIndexOfSex);
          }
          _item.setSex(_tmpSex);
          final double _tmpHeight;
          _tmpHeight = _stmt.getDouble(_columnIndexOfHeight);
          _item.setHeight(_tmpHeight);
          final double _tmpWeight;
          _tmpWeight = _stmt.getDouble(_columnIndexOfWeight);
          _item.setWeight(_tmpWeight);
          final int _tmpAgeY;
          _tmpAgeY = (int) (_stmt.getLong(_columnIndexOfAgeY));
          _item.setAgeY(_tmpAgeY);
          final int _tmpAgeM;
          _tmpAgeM = (int) (_stmt.getLong(_columnIndexOfAgeM));
          _item.setAgeM(_tmpAgeM);
          final int _tmpAgeD;
          _tmpAgeD = (int) (_stmt.getLong(_columnIndexOfAgeD));
          _item.setAgeD(_tmpAgeD);
          final double _tmpBmi;
          _tmpBmi = _stmt.getDouble(_columnIndexOfBmi);
          _item.setBmi(_tmpBmi);
          final double _tmpWtZ;
          _tmpWtZ = _stmt.getDouble(_columnIndexOfWtZ);
          _item.setWtZ(_tmpWtZ);
          final double _tmpHtZ;
          _tmpHtZ = _stmt.getDouble(_columnIndexOfHtZ);
          _item.setHtZ(_tmpHtZ);
          final String _tmpWtStatus;
          if (_stmt.isNull(_columnIndexOfWtStatus)) {
            _tmpWtStatus = null;
          } else {
            _tmpWtStatus = _stmt.getText(_columnIndexOfWtStatus);
          }
          _item.setWtStatus(_tmpWtStatus);
          final int _tmpWtColor;
          _tmpWtColor = (int) (_stmt.getLong(_columnIndexOfWtColor));
          _item.setWtColor(_tmpWtColor);
          final String _tmpHtStatus;
          if (_stmt.isNull(_columnIndexOfHtStatus)) {
            _tmpHtStatus = null;
          } else {
            _tmpHtStatus = _stmt.getText(_columnIndexOfHtStatus);
          }
          _item.setHtStatus(_tmpHtStatus);
          final int _tmpHtColor;
          _tmpHtColor = (int) (_stmt.getLong(_columnIndexOfHtColor));
          _item.setHtColor(_tmpHtColor);
          final String _tmpWtPos;
          if (_stmt.isNull(_columnIndexOfWtPos)) {
            _tmpWtPos = null;
          } else {
            _tmpWtPos = _stmt.getText(_columnIndexOfWtPos);
          }
          _item.setWtPos(_tmpWtPos);
          final String _tmpHtPos;
          if (_stmt.isNull(_columnIndexOfHtPos)) {
            _tmpHtPos = null;
          } else {
            _tmpHtPos = _stmt.getText(_columnIndexOfHtPos);
          }
          _item.setHtPos(_tmpHtPos);
          final String _tmpDocRef;
          if (_stmt.isNull(_columnIndexOfDocRef)) {
            _tmpDocRef = null;
          } else {
            _tmpDocRef = _stmt.getText(_columnIndexOfDocRef);
          }
          _item.setDocRef(_tmpDocRef);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @Override
  public LiveData<List<String>> getGradeList(final int censusNo, final Date from, final Date to) {
    final String _sql = "SELECT distinct grade FROM student where censusNo=? and mdate BETWEEN ? and ? order by grade";
    return __db.getInvalidationTracker().createLiveData(new String[] {"student"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, censusNo);
        _argIndex = 2;
        final Long _tmp = DateConverter.dateToTimestamp(from);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 3;
        final Long _tmp_1 = DateConverter.dateToTimestamp(to);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
