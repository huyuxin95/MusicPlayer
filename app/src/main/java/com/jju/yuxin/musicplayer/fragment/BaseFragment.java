package com.jju.yuxin.musicplayer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.jju.yuxin.musicplayer.R;
import com.jju.yuxin.musicplayer.utils.MyLogger;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.weibo.fragments
 * Created by yuxin.
 * Created time 2016/9/24 0024 上午 11:46.
 * Version   1.0;
 * Describe : fragment的基类
 * History:
 * ==============================================================================
 */

public class BaseFragment extends Fragment {
    public MyLogger hlog;
    public BaseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      hlog=MyLogger.hLog();
    }

    /**
     * 设置调用startActivity的切换动画
     * @param intent
     */
    @Override
    public void startActivity(Intent intent) {
       getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
      getActivity().startActivityForResult(intent,requestCode);
        getActivity().overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }


}
