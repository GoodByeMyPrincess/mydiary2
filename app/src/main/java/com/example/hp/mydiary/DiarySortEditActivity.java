package com.example.hp.mydiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.mydiary.DiarySort.DiarySort;

import java.util.Date;

public class DiarySortEditActivity extends AppCompatActivity {
    public static final String EXTRA_DIARY_SORT =
            "com.example.hp.my_diary.diary_sort";

    private Toolbar mToolbar;
    private TextView mToolbarTitleTextView;
    private EditText mEditText;
    private DiarySort diarySort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_sort_edit);

        String diarySortName = getIntent().getStringExtra(EXTRA_DIARY_SORT);
        diarySort = DiaryLab.get(this).getDiarySort(diarySortName);

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mToolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title);
        mEditText = (EditText) findViewById(R.id.ed_diary_sort);

        mEditText.setText(diarySortName);

        mToolbarTitleTextView.setText("修改分类");


        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_diary_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_confirm:
                String diarySortName = mEditText.getText().toString();
                if (diarySortName == null || diarySortName.equals("")) {
                    Toast.makeText(this, "名称不能为空！", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    diarySort.setDiarySortName(diarySortName);
                    DiaryLab.get(this).updateDiarySort(diarySort);

                    Toast.makeText(this, "修改成功！", Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                }

            case R.id.menu_delete:

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("警告！")
                        .setMessage("该操作会删除该分类下所有日记！")
                        .setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                            }
                        })
                        .setPositiveButton("仍然删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DiaryLab.get(DiarySortEditActivity.this).deleteDiarySort(diarySort);
                                Toast.makeText(DiarySortEditActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                finish();
                            }
                        }).create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
