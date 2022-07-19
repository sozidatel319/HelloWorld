package com.mikhail.weatherclient.data

data class PartOfDay(
    val day:Int,
    val time: Int,
    val wind:Int,
    val pressure:Int,
    val clouds: Int,
    val temp: Int,
    val tempMin: Int,
    val tempMax: Int
)
