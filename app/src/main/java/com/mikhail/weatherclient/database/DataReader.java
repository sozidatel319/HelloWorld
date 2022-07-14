package com.mikhail.weatherclient.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Closeable;
import java.io.IOException;

public class DataReader implements Closeable {
    private final SQLiteDatabase database;
    private Cursor cursor;
    private String [] allColumn = {
            DataHelper.TABLE_ID,
            DataHelper.CITY_NAME,
            DataHelper.TEMPERATURE_TODAY_CEL,
            DataHelper.TEMPERATURE_TODAY_FAR,
            DataHelper.CLOUDS,
            DataHelper.PRESSURE,
            DataHelper.WIND,
            DataHelper.DATE
    };

    public DataReader(SQLiteDatabase database) {
    this.database = database;
    }

    public void open(){
        query();
        cursor.moveToFirst();
    }

    public void query(){
        cursor = database.query(DataHelper.TABLE_NAME, allColumn, null,null, null, null, null);
    }

    public void refresh(){
        int pos = cursor.getPosition();
        query();
        cursor.moveToPosition(pos);
    }

    private Note cursorToNote(){
        Note note = new Note();
        note.setId(cursor.getLong(0));
        note.setCityname(cursor.getString(1));
        note.setTemperature_today_cel(cursor.getString(2));
        note.setTemperature_today_far(cursor.getString(3));
        note.setClouds(cursor.getString(4));
        note.setPressure(cursor.getString(5));
        note.setWind(cursor.getString(6));
        note.setDatenow(cursor.getString(7));
        return note;
    }

    public Note getPosition(int position){
        cursor.moveToPosition(position);
        return cursorToNote();
    }

    public int getCount(){
        return cursor.getCount();
    }

    @Override
    public void close() throws IOException {

    }
}
