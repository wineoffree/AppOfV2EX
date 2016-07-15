package com.example.administrator.appofv2ex.netspider;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.administrator.appofv2ex.MainActivity;
import com.example.administrator.appofv2ex.listadapter.ListWithNetAdapter;
import com.example.administrator.appofv2ex.save.SaveTask;
import com.example.administrator.appofv2ex.save.SaveToFile;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/11.
 * 爬取第一层内容
 */
public class FirstTask extends AsyncTask<String, Integer, List<Map<String,String>>> {
    ProgressDialog progressDialog;
    ListWithNetAdapter adapter;
    ListView listView;//下拉框
    Context context;
    int i;
    int index;
    String content;
    public FirstTask(ProgressDialog progressDialog, ListWithNetAdapter adapter, ListView listView, Context context, int index){
        this.progressDialog=progressDialog;
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
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(List<Map<String, String>> result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        adapter.setData(result);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
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
