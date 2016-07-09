package com.example.administrator.v2exofautoedit.saveordownload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/6.
 * 异步加载图片
 */
public class DownImageforfile {

    public String image_path;
    Bitmap bitmap;
    Context context;
    int i;

    public DownImageforfile(String image_path, Context context,int i) {
        this.image_path = image_path;
        this.context=context;
        this.i=i;

    }


    public void loadImage(final ImageCallBack callBack){

        final Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                bitmap = ( Bitmap) msg.obj;

             //图片存入文件
                SaveToFile saveToFile=new SaveToFile(context);
                saveToFile.setBitmap(bitmap);
                Log.d("ttttt","hahahhahaha");
                saveToFile.setpath(i);
                saveToFile.saveBitmap();

                //图片缓存
                CacheImage.putBitmapToMem(String.valueOf(i),bitmap);

                callBack.getDrawable(bitmap);
            }

        };

        new Thread(new Runnable() {

            @Override
            public void run() {
                URL fileUrl = null;
                Bitmap bitmap = null;
                // TODO Auto-generated method stub
                try {
                    try {
                        fileUrl = new URL("http:"+image_path);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    HttpURLConnection conn = (HttpURLConnection) fileUrl
                            .openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    // Drawable drawable = Drawable.createFromStream(new URL("http:"+image_path).openStream(), "");
                    // Log.d("imaurl",image_path);
                    Message message = Message.obtain();
                    message.obj =  bitmap;
                    handler.sendMessage(message);
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public interface ImageCallBack{
        public void getDrawable(Bitmap bitmap);
    }
    public Bitmap getBitmap(){
        return this.bitmap;
    }
}