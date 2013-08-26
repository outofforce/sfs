package com.asiainfo.proto;

import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午12:36
 * To change this template use File | Settings | File Templates.
 */
public class YouHaveMessage extends SfsServerGet {
    public YouHaveMessage(int max_pub_id) {
        setBody2("publishId", ""+max_pub_id);
        setUrlSufix("youHaveMessage.do");
    }

    @Override
    public void PraseResult(ServerResult result) {
        super.PraseResult(result);
        if (result.result.substring(0,7).equalsIgnoreCase("success"))   {

            result.err_code = SfsErrorCode.Success;
            result.result = result.result.substring(7);
        } else {
            result.err_code = SfsErrorCode.E_QUERY;
        }
    }
}
