package com.example.administrator.v2exofautoedit.saveordownload;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.ListView;

import com.example.administrator.v2exofautoedit.MainActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/9.
 */
public class ShowTask extends AsyncTask<String, Integer, List<Map<String,Object>>> {

    ProgressDialog progressDialog;
    MainActivity.TheLastListViewAdapterNoNet adapter;
    ListView listView;//下拉框
    int i;

    public ShowTask(ProgressDialog progressDialog,MainActivity.TheLastListViewAdapterNoNet adapter,ListView listView){

        this.progressDialog=progressDialog;
        this.adapter=adapter;
        this.listView=listView;

    }
    @Override
    protected void onPostExecute(List<Map<String, Object>> result)  {
        super.onPostExecute(result);
        adapter.setData(result);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    protected List<Map<String, Object>> doInBackground(String... strings) {
        List<Map<String,Object>> list;

       FileToShow fileToShow=new FileToShow();
         list=fileToShow.getRequest2List();
        return list;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        progressDialog.show();
    }
}
