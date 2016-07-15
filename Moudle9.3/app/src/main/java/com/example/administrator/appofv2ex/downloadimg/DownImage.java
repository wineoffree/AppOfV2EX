package com.example.administrator.appofv2ex.downloadimg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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


    public Bitmap loadImage(){
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

                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        return bitmap;
            }
}