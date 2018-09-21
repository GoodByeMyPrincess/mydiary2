package com.example.hp.mydiary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.mydiary.UserInformation.User;
import com.xwray.passwordview.PasswordView;

public class UserInformationActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mToolbarTitleTextView;
    private PasswordView mPassword;
    private PasswordView mQuestion;
    private PasswordView mAnswer;
    private CheckBox mIsSetPasswordCheckBox;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mToolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title);
        mPassword = (PasswordView) findViewById(R.id.set_password);
        mQuestion = (PasswordView) findViewById(R.id.set_question);
        mAnswer = (PasswordView) findViewById(R.id.set_answer);
        mIsSetPasswordCheckBox = (CheckBox) findViewById(R.id.cb_password_enable);
        mUser = DiaryLab.get(this).getUserInformation();

        updateUi(mUser.getSetPassword());

        mIsSetPasswordCheckBox.setChecked(mUser.getSetPassword());
        mIsSetPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mUser.setSetPassword(b);
                updateUi(b);
            }
        });

        mToolbarTitleTextView.setText("启用密码");


        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void updateUi(boolean b) {
        if (!b) {
            mPassword.setVisibility(View.INVISIBLE);
            mQuestion.setVisibility(View.INVISIBLE);
            mAnswer.setVisibility(View.INVISIBLE);
        } else {
            mPassword.setVisibility(View.VISIBLE);
            mQuestion.setVisibility(View.VISIBLE);
            mAnswer.setVisibility(View.VISIBLE);
        }
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
                mUser.setPassword(mPassword.getText().toString());
                mUser.setQuestion(mQuestion.getText().toString());
                mUser.setAnswer(mAnswer.getText().toString());
                DiaryLab.get(this).updateUserInformation(mUser);
                Toast.makeText(this, "修改成功!", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
