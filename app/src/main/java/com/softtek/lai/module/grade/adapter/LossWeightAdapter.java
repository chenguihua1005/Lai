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
public class LossWeightAdapter extends BaseAdapter{

    private LayoutInflater inflater;

    private List<Student> students;

    private int flag;

    public LossWeightAdapter(Context context,List<Student> students,int flag){
        inflater=LayoutInflater.from(context);
        this.students=students;
        this.flag=flag;
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
        switch (flag){
            case 0:
                return getLossWeightView(position,convertView,parent);
            case 1:
                return getLossWeightPerView(position,convertView,parent);
            case 2:
                return getPhysicalView(position,convertView,parent);
            case 3:
                return getWaistlineView(position,convertView,parent);
            default:
                return getLossWeightView(position,convertView,parent);
        }

    }

    private View getLossWeightView(int position, View convertView, ViewGroup parent){
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

    private View getLossWeightPerView(int position, View convertView, ViewGroup parent){
        LossWeightPerHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.loss_weight_per_item,parent,false);
            holder=new LossWeightPerHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (LossWeightPerHolder) convertView.getTag();
        }
        Student student=students.get(position);
        holder.tv_order.setText(position+"");
        holder.tv_name.setText(student.getUserName());
        holder.tv_lw_per.setText(student.getLossPercent());
        return convertView;
    }

    private View getPhysicalView(int position, View convertView, ViewGroup parent){
        PhysicalHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.physical_item,parent,false);
            holder=new PhysicalHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (PhysicalHolder) convertView.getTag();
        }
        Student student=students.get(position);
        holder.tv_order.setText(position+"");
        holder.tv_name.setText(student.getUserName());
        holder.tv_physical.setText(student.getPysical());
        return convertView;
    }
    private View getWaistlineView(int position, View convertView, ViewGroup parent){
        WaistlineHolder holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.waistline_item,parent,false);
            holder=new WaistlineHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (WaistlineHolder) convertView.getTag();
        }
        Student student=students.get(position);
        holder.tv_order.setText(position+"");
        holder.tv_name.setText(student.getUserName());
        holder.tv_wl_before.setText(student.getWaistlinebefor());
        holder.tv_wl_after.setText(student.getWaistlineAfter());
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

    static class LossWeightPerHolder{
        TextView tv_order;
        TextView tv_name;
        TextView tv_lw_per;
        public  LossWeightPerHolder(View view){
            tv_order= (TextView) view.findViewById(R.id.tv_order);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_lw_per= (TextView) view.findViewById(R.id.tv_lw_per);
        }
    }
    static class PhysicalHolder{
        TextView tv_order;
        TextView tv_name;
        TextView tv_physical;
        public PhysicalHolder(View view){
            tv_order= (TextView) view.findViewById(R.id.tv_order);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_physical= (TextView) view.findViewById(R.id.tv_pysical);
        }
    }
    static class WaistlineHolder{
        TextView tv_order;
        TextView tv_name;
        TextView tv_wl_before;
        TextView tv_wl_after;
        public WaistlineHolder(View view){
            tv_order= (TextView) view.findViewById(R.id.tv_order);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_wl_before= (TextView) view.findViewById(R.id.tv_wl_before);
            tv_wl_after= (TextView) view.findViewById(R.id.tv_wl_after);
        }
    }

}
