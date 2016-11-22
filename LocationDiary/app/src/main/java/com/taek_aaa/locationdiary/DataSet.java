package com.taek_aaa.locationdiary;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

/**
 * Created by taek_aaa on 2016. 11. 22..
 */

public class DataSet {
    public static ArrayList<Double> alistLatitude = new ArrayList<Double>();
    public static ArrayList<Double> alistLongitude = new ArrayList<Double>();
    public static ArrayList<LatLng> alistLocation = new ArrayList<LatLng>();
    public static ArrayList<String> alistTodo = new ArrayList<String>();
    public static ArrayList<String> alistText = new ArrayList<String>();
    public static ArrayList<String> alistTime = new ArrayList<String>();
    public static ArrayList<String> alistCategory = new ArrayList<String>();
    public static String[] category_arr = {"공부", "식사", "카페", "산책"};

}
