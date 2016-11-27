package com.taek_aaa.locationdiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLng;
import static com.taek_aaa.locationdiary.DataSet.category_arr;
import static com.taek_aaa.locationdiary.DataSet.iter;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    DBManager dbManager = new DBManager(this, "logger.db", null, 1);
    private GoogleMap mMap;
    Spinner spinner;
    String type_str = "";
    EditText editText;
    LinearLayout type_ll;
    static String outermemo;
    static int temp;
    LinkedList<DBData> sllDBData = new LinkedList<DBData>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        MapsInitializer.initialize(getApplicationContext());
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        sllDBData.clear();
        dbManager.getResult(sllDBData);
        mMap = googleMap;

        int count = Integer.parseInt(sllDBData.getLast().curNum )+1;

        for(int i=0; i< iter;i++){
            Log.e("value", String.valueOf(count));
            Log.e("value",String.valueOf(sllDBData.get(i).curlatitude));
            Log.e("value",String.valueOf(sllDBData.get(i).curlongitude));
            Log.e("value",sllDBData.get(i).curTodoOrEvent);
            Log.e("value",String.valueOf(sllDBData.get(i).curCategory));
            Log.e("value",String.valueOf(sllDBData.get(i).curHowLong));
            Log.e("value",sllDBData.get(i).curNum);
            Log.e("value",sllDBData.get(i).curText);
            Log.e("value",sllDBData.get(i).curTime);

        }



        for (int i = 0; i < iter; i++) {
            MarkerOptions opt = new MarkerOptions();
            opt.position(new LatLng(sllDBData.get(i).curlatitude, sllDBData.get(i).curlongitude));
            opt.title(sllDBData.get(i).curNum);
            opt.snippet(sllDBData.get(i).curText + "@" + sllDBData.get(i).curTime);

            mMap.addMarker(opt).showInfoWindow();
            if (i != 0) {
                mMap.addPolyline(new PolylineOptions().geodesic(true).add(new LatLng(Double.valueOf(sllDBData.get(i - 1).curlatitude), Double.valueOf(sllDBData.get(i - 1).curlongitude)), new LatLng(Double.valueOf(sllDBData.get(i).curlatitude), Double.valueOf(sllDBData.get(i).curlongitude))).width(5).color(Color.RED));
            }
            Log.e("value","한바퀴돔");
        }
        mMap.moveCamera(newLatLng(new LatLng(sllDBData.get(0).curlatitude, sllDBData.get(0).curlongitude)));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                AlertDialog.Builder adb = new AlertDialog.Builder(MapsActivity.this);
                type_ll = new LinearLayout(MapsActivity.this);
                //setSpinner();
                //setEditText();
                //type_ll.addView(spinner);
                //type_ll.addView(editText);
                //type_ll.setPadding(50, 0, 0, 0);
                type_ll.setPadding(0, 0, 0, 0);
                int a = Integer.valueOf(marker.getTitle());
                temp = a;
                adb
                        .setTitle("정보창")
                        .setCancelable(false)
                        .setMessage("종류 : "+sllDBData.get(a).curTodoOrEvent+"\n"+"카테고리 : "+category_arr[sllDBData.get(a).curCategory]+"\n"+"소요시간 : "+sllDBData.get(a).curHowLong+"초"+"\n"+""+"내용 : "+sllDBData.get(a).curText+"\n"+"시간 : "+sllDBData.get(a).curTime)
                        .setView(type_ll)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setNeutralButton("사진 추가",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                                intent.setType("image*//*");
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("사진 촬영", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction("android.media.action.IMAGE_CAPTURE");
                                startActivity(intent);
                            }
                        });
                AlertDialog ad = adb.create();
                ad.show();
            }

        });
    }

    public void setSpinner() {
        spinner = new Spinner(this);
        ArrayAdapter memoAdapter = new ArrayAdapter(MapsActivity.this, android.R.layout.simple_spinner_item, category_arr);
        memoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(memoAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_str = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setEditText() {
        editText = new EditText(this);
        editText.setHint("메모를 입력하세요.");
        editText.setHintTextColor(0x50000000);
        editText.setEms(12);
    }
}
