package com.unikfunlearn.treasurehuntgame.core;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.unikfunlearn.treasurehuntgame.BuildConfig;

import androidx.multidex.MultiDex;

public class BaseApplication extends Application {
    private static BaseApplication baseApplication;

    public static BaseApplication getInstance() {
        return baseApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }


    }
}
