package com.example.hzu.jkbd.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.hzu.jkbd.ExamApplication;
import com.example.hzu.jkbd.R;
import com.example.hzu.jkbd.bean.ExamInfo;


/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity{
    TextView tvExamInfo;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        initData();

    }

    private void initView() {
        tvExamInfo=(TextView) findViewById(R.id.tv_examinfo);
    }

    private void initData() {
        ExamInfo examInfo =ExamApplication.getInstance().getmExamInfo();
        if (examInfo !=null){
            showData(examInfo);
        }
    }

    private void showData(ExamInfo examInfo) {
        tvExamInfo.setText(examInfo.toString());
    }


}
