package com.asiainfo.proto;


import android.util.Base64;
import android.util.Log;
import com.asiainfo.model.MtlErrorCode;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class MtlServerGet {
    private String urlPre ;
    private String urlSufix;
    private String body;
    private HttpClient client;
    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
    public MtlServerGet()  {
        urlPre = "http://192.168.1.107:8080/";
        client=new DefaultHttpClient();
        client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
    }
    public void setBody(String abody) {
        body = abody;
    }

    public void setBody2(String name,String value) {
        BasicNameValuePair param = new BasicNameValuePair(name,value);
        paramList.add(param);
    }

    protected void setUrlSufix(String urlSufix) {
        this.urlSufix = urlSufix;
    }

    public class ServerResult {
        public int err_code = MtlErrorCode.Success;
        public String err_msg;
        public String result;
    }

    public void PraseResult(ServerResult result) {
        result.err_code= MtlErrorCode.Success;
    }

    public ServerResult handle() {

        ServerResult result = new ServerResult();
        try {
            HttpPost  post  = new HttpPost(urlPre+urlSufix);
            //StringEntity entity = new StringEntity(paramList.toString());

            post.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
            Log.e("MYDEBUG","sending : "+urlPre+urlSufix+"\n"+paramList.toString());
            HttpResponse response=client.execute(post);
            result.result = EntityUtils.toString(response.getEntity());
            Log.e("MYDEBUG","recieve :befor encode "+result.result);
            byte[] bytss = Base64.decode(result.result, Base64.NO_PADDING);
            result.result = new String(bytss,0,bytss.length,"UTF-8");
            Log.e("MYDEBUG","recieve : "+result.result);

            PraseResult(result);


        } catch (MalformedURLException e) {
            Log.e("MYDEBUG","==== : "+e.getMessage());
            result.err_msg =  e.getMessage();
            result.err_code = MtlErrorCode.E_Url;

            e.printStackTrace();

        } catch (IOException e) {
            Log.e("MYDEBUG","==== : "+e.getMessage());
            result.err_msg =  e.getMessage();
            result.err_code = MtlErrorCode.E_IO;
            e.printStackTrace();
        } catch(Exception e) {
            Log.e("MYDEBUG","==== : "+e.getMessage());
            result.err_msg =  e.getMessage();
            result.err_code = MtlErrorCode.E_UNDEFINE;
            e.printStackTrace();
        }





        return result;
    }
}
