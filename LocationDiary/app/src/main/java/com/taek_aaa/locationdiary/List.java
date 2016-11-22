package com.taek_aaa.locationdiary;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class List extends Activity {
    int ary[] = new int[4];
    String arystr[] = new String[4];

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        MainActivity mact = new MainActivity();
        int alsize = mact.alistLatitude.size();
        int studyNum = 0;
        int eatNum = 0;
        int cafeNum = 0;
        int walkingNum = 0;

        for(int i=0; i<alsize; i++) {
            if(mact.alistCategory.get(i).toString().equals("공부")){
                studyNum++;
            }
            else if (mact.alistCategory.get(i).toString().equals("식사")){
                eatNum++;
            }else if(mact.alistCategory.get(i).toString().equals("카페")){
                cafeNum++;
            }else if(mact.alistCategory.get(i).toString().equals("산책")){
                walkingNum++;
            }
        }
        ary[0] = studyNum;
        arystr[0] = "공부";
        ary[1] = eatNum;
        arystr[1] = "식사";
        ary[2] = cafeNum;
        arystr[2] = "카페";
        ary[3] = walkingNum;
        arystr[3] = "산책";

        sort();
        setRank();

    }

    public void sort(){
        int tmp = 0;
        String tmpstr = null;

        for(int i=0; i<ary.length-1; i++){
            for(int j=i+1; j<ary.length; j++){
                if(ary[i]>ary[j]){
                    tmp=ary[i];
                    tmpstr=arystr[i];
                    ary[i]=ary[j];
                    arystr[i] = arystr[j];
                    ary[j] = tmp;
                    arystr[j] = tmpstr;
                }
            }
        }
    }
    public void setRank(){
        TextView tv1 = (TextView)findViewById(R.id.tv1);
        TextView tv2 = (TextView)findViewById(R.id.tv2);
        TextView tv3 = (TextView)findViewById(R.id.tv3);
        TextView tv4 = (TextView)findViewById(R.id.tv4);

        tv1.setText("1st. "+arystr[3] + " : "+ary[3]);
        tv2.setText("2nd. "+arystr[2] + " : "+ary[2]);
        tv3.setText("3th. "+arystr[1] + " : "+ary[1]);
        tv4.setText("4th. "+arystr[0] + " : "+ary[0]);
    }
}
