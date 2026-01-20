package sss.yml.smi.room;

import androidx.annotation.NonNull;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class BMISdb_Impl extends BMISdb {
  private volatile ChildDao _childDao;

  private volatile SchoolDao _schoolDao;

  private volatile StudentDao _studentDao;

  @Override
  @NonNull
  protected RoomOpenDelegate createOpenDelegate() {
    final RoomOpenDelegate _openDelegate = new RoomOpenDelegate(2, "be93446cc2f733bf67a6521a16b5e4e4", "e1abc52209948392f1d807554141cd7a") {
      @Override
      public void createAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `child` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `regNo` TEXT NOT NULL, `name` TEXT NOT NULL, `sex` TEXT NOT NULL, `dob` INTEGER NOT NULL, `maturity` INTEGER NOT NULL, `guardian` TEXT, `tp` TEXT, `note` TEXT DEFAULT '', `fht` REAL NOT NULL DEFAULT 0.0, `mht` REAL NOT NULL DEFAULT 0.0, `deleted` INTEGER NOT NULL, `accessTime` INTEGER DEFAULT CURRENT_TIMESTAMP)");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_child_name` ON `child` (`name`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `childWpData` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `childId` INTEGER NOT NULL, `mDate` INTEGER, `wt` REAL NOT NULL, `ht` REAL NOT NULL, `note` TEXT, `ageDays` REAL NOT NULL, `ageYY` INTEGER NOT NULL, `ageMM` INTEGER NOT NULL, `ageDD` INTEGER NOT NULL, `wfaZ` REAL NOT NULL, `hfaZ` REAL NOT NULL, `wfhZ` REAL NOT NULL, `wfaStatus` TEXT, `hfaStatus` TEXT, `wfhStatus` TEXT)");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_childWpData_childId` ON `childWpData` (`childId`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `school` (`censusNo` INTEGER NOT NULL, `name` TEXT NOT NULL, `address` TEXT NOT NULL, `province` TEXT NOT NULL, `district` TEXT NOT NULL, `zone` TEXT NOT NULL, `division` TEXT NOT NULL, `level` TEXT NOT NULL, `span` TEXT NOT NULL, `male` INTEGER NOT NULL, `female` INTEGER NOT NULL, `selected` INTEGER NOT NULL, PRIMARY KEY(`censusNo`))");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_school_censusNo` ON `school` (`censusNo`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `student` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `censusNo` INTEGER NOT NULL, `refId` INTEGER NOT NULL DEFAULT 0, `userName` TEXT DEFAULT '', `grade` TEXT DEFAULT '', `dob` INTEGER NOT NULL, `mdate` INTEGER NOT NULL, `sex` TEXT NOT NULL, `height` REAL NOT NULL, `weight` REAL NOT NULL, `ageY` INTEGER NOT NULL, `ageM` INTEGER NOT NULL, `ageD` INTEGER NOT NULL, `bmi` REAL NOT NULL, `wtZ` REAL NOT NULL, `htZ` REAL NOT NULL, `wtStatus` TEXT NOT NULL, `wtColor` INTEGER NOT NULL, `htStatus` TEXT NOT NULL, `htColor` INTEGER NOT NULL, `wtPos` TEXT NOT NULL, `htPos` TEXT NOT NULL, `docRef` TEXT)");
        SQLite.execSQL(connection, "CREATE INDEX IF NOT EXISTS `index_student_id` ON `student` (`id`)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        SQLite.execSQL(connection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'be93446cc2f733bf67a6521a16b5e4e4')");
      }

      @Override
      public void dropAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `child`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `childWpData`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `school`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `student`");
      }

      @Override
      public void onCreate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      public void onOpen(@NonNull final SQLiteConnection connection) {
        internalInitInvalidationTracker(connection);
      }

      @Override
      public void onPreMigrate(@NonNull final SQLiteConnection connection) {
        DBUtil.dropFtsSyncTriggers(connection);
      }

      @Override
      public void onPostMigrate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      @NonNull
      public RoomOpenDelegate.ValidationResult onValidateSchema(
          @NonNull final SQLiteConnection connection) {
        final Map<String, TableInfo.Column> _columnsChild = new HashMap<String, TableInfo.Column>(13);
        _columnsChild.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChild.put("regNo", new TableInfo.Column("regNo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChild.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChild.put("sex", new TableInfo.Column("sex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChild.put("dob", new TableInfo.Column("dob", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChild.put("maturity", new TableInfo.Column("maturity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChild.put("guardian", new TableInfo.Column("guardian", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChild.put("tp", new TableInfo.Column("tp", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChild.put("note", new TableInfo.Column("note", "TEXT", false, 0, "''", TableInfo.CREATED_FROM_ENTITY));
        _columnsChild.put("fht", new TableInfo.Column("fht", "REAL", true, 0, "0.0", TableInfo.CREATED_FROM_ENTITY));
        _columnsChild.put("mht", new TableInfo.Column("mht", "REAL", true, 0, "0.0", TableInfo.CREATED_FROM_ENTITY));
        _columnsChild.put("deleted", new TableInfo.Column("deleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChild.put("accessTime", new TableInfo.Column("accessTime", "INTEGER", false, 0, "CURRENT_TIMESTAMP", TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysChild = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesChild = new HashSet<TableInfo.Index>(1);
        _indicesChild.add(new TableInfo.Index("index_child_name", false, Arrays.asList("name"), Arrays.asList("ASC")));
        final TableInfo _infoChild = new TableInfo("child", _columnsChild, _foreignKeysChild, _indicesChild);
        final TableInfo _existingChild = TableInfo.read(connection, "child");
        if (!_infoChild.equals(_existingChild)) {
          return new RoomOpenDelegate.ValidationResult(false, "child(sss.yml.smi.room.Child).\n"
                  + " Expected:\n" + _infoChild + "\n"
                  + " Found:\n" + _existingChild);
        }
        final Map<String, TableInfo.Column> _columnsChildWpData = new HashMap<String, TableInfo.Column>(16);
        _columnsChildWpData.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("childId", new TableInfo.Column("childId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("mDate", new TableInfo.Column("mDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("wt", new TableInfo.Column("wt", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("ht", new TableInfo.Column("ht", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("ageDays", new TableInfo.Column("ageDays", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("ageYY", new TableInfo.Column("ageYY", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("ageMM", new TableInfo.Column("ageMM", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("ageDD", new TableInfo.Column("ageDD", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("wfaZ", new TableInfo.Column("wfaZ", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("hfaZ", new TableInfo.Column("hfaZ", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("wfhZ", new TableInfo.Column("wfhZ", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("wfaStatus", new TableInfo.Column("wfaStatus", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("hfaStatus", new TableInfo.Column("hfaStatus", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChildWpData.put("wfhStatus", new TableInfo.Column("wfhStatus", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysChildWpData = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesChildWpData = new HashSet<TableInfo.Index>(1);
        _indicesChildWpData.add(new TableInfo.Index("index_childWpData_childId", false, Arrays.asList("childId"), Arrays.asList("ASC")));
        final TableInfo _infoChildWpData = new TableInfo("childWpData", _columnsChildWpData, _foreignKeysChildWpData, _indicesChildWpData);
        final TableInfo _existingChildWpData = TableInfo.read(connection, "childWpData");
        if (!_infoChildWpData.equals(_existingChildWpData)) {
          return new RoomOpenDelegate.ValidationResult(false, "childWpData(sss.yml.smi.room.ChildWpData).\n"
                  + " Expected:\n" + _infoChildWpData + "\n"
                  + " Found:\n" + _existingChildWpData);
        }
        final Map<String, TableInfo.Column> _columnsSchool = new HashMap<String, TableInfo.Column>(12);
        _columnsSchool.put("censusNo", new TableInfo.Column("censusNo", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchool.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchool.put("address", new TableInfo.Column("address", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchool.put("province", new TableInfo.Column("province", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchool.put("district", new TableInfo.Column("district", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchool.put("zone", new TableInfo.Column("zone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchool.put("division", new TableInfo.Column("division", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchool.put("level", new TableInfo.Column("level", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchool.put("span", new TableInfo.Column("span", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchool.put("male", new TableInfo.Column("male", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchool.put("female", new TableInfo.Column("female", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchool.put("selected", new TableInfo.Column("selected", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysSchool = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesSchool = new HashSet<TableInfo.Index>(1);
        _indicesSchool.add(new TableInfo.Index("index_school_censusNo", false, Arrays.asList("censusNo"), Arrays.asList("ASC")));
        final TableInfo _infoSchool = new TableInfo("school", _columnsSchool, _foreignKeysSchool, _indicesSchool);
        final TableInfo _existingSchool = TableInfo.read(connection, "school");
        if (!_infoSchool.equals(_existingSchool)) {
          return new RoomOpenDelegate.ValidationResult(false, "school(sss.yml.smi.room.School).\n"
                  + " Expected:\n" + _infoSchool + "\n"
                  + " Found:\n" + _existingSchool);
        }
        final Map<String, TableInfo.Column> _columnsStudent = new HashMap<String, TableInfo.Column>(23);
        _columnsStudent.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("censusNo", new TableInfo.Column("censusNo", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("refId", new TableInfo.Column("refId", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("userName", new TableInfo.Column("userName", "TEXT", false, 0, "''", TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("grade", new TableInfo.Column("grade", "TEXT", false, 0, "''", TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("dob", new TableInfo.Column("dob", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("mdate", new TableInfo.Column("mdate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("sex", new TableInfo.Column("sex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("height", new TableInfo.Column("height", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("weight", new TableInfo.Column("weight", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("ageY", new TableInfo.Column("ageY", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("ageM", new TableInfo.Column("ageM", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("ageD", new TableInfo.Column("ageD", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("bmi", new TableInfo.Column("bmi", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("wtZ", new TableInfo.Column("wtZ", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("htZ", new TableInfo.Column("htZ", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("wtStatus", new TableInfo.Column("wtStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("wtColor", new TableInfo.Column("wtColor", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("htStatus", new TableInfo.Column("htStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("htColor", new TableInfo.Column("htColor", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("wtPos", new TableInfo.Column("wtPos", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("htPos", new TableInfo.Column("htPos", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsStudent.put("docRef", new TableInfo.Column("docRef", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysStudent = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesStudent = new HashSet<TableInfo.Index>(1);
        _indicesStudent.add(new TableInfo.Index("index_student_id", false, Arrays.asList("id"), Arrays.asList("ASC")));
        final TableInfo _infoStudent = new TableInfo("student", _columnsStudent, _foreignKeysStudent, _indicesStudent);
        final TableInfo _existingStudent = TableInfo.read(connection, "student");
        if (!_infoStudent.equals(_existingStudent)) {
          return new RoomOpenDelegate.ValidationResult(false, "student(sss.yml.smi.room.Student).\n"
                  + " Expected:\n" + _infoStudent + "\n"
                  + " Found:\n" + _existingStudent);
        }
        return new RoomOpenDelegate.ValidationResult(true, null);
      }
    };
    return _openDelegate;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final Map<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final Map<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "child", "childWpData", "school", "student");
  }

  @Override
  public void clearAllTables() {
    super.performClear(false, "child", "childWpData", "school", "student");
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final Map<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ChildDao.class, ChildDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SchoolDao.class, SchoolDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StudentDao.class, StudentDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final Set<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ChildDao childDao() {
    if (_childDao != null) {
      return _childDao;
    } else {
      synchronized(this) {
        if(_childDao == null) {
          _childDao = new ChildDao_Impl(this);
        }
        return _childDao;
      }
    }
  }

  @Override
  public SchoolDao schoolDao() {
    if (_schoolDao != null) {
      return _schoolDao;
    } else {
      synchronized(this) {
        if(_schoolDao == null) {
          _schoolDao = new SchoolDao_Impl(this);
        }
        return _schoolDao;
      }
    }
  }

  @Override
  public StudentDao studentDao() {
    if (_studentDao != null) {
      return _studentDao;
    } else {
      synchronized(this) {
        if(_studentDao == null) {
          _studentDao = new StudentDao_Impl(this);
        }
        return _studentDao;
      }
    }
  }
}
