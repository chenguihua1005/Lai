package com.softtek.lai.module.retest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.retest.model.Banji;

import java.util.List;

/**
 * Created by lareina.qiao on 3/18/2016.
 */
public class ClassAdapter extends BaseAdapter {
    private int resourceId;
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
        viewHolder.StartDate.setText("");
        viewHolder.ClassName.setText("dsf");
        viewHolder.Total.setText("dfs");
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
}
