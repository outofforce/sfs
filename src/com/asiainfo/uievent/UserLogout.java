package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
public class UserLogout implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, MtlResult result) {
        Intent t = new Intent();
        User user = intent.getParcelableExtra("User");
        if (user != null) {
            //Logout req = new Logout(user);

            SharedPreferences mPerferences = PreferenceManager
                    .getDefaultSharedPreferences(cx);
            SharedPreferences.Editor mEditor = mPerferences.edit();
            mEditor.putInt("Status", User.LOGOUT);
            mEditor.commit();
            result.err_code = MtlErrorCode.Success;

        } else {
            result.err_code = MtlErrorCode.E_UI_ARG;
            result.err_msg = "arg User is NULL";
        }

        return t ;
    }
}
