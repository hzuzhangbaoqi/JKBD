package com.example.hzu.jkbd.dao;

import android.content.Intent;
import android.util.Log;

import com.example.hzu.jkbd.ExamApplication;
import com.example.hzu.jkbd.bean.ExamInfo;
import com.example.hzu.jkbd.bean.Question;
import com.example.hzu.jkbd.bean.Result;
import com.example.hzu.jkbd.utils.OkHttpUtils;
import com.example.hzu.jkbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

        public class ExamDao implements IExamDao {

    @Override
    public void loadExamInfo() {
        OkHttpUtils<ExamInfo> utils = new OkHttpUtils<>(ExamApplication.getInstance());
        String url = "http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(url)
                .targetClass(ExamInfo.class)
                .execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
                    public void onSuccess(ExamInfo result) {
                        Log.e("main", "result=" + result);
                        ExamApplication.getInstance().setmExamInfo(result);
                        ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                                                     .putExtra(ExamApplication.LOAD_DATA_SUCCESS,true));
                    }
                    public void onError(String error) {
                        Log.e("main", "error=" + error);
                        ExamApplication.getInstance().sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_INFO)
                                .putExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
                    }
                });
    }

    @Override
    public void loadQusetionLists() {
                OkHttpUtils<String> utils1=new OkHttpUtils<>(ExamApplication.getInstance());
                String url="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils1.url(url)
                        .targetClass(String.class)
                        .execute(new OkHttpUtils.OnCompleteListener<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Result result1= ResultUtils.getListResultFromJson(result);
                                if(result1!= null && result1.getError_code()==0){
                                    List<Question> list= result1.getResult();
                                    if(list!=null && list.size()>0){
                                        Log.e("main", "resultQuestion=" + result);
                                       ExamApplication.getInstance().setmExamList(list);
                                    }
                                }
                            }

                            public void onError(String error) {
                                Log.e("main", "result=" + error);
                            }
                        });
    }
}
