package com.softtek.lai.module.laicheng.adapter;

import android.content.Context;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laicheng.model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shelly.xu on 2017/4/10.
 */

public class HistoryAdapter extends BaseAdapter implements Filterable {
    private List<HistoryModel> historyModels=new ArrayList<>();
    Context context;
    private MyFilter mFilter;
    SearchView.SearchAutoComplete txt_search;

    public HistoryAdapter(Context context, List<HistoryModel> historyModels,SearchView.SearchAutoComplete txt_search) {
        this.context = context;
        this.historyModels = historyModels;
        this.txt_search=txt_search;
    }

    @Override
    public int getCount() {
        return historyModels.size();
    }

    @Override
    public Object getItem(int i) {
        return historyModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.visitor_history_item_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HistoryModel historyModel=historyModels.get(position);
        viewHolder.tv_visittime.setText(historyModel.getTime());
        viewHolder.tv_visitor.setText(historyModel.getName());
        viewHolder.tv_phoneNo.setText(historyModel.getPhoneNo());
        viewHolder.tv_age.setText(historyModel.getAge());
        viewHolder.tv_gender.setText(historyModel.getGender());
        viewHolder.tv_height.setText(historyModel.getHeight());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (null == mFilter) {
            mFilter = new MyFilter();
        }
        return mFilter;
    }

    class ViewHolder {
        TextView tv_visittime;
        TextView tv_visitor;
        TextView tv_phoneNo;
        TextView tv_gender;
        TextView tv_age;
        TextView tv_height;

        public ViewHolder(View view) {
            tv_visittime=(TextView)view.findViewById(R.id.tv_visittime);
            tv_visitor=(TextView)view.findViewById(R.id.tv_visitor);
            tv_phoneNo=(TextView)view.findViewById(R.id.tv_phoneNo);
            tv_gender=(TextView)view.findViewById(R.id.tv_gender);
            tv_age=(TextView)view.findViewById(R.id.tv_age);
            tv_height=(TextView)view.findViewById(R.id.tv_height);

        }
    }
    //自定义filter类
    class MyFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            List<String> newValues = new ArrayList<String>();
            String filterString = txt_search.toString().trim()
                    .toLowerCase();

//            // 如果搜索框内容为空，就恢复原始数据
//            if (TextUtils.isEmpty(filterString)) {
//                newValues = mBackData;
//            } else {
//                // 过滤出新数据
//                for (String str : mBackData) {
//                    if (-1 != str.toLowerCase().indexOf(filterString)) {
//                        newValues.add(str);
//                    }
//                }
//            }

            results.values = newValues;
            results.count = newValues.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        }
    }
}
