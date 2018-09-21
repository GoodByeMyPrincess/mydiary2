package com.example.hp.mydiary;

import java.util.Date;
import java.util.UUID;

public class Diary {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String mWeather;
    private String mDiaryText;
    private int mDiarySortId;

//1234
    public Diary() {
        this(UUID.randomUUID());
    }

    public Diary (UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getDiaryText() {
        return mDiaryText;
    }

    public void setDiaryText(String diaryText) {
        mDiaryText = diaryText;
    }

    public String getWeather() {
        return mWeather;
    }

    public void setWeather(String weather) {
        mWeather = weather;
    }


    public int getDiarySortId() {
        return mDiarySortId;
    }

    public void setDiarySortId(int diarySortId) {
        mDiarySortId = diarySortId;
    }
}
