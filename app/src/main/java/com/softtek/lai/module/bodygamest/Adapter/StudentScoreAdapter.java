/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygamest.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygamest.model.StudentScripInfo;
import com.softtek.lai.module.counselor.model.MarchInfoModel;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class StudentScoreAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<StudentScripInfo> list;
    private Context context;
    private IAssistantPresenter assistantPresenter;

    /**
     * 构造函数
     */
    public StudentScoreAdapter(Context context, List<StudentScripInfo> list) {
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
            convertView = mInflater.inflate(R.layout.student_score_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/

            holder.text_rnum = (TextView) convertView.findViewById(R.id.text_rnum);
            holder.text_user_name = (TextView) convertView.findViewById(R.id.text_user_name);
            holder.text_before_weight = (TextView) convertView.findViewById(R.id.text_before_weight);
            holder.text_after_weight = (TextView) convertView.findViewById(R.id.text_after_weight);
            holder.text_lose_weight = (TextView) convertView.findViewById(R.id.text_lose_weight);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.img_state = (ImageView) convertView.findViewById(R.id.img_state);


            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        StudentScripInfo marchInfo = list.get(position);
        String path= AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if ("".equals(marchInfo.getPhoto())) {
            Picasso.with(context).load("111").error(R.drawable.img_default).into(holder.img);
        } else {
            Picasso.with(context).load(path+marchInfo.getPhoto()).error(R.drawable.img_default).into(holder.img);
        }
        if ((position + 1) < 4) {
            holder.text_rnum.setTextColor(Color.parseColor("#FDB02B"));
        }else {
            holder.text_rnum.setTextColor(context.getResources().getColor(R.color.word3));
        }

        holder.text_rnum.setText(marchInfo.getRowNumber().toString());
        holder.text_user_name.setText(marchInfo.getUserName().toString());
        holder.text_before_weight.setText("前 " + marchInfo.getBeforeWeight().toString() + "斤");
        holder.text_after_weight.setText("后 " + marchInfo.getAfterWeight().toString() + "斤");
        holder.text_lose_weight.setText(marchInfo.getLoss().toString());
        if("上升".equals(marchInfo.getChange())){
            holder.img_state.setImageResource(R.drawable.img_score_up);
        }else {
            holder.img_state.setImageResource(R.drawable.img_score_down);
        }
        return convertView;
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_rnum;
        public TextView text_user_name;
        public TextView text_before_weight;
        public TextView text_after_weight;
        public TextView text_lose_weight;
        public ImageView img;
        public ImageView img_state;
    }
}



