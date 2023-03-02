package com.example.galaxynews.data;

import com.example.galaxynews.pojo.HomeResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeServices {

String key = "";

    @GET("top-headlines")
    Observable<HomeResponse> getByCountry(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("top-headlines")
    Observable<HomeResponse> getBySource(
            @Query("sources") String sources,
            @Query("apiKey") String apiKey
    );

}
