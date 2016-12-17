package com.taek_aaa.locationdiary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;

import java.util.Calendar;

import static com.taek_aaa.locationdiary.DataSet.category_arr;
import static com.taek_aaa.locationdiary.DataSet.mainCategory_arr_index;
import static com.taek_aaa.locationdiary.DataSet.subCategory_arr_index;

/**  통계 확인하기 눌렀을 때  **/
public class List extends Activity {
    Spinner mainspinner;
    Spinner subspinner;
    TextView startdayTv, enddayTv;
    int iYears;     //이는 컴퓨터가 인지하는
    int iMonths;
    int iDates;
    int hMonths;  //h가 붙은 것들은 사람이 아는 값
    int iYeare;     //이는 컴퓨터가 인지하는
    int iMonthe;
    int iDatee;
    int hMonthe;  //h가 붙은 것들은 사람이 아는 값
    final static int CATEGORY_SEQ = 1001;
    final static int TIME_SEQ = 1002;
    int state;
    int nullData;
    TextView showre;
    DBManager dbManager = new DBManager(this, "logger.db", null, 1);
    PieChart pieChart;
    PieChartClass mypieChart;
    int[] numForGraph = new int[7];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        startdayTv = (TextView) findViewById(R.id.Text_StartDay);
        enddayTv = (TextView) findViewById(R.id.Text_EndDay);

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
        showre = (TextView) findViewById(R.id.showListTextview);

        startdayTv.setText(iYears + "년 " + hMonths + "월 " + iDates + "일");
        enddayTv.setText(iYeare + "년 " + hMonthe + "월 " + iDatee + "일");

        mainspinner = (Spinner) findViewById(R.id.mainCategoryspinner);
        subspinner = (Spinner) findViewById(R.id.subCategoryspinner);
        mainspinner.setSelection(0);
        subspinner.setSelection(0);
        //메인스피너
        mainspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainCategory_arr_index = position;
                switch (position) {
                    case (0):
                        populateSubSpinners(R.array.subSpinnerContentsCategory);
                        state = CATEGORY_SEQ;
                        showre.setText("");
                        break;
                    case (1):
                        populateSubSpinners(R.array.subSpinnerContentsTime);
                        showre.setText("<Pie Chart>");
                        state = TIME_SEQ;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //서브스피너
        subspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subCategory_arr_index = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void populateSubSpinners(int itemNum) {
        ArrayAdapter<CharSequence> fAdapter;
        fAdapter = ArrayAdapter.createFromResource(this,
                itemNum,
                android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subspinner.setAdapter(fAdapter);
    }

    public void onclickstart(View v) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() { //datepicker


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                hMonths = monthOfYear + 1;
                TextView caltv = (TextView) findViewById(R.id.Text_StartDay);
                caltv.setText(year + "년 " + hMonths + "월 " + dayOfMonth + "일");

                iYears = year;
                iMonths = monthOfYear;
                iDates = dayOfMonth;
            }
        };
        new DatePickerDialog(this, dateSetListener, iYears, iMonths, iDates).show();
    }

    public void onclickend(View v) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() { //datepicker


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                hMonthe = monthOfYear + 1;
                TextView caltv = (TextView) findViewById(R.id.Text_EndDay);
                caltv.setText(year + "년 " + hMonthe + "월 " + dayOfMonth + "일");

                iYeare = year;
                iMonthe = monthOfYear;
                iDatee = dayOfMonth;
            }
        };
        new DatePickerDialog(this, dateSetListener, iYeare, iMonthe, iDatee).show();

    }

    /** SHOW 버튼을 눌렀을 때**/
    public void onClickspinnerShowbtn(View v) {
        final int datestart = iYears * 10000 + iMonths * 100 + iDates;
        final int dateend = iYeare * 10000 + iMonthe * 100 + iDatee;
        int parstart = hMonths * 100 + iDates;
        int parend = hMonthe * 100 + iDatee;

        if (datestart > dateend) {
            Toast.makeText(this, "잘못된 입력이 있습니다.", Toast.LENGTH_SHORT).show();
            showre.setText("");
        }

        if (state == CATEGORY_SEQ) {
            int showTotalResult = dbManager.staticslist(parstart, parend, subCategory_arr_index);
            ConvertSecondtoTime cst = new ConvertSecondtoTime();
            cst.transferTime(showTotalResult);
            String h = cst.getHour();
            String m = cst.getMinute();
            String s = cst.getSecond();

            showre.setText("" + category_arr[subCategory_arr_index] + "에 소요한 총 시간은 " + "" + h + "시간 " + m + "분 " + s + "초 입니다.");
        } else if (state == TIME_SEQ) {
            pieChart = (PieChart) findViewById(R.id.pie_Chart) ;
            Description desc = new Description();
            desc.setText("Category Stats");
            pieChart.setDescription(desc);

            for(int i=0; i<numForGraph.length; i++){
                numForGraph[i] = dbManager.staticslist(parstart,parend,i);
            }


            for(int i=0; i<numForGraph.length; i++){
                nullData+=numForGraph[i];
            }

            if(nullData==0){
                Toast.makeText(this,"해당 날짜에 데이터가 없습니다.",Toast.LENGTH_SHORT).show();
            }

            mypieChart = new PieChartClass(pieChart) ;
            mypieChart.setyData(numForGraph);
            mypieChart.addData();
        }
    }
}
