package com.asiainfo.proto;

import com.asiainfo.model.PublishData;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午12:36
 * To change this template use File | Settings | File Templates.
 */
public class PostPublishData extends SfsServerGet {
    public PostPublishData(User user, PublishData data) {
        setUrlSufix("postPubData.do?userName="+user.user_name+
                "&postImg="+data.context_img+
                "&postContext="+data.pub_context +
                "&simpleImg="+data+
                "&gisInfo="+data.gis_info+
                "&ttl_type="+1);
    }

    @Override
    public void PraseResult(ServerResult result) {
        super.PraseResult(result);
        //Log.e("MYDEBUG", result.result.substring(0, 7));


        if (result.result.substring(0,7).equalsIgnoreCase("success"))   {

            result.err_code = SfsErrorCode.Success;
            result.result = result.result.substring(7);
        } else {
            result.err_code = SfsErrorCode.E_GET_PUBLIST;
            result.err_msg = "获取公告栏错误";
        }
    }
}
