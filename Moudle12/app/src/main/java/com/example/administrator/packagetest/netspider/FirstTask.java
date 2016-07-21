package com.example.administrator.packagetest.netspider;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.administrator.packagetest.listadapter.ListWithNetAdapter;
import com.example.administrator.packagetest.save.SaveTask;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/11.
 * 爬取第一层内容
 */
public class FirstTask extends AsyncTask<String, Integer, List<Map<String,String>>> {
    ProgressBar progressBar ;
    ListWithNetAdapter adapter;
    ListWithNetAdapter spareAdapter=null;//设置两个为了防止刷新时点击
    ListView listView;//下拉框
    Context context;
    int i;
    int index;
    String content;
    public FirstTask(ProgressBar progressBar, ListWithNetAdapter adapter,ListWithNetAdapter spareAdapter,ListView listView, Context context, int index){
        this.progressBar=progressBar;
        this.adapter=adapter;
        this.listView=listView;
        this.context=context;
        this.index=index;
        this.spareAdapter=spareAdapter;
    }
    public FirstTask(ProgressBar progressBar, ListWithNetAdapter adapter,ListView listView, Context context, int index){
        this.progressBar=progressBar;
        this.adapter=adapter;
        this.listView=listView;
        this.context=context;
        this.index=index;
    }
    @Override
    protected void onPreExecute() {
        Log.d("wiwiwi","ewedasdasdas");
        // TODO Auto-generated method stub
        super.onPreExecute();
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected void onPostExecute(List<Map<String, String>> result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        adapter.setData(result);
        adapter.setIfFinishDownload(true);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if(spareAdapter!=null){
            spareAdapter.setData(result);
            spareAdapter.setIfFinishDownload(true);
            spareAdapter.notifyDataSetChanged();
        }
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        SaveTask saveTask=new SaveTask(index,content);
        saveTask.execute();
    }

    @Override
    protected List<Map<String,String>> doInBackground(String... params) {
        List<Map<String,String>> list=null;
        DrawDate drawDate=new DrawDate(index);
        drawDate.connectFirst();
        list=drawDate.getRequestList();
        content=drawDate.content;
        return list;
    }
}
