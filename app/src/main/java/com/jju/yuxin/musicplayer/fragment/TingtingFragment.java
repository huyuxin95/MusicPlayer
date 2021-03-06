package com.jju.yuxin.musicplayer.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jju.yuxin.musicplayer.R;
import com.jju.yuxin.musicplayer.activity.MainActivity;
import com.jju.yuxin.musicplayer.domain.Music_;
import com.jju.yuxin.musicplayer.utils.MusicUtil;

import java.util.List;

import static android.util.Log.e;
import static android.util.Log.i;


/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.musicplayer.fragment
 * Created by yuxin.
 * Created time 2016/10/16 0016 上午 12:44.
 * Version   1.0;
 * Describe : 听听界面fragment
 * History:
 * ==============================================================================
 */

public class TingtingFragment extends BaseFragment {

    private static final String TAG = TingtingFragment.class.getSimpleName();
    private TextView tvusername;
    private TextView tvname;
    private TextView tvauther;
    private TextView starttime;
    private SeekBar seek;
    private TextView endtime;
    private ImageButton ibup;
    private ImageButton ibplay;
    private ImageButton ibdown;
    private ImageButton ibmode;
    private Music_ cur_music; //当前播放的音乐
    private QukuFragment qukuFragment;
    private TingtingService tingtingService;
    private IntentFilter intentFilte;
    private MainActivity activity;
    private int state = 1;//1表示未播放,2表示正在播放,3表示停止
    private List<Music_> lv_musics;
    private int[] images = new int[]{R.drawable.playmode_repeate_all_pressed, R.drawable.playmode_repeate_random_pressed, R.drawable.playmode_repeate_single_pressed};
    private int modeindex = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //开启播放音乐的服务
        activity = (MainActivity) getActivity();
        Intent intentservice = new Intent("music_service");
        intentservice.setPackage("com.jju.yuxin.musicplayer");
        activity.startService(intentservice);

