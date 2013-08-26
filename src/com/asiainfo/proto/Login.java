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
public class Login extends MtlServerGet {

    public Login(User user,int flag) {
        setUrlSufix("login.do");
        //setBody("userName=" + user.user_name + "&passwd=" + user.passwd + "&flag=" + flag);
        setBody2("userName",user.user_name);
        setBody2("passwd",user.passwd);
        setBody2("flag",""+flag);

    }
    @Override
    public void PraseResult(ServerResult result) {
        super.PraseResult(result);
        if (result.result.length()>=7 && result.result.substring(0,7).equalsIgnoreCase("success"))   {
            result.err_code = MtlErrorCode.Success;
            result.result = result.result.substring(7);
        } else if (result.result.length()>=8 && result.result.equalsIgnoreCase("inactive")) {
            result.err_code = MtlErrorCode.E_USER_INACITVE;
        } else {
            result.err_code = MtlErrorCode.E_LOGIN;
            result.err_msg = result.result;
        }
    }
}
