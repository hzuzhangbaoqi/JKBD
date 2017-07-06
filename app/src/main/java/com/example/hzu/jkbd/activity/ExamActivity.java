package com.example.hzu.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hzu.jkbd.ExamApplication;
import com.example.hzu.jkbd.R;
import com.example.hzu.jkbd.bean.ExamInfo;
import com.example.hzu.jkbd.bean.Question;
import com.example.hzu.jkbd.biz.ExamBiz;
import com.example.hzu.jkbd.biz.IExamBiz;
import com.example.hzu.jkbd.view.QuestionAdapter;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.onClick;


/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity {
    CheckBox[] cbs = new CheckBox[4];
    TextView[] tvOps=new TextView[4];
    QuestionAdapter mAdapter;
    IExamBiz biz;
    boolean isLoadExamInfo = false;
    boolean isLoadQuestions = false;
    boolean isLoadExamInfoReceiver = false;
    boolean isLoadQuestionsReceiver = false;

    LoadQuestionBroadcast mLoadQuestionBroadcast;
    LoadExamBroadcast mLoadExamBroadcast;
    @BindView(R.id.load_dialog) ProgressBar dialog;
    @BindView(R.id.tv_load) TextView tvLoad;
    @BindView(R.id.layout_loading) LinearLayout layoutLoading;
    @BindView(R.id.tv_examinfo) TextView tvExamInfo;
    @BindView(R.id.tv_time) TextView tvTime;
    @BindView(R.id.tv_exam_no) TextView tvNo;
    @BindView(R.id.tv_exam_title) TextView tvExamTitle;
    @BindView(R.id.im_exam_image) ImageView mImageView;
    @BindView(R.id.tv_op1) TextView tvOp1;
    @BindView(R.id.tv_op2) TextView tvOp2;
    @BindView(R.id.tv_op3) TextView tvOp3;
    @BindView(R.id.layout_03) LinearLayout layout03;
    @BindView(R.id.tv_op4) TextView tvOp4;
    @BindView(R.id.layout_04) LinearLayout layout04;
    @BindView(R.id.cb_01) CheckBox cb01;
    @BindView(R.id.cb_02) CheckBox cb02;
    @BindView(R.id.cb_03) CheckBox cb03;
    @BindView(R.id.cb_04) CheckBox cb04;
    @BindView(R.id.gallery) Gallery mGallery;
    @BindView(R.id.btn_next) Button btnNext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        ButterKnife.bind(this);
        mLoadExamBroadcast = new LoadExamBroadcast();
        mLoadQuestionBroadcast = new LoadQuestionBroadcast();
        setListener();
        initView();
        loadData();
        biz = new ExamBiz();

    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast, new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast, new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        biz = new ExamBiz();
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
        //layoutLoading = (LinearLayout) findViewById(R.id.layout_loading);
//        layout03 = (LinearLayout) findViewById(R.id.layout_03);
//        layout04 = (LinearLayout) findViewById(R.id.layout_04);
//        tvNo = (TextView) findViewById(R.id.tv_exam_no);
        //tvExamInfo = (TextView) findViewById(R.id.tv_examinfo);
//        tvExamTitle = (TextView) findViewById(R.id.tv_exam_title);
//        dialog = (ProgressBar) findViewById(R.id.load_dialog);
//        mGallery = (Gallery) findViewById(R.id.gallery);
//        tvOp1 = (TextView) findViewById(R.id.tv_op1);
//        tvOp2 = (TextView) findViewById(R.id.tv_op2);
//        tvOp3 = (TextView) findViewById(R.id.tv_op3);
//        tvOp4 = (TextView) findViewById(R.id.tv_op4);
//        cb01 = (CheckBox) findViewById(R.id.cb_01);
//        cb02 = (CheckBox) findViewById(R.id.cb_02);
//        cb03 = (CheckBox) findViewById(R.id.cb_03);
//        cb04 = (CheckBox) findViewById(R.id.cb_04);
        cbs[0] = cb01;
        cbs[1] = cb02;
        cbs[2] = cb03;
        cbs[3] = cb04;
        tvOps[0]=tvOp1;
        tvOps[1]=tvOp2;
        tvOps[2]=tvOp3;
        tvOps[3]=tvOp4;
//        tvLoad = (TextView) findViewById(R.id.tv_load);
//        mImageView = (ImageView) findViewById(R.id.im_exam_image);
        /*layoutLoading.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadData();

            }
        });*/
        cb01.setOnCheckedChangeListener(listener);
        cb02.setOnCheckedChangeListener(listener);
        cb03.setOnCheckedChangeListener(listener);
        cb04.setOnCheckedChangeListener(listener);


    }
    @OnClick(R.id.layout_loading) void onLoadClick(){
        loadData();
    }
    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                int userAnswer = 0;
                switch (buttonView.getId()) {
                    case R.id.cb_01:
                        userAnswer = 1;
                        break;
                    case R.id.cb_02:
                        userAnswer = 2;
                        break;
                    case R.id.cb_03:
                        userAnswer = 3;
                        break;
                    case R.id.cb_04:
                        userAnswer = 4;
                        break;
                }
                if (userAnswer > 0) {
                    for (CheckBox cb : cbs) {
                        cb.setChecked(false);
                    }
                    cbs[userAnswer - 1].setChecked(true);
                }
            }
        }
    };

    private void initData() {
        if (isLoadQuestionsReceiver & isLoadExamInfoReceiver) {
            if (isLoadExamInfo && isLoadQuestions) {
                layoutLoading.setVisibility(View.GONE);
                ExamInfo examInfo = ExamApplication.getInstance().getmExamInfo();
                if (examInfo != null) {
                    showData(examInfo);
                    initTimer(examInfo);
                }

                // List<Question> examList = ExamApplication.getInstance().getmExamList();
                //if (examList != null) {
                initGallery();
                showExam(biz.getExam());

                //  }
            } else {
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tvLoad.setText("下载失败,点击重新下载");

            }
        }

    }

    private void initGallery() {
        mAdapter = new QuestionAdapter(this);
        mGallery.setAdapter(mAdapter);
        mGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                saveUserAnswer();
                showExam(biz.getExam(position));
            }
        });
    }

    private void initTimer(ExamInfo examInfo) {
        int sumTime = examInfo.getLimitTime() * 60 * 1000;
        final long overTime = (long) (sumTime + System.currentTimeMillis());
        final Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long l = overTime - System.currentTimeMillis();
                final long min = (long) (l / 1000 / 60);
                final long sec = (long) (l / 1000 % 60);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText("剩余时间:" + min + "分" + sec + "秒");
                    }
                });
            }
        }, 0, 1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commit(null);
                    }
                });
            }
        }, sumTime);
    }


    private void showExam(Question exam) {
        //Log.e("123","exxam="+exam);
        if (exam != null) {
            tvNo.setText(biz.getExamIndex());
            tvExamTitle.setText(exam.getQuestion());
            tvOp1.setText(exam.getItem1());
            tvOp2.setText(exam.getItem2());
            tvOp3.setText(exam.getItem3());
            tvOp4.setText(exam.getItem4());

            layout03.setVisibility(exam.getItem3().equals("") ? View.GONE : View.VISIBLE);
            cb03.setVisibility(exam.getItem3().equals("") ? View.GONE : View.VISIBLE);
            layout04.setVisibility(exam.getItem4().equals("") ? View.GONE : View.VISIBLE);
            cb04.setVisibility(exam.getItem4().equals("") ? View.GONE : View.VISIBLE);
            tvTime = (TextView) findViewById(R.id.tv_time);
            //Log.e("image","exam"+exam.getUrl());
            if (exam.getUrl() != null && !exam.getUrl().equals("")) {
                mImageView.setVisibility(View.VISIBLE);
                Picasso.with(ExamActivity.this)
                        .load(exam.getUrl())
                        .into(mImageView);
                //Log.e("image","exam"+exam.getUrl());
            } else {
                mImageView.setVisibility(View.GONE);
            }
            resetOptions();
            String userAnswer = exam.getUserAnswer();
            if (userAnswer != null && !userAnswer.equals("")) {
                int userCB = Integer.parseInt(userAnswer) - 1;
                cbs[userCB].setChecked(true);
                setOptions(true);
                setAnswerTextColor(userAnswer,exam.getAnswer());
            } else {
                setOptions(false);
                setOptionsColor();
            }
        }
    }

    private void setOptionsColor() {
        for (TextView tvOp : tvOps) {
            tvOp.setTextColor(getResources().getColor(R.color.black));
        }
    }

    private void setAnswerTextColor(String userAnswer, String answer) {
        int ra=Integer.parseInt(answer)-1;
        for (int i = 0; i < tvOps.length; i++) {
            if(i==ra){
                tvOps[i].setTextColor(getResources().getColor(R.color.green));
            }
            else {
                if((Integer.parseInt(userAnswer)-1)==i && !userAnswer.equals(answer)){
                    //int ua=Integer.parseInt(userAnswer)-1;
                    tvOps[i].setTextColor(getResources().getColor(R.color.red));
                }else{
                    tvOps[i].setTextColor(getResources().getColor(R.color.black));
                }
            }
        }

    }


    private void setOptions(boolean hasAnswer) {
        for (CheckBox cb : cbs) {
            cb.setEnabled(!hasAnswer);
        }
    }

    private void resetOptions() {
        for (CheckBox cb : cbs) {
            cb.setChecked(false);
        }
    }

    private void saveUserAnswer() {

        for (int i = 0; i < cbs.length; i++) {

            if (cbs[i].isChecked()) {
                biz.getExam().setUserAnswer(String.valueOf(i + 1));
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
        Log.e("123123", "Dao" + biz.getExam());
        biz.getExam().setUserAnswer("");
        mAdapter.notifyDataSetChanged();
        Log.e("456789", "Dao" + biz.getExam());
    }

    private void showData(ExamInfo examInfo) {
        tvExamInfo.setText(examInfo.toString());
    }

    protected void onDestroy() {
        super.onDestroy();
        if (mLoadExamBroadcast != null) {
            unregisterReceiver(mLoadExamBroadcast);
        }
        if (mLoadQuestionBroadcast != null) {
            unregisterReceiver(mLoadQuestionBroadcast);
        }
    }

    public void preExam(View view) {
        saveUserAnswer();
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        saveUserAnswer();
        showExam(biz.nextQuestion());
    }

    public void commit(View view) {
        saveUserAnswer();
        int s = biz.commitExam();

        View inflate = View.inflate(this, R.layout.layout_result, null);
        TextView tvResult = (TextView) inflate.findViewById(R.id.tv_result);
        tvResult.setText("你的分数为\n" + s + "分!");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.exam_commit32x32)
                .setTitle("交卷")
                .setView(inflate)
                //.setMessage("你的分数为\n"+s+"分!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.create().show();


    }

    class LoadExamBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            //Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess="+isSuccess);
            //Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess="+new Intent().getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
            Log.e("LoadQuestionBroadcast", "LoadQuestionBroadcast,isSuccess=" + new Intent().getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false));
            if (isSuccess) {
                isLoadExamInfo = true;
            }
            isLoadExamInfoReceiver = true;
            initData();
        }
    }

    class LoadQuestionBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            Log.e("LoadQuestionBroadcast", "LoadQuestionBroadcast,isSuccess=" + isSuccess);
            if (isSuccess) {
                isLoadQuestions = true;
            }
            isLoadQuestionsReceiver = true;
            initData();
        }
    }

}
