package sss.yml.smi.room;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SchoolDao extends BaseDao<School>{
    @Query("SELECT * FROM school where censusNo = :censusNo")
    School getSchool(int censusNo);

    @Query("SELECT * FROM school where district = :district and upper(name) like upper(:name)")
    School getSchool(String district, String name);

    @Query("SELECT * FROM school where selected = :deleted")
    List<School> getSchoolList(boolean deleted);

    @Query("SELECT DISTINCT censusNo FROM school")
    List<Integer> getCensusIds();
}
