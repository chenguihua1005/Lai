package com.softtek.lai.module.retest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.retest.model.Banji;

import java.util.List;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
public class ClassAdapter extends BaseAdapter {

    private Context context;
    private List<Banji> banjiList;
    private LayoutInflater inflater;

    public ClassAdapter(Context context,List<Banji> banjiList) {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.banjiList=banjiList;
    }


    public void updateData(List<Banji> banjiList){
        this.banjiList=banjiList;
        notifyDataSetChanged();;
    }


    @Override
    public int getCount() {
        return banjiList.size();
    }

    @Override
    public Object getItem(int position) {
        return banjiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=null;
        if (convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.listview_retest_class,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        Banji banji=banjiList.get(position);
        viewHolder.StartDate.setText(tomonth((banji.getStartDate().substring(5,7))));
        viewHolder.ClassName.setText(banji.getClassName());
        viewHolder.Total.setText(banji.getTotal()+"");
        return convertView;
    }


    class ViewHolder{
        TextView StartDate;
        TextView ClassName;
        TextView Total;
        public ViewHolder(View view){
            StartDate=(TextView)view.findViewById(R.id.tv_classname);
            ClassName=(TextView)view.findViewById(R.id.tv_title);
            Total=(TextView)view.findViewById(R.id.tv_personum);
        }
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
