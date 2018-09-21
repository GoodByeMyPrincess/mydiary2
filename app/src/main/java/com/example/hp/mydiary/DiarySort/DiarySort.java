package com.example.hp.mydiary.DiarySort;

import java.util.Date;

public class DiarySort {
    private int mDiarySortId;
    private String mDiarySortName;
    private Date mDate;



    public Date getDate() {
        return mDate;
    }

    public DiarySort (String name , Date date) {
        mDiarySortName = name;
        mDate = date;
    }

    public int getDiarySortId() {
        return mDiarySortId;
    }

    public void setDiarySortId(int diarySortId) {
        mDiarySortId = diarySortId;
    }

    public String getDiarySortName() {
        return mDiarySortName;
    }

    public void setDiarySortName(String diarySortName) {
        mDiarySortName = diarySortName;
    }
}
