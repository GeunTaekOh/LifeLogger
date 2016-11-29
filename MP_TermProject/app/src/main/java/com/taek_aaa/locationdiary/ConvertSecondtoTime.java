package com.taek_aaa.locationdiary;

/**
 * Created by taek_aaa on 2016. 11. 30..
 */

public class ConvertSecondtoTime {
    private String hour;
    private String minute;
    private String second;

    public void transferTime(int totalSecond) {
        hour = String.valueOf(totalSecond / (60 * 60));
        if (totalSecond / (60 * 60) == 0) {

        } else {
            totalSecond -= totalSecond / (60 * 60);
        }
        minute = String.valueOf(totalSecond / 60);
        second = String.valueOf(totalSecond % 60);
    }


    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getSecond() {
        return second;
    }


}
