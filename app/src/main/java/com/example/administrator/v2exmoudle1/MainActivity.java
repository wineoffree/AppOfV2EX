package com.example.administrator.v2exmoudle1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private View view1, view2, view3;
    private List<View> viewList;// view数组
    private ViewPager viewPager; // 对应的viewPager

    private ImageView cursor;
    private int bmpw = 0; // 游标宽度
    private int offset = 0;// // 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private TextView t1, t2, t3;// 页卡头标

    ListView listView;//下拉框
    MyAdapter adapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        InitialView(this);
        initCursorPos();
        InitTextView();
        viewPager.setAdapter(new MyPagerAdapter(viewList));
        viewPager.addOnPageChangeListener(new MyPageChangeListener());



        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("正在下载....");
        adapter = new MyAdapter(this);
        new MyTask().execute(HttpUtil.BASE_URL);
        progressDialog.dismiss();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(adapter.list.get(position).get("url").toString()));
                startActivity(intent);
            }
        });
    }


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
    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mListViews.size();
        }


        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            container.removeView(mListViews.get(position));
        }


        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(mListViews.get(position));

            return mListViews.get(position);
        }
    }

    //完成JSON的异步解析
    public class MyTask extends AsyncTask<String, Integer, List<Map<String,Object>>> {

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

        }

        @Override
        protected List<Map<String, Object>> doInBackground(String... params) {

            List<Map<String,Object>> list ;
            String str=null;
            try {
                str= HttpUtil.readParse(params[0]);
            }
            catch (Exception e){e.printStackTrace();}
            list = HttpUtil.getRequest2List(str);

            return list;
        }

    }
    //添加listview适配器
    public class MyAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private List<Map<String,Object>> list;

        public MyAdapter(Context context) {

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

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
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
            DownImage downImage;
            try {
                jsonObject1 =new JSONObject(list.get(position).get("node").toString()) ;
                viewHolder.header.setText(jsonObject1.getString("title"));
                downImage = new DownImage(jsonObject1.getString("avatar_normal"));
                downImage.loadImage(new DownImage.ImageCallBack() {

                    @Override
                    public void getDrawable(Drawable drawable) {
                        // TODO Auto-generated method stub
                        viewHolder.img.setImageDrawable(drawable);
                    }
                });
            }
            catch (Exception e){e.printStackTrace();}



            // viewHolder.address.setText(list.get(position).get("addr").toString());


            //接口回调的方法，完成图片的读取;




            return convertView;
        }

    }



}
