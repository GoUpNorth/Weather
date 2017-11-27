package com.gonnord.weather.ui;

/**
 * Created by GONNORD_pierreantoine on 26/11/2017.
 */

public interface IBasePresenter<View> {

    void onViewActive(View view);

    void onViewInactive();

}
