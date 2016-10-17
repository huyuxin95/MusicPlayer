package com.jju.yuxin.musicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jju.yuxin.musicplayer.domain.Music_;

import org.litepal.crud.ClusterQuery;
import org.litepal.crud.DataSupport;

import java.util.List;

import static android.util.Log.e;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.musicplayer
 * Created by yuxin.
 * Created time 2016/10/16 0016 下午 10:41.
 * Version   1.0;
 * Describe :
 * History:
 * ==============================================================================
 */

public class MusicReceiver extends BroadcastReceiver {


    private static final String TAG = MusicReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Music_ music_ = intent.getParcelableExtra("music");
        ClusterQuery clusterQuery = DataSupport.select("*").where("path=?", music_.getPath());
        if (clusterQuery!=null||clusterQuery.count(Music_.class)>0) {
             DataSupport.deleteAll(Music_.class,"path=?",music_.getPath());
        }
        music_.save();
        e(TAG, "onReceive" + music_.toString());
    }
}
