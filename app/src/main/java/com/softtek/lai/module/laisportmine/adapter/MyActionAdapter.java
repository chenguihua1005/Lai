package com.softtek.lai.module.laisportmine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.module.laisportmine.model.PublicWewlfModel;

import java.util.List;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class MyActionAdapter extends BaseAdapter {
    private Context context;
    private List<ActionModel> actionModelList;
    private LayoutInflater inflater;

    public MyActionAdapter(Context context, List<ActionModel> actionModelList) {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.actionModelList=actionModelList;
    }


    public void updateData(List<ActionModel> actionModelList){
        this.actionModelList=actionModelList;
        notifyDataSetChanged();;
    }


    @Override
    public int getCount() {
        return actionModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return actionModelList.get(position);
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
            convertView=LayoutInflater.from(context).inflate(R.layout.listitem_my_action,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        ActionModel actionModel=actionModelList.get(position);
        viewHolder.tv_action_date.setText((actionModel.getSendTime()));
        viewHolder.tv_action_content.setText(actionModel.getContent());
        viewHolder.tv_action_name.setText(actionModel.getActTitle());

        return convertView;
    }


    class ViewHolder{
        TextView tv_action_date;
        TextView tv_action_content;
        TextView tv_action_name;
        public ViewHolder(View view){
            tv_action_date=(TextView)view.findViewById(R.id.tv_action_date);
            tv_action_content=(TextView)view.findViewById(R.id.tv_action_content);
            tv_action_name= (TextView) view.findViewById(R.id.tv_action_name);

        }
    }
}
