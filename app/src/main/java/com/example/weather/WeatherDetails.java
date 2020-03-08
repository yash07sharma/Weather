package com.example.weather;

import com.google.gson.annotations.SerializedName;

public class WeatherDetails {
    @SerializedName("WeatherText")
    private String weatherType;
    @SerializedName("HasPrecipitation")
    private boolean is_raining;
    @SerializedName("IsDayTime")
    private boolean is_day;
    @SerializedName("Temperature")
    private TemperatureDetails temperatureDetails;
    @SerializedName("WeatherIcon")
    private int weatherIconCode;

    public WeatherDetails(String type, boolean is_raining, boolean is_day, TemperatureDetails temperatureDetails, int weatherIconCode) {
        this.weatherType = type;
        this.is_raining = is_raining;
        this.is_day = is_day;
        this.temperatureDetails = temperatureDetails;
        this.weatherIconCode = weatherIconCode;
    }

    public int getWeatherIconCode() {
        return weatherIconCode;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public boolean isIs_raining() {
        return is_raining;
    }

    public boolean isIs_day() {
        return is_day;
    }

    public TemperatureDetails getTemperatureDetails() {
        return temperatureDetails;
    }
}
