package com.example.administrator.v2exofautoedit.saveordownload;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.example.administrator.v2exofautoedit.MainActivity;
import com.example.administrator.v2exofautoedit.R;
import com.example.administrator.v2exofautoedit.obofsqlite.InitialTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/9.
 */
public class SearchfindTask extends AsyncTask<String, Integer, List<Map<String,Object>>> {

    ProgressDialog progressDialog;
    AutoCompleteTextView auto;
    int i;
    ArrayAdapter adapter;
    ArrayList<String> list=new ArrayList<String>();
    Context context;
    public SearchfindTask(ProgressDialog progressDialog,
                          ArrayAdapter adapter,
                          AutoCompleteTextView autoCompleteTextView,
                          Context context){

        this.progressDialog=progressDialog;
        this.adapter=adapter;
        this.auto=autoCompleteTextView;
         this.context=context;
    }
    @Override
    protected void onPostExecute(List<Map<String, Object>> result)  {

        super.onPostExecute(result);
        String a;
        InitialTable initialTable;
        for(int i=0;i<result.size();i++){
           a=result.get(i).get("id").toString().trim();
            list.add(a);
        }
        Log.d("ssitt",list.toString());
        adapter = new ArrayAdapter(context,
                R.layout.secondlist_item,list);

      auto.setAdapter(adapter);
        progressDialog.dismiss();
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
