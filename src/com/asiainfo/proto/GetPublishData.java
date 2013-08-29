package com.asiainfo.proto;

import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午12:36
 * To change this template use File | Settings | File Templates.
 */
public class GetPublishData extends MtlServerGet {
    public GetPublishData(User user, Long pubMaxId) {
        setBody2("userId",""+user.remote_id);
        setBody2("publishId", "" + pubMaxId);
        setUrlSufix("queryPubdata.do");
    }

    @Override
    public void PraseResult(ServerResult result) {
        super.PraseResult(result);
        //Log.e("MYDEBUG", result.result.substring(0, 7));


        if (result.result.substring(0,7).equalsIgnoreCase("success"))   {

            result.err_code = MtlErrorCode.Success;
            result.result = result.result.substring(7);
        } else {
            result.err_code = MtlErrorCode.E_GET_PUBLIST;
            result.err_msg = "获取公告栏错误";
        }
    }
}
