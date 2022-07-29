package com.mikhail.weatherclient.data.network.weatherapi.mappers

import com.mikhail.weatherclient.data.WeatherToWeek

sealed class WeatherResult {

    class Error(
        val cod: Int,
        val message: String?
    ) : WeatherResult()

    class Success(val data: WeatherToWeek) : WeatherResult()
}



