package com.mikhail.weatherclient.domain

import com.mikhail.weatherclient.data.WeatherToWeek
import com.mikhail.weatherclient.data.network.weatherapi.WeatherRepository
import com.mikhail.weatherclient.model.WeatherModel

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {

    suspend fun getWeatherForCurrentCity(cityName: String): WeatherToWeek {
        return weatherRepository.getWeather(cityName)
    }

    suspend fun getWeatherByCoords(lat: Float, lon: Float): WeatherModel? {
        return weatherRepository.getWeatherByCoords(lat, lon)
    }
}