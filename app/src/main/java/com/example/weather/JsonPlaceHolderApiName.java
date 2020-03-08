package com.example.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderApiName {
    String API_KEY = "HRf61nAA4wjxvHtZe5rMF83c3k4O40Iz";
    String language = "en-us";
    boolean details = false;
    boolean toplevel = false;

    @GET("locations/v1/cities/geoposition/search")
    Call<WeatherLocation> getWeatherLocation(@Query("apikey") String apikey, @Query("q") String LatLng, @Query("language") String language,
                                             @Query("details") boolean details, @Query("toplevel") boolean toplevel
    );
}
