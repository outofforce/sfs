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

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
public class GetLocalPublishData implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, MtlResult result) {

        Intent t = new Intent();
        User user = intent.getParcelableExtra("User");
        ArrayList<PublishData> list ;
        if ( user != null) {
            MtlTableHelper helper  = new MtlTableHelper(cx);
            SQLiteDatabase db = helper.getReadableDatabase();
            try {
            TPublishData tpd = new TPublishData(db);
            list = tpd.getLastPublishData(0,20);
            t.putParcelableArrayListExtra("PublicDatas",list);
            result.err_code = MtlErrorCode.Success;
            result.err_msg = "Get "+ list.size() + " Publishs ";
            } catch (Exception e) {
                result.err_code = MtlErrorCode.E_IO;
                result.err_msg = e.getMessage();
            } finally {
                if (db.isOpen())
                    db.close();
            }
        } else {
            result.err_code = MtlErrorCode.E_UI_ARG;
            result.err_msg = "arg User is NULL";

        }
        return t ;
    }
}
