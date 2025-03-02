package com.example.testadapterviewapp;

import java.io.Serializable;

public class SongModel implements Serializable {

    private String mCode;
    private String mTitle;
    private String mLyric;
    private String mArtist;

    public SongModel(String mCode, String mTitle, String mLyric, String mArtist) {
        this.mCode = mCode;
        this.mTitle = mTitle;
        this.mLyric = mLyric;
        this.mArtist = mArtist;
    }

    public String getmCode() {
        return mCode;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmLyric() {
        return mLyric;
    }

    public String getmArtist() {
        return mArtist;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmLyric(String mLyric) {
        this.mLyric = mLyric;
    }

    public void setmArtist(String mArtist) {
        this.mArtist = mArtist;
    }


}
