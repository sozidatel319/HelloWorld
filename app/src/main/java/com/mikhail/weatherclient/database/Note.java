package com.mikhail.weatherclient.database;

public class Note {
    private long id;
    private String cityname;
    private String temperature_today_cel;
    private String temperature_today_far;
    private String clouds;
    private String pressure;
    private String wind;
    private String datenow;

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getTemperature_today_cel() {
        return temperature_today_cel;
    }

    public void setTemperature_today_cel(String temperature_today_cel) {
        this.temperature_today_cel = temperature_today_cel;
    }

    public String getTemperature_today_far() {
        return temperature_today_far;
    }

    public void setTemperature_today_far(String temperature_today_far) {
        this.temperature_today_far = temperature_today_far;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getDatenow() {
        return datenow;
    }

    public void setDatenow(String datenow) {
        this.datenow = datenow;
    }
}
