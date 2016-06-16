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
import android.widget.ImageView;
import android.widget.TextView;
import com.softtek.lai.R;
import com.softtek.lai.module.grade.model.DynamicInfoModel;
import com.softtek.lai.utils.DateUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jerry.guan on 3/22/2016.
 */
public class DynamicAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    Context context;
    private List<DynamicInfoModel> dynamicInfoList;

    public DynamicAdapter(Context context, List<DynamicInfoModel> dynamicInfos) {
        this.context=context;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dynamic_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DynamicInfoModel info = dynamicInfoList.get(position);
        holder.tv_date.setText(DateUtil.getInstance().convertDateStr(info.getCreateDate(),"yyyy年MM月dd日"));
        holder.tv_content.setText(info.getDyContent());
        if("2".equals(info.getDyType())){
            holder.iv_icon.setVisibility(View.GONE);
            holder.iv_head.setVisibility(View.VISIBLE);
            if(StringUtils.isNotEmpty(info.getPhoto())){
                Picasso.with(context).load(AddressManager.get("photoHost")+info.getPhoto()).fit().placeholder(R.drawable.img_default).error(R.drawable.img_default).into(holder.iv_head);
            }else{
                Picasso.with(context).load(R.drawable.img_default).into(holder.iv_head);
            }
        }else {
            holder.iv_icon.setVisibility(View.VISIBLE);
            holder.iv_head.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder {

        TextView tv_date;
        TextView tv_content;
        ImageView iv_icon;
        CircleImageView iv_head;


        public ViewHolder(View view) {
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            iv_icon= (ImageView) view.findViewById(R.id.iv_icon);
            iv_head= (CircleImageView) view.findViewById(R.id.iv_head);
        }

    }
}
