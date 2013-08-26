package com.asiainfo.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;


/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-17
 * Time: 下午4:19
 * To change this template use File | Settings | File Templates.
 */
public class MtlApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();



    }



    @Override
    public void onTerminate() {

        super.onTerminate();    //To change body of overridden methods use File | Settings | File Templates.
    }


    public Boolean checkSdCard() {
        if (! Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getApplicationContext(), "sd卡拔出, 图片语音的功能无效",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public String getLongTimeDir() {
        return  "/sdcard/sfs/long_time/" ;
    }

    public String getTempTimeDir() {
        return  "/sdcard/sfs/download/" ;
    }
}
