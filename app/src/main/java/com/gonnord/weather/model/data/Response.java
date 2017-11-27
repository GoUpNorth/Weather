package com.gonnord.weather.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GONNORD_pierreantoine on 25/11/2017.
 */

public class Response {

    @SerializedName("city")
    City city;

    @SerializedName("cod")
    String cod;

    @SerializedName("message")
    double message;

    @SerializedName("cnt")
    int count;

    @SerializedName("list")
    List<Forecast> forecasts;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
