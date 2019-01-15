package com.unikfunlearn.treasurehuntgame.repo.local.dao;

import com.unikfunlearn.treasurehuntgame.models.tables.Question;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface QuestionDao {

    @Insert
    void insertQuestion(Question question);

    @Query("delete from question_table")
    void delQuestionAll();
}
