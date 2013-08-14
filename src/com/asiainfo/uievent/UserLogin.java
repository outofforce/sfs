package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;
import com.asiainfo.model.User;
import com.asiainfo.proto.Login;
import com.asiainfo.proto.SfsServerGet;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
public class UserLogin implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, SfsResult result) {
        Intent t = new Intent();
        User user = intent.getParcelableExtra("User");
        Log.e("MYDEBUG"," login....");
        if ( user != null) {
            Log.e("MYDEBUG",user.user_name+ " login....");
            Login req = new Login(user);
            SfsServerGet.ServerResult res =  req.handle();
            result.err_msg = res.err_msg;
            result.result = res.result;
            result.err_code = res.err_code;
            if (res.err_code == SfsErrorCode.Success ||
                    res.err_code == SfsErrorCode.E_USER_INACITVE
                    ) {
                // 写入
                SharedPreferences mPerferences = PreferenceManager
                        .getDefaultSharedPreferences(cx);
                SharedPreferences.Editor mEditor = mPerferences.edit();
                if (res.err_code == SfsErrorCode.E_USER_INACITVE)
                    mEditor.putInt("Status", User.NO_ACTIVE);
                else
                    mEditor.putInt("Status", User.NORMAL);

                mEditor.putString("UserName", user.user_name);
                mEditor.putString("Passwd", user.passwd);

                mEditor.commit();
            }

        } else {
            result.err_code = SfsErrorCode.E_UI_ARG;
            result.err_msg = "arg User is NULL";

        }

        return t ;
    }
}
