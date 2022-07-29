package com.mikhail.weatherclient.data.network.weatherapi

import android.content.Context
import android.content.SharedPreferences
import com.mikhail.weatherclient.App
import com.mikhail.weatherclient.Constants.CITY_NAME
import com.mikhail.weatherclient.Constants.INFO
import com.mikhail.weatherclient.Constants.MOSCOW
import com.mikhail.weatherclient.Constants.NOW
import com.mikhail.weatherclient.Constants.RESPONSE_UNIT_OF_MEASURE_CELSIUS
import com.mikhail.weatherclient.Constants.RESPONSE_UNIT_OF_MEASURE_FAHRENHEIT
import com.mikhail.weatherclient.Constants.UNIT_OF_MEASURE_FAHRENHEIT
import com.mikhail.weatherclient.data.Mapper.toWeatherToWeek
import com.mikhail.weatherclient.data.network.weatherapi.mappers.WeatherResult
import com.mikhail.weatherclient.model.WeatherModel
import retrofit2.Response

class WeatherRepositoryImpl : WeatherRepository {
    private val weatherApi: WeatherApiService =
        RetrofitInstance.getInstance().create(WeatherApiService::class.java)

    private val sharedPreferences: SharedPreferences =
        App.applicationContext().getSharedPreferences(NOW, Context.MODE_PRIVATE)

    override suspend fun getWeather(cityName: String?): WeatherResult {

        val response: Response<WeatherModel> = weatherApi.getWeather(
            q = if (cityName.isNullOrBlank()) {
                getCurrentCityFromSharedPreference()
            } else {
                cityName
            },
            measure = if (!getUnitOfMeasure()) {
                RESPONSE_UNIT_OF_MEASURE_CELSIUS
            } else {
                RESPONSE_UNIT_OF_MEASURE_FAHRENHEIT
            }
        )
        return if (response.isSuccessful) {
            WeatherResult.Success(response.body()?.toWeatherToWeek())
        } else {
            val message = response.raw()
            WeatherResult.Error(message.code, message.message)
        }
    }


    override suspend fun getWeatherByCoords(lat: Float, lon: Float): WeatherModel? {
        try {
            return weatherApi.getWeatherByCoords(lat, lon)
        } catch (exception: Exception) {
            throw exception
        }
    }

    override suspend fun getUnitOfMeasure(): Boolean {
        return sharedPreferences.getBoolean(UNIT_OF_MEASURE_FAHRENHEIT, false)
    }

    override suspend fun setUnitOfMeasure(isFahrenheit: Boolean) {
        sharedPreferences.edit().putBoolean(UNIT_OF_MEASURE_FAHRENHEIT, isFahrenheit).apply()
    }

    override suspend fun getCurrentCityFromSharedPreference(): String {
        return sharedPreferences.getString(CITY_NAME, MOSCOW) ?: MOSCOW
    }

    override suspend fun saveCurrentCityToSharedPreference(cityName: String) {
        sharedPreferences.edit().putString(CITY_NAME, cityName).apply()
    }

    override suspend fun saveNeedToShowAdditionalInfo(isShow: Boolean) {
        sharedPreferences.edit().putBoolean(INFO, isShow).apply()
    }

    override suspend fun isShowAdditionalInfo(): Boolean {
        return sharedPreferences.getBoolean(INFO, false)
    }

}