package com.mcw.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/7
 */
public class MeetingContentPicEntity implements Parcelable{
    public String content;
    public ArrayList<String> photos;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeStringList(this.photos);
    }

    public MeetingContentPicEntity() {
    }

    public MeetingContentPicEntity(String content, ArrayList<String> photos) {
        this.content = content;
        this.photos = photos;
    }

    protected MeetingContentPicEntity(Parcel in) {
        this.content = in.readString();
        this.photos = in.createStringArrayList();
    }

    public static final Parcelable.Creator<MeetingContentPicEntity> CREATOR = new Parcelable.Creator<MeetingContentPicEntity>() {
        @Override
        public MeetingContentPicEntity createFromParcel(Parcel source) {
            return new MeetingContentPicEntity(source);
        }

        @Override
        public MeetingContentPicEntity[] newArray(int size) {
            return new MeetingContentPicEntity[size];
        }
    };
}
