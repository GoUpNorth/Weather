package com.gonnord.weather.utils;

/**
 * Created by pierre-antoinegonnord on 28/11/2017.
 */

public enum MeasurementSystem {

    METRIC("metric"),
    IMPERIAL("imperial");

    String code;

    MeasurementSystem(String code) {
        this.code = code;
    }

    public String getCode() {
            return code;
        }

    public boolean getSwitchToggle() {
        return this.equals(IMPERIAL);
    }
}
