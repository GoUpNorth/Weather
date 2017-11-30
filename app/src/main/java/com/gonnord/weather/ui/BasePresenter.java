package com.gonnord.weather.ui;

/**
 * Created by GONNORD_pierreantoine on 26/11/2017.
 */

public class BasePresenter <View> implements IBasePresenter<View> {

    protected View view;

    @Override
    public void onViewActive(View view) {
        this.view = view;
    }

    @Override
    public void onViewInactive() {
        this.view = null;
    }
}
