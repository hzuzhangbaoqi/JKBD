package com.example.hzu.jkbd.biz;

import com.example.hzu.jkbd.bean.Question;

/**
 * Created by Administrator on 2017/6/30.
 */

public interface IExamBiz {
    void beginExam();
    Question getExam();
    Question nextQuestion();
    Question preQuestion();
    void commitExam();
    String getExamIndex();
}
