package com.asiainfo.tools;

/**
 * Created with IntelliJ IDEA.
 * User: outofforce
 * Date: 13-8-13
 * Time: 下午10:31
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

public class NetTools {
    static public String download(String addr,String file) {
        try {
            URL getUrl=new URL("http://192.168.1.104/getfile-"+file);
            URLConnection conn=getUrl.openConnection();
            conn.connect();
            conn.setReadTimeout(5000);
            InputStream is=conn.getInputStream();
            if (is == null)
            {
                throw new RuntimeException("stream is null");
            }
            file=file.replace("image/jpeg", "jpeg").replace("image/png", "png");
			/*建立文件地址*/

            String path = "/sdcard/sfs/download/";
            File dirfile = new File(path);
            if (!dirfile.exists()) {
                dirfile.mkdirs();
            }

            File tempFile = new File(path+file);
            Log.v("Fanglf","abs == "+tempFile.getAbsolutePath());

			/*取得站存盘案路径*/
            String currentTempFilePath = tempFile.getAbsolutePath();

			/*将文件写入临时盘*/
            FileOutputStream fos = new FileOutputStream(tempFile);
            byte buf[] = new byte[128];
            do
            {
                int numread = is.read(buf);
                if (numread <= 0)
                {
                    break;
                }
                fos.write(buf, 0, numread);
            }while (true);

            return currentTempFilePath;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    static public String upload(String addr_faile,String file) {
        try
        {
            URL url=new URL("http://192.168.1.104/postfile");
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            FileInputStream fStream = new FileInputStream(file);
            Integer lon=fStream.available();
			/* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
			/* 设定传送的method=POST */
            con.setRequestMethod("POST");
			/* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            if(!file.contains(".ylv"))
                con.setRequestProperty("Content-Type",
                        "image/jpeg");
            else
                con.setRequestProperty("Content-Type",
                        "audio/ylv");
            con.setRequestProperty("Content-Length", lon.toString());
            con.setReadTimeout(10*1000);  // 设置超时
			/* 设定DataOutputStream */
            DataOutputStream ds =
                    new DataOutputStream(con.getOutputStream());

			/* 取得文件的FileInputStream */

			/* 设定每次写入1024bytes */
            byte[] buffer = new byte[lon.intValue()];

            int length = -1;
			/* 从文件读取数据到缓冲区 */
            while((length = fStream.read(buffer)) != -1)
            {
				/* 将数据写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }

			/* close streams */
            fStream.close();
            ds.flush();

			/* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b =new StringBuffer();
            while( ( ch = is.read() ) != -1 )
            {
                b.append( (char)ch );
            }
            try {
                ds.close();
            } catch(Exception e) {
                //如果关闭失败那么，认为数据已经上传成功
                e.printStackTrace();
            }
			/* 将Response显示于Dialog */
            file=b.toString().trim().substring(2, b.toString().trim().length());
            return file;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;

        }
    }


    static public String uploadFile(String addr,String file) {
        try
        {
            URL url =new URL(addr);
            HttpURLConnection con=(HttpURLConnection)url.openConnection();
            FileInputStream fStream = new FileInputStream(file);
            Integer lon=fStream.available();
			/* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
			/* 设定传送的method=POST */
            con.setRequestMethod("POST");
			/* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type","text/html");
            con.setRequestProperty("Content-Length", lon.toString());
            con.setReadTimeout(10*1000);  // 设置超时
			/* 设定DataOutputStream */
            DataOutputStream ds =
                    new DataOutputStream(con.getOutputStream());

			/* 取得文件的FileInputStream */

			/* 设定每次写入1024bytes */
            byte[] buffer = new byte[lon.intValue()];

            int length = -1;
			/* 从文件读取数据到缓冲区 */
            while((length = fStream.read(buffer)) != -1)
            {
				/* 将数据写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }

			/* close streams */
            fStream.close();
            ds.flush();

			/* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b =new StringBuffer();
            while( ( ch = is.read() ) != -1 )
            {
                b.append( (char)ch );
            }
            try {
                ds.close();
            } catch(Exception e) {
                //如果关闭失败那么，认为数据已经上传成功
                e.printStackTrace();
            }
			/* 将Response显示于Dialog */
            file=b.toString().trim().substring(2, b.toString().trim().length());
            return file;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;

        }
    }
}
