package com.asiainfo.testapp;

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
import android.widget.TextView;
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
public class LoginActivity extends Activity {
    public final static String TAG="LoginActivity";
    Button Bt_loginOrReg;
    DataReceiver dataReceiver;
    TextView Tv_Select;
    Boolean IsLogin = true;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        dataReceiver = new DataReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("UserLogin_RES");
        filter.addAction("UserRegister_RES");
        registerReceiver(dataReceiver, filter);

        Bt_loginOrReg = (Button)findViewById(R.id.bt_LoginOrReg);
        Bt_loginOrReg.setOnClickListener(cl_loginOrReg);
        Tv_Select = (TextView)findViewById(R.id.tv_SelectLoginReg);
        Tv_Select.setOnClickListener(cl_type);




    }

    View.OnClickListener cl_type = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
             if (IsLogin == true) {
                 Tv_Select.setText("Login ?");
                 Bt_loginOrReg.setText("register");
                 IsLogin = false;

             } else {
                 Tv_Select.setText("Register ?");
                 Bt_loginOrReg.setText("login");
                 IsLogin = true;
             }

        }
    };

    Button.OnClickListener cl_loginOrReg = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (IsLogin) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, com.asiainfo.testapp.sfsService.class);
                intent.setAction("UserLogin");
                User user = new User(
                                    ((EditText)findViewById(R.id.ed_email)).getText().toString(),
                                    ((EditText)findViewById(R.id.ed_passwd)).getText().toString(),
                                    User.NORMAL
                                    );


                intent.putExtra("User",user);
                startService(intent);
            } else {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, com.asiainfo.testapp.sfsService.class);
                intent.setAction("UserRegister");
                User user = new User(
                        ((EditText)findViewById(R.id.ed_email)).getText().toString(),
                        ((EditText)findViewById(R.id.ed_passwd)).getText().toString(),
                        User.NO_ACTIVE
                );
                intent.putExtra("User",user);
                startService(intent);
            }
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
            if (action.equals("UserLogin_RES")) {
                SfsResult res =   intent.getParcelableExtra("UI_RESULT");
                if (res != null) {
                    if (res.err_code == SfsErrorCode.Success) {
                        // 跳转到主界面
                        Intent mainIntent = new Intent();
                        mainIntent.setClass(getApplicationContext(),sfsFrame.class);
                        //mainIntent.putExtra("User", user);
                        startActivity(mainIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),res.err_msg+" "+res.err_code,Toast.LENGTH_LONG).show();
                    }
                }
            } else if (action.equals("UserRegister_RES")) {
                SfsResult res =   intent.getParcelableExtra("UI_RESULT");
                if (res != null) {
                    if (res.err_code == SfsErrorCode.Success) {
                        // 跳转到主界面
                        Intent activeIntent = new Intent();
                        activeIntent.setClass(getApplicationContext(),ActiveActivity.class);
                        User user = new User(
                                ((EditText)findViewById(R.id.ed_email)).getText().toString(),
                                ((EditText)findViewById(R.id.ed_passwd)).getText().toString(),
                                User.NORMAL
                        );
                        activeIntent.putExtra("User",user);
                        startActivity(activeIntent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),res.err_msg+" "+res.err_code,Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}