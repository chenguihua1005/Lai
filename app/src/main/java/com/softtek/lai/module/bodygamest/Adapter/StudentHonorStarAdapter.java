package com.softtek.lai.module.bodygamest.Adapter;

/**
 * Created by jarvis.liu on 4/1/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygamest.model.StudentHonorInfo;

import java.util.List;

public class StudentHonorStarAdapter extends BaseAdapter {
    private List<StudentHonorInfo> list;
    private LayoutInflater mInflater;
    private Context context;

    public StudentHonorStarAdapter(Context context, List<StudentHonorInfo> list) {
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
            convertView = mInflater.inflate(R.layout.student_honor_star_item, null);
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.text_value1 = (TextView) convertView.findViewById(R.id.text_value1);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        StudentHonorInfo studentHonorInfo=list.get(position);
        holder.text_value.setText(studentHonorInfo.getValue().toString());
        String time=studentHonorInfo.getCreateDate();
        String[] str1=time.split(" ");
        String[] str=str1[0].split("-");
        holder.text_value1.setText(str[0]+"/"+str[1]+"/"+str[2]);



        return convertView;
    }

    private static class ViewHolder {
        private TextView text_value;
        private TextView text_value1;
    }
}