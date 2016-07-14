package com.example.administrator.appofv2ex.search;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.appofv2ex.R;
import com.example.administrator.appofv2ex.downloadimg.DownImageTask;
import com.example.administrator.appofv2ex.listadapter.ListWithoutNetAdapter;
import com.example.administrator.appofv2ex.listadapter.SearchItemAdapter;
import com.example.administrator.appofv2ex.netspider.DrawDate;
import com.example.administrator.appofv2ex.save.CacheImage;
import com.example.administrator.appofv2ex.save.DateFromFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/14.
 */
public class SearchItemTask extends AsyncTask<String, Integer, List<Map<String,String>>> {
    ListView listView;//下拉框
    Context context;
   SearchItemAdapter adapter;
    List<String> results=new ArrayList<String>();
    List<String> urlList=new ArrayList<String>();
    public SearchItemTask(SearchItemAdapter adapter, ListView listView, Context context){
        this.adapter=adapter;
        this.listView=listView;
        this.context=context;
    }
    @Override
    protected void onPreExecute() {
        Log.d("wiwiwi","ewedasdasdas");
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Map<String, String>> result) {

        // TODO Auto-generated method stub
        super.onPostExecute(result);

        for(int i=0;i<result.size();i++){
            results.add(result.get(i).get("type")+result.get(i).get("content"));
        }
        adapter.setList(result);
        adapter.setData(results);
        listView.setAdapter(adapter);

        listView.setTextFilterEnabled(true);
    }

    @Override
    protected List<Map<String,String>> doInBackground(String... params) {

        List<Map<String,String>> list=null;
        DateFromFile dateFromFile=new DateFromFile();
        dateFromFile.setIndex(0);
        list=dateFromFile.getRequestList();
        Log.d("baba",list.toString());
        return list;
    }
}
