package com.taek_aaa.locationdiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

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

/**
 * 목표 확인하기 눌렀을 때
 **/
public class GoalActivity extends Activity {
    TextView goalContentsTv, goalResultstv, goalTitleTv;
    ImageView goalResultsView;
    final public static int DOING_GOAL = 9991;
    final public static int YOU_ARE_SUCCESS = 9992;
    final public static int YOU_ARE_FAILED = 9993;
    public static int status = 9994;
    DBManager dbManager = new DBManager(this, "logger.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        goalContentsTv = (TextView) findViewById(R.id.showGoalcontets);
        goalResultsView = (ImageView) findViewById(R.id.showResultiv);
        goalResultstv = (TextView) findViewById(R.id.showResulttv);
        goalTitleTv = (TextView) findViewById(R.id.showGoalTitle);

        showResultView();
    }

    /**목표 설정 눌렀을 때**/
    public void onClickSetGoal(View v) {
        startActivity(new Intent(this, GoalSettingActivity.class));
    }

    /**새로 고침 눌렀을때**/
    public void onClickGoalRefresh(View v) {
        setResultStatus();
        showResultView();
    }
    /**목표 삭제 눌렀을 때**/
    public void onClickDeleteGoal(View v) {
        Toast.makeText(getBaseContext(), "목표가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
        goalContentsTv.setText("");
        goalTitleTv.setText("");
        goalResultstv.setText("");
        goalResultsView.setImageResource(R.drawable.goal);
    }
    /**setText를 해주는 함수**/
    public void showResultView() {
        if (goalTime.equals("")) {
            goalTitleTv.setText("");
            goalContentsTv.setText("목표를 설정하세요!");
            goalResultstv.setText("");
            goalResultsView.setImageResource(R.drawable.goal);
        } else {
            goalTitleTv.setText(goalString + "\n");
            goalTitleTv.setTextSize(30);
            goalContentsTv.setText("" + goalStartYear + "년 " + "" + goalStartMonth + "월 " + "" + goalStartDate + "일 부터 " + "" + goalEndYear + "년 " + "" + goalEndMonth + "월 " + "" + goalEndDate + "일 까지" + "" + category_arr[categoty_arr_index2] + "을/를 " + "" + goalTime + "시간 " + "" + biggerOrSmaller + "하자 !!");
            goalContentsTv.setTextSize(25);
        }

        if (status == YOU_ARE_SUCCESS) {
            goalResultsView.setImageResource(R.drawable.success);
            goalResultstv.setText("고생했어요 성공했어요!");

        } else if (status == YOU_ARE_FAILED) {
            goalResultsView.setImageResource(R.drawable.fail);
            goalResultstv.setText("아쉽네요 분발하세요!");
        } else if (status == DOING_GOAL) {
            goalResultsView.setImageResource(R.drawable.running);
            goalResultstv.setText("목표를 향해 달려가는 중이네요!");
        } else {
            goalResultsView.setImageResource(R.drawable.goal);
        }
    }

    /**목표 설정한 값에 맞게 상태 조정**/
    public void setResultStatus() {
        try {
            Calendar today;
            today = Calendar.getInstance();
            int todayMonth = today.get(Calendar.MONTH);
            int todayDate = today.get(Calendar.DAY_OF_MONTH);
            int parstart = goalStartMonth * 100 + goalStartDate;
            int parend = goalEndMonth * 100 + goalEndDate;
            int parToday = (todayMonth + 1) * 100 + todayDate;
            Log.e("plm", "" + todayMonth);
            Log.e("plm", "" + goalEndMonth);
            int showTotalResult = dbManager.staticslist(parstart, parend, categoty_arr_index2);

            if (parend > parToday) {
                status = DOING_GOAL;
                Log.e("plm", "하는중");
            } else {
                if (biggerOrSmaller.equals("이상")) {
                    if (Integer.parseInt(goalTime) <= showTotalResult) {
                        status = YOU_ARE_SUCCESS;
                        Log.e("plm", "성공,이상");
                    } else {
                        status = YOU_ARE_FAILED;
                        Log.e("plm", "실패,이상");
                    }
                } else {
                    if (Integer.parseInt(goalTime) >= showTotalResult) {
                        status = YOU_ARE_SUCCESS;
                        Log.e("plm", "성공,이하");
                    } else {
                        status = YOU_ARE_FAILED;
                        Log.e("plm", "실패,이하");
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "먼저 값을 입력하세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
