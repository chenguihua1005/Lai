/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.message.model.MessageDetailInfo;

import java.util.ArrayList;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class MessagePcRemindAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private ArrayList<MessageDetailInfo> list;
    private Context context;
    private IAssistantPresenter assistantPresenter;

    /**
     * 构造函数
     */
    public MessagePcRemindAdapter(Context context, ArrayList<MessageDetailInfo> list) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        assistantPresenter = new AssistantImpl(context);
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
            convertView = mInflater.inflate(R.layout.pc_remind_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        MessageDetailInfo messageDetailInfo = list.get(position);
        String time = messageDetailInfo.getSendTime();

        if (!"".equals(time)) {
            String[] str1 = time.split(" ");
            String[] str = str1[0].split("-");
            holder.text_time.setText(str[0] + "年" + str[1] + "月" + str[2] + "日");
        } else {
            holder.text_time.setText("");
        }
        if (!"".equals(messageDetailInfo.getComments())) {
            holder.text_value.setText(messageDetailInfo.getComments());
        } else {
            holder.text_value.setText("");
        }

        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_time;
        public TextView text_value;
    }
}



