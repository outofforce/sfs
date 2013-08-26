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
    private final static int DATABASE_VERSION = 2;


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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //TODO	  
        String sql = "DROP TABLE IF EXISTS EVENT_LOG " ;
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS TPUBLISHDATA " ;
        onCreate(db);
    }

 /*
    public Cursor select(SQLiteDatabase db,String tableName)
    {
        return db.query(tableName, null, null, null, null, null, "_ID DESC");
    }

    public Cursor select_raw(SQLiteDatabase db,String sql)
    {
        return  db.rawQuery(sql, null);
    }

    public Cursor select(SQLiteDatabase db,String tableName,String selection,String[] selectionArgs){
        return db.query(tableName, null, selection, selectionArgs, null, null, "_ID DESC");
    }

    public Cursor select_asc (SQLiteDatabase db,String tableName,String selection,String[] selectionArgs){
        return db.query(tableName, null, selection, selectionArgs, null, null, "_ID ");
    }

    public Cursor query(SQLiteDatabase db,String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }
    */

}