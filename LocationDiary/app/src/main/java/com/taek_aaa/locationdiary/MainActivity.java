package com.taek_aaa.locationdiary;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import static java.lang.System.exit;

public class MainActivity extends Activity {

    final static int interval_Time = 1000 * 60 * 3;
    public static double latitudeDouble;
    public static double longitudeDouble;
    public static ArrayList<Double> alistLatitude = null;
    public static ArrayList<Double> alistLongitude = null;
    public static ArrayList<LatLng> alistLocation = null;
    public static ArrayList<String> alistTodo = null;
    public static ArrayList<String> alistText = null;
    public static ArrayList<String> alistTime = null;
    public static ArrayList<String> alistCategory = null;
    final DBManager dbManager = new DBManager(this, "GPS.db", null, 1);
    ScrollView scroll;
    TextView mDisplayDbEt;
    int iter = 0;
    MyLocationListener mll = null;
    Location mLocation;
    SQLiteDatabase db;
    private long lastTimeBackPressed;
    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDisplayDbEt = (TextView) findViewById(R.id.dbtv);          //이값들을 스태틱 클래스로하나만들기
        alistLatitude = new ArrayList<Double>();
        alistLongitude = new ArrayList<Double>();
        alistLocation = new ArrayList<LatLng>();
        alistTodo = new ArrayList<String>();
        alistText = new ArrayList<String>();
        alistTime = new ArrayList<String>();
        alistCategory = new ArrayList<String>();
        scroll = (ScrollView) findViewById(R.id.scrollview);
        scroll.setVerticalScrollBarEnabled(true);

        final Intent mapitt = new Intent(this, MapsActivity.class);
        Button mapbtn = (Button) findViewById(R.id.viewMapbtn);
        getLocation();

        mapbtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                startActivity(mapitt);
            }
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

    public void onClick(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        locationManager.removeUpdates(mll);
        Toast.makeText(MainActivity.this, "더이상 GPS 정보롤 받아오지 않습니다", Toast.LENGTH_SHORT).show();
    }

    public void onClickLocationbtn(View v) {
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
            if (isNetwork) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, interval_Time, 0, mll);
                Toast.makeText(this, "GPS로 좌표값을 가져옵니다", Toast.LENGTH_SHORT).show();
            } else if (isGps) {
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
            mLocation = location;
            String str = "Latitude: " + location.getLatitude() + "\n" + "Longitude: " + location.getLongitude() + "\n";
            TextView tv = (TextView) findViewById(R.id.textview);
            tv.append(str);
            Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();

            latitudeDouble = location.getLatitude();
            longitudeDouble = location.getLongitude();

            dbManager.insert(latitudeDouble, longitudeDouble);

            alistLatitude.add(iter, latitudeDouble);
            alistLongitude.add(iter, longitudeDouble);
            alistLocation.add(iter, new LatLng(alistLatitude.get(iter), alistLongitude.get(iter)));
            alistTodo.add(iter, iter + "");
            alistText.add(iter, "");
            alistTime.add(iter, "");
            alistCategory.add(iter, "");
            iter++;
            Log.i("저장", "성공");
            dbManager.getResult();
            Toast.makeText(MainActivity.this, "DB에 입력 되었습니다.", Toast.LENGTH_SHORT).show();
            mLocation = location;
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
}



