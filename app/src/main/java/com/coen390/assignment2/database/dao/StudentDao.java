package com.coen390.assignment2.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.coen390.assignment2.database.entity.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM student")
    List<Student> getAll();

    @Query("SELECT studentID FROM student")
    List<Integer> getAllId();

    @Query("SELECT name FROM student")
    List<String> getAllName();

    @Query("SELECT surname FROM student")
    List<String> getAllSurname();

    @Query("SELECT * FROM student WHERE studentID=:id")
    Student findById(int id);

    @Insert
    void insertAll(Student... students);

    @Delete
    void delete(Student student);

}
