package com.fourapeteam.snacksmall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {


    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //第一：默认初始化
        Bmob.initialize(this, "d21d8f06a330550389d3a7c59cf2d41f");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,MallActivity.class);
                startActivity(intent);
                finish();
            }
        }, 300);

    }
}
