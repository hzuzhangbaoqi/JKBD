package com.example.hzu.jkbd.biz;

import com.example.hzu.jkbd.dao.ExamDao;
import com.example.hzu.jkbd.dao.IExamDao;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamBiz implements IExamBiz{
    IExamDao dao;
    public ExamBiz(){
        this.dao= new ExamDao();
    }



    @Override
    public void beginExam() {
        dao.loadExamInfo();
        dao.loadQusetionLists();
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
