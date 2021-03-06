package com.example.hzu.jkbd.biz;

import com.example.hzu.jkbd.bean.Question;

/**
 * Created by Administrator on 2017/6/30.
 */

public interface IExamBiz {
    void beginExam();
    Question getExam();
    Question getExam(int index);
    Question nextQuestion();
    Question preQuestion();
    int commitExam();
    String getExamIndex();
}
