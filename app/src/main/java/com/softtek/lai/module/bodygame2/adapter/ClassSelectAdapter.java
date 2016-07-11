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
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame2.model.ClassSelectModel;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;

import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ClassSelectAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<ClassSelectModel> list;
    private Context context;
    private IAssistantPresenter assistantPresenter;

    /**
     * 构造函数
     */
    public ClassSelectAdapter(Context context, List<ClassSelectModel> list) {
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
            convertView = mInflater.inflate(R.layout.select_class_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/

            holder.text_month = (TextView) convertView.findViewById(R.id.text_month);
            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        ClassSelectModel model = list.get(position);
        holder.text_month.setText(model.getClassMonth());
        holder.text_name.setText(model.getClassName());
        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_month;
        public TextView text_name;
    }
}



