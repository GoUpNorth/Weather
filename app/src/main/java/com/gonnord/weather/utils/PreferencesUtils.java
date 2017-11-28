package com.gonnord.weather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public class PreferencesUtils {

    private static final String PREFERENCE_UNIT_SYSTEM = "PREFERENCE_UNIT_SYSTEM";

    private static final String PREFERENCE_FORECAST_COUNT_REQUEST = "PREFERENCE_FORECAST_COUNT_REQUEST";


    public enum UnitSystem {
        METRIC,
        IMPERIAL
    }

    public static UnitSystem getUnitSystem(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String pref = prefs.getString(PREFERENCE_UNIT_SYSTEM, UnitSystem.METRIC.toString());
        UnitSystem unit;
        try {
            unit = UnitSystem.valueOf(pref);
        } catch (IllegalArgumentException e) {
            Log.e(PreferencesUtils.class.getSimpleName(), e.getMessage(), e);
            unit = UnitSystem.METRIC;
        }
        return unit;
    }

    public static int getForecastCount(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(PREFERENCE_FORECAST_COUNT_REQUEST, 5);
    }
}
