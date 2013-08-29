package com.asiainfo.app;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.asiainfo.R;
import com.asiainfo.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-6
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */

public class SelectSenderAdapter extends BaseAdapter {



        private ArrayList<User> mWathUser = new ArrayList<User>();
        private Map<Integer,Integer> mSelected = new HashMap<Integer,Integer>();
        private LayoutInflater mInflater;
        private Context mCx;
        private User mUser;

        public SelectSenderAdapter(LayoutInflater inflater, Context cx, User user) {
            mInflater = inflater;
            mCx = cx;

            mUser = user;
        }

        public void clear() {
            mWathUser.clear();
        }
        public ArrayList<User> getSelectItems() {
            ArrayList<User> list = new ArrayList<User>();
            Iterator it = mSelected.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Integer pos = (Integer)entry.getValue();
                list.add(mWathUser.get(pos)) ;
            }
            return   list;
        }

        public int add_withoutNotify(User item) {
            this.mWathUser.add(item);
            return mWathUser.size();
        }
        public int add(User item) {
            this.mWathUser.add(item);
            super.notifyDataSetChanged();
            return mWathUser.size();
        }

        public int getCount() {

            return mWathUser.size();
        }


        public Object getItem(int position) {
            return mWathUser.get(position);
        }
        public int removeByPosition(int position) {
            mWathUser.remove(position);
            super.notifyDataSetChanged();
            return mWathUser.size();
        }

        public long getItemId(int position) {
            return position;
        }


        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            SViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.select_user_item, null);
                holder = new SViewHolder();

                holder.userName = (TextView) convertView.findViewById(R.id.pubName);
                holder.userHead = (ImageView) convertView.findViewById(R.id.pubHead);
                holder.Cb_selected = (CheckBox) convertView.findViewById(R.id.Cb_userSelected);
                convertView.setTag(holder);
            } else {
                holder = (SViewHolder) convertView.getTag();
            }

            final User t = mWathUser.get(position);
            holder.clear();

            holder.userName.setText(t.nick_name);

            if (mSelected.get(position) != null) {
                holder.Cb_selected.setSelected(true);
            }

            holder.Cb_selected.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("MYDEBUG", "click " + position);
                    if (mSelected.get(position) == null) {
                        mSelected.put(position,position);
                    } else {
                        mSelected.remove(position);
                    }
                }
            });

            if (!t.head_img.equals("") && t.head_img_load == User.IMG_NO_LOADED) {
                Intent intent = new Intent();
                intent.setClass(mCx, MtlService.class);
                intent.putExtra("AttachmentType","image");
                intent.putExtra("AttachmentPath",t.head_img);
                intent.putExtra("ListPos",position);
                intent.setAction("GetWatcherPic");
                mCx.startService(intent);

            } else if (t.head_img_load == User.IMG_LOADED ) {
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                options.inPurgeable = true;
                options.inInputShareable = true;
                Bitmap bitmapImage = BitmapFactory.decodeFile(t.head_img_remote_addr,options);
                holder.userHead.setImageBitmap(bitmapImage);
            }



            return convertView;



        }
    
        static class SViewHolder {
//            public TextView pubTime;
//            public ImageView pubImg;
//            public TextView pubContent;
            public TextView userName;
            public ImageView userHead;
            public CheckBox Cb_selected;
            public void clear() {
//                pubTime.setText(null);
//                pubImg.setImageBitmap(null);
//                pubContent.setText(null);
                userName.setText(null);
                userHead.setImageBitmap(null);
                //Cb_selected.setText(null);
                Cb_selected.setSelected(false);

            }
        }


    }


