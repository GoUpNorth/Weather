package com.gonnord.weather.model;

import com.gonnord.weather.network.OpenWeatherMapForecastSource;

/**
 * Created by GONNORD_pierreantoine on 26/11/2017.
 */

public class ForecastProviderInjection {

    public ForecastSource getSource() {
        return OpenWeatherMapForecastSource.get();
    }
}
