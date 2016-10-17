package com.jju.yuxin.musicplayer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jju.yuxin.musicplayer.R;
import com.jju.yuxin.musicplayer.domain.Music_;

import java.util.List;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.musicplayer.adapter
 * Created by yuxin.
 * Created time 2016/10/16 0016 上午 9:44.
 * Version   1.0;
 * Describe :
 * History:
 * ==============================================================================
 */

public class Music_Adapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Music_> music_;
    public Music_Adapter(Context context, List<Music_> musics) {
        this.context=context;
        this.music_=musics;
        this.layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return music_.size();
    }

    @Override
    public Object getItem(int position) {
        return music_.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.music_item_layout,null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.music_name);
            viewHolder.author = (TextView) convertView.findViewById(R.id.music_author);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(music_.get(position).getName());
        viewHolder.author.setText(music_.get(position).getAuthor());
        return convertView;
    }

    private class ViewHolder{
        TextView name;
        TextView author;
    }
}
