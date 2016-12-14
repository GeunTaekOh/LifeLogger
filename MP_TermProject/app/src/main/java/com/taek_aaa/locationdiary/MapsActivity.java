package com.taek_aaa.locationdiary;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import static com.google.android.gms.maps.CameraUpdateFactory.newLatLng;
import static com.taek_aaa.locationdiary.DataSet.category_arr;
import static com.taek_aaa.locationdiary.DataSet.itc;
import static com.taek_aaa.locationdiary.DataSet.iter;
import static com.taek_aaa.locationdiary.DataSet.moveCameraIter;
import static com.taek_aaa.locationdiary.DataSet.sllDBData;


/**  맵으로 보기 눌렀을 때  **/
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    final DBManager dbManager = new DBManager(this, "logger.db", null, 1);
    private GoogleMap mMap;
    LinearLayout type_ll;
    static int temp;
    Bitmap photo;
    final int PICK_FROM_ALBUM = 101;
    AlertDialog tempad;
    Dialog dialog;
    ImageView imageView;
    Button moveCameraBtn;
    CircleOptions circle;
    String photo_str;
    ViewGroup.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        MapsInitializer.initialize(getApplicationContext());
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**  사진 추가 버튼을 눌러서 intent의 결과를 받는 부분 **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_FROM_ALBUM:
                try {
                    Uri uri = data.getData();
                    photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    dialog = new Dialog(this);
                    dialog.setOwnerActivity(this);
                    dialog.setContentView(R.layout.activity_dialog);
                    imageView = (ImageView) dialog.findViewById(R.id.imageview);
                    imageView.setImageBitmap(photo);
////이부분수정 사진불러오기
                    dialog.show();
/*

                    uri= data.getData();
                    photo_str = uri.toString();
                    MediaStore.Images.Media.getBitmap( getContentResolver(), uri);
                    imageView.setImageURI(uri);
*/



                } catch (Exception e) {
                    e.getStackTrace();
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        sllDBData.clear();
        //dbManager.update();
        dbManager.getResult(sllDBData);
        Log.e("slldbdata의 크기",""+sllDBData.size());
        moveCameraIter = 0;

        mMap = googleMap;
        iter = dbManager.getIter();

       try {
            Log.e("value", String.valueOf(itc.getIteration()));
            for (int i = 0; i < sllDBData.size(); i++) {
                Log.e("value", String.valueOf(sllDBData.get(i).curlatitude));
                Log.e("value", String.valueOf(sllDBData.get(i).curlongitude));
                Log.e("value", sllDBData.get(i).curTodoOrEvent);
                Log.e("value", String.valueOf(sllDBData.get(i).curCategory));
                Log.e("value", String.valueOf(sllDBData.get(i).curHowLong));
                Log.e("value", sllDBData.get(i).curNum);
                Log.e("value", sllDBData.get(i).curText);
                Log.e("value", sllDBData.get(i).curTime);
            }
           for(int i=0; i<sllDBData.size();i++){
               sllDBData.get(i).curNum=""+i;
           }

            for (int i = 0; i < sllDBData.size(); i++) {
                MarkerOptions opt = new MarkerOptions();
                opt.position(new LatLng(sllDBData.get(i).curlatitude, sllDBData.get(i).curlongitude));
                opt.title(sllDBData.get(i).curNum);
                opt.snippet(sllDBData.get(i).curText + "@" + sllDBData.get(i).curTime);
                if(sllDBData.get(i).curTodoOrEvent.equals("Event")){
                     circle = new CircleOptions().center(new LatLng(sllDBData.get(i).curlatitude, sllDBData.get(i).curlongitude)) //원점
                            .radius(10)      //반지름 단위 : m
                            .strokeWidth(0f)  //선너비 0f : 선없음
                            .fillColor(Color.parseColor("#880000ff"));


                }else {
                    circle = new CircleOptions().center(new LatLng(sllDBData.get(i).curlatitude, sllDBData.get(i).curlongitude)) //원점
                            .radius(0)
                            .strokeWidth(0f);
                }

                mMap.addMarker(opt).showInfoWindow();
                mMap.addCircle(circle);
                if (i != 0) {
                    mMap.addPolyline(new PolylineOptions().geodesic(true).add(new LatLng(Double.valueOf(sllDBData.get(i - 1).curlatitude), Double.valueOf(sllDBData.get(i - 1).curlongitude)), new LatLng(Double.valueOf(sllDBData.get(i).curlatitude), Double.valueOf(sllDBData.get(i).curlongitude))).width(5).color(Color.RED));
                }
                Log.e("value", "한바퀴돔");
            }
            mMap.moveCamera(newLatLng(new LatLng(sllDBData.get(0).curlatitude, sllDBData.get(0).curlongitude)));
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(MapsActivity.this);
                    type_ll = new LinearLayout(MapsActivity.this);
                    type_ll.setPadding(0, 0, 0, 0);

                    int a = Integer.valueOf(marker.getTitle());
                    ConvertSecondtoTime cst = new ConvertSecondtoTime();
                    Log.e("현재 클릭한 마커의 제목의 타이틀",""+a);
                    cst.transferTime(sllDBData.get(a).curHowLong);
                    String h = cst.getHour();
                    String m = cst.getMinute();
                    String s = cst.getSecond();

                    temp = a;
                    adb
                            .setTitle("정보창")
                            .setCancelable(false)
                            .setMessage("종류 : " + sllDBData.get(a).curTodoOrEvent + "\n" + "카테고리 : " + category_arr[sllDBData.get(a).curCategory] + "\n" + "소요시간 : " + h + "시간 " + m + "분 " + s + "초" + "\n" + "" + "내용 : " + sllDBData.get(a).curText + "\n" + "시간 : " + sllDBData.get(a).curTime)
                            .setView(type_ll)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setNeutralButton("사진 추가", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(intent, PICK_FROM_ALBUM);

                                }
                            })
                            .setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dbManager.delete(sllDBData.get(temp).curlatitude,sllDBData.get(temp).curlongitude);
                                    Toast.makeText(MapsActivity.this, "정상적으로 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                    sllDBData.clear();
                                    dbManager.idUpdate(temp);
                                    dbManager.titleUpdate(temp);
                                    dbManager.getResult(sllDBData);
                                    Log.e("ogt","삭제후 총 iter : " + dbManager.getIter());
                                   Intent moveToMap = new Intent (getApplicationContext(), MapsActivity.class);
                                    startActivity(moveToMap);
                                    for(int i=0; i<sllDBData.size();i++){
                                        sllDBData.get(i).curNum=""+i;
                                    }
                                    onMapReady(mMap);
                                    finish();
                                }
                            });
                    AlertDialog ad = adb.create();
                    tempad = ad;
                    ad.show();
                }

            });
        } catch (Exception e) {
            e.getMessage();
            Log.e("ogt",""+e);
            Toast.makeText(this, "먼저 값을 받아주세요.", Toast.LENGTH_SHORT).show();
        }

    }

    /** 궤적 보기 버튼을 눌렀을 때 **/
    public void onClickMoveCamera(View v) {
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        moveCameraBtn = (Button)findViewById(R.id.moveCamerabtn) ;
        iter = dbManager.getIter();


        if (moveCameraIter == sllDBData.size()) {
            Toast.makeText(this, "마지막 궤적입니다.",Toast.LENGTH_SHORT).show();
            moveCameraBtn.setText("궤적보기");
            moveCameraIter = 0;

        }else {
            moveCameraBtn.setText("다음");
            mMap.moveCamera(newLatLng(new LatLng(sllDBData.get(moveCameraIter).curlatitude, sllDBData.get(moveCameraIter).curlongitude)));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2500, null);
        }

        moveCameraIter++;
    }


    /** 사진 촬영 버튼 눌렀을 때 **/
    public void onClickPicture(View v){
        Intent intent = new Intent();
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

}


