package com.asiainfo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MtlResult implements Parcelable {

    public String err_msg ="";
    public String result = "";
    public int err_code = MtlErrorCode.Success;


    public MtlResult() {

    }
    public MtlResult(String aerr_msg, String aresult, int aerr_code) {
        err_msg = aerr_msg;
        result = aresult;
        err_code =  aerr_code;
    }

    public MtlResult(Parcel source) {
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

    public static final Creator<MtlResult> CREATOR = new Creator<MtlResult>() {

        @Override
        public MtlResult[] newArray(int size) {
            return new MtlResult[size];
        }


        @Override
        public MtlResult createFromParcel(Parcel source) {
            return new MtlResult(source);
        }
    };
}