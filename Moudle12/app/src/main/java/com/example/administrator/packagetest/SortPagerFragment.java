package com.example.administrator.packagetest;

import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/20.
 */
public class SortPagerFragment extends Fragment {
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    String []strings={"全部","创意","好玩","apple","酷工作","交易","城市","问与答","最热","技术","R2",};
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_sort_pager,container,false);
        DragListView sortListView=(DragListView)view.findViewById(R.id.sortlistview);
        view.setOnKeyListener(backlistener);
        ArrayList<String> arrayList=new ArrayList<String>();
        for(int i=0;i<11;i++){
            arrayList.add(strings[i]);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,arrayList);
        sortListView.setAdapter(adapter);
        Button sure=(Button)view.findViewById(R.id.sortsure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("qiqi","sadas");
                getFragmentManager().beginTransaction()
                        .remove(SortPagerFragment.this)
                        .commit();
            }
        });
        Button cancel=(Button)view.findViewById(R.id.sortcancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("qiqi","sadas");
                getFragmentManager().beginTransaction()
                        .remove(SortPagerFragment.this)
                        .commit();
            }
        });
        return view;
    }

    private View.OnKeyListener backlistener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                if (i == KeyEvent.KEYCODE_BACK) {  //表示按返回键 时的操作
                    Log.d("qiqi","sadassadsadsdasdasdsadasdasdas");
                    getFragmentManager().beginTransaction()
                            .remove(SortPagerFragment.this)
                            .commit();
                    return true;    //已处理
                }
            }
            return true;
        }
    };

}