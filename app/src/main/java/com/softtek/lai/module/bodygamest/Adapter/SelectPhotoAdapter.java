/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.bodygamest.model.DownPhotoModel;
import com.softtek.lai.module.bodygamest.model.LogListModel;
import com.softtek.lai.module.counselor.model.ApplyAssistantModel;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class SelectPhotoAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<LogListModel> list;
    Context context;
    private IAssistantPresenter assistantPresenter;

    /**
     * 构造函数
     */
    public SelectPhotoAdapter(Context context, List<LogListModel> list) {
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
            convertView = mInflater.inflate(R.layout.select_photo_list_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
//            holder.rel_item = (RelativeLayout) convertView.findViewById(R.id.rel_item);
//            holder.lin_state = (LinearLayout) convertView.findViewById(R.id.lin_state);
            holder.img = (ImageView) convertView.findViewById(R.id.img);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final LogListModel logListModel = list.get(position);
        if (!TextUtils.isEmpty(logListModel.getImgUrl())) {
            Picasso.with(context).load(logListModel.getImgUrl()).placeholder(R.drawable.lufei).error(R.drawable.lufei).into(holder.img);
        } else {
            Picasso.with(context).load("www").placeholder(R.drawable.lufei).error(R.drawable.lufei).into(holder.img);
        }
        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public ImageView img;
    }
}



