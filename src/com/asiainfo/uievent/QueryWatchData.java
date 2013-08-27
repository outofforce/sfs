package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;
import com.asiainfo.model.PublishData;
import com.asiainfo.model.User;
import com.asiainfo.proto.GetPublishData;
import com.asiainfo.proto.GetWatchData;
import com.asiainfo.proto.MtlServerGet;
import com.asiainfo.tab.MtlTableHelper;
import com.asiainfo.tab.TPublishData;
import com.asiainfo.tools.TimeTrans;
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
public class QueryWatchData implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, MtlResult result) {

        Intent t = new Intent();
        User user = intent.getParcelableExtra("User");
        ArrayList<User> list = new ArrayList<User>();
        if ( user != null) {
            GetWatchData reg = new GetWatchData(user);
            MtlServerGet.ServerResult res =  reg.handle();
            result.err_msg = res.err_msg;
            result.err_code = res.err_code ;
            if (result.err_code == MtlErrorCode.Success) {
                try {
                    JSONArray array = new JSONArray(res.result);
                    int max_id = 0;
                    for (int i=0;i<array.length();i++) {
                        JSONObject s = (JSONObject)array.get(i);
                        User d = new User();
                        d.remote_id = s.getInt("id");
                        d.nick_name = s.getString("nickName");
                        d.head_img = s.getString("headImg");
                        d.user_name = s.getString("userName");
                        d.head_img_load = User.IMG_NO_LOADED;
                        //d.user_name = d.nick_name;
                        list.add(d);
                    }
                    t.putParcelableArrayListExtra("WatchDatas",list);


                } catch (JSONException e) {
                    result.err_code = MtlErrorCode.E_JSON_ERROR ;
                    result.err_msg = "协议解析错误 "+e.getMessage();
                    e.printStackTrace();
                }

            }
        } else {
            result.err_code = MtlErrorCode.E_UI_ARG;
            result.err_msg = "arg User is NULL";

        }
        return t ;
    }
}
