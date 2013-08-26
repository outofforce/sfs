package com.asiainfo.testapp;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.widget.*;
import com.asiainfo.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import com.asiainfo.lib.DrawerFragment;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;
import com.asiainfo.model.User;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-6
 * Time: 下午10:04
 * To change this template use File | Settings | File Templates.
 */
public class sfsFrame extends FragmentActivity {
    public static final String TAG = "sfsFrame";
    public DrawerLayout drawerLayout;// 侧边栏布局
    public ListView leftList;// 侧边栏内的选项
    public ArrayAdapter<String> arrayAdapter;
    private String[] items;
    public static final int M_EXIT=0;
    public static final int M_SETUP=1;
    public static final int M_TODO=2;

    DataReceiver dataReceiver;


    private PendingIntent mAlarmSender;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        dataReceiver = new DataReceiver();
        user = getIntent().getParcelableExtra("User");
        if (user == null) {
            throw new NullPointerException("No User Data!");
        }
        //Intent  alarmIntent = new Intent();
        //long firstTime = SystemClock.elapsedRealtime();
        //alarmIntent.setClass(sfsFrame.this, sfsService.class);
        //alarmIntent.setAction("CheckNewMessage");
        // mAlarmSender = PendingIntent.getService(sfsFrame.this,
        //        0, alarmIntent, 0);



        //AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        //am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
        //        firstTime, 60*1000, mAlarmSender);


    }

    public User getUser() {
        return user;
    }
    // 初始化控件
    private void initViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        items = getResources().getStringArray(R.array.left_array);
        leftList = (ListView) findViewById(R.id.left_drawer);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.listitem, new String[] {" 退 出 "," 设 置 "," Todo "});
        leftList.setAdapter(arrayAdapter);
        leftList.setOnItemClickListener(itemListener);
        initFragments();

    }

    public class sfsReceiver {
        public onSfsDataReceiver mOnSfsDataRecieiver;
        public IntentFilter mIntentFilter;
        public  sfsReceiver(onSfsDataReceiver r,IntentFilter f) {
            mOnSfsDataRecieiver = r;
            mIntentFilter = f;
        }
    }

    HashMap<String,sfsReceiver> mRecieverMap = new HashMap<String,sfsReceiver>();
    //onSfsDataReceiver mSfsDataReciver;
    public void registerSfsDataReciever (String key,onSfsDataReceiver receiver,IntentFilter filter) {
        mRecieverMap.put(key,new sfsReceiver(receiver,filter));
    }
    public void  unregisterSfsDataReciever (String key)  {
        mRecieverMap.remove(key);
    }

    // 添加碎片
    private void initFragments() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        DrawerFragment fragment = new DrawerFragment();
        fragment.setDrawerLayout(drawerLayout, leftList);
        transaction.add(R.id.main_content, fragment);
        transaction.commit();
    }

    // 选项点击事件
    OnItemClickListener itemListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int position,
                                long arg3) {
            // TODO Auto-generated method stub
            // 设置Activity的标题，这里只是用来做一个测试，你可以在这里用来处理单击侧边栏的选项事件
            setTitle(items[position]+" "+position);
            // 关闭侧边栏
            drawerLayout.closeDrawer(leftList);
            Log.i("onItemSelected",
                    "open?:" + drawerLayout.isDrawerOpen(leftList) + ' '+ position);

            if (position == M_EXIT) {
                Intent intent = new Intent();
                intent.setClass(sfsFrame.this, com.asiainfo.testapp.sfsService.class);
                intent.setAction("UserLogout");
                intent.putExtra("User",user);
                startService(intent);
            }


        }

    };

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onKeyDown(int,
     * android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        // 使用menu键打开或关闭侧边栏
        if (keyCode == KeyEvent.KEYCODE_MENU) {

            if (drawerLayout.isDrawerOpen(leftList)) {
                drawerLayout.closeDrawer(leftList);
            } else {
                drawerLayout.openDrawer(leftList);
            }
        }
        return super.onKeyDown(keyCode, event);
    }




    @Override
    protected void onResume() {
        super.onResume();    //To change body of overridden methods use File | Settings | File Templates.
        IntentFilter filter = new IntentFilter();
        filter.addAction("UserLogout_RES");
        filter.addAction("QueryPublishData_RES");
        filter.addAction("GetLocalPublishData_RES");
        filter.addAction("GetThumbPic_RES");
        filter.addAction("PostPublish_RES");

        registerReceiver(dataReceiver, filter);

    }

    @Override
    protected void onPause() {
        unregisterReceiver(dataReceiver);
        super.onStop();    //To change body of overridden methods use File | Settings | File Templates.
    }

    private class DataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            SfsResult res = intent.getParcelableExtra("UI_RESULT");
            if (res.err_code == SfsErrorCode.Success) {
                if (action.equals("UserLogout_RES")) {
                    stopService(new Intent(sfsFrame.this,
                           com.asiainfo.testapp.sfsService.class));
                    finish();
                } else   {
                    Iterator it = mRecieverMap.entrySet().iterator();

                    while(it.hasNext()) {
                        Map.Entry entry = (Map.Entry) it.next();
                        sfsReceiver tReceiver = (sfsReceiver)entry.getValue();
                        if (tReceiver.mIntentFilter.hasAction(action))
                                tReceiver.mOnSfsDataRecieiver.onDataCome(intent);
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), res.err_msg + " code= " + res.err_code, Toast.LENGTH_LONG).show();
            }

        }
    }

}