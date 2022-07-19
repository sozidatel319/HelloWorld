package com.mikhail.weatherclient.data.network.weatherapi

import android.content.Context
import android.content.SharedPreferences
import com.mikhail.weatherclient.App
import com.mikhail.weatherclient.data.Mapper.toWeatherToWeek
import com.mikhail.weatherclient.data.WeatherToWeek
import com.mikhail.weatherclient.model.WeatherModel

class WeatherRepositoryImpl : WeatherRepository {
    private val weatherApi: WeatherApiService =
        RetrofitInstance.getInstance().create(WeatherApiService::class.java)

    private val sharedPreferences: SharedPreferences =
        App.applicationContext().getSharedPreferences("now", Context.MODE_PRIVATE)

    override suspend fun getWeather(cityName: String): WeatherToWeek {
        try {
            return weatherApi.getWeather(
                cityName,
                "metric",
                "0507febdbdf6a636ec6bdcdfe0b909fc"
            ).toWeatherToWeek()
        } catch (exception: Exception) {
            throw exception
        }
    }

    override suspend fun getWeatherByCoords(lat: Float, lon: Float): WeatherModel? {
        try {
            return weatherApi.getWeatherByCoords(lat, lon, "0507febdbdf6a636ec6bdcdfe0b909fc")
        } catch (exception: Exception) {
            throw exception
        }
    }
}