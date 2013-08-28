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
import android.util.Log;
import com.asiainfo.model.EventLog;


public  class TEventLog  {

    public static final String TABLE_NAME = "EVENT_LOG";


    SQLiteDatabase  db;
    public TEventLog(SQLiteDatabase  _db)
    {
        db=_db;
    }

    public long newImgCacheEvent(String key,String value) {
        ContentValues cv = new ContentValues();
        cv.put("EVENT_KEY", key);
        cv.put("EVENT_VALUE",value);
        cv.put("EVENT_STATUS",EventLog.STATUS_INIT);
        cv.put("EVENT_TYPE",EventLog.TYPE_IMG_CACHE);
        return  db.insert(TABLE_NAME, null, cv);
    }

    public long updateStatus(long id,int status) {
        ContentValues cv = new ContentValues();
        cv.put("EVENT_STATUS",status);
        return db.update(TABLE_NAME,cv,"_ID=?",new String[] {Long.toString(id)});
    }
    public EventLog getEventLog(String eventKey) {
        EventLog e = null;
        Cursor c = null;
        try {

             c =db.query(TABLE_NAME, null, "EVENT_KEY=?",
                    new String[] { eventKey }, null, null, null , null);
            c.moveToFirst();

            if (c.getCount()>0) {

                e = new EventLog();
                e.event_key =  c.getString(c.getColumnIndex("EVENT_KEY"));
                e.event_value =  c.getString(c.getColumnIndex("EVENT_VALUE"));
                e.event_type =  c.getInt(c.getColumnIndex("EVENT_TYPE"));
                e.event_status =  c.getInt(c.getColumnIndex("EVENT_STATUS"));
                e.id =  c.getLong(c.getColumnIndex("_ID"));


            }
            c.close();
        } catch (Exception exception) {
          exception.printStackTrace();
        }  finally {
            if (c != null)
                c.close();
        }


        return e;
    }

//    private long delete(long ID) {
//        return db.delete(TABLE_NAME, "_ID=?",new String[] {Long.toString(ID)});
//    }
//
//    private long update(ContentValues values,long ID) {
//        return db.update(TABLE_NAME,values,"_ID=?",new String[] {Long.toString(ID)});
//    }

}
