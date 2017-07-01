package com.example.hzu.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hzu.jkbd.ExamApplication;
import com.example.hzu.jkbd.R;
import com.example.hzu.jkbd.bean.ExamInfo;
import com.example.hzu.jkbd.bean.Question;
import com.example.hzu.jkbd.bean.Result;
import com.example.hzu.jkbd.biz.ExamBiz;
import com.example.hzu.jkbd.biz.IExamBiz;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity{
    TextView tvExamInfo,tvExamTitle,tvOp1,tvOp2,tvOp3,tvOp4;
    ImageView mImageView;
    IExamBiz biz;
    boolean isLoadExamInfo=false;
    boolean isLoadQuestions=false;

    LoadQuestionBroadcast mLoadQuestionBroadcast;
    LoadExamBroadcast mLoadExamBroadcast;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mLoadExamBroadcast =new LoadExamBroadcast();
        mLoadQuestionBroadcast= new LoadQuestionBroadcast();
        setListener();
        initView();
        loadData();

    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        biz=new ExamBiz();
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
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
        if (isLoadExamInfo && isLoadQuestions) {
            ExamInfo examInfo = ExamApplication.getInstance().getmExamInfo();
            if (examInfo != null) {
                showData(examInfo);
            }
            List<Question> examList = ExamApplication.getInstance().getmExamList();
            if (examList != null) {
                showExam(examList);
            }
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
    protected void onDestroy(){
        super.onDestroy();
        if (mLoadExamBroadcast!=null){
            unregisterReceiver(mLoadExamBroadcast);
        }
        if(mLoadQuestionBroadcast!=null){
            unregisterReceiver(mLoadQuestionBroadcast);
        }
    }

    class LoadExamBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess"+isSuccess);
            if(isSuccess){
                isLoadExamInfo =true;
            }
            initData();
        }
    }
    class LoadQuestionBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSuccess"+isSuccess);
            if(isSuccess){
                initData();
                isLoadQuestions=true;
            }
            initData();
        }
    }


}
