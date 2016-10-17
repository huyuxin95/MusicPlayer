package com.jju.yuxin.musicplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jju.yuxin.musicplayer.R;
import com.jju.yuxin.musicplayer.activity.MainActivity;
import com.jju.yuxin.musicplayer.adapter.Music_Adapter;
import com.jju.yuxin.musicplayer.domain.Music_;
import com.jju.yuxin.musicplayer.utils.MusicUtil;

import java.util.List;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.musicplayer.fragment
 * Created by yuxin.
 * Created time 2016/10/16 0016 上午 12:44.
 * Version   1.0;
 * Describe :
 * History:
 * ==============================================================================
 */

public class QukuFragment extends BaseFragment {

    private ListView lv_music;
    private  static  OnListViewClickChangeListener listeners;
    private MainActivity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frament_quku, container, false);
        lv_music = (ListView) view.findViewById(R.id.lv_music);
        List<Music_> music = MusicUtil.getMusic(activity);
        final Music_Adapter adapter = new Music_Adapter(activity, music);
        lv_music.setAdapter(adapter);
        lv_music.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hlog.e("position"+position+"adapter"+adapter+"listener"+listeners);
                listeners.OnItemClicked((Music_) adapter.getItem(position));
                //跳转到界面一
                activity.rbtabtingting.setChecked(true);
            }
        });
        return view;
    }
    public static void SetOnListViewClickListener(OnListViewClickChangeListener listener){
        listeners=listener;
    }
    interface OnListViewClickChangeListener{
        public void OnItemClicked(Music_ music_);
    }
}
