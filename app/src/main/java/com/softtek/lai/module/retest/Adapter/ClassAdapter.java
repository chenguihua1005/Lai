package com.softtek.lai.module.retest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.retest.model.BanjiModel;

import java.util.List;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
public class ClassAdapter extends BaseAdapter {

    private Context context;
    private List<BanjiModel> banjiModelList;

    public ClassAdapter(Context context,List<BanjiModel> banjiModelList) {
        this.context=context;
        this.banjiModelList=banjiModelList;
    }


    public void updateData(List<BanjiModel> banjiModelList){
        this.banjiModelList=banjiModelList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return banjiModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return banjiModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView==null)
        {
            convertView=LayoutInflater.from(context).inflate(R.layout.listview_retest_class,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        BanjiModel banjiModel=banjiModelList.get(position);
        viewHolder.StartDate.setText(tomonth((banjiModel.getStartDate().substring(5,7))));
        viewHolder.ClassName.setText(banjiModel.getClassName());
        viewHolder.Total.setText(banjiModel.getTotal()+"");
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
