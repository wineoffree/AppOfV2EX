package com.example.administrator.appofv2ex.netspider;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/11.
 */
public class DrawDate {
    String url="http://www.v2ex.com/";

    String[] slector={"?tab=tech","?tab=creative","?tab=play","?tab=apple"
            ,"?tab=jobs","?tab=deals","?tab=city","?tab=qna","?tab=hot","?tab=all","?tab=r2"};
    String secondUrl;
    Document doc;
    Elements dates;
    int index;


    public DrawDate(int index){
        this.index=index;
    }
    public DrawDate(String secondUrl){
        this.secondUrl=secondUrl;
    }
      public  void connectFirst(){
          try {
              doc = Jsoup.connect(url+slector[index]).get();

          }
          catch (Exception e){e.printStackTrace();}

      }
    public  void connectSecond(){
        try {
            doc = Jsoup.connect(secondUrl).get();

        }
        catch (Exception e){e.printStackTrace();}

    }

    //设置list
    public  void setList(List<Map<String ,String>> list){
        try {

            dates=doc.select("div[id~=^Wrapper$]").select("div[class~=^cell item$]");
            for (Element date : dates) {
                Map<String ,String> map = new HashMap<String, String>();
                String ima =date.select("img[src~=^//cdn.v2ex.co/avatar/.+]").attr("src");
                map.put("ima",ima);
                String newUrl =date.select("span[class~=^item_title$]").select("a").attr("abs:href");
                map.put("newUrl",newUrl);
                String content =date.select("span[class~=^item_title$]").select("a").text();
                map.put("content",content);
                String name =date.select("span[class~=^small fade$]").select("strong").first().select("a").text();
                map.put("name",name);
                String type =date.select("span[class~=^small fade$]").select("a").first().text();
                map.put("type",type);
                String time =date.select("span[class~=^small fade$]").select(":contains(分钟前)").text();
                Log.d("haha","type");
                map.put("time",time );
                String showId=date.select("td[align~=^right$]").select("a").text();
                map.put("showId",showId);
                list.add(map);
            }
        }
       catch (Exception e){e.printStackTrace();}
       // Log.d("haha",dates.toString());

    }
    //设置第二层topic_content
    public String getTopicContent(){
        String topic_content=null;
        try {
            dates=doc.select("div[id~=^Wrapper$]").select("div[class~=^topic_content$]");
            Elements as=doc.select("div[id~=^Wrapper$]").select("div[id~=^r_.+]");
            //Element bs=as.nextElementSibling();

            topic_content=dates.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return topic_content;
    }
    //设置第二层list
    public  void setSecondList(List<Map<String,String>> list){
        try {
            Elements as=doc.select("div[id~=^Wrapper$]").select("div[id~=^r_.+]");

            for (Element date : as) {
                Map<String ,String> map = new HashMap<String, String>();
                String ima =date.select("img[src~=^//cdn.v2ex.co/.+]").attr("src");
                map.put("ima",ima);
                String content =date.select("div[class~=^reply_content$]").text();
                map.put("content",content);
                //Log.d("hahaha", content);
                String name =date.select("strong").select("a[class~=^dark$]").first().text();
                map.put("name",name);
                String time =date.select("span[class~=^fade small$]").first().text();
                map.put("time",time);
                Log.d("haha123",time);
                list.add(map);
            }
        }
        catch (Exception e){e.printStackTrace();}
        // Log.d("haha",dates.toString());
        Log.d("haha123","end sssad");
    }
    //得到list
    public  List<Map<String,String>> getRequestList(){

        List<Map<String,String>> list = new ArrayList<Map<String ,String>>();

        setList(list);

        return list;

    }
    public  List<Map<String,String>> getSecondRequestList(){

        List<Map<String,String>> list = new ArrayList<Map<String ,String>>();

        setSecondList(list);

        return list;

    }



}
