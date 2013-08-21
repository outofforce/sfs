package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;
import com.asiainfo.tools.NetTools;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */


public class GetHeadPic implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, SfsResult result) {

        Intent t = new Intent();
        String type = intent.getStringExtra("AttachmentType");
        String pos = intent.getStringExtra("HEADPIC_PATH");
        if (pos != null)
        {
            String download_path= NetTools.download("", pos);
            Log.e("MYDEBUG", "download_loacl_path=" + download_path);
            if (download_path != null) {
                SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(cx);
                SharedPreferences.Editor mEditor = mPerferences.edit();
                mEditor.putString("UserHeadImg", pos);
                mEditor.putBoolean("IsHeadImgLoad", true);
                mEditor.putString("LoaclUserHeadImgPath",download_path) ;
                mEditor.commit();
                result.err_code = SfsErrorCode.Success;

                return t;
            }
        }


        return t ;
    }
}
