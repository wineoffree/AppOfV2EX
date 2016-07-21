package com.example.administrator.packagetest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.NavigationView;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.example.administrator.packagetest.listadapter.ListWithNetAdapter;
import com.example.administrator.packagetest.listadapter.ListWithoutNetAdapter;
import com.example.administrator.packagetest.listadapter.MyPagerAdapter;
import com.example.administrator.packagetest.netspider.FirstTask;
import com.example.administrator.packagetest.save.FileShowTask;
import com.example.administrator.packagetest.save.SaveToFile;
import com.example.administrator.packagetest.search.SearchChooseActivity;
import com.example.netlibrary.CacheImage;
import com.example.netlibrary.DownImageTask;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //记录当前指示条和VIEW页面所在位置
    int currentIndex=0;
    ArrayList<RadioButton> buttons=new ArrayList<RadioButton>();
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
    ProgressBar progressBar;
    ProgressBar reProgressBar;
    //是否有网
    boolean ifHasNet=false;
    //监听当前的 index
    int mainIndex=0;
    private Toolbar toolbar;
    //drawer里面按钮
    Button delete,sortpager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    //listview第一个ITEM是否显示
    private int firstItem=0;
    //进度条初始位置 上次位置
    float originY;float lastY;
    Animation animation = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CacheImage cacheImage=new CacheImage();
        InitialPager();
        InitRadioButton();
        final MyPagerAdapter adapter = new MyPagerAdapter(viewList);
        //设定viewPager适配器
        viewPagerp = (ViewPager)findViewById(R.id.viewpager);
        viewPagerp.setAdapter(adapter);
        viewPagerp.addOnPageChangeListener(new MyPageChangeListener());
        progressBar = (ProgressBar)findViewById(R.id.bar);
        progressBar .setVisibility(View.VISIBLE);
        reProgressBar=(ProgressBar)findViewById(R.id.rebar);
        reProgressBar.setVisibility(View.INVISIBLE);
        lastY=progressBar.getY();
        originY=lastY;
        //初始化界面
        //无网时
        ifHasNet=isNetworkAvailable(MainActivity.this);
        if(ifHasNet){
            listWithNetAdapter = new ListWithNetAdapter(MainActivity.this, 0);
            listView.setOnScrollListener(new myListViewlistener());
            FirstTask downloadTheLastTask = new FirstTask(progressBar ,listWithNetAdapter, listView, MainActivity.this, 0);
            downloadTheLastTask.execute();
        }
        else {
            listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,0);
            FileShowTask fileShowTask=new  FileShowTask(progressBar,listWithoutNetAdapter,listView,MainActivity.this,0);
            fileShowTask.execute();
        }
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawOnclick();
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) ;
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //TouchEvent dispatcher.
        this.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
    //上次手势位置
    float lastGestureY;
    //记录初始手势位置
    float originGestureY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastY=originY;
            originGestureY=event.getRawY();
            lastGestureY=event.getRawY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if(listWithNetAdapter.getIfFinishDownload())
                if (firstItem==0){
                if((event.getRawY()-originGestureY)>300){
                    if(listWithNetAdapter.getIfFinishDownload())
                        {
                            ifHasNet=isNetworkAvailable(MainActivity.this);
                            if(ifHasNet){
                                ListWithNetAdapter listWithNetAdapter2=new ListWithNetAdapter(MainActivity.this,mainIndex);
                                listView.setOnScrollListener(new myListViewlistener());
                                FirstTask downloadTheLastTask = new FirstTask(progressBar, listWithNetAdapter2, listWithNetAdapter, listView, MainActivity.this, mainIndex);
                                downloadTheLastTask.execute();
                            }
                            else {
                                listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this, mainIndex);
                                FileShowTask fileShowTask=new  FileShowTask(progressBar,listWithoutNetAdapter,listView,MainActivity.this, mainIndex);
                                fileShowTask.execute();
                            }
                            reProgressBar.clearAnimation();
                            reProgressBar.setVisibility(ProgressBar.INVISIBLE);
                        }
                }
                    else {
                    if((lastY-originY)>3){
                    animation = new TranslateAnimation(0, 0, lastY,originY);
                    animation.setDuration(400);
                    reProgressBar.startAnimation(animation);}
                }
                }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if((event.getRawY()-lastGestureY)>3)
            if(listWithNetAdapter.getIfFinishDownload())
                if(event.getRawY()>lastGestureY)
                if(firstItem==0){
                    if(lastY<400){
                        reProgressBar.setVisibility(View.VISIBLE);
                        animation = new TranslateAnimation(0, 0, lastY,lastY+event.getRawY()-lastGestureY);
                        animation.setFillAfter(true);
                        animation.setDuration(400);
                        reProgressBar.startAnimation(animation);
                    }
                    if(lastY>400){
                        reProgressBar.setVisibility(View.VISIBLE);
                        animation = new TranslateAnimation(0, 0, lastY,lastY);
                        animation.setDuration(1000);
                        reProgressBar.startAnimation(animation);
                    }
                }
            lastY=lastY+event.getRawY()-lastGestureY;
            lastGestureY=event.getRawY();
            reProgressBar.setVisibility(View.INVISIBLE);
        }

        return false;
    }
    //drawer中点击事件
    public void drawOnclick(){
        Log.d("papapa","hahahah");
        delete=(Button)findViewById(R.id.delete);
        sortpager=(Button)findViewById(R.id.sortpager);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("papapa","hahahah");
                SaveToFile saveToFile=new SaveToFile(MainActivity.this);
                saveToFile.deleteFile();
            }
        });
        sortpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortPagerFragment fragment = new  SortPagerFragment();
                // 使用fragment替换book_detail_container容器当前显示的Fragment
                getFragmentManager().beginTransaction()
                        .replace(R.id.frage, fragment)
                        .commit();
            }
        });
    }
    //menu布局
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflator = new MenuInflater(this);
        //装填R.menu.my_menu对应的菜单，并添加到menu中
        inflator.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // 菜单项被单击后的回调方法
    public boolean onOptionsItemSelected(MenuItem mi)
    {   reProgressBar.clearAnimation();
        if(mi.isCheckable())
        {
            // 勾选该菜单项
            mi.setChecked(true);  // ②
        }
        //判断单击的是哪个菜单项，并有针对性地作出响应
        switch (mi.getItemId())
        {
            case R.id.search:
                Intent intent=new Intent(MainActivity.this,SearchChooseActivity.class);
                startActivity(intent);
                break;
            case R.id.refresh:
                ifHasNet=isNetworkAvailable(MainActivity.this);
                if(ifHasNet){
                    ListWithNetAdapter listWithNetAdapter2=new ListWithNetAdapter(MainActivity.this,mainIndex);
                    listView.setOnScrollListener(new myListViewlistener());
                    FirstTask downloadTheLastTask = new FirstTask(progressBar, listWithNetAdapter2,listWithNetAdapter, listView, MainActivity.this, mainIndex);
                    downloadTheLastTask.execute();
                }
                else {
                    listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this, mainIndex);
                    FileShowTask fileShowTask=new  FileShowTask(progressBar,listWithoutNetAdapter,listView,MainActivity.this, mainIndex);
                    fileShowTask.execute();
                }
                break;
        }
        return true;
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
    //设置buttons点击事件
    public void onButtons(int i){
        mainIndex=i;
        setColor(currentIndex);
        currentIndex=i+1;
        viewPagerp.setCurrentItem(currentIndex-1);
        listView=(ListView)viewList.get(i).findViewById(R.id.list);
        listView.setOnItemClickListener(new MyListViewClicklistener());
        ifHasNet=isNetworkAvailable(MainActivity.this);
        if(ifHasNet){
            ListWithNetAdapter listWithNetAdapter2=new ListWithNetAdapter(MainActivity.this,i);
            listView.setOnScrollListener(new myListViewlistener());
            FirstTask downloadTheLastTask = new FirstTask(progressBar, listWithNetAdapter2,listWithNetAdapter, listView, MainActivity.this,i);
            downloadTheLastTask.execute();
        }
        else {
            listWithoutNetAdapter=new ListWithoutNetAdapter(MainActivity.this,i);
            FileShowTask fileShowTask=new  FileShowTask(progressBar,listWithoutNetAdapter,listView,MainActivity.this,i);
            fileShowTask.execute();
        }
    }
    //初始化button
    private void InitRadioButton() {
        horizontalScrollView=(HorizontalScrollView)findViewById(R.id.scrollView) ;
        btn1 = (RadioButton) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn1.setTextColor(Color.rgb(0,0,0));
                btn1.setBackgroundColor(Color.rgb(248,248,255));
                onButtons(0);
            }
        });
        btn2 = (RadioButton) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn2.setTextColor(Color.rgb(0,0,0));
                btn2.setBackgroundColor(Color.rgb(248,248,255));
                onButtons(1);
            }
        });
        btn3 = (RadioButton) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn3.setTextColor(Color.rgb(0,0,0));
                btn3.setBackgroundColor(Color.rgb(248,248,255));
                onButtons(2);
            }
        });
        btn4 = (RadioButton) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn4.setTextColor(Color.rgb(0,0,0));
                btn4.setBackgroundColor(Color.rgb(248,248,255));
                onButtons(3);
            }
        });
        btn5 = (RadioButton) findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn5.setTextColor(Color.rgb(0,0,0));
                btn5.setBackgroundColor(Color.rgb(248,248,255));
                onButtons(4);
            }
        });
        btn6 = (RadioButton) findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn6.setTextColor(Color.rgb(0,0,0));
                btn6.setBackgroundColor(Color.rgb(248,248,255));
                onButtons(5);
            }
        });
        btn7 = (RadioButton) findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn7.setTextColor(Color.rgb(0,0,0));
                btn7.setBackgroundColor(Color.rgb(248,248,255));
                onButtons(6);
            }
        });
        btn8 = (RadioButton) findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn8.setTextColor(Color.rgb(0,0,0));
                btn8.setBackgroundColor(Color.rgb(248,248,255));
                onButtons(7);
            }
        });
        btn9 = (RadioButton) findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn9.setTextColor(Color.rgb(0,0,0));
                btn9.setBackgroundColor(Color.rgb(248,248,255));
                onButtons(8);
            }
        });
        btn10 = (RadioButton) findViewById(R.id.btn10);
        btn10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn10.setTextColor(Color.rgb(0,0,0));
                btn10.setBackgroundColor(Color.rgb(248,248,255));
                onButtons(9);
            }
        });
        btn11 = (RadioButton) findViewById(R.id.btn11);
        btn11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btn11.setTextColor(Color.rgb(0,0,0));
                btn11.setBackgroundColor(Color.rgb(248,248,255));
                onButtons(10);
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
        listView.setLongClickable(true);
    }
    //自定义listview滚动监听
    public class myListViewlistener implements AbsListView.OnScrollListener
    {

        // 标记上次滑动位置
        private int lastPosition;
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
                        firstItem=absListView.getFirstVisiblePosition();
                        for (int i = 0; i < count; i++) {

                            //获取到item的头像
                            ImageView ima = (ImageView) absListView.getChildAt(i).findViewById(R.id.ima);


                            if (!ima.getTag().equals("1")) {//!="1"说明需要加载数据
                                Log.d("imamamama",ima.toString());
                                String image_url = ima.getTag().toString();//直接从Tag中取出我们存储的数据image——url
                                new DownImageTask(ima).execute(image_url);
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
        public void onScroll(AbsListView absListView, int firstVisibleItem,int visibleItemCount, int totalItemCount) {


        }
    }
    //viewpager的页面改变监听器
    public class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            listWithNetAdapter.setIfFinishDownload(false);
            reProgressBar.clearAnimation();
            if(arg0==0){btn1.performClick();horizontalScrollView.scrollTo(0,0);}
            if(arg0==1){btn2.performClick();horizontalScrollView.scrollTo(0,0);}
            if(arg0==2){btn3.performClick();horizontalScrollView.scrollTo(0,0);}
            if(arg0==3){btn4.performClick();horizontalScrollView.scrollTo(0,0);}
            if(arg0==4){btn5.performClick();horizontalScrollView.scrollTo(150,0);}
            if(arg0==5){btn6.performClick();horizontalScrollView.scrollTo(250,0);}
            if(arg0==6){btn7.performClick();horizontalScrollView.scrollTo(350,0);}
            if(arg0==7){btn8.performClick();horizontalScrollView.scrollTo(450,0);}
            if(arg0==8){btn9.performClick();horizontalScrollView.scrollTo(550,0);}
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
            if(ifHasNet){Log.d("pipi",listWithNetAdapter.getList().get(position).get("newUrl"));
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