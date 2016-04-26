/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.softtek.lai.R;
import com.softtek.lai.module.grade.model.DynamicInfoModel;
import com.softtek.lai.utils.DateUtil;

import java.util.List;

/**
 * Created by jerry.guan on 3/22/2016.
 */
public class DynamicAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<DynamicInfoModel> dynamicInfoList;

    public DynamicAdapter(Context context, List<DynamicInfoModel> dynamicInfos) {
        inflater = LayoutInflater.from(context);
        this.dynamicInfoList = dynamicInfos;
    }

    @Override
    public int getCount() {
        return dynamicInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return dynamicInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dynamic_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DynamicInfoModel info = dynamicInfoList.get(position);
        holder.tv_date.setText(DateUtil.getInstance().convertDateStr(info.getCreateDate(),DateUtil.yyyy_MM_dd));
        holder.tv_content.setText(info.getDyContent());
        return convertView;
    }

    static class ViewHolder {

        TextView tv_date;
        TextView tv_content;

        public ViewHolder(View view) {
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
        }

    }
}
