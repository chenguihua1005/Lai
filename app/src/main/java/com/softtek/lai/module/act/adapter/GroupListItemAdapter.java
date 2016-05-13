/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.act.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.act.model.ActlistModel;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.sport.model.RecentlyActiviteModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class GroupListItemAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<ActlistModel> list;

    private BaseActivity context;
    private IAssistantPresenter assistantPresenter;

    /**
     * 构造函数
     */
    public GroupListItemAdapter(BaseActivity context, List<ActlistModel> list) {
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
            convertView = mInflater.inflate(R.layout.activity_group_main_activity_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_title = (TextView) convertView.findViewById(R.id.text_title);
            holder.text_state = (TextView) convertView.findViewById(R.id.text_state);
            holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            holder.text_lx = (TextView) convertView.findViewById(R.id.text_lx);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.img_state = (ImageView) convertView.findViewById(R.id.img_state);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final ActlistModel actlistModel = list.get(position);

        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if ("".equals(actlistModel.getActimg()) || "null".equals(actlistModel.getActimg()) || actlistModel.getActimg() == null) {
            Picasso.with(context).load("111").fit().error(R.drawable.img_default).into(holder.img);
        } else {
            Picasso.with(context).load(path + actlistModel.getActimg()).error(R.drawable.img_default).into(holder.img);
        }
        holder.text_title.setText(actlistModel.getActTitle().toString());
        String status=actlistModel.getAcStatus();
        if("1".equals(status)){
            holder.text_state.setText("进行中");
            holder.img_state.setImageResource(R.drawable.img_activity_1);
            holder.text_state.setTextColor(context.getResources().getColor(R.color.editorText));
        }else if("0".equals(status)){
            holder.text_state.setText("已结束");
            holder.img_state.setImageResource(R.drawable.img_activity_3);
            holder.text_state.setTextColor(context.getResources().getColor(R.color.word16));
        }else {
            holder.text_state.setText("未开始");
            holder.img_state.setImageResource(R.drawable.img_activity_2);
            holder.text_state.setTextColor(context.getResources().getColor(R.color.word15));

        }
        String type=actlistModel.getActiveType();
        if("1".equals(type)){
            holder.text_lx.setText("团体赛");
        }else {
            holder.text_lx.setText("个人赛");
        }
        String start = actlistModel.getStart();
        String end = actlistModel.getEnd();

        String start_time = "";
        String end_time = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("MM月dd号 HH:mm");
        try {
            Date start_date = sdf.parse(start);
            Date end_date = sdf.parse(end);
            start_time = format.format(start_date);
            end_time = format.format(end_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.text_time.setText(start_time + " - " + end_time);
        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_title;
        public TextView text_state;
        public TextView text_time;
        public TextView text_lx;
        public ImageView img;
        public ImageView img_state;
    }
}



