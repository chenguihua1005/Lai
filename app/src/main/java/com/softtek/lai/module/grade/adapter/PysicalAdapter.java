package com.softtek.lai.module.grade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.grade.model.Student;

import java.util.List;

/**
 * Created by jerry.guan on 3/22/2016.
 */
public class PysicalAdapter extends BaseAdapter{

    private LayoutInflater inflater;

    private List<Student> students;

    public PysicalAdapter(Context context, List<Student> students){
        inflater=LayoutInflater.from(context);
        this.students=students;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.loss_weight_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Student student=students.get(position);
        holder.tv_order.setText(position+"");
        holder.tv_name.setText(student.getUserName());
        holder.tv_lw_before.setText(student.getLossBefor());
        holder.tv_lw_after.setText(student.getLossAfter());
        holder.tv_lw_totle.setText(student.getLossWeght());
        return convertView;
    }

    static class ViewHolder{
        TextView tv_order;
        TextView tv_name;
        TextView tv_lw_before;
        TextView tv_lw_after;
        TextView tv_lw_totle;

        public ViewHolder(View view){
            tv_order= (TextView) view.findViewById(R.id.tv_order);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_lw_before= (TextView) view.findViewById(R.id.tv_lw_before);
            tv_lw_after= (TextView) view.findViewById(R.id.tv_lw_after);
            tv_lw_totle= (TextView) view.findViewById(R.id.tv_lw_totle);

        }
    }
}
