package com.asiainfo.tools;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-22
 * Time: 下午6:55
 * To change this template use File | Settings | File Templates.
 */
public class Base64Code {
    static public String encode(String str) {

        String ret="";
        try {
            byte[] bytss = Base64.encode(str.trim().getBytes(), Base64.NO_WRAP);
            ret =  new String(bytss,0,bytss.length,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return ret;

    }
    static public String decode(String str) {

        String ret="";
        try {
            byte[] bytss = Base64.decode(str.trim(), Base64.NO_WRAP);
            ret =  new String(bytss,0,bytss.length,"UTF-8").trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return ret;

    }
}
