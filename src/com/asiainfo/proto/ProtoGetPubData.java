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
public class ProtoGetPubData extends MtlServerGet {
    public ProtoGetPubData(User user,Long timeMills) {
        setBody2("userName", user.user_name);
        setBody2("timeMills", "" + timeMills);
        setBody2("maxCount",""+20);
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
