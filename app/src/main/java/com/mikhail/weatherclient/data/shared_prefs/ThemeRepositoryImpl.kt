package com.mikhail.weatherclient.data.shared_prefs

import android.content.Context
import android.content.SharedPreferences
import com.mikhail.weatherclient.App
import com.mikhail.weatherclient.Constants.THEME

class ThemeRepositoryImpl : ThemeRepository {
    private val sharedPreferences: SharedPreferences =
        App.applicationContext().getSharedPreferences(THEME, Context.MODE_PRIVATE)

    private val isDark = "IS_DARK_THEME"

    override suspend fun saveTheme(isDarkTheme: Boolean) {
        sharedPreferences.edit().putBoolean(isDark, isDarkTheme).apply()
    }

    override suspend fun getTheme():Boolean {
        return sharedPreferences.getBoolean(isDark, false)
    }
}