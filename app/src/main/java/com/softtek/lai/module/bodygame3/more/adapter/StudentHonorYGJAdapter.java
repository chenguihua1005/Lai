package com.softtek.lai.module.bodygame3.more.adapter;

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
import com.softtek.lai.module.bodygame3.more.model.StudentHonorInfo;

import java.util.List;

public class StudentHonorYGJAdapter extends BaseAdapter {
    private List<StudentHonorInfo> list;
    private Context context;

    public StudentHonorYGJAdapter(Context context, List<StudentHonorInfo> list) {
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.student_honor_ygj_item, null);
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        StudentHonorInfo studentHonorInfo=list.get(position);
        String time=studentHonorInfo.getCreateDate();
        String[] str1=time.split(" ");
        String[] str=str1[0].split("-");
        holder.text_value.setText(str[0]+"年"+str[1]+"月");

        return convertView;
    }

    private static class ViewHolder {
        private TextView text_value;
    }
}