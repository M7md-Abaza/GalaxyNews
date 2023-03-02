package com.example.galaxynews.ui.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.ViewModel;

public class BaseViewModel extends ViewModel {

    public String apiKey = "2d009929ce814433a043d8158b38ad3d";

    public boolean isOnline(Context mContext) {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
