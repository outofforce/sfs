package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;
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
    public Intent doUiEvent(Intent intent, Context cx, SfsResult result) {
        Log.e("MYDEBUG","66666666666666666");
        String  userName = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getString("UserName", "");
        Log.e("MYDEBUG","66666666666666667");
        String  passwd = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getString("Passwd", "");
        Log.e("MYDEBUG","66666666666666668");
        int  status = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getInt("Status",-1);
        Log.e("MYDEBUG","66666666666666669");
        String  nikeName = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getString("NickName", "");
        Log.e("MYDEBUG","666666666666666610");
        Boolean headImgIsLoad = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getBoolean("IsHeadImgLoad", false);
        Log.e("MYDEBUG","666666666666666611");
        int remote_id = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getInt("RemoteID", -1);
        Log.e("MYDEBUG","666666666666666612");
        String  headImg ="";
        if (headImgIsLoad)
          headImg = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getString("LoaclUserHeadImgPath", "");
        Intent t = new Intent();
        Log.e("MYDEBUG","666666666666666613");
        if (!userName.equals("")) {
            Log.e("MYDEBUG",userName +"|"+passwd+"|"+status+"|"+nikeName+"|"+headImg+"|"+remote_id);
            User user = new User(userName,passwd,status,nikeName,headImg,remote_id);
            t.putExtra("User",user);
            result.err_code = SfsErrorCode.Success;
        } else {
            result.err_code = SfsErrorCode.E_DEV_NO_USER;
        }

        return t;
    }
}
