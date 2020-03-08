package com.example.weather;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface JsonPlaceHolderApiWeather {
    String API_KEY = "HRf61nAA4wjxvHtZe5rMF83c3k4O40Iz";
    String language = "en-us";
    String details = "false";

    @GET
    public Call<ArrayList<WeatherDetails>> getWeatherDetails(@Url String url, @Query("apikey") String apikey, @Query("language") String language, @Query("details")
            String details);
}
