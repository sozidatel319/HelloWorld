package com.mikhail.weatherclient.model

import com.google.gson.annotations.Expose

data class WeatherModel(
    val cod: String,
    val message: String,
    //@SerializedName("sys")
    // @Expose
    // private Sys sys;
    //@SerializedName("timezone")
    //@Expose
    //private Integer timezone;
    //@SerializedName("id")
    // @Expose
    // private Integer id;
    //@SerializedName("name")
    // @Expose
    // private String name;
    //  @SerializedName("cod")
    // @Expose
    //  private Integer cod;
    /*public Coord getCoord() {
              return coord;
          }
      
          public void setCoord(Coord coord) {
              this.coord = coord;
          }
          public java.util.List<com.example.helloworld.model.List> getList() {
          return list;
          }
      
          public void setList(java.util.List<com.example.helloworld.model.List> list) {
          this.list = list;
          }
          public List<Weather> getWeather() {
              return weather;
          }
      
          public void setWeather(List<Weather> weather) {
              this.weather = weather;
          }
      
          public String getBase() {
              return base;
          }
      
          public void setBase(String base) {
              this.base = base;
          }*/
    @Expose
    var list: List<WeatherList> = ArrayList(),

    //@SerializedName("coord")
    //@Expose
    //private Coord coord;
    //@SerializedName("weather")
    //@Expose
    // private List<Weather> weather = null;
    //@SerializedName("base")
    //@Expose
    //private String base;
    @Expose
    private var main: Main? = null,

    // @SerializedName("visibility")
    //@Expose
    //  private Integer visibility;
    @Expose
    private var wind: Wind? = null,

    @Expose
    private var clouds: Clouds? = null,

    @Expose
    private var dt: Int? = null,

    @Expose var city: City,

   // val country
)

