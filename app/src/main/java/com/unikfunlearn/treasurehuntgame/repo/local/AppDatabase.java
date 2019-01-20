package com.unikfunlearn.treasurehuntgame.repo.local;

import android.content.Context;

import com.unikfunlearn.treasurehuntgame.models.tables.Answer;
import com.unikfunlearn.treasurehuntgame.models.tables.Game;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.models.tables.Record;
import com.unikfunlearn.treasurehuntgame.models.tables.School;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.AnswerDao;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.GameDao;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.QuestionDao;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.RecordDao;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.SchoolDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Game.class, Question.class, School.class, Record.class, Answer.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;

    public abstract GameDao gameDao();
    public abstract QuestionDao questionDao();
    public abstract SchoolDao schoolDao();
    public abstract RecordDao recordDao();
    public abstract AnswerDao answerDao();

    private static final Object sLock = new Object();

    public static AppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "database.db")
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return appDatabase;
    }
}
