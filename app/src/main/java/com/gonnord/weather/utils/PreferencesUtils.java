package com.gonnord.weather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public class PreferencesUtils {

    private static final String PREFERENCE_UNIT_SYSTEM = "PREFERENCE_UNIT_SYSTEM";

    public static MeasurementSystem getMeasurementSystem(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String pref = prefs.getString(PREFERENCE_UNIT_SYSTEM, MeasurementSystem.METRIC.toString());
        MeasurementSystem unit;
        try {
            unit = MeasurementSystem.valueOf(pref);
        } catch (IllegalArgumentException e) {
            Log.e(PreferencesUtils.class.getSimpleName(), e.getMessage(), e);
            unit = MeasurementSystem.METRIC;
        }
        return unit;
    }

    public static void setMeasurementSystem(Context context, @NonNull MeasurementSystem system) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(PREFERENCE_UNIT_SYSTEM, system.toString());
        editor.apply();
        Log.i(PreferencesUtils.class.getSimpleName(), "Measurement system pref updated:" + system.toString());
    }
}
