/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.review.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.counselor.model.ClassInfoModel;

import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ReviewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<ClassInfoModel> list;
    Context context;

    /**
     * 构造函数
     */
    public ReviewAdapter(Context context, List<ClassInfoModel> list) {
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
        ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.counselor_class_list_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.rel_item = (RelativeLayout) convertView.findViewById(R.id.rel_item);
            holder.lin_state = (LinearLayout) convertView.findViewById(R.id.lin_state);
            holder.text_class_name = (TextView) convertView.findViewById(R.id.text_class_name);
            holder.text_state = (TextView) convertView.findViewById(R.id.text_state);
            holder.text_people_count = (TextView) convertView.findViewById(R.id.text_people_count);
            holder.img_more = (ImageView) convertView.findViewById(R.id.img_more);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        ClassInfoModel classInfo = list.get(position);
        if (classInfo.getClassStatus().equals("-1")) {
            holder.text_state.setText("未开班");
            holder.text_state.setTextColor(context.getResources().getColor(R.color.grey_font));
            holder.text_class_name.setTextColor(context.getResources().getColor(R.color.grey_font));
            holder.text_people_count.setTextColor(context.getResources().getColor(R.color.grey_font));
            holder.img_more.setVisibility(View.GONE);
        } else if (classInfo.getClassStatus().equals("0")) {
            holder.text_state.setText("已开班");
            holder.text_state.setTextColor(context.getResources().getColor(R.color.mytoolbar_green));
            holder.text_class_name.setTextColor(context.getResources().getColor(R.color.black));
            holder.text_people_count.setTextColor(context.getResources().getColor(R.color.black));
            holder.img_more.setVisibility(View.VISIBLE);
        } else if (classInfo.getClassStatus().equals("1")) {
            holder.text_state.setText("已结束");
        }
        holder.text_class_name.setText(classInfo.getClassName().toString());
        String startTimeStr = classInfo.getStartDate().toString();
        String str[] = startTimeStr.split("-");
        if ("01".equals(str[1]) || "1".equals(str[1])) {
            holder.text_people_count.setText("一月班（" + classInfo.getMemberCount() + "人）");
        } else if ("02".equals(str[1]) || "2".equals(str[1])) {
            holder.text_people_count.setText("二月班（" + classInfo.getMemberCount() + "人）");
        } else if ("03".equals(str[1]) || "3".equals(str[1])) {
            holder.text_people_count.setText("三月班（" + classInfo.getMemberCount() + "人）");
        } else if ("04".equals(str[1]) || "4".equals(str[1])) {
            holder.text_people_count.setText("四月班（" + classInfo.getMemberCount() + "人）");
        } else if ("05".equals(str[1]) || "5".equals(str[1])) {
            holder.text_people_count.setText("五月班（" + classInfo.getMemberCount() + "人）");
        } else if ("06".equals(str[1]) || "6".equals(str[1])) {
            holder.text_people_count.setText("六月班（" + classInfo.getMemberCount() + "人）");
        } else if ("07".equals(str[1]) || "7".equals(str[1])) {
            holder.text_people_count.setText("七月班（" + classInfo.getMemberCount() + "人）");
        } else if ("08".equals(str[1]) || "8".equals(str[1])) {
            holder.text_people_count.setText("八月班（" + classInfo.getMemberCount() + "人）");
        } else if ("09".equals(str[1]) || "9".equals(str[1])) {
            holder.text_people_count.setText("九月班（" + classInfo.getMemberCount() + "人）");
        } else if ("10".equals(str[1])) {
            holder.text_people_count.setText("十月班（" + classInfo.getMemberCount() + "人）");
        } else if ("11".equals(str[1])) {
            holder.text_people_count.setText("十一月班（" + classInfo.getMemberCount() + "人）");
        } else if ("12".equals(str[1])) {
            holder.text_people_count.setText("十二月班（" + classInfo.getMemberCount() + "人）");
        }


        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public RelativeLayout rel_item;
        public LinearLayout lin_state;
        public TextView text_class_name;
        public TextView text_state;
        public TextView text_people_count;
        public ImageView img_more;
    }
}



