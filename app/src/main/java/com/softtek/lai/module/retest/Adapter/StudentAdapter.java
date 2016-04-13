package com.softtek.lai.module.retest.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.retest.model.BanjiStudentModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lareina.qiao on 3/24/2016.
 */
public class StudentAdapter extends BaseAdapter {
    private List<BanjiStudentModel> banjiStudentModelList;
    private Context context;
    public StudentAdapter(Context context, List<BanjiStudentModel>banjiStudentModelList){
        this.banjiStudentModelList=banjiStudentModelList;
        this.context=context;
    }
    public void updateData(List<BanjiStudentModel> banjiStudentModelList){
        this.banjiStudentModelList=banjiStudentModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return banjiStudentModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return banjiStudentModelList.get(position);
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
            holder.iv_type_ls=(ImageView)convertView.findViewById(R.id.iv_type_ls);
            holder.tv_month= (TextView) convertView.findViewById(R.id.iv_month);
            holder.tv_month1=(TextView)convertView.findViewById(R.id.iv_month1);
            holder.tv_date= (TextView) convertView.findViewById(R.id.iv_date);
            holder.tv_date1= (TextView) convertView.findViewById(R.id.iv_date1);
            convertView.setTag(holder);
        }
        else {
            holder= (Holder) convertView.getTag();
        }
        BanjiStudentModel banjiStudentModel=banjiStudentModelList.get(position);
        if(!TextUtils.isEmpty(banjiStudentModel.getPhoto())){
            Picasso.with(context).load(banjiStudentModel.getPhoto()).placeholder(R.drawable.img_default).error(R.drawable.lufei).into(holder.iv_head);
        }else{
            Picasso.with(context).load("www").placeholder(R.drawable.img_default).error(R.drawable.img_default).into(holder.iv_head);
        }

        holder.tv_retest_nick.setText(banjiStudentModel.getUserName());
        holder.tv_retest_phone.setText(banjiStudentModel.getMobile());
        String[] startClass=banjiStudentModel.getStartDate().split("-");
        holder.tv_retest_classdate.setText(tomonth(startClass[1]));
        holder.tv_retest_classweek.setText(banjiStudentModel.getWeekth()+"");

        String status= "".equals(banjiStudentModel.getAMStatus())?"未复测":"待审核";
        if (status=="未复测")
        {
            holder.tv_retest_type.setText("未复测");

            holder.iv_type_ls.setImageResource(R.drawable.luru);


            holder.tv_retest_type.setTextColor(context.getResources().getColor(R.color.green));
        }
        else {
            holder.tv_retest_type.setText("待审核");
            holder.iv_type_ls.setImageResource(R.drawable.shenhe);
            holder.tv_retest_type.setTextColor(context.getResources().getColor(R.color.orange));
        }
//        holder.tv_retest_type.setText();

        String[] currStart=banjiStudentModel.getCurrStart().split("-");
        String[] currEnd=banjiStudentModel.getCurrEnd().split("-");
        holder.tv_month.setText(currStart[1]);
        holder.tv_month1.setText(currEnd[1]);
        holder.tv_date.setText(currStart[2]);
        holder.tv_date1.setText(currEnd[2]);
        return convertView;
    }
    class Holder{
        CircleImageView iv_head;
        TextView tv_retest_nick;
        TextView tv_retest_phone;
        TextView tv_retest_classdate;
        TextView tv_retest_classweek;
        TextView tv_retest_type;
        ImageView iv_type_ls;
        TextView tv_month;
        TextView tv_month1;
        TextView tv_date;
        TextView tv_date1;
    }
    public String tomonth(String month){

        if (month.equals("01")||month.equals("1")){
            month="一月班";
        }
        else if (month.equals("02")||month.equals("2")){
            month="二月班";
        }else if (month.equals("03")||month.equals("3"))
        {
            month="三月班";
        }else if (month.equals("04")||month.equals("4"))
        {
            month="四月班";

        }else if (month.equals("05")||month.equals("5"))
        {
            month="五月班";
        }else if (month.equals("06")||month.equals("6"))
        {
            month="六月班";
        }else if (month.equals("07")||month.equals("7"))
        {
            month="七月班";
        } else if (month.equals("08")||month.equals("8"))
        {
            month="八月班";
        }else if (month.equals("09")||month.equals("9"))
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
