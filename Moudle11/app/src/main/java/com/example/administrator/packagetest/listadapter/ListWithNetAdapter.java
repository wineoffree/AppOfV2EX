package com.example.administrator.packagetest.listadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.packagetest.R;
import com.example.netlibrary.DownImageTask;


import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/13.
 *  网络加载时最新内容的适配器
 */
public class ListWithNetAdapter extends BaseAdapter {
    Bitmap bitmap;
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Map<String,String>> list;
     int index;
    //定义当前listview是否在滑动状态
    private  boolean scrollState=false;
    public ListWithNetAdapter(Context context,int index) {
        this.index=index;
        this.context = context;
        layoutInflater = layoutInflater.from(context);
        scrollState=false;
    }
    public int  getIndex(){
        return this.index;
    }

    public void setData(List<Map<String,String>> data){
        this.list = data;
    }

    public void setScrollState(boolean scrollState) {
        this.scrollState = scrollState;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    public  List<Map<String,String>> getList() {
        return list;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        final ViewHolder viewHolder;

        if (convertView == null ) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.layout_item, null);
            viewHolder.content = (TextView)convertView.findViewById(R.id.content);
            viewHolder.img = (ImageView)convertView.findViewById(R.id.ima);
            viewHolder.time = (TextView)convertView.findViewById(R.id.time);
            viewHolder.name = (TextView)convertView.findViewById(R.id.name);
            viewHolder.type = (TextView)convertView.findViewById(R.id.type);
            viewHolder.showId = (TextView)convertView.findViewById(R.id.showid);
            convertView.setTag(viewHolder);
        }
        else {
           viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.content.setText(list.get(position).get("content").toString());
        viewHolder.time.setText(list.get(position).get("time").toString());
        viewHolder.name.setText(list.get(position).get("name").toString());
        viewHolder.type.setText(list.get(position).get("type").toString());
        viewHolder.showId.setText(list.get(position).get("showId").toString());
        viewHolder.newUrl=list.get(position).get("newUrl").toString();

        if (!scrollState){
            //加载图片
            Log.d("imamamama",viewHolder.img.toString());
            new DownImageTask(viewHolder.img).execute(list.get(position).get("ima"));
            //设置tag为1表示已加载过数据
            viewHolder.img.setTag("1");
        }else{
            viewHolder.img.setTag(list.get(position).get("ima"));
            //默认图片
            viewHolder.img.setImageResource(R.drawable.unload);
        }
        return convertView;
    }

}