package com.example.galaxynews.ui.fragments.main.search.interfaces;

import com.example.galaxynews.pojo.HomeResponse;

import io.reactivex.rxjava3.core.Observable;

public interface ISearchRepository {

    Observable<HomeResponse> searchData(String query, String apiKey);
}
