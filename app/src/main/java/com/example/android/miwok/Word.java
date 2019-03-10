package com.example.android.miwok;

public class Word {

    //default translation for the the word
    private String mDefaultTranslation;

     //miwok translation for word
    private String mMiwokTranslation;

    //miwok image
    private int mImageMiwok;

    //miwok Audio
    private int mAudio;

    public Word(String defaultTranslation,String miwokTranslation,int audio)
    {
        mDefaultTranslation=defaultTranslation;

        mAudio=audio;

        mMiwokTranslation=miwokTranslation;

    }

    public Word(String defaultTranslation,String miwokTranslation,int imageMiwok,int audio)
    {
        mDefaultTranslation=defaultTranslation;

        mImageMiwok=imageMiwok;

        mMiwokTranslation=miwokTranslation;

        mAudio=audio;

    }

    //get the defaultTranslation of the word */

    public String getmDefaultTranslation()
    {
        return mDefaultTranslation;
    }

    //get the miwokTranslation of the word*/

    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }

    //get thr miwol image*/

    public int getmImageMiwok()
    {
        return mImageMiwok;
    }

    //    get miwok Audio
    public int getAudio()
    {
        return mAudio;
    }
}

