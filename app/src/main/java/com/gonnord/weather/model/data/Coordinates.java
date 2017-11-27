package com.gonnord.weather.model.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by GONNORD_pierreantoine on 25/11/2017.
 */

public class Coordinates {

    @SerializedName("lon")
    double longitude;

    @SerializedName("lat")
    double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
