package com.asiainfo.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.asiainfo.R;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-8
 * Time: 下午4:59
 * To change this template use File | Settings | File Templates.
 */
public class testapp extends Activity {
	TextView textView;
	String resultString;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testart);
		textView=(TextView)findViewById(R.id.testHttpResponse) ;
		Button button=(Button)findViewById(R.id.testHttp);

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//To change body of implemented methods use File | Settings | File Templates.
				new Thread(runnable).start();
				textView.setText(resultString);
			}
			});
	}
	Runnable runnable = new Runnable(){
		@Override
		public void run() {
			//
			// TODO: http request.
			//
			String result = "";
			String urlStr = "http://192.168.1.107:8080/welcome/test.do";
			System.out.println(urlStr);

			HttpGet get=new HttpGet(urlStr);
			HttpClient client=new DefaultHttpClient();
			try {

				HttpResponse response=client.execute(get);//执行Post方法
				resultString= EntityUtils.toString(response.getEntity());
				Log.d("resultString=====",resultString);
				//result = new String(DESHelper.doWork(result, "sopu01hz", 1));
				//System.out.println(result);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};
}
