package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;
import com.asiainfo.model.PublishData;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;
import com.asiainfo.model.User;
import com.asiainfo.proto.PostPublishData;
import com.asiainfo.proto.ProtoGetPubData;
import com.asiainfo.proto.SfsServerGet;
import com.asiainfo.tab.SfsTableHelper;
import com.asiainfo.tab.TPublishData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */
public class PostPublish implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, SfsResult result) {

        Intent t = new Intent();
        User user = intent.getParcelableExtra("User");
        PublishData pub = intent.getParcelableExtra("PostPushishData");
        boolean hasImg = intent.getBooleanExtra("HasImg",false);


        if ( user != null) {

            if (hasImg) {
                UpThumbPic sender = new UpThumbPic();
                Intent imgIntent = new Intent();

                imgIntent.putExtra("AttachmentPath",pub.thumb_img);
                SfsResult imgres = new SfsResult();
                Intent tmpIntent = sender.doUiEvent(imgIntent,cx,imgres);

                String path= tmpIntent.getStringExtra("AttachmentPath");
                Log.e("MYDEBUG","000="+path+"-00-"+pub.thumb_img);
                if (path != null) {
                    pub.thumb_img = path;
                } else {
                    pub.thumb_img = "";
                }
                imgIntent.putExtra("AttachmentPath",pub.context_img);
                tmpIntent = sender.doUiEvent(imgIntent,cx,imgres);

                path= tmpIntent.getStringExtra("AttachmentPath");
                if (path != null) {
                    pub.context_img = path;
                } else {
                    pub.context_img = "";
                }
            }



            PostPublishData reg = new PostPublishData(user,pub);

            SfsServerGet.ServerResult res =  reg.handle();
            result.err_msg = res.err_msg;
            result.err_code = res.err_code ;
            result.result = res.result ;
        } else {
            result.err_code = SfsErrorCode.E_UI_ARG;
            result.err_msg = "arg User is NULL";

        }
        return t ;
    }
}
