package com.jju.yuxin.musicplayer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
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
 * Describe :
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
        fragments = new ArrayList<>();
        fragments.add(new TingtingFragment());
        fragments.add(new QukuFragment());
        fragments.add(new SousuoFragment());
        fragments.add(new GongNengFragment());
        initialize();
    }

    private void initialize() {
        flcontair = (FrameLayout) findViewById(R.id.fl_contair);
        fthhome = (FragmentTabHost) findViewById(R.id.fth_home);
        rbtabtingting = (RadioButton) findViewById(R.id.rb_tab_tingting);
        rbtabquku= (RadioButton) findViewById(R.id.rb_tab_quku);
        rbtabsousuo = (RadioButton) findViewById(R.id.rb_tab_sousuo);
        rbtabgongneng= (RadioButton) findViewById(R.id.rb_tab_gongneng);
        rg_tab_home = (RadioGroup) findViewById(R.id.rg_tab_home);

        fthhome.setup(getApplicationContext(), getSupportFragmentManager(), R.id.fl_contair);

        for (int i = 0; i < fragments.size(); i++) {
            View view = new View(this);
            view.setVisibility(View.GONE);
            TabHost.TabSpec tabSpec = fthhome.newTabSpec(String.valueOf(i)).setIndicator(view);
            fthhome.addTab(tabSpec, fragments.get(i).getClass(), null);
        }
 //       fthhome.setCurrentTab(CurrentTab);
//        rg_tab_home.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb_tab_tingting:
//                        fthhome.setCurrentTab(0);
//                        break;
//                    case R.id.rb_tab_quku:
//                        fthhome.setCurrentTab(1);
//                        break;
//                    case R.id.rb_tab_sousuo:
//                        fthhome.setCurrentTab(2);
//                        break;
//                    case R.id.rb_tab_gongneng:
//                        fthhome.setCurrentTab(3);
//                        break;
//
//                    default:
//                        break;
//                }
//            }
//        });

        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments, R.id.fl_contair, rg_tab_home);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener(){
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                System.out.println("Extra---- " + index + " checked!!! ");
            }
        });
    }

}
