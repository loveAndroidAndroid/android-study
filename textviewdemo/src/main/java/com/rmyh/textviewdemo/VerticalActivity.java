package com.rmyh.textviewdemo;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rmyh.textviewdemo.view.ScrollTextView;

public class VerticalActivity extends AppCompatActivity {
    private ScrollTextView scrollTextView;
    private static int count = 0;
    private boolean isContinue;
    private Thread myThread;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    scrollTextView.next();
                    count++;
                    scrollTextView.setText("这是第" + count + "次滚动");
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical);
        init();
    }

    private void init() {
        scrollTextView = (ScrollTextView) findViewById(R.id.switcher02);
        scrollTextView.setText("初始值");// 设置初始值
        isContinue = true;
        myThread = new Thread() {
            public void run() {
                while (isContinue) {
                    SystemClock.sleep(3000);
                    handler.sendEmptyMessage(0);// 每隔一秒 发一个空消息 滚动一次
                }
            };
        };
        myThread.start();
    }

    /**
     * 在销毁的时候干掉线程
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isContinue = false;
        if (myThread != null && myThread.isAlive()) {
            myThread.interrupt();
        }
    }
}
