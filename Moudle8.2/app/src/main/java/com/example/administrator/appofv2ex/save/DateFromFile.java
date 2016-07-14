package com.example.administrator.appofv2ex.save;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
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
 * Created by Administrator on 2016/7/13.
 */
public class DateFromFile {
    String filepath =Environment.getExternalStorageDirectory().getAbsolutePath()+"/myv2ex";
    List<Map<String, String>> list;
    int index;
    String imaUrl;
    Bitmap bitmap;
    public void setIndex(int index){
        this.index=index;
    }
    public void setImaUrl(String imaUrl){
        this.imaUrl=imaUrl;
    }
    public   void getList( List<Map<String,String>> list) {
        Log.d("bababa",String.valueOf(index));
        String resultStr = null;
        try {
            File filecontent = new File(filepath+"/save"+String.valueOf(index)+"content.txt");
            FileInputStream fileInputStream = new FileInputStream(filecontent);
            byte[] b = new byte[fileInputStream.available()];
            fileInputStream.read(b);
            resultStr = new String(b);
            if(fileInputStream != null){
                fileInputStream.close();
            }

            JSONArray jsonArray=new JSONArray(resultStr);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                Map<String ,String> map = new HashMap<String, String>();
                map.put("newUrl",jsonObject2.getString("newUrl"));
                map.put("content",jsonObject2.getString("content"));
                map.put("name",jsonObject2.getString("name"));
                map.put("type",jsonObject2.getString("type"));
                map.put("time",jsonObject2.getString("time") );
                map.put("showId",jsonObject2.getString("showId"));
                map.put("imaUrl",jsonObject2.getString("imaUrl"));
                list.add(map);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    // post方法访问服务器，返回集合数据
    public  List<Map<String,String>> getRequestList(){

        List<Map<String,String>> list = new ArrayList<Map<String ,String>>();

        getList(list);

        return list;

    }

    public Bitmap getBitmap(){
        bitmap=BitmapFactory.decodeFile(filepath+"/"+imaUrl+".png");
        return bitmap;
    }
}
