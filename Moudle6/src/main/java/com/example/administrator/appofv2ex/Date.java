package com.example.administrator.appofv2ex;

import java.io.Serializable;

/**
 * 用来存放界面一到界面二内容的类
 * Created by Administrator on 2016/7/12.
 */
public class Date  implements Serializable{
    private String url;
    private String name;
    private String type;
    private String showId;
    private String time;
    private String content;
    //private Bitmap ima;
    public Date(String url,String name,String type,String showId,String time,String content){
        this.url=url;
        this.name=name;
        this.type=type;
        this.showId=showId;
        this.time=time;
        this.content=content;
        //this.ima=bitmap;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getShowId() {
        return showId;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

}

