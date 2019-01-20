package com.unikfunlearn.treasurehuntgame.repo.local.dao;

import com.unikfunlearn.treasurehuntgame.models.tables.Record;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface RecordDao {

    @Insert
    long insertRecord(Record record);
}
