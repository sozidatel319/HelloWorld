package com.mikhail.weatherclient;

import com.mikhail.weatherclient.model.WeatherModel;
import java.util.ArrayList;

public interface WeatherProviderListener {

    void updateWeather(WeatherModel model, ArrayList<String> time);
}
