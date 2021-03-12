package com.example.myapplication4.myLib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnectTest {

    public static void updateNetworkConnectStatus(NetworkConnectStatus networkConnectStatus, Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
//        boolean wifiConnected = false;
//        boolean mobileConnected = false;
        if (activeInfo != null && activeInfo.isConnected()) {
//            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
//            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            networkConnectStatus.setIsOnline(true);
        } else {
            networkConnectStatus.setIsOnline(false);
        }
    }
}


