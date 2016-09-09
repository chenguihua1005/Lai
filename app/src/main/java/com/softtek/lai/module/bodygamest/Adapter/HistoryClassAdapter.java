/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygamest.model.HistoryClassModel;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.utils.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class HistoryClassAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<HistoryClassModel> list;
    private Context context;
    private IAssistantPresenter assistantPresenter;

    /**
     * 构造函数
     */
    public HistoryClassAdapter(Context context, List<HistoryClassModel> list) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        Log.e("jarvis", list.toString());
    }

    @Override
    public int getCount() {
        return list.size();//返回数组的长度
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 书中详细解释该方法
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.history_class_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/

            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            holder.text1 = (TextView) convertView.findViewById(R.id.text1);
            holder.text2 = (TextView) convertView.findViewById(R.id.text2);
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        HistoryClassModel historyClassModel = list.get(position);
        holder.text_value.setText(StringUtil.getFloatValue(historyClassModel.getLoseWeight()));
        holder.text_name.setText(historyClassModel.getClassName());

        String start = historyClassModel.getStartDate();
        String end = historyClassModel.getEndDate();

        String start_time = "";
        String end_time = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
        try {
            Date start_date = sdf.parse(start);
            Date end_date = sdf.parse(end);
            start_time = format.format(start_date);
            end_time = format.format(end_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.text_time.setText(start_time + " - " + end_time);

        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_name;
        public TextView text_time;
        public TextView text1;
        public TextView text2;
        public TextView text_value;
    }
}



