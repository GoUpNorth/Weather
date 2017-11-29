package com.gonnord.weather.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gonnord.weather.R;
import com.gonnord.weather.ui.list.ForecastListFragment;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public abstract class FragmentManagerActivity extends AppCompatActivity implements IFragmentManager {

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
        if(popBackStack) {
            popBackStack();
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(
                fragmentClass.getClass().getSimpleName());
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("New Fragment should have been created", e);
            }
        }
        fragment.setArguments(args);

        if(addToBackStack) {
            fragmentTransaction.addToBackStack(null);

        }
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container, fragment,
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
