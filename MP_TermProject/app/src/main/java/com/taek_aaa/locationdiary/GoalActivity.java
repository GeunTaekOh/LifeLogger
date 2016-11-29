package com.taek_aaa.locationdiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by taek_aaa on 2016. 11. 27..
 */

public class GoalActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
    }

    public void onClickSetGoal(View v) {
        startActivity(new Intent(this, GoalSettingActivity.class));
    }

}
