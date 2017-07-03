package com.example.hzu.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    TextView tvExamInfo,tvExamTitle,tvOp1,tvOp2,tvOp3,tvOp4,tvLoad,tvNo;
    LinearLayout layoutLoading;
    ImageView mImageView;
    ProgressBar dialog;
    IExamBiz biz;
    boolean isLoadExamInfo=false;
    boolean isLoadQuestions=false;
    boolean isLoadExamInfoReceiver=false;
    boolean isLoadQuestionsReceiver=false;

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
        biz =new ExamBiz();

    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        biz =new ExamBiz();
        layoutLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        tvLoad.setText("下载数据中...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void initView() {
        layoutLoading=(LinearLayout) findViewById(R.id.layout_loading);
        tvNo=(TextView) findViewById(R.id.tv_exam_no);
        tvExamInfo=(TextView) findViewById(R.id.tv_examinfo);
        tvExamTitle=(TextView) findViewById(R.id.tv_exam_title);
        dialog=(ProgressBar) findViewById(R.id.load_dialog);
        tvOp1=(TextView) findViewById(R.id.tv_op1);
        tvOp2=(TextView) findViewById(R.id.tv_op2);
        tvOp3=(TextView) findViewById(R.id.tv_op3);
        tvOp4=(TextView) findViewById(R.id.tv_op4);
        tvLoad=(TextView)findViewById(R.id.tv_load);
        mImageView=(ImageView) findViewById(R.id.im_exam_image);
        layoutLoading.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                loadData();

            }
        });
    }

    private void initData() {
        if (isLoadQuestionsReceiver&isLoadExamInfoReceiver){
            if (isLoadExamInfo && isLoadQuestions) {
                layoutLoading.setVisibility(View.GONE);
                ExamInfo examInfo = ExamApplication.getInstance().getmExamInfo();
                 if (examInfo != null) {
                     showData(examInfo);
                 }

               // List<Question> examList = ExamApplication.getInstance().getmExamList();
                //if (examList != null) {
                showExam(biz.getExam());
              //  }
            }
            else{
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tvLoad.setText("下载失败,点击重新下载");

            }
        }

    }
    private void showExam(Question exam) {

        if (exam != null) {
            tvNo.setText(biz.getExamIndex());
            tvExamTitle.setText(exam.getQuestion());
            tvOp1.setText(exam.getItem1());
            tvOp2.setText(exam.getItem2());
            tvOp3.setText(exam.getItem3());
            tvOp4.setText(exam.getItem4());
            if (exam.getUrl() != null && !exam.getUrl().equals("")) {
                Picasso.with(ExamActivity.this)
                        .load(exam.getUrl())
                        .into(mImageView);
            }
            else {
                mImageView.setVisibility(View.GONE);
            }

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
    public void preExam(View view){
        showExam(biz.preQuestion());
    }
    public void nextExam(View view){
        showExam(biz.nextQuestion());
    }
    class LoadExamBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            //Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess="+isSuccess);
           //Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess="+new Intent().getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSuccess="+new Intent().getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
            if(isSuccess){
                isLoadExamInfo =true;
            }
            isLoadExamInfoReceiver=true;
            initData();
        }
    }

   class LoadQuestionBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSuccess="+isSuccess);
            if(isSuccess){
                isLoadQuestions=true;
            }
            isLoadQuestionsReceiver=true;
            initData();
        }
    }

}
