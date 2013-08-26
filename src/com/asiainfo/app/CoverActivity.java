package com.asiainfo.app;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.asiainfo.R;
import android.app.Activity;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;
import com.asiainfo.model.User;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-10
 * Time: 下午2:53
 * To change this template use File | Settings | File Templates.
 */
public class CoverActivity extends Activity {

    private DataReceiver dataReceiver;
    static final String TAG = "CoverActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cover);
        dataReceiver = new DataReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("QueryStartInfo_RES");
        registerReceiver(dataReceiver, filter);



        Timer timer=new Timer();
        timer.schedule(new TimerTask(){

            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(CoverActivity.this, MtlService.class);
                intent.setAction("QueryStartInfo");
                startService(intent);
            }

        }, 1000);
    }

    private class DataReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.v(TAG, "===================" + action);

            if (action.equals("QueryStartInfo_RES")) {
                MtlResult res =   intent.getParcelableExtra("UI_RESULT");
                if (res != null) {
                    if (res.err_code == MtlErrorCode.E_DEV_NO_USER) {
                        // 跳转到登陆界面
                        Intent loginIntent = new Intent();
                        loginIntent.setClass(getApplicationContext(),LoginActivity.class);
                        startActivity(loginIntent);
                        finish();

                    } else {
                        User user = intent.getParcelableExtra("User");
                        if (user.status == User.NO_ACTIVE) {
                            // 跳转的激活界面
                            Intent activeIntent = new Intent();
                            activeIntent.setClass(getApplicationContext(),ActiveActivity.class);
                            activeIntent.putExtra("User",user);
                            startActivity(activeIntent);
                            finish();
                        } else if (user.status == User.LOGOUT) {
                            // 跳转的登陆界面
                            Intent loginIntent = new Intent();
                            loginIntent.setClass(getApplicationContext(),LoginActivity.class);
                            loginIntent.putExtra("User",user);
                            startActivity(loginIntent);
                            finish();
                        } else if (user.status == User.NORMAL) {
                            // 跳转到主界面
                            Intent mainIntent = new Intent();
                            mainIntent.setClass(getApplicationContext(),MtlFragmentActivity.class);
                            mainIntent.putExtra("User",user);
                            startActivity(mainIntent);
                            finish();
                        }
                    }
                }
            }
        }
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(dataReceiver);
        super.onDestroy();
    }
}