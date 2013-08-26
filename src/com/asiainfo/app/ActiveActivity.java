package com.asiainfo.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.asiainfo.R;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;
import com.asiainfo.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-11
 * Time: 下午6:56
 * To change this template use File | Settings | File Templates.
 */
public class ActiveActivity extends Activity {
    public final static String TAG="ActiveActivity";
    Button Bt_Active;
    EditText Ed_Email;
    DataReceiver dataReceiver;
    User user;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active);

        dataReceiver = new DataReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("UserActive_RES");
        registerReceiver(dataReceiver, filter);

        Bt_Active = (Button)findViewById(R.id.bt_Active);
        Bt_Active.setOnClickListener(cl_active);
        Intent intent = getIntent();
        user = intent.getParcelableExtra("User");
        Ed_Email = (EditText) findViewById(R.id.ed_email);
        Ed_Email.setText(user.user_name);



    }

    Button.OnClickListener cl_active = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ActiveActivity.this, sfsService.class);
            intent.setAction("UserActive");
            intent.putExtra("ActiveCode", ((EditText) findViewById(R.id.ed_ActiveCode)).getText().toString());
            intent.putExtra("User",user);
            startService(intent);
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(dataReceiver);
        super.onDestroy();
    }

    private class DataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.v(TAG, "===================" + action);

            if (action.equals("UserActive_RES")) {
                SfsResult res =   intent.getParcelableExtra("UI_RESULT");
                if (res != null) {
                    if (res.err_code == SfsErrorCode.Success) {
                        // 跳转到主界面
                        Intent mainIntent = new Intent();
                        mainIntent.setClass(getApplicationContext(),sfsFrame.class);
                        mainIntent.putExtra("User", user);
                        startActivity(mainIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),res.err_msg+" "+res.err_code,Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}