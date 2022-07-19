package com.mikhail.weatherclient.presentation;

public final class City_changerPresenter {
    private String cityName;
    private String cityNameByLocation;
    private Float lon;
    private Float lat;
    private boolean infoisChecked;
    private boolean useloctation;

    public boolean isUseloctation() {
        return useloctation;
    }

    public void setUseloctation(boolean useloctation) {
        this.useloctation = useloctation;
    }

    private String wind;
    private String pressure;
    private volatile int mistake = 0;
    private boolean isOpened = false;

    private static City_changerPresenter instance;

    public static synchronized City_changerPresenter getInstance() {
        instance = instance == null ? new City_changerPresenter() : instance;
        return instance;
    }

    public String getCityNameByLocation() {
        return cityNameByLocation;
    }

    public void setCityNameByLocation(String cityNameByLocation) {
        this.cityNameByLocation = cityNameByLocation;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public boolean getOpened(){
        return isOpened;
    }

    public synchronized int getMistake() {
        return mistake;
    }

    public synchronized void setMistake(int mistake) {
        this.mistake = mistake;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public boolean getInfoisChecked() {
        return infoisChecked;
    }

    public void setInfoisChecked(boolean infoisChecked) {
        this.infoisChecked = infoisChecked;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

}
