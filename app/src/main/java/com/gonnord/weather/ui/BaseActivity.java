package com.gonnord.weather.ui;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.gonnord.weather.R;
import com.gonnord.weather.network.ConnectivityBroadcastReceiver;
import com.gonnord.weather.network.ConnectivityObservable;
import com.gonnord.weather.network.NetworkStateMonitor;
import com.gonnord.weather.utils.NetworkUtils;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public abstract class BaseActivity extends FragmentManagerActivity implements Observer {

    public static final String TAG = BaseActivity.class.getSimpleName();

    boolean runnableStarted = false;

    Handler handler;

    View parentView;

    Snackbar snackbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        handler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Check network connection
        postNotifyNoConnectivity(0);

        startNetworkMonitoring();
    }


    @Override
    protected void onPause() {
        super.onPause();

        stopNetworkMonitoring();

        removeCallbacks();
    }

    /**
     * Start the network monitoring via a NetworkCallback for devices running SDK 21+
     * and a broadcastreceiver for older devices
     */
    private void startNetworkMonitoring() {
        ConnectivityObservable.get().addObserver(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkStateMonitor.get().enable(BaseActivity.this);
        } else {
            ConnectivityBroadcastReceiver.get().enable(this);
        }
    }

    /**
     * Stop the network monitoring
     */
    private void stopNetworkMonitoring() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkStateMonitor.get().disable(BaseActivity.this);
        } else {
            ConnectivityBroadcastReceiver.get().disable(this);
        }

        ConnectivityObservable.get().deleteObserver(this);
    }

    /**
     * Removing the notification runnable from the handler queue
     */
    private void removeCallbacks() {
        if(handler != null) {
            handler.removeCallbacks(notifyNoConnectiviy);
            runnableStarted = false;
            Log.i(TAG, "Connectivity callback removed");
        }
    }

    private synchronized void postNotifyNoConnectivity(long delay) {
        if(delay == 0) {
            handler.post(notifyNoConnectiviy);
        } else {
            handler.postDelayed(notifyNoConnectiviy, delay);
        }
        Log.i(TAG, "Connectivity callback posted");

        runnableStarted = true;
    }

    /**
     * Lost network notification runnable
     * Displays a Snackbar to the user if the network is lost
     */
    private Runnable notifyNoConnectiviy = new Runnable() {

        @Override
        public void run() {
            boolean isConnected = NetworkUtils.isNetworkAvailable(BaseActivity.this);
            Log.i(TAG, "Checking connectivity after 1500ms, isConnected="+isConnected);

            if(!isConnected) {
                if(getParentView() == null) {
                    Log.e(TAG, "Parent view with id R.id.parentPanel not found");
                    runnableStarted = false;
                    return;
                }
                displaySnackbar(getString(R.string.snackbar_title_connectivity), getString(R.string.snackbar_hide_action), Snackbar.LENGTH_INDEFINITE);
            }
            runnableStarted = false;
        }
    };

    @SuppressWarnings("deprecation")
    protected void displaySnackbar(String text, String action, int length) {
        snackbar = Snackbar.make(getParentView(), text, length);
        snackbar.setAction(action, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            snackbar.setActionTextColor(BaseActivity.this.getResources().getColor(R.color.colorAccent));
        } else {
            snackbar.setActionTextColor(BaseActivity.this.getResources().getColor(R.color.colorAccent, null));
        }
        snackbar.show();
    }


    protected View getParentView() {
        if(this.parentView == null) {
            parentView = findViewById(R.id.fragment_container);
        }
        return parentView;
    }


    /**
     * Observer implementation
     */
    @Override
    public void update(Observable o, Object arg) {
        Log.i(TAG, "Observable notification received: " + o.getClass().getSimpleName());
        if (o.getClass().equals(ConnectivityObservable.class)) {
            boolean noConnectivity = (boolean) arg;

            if(noConnectivity && !runnableStarted) {
                postNotifyNoConnectivity(1500);
            } else if (!noConnectivity) {
                if(snackbar!=null) {
                    snackbar.dismiss();
                    snackbar = null;
                }
                if(handler != null) {
                    removeCallbacks();
                }
                if(this.fragment != null) {
                    this.fragment.onNetworkRecover();
                }
            }
        }
    }
}
