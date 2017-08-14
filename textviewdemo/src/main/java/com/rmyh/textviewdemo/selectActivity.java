package com.rmyh.textviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class selectActivity extends AppCompatActivity {


    @InjectView(R.id.text1)
    Button text1;
    @InjectView(R.id.text2)
    Button text2;
    @InjectView(R.id.text3)
    Button text3;
    @InjectView(R.id.text4)
    Button text4;
    @InjectView(R.id.activity_select)
    LinearLayout activitySelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.text1, R.id.text2, R.id.text3, R.id.text4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text1:
                startActivity(RollActivity.class);
                break;
            case R.id.text2:
                startActivity(VerticalActivity.class);
                break;
            case R.id.text3:
                startActivity(ChangeActivity.class);
                break;
            case R.id.text4:
                Toast.makeText(selectActivity.this,"请点击博客链接富文本的使用",Toast.LENGTH_LONG).show();
                break;
        }
    }
    private void startActivity(Class aClass){
        Intent intent = new Intent(selectActivity.this,aClass);
        startActivity(intent);
    }
}
