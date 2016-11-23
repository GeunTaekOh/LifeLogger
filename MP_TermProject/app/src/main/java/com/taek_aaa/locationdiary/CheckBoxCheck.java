package com.taek_aaa.locationdiary;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

/**
 * Created by taek_aaa on 2016. 11. 23..
 */

public class CheckBoxCheck extends Activity {
    boolean result;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        CheckBox checkBoxTodo = (CheckBox)findViewById(R.id.checkToDo);
        CheckBox checkBoxEvent = (CheckBox)findViewById(R.id.checkEvent);


        checkBoxTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId()==R.id.checkToDo){
                    if(isChecked){
                        result=true;
                    }
                }else if(buttonView.getId()==R.id.checkToDo && buttonView.getId()==R.id.checkEvent){
                    Toast.makeText(getBaseContext(), "하나만 체크하세요.", Toast.LENGTH_SHORT).show();
                }else{
                    result=false;
                }
            }
        });
        checkBoxEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.getId()==R.id.checkEvent){
                    if(isChecked){
                        result=false;
                    }else if(buttonView.getId()==R.id.checkEvent && buttonView.getId()==R.id.checkToDo){
                        Toast.makeText(getBaseContext(), "하나만 체크하세요.", Toast.LENGTH_SHORT).show();
                    }else{
                        result=true;
                    }
                }
            }
        });

    }

    public boolean result(){


        return result;

    }


}
