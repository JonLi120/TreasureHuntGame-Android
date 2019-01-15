package com.unikfunlearn.treasurehuntgame.repo.local;

import android.content.Context;

import com.unikfunlearn.treasurehuntgame.models.tables.Act;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.ActDao;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.QuestionDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Act.class, Question.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;

    public abstract ActDao actDao();
    public abstract QuestionDao questionDao();

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
