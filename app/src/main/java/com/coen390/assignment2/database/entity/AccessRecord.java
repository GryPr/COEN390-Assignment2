package com.coen390.assignment2.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity
public class AccessRecord {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "student_id")
    public int studentID;

    @ColumnInfo(name = "access_type")
    public String accesstype;

    @ColumnInfo(name = "timestamp")
    public long timestamp;

    public AccessRecord(int studentID, String accesstype) {
        this.studentID = studentID;
        this.accesstype = accesstype;
        this.timestamp = Calendar.getInstance().getTime().getTime();
    }

}
