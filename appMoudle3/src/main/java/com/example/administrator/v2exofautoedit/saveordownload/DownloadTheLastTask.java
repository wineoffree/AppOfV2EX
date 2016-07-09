package com.example.administrator.v2exofautoedit.saveordownload;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.administrator.v2exofautoedit.MainActivity;
import com.example.administrator.v2exofautoedit.ViewHolder;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

//完成JSON的异步解析
public class DownloadTheLastTask extends AsyncTask<String, Integer, List<Map<String,Object>>> {
    ProgressDialog progressDialog;
    MainActivity.TheLastListViewAdapter adapter;
    ListView listView;//下拉框
    Context context;
    int i;

    public DownloadTheLastTask(ProgressDialog progressDialog,MainActivity.TheLastListViewAdapter adapter,ListView listView,Context context){

        this.progressDialog=progressDialog;
        this.adapter=adapter;
        this.listView=listView;
        this.context=context;
    }
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(List<Map<String, Object>> result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        adapter.setData(result);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        while (i< result.size()) {
            JSONObject jsonObject1=new JSONObject();
            DownImageforfile downImage;
                try {
                    jsonObject1 =new JSONObject(result.get(i).get("node").toString()) ;
                    downImage = new DownImageforfile(jsonObject1.getString("avatar_normal"),context,i);
                    downImage.loadImage(new DownImageforfile.ImageCallBack() {
                        public void getDrawable(Bitmap bitmap) {
                            // TODO Auto-generated method stub
                        }
                    });
                }
            catch (Exception e){e.printStackTrace();}


            Log.d("ttttt",String.valueOf(i));


            i++;
        }
    }

    @Override
    protected List<Map<String, Object>> doInBackground(String... params) {
        List<Map<String,Object>> list;
        String str=null;
        try {
            str= HttpUtil.readParse(params[0]);
        }
        catch (Exception e){e.printStackTrace();}
        list = HttpUtil.getRequest2List(str);

        final ViewHolder viewHolder;

        JSONObject jsonObject1=new JSONObject();


        SaveToFile saveToFile=new SaveToFile(context);
        saveToFile.setContent(str);
        saveToFile.saveContent();
        saveToFile.setConfigure(String.valueOf(list.size()));
        saveToFile.saveConfigure();
        return list;
    }



}