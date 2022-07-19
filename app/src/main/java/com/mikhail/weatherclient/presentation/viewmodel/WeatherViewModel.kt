package com.mikhail.weatherclient.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mikhail.weatherclient.data.WeatherToWeek
import com.mikhail.weatherclient.data.network.weatherapi.WeatherRepository
import com.mikhail.weatherclient.data.network.weatherapi.WeatherRepositoryImpl
import kotlinx.coroutines.runBlocking

class WeatherViewModel() : ViewModel() {

    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl()
    private val mutableLiveData:MutableLiveData<WeatherToWeek> = MutableLiveData()
    val weatherLiveData: LiveData<WeatherToWeek> get() = mutableLiveData

    init {
        mutableLiveData.value = getWeather("Moscow")
    }


    fun getWeather(cityname: String): WeatherToWeek {
        return runBlocking {
            try {
                weatherRepository.getWeather(
                    cityname
                )
            } catch (exception: Exception) {
                //isFirstDownload = true
                throw exception
            }
        }
    }

}