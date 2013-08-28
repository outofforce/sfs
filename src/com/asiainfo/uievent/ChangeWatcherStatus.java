package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;
import com.asiainfo.model.User;
import com.asiainfo.proto.ChgWatcherStatus;
import com.asiainfo.proto.GetWatchData;
import com.asiainfo.proto.MtlServerGet;
import com.asiainfo.tab.MtlTableHelper;
import com.asiainfo.tab.TUser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
public class ChangeWatcherStatus implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, MtlResult result) {

        Intent t = new Intent();
        User user = intent.getParcelableExtra("User");
        User watcherUser = intent.getParcelableExtra("BeWatcher");
        int pos  = intent.getIntExtra("ListPos", 0);
        ArrayList<User> list = new ArrayList<User>();
        if ( user != null) {
            ChgWatcherStatus reg ;
            reg= new ChgWatcherStatus(user,watcherUser);
            MtlServerGet.ServerResult res =  reg.handle();
            result.err_msg = res.err_msg;
            result.err_code = res.err_code ;
            if (result.err_code == MtlErrorCode.Success) {
                MtlTableHelper helper  = new MtlTableHelper(cx);
                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                    TUser tuser = new TUser(db);
                    if (watcherUser.is_my_watcher == User.IS_WATCHER)  {
                        watcherUser.is_my_watcher = User.IS_NOT_WATCHER;
                        tuser.delUser(watcherUser);
                    }
                    else {
                        watcherUser.is_my_watcher = User.IS_WATCHER;
                        tuser.newUser(watcherUser);
                    }
                    t.putExtra("WatcherUser",watcherUser);
                    t.putExtra("ListPos",pos);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (db.isOpen())
                        db.close();
                }

            }

        } else {
            result.err_code = MtlErrorCode.E_UI_ARG;
            result.err_msg = "arg User is NULL";

        }
        return t ;
    }
}
