package com.coen390.assignment2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.coen390.assignment2.database.dao.AccessRecordDao;
import com.coen390.assignment2.database.dao.StudentDao;
import com.coen390.assignment2.database.entity.AccessRecord;
import com.coen390.assignment2.database.entity.Student;

// Instead of accessing the database using raw sql queries, instead use Room (included in the Android libraries) which abstracts the queries
// This is a much safer way of accessing sqlite without needing to validate and sanitize queries
@Database(entities = {Student.class, AccessRecord.class}, version = 2)
public abstract class DatabaseHelper extends RoomDatabase {
    private static volatile DatabaseHelper db;

    // Data Access Objects
    public abstract StudentDao studentDao();
    public abstract AccessRecordDao accessRecordDao();

    protected DatabaseHelper(){}

    // Create the database object
    private static DatabaseHelper create(Context context) {
        db = Room.databaseBuilder(context, DatabaseHelper.class,"studentdatabase").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        return db;
    }

    // Get the database object, if it doesn't exist, create it
    public static synchronized DatabaseHelper getDb(Context context){
        if(db == null)
        {
            db = create(context);
        }
        return db;
    }

}
