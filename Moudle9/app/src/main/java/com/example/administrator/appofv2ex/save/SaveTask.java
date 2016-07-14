package com.example.administrator.appofv2ex.save;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.appofv2ex.R;
import com.example.administrator.appofv2ex.downloadimg.DownImageTask;
import com.example.administrator.appofv2ex.save.SaveToFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/13.
 */
public class SaveTask extends AsyncTask<String, Integer, List<Map<String,String>>> {
    int index;
    String content;
    public SaveTask(int index,String content){
       this.content=content;
        this.index=index;
    }
    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Map<String, String>> result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
    }

    @Override
    protected List<Map<String,String>> doInBackground(String... params) {
        List<Map<String,String>> list=null;
        //存储到文件
        SaveToFile saveToFile=new SaveToFile(index);
        saveToFile.setContent(content);
        saveToFile.saveContent();
        //saveToFile.setConfigure(String.valueOf(list.size()));
        //saveToFile.saveConfigure();
        return list;
    }
}
