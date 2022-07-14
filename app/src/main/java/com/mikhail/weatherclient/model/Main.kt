package com.mikhail.weatherclient.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Main(
    @Expose
    var temp: Double = 0.0,

    @Expose
    var pressure: Int = 0,

    @Expose
    var humidity: Int = 0,

    @Expose
    @SerializedName("temp_min")
    var tempMin: Double = 0.0,

    @Expose
    @SerializedName("temp_max")
    var tempMax: Double = 0.0
)