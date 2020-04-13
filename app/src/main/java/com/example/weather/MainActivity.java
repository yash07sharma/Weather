package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public double Lat = 0;
    public double Long = 0;
    public String LatLng;
    public String currentKey;
    public FusedLocationProviderClient mFusedLocationClient;
    public int PERMISSION_ID = 44;
    public ImageView backgroundImage;
    public String temp_details;
    public String temp_unitDetails;
    public String temp_cityDetails;
    public String temp_country;
    public String temp_weather_type;

    public TextView tempDetails;
    public TextView unitDetails;
    public TextView cityDetails;
    public TextView countryDetails;
    public TextView weatherType;
    public ImageView weatherIcon;
    public Context context;
    public int currentWeatherIconCode;
    public int[] backgroundImageList = {R.drawable.sunrise, R.drawable.night};
    public Client client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this.context;
        backgroundImage = findViewById(R.id.background_image);
        tempDetails = findViewById(R.id.temperature_details);
        unitDetails = findViewById(R.id.unit_details);
        cityDetails = findViewById(R.id.city_details);
        countryDetails = findViewById(R.id.country_details);
        weatherIcon = findViewById(R.id.weather_icon);
        weatherType = findViewById(R.id.weather_type);
        backgroundImage.setTag(1);
        backgroundImage.setImageResource((backgroundImageList[0]));
        client=new Client(MainActivity.this);
        /*backgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage();
            }
        });*/
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }
    public void changeImageToNight() {
        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        backgroundImage.setImageResource((R.drawable.night));
                        backgroundImage.setTag(1);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .repeat(0)
                                .playOn(backgroundImage);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(backgroundImage);
    }
    public void changeImageToDay() {
        YoYo.with(Techniques.FadeOut)
                .duration(500)
                .repeat(0)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        backgroundImage.setBackground(getDrawable(R.drawable.sunrise));
                        backgroundImage.setTag(1);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .repeat(0)
                                .playOn(backgroundImage);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(backgroundImage);
    }

//    private void setCity() {
//        Retrofit retrofitName = new Retrofit.Builder()
//                .baseUrl("http://dataservice.accuweather.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        JsonPlaceHolderApiName jsonPlaceHolderApiName = retrofitName.create(JsonPlaceHolderApiName.class);
//        Call<WeatherLocation> callName = jsonPlaceHolderApiName.getWeatherLocation(JsonPlaceHolderApiName.API_KEY, LatLng,
//                JsonPlaceHolderApiName.language, JsonPlaceHolderApiName.details, JsonPlaceHolderApiName.toplevel
//        );
//
//        callName.enqueue(new Callback<WeatherLocation>() {
//            @Override
//            public void onResponse(Call<WeatherLocation> call, Response<WeatherLocation> response) {
//                WeatherLocation locationData = response.body();
//                temp_cityDetails=(locationData.getName());
//                temp_country=(locationData.getWeatherCountryName());
//                currentKey = locationData.getKey();
//                setTemp();
//            }
//
//            @Override
//            public void onFailure(Call<WeatherLocation> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Error shown", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

//    private void setTemp() {
//        Retrofit retrofitTemp = new Retrofit.Builder()
//                .baseUrl("http://dataservice.accuweather.com/currentconditions/v1/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        JsonPlaceHolderApiWeather jsonPlaceHolderApiWeather = retrofitTemp.create(JsonPlaceHolderApiWeather.class);
//        Call<ArrayList<WeatherDetails>> callWeather = jsonPlaceHolderApiWeather.getWeatherDetails(currentKey, JsonPlaceHolderApiWeather.API_KEY, JsonPlaceHolderApiWeather.language, JsonPlaceHolderApiWeather.
//                details);
//        callWeather.enqueue(new Callback<ArrayList<WeatherDetails>>() {
//            @Override
//            public void onResponse(Call<ArrayList<WeatherDetails>> call, Response<ArrayList<WeatherDetails>> response) {
//                ArrayList<WeatherDetails> details = response.body();
//                for (WeatherDetails weatherDetails : details) {
//                    temp_details=(weatherDetails.getTemperatureDetails().getMetric().getTemp().toString());
//                    temp_unitDetails=(" °"+weatherDetails.getTemperatureDetails().getMetric().getUnit());
//                    temp_weather_type=(weatherDetails.getWeatherType());
//                    currentWeatherIconCode = weatherDetails.getWeatherIconCode();
//                    if(weatherDetails.isIs_day()==true)
//                    {
//                        changeImageToDay();
//                        setImage();
//                    }
//                    else
//                    {
//                        changeImageToNight();
//                        setImage();
//                    }
//                    //String imagetoSet
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<WeatherDetails>> call, Throwable t) {
//
//            }
//        });
//    }

    public void setImage() {
        String imagetoSet;
        if (currentWeatherIconCode >= 10)
            imagetoSet = "https://developer.accuweather.com/sites/default/files/" + currentWeatherIconCode + "-s" + ".png";
        else
            imagetoSet = "https://developer.accuweather.com/sites/default/files/0" + currentWeatherIconCode + "-s" + ".png";
        Picasso.get().load(imagetoSet).resize(weatherIcon.getWidth(),weatherIcon.getHeight()).centerCrop().into(weatherIcon);
        weatherIcon.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .repeat(0)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        cityDetails.setText(temp_cityDetails);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(cityDetails);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .repeat(0)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        tempDetails.setText(temp_details);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(tempDetails);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .repeat(0)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        unitDetails.setText(temp_unitDetails);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(unitDetails);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .repeat(0)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        countryDetails.setText(temp_country);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(countryDetails);
        YoYo.with(Techniques.FadeIn)
                .duration(1000)
                .repeat(0)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        weatherType.setText(temp_weather_type);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .playOn(weatherType);
    }

    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                (this),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    Lat = location.getLatitude();
                                    Long = location.getLongitude();
                                    LatLng = Lat + "," + Long;
                                    client.setCity();
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            Lat = mLastLocation.getLatitude();
            Long = mLastLocation.getLongitude();
            LatLng = Lat + "," + Long;
            client.setCity();
        }
    };

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }
}
