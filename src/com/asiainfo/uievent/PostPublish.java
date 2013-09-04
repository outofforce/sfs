package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;
import com.asiainfo.model.PublishData;
import com.asiainfo.model.User;
import com.asiainfo.proto.MtlServerGet;
import com.asiainfo.proto.PostPublishData;

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
    public Intent doUiEvent(Intent intent, Context cx, MtlResult result) {

        Intent t = new Intent();
        User user = intent.getParcelableExtra("User");
        PublishData pub = intent.getParcelableExtra("PostPushishData");
        ArrayList<User> selectedUser = intent.getParcelableArrayListExtra("Post_To");
        boolean hasImg = intent.getBooleanExtra("HasImg",false);


        if ( user != null) {

            if (hasImg) {
                UpThumbPic sender = new UpThumbPic();
                Intent imgIntent = new Intent();

                imgIntent.putExtra("AttachmentPath",pub.thumb_img);
                MtlResult imgres = new MtlResult();
                Intent tmpIntent = sender.doUiEvent(imgIntent,cx,imgres);

                String path= tmpIntent.getStringExtra("AttachmentPath");
                //Log.e("MYDEBUG","000="+path+"-00-"+pub.thumb_img);
                if (path != null) {
                    pub.thumb_img = path;
                } else {
                    pub.thumb_img = "";
                }

                if (selectedUser != null)
                    pub.getToGroupData(selectedUser);

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

            MtlServerGet.ServerResult res =  reg.handle();
            result.err_msg = res.err_msg;
            result.err_code = res.err_code ;
            result.result = res.result ;
        } else {
            result.err_code = MtlErrorCode.E_UI_ARG;
            result.err_msg = "arg User is NULL";

        }
        return t ;
    }
}
