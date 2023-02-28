package com.example.galaxynews.ui.fragments.main.home;

import com.example.galaxynews.data.HomeServices;
import com.example.galaxynews.pojo.HomeResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class HomeRepository {

    private final HomeServices homeServices;

    @Inject
    public HomeRepository(HomeServices homeServices) {
        this.homeServices = homeServices;
    }

    public Observable<HomeResponse> getByCountry(String query, String apiKey) {
        return homeServices.getByCountry(query, apiKey);
    }

    public Observable<HomeResponse> getBySource(String query, String apiKey) {
        return homeServices.getBySource(query, apiKey);
    }

}
