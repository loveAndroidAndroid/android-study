package com.rmyh.textviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rmyh.textviewdemo.view.NumberRunningTextView;

public class ChangeActivity extends AppCompatActivity {

    private NumberRunningTextView tvMoney;
    private NumberRunningTextView tvNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        tvMoney = (NumberRunningTextView) findViewById(R.id.tv_money);
        tvNum = (NumberRunningTextView) findViewById(R.id.tv_num);
        tvMoney.setContent("1454.00");
        tvNum.setContent("300");
    }
}
