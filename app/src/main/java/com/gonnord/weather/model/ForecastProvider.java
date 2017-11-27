package com.gonnord.weather.model;

import android.content.Context;

/**
 * Created by GONNORD_pierreantoine on 26/11/2017.
 */

public class ForecastProvider {

    private static final String TAG = ForecastProvider.class.getSimpleName();

    private ForecastSource source;

    public ForecastProvider() {
        source = new ForecastProviderInjection().getSource();
    }

    public void getWeatherForecasts(Context context, ForecastSource.ForecastRequestCallback callback) {
        source.getWeatherForecast(callback, context);
    }
}
