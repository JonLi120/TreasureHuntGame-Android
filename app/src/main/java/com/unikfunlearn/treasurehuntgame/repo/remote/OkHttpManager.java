package com.unikfunlearn.treasurehuntgame.repo.remote;

import com.unikfunlearn.treasurehuntgame.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpManager {
    private static OkHttpClient okHttpClient;

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OkHttpManager.class) {
                if (okHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();

                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(interceptor);
                    }

                    builder.addInterceptor(new ResponseInterceptor());
                    builder.addNetworkInterceptor(new NetInterceptor());
                    builder.connectTimeout(30, TimeUnit.SECONDS);
                    builder.readTimeout(30, TimeUnit.SECONDS);
                    builder.writeTimeout(30, TimeUnit.SECONDS);
                    builder.retryOnConnectionFailure(false);
                    okHttpClient = builder.build();
                }
            }
        }
        return okHttpClient;
    }

    static class NetInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder().addHeader("Connection", "close").build();
            return chain.proceed(request);
        }
    }

    static class ResponseInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            return response.newBuilder()
                    .addHeader("Content-Type", "application/json; charset=utf-8")
                    .build();
        }
    }
}
