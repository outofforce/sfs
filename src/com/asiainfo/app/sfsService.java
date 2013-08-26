package com.asiainfo.app;


import com.asiainfo.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;
import com.asiainfo.uievent.SfsUiEvent;

import java.util.concurrent.LinkedBlockingQueue;

public class sfsService extends Service {
    private NotificationManager mNM;
    static final String TAG = "sfsService";

    public Notification notification;

    public LinkedBlockingQueue<ProtoMsg> dealQueue = new LinkedBlockingQueue<ProtoMsg>();

    public static final int STATE_NONE = 0;

    private int mState = STATE_NONE;

    public doQueenThread doJobTread ;



    public synchronized void dealProtoData(ProtoMsg data) {
        String action = data.getIntent().getAction();
        Intent intent = data.getIntent();
        if (data.getDirect() == ProtoMsg.DIRECT.UI_EVENT) {
            SfsUiEvent eventhandle = new SfsUiEvent();
            Intent t = eventhandle.processUiEvent(action,intent,getApplication());
            sendBroadcast(t);
        } else if (data.getDirect() == ProtoMsg.DIRECT.UI_NOTIFY ) {
            if (intent.getAction().equals("ClearNotify"))
                mNM.cancel(R.string.sfs_service_start);
            else {
                SfsUiEvent eventhandle = new SfsUiEvent();
                Intent t = eventhandle.processUiEvent(action,intent,getApplication());
                SfsResult res = t.getParcelableExtra("UI_RESULT");
                if ((res != null) && (res.err_code == SfsErrorCode.Success))  {
                    if (t.getBooleanExtra("NeedNotify",false)) {
                        showNotification(res.result,"sfs");
                    }
                }
            }
        }

    }

    private class doQueenThread extends Thread {
        private Boolean status_run ;

        public doQueenThread() {
            status_run = true;
        }

        public void run() {
            while (status_run) {
                try {
                     ProtoMsg tdata = dealQueue.take();
                     dealProtoData(tdata);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }
        public void cancel() {
            status_run = false;
        }
    }



    public class LocalBinder extends Binder {
        sfsService getService() {
            return sfsService.this;
        }
    }

    @Override
    public void onCreate() {

        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.icon = android.R.drawable.stat_notify_voicemail;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.defaults |= Notification.FLAG_AUTO_CANCEL;

        doJobTread = new doQueenThread();
        doJobTread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "Received start id " + startId + ": " + intent);
        addToQ(intent);



        return START_STICKY;
    }

    public  void addToQ(Intent intent) {
        if (intent != null && intent.getAction() != null ) {

            if  (intent.getAction().equals("CheckNewMessage")) {
                dealQueue.add(new ProtoMsg("0",intent,ProtoMsg.DIRECT.UI_NOTIFY));
            } else if  (intent.getAction().equals("ClearNotify")) {
                dealQueue.add(new ProtoMsg("0",intent,ProtoMsg.DIRECT.UI_NOTIFY));
            } else {
                dealQueue.add(new ProtoMsg("0",intent,ProtoMsg.DIRECT.UI_EVENT));
            }
        }
    }


    @Override
    public void onDestroy() {
        //mNM.cancel(R.string.sfs_service_start);
        //Toast.makeText(this, R.string.sfs_service_end, Toast.LENGTH_SHORT).show();
        doJobTread.cancel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();

    private void showNotification() {
        notification = new Notification();
        notification.icon = android.R.drawable.stat_notify_voicemail;

        //notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;
        notification.defaults |= Notification.FLAG_AUTO_CANCEL;

        showNotification("on Service","Sfs");

    }

    private void showNotification(String msg,String head) {

        Intent t = new Intent();
        t.setClass(this, CoverActivity.class);
        t.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,t, 0);
        notification.tickerText = msg;
        notification.setLatestEventInfo(this, head, msg, contentIntent);
        mNM.notify(R.string.sfs_service_start, notification);
        //startForeground(R.string.sfs_service_start, notification);
        //mNM.notify(0, notification);
    }


}

