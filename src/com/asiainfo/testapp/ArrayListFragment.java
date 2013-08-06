package com.asiainfo.testapp;
import android.support.v4.app.Fragment;
import com.asiainfo.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public  class ArrayListFragment extends Fragment {
    int mNum;

    public static ArrayListFragment newInstance(int num) {
        ArrayListFragment f = new ArrayListFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }


    private ListView mpubItemListView;
    private pubItemAdapter mpubItemAdpater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    //这里是初始化页面内容的函数，我们的监听就是在这儿
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.board_list, container, false);

        mpubItemListView = (ListView) v.findViewById(R.id.pubboard);

        mpubItemAdpater = new pubItemAdapter(inflater);
        for (int i=0;i<10;i++) {
            pubItemAdapter.pubItem item = new pubItemAdapter.pubItem();

            mpubItemAdpater.add(item);
        }

        //loadSessionInfo();
        mpubItemListView.setAdapter(mpubItemAdpater);

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