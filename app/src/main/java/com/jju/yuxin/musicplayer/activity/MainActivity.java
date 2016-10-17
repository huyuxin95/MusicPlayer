package com.jju.yuxin.musicplayer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.jju.yuxin.musicplayer.R;
import com.jju.yuxin.musicplayer.adapter.FragmentTabAdapter;
import com.jju.yuxin.musicplayer.fragment.GongNengFragment;
import com.jju.yuxin.musicplayer.fragment.QukuFragment;
import com.jju.yuxin.musicplayer.fragment.SousuoFragment;
import com.jju.yuxin.musicplayer.fragment.TingtingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.musicplayer.activity
 * Created by yuxin.
 * Created time 2016/10/16 0016 上午 12:10.
 * Version   1.0;
 * Describe : 主Activity,在他的上面有个Fragmenttabhost
 * History:
 * ==============================================================================
 */

public class MainActivity extends BaseActivity {

    private FrameLayout flcontair;
    private FragmentTabHost fthhome;
    public RadioButton rbtabtingting;
    private RadioButton rbtabquku;
    private RadioButton rbtabsousuo;
    private RadioButton rbtabgongneng;
    private List<Fragment> fragments;
    private RadioGroup rg_tab_home;
    //进入时的页面位置
    private int CurrentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);
        //初始化所有的fragment,添加到List中便于管理
        fragments = new ArrayList<>();
        fragments.add(new TingtingFragment());
        fragments.add(new QukuFragment());
        fragments.add(new SousuoFragment());
        fragments.add(new GongNengFragment());
        //控件的初始化,与监听的初始化
        initialize();
    }

    /**
     * 控件的初始化,与监听的初始化
     */
    private void initialize() {
        //MainActivity里面显示fragment的位置
        flcontair = (FrameLayout) findViewById(R.id.fl_contair);
        //fragmenttabhost
        fthhome = (FragmentTabHost) findViewById(R.id.fth_home);
        //底部的RadioGroup
        rbtabtingting = (RadioButton) findViewById(R.id.rb_tab_tingting);
        rbtabquku= (RadioButton) findViewById(R.id.rb_tab_quku);
        rbtabsousuo = (RadioButton) findViewById(R.id.rb_tab_sousuo);
        rbtabgongneng= (RadioButton) findViewById(R.id.rb_tab_gongneng);
        rg_tab_home = (RadioGroup) findViewById(R.id.rg_tab_home);

        fthhome.setup(getApplicationContext(), getSupportFragmentManager(), R.id.fl_contair);
        //循环的向tabhost添加fragment
        for (int i = 0; i < fragments.size(); i++) {
            //将一个隐藏的视图给tabs,达到隐藏tabs的目的
            View view = new View(this);
            view.setVisibility(View.GONE);
            TabHost.TabSpec tabSpec = fthhome.newTabSpec(String.valueOf(i)).setIndicator(view);
            fthhome.addTab(tabSpec, fragments.get(i).getClass(), null);
        }
        //自定义个适配器使得fragment当被初始化后,切换页面时只会执行onpause()不会销毁
        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments, R.id.fl_contair, rg_tab_home);
        //判断当前点击的是哪个fragment
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener(){
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                System.out.println("Extra---- " + index + " checked!!! ");
            }
        });
    }

}
