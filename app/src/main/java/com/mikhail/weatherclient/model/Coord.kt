package com.mikhail.weatherclient.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Coord(
    @SerializedName("lon")
    @Expose
    var lon: Double? = null,

    @SerializedName("lat")
    @Expose
    var lat: Double? = null
)