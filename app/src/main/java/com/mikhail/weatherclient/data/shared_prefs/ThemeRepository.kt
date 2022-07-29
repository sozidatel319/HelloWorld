package com.mikhail.weatherclient.data.shared_prefs

interface ThemeRepository {

    suspend fun saveTheme(isDarkTheme: Boolean)

    suspend fun getTheme(): Boolean
}