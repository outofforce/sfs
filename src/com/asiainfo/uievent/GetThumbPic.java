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


public class GetThumbPic implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, SfsResult result) {

        Intent t = new Intent();
        //User user = intent.getParcelableExtra("User");
        String type = intent.getStringExtra("AttachmentType");
        String path = intent.getStringExtra("AttachmentPath");
        int pos =  intent.getIntExtra("ListPos",0);


        if (path != null)
        {
              String download_path= NetTools.download("", path);
              if (download_path != null) {
                 result.err_code = SfsErrorCode.Success;
                 t.putExtra("ListPos",pos);
                 t.putExtra("AttachmentPath",download_path);
                 return t;
             }
        }



        return t ;
    }
}
