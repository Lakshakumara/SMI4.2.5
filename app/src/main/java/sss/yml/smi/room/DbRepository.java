package sss.yml.smi.room;

import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.LiveData;
import sss.yml.smi.Home.HomePage;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DbRepository {
    BMISdb database;
    private static ChildDao childDao;
    private static SchoolDao schoolDao;
    private static StudentDao studentDao;
    private LiveData<Child> child;
    private LiveData<List<ChildData>> allChildData;

    // Replaces AsyncTask's process-wide SERIAL_EXECUTOR: one thread, FIFO —
    // preserves the original ordering of DB operations app-wide.
    private static final ExecutorService DB_EXECUTOR = Executors.newSingleThreadExecutor();

    Handler handler = new Handler(Looper.getMainLooper());

    public DbRepository() {
        database = BMISdb.getInstance(HomePage.application);
        childDao = database.childDao();
        schoolDao = database.schoolDao();
        studentDao = database.studentDao();
    }

    public void close() {
        database.close();
    }

    public void setNull() {
        database.setToNull();
    }

    public Long insert(Child child) throws ExecutionException, InterruptedException {
        Future<Long> future = DB_EXECUTOR.submit(() -> childDao.insert(child));
        return future.get();
    }

    public Long insert(Student student) throws ExecutionException, InterruptedException {
        Future<Long> future = DB_EXECUTOR.submit(() -> studentDao.insert(student));
        return future.get();
    }

    public void update(Student student) {
        DB_EXECUTOR.execute(() -> studentDao.update(student));
    }

    public void delete(Student student) {
        DB_EXECUTOR.execute(() -> studentDao.delete(student));
    }

    public void insert(School school) {
        DB_EXECUTOR.execute(() -> schoolDao.insert(school));
    }

    public void update(Child child) {
        DB_EXECUTOR.execute(() -> childDao.update(child));
    }

    public void delete(Child child) {
        DB_EXECUTOR.execute(() -> childDao.delete(child));
    }

    public LiveData<Child> getChild(int childId) {
        try {
            child = DB_EXECUTOR.submit(() -> childDao.getChild(childId, false)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return child;
    }

    public Child getChild(String... childBI) {
        Child one = null;
        try {
            one = DB_EXECUTOR.submit(() ->
                    (childBI.length == 2) ? childDao.getChildByName(childBI[1]) : childDao.getChild(childBI[0]))
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return one;
    }

    public LiveData<List<String>> getGradeList(int censusNo, int year) {
        LiveData<List<String>> gradeList = null;
        Date from = new Date(year, 0, 01);
        Date to = new Date(year, 11, 31);
        try {
            gradeList = DB_EXECUTOR.submit(() -> studentDao.getGradeList(censusNo, from, to)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return gradeList;
    }

    public LiveData<List<Student>> getStudentList(int censusNo, int year, String grade, int order) {
        LiveData<List<Student>> studentList = null;
        Date from = new Date(year, 0, 01);
        Date to = new Date(year, 11, 31);
        try {
            studentList = DB_EXECUTOR.submit(() -> studentDao.getStudentList(censusNo, from, to, grade, order)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    public Integer getChildId(String name) {
        Integer childId = 0;
        try {
            childId = DB_EXECUTOR.submit(() -> childDao.getChildId(name)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return childId;
    }

    public LiveData<List<ChildData>> getChildData(String name, boolean delete) {
        try {
            allChildData = DB_EXECUTOR.submit(() -> childDao.getChildData(name, delete)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return allChildData;
    }

    public LiveData<List<String>> getStringList(String field) {
        LiveData<List<String>> textList = null;
        try {
            textList = DB_EXECUTOR.submit(() -> {
                if (field.equalsIgnoreCase("childname"))
                    return childDao.getChildName(false);
                /*else if (field.equalsIgnoreCase("model"))
                    return itemDao.getModelList();
                else if (field.equalsIgnoreCase("funding Source"))
                    return itemDao.getFoundList();*/
                else return null;
            }).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return textList;
    }

    /**
     * Child Wp DataSet
     */
    public void insert(ChildWpData childWpData) {
        DB_EXECUTOR.execute(() -> childDao.insert(childWpData));
    }

    public void update(ChildWpData childWpData) {
        DB_EXECUTOR.execute(() -> childDao.update(childWpData));
    }

    public void delete(ChildWpData childWpData) {
        DB_EXECUTOR.execute(() -> childDao.delete(childWpData));
    }

    public void deleteData(int childId) {
        DB_EXECUTOR.execute(() -> childDao.deleteData(childId));
    }

    public void delete(School school) {
        DB_EXECUTOR.execute(() -> schoolDao.delete(school));
    }

    public List<School> getSchoolList(boolean selected) {
        List<School> schoolList = null;
        try {
            schoolList = DB_EXECUTOR.submit(() -> schoolDao.getSchoolList(selected)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (schoolList == null) schoolList = new ArrayList<>();
        schoolList.add(new School(1, "Continue without School Data", "",
                "Sabaragamuwa", "Ratnapura", "Ratnapura", "Ratnapura", "Nat.",
                "G1-13", 1000, 1000, true));
        return schoolList;
    }

    public String getDbPath() {
        ContextWrapper cw = new ContextWrapper(HomePage.application);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDatabasePath("bmis_db");
        return directory.getAbsolutePath();
    }
}
