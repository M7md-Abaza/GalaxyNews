package com.example.galaxynews.data;

import com.example.galaxynews.pojo.HomeResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeServices {

    @GET("everything")
    Observable<HomeResponse> getNewsData(
            @Query("q") String q,
            @Query("from") String from,
            @Query("sortBy") String sortBy ,
            @Query("apiKey") String apiKey
    );

}
