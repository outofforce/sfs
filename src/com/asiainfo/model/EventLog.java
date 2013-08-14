package com.asiainfo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EventLog {

    public long id ;
    public int event_type ;
    public String event_key = "";
    public String event_value = "";
    public int event_status ;

    public static final int TYPE_IMG_CACHE = 1;

    public static final int STATUS_INIT = 0;
    public static final int STATUS_FINISH = 1;
    public static final int STATUS_DOING = 2;
    public static final int STATUS_BREAK= 8;

}