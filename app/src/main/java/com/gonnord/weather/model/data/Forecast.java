package com.gonnord.weather.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by GONNORD_pierreantoine on 25/11/2017.
 */

public class Forecast implements Serializable {

    @SerializedName("dt")
    long date;

    @SerializedName("temp")
    Temperature temperature;

    @SerializedName("pressure")
    double pressure;

    @SerializedName("humidity")
    int humidity;

    @SerializedName("weather")
    List<Weather> weathers;

    @SerializedName("speed")
    double speed;

    @SerializedName("deg")
    int deg;

    @SerializedName("clouds")
    int clouds;

    @SerializedName("rain")
    double rain;

    public long getDate() {
        return date;
    }

    public Date getDateObject() {
        return new Date(date * 1000L);
    }


    public void setDate(long date) {
        this.date = date;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public List<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(List<Weather> weathers) {
        this.weathers = weathers;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }
}
