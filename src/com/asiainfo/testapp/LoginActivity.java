package com.asiainfo.testapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.asiainfo.R;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;
import com.asiainfo.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
    TextView Tv_nikeName;
    EditText Ed_nikeName;
    ImageView Iv_headImg;
    User user;
    boolean boolean_ChgHeadImg = false;
    BitmapFactory.Options options;
    Bitmap bitmapImage;
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
        Ed_nikeName = ((EditText) findViewById(R.id.ed_nikename));
        Tv_nikeName =  (TextView)findViewById(R.id.tv_nikename);
        Iv_headImg =  ((ImageView) findViewById(R.id.iv_headview));
        user = getIntent().getParcelableExtra("User");

        options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = true;
        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;

        ((ImageView)findViewById(R.id.iv_headview)).setOnClickListener(pick_img_click);

        if (user != null) {
            ((EditText) findViewById(R.id.ed_email)).setText(user.user_name);
            ((EditText) findViewById(R.id.ed_passwd)).setText(user.passwd);
            Ed_nikeName.setText(user.nick_name);
            if (user.head_img != null) {


                Log.e("MYDEBUG","user.head_file ="+user.head_img);
                bitmapImage = BitmapFactory.decodeFile(user.head_img, options);
                Iv_headImg.setImageBitmap(bitmapImage);
            }

        } else {
            user = new User();
        }

        Ed_nikeName.setVisibility(View.GONE);
        Tv_nikeName.setVisibility(View.GONE);



    }

    View.OnClickListener cl_type = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
             if (IsLogin == true) {
                 Tv_Select.setText("Login ?");
                 Bt_loginOrReg.setText("register");
                 IsLogin = false;
                 Ed_nikeName.setVisibility(View.VISIBLE);
                 Tv_nikeName.setVisibility(View.VISIBLE);
                 Iv_headImg.setImageBitmap(null);
             } else {
                 Tv_Select.setText("Register ?");
                 Bt_loginOrReg.setText("login");
                 IsLogin = true;
                 Ed_nikeName.setVisibility(View.GONE);
                 Tv_nikeName.setVisibility(View.GONE);
                 Iv_headImg.setImageBitmap(bitmapImage);
             }

        }
    };

    Button.OnClickListener cl_loginOrReg = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            boolean chg_user = false;

            if (!user.user_name.equals(((EditText)findViewById(R.id.ed_email)).getText().toString())) {
                // 用户换了ID 登录，或者注册新ID
                user.user_name =  ((EditText)findViewById(R.id.ed_email)).getText().toString();
                user.passwd = ((EditText)findViewById(R.id.ed_passwd)).getText().toString();
                user.nick_name = ""; // 去服务器上获取
                Log.e("MYDEBUG","-------------");
                chg_user = true;
            } else {
                // 仅仅登录
                user.passwd = ((EditText)findViewById(R.id.ed_passwd)).getText().toString();
            }
            if (user.user_name.indexOf("@") == -1) {
                Toast.makeText(getApplicationContext(),"UserName must be email",Toast.LENGTH_LONG).show();
            }
            if (user.passwd.length() == 0) {
                Toast.makeText(getApplicationContext(),"Please input Passwd ! ",Toast.LENGTH_LONG).show();
            }

            if (!IsLogin || chg_user) {
                // 如果是注册需要把头像清除
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, com.asiainfo.testapp.sfsService.class);
                intent.setAction("ClearUserData");
                startService(intent);
            }

            if (boolean_ChgHeadImg) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, com.asiainfo.testapp.sfsService.class);
                intent.setAction("PostLongTimeAttachement");
                intent.putExtra("AttachmentType","jpeg");
                intent.putExtra("AttachmentPath",user.head_img);
                intent.putExtra("User",user);
                startService(intent);
            }



            if (IsLogin) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, com.asiainfo.testapp.sfsService.class);
                intent.setAction("UserLogin");
                user.status = User.NORMAL;
                intent.putExtra("User",user);
                intent.putExtra("IsLoginChgUser",chg_user);
                startService(intent);
            } else {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, com.asiainfo.testapp.sfsService.class);
                intent.setAction("UserRegister");
                user.status = User.NO_ACTIVE;
                intent.putExtra("User",user);
                startService(intent);
                if (user.nick_name.length() > 0) {
                    Toast.makeText(getApplicationContext(),"Please input a nike_name ! ",Toast.LENGTH_LONG).show();
                }
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
            User user = new User(
                    ((EditText)findViewById(R.id.ed_email)).getText().toString(),
                    ((EditText)findViewById(R.id.ed_passwd)).getText().toString(),
                    User.NORMAL,"","",0
            );
            if (action.equals("UserLogin_RES")) {
                SfsResult res =   intent.getParcelableExtra("UI_RESULT");
                if (res != null) {
                    if (res.err_code == SfsErrorCode.Success) {
                        // 跳转到主界面
                        Intent mainIntent = new Intent();
                        mainIntent.setClass(getApplicationContext(),sfsFrame.class);
                        mainIntent.putExtra("User",user);
                        startActivity(mainIntent);
                        finish();
                    } else if (res.err_code == SfsErrorCode.E_USER_INACITVE) {
                        Intent activeIntent = new Intent();
                        activeIntent.setClass(getApplicationContext(),ActiveActivity.class);
                        user.status = User.NO_ACTIVE;
                        activeIntent.putExtra("User",user);
                        startActivity(activeIntent);
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
                        user.status = User.NO_ACTIVE;
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

    // 处理头像
    ImageView.OnClickListener pick_img_click = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);//裁剪框比例
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 80);//输出图片大小
            intent.putExtra("outputY", 80);
            intent.putExtra("return-data", true);
            startActivityForResult(Intent.createChooser(intent, "选择图片"), R.id.iv_headview);

        }
    };

    // 处理响应
    protected  void onActivityResult(int requestCode, int resultCode,Intent data) {
        if  (resultCode == RESULT_CANCELED) return;
        if (requestCode == R.id.iv_headview) {
            Log.e("MYDEBUG","select_img_file_name : "+data.getData());
            Bitmap bmp=data.getParcelableExtra("data");
            Iv_headImg.setImageBitmap(bmp);
            try {
                if (((sfsApplication)getApplication()).checkSdCard() == true) {
                    File longDir = new File(((sfsApplication) getApplication()).getLongTimeDir());
                    if (!longDir.exists()) {
                        longDir.mkdirs();
                    }

                    File groupPicName = File.createTempFile("UserHeadImg", ".jpeg",longDir);

                    FileOutputStream out;
                    String picNameStr = groupPicName.getAbsolutePath();
                    out = new FileOutputStream(picNameStr);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    user.head_img = picNameStr;
                    boolean_ChgHeadImg = true;

                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}