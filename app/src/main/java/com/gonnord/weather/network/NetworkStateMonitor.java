package com.gonnord.weather.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by pierre-antoinegonnord on 27/11/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkStateMonitor extends ConnectivityManager.NetworkCallback {

    private static final String TAG = NetworkStateMonitor.class.getSimpleName();

    private final NetworkRequest networkRequest;

    private static NetworkStateMonitor monitor;

    private NetworkStateMonitor() {
        networkRequest = new NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build();
    }

    public static NetworkStateMonitor get() {
        if(monitor==null) {
            monitor = new NetworkStateMonitor();
        }

        return monitor;
    }

    public void enable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerNetworkCallback(networkRequest , this);
    }

    public void disable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.unregisterNetworkCallback(this);
    }


    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.i(TAG, "Network lost");
        ConnectivityObservable.get().notifyConnectivityIssue(true);
    }


    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.i(TAG, "Network available");
        ConnectivityObservable.get().notifyConnectivityIssue(false);
    }
}
