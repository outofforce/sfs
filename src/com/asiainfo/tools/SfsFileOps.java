package com.asiainfo.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;


   


public class SfsFileOps {
    	private String fileFullName;
    	private int fileType;
    	private String fileThumbnailsPath;
    	private Context content;
		public String getFileFullName() {
			return fileFullName;
		}
		public void setFileFullName(String fileFullName) {
			this.fileFullName = fileFullName;
		}
		public int getFileType() {
			return fileType;
		}
		public void setFileType(int fileType) {
			this.fileType = fileType;
		}
		public String getFileThumbnailsPath() {
			return fileThumbnailsPath;
		}
		public void setFileThumbnailsPath(String fileThumbnailsPath) {
			this.fileThumbnailsPath = fileThumbnailsPath;
		}
		
		//生成图片的缩略图
		static public String genThumbnail(Context content,String ori_fullpath,int width,int height) {
			
			String path="";
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmapImage;
//            options.inPurgeable = true;
//            options.inInputShareable = true;	            
            options.inJustDecodeBounds = true; 
            	bitmapImage = BitmapFactory.decodeFile(ori_fullpath, options);
                //获取到这个图片的原始宽度和高度  
            	int picWidth  = options.outWidth;  
            	int picHeight = options.outHeight;  
            
            	if(picWidth>picHeight){
            		if(picWidth>width)
            			options.inSampleSize=(picWidth/width)*2;
            	}else{  
            		if(picHeight>height)  
            			options.inSampleSize = (picHeight/height)*2;  
            	}  
              options.inPurgeable = true;
              options.inInputShareable = true;	    
              options.inJustDecodeBounds=false;
          	  bitmapImage = BitmapFactory.decodeFile(ori_fullpath, options);
            
			try {
                String newPath = "/sdcard/sfs/thumbnail/";
                File dir = new File(newPath);  
                if (!dir.exists()) {  
                    dir.mkdirs();  
                }  
				FileOutputStream fos;		
				long dateTaken = System.currentTimeMillis(); 
				fos = new FileOutputStream(newPath + dateTaken+".jpeg"); 
				bitmapImage.compress(CompressFormat.JPEG, 50, fos);
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				path=newPath + dateTaken+".jpeg";
			} catch (FileNotFoundException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}             
			return path;
		}
		// 获取系统说略图
		static public SfsFileOps getFileOps(Context content,Uri uri,int type,int width,int height) {

			Uri fileUri=uri;
			Cursor cur = content.getContentResolver().query(fileUri, null, null, null, null);
			SfsFileOps model = null;
			try {
				if (cur.moveToNext()) {
		    		String id = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media._ID));  	
		    		String fileName = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));  
		    		String filePath = cur.getString(cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));    
		    		long fileSize = cur.getLong(cur.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)); 
					
		    		//判断文件存在才插入。因为有些文件被删除了。数据库要重新开机才会更新
		    		if (new File(filePath).exists()) {
						System.out.println(id);
						System.out.println(filePath);
						
						//_id图片id等于image_id 对应原图id　
						//获取缩略图路径
						String fileThumbnailPath=genThumbnail(content,filePath,width,height);
						model = new SfsFileOps();
						Log.v("IMG","缩略图"+fileThumbnailPath);
						model.setFileFullName(filePath+"/"+fileName);
						model.setFileThumbnailsPath(fileThumbnailPath);	
						Log.v("Fanglf", fileThumbnailPath);
						model.setFileType(type);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if (cur != null) {
					cur.close();
				}
			}
			return model;
		}


        public static String getRealPathFromURI(Uri contentUri,Context cx) {
            String[] proj = { MediaStore.Images.Media.DATA };
            CursorLoader loader = new CursorLoader(cx, contentUri, proj, null, null, null);
            Cursor cursor = loader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
    	
    }