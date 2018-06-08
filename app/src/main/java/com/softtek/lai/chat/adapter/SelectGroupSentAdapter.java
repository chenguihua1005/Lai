/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.chat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.chat.model.SelectContactInfoModel;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.bodygame3.conversation.model.ChatContactModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class SelectGroupSentAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<SelectContactInfoModel> list;
    private BaseActivity context;

    /**
     * 构造函数
     */
    public SelectGroupSentAdapter(BaseActivity context, List<SelectContactInfoModel> list) {
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
            convertView = mInflater.inflate(R.layout.chat_select_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_name = (TextView) convertView.findViewById(R.id.text_name);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.img_select_button = (ImageView) convertView.findViewById(R.id.img_select_button);
            holder.rel = (RelativeLayout) convertView.findViewById(R.id.rel);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final SelectContactInfoModel selectContactInfoModel = list.get(position);
        ChatContactModel contactListInfo = selectContactInfoModel.getModel();
        String photo = contactListInfo.getPhoto();
        String path= AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if ("".equals(photo)) {
            Picasso.with(context).load("111").fit().error(R.drawable.img_default).into(holder.img);
        } else {
            Picasso.with(context).load(path+photo).fit().error(R.drawable.img_default).into(holder.img);
        }
        holder.text_name.setText(contactListInfo.getUserName());
        final boolean isSelect = selectContactInfoModel.isSelected();
        if (isSelect) {
            holder.img_select_button.setImageResource(R.drawable.history_data_circled);
        } else {
            holder.img_select_button.setImageResource(R.drawable.history_data_circle);
        }
        holder.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectContactInfoModel.isSelected()) {
                    holder.img_select_button.setImageResource(R.drawable.history_data_circle);
                    selectContactInfoModel.setSelected(false);
                } else {
                    holder.img_select_button.setImageResource(R.drawable.history_data_circled);
                    selectContactInfoModel.setSelected(true);
                }
            }
        });
        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_name;
        public ImageView img;
        public ImageView img_select_button;
        public RelativeLayout rel;
    }
}



