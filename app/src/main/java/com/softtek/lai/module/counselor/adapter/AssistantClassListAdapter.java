/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.softtek.lai.R;
import com.softtek.lai.module.counselor.model.AssistantInfoModel;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class AssistantClassListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<AssistantInfoModel> list;
    private Context context;
    private IAssistantPresenter assistantPresenter;

    /**
     * 构造函数
     */
    public AssistantClassListAdapter(Context context, List<AssistantInfoModel> list) {
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
            convertView = mInflater.inflate(R.layout.fragment_assistant_list_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            holder.text_class_name = (TextView) convertView.findViewById(R.id.text_class_name);
            holder.img = (ImageView) convertView.findViewById(R.id.img);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }


        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        AssistantInfoModel assistantClassInfo = list.get(position);
        if ("".equals(assistantClassInfo.getPhoto())) {
            Picasso.with(context).load("111").error(R.drawable.img_default).into(holder.img);
        } else {
            Picasso.with(context).load(assistantClassInfo.getPhoto()).error(R.drawable.img_default).into(holder.img);
        }
        holder.text_name.setText(assistantClassInfo.getUserName() + "(" + assistantClassInfo.getMobile() + ")");
        String startTimeStr = assistantClassInfo.getStartDate().toString();
        String str[] = startTimeStr.split("-");
        if ("01".equals(str[1]) || "1".equals(str[1])) {
            holder.text_class_name.setText("一月班");
        } else if ("02".equals(str[1]) || "2".equals(str[1])) {
            holder.text_class_name.setText("二月班");
        } else if ("03".equals(str[1]) || "3".equals(str[1])) {
            holder.text_class_name.setText("三月班");
        } else if ("04".equals(str[1]) || "4".equals(str[1])) {
            holder.text_class_name.setText("四月班");
        } else if ("05".equals(str[1]) || "5".equals(str[1])) {
            holder.text_class_name.setText("五月班");
        } else if ("06".equals(str[1]) || "6".equals(str[1])) {
            holder.text_class_name.setText("六月");
        } else if ("07".equals(str[1]) || "7".equals(str[1])) {
            holder.text_class_name.setText("七月班");
        } else if ("08".equals(str[1]) || "8".equals(str[1])) {
            holder.text_class_name.setText("八月班");
        } else if ("09".equals(str[1]) || "9".equals(str[1])) {
            holder.text_class_name.setText("九月班");
        } else if ("10".equals(str[1])) {
            holder.text_class_name.setText("十月班");
        } else if ("11".equals(str[1])) {
            holder.text_class_name.setText("十一月班");
        } else if ("12".equals(str[1])) {
            holder.text_class_name.setText("十二月班");
        }

        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_name;
        public TextView text_class_name;
        public ImageView img;
    }
}



