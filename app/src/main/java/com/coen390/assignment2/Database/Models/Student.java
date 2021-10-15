package com.coen390.assignment2.Database.Models;


import java.util.Calendar;
import java.util.Date;

public class Student {
    private int studentID;
    private String surname, name;
    private float gpa;
    private Date timestamp;

    public Student(int studentID, String surname, String name, float gpa) {
        this.studentID = studentID;
        this.surname = surname;
        this.name = name;
        this.gpa = gpa;
        this.timestamp = Calendar.getInstance().getTime();
    }

    public Student(int studentID, String surname, String name, float gpa, Date date) {
        this.studentID = studentID;
        this.surname = surname;
        this.name = name;
        this.gpa = gpa;
        this.timestamp = date;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
