package com.coen390.assignment2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coen390.assignment2.Models.Student;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, Config.DATABASE_NAME, null, Config.DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create student table
        String CREATE_TABLE_STUDENT = "CREATE TABLE " + Config.STUDENT_TABLE_NAME
                + " (" + Config.COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY, "
                + Config.COLUMN_STUDENT_SURNAME + "TEXT NOT NULL, "
                + Config.COLUMN_STUDENT_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_STUDENT_GPA + " DECIMAL(1,2) NOT NULL, "
                + Config.COLUMN_STUDENT_TIMESTAMP + " TIMESTAMP NOT NULL)";

        sqLiteDatabase.execSQL(CREATE_TABLE_STUDENT);

        // Create access table
        String CREATE_TABLE_ACCESS = "CREATE TABLE " + Config.ACCESS_TABLE_NAME
                + " (" + Config.COLUMN_ACCESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_STUDENT_ID_ACCESS + "INTEGER NOT NULL, "
                + Config.COLUMN_ACCESS_TYPE + " TEXT NOT NULL, "
                + Config.COLUMN_ACCESS_TIMESTAMP + " TIMESTAMP NOT NULL)";

        sqLiteDatabase.execSQL(CREATE_TABLE_ACCESS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertStudent(Student student) {

    }
}
