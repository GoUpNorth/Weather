package com.gonnord.weather.model;

/**
 * Created by GONNORD_pierreantoine on 26/11/2017.
 */

public class ForecastProviderInjection {

    public ForecastSource getSource() {
        return MockedForecastSource.get();
    }
}
