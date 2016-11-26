package com.taek_aaa.locationdiary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by taek_aaa on 2016. 11. 26..
 */

public class MainActivity extends Activity{
    private long lastTimeBackPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Button inputBtn = (Button)findViewById(R.id.inputbtn);
        Button mapBtn = (Button)findViewById(R.id.mapbtn);
        Button staticBtn = (Button)findViewById(R.id.staticbtn);
        Button goalBtn = (Button)findViewById(R.id.goalbtn);

        setContentView(R.layout.activity_main);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.inputbtn:
                startActivity(new Intent(this,InsertActivity.class));
                break;
            case R.id.mapbtn:
                startActivity(new Intent(this,MapsActivity.class));
                break;
            case R.id.staticbtn:
                break;
            case R.id.goalbtn:
                break;
            case R.id.exitbtn:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed > 1500 && System.currentTimeMillis() - lastTimeBackPressed < 4500) {
            finish();
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
            //locationManager.removeUpdates(mll);
            return;
        }
        Toast.makeText(MainActivity.this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }

}
