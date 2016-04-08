/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.softtek.lai.R;
import com.softtek.lai.module.jingdu.model.RankModel;
import com.softtek.lai.module.jingdu.model.Table1Model;
import com.softtek.lai.module.retest.model.BanjiModel;

import java.util.List;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class RankAdapter extends BaseAdapter {

    private Context context;
    private List<Table1Model> table1ModelList;
    public RankAdapter(Context context,List<Table1Model>table1ModelList){
    this.context=context;
    this.table1ModelList=table1ModelList;

}
    public void updateData(List<Table1Model> table1ModelList){
        this.table1ModelList=table1ModelList;
        notifyDataSetChanged();;
    }
    @Override
    public int getCount() {
        return table1ModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return table1ModelList.get(position);
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
            convertView=LayoutInflater.from(context).inflate( R.layout.rank_item,parent,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        Table1Model table1Model=table1ModelList.get(position);
        viewHolder.ranid.setText(table1ModelList.get(position).getAccountId());
        viewHolder.Username.setText(table1ModelList.get(position).getUserName());
        viewHolder.AfterWeight.setText(table1ModelList.get(position).getAfterWeight());
        viewHolder.BeforeWeight.setText(table1ModelList.get(position).getBeforeWight());
        viewHolder.LossWeight.setText(table1ModelList.get(position).getLoseWeight());
        return convertView;
    }
    class ViewHolder{
        TextView ranid;
        TextView Username;
        TextView AfterWeight;
        TextView BeforeWeight;
        TextView LossWeight;
        public ViewHolder(View view){
            ranid=(TextView)view.findViewById(R.id.tv_rank);
            Username=(TextView)view.findViewById(R.id.tv_name);
            AfterWeight=(TextView)view.findViewById(R.id.tv_LossAfter);
            BeforeWeight=(TextView)view.findViewById(R.id.tv_LossBefore);
            LossWeight=(TextView)view.findViewById(R.id.tv_LossWeight);
        }
    }

//    public RankAdapter(Context context, int textViewResourceId, List<RankModel> rankList) {
//        super(context, textViewResourceId, rankList);
//        resourceId = textViewResourceId;
//        this.rankList = rankList;
//    }
//
//    public void updateData(List<RankModel> rankList) {
//        this.rankList = rankList;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        RankModel rank = getItem(position);
//        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
//        TextView ranid = (TextView) view.findViewById(R.id.tv_rank);
//        TextView Username = (TextView) view.findViewById(R.id.tv_name);
//        TextView AfterWeight = (TextView) view.findViewById(R.id.tv_LossAfter);
//        TextView BeforeWeight = (TextView) view.findViewById(R.id.tv_LossBefore);
//        TextView LossWeight = (TextView) view.findViewById(R.id.tv_LossWeight);
//
////        ranid.setText(RankModel.getAccountId() + "");
////        Username.setText(rank.getUserName());
////        AfterWeight.setText(rank.getAfterWeight() + "");
////        BeforeWeight.setText(rank.getBeforeWight() + "");
////        LossWeight.setText(rank.getLoseWeight()+ "");
//        return view;
//    }
}
