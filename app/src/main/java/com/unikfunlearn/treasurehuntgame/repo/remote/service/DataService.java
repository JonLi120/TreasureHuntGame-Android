package com.unikfunlearn.treasurehuntgame.repo.remote.service;

import com.unikfunlearn.treasurehuntgame.models.DownloadResponse;
import com.unikfunlearn.treasurehuntgame.models.SchoolResponse;
import com.unikfunlearn.treasurehuntgame.repo.DataRepository;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {

    @GET("api")
    Single<DownloadResponse> downloadData();

    @GET("api/school")
    Call<SchoolResponse> callSchoolApi();

    @POST("api/grade")
    @FormUrlEncoded
    Call<ResponseBody> callUploadApi(@Field("data") String data);
}
