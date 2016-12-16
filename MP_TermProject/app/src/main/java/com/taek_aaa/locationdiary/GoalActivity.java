package com.taek_aaa.locationdiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.taek_aaa.locationdiary.DataSet.goalString;

/**
 * Created by taek_aaa on 2016. 11. 27..
 */

/** 목표 확인하기 눌렀을 때 **/
public class GoalActivity extends Activity {
    TextView goalContentsTv, goalResultsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        goalContentsTv = (TextView)findViewById(R.id.showGoalcontets);
        goalResultsTv = (TextView)findViewById(R.id.showResulttv);

        goalContentsTv.setText(goalString);
        goalContentsTv.setTextSize(30);




    }

    public void onClickSetGoal(View v) {
        startActivity(new Intent(this, GoalSettingActivity.class));
    }

    public void onClickGoalRefresh(View v){
        goalContentsTv.setText(goalString);
    }
    public void onClickDeleteGoal(View v) {
        Toast.makeText(getBaseContext(),"목표가 삭제되었습니다.",Toast.LENGTH_SHORT).show();
        goalContentsTv.setText("");
    }
}
