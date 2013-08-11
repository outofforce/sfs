package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import com.asiainfo.model.SfsResult;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public interface ISfsUiEvent {
    Intent doUiEvent(Intent intent, Context cx, SfsResult result);
}
