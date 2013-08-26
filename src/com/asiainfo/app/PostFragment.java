package com.asiainfo.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.asiainfo.R;
import com.asiainfo.model.PublishData;
import com.asiainfo.model.SfsErrorCode;
import com.asiainfo.model.SfsResult;
import com.asiainfo.model.User;
import com.asiainfo.tools.DisplayUtil;
import com.asiainfo.tools.SfsFileOps;

public  class PostFragment extends Fragment implements onSfsDataReceiver {
    int mNum;

    public static PostFragment newInstance(int num) {
        PostFragment f = new PostFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    Button Bt_send;
    EditText Ed_postContext;
    ImageView Iv_postImg;
    public static final int  id_select_img = 100;
    String send_img_path ="";
    String view_img_path ="";
    boolean hasImg = false;
    sfsFrame father;

//    private ListView mpubItemListView;
//    private pubItemAdapter mpubItemAdpater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }

    boolean boolean_ChgImg = false;
    //这里是初始化页面内容的函数，我们的监听就是在这儿
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("MYDEBUG", "CreateView " + mNum);
        View v = inflater.inflate(R.layout.post, container, false);

        Bt_send = (Button)v.findViewById(R.id.bt_send);
        Ed_postContext = (EditText)v.findViewById(R.id.ed_postContext);
        Iv_postImg = (ImageView)v.findViewById(R.id.iv_postImg);

        father = (sfsFrame)getActivity();
        IntentFilter filter = new IntentFilter();
        filter.addAction("PostPublish_RES");
        father.registerSfsDataReciever(PostFragment.class.getName(),this,filter);
        Bt_send.setOnClickListener(cl_post);
        Iv_postImg.setOnClickListener(cl_selectImg);


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
    public void onDestroyView() {
        father.unregisterSfsDataReciever(PublishListFragment.class.getName());
        super.onDestroyView();    //To change body of overridden methods use File | Settings | File Templates.
    }


    View.OnClickListener cl_selectImg = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, id_select_img);
        }
    } ;

    View.OnClickListener cl_post = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (Ed_postContext.getText().length()>0) {
                //Bt_send.setEnabled(false);
                Intent intent = new Intent();
                intent.setClass(getActivity(), sfsService.class);
                intent.setAction("PostPublish");
                User user = father.getUser();
                intent.putExtra("User",user);
                PublishData p = new PublishData();
                if (hasImg) {
                     p.thumb_img = send_img_path;
                     p.context_img = view_img_path;
                }
                p.pub_context =Ed_postContext.getText().toString();
                intent.putExtra("PostPushishData",p);
                intent.putExtra("HasImg",hasImg);
                intent.setClass(father, sfsService.class);
                father.startService(intent);


            } else {
                Toast.makeText(getActivity(),"no data need be send!",Toast.LENGTH_SHORT).show();
            }
            //To change body of implemented methods use File | Settings | File Templates.
        }
    } ;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setListAdapter(new ArrayAdapter<String>(getActivity(),
         //       android.R.layout.simple_list_item_1, sCheeseStrings));
    }

    @Override
    public void onDataCome(Intent intent) {
        SfsResult res = intent.getParcelableExtra("UI_RESULT");
        if (res.err_code == SfsErrorCode.Success) {
            if (intent.getAction().equals("PostPublish_RES")) {
                Toast.makeText(getActivity(),"send sucess !",Toast.LENGTH_SHORT).show();
                Ed_postContext.setText("");
                //Bt_send.setEnabled(true);
            }
        }

        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if  (resultCode == 0 ) return;
        if (requestCode == id_select_img) {
            String path = SfsFileOps.getRealPathFromURI(data.getData(),getActivity());
            send_img_path = SfsFileOps.genThumbnail(getActivity(),path,
                    DisplayUtil.dip2px(getActivity(),150),
                    DisplayUtil.dip2px(getActivity(),150));
            view_img_path = SfsFileOps.genThumbnail(getActivity(),path,
                    DisplayUtil.dip2px(getActivity(),720),
                    DisplayUtil.dip2px(getActivity(),720));
            Bitmap bitmap = BitmapFactory.decodeFile(send_img_path);
            Iv_postImg.setImageBitmap(bitmap);
            hasImg = true;
        }
    }

    //    @Override
//    public void onItemClick(ListView l, View v, int position, long id) {
//        Log.i("FragmentList", "Item clicked: " + id);
//    }
}