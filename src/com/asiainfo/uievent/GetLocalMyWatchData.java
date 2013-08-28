package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;
import com.asiainfo.model.PublishData;
import com.asiainfo.model.User;
import com.asiainfo.tab.MtlTableHelper;
import com.asiainfo.tab.TPublishData;
import com.asiainfo.tab.TUser;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
public class GetLocalMyWatchData implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, MtlResult result) {

        Intent t = new Intent();
        ArrayList<User> list ;
        MtlTableHelper helper  = new MtlTableHelper(cx);
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            TUser tuser = new TUser(db);
            list = tuser.getMyWatchUser();
            t.putParcelableArrayListExtra("WatchDatas",list);
            result.err_code = MtlErrorCode.Success;
            result.err_msg = "Get "+ list.size() + " Publishs ";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen())
                db.close();
        }

        return t ;
    }
}
