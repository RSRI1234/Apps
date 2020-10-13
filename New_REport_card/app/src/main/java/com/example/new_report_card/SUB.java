package com.example.new_report_card;

import android.view.ViewDebug;

public class SUB {
    private int mmarks;
    private String msubject;
    public SUB (String sub,int mark)
    {
        mmarks=mark;
        msubject=sub;
    }


    public String getMark(){return String.valueOf(mmarks);};
    public String getSubject(){return msubject;};
}
