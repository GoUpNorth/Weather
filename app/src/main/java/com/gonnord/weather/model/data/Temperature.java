package com.gonnord.weather.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GONNORD_pierreantoine on 25/11/2017.
 */

public class Temperature implements Serializable{

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
}
