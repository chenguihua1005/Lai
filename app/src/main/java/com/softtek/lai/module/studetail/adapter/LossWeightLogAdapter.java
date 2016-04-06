package com.softtek.lai.module.studetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;

import java.util.List;

/**
 * Created by John on 2016/4/3.
 */
public class LossWeightLogAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private List<LossWeightLogModel> logs;

    public  LossWeightLogAdapter(Context context,List<LossWeightLogModel> logs){
        inflater=LayoutInflater.from(context);
        this.logs=logs;
    }

    @Override
    public int getCount() {
        return logs.size();
    }

    @Override
    public Object getItem(int position) {
        return logs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.loss_weight_log_item,parent,false);
            holder=new LogHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (LogHolder) convertView.getTag();
        }
        LossWeightLogModel log=logs.get(position-2);
        return convertView;
    }

    private static class LogHolder{

        public TextView tv_day,tv_month,tv_log_title,tv_content;
        public ImageView iv_image;
        public CheckBox cb_zan;

        public LogHolder(View view){
            tv_day= (TextView) view.findViewById(R.id.tv_day);
            tv_month= (TextView) view.findViewById(R.id.tv_month);
            tv_log_title= (TextView) view.findViewById(R.id.tv_log_title);
            tv_content= (TextView) view.findViewById(R.id.tv_content);
            iv_image= (ImageView) view.findViewById(R.id.iv_image);
            cb_zan= (CheckBox) view.findViewById(R.id.cb_zan);
        }
    }
}
