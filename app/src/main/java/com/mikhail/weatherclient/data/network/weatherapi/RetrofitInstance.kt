package com.mikhail.weatherclient.data.network.weatherapi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitInstance {

    private var INSTANCE: Retrofit? = null


    fun getInstance(): Retrofit = INSTANCE ?: kotlin.run {
        val interceptor = HttpLoggingInterceptor()
        val client = OkHttpClient()

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        client
            .newBuilder()
            .addInterceptor(interceptor)
            .build()


        Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }
}
