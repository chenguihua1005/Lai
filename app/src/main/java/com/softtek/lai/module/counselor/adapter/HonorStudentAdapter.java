/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.counselor.model.HonorTable1Model;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.utils.StringUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class HonorStudentAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<HonorTable1Model> list;
    private Context context;
    private IAssistantPresenter assistantPresenter;

    /**
     * 构造函数
     */
    public HonorStudentAdapter(Context context, List<HonorTable1Model> list) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        Log.e("jarvis", list.toString());
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
            convertView = mInflater.inflate(R.layout.sp_honor_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_rnum = (TextView) convertView.findViewById(R.id.text_rnum);
            holder.text_user_name = (TextView) convertView.findViewById(R.id.text_user_name);
            holder.text_before_weight = (TextView) convertView.findViewById(R.id.text_before_weight);
            holder.text_after_weight = (TextView) convertView.findViewById(R.id.text_after_weight);
            holder.text_lose_weight = (TextView) convertView.findViewById(R.id.text_lose_weight);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.img_top = (ImageView) convertView.findViewById(R.id.img_top);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final HonorTable1Model honorTable1 = list.get(position);
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if ("".equals(honorTable1.getPhoto()) || "null".equals(honorTable1.getPhoto()) || honorTable1.getPhoto() == null) {
            Picasso.with(context).load("111").fit().error(R.drawable.img_default).fit().into(holder.img);
        } else {
            Picasso.with(context).load(honorTable1.getPhoto()).fit().error(R.drawable.img_default).into(holder.img);
        }
        //Picasso.with(context).load("111").error(R.drawable.img_default).into(holder.img);
        String rnum = honorTable1.getRnum();
        if (rnum.equals("1")) {
            holder.text_rnum.setVisibility(View.GONE);
            holder.img_top.setVisibility(View.VISIBLE);
            holder.img_top.setImageResource(R.drawable.img_honor_item_1);
        } else if (rnum.equals("2")) {
            holder.text_rnum.setVisibility(View.GONE);
            holder.img_top.setVisibility(View.VISIBLE);
            holder.img_top.setImageResource(R.drawable.img_honor_item_2);
        } else if (rnum.equals("3")) {
            holder.text_rnum.setVisibility(View.GONE);
            holder.img_top.setVisibility(View.VISIBLE);
            holder.img_top.setImageResource(R.drawable.img_honor_item_3);
        } else {
            holder.text_rnum.setVisibility(View.VISIBLE);
            holder.img_top.setVisibility(View.GONE);
            holder.text_rnum.setTextColor(context.getResources().getColor(R.color.word3));
        }

        holder.text_rnum.setText(honorTable1.getRnum().toString());
        holder.text_user_name.setText(honorTable1.getUserName().toString());
        holder.text_before_weight.setText("减重前 " + StringUtil.getFloatValue(honorTable1.getBeforeWeight()) + "斤");
        holder.text_after_weight.setText("后 " + StringUtil.getFloatValue(honorTable1.getAfterWeight()) + "斤");
        holder.text_lose_weight.setText(StringUtil.getFloatValue(honorTable1.getLoseWeight()));
        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_rnum;
        public TextView text_user_name;
        public TextView text_before_weight;
        public TextView text_after_weight;
        public TextView text_lose_weight;
        public ImageView img;
        public ImageView img_top;
    }
}



