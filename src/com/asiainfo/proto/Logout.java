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
public class Logout extends MtlServerGet {
    public Logout(User user) {
        setUrlSufix("logout.do?userName=" + user.user_name + "&passwd=" + user.passwd);
    }

    @Override
    public void PraseResult(ServerResult result) {
        super.PraseResult(result);
        if (result.result.equalsIgnoreCase("success"))   {
            result.err_code = MtlErrorCode.Success;
        } else {
            result.err_code = MtlErrorCode.E_LOGOUT;
        }
    }
}
