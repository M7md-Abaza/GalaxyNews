package com.example.galaxynews.data;

import com.example.galaxynews.pojo.HomeResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchServices {

    @GET("everything")
    Observable<HomeResponse> searchData(
            @Query("q") String q,
            @Query("apiKey") String apiKey
    );
}
