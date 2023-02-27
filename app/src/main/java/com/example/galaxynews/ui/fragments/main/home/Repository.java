package com.example.galaxynews.ui.fragments.main.home;

import com.example.galaxynews.data.HomeServices;
import com.example.galaxynews.pojo.HomeResponse;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {

    private HomeServices homeServices;


    public Repository(HomeServices homeServices) {
        this.homeServices = homeServices;
    }

    public Observable<HomeResponse> getNewsData(String query,String fromDate) {
        return homeServices.getNewsData(query,fromDate);
    }

}
