package com.mikhail.weatherclient.data.network.weatherapi

import com.mikhail.weatherclient.data.network.weatherapi.mappers.WeatherResult
import com.mikhail.weatherclient.model.WeatherModel

interface WeatherRepository {

   suspend fun getWeather(cityName: String?): WeatherResult

   suspend fun getWeatherByCoords(lat: Float, lon: Float): WeatherModel?

   suspend fun getUnitOfMeasure(): Boolean

   suspend fun setUnitOfMeasure(isFahrenheit: Boolean)

   suspend fun getCurrentCityFromSharedPreference(): String

   suspend fun saveCurrentCityToSharedPreference(cityName: String)

   suspend fun saveNeedToShowAdditionalInfo(isShow: Boolean)

   suspend fun isShowAdditionalInfo():Boolean

}