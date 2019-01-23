package com.unikfunlearn.treasurehuntgame.repo.local.dao;

import com.unikfunlearn.treasurehuntgame.models.tables.Answer;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface AnswerDao {

    @Insert
    void insert(Answer... answer);
}
