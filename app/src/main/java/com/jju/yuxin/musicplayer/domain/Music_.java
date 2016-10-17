package com.jju.yuxin.musicplayer.domain;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * =============================================================================
 * Copyright (c) 2016 yuxin All rights reserved.
 * Packname com.jju.yuxin.musicplayer.domain
 * Created by yuxin.
 * Created time 2016/10/16 0016 上午 9:18.
 * Version   1.0;
 * Describe :
 * History:
 * ==============================================================================
 */

public class Music_ extends DataSupport implements Parcelable {

    private int id;
    private String name;
    private String author;
    private String path;


    public Music_() {
    }

    public Music_(int id, String name, String author, String uri) {
        this.id = id;
        this.name = name;
        this.path = uri;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.author);
        dest.writeString(this.path);
    }

    protected Music_(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.author = in.readString();
        this.path = in.readString();
    }

    public static final Parcelable.Creator<Music_> CREATOR = new Parcelable.Creator<Music_>() {
        @Override
        public Music_ createFromParcel(Parcel source) {
            return new Music_(source);
        }

        @Override
        public Music_[] newArray(int size) {
            return new Music_[size];
        }
    };

    @Override
    public String toString() {
        return "Music_{" +
                "author='" + author + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
