package com.taek_aaa.locationdiary;
import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;

/**
 * Created by taek_aaa on 2016. 11. 22..
 */

public class DataSet {

    public static double latitudeDouble;
    public static double longitudeDouble;
    final static int interval_Time = 1000 * 60 * 3;

    public static LinkedList<Double> llistLatitude = new LinkedList<Double>();
    public static LinkedList<Double> llistLongitude = new LinkedList<Double>();
    public static LinkedList<LatLng> llistLocation = new LinkedList<LatLng>();
    public static LinkedList<Boolean> llistisToDoorEvent = new LinkedList<Boolean>();
    public static LinkedList<String> llistCategory = new LinkedList<String>();
    public static LinkedList<Integer> llistHowLong = new LinkedList<Integer>();
    public static LinkedList<String> llistNum = new LinkedList<String>();
    public static LinkedList<String> llistText = new LinkedList<String>();
    public static LinkedList<String> llistTime = new LinkedList<String>();

    public static String[] category_arr = {"공부", "식사", "카페", "이동", "수업", "친구", "휴식"};
    static int iter = 0;

}
