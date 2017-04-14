package com.softtek.lai.module.laicheng.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laicheng.model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.util.Util;

/**
 * Created by shelly.xu on 2017/4/10.
 */

public class HistoryAdapter extends BaseAdapter implements Filterable {
    private List<HistoryModel> historyModels = new ArrayList<>();//初始数据
    private List<HistoryModel> historyNewModels = new ArrayList<>();//变化数据
    Context context;
    private MyFilter mFilter;
    EditText txt_search;

    public HistoryAdapter(Context context, List<HistoryModel> historyModels, EditText txt_search) {
        this.context = context;
        this.historyModels.addAll(historyModels);
        this.historyNewModels.addAll(historyModels);
        this.txt_search = txt_search;
    }

    @Override
    public int getCount() {
        return historyNewModels.size();
    }

    @Override
    public Object getItem(int i) {
        return historyNewModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.visitor_history_item_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final HistoryModel historyModel = historyNewModels.get(position);
        viewHolder.tv_visittime.setText(historyModel.getMeasuredTime());
        viewHolder.tv_visitor.setText(historyModel.getVisitor().getName());
        viewHolder.tv_phoneNo.setText(historyModel.getVisitor().getPhoneNo());
        viewHolder.tv_age.setText(historyModel.getVisitor().getAge());
        viewHolder.tv_gender.setText(historyModel.getVisitor().getGender());
        viewHolder.tv_height.setText(historyModel.getVisitor().getHeight());
        viewHolder.ll_item_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.toastMsg("点击了"+historyNewModels.get(position).getVisitor().getName());
            }
        });
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
        LinearLayout ll_item_click;
        public ViewHolder(View view) {
            tv_visittime = (TextView) view.findViewById(R.id.tv_visittime);
            tv_visitor = (TextView) view.findViewById(R.id.tv_visitor);
            tv_phoneNo = (TextView) view.findViewById(R.id.tv_phoneNo);
            tv_gender = (TextView) view.findViewById(R.id.tv_gender);
            tv_age = (TextView) view.findViewById(R.id.tv_age);
            tv_height = (TextView) view.findViewById(R.id.tv_height);
            ll_item_click= (LinearLayout) view.findViewById(R.id.ll_item_click);

        }
    }

    //自定义filter类
    class MyFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            String filterString = txt_search.getText().toString().trim()
                    .toLowerCase();//edittext
            Log.i("filter", filterString);
            // 如果搜索框内容为空，就恢复原始数据
            if (TextUtils.isEmpty(filterString)) {
                historyNewModels.clear();
                historyNewModels.addAll(historyModels);
            } else {
                // 过滤出新数据
                historyNewModels.clear();
                for (HistoryModel str : historyModels) {
                    if (-1 != str.getVisitor().getName().toLowerCase().indexOf(filterString) || -1 != str.getVisitor().getPhoneNo().indexOf(filterString)) {
                        historyNewModels.add(str);
                    }
                }
            }

            results.values = historyNewModels;
            results.count = historyNewModels.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults.count > 0) {
                notifyDataSetChanged();  // 通知数据发生了改变
            } else {
                notifyDataSetInvalidated(); // 通知数据失效

            }
        }
    }

}
