package com.taek_aaa.locationdiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.taek_aaa.locationdiary.DataSet.biggerOrSmaller;
import static com.taek_aaa.locationdiary.DataSet.category_arr;
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
 * Created by taek_aaa on 2016. 11. 27..
 */

/** 목표 확인하기 눌렀을 때 **/
public class GoalActivity extends Activity {
    TextView goalContentsTv,goalResultstv, goalTitleTv;
    ImageView goalResultsView;
    final public static int DOING_GOAL = 9991;
    final public static int YOU_ARE_SUCCESS = 9992;
    final public static int YOU_ARE_FAILED = 9993;
    public static int status = DOING_GOAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        goalContentsTv = (TextView)findViewById(R.id.showGoalcontets);
        goalResultsView = (ImageView)findViewById(R.id.showResultiv);
        goalResultstv = (TextView)findViewById(R.id.showResulttv);
        goalTitleTv = (TextView)findViewById(R.id.showGoalTitle);

        showResultView();
    }

    public void onClickSetGoal(View v) {
        startActivity(new Intent(this, GoalSettingActivity.class));
    }

    public void onClickGoalRefresh(View v){
        showResultView();
    }
    public void onClickDeleteGoal(View v) {
        Toast.makeText(getBaseContext(),"목표가 삭제되었습니다.",Toast.LENGTH_SHORT).show();
        goalContentsTv.setText("");
        goalTitleTv.setText("");
        goalResultstv.setText("");
        goalResultsView.setImageResource(R.drawable.goal);
    }

    public void showResultView(){
        if(goalTime.equals("")){
            goalTitleTv.setText("");
            goalContentsTv.setText("목표를 설정하세요!");
            goalResultstv.setText("");
            goalResultsView.setImageResource(R.drawable.goal);
        }else {
            goalTitleTv.setText(goalString + "\n");
            goalTitleTv.setTextSize(30);
            goalContentsTv.setText("" + goalStartYear + "년 " + "" + goalStartMonth + "월 " + "" + goalStartDate + "일 부터 " + "" + goalEndYear + "년 " + "" + goalEndMonth + "월 " + "" + goalEndDate + "일 까지" + "" + category_arr[categoty_arr_index2] + "을/를 " + "" + goalTime + "시간 " + "" + biggerOrSmaller + "하자 !!");
            goalContentsTv.setTextSize(25);
        }

        if(status==YOU_ARE_SUCCESS){
            goalResultsView.setImageResource(R.drawable.success);
            goalResultstv.setText("고생했어요 성공했어요!");

        }else if(status==YOU_ARE_FAILED){
            goalResultsView.setImageResource(R.drawable.fail);
            goalResultstv.setText("아쉽네요 분발하세요!");
        }else if(status==DOING_GOAL){
            goalResultsView.setImageResource(R.drawable.running);
            goalResultstv.setText("목표를 향해 달려가는 중이네요!");
        }else{

            goalResultsView.setImageResource(R.drawable.goal);
        }
    }
}
