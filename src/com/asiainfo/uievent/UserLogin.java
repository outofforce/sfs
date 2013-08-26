package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.asiainfo.app.MtlService;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;
import com.asiainfo.model.User;
import com.asiainfo.proto.Login;
import com.asiainfo.proto.SfsServerGet;
import org.json.JSONException;
import org.json.JSONObject;

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
        Boolean chg_user = intent.getBooleanExtra("IsLoginChgUser",false);
        int flag = chg_user?1:0;
        if ( user != null) {
            Log.e("MYDEBUG",user.user_name+ " login....");
            Login req = new Login(user,flag);
            SfsServerGet.ServerResult res =  req.handle();
            result.err_msg = res.err_msg;
            result.result = res.result;
            result.err_code = res.err_code;
            if (chg_user) {
                SharedPreferences mPerferences = PreferenceManager
                    .getDefaultSharedPreferences(cx);
                SharedPreferences.Editor mEditor = mPerferences.edit();
                mEditor.putString("UserName", user.user_name);
                mEditor.putString("Passwd", user.passwd);
                mEditor.commit();
            }

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
                if (chg_user) {
                    JSONObject s = null;
                    try {
                        s = new JSONObject(res.result);
                        user.remote_id =  s.getInt("user_id");;
                        user.nick_name= s.getString("nick_name");
                        user.head_img = s.getString("head_img");
                        mEditor.putString("NickName", user.nick_name);
                        mEditor.putString("UserHeadImg",user.head_img);
                        mEditor.putInt("RemoteID",user.remote_id);
                        if (!s.getString("head_img").equals("")) {

                            Intent dlintent = new Intent();
                            dlintent.setClass(cx, MtlService.class);
                            dlintent.setAction("GetHeadPic");
                            dlintent.putExtra("HEADPIC_PATH",s.getString("head_img")) ;
                            cx.startService(dlintent);
                        }
                    } catch (JSONException e) {
                        result.err_code = SfsErrorCode.E_JSON_ERROR;
                        result.err_msg = e.getMessage();
                        e.printStackTrace();
                    }

                }
                t.putExtra("User",user);
                mEditor.commit();

            }

        } else {
            result.err_code = SfsErrorCode.E_UI_ARG;
            result.err_msg = "arg User is NULL";

        }

        return t ;
    }
}
