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
public class FatAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private List<StudentModel> students;
    private Context context;

    public FatAdapter(Context context, List<StudentModel> studentModels) {
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

        PhysicalHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.physical_item, parent, false);
            holder = new PhysicalHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PhysicalHolder) convertView.getTag();
        }
        StudentModel studentModel = students.get(position);
        int order = studentModel.getOrderNum();
        holder.tv_order.setText(order+"");
        if (order == 1 || order == 2 || order == 3) {
            holder.tv_order.setTextColor(Color.parseColor("#FDB02B"));
        }else if(order==0){
            holder.tv_order.setVisibility(View.INVISIBLE);
        }else{
            holder.tv_order.setTextColor(Color.parseColor("#707070"));
        }
        if(studentModel.getIsTest()==1){//如果有复测数据
            if(studentModel.getIsMemberOfAssistant()==1){//表示不能点击进入学员详情
                holder.iv_arrow.setVisibility(View.INVISIBLE);
            }else{
                holder.iv_arrow.setVisibility(View.VISIBLE);
            }
            if(order==0){//表示没有录入数据
                //显示短杠
                holder.ll_show1.setVisibility(View.VISIBLE);
                holder.ll_total_show.setVisibility(View.VISIBLE);
                holder.no_show.setVisibility(View.VISIBLE);
                //隐藏内容
                holder.ll_total_show.setVisibility(View.GONE);
                holder.ll_show.setVisibility(View.GONE);
            }else{//有录入数据正常显示数据
                holder.tv_py_before.setText("前 " + studentModel.getLossBefore()+ "%" );
                holder.tv_py_after.setText("后 " + studentModel.getLossAfter()+ "%" );
                holder.tv_physical.setText(studentModel.getPysical());
            }

        }else{//没有复测数据，则无法
            holder.iv_arrow.setVisibility(View.INVISIBLE);
            holder.ll_show.setVisibility(View.GONE);
            holder.tv_show.setVisibility(View.VISIBLE);
            holder.tv_physical.setVisibility(View.INVISIBLE);
            holder.tv_unit.setVisibility(View.INVISIBLE);
            holder.ll_content.setBackgroundColor(Color.parseColor("#F9F9F9"));
        }
        holder.tv_name.setText(studentModel.getUserName());
        if (StringUtils.isNotEmpty(studentModel.getPhoto())) {
            Picasso.with(context).load(AddressManager.get("photoHost") + studentModel.getPhoto()).fit().placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(holder.civ_header_image);
        }
        return convertView;


    }

    static class PhysicalHolder {
        TextView tv_order;
        TextView tv_name;
        TextView tv_physical;
        CircleImageView civ_header_image;
        TextView tv_py_before;
        TextView tv_py_after;
        ImageView iv_arrow;
        LinearLayout ll_content,ll_show,no_show,ll_total_show,ll_show1;
        TextView tv_show,tv_unit;

        public PhysicalHolder(View view) {
            tv_order = (TextView) view.findViewById(R.id.tv_order);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_physical = (TextView) view.findViewById(R.id.tv_pysical);
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);
            tv_py_after = (TextView) view.findViewById(R.id.tv_py_after);
            tv_py_before = (TextView) view.findViewById(R.id.tv_py_before);
            iv_arrow= (ImageView) view.findViewById(R.id.iv_arrow);
            ll_content= (LinearLayout) view.findViewById(R.id.ll_content);
            ll_show= (LinearLayout) view.findViewById(R.id.ll_show);
            tv_show= (TextView) view.findViewById(R.id.tv_show);
            tv_unit= (TextView) view.findViewById(R.id.tv_unit);
            no_show= (LinearLayout) view.findViewById(R.id.no_show);
            ll_total_show= (LinearLayout) view.findViewById(R.id.ll_total_show);
            ll_show1= (LinearLayout) view.findViewById(R.id.ll_show1);
        }
    }
}
