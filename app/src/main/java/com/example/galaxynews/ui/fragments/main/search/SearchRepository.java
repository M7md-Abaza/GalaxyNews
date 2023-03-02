package com.example.galaxynews.ui.fragments.main.search;

import com.example.galaxynews.data.SearchServices;
import com.example.galaxynews.pojo.HomeResponse;
import com.example.galaxynews.ui.fragments.main.search.interfaces.ISearchRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class SearchRepository implements ISearchRepository {

    private final SearchServices SearchServices;

    @Inject
    public SearchRepository(SearchServices SearchServices) {
        this.SearchServices = SearchServices;
    }

    @Override
    public Observable<HomeResponse> searchData(String query, String apiKey) {
        return SearchServices.searchData(query, apiKey);
    }
}
