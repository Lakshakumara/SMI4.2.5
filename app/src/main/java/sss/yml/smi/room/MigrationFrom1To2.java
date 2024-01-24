package sss.yml.smi.room;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class MigrationFrom1To2 extends Migration {
    public MigrationFrom1To2() {
        super(1, 2);
    }

    @Override
    public void migrate(SupportSQLiteDatabase database) {
        // Add your SQL statements to update the schema here.
        // For example, if you need to add a new column:
         database.execSQL("ALTER TABLE student ADD COLUMN docRef TEXT");
    }
}