
package com.mikhail.weatherclient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherModel {
    @SerializedName("list")
    @Expose
    private java.util.List<com.mikhail.weatherclient.model.List> list = null;
    //@SerializedName("coord")
    //@Expose
    //private Coord coord;
    //@SerializedName("weather")
    //@Expose
    // private List<Weather> weather = null;
    //@SerializedName("base")
    //@Expose
    //private String base;
    @SerializedName("main")
    @Expose
    private Main main;
    // @SerializedName("visibility")
    //@Expose
    //  private Integer visibility;
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @SerializedName("clouds")
    @Expose
    private Clouds clouds;
    @SerializedName("dt")
    @Expose
    private Integer dt;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @SerializedName("city")
    @Expose
    private City city;
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

    public java.util.List<com.mikhail.weatherclient.model.List> getList() {
        return list;
    }

    public void setList(java.util.List<com.mikhail.weatherclient.model.List> list) {
        this.list = list;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
/*

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }
*/

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }


    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Integer getDt() {
        return dt;
    }

    public void setDt(Integer dt) {
        this.dt = dt;
    }
/*
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