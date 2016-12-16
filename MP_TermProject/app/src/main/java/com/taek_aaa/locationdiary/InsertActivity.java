package com.taek_aaa.locationdiary;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.taek_aaa.locationdiary.DataSet.categoty_arr_index;
import static com.taek_aaa.locationdiary.DataSet.dbiter;
import static com.taek_aaa.locationdiary.DataSet.interval_Time;
import static com.taek_aaa.locationdiary.DataSet.itc;
import static com.taek_aaa.locationdiary.DataSet.iter;
import static com.taek_aaa.locationdiary.DataSet.latitudeDouble;
import static com.taek_aaa.locationdiary.DataSet.longitudeDouble;
import static com.taek_aaa.locationdiary.DataSet.sllDBData;
import static com.taek_aaa.locationdiary.DataSet.stoDoOrEvent;
import static com.taek_aaa.locationdiary.R.id.spinner;
import static java.lang.System.exit;

/**
 * 정보 입력하기 부분
 **/
public class InsertActivity extends Activity {

    MyLocationListener mll = null;
    SQLiteDatabase db;
    private LocationManager locationManager;
    long starttime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedtime = 0L;
    int secs = 0;
    int mins = 0;
    int hours = 0;
    Handler handler = new Handler();
    int t = 1;
    RadioButton checkBoxTodo = null;
    RadioButton checkBoxEvent = null;
    RadioGroup radioGroup = null;
    Button confirm = null;
    RadioButton selectedbtn = null;
    DBManager dbManager = new DBManager(this, "logger.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        iter = dbManager.getIter();

        TextView stopWatchtv = (TextView) findViewById(R.id.timerTextView);
        stopWatchtv.setText("00:00:00");
        listenerOnBtn();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        /** 카테고리를 선택하는 Spinner 부분 **/
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoty_arr_index = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * 현재 위치 검색 버튼을 눌렀을 때
     **/
    public void onClickCurrentLocation(View v) {
        getLocation();
    }

    /**
     * 현재 위치를 받는 메서드
     **/
    public void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mll = new MyLocationListener();
        //- SDK 23버전 이상 (마시멜로우 이상부터)부터는 아래 처럼 권한을 사용자가 직접 허가해주어야 GPS기능을 사용가능 GPS 기능을 사용하기전 위치에 추가해야함
        //체크 퍼미션
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(InsertActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        boolean isGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGps && !isNetwork) {
            new AlertDialog.Builder(InsertActivity.this)
                    .setMessage("GPS가 꺼져있습니다.\n ‘위치 서비스’에서 ‘Google 위치 서비스’를 체크해주세요")
                    .setPositiveButton("설정", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }

                    })
                    .setNegativeButton("취소", null).show();
            Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
        } else {
            if (isGps) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, interval_Time, 0, mll);
                Toast.makeText(this, "GPS로 좌표값을 가져옵니다", Toast.LENGTH_SHORT).show();
            } else if (isNetwork) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, interval_Time, 0, mll);  //3000 -> 3초
                Toast.makeText(this, "네트워크로 좌표값을 가져옵니다", Toast.LENGTH_SHORT).show();
            } else {
                exit(1);
            }
        }
    }

    /**
     * GPS에 대한 리스너
     **/
    public class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            String str = "Latitude: " + location.getLatitude() + "\n" + "Longitude: " + location.getLongitude() + "\n";
            TextView tv = (TextView) findViewById(R.id.showLocationtv);
            tv.setText(str);
            Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();

            latitudeDouble = location.getLatitude();
            longitudeDouble = location.getLongitude();

            if (ActivityCompat.checkSelfPermission(InsertActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(InsertActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(InsertActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            locationManager.removeUpdates(mll);


        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            new AlertDialog.Builder(InsertActivity.this)
                    .setMessage("GPS가 꺼져있습니다.\n ‘위치 서비스’에서 ‘Google 위치 서비스’를 체크해주세요")
                    .setPositiveButton("설정", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }

                    })
                    .setNegativeButton("취소", null).show();
            Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 타이머 START 버튼
     **/
    public void onClickTimerStartbtn(View v) {
        Button startbtn = (Button) findViewById(R.id.timerStartbtn);
        final TextView timerTv = (TextView) findViewById(R.id.timerTextView);
        try {
            if (stoDoOrEvent.equals("To Do")) {
                if (t == 1) {
                    startbtn.setText("Pause");
                    starttime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimer, 0);
                    t = 0;
                } else {
                    startbtn.setText("Start");
                    timerTv.setTextColor(Color.BLUE);
                    timeSwapBuff += timeInMilliseconds;
                    handler.removeCallbacks(updateTimer);
                    t = 1;
                }
            } else if (stoDoOrEvent.equals("Event")) {
                //startbtn.setText("");
                //startbtn.setEnabled(false);
                Log.e("event", "이벤트로 인식함");
                startbtn.setVisibility(View.INVISIBLE);

            }
        } catch (Exception e) {
            Toast.makeText(this, "확인 버튼을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 타이머 END 버튼
     **/
    public void onClickTimerEndbtn(View v) {
        String howlongtime;
        String shour;
        String sminute;
        String sseconds;
        int ihowlongtime;
        String strText;
        EditText textedit = (EditText) findViewById(R.id.TexteditText);
        TextView stopWatchtv = (TextView) findViewById(R.id.timerTextView);
        TextView tmptv = (TextView) findViewById(R.id.showLocationtv);
        howlongtime = stopWatchtv.getText().toString();
        shour = howlongtime.substring(0, 2);
        sminute = howlongtime.substring(3, 5);
        sseconds = howlongtime.substring(6);
        ihowlongtime = Integer.valueOf(shour) * 60 * 60 + Integer.valueOf(sminute) * 60 + Integer.valueOf(sseconds);

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/hh:mm");
        Log.e("ppq",""+df);
        Date clsTime = new Date();
        String resulttime = df.format(clsTime);

        strText = textedit.getText().toString();
        Spinner tmpspinner = (Spinner) findViewById(spinner);

        dbiter = dbManager.getIter();

        Log.i("test", String.valueOf(howlongtime));
        Button startbtn = (Button) findViewById(R.id.timerStartbtn);
        starttime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedtime = 0L;
        secs = 0;
        mins = 0;
        hours = 0;
        handler.removeCallbacks(updateTimer);
        stopWatchtv.setText("00:00:00");
        startbtn.setVisibility(View.VISIBLE);
        startbtn.setText("Start");
        t = 1;
        Log.i("test", "찍힘");
        sllDBData.clear();
        dbManager.getResult(sllDBData);
        try {
            iter = dbManager.getIter();
            dbManager.insert(latitudeDouble, longitudeDouble, stoDoOrEvent, categoty_arr_index, ihowlongtime, iter, strText, resulttime);
            Log.e("bbb", "" + itc.getIteration() + "insert다음");
            Log.e("value", "db에값을입력하였습니다");
            Log.e("value", "" + latitudeDouble);
            Log.e("value", "" + longitudeDouble);
            Log.e("value", "" + stoDoOrEvent);
            Log.e("value", "" + categoty_arr_index);
            Log.e("value", "" + ihowlongtime);
            Log.e("value", "" + String.valueOf(itc.getIteration()));
            Log.e("value", "" + strText);
            Log.e("value", "" + resulttime);

        } catch (Exception e) {
            Toast.makeText(this, "데이터 입력에 오류가 있습니다", Toast.LENGTH_SHORT).show();
        }
        textedit.setText("");
        tmpspinner.setSelection(0);
        tmptv.setText("");
        Toast.makeText(this, "DB에 정상입력 되었습니다", Toast.LENGTH_SHORT).show();


        itc.intcrease();
        Log.e("bbb", "" + itc.getIteration() + "itc increase하고난다음");
        iter = dbManager.getIter();
        Log.e("pp", "" + iter + "디비의 총 행");

    }

    /**
     * 타이머를 관리하는 쓰레드
     **/
    public Runnable updateTimer = new Runnable() {
        public void run() {
            final TextView timerTv = (TextView) findViewById(R.id.timerTextView);
            timeInMilliseconds = SystemClock.uptimeMillis() - starttime;
            updatedtime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedtime / 1000);
            mins = secs / 60;
            hours = mins / 60;
            secs = secs % 60;
            timerTv.setText("" + String.format("%02d", hours) + ":" + "" + String.format("%02d", mins) + ":" + String.format("%02d", secs));
            handler.postDelayed(this, 0);
        }
    };

    /**
     * 확인 버튼을 눌렀을 때 라디오 버튼의 값을 가져옴
     **/
    public void listenerOnBtn() {
        radioGroup = (RadioGroup) findViewById(R.id.radiobtnGroup);
        checkBoxTodo = (RadioButton) findViewById(R.id.checkToDo);
        checkBoxEvent = (RadioButton) findViewById(R.id.checkEvent);
        confirm = (Button) findViewById(R.id.confirmbtn);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radioGroup.getCheckedRadioButtonId();
                Log.i("test", String.valueOf(selected));
                selectedbtn = (RadioButton) findViewById(selected);
                Log.i("test", selectedbtn.getText().toString());
                stoDoOrEvent = selectedbtn.getText().toString();
            }
        });
    }

    /**
     * DB삭제 버튼을 눌렀을 때
     **/
    public void onClickClearDB(View v) {     //디비삭제
        db = dbManager.getWritableDatabase();
        AlertDialog.Builder adb = new AlertDialog.Builder(InsertActivity.this);

        adb
                .setTitle("경고")
                .setCancelable(false)
                .setMessage("정말 데이터베이스를 삭제하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbManager.onUpgrade(db, 1, 2);
                        dbManager.close();
                        itc.setZero();
                        Toast.makeText(InsertActivity.this, "DB를 삭제하였습니다", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog ad = adb.create();
        ad.show();
    }
}






