package com.example.weather;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    public MainActivity mainActivity;

    public Client(MainActivity mainActivity)
    {
        this.mainActivity=mainActivity;
    }

    public void setCity() {
        Retrofit retrofitName = new Retrofit.Builder()
                .baseUrl("http://dataservice.accuweather.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApiName jsonPlaceHolderApiName = retrofitName.create(JsonPlaceHolderApiName.class);
        Call<WeatherLocation> callName = jsonPlaceHolderApiName.getWeatherLocation(JsonPlaceHolderApiName.API_KEY, mainActivity.LatLng,
                JsonPlaceHolderApiName.language, JsonPlaceHolderApiName.details, JsonPlaceHolderApiName.toplevel
        );

        callName.enqueue(new Callback<WeatherLocation>() {
            @Override
            public void onResponse(Call<WeatherLocation> call, Response<WeatherLocation> response) {
                WeatherLocation locationData = response.body();
                mainActivity.temp_cityDetails=(locationData.getName());
                mainActivity.temp_country=(locationData.getWeatherCountryName());
                mainActivity.currentKey = locationData.getKey();
                setTemp();
            }

            @Override
            public void onFailure(Call<WeatherLocation> call, Throwable t) {
                Toast.makeText(mainActivity.context, "Error shown", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void setTemp() {
        Retrofit retrofitTemp = new Retrofit.Builder()
                .baseUrl("http://dataservice.accuweather.com/currentconditions/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApiWeather jsonPlaceHolderApiWeather = retrofitTemp.create(JsonPlaceHolderApiWeather.class);
        Call<ArrayList<WeatherDetails>> callWeather = jsonPlaceHolderApiWeather.getWeatherDetails(mainActivity.currentKey, JsonPlaceHolderApiWeather.API_KEY, JsonPlaceHolderApiWeather.language, JsonPlaceHolderApiWeather.
                details);
        callWeather.enqueue(new Callback<ArrayList<WeatherDetails>>() {
            @Override
            public void onResponse(Call<ArrayList<WeatherDetails>> call, Response<ArrayList<WeatherDetails>> response) {
                ArrayList<WeatherDetails> details = response.body();
                for (WeatherDetails weatherDetails : details) {
                    mainActivity.temp_details=(weatherDetails.getTemperatureDetails().getMetric().getTemp().toString());
                    mainActivity.temp_unitDetails=(" °"+weatherDetails.getTemperatureDetails().getMetric().getUnit());
                    mainActivity.temp_weather_type=(weatherDetails.getWeatherType());
                    mainActivity.currentWeatherIconCode = weatherDetails.getWeatherIconCode();
                    if(weatherDetails.isIs_day()==true)
                    {
                        mainActivity.changeImageToDay();
                        mainActivity.setImage();
                    }
                    else
                    {
                        mainActivity.changeImageToNight();
                        mainActivity.setImage();
                    }
                    //String imagetoSet
                }
            }

            @Override
            public void onFailure(Call<ArrayList<WeatherDetails>> call, Throwable t) {

            }
        });
    }
}
