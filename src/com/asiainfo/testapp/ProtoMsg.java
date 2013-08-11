package com.asiainfo.testapp;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-10
 * Time: 下午12:00
 * To change this template use File | Settings | File Templates.
 */
import android.content.Intent;


public class ProtoMsg  {
    private String serial;
    private Intent intent;
    private int status; // 0 ,正常  4 作废
    public static final int DEL = 4;
    public static final int NORMAL = 0;
    public enum DIRECT {UI_IN,NET_IN,OUT_TO_NET,OUT_TO_UI,UI_EVENT}
    private DIRECT direct;
    public ProtoMsg(String serial,Intent intent,DIRECT d) {
        this.serial = serial;
        this.intent = intent;
        this.direct = d;
        this.status = 0;
    }
    public String getSerial() {
        return serial;
    }
    public void setSerial(String serial) {
        this.serial = serial;
    }
    public Intent getIntent() {
        return intent;
    }
    public void setIntent(Intent intent) {
        this.intent = intent;
    }
    public DIRECT getDirect() {
        return direct;
    }
    public void setDirect(DIRECT direct) {
        this.direct = direct;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
