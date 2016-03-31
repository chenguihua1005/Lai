/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.retest.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.softtek.lai.R;
import com.softtek.lai.module.retest.model.StudentModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lareina.qiao on 3/23/2016.
 */
public class QueryAdapter extends BaseAdapter {
    private List<StudentModel> studentModelList;

    private Context context;

    public QueryAdapter(Context context, List<StudentModel> studentModelList) {
        this.studentModelList = studentModelList;
        this.context = context;

    }


    public void updateData(List<StudentModel> studentModelList) {
        this.studentModelList = studentModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return studentModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_retest_query, null);
            holder = new Holder();
            holder.iv_head = (CircleImageView) convertView.findViewById(R.id.iv_head);
            holder.tv_retest_nick = (TextView) convertView.findViewById(R.id.tv_retest_nick);
            holder.tv_retest_phone = (TextView) convertView.findViewById(R.id.tv_retest_phone);
            holder.tv_retest_classdate = (TextView) convertView.findViewById(R.id.tv_retest_classdate);
            holder.tv_retest_classweek = (TextView) convertView.findViewById(R.id.tv_retest_classweek);
            holder.tv_retest_type = (TextView) convertView.findViewById(R.id.tv_retest_type);
            holder.tv_month = (TextView) convertView.findViewById(R.id.iv_month);
            holder.tv_month1 = (TextView) convertView.findViewById(R.id.iv_month1);
            holder.tv_date = (TextView) convertView.findViewById(R.id.iv_date);
            holder.tv_date1 = (TextView) convertView.findViewById(R.id.iv_date1);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        StudentModel studentModel = studentModelList.get(position);
        if (!TextUtils.isEmpty(studentModel.getPhoto())) {
            Picasso.with(context).load(studentModel.getPhoto()).placeholder(R.drawable.lufei).error(R.drawable.lufei).into(holder.iv_head);
        } else {
            Picasso.with(context).load("www").placeholder(R.drawable.lufei).error(R.drawable.lufei).into(holder.iv_head);
        }
//        holder.iv_head.setImageDrawable();
        holder.tv_retest_nick.setText(studentModel.getUserName());
        holder.tv_retest_phone.setText(studentModel.getMobile());
//        Log.i(">>>>>>>>>>>>","<<<<<<<<<<"+tomonth((studentModel.getStartDate().substring(5,7))));
        holder.tv_retest_classdate.setText(tomonth((studentModel.getStartDate().substring(5, 7))));
        holder.tv_retest_classweek.setText(studentModel.getWeekth() + "");
        String status = "".equals(studentModel.getAMStatus()) ? "录入" : "审核";
        if (status == "录入") {
            holder.tv_retest_type.setText("录入");

//            holder.iv_type_ls.setImageResource(R.drawable.shenhe);


            holder.tv_retest_type.setTextColor(context.getResources().getColor(R.color.green));
        } else {
            holder.tv_retest_type.setText("审核");
//            holder.iv_type_ls.setImageResource(R.drawable.shenhe);
            holder.tv_retest_type.setTextColor(context.getResources().getColor(R.color.orange));
        }
        holder.tv_month.setText(studentModel.getCurrStart().substring(6, 7));
        holder.tv_month1.setText(studentModel.getCurrEnd().substring(6, 7));
        holder.tv_date.setText(studentModel.getCurrStart().substring(8, 10));
        holder.tv_date1.setText(studentModel.getCurrEnd().substring(8, 10));
        return convertView;
    }

    class Holder {
        CircleImageView iv_head;
        TextView tv_retest_nick;
        TextView tv_retest_phone;
        TextView tv_retest_classdate;
        TextView tv_retest_classweek;
        TextView tv_retest_type;
        ImageView iv_type_ls;
        TextView tv_month;
        TextView tv_month1;
        TextView tv_date;
        TextView tv_date1;
    }

    public String tomonth(String month) {

        if (month.equals("01")) {
            month = "一月班";
        } else if (month.equals("02")) {
            month = "二月班";
        } else if (month.equals("03")) {
            month = "三月班";
        } else if (month.equals("04")) {
            month = "四月班";

        } else if (month.equals("05")) {
            month = "五月班";
        } else if (month.equals("06")) {
            month = "六月班";
        } else if (month.equals("07")) {
            month = "七月班";
        } else if (month.equals("08")) {
            month = "八月班";
        } else if (month.equals("09")) {
            month = "九月班";
        } else if (month.equals("10")) {
            month = "十月班";
        } else if (month.equals("11")) {
            month = "十一月班";
        } else {
            month = "十二月班";
        }
        return month;
    }
}
