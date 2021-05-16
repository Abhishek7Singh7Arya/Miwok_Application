package com.example.miwok_app;

public class Word {
    private String mDefaultTranslation;

    private  String mMiwokTranslation;

    private int imgResourceId = NO_IMG_RESOURCE;

    private  static  final int  NO_IMG_RESOURCE =-1;

    private static final int NO_AUD_RESOURCE   = -1;
    int getAudioResourceId =NO_AUD_RESOURCE;

    public int getAudioResourceId() {
        return getAudioResourceId;
    }



    public int getImgResourceId() {
        return imgResourceId;
    }

    public Word(String defaultTranslation, String miwokTranslation , int src,int audioResourceId)  {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        imgResourceId = src;
        getAudioResourceId = audioResourceId;
    }

    public Word(String defaultTranslation, String miwokTranslation,int audioResourceId )  {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        getAudioResourceId = audioResourceId;

    }

    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public boolean hasImgResource() {
        return NO_IMG_RESOURCE != imgResourceId;
    }
}
