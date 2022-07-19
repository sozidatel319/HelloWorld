package com.mikhail.weatherclient;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceWrapper {
    private static PreferenceWrapper instance;
    private SharedPreferences sharedPreferences;

    public static PreferenceWrapper getPreference(Context context) {
        instance = instance == null ? new PreferenceWrapper(context) : instance;
        return instance;
    }

    PreferenceWrapper(Context context) {
        sharedPreferences = context.getSharedPreferences("now", Context.MODE_PRIVATE);
        //City_changerPresenter.getInstance().setCityName(sharedPreferences.getString(Constants.CITY_NAME,"Moscow"));
    }

    public String getString(String keyname, String defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(keyname, defValue);
        }
        return null;
    }

    public boolean getBoolean(String keyname, Boolean defValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getBoolean(keyname, defValue);
        }
        return false;
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    public void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).commit();
    }
}
