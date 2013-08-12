package com.asiainfo.proto;


import android.util.Log;
import com.asiainfo.model.SfsErrorCode;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import com.asiainfo.model.SfsErrorCode;

import java.io.IOException;
import java.net.MalformedURLException;



public class SfsHttpGet {
    private String urlPre ;
    private String urlSufix;
    private HttpClient client;

    public SfsHttpGet()  {
        urlPre = "http://192.168.1.107:8080/";
        client=new DefaultHttpClient();
        client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
    }

    protected void setUrlSufix(String urlSufix) {
        this.urlSufix = urlSufix;
    }

    public class HttpResult {
        public int err_code = SfsErrorCode.Success;
        public String err_msg;
        public String result;
    }

    public void PraseResult(HttpResult result) {
        result.err_code=SfsErrorCode.Success;
    }

    public HttpResult handle() {
        Log.e("MYDEBUG", "ok here!");
        HttpResult result = new HttpResult();

        try {
            HttpGet httpMethod = new HttpGet(urlPre+urlSufix);
            Log.e("MYDEBUG","sending : "+urlPre+urlSufix);
            HttpResponse response=client.execute(httpMethod);
            //response.getStatusLine().getStatusCode() =
            result.result = EntityUtils.toString(response.getEntity());
            Log.e("MYDEBUG","recieve : "+result.result);
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
