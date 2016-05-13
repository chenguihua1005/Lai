package com.softtek.lai.module.laisportmine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laisportmine.model.PublicWewlfModel;
import com.softtek.lai.module.retest.model.BanjiModel;

import java.util.List;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class MyPublicWealfareAdapter extends BaseAdapter {
    private Context context;
    private List<PublicWewlfModel> publicWewlfModelList;
    private LayoutInflater inflater;

    public MyPublicWealfareAdapter(Context context,List<PublicWewlfModel> publicWewlfModelList) {
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.publicWewlfModelList=publicWewlfModelList;
    }


    public void updateData(List<PublicWewlfModel> publicWewlfModelList){
        this.publicWewlfModelList=publicWewlfModelList;
        notifyDataSetChanged();;
    }


    @Override
    public int getCount() {
        return publicWewlfModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return publicWewlfModelList.get(position);
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
            convertView=LayoutInflater.from(context).inflate(R.layout.listitem_my_publicwelfare,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        PublicWewlfModel publicWewlfModel=publicWewlfModelList.get(position);
        viewHolder.tv_public_date.setText((publicWewlfModel.getSendTime()));
        viewHolder.tv_public_content.setText(publicWewlfModel.getContent());

        return convertView;
    }


    class ViewHolder{
        TextView tv_public_date;
        TextView tv_public_content;
        public ViewHolder(View view){
            tv_public_date=(TextView)view.findViewById(R.id.tv_public_date);
            tv_public_content=(TextView)view.findViewById(R.id.tv_public_content);

        }
    }
}
