package com.gonnord.weather.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by pierre-antoinegonnord on 06/11/2017.
 */

public class ConnectivityBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = ConnectivityBroadcastReceiver.class.getSimpleName();

    private static ConnectivityBroadcastReceiver receiver;

    public static ConnectivityBroadcastReceiver get() {
        if(receiver == null) {
            receiver = new ConnectivityBroadcastReceiver();
        }
        return receiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().contentEquals(ConnectivityManager.CONNECTIVITY_ACTION)){
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            Log.i(TAG, "No connectivity= "+noConnectivity);

            ConnectivityObservable.get().notifyConnectivityIssue(noConnectivity);
        }
    }

    public void enable(Context context) {
        IntentFilter filterUpdate = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, filterUpdate);
    }

    public void disable(Context context) {
        context.unregisterReceiver(receiver);
    }
}
