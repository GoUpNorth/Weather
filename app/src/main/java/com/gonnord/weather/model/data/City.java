package com.gonnord.weather.model.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by GONNORD_pierreantoine on 25/11/2017.
 */

public class City {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("coord")
    Coordinates coordinates;

    @SerializedName("country")
    String countryCode;

    @SerializedName("population")
    int population;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
