package com.coen390.assignment2.Database.Models;

import java.util.Calendar;
import java.util.Date;

public class AccessRecord {
    private int studentID;
    private String accesstype;
    private Date timestamp;

    public AccessRecord(int studentID, String accesstype) {
        this.studentID = studentID;
        this.accesstype = accesstype;
        this.timestamp = Calendar.getInstance().getTime();
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getAccesstype() {
        return accesstype;
    }

    public void setAccesstype(String accesstype) {
        this.accesstype = accesstype;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
