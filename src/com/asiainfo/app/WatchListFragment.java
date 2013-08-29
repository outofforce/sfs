package com.asiainfo.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.asiainfo.R;
import com.asiainfo.model.MtlErrorCode;
import com.asiainfo.model.MtlResult;
import com.asiainfo.model.PublishData;
import com.asiainfo.model.User;
import com.asiainfo.tools.TimeTrans;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public  class WatchListFragment extends Fragment implements IOnSfsDataReceiver {
    int mNum;
    public static final String TAG="WatchListFragment";
    public static WatchListFragment newInstance(int num) {
        WatchListFragment f = new WatchListFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }


    private MtlListView mWatchItemListView;
    private WatchItemAdapter mWatchItemAdpater;
    private boolean mIsLoading = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;


    }



    //这里是初始化页面内容的函数，我们的监听就是在这儿
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("MYDEBUG", "CreateView " + mNum);
        View v = inflater.inflate(R.layout.board_list, container, false);
        mWatchItemListView = (MtlListView) v.findViewById(R.id.pubboard);
        mWatchItemAdpater = new WatchItemAdapter(inflater,getActivity(),((MtlFragmentActivity) getActivity()).getUser());
        IntentFilter filter = new IntentFilter();
        filter.addAction("QueryMyWatchData_RES");
        filter.addAction("GetLocalMyWatchData_RES");
        filter.addAction("ChangeWatcherStatus_RES");
        filter.addAction("GetWatcherPic_RES");
        ((MtlFragmentActivity)getActivity()).registerSfsDataReciever(WatchListFragment.class.getName(), this, filter);
        mWatchItemListView.setAdapter(mWatchItemAdpater);
        mWatchItemListView.setonRefreshListener(new MtlListView.OnRefreshListener() {
            public void onRefresh() {
                if (!mIsLoading )
                    loadHistory();

                Timer timer=new Timer();
                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        mIsLoading = false;
                    }

                }, 1000);
            }
        });





        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("MYDEBUG","WatchList onResume");
        Intent intent = new Intent();
        intent.setClass(getActivity(), MtlService.class);
        intent.setAction("GetLocalMyWatchData");

        //intent.putExtra("User", ((MtlFragmentActivity) getActivity()).getUser());
        getActivity().startService(intent);

    }

    @Override
    public void onDestroyView() {
        ((MtlFragmentActivity)getActivity()).unregisterSfsDataReciever(WatchListFragment.class.getName());
        super.onDestroyView();    //To change body of overridden methods use File | Settings | File Templates.
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDataCome(Intent intent) {
        MtlResult res = intent.getParcelableExtra("UI_RESULT");
        if (res.err_code == MtlErrorCode.Success) {
            if (intent.getAction().equals("GetWatcherPic_RES")) {
                int pos = intent.getIntExtra("ListPos",-1);
                if (pos == -1) return ;
                String path = intent.getStringExtra("AttachmentPath");
                if (path != null) {
                    ((User) mWatchItemAdpater.getItem(pos)).head_img_load = User.IMG_LOADED ;
                    ((User) mWatchItemAdpater.getItem(pos)).head_img_remote_addr = path ;
                    mWatchItemAdpater.notifyDataSetChanged();
                }
            }  else if (intent.getAction().equals("ChangeWatcherStatus_RES")) {
                int pos = intent.getIntExtra("ListPos",-1);
                if (pos == -1) return ;
                mWatchItemAdpater.removeByPosition(pos);

            }  else if (intent.getAction().equals("GetLocalMyWatchData_RES") ||
                    intent.getAction().equals("QueryMyWatchData_RES")) {
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

                    }
                    mIsLoading = false;
                }
            }
        }


    }

    private void loadHistory(){

        Intent intent = new Intent();
        intent.setClass(getActivity(), MtlService.class);
        intent.setAction("QueryMyWatchData");

        intent.putExtra("User", ((MtlFragmentActivity) getActivity()).getUser());
        getActivity().startService(intent);
        mIsLoading = true;

    }


//    @Override
//    public void onItemClick(ListView l, View v, int position, long id) {
//        Log.i("FragmentList", "Item clicked: " + id);
//    }




}