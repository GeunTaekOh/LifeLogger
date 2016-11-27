package com.taek_aaa.locationdiary;

import android.app.Activity;
import android.os.Bundle;

import static com.taek_aaa.locationdiary.DataSet.category_arr;


public class List extends Activity {
    int ary[] = new int[7];
    String arystr[] = new String[7];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        // int alsize = llistLatitude.size();
        int valueOfCategory[] = new int[category_arr.length];

      /*  for(int i=0; i<alsize; i++) {
            if(llistCategory.get(i).toString().equals(category_arr[0])){
                valueOfCategory[0]++;
            }
            else if (llistCategory.get(i).toString().equals(category_arr[1])){
                valueOfCategory[1]++;
            }else if(llistCategory.get(i).toString().equals(category_arr[2])){
                valueOfCategory[2]++;
            }else if(llistCategory.get(i).toString().equals(category_arr[3])){
                valueOfCategory[3]++;
            }else if(llistCategory.get(i).toString().equals(category_arr[4])){
                valueOfCategory[4]++;
            }else if(llistCategory.get(i).toString().equals(category_arr[5])){
                valueOfCategory[5]++;
            }else if(llistCategory.get(i).toString().equals(category_arr[6])) {
                valueOfCategory[6]++;
            }else if(llistCategory.get(i).toString().equals(category_arr[7])) {
                valueOfCategory[7]++;
            }
        }*/
        for (int i = 0; i < category_arr.length; i++) {
            ary[i] = valueOfCategory[i];
            arystr[i] = category_arr[i];
        }
        sort();
        //setRank();

    }

    public void sort() {
        int tmp;
        String tmpstr;

        for (int i = 0; i < ary.length - 1; i++) {
            for (int j = i + 1; j < ary.length; j++) {
                if (ary[i] > ary[j]) {
                    tmp = ary[i];
                    tmpstr = arystr[i];
                    ary[i] = ary[j];
                    arystr[i] = arystr[j];
                    ary[j] = tmp;
                    arystr[j] = tmpstr;
                }
            }
        }
    }

    /*public void setRank() {
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        TextView tv3 = (TextView) findViewById(R.id.tv3);
        TextView tv4 = (TextView) findViewById(R.id.tv4);

        tv1.setText("1st. " + arystr[3] + " : " + ary[3]);
        tv2.setText("2nd. " + arystr[2] + " : " + ary[2]);
        tv3.setText("3th. " + arystr[1] + " : " + ary[1]);
        tv4.setText("4th. " + arystr[0] + " : " + ary[0]);
    }*/
}
