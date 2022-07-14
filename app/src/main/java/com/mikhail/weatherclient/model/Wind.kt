package com.mikhail.weatherclient.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Wind(
    //  public Integer getDeg() {
    //@SerializedName("deg")
    //@Expose
    //private Integer deg;
    @SerializedName("speed")
    @Expose
    var speed: Double? = null
    //      return deg;
    //  }
    //  public void setDeg(Integer deg) {
    //      this.deg = deg;
    //  }
)