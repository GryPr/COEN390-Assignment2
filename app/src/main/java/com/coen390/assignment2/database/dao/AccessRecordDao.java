package com.coen390.assignment2.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.coen390.assignment2.database.entity.AccessRecord;

import java.util.List;

@Dao
public interface AccessRecordDao {

    @Query("SELECT * FROM accessrecord WHERE student_id=:id")
    List<AccessRecord> findByStudentId(int id);

    @Insert
    void insertAll(AccessRecord... accessRecords);

}
