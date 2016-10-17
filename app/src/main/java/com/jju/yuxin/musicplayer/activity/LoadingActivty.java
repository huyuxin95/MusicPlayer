package com.jju.yuxin.musicplayer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.jju.yuxin.musicplayer.R;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivty extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.loading_layout);

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent =new Intent(LoadingActivty.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
