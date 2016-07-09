package com.example.administrator.v2exofautoedit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.v2exofautoedit.listadapter.MyPagerAdapter;
import com.example.administrator.v2exofautoedit.saveordownload.CacheImage;
import com.example.administrator.v2exofautoedit.saveordownload.DownImage;
import com.example.administrator.v2exofautoedit.saveordownload.FileToShow;
import com.example.administrator.v2exofautoedit.saveordownload.ShowTask;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //文件操作


    private LruCache<String, Bitmap> memCache;
    ListView listView;//下拉框
    TheLastListViewAdapter adapter;
    TheLastListViewAdapterNoNet adapterNoNet;
    ProgressDialog progressDialog;
    CacheImage cacheImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //新建缓存
        cacheImage=new CacheImage(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        InitialView(this);
        initCursorPos();
        InitTextView();
        viewPager.setAdapter(new MyPagerAdapter(viewList));
        viewPager.addOnPageChangeListener(new MyPageChangeListener());
        //InitialTable initialTable=new InitialTable(this,"UserStore.db",null,2);
        //测试用的插入
        //initialTable.Insert();
        ImageView search=(ImageView) findViewById(R.id.search);
               search.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                  Intent intent=new Intent(MainActivity.this,SecondActivity.class);
               startActivity(intent);
           }
       });
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("正在下载....");

        //adapter = new TheLastListViewAdapter(this);
        adapterNoNet=new TheLastListViewAdapterNoNet(this);
       // DownloadTheLastTask downloadTheLastTask=new DownloadTheLastTask(progressDialog,adapter,listView,MainActivity.this);
        //downloadTheLastTask.execute(HttpUtil.BASE_URL);
        ShowTask showTask=new ShowTask(progressDialog,adapterNoNet,listView);
        showTask.execute();

        progressDialog.dismiss();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(adapterNoNet.getList().get(position).get("url").toString()));
                startActivity(intent);
            }
        });
    }

    private View view1, view2, view3;
    private List<View> viewList;// view数组
    private ViewPager viewPager; // 对应的viewPager

    private ImageView cursor;
    private int bmpw = 0; // 游标宽度
    private int offset = 0;// // 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private TextView t1, t2, t3;// 页卡头标
    private void InitialView(Context th){

        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.layout1, null);
        view2 = inflater.inflate(R.layout.layout2, null);
        view3 = inflater.inflate(R.layout.layout3, null);

        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        listView = (ListView) (view1.findViewById(R.id.list1));

    }

    private void InitTextView() {
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);

        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));
    }

    //添加指示条点击事件
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }
    };
    //初始化指示器位置
    public void initCursorPos() {
        // 初始化动画
        cursor = (ImageView) findViewById(R.id.cursor);
        bmpw = BitmapFactory.decodeResource(getResources(), R.drawable.a)
                .getWidth();// 获取图片宽度

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / viewList.size() - bmpw) / 2;// 计算偏移量

        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置
    }
    //页面改变监听器
    public class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpw;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
    //ViewPager适配器


   //网络加载时最新内容的适配器
    public class TheLastListViewAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private List<Map<String,Object>> list;

        public TheLastListViewAdapter(Context context) {

            this.context = context;
            layoutInflater = layoutInflater.from(context);

        }

        public List getData(){
            return list;
        }

        public void setData(List<Map<String,Object>> data){
            this.list = data;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        public  List<Map<String,Object>> getList() {
            // TODO Auto-generated method stub
            return list;
        }
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = null;
            final ViewHolder viewHolder;
            Log.d("imama2",String.valueOf(position));
            if (convertView == null ) {
                convertView = layoutInflater.inflate(R.layout.itemview, null);
                viewHolder = new ViewHolder();
                viewHolder.header = (TextView)convertView.findViewById(R.id.title);
                viewHolder.content = (TextView)convertView.findViewById(R.id.content);
                //viewHolder.address = (TextView)convertView.findViewById(R.id.textView3);
                viewHolder.img = (ImageView)convertView.findViewById(R.id.ima);

                convertView.setTag(viewHolder);

            }else{

                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.content.setText(list.get(position).get("title").toString());
            JSONObject jsonObject1=new JSONObject();
            DownImage downImage;
            try {
                jsonObject1 =new JSONObject(list.get(position).get("node").toString()) ;
                viewHolder.header.setText(jsonObject1.getString("title"));
                downImage = new DownImage(jsonObject1.getString("avatar_normal"));
                downImage.loadImage(new  DownImage.ImageCallBack() {
                    public void getDrawable(Bitmap bitmap) {
                        // TODO Auto-generated method stub
                        viewHolder.img.setImageBitmap(bitmap);

                    }
                });

            }
            catch (Exception e){e.printStackTrace();}
            // viewHolder.address.setText(list.get(position).get("addr").toString());

            //接口回调的方法，完成图片的读取;
            return convertView;
        }

    }
//无网加载时
    public class TheLastListViewAdapterNoNet extends BaseAdapter{
        private Context context;
        private LayoutInflater layoutInflater;
        private List<Map<String,Object>> list;
        public TheLastListViewAdapterNoNet(Context context) {

            this.context = context;
            layoutInflater = layoutInflater.from(context);

        }

        public List getData(){
            return list;
        }

        public void setData(List<Map<String,Object>> data){
            this.list = data;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        public  List<Map<String,Object>> getList() {
            // TODO Auto-generated method stub
            return list;
        }
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = null;
            final ViewHolder viewHolder;
            if (convertView == null ) {
                convertView = layoutInflater.inflate(R.layout.itemview, null);
                viewHolder = new ViewHolder();
                viewHolder.header = (TextView)convertView.findViewById(R.id.title);
                viewHolder.content = (TextView)convertView.findViewById(R.id.content);
                //viewHolder.address = (TextView)convertView.findViewById(R.id.textView3);
                viewHolder.img = (ImageView)convertView.findViewById(R.id.ima);
                convertView.setTag(viewHolder);
            }else{

                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.content.setText(list.get(position).get("title").toString());
            JSONObject jsonObject1=new JSONObject();
            FileToShow fileToShow=new FileToShow();
            fileToShow.setPosition(position);
            try {
                jsonObject1 =new JSONObject(list.get(position).get("node").toString()) ;
                viewHolder.header.setText(jsonObject1.getString("title"));

                Bitmap bitmap = CacheImage.getBitmapFromMem(String.valueOf(position));
                if (bitmap != null) {
                    viewHolder.img.setImageBitmap(bitmap);

                }
                else {
                fileToShow.getBitmap(new  FileToShow.ImageCallBack() {
                    public void getDrawable(Bitmap bitmap) {
                        // TODO Auto-generated method stub
                        viewHolder.img.setImageBitmap(bitmap);
                    }
                });
                }
            }
            catch (Exception e){e.printStackTrace();}
            // viewHolder.address.setText(list.get(position).get("addr").toString());

            //接口回调的方法，完成图片的读取;
            return convertView;
        }
    }


    //三级策略

}
