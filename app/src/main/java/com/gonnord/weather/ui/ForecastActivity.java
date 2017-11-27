package com.gonnord.weather.ui;

import android.os.Bundle;

import com.gonnord.weather.R;
import com.gonnord.weather.ui.list.ForecastListFragment;

/**
 * Created by GONNORD_pierreantoine on 26/11/2017.
 */

public class ForecastActivity extends BaseActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        this.displayFragment(ForecastListFragment.class, null, false);
    }
}
