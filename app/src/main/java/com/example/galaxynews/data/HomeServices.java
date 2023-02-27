package com.example.galaxynews.data;

import com.example.galaxynews.pojo.HomeResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeServices {

    @GET("everything{q}&{from}&sortBy=publishedAt&apiKey=eef75b5ba00148dfa0e7f01d858dcd5d")
    Observable<HomeResponse> getNewsData(
            @Query("q") String q,
            @Query("from") String from
    );

}
