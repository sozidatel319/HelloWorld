package com.mikhail.weatherclient.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "weathertoweek.db";
    private static final int DB_VERSION = 2;
    public static final String TABLE_ID = "ID";
    public static final String TABLE_NAME = "WEATHER_NOW";
    public static final String TABLE_TITLE = "TABLE_WEATHER";
    public static final String CITY_NAME = "CITY_NAME";
    public static final String TEMPERATURE = "TEMPERATURE";
    public static final String CLOUDS = "CLOUDS";
    public static final String PRESSURE = "PRESSURE";
    public static final String WIND = "WIND";
    public static final String DATE = "DATE";
    public static final String TEMPERATURE_TODAY_CEL = "TODAY_TEMP_CEL";
    public static final String TEMPERATURE_TODAY_FAR = "TODAY_TEMP_FAR";
   /* public static final String TABLE_NAME_TEMPERATURE = "TEMPERATURE";
    public static final String TABLE_TITLE_TEMPERATURE = "TABLE_TEMPERATURE";
    public static final String TEMPERATURE_2_CEL = "DAY_2_CEL";
    public static final String TEMPERATURE_2_FAR = "DAY_2_FAR";
    public static final String TEMPERATURE_3_CEL = "DAY_3_CEL";
    public static final String TEMPERATURE_3_FAR = "DAY_3_FAR";
    public static final String TEMPERATURE_4_CEL = "DAY_4_CEL";
    public static final String TEMPERATURE_4_FAR = "DAY_4_FAR";*/

    public DataHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CITY_NAME + " TEXT, " + TEMPERATURE_TODAY_CEL + " TEXT, " + TEMPERATURE_TODAY_FAR
                + " TEXT, " + CLOUDS + " TEXT, "+ PRESSURE + " TEXT, " + WIND + " TEXT, " + DATE + " TEXT" + ");");
        /*db.execSQL("CREATE TABLE " + TABLE_NAME_TEMPERATURE + " (" +
                TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TEMPERATURE_TODAY_CEL + " TEXT, " + TEMPERATURE_TODAY_FAR + " TEXT, "
                + TEMPERATURE_2_CEL + " TEXT, " + TEMPERATURE_2_FAR + " TEXT, " + TEMPERATURE_3_CEL + " TEXT, " + TEMPERATURE_3_FAR + " TEXT, "
                + TEMPERATURE_4_CEL + " TEXT, " + TEMPERATURE_4_FAR + " TEXT" + ");");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            String upgradeStr = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + TABLE_TITLE;
            db.execSQL(upgradeStr);

        }
    }
}
