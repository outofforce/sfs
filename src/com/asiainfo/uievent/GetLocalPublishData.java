package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;
import com.asiainfo.model.PublishData;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;
import com.asiainfo.model.User;
import com.asiainfo.proto.ProtoGetPubData;
import com.asiainfo.proto.SfsServerGet;
import com.asiainfo.tab.SfsTableHelper;
import com.asiainfo.tab.TPublishData;
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
public class GetLocalPublishData implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, SfsResult result) {

        Intent t = new Intent();
        User user = intent.getParcelableExtra("User");
        ArrayList<PublishData> list ;
        if ( user != null) {
            SfsTableHelper helper  = new SfsTableHelper(cx);
            SQLiteDatabase db = helper.getReadableDatabase();
            TPublishData tpd = new TPublishData(db);
            list = tpd.getLastPublishData(0,20);
            t.putParcelableArrayListExtra("PublicDatas",list);
            result.err_code = SfsErrorCode.Success;
            result.err_msg = "Get "+ list.size() + " Publishs ";
        } else {
            result.err_code = SfsErrorCode.E_UI_ARG;
            result.err_msg = "arg User is NULL";

        }
        return t ;
    }
}
