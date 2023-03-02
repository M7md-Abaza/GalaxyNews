package com.example.galaxynews.ui.fragments.main.home;

import com.example.galaxynews.data.HomeServices;
import com.example.galaxynews.pojo.HomeResponse;
import com.example.galaxynews.ui.fragments.main.home.interfaces.IHomeRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class HomeRepository implements IHomeRepository {

    private final HomeServices homeServices;

    @Inject
    public HomeRepository(HomeServices homeServices) {
        this.homeServices = homeServices;
    }

    @Override
    public Observable<HomeResponse> getByCountry(String query, String apiKey) {
        return homeServices.getByCountry(query, apiKey);
    }

    @Override
    public Observable<HomeResponse> getBySource(String query, String apiKey) {
        return homeServices.getBySource(query, apiKey);
    }

}
