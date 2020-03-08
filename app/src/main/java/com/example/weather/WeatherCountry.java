package com.example.weather;

import com.google.gson.annotations.SerializedName;

public class WeatherCountry {
    @SerializedName("LocalizedName")
    public String countryName;

    public WeatherCountry() {
        countryName = "NULL";
    }

    public WeatherCountry(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }
}

