/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;
import com.softtek.lai.module.jingdu.model.PaimingModel;
import com.softtek.lai.module.jingdu.model.Table1Model;

import java.util.List;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class RankAdapter extends BaseAdapter {

    private Context context;
    private List<Table1Model> table1ModelList;

   // private List<PaimingModel> paimingModelList;

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

       // initpaiming();
        //PaimingModel paimingModel=paimingModelList.get(position);

//        String []paiming={"1","2","3","4","5","6","7","8","9","10"};
//        for (int i=0;i<10;i++){
//            Log.i(paiming[i]);
//            viewHolder.ranid.setText(paiming[i]);
//        }

       // viewHolder.ranid.setText(paimingModelList.get(position).getPaiming()+"");

        viewHolder.ranid.setText(table1ModelList.get(position).getAccountId());
        viewHolder.Username.setText(table1ModelList.get(position).getUserName());
        viewHolder.AfterWeight.setText(table1ModelList.get(position).getAfterWeight());
        viewHolder.BeforeWeight.setText(table1ModelList.get(position).getBeforeWight());
        viewHolder.LossWeight.setText(table1ModelList.get(position).getLoseWeight());
        return convertView;
    }

//    private void initpaiming(){
//        PaimingModel p1=new PaimingModel(1);
//        paimingModelList.add(p1);
//        PaimingModel p2=new PaimingModel(2);
//        paimingModelList.add(p2);
//        PaimingModel p3=new PaimingModel(3);
//        paimingModelList.add(p3);
//        PaimingModel p4=new PaimingModel(4);
//        paimingModelList.add(p4);
//        PaimingModel p5=new PaimingModel(5);
//        paimingModelList.add(p5);
//        PaimingModel p6=new PaimingModel(6);
//        paimingModelList.add(p6);
//        PaimingModel p7=new PaimingModel(7);
//        paimingModelList.add(p7);
//        PaimingModel p8=new PaimingModel(8);
//        paimingModelList.add(p8);
//        PaimingModel p9=new PaimingModel(9);
//        paimingModelList.add(p9);
//        PaimingModel p10=new PaimingModel(10);
//        paimingModelList.add(p10);
//    }

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

}
