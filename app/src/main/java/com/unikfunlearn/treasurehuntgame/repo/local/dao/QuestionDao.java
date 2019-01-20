package com.unikfunlearn.treasurehuntgame.repo.local.dao;

import com.unikfunlearn.treasurehuntgame.models.tables.Question;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface QuestionDao {

    @Insert
    void insertQuestion(Question question);

    @Query("delete from question_table")
    void delQuestionAll();

    @Query("select * from question_table where AID = :id")
    List<Question> getQuestionByAID(int id);
}
