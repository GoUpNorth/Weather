package com.gonnord.weather.ui;

import android.support.v4.app.Fragment;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public abstract class BaseFragment extends Fragment implements IBaseFragment {

    protected IFragmentManager fragmentManager;

    @Override
    public void onStart() {
        super.onStart();
        if(getActivity() instanceof IFragmentManager) {
            fragmentManager = (IFragmentManager) getActivity();
            fragmentManager.onFragmentStarted(this);
        }
    }

    @Override
    public void onStop() {
        fragmentManager.onFragmentStopped(this.getClass());
        fragmentManager = null;
        super.onStop();
    }
}
