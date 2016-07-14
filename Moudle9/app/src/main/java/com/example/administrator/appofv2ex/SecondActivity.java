package com.example.administrator.appofv2ex;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.appofv2ex.netspider.SecondTask;
import com.example.administrator.appofv2ex.save.DateFromFile;

public class SecondActivity extends AppCompatActivity {
    //从文件获取位置
      int index;int position;
    //图片
    String imaUrl;
    //进度条
    ProgressDialog progressDialog;
    //从MainActivity传来的数据
    Date date;
    //动态添加元素的linearlayout布局
    LinearLayout linearLayout;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("正在下载....");
        Log.d("haha","wuxian");
        //获取URL
        Intent intent=getIntent();

        date=(Date)intent.getSerializableExtra("dates");
        Initialview();
        TextView topicContent=(TextView)findViewById(R.id.topic_content);
        boolean ifHasNet=isNetworkAvailable(SecondActivity.this);

       SecondTask secondTask=new  SecondTask(progressDialog,SecondActivity.this,date.getNewUrl(),topicContent,linearLayout,index,imaUrl,imageView,ifHasNet);
        secondTask.execute();

    }
    //判断是否有网
    public boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void  Initialview(){
        linearLayout=(LinearLayout)findViewById(R.id.linear);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("正在下载....");
       imageView=(ImageView)findViewById(R.id.ima);
        TextView name=(TextView)findViewById(R.id.name);name.setText(date.getName());
        TextView type=(TextView)findViewById(R.id.name);type.setText(date.getType());
        TextView showId=(TextView)findViewById(R.id.showId);showId.setText(date.getShowId());
        TextView time=(TextView)findViewById(R.id.time);time.setText(date.getTime());
        TextView content=(TextView)findViewById(R.id.content);content.setText(date.getContent());
        index=date.getIndex();
        imaUrl=date.getUrl();
    }

}
