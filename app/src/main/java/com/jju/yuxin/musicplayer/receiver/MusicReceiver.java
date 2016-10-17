package com.jju.yuxin.musicplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jju.yuxin.musicplayer.domain.Music_;

import org.litepal.crud.ClusterQuery;
import org.litepal.crud.DataSupport;

import static android.util.Log.e;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.musicplayer
 * Created by yuxin.
 * Created time 2016/10/16 0016 下午 10:41.
 * Version   1.0;
 * Describe :  用于接收带自服务发送的播放新的一首歌曲的广播,
 * History:
 * ==============================================================================
 */

public class MusicReceiver extends BroadcastReceiver {


    private static final String TAG = MusicReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取传递过来的新的播放歌曲
        Music_ music_ = intent.getParcelableExtra("music");
        //查询最近播放列表的数据库是否已经存在
        ClusterQuery clusterQuery = DataSupport.select("*").where("path=?", music_.getPath());
        //如果存在,那么将其删除
        if (clusterQuery!=null||clusterQuery.count(Music_.class)>0) {
             DataSupport.deleteAll(Music_.class,"path=?",music_.getPath());
        }
        //将新的歌曲保存在数据库最后的位置
        music_.save();
    }
}
