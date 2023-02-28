package com.example.galaxynews.ui.fragments.main.search;

import com.example.galaxynews.data.HomeServices;
import com.example.galaxynews.data.SearchServices;
import com.example.galaxynews.pojo.HomeResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class SearchRepository {

    private final SearchServices SearchServices;

    @Inject
    public SearchRepository(SearchServices SearchServices) {
        this.SearchServices = SearchServices;
    }

    public Observable<HomeResponse> searchData(String query, String from, String sortBy, String apiKey) {
        return SearchServices.searchData(query, from, sortBy, apiKey);
    }
}
