package com.jju.yuxin.musicplayer.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jju.yuxin.musicplayer.domain.Music_;

import java.io.IOException;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.musicplayer.service
 * Created by yuxin.
 * Created time 2016/10/16 0016 下午 2:14.
 * Version   1.0;
 * Describe :
 * History:
 * ==============================================================================
 */

public class Music_Play_Service extends Service {

    // private   OnMusicChangeListener listeners;
    private PlayService playService;
    private Music_ playmusic;
    MediaPlayer player = new MediaPlayer();
    private int duration;
    private boolean isStop = false;
    private int curposition;
    private int state = 1;//1表示未播放,2表示正在播放,3表示停止


    @Override
    public void onCreate() {
        super.onCreate();
        playService = new PlayService();
        IntentFilter intentFilter = new IntentFilter("PlayService");
        registerReceiver(playService, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent("TingtingService");
                intent.putExtra("music", playmusic);
                intent.putExtra("dur", duration);
                intent.putExtra("cur", curposition);
                intent.putExtra("over", true);
                sendBroadcast(intent);
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private class PlayService extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            playmusic = intent.getParcelableExtra("palymusic");
            int contorl = intent.getIntExtra("contorl", 0);
            state = intent.getIntExtra("state", 1);
            int progress = intent.getIntExtra("progress", 0);
            int new_music = intent.getIntExtra("new_music", 0);
            //从listview点入,执行播放新的首歌曲
            if (new_music == 1) {
                Intent musciChange = new Intent("GongNengFragment");
                musciChange.putExtra("music", playmusic);
                musciChange.setPackage("com.jju.yuxin.musicplayer");
                sendBroadcast(musciChange);
                Music_Play_Service.this.state = 2;
                play_();
                //向最近列表里面当前歌曲
//                Log.e("TAG", "onReceive" + listeners);
//                Music_Play_Service.this.listeners.addNewMusic(playmusic);

                //执行来自于按键控制的操作
            } else if (contorl == 1) {
                //如果当前音乐正在播放执行停止操作
                if (state == 2) {
                    state = 3;
                    player.pause();

                    //当前音乐为停止状态,执行播放操作
                } else if (state == 3) {
                    state = 2;
                    player.start();
                }
            }
            if (progress > 0) {
                player.seekTo(progress * 1000);
            }

        }

        private void play_() {
            try {
                player.stop();
                player.reset();
                player.setDataSource(String.valueOf(playmusic.getPath()));
                player.prepare();
                player.start();
                duration = player.getDuration();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        while (!isStop) {
                            try {
                                sleep(1000);
                                curposition = player.getCurrentPosition();
                                Intent intent = new Intent("TingtingService");
                                intent.putExtra("dur", duration);
                                intent.putExtra("cur", curposition);
                                intent.putExtra("music", playmusic);
                                intent.putExtra("state", state);
                                sendBroadcast(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isStop = true;
        unregisterReceiver(playService);
    }

//    public  void SetOnMusicChangeListener(OnMusicChangeListener listener){
//        this.listeners=listener;
//    }
//
//    public interface OnMusicChangeListener{
//       void addNewMusic(Music_ music_);
//    }

}
