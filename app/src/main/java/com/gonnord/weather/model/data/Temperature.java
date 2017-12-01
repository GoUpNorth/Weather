package com.gonnord.weather.model.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

/**
 * Created by GONNORD_pierreantoine on 25/11/2017.
 */

public class Temperature implements Parcelable{

    private static final String TAG = Temperature.class.getSimpleName();

    @SerializedName("day")
    double dayTemp;

    @SerializedName("min")
    double minTemp;

    @SerializedName("max")
    double maxTemp;

    @SerializedName("night")
    double nightTemp;

    @SerializedName("eve")
    double eveningTemp;

    @SerializedName("morn")
    double morningTemp;

    private Temperature(Parcel in) {
        dayTemp = in.readDouble();
        minTemp = in.readDouble();
        maxTemp = in.readDouble();
        nightTemp = in.readDouble();
        eveningTemp = in.readDouble();
        morningTemp = in.readDouble();
    }

    public static final Creator<Temperature> CREATOR = new Creator<Temperature>() {
        @Override
        public Temperature createFromParcel(Parcel in) {
            return new Temperature(in);
        }

        @Override
        public Temperature[] newArray(int size) {
            return new Temperature[size];
        }
    };

    public double getDayTemp() {
        return dayTemp;
    }

    public void setDayTemp(double dayTemp) {
        this.dayTemp = dayTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getNightTemp() {
        return nightTemp;
    }

    public void setNightTemp(double nightTemp) {
        this.nightTemp = nightTemp;
    }

    public double getEveningTemp() {
        return eveningTemp;
    }

    public void setEveningTemp(double eveningTemp) {
        this.eveningTemp = eveningTemp;
    }

    public double getMorningTemp() {
        return morningTemp;
    }

    public void setMorningTemp(double morningTemp) {
        this.morningTemp = morningTemp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        try {
            dest.writeDouble(dayTemp);
            dest.writeDouble(minTemp);
            dest.writeDouble(maxTemp);
            dest.writeDouble(nightTemp);
            dest.writeDouble(eveningTemp);
            dest.writeDouble(morningTemp);
        } catch (Exception e) {
            Log.e(TAG, "Write to parcel failed: "+ e.getMessage(),e);
        }
    }
}
