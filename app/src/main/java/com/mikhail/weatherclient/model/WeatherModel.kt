package com.mikhail.weatherclient.model

import com.google.gson.annotations.Expose

data class WeatherModel(
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
    var list: List<WeatherList>? = ArrayList(),

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
    private var dt: Int? = null)
{

    fun getCity(): City? {
        return city
    }

    fun setCity(city: City?) {
        this.city = city
    }

    @Expose
    private var city: City? = null

    /*fun setList(list: kotlin.collections.List<List>?) {
        field = list
    }*/

    fun getMain(): Main? {
        return main
    }

    fun setMain(main: Main?) {
        this.main = main
    }

    /*

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }
*/
    fun getWind(): Wind? {
        return wind
    }

    fun setWind(wind: Wind?) {
        this.wind = wind
    }

    fun getClouds(): Clouds? {
        return clouds
    }

    fun setClouds(clouds: Clouds?) {
        this.clouds = clouds
    }

    fun getDt(): Int? {
        return dt
    }

    fun setDt(dt: Int?) {
        this.dt = dt
    } /*
    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }
*/
}