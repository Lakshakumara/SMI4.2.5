package sss.yml.smi.room;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Child.class, ChildWpData.class, School.class, Student.class}, version = 2)
public abstract class BMISdb extends RoomDatabase {
    public static final Migration MIGRATION_1_2 = new MigrationFrom1To2();

    private static BMISdb instance;
    public abstract ChildDao childDao();
    public abstract SchoolDao schoolDao();
    public abstract StudentDao studentDao();
    public static synchronized BMISdb getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BMISdb.class, "bmis_db")
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomDatabase)
                    .build();
        }
        return  instance;
    }
    private static final RoomDatabase.Callback roomDatabase = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    public void setToNull(){
        if(!instance.isOpen()) instance = null;
    }
}
