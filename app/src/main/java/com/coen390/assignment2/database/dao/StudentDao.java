package com.coen390.assignment2.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.coen390.assignment2.database.entity.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM student")
    LiveData<List<Student>> getAllLive();

    @Query("SELECT * FROM student")
    List<Student> getAll();

    @Query("SELECT * FROM student WHERE studentID=:id")
    Student findById(int id);

    @Insert
    void insertAll(Student... students);

    @Delete
    void delete(Student student);

}
