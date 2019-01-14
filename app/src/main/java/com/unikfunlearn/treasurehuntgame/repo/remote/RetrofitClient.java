package com.unikfunlearn.treasurehuntgame.repo.remote;

import com.unikfunlearn.treasurehuntgame.repo.remote.service.DataService;

import retrofit2.Retrofit;

public class RetrofitClient {

    private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;

    private DataService dataService;

    private RetrofitClient() {
        retrofit = RetrofitBuilder.buildRetrofit();

        dataService = create(DataService.class);
    }

    public synchronized static RetrofitClient getInstance() {
        if (retrofitClient == null) {
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    private <T> T create(Class<T> clz) {
        return retrofit.create(clz);
    }

    public DataService getDataService() {
        return dataService;
    }
}
