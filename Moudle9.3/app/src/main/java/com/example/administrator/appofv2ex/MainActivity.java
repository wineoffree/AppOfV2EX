package com.example.administrator.appofv2ex;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.administrator.appofv2ex.downloadimg.DownImageTask;
import com.example.administrator.appofv2ex.listadapter.ListWithNetAdapter;
import com.example.administrator.appofv2ex.listadapter.ListWithoutNetAdapter;
import com.example.administrator.appofv2ex.listadapter.MyPagerAdapter;
import com.example.administrator.appofv2ex.netspider.FirstTask;
import com.example.administrator.appofv2ex.save.CacheImage;
import com.example.administrator.appofv2ex.save.FileShowTask;
import com.example.administrator.appofv2ex.search.SearchChooseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //记录当前指示条和VIEW页面所在位置
    int currentIndex=0;
    //记录选择的指示条和VIEW页面所在位置
    int selectIndex;
    ArrayList<RadioButton>buttons=new ArrayList<RadioButton>();
    RadioButton btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11;
    HorizontalScrollView horizontalScrollView;
    // view数组
    private List<View> viewList;
    //viewPager
    ViewPager viewPagerp;
    //listview
    ListView listView;
    //listview适配器
    ListWithNetAdapter listWithNetAdapter=null;ListWithoutNetAdapter listWithoutNetAdapter=null;
    //进度条
    ProgressDialog progressDialog;
    //是否有网
    boolean ifHasNet=false;
    //监听当前的 index
    int mainIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CacheImage cacheImage=new CacheImage();
        InitialPager();
        InitRadioButton();
        MyPagerAdapter adapter = new MyPagerAdapter(viewList);
        //设定viewPager适配器
        viewPagerp = (ViewPager)findViewById(R.id.viewpager);
        viewPagerp.setAdapter(adapter);
        viewPagerp.addOnPageChangeListener(new MyPageChangeListener());
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("正在下载....");
        //初始化界面
        //无网时
        ifHasNet=isNetworkAvailable(MainActivity.this);
        if(ifHasNet){
            listWithNetAdapter = new ListWithNetAdapter(MainActivity.this, 0);
            listView.setOnScrollListener(new myListViewlistener());
            FirstTask downloadTheLastTask = new FirstTask(progressDialog, listWithNetAdapter, listView, MainActivity.this, 0);
            downloadTheLastTask.execute();
        }
        else {
            listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,0);
            FileShowTask fileShowTask=new  FileShowTask(progressDialog,listWithoutNetAdapter,listView,MainActivity.this,0);
            fileShowTask.execute();
        }
        //搜索按钮
        ImageButton search=(ImageButton) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SearchChooseActivity.class);
                startActivity(intent);
            }
        });
        //刷新按钮
        ImageButton refresh=(ImageButton) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifHasNet=isNetworkAvailable(MainActivity.this);
                if(ifHasNet){
                listWithNetAdapter = new ListWithNetAdapter(MainActivity.this,mainIndex);
                    listView.setOnScrollListener(new myListViewlistener());
                FirstTask downloadTheLastTask = new FirstTask(progressDialog, listWithNetAdapter, listView, MainActivity.this, mainIndex);
                downloadTheLastTask.execute();}
                else {
                    listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this, mainIndex);
                    FileShowTask fileShowTask=new  FileShowTask(progressDialog,listWithoutNetAdapter,listView,MainActivity.this, mainIndex);
                    fileShowTask.execute();
                }
            }
        });
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
    //恢复原有按钮颜色
    public void setColor(int index){
        if(index==1){btn1.setTextColor(Color.rgb(248,248,255));
            btn1.setBackgroundColor(Color.rgb(211,211,211));}
        if(index==2){btn2.setTextColor(Color.rgb(248,248,255));
            btn2.setBackgroundColor(Color.rgb(211,211,211));}
        if(index==3){btn3.setTextColor(Color.rgb(248,248,255));
            btn3.setBackgroundColor(Color.rgb(211,211,211));}
        if(index==4){btn4.setTextColor(Color.rgb(248,248,255));
            btn4.setBackgroundColor(Color.rgb(211,211,211));}
        if(index==5){btn5.setTextColor(Color.rgb(248,248,255));
            btn5.setBackgroundColor(Color.rgb(211,211,211));}
        if(index==6){btn6.setTextColor(Color.rgb(248,248,255));
            btn6.setBackgroundColor(Color.rgb(211,211,211));}
        if(index==7){btn7.setTextColor(Color.rgb(248,248,255));
            btn7.setBackgroundColor(Color.rgb(211,211,211));}
        if(index==8){btn8.setTextColor(Color.rgb(248,248,255));
            btn8.setBackgroundColor(Color.rgb(211,211,211));}
        if(index==9){btn9.setTextColor(Color.rgb(248,248,255));
            btn9.setBackgroundColor(Color.rgb(211,211,211));}
        if(index==10){btn10.setTextColor(Color.rgb(248,248,255));
            btn10.setBackgroundColor(Color.rgb(211,211,211));}
        if(index==11){btn11.setTextColor(Color.rgb(248,248,255));
            btn11.setBackgroundColor(Color.rgb(211,211,211));}
    }
    //初始化button
    private void InitRadioButton() {
        horizontalScrollView=(HorizontalScrollView)findViewById(R.id.scrollView) ;
        btn1 = (RadioButton) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainIndex=0;
                setColor(currentIndex);
                btn1.setTextColor(Color.rgb(0,0,0));
                btn1.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=1;
                viewPagerp.setCurrentItem(currentIndex-1);
                listView=(ListView)viewList.get(0).findViewById(R.id.list);
                listView.setOnItemClickListener(new MyListViewClicklistener());

                ifHasNet=isNetworkAvailable(MainActivity.this);
                if( ifHasNet){
                    listWithNetAdapter = new ListWithNetAdapter(MainActivity.this, 0);
                    listView.setOnScrollListener(new myListViewlistener());
                    FirstTask downloadTheLastTask = new FirstTask(progressDialog, listWithNetAdapter, listView, MainActivity.this, 0);
                    downloadTheLastTask.execute();

                }
                else {
                    listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,0);
                    FileShowTask fileShowTask=new  FileShowTask(progressDialog,listWithoutNetAdapter,listView,MainActivity.this,0);
                    fileShowTask.execute();
                }
            }
        });
        btn2 = (RadioButton) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainIndex=1;
                setColor(currentIndex);
                btn2.setTextColor(Color.rgb(0,0,0));
                btn2.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=2;
                viewPagerp.setCurrentItem(currentIndex-1);
                listView=(ListView)viewList.get(1).findViewById(R.id.list);
                listView.setOnItemClickListener(new MyListViewClicklistener());
                ifHasNet=isNetworkAvailable(MainActivity.this);
                if(ifHasNet){
                    listWithNetAdapter = new ListWithNetAdapter(MainActivity.this,1);
                    listView.setOnScrollListener(new myListViewlistener());
                    FirstTask downloadTheLastTask = new FirstTask(progressDialog, listWithNetAdapter, listView, MainActivity.this, 1);
                    downloadTheLastTask.execute();

                }
                else {
                    listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,1);
                    FileShowTask fileShowTask=new  FileShowTask(progressDialog,listWithoutNetAdapter,listView,MainActivity.this,1);
                    fileShowTask.execute();
                }
            }
        });
        btn3 = (RadioButton) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainIndex=2;
                setColor(currentIndex);
                btn3.setTextColor(Color.rgb(0,0,0));
                btn3.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=3;
                viewPagerp.setCurrentItem(currentIndex-1);
                listView=(ListView)viewList.get(2).findViewById(R.id.list);
                listView.setOnItemClickListener(new MyListViewClicklistener());
                ifHasNet=isNetworkAvailable(MainActivity.this);
                if(ifHasNet){

                    listWithNetAdapter = new ListWithNetAdapter(MainActivity.this, 2);
                    listView.setOnScrollListener(new myListViewlistener());
                    FirstTask downloadTheLastTask = new FirstTask(progressDialog, listWithNetAdapter, listView, MainActivity.this, 2);
                    downloadTheLastTask.execute();

                }
                else { listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,2);
                    FileShowTask fileShowTask=new  FileShowTask(progressDialog,listWithoutNetAdapter,listView,MainActivity.this,2);
                    fileShowTask.execute();
                }
            }
        });
        btn4 = (RadioButton) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainIndex=3;
                setColor(currentIndex);
                btn4.setTextColor(Color.rgb(0,0,0));
                btn4.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=4;
                viewPagerp.setCurrentItem(currentIndex-1);
                listView=(ListView)viewList.get(3).findViewById(R.id.list);
                listView.setOnItemClickListener(new MyListViewClicklistener());
                // ifHasNet=isNetworkAvailable(MainActivity.this);
                if(ifHasNet){

                    listWithNetAdapter = new ListWithNetAdapter(MainActivity.this, 3);
                    listView.setOnScrollListener(new myListViewlistener());
                    FirstTask downloadTheLastTask = new FirstTask(progressDialog, listWithNetAdapter, listView, MainActivity.this, 3);
                    downloadTheLastTask.execute();
                }
                else { listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,3);
                    FileShowTask fileShowTask=new  FileShowTask(progressDialog,listWithoutNetAdapter,listView,MainActivity.this,3);
                    fileShowTask.execute();

                }
            }
        });
        btn5 = (RadioButton) findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainIndex=4;
                setColor(currentIndex);
                btn5.setTextColor(Color.rgb(0,0,0));
                btn5.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=5;
                viewPagerp.setCurrentItem(currentIndex-1);
                listView=(ListView)viewList.get(4).findViewById(R.id.list);
                listView.setOnItemClickListener(new MyListViewClicklistener());
                ifHasNet=isNetworkAvailable(MainActivity.this);
                if(ifHasNet){

                    listWithNetAdapter = new ListWithNetAdapter(MainActivity.this, 4);
                    listView.setOnScrollListener(new myListViewlistener());
                    FirstTask downloadTheLastTask = new FirstTask(progressDialog, listWithNetAdapter, listView, MainActivity.this, 4);
                    downloadTheLastTask.execute();
                }
                else {listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,4);
                    FileShowTask fileShowTask=new  FileShowTask(progressDialog,listWithoutNetAdapter,listView,MainActivity.this,4);
                    fileShowTask.execute();

                }
            }
        });
        btn6 = (RadioButton) findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainIndex=5;
                setColor(currentIndex);
                btn6.setTextColor(Color.rgb(0,0,0));
                btn6.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=6;
                viewPagerp.setCurrentItem(currentIndex-1);
                listView=(ListView)viewList.get(5).findViewById(R.id.list);
                listView.setOnItemClickListener(new MyListViewClicklistener());
                ifHasNet=isNetworkAvailable(MainActivity.this);
                if(ifHasNet){

                    listWithNetAdapter = new ListWithNetAdapter(MainActivity.this, 5);
                    listView.setOnScrollListener(new myListViewlistener());
                    FirstTask downloadTheLastTask = new FirstTask(progressDialog, listWithNetAdapter, listView, MainActivity.this, 5);
                    downloadTheLastTask.execute();
                }
                else { listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,5);
                    FileShowTask fileShowTask=new  FileShowTask(progressDialog,listWithoutNetAdapter,listView,MainActivity.this,5);
                    fileShowTask.execute();

                }
            }
        });
        btn7 = (RadioButton) findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainIndex=6;
                setColor(currentIndex);
                btn7.setTextColor(Color.rgb(0,0,0));
                btn7.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=7;
                viewPagerp.setCurrentItem(currentIndex-1);
                listView=(ListView)viewList.get(6).findViewById(R.id.list);
                listView.setOnItemClickListener(new MyListViewClicklistener());
                ifHasNet=isNetworkAvailable(MainActivity.this);
                if(ifHasNet){

                    listWithNetAdapter = new ListWithNetAdapter(MainActivity.this, 6);
                    listView.setOnScrollListener(new myListViewlistener());
                    FirstTask downloadTheLastTask = new FirstTask(progressDialog, listWithNetAdapter, listView, MainActivity.this, 6);
                    downloadTheLastTask.execute();
                }
                else {listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,6);
                    FileShowTask fileShowTask=new  FileShowTask(progressDialog,listWithoutNetAdapter,listView,MainActivity.this,6);
                    fileShowTask.execute();

                }
            }
        });
        btn8 = (RadioButton) findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainIndex=7;
                setColor(currentIndex);
                btn8.setTextColor(Color.rgb(0,0,0));
                btn8.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=8;
                viewPagerp.setCurrentItem(currentIndex-1);
                listView=(ListView)viewList.get(7).findViewById(R.id.list);
                listView.setOnItemClickListener(new MyListViewClicklistener());
                ifHasNet=isNetworkAvailable(MainActivity.this);
                if(ifHasNet){

                    listWithNetAdapter = new ListWithNetAdapter(MainActivity.this, 7);
                    listView.setOnScrollListener(new myListViewlistener());
                    FirstTask downloadTheLastTask = new FirstTask(progressDialog, listWithNetAdapter, listView, MainActivity.this, 7);
                    downloadTheLastTask.execute();
                }
                else {  listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,7);
                    FileShowTask fileShowTask=new  FileShowTask(progressDialog,listWithoutNetAdapter,listView,MainActivity.this,7);
                    fileShowTask.execute();

                }
            }
        });
        btn9 = (RadioButton) findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainIndex=8;
                setColor(currentIndex);
                btn9.setTextColor(Color.rgb(0,0,0));
                btn9.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=9;
                viewPagerp.setCurrentItem(currentIndex-1);
                listView=(ListView)viewList.get(8).findViewById(R.id.list);
                listView.setOnItemClickListener(new MyListViewClicklistener());
                ifHasNet=isNetworkAvailable(MainActivity.this);
                if(ifHasNet){

                    listWithNetAdapter = new ListWithNetAdapter(MainActivity.this, 8);
                    listView.setOnScrollListener(new myListViewlistener());
                    FirstTask downloadTheLastTask = new FirstTask(progressDialog, listWithNetAdapter, listView, MainActivity.this, 8);
                    downloadTheLastTask.execute();
                }
                else {listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,8);
                    FileShowTask fileShowTask=new  FileShowTask(progressDialog,listWithoutNetAdapter,listView,MainActivity.this,8);
                    fileShowTask.execute();

                }
            }
        });
        btn10 = (RadioButton) findViewById(R.id.btn10);
        btn10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainIndex=9;
                setColor(currentIndex);
                btn10.setTextColor(Color.rgb(0,0,0));
                btn10.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=10;
                viewPagerp.setCurrentItem(currentIndex-1);
                listView=(ListView)viewList.get(9).findViewById(R.id.list);
                listView.setOnItemClickListener(new MyListViewClicklistener());
                ifHasNet=isNetworkAvailable(MainActivity.this);
                if(ifHasNet){

                    listWithNetAdapter = new ListWithNetAdapter(MainActivity.this, 9);
                    listView.setOnScrollListener(new myListViewlistener());
                    FirstTask downloadTheLastTask = new FirstTask(progressDialog, listWithNetAdapter, listView, MainActivity.this, 9);
                    downloadTheLastTask.execute();
                }
                else { listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,9);
                    FileShowTask fileShowTask=new  FileShowTask(progressDialog,listWithoutNetAdapter,listView,MainActivity.this,9);
                    fileShowTask.execute();

                }
            }
        });
        btn11 = (RadioButton) findViewById(R.id.btn11);
        btn11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainIndex=10;
                setColor(currentIndex);
                btn11.setTextColor(Color.rgb(0,0,0));
                btn11.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=11;
                viewPagerp.setCurrentItem(currentIndex-1);
                listView=(ListView)viewList.get(10).findViewById(R.id.list);
                listView.setOnItemClickListener(new MyListViewClicklistener());
                 ifHasNet=isNetworkAvailable(MainActivity.this);
                if( ifHasNet){

                    listWithNetAdapter = new ListWithNetAdapter(MainActivity.this, 10);
                    listView.setOnScrollListener(new myListViewlistener());
                    FirstTask downloadTheLastTask = new FirstTask(progressDialog, listWithNetAdapter, listView, MainActivity.this, 10);
                    downloadTheLastTask.execute();
                }
                else { listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,10);
                    FileShowTask fileShowTask=new  FileShowTask(progressDialog,listWithoutNetAdapter,listView,MainActivity.this,10);
                    fileShowTask.execute();

                }
            }
        });
        btn1.setTextColor(Color.rgb(248,248,255));
        btn1.setBackgroundColor(Color.rgb(211,211,211));



    }
    //初始化viewpager
    public void InitialPager() {
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        LayoutInflater inflater = getLayoutInflater();

        for(int i=0;i<11;i++) {
            View view = inflater.inflate(R.layout.layout_viewofpager, null);
            viewList.add(view);
        }
        listView=(ListView)  viewList.get(0).findViewById(R.id.list);
        listView.setOnItemClickListener(new MyListViewClicklistener());

    }
    //自定义listview滚动监听
    public class myListViewlistener implements AbsListView.OnScrollListener {
        @Override
        public void onScrollStateChanged(AbsListView absListView, int scrollState) {
            if(ifHasNet) {
                 switch (scrollState) {

                     case AbsListView.OnScrollListener.SCROLL_STATE_IDLE://停止滚动
                     {   try {
                         //设置为停止滚动
                         listWithNetAdapter.setScrollState(false);
                     }
                         catch (Exception e){e.printStackTrace();}
                         //当前屏幕中listview的子项的个数
                         int count = absListView.getChildCount();
                         Log.e("MainActivity", count + "");

                         for (int i = 0; i < count; i++) {

                             //获取到item的头像
                             ImageView ima = (ImageView) absListView.getChildAt(i).findViewById(R.id.ima);


                             if (!ima.getTag().equals("1")) {//!="1"说明需要加载数据
                                 String image_url = ima.getTag().toString();//直接从Tag中取出我们存储的数据image——url
                                 new DownImageTask(ima, true, mainIndex).execute(image_url);
                                 ima.setTag("1");//设置为已加载过数据
                             }
                         }
                         break;
                     }
                     case AbsListView.OnScrollListener.SCROLL_STATE_FLING://滚动做出了抛的动作
                     {
                         //设置为正在滚动
                         try {
                             listWithNetAdapter.setScrollState(true);

                         }
                         catch (Exception e){e.printStackTrace();}
                         break;
                     }

                     case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://正在滚动
                     { try {
                         //设置为正在滚动
                         listWithNetAdapter.setScrollState(true);

                     }
                        catch (Exception e){e.printStackTrace();}
                         break;
                     }
                 }

             }

        }

        @Override
        public void onScroll(AbsListView absListView, int i, int i1, int i2) {

        }
    }
    //viewpager的页面改变监听器
    public class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            if(arg0==0){btn1.performClick();horizontalScrollView.scrollTo(0,0);}
            if(arg0==1){btn2.performClick();horizontalScrollView.scrollTo(0,0);}
            if(arg0==2){btn3.performClick();horizontalScrollView.scrollTo(0,0);}
            if(arg0==3){btn4.performClick();horizontalScrollView.scrollTo(0,0);}
            if(arg0==4){btn5.performClick();horizontalScrollView.scrollTo(150,0);}
            if(arg0==5){btn6.performClick();horizontalScrollView.scrollTo(150,0);}
            if(arg0==6){btn7.performClick();horizontalScrollView.scrollTo(200,0);}
            if(arg0==7){btn8.performClick();horizontalScrollView.scrollTo(250,0);}
            if(arg0==8){btn9.performClick();horizontalScrollView.scrollTo(320,0);}
            if(arg0==9){btn10.performClick();horizontalScrollView.scrollTo(800,0);}
            if(arg0==10){btn11.performClick();horizontalScrollView.scrollTo(800,0);}

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
    //listview items监听器
    class MyListViewClicklistener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            ifHasNet=isNetworkAvailable(MainActivity.this);
            if(ifHasNet){
            Date date=new Date(listWithNetAdapter.getList().get(position).get("newUrl"),
                    listWithNetAdapter.getList().get(position).get("name"),
                    listWithNetAdapter.getList().get(position).get("type"),
                    listWithNetAdapter.getList().get(position).get("showId"),
                    listWithNetAdapter.getList().get(position).get("time"),
                    listWithNetAdapter.getList().get(position).get("content"),
                    listWithNetAdapter.getIndex(),
                    listWithNetAdapter.getList().get(position).get("ima"));
            Bundle dates=new Bundle();
            dates.putSerializable("dates",date);
            Intent intent=new Intent(MainActivity.this,SecondActivity.class);
            intent.putExtras(dates);
            startActivity(intent);}
        }
    }

}


