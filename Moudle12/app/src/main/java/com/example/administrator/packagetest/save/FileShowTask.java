package com.example.administrator.packagetest.save;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.administrator.packagetest.listadapter.ListWithoutNetAdapter;
import com.example.netlibrary.CacheImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/13.
 */
public class FileShowTask extends AsyncTask<String, Integer, List<Map<String,String>>> {
    ProgressBar progressBar;
    ListWithoutNetAdapter adapter;
    ListView listView;//下拉框
    Context context;
    int i;
    int index;
    ArrayList<Bitmap> bitmaps;
    public FileShowTask( ProgressBar progressBar, ListWithoutNetAdapter adapter, ListView listView, Context context, int index){
        this.progressBar=progressBar;
        this.adapter=adapter;
        this.listView=listView;
        this.context=context;
        this.index=index;
        this.bitmaps=bitmaps;
    }
    @Override
    protected void onPreExecute() {
        Log.d("wiwiwi","ewedasdasdas");
        // TODO Auto-generated method stub
        super.onPreExecute();
        bitmaps=new ArrayList<Bitmap>();
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    protected void onPostExecute(List<Map<String, String>> result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        adapter.setData(result);
        adapter.setBitmaps(bitmaps);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    protected List<Map<String,String>> doInBackground(String... params) {

        List<Map<String,String>> list=null;
        DateFromFile dateFromFile=new DateFromFile();
        dateFromFile.setIndex(index);
        list=dateFromFile.getRequestList();
        Log.d("baba",list.toString());
        for(int i=0;i<list.size();i++){
            try {
                //从缓存提取
                Bitmap bitmap = CacheImage.getBitmap(list.get(i).get("imaUrl").replaceAll("/","").replace(".png?m=","").replace("_",""));
                if(bitmap!=null)
                    bitmaps.add(bitmap);
                else {
                    //从文件取
                    dateFromFile.setImaUrl(list.get(i).get("imaUrl").replaceAll("/","").replace(".png?m=","").replace("_",""));
                    bitmaps.add(dateFromFile.getBitmap());
                    //图片缓存i
                    CacheImage.putBitmap(list.get(i).get("imaUrl").replaceAll("/","").replace(".png?m=","").replace("_",""),dateFromFile.getBitmap());}
            }
           catch (Exception e){e.printStackTrace();}
        }
        return list;
    }
}
