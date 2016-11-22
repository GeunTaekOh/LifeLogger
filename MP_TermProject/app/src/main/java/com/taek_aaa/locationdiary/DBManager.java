package com.taek_aaa.locationdiary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {
    public static double curlatitude;
    public static double curlongitude;

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 Table 생성
        db.execSQL("CREATE TABLE gpstable (id INTEGER PRIMARY KEY AUTOINCREMENT, latitude double , longitude double);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists gpstable;");
    }

    public void insert(double latitude, double longitude) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO gpstable VALUES(NULL, " + latitude + ", " + longitude + ");");  //string넣을때는 '' 하고그안에""해야
        db.close();
    }

    public void getResult() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM gpstable", null);
       // c = cursor;
        while (cursor.moveToNext()) {
            double latitudecur = cursor.getDouble(cursor.getColumnIndex("latitude"));
            double longitudecur = cursor.getDouble(cursor.getColumnIndex("longitude"));
            Log.i("SQLDB ", "select : " + "(Latitude" + latitudecur + ")(Longitude:" + longitudecur + ")");
            curlatitude = latitudecur;
            curlongitude = longitudecur;
        }
        cursor.close();
        db.close();
    }
}
