package com.asiainfo.testapp;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-17
 * Time: 下午4:19
 * To change this template use File | Settings | File Templates.
 */
public class sfsApplication extends Application {
    //private SfsTableHelper helper  = new SfsTableHelper(this);
    //private SQLiteDatabase db ;

    @Override
    public void onCreate() {
        super.onCreate();
//        helper  = new SfsTableHelper(this);
//        db = helper.getWritableDatabase() ;

    }

//    public SQLiteDatabase getDb() {
//        return db;
//    }

    @Override
    public void onTerminate() {
//        if (db.isOpen()) {
//            db.close();
//        }
//        helper.close();
        super.onTerminate();    //To change body of overridden methods use File | Settings | File Templates.
    }




}
