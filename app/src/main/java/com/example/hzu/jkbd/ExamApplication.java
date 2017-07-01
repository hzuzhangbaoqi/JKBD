package com.example.hzu.jkbd;

import android.app.Application;
import android.util.Log;

import com.example.hzu.jkbd.bean.ExamInfo;
import com.example.hzu.jkbd.bean.Question;
import com.example.hzu.jkbd.bean.Result;
import com.example.hzu.jkbd.biz.ExamBiz;
import com.example.hzu.jkbd.biz.IExamBiz;
import com.example.hzu.jkbd.utils.OkHttpUtils;
import com.example.hzu.jkbd.utils.ResultUtils;
import com.example.hzu.jkbd.bean.Question;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    public static String LOAD_EXAM_INFO="load_exam_info";
    public static String LOAD_EXAM_QUESTION="load_exam_qusetion";
    public static String LOAD_DATA_SUCCESS="load_data_success";
    ExamInfo mExamInfo;
    List<Question> mExamList;
    private static ExamApplication instance;
    IExamBiz biz;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        biz=new ExamBiz();
        //initData();

    }
    public static ExamApplication getInstance(){
        return instance;
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
