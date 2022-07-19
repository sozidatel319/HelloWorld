package com.mikhail.weatherclient.model

import com.google.gson.annotations.Expose

data class City(

@Expose
val id: Int? = null,

@Expose
val name: String,

@Expose
val coord: Coord? = null,

@Expose
val country: String? = null,

@Expose
val timezone: Int? = null,

@Expose
val sunrise: Int? = null,

@Expose
val sunset: Int? = null
)
