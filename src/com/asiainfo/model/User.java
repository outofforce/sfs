package com.asiainfo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable { //声明实现接口Parcelable

    public String user_name;
    public String passwd;
    public int status;
    public static final int NORMAL = 1;
    public static final int NO_ACTIVE = 0;
    public static final int LOGOUT = 2;


    public User(String auser_name, String apasswd,int astatus) {
        user_name = auser_name;
        passwd = apasswd;
        status =  astatus;
    }

    public User(Parcel source) {
        user_name = source.readString();
        passwd = source.readString();
        status = source.readInt();
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