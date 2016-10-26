package com.jju.yuxin.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.jju.yuxin.musicplayer.R;
import com.jju.yuxin.musicplayer.utils.ActivityCollector;
import com.jju.yuxin.musicplayer.utils.MyLogger;

/**
 *=============================================================================
 *
 * Copyright (c) 2016  yuxin rights reserved.
 *
 * ClassName BaseActivity
 *
 * Created by yuxin.
 *
 * Created time 24-09-2016 00:29.
 *
 * Describe :项目中所有Activity的父类，用于管理通用的属性与方法
 *
 * History:BaseActivity用于控制所有activity
 *
 * Version   1.0.
 *
 *==============================================================================
 */
public abstract class BaseActivity extends AppCompatActivity {

    
    public MyLogger hlog;
    private RelativeLayout rlcontent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //日志管理
        hlog= MyLogger.hLog();

        //打印输出当前所在activity
        hlog.i("--------------"+getClass().getSimpleName()+"--------------");
        //将当前activity添加置活动管理器
        ActivityCollector.addActivity(this);
        //设置activity切换动画
        overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //当执行onDestroy()方法，将activity从活动管理器中移除
        ActivityCollector.removeActivity(this);

    }

    //执行finish时的动画
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_left_right,R.anim.anim_out_left_right);
    }
    //当调用startactiityforresult时的动画
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);

    }
}