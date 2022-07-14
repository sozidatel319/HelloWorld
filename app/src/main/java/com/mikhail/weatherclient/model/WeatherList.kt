package com.mikhail.weatherclient.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class WeatherList(
    @SerializedName("dt")
    @Expose
    var dt: Int? = null,

    @SerializedName("main")
    @Expose
    var main: Main? = null,

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
    var clouds: Clouds? = null,

    @Expose
    var wind: Wind? = null,

    /* @SerializedName("snow")
      @Expose
      private Snow snow;
      @SerializedName("sys")
      @Expose
      private Sys sys;*/
    @SerializedName("dt_txt")
    @Expose
    var dtTxt: String? = null
)