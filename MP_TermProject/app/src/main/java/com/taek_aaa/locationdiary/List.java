package com.taek_aaa.locationdiary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import static com.taek_aaa.locationdiary.DataSet.mainCategory_arr_index;
import static com.taek_aaa.locationdiary.DataSet.subCategory_arr_index;


public class List extends Activity {
    Spinner mainspinner;
    Spinner subspinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mainspinner = (Spinner) findViewById(R.id.mainCategoryspinner);
        subspinner = (Spinner) findViewById(R.id.subCategoryspinner);
        mainspinner.setSelection(0);
        subspinner.setSelection(0);

        mainspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mainCategory_arr_index = position;
                switch (position) {
                    case (0):
                        populateSubSpinners(R.array.subSpinnerContentsCategory);
                        break;
                    case (1):
                        populateSubSpinners(R.array.subSpinnerContentsTime);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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


}
