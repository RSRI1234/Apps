package com.example.android.quakereport;

public class eqClass {
    private String mplace;
    private long mTimeInMilliseconds;
    private double  mmagnitude;
    private String murl;
    public eqClass(double  mag, String place, long time,String url){
        mmagnitude=mag;
        mplace=place;
       mTimeInMilliseconds=time;
       murl=url;
    }
    public String getplace(){return mplace;}
    public double  getmagnitude(){return mmagnitude;}
    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }
    public  String geturl(){return murl;}
}
