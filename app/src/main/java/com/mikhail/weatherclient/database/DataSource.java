package com.mikhail.weatherclient.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

public class DataSource implements Closeable {

    private final DataHelper dbHelper;
    private SQLiteDatabase database;
    private DataReader reader;

    public DataSource(Context context) {
        dbHelper = new DataHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        reader = new DataReader(database);
        reader.open();
    }

    @Override
    public void close() throws IOException {
        database.close();
        reader.close();
    }

    public DataReader getReader() {
        return reader;
    }

    public Note add(String cityname, String temperature_today_cel, String temperature_today_far, String clouds, String pressure, String wind, String date) {
        Note note = new Note();
        ContentValues values = new ContentValues();
        values.put(DataHelper.CITY_NAME, cityname);
        values.put(DataHelper.TEMPERATURE_TODAY_CEL, temperature_today_cel);
        values.put(DataHelper.TEMPERATURE_TODAY_FAR, temperature_today_far);
        values.put(DataHelper.CLOUDS, clouds);
        values.put(DataHelper.PRESSURE, pressure);
        values.put(DataHelper.WIND, wind);
        values.put(DataHelper.DATE, date);
        long id = database.insert(DataHelper.TABLE_NAME, null, values);
        note.setId(id);
        note.setCityname(cityname);
        note.setTemperature_today_cel(temperature_today_cel);
        note.setTemperature_today_far(temperature_today_far);
        note.setClouds(clouds);
        note.setPressure(pressure);
        note.setWind(wind);
        note.setDatenow(date);
        return note;
    }

    public void edit(Note note, String cityname, String temperature_today_cel, String temperature_today_far, String clouds, String pressure, String wind, String date) {

        ContentValues values = new ContentValues();
        values.put(DataHelper.CITY_NAME, cityname);
        values.put(DataHelper.TEMPERATURE_TODAY_CEL, temperature_today_cel);
        values.put(DataHelper.TEMPERATURE_TODAY_FAR, temperature_today_far);
        values.put(DataHelper.CLOUDS, clouds);
        values.put(DataHelper.PRESSURE, pressure);
        values.put(DataHelper.WIND, wind);
        values.put(DataHelper.TABLE_ID, note.getId());
        values.put(DataHelper.DATE, date);
        database.update(DataHelper.TABLE_NAME, values, DataHelper.TABLE_ID + "=" + note.getId(), null);
    }

    public void delete(Note note) {
        database.delete(DataHelper.TABLE_NAME, DataHelper.TABLE_ID + "=" + note.getId(), null);
    }

    public void deleteAll() {
        database.delete(DataHelper.TABLE_NAME, null, null);
    }
}
