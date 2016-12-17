package com.taek_aaa.locationdiary;

import java.util.LinkedList;

/**
 * Created by taek_aaa on 2016. 11. 22..
 */


/** static 변수들 **/
public class DataSet {
    public static double latitudeDouble;        //위도값
    public static double longitudeDouble;       //경도값
    public static String stoDoOrEvent;          //Todo나 event값
    public static String biggerOrSmaller;       //목표 설정에서 이상이나 이하 값
    public static int categoty_arr_index;       //정보입력 부분에서 spinner로 선택받은 index
    public static int mainCategory_arr_index;    //통계 부분에서 첫번째 spinner로 선택받은 index
    public static int subCategory_arr_index;    //통계 부분에서 두번째 spinner로 선택받은 index
    final static int interval_Time = 1000 * 60 * 20;    //GPS를 받는 주기
    public static String[] category_arr = {"공부", "식사", "카페", "이동", "수업", "친구", "휴식"};       //카테고리
    public static int iter;             //iter값을 임시로 저장하는 변수
    public static int dbiter = 0;       //dbiter값을 임시로 저장하는 변수
    public static LinkedList<DBData> sllDBData = new LinkedList<DBData>();      //DBDate객체로 LinkedList생성
    public static IterationClass itc = new IterationClass();            //IterationClass를 어디서돈 사용할 수 있게 함
    public static int moveCameraIter=0;                         //카메라가 몇바퀴 도는 지에 대한 값
    public static Boolean isUpdate=false;           //DB에서 중간삭제를 한 여부 값 (중간 삭제를 할 시에 전체 for문 삭제한 수만큼 줄여야함)
    public static int categoty_arr_index2;          //목표설정에 있는 spinner의 index
    public static String goalString="";             //목표 설정에서 입력한 목표
    public static int goalStartYear;                //목표설정에서 사용하는 시작하는 년
    public static int goalStartMonth;               //목표설정에서 사용하는 시작하는 월
    public static int goalStartDate;                //목표설정에서 사용하는 시작하는 일
    public static int goalEndYear;                  //목표설정에서 사용하는 끝나는 년
    public static int goalEndMonth;                 //목표설정에서 사용하는 끝나는 월
    public static int goalEndDate;                  //목표설정에서 사용하는 끝나는 일
    public static String goalTime = "";             //목표설정에서 설정한 입력한 기준시간
}
