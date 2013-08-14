package com.asiainfo.testapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.asiainfo.R;

public  class ResumeFragment extends Fragment {
    int mNum;

    public static ResumeFragment newInstance(int num) {
        ResumeFragment f = new ResumeFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }


//    private ListView mpubItemListView;
//    private pubItemAdapter mpubItemAdpater;

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
        View v = inflater.inflate(R.layout.cover, container, false);


//        text.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                System.out.println("点击成功！");
//            }
//        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setListAdapter(new ArrayAdapter<String>(getActivity(),
         //       android.R.layout.simple_list_item_1, sCheeseStrings));
    }

//    @Override
//    public void onItemClick(ListView l, View v, int position, long id) {
//        Log.i("FragmentList", "Item clicked: " + id);
//    }
}