package com.mikhail.weatherclient.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikhail.weatherclient.data.network.weatherapi.WeatherRepository
import com.mikhail.weatherclient.data.network.weatherapi.WeatherRepositoryImpl
import com.mikhail.weatherclient.data.shared_prefs.ThemeRepository
import com.mikhail.weatherclient.data.shared_prefs.ThemeRepositoryImpl
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl(),
    private val themeRepository: ThemeRepository = ThemeRepositoryImpl()
) : ViewModel() {

    private val isFahrenheitMutableLiveData = MutableLiveData<Boolean>()
    private val isDarkThemeMutableLiveData = MutableLiveData<Boolean>()

    val isFahrenheitLiveData: LiveData<Boolean> get() = isFahrenheitMutableLiveData
    val isDarkThemeLiveData: LiveData<Boolean> get() = isDarkThemeMutableLiveData


    init {
        viewModelScope.launch {
            val unitOfMeasure = weatherRepository.getUnitOfMeasure()
            isFahrenheitMutableLiveData.value = unitOfMeasure
            val isDarkTheme = themeRepository.getTheme()
            isDarkThemeMutableLiveData.value = isDarkTheme
        }
    }

    fun saveUnitOfMeasure(isFahrenheit: Boolean) {
        viewModelScope.launch {
            isFahrenheitMutableLiveData.value = isFahrenheit
            weatherRepository.setUnitOfMeasure(isFahrenheit)
        }
    }

    fun saveTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            themeRepository.saveTheme(isDarkTheme)
            isDarkThemeMutableLiveData.value = isDarkTheme
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}