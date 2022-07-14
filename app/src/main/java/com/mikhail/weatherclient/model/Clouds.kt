package com.mikhail.weatherclient.model

import com.google.gson.annotations.Expose

data class Clouds (
    @Expose
    var all: Int? = null)