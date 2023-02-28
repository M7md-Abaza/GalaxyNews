package com.example.galaxynews.ui.fragments.main.home;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.galaxynews.pojo.Article;
import com.example.galaxynews.pojo.HomeResponse;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    MutableLiveData<List<Article>> sliderNewsMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<Article>> latestNewsMutableLiveData = new MutableLiveData<>();

    @Inject
    public HomeViewModel() {

    }

    @Inject
    HomeRepository homeRepository;


    public void getNews() {
        Observable<HomeResponse> observable = homeRepository.getNewsData("egypt", "26-02-2023", "publishedAt", "eef75b5ba00148dfa0e7f01d858dcd5d")
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
                sliderNewsMutableLiveData.setValue(homeResponse.getArticles());
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

    public void getLatestNews() {
        Observable<HomeResponse> observable = homeRepository.getNewsData("tesla", "26-02-2023", "publishedAt", "eef75b5ba00148dfa0e7f01d858dcd5d")
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
//                List<Article> newsList = homeResponse.getArticles().stream().filter(article -> article.getSource().getName().equals("bbc-news")).collect(Collectors.toList());
//                newsList.addAll(homeResponse.getArticles().stream().filter(article -> article.getSource().getName().equals("the-next-web")).collect(Collectors.toList()));
                latestNewsMutableLiveData.setValue(homeResponse.getArticles());
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

    public boolean isOnline(Context mContext) {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

