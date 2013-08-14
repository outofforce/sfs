package com.asiainfo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PublishData implements Parcelable { //声明实现接口Parcelable
    public int id;
    public int user_id;
    public String nick_name;
    public String pub_context;
    public String gis_info;
    public String context_img;
    public Long create_time;
    public Long chg_time;
    public int status;
    public int context_img_loaded = 0;
    public long local_id=0;


    public static final int INIT = 0;

    public static final int LOADED = 1;
    public static final int NOLOAD= 2;
    public static final int BREAK = 3;

    public PublishData() {}

    public PublishData(
             int _id,
             int _user_id,
             String _nick_name,
             String _pub_context,
             String _gis_info,
             String _context_img,
             Long _create_time,
             Long _chg_time,
             int _status,
             int _context_img_loaded,
             long _local_id
    ) {
        id = _id;
        user_id =_user_id;
        nick_name =_nick_name;
        pub_context =_pub_context;
        gis_info =_gis_info;
        context_img =_context_img;
        create_time =_create_time;
        chg_time =_chg_time;
        status =_status;
        context_img_loaded = _context_img_loaded;
        local_id = _local_id;
    }

    public PublishData(Parcel s) {
        id = s.readInt();
        user_id = s.readInt();
        nick_name = s.readString();
        pub_context = s.readString();
        gis_info = s.readString();
        context_img = s.readString();
        create_time = s.readLong();
        chg_time = s.readLong();
        status = s.readInt();
        context_img_loaded = s.readInt();
        local_id = s.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(user_id);
        dest.writeString(nick_name);
        dest.writeString(pub_context);
        dest.writeString(gis_info);
        dest.writeString(context_img);
        dest.writeLong(create_time);
        dest.writeLong(chg_time);
        dest.writeInt(status);
        dest.writeInt(context_img_loaded);
        dest.writeLong(local_id);
    }

    public static final Creator<PublishData> CREATOR = new Creator<PublishData>() {

        @Override
        public PublishData[] newArray(int size) {
            return new PublishData[size];
        }


        @Override
        public PublishData createFromParcel(Parcel source) {
            return new PublishData(source);
        }
    };
}