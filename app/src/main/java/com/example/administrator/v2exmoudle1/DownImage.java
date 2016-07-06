package com.example.administrator.v2exmoudle1;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/7/6.
 * 异步加载图片
 */
public class DownImage {

    public String image_path;


    public DownImage(String image_path) {
        this.image_path = image_path;
    }

    public void loadImage(final ImageCallBack callBack){

        final Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                Drawable drawable = (Drawable) msg.obj;
                callBack.getDrawable(drawable);
            }

        };

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Drawable drawable = Drawable.createFromStream(new URL("http:"+image_path).openStream(), "");
                      Log.d("imaurl",image_path);
                    Message message = Message.obtain();
                    message.obj = drawable;
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
        public void getDrawable(Drawable drawable);
    }

}