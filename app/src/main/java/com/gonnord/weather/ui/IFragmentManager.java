package com.gonnord.weather.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public interface IFragmentManager {

    <T extends Fragment> void displayFragment(Class<T> fragmentClass, Bundle args, boolean addToBackStack);

    <T extends Fragment> void displayFragment(T fragmentClass, Bundle args, boolean addToBackStack);

    <T extends Fragment> void displayFragment(T fragmentClass, Bundle args, boolean addToBackStack, boolean popBackStack);

    void onFragmentStarted(Fragment fragment);

    <T extends Fragment> void onFragmentStopped(Class<T> stoppedFragmentClass);
}
