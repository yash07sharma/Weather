package com.example.weather;

import com.google.gson.annotations.SerializedName;

public class WeatherLocation {
    @SerializedName("LocalizedName")
    private String name;
    @SerializedName("Country")
    WeatherCountry weatherCountry;
    @SerializedName("Key")
    private String key;

    public WeatherLocation() {
        name = "NULL";
        key = "0";

    }

    public WeatherLocation(String name, String key, WeatherCountry weatherCountry) {
        this.name = name;
        this.weatherCountry = weatherCountry;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getWeatherCountryName() {
        return this.weatherCountry.getCountryName();
    }
}
