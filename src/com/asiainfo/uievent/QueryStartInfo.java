package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;
import com.asiainfo.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
public class QueryStartInfo implements ISfsUiEvent {
    @Override
    public Intent doUiEvent(Intent intent, Context cx, MtlResult result) {
        String  userName = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getString("UserName", "");
        String  passwd = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getString("Passwd", "");
        int  status = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getInt("Status",-1);
        String  nikeName = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getString("NickName", "");
        Boolean headImgIsLoad = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getBoolean("IsHeadImgLoad", false);
        int remote_id = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getInt("RemoteID", -1);
        String  headImg ="";
        if (headImgIsLoad)
          headImg = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getString("LoaclUserHeadImgPath", "");
        Intent t = new Intent();

        if (!userName.equals("")) {
            Log.e("MYDEBUG",userName +"|"+passwd+"|"+status+"|"+nikeName+"|"+headImg+"|"+remote_id);
            User user = new User(userName,passwd,status,nikeName,headImg,remote_id);
            t.putExtra("User",user);
            result.err_code = MtlErrorCode.Success;
        } else {
            result.err_code = MtlErrorCode.E_DEV_NO_USER;
        }

        return t;
    }
}
