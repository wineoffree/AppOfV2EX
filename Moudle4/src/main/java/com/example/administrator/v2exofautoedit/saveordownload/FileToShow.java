package com.example.administrator.v2exofautoedit.saveordownload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/9.
 */
public class FileToShow {
    List<Map<String, Object>> list;
    String filepath =   Environment.getExternalStorageDirectory().getAbsolutePath()+"/saves";

    int position;
    Bitmap bitmap;

    public void setPosition(int position){
        this.position=position;
    }
    public   void getList( List<Map<String,Object>> list) {

                 String resultStr = null;
                 try {
                     File filecontent = new File(filepath+"/content.txt");
                     FileInputStream fileInputStream = new FileInputStream(filecontent);
                     byte[] b = new byte[fileInputStream.available()];
                     fileInputStream.read(b);
                     resultStr = new String(b);

                     if(fileInputStream != null){
                         fileInputStream.close();
                     }
                     JSONArray jsonArray=new JSONArray(resultStr);
                     Log.d("pipi",String.valueOf(jsonArray.length()));
                     for (int i = 0; i < jsonArray.length(); i++) {
                         JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                         Log.d("pipi",jsonObject2.getString("id"));
                         Map<String ,Object> map = new HashMap<String, Object>();
                         Iterator<String> iterator = jsonObject2.keys();
                         while (iterator.hasNext()) {
                             String key = iterator.next();
                             Object value = jsonObject2.get(key);
                             map.put(key, value);
                             Log.d("pipi",value.toString());
                         }

                         list.add(map);

                     }
                 } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                 }




    }
    // post方法访问服务器，返回集合数据
    public  List<Map<String,Object>> getRequest2List(){

        List<Map<String,Object>> list = new ArrayList<Map<String ,Object>>();

       getList(list);

        return list;

    }

    public void getBitmap(final ImageCallBack callBack){
        final Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                bitmap = ( Bitmap) msg.obj;
                //存入缓存
                CacheImage.putBitmapToMem(String.valueOf(position),bitmap);
                callBack.getDrawable(bitmap);
            }

        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                 bitmap= BitmapFactory.decodeFile(filepath+"/ima"+ String.valueOf(position)+".png");
                Log.d("tititi",filepath+"/ima"+ String.valueOf(position)+".txt");
                Message message = Message.obtain();
                message.obj =  bitmap;
                handler.sendMessage(message);
            }
        }).start();

    }
    public interface ImageCallBack{
        public void getDrawable(Bitmap bitmap);
    }

}
