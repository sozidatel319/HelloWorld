package com.mikhail.weatherclient.data

data class WeatherToWeek(
    val city: String,
    val country: String,
    val firstDay: List<PartOfDay> = emptyList(),
    val weatherWeekList: List<WeatherDay>
)