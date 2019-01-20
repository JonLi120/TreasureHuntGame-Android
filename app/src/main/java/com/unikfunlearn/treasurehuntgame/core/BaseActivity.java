package com.unikfunlearn.treasurehuntgame.core;

import android.os.Bundle;

import com.unikfunlearn.treasurehuntgame.repo.local.AppDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected BaseApplication baseApplication;
    protected AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseApplication = BaseApplication.getInstance();
        db = AppDatabase.getInstance(baseApplication);
    }
}
