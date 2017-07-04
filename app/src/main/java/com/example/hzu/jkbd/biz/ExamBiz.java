package com.example.hzu.jkbd.biz;

import android.util.Log;

import com.example.hzu.jkbd.ExamApplication;
import com.example.hzu.jkbd.bean.Question;
import com.example.hzu.jkbd.dao.ExamDao;
import com.example.hzu.jkbd.dao.IExamDao;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamBiz implements IExamBiz{
    IExamDao dao;
    int examIndex =0;
    List<Question> examList =null;
    public ExamBiz(){
        this.dao= new ExamDao();
    }

    @Override
    public void beginExam() {
        examIndex=0;
        dao.loadExamInfo();
        dao.loadQusetionLists();

    }

    @Override
    public Question getExam() {
        examList=ExamApplication.getInstance().getmExamList();
        if (examList != null) {
            return examList.get(examIndex);
        }else{
            return null;
        }
    }
    @Override
    public Question nextQuestion() {
        if (examList != null && examIndex<examList.size()-1) {
            examIndex++;
            return examList.get(examIndex);
        }else{
            return null;
        }
    }

    @Override
    public Question preQuestion() {
        if (examList != null && examIndex>0) {
            examIndex--;
            return examList.get(examIndex);
        }else{
            return null;
        }
    }

    @Override
    public int commitExam() {
        int s =0;
        for (Question question : examList) {
            String userAnswer = question.getUserAnswer();

            if (userAnswer!=null && !userAnswer.equals("")){
                if (question.getAnswer().equals(userAnswer)){

                    s++;
                }
            }
        }
        return s;
    }

    @Override
    public String getExamIndex() {
        return (examIndex+1)+".";
    }


}
