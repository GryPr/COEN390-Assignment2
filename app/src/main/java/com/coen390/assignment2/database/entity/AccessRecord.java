package com.coen390.assignment2.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
public class AccessRecord {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "student_id")
    public int studentID;

    @ColumnInfo(name = "access_type")
    public String accesstype;

    // Milliseconds since the epoch
    @ColumnInfo(name = "timestamp")
    public long timestamp;

    public AccessRecord(int studentID, String accesstype) {
        this.studentID = studentID;
        this.accesstype = accesstype;
        this.timestamp = Calendar.getInstance().getTime().getTime();
    }

    // Convert from ms since epoch to human-readable format
    public String getFormattedDate() {
        Date date = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd @ hh:mm:ss");
        return format.format(date);
    }

}
