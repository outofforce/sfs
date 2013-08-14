package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import com.asiainfo.model.PublishData;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;
import com.asiainfo.model.User;
import com.asiainfo.proto.ProtoGetPubData;
import com.asiainfo.proto.SfsServerGet;
import com.asiainfo.proto.YouHaveMessage;
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
public class CheckNewMessage implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, SfsResult result) {

        Intent t = new Intent();
        int  last_max_id = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getInt("PublshMaxId",0);
        YouHaveMessage reg = new YouHaveMessage(last_max_id);
        SfsServerGet.ServerResult res =  reg.handle();
        result.err_msg = res.err_msg;
        result.err_code = res.err_code ;
        if ( result.err_code == SfsErrorCode.Success) {
            if (!res.result.trim().equals("0")) {
                result.result = "发现 "+ res.result + "个通知";
                t.putExtra("NeedNotify",true);
            }

        }
        else
            result.result = res.result;

        return t ;
    }
}
