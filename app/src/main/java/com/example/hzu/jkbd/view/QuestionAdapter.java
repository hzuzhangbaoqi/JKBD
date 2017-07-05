package com.example.hzu.jkbd.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hzu.jkbd.ExamApplication;
import com.example.hzu.jkbd.R;
import com.example.hzu.jkbd.bean.Question;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class QuestionAdapter extends BaseAdapter {
    Context mContext;
    List<Question> examList;
    public QuestionAdapter(Context context){
        mContext=context;
         examList = ExamApplication.getInstance().getmExamList();
    }
    @Override
    public int getCount() {
        return examList==null?0:examList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(mContext, R.layout.item_question,null);
        TextView tvNo=(TextView)view.findViewById(R.id.tv_no);
        ImageView ivQuestion =(ImageView) view.findViewById(R.id.iv_question);
        String ua=examList.get(position).getUserAnswer();
        if (ua!=null&&!ua.equals("")){
            ivQuestion.setImageResource(R.mipmap.answer24x24);
        }else {
            ivQuestion.setImageResource(R.mipmap.ques24x24);
        }
        tvNo.setText("第"+(position+1)+"题");
        return view;
    }
}
