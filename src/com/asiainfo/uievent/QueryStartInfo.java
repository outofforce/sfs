package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
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

        String  userName = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getString("UserName", "");
        String  passwd = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getString("Passwd", "");
        int  status = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getInt("Status",-1);
        Intent t = new Intent();
        if (!userName.equals("")) {
            User user = new User(userName,passwd,status);
            t.putExtra("User",user);
            result.err_code = SfsErrorCode.Success;
        } else {
            result.err_code = SfsErrorCode.E_DEV_NO_USER;
        }

        return t;
    }
}
