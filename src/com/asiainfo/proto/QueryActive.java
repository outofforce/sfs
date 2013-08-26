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
public class QueryActive extends MtlServerGet {
    public QueryActive(User user) {
        setBody2("userName", user.user_name);
        setUrlSufix("queryActive.do");
    }

    @Override
    public void PraseResult(ServerResult result) {
        super.PraseResult(result);
        if (result.result.equalsIgnoreCase("success"))   {
            result.err_code = MtlErrorCode.Success;
        } else {
            result.err_code = MtlErrorCode.E_QUERY;
        }
    }
}
