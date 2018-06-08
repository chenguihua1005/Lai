/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.act.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.softtek.lai.R;
import com.softtek.lai.module.act.model.ActZKP1Model;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ActZKPAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<ActZKP1Model> list;

    private Context context;
    String type;
    float target_distance;

    /**
     * 构造函数
     */
    public ActZKPAdapter(Context context, List<ActZKP1Model> list, String type, float distance) {
        this.type = type;
        this.target_distance = distance;
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
            convertView = mInflater.inflate(R.layout.act_list_group_person_item, parent,false);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            holder.text_order = (TextView) convertView.findViewById(R.id.text_order);
            holder.rcpb_prog = (RoundCornerProgressBar) convertView.findViewById(R.id.rcpb_prog);
            holder.img_person = (ImageView) convertView.findViewById(R.id.img_person);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final ActZKP1Model actZKP1Model = list.get(position);
        holder.text_name.setText(actZKP1Model.getActDName());
        holder.text_order.setText(actZKP1Model.getActDOrder());
        if ((position + 1) < 4) {
            holder.text_order.setTextColor(Color.parseColor("#FDB02B"));
        } else {
            holder.text_order.setTextColor(ContextCompat.getColor(context,R.color.word3));
        }
        String path = AddressManager.get("photoHost");
        if (!TextUtils.isEmpty(actZKP1Model.getActDImg())) {
            Picasso.with(context).load(path + actZKP1Model.getActDImg()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(holder.img_person);
        } else {
            Picasso.with(context).load(R.drawable.img_default).into(holder.img_person);
        }

        float step;
        float distance;
        String order = actZKP1Model.getActDOrder();
        if (TextUtils.isEmpty(actZKP1Model.getActDTotal())) {
            step = 0;
        } else {
            step = Float.parseFloat(actZKP1Model.getActDTotal());
        }
        if("1".equals(type)){
            holder.text_value.setText(actZKP1Model.getActDTotal() + "公里");
        }else {
            holder.text_value.setText(actZKP1Model.getActDTotal() + "步");
        }
        int f = (int) ((step / target_distance) * 100);
        holder.rcpb_prog.setProgress(f);

        return convertView;
    }


    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_value;
        public TextView text_name;
        public TextView text_order;
        public RoundCornerProgressBar rcpb_prog;
        public ImageView img_person;
    }
}



