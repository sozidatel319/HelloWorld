package com.mikhail.weatherclient.data.network.weatherapi

import com.mikhail.weatherclient.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("data/2.5/forecast")
    suspend fun getWeather(
        @Query("q") q: String?,
        @Query("units") measure: String = "metric",
        @Query("appid") key: String = "0507febdbdf6a636ec6bdcdfe0b909fc"
    ): WeatherModel

    @GET("data/2.5/forecast")
    suspend fun getWeatherByCoords(
        @Query("lat") lat: Float?,
        @Query("lon") lon: Float?,
        @Query("appid") key: String?
    ): WeatherModel
}