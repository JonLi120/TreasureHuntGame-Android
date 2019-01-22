package com.unikfunlearn.treasurehuntgame.repo.local.dao;

import com.unikfunlearn.treasurehuntgame.models.tables.Record;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RecordDao {

    @Insert
    long insertRecord(Record record);

    @Query("update record_table set scores = :score where RID = :rid")
    int updateRecordScore(int rid, int score);
}
