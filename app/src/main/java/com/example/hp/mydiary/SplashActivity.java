package com.example.hp.mydiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hp.mydiary.UserInformation.User;
import com.xwray.passwordview.PasswordView;

import java.lang.reflect.Field;

public class SplashActivity extends AppCompatActivity {
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ConstraintLayout splash_layout = (ConstraintLayout) findViewById(R.id.activity_splash);
        ImageView mDiary_image1 = (ImageView) findViewById(R.id.imageView_diary1);
        ImageView mDiary_image2 = (ImageView) findViewById(R.id.imageView_diary2);

        mUser = DiaryLab.get(this).getUserInformation();

        AlphaAnimation alphaAnimation1 = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation1.setDuration(2000);
        mDiary_image1.startAnimation(alphaAnimation1);
        mDiary_image2.startAnimation(alphaAnimation1);

        alphaAnimation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mUser.getSetPassword()) {
                    View view = getLayoutInflater().inflate(R.layout.dialog_view, null);
                    final PasswordView editText = (PasswordView) view.findViewById(R.id.password_login);
                    final AlertDialog dialog = new AlertDialog.Builder(SplashActivity.this)
                            .setIcon(R.mipmap.login_password)
                            .setTitle("登录")
                            .setView(view)
                            .setCancelable(false)
                            .setNeutralButton("忘记密码", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    Intent intent = new Intent(SplashActivity.this, ForgetPasswordActivity.class);
                                    finish();
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String content = editText.getText().toString();
                                    if (content.equals(mUser.getPassword())) {
                                        Intent intent = new Intent(SplashActivity.this, DiaryListActivity.class);
                                        finish();
                                        startActivity(intent);
                                    } else {
                                        editText.setText("");
                                        Toast.makeText(SplashActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                        try {
                                            Field field = dialog.getClass().getSuperclass()
                                                    .getDeclaredField("mShowing");
                                            field.setAccessible(true);
                                            field.set(dialog, false);
                                        } catch (Exception e) {
                                            Log.e(e.getMessage(), null);
                                            e.printStackTrace();
                                        }

                                    }

                                }
                            }).create();
                    dialog.show();
                } else {
                    Intent intent = new Intent(SplashActivity.this, DiaryListActivity.class);
                    finish();
                    startActivity(intent);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
