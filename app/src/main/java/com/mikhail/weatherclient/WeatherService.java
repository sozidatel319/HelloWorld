package com.mikhail.weatherclient;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.mikhail.weatherclient.model.WeatherModel;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherService extends Service {
    WeatherModel model;

    @Override
    public int onStartCommand(@Nullable final Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                model = null;
                while (!Thread.interrupted()) {
                    try {
                        model = getWeather(intent.getStringExtra(Constants.CITY_NAME));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    interface OpenWeather {
        @GET("data/2.5/forecast")
        Call<WeatherModel> getWeather(@Query("q") String q, @Query("appid") String key);
    }

    private WeatherModel getWeather(String cityname) throws Exception {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<WeatherModel> call = retrofit.create(OpenWeather.class).getWeather(cityname + ",ru", "0507febdbdf6a636ec6bdcdfe0b909fc");
        Response<WeatherModel> response = call.execute();
        if (response.isSuccessful()) return response.body();
        else throw new Exception(response.errorBody().string(), null);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
