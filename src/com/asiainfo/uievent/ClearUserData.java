package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;
import com.asiainfo.model.User;
import com.asiainfo.tools.NetTools;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
public class ClearUserData implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, SfsResult result) {

        Intent t = new Intent();
        SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(cx);
        SharedPreferences.Editor mEditor = mPerferences.edit();
        mEditor.putString("UserHeadImg", "");
        mEditor.putBoolean("IsHeadImgLoad", false);
        mEditor.putString("LoaclUserHeadImgPath","") ;
        mEditor.putString("UserName","") ;
        mEditor.putString("Passwd","") ;
        mEditor.putString("NickName","") ;
        mEditor.putInt("Status",-1) ;
        mEditor.commit();
        result.err_code = SfsErrorCode.Success;

        return t ;
    }
}
