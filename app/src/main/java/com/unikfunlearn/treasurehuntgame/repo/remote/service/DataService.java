package com.unikfunlearn.treasurehuntgame.repo.remote.service;

import com.unikfunlearn.treasurehuntgame.models.DownloadResponse;
import com.unikfunlearn.treasurehuntgame.repo.DataRepository;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface DataService {

    @GET("api")
    Single<DownloadResponse> downloadData();
}
