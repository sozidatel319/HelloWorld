package com.mikhail.weatherclient.model

import com.google.gson.annotations.Expose

data class City(

@Expose
private var id: Int? = null,

@Expose
private val name: String? = null,

@Expose
private val coord: Coord? = null,

@Expose
private val country: String? = null,

@Expose
private val timezone: Int? = null,

@Expose
private val sunrise: Int? = null,

@Expose
private val sunset: Int? = null
)
