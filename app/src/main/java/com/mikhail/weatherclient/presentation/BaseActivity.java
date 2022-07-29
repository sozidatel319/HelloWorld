package com.mikhail.weatherclient.presentation;

import static com.mikhail.weatherclient.Constants.THEME;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mikhail.weatherclient.R;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String NameSharedPreference = "LOGIN";
    private static final String IsDarkTheme = "IS_DARK_THEME";
    private static final String IsLightTheme = "IS_LIGHT_THEME";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected boolean isDarkTheme() {
        SharedPreferences sharedPref = getSharedPreferences(THEME, MODE_PRIVATE);
        return sharedPref.getBoolean(IsDarkTheme, false);
    }
    public void setDarkTheme(boolean isDarkTheme) {
        SharedPreferences sharedPref = getSharedPreferences(THEME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IsDarkTheme, isDarkTheme);
        editor.apply();
    }

    protected void setLightTheme(boolean isDarkTheme) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(IsLightTheme, isDarkTheme);
        editor.apply();
    }


    protected void setCancelThemeChanges(){

    }

    public void recreateTheme(){
        recreate();
    }



}
