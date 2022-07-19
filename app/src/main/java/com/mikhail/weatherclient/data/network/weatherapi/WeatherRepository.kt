package com.mikhail.weatherclient.data.network.weatherapi

import com.mikhail.weatherclient.data.WeatherToWeek
import com.mikhail.weatherclient.model.WeatherModel

interface WeatherRepository {

   suspend fun getWeather(cityName: String): WeatherToWeek

   suspend fun getWeatherByCoords(lat: Float, lon: Float): WeatherModel?

}