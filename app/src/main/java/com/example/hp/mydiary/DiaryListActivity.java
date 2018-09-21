package com.example.hp.mydiary;

import android.support.v4.app.Fragment;


import com.example.hp.mydiary.Utils.DoubleExit;


public class DiaryListActivity extends SingleFragmentActivity {
    private DoubleExit mDoubleExit;


    @Override
    protected Fragment createFragment() {

        return new DiaryListFragment();
    }

    @Override
    public void onBackPressed() {
        mDoubleExit = new DoubleExit();
        mDoubleExit.onBackPressed(this);
    }

}
