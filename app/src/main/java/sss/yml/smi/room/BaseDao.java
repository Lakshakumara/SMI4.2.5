package sss.yml.smi.room;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(T obj);

    @Update
    void update(T obj);

    @Delete
    void delete(T obj);
}
