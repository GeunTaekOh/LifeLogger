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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import static com.taek_aaa.locationdiary.DataSet.interval_Time;
import static com.taek_aaa.locationdiary.DataSet.latitudeDouble;
import static com.taek_aaa.locationdiary.DataSet.llistCategory;
import static com.taek_aaa.locationdiary.DataSet.llistHowLong;
import static com.taek_aaa.locationdiary.DataSet.llistLatitude;
import static com.taek_aaa.locationdiary.DataSet.llistLocation;
import static com.taek_aaa.locationdiary.DataSet.llistLongitude;
import static com.taek_aaa.locationdiary.DataSet.llistNum;
import static com.taek_aaa.locationdiary.DataSet.llistText;
import static com.taek_aaa.locationdiary.DataSet.llistTime;
import static com.taek_aaa.locationdiary.DataSet.longitudeDouble;
import static java.lang.System.exit;

public class MainActivity extends Activity {

    final DBManager dbManager = new DBManager(this);
    ScrollView scroll;
    int iter = 0;
    MyLocationListener mll = null;
    SQLiteDatabase db;
    private long lastTimeBackPressed;
    private LocationManager locationManager;
    long starttime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedtime = 0L;
    int secs = 0;
    int mins = 0;
    int hours = 0;
    Handler handler = new Handler();
    int t=1;
    RadioButton checkBoxTodo =null;
    RadioButton checkBoxEvent = null;
    RadioGroup radioGroup = null;
    Button confirm = null;
    RadioButton selectedbtn =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scroll = (ScrollView) findViewById(R.id.scrollView);
        scroll.setVerticalScrollBarEnabled(true);
        TextView stopWatchtv = (TextView)findViewById(R.id.timerTextView);
        stopWatchtv.setText("00:00:00");

        listenerOnBtn();
        final Intent mapitt = new Intent(this, MapsActivity.class);
        Button mapbtn = (Button) findViewById(R.id.mapViewbtn);


        mapbtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(mapitt);
            }
        });

        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                llistCategory.add(iter,String.valueOf(position));
                Log.i("test","카테고리의"+position+"이셀렉되었습니다");

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });




    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed > 1500 && System.currentTimeMillis() - lastTimeBackPressed < 4500) {
            finish();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            locationManager.removeUpdates(mll);
            return;
        }
        Toast.makeText(MainActivity.this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

    public void onClickStopbtn(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        locationManager.removeUpdates(mll);
        Toast.makeText(MainActivity.this, "더이상 GPS 정보롤 받아오지 않습니다", Toast.LENGTH_SHORT).show();
    }

    public void onClickCurrentLocation(View v) {
        getLocation();
    }

    public void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mll = new MyLocationListener();
        //- SDK 23버전 이상 (마시멜로우 이상부터)부터는 아래 처럼 권한을 사용자가 직접 허가해주어야 GPS기능을 사용가능 GPS 기능을 사용하기전 위치에 추가해야함
        //체크 퍼미션
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        boolean isGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGps && !isNetwork) {
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("GPS가 꺼져있습니다.\n ‘위치 서비스’에서 ‘Google 위치 서비스’를 체크해주세요")
                    .setPositiveButton("설정", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                            //startActivityForResult(intent,);
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

    public class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            String str = "Latitude: " + location.getLatitude() + "\n" + "Longitude: " + location.getLongitude() + "\n";
            TextView tv = (TextView) findViewById(R.id.showLocationtv);
            tv.setText(str);//append를 setText로 바꿈
            Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();

            latitudeDouble = location.getLatitude();
            longitudeDouble = location.getLongitude();

            dbManager.insert(latitudeDouble, longitudeDouble,true,"",iter,"","","");
            Log.i("test",dbManager.toString());
            //Log.i("test",String.valueOf(re));
            llistLatitude.add(iter, latitudeDouble);
            llistLongitude.add(iter, longitudeDouble);
            llistLocation.add(iter, new LatLng(latitudeDouble,longitudeDouble));
            //llistisToDoorEvent.add(iter, re);
            llistCategory.add(iter, "");
            llistHowLong.add(iter, 0);
            llistNum.add(iter,iter+"");
            llistText.add(iter, "");
            llistTime.add(iter, "");
            iter++;

            Log.i("test", "디비저장성공");
           // dbManager.getResult();
            Toast.makeText(MainActivity.this, "DB에 입력 되었습니다.", Toast.LENGTH_SHORT).show();
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
            new AlertDialog.Builder(MainActivity.this)
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
    public void onClickTimerStartbtn(View v){
        Button startbtn = (Button) findViewById(R.id.timerStartbtn);
        final TextView timerTv = (TextView) findViewById(R.id.timerTextView);
        if(t==1) {
            startbtn.setText("Pause");
            starttime = SystemClock.uptimeMillis();
            handler.postDelayed(updateTimer, 0);
            t=0;
        }else{
            startbtn.setText("Start");
            timerTv.setTextColor(Color.BLUE);
            timeSwapBuff += timeInMilliseconds;
            handler.removeCallbacks(updateTimer);
            t=1;
        }
    }


    public void onClickTimerEndbtn(View v){
        String howlongtime;
        TextView stopWatchtv = (TextView)findViewById(R.id.timerTextView);
        howlongtime = stopWatchtv.getText().toString();
        //llistHowLong.add(iter,howlongtime);
        Log.i("test",String.valueOf(howlongtime));
        Button startbtn = (Button) findViewById(R.id.timerStartbtn);
        starttime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedtime = 0L;
        secs = 0;
        mins = 0;
        hours=0;
        handler.removeCallbacks(updateTimer);
        stopWatchtv.setText("00:00:00");
        startbtn.setText("Start");
        t=1;
        Log.i("test","찍힘");
    }

    public Runnable updateTimer = new Runnable() {
        public void run() {
            final TextView timerTv = (TextView) findViewById(R.id.timerTextView);
            timeInMilliseconds = SystemClock.uptimeMillis() - starttime;
            updatedtime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedtime / 1000);
            mins = secs / 60;
            hours = mins/60;
            secs = secs % 60;
            timerTv.setText(""+ String.format("%02d",hours)+":"+"" + String.format("%02d",mins) + ":" + String.format("%02d", secs));
            handler.postDelayed(this, 0);
        }
    };

    public void listenerOnBtn(){
        radioGroup = (RadioGroup)findViewById(R.id.radiobtnGroup);
        checkBoxTodo = (RadioButton) findViewById(R.id.checkToDo);
        checkBoxEvent = (RadioButton)findViewById(R.id.checkEvent);
        confirm = (Button)findViewById(R.id.confirmbtn);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radioGroup.getCheckedRadioButtonId();
                Log.i("test",String.valueOf(selected));
                selectedbtn = (RadioButton)findViewById(selected);
                Log.i("test",selectedbtn.getText().toString());
            }
        });
    }


}



