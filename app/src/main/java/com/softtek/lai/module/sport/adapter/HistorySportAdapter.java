/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.sport.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.act.model.ActlistModel;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class HistorySportAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<HistorySportModel> list;

    private BaseActivity context;
    private IAssistantPresenter assistantPresenter;

    /**
     * 构造函数
     */
    public HistorySportAdapter(BaseActivity context, List<HistorySportModel> list) {
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
            convertView = mInflater.inflate(R.layout.activity_history_sport_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_type = (TextView) convertView.findViewById(R.id.text_type);
            holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.text_during = (TextView) convertView.findViewById(R.id.text_during);
            holder.text_kll = (TextView) convertView.findViewById(R.id.text_kll);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final HistorySportModel historySportModel = list.get(position);

        if ("0".equals(historySportModel.getMType())) {
            holder.text_type.setText("跑步");
        }
        holder.text_value.setText(StringUtil.getDoubleValue(historySportModel.getTotal()));
        holder.text_during.setText(historySportModel.getTimeLength());
        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_type;
        public TextView text_time;
        public TextView text_value;
        public TextView text_during;
        public TextView text_kll;
    }
}



