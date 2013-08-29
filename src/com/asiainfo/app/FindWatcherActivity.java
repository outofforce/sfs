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

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-8
 * Time: 下午4:59
 * To change this template use File | Settings | File Templates.
 */
public class FindWatcherActivity extends Activity {
    EditText Et_Context;
    Button Bt_SearchWatcher;
    private MtlListView mWatchItemListView;
    private WatchItemAdapter mWatchItemAdpater;
    private boolean mIsLoading = false;
    DataReceiver dataReceiver;
    User user;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        user = getIntent().getParcelableExtra("User");
        if (user == null) {
            throw new NullPointerException("No User Data!");
        }
		setContentView(R.layout.search_watcher);

        Bt_SearchWatcher=(Button)findViewById(R.id.bt_search);
        Et_Context=(EditText)findViewById(R.id.ed_searchContext);

        Bt_SearchWatcher.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                if (!mIsLoading )
                    loadHistory();

                Timer timer=new Timer();
                timer.schedule(new TimerTask(){

                    @Override
                    public void run() {
                        mIsLoading = false;
                    }

                }, 1000);
			}
		});
        dataReceiver = new DataReceiver();

        mWatchItemListView = (MtlListView) findViewById(R.id.Lv_Search);
        mWatchItemAdpater = new WatchItemAdapter(getLayoutInflater(),this,user);

        mWatchItemListView.setAdapter(mWatchItemAdpater);
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
        filter.addAction("QueryWatchData_RES");
        filter.addAction("GetWatcherPic_RES");
        filter.addAction("ChangeWatcherStatus_RES");
        registerReceiver(dataReceiver, filter);
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
                        ((User) mWatchItemAdpater.getItem(pos)).head_img_load = User.IMG_LOADED ;
                        ((User) mWatchItemAdpater.getItem(pos)).head_img_remote_addr = path ;
                        mWatchItemAdpater.notifyDataSetChanged();
                    }
                }  else if (action.equals("ChangeWatcherStatus_RES")) {
                    int pos = intent.getIntExtra("ListPos",0);
                    User user = intent.getParcelableExtra("WatcherUser");
                    ((User) mWatchItemAdpater.getItem(pos)).is_my_watcher = user.is_my_watcher ;
                    mWatchItemAdpater.notifyDataSetChanged();
                }  else if (action.equals("QueryWatchData_RES")) {
                    ArrayList<User> pblist  = intent.getParcelableArrayListExtra("WatchDatas");
                    mWatchItemAdpater.clear();
                    if (pblist != null) {
                        for (int i=0;i<pblist.size();i++) {
                            User item =  pblist.get(i);
                            mWatchItemAdpater.add(item);
                        }
                        if (pblist.size()>0) {
                            mWatchItemListView.requestFocusFromTouch();
                            mWatchItemListView.setSelection(0);
                            mIsLoading = false;
                        }
                        //Toast.makeText(FindWatcherActivity.this,"收到"+pblist.size()+"条记录，一共"+ mWatchItemAdpater.getCount(),Toast.LENGTH_SHORT).show();
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
