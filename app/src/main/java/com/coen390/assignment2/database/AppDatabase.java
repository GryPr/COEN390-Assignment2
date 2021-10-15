package com.coen390.assignment2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.coen390.assignment2.database.dao.AccessRecordDao;
import com.coen390.assignment2.database.dao.StudentDao;
import com.coen390.assignment2.database.entity.AccessRecord;
import com.coen390.assignment2.database.entity.Student;

@Database(entities = {Student.class, AccessRecord.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase db;

    // Data Access Objects
    public abstract StudentDao studentDao();
    public abstract AccessRecordDao accessRecordDao();

    protected AppDatabase(){}

    private static AppDatabase create(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class,"studentdatabase").allowMainThreadQueries().build();
        return db;
    }

    public static synchronized AppDatabase getDb(Context context){
        if(db == null)
        {
            db = create(context);
        }
        return db;
    }

}
