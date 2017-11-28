package com.gonnord.weather.network;

import android.util.Log;

import java.util.Observable;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public class ConnectivityObservable extends Observable {

    private static final String TAG = ConnectivityObservable.class.getSimpleName();

    private static ConnectivityObservable object;

    public static synchronized ConnectivityObservable get() {
        if(object == null) {
            object = new ConnectivityObservable();
        }

        return object;
    }

    public void notifyConnectivityIssue(boolean noConnectivity) {
        synchronized (this) {
            setChanged();
            notifyObservers(noConnectivity);
            Log.i(TAG, "Observers notified");
        }
    }
}
