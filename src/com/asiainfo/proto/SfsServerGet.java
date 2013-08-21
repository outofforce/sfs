package com.asiainfo.proto;


import android.util.Base64;
import android.util.Log;
import com.asiainfo.model.SfsErrorCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.MalformedURLException;



public class SfsServerGet {
    private String urlPre ;
    private String urlSufix;
    private HttpClient client;

    public SfsServerGet()  {
        urlPre = "http://192.168.1.107:8080/";
        client=new DefaultHttpClient();
        client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
    }

    protected void setUrlSufix(String urlSufix) {
        this.urlSufix = urlSufix;
    }

    public class ServerResult {
        public int err_code = SfsErrorCode.Success;
        public String err_msg;
        public String result;
    }

    public void PraseResult(ServerResult result) {
        result.err_code=SfsErrorCode.Success;
    }

    public ServerResult handle() {
        ServerResult result = new ServerResult();

        try {
            HttpGet httpMethod = new HttpGet(urlPre+urlSufix);
            Log.e("MYDEBUG","sending : "+urlPre+urlSufix);
            HttpResponse response=client.execute(httpMethod);
            //response.getStatusLine().getStatusCode() =
            result.result = EntityUtils.toString(response.getEntity());


            byte[] bytss = Base64.decode(result.result, Base64.NO_PADDING);
            result.result = new String(bytss,0,bytss.length,"UTF-8");
            Log.e("MYDEBUG","recieve : "+result.result);
            //Log.e("MYDEBUG","recieve : "+result.result);

            PraseResult(result);

        } catch (MalformedURLException e) {
            result.err_msg =  e.getMessage();
            result.err_code = SfsErrorCode.E_Url;

            e.printStackTrace();

        } catch (IOException e) {
            result.err_msg =  e.getMessage();
            result.err_code = SfsErrorCode.E_IO;
            e.printStackTrace();
        }




        return result;
    }
}
