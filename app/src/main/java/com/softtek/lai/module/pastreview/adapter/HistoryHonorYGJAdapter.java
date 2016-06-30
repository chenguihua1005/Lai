package com.softtek.lai.module.pastreview.adapter;

/**
 * Created by jarvis.liu on 4/1/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.pastreview.model.HistoryHonorInfo;

import java.util.List;

public class HistoryHonorYGJAdapter extends BaseAdapter {
    private List<HistoryHonorInfo> list;
    private LayoutInflater mInflater;
    private Context context;

    public HistoryHonorYGJAdapter(Context context, List<HistoryHonorInfo> list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.history_honor_ygj_item, null);
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.text_sm = (TextView) convertView.findViewById(R.id.text_sm);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HistoryHonorInfo studentHonorInfo=list.get(position);
        String time=studentHonorInfo.getCreateDate();
        String[] str1=time.split(" ");
        String[] str=str1[0].split("-");
        holder.text_value.setText(str[0]+"年"+str[1]+"月");
        holder.text_sm.setText(str[1]+"月月度冠军");

        return convertView;
    }

    private static class ViewHolder {
        private TextView text_value;
        private TextView text_sm;
    }
}