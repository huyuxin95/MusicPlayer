package com.jju.yuxin.musicplayer.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.jju.yuxin.musicplayer.domain.Music_;

import java.io.IOException;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.musicplayer.service
 * Created by yuxin.
 * Created time 2016/10/16 0016 下午 2:14.
 * Version   1.0;
 * Describe : 控制音乐状态的服务
 * History:
 * ==============================================================================
 */

public class Music_Play_Service extends Service {

    private PlayService playService;
    private Music_ playmusic;
    //实例化MediaPlayer对象
    MediaPlayer player = new MediaPlayer();
    private int duration;
    private boolean isStop = false;
    private int curposition;
    private int state = 1;//1表示未播放,2表示正在播放,3表示停止


    @Override
    public void onCreate() {
        super.onCreate();
        //动态注册广播,用于接收来自播放界面发送个音乐控制
        playService = new PlayService();
        IntentFilter intentFilter = new IntentFilter("PlayService");
        registerReceiver(playService, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //当音乐播放结束,将当前的音乐状态发送给Tingting
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

    /**
     * 用于接收别的地方发送来的广播
     */
    private class PlayService extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //获取到要播放的音乐对象
            playmusic = intent.getParcelableExtra("palymusic");
            //用于判断是否来自Tingting的控制
            int contorl = intent.getIntExtra("contorl", 0);
            //获取当前音乐播放状态
            state = intent.getIntExtra("state", 1);
            //获取拖动条拖动到的进度位置
            int progress = intent.getIntExtra("progress", 0);
            //用于判断是否播放新的一首歌曲
            int new_music = intent.getIntExtra("new_music", 0);
            //从listview点入,执行播放新的首歌曲
            if (new_music == 1) {
                //因为是播放的新的歌曲,用广播发送当前歌曲给MusicReceiver
                Intent musciChange = new Intent("GongNengFragment");
                musciChange.putExtra("music", playmusic);
                musciChange.setPackage("com.jju.yuxin.musicplayer");
                sendBroadcast(musciChange);
                //将当前状态修改为播放状态
                Music_Play_Service.this.state = 2;
                //播放一首的新的歌曲
                play_();

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
            //当信息包含progress值,表示需要跳转到指定位置
            if (progress > 0) {
                player.seekTo(progress * 1000);
            }

        }

        /**
         * 播放一首的新的歌曲
         */
        private void play_() {
            try {
                player.stop();
                player.reset();
                player.setDataSource(String.valueOf(playmusic.getPath()));
                player.prepare();
                player.start();
                duration = player.getDuration();//获取总时长,毫秒
                /**
                 * 开启新的线程,用于发送每一秒的状态给播放界面,修改进度条和当前时间等状态
                 */
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        //服务开启后为死循环,直到服务注销
                        while (!isStop) {
                            try {
                                //等待一秒
                                sleep(1000);
                                //将当前音乐播放状态信息发送出去
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
                Toast.makeText(Music_Play_Service.this, "未找到要播放的音乐", Toast.LENGTH_SHORT).show();
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


}
