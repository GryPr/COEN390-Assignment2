package com.coen390.assignment2.database.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Student implements Serializable {
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
        this.timestamp = System.currentTimeMillis();
    }

    public Student(int studentID, String surname, String name, float gpa, Date date) {
        this.studentID = studentID;
        this.surname = surname;
        this.name = name;
        this.gpa = gpa;
        this.timestamp = date.getTime();
    }

    public String getFormattedDate() {
        Date date = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd @ hh:mm:ss");
        return format.format(date);
    }
}
