package com.mikhail.weatherclient

import android.app.Application
import android.content.Context
import com.mikhail.weatherclient.App.Companion.applicationContext

class App : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: Application? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any

        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context: Context = applicationContext()
    }
}