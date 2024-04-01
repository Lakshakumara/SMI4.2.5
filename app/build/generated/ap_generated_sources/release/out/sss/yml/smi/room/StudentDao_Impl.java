package sss.yml.smi.room;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class StudentDao_Impl implements StudentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Student> __insertionAdapterOfStudent;

  private final EntityDeletionOrUpdateAdapter<Student> __deletionAdapterOfStudent;

  private final EntityDeletionOrUpdateAdapter<Student> __updateAdapterOfStudent;

  public StudentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudent = new EntityInsertionAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `student` (`id`,`censusNo`,`refId`,`userName`,`grade`,`dob`,`mdate`,`sex`,`height`,`weight`,`ageY`,`ageM`,`ageD`,`bmi`,`wtZ`,`htZ`,`wtStatus`,`wtColor`,`htStatus`,`htColor`,`wtPos`,`htPos`,`docRef`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCensusNo());
        statement.bindLong(3, entity.getRefId());
        if (entity.getUserName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getUserName());
        }
        if (entity.getGrade() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getGrade());
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
          statement.bindString(8, entity.getSex());
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
          statement.bindString(17, entity.getWtStatus());
        }
        statement.bindLong(18, entity.getWtColor());
        if (entity.getHtStatus() == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, entity.getHtStatus());
        }
        statement.bindLong(20, entity.getHtColor());
        if (entity.getWtPos() == null) {
          statement.bindNull(21);
        } else {
          statement.bindString(21, entity.getWtPos());
        }
        if (entity.getHtPos() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getHtPos());
        }
        if (entity.getDocRef() == null) {
          statement.bindNull(23);
        } else {
          statement.bindString(23, entity.getDocRef());
        }
      }
    };
    this.__deletionAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `student` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfStudent = new EntityDeletionOrUpdateAdapter<Student>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `student` SET `id` = ?,`censusNo` = ?,`refId` = ?,`userName` = ?,`grade` = ?,`dob` = ?,`mdate` = ?,`sex` = ?,`height` = ?,`weight` = ?,`ageY` = ?,`ageM` = ?,`ageD` = ?,`bmi` = ?,`wtZ` = ?,`htZ` = ?,`wtStatus` = ?,`wtColor` = ?,`htStatus` = ?,`htColor` = ?,`wtPos` = ?,`htPos` = ?,`docRef` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Student entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCensusNo());
        statement.bindLong(3, entity.getRefId());
        if (entity.getUserName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getUserName());
        }
        if (entity.getGrade() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getGrade());
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
          statement.bindString(8, entity.getSex());
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
          statement.bindString(17, entity.getWtStatus());
        }
        statement.bindLong(18, entity.getWtColor());
        if (entity.getHtStatus() == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, entity.getHtStatus());
        }
        statement.bindLong(20, entity.getHtColor());
        if (entity.getWtPos() == null) {
          statement.bindNull(21);
        } else {
          statement.bindString(21, entity.getWtPos());
        }
        if (entity.getHtPos() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getHtPos());
        }
        if (entity.getDocRef() == null) {
          statement.bindNull(23);
        } else {
          statement.bindString(23, entity.getDocRef());
        }
        statement.bindLong(24, entity.getId());
      }
    };
  }

  @Override
  public Long insert(final Student obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final Long _result = __insertionAdapterOfStudent.insertAndReturnId(obj);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Student obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfStudent.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Student obj) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfStudent.handle(obj);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<Student> getStudent(final int id) {
    final String _sql = "SELECT * FROM student where id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[] {"student"}, false, new Callable<Student>() {
      @Override
      @Nullable
      public Student call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCensusNo = CursorUtil.getColumnIndexOrThrow(_cursor, "censusNo");
          final int _cursorIndexOfRefId = CursorUtil.getColumnIndexOrThrow(_cursor, "refId");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfDob = CursorUtil.getColumnIndexOrThrow(_cursor, "dob");
          final int _cursorIndexOfMdate = CursorUtil.getColumnIndexOrThrow(_cursor, "mdate");
          final int _cursorIndexOfSex = CursorUtil.getColumnIndexOrThrow(_cursor, "sex");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfAgeY = CursorUtil.getColumnIndexOrThrow(_cursor, "ageY");
          final int _cursorIndexOfAgeM = CursorUtil.getColumnIndexOrThrow(_cursor, "ageM");
          final int _cursorIndexOfAgeD = CursorUtil.getColumnIndexOrThrow(_cursor, "ageD");
          final int _cursorIndexOfBmi = CursorUtil.getColumnIndexOrThrow(_cursor, "bmi");
          final int _cursorIndexOfWtZ = CursorUtil.getColumnIndexOrThrow(_cursor, "wtZ");
          final int _cursorIndexOfHtZ = CursorUtil.getColumnIndexOrThrow(_cursor, "htZ");
          final int _cursorIndexOfWtStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "wtStatus");
          final int _cursorIndexOfWtColor = CursorUtil.getColumnIndexOrThrow(_cursor, "wtColor");
          final int _cursorIndexOfHtStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "htStatus");
          final int _cursorIndexOfHtColor = CursorUtil.getColumnIndexOrThrow(_cursor, "htColor");
          final int _cursorIndexOfWtPos = CursorUtil.getColumnIndexOrThrow(_cursor, "wtPos");
          final int _cursorIndexOfHtPos = CursorUtil.getColumnIndexOrThrow(_cursor, "htPos");
          final int _cursorIndexOfDocRef = CursorUtil.getColumnIndexOrThrow(_cursor, "docRef");
          final Student _result;
          if (_cursor.moveToFirst()) {
            _result = new Student();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _result.setId(_tmpId);
            final int _tmpCensusNo;
            _tmpCensusNo = _cursor.getInt(_cursorIndexOfCensusNo);
            _result.setCensusNo(_tmpCensusNo);
            final int _tmpRefId;
            _tmpRefId = _cursor.getInt(_cursorIndexOfRefId);
            _result.setRefId(_tmpRefId);
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            _result.setUserName(_tmpUserName);
            final String _tmpGrade;
            if (_cursor.isNull(_cursorIndexOfGrade)) {
              _tmpGrade = null;
            } else {
              _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            }
            _result.setGrade(_tmpGrade);
            final Date _tmpDob;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDob)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDob);
            }
            _tmpDob = DateConverter.fromTimestamp(_tmp);
            _result.setDob(_tmpDob);
            final Date _tmpMdate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfMdate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfMdate);
            }
            _tmpMdate = DateConverter.fromTimestamp(_tmp_1);
            _result.setMdate(_tmpMdate);
            final String _tmpSex;
            if (_cursor.isNull(_cursorIndexOfSex)) {
              _tmpSex = null;
            } else {
              _tmpSex = _cursor.getString(_cursorIndexOfSex);
            }
            _result.setSex(_tmpSex);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            _result.setHeight(_tmpHeight);
            final double _tmpWeight;
            _tmpWeight = _cursor.getDouble(_cursorIndexOfWeight);
            _result.setWeight(_tmpWeight);
            final int _tmpAgeY;
            _tmpAgeY = _cursor.getInt(_cursorIndexOfAgeY);
            _result.setAgeY(_tmpAgeY);
            final int _tmpAgeM;
            _tmpAgeM = _cursor.getInt(_cursorIndexOfAgeM);
            _result.setAgeM(_tmpAgeM);
            final int _tmpAgeD;
            _tmpAgeD = _cursor.getInt(_cursorIndexOfAgeD);
            _result.setAgeD(_tmpAgeD);
            final double _tmpBmi;
            _tmpBmi = _cursor.getDouble(_cursorIndexOfBmi);
            _result.setBmi(_tmpBmi);
            final double _tmpWtZ;
            _tmpWtZ = _cursor.getDouble(_cursorIndexOfWtZ);
            _result.setWtZ(_tmpWtZ);
            final double _tmpHtZ;
            _tmpHtZ = _cursor.getDouble(_cursorIndexOfHtZ);
            _result.setHtZ(_tmpHtZ);
            final String _tmpWtStatus;
            if (_cursor.isNull(_cursorIndexOfWtStatus)) {
              _tmpWtStatus = null;
            } else {
              _tmpWtStatus = _cursor.getString(_cursorIndexOfWtStatus);
            }
            _result.setWtStatus(_tmpWtStatus);
            final int _tmpWtColor;
            _tmpWtColor = _cursor.getInt(_cursorIndexOfWtColor);
            _result.setWtColor(_tmpWtColor);
            final String _tmpHtStatus;
            if (_cursor.isNull(_cursorIndexOfHtStatus)) {
              _tmpHtStatus = null;
            } else {
              _tmpHtStatus = _cursor.getString(_cursorIndexOfHtStatus);
            }
            _result.setHtStatus(_tmpHtStatus);
            final int _tmpHtColor;
            _tmpHtColor = _cursor.getInt(_cursorIndexOfHtColor);
            _result.setHtColor(_tmpHtColor);
            final String _tmpWtPos;
            if (_cursor.isNull(_cursorIndexOfWtPos)) {
              _tmpWtPos = null;
            } else {
              _tmpWtPos = _cursor.getString(_cursorIndexOfWtPos);
            }
            _result.setWtPos(_tmpWtPos);
            final String _tmpHtPos;
            if (_cursor.isNull(_cursorIndexOfHtPos)) {
              _tmpHtPos = null;
            } else {
              _tmpHtPos = _cursor.getString(_cursorIndexOfHtPos);
            }
            _result.setHtPos(_tmpHtPos);
            final String _tmpDocRef;
            if (_cursor.isNull(_cursorIndexOfDocRef)) {
              _tmpDocRef = null;
            } else {
              _tmpDocRef = _cursor.getString(_cursorIndexOfDocRef);
            }
            _result.setDocRef(_tmpDocRef);
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
  public LiveData<List<Student>> getStudentList() {
    final String _sql = "SELECT * FROM student";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"student"}, false, new Callable<List<Student>>() {
      @Override
      @Nullable
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCensusNo = CursorUtil.getColumnIndexOrThrow(_cursor, "censusNo");
          final int _cursorIndexOfRefId = CursorUtil.getColumnIndexOrThrow(_cursor, "refId");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfDob = CursorUtil.getColumnIndexOrThrow(_cursor, "dob");
          final int _cursorIndexOfMdate = CursorUtil.getColumnIndexOrThrow(_cursor, "mdate");
          final int _cursorIndexOfSex = CursorUtil.getColumnIndexOrThrow(_cursor, "sex");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfAgeY = CursorUtil.getColumnIndexOrThrow(_cursor, "ageY");
          final int _cursorIndexOfAgeM = CursorUtil.getColumnIndexOrThrow(_cursor, "ageM");
          final int _cursorIndexOfAgeD = CursorUtil.getColumnIndexOrThrow(_cursor, "ageD");
          final int _cursorIndexOfBmi = CursorUtil.getColumnIndexOrThrow(_cursor, "bmi");
          final int _cursorIndexOfWtZ = CursorUtil.getColumnIndexOrThrow(_cursor, "wtZ");
          final int _cursorIndexOfHtZ = CursorUtil.getColumnIndexOrThrow(_cursor, "htZ");
          final int _cursorIndexOfWtStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "wtStatus");
          final int _cursorIndexOfWtColor = CursorUtil.getColumnIndexOrThrow(_cursor, "wtColor");
          final int _cursorIndexOfHtStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "htStatus");
          final int _cursorIndexOfHtColor = CursorUtil.getColumnIndexOrThrow(_cursor, "htColor");
          final int _cursorIndexOfWtPos = CursorUtil.getColumnIndexOrThrow(_cursor, "wtPos");
          final int _cursorIndexOfHtPos = CursorUtil.getColumnIndexOrThrow(_cursor, "htPos");
          final int _cursorIndexOfDocRef = CursorUtil.getColumnIndexOrThrow(_cursor, "docRef");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            _item = new Student();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpCensusNo;
            _tmpCensusNo = _cursor.getInt(_cursorIndexOfCensusNo);
            _item.setCensusNo(_tmpCensusNo);
            final int _tmpRefId;
            _tmpRefId = _cursor.getInt(_cursorIndexOfRefId);
            _item.setRefId(_tmpRefId);
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            _item.setUserName(_tmpUserName);
            final String _tmpGrade;
            if (_cursor.isNull(_cursorIndexOfGrade)) {
              _tmpGrade = null;
            } else {
              _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            }
            _item.setGrade(_tmpGrade);
            final Date _tmpDob;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDob)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDob);
            }
            _tmpDob = DateConverter.fromTimestamp(_tmp);
            _item.setDob(_tmpDob);
            final Date _tmpMdate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfMdate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfMdate);
            }
            _tmpMdate = DateConverter.fromTimestamp(_tmp_1);
            _item.setMdate(_tmpMdate);
            final String _tmpSex;
            if (_cursor.isNull(_cursorIndexOfSex)) {
              _tmpSex = null;
            } else {
              _tmpSex = _cursor.getString(_cursorIndexOfSex);
            }
            _item.setSex(_tmpSex);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            _item.setHeight(_tmpHeight);
            final double _tmpWeight;
            _tmpWeight = _cursor.getDouble(_cursorIndexOfWeight);
            _item.setWeight(_tmpWeight);
            final int _tmpAgeY;
            _tmpAgeY = _cursor.getInt(_cursorIndexOfAgeY);
            _item.setAgeY(_tmpAgeY);
            final int _tmpAgeM;
            _tmpAgeM = _cursor.getInt(_cursorIndexOfAgeM);
            _item.setAgeM(_tmpAgeM);
            final int _tmpAgeD;
            _tmpAgeD = _cursor.getInt(_cursorIndexOfAgeD);
            _item.setAgeD(_tmpAgeD);
            final double _tmpBmi;
            _tmpBmi = _cursor.getDouble(_cursorIndexOfBmi);
            _item.setBmi(_tmpBmi);
            final double _tmpWtZ;
            _tmpWtZ = _cursor.getDouble(_cursorIndexOfWtZ);
            _item.setWtZ(_tmpWtZ);
            final double _tmpHtZ;
            _tmpHtZ = _cursor.getDouble(_cursorIndexOfHtZ);
            _item.setHtZ(_tmpHtZ);
            final String _tmpWtStatus;
            if (_cursor.isNull(_cursorIndexOfWtStatus)) {
              _tmpWtStatus = null;
            } else {
              _tmpWtStatus = _cursor.getString(_cursorIndexOfWtStatus);
            }
            _item.setWtStatus(_tmpWtStatus);
            final int _tmpWtColor;
            _tmpWtColor = _cursor.getInt(_cursorIndexOfWtColor);
            _item.setWtColor(_tmpWtColor);
            final String _tmpHtStatus;
            if (_cursor.isNull(_cursorIndexOfHtStatus)) {
              _tmpHtStatus = null;
            } else {
              _tmpHtStatus = _cursor.getString(_cursorIndexOfHtStatus);
            }
            _item.setHtStatus(_tmpHtStatus);
            final int _tmpHtColor;
            _tmpHtColor = _cursor.getInt(_cursorIndexOfHtColor);
            _item.setHtColor(_tmpHtColor);
            final String _tmpWtPos;
            if (_cursor.isNull(_cursorIndexOfWtPos)) {
              _tmpWtPos = null;
            } else {
              _tmpWtPos = _cursor.getString(_cursorIndexOfWtPos);
            }
            _item.setWtPos(_tmpWtPos);
            final String _tmpHtPos;
            if (_cursor.isNull(_cursorIndexOfHtPos)) {
              _tmpHtPos = null;
            } else {
              _tmpHtPos = _cursor.getString(_cursorIndexOfHtPos);
            }
            _item.setHtPos(_tmpHtPos);
            final String _tmpDocRef;
            if (_cursor.isNull(_cursorIndexOfDocRef)) {
              _tmpDocRef = null;
            } else {
              _tmpDocRef = _cursor.getString(_cursorIndexOfDocRef);
            }
            _item.setDocRef(_tmpDocRef);
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
  public LiveData<List<Student>> getStudentList(final int censusNo, final Date from, final Date to,
      final String grade) {
    final String _sql = "SELECT * FROM student where censusNo=? and mdate BETWEEN ? and ? and upper(grade) like upper(?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, censusNo);
    _argIndex = 2;
    final Long _tmp = DateConverter.dateToTimestamp(from);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = DateConverter.dateToTimestamp(to);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    _argIndex = 4;
    if (grade == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, grade);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"student"}, false, new Callable<List<Student>>() {
      @Override
      @Nullable
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCensusNo = CursorUtil.getColumnIndexOrThrow(_cursor, "censusNo");
          final int _cursorIndexOfRefId = CursorUtil.getColumnIndexOrThrow(_cursor, "refId");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfDob = CursorUtil.getColumnIndexOrThrow(_cursor, "dob");
          final int _cursorIndexOfMdate = CursorUtil.getColumnIndexOrThrow(_cursor, "mdate");
          final int _cursorIndexOfSex = CursorUtil.getColumnIndexOrThrow(_cursor, "sex");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfAgeY = CursorUtil.getColumnIndexOrThrow(_cursor, "ageY");
          final int _cursorIndexOfAgeM = CursorUtil.getColumnIndexOrThrow(_cursor, "ageM");
          final int _cursorIndexOfAgeD = CursorUtil.getColumnIndexOrThrow(_cursor, "ageD");
          final int _cursorIndexOfBmi = CursorUtil.getColumnIndexOrThrow(_cursor, "bmi");
          final int _cursorIndexOfWtZ = CursorUtil.getColumnIndexOrThrow(_cursor, "wtZ");
          final int _cursorIndexOfHtZ = CursorUtil.getColumnIndexOrThrow(_cursor, "htZ");
          final int _cursorIndexOfWtStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "wtStatus");
          final int _cursorIndexOfWtColor = CursorUtil.getColumnIndexOrThrow(_cursor, "wtColor");
          final int _cursorIndexOfHtStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "htStatus");
          final int _cursorIndexOfHtColor = CursorUtil.getColumnIndexOrThrow(_cursor, "htColor");
          final int _cursorIndexOfWtPos = CursorUtil.getColumnIndexOrThrow(_cursor, "wtPos");
          final int _cursorIndexOfHtPos = CursorUtil.getColumnIndexOrThrow(_cursor, "htPos");
          final int _cursorIndexOfDocRef = CursorUtil.getColumnIndexOrThrow(_cursor, "docRef");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            _item = new Student();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpCensusNo;
            _tmpCensusNo = _cursor.getInt(_cursorIndexOfCensusNo);
            _item.setCensusNo(_tmpCensusNo);
            final int _tmpRefId;
            _tmpRefId = _cursor.getInt(_cursorIndexOfRefId);
            _item.setRefId(_tmpRefId);
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            _item.setUserName(_tmpUserName);
            final String _tmpGrade;
            if (_cursor.isNull(_cursorIndexOfGrade)) {
              _tmpGrade = null;
            } else {
              _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            }
            _item.setGrade(_tmpGrade);
            final Date _tmpDob;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDob)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDob);
            }
            _tmpDob = DateConverter.fromTimestamp(_tmp_2);
            _item.setDob(_tmpDob);
            final Date _tmpMdate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfMdate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfMdate);
            }
            _tmpMdate = DateConverter.fromTimestamp(_tmp_3);
            _item.setMdate(_tmpMdate);
            final String _tmpSex;
            if (_cursor.isNull(_cursorIndexOfSex)) {
              _tmpSex = null;
            } else {
              _tmpSex = _cursor.getString(_cursorIndexOfSex);
            }
            _item.setSex(_tmpSex);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            _item.setHeight(_tmpHeight);
            final double _tmpWeight;
            _tmpWeight = _cursor.getDouble(_cursorIndexOfWeight);
            _item.setWeight(_tmpWeight);
            final int _tmpAgeY;
            _tmpAgeY = _cursor.getInt(_cursorIndexOfAgeY);
            _item.setAgeY(_tmpAgeY);
            final int _tmpAgeM;
            _tmpAgeM = _cursor.getInt(_cursorIndexOfAgeM);
            _item.setAgeM(_tmpAgeM);
            final int _tmpAgeD;
            _tmpAgeD = _cursor.getInt(_cursorIndexOfAgeD);
            _item.setAgeD(_tmpAgeD);
            final double _tmpBmi;
            _tmpBmi = _cursor.getDouble(_cursorIndexOfBmi);
            _item.setBmi(_tmpBmi);
            final double _tmpWtZ;
            _tmpWtZ = _cursor.getDouble(_cursorIndexOfWtZ);
            _item.setWtZ(_tmpWtZ);
            final double _tmpHtZ;
            _tmpHtZ = _cursor.getDouble(_cursorIndexOfHtZ);
            _item.setHtZ(_tmpHtZ);
            final String _tmpWtStatus;
            if (_cursor.isNull(_cursorIndexOfWtStatus)) {
              _tmpWtStatus = null;
            } else {
              _tmpWtStatus = _cursor.getString(_cursorIndexOfWtStatus);
            }
            _item.setWtStatus(_tmpWtStatus);
            final int _tmpWtColor;
            _tmpWtColor = _cursor.getInt(_cursorIndexOfWtColor);
            _item.setWtColor(_tmpWtColor);
            final String _tmpHtStatus;
            if (_cursor.isNull(_cursorIndexOfHtStatus)) {
              _tmpHtStatus = null;
            } else {
              _tmpHtStatus = _cursor.getString(_cursorIndexOfHtStatus);
            }
            _item.setHtStatus(_tmpHtStatus);
            final int _tmpHtColor;
            _tmpHtColor = _cursor.getInt(_cursorIndexOfHtColor);
            _item.setHtColor(_tmpHtColor);
            final String _tmpWtPos;
            if (_cursor.isNull(_cursorIndexOfWtPos)) {
              _tmpWtPos = null;
            } else {
              _tmpWtPos = _cursor.getString(_cursorIndexOfWtPos);
            }
            _item.setWtPos(_tmpWtPos);
            final String _tmpHtPos;
            if (_cursor.isNull(_cursorIndexOfHtPos)) {
              _tmpHtPos = null;
            } else {
              _tmpHtPos = _cursor.getString(_cursorIndexOfHtPos);
            }
            _item.setHtPos(_tmpHtPos);
            final String _tmpDocRef;
            if (_cursor.isNull(_cursorIndexOfDocRef)) {
              _tmpDocRef = null;
            } else {
              _tmpDocRef = _cursor.getString(_cursorIndexOfDocRef);
            }
            _item.setDocRef(_tmpDocRef);
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
  public LiveData<List<Student>> getStudentList(final int censusNo, final Date from, final Date to,
      final String grade, final int order) {
    final String _sql = "SELECT * FROM student where censusNo=? and mdate BETWEEN ? and ? and upper(grade) like upper(?) ORDER BY CASE WHEN ? = 0 THEN id END ASC, CASE WHEN ? = 1 THEN id END DESC, CASE WHEN ? = 2 THEN bmi END ASC, CASE WHEN ? = 3 THEN bmi END DESC, CASE WHEN ? = 4 THEN sex END DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 9);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, censusNo);
    _argIndex = 2;
    final Long _tmp = DateConverter.dateToTimestamp(from);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = DateConverter.dateToTimestamp(to);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    _argIndex = 4;
    if (grade == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, grade);
    }
    _argIndex = 5;
    _statement.bindLong(_argIndex, order);
    _argIndex = 6;
    _statement.bindLong(_argIndex, order);
    _argIndex = 7;
    _statement.bindLong(_argIndex, order);
    _argIndex = 8;
    _statement.bindLong(_argIndex, order);
    _argIndex = 9;
    _statement.bindLong(_argIndex, order);
    return __db.getInvalidationTracker().createLiveData(new String[] {"student"}, false, new Callable<List<Student>>() {
      @Override
      @Nullable
      public List<Student> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCensusNo = CursorUtil.getColumnIndexOrThrow(_cursor, "censusNo");
          final int _cursorIndexOfRefId = CursorUtil.getColumnIndexOrThrow(_cursor, "refId");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "grade");
          final int _cursorIndexOfDob = CursorUtil.getColumnIndexOrThrow(_cursor, "dob");
          final int _cursorIndexOfMdate = CursorUtil.getColumnIndexOrThrow(_cursor, "mdate");
          final int _cursorIndexOfSex = CursorUtil.getColumnIndexOrThrow(_cursor, "sex");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfAgeY = CursorUtil.getColumnIndexOrThrow(_cursor, "ageY");
          final int _cursorIndexOfAgeM = CursorUtil.getColumnIndexOrThrow(_cursor, "ageM");
          final int _cursorIndexOfAgeD = CursorUtil.getColumnIndexOrThrow(_cursor, "ageD");
          final int _cursorIndexOfBmi = CursorUtil.getColumnIndexOrThrow(_cursor, "bmi");
          final int _cursorIndexOfWtZ = CursorUtil.getColumnIndexOrThrow(_cursor, "wtZ");
          final int _cursorIndexOfHtZ = CursorUtil.getColumnIndexOrThrow(_cursor, "htZ");
          final int _cursorIndexOfWtStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "wtStatus");
          final int _cursorIndexOfWtColor = CursorUtil.getColumnIndexOrThrow(_cursor, "wtColor");
          final int _cursorIndexOfHtStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "htStatus");
          final int _cursorIndexOfHtColor = CursorUtil.getColumnIndexOrThrow(_cursor, "htColor");
          final int _cursorIndexOfWtPos = CursorUtil.getColumnIndexOrThrow(_cursor, "wtPos");
          final int _cursorIndexOfHtPos = CursorUtil.getColumnIndexOrThrow(_cursor, "htPos");
          final int _cursorIndexOfDocRef = CursorUtil.getColumnIndexOrThrow(_cursor, "docRef");
          final List<Student> _result = new ArrayList<Student>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Student _item;
            _item = new Student();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpCensusNo;
            _tmpCensusNo = _cursor.getInt(_cursorIndexOfCensusNo);
            _item.setCensusNo(_tmpCensusNo);
            final int _tmpRefId;
            _tmpRefId = _cursor.getInt(_cursorIndexOfRefId);
            _item.setRefId(_tmpRefId);
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            _item.setUserName(_tmpUserName);
            final String _tmpGrade;
            if (_cursor.isNull(_cursorIndexOfGrade)) {
              _tmpGrade = null;
            } else {
              _tmpGrade = _cursor.getString(_cursorIndexOfGrade);
            }
            _item.setGrade(_tmpGrade);
            final Date _tmpDob;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDob)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDob);
            }
            _tmpDob = DateConverter.fromTimestamp(_tmp_2);
            _item.setDob(_tmpDob);
            final Date _tmpMdate;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfMdate)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfMdate);
            }
            _tmpMdate = DateConverter.fromTimestamp(_tmp_3);
            _item.setMdate(_tmpMdate);
            final String _tmpSex;
            if (_cursor.isNull(_cursorIndexOfSex)) {
              _tmpSex = null;
            } else {
              _tmpSex = _cursor.getString(_cursorIndexOfSex);
            }
            _item.setSex(_tmpSex);
            final double _tmpHeight;
            _tmpHeight = _cursor.getDouble(_cursorIndexOfHeight);
            _item.setHeight(_tmpHeight);
            final double _tmpWeight;
            _tmpWeight = _cursor.getDouble(_cursorIndexOfWeight);
            _item.setWeight(_tmpWeight);
            final int _tmpAgeY;
            _tmpAgeY = _cursor.getInt(_cursorIndexOfAgeY);
            _item.setAgeY(_tmpAgeY);
            final int _tmpAgeM;
            _tmpAgeM = _cursor.getInt(_cursorIndexOfAgeM);
            _item.setAgeM(_tmpAgeM);
            final int _tmpAgeD;
            _tmpAgeD = _cursor.getInt(_cursorIndexOfAgeD);
            _item.setAgeD(_tmpAgeD);
            final double _tmpBmi;
            _tmpBmi = _cursor.getDouble(_cursorIndexOfBmi);
            _item.setBmi(_tmpBmi);
            final double _tmpWtZ;
            _tmpWtZ = _cursor.getDouble(_cursorIndexOfWtZ);
            _item.setWtZ(_tmpWtZ);
            final double _tmpHtZ;
            _tmpHtZ = _cursor.getDouble(_cursorIndexOfHtZ);
            _item.setHtZ(_tmpHtZ);
            final String _tmpWtStatus;
            if (_cursor.isNull(_cursorIndexOfWtStatus)) {
              _tmpWtStatus = null;
            } else {
              _tmpWtStatus = _cursor.getString(_cursorIndexOfWtStatus);
            }
            _item.setWtStatus(_tmpWtStatus);
            final int _tmpWtColor;
            _tmpWtColor = _cursor.getInt(_cursorIndexOfWtColor);
            _item.setWtColor(_tmpWtColor);
            final String _tmpHtStatus;
            if (_cursor.isNull(_cursorIndexOfHtStatus)) {
              _tmpHtStatus = null;
            } else {
              _tmpHtStatus = _cursor.getString(_cursorIndexOfHtStatus);
            }
            _item.setHtStatus(_tmpHtStatus);
            final int _tmpHtColor;
            _tmpHtColor = _cursor.getInt(_cursorIndexOfHtColor);
            _item.setHtColor(_tmpHtColor);
            final String _tmpWtPos;
            if (_cursor.isNull(_cursorIndexOfWtPos)) {
              _tmpWtPos = null;
            } else {
              _tmpWtPos = _cursor.getString(_cursorIndexOfWtPos);
            }
            _item.setWtPos(_tmpWtPos);
            final String _tmpHtPos;
            if (_cursor.isNull(_cursorIndexOfHtPos)) {
              _tmpHtPos = null;
            } else {
              _tmpHtPos = _cursor.getString(_cursorIndexOfHtPos);
            }
            _item.setHtPos(_tmpHtPos);
            final String _tmpDocRef;
            if (_cursor.isNull(_cursorIndexOfDocRef)) {
              _tmpDocRef = null;
            } else {
              _tmpDocRef = _cursor.getString(_cursorIndexOfDocRef);
            }
            _item.setDocRef(_tmpDocRef);
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
  public LiveData<List<String>> getGradeList(final int censusNo, final Date from, final Date to) {
    final String _sql = "SELECT distinct grade FROM student where censusNo=? and mdate BETWEEN ? and ? order by grade";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, censusNo);
    _argIndex = 2;
    final Long _tmp = DateConverter.dateToTimestamp(from);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = DateConverter.dateToTimestamp(to);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"student"}, false, new Callable<List<String>>() {
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
