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
        setUrlSufix("register.do?userName=" + user.user_name +
                "&passwd=" + user.passwd+"&nickName="+user.nick_name+
                "&headImg="+user.head_img);
    }

    @Override
    public void PraseResult(ServerResult result) {
        super.PraseResult(result);
        if (result.result.equalsIgnoreCase("success"))   {
            result.err_code = SfsErrorCode.Success;
        } else {
            result.err_code = SfsErrorCode.E_REGISTER;
            result.err_msg = "注册失败";
        }
    }
}
