package com.jju.yuxin.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.jju.yuxin.musicplayer.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 *=============================================================================
 *
 * Copyright (c) 2016  yuxin rights reserved.
 * ClassName LoadingActivty
 * Created by yuxin.
 * Created time 17-10-2016 10:51.
 * Describe : 启动页
 * History:
 * Version   1.0.
 *
 *==============================================================================
 */
public class LoadingActivty extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_layout);

        //给个定时器,在1000后自动从,启动页跳到界面一
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent =new Intent(LoadingActivty.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }
}
