package com.mikhail.weatherclient;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Constants extends AppCompatActivity {
    public static final String CITY_NAME = "cityname";
    public static final String INFO = "info";
    public static final String PRESSURE = "pressure";
    public static final String WIND_SPEED = "windspeed";
    public static final String THEME = "theme";
    public static final String LIGHT_THEME = "light_theme";
    public static final String DARK_THEME = "dark_theme";
    public static final String FONTSIZE = "fontsize";
    public static final String UNIT_OF_MEASURE_FAHRENHEIT = "unitofmeasure";
    public static final String USE_LOCATION = "use_location";
    public static final int CITYCHANGER_CODE = 7;
    public static final int SETTINGS_CODE = 90;
    public static final String CELSIUS = "°C";
    public static final String FAHRENHEIT = "°F";
    public static final int MAXIMUM_DAYS_IN_LIST = 6;

    public static void logAndToast(String text, String tag){
        Activity activity = new Activity();
        Log.d(tag, text);
        Toast.makeText(activity.getApplicationContext(),text + "_" + tag, Toast.LENGTH_LONG).show();
    }
}



