package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;
import com.asiainfo.model.User;
import com.asiainfo.proto.MtlServerGet;
import com.asiainfo.proto.Register;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
public class UserRegister implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, MtlResult result) {

        Intent t = new Intent();
        User user = intent.getParcelableExtra("User");
        String  headImg = PreferenceManager.getDefaultSharedPreferences(cx.getApplicationContext()).getString("UserHeadImg", "NULL");
        user.head_img = (headImg == "NULL")?"":headImg;

        if ( user != null) {
            Register reg = new Register(user);
            MtlServerGet.ServerResult res =  reg.handle();
            result.err_msg = res.err_msg;
            result.result = res.result;
            result.err_code = res.err_code;
            if (res.err_code == MtlErrorCode.Success) {
                // 写入
                SharedPreferences mPerferences = PreferenceManager
                        .getDefaultSharedPreferences(cx);
                SharedPreferences.Editor mEditor = mPerferences.edit();
                mEditor.putString("UserName", user.user_name);
                mEditor.putString("Passwd", user.passwd);
                mEditor.putString("NickName", user.nick_name);
                mEditor.putInt("Status", User.NO_ACTIVE);
                user.remote_id =  Integer.valueOf(result.result);
                mEditor.putInt("RemoteID",user.remote_id);// result  为远端的id
                mEditor.commit();
                t.putExtra("User",user);
            }



        } else {
            result.err_code = MtlErrorCode.E_UI_ARG;
            result.err_msg = "arg User is NULL";

        }
        return t ;
    }
}
