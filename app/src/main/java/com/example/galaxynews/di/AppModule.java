package com.example.galaxynews.di;

import com.example.galaxynews.data.HomeServices;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    public final Long TIMEOUT = 30L;


    @Provides
    @Singleton
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttp(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder().
                addInterceptor(httpLoggingInterceptor).
                connectTimeout(TIMEOUT, TimeUnit.SECONDS).
                writeTimeout(TIMEOUT, TimeUnit.SECONDS).
                readTimeout(TIMEOUT, TimeUnit.SECONDS).
                build();
    }


    @Provides
    @Singleton
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder().
                baseUrl("https://newsapi.org/").
                client(okHttpClient).
                addConverterFactory(GsonConverterFactory.create()).
                build();
    }

    @Provides
    @Singleton
    HomeServices provideHomeServices(Retrofit retrofit) {
        return retrofit.create(HomeServices.class);
    }

}
