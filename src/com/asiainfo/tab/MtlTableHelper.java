package com.asiainfo.tab;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-13
 * Time: 下午3:59
 * To change this template use File | Settings | File Templates.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MtlTableHelper extends SQLiteOpenHelper
{

    private final static String DATABASE_NAME = "ServiceForStudent";
    private final static int DATABASE_VERSION = 3;


    public MtlTableHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql="CREATE TABLE EVENT_LOG (_ID INTEGER PRIMARY KEY AUTOINCREMENT, EVENT_TYPE INTEGER , EVENT_KEY VARCHAR(256), EVENT_VALUE VARCHAR(256) ,EVENT_STATUS INTEGER)";
        db.execSQL(sql);
        sql=" CREATE TABLE TPUBLISHDATA (_ID INTEGER PRIMARY KEY AUTOINCREMENT, REMOTE_ID INTEGER , " +
                " USER_ID INTEGER, NICK_NAME VARCHAR(256) ,PUB_CONTEXT  VARCHAR(20000) , " +
                "  GIS_INFO VARCHAR(256), CONTEXT_IMG VARCHAR(256) ,CREATE_TIME INTEGER ," +
                "  CHG_TIME INTEGER, STATUS INTEGER ,CONTEXT_IMG_LOADED INTEGER)";
        db.execSQL(sql);
        sql="CREATE TABLE TUSER (_ID INTEGER PRIMARY KEY AUTOINCREMENT, REMOTE_ID INTEGER , NICK_NAME VARCHAR(256), USER_NAME VARCHAR(256))";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //TODO	  
        String sql = "DROP TABLE IF EXISTS EVENT_LOG " ;
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS TPUBLISHDATA " ;
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS TUSER " ;
        db.execSQL(sql);
        onCreate(db);
    }




}