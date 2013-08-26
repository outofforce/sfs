package com.asiainfo.app;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.asiainfo.R;
import com.asiainfo.model.User;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-6
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */

public class WatchItemAdapter extends BaseAdapter {



        private ArrayList<User> mWathUser = new ArrayList<User>();

        private LayoutInflater mInflater;
        private Context mCx;

        public WatchItemAdapter(LayoutInflater inflater, Context cx) {
            mInflater = inflater;
            mCx = cx;

        }

        public void clear() {
            mWathUser.clear();
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


        public long getItemId(int position) {
            return position;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            SViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.board_item, null);
                holder = new SViewHolder();

                holder.pubName = (TextView) convertView.findViewById(R.id.pubName);
                holder.pubHead = (ImageView) convertView.findViewById(R.id.pubHead);

                convertView.setTag(holder);
            } else {
                holder = (SViewHolder) convertView.getTag();
            }

            User t = mWathUser.get(position);
            holder.clear();

            holder.pubName.setText(t.nick_name);
            //holder.pubHead.setImageResource(R.drawable.google);


            if (!t.head_img.equals("") && t.head_img_load == User.IMG_NO_LOADED) {
                Intent intent = new Intent();
                intent.setClass(mCx, MtlService.class);
                intent.putExtra("AttachmentType","image");
                intent.putExtra("AttachmentPath",t.head_img);
                intent.putExtra("ListPos",position);
                intent.setAction("GetThumbPic");
                mCx.startService(intent);

            } else if (t.head_img_load == User.IMG_LOADED ) {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    options.inPurgeable = true;
                    options.inInputShareable = true;
                    Bitmap bitmapImage = BitmapFactory.decodeFile(t.head_img,options);
                    holder.pubHead.setImageBitmap(bitmapImage);
            }

            return convertView;



        }
    
        static class SViewHolder {
//            public TextView pubTime;
//            public ImageView pubImg;
//            public TextView pubContent;
            public TextView pubName;
            public ImageView pubHead;
            public void clear() {
//                pubTime.setText(null);
//                pubImg.setImageBitmap(null);
//                pubContent.setText(null);
                pubName.setText(null);
                pubHead.setImageBitmap(null);
            }
        }


    }


