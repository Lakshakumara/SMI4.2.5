package sss.yml.smi.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;

@TypeConverters({DateConverter.class})
@Dao
public interface ChildDao  extends BaseDao<Child>{
    @Transaction
    @Query("SELECT * FROM child WHERE name LIKE :name AND deleted = :delete order by name")
    LiveData<List<ChildData>> getChildData(String name, boolean delete);

    @Query("SELECT * FROM child where id = :childId and deleted = :delete")
    LiveData<Child> getChild(int childId, boolean delete);

    @Query("SELECT * FROM child WHERE upper(name) = upper(:name)") // LIMIT 1
    Child getChildByName(String name);

    @Query("SELECT * FROM child where regNo = :regNo")
    Child getChild(String regNo);

    @Query("SELECT name FROM child WHERE deleted = :delete order by name") // LIMIT 1
    LiveData<List<String>> getChildName(boolean delete);

    @Query("SELECT id FROM child WHERE upper(name) = upper(:name) ")
    Integer getChildId(String name);

    @Query("DELETE FROM ChildWpData WHERE childId =:childId ")
    void deleteData(int childId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ChildWpData childWpData);
    @Update
    void update (ChildWpData childWpData);
    @Delete
    void delete (ChildWpData childWpData);
    @Delete
    void deleteAll (ChildWpData... childWpData);

}
