package com.coen390.assignment2.database.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity
public class Student {
    @PrimaryKey
    public int studentID;

    @ColumnInfo(name = "surname")
    public String surname;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "gpa")
    public float gpa;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    public Student(int studentID, String surname, String name, float gpa) {
        this.studentID = studentID;
        this.surname = surname;
        this.name = name;
        this.gpa = gpa;
        this.timestamp = Calendar.getInstance().getTime().getTime();
    }

    public Student(int studentID, String surname, String name, float gpa, Date date) {
        this.studentID = studentID;
        this.surname = surname;
        this.name = name;
        this.gpa = gpa;
        this.timestamp = date.getTime();
    }

    public Date getDate() {
        return new Date(timestamp);
    }

    public int getStudentID() {
        return studentID;
    }

    public String getSurname() {
        return surname;
    }
}
