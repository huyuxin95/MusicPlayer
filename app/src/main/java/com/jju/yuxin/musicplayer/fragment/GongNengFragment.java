package com.jju.yuxin.musicplayer.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jju.yuxin.musicplayer.R;
import com.jju.yuxin.musicplayer.activity.MainActivity;
import com.jju.yuxin.musicplayer.adapter.ZuiJinAdapter;
import com.jju.yuxin.musicplayer.domain.Music_;
import com.jju.yuxin.musicplayer.service.Music_Play_Service;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
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
 * Describe :  最近播放列表
 * History:
 * ==============================================================================
 */

public class GongNengFragment extends BaseFragment {

    private ListView lv_zuijin;
    private List<Music_> lv_music;
    private ZuiJinAdapter adapte;
    private Music_Play_Service play_service;
    private Music_ clickmusic;
    private MainActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lv_music = new ArrayList<>();
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frament_gongeng, container, false);
        lv_zuijin = (ListView) view.findViewById(R.id.lv_zuijin);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //查询数据库中所有已播放的歌曲
        lv_music = DataSupport.findAll(Music_.class);

        adapte = new ZuiJinAdapter(getContext(), lv_music);
        lv_zuijin.setAdapter(adapte);
        //列表点击事件
        lv_zuijin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取点击事件
                clickmusic=(Music_) adapte.getItem(position);
                Intent intent = new Intent("TingtingService");
                intent.putExtra("music", clickmusic);
                intent.putExtra("zuijing", 1);
                activity.sendBroadcast(intent);
                //跳转到第一界面
                activity.rbtabtingting.setChecked(true);
            }
        });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
