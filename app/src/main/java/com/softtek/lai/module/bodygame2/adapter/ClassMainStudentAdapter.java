/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame2.model.ClassMainStudentModel;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;

import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ClassMainStudentAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<ClassMainStudentModel> list;
    private Context context;
    private IAssistantPresenter assistantPresenter;

    /**
     * 构造函数
     */
    public ClassMainStudentAdapter(Context context, List<ClassMainStudentModel> list) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
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
            convertView = mInflater.inflate(R.layout.class_main_student_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/

            holder.tv_order = (TextView) convertView.findViewById(R.id.tv_order);
            holder.text_before_weight = (TextView) convertView.findViewById(R.id.text_before_weight);
            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            holder.tv_who = (TextView) convertView.findViewById(R.id.tv_who);
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.text_count = (TextView) convertView.findViewById(R.id.text_count);
            holder.cb_gender = (CheckBox) convertView.findViewById(R.id.cb_gender);
            holder.cb_star = (CheckBox) convertView.findViewById(R.id.cb_star);
            holder.cb_fc = (CheckBox) convertView.findViewById(R.id.cb_fc);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        ClassMainStudentModel model = list.get(position);
        holder.text_before_weight.setText("初始体重："+model.getWeight());
        holder.tv_order.setText(model.getOrder());
        holder.text_name.setText(model.getName());
        holder.tv_who.setText(model.getZname());
        holder.text_value.setText(model.getValue()+"斤");
        holder.text_count.setText("x"+model.getCount());

        if(model.getGender().equals("1")){
            holder.cb_gender.setChecked(true);
        }else {
            holder.cb_gender.setChecked(false);
        }
        if(model.getType1().equals("1")){
            holder.cb_star.setChecked(true);
        }else {
            holder.cb_star.setChecked(false);
        }
        if(model.getType2().equals("1")){
            holder.cb_fc.setChecked(true);
        }else {
            holder.cb_fc.setChecked(false);
        }
        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView tv_order;
        public TextView text_before_weight;
        public TextView text_name;
        public TextView tv_who;
        public TextView text_value;
        public TextView text_count;
        public CheckBox cb_gender;
        public CheckBox cb_star;
        public CheckBox cb_fc;
    }
}



