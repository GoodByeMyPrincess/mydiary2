package com.example.hp.mydiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.mydiary.UserInformation.User;
import com.example.hp.mydiary.Utils.DoubleExit;

public class ForgetPasswordActivity extends AppCompatActivity {
    private TextView mQuestionTextView;
    private EditText mAnswerEditText;
    private Button mConfirmButton;
    private User mUser;
    private DoubleExit mDoubleExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        TextView mToolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title);

        mToolbarTitleTextView.setText("重置密码");


        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        mQuestionTextView = (TextView) findViewById(R.id.tv_question);
        mAnswerEditText = (EditText) findViewById(R.id.ed_answer);
        mConfirmButton = (Button) findViewById(R.id.btn_confirm);
        mUser = DiaryLab.get(this).getUserInformation();

        mQuestionTextView.setText(mUser.getQuestion());

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String answer = mAnswerEditText.getText().toString();
                if (mUser.getAnswer().equals(answer)) {
                    mUser = new User();
                    mUser.setSetPassword(false);
                    DiaryLab.get(ForgetPasswordActivity.this).updateUserInformation(mUser);
                    Toast.makeText(ForgetPasswordActivity.this, "密码已取消", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgetPasswordActivity.this, DiaryListActivity.class);
                    finish();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ForgetPasswordActivity.this, "答案错误", Toast.LENGTH_SHORT).show();
                    mAnswerEditText.setText("");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        mDoubleExit = new DoubleExit();
        mDoubleExit.onBackPressed(this);
    }
}
