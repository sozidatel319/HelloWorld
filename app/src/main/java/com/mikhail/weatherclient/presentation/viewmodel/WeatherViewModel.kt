package com.mikhail.weatherclient.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mikhail.weatherclient.data.WeatherToWeek
import com.mikhail.weatherclient.data.network.weatherapi.WeatherRepository
import com.mikhail.weatherclient.data.network.weatherapi.WeatherRepositoryImpl
import com.mikhail.weatherclient.data.network.weatherapi.mappers.WeatherResult
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl()
) : BaseViewModel() {

    private val mutableWeatherToWeekLiveData: MutableLiveData<WeatherToWeek> = MutableLiveData()
    private val mutableResponseErrorLiveData: MutableLiveData<String> = MutableLiveData()
    private val needToShowAdditionalInfoMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()


    val weatherLiveData: LiveData<WeatherToWeek> get() = mutableWeatherToWeekLiveData
    val responseErrorLiveData get() = mutableResponseErrorLiveData
    val needToShowAdditionalInfo get() = needToShowAdditionalInfoMutableLiveData

    init {
        getWeather()
        getUnitOfMeasure()
    }

    fun getWeather(cityName: String? = null) {

        viewModelScope.launch {
            try {
                when (val response = weatherRepository.getWeather(cityName)) {
                    is WeatherResult.Success -> {

                        mutableWeatherToWeekLiveData.value = response.data

                        cityName?.let {
                            if (it.isBlank()) return@launch
                            weatherRepository.saveCurrentCityToSharedPreference(it)
                        }
                    }
                    is WeatherResult.Error -> {
                        mutableResponseErrorLiveData.value = "${response.cod} ${response.message}"}
                    }
            } catch (exception: Exception) {
                if (exception is CancellableContinuation<*>) {
                    throw exception
                }
            }
        }
    }
}