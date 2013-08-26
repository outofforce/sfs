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
public class Register extends SfsServerGet {
    public Register(User user) {
        setBody2("userName",user.user_name);
        setBody2("passwd",user.passwd);
        setBody2("nickName",user.nick_name);
        setBody2("headImg",user.head_img);
        setUrlSufix("register.do");
    }

    @Override
    public void PraseResult(ServerResult result) {
        super.PraseResult(result);


        if (result.result.length()>=7 && result.result.substring(0,7).equalsIgnoreCase("success"))   {
            result.err_code = SfsErrorCode.Success;
            result.result = result.result.substring(7);
        } else {
            result.err_code = SfsErrorCode.E_REGISTER;
            result.err_msg = "注册失败";
        }
    }
}
