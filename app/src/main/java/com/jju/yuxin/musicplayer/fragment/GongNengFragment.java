package com.jju.yuxin.musicplayer.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jju.yuxin.musicplayer.R;
import com.jju.yuxin.musicplayer.adapter.ZuiJinAdapter;
import com.jju.yuxin.musicplayer.domain.Music_;
import com.jju.yuxin.musicplayer.service.Music_Play_Service;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static android.util.Log.e;

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

public class GongNengFragment extends BaseFragment {

    private ListView lv_zuijin;
    private List<Music_> lv_music;
    private ZuiJinAdapter adapte;
    private Music_Play_Service play_service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lv_music = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frament_gongeng, container, false);
        lv_zuijin = (ListView) view.findViewById(R.id.lv_zuijin);
        play_service = new Music_Play_Service();
//        play_service.SetOnMusicChangeListener(new Music_Play_Service.OnMusicChangeListener() {
//            @Override
//            public void addNewMusic(Music_ music_) {
//                if (lv_music.contains(music_)){
//                    lv_music.remove(music_);
//                }
//                lv_music.add(music_);
//                adapte.notifyDataSetChanged();
//            }
//        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        lv_music=DataSupport.findAll(Music_.class);
        adapte = new ZuiJinAdapter(getContext(), lv_music);
        lv_zuijin.setAdapter(adapte);
        System.out.println("AAAAAAAAAA____onResume");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        System.out.println("AAAAAAAAAA____onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("AAAAAAAAAA____onStart");
    }


    @Override
    public void onPause() {
        super.onPause();
        System.out.println("AAAAAAAAAA____onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("AAAAAAAAAA____onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("AAAAAAAAAA____onDestroyView");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("AAAAAAAAAA____onDetach");
    }
}
