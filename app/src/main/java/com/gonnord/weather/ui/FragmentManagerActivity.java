package com.gonnord.weather.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gonnord.weather.R;
import com.gonnord.weather.ui.list.ForecastListFragment;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public class FragmentManagerActivity extends AppCompatActivity implements IFragmentManager {

    private static final String TAG = FragmentManagerActivity.class.getSimpleName();

    IBaseFragment fragment;

    /**
     * IFragmentManager implementation
     */

    @Override
    public void onFragmentStarted(Fragment fragment) {
        if(fragment instanceof IBaseFragment) {
            this.fragment = (IBaseFragment) fragment;
        }
    }

    @Override
    public <T extends Fragment> void onFragmentStopped(Class<T> stoppedFragmentClass) {
        if(this.fragment != null && this.fragment.getClass().equals(stoppedFragmentClass)) {
            this.fragment = null;
        }
    }

    @Override
    public <T extends Fragment> void displayFragment(Class<T> fragmentClass, Bundle args, boolean addToBackStack, boolean popBackStack) {
        try {
            displayFragment(fragmentClass.newInstance(), args, addToBackStack, popBackStack);
        } catch (InstantiationException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public <T extends Fragment> void displayFragment(T fragment, Bundle args, boolean addToBackStack, boolean popBackStack) {
        if(popBackStack) {
            popBackStack();
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment newFragment = getSupportFragmentManager().findFragmentByTag(
                fragment.getClass().getSimpleName());
        if (newFragment == null) {
            try {
                newFragment = fragment;
            } catch (Exception e) {
                throw new RuntimeException("New Fragment should have been created", e);
            }
        }

        try {
            if(args != null) {
                newFragment.setArguments(args);
            }
        } catch (IllegalStateException e) {
            Log.e(TAG, "Can't add arguments to fragment: " + e.getMessage(), e);
        }

        if(addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, newFragment,
                ForecastListFragment.class.getSimpleName());
        fragmentTransaction.commit();
    }

    private void popBackStack() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
    }
}
