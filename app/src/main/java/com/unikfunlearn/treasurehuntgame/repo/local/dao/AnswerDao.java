package com.unikfunlearn.treasurehuntgame.repo.local.dao;

import com.unikfunlearn.treasurehuntgame.models.tables.Answer;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AnswerDao {

    @Insert
    void insert(Answer... answer);

    @Query("select * from answer_table where rid = :rid")
    List<Answer> getAnswer(int rid);

    @Query("delete from answer_table")
    int deleteAll();
}
