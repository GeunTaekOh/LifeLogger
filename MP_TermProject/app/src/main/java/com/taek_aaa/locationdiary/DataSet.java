package com.taek_aaa.locationdiary;

import java.util.LinkedList;

/**
 * Created by taek_aaa on 2016. 11. 22..
 */

public class DataSet {
    public static double latitudeDouble;
    public static double longitudeDouble;
    public static String stoDoOrEvent;
    public static int categoty_arr_index;
    public static int mainCategory_arr_index;
    public static int subCategory_arr_index;
    final static int interval_Time = 1000 * 60 * 20;
    public static String[] category_arr = {"공부", "식사", "카페", "이동", "수업", "친구", "휴식"};
    public static int iter;
    public static int dbiter = 0;
    public static LinkedList<DBData> sllDBData = new LinkedList<DBData>();
    public static IterationClass itc = new IterationClass();
    public static int moveCameraIter=0;

}
