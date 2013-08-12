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
public class Active extends SfsHttpGet {
    public Active(User user, String code) {
        setUrlSufix("active.do?userName=" + user.user_name + "&value=" + code);
    }

    @Override
    public void PraseResult(HttpResult result) {
        super.PraseResult(result);
        if (result.result.equalsIgnoreCase("success"))   {
            result.err_code = SfsErrorCode.Success;
        } else {
            result.err_code = SfsErrorCode.E_ACTIVITY;
            result.err_msg = "激活失败";
        }
    }
}
