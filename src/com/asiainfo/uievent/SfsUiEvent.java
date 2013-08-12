package com.asiainfo.uievent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public class SfsUiEvent {
    /*
    private ISfsUiEvent findByAction(String action) {
        if (action.equals("QueryStartInfo")) {
            return new QueryStartInfo();
        } else if (action.equals("UserActive")) {
            return new UserActive();
        } else if (action.equals("UserLogin")) {
            return new UserLogin();
        } else if (action.equals("UserLogout")) {
            return new UserLogout();
        } else if (action.equals("UserRegister")) {
            return new UserRegister();
        } else {
            return null;
        }
    }


    public  Intent processUiEvent(String action,Intent intent,Context cx) {
        Intent t ;
        SfsResult  ret ;
        try {
            ret = new SfsResult("","",SfsErrorCode.Success);

            Log.e("MYDEBUG",action);
            ISfsUiEvent cls = findByAction(action);
            if (cls == null) throw new ClassNotFoundException();
            Log.e("MYDEBUG","out...1");
            t = cls.doUiEvent(intent,cx,ret);
            Log.e("MYDEBUG","out...2");

            if (t != null) {
                t.setAction(action+"_RES");
                t.putExtra("UI_RESULT",ret);
            } else {
                t = new Intent();
                t.setAction(action+"_RES");
                ret = new SfsResult(action+ "return null" ,"", SfsErrorCode.E_IMP_ERROR);
                t.putExtra("UI_RESULT",ret);
            }

        } catch (ClassNotFoundException e) {
            t = new Intent();
            t.setAction(action+"_RES");
            ret = new SfsResult(e.getMessage(),"", SfsErrorCode.E_CLASS_NO_FOUNT);
            t.putExtra("UI_RESULT",ret);
            e.printStackTrace();
        } catch(Exception e) {
            t = new Intent();
            t.setAction(action+"_RES");
            ret = new SfsResult(e.getMessage(),"", SfsErrorCode.E_UNDEFINE);
            t.putExtra("UI_RESULT",ret);
            e.printStackTrace();
        }

        return t;

    }     */


    public  Intent processUiEvent(String action,Intent intent,Context cx) {
        Intent t = null;
        Bundle b = null;
        SfsResult  ret = null;
        try {
            ret = new SfsResult("","",SfsErrorCode.Success);

            Log.e("MYDEBUG",action);
            Class cls = Class.forName("com.asiainfo.uievent."+action);
            Method m = cls.getDeclaredMethod("doUiEvent", new Class[]{Intent.class, Context.class, SfsResult.class});
            t = (Intent) m.invoke(cls.newInstance(),intent,cx,ret);
            Log.e("MYDEBUG","out...");
            if (t != null) {
                 t.setAction(action+"_RES");
                 t.putExtra("UI_RESULT",ret);
            } else {
                t = new Intent();
                t.setAction(action+"_RES");
                ret = new SfsResult(action+ "return null" ,"", SfsErrorCode.E_IMP_ERROR);
                t.putExtra("UI_RESULT",ret);
            }

        } catch (ClassNotFoundException e) {
            t = new Intent();
            t.setAction(action+"_RES");
            ret = new SfsResult(e.getMessage(),"", SfsErrorCode.E_CLASS_NO_FOUNT);
            t.putExtra("UI_RESULT",ret);
            e.printStackTrace();
        } catch(Exception e) {
            t = new Intent();
            t.setAction(action+"_RES");
            ret = new SfsResult(e.getMessage(),"", SfsErrorCode.E_UNDEFINE);
            t.putExtra("UI_RESULT",ret);
            e.printStackTrace();
        }

        return t;

    }
}
