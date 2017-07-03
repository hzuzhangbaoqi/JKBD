package com.example.hzu.jkbd.biz;

import com.example.hzu.jkbd.ExamApplication;
import com.example.hzu.jkbd.bean.Question;
import com.example.hzu.jkbd.dao.ExamDao;
import com.example.hzu.jkbd.dao.IExamDao;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamBiz implements IExamBiz{
    IExamDao dao;
    int examIndex =0;
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
        return ExamApplication.getInstance().getmExamList().get(examIndex);
    }

    @Override
    public void nextQuestion() {

    }

    @Override
    public void preQuestion() {

    }

    @Override
    public void commitExam() {

    }
}
