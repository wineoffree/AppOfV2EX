package com.example.administrator.appv2ex;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.appv2ex.downloadandsafe.DownImage;
import com.example.administrator.appv2ex.downloadandsafe.DownImageTask;
import com.example.administrator.appv2ex.listadapter.MyPagerAdapter;
import com.example.administrator.appv2ex.netspider.FirstTask;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //记录当前指示条和VIEW页面所在位置
    int currentIndex=0;
    //记录选择的指示条和VIEW页面所在位置
    int selectIndex;
    RadioButton btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11;
    HorizontalScrollView horizontalScrollView;
    // view数组
    private List<View> viewList;
   //viewPager
   ViewPager vp;
   //listview
    ListView listView;
//存放每页的view
ArrayList<View> views;
    //listview适配器
    ListWithNetAdapter listWithNetAdapter;
//进度条
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView;
        InitRadioButton();
        InitialPager();
        MyPagerAdapter adapter = new MyPagerAdapter(viewList);

        //设定viewPager适配器
       vp = (ViewPager)findViewById(R.id.viewpager);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(new MyPageChangeListener());

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("正在下载....");

        listWithNetAdapter=new ListWithNetAdapter(this);
        FirstTask downloadTheLastTask=new  FirstTask(progressDialog,listWithNetAdapter,listView,MainActivity.this,0);
        downloadTheLastTask.execute();
        //初始化界面

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
                setColor(currentIndex);
                btn1.setTextColor(Color.rgb(0,0,0));
                btn1.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=1;
                vp.setCurrentItem(currentIndex-1);
                listView=(ListView) views.get(0).findViewById(R.id.list);
                FirstTask downloadTheLastTask=new  FirstTask(progressDialog,listWithNetAdapter,listView,MainActivity.this,0);
                downloadTheLastTask.execute();
            }
        });
        btn2 = (RadioButton) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(currentIndex);
                btn2.setTextColor(Color.rgb(0,0,0));
                btn2.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=2;
                vp.setCurrentItem(currentIndex-1);
                listView=(ListView) views.get(1).findViewById(R.id.list);
                FirstTask downloadTheLastTask=new  FirstTask(progressDialog,listWithNetAdapter,listView,MainActivity.this,1);
                downloadTheLastTask.execute();
            }
        });
        btn3 = (RadioButton) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(currentIndex);
                btn3.setTextColor(Color.rgb(0,0,0));
                btn3.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=3;
                vp.setCurrentItem(currentIndex-1);
                listView=(ListView) views.get(2).findViewById(R.id.list);
                FirstTask downloadTheLastTask=new  FirstTask(progressDialog,listWithNetAdapter,listView,MainActivity.this,2);
                downloadTheLastTask.execute();
            }
        });
        btn4 = (RadioButton) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(currentIndex);
                btn4.setTextColor(Color.rgb(0,0,0));
                btn4.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=4;
                vp.setCurrentItem(currentIndex-1);
                listView=(ListView) views.get(3).findViewById(R.id.list);
                FirstTask downloadTheLastTask=new  FirstTask(progressDialog,listWithNetAdapter,listView,MainActivity.this,3);
                downloadTheLastTask.execute();
            }
        });
        btn5 = (RadioButton) findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(currentIndex);
                btn5.setTextColor(Color.rgb(0,0,0));
                btn5.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=5;
                vp.setCurrentItem(currentIndex-1);
                listView=(ListView) views.get(4).findViewById(R.id.list);
                FirstTask downloadTheLastTask=new  FirstTask(progressDialog,listWithNetAdapter,listView,MainActivity.this,4);
                downloadTheLastTask.execute();
            }
        });
        btn6 = (RadioButton) findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(currentIndex);
                btn6.setTextColor(Color.rgb(0,0,0));
                btn6.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=6;
                vp.setCurrentItem(currentIndex-1);
                listView=(ListView) views.get(5).findViewById(R.id.list);
                FirstTask downloadTheLastTask=new  FirstTask(progressDialog,listWithNetAdapter,listView,MainActivity.this,5);
                downloadTheLastTask.execute();
            }
        });
        btn7 = (RadioButton) findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(currentIndex);
                btn7.setTextColor(Color.rgb(0,0,0));
                btn7.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=7;
                vp.setCurrentItem(currentIndex-1);
                listView=(ListView) views.get(6).findViewById(R.id.list);
                FirstTask downloadTheLastTask=new  FirstTask(progressDialog,listWithNetAdapter,listView,MainActivity.this,6);
                downloadTheLastTask.execute();
            }
        });
        btn8 = (RadioButton) findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(currentIndex);
                btn8.setTextColor(Color.rgb(0,0,0));
                btn8.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=8;
                vp.setCurrentItem(currentIndex-1);
                listView=(ListView) views.get(7).findViewById(R.id.list);
                FirstTask downloadTheLastTask=new  FirstTask(progressDialog,listWithNetAdapter,listView,MainActivity.this,7);
                downloadTheLastTask.execute();
            }
        });
        btn9 = (RadioButton) findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(currentIndex);
                btn9.setTextColor(Color.rgb(0,0,0));
                btn9.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=9;
                vp.setCurrentItem(currentIndex-1);
                listView=(ListView) views.get(8).findViewById(R.id.list);
                FirstTask downloadTheLastTask=new  FirstTask(progressDialog,listWithNetAdapter,listView,MainActivity.this,8);
                downloadTheLastTask.execute();
            }
        });
        btn10 = (RadioButton) findViewById(R.id.btn10);
        btn10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(currentIndex);
                btn10.setTextColor(Color.rgb(0,0,0));
                btn10.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=10;
                vp.setCurrentItem(currentIndex-1);
                listView=(ListView) views.get(9).findViewById(R.id.list);
                FirstTask downloadTheLastTask=new  FirstTask(progressDialog,listWithNetAdapter,listView,MainActivity.this,9);
                downloadTheLastTask.execute();
            }
        });
        btn11 = (RadioButton) findViewById(R.id.btn11);
        btn11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(currentIndex);
                btn11.setTextColor(Color.rgb(0,0,0));
                btn11.setBackgroundColor(Color.rgb(248,248,255));
                currentIndex=11;
                vp.setCurrentItem(currentIndex-1);
                listView=(ListView) views.get(10).findViewById(R.id.list);
                FirstTask downloadTheLastTask=new  FirstTask(progressDialog,listWithNetAdapter,listView,MainActivity.this,10);
                downloadTheLastTask.execute();
            }
        });
        btn1.setTextColor(Color.rgb(248,248,255));
        btn1.setBackgroundColor(Color.rgb(211,211,211));



    }


    //初始化viewpager
    public void InitialPager() {
        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        LayoutInflater inflater = getLayoutInflater();
        views=new ArrayList<View>();
        for(int i=0;i<11;i++) {
           View view = inflater.inflate(R.layout.layout_viewofpager, null);
            views.add(view);
            viewList.add(view);
        }
        listView=(ListView) views.get(0).findViewById(R.id.list);
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

    //页面listview适配器
    //网络加载时最新内容的适配器
    public class ListWithNetAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private List<Map<String,String>> list;
        public ListWithNetAdapter(Context context) {
            this.context = context;
            layoutInflater = layoutInflater.from(context);

        }

        public List getData(){
            return list;
        }

        public void setData(List<Map<String,String>> data){
            this.list = data;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        public  List<Map<String,String>> getList() {
            return list;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;
           final ViewHolder viewHolder;

            if (convertView == null ) {
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.layout_item, null);
                viewHolder.content = (TextView)convertView.findViewById(R.id.content);
                viewHolder.img = (ImageView)convertView.findViewById(R.id.ima);
                viewHolder.time = (TextView)convertView.findViewById(R.id.time);
                viewHolder.name = (TextView)convertView.findViewById(R.id.name);
                viewHolder.type = (TextView)convertView.findViewById(R.id.type);
                viewHolder.showId = (TextView)convertView.findViewById(R.id.showId);
                convertView.setTag(viewHolder);
            }
          else {

                viewHolder = (ViewHolder)convertView.getTag();
            }

            viewHolder.content.setText(list.get(position).get("content").toString());
            viewHolder.time.setText(list.get(position).get("time").toString());
            viewHolder.name.setText(list.get(position).get("name").toString());
            viewHolder.type.setText(list.get(position).get("type").toString());
            viewHolder.showId.setText(list.get(position).get("showId").toString());

            DownImage downImage;
            viewHolder.newUrl=list.get(position).get("newUrl").toString();
            //给img设置tag防止错位
            viewHolder.img.setTag(list.get(position).get("ima"));
            try {
                //downImage.loadImage(list.get(position).get("newUrl").toString())
                new DownImageTask(viewHolder.img).execute(list.get(position).get("ima"));

            }
            catch (Exception e){e.printStackTrace();}

            return convertView;
        }

    }
}


