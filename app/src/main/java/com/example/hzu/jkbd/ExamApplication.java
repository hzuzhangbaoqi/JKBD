package com.example.hzu.jkbd;

import android.app.Application;
import android.util.Log;

import com.example.hzu.jkbd.bean.ExamInfo;
import com.example.hzu.jkbd.bean.Question;
import com.example.hzu.jkbd.utils.OkHttpUtils;

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