        //动态注册广播
        tingtingService = new TingtingService();
        intentFilte = new IntentFilter("TingtingService");
        activity.registerReceiver(tingtingService, intentFilte);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frament_tingting, container, false);
        initialize(view);
        qukuFragment = new QukuFragment();
        //获取到点击的歌曲
        qukuFragment.SetOnListViewClickListener(new QukuFragment.OnListViewClickChangeListener() {
            @Override
            public void OnItemClicked(Music_ music_) {
                cur_music = music_;
                play_();
            }
        });


        return view;
    }

    /**
     * 初始化控件和设置监听事件
     * @param view
     */
    private void initialize(View view) {
        lv_musics = MusicUtil.getMusic(activity); //获取所有歌曲
        Mylistener listener = new Mylistener();  //获取监听器对象
        tvusername = (TextView) view.findViewById(R.id.tv_username);//标题名
        tvname = (TextView) view.findViewById(R.id.tv_name);//歌曲名
        tvauther = (TextView) view.findViewById(R.id.tv_auther);//歌手名
        starttime = (TextView) view.findViewById(R.id.start_time);//当前时间
        seek = (SeekBar) view.findViewById(R.id.seek);//拖动条
        endtime = (TextView) view.findViewById(R.id.end_time);//总时间
        ibup = (ImageButton) view.findViewById(R.id.ib_up);//上一曲
        ibplay = (ImageButton) view.findViewById(R.id.ib_play);//播放
        ibdown = (ImageButton) view.findViewById(R.id.ib_down);//下一曲
        ibmode = (ImageButton) view.findViewById(R.id.ib_mode);//播放模式
        ibplay.setOnClickListener(listener);
        ibup.setOnClickListener(listener);
        ibdown.setOnClickListener(listener);
        ibmode.setOnClickListener(listener);
        seek.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    /**
     * 四个ImageView监听
     */
    private class Mylistener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_play:
                    //正在播放执行暂停操作
                    ib_play();
                    break;
                case R.id.ib_up:
                    //上一曲
                    play_pre();
                    break;
                case R.id.ib_down:
                    //下一曲
                    play_next();
                    break;
                case R.id.ib_mode:
                    //修改循环模式
                    changemode();
                    break;

                default:
                    break;
            }

        }
    }


    /**
     * 拖动条的监听事件
     */

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            //当拖动条,拖动结束的时候执行下面代码
            Intent intent = new Intent("PlayService");
            intent.putExtra("palymusic", cur_music);
            intent.putExtra("state", state);
            intent.putExtra("progress", seekBar.getProgress());
            activity.sendBroadcast(intent);
        }
    };

    /**
     * 控制按钮,播放或暂停
     */
    private void ib_play() {
        Intent play_intent = new Intent("PlayService");
        //当前是播放状态,切换到暂停状态
        if (state == 2) {
            ibplay.setBackgroundResource(R.drawable.selector_ib_play);
            //正在暂停执行播放操作
        } else if (state == 3) {
            ibplay.setBackgroundResource(R.drawable.selector_ib_play);
        }
        //第一次进入时,cur_music为空获取到列表的第一首歌
        if (cur_music == null) {
            cur_music = MusicUtil.getFirstMusic(activity);
            //如果当前没有播放歌曲,那么标志位新的播放一首歌曲
            play_intent.putExtra("new_music", 1);
        }
        play_intent.putExtra("state", state);
        //用于标志操作来自原按键控制
        play_intent.putExtra("contorl", 1);
        play_intent.putExtra("palymusic", cur_music);
        activity.sendBroadcast(play_intent);
    }

    /**
     * 获取发送的广播
     */
    private class TingtingService extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int dur = intent.getIntExtra("dur", 0);
            int cur = intent.getIntExtra("cur", 0);
            state = intent.getIntExtra("state", 1);
            //标志来自于最近列表的点击
            int zuijing = intent.getIntExtra("zuijing", 0);
            cur_music = intent.getParcelableExtra("music");
            //如果传过来的歌曲对象不为空
            if (cur_music != null) {
                tvname.setText(cur_music.getName());
                tvauther.setText(cur_music.getAuthor());
            }
            //设置歌曲时间
            starttime.setText(getTime(cur));
            endtime.setText(getTime(dur));
            //如果当前是播放歌曲状态,修改播放图片为暂停
            if (state == 2) {
                ibplay.setBackgroundResource(R.drawable.selector_ib_pause);
                //如果当前是暂停状态,修改播放图片为播放
            } else if (state == 3) {
                ibplay.setBackgroundResource(R.drawable.selector_ib_play);
            }
            seek.setMax(dur / 1000);
            seek.setProgress(cur / 1000);
            //监听当前的歌曲是否播放结束
            boolean over = intent.getBooleanExtra("over", false);
            //播放模式
            if (over) {
                switch (modeindex) {
                    //列表循环
                    case 0:
                        play_next();
                        break;
                    //随机循环
                    case 1:
                        int random = (int) (Math.random() * 1000);
                        Intent randomintent = new Intent("PlayService");
                        randomintent.putExtra("new_music", 1);
                        cur_music = lv_musics.get(((++random) % lv_musics.size()));
                        randomintent.putExtra("palymusic", cur_music);
                        activity.sendBroadcast(randomintent);
                        break;
                    //单曲播放
                    case 2:
                        Intent singleintent = new Intent("PlayService");
                        singleintent.putExtra("new_music", 1);
                        singleintent.putExtra("palymusic", cur_music);
                        activity.sendBroadcast(singleintent);
                        break;
                    default:
                        break;
                }
            }
            //如果是从最近列表发送过来的广播,那么执行播放接受的音乐
            if(zuijing==1){
                play_();
            }
        }
    }

    /**
     * 播放新的一首歌
     */
    public void play_() {
        Intent play_intent = new Intent("PlayService");
        play_intent.putExtra("palymusic", cur_music);

        //新的播放歌曲标志 1为新的歌曲
        play_intent.putExtra("new_music", 1);
        activity.sendBroadcast(play_intent);
    }

    /**
     * 播放下一曲
     */
    private void play_next() {
        if (cur_music != null) {
            int index = cur_music.getId();
            Intent intent = new Intent("PlayService");
            intent.putExtra("new_music", 1);
            cur_music = lv_musics.get(((++index) % lv_musics.size()));
            intent.putExtra("palymusic", cur_music);
            activity.sendBroadcast(intent);
        }
    }

    /**
     * 播放上一曲
     */
    private void play_pre() {
        if (cur_music != null) {
            int index = cur_music.getId();
            Intent intent = new Intent("PlayService");
            if (index == 0) {
                index = lv_musics.size();
            }
            cur_music = lv_musics.get((--index % lv_musics.size()));
            intent.putExtra("new_music", 1);
            intent.putExtra("palymusic", cur_music);
            activity.sendBroadcast(intent);
        }
    }

    /**
     * 切换播放模式
     */
    private void changemode() {
        switch (modeindex) {
            //切换到随机播放
            case 0:
                modeindex++;
                ibmode.setBackgroundResource(images[modeindex]);
                i(TAG, "changemode" + modeindex);
                break;
            //切换到单曲循环
            case 1:
                modeindex++;
                ibmode.setBackgroundResource(images[modeindex]);
                i(TAG, "changemode" + modeindex);
                break;
            //切换到列表循环
            case 2:
                modeindex++;
                ibmode.setBackgroundResource(images[modeindex % 3]);
                modeindex = 0;
                i(TAG, "changemode" + modeindex);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(tingtingService);
    }

    /**
     * 对时间字符串的拼接
     *
     * @param time
     * @return
     */
    public String getTime(int time) {
        int second = time / 1000 % 60;
        int minute = time / 1000 / 60 % 60;
        return zero_(minute) + ":" + zero_(second);

    }

    /**
     * 对时间零的处理
     * @param time
     * @return
     */
    public String zero_(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return time + "";
    }
}
