/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.grade.model.StudentModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jerry.guan on 3/22/2016.
 */
public class WaistAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private List<StudentModel> students;
    private Context context;

    public WaistAdapter(Context context, List<StudentModel> studentModels) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.students = studentModels;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        WaistlineHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.waistline_item, parent, false);
            holder = new WaistlineHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (WaistlineHolder) convertView.getTag();
        }
        StudentModel studentModel = students.get(position);
        holder.tv_order.setText(studentModel.getOrderNum()+"");
        int order = studentModel.getOrderNum();
        if (order == 1 || order == 2 || order == 3) {
            holder.tv_order.setTextColor(Color.parseColor("#FDB02B"));
        }else{
            holder.tv_order.setTextColor(Color.parseColor("#707070"));
        }
        if(studentModel.getIsTest()==1){//如果有复测数据
            holder.ll_content.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.ll_show.setVisibility(View.VISIBLE);
            holder.tv_show.setVisibility(View.GONE);
            holder.tv_wl_totle.setVisibility(View.VISIBLE);
            holder.tv_unit.setVisibility(View.VISIBLE);
            if(order==0){//表示没有录入数据
                //显示短杠
                if(studentModel.getIsMemberOfAssistant()==1){//表示不能点击进入学员详情
                    holder.iv_arrow1.setVisibility(View.INVISIBLE);
                }else{
                    holder.iv_arrow1.setVisibility(View.VISIBLE);
                }
                holder.tv_order.setVisibility(View.INVISIBLE);
                holder.ll_show1.setVisibility(View.VISIBLE);
                holder.ll_total_show.setVisibility(View.VISIBLE);
                holder.no_show.setVisibility(View.VISIBLE);
                //隐藏内容
                holder.ll_total_show.setVisibility(View.GONE);
                holder.ll_show.setVisibility(View.GONE);
            }else{//有录入数据正常显示数据
                if(studentModel.getIsMemberOfAssistant()==1){//表示不能点击进入学员详情
                    holder.iv_arrow.setVisibility(View.INVISIBLE);
                }else{
                    holder.iv_arrow.setVisibility(View.VISIBLE);
                }
                holder.tv_order.setVisibility(View.VISIBLE);
                holder.ll_show1.setVisibility(View.GONE);
                holder.ll_total_show.setVisibility(View.GONE);
                holder.no_show.setVisibility(View.GONE);
                //隐藏内容
                holder.ll_total_show.setVisibility(View.VISIBLE);
                holder.ll_show.setVisibility(View.VISIBLE);

            }

        }else{//没有复测数据，则无法
            holder.no_show.setVisibility(View.GONE);
            holder.ll_total_show.setVisibility(View.VISIBLE);
            holder.ll_show1.setVisibility(View.GONE);

            holder.tv_order.setVisibility(View.INVISIBLE);
            holder.iv_arrow.setVisibility(View.INVISIBLE);
            holder.ll_show.setVisibility(View.GONE);
            holder.tv_show.setVisibility(View.VISIBLE);
            holder.tv_wl_totle.setVisibility(View.INVISIBLE);
            holder.tv_unit.setVisibility(View.INVISIBLE);
            holder.ll_content.setBackgroundColor(Color.parseColor("#F9F9F9"));
        }
        holder.tv_wl_before.setText("前 " + studentModel.getWaistlinebefore()+ "cm" );
        holder.tv_wl_after.setText("后 " + studentModel.getWaistlineAfter()+ "cm");
        holder.tv_wl_totle.setText(studentModel.getLossline());
        holder.tv_name.setText(studentModel.getUserName());
        if (StringUtils.isNotEmpty(studentModel.getPhoto())) {
            Picasso.with(context).load(AddressManager.get("photoHost") + studentModel.getPhoto()).fit().placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(holder.civ_header_image);
        }
        return convertView;


    }

    static class WaistlineHolder {
        TextView tv_order;
        TextView tv_name;
        TextView tv_wl_before;
        TextView tv_wl_after;
        TextView tv_wl_totle;
        CircleImageView civ_header_image;
        ImageView iv_arrow,iv_arrow1;
        LinearLayout ll_content,ll_show,no_show,ll_total_show,ll_show1;
        TextView tv_show,tv_unit;

        public WaistlineHolder(View view) {
            tv_order = (TextView) view.findViewById(R.id.tv_order);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_wl_before = (TextView) view.findViewById(R.id.tv_wl_before);
            tv_wl_after = (TextView) view.findViewById(R.id.tv_wl_after);
            tv_wl_totle = (TextView) view.findViewById(R.id.tv_wl_totle);
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);
            iv_arrow= (ImageView) view.findViewById(R.id.iv_arrow);
            ll_content= (LinearLayout) view.findViewById(R.id.ll_content);
            ll_show= (LinearLayout) view.findViewById(R.id.ll_show);
            tv_show= (TextView) view.findViewById(R.id.tv_show);
            tv_unit= (TextView) view.findViewById(R.id.tv_unit);
            no_show= (LinearLayout) view.findViewById(R.id.no_show);
            ll_total_show= (LinearLayout) view.findViewById(R.id.ll_total_show);
            ll_show1= (LinearLayout) view.findViewById(R.id.ll_show1);
            iv_arrow1= (ImageView) view.findViewById(R.id.iv_arrow1);
        }
    }

}
