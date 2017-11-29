package com.gonnord.weather.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by GONNORD_pierreantoine on 25/11/2017.
 */

public class Forecast implements Parcelable {

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


    private Forecast(Parcel in) {
        date = in.readLong();
        temperature = in.readParcelable(Temperature.class.getClassLoader());
        pressure = in.readDouble();
        humidity = in.readInt();
        weathers = in.createTypedArrayList(Weather.CREATOR);
        speed = in.readDouble();
        deg = in.readInt();
        clouds = in.readInt();
        rain = in.readDouble();
    }

    public static final Creator<Forecast> CREATOR = new Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel in) {
            return new Forecast(in);
        }

        @Override
        public Forecast[] newArray(int size) {
            return new Forecast[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date);
        dest.writeParcelable(temperature, flags);
        dest.writeDouble(pressure);
        dest.writeInt(humidity);
        dest.writeTypedList(weathers);
        dest.writeDouble(speed);
        dest.writeInt(deg);
        dest.writeInt(clouds);
        dest.writeDouble(rain);
    }
}
