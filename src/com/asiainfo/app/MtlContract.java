package com.asiainfo.app;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.PendingIntent;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import com.asiainfo.model.User;
import com.asiainfo.tools.PinYin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.asiainfo.R;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ResourceCursorAdapter;

import android.widget.TextView;

public class MtlContract extends Activity {

    static final String[] CONTACTS_SUMMARY_PROJECTION = new String[] {
            Contacts._ID, // 0
            Contacts.DISPLAY_NAME, // 1
            Contacts.STARRED, // 2
            Contacts.TIMES_CONTACTED, // 3
            Contacts.CONTACT_PRESENCE, // 4
            Contacts.PHOTO_ID, // 5
            Contacts.LOOKUP_KEY, // 6
            Contacts.HAS_PHONE_NUMBER // 7
    };
    static final private int GET_CODE = 0;
    static final int SUMMARY_ID_COLUMN_INDEX = 0;
    static final int SUMMARY_NAME_COLUMN_INDEX = 1;
    static final int SUMMARY_PHOTO_ID_COLUMN_INDEX = 5;



    public ListView ConstractListView ;
    ContactListItemAdapter adapter;
    public EditText SearchEdit;
   	public Cursor myCursor = null;

    SmsManager smsManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.mtl_contacts);
        if (this.getIntent() != null) {

        }
        

        
        String select = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                + Contacts.DISPLAY_NAME + " != '' ))";
        myCursor =
                getContentResolver().query(Contacts.CONTENT_URI, CONTACTS_SUMMARY_PROJECTION, select,
                null, "SORT_KEY");


        adapter = new ContactListItemAdapter(this, R.layout.mtl_contract_item, myCursor);
        ConstractListView = (ListView) findViewById(R.id.Lv_Contracts);
        ConstractListView.setAdapter(adapter);
        
        ConstractListView.setOnItemClickListener(contract_item_handle);

        SearchEdit = (EditText) findViewById(R.id.ed_searchContext);
        SearchEdit.addTextChangedListener(watcher);
        
        SearchEdit.setVisibility(View.VISIBLE);

        smsManager = SmsManager.getDefault();
        SearchEdit.setText("");
        SearchEdit.clearFocus();
    }



    TextWatcher watcher=new TextWatcher() {

		public void afterTextChanged(Editable s) {

		}

		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

			Cursor c = adapter.runQueryOnBackgroundThread(s);
			
			if (c != null) {
				adapter.changeCursor(c);
			}
		}
    	
    };
    



    OnItemClickListener contract_item_handle = new OnItemClickListener() {
    	
    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
    		ContactListItemCache cc = (ContactListItemCache) ((RelativeLayout) arg1).getTag();
    		cc.inviteBt.performClick();
    	}
	};

	
	 @Override
	protected void onActivityResult(int requestCode, int resultCode,Intent data) {
	        if (requestCode == GET_CODE) {
	            if (resultCode == RESULT_CANCELED) {	                
	            } else {
                    User user = data.getParcelableExtra("SelectUser") ;
                    int pos = data.getIntExtra("POS",0);
                    Log.e("MYDEBUG","Sending Sms");
                    smsManager.sendTextMessage(user.user_name,null,"我已加入蝴蝶，邀请你来尝试下！http://10.1.251.155:9090/butterfly/download.do",null,null);
                    ConstractListView.requestFocusFromTouch();
                    ConstractListView.setSelection(pos);
	            }
	        }
	}
	
    private final class ContactListItemAdapter extends ResourceCursorAdapter {
    	private Context mContext;
    	
        public ContactListItemAdapter(Context context, int layout, Cursor c) {
            super(context,layout,c,false);
            this.mContext=context;
        }
        
        
        public String convertToString(Cursor cursor) {
            return cursor.getString(SUMMARY_NAME_COLUMN_INDEX);
        }       
        
        public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
            if (getFilterQueryProvider() != null) {
                return getFilterQueryProvider().runQuery(constraint);
            }
            
            String[] args = null;
            String select ="";
            if (constraint != null) {
                select = "((" + Contacts.DISPLAY_NAME + " like ? ) AND ("
                        + Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                        + Contacts.DISPLAY_NAME + " != '' ))";
                args = new String[] { "%"+constraint.toString()+"%"  };               
            }
            Cursor c = mContext.getContentResolver().query(Contacts.CONTENT_URI, CONTACTS_SUMMARY_PROJECTION, (select.length() == 0 ? null:select),args, "SORT_KEY");
            return c;
        }          

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            final ContactListItemCache cache = (ContactListItemCache) view.getTag();
            // Set the name
            cursor.copyStringToBuffer(SUMMARY_NAME_COLUMN_INDEX, cache.nameBuffer);
            int size = cache.nameBuffer.sizeCopied;
            cache.nameView.setText(cache.nameBuffer.data, 0, size);
            final long contactId = cursor.getLong(SUMMARY_ID_COLUMN_INDEX);
            final long photo_id = cursor.getLong(SUMMARY_PHOTO_ID_COLUMN_INDEX);
            

            if (photo_id>0) {
	            Uri uri =ContentUris.withAppendedId(Contacts.CONTENT_URI,contactId);
	            InputStream input = Contacts.openContactPhotoInputStream(getContentResolver(), uri);
	            Bitmap contactPhoto = BitmapFactory.decodeStream(input);     
	            cache.photoView.setImageBitmap(contactPhoto);
            } else {
            	cache.photoView.setImageResource(R.drawable.contracthead);
            }
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = super.newView(context, cursor, parent);
            ContactListItemCache cache = new ContactListItemCache();
            cache.nameView = (TextView) view.findViewById(R.id.Tv_Name);
            cache.photoView = (ImageView) view.findViewById(R.id.Iv_Badge);
            cache.inviteBt = (Button) view.findViewById(R.id.Bt_Invite);
            view.setTag(cache);
            return view;
        }    
        
       
        
        @Override
   	    public View getView(final int position, View convertView, final ViewGroup parent) {  
   	        View view = super.getView(position, convertView, parent);  
   	        Button inviteBt = (Button)view.findViewById(R.id.Bt_Invite);
            inviteBt.setOnClickListener(new OnClickListener(){
   	            public void onClick(View v) {
                    Cursor cursor = getCursor();
                    cursor.moveToPosition(position);
                    int rowId = cursor.getInt(SUMMARY_ID_COLUMN_INDEX);

                    ArrayList<User> users = getContacts(rowId) ;

                    Intent intent=new Intent();
                    intent.setClass(MtlContract.this, MtlSelectNumber.class);
                    intent.putExtra("ContractsData",users);
                    intent.putExtra("POS", position);
                    startActivityForResult(intent, GET_CODE);

   	            }  
   	        });
   	        return view;  
   	    }  

               

    };

    final static class ContactListItemCache {
    	public Button inviteBt;
        public TextView nameView;
        public ImageView photoView;
        public CharArrayBuffer nameBuffer = new CharArrayBuffer(128);
    }
    
    
    
    @Override
	protected void onDestroy() {
    	if (myCursor != null)
    		myCursor.close();
		super.onDestroy();
	}

	protected synchronized void onResume() {
    	
    	super.onResume();
   

        

        
    }


    private ArrayList<User> getContacts(int contractID) {


        Cursor numberCursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ Integer.toString(contractID),
                null,
                null);
        int count = 0;
        ArrayList<User> list = new ArrayList<User>();
        while (numberCursor.moveToNext()) {
            String strPhoneNumber = numberCursor.getString(numberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            User u = new User();
            u.remote_id =  contractID;
            u.user_name =  strPhoneNumber;
            u.status = User.BEIVITED;
            list.add(u);
        }
        numberCursor.close();




        return list;
    }

}
    
    
    
    
