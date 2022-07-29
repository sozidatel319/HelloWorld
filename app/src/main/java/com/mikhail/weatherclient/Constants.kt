package com.mikhail.weatherclient

import android.app.Activity
import android.util.Log
import android.widget.Toast

object Constants {
    const val NOW = "now"
    const val CITY_NAME = "cityname"
    const val INFO = "info"
    const val PRESSURE = "pressure"
    const val WIND_SPEED = "windspeed"
    const val THEME = "theme"
    const val LIGHT_THEME = "light_theme"
    const val DARK_THEME = "dark_theme"
    const val FONTSIZE = "fontsize"
    const val UNIT_OF_MEASURE_FAHRENHEIT = "unitofmeasure"
    const val USE_LOCATION = "use_location"
    const val CITYCHANGER_CODE = 7
    const val SETTINGS_CODE = 90
    const val CELSIUS = "°C"
    const val FAHRENHEIT = "°F"
    const val MAXIMUM_DAYS_IN_LIST = 6
    const val RESPONSE_UNIT_OF_MEASURE_CELSIUS = "metric"
    const val RESPONSE_UNIT_OF_MEASURE_FAHRENHEIT = "imperial"
    const val MOSCOW = "MOSCOW"
    fun logAndToast(text: String, tag: String) {
        val activity = Activity()
        Log.d(tag, text)
        Toast.makeText(activity.applicationContext, text + "_" + tag, Toast.LENGTH_LONG).show()
    }
}