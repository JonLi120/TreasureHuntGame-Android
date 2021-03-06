package com.unikfunlearn.treasurehuntgame.repo.local.dao;

import com.unikfunlearn.treasurehuntgame.models.UploadRequest;
import com.unikfunlearn.treasurehuntgame.models.tables.Record;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RecordDao {

    @Insert
    long insertRecord(Record record);

    @Query("update record_table set scores = :score, finished = 1 where RID = :rid")
    int updateRecordScore(int rid, int score);

    @Query("select * from record_table where finished = 1")
    List<Record> getFinishedRecord();

    @Query("delete from record_table")
    int deleteAll();
}
