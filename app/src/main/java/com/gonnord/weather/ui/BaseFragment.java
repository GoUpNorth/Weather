package com.gonnord.weather.ui;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public abstract class BaseFragment extends Fragment implements IBaseFragment {

    protected IFragmentManager fragmentManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() instanceof IFragmentManager) {
            fragmentManager = (IFragmentManager) getActivity();
            fragmentManager.onAttachFragment(this);
        }
    }

    @Override
    public void onDetach() {
        fragmentManager.onDetachFragment();
        fragmentManager = null;
        super.onDetach();
    }
}
