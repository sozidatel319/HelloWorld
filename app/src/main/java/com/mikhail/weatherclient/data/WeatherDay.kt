package com.mikhail.weatherclient.data

data class WeatherDay(
    var currentDay: Int = 0,
    val wind: Int = 0,
    val pressure: Int = 0,
    val clouds: Int = 0,
    val minTemp: Int = 0,
    val maxTemp: Int = 0
)