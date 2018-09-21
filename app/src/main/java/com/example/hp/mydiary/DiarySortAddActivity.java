package com.example.hp.mydiary;

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

public class DiarySortAddActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView mToolbarTitleTextView;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_sort_add);

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mToolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title);
        mEditText = (EditText) findViewById(R.id.ed_diary_sort);

        mToolbarTitleTextView.setText("添加新的分类");


        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_diary_sort, menu);
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
                    Toast.makeText(this, "名称不能为空！",Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    DiarySort diarySort = new DiarySort(diarySortName, new Date());
                    DiaryLab.get(this).addDiarySort(diarySort);
                    Toast.makeText(this, "创建成功！",Toast.LENGTH_SHORT).show();
                    finish();
                    return true;
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
