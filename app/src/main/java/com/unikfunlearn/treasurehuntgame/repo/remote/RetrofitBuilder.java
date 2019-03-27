package com.unikfunlearn.treasurehuntgame.repo.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitBuilder {
    private static Retrofit retrofit;

    public static synchronized Retrofit buildRetrofit() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();

            retrofit = new Retrofit.Builder().client(OkHttpManager.getInstance())
                    .baseUrl("http://www.unikfunlearn.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
