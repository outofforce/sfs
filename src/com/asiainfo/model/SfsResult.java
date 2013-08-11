package com.asiainfo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SfsResult implements Parcelable {

    public String err_msg ="";
    public String result = "";
    public int err_code = SfsErrorCode.Success;



    public SfsResult(String aerr_msg, String aresult, int aerr_code) {
        err_msg = aerr_msg;
        result = aresult;
        err_code =  aerr_code;
    }

    public SfsResult(Parcel source) {
        err_msg = source.readString();
        result = source.readString();
        err_code = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(err_msg);
        dest.writeString(result);
        dest.writeInt(err_code);
    }

    public static final Creator<SfsResult> CREATOR = new Creator<SfsResult>() {

        @Override
        public SfsResult[] newArray(int size) {
            return new SfsResult[size];
        }


        @Override
        public SfsResult createFromParcel(Parcel source) {
            return new SfsResult(source);
        }
    };
}