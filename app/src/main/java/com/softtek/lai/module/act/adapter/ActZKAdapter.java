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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.softtek.lai.R;
import com.softtek.lai.module.act.model.ActDetiallistModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ActZKAdapter extends BaseAdapter {

    private List<ActDetiallistModel> list;

    private Context context;

    String type;
    double target_distance;
    int target_step;

    /**
     * 构造函数
     */
    public ActZKAdapter(Context context, List<ActDetiallistModel> list, String type, double distance) {
        this.type = type;
        this.target_distance = distance;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.act_list_distance_item, parent,false);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.text_full = (TextView) convertView.findViewById(R.id.text_full);
            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            holder.text_order = (TextView) convertView.findViewById(R.id.text_order);
            holder.rcpb_prog = (RoundCornerProgressBar) convertView.findViewById(R.id.rcpb_prog);
            //holder.progressBar1 = (ProgressBar) convertView.findViewById(R.id.progressBar1);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.img_full = (ImageView) convertView.findViewById(R.id.img_full);
            holder.img_person = (ImageView) convertView.findViewById(R.id.img_person);
            holder.img_group = (ImageView) convertView.findViewById(R.id.img_group);
            holder.rel = (RelativeLayout) convertView.findViewById(R.id.rel);
            holder.rel_group = (RelativeLayout) convertView.findViewById(R.id.rel_group);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }

        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final ActDetiallistModel actDetiallistModel = list.get(position);
        holder.text_name.setText(actDetiallistModel.getActDName());
        holder.text_order.setText(actDetiallistModel.getActDOrder());
        if ((position + 1) < 4) {
            holder.text_order.setTextColor(Color.parseColor("#FDB02B"));
        } else {
            holder.text_order.setTextColor(ContextCompat.getColor(context,R.color.word3));
        }
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if ("1".equals(type) || "0".equals(type)) {
            holder.rel_group.setVisibility(View.VISIBLE);
            holder.img_person.setVisibility(View.GONE);
            holder.img.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(actDetiallistModel.getActDImg())) {
                Picasso.with(context).load(path + actDetiallistModel.getActDImg()).placeholder(R.drawable.img_group_default).fit().centerCrop().error(R.drawable.img_group_default).into(holder.img_group);
            } else {
                Picasso.with(context).load("www").placeholder(R.drawable.img_group_default).fit().centerCrop().error(R.drawable.img_group_default).into(holder.img_group);
            }
        } else {
            holder.rel_group.setVisibility(View.GONE);
            holder.img_person.setVisibility(View.VISIBLE);
            holder.img.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(actDetiallistModel.getActDImg())) {
                Picasso.with(context).load(path + actDetiallistModel.getActDImg()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(holder.img_person);
            } else {
                Picasso.with(context).load("www").placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(holder.img_person);
            }
        }
        float step;
        float distance;
        String order = actDetiallistModel.getActDOrder();
        if (TextUtils.isEmpty(actDetiallistModel.getActDTotal())) {
            step = 0;
        } else {
            step = Float.parseFloat(actDetiallistModel.getActDTotal());
        }
        if ("0".equals(type)) {
            holder.img_full.setVisibility(View.GONE);
            if (TextUtils.isEmpty(list.get(0).getActDTotal())) {
                target_step = 0;
            } else {
                target_step = Integer.parseInt(list.get(0).getActDTotal());
            }

            holder.text_value.setText(actDetiallistModel.getActDTotal() + "步");

            if ("1".equals(order)) {
                if(target_step==0){
                    holder.rcpb_prog.setProgress(0);
                }else {
                    holder.rcpb_prog.setProgress(85);
                }
            } else {
                holder.rcpb_prog.setProgress((int) ((step * 0.8 / target_step) * 100));

            }

        } else if ("1".equals(type)) {
            holder.img_full.setVisibility(View.VISIBLE);
            holder.text_value.setText(actDetiallistModel.getActDTotal() + "公里");
            int f = (int) ((step / target_distance) * 100);
            if (f >= 100) {
                holder.rcpb_prog.setProgress(100);
                holder.rcpb_prog.setVisibility(View.GONE);
                holder.text_full.setText("达标");
                holder.img_full.setImageResource(R.drawable.img_act_group_full);
            } else {
                holder.text_full.setText("");
                holder.img_full.setImageResource(R.drawable.img_act_group_bg);
                holder.rcpb_prog.setVisibility(View.VISIBLE);
                holder.rcpb_prog.setProgress(f);

            }
        } else if ("2".equals(type)) {
            holder.img_full.setVisibility(View.GONE);
            if (TextUtils.isEmpty(list.get(0).getActDTotal())) {
                target_step = 0;
            } else {
                target_step = Integer.parseInt(list.get(0).getActDTotal());
            }

            holder.text_value.setText(actDetiallistModel.getActDTotal() + "步");
            if ("1".equals(order)) {
                if(target_step==0){
                    holder.rcpb_prog.setProgress(0);
                }else {
                    holder.rcpb_prog.setProgress(85);
                }
            } else {
                holder.rcpb_prog.setProgress((int) ((step * 0.85 / target_step) * 100));

            }
        } else if ("3".equals(type)) {
            holder.img_full.setVisibility(View.VISIBLE);
            holder.img_full.setVisibility(View.VISIBLE);
            holder.text_value.setText(actDetiallistModel.getActDTotal() + "公里");
            int f = (int) ((step / target_distance) * 100);
            if (f >= 100) {
                holder.rcpb_prog.setProgress(100);
                holder.rcpb_prog.setVisibility(View.GONE);
                holder.text_full.setText("达标");
                holder.img_full.setImageResource(R.drawable.img_act_group_full);
            } else {
                holder.text_full.setText("");
                holder.img_full.setImageResource(R.drawable.img_act_group_bg);
                holder.rcpb_prog.setVisibility(View.VISIBLE);
                holder.rcpb_prog.setProgress(f);

            }
        }

        return convertView;
    }


    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_value;
        public TextView text_full;
        public TextView text_name;
        public TextView text_order;
        public RoundCornerProgressBar rcpb_prog;
        //public ProgressBar progressBar1;
        public ImageView img_person;
        public ImageView img_group;
        public ImageView img;
        public ImageView img_full;
        public RelativeLayout rel;
        public RelativeLayout rel_group;
    }
}



