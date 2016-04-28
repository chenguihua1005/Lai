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
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.grade.model.StudentModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

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
        holder.tv_order.setText(studentModel.getOrderNum()+"");
        int order = studentModel.getOrderNum();
        if (order == 1 || order == 2 || order == 3) {
            holder.tv_order.setTextColor(Color.parseColor("#FDB02B"));
        }
        holder.tv_py_before.setText("前 " + studentModel.getLossBefor() + "%");
        holder.tv_py_after.setText("后 " + studentModel.getLossAfter() + "%");
        holder.tv_name.setText(studentModel.getUserName());
        holder.tv_physical.setText(studentModel.getPysical());
        if (!"".equals(studentModel.getPhoto()) && null != studentModel.getPhoto()) {
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

        public PhysicalHolder(View view) {
            tv_order = (TextView) view.findViewById(R.id.tv_order);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_physical = (TextView) view.findViewById(R.id.tv_pysical);
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);
            tv_py_after = (TextView) view.findViewById(R.id.tv_py_after);
            tv_py_before = (TextView) view.findViewById(R.id.tv_py_before);
        }
    }
}