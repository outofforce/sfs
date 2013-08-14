package com.asiainfo.tab;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-13
 * Time: 下午5:53
 * To change this template use File | Settings | File Templates.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.asiainfo.model.PublishData;

import java.util.ArrayList;


public  class TPublishData {

    public static final String TABLE_NAME = "TPUBLISHDATA";


    SQLiteDatabase  db;
    public TPublishData(SQLiteDatabase _db)
    {
        db=_db;
    }

    public long newPublicData(PublishData data) {
        ContentValues cv = new ContentValues();
        cv.put("REMOTE_ID", data.id);
        cv.put("USER_ID", data.user_id);
        cv.put("NICK_NAME", data.nick_name);
        cv.put("PUB_CONTEXT", data.pub_context);
        cv.put("GIS_INFO", data.gis_info);
        cv.put("CONTEXT_IMG", data.context_img);
        cv.put("CREATE_TIME", data.create_time);
        cv.put("CHG_TIME", data.chg_time);
        cv.put("STATUS", data.status);
        cv.put("CONTEXT_IMG_LOADED", data.context_img_loaded);
        return  db.insert(TABLE_NAME, null, cv);
    }

    public long updatePublicData(PublishData data) {
        ContentValues cv = new ContentValues();
        cv.put("REMOTE_ID", data.id);
        cv.put("USER_ID", data.user_id);
        cv.put("NICK_NAME", data.nick_name);
        cv.put("PUB_CONTEXT", data.pub_context);
        cv.put("GIS_INFO", data.gis_info);
        cv.put("CONTEXT_IMG", data.context_img);
        cv.put("CREATE_TIME", data.create_time);
        cv.put("CHG_TIME", data.chg_time);
        cv.put("STATUS", data.status);
        cv.put("CONTEXT_IMG_LOADED", data.context_img_loaded);
        return db.update(TABLE_NAME,cv,"_ID=?",new String[] {Long.toString(data.local_id)});
    }

    public ArrayList<PublishData> getLastPublishData(int begin,int count) {
        ArrayList<PublishData> list = new ArrayList();
        Cursor c =db.query(TABLE_NAME, null, null,
                null, null, null, "_ID DESC", ""+begin+","+count);
        c.moveToLast();
        if (c.getCount()>0) {
            do {
                PublishData d = new PublishData();
                d.id = c.getInt(c.getColumnIndex("REMOTE_ID"));
                d.user_id = c.getInt(c.getColumnIndex("USER_ID"));
                d.nick_name = c.getString(c.getColumnIndex("NICK_NAME"));
                d.pub_context = c.getString(c.getColumnIndex("PUB_CONTEXT"));
                d.gis_info = c.getString(c.getColumnIndex("GIS_INFO"));
                d.context_img = c.getString(c.getColumnIndex("CONTEXT_IMG"));
                d.create_time = System.currentTimeMillis();
                d.chg_time = System.currentTimeMillis();
                d.status = c.getInt(c.getColumnIndex("STATUS"));
                d.local_id = c.getInt(c.getColumnIndex("_ID"));
                d.context_img_loaded = PublishData.INIT;
                list.add(d);
            }
            while(c.moveToPrevious());
        }
        c.close();
        return list;
    }


//    public Cursor select(SQLiteDatabase db,String tableName,String selection,String[] selectionArgs){
//        return db.query(tableName, null, selection, selectionArgs, null, null, "_ID DESC");
//    }
}
