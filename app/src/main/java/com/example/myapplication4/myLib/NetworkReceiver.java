package com.example.myapplication4.myLib;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

public class NetworkReceiver extends BroadcastReceiver {
    private final NetworkConnectStatus networkConnectStatus;

    public NetworkReceiver(NetworkConnectStatus networkConnectStatus) {
        this.networkConnectStatus = networkConnectStatus;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkConnectTest.updateNetworkConnectStatus(networkConnectStatus, context);
    }

    public static NetworkReceiver registerNetworkReceiver(NetworkConnectStatus networkConnectStatus, Context context) {
        NetworkReceiver receiver = new NetworkReceiver(networkConnectStatus);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, filter);
        return receiver;
    }

    public static void unregisterNetworkReceiver(NetworkReceiver receiver, Context context) {
        context.unregisterReceiver(receiver);
    }

}
