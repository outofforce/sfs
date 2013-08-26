package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public class SfsUiEvent {


    public  Intent processUiEvent(String action,Intent intent,Context cx) {
        Intent t = null;
        Bundle b = null;
        MtlResult ret = null;
        try {
            ret = new MtlResult("","", MtlErrorCode.Success);

            Log.e("MYDEBUG","in.. "+action);
            Class cls = Class.forName("com.asiainfo.uievent."+action);
            Method m = cls.getDeclaredMethod("doUiEvent", new Class[]{Intent.class, Context.class, MtlResult.class});
            t = (Intent) m.invoke(cls.newInstance(),intent,cx,ret);

            if (t != null) {
                 t.setAction(action+"_RES");
                 t.putExtra("UI_RESULT",ret);
            } else {
                t = new Intent();
                t.setAction(action+"_RES");
                ret = new MtlResult(action+ "return null" ,"", MtlErrorCode.E_IMP_ERROR);
                t.putExtra("UI_RESULT",ret);
            }

        } catch (ClassNotFoundException e) {
            t = new Intent();
            t.setAction(action+"_RES");
            ret = new MtlResult(e.getMessage(),"", MtlErrorCode.E_CLASS_NO_FOUNT);
            t.putExtra("UI_RESULT",ret);
            e.printStackTrace();
        } catch(Exception e) {
            t = new Intent();
            t.setAction(action+"_RES");
            ret = new MtlResult(e.getMessage(),"", MtlErrorCode.E_UNDEFINE);
            t.putExtra("UI_RESULT",ret);
            e.printStackTrace();
        }
        Log.e("MYDEBUG","out.. "+action+" \n ret.error_code="+ ret.err_code+ "\n ret.error_msg="+ ret.err_msg);
        return t;

    }
}
