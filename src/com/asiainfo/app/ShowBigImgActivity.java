package com.asiainfo.app;

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
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;
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
public class ShowBigImgActivity extends Activity {
    public final static String TAG="ShowBigImgActivity";
    DataReceiver dataReceiver;
    ImageView Iv_ShowImg;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);

        dataReceiver = new DataReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("GetBigPic_RES");
        registerReceiver(dataReceiver, filter);
        Iv_ShowImg =  ((ImageView) findViewById(R.id.imageView));
        Intent intent = new Intent();
        intent.setClass(this, MtlService.class);
        intent.putExtra("AttachmentType","image");
        intent.putExtra("AttachmentPath",getIntent().getStringExtra("AttachmentPath"));
        intent.setAction("GetBigPic") ;
        startService(intent);
        Iv_ShowImg.setOnClickListener(cl_type);

    }

    View.OnClickListener cl_type = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
              finish();
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
            if (action.equals("GetBigPic_RES")) {
                MtlResult res =   intent.getParcelableExtra("UI_RESULT");
                if (res != null) {
                    if (res.err_code == MtlErrorCode.Success) {
                        // 跳转到主界面
                        String path = intent.getStringExtra("AttachmentPath");
                        final BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        options.inPurgeable = true;
                        options.inInputShareable = true;
                        Bitmap bitmapImage = BitmapFactory.decodeFile(path,options);
                        Iv_ShowImg.setImageBitmap(bitmapImage);
                    } else {
                        Toast.makeText(getApplicationContext(),res.err_msg+" "+res.err_code,Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}