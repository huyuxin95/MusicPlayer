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
 * Created time 2016/10/16 0016 下午 9:11.
 * Version   1.0;
 * Describe :
 * History:
 * ==============================================================================
 */

public class ZuiJinAdapter extends BaseAdapter {
    private Context context;
    private List<Music_> olist;
    private LayoutInflater inflater;
    public ZuiJinAdapter(Context context, List<Music_> olist) {
        this.context=context;
        this.olist=olist;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return olist.size();
    }

    @Override
    public Object getItem(int position) {
        return olist.get(position);
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
            convertView=inflater.inflate(R.layout.music_item_layout,null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.music_name);
            viewHolder.author = (TextView) convertView.findViewById(R.id.music_author);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(olist.get(olist.size()-1-position).getName());
        viewHolder.author.setText(olist.get(olist.size()-1-position).getAuthor());
        return convertView;
    }

    private class ViewHolder{
        TextView name;
        TextView author;
    }
}
