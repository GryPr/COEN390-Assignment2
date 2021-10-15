package com.coen390.assignment2.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.coen390.assignment2.database.entity.AccessRecord;

import java.util.List;

@Dao
public interface AccessRecordDao {
    @Query("SELECT * FROM accessrecord")
    List<AccessRecord> getAll();

    @Query("SELECT * FROM accessrecord WHERE uid=:uid")
    AccessRecord findByUid(int uid);

    @Insert
    void insertAll(AccessRecord... accessRecords);

    @Delete
    void delete(AccessRecord accessRecord);
}
