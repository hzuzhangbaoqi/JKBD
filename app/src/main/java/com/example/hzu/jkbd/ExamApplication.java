package com.example.hzu.jkbd;

import android.app.Application;
import android.util.Log;

import com.example.hzu.jkbd.bean.ExamInfo;
import com.example.hzu.jkbd.bean.Question;
import com.example.hzu.jkbd.bean.Result;
import com.example.hzu.jkbd.utils.OkHttpUtils;
import com.example.hzu.jkbd.utils.ResultUtils;
import com.example.hzu.jkbd.bean.Question;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    ExamInfo mExamInfo;
    List<Question> mExamList;
    private static ExamApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initData();

    }
    public static ExamApplication getInstance(){
        return instance;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils<ExamInfo> utils = new OkHttpUtils<>(getApplicationContext());
                String url = "http://101.251.196.90:8080/JztkServer/examInfo";
                utils.url(url)
                        .targetClass(ExamInfo.class)
                        .execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
                            public void onSuccess(ExamInfo result) {
                                Log.e("main", "result=" + result);
                                mExamInfo=result;
                            }
                            public void onError(String error) {
                                Log.e("main", "error=" + error);
                            }
                        });
                OkHttpUtils<String> utils1=new OkHttpUtils<>(instance);
                String url2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils1.url(url2)
                        .targetClass(String.class)
                        .execute(new OkHttpUtils.OnCompleteListener<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Result result1= ResultUtils.getListResultFromJson(result);
                                if(result1!= null && result1.getError_code()==0){
                                    List<Question> list= result1.getResult();
                                    if(list!=null && list.size()>0){
                                      mExamList=list;
                                    }
                                }

                            }

                            public void onError(String error) {
                                Log.e("main", "result=" + error);
                            }
                        });
            }
        }).start();

    }

    public ExamInfo getmExamInfo() {
        return mExamInfo;
    }

    public void setmExamInfo(ExamInfo mExamInfo) {
        this.mExamInfo = mExamInfo;
    }

    public List<Question> getmExamList() {
        return mExamList;
    }

    public void setmExamList(List<Question> mExamList) {
        this.mExamList = mExamList;
    }
}
