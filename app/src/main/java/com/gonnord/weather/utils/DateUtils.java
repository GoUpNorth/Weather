package com.gonnord.weather.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by GONNORD_pierreantoine on 27/11/2017.
 */

public class DateUtils {

    public static String getReadableDate(Date date) {
        DateFormat format = new SimpleDateFormat("EEEE dd MMM", Locale.UK);
        return format.format(date);
    }
}
