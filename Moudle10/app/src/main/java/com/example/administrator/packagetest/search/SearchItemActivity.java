package com.example.administrator.packagetest.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.administrator.packagetest.Date;
import com.example.administrator.packagetest.R;
import com.example.administrator.packagetest.SecondActivity;
import com.example.administrator.packagetest.listadapter.SearchItemAdapter;

public class SearchItemActivity extends AppCompatActivity {
    //listview
    ListView listView;
    //listview适配器
    SearchItemAdapter searchItemAdapter=null;

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);
         listView=(ListView)findViewById(R.id.resultlist);
          searchView=(SearchView)findViewById(R.id.searchitems);
        searchItemAdapter = new SearchItemAdapter(this);
        SearchItemTask searchItemTask = new SearchItemTask(searchItemAdapter,listView,SearchItemActivity.this);
        searchItemTask.execute();
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint("输入帖子类型或名称");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    listView.clearTextFilter();
                }
                else {//根据输入过滤
                    listView.setFilterText(newText);

                }
                return true;
            }
        });
        listView.setOnItemClickListener(new MyListViewClicklistener());

    }
    //listview items监听器
    class MyListViewClicklistener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Log.d("haha","qweqwrewrwe");
            Date date=new Date(searchItemAdapter.getList().get(position).get("newUrl"),
                    searchItemAdapter.getList().get(position).get("name"),
                    searchItemAdapter.getList().get(position).get("type"),
                    searchItemAdapter.getList().get(position).get("showId"),
                    searchItemAdapter.getList().get(position).get("time"),
                    searchItemAdapter.getList().get(position).get("content"),
                    0,
                    searchItemAdapter.getList().get(position).get("imaUrl"));
            Bundle dates=new Bundle();
            dates.putSerializable("dates",date);
            Intent intent=new Intent(SearchItemActivity.this,SecondActivity.class);
            intent.putExtras(dates);
            startActivity(intent);
        }
    }
}
