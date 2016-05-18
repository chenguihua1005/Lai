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
public class LossWeightPerAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private List<StudentModel> students;
    private Context context;

    public LossWeightPerAdapter(Context context, List<StudentModel> studentModels) {
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

        LossWeightPerHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.loss_weight_per_item, parent, false);
            holder = new LossWeightPerHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (LossWeightPerHolder) convertView.getTag();
        }
        StudentModel studentModel = students.get(position);
        int order = studentModel.getOrderNum();
        holder.tv_order.setText(order + "");
        if (order == 1 || order == 2 || order == 3) {
            holder.tv_order.setTextColor(Color.parseColor("#FDB02B"));
        }else if(order==0){
            holder.tv_order.setVisibility(View.INVISIBLE);
            holder.ll_content.setBackgroundColor(Color.parseColor("#F9F9F9"));
            holder.ll_show.setVisibility(View.GONE);
            holder.tv_show.setVisibility(View.VISIBLE);
            holder.tv_lw_per.setVisibility(View.INVISIBLE);
            holder.tv_unit.setVisibility(View.INVISIBLE);
        }else{
            holder.tv_order.setTextColor(Color.parseColor("#707070"));
        }
        holder.tv_lw_before.setText("前 " + studentModel.getLossBefore() + "斤");
        holder.tv_lw_after.setText("后 " + studentModel.getLossAfter() + "斤");
        holder.tv_name.setText(studentModel.getUserName());
        holder.tv_lw_per.setText(studentModel.getLossPercent());
        if(studentModel.getIsMemberOfAssistant()==1||studentModel.getIsTest()==0){//表示不能点击进入学员详情
            holder.iv_arrow.setVisibility(View.INVISIBLE);
        }else{
            holder.iv_arrow.setVisibility(View.VISIBLE);
        }
        if (StringUtils.isNotEmpty(studentModel.getPhoto())) {
            Picasso.with(context).load(AddressManager.get("photoHost") + studentModel.getPhoto()).fit().placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(holder.civ_header_image);
        }
        return convertView;


    }

    static class LossWeightPerHolder {
        TextView tv_order;
        TextView tv_name;
        TextView tv_lw_per;
        CircleImageView civ_header_image;
        TextView tv_lw_before;
        TextView tv_lw_after;
        ImageView iv_arrow;
        LinearLayout ll_content,ll_show;
        TextView tv_show,tv_unit;

        public LossWeightPerHolder(View view) {
            tv_order = (TextView) view.findViewById(R.id.tv_order);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_lw_per = (TextView) view.findViewById(R.id.tv_lw_per);
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);
            tv_lw_before = (TextView) view.findViewById(R.id.tv_lw_before);
            tv_lw_after = (TextView) view.findViewById(R.id.tv_lw_after);
            iv_arrow= (ImageView) view.findViewById(R.id.iv_arrow);
            ll_content= (LinearLayout) view.findViewById(R.id.ll_content);
            ll_show= (LinearLayout) view.findViewById(R.id.ll_show);
            tv_show= (TextView) view.findViewById(R.id.tv_show);
            tv_unit= (TextView) view.findViewById(R.id.tv_unit);
        }
    }

}
