package com.example.galaxynews.ui.fragments.main.search;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.galaxynews.pojo.Article;
import com.example.galaxynews.pojo.HomeResponse;
import com.example.galaxynews.ui.base.BaseViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class SearchViewModel extends BaseViewModel {

    @Inject
    SearchRepository searchRepository;

    @Inject
    public SearchViewModel() {
    }

    public MutableLiveData<List<Article>> searchMutableLiveData = new MutableLiveData<>();

    public void getSearchResults(String search) {
        Observable<HomeResponse> observable = searchRepository.searchData(search, apiKey)
                .debounce(500, TimeUnit.MILLISECONDS)
                // to change thread from Main Thread to io to run on background because it takes long time
                .subscribeOn(Schedulers.io())
                // to manage download(Observer) stream to ba as upload stream(Observable)
                .observeOn(AndroidSchedulers.mainThread());

        Observer<HomeResponse> observer = new Observer<HomeResponse>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(@NonNull HomeResponse homeResponse) {
                searchMutableLiveData.setValue(homeResponse.getArticles());
            }


            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribe(observer);
    }
}
