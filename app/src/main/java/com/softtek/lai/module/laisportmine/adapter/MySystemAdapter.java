package com.softtek.lai.module.laisportmine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.model.SystemNewsModel;
import com.softtek.lai.utils.DateUtil;

import java.util.List;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class MySystemAdapter extends BaseAdapter {
    private Context context;
    private List<SystemNewsModel> systemNewsModelList;
    private LayoutInflater inflater;

    public MySystemAdapter(Context context, List<SystemNewsModel> systemNewsModelList) {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.systemNewsModelList=systemNewsModelList;
    }


    public void updateData(List<SystemNewsModel> systemNewsModelList){
        this.systemNewsModelList=systemNewsModelList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return systemNewsModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return systemNewsModelList.get(position);
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
            convertView=LayoutInflater.from(context).inflate(R.layout.listitem_my_system,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        SystemNewsModel systemNewsModel=systemNewsModelList.get(position);
        String date= DateUtil.getInstance().convertDateStr(systemNewsModel.getSendTime(),"yyyy年MM月dd日");
        viewHolder.tv_system_date.setText(date);
        viewHolder.tv_system_content.setText("系统消息");
        viewHolder.tv_system_name.setText(systemNewsModel.getContent());

        return convertView;
    }


    class ViewHolder{
        TextView tv_system_date;
        TextView tv_system_content;
        TextView tv_system_name;
        public ViewHolder(View view){
            tv_system_date=(TextView)view.findViewById(R.id.tv_system_date);
            tv_system_content=(TextView)view.findViewById(R.id.tv_system_content);
            tv_system_name= (TextView) view.findViewById(R.id.tv_system_name);

        }
    }
}
