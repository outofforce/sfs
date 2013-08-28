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
import com.asiainfo.model.EventLog;
import com.asiainfo.model.User;


public  class TUser {

    public static final String TABLE_NAME = "TUSER";


    SQLiteDatabase  db;
    public TUser(SQLiteDatabase _db)
    {
        db=_db;
    }
    public long  clear() {
        return db.delete(TABLE_NAME,null,null);
    }

    public long newUser(User user) {
        ContentValues cv = new ContentValues();


        cv.put("USER_NAME", user.user_name);
        cv.put("NICK_NAME",user.nick_name);
        cv.put("REMOTE_ID",user.remote_id);

        return  db.insert(TABLE_NAME, null, cv);
    }

    public long delUser(User user) {
        return db.delete(TABLE_NAME,"REMOTE_ID=?",new String[] {""+user.remote_id});
    }

    public User getUser(User user) {
        User e = null;
        Cursor c = null;
        try {

             c =db.query(TABLE_NAME, null, "REMOTE_ID=?",
                    new String[] { ""+user.remote_id }, null, null, null , null);
            c.moveToFirst();

            if (c.getCount()>0) {

                e = new User();
                e.user_name =  c.getString(c.getColumnIndex("USER_NAME"));
                e.nick_name =  c.getString(c.getColumnIndex("NICK_NAME"));
                e.remote_id =  c.getInt(c.getColumnIndex("REMOTE_ID"));

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
