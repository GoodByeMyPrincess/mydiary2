package com.example.hp.mydiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.mydiary.Interface.IBackInterface;

import java.util.UUID;

import static com.example.hp.mydiary.DiaryFragment.EXTRA_DIARY_ID2;

public class DiaryActivity extends SingleFragmentActivity implements IBackInterface {
    private static final String EXTRA_DIARY_ID =
            "com.example.hp.mydiary.diary.id";

    private Fragment mFragment;
    private TextView mToolbarTitle;
    private boolean isAddNew;

    public static Intent newInstance(Context packageContext, UUID diaryId) {
        Intent intent = new Intent(packageContext, DiaryActivity.class);
        intent.putExtra(EXTRA_DIARY_ID, diaryId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarTitle.setText("写日记");
    }


    @Override
    protected Fragment createFragment() {
        UUID diaryId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_DIARY_ID);

        isAddNew = diaryId == null;

        return DiaryFragment.newInstance(diaryId);

    }

    @Override
    public void setSelectFragment(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void onBackPressed() {
        if (mFragment != null) {
            Diary mDiary = ((DiaryFragment) mFragment).onBackPressed();
            if (isAddNew) {
                if (mDiary.getTitle() == null && mDiary.getDiaryText() == null) {
                    Toast.makeText(this, "日记未保存!", Toast.LENGTH_SHORT).show();
                } else if (mDiary.getDiaryText() != null && mDiary.getTitle() == null
                        || mDiary.getTitle() == "") {
                    mDiary.setTitle("无标题");
                    DiaryLab.get(this).addDiary(mDiary);
                    Toast.makeText(this, "保存成功!", Toast.LENGTH_SHORT).show();
                } else {
                    DiaryLab.get(this).addDiary(mDiary);
                    Toast.makeText(this, "保存成功!", Toast.LENGTH_SHORT).show();
                }
            } else {
                DiaryLab.get(this).updateDiaries(mDiary);
            }
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

}
