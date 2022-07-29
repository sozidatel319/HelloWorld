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

abstract class BaseViewModel : ViewModel() {

    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl()
    private val themeRepository: ThemeRepository = ThemeRepositoryImpl()

    private val isFahrenheitMutableLiveData = MutableLiveData<Boolean>()
    private val isDarkThemeMutableLiveData = MutableLiveData<Boolean>()

    val isFahrenheitLiveData: LiveData<Boolean> get() = isFahrenheitMutableLiveData
    val isDarkThemeLiveData: LiveData<Boolean> get() = isDarkThemeMutableLiveData

    init {
        getTheme()
    }


    fun getUnitOfMeasure() {
        viewModelScope.launch {
            isFahrenheitMutableLiveData.value = weatherRepository.getUnitOfMeasure()
        }
    }

    fun getTheme() {
        viewModelScope.launch {
            isDarkThemeMutableLiveData.value = themeRepository.getTheme()
        }
    }
}