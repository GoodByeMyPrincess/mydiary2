package com.example.hp.mydiary.Utils;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class DoubleExit extends AppCompatActivity{
    // 弹出提示框
    private static Toast toast;
    // 记录第一次按下的时间
    private static long firstPressTime = -1;
    // 记录第二次按下的时间
    private static long lastPressTime;
    // 两次按下的时间间隔
    private static final long INTERVAL = 2000;



    /**
     * 按下返回键的时候调用
     */
    public void onBackPressed(Context context) {
        toast = Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT);
        showQuitTips();
    }


    /**
     * 显示提示框
     */
    private void showQuitTips() {

        // 如果是第一次按下 直接提示
        if (firstPressTime == -1) {
            firstPressTime = System.currentTimeMillis();
            toast.show();

        }

        // 如果是第二次按下，需要判断与上一次按下的时间间隔，这里设置2秒
        else {

            lastPressTime = System.currentTimeMillis();
            if (lastPressTime - firstPressTime <= INTERVAL) {
                System.exit(0);
            } else {
                firstPressTime = lastPressTime;
                toast.show();
            }
        }
    }

   /** 作者：YungFan
    链接：https://www.jianshu.com/p/baeb60fbb053
    來源：简书
    简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
    */
}
