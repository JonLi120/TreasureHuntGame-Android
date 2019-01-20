package com.unikfunlearn.treasurehuntgame.repo.local.dao;

import com.unikfunlearn.treasurehuntgame.models.tables.Game;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface GameDao {

    @Insert
    void insert(Game game);

    @Query("delete from game_table")
    void delAll();

    @Query("select * from game_table where SCID = :id")
    List<Game> getGameBySCID(int id);

    @Query("select * from game_table where AID = :id")
    List<Game> getGameByAID(int id);
}
