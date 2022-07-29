package com.mikhail.weatherclient.data.network.weatherapi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitInstance {

    private var INSTANCE: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .build()

        INSTANCE = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }


    fun getInstance(): Retrofit = INSTANCE

}
