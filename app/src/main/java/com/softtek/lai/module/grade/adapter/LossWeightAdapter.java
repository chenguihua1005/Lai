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
public class LossWeightAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private List<StudentModel> students;
    private Context context;

    private int flag;

    public LossWeightAdapter(Context context, List<StudentModel> studentModels, int flag) {
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.students = studentModels;
        this.flag = flag;
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
        switch (flag) {
            case 0:
                return getLossWeightView(position, convertView, parent);
            case 1:
                return getLossWeightPerView(position, convertView, parent);
            case 2:
                return getPhysicalView(position, convertView, parent);
            case 3:
                return getWaistlineView(position, convertView, parent);
            default:
                return getLossWeightView(position, convertView, parent);
        }

    }

    private View getLossWeightView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.loss_weight_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        StudentModel studentModel = students.get(position);
        holder.tv_order.setText(position + 1 + "");
        if ((position + 1) < 4) {
            holder.tv_order.setTextColor(Color.parseColor("#FDB02B"));
        }
        holder.tv_name.setText(studentModel.getUserName());
        holder.tv_lw_before.setText("前 " + studentModel.getLossBefor() + "kg");
        holder.tv_lw_after.setText("后 " + studentModel.getLossAfter() + "kg");
        holder.tv_lw_totle.setText(studentModel.getLossWeght());
        if(!"".equals(studentModel.getPhoto())&&null!=studentModel.getPhoto()){
            Picasso.with(context).load(AddressManager.get("photoHost")+studentModel.getPhoto()).placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(holder.civ_header_image);
        }
        return convertView;
    }

    private View getLossWeightPerView(int position, View convertView, ViewGroup parent) {
        LossWeightPerHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.loss_weight_per_item, parent, false);
            holder = new LossWeightPerHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (LossWeightPerHolder) convertView.getTag();
        }
        StudentModel studentModel = students.get(position);
        holder.tv_order.setText(position + 1 + "");
        if ((position + 1) < 4) {
            holder.tv_order.setTextColor(Color.parseColor("#FDB02B"));
        }
        holder.tv_lw_before.setText("前 " + studentModel.getLossBefor() + "kg");
        holder.tv_lw_after.setText("后 " + studentModel.getLossAfter() + "kg");
        holder.tv_name.setText(studentModel.getUserName());
        holder.tv_lw_per.setText(studentModel.getLossPercent());
        if(!"".equals(studentModel.getPhoto())&&null!=studentModel.getPhoto()){
            Picasso.with(context).load(AddressManager.get("photoHost")+studentModel.getPhoto()).placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(holder.civ_header_image);
        }
        return convertView;
    }

    private View getPhysicalView(int position, View convertView, ViewGroup parent) {
        PhysicalHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.physical_item, parent, false);
            holder = new PhysicalHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (PhysicalHolder) convertView.getTag();
        }
        StudentModel studentModel = students.get(position);
        holder.tv_order.setText(position + 1 + "");
        if ((position + 1) < 4) {
            holder.tv_order.setTextColor(Color.parseColor("#FDB02B"));
        }
        holder.tv_py_before.setText("前 " + studentModel.getLossBefor() + "kg");
        holder.tv_py_after.setText("后 " + studentModel.getLossAfter() + "kg");
        holder.tv_name.setText(studentModel.getUserName());
        holder.tv_physical.setText(studentModel.getPysical());
        if(!"".equals(studentModel.getPhoto())&&null!=studentModel.getPhoto()){
            Picasso.with(context).load(AddressManager.get("photoHost")+studentModel.getPhoto()).placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(holder.civ_header_image);
        }
        return convertView;
    }

    private View getWaistlineView(int position, View convertView, ViewGroup parent) {
        WaistlineHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.waistline_item, parent, false);
            holder = new WaistlineHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (WaistlineHolder) convertView.getTag();
        }
        StudentModel studentModel = students.get(position);
        holder.tv_order.setText(position + 1 + "");
        if ((position + 1) < 4) {
            holder.tv_order.setTextColor(Color.parseColor("#FDB02B"));
        }
        holder.tv_name.setText(studentModel.getUserName());
        holder.tv_wl_before.setText("前 " + studentModel.getWaistlinebefor() + "cm");
        holder.tv_wl_after.setText("后 " + studentModel.getWaistlineAfter() + "cm");
        holder.tv_wl_totle.setText("00");
        if(!"".equals(studentModel.getPhoto())&&null!=studentModel.getPhoto()){
            Picasso.with(context).load(AddressManager.get("photoHost")+studentModel.getPhoto()).placeholder(R.drawable.img_default)
                    .error(R.drawable.img_default).into(holder.civ_header_image);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tv_order;
        TextView tv_name;
        TextView tv_lw_before;
        TextView tv_lw_after;
        TextView tv_lw_totle;
        CircleImageView civ_header_image;

        public ViewHolder(View view) {
            tv_order = (TextView) view.findViewById(R.id.tv_order);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_lw_before = (TextView) view.findViewById(R.id.tv_lw_before);
            tv_lw_after = (TextView) view.findViewById(R.id.tv_lw_after);
            tv_lw_totle = (TextView) view.findViewById(R.id.tv_lw_totle);
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);

        }
    }

    static class LossWeightPerHolder {
        TextView tv_order;
        TextView tv_name;
        TextView tv_lw_per;
        CircleImageView civ_header_image;
        TextView tv_lw_before;
        TextView tv_lw_after;

        public LossWeightPerHolder(View view) {
            tv_order = (TextView) view.findViewById(R.id.tv_order);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_lw_per = (TextView) view.findViewById(R.id.tv_lw_per);
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);
            tv_lw_before = (TextView) view.findViewById(R.id.tv_lw_before);
            tv_lw_after = (TextView) view.findViewById(R.id.tv_lw_after);
        }
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

    static class WaistlineHolder {
        TextView tv_order;
        TextView tv_name;
        TextView tv_wl_before;
        TextView tv_wl_after;
        TextView tv_wl_totle;
        CircleImageView civ_header_image;

        public WaistlineHolder(View view) {
            tv_order = (TextView) view.findViewById(R.id.tv_order);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_wl_before = (TextView) view.findViewById(R.id.tv_wl_before);
            tv_wl_after = (TextView) view.findViewById(R.id.tv_wl_after);
            tv_wl_totle = (TextView) view.findViewById(R.id.tv_wl_totle);
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);
        }
    }

}
