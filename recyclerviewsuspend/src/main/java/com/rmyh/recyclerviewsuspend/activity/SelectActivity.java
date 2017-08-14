package com.rmyh.recyclerviewsuspend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rmyh.recyclerviewsuspend.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SelectActivity extends AppCompatActivity {

    @InjectView(R.id.button1)
    Button button1;
    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.button4)
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                startActivity(PaddingActivity.class);
                break;
            case R.id.button2:
                startActivity(BottomActivity.class);
                break;
            case R.id.button3:
                startActivity(StickyActivity.class);
                break;
            case R.id.button4:
                startActivity(TotalActivity.class);
                break;
        }
    }
    private void startActivity(Class aClass){
        Intent intent = new Intent(SelectActivity.this,aClass);
        startActivity(intent);
    }
}
