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
public class QueryPublishData implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, SfsResult result) {

        Intent t = new Intent();
        User user = intent.getParcelableExtra("User");
        ArrayList<PublishData> list = new ArrayList<PublishData>();
        if ( user != null) {
            ProtoGetPubData reg = new ProtoGetPubData(user,System.currentTimeMillis());
            SfsServerGet.ServerResult res =  reg.handle();
            result.err_msg = res.err_msg;
            result.err_code = res.err_code ;


            if (result.err_code == SfsErrorCode.Success) {
                SfsTableHelper helper  = new SfsTableHelper(cx);
                SQLiteDatabase db = helper.getWritableDatabase();
                TPublishData tp = new TPublishData(db);
                try {


                    JSONArray array = new JSONArray(res.result);
                    int max_id = 0;
                    for (int i=0;i<array.length();i++) {
                        JSONObject s = (JSONObject)array.get(i);

                        Log.e("MYDEBUG",""+s.toString());
                        PublishData d = new PublishData();
                        d.id = s.getInt("id");
                        if (d.id >max_id)
                            max_id = d.id;
                        d.user_id = s.getInt("userId");
                        d.nick_name = s.getString("nickName");
                        d.pub_context = s.getString("postContext");
                        d.gis_info = s.getString("gisInfo");
                        d.context_img = s.getString("postImg");
                        d.create_time = System.currentTimeMillis();
                        d.chg_time = System.currentTimeMillis();
                        d.status = s.getInt("status");
                        d.context_img_loaded = PublishData.INIT;
                        d.thumb_img = s.getString("postImg");
                        list.add(d);
                        //tp.newPublicData(d);


                    }
                    t.putParcelableArrayListExtra("PublicDatas",list);
                    int  last_max_id = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getInt("PublshMaxId",0);
                    if (max_id > last_max_id) {
                        SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(cx);
                        SharedPreferences.Editor mEditor = mPerferences.edit();
                        mEditor.putInt("PublshMaxId", max_id);
                        mEditor.commit();
                    }


                } catch (JSONException e) {
                    result.err_code = SfsErrorCode.E_JSON_ERROR ;
                    result.err_msg = "协议解析错误 "+e.getMessage();
                    e.printStackTrace();
                }
                if (db.isOpen())
                    db.close();

            }
        } else {
            result.err_code = SfsErrorCode.E_UI_ARG;
            result.err_msg = "arg User is NULL";

        }
        return t ;
    }
}
