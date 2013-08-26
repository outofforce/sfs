package com.asiainfo.app;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.asiainfo.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-6
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */

public class PublishItemAdapter extends BaseAdapter {
    
        public static class pubItem {
            public String pubTime = "";
            public String pubImg = "";
            public String pubContext = "";
            public String pubName = "";
            public String pubHead = "";
            public Boolean pubImgLoad = false;
            public Bitmap drawable;

        }
    
        private ArrayList<pubItem> mPubItems = new ArrayList<pubItem>();

        private LayoutInflater mInflater;
        private Context mCx;

        public PublishItemAdapter(LayoutInflater inflater, Context cx) {
            mInflater = inflater;
            mCx = cx;

        }

        public void clear() {
            mPubItems.clear();
        }


        public int add_withoutNotify(pubItem item) {
            this.mPubItems.add(item);
            return mPubItems.size();
        }
        public int add(pubItem item) {
            this.mPubItems.add(item);
            super.notifyDataSetChanged();
            return mPubItems.size();
        }

        public int getCount() {

            return mPubItems.size();
        }


        public Object getItem(int position) {
            return mPubItems.get(position);
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
                holder.pubImg = (ImageView) convertView.findViewById(R.id.pubImg);
                holder.pubContent = (TextView) convertView.findViewById(R.id.pubContent);
                holder.pubTime = (TextView) convertView.findViewById(R.id.pubTime);

                convertView.setTag(holder);
            } else {
                holder = (SViewHolder) convertView.getTag();
            }

            pubItem t = mPubItems.get(position);
            holder.clear();

            holder.pubName.setText(t.pubName);
            holder.pubHead.setImageResource(R.drawable.google);

            holder.pubContent.setText(t.pubContext);
            holder.pubTime.setText(t.pubTime);


            if (!t.pubImg.equals("") && t.pubImgLoad==false) {
                Intent intent = new Intent();
                intent.setClass(mCx, MtlService.class);
                intent.putExtra("AttachmentType","image");
                intent.putExtra("AttachmentPath",t.pubImg);
                intent.putExtra("ListPos",position);
                intent.setAction("GetThumbPic");
                mCx.startService(intent);

            } else if (t.pubImgLoad == true ) {
                Log.e("MYDEBUG", "ImgPath=" + t.pubImg) ;

                    if (t.drawable == null) {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    //options.inJustDecodeBounds = true;
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    options.inPurgeable = true;
                    options.inInputShareable = true;
                    Bitmap bitmapImage = BitmapFactory.decodeFile(t.pubImg,options);
                    t.drawable = bitmapImage ;
                    holder.pubImg.setImageBitmap(bitmapImage);
                } else {
                    holder.pubImg.setImageBitmap(t.drawable);
                }

            }

            return convertView;

        }
    
        static class SViewHolder {
            public TextView pubTime;
            public ImageView pubImg;
            public TextView pubContent;
            public TextView pubName;
            public ImageView pubHead;
            public void clear() {
                pubTime.setText(null);
                pubImg.setImageBitmap(null);
                pubContent.setText(null);
                pubName.setText(null);
                pubHead.setImageBitmap(null);
            }
        }


    }


