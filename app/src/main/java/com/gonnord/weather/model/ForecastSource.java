package com.gonnord.weather.model;

import android.content.Context;

import com.gonnord.weather.model.data.Response;

/**
 * Created by GONNORD_pierreantoine on 26/11/2017.
 */

public abstract class ForecastSource {

    public interface ForecastRequestCallback {
        void onSuccess(Response response);

        void onFailure(Throwable throwable);

        void onNetworkFailure();
    }

    public abstract void getWeatherForecast(ForecastRequestCallback callback, Context context);

    public abstract void cancelForecastRequests();
}
