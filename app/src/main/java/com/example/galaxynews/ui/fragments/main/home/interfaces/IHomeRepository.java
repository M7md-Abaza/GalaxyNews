package com.example.galaxynews.ui.fragments.main.home.interfaces;

import com.example.galaxynews.pojo.HomeResponse;

import io.reactivex.rxjava3.core.Observable;

public interface IHomeRepository {

    Observable<HomeResponse> getByCountry(String query, String apiKey);

    Observable<HomeResponse> getBySource(String query, String apiKey);
}
