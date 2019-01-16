package com.unikfunlearn.treasurehuntgame.repo.local.dao;

import com.unikfunlearn.treasurehuntgame.models.tables.School;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SchoolDao {

    @Insert
    void insertSchool(School school);

    @Query("delete from school_table")
    void delAll();
}
