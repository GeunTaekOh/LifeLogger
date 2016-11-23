package com.taek_aaa.locationdiary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

/**
 * Created by taek_aaa on 2016. 11. 23..
 */

public class CheckBoxCheck extends Activity {
    boolean result;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        RadioButton checkBoxTodo = (RadioButton) findViewById(R.id.checkToDo);
        RadioButton checkBoxEvent = (RadioButton)findViewById(R.id.checkEvent);
        checkBoxTodo.setChecked(true);
       // result();
       /* checkBoxTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId()==R.id.checkToDo){
                    if(isChecked){
                        Log.d("test","1");
                        result=true;
                    }
                }else{
                    Log.d("test","2");
                    result=false;
                }
            }
        });
        checkBoxEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId()==R.id.checkEvent){
                    if(isChecked){
                        Log.d("test","3");
                        result=false;
                    }else{
                        Log.d("test","4");
                        result=true;
                    }
                }
            }
        });*/

    }

    public boolean result(){
        RadioButton checkBoxTodo = (RadioButton) findViewById(R.id.checkToDo);
        RadioButton checkBoxEvent = (RadioButton)findViewById(R.id.checkEvent);

        checkBoxTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test","라디오박스 투두");
                result = true;
            }
        });

        checkBoxEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test","라디오박스 이벤트");
                result=false;

            }
        });


        return result;

    }


}
