package com.coen390.assignment2.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.coen390.assignment2.Database.Models.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


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

    public List<Student> getStudentList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        List<Student> students = new ArrayList();

        try {
            cursor = db.query(Config.STUDENT_TABLE_NAME, null, null, null, null, null, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {

                    do {
                        @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Config.COLUMN_STUDENT_ID));
                        @SuppressLint("Range") String surname = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_SURNAME));
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_NAME));
                        @SuppressLint("Range") String unparsedGpa = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_GPA));
                        float gpa = Float.parseFloat(unparsedGpa);
                        @SuppressLint("Range") String unparsedDate = cursor.getString(cursor.getColumnIndex(Config.COLUMN_STUDENT_TIMESTAMP));
                        Date date;
                        try {
                            date = dateFormat.parse(unparsedDate);
                        } catch (ParseException pe) {
                            Toast.makeText(context, "Exception parsing date: " + pe, Toast.LENGTH_LONG).show();
                            break;
                        }
                        students.add(new Student(id, surname, name, gpa, date));
                    } while (cursor.moveToNext());
                }
            }

        } catch (SQLiteException e) {
            Toast.makeText(context, "Exception getting student list: " + e, Toast.LENGTH_LONG);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return students;
    }

    public boolean insertStudent(Student student) {
        boolean success = true;
        // Check if student already exists in the database
        if (checkIfStudentExists(student.getStudentID())) {
            Toast.makeText(context, "Student ID already exists", Toast.LENGTH_SHORT).show();
            return false;
        }

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Config.COLUMN_STUDENT_ID, student.getStudentID());
        contentValues.put(Config.COLUMN_STUDENT_SURNAME, student.getSurname());
        contentValues.put(Config.COLUMN_STUDENT_NAME, student.getName());
        contentValues.put(Config.COLUMN_STUDENT_GPA, student.getGpa());

        // format date before inserting
        contentValues.put(Config.COLUMN_STUDENT_TIMESTAMP, dateFormat.format(student.getTimestamp()));

        try {
            db.insertOrThrow(Config.STUDENT_TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {
            Toast.makeText(context, "Exception inserting student: " + e, Toast.LENGTH_LONG).show();
            success = false;
        } finally {
            db.close();
        }

        return success;
    }

    public boolean checkIfStudentExists(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + Config.STUDENT_TABLE_NAME + " WHERE " + Config.COLUMN_STUDENT_ID + " =?";

        Cursor cursor = db.rawQuery(select, new String[] {Integer.toString(id)});

        return cursor.moveToFirst();
    }
}
