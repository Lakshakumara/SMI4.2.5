package sss.yml.smi.room;

import android.content.ContextWrapper;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import sss.yml.smi.Home.HomePage;
import sss.yml.smi.util.Logger;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

public class DbRepository {
    BMISdb database;
    private static ChildDao childDao;
    private static SchoolDao schoolDao;
    private static StudentDao studentDao;
    private LiveData<Child> child;
    private LiveData<List<ChildData>> allChildData;

    Handler handler = new Handler(Looper.getMainLooper());
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

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
        InsertChildAsyncTask t = new InsertChildAsyncTask();
        t.execute(child);
        return t.get();
    }

    public Long insert(Student student) throws ExecutionException, InterruptedException {
        InsertStudentAsyncTask t = new InsertStudentAsyncTask();
        t.execute(student);
        return t.get();
    }

    public void update(Student student) {
        UpdateStudentAsyncTask t = new UpdateStudentAsyncTask();
        t.execute(student);
    }

    public void delete(Student student) {
        DeleteStudentAsyncTask t = new DeleteStudentAsyncTask();
        t.execute(student);
        //t.get();
    }

    private static class DeleteStudentAsyncTask extends AsyncTask<Student, Void, Void> {
        @Override
        protected Void doInBackground(Student... student) {
            studentDao.delete(student[0]);
            return null;
        }
    }

    private static class InsertChildAsyncTask extends AsyncTask<Child, Void, Long> {
        @Override
        protected Long doInBackground(Child... children) {
            return childDao.insert(children[0]);
        }
    }

    public void insert(School school) {
        InsertSchoolAsyncTask t = new InsertSchoolAsyncTask();
        t.execute(school);
    }

    private static class InsertSchoolAsyncTask extends AsyncTask<School, Void, Long> {
        @Override
        protected Long doInBackground(School... schools) {
            return schoolDao.insert(schools[0]);
        }
    }

    private static class InsertStudentAsyncTask extends AsyncTask<Student, Void, Long> {
        @Override
        protected Long doInBackground(Student... student) {
            return studentDao.insert(student[0]);
        }
    }

    private static class UpdateStudentAsyncTask extends AsyncTask<Student, Void, Void> {
        @Override
        protected Void doInBackground(Student... student) {
            studentDao.update(student[0]);
            return null;
        }
    }

    public void update(Child child) {
        new UpdateChildAsyncTask().execute(child);
    }

    private static class UpdateChildAsyncTask extends AsyncTask<Child, Void, Void> {
        @Override
        protected Void doInBackground(Child... children) {
            childDao.update(children[0]);
            return null;
        }
    }

    public void delete(Child child) {
        new DeleteChildAsyncTask().execute(child);
    }

    private static class DeleteChildAsyncTask extends AsyncTask<Child, Void, Void> {
        @Override
        protected Void doInBackground(Child... children) {
            childDao.delete(children[0]);
            return null;
        }
    }

    public LiveData<Child> getChild(int childId) {
        try {
            child = new GetChildAsyncTask().execute(childId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return child;
    }

    public Child getChild(String... childBI) {
        Child one = null;
        try {
            one = new GetOneChildAsyncTask().execute(childBI).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return one;
    }

    public LiveData<List<String>> getGradeList(int censusNo, int year) {
        LiveData<List<String>> gradeList = null;
        try {
            gradeList = new GetGradeList(censusNo, year).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return gradeList;
    }

    private static class GetGradeList extends AsyncTask<Void, Void, LiveData<List<String>>> {
        int censusNo;
        Date from;
        Date to;

        public GetGradeList(int censusNo, int year) {
            this.censusNo = censusNo;
            Calendar c = Calendar.getInstance();
            c.set(year, 0, 1);
            this.from = c.getTime();
            //new Date(year-
            c.set(year, 11, 31);
            this.to = c.getTime();
        }

        @Override
        protected LiveData<List<String>> doInBackground(Void... voids) {
            Logger.setMsg(censusNo, from, to);
            return studentDao.getGradeList(censusNo, from, to);
        }
    }

    public LiveData<List<Student>> getStudentList(int censusNo, int year, String grade, int order) {
        LiveData<List<Student>> studentList = null;
        try {
            studentList = new GetStudentList(censusNo, year, grade, order).execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    private static class GetStudentList extends AsyncTask<Void, Void, LiveData<List<Student>>> {
        int censusNo;
        Date from;
        Date to;
        String grade;
        int order;

        public GetStudentList(int censusNo, int year, String grade, int order) {
            this.censusNo = censusNo;
            this.from = new Date(year, 0, 01);
            this.to = new Date(year, 11, 31);
            this.grade = grade;
            this.order = order;
        }

        @Override
        protected LiveData<List<Student>> doInBackground(Void... voids) {
            return studentDao.getStudentList(censusNo, from, to, grade, order);
        }
    }

    private static class GetChildAsyncTask extends AsyncTask<Integer, Void, LiveData<Child>> {
        @Override
        protected LiveData<Child> doInBackground(Integer... childId) {
            return childDao.getChild(childId[0], false);
        }
    }

    private static class GetOneChildAsyncTask extends AsyncTask<String, Void, Child> {
        @Override
        protected Child doInBackground(String... childBI) {
            return (childBI.length == 2) ? childDao.getChildByName(childBI[1]) : childDao.getChild(childBI[0]);
        }
    }

    public Integer getChildId(String name) {
        Integer childId = 0;
        try {
            childId = new GetChildIdAsyncTask().execute(name).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return childId;
    }

    private static class GetChildIdAsyncTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... name) {
            return childDao.getChildId(name[0]);
        }
    }

    public LiveData<List<ChildData>> getChildData(String name, boolean delete) {
        try {
            allChildData = new GetChildDataByNameAsyncTask(delete).execute(name).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return allChildData;
    }

    private static class GetChildDataByNameAsyncTask extends AsyncTask<String, Void, LiveData<List<ChildData>>> {
        boolean delete;

        private GetChildDataByNameAsyncTask(boolean delete) {
            this.delete = delete;
        }

        @Override
        protected LiveData<List<ChildData>> doInBackground(String... name) {
            return childDao.getChildData(name[0], delete);
        }
    }

    public LiveData<List<String>> getStringList(String field) {
        LiveData<List<String>> textList = null;
        try {
            textList = new GetFieldAsyncTask().execute(field).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return textList;
    }

    private static class GetFieldAsyncTask extends AsyncTask<String, Void, LiveData<List<String>>> {
        @Override
        protected LiveData<List<String>> doInBackground(String... filed) {
            if (filed[0].equalsIgnoreCase("childname"))
                return childDao.getChildName(false);
            else return null;
        }
    }

    /**
     * Child Wp DataSet
     */
    public void insert(ChildWpData childWpData) {
        new InsertChildWpDataAsyncTask().execute(childWpData);
    }

    private static class InsertChildWpDataAsyncTask extends AsyncTask<ChildWpData, Void, Void> {
        @Override
        protected Void doInBackground(ChildWpData... childWpData) {
            //Log.e("xyz", "childWpData insert  " + childWpData[0]);
            childDao.insert(childWpData[0]);
            return null;

        }
    }

    public void update(ChildWpData childWpData) {
        new UpdateChildWpDataAsyncTask().execute(childWpData);
    }

    private static class UpdateChildWpDataAsyncTask extends AsyncTask<ChildWpData, Void, Void> {
        @Override
        protected Void doInBackground(ChildWpData... childWpData) {
            childDao.update(childWpData[0]);
            return null;
        }
    }

    public void delete(ChildWpData childWpData) {
        new DeleteChildWpDataAsyncTask().execute(childWpData);
    }

    private static class DeleteChildWpDataAsyncTask extends AsyncTask<ChildWpData, Void, Void> {
        @Override
        protected Void doInBackground(ChildWpData... childWpDatas) {
            childDao.delete(childWpDatas[0]);
            return null;
        }
    }

    public void deleteData(int childId) {
        new DeleteDataAsyncTask().execute(childId);
    }

    private static class DeleteDataAsyncTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... childId) {
            childDao.deleteData(childId[0]);
            return null;
        }
    }

    public void delete(School school) {
        new DeleteSchoolAsyncTask().execute(school);
    }

    private static class DeleteSchoolAsyncTask extends AsyncTask<School, Void, Void> {
        @Override
        protected Void doInBackground(School... schools) {
            schoolDao.delete(schools[0]);
            return null;
        }
    }

    public List<School> getSchoolList(boolean selected) {
        List<School> schoolList = null;
        try {
            schoolList = new GetSchoolList().execute(selected).get();
        } catch (ExecutionException | InterruptedException ignored) {
        }
        if (schoolList == null) schoolList = new ArrayList<>();
        schoolList.add(new School(1, "Continue without School Data", "",
                "Sabaragamuwa", "Ratnapura", "Ratnapura", "Ratnapura", "Nat.",
                "G1-13", 1000, 1000, true));
        return schoolList;
    }

    private static class GetSchoolList extends AsyncTask<Boolean, Void, List<School>> {
        @Override
        protected List<School> doInBackground(Boolean... selected) {
            return schoolDao.getSchoolList(selected[0]);
        }
    }

   /* public LiveData<List<Integer>> getYears(int censusNo) {
        LiveData<List<Integer>> textList = null;
        try {
            textList = new GetYearsAsyncTask().execute(censusNo).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return textList;
    }

    private static class GetYearsAsyncTask extends AsyncTask<Integer, Void, LiveData<List<Integer>>> {
        @Override
        protected LiveData<List<Integer>> doInBackground(Integer... filed) {
            List<Date> dates = studentDao.getYears(filed[0]);

            List<Integer> years = new ArrayList<>();
            for (Date date : dates) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                years.add(calendar.get(Calendar.YEAR));
            }
            List<Integer> sortedYears = new ArrayList<>(years);
            Collections.sort(sortedYears);
            return new MutableLiveData<>(sortedYears);
        }
    }*/
   public LiveData<List<Integer>> getYears(int censusNo) {
       MutableLiveData<List<Integer>> liveData = new MutableLiveData<>();

       executorService.execute(() -> {
           List<Date> dates = studentDao.getYears(censusNo);

           List<Integer> years = new ArrayList<>();
           for (Date date : dates) {
               Calendar calendar = Calendar.getInstance();
               calendar.setTime(date);
               years.add(calendar.get(Calendar.YEAR));
           }

           Collections.sort(years); // Sort the years
           liveData.postValue(years); // Update LiveData on the main thread
       });

       return liveData;
   }

    public Cursor getCursor(String query, Object[] args) {
        return database.query(query, args);
    }

    public String getDbPath() {
        ContextWrapper cw = new ContextWrapper(HomePage.application);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDatabasePath("bmis_db");
        return directory.getAbsolutePath();
    }
}
