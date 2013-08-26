package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;
import com.asiainfo.model.User;
import com.asiainfo.tools.NetTools;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */


public class PostLongTimeAttachement implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, MtlResult result) {

        Intent t = new Intent();
        User user = intent.getParcelableExtra("User");
        String type = intent.getStringExtra("AttachmentType");
        String path = intent.getStringExtra("AttachmentPath");

        if ( user != null) {


             String pos = NetTools.upload("", path);
             Log.e("MYDEBUG", "uploadPath=" + pos);

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
                     result.err_code = MtlErrorCode.Success;

                     return t;
                 }
             }
             SharedPreferences mPerferences = PreferenceManager.getDefaultSharedPreferences(cx);
             SharedPreferences.Editor mEditor = mPerferences.edit();
             mEditor.putString("UserHeadImg", "NULL");
             mEditor.commit();
             result.err_code = MtlErrorCode.E_IO;
             result.err_msg = "图片上传下载异常！";


        } else {
            result.err_code = MtlErrorCode.E_UI_ARG;
            result.err_msg = "arg User is NULL";

        }
        return t ;
    }
}
