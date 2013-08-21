package com.asiainfo.testapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.asiainfo.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.asiainfo.model.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-6
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */

public class pubItemAdapter extends BaseAdapter {
    
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

        public pubItemAdapter(LayoutInflater inflater,Context cx) {
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
            this.mPubItems.add(0,item);
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
                convertView = mInflater.inflate(R.layout.boarditem, null);
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
//            if (t.pubName.length() == 0) {
//            if (position%2==1)
//                holder.pubName.setText("  IBM中国有限公司");
//            else
//                holder.pubName.setText("  微软中国有限公司");
//
//            }
            holder.clear();



            holder.pubName.setText(""+position);
            if (position%2==1)
                holder.pubHead.setImageResource(R.drawable.google);
            else
                holder.pubHead.setImageResource(R.drawable.ibm);

            holder.pubContent.setText(t.pubContext);

            if (!t.pubImg.equals("") && t.pubImgLoad==false) {
                Intent intent = new Intent();
                intent.setClass(mCx, com.asiainfo.testapp.sfsService.class);
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

//            pubItem t = mPubItems.get(position);
//            holder.name.setText(t.getName());
//            holder.content.setText(t.getContent());
//            holder.chg_time.setText(t.getChgTime());
//            holder.session_id = t.getID();
//            if (t.getUnreadCount().equals("0")) {
//                holder.unread_count.setVisibility(View.INVISIBLE);
//                holder.unread_count.setText("");
//            } else {
//                holder.unread_count.setVisibility(View.VISIBLE);
//                holder.unread_count.setText(t.getUnreadCount());
//            }
//
//
//            if (t.getSessionType()==SessionType.P2P)  //个人
//            {
//                YlUser user = (YlUser)YlUser.getYlUserByNumber(cx, t.getNumber());
//                if (user != null) {
//                    if (user.valid==true && user.photoId>0)
//                        holder.icon.setImageBitmap(YlUser.getPhotoBitmap(cx,user.contractId));
//                    else
//                        holder.icon.setImageResource(R.drawable.contracthead);
//                }
//                //holder.rl.setBackgroundResource(R.drawable.yl_block_qun);
//                holder.name.setBackgroundResource(R.color.main_dark_gray);
//                holder.flag.setVisibility(View.GONE);
//
//            } else {
//                if (t.getImgPath().length()>0) {
//                    holder.icon.setImageBitmap(BitmapFactory.decodeFile(t.getImgPath()));
//                } else {
//                    holder.icon.setImageResource(R.drawable.black_session);
//                }
//                holder.name.setBackgroundResource(R.drawable.red);
//                holder.flag.setVisibility(View.VISIBLE);
//            }

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


