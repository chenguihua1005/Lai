package com.softtek.lai.module.historydate.adapter;

/**
 * Created by jarvis.liu on 4/1/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygamest.model.StudentHonorInfo;
import com.softtek.lai.module.historydate.model.HistoryHonorInfo;

import java.util.List;

public class HistoryHonorJZAdapter extends BaseAdapter {
    private List<HistoryHonorInfo> list;
    private LayoutInflater mInflater;
    private Context context;

    public HistoryHonorJZAdapter(Context context, List<HistoryHonorInfo> list) {
        System.out.println("list:" + list);
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
            convertView = mInflater.inflate(R.layout.history_honor_jz_item, null);
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.text_sm = (TextView) convertView.findViewById(R.id.text_sm);
            holder.img = (ImageView) convertView.findViewById(R.id.img);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HistoryHonorInfo studentHonorInfo = list.get(position);
        holder.text_value.setText(studentHonorInfo.getValue().toString());
        holder.text_sm.setText("减重"+studentHonorInfo.getValue().toString()+"斤奖章");
        holder.img.setImageResource(R.drawable.img_student_honor_jz_bg);

        return convertView;
    }

    private static class ViewHolder {
        private TextView text_value;
        private TextView text_sm;
        private ImageView img;
    }
}