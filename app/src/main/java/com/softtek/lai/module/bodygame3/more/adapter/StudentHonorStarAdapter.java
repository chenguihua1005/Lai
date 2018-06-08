package com.softtek.lai.module.bodygame3.more.adapter;

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
import com.softtek.lai.module.bodygame3.more.model.StudentHonorInfo;

import java.util.List;

public class StudentHonorStarAdapter extends BaseAdapter {
    private List<StudentHonorInfo> list;
    private Context context;

    public StudentHonorStarAdapter(Context context, List<StudentHonorInfo> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.student_honor_star_item, null);
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.text_value1 = (TextView) convertView.findViewById(R.id.text_value1);
            holder.img = (ImageView) convertView.findViewById(R.id.img);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        StudentHonorInfo studentHonorInfo = list.get(position);
        if ("3".equals(studentHonorInfo.getHonorType())) {
            holder.img.setImageResource(R.drawable.img_student_honor_star);
        } else {
            if ("1".equals(studentHonorInfo.getValue())) {
                holder.img.setImageResource(R.drawable.img_student_honor_1);
            } else if ("2".equals(studentHonorInfo.getValue())) {
                holder.img.setImageResource(R.drawable.img_student_honor_2);
            } else {
                holder.img.setImageResource(R.drawable.img_student_honor_3);
            }
        }
        holder.text_value.setText(studentHonorInfo.getValue());
        String time = studentHonorInfo.getCreateDate();
        String[] str1 = time.split(" ");
        String[] str = str1[0].split("-");
        holder.text_value1.setText(str[0] + "/" + str[1] + "/" + str[2]);


        return convertView;
    }

    private static class ViewHolder {
        private TextView text_value;
        private TextView text_value1;
        private ImageView img;
    }
}