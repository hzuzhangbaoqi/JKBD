package com.example.hzu.jkbd.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hzu.jkbd.ExamApplication;
import com.example.hzu.jkbd.R;
import com.example.hzu.jkbd.bean.ExamInfo;
import com.example.hzu.jkbd.bean.Question;
import com.example.hzu.jkbd.bean.Result;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity{
    TextView tvExamInfo,tvExamTitle,tvOp1,tvOp2,tvOp3,tvOp4;
    ImageView mImageView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        initData();

    }

    private void initView() {
        tvExamInfo=(TextView) findViewById(R.id.tv_examinfo);
        tvExamTitle=(TextView) findViewById(R.id.tv_exam_title);
        tvOp1=(TextView) findViewById(R.id.tv_op1);
        tvOp2=(TextView) findViewById(R.id.tv_op2);
        tvOp3=(TextView) findViewById(R.id.tv_op3);
        tvOp4=(TextView) findViewById(R.id.tv_op4);
        mImageView=(ImageView) findViewById(R.id.im_exam_image);
    }

    private void initData() {
        ExamInfo examInfo =ExamApplication.getInstance().getmExamInfo();
        if (examInfo !=null){
            showData(examInfo);
        }
        List<Question> examList = ExamApplication.getInstance().getmExamList();
        if(examList!=null ){
            showExam(examList);
        }
    }

    private void showExam(List<Question> examList) {
        Question exam = examList.get(0);
        if (exam!=null){
            tvExamTitle.setText(exam.getQuestion());
            tvOp1.setText(exam.getItem1());
            tvOp2.setText(exam.getItem2());
            tvOp3.setText(exam.getItem3());
            tvOp4.setText(exam.getItem4());

            Picasso.with(ExamActivity.this)
                    .load(exam.getUrl())
                    .into(mImageView);
        }
    }

    private void showData(ExamInfo examInfo) {
        tvExamInfo.setText(examInfo.toString());
    }


}
