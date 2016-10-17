package com.jju.yuxin.musicplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jju.yuxin.musicplayer.R;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.musicplayer.fragment
 * Created by yuxin.
 * Created time 2016/10/16 0016 上午 12:44.
 * Version   1.0;
 * Describe : 搜一搜fragment
 * History:
 * ==============================================================================
 */

public class SousuoFragment extends BaseFragment {

    private WebView wv_baidu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_frament_sousuo,container,false);
        //设置一个webview的地址为"http://m.baidu.com"
        wv_baidu = (WebView) view.findViewById(R.id.wv_baidu);
        wv_baidu.loadUrl("http://m.baidu.com");
        //不开启第三方浏览器
        wv_baidu.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //支持js
        WebSettings settings = wv_baidu.getSettings();
        settings.setJavaScriptEnabled(true);
        return view;
    }
}
