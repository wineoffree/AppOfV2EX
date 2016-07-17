package com.example.administrator.packagetest.search;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.administrator.packagetest.listadapter.SearchItemAdapter;
import com.example.administrator.packagetest.save.DateFromFile;

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
