package com.softtek.lai.module.retest.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame.Counselor;
import com.softtek.lai.module.retest.model.Student;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lareina.qiao on 3/23/2016.
 */
public class QueryAdapter extends BaseAdapter{
    private List<Student> studentList;
    private Context context;
    public QueryAdapter(Context context,List<Student> studentList){
        this.studentList=studentList;
        this.context=context;

    }


    public void updateData(List<Student> studentList){
        this.studentList=studentList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.listview_retest_query,null);
            holder=new Holder();
            holder.iv_head= (CircleImageView) convertView.findViewById(R.id.iv_head);
            holder.tv_retest_nick= (TextView) convertView.findViewById(R.id.tv_retest_nick);
            holder.tv_retest_phone= (TextView) convertView.findViewById(R.id.tv_retest_phone);
            holder.tv_retest_classdate= (TextView) convertView.findViewById(R.id.tv_retest_classdate);
            holder.tv_retest_classweek= (TextView) convertView.findViewById(R.id.tv_retest_classweek);
            holder.tv_retest_type= (TextView) convertView.findViewById(R.id.tv_retest_type);
            convertView.setTag(holder);
        }
        else {
            holder= (Holder) convertView.getTag();
        }
        Student student=studentList.get(position);
        if(!TextUtils.isEmpty(student.getPhoto())){
        Picasso.with(context).load(student.getPhoto()).placeholder(R.drawable.lufei).error(R.drawable.lufei).into(holder.iv_head);
        }else{
            Picasso.with(context).load("www").placeholder(R.drawable.lufei).error(R.drawable.lufei).into(holder.iv_head);
        }
//        holder.iv_head.setImageDrawable();
        holder.tv_retest_nick.setText(student.getUserName());
        holder.tv_retest_phone.setText(student.getMobile());
//        Log.i(">>>>>>>>>>>>","<<<<<<<<<<"+tomonth((student.getStartDate().substring(5,7))));
        holder.tv_retest_classdate.setText(tomonth((student.getStartDate().substring(5,7))));
        holder.tv_retest_classweek.setText(student.getWeekth()+"");
        holder.tv_retest_type.setText(equals(student.getAMStatus()==0)?"未审核":equals(student.getAMStatus()==1)?"已审核":"录入");
        return convertView;
    }
    class Holder{
        CircleImageView iv_head;
        TextView tv_retest_nick;
        TextView tv_retest_phone;
        TextView tv_retest_classdate;
        TextView tv_retest_classweek;
        TextView tv_retest_type;
    }
    public String tomonth(String month){

        if (month.equals("01")){
            month="一月班";
        }
        else if (month.equals("02")){
            month="二月班";
        }else if (month.equals("03"))
        {
            month="三月班";
        }else if (month.equals("04"))
        {
            month="四月班";

        }else if (month.equals("05"))
        {
            month="五月班";
        }else if (month.equals("06"))
        {
            month="六月班";
        }else if (month.equals("07"))
        {
            month="七月班";
        } else if (month.equals("08"))
        {
            month="八月班";
        }else if (month.equals("09"))
        {
            month="九月班";
        }else if (month.equals("10"))
        {
            month="十月班";
        }else if (month.equals("11"))
        {
            month="十一月班";
        }else
        {
            month="十二月班";
        }
        return month;
    }
}
