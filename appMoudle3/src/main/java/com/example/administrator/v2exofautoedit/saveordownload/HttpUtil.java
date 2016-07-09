package com.example.administrator.v2exofautoedit.saveordownload;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/6.
 * 解析JSON
 */
public class HttpUtil {

    public static final String BASE_URL ="https://www.v2ex.com/api/topics/latest.json";
    //public static final String IMG_URL = "http://10.0.2.2:8080/jsontest/upload/";

    private String jsonData;
    ;
    // HttpURLConnection方法访问服务器，返回json字符串
    public static String readParse(String urlPath) throws Exception {

        StringBuilder response= new StringBuilder();;
        try {
            URL url = new URL(urlPath);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(6000);
            InputStream inStream = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

            String line;
            while ((line = reader.readLine()) != null) {

                response.append(line);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return response.toString();//通过out.Stream.toByteArray获取到写的数据
    }

    // 字符串转成集合数据
    public static void resultString2List(List<Map<String ,Object>> list, String url) {
        try {
            JSONArray jsonArray=new JSONArray(url);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                Map<String ,Object> map = new HashMap<String, Object>();
                Iterator<String> iterator = jsonObject2.keys();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    Object value = jsonObject2.get(key);
                    map.put(key, value);
                }

                list.add(map);

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // post方法访问服务器，返回集合数据
    public static List<Map<String,Object>> getRequest2List(String url){

        List<Map<String,Object>> list = new ArrayList<Map<String ,Object>>();

        resultString2List(list, url);

        return list;

    }



}