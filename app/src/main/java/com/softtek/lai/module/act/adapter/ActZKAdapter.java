/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.act.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.module.act.model.ActDetiallistModel;
import com.softtek.lai.module.act.model.ActlistModel;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class ActZKAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<ActDetiallistModel> list;

    private Context context;
    private IAssistantPresenter assistantPresenter;
    String type;

    /**
     * 构造函数
     */
    public ActZKAdapter(Context context, List<ActDetiallistModel> list,String type) {
        this.type=type;
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
            convertView = mInflater.inflate(R.layout.act_list_distance_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.text_full = (TextView) convertView.findViewById(R.id.text_full);
            holder.rcpb_prog = (RoundCornerProgressBar) convertView.findViewById(R.id.rcpb_prog);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.img_person = (ImageView) convertView.findViewById(R.id.img_person);
            holder.img_group = (ImageView) convertView.findViewById(R.id.img_group);
            holder.rel = (RelativeLayout) convertView.findViewById(R.id.rel);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final ActDetiallistModel actDetiallistModel = list.get(position);
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if("1".equals(type) ||"0".equals(type)){
            holder.img_group.setVisibility(View.VISIBLE);
            holder.img_person.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(actDetiallistModel.getActDImg())) {
                Picasso.with(context).load(path + actDetiallistModel.getActDImg()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(holder.img_group);
            } else {
                Picasso.with(context).load("www").placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(holder.img_group);
            }
        }else {
            holder.img_group.setVisibility(View.GONE);
            holder.img_person.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(actDetiallistModel.getActDImg())) {
                Picasso.with(context).load(path + actDetiallistModel.getActDImg()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(holder.img_person);
            } else {
                Picasso.with(context).load("www").placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(holder.img_person);
            }
        }
        if("0".equals(type)){

        }else if("1".equals(type)){

        }else if("2".equals(type)){

        }else if("3".equals(type)){

        }

        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_value;
        public TextView text_full;
        public RoundCornerProgressBar rcpb_prog;
        public ImageView img_person;
        public ImageView img_group;
        public ImageView img;
        public RelativeLayout rel;
    }
}



