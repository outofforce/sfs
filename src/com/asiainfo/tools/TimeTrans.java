package com.asiainfo.tools;

import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-26
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
public class TimeTrans {
    public static long StringToLong(String timeStr) {
        Timestamp ts = Timestamp.valueOf(timeStr);
        return ts.getTime();
    }
    public static Date StampToDate(long stamp) {
        java.util.Date utilDate = new java.util.Date(stamp) ;
        return  utilDate;
    }
    public static String LongToString(long stamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = StampToDate(stamp);
        return format.format(date);
    }


    public static String LongToBusiString(long stamp) {
        long second = 1000;
        long minutes = second*60;
        long hours = minutes*60;
        long days = hours*24;
        long months = days*30;
        Date nowtime = new Date();
        long longtime =nowtime.getTime()- stamp;
        //Log.e("MYDEBUG", "now=" + LongToString(nowtime.getTime()));
        //Log.e("MYDEBUG", "remote_time=" +  LongToString(stamp));
        if( longtime > months*2 ) {
            return LongToString(stamp);
        } else if (longtime > months) {
            return "1个月前";
        } else if (longtime > days*7) {
            return ("1周前");
        } else if (longtime > days) {
            return((int)Math.floor(longtime/days)+"天前");
        } else if ( longtime > hours) {
            return((int)Math.floor(longtime/hours)+"小时前");
        } else if (longtime > minutes) {
            return((int)Math.floor(longtime/minutes)+"分钟前");
        } else if (longtime > second) {
            return((int)Math.floor(longtime/second)+"秒前");
        } else {
            return("刚刚");
        }
    }
}
