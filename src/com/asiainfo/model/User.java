package com.asiainfo.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.asiainfo.tools.Base64Code;

public class User implements Parcelable { //声明实现接口Parcelable

    public String user_name="";
    public String passwd="";
    public int status=0;
    public String nick_name="";
    public String head_img="";
    public int remote_id=0;

    public static final int NORMAL = 1;
    public static final int NO_ACTIVE = 0;
    public static final int LOGOUT = 2;

    public User() {

    }
    public String getNickName64() {
        return Base64Code.encode(nick_name) ;
    }
    public void setNickName64(String nickname64) {
        nick_name = Base64Code.decode(nickname64);
    }

    public User(String auser_name, String apasswd,int astatus,String anike_name,String aheadimg,int aremote_id) {
        user_name = auser_name;
        passwd = apasswd;
        status =  astatus;
        nick_name = anike_name;
        head_img = aheadimg;
        remote_id =  aremote_id;
    }

    public User(Parcel source) {
        user_name = source.readString();
        passwd = source.readString();
        status = source.readInt();
        nick_name = source.readString();
        head_img = source.readString();
        remote_id = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(user_name);
        dest.writeString(passwd);
        dest.writeInt(status);
        dest.writeString(nick_name);
        dest.writeString(head_img);
        dest.writeInt(remote_id);
    }

    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }


        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }
    };
}