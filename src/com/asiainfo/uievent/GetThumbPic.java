package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.asiainfo.model.EventLog;
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;
import com.asiainfo.tab.MtlTableHelper;
import com.asiainfo.tab.TEventLog;
import com.asiainfo.tab.TPublishData;
import com.asiainfo.tools.NetTools;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */


public class GetThumbPic implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, MtlResult result) {

        Intent t = new Intent();
        String path = intent.getStringExtra("AttachmentPath");
        int pos =  intent.getIntExtra("ListPos",0);


        if (path != null)
        {
            MtlTableHelper helper  = new MtlTableHelper(cx);
            SQLiteDatabase db = helper.getWritableDatabase();
            try {

             TEventLog tenvenlog = new TEventLog(db);
             EventLog log = tenvenlog.getEventLog(path);


             String download_path;
             if (log == null) {
                 download_path= NetTools.download("", path);
                 tenvenlog.newImgCacheEvent(path,download_path);
             } else {
                 download_path= log.event_value;
             }

             if (download_path != null) {
                 result.err_code = MtlErrorCode.Success;
                 t.putExtra("ListPos",pos);
                 t.putExtra("AttachmentPath",download_path);

             }
            } catch (Exception e) {
                 result.err_code = MtlErrorCode.E_CACHE_IMG;
                 result.err_msg = e.getMessage();
            } finally {
                if (db.isOpen())
                    db.close();
            }

        }



        return t ;
    }
}
