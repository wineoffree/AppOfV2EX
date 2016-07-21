package com.example.administrator.packagetest.listadapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.administrator.packagetest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/14.
 */
public class SearchItemAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<String> list;
    private List<Map<String,String>> lists;
    private MyFilter mFilter;
    public  List<String>newResultList= new ArrayList<String>(); ;
    public SearchItemAdapter(Context context) {
        this.context = context;
        layoutInflater = layoutInflater.from(context);
    }

    public void setList(List<Map<String,String>> lists) {
       this.lists=lists;
    }

    public void setData(List<String> data) {
        this.list = data;
    }

    @Override
    public int getCount() {
        return newResultList.size();
    }

    @Override
    public Object getItem(int position) {
        return newResultList.get(position);
    }

    public List<Map<String,String>> getList() {
        return lists;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.layout_search_result_item, null);
            viewHolder.content = (TextView) convertView.findViewById(R.id.searchcontent);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.content.setText(newResultList.get(position));
       // viewHolder.newUrl = list.get(position).get("newUrl").toString();
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new MyFilter();
        }
        return mFilter;
    }

    // 自定义Filter类
    class MyFilter extends Filter {
        @Override
        // 该方法在子线程中执行
        // 自定义过滤规则
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            List<String> newResults = new ArrayList<String>();
            String filterString = constraint.toString().trim()
                    .toLowerCase();

            // 如果搜索框内容为空，就恢复原始数据
            if (TextUtils.isEmpty(filterString)) {
                newResults = list;
            } else {
                // 过滤出新数据
                for (String str : list) {
                    if (-1 != str.toLowerCase().indexOf(filterString)) {
                        newResults.add(str);
                    }
                }
            }

            results.values = newResults;
            results.count = newResults.size();

            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            newResultList = (List<String>) results.values;

            if (results.count > 0) {
                notifyDataSetChanged();  // 通知数据发生了改变
            } else {
                notifyDataSetInvalidated(); // 通知数据失效
            }
        }
    }

}