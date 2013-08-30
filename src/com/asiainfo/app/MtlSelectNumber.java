package com.asiainfo.app;

import java.util.ArrayList;
import java.util.List;

import com.asiainfo.model.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.asiainfo.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;



public class MtlSelectNumber extends Activity {
    private int pos;
    ArrayList<User> users;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
 
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.dialog_activity);
        users = getIntent().getParcelableArrayListExtra("ContractsData");
        pos=getIntent().getIntExtra("POS",0);
        List<String> strNumberList = new ArrayList<String>();

        for (int i=0;i<users.size();i++) {
            strNumberList.add(users.get(i).user_name);
        }

		ListView lv = (ListView) findViewById(R.id.numberListView);
		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strNumberList));
        lv.setOnItemClickListener(item_click_handle);
        

    }
    
    
    OnItemClickListener item_click_handle = new OnItemClickListener() {
    	
    	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
  	 		Intent intent=new Intent();
            User user = new User();
            user.user_name =  ((TextView )arg1).getText().toString();
            user.remote_id = users.get(0).remote_id;
  	 		intent.putExtra("POS",pos);
            intent.putExtra("SelectUser",user);
  	 		setResult(RESULT_OK, intent);
  	 		finish();		
    	}
	};
}
