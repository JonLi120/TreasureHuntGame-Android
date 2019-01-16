package com.unikfunlearn.treasurehuntgame.repo.local.dao;

import com.unikfunlearn.treasurehuntgame.models.tables.Game;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface GameDao {

    @Insert
    void insert(Game game);

    @Query("delete from Game")
    void delAll();
}
