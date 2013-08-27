package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;
import com.asiainfo.tools.NetTools;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:32
 * To change this template use File | Settings | File Templates.
 */


public class GetWatcherPic implements ISfsUiEvent {

    @Override
    public Intent doUiEvent(Intent intent, Context cx, MtlResult result) {

        GetThumbPic hander = new GetThumbPic();
        return hander.doUiEvent(intent,cx,result);

    }
}
