package sss.yml.smi.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.List;

@TypeConverters({DateConverter.class})
@Dao
public interface StudentDao extends BaseDao<Student> {
    @Query("SELECT * FROM student where id = :id")
    LiveData<Student> getStudent(int id);

    @Query("SELECT * FROM student")
    LiveData<List<Student>> getStudentList();

    @Query("SELECT * FROM student where censusNo=:censusNo and mdate BETWEEN :from and :to and upper(grade) like upper(:grade)")
    LiveData<List<Student>> getStudentList(int censusNo, Date from, Date to, String grade);

    @Query("SELECT * FROM student where censusNo=:censusNo and mdate BETWEEN :from and :to and upper(grade)" +
            " like upper(:grade) ORDER BY " +
            "CASE WHEN :order = 0 THEN id END ASC, " +
            "CASE WHEN :order = 1 THEN id END DESC, " +
            "CASE WHEN :order = 2 THEN bmi END ASC, " +
            "CASE WHEN :order = 3 THEN bmi END DESC, " +
            "CASE WHEN :order = 4 THEN sex END DESC")
    LiveData<List<Student>> getStudentList(int censusNo, Date from, Date to, String grade, int order);

    @Query("SELECT distinct grade FROM student where censusNo=:censusNo and mdate BETWEEN :from and :to order by grade")
    LiveData<List<String>> getGradeList(int censusNo, Date from, Date to);

    @Query("SELECT distinct mdate FROM student where censusNo = :censusNo")
    List<Date> getYears(int censusNo);
}
