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
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jerry.guan on 3/22/2016.
 */
public class LossWeightAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private List<StudentModel> students;
    private Context context;


    public LossWeightAdapter(Context context, List<StudentModel> studentModels) {
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
        ViewHolder holder;
        StudentModel studentModel = students.get(position);
        int order = studentModel.getOrderNum();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.loss_weight_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (order == 1 || order == 2 || order == 3) {
            holder.tv_order.setTextColor(Color.parseColor("#FDB02B"));
        }else{
            holder.tv_order.setTextColor(Color.parseColor("#707070"));
        }
        holder.tv_order.setText(order+"");
        if(studentModel.getIsTest()==1){//若有复测
            holder.tv_order.setVisibility(View.VISIBLE);
            holder.ll_content.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.ll_show.setVisibility(View.VISIBLE);
            holder.tv_show.setVisibility(View.GONE);
            holder.tv_lw_totle.setVisibility(View.VISIBLE);
            holder.tv_unit.setVisibility(View.VISIBLE);

        }else{//没有复测
            holder.tv_order.setVisibility(View.INVISIBLE);
            holder.ll_content.setBackgroundColor(Color.parseColor("#F9F9F9"));
            holder.ll_show.setVisibility(View.GONE);
            holder.tv_show.setVisibility(View.VISIBLE);
            holder.tv_lw_totle.setVisibility(View.INVISIBLE);
            holder.tv_unit.setVisibility(View.INVISIBLE);
        }
        holder.tv_name.setText(studentModel.getUserName());
        holder.tv_lw_before.setText("前 " + StringUtil.convertValue7(studentModel.getLossBefore(),"斤"));
        holder.tv_lw_after.setText("后 " + StringUtil.convertValue7(studentModel.getLossAfter(),"斤"));
        holder.tv_lw_totle.setText(studentModel.getLossWeght());
        if(studentModel.getIsMemberOfAssistant()==1){//表示不能点击进入学员详情
            holder.iv_arrow.setVisibility(View.INVISIBLE);
        }else{
            holder.iv_arrow.setVisibility(View.VISIBLE);
        }
        if (StringUtils.isNotEmpty(studentModel.getPhoto())) {
            Picasso.with(context).load(AddressManager.get("photoHost") + studentModel.getPhoto()).fit().placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(holder.civ_header_image);
        }else {
            Picasso.with(context).load(R.drawable.img_default).into(holder.civ_header_image);
        }
        return convertView;

    }


    static class ViewHolder {
        TextView tv_order;
        TextView tv_name;
        TextView tv_lw_before;
        TextView tv_lw_after;
        TextView tv_lw_totle;
        ImageView iv_arrow;
        CircleImageView civ_header_image;
        LinearLayout ll_content,ll_show;
        TextView tv_show,tv_unit;

        public ViewHolder(View view) {
            tv_order = (TextView) view.findViewById(R.id.tv_order);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_lw_before = (TextView) view.findViewById(R.id.tv_lw_before);
            tv_lw_after = (TextView) view.findViewById(R.id.tv_lw_after);
            tv_lw_totle = (TextView) view.findViewById(R.id.tv_lw_totle);
            iv_arrow= (ImageView) view.findViewById(R.id.iv_arrow);
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);
            ll_content= (LinearLayout) view.findViewById(R.id.ll_content);
            ll_show= (LinearLayout) view.findViewById(R.id.ll_show);
            tv_show= (TextView) view.findViewById(R.id.tv_show);
            tv_unit= (TextView) view.findViewById(R.id.tv_unit);

        }
    }

}
