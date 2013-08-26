package com.asiainfo.proto;

import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.PublishData;
import com.asiainfo.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午12:36
 * To change this template use File | Settings | File Templates.
 */
public class PostPublishData extends MtlServerGet {
    public PostPublishData(User user, PublishData data) {
        setUrlSufix("postPubData.do");
        setBody2("userId", "" + user.remote_id);
        setBody2("postImg", data.context_img);
        setBody2("postContext", data.pub_context);
        setBody2("simpleImg", data.thumb_img);
        setBody2("gisInfo", data.gis_info);
        setBody2("ttl_type",""+1);
    }

    @Override
    public void PraseResult(ServerResult result) {
        super.PraseResult(result);
        //Log.e("MYDEBUG", result.result.substring(0, 7));


        if (result.result.length()>=7 && result.result.substring(0,7).equalsIgnoreCase("success"))   {
            result.err_code = MtlErrorCode.Success;
            result.result = result.result.substring(7);
        } else {
            result.err_code = MtlErrorCode.E_GET_PUBLIST;
            result.err_msg = "获取公告栏错误";
        }
    }
}
