package com.gonnord.weather.utils;

import android.content.Context;

import com.gonnord.weather.R;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public class WindUtils {

    public static String getWindDescriptionBySpeed(Context context, double speed) {
        if(speed < 0) {
            return "";
        }

        if(speed >= 0 && speed < 0.3) {
            return context.getResources().getString(R.string.beaufort_level_0);
        } else if(speed >= 0.3 && speed < 1.5) {
            return context.getResources().getString(R.string.beaufort_level_1);
        } else if(speed >= 1.5 && speed < 3.3) {
            return context.getResources().getString(R.string.beaufort_level_2);
        } else if(speed >= 3.3 && speed < 5.5) {
            return context.getResources().getString(R.string.beaufort_level_3);
        } else if(speed >= 5.5 && speed < 7.9) {
            return context.getResources().getString(R.string.beaufort_level_4);
        } else if(speed >= 7.9 && speed < 10.7) {
            return context.getResources().getString(R.string.beaufort_level_5);
        } else if(speed >= 10.7 && speed < 13.8) {
            return context.getResources().getString(R.string.beaufort_level_6);
        } else if(speed >= 13.8 && speed < 17.1) {
            return context.getResources().getString(R.string.beaufort_level_7);
        } else if(speed >= 17.1 && speed < 20.7) {
            return context.getResources().getString(R.string.beaufort_level_8);
        } else if(speed >= 20.7 && speed < 24.4) {
            return context.getResources().getString(R.string.beaufort_level_9);
        } else if(speed >= 24.4 && speed < 28.4) {
            return context.getResources().getString(R.string.beaufort_level_10);
        } else if(speed >= 28.4 && speed < 32.6) {
            return context.getResources().getString(R.string.beaufort_level_11);
        }

        return context.getResources().getString(R.string.beaufort_level_12);
    }

    public static String getWindOrientationByDegree(Context context, double degree) {
        if( degree > 360 || degree < 0) {
            return "";
        }

        if (degree > 348.75 || degree < 11.25) {
            return context.getResources().getString(R.string.wind_orientation_n);
        } else if (degree >= 11.25 && degree < 33.75) {
            return context.getResources().getString(R.string.wind_orientation_nne);
        } else if (degree >= 33.75 && degree < 56.25) {
            return context.getResources().getString(R.string.wind_orientation_ne);
        } else if (degree >= 56.25 && degree < 78.75) {
            return context.getResources().getString(R.string.wind_orientation_ene);
        } else if (degree >= 78.75 && degree < 101.25) {
            return context.getResources().getString(R.string.wind_orientation_e);
        } else if (degree >= 101.25 && degree < 123.75) {
            return context.getResources().getString(R.string.wind_orientation_ese);
        } else if (degree >= 123.75 && degree < 146.25) {
            return context.getResources().getString(R.string.wind_orientation_se);
        } else if (degree >= 146.25 && degree < 168.75) {
            return context.getResources().getString(R.string.wind_orientation_sse);
        } else if (degree >= 168.75 && degree < 191.25) {
            return context.getResources().getString(R.string.wind_orientation_s);
        } else if (degree >= 191.25 && degree < 213.75) {
            return context.getResources().getString(R.string.wind_orientation_ssw);
        } else if (degree >= 213.75 && degree < 236.25) {
            return context.getResources().getString(R.string.wind_orientation_sw);
        } else if (degree >= 236.25 && degree < 258.75) {
            return context.getResources().getString(R.string.wind_orientation_wsw);
        } else if (degree >= 258.75 && degree < 281.25) {
            return context.getResources().getString(R.string.wind_orientation_w);
        } else if (degree >= 281.25 && degree < 303.75) {
            return context.getResources().getString(R.string.wind_orientation_wnw);
        } else if (degree >= 303.75 && degree < 326.25) {
            return context.getResources().getString(R.string.wind_orientation_nw);
        }

        return context.getResources().getString(R.string.wind_orientation_nnw);
    }
}
