package com.example.administrator.v2exofautoedit;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.v2exofautoedit.obofsqlite.InitialTable;
import com.example.administrator.v2exofautoedit.saveordownload.SearchfindTask;
import com.example.administrator.v2exofautoedit.saveordownload.ShowTask;

public class SecondActivity extends Activity {

    private AutoCompleteTextView actv;
    ArrayAdapter adapter;
    ProgressDialog progressDialog;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("正在下载....");
        //通过findViewById()方法取到actv
        actv = (AutoCompleteTextView)findViewById(R.id.actv);
        //new ArrayAdapter对象并将autoStr字符串数组传入actv中
       SearchfindTask showTask=new SearchfindTask(progressDialog,adapter,actv,this);
        showTask.execute();
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                //这个就是取点击的条目绑定的值,
                //实际上返回的就是适配器的 Adapter.getItem(position);
            }
        });
    }

}