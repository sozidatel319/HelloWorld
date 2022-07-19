package com.mikhail.weatherclient.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class WeatherList(
    @SerializedName("dt")
    @Expose
    var dt: Int,

    @SerializedName("main")
    @Expose
    var main: Main,

    /* public java.util.List<Weather> getWeather() {
          return weather;
      }
  
      public void setWeather(java.util.List<Weather> weather) {
          this.weather = weather;
      }*/
    //  @SerializedName("weather")
    //    @Expose
    //  private java.util.List<Weather> weather = null;
    @Expose
    var clouds: Clouds,

    @Expose
    var wind: Wind,

    /* @SerializedName("snow")
      @Expose
      private Snow snow;
      @SerializedName("sys")
      @Expose
      private Sys sys;*/
    @SerializedName("dt_txt")
    @Expose
    var dtTxt: String
)