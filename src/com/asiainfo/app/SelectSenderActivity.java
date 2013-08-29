package com.asiainfo.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.asiainfo.R;
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;
import com.asiainfo.model.User;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-8
 * Time: 下午4:59
 * To change this template use File | Settings | File Templates.
 */
public class SelectSenderActivity extends Activity {
    EditText Et_Context;
    Button Bt_Sender;
    private MtlListView mWatchItemListView;
    private SelectSenderAdapter mSelectSenderAdapter;
    private boolean mIsLoading = false;
    DataReceiver dataReceiver;
    User user;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        user = getIntent().getParcelableExtra("User");
        if (user == null) {
            throw new NullPointerException("No User Data!");
        }
		setContentView(R.layout.select_user_to_send);

        Bt_Sender=(Button)findViewById(R.id.bt_search);
        Et_Context=(EditText)findViewById(R.id.ed_searchContext);

        Bt_Sender.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                // 获取  mSelectSenderAdapter 里面 被选中的用户 列表 ，发送
                Intent data = new Intent();
                ArrayList<User> list =  mSelectSenderAdapter.getSelectItems();
                data.putExtra("Post_To",list);
                setResult(Activity.RESULT_OK,data);
                finish();
			}
		});
        dataReceiver = new DataReceiver();

        mWatchItemListView = (MtlListView) findViewById(R.id.Lv_Search);
        mSelectSenderAdapter = new SelectSenderAdapter(getLayoutInflater(),this,user);

        mWatchItemListView.setAdapter(mSelectSenderAdapter);
        mWatchItemListView.setonRefreshListener(new MtlListView.OnRefreshListener() {
            public void onRefresh() {
            }
        });
	}

    private void loadHistory(){

        Intent intent = new Intent();
        intent.setClass(this, MtlService.class);
        intent.setAction("QueryWatchData");
        User queryUser = new User();
        queryUser.nick_name = Et_Context.getText().toString().trim();
        intent.putExtra("QueryUser",queryUser);
        intent.putExtra("User", user);
        startService(intent);
        mIsLoading = true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("GetWatcherPic_RES");
        filter.addAction("GetLocalMyWatchData_RES");
        registerReceiver(dataReceiver, filter);

        Intent intent = new Intent();
        intent.setClass(this, MtlService.class);
        intent.setAction("GetLocalMyWatchData");
        startService(intent);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(dataReceiver);
        super.onPause();
    }


    private class DataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            MtlResult res = intent.getParcelableExtra("UI_RESULT");
            if (res.err_code == MtlErrorCode.Success) {
                if (action.equals("GetWatcherPic_RES")) {
                    int pos = intent.getIntExtra("ListPos",-1);
                    String path = intent.getStringExtra("AttachmentPath");
                    if (path != null) {
                        ((User) mSelectSenderAdapter.getItem(pos)).head_img_load = User.IMG_LOADED ;
                        ((User) mSelectSenderAdapter.getItem(pos)).head_img_remote_addr = path ;
                        mSelectSenderAdapter.notifyDataSetChanged();
                    }

                }  else if (action.equals("GetLocalMyWatchData_RES")) {
                    ArrayList<User> pblist  = intent.getParcelableArrayListExtra("WatchDatas");
                    mSelectSenderAdapter.clear();
                    if (pblist != null) {
                        for (int i=0;i<pblist.size();i++) {
                            User item =  pblist.get(i);
                            mSelectSenderAdapter.add(item);
                        }
                        if (pblist.size()>0) {
                            mWatchItemListView.requestFocusFromTouch();
                            mWatchItemListView.setSelection(0);

                        }
                        mIsLoading = false;
                        //Toast.makeText(FindWatcherActivity.this,"收到"+pblist.size()+"条记录，一共"+ mSelectSenderAdapter.getCount(),Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), res.err_msg + " code= " + res.err_code, Toast.LENGTH_LONG).show();
            }

        }
    }

//	Runnable runnable = new Runnable(){
//		@Override
//		public void run() {
//            Timer timer=new Timer();
//            timer.schedule(new TimerTask(){
//
//                @Override
//                public void run() {
//                    Intent intent = new Intent();
//                    intent.setClass(FindWatcherActivity.this, MtlService.class);
//                    intent.setAction("QueryStartInfo");
//                    startService(intent);
//                }
//
//            }, 1000);
//		}
//	};
}
