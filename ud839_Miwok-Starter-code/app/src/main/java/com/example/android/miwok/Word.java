package com.example.android.miwok;

public class Word {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private static int no_image=-1;
    private int  mresid=-1;
    private int msongid;

    public Word(String DefaultTranslation,String MiwokTranslation,int songid)
    {
        mDefaultTranslation = DefaultTranslation;
        mMiwokTranslation = MiwokTranslation;
        msongid=songid;
    }
    public Word(String DefaultTranslation,String MiwokTranslation,int imageid,int songid)
    {
        mDefaultTranslation = DefaultTranslation;
        mMiwokTranslation = MiwokTranslation;
        mresid=imageid;
        msongid=songid;
    }
    public boolean hasimage(){return mresid!=no_image;}
    public int getImageID(){return mresid;}
    public String getDefaultTranslation(){ return mDefaultTranslation; }
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }
    public int getaudioid(){return msongid;}

}