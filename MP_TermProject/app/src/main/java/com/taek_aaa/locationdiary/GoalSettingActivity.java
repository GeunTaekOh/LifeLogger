package com.taek_aaa.locationdiary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static com.taek_aaa.locationdiary.DataSet.biggerOrSmaller;
import static com.taek_aaa.locationdiary.DataSet.categoty_arr_index2;
import static com.taek_aaa.locationdiary.DataSet.goalEndDate;
import static com.taek_aaa.locationdiary.DataSet.goalEndMonth;
import static com.taek_aaa.locationdiary.DataSet.goalEndYear;
import static com.taek_aaa.locationdiary.DataSet.goalStartDate;
import static com.taek_aaa.locationdiary.DataSet.goalStartMonth;
import static com.taek_aaa.locationdiary.DataSet.goalStartYear;
import static com.taek_aaa.locationdiary.DataSet.goalString;
import static com.taek_aaa.locationdiary.DataSet.goalTime;

/**
 * Created by taek_aaa on 2016. 11. 29..
 */

/**
 * 목표 설정 눌렀을 때
 **/
public class GoalSettingActivity extends Activity {
    EditText editTextContents, editHowlongtime;
    TextView startDay;
    TextView endDay;
    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton selectedbtn;
    Button setButton;
    String strContents, strHowlong;
    int iYears, iDates, iMonths, hMonths;
    int iYeare, iMonthe, iDatee, hMonthe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goalsetting);
        editTextContents = (EditText) findViewById(R.id.goalEditText);
        startDay = (TextView) findViewById(R.id.goalStartDate);
        endDay = (TextView) findViewById(R.id.goalEndDate);
        editHowlongtime = (EditText) findViewById(R.id.goalHowTimeEditText);

        Calendar today;
        today = Calendar.getInstance();
        iYears = today.get(Calendar.YEAR);
        iMonths = today.get(Calendar.MONTH);
        iDates = today.get(Calendar.DAY_OF_MONTH);
        hMonths = today.get(Calendar.MONTH) + 1;
        iYeare = today.get(Calendar.YEAR);
        iMonthe = today.get(Calendar.MONTH);
        iDatee = today.get(Calendar.DAY_OF_MONTH);
        hMonthe = today.get(Calendar.MONTH) + 1;

        startDay.setText(iYears + "년 " + hMonths + "월 " + iDates + "일");
        endDay.setText(iYeare + "년 " + hMonthe + "월 " + iDatee + "일");


        strContents = editTextContents.getText().toString();        //editText값 내용
        strHowlong = editHowlongtime.getText().toString();          //editText값 몇시간
        listenerOnRadioBtn();
        /** Spinner 선택 하는 부분 **/
        Spinner spinner = (Spinner) findViewById(R.id.spinnerGoalSetting);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoty_arr_index2 = position;         //목표에서 뽑은 카테고리
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    /**
     * 라디오 어떤 값을 가져왔는지를 확인
     **/
    public void listenerOnRadioBtn() {
        radioGroup = (RadioGroup) findViewById(R.id.goalRadioGroup);
        radioButton1 = (RadioButton) findViewById(R.id.goalRadio1);
        radioButton2 = (RadioButton) findViewById(R.id.goalRadio2);
        setButton = (Button) findViewById(R.id.saveRadiobuttonGoalSetting);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selected = radioGroup.getCheckedRadioButtonId();
                selectedbtn = (RadioButton) findViewById(selected);
                Log.e("ppq", selectedbtn.getText().toString());
                biggerOrSmaller = selectedbtn.getText().toString();
            }
        });
    }


    /**
     * 목표 설정에서 시작 날짜
     **/
    public void onclickStartGoal(View v) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() { //datepicker


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                hMonths = monthOfYear + 1;
                TextView caltv = (TextView) findViewById(R.id.goalStartDate);
                caltv.setText(year + "년 " + hMonths + "월 " + dayOfMonth + "일");

                iYears = year;                 //이부분을 하지 않으면 클릭하여서 날짜를 바꾸면 그게 DatePickerDialog에 반영되지 않음
                iMonths = monthOfYear;
                iDates = dayOfMonth;
                goalStartYear = year;
                goalStartMonth = hMonths;
                goalStartDate = dayOfMonth;


            }
        };
        new DatePickerDialog(this, dateSetListener, iYears, iMonths, iDates).show();
    }

    /**
     * 목표 설정에서 끝 날짜
     **/
    public void onclickEndGoal(View v) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() { //datepicker


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                hMonthe = monthOfYear + 1;
                TextView caltv = (TextView) findViewById(R.id.goalEndDate);
                caltv.setText(year + "년 " + hMonthe + "월 " + dayOfMonth + "일");

                iYeare = year;                 //이부분을 하지 않으면 클릭하여서 날짜를 바꾸면 그게 DatePickerDialog에 반영되지 않음
                iMonthe = monthOfYear;
                iDatee = dayOfMonth;
                goalEndYear = year;
                goalEndMonth = hMonthe;
                goalEndDate = dayOfMonth;
            }
        };
        new DatePickerDialog(this, dateSetListener, iYeare, iMonthe, iDatee).show();

    }

    public void onClickGoalSet(View v) {
        editTextContents = (EditText) findViewById(R.id.goalEditText);
        editHowlongtime = (EditText) findViewById(R.id.goalHowTimeEditText);
        strContents = editTextContents.getText().toString();
        strHowlong = editHowlongtime.getText().toString();

        if (strContents == null || strHowlong == null) {
            Toast.makeText(getBaseContext(), "값을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
        } else {
            goalString = strContents;
            goalTime = strHowlong;
        }
        editTextContents.setText("");
        editHowlongtime.setText("");


        Toast.makeText(getBaseContext(), "목표를 설정하였습니다.", Toast.LENGTH_SHORT).show();

    }

}
