package com.softtek.lai.module.laicheng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.softtek.lai.R;

import java.util.List;

/**
 * Created by jerry.guan on 4/7/2017.
 */

public class HealthyReportAdapter extends RecyclerView.Adapter<HealthyReportAdapter.HealthyReportHolder>{

    Context context;
    List<String> items;

    public HealthyReportAdapter(List<String> items, Context context) {
        this.items = items;
        this.context = context;
    }
    int i;
    @Override
    public HealthyReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("创建holder");
        i++;
        System.out.println("当前第几"+i);
        return new HealthyReportHolder(LayoutInflater.from(context).inflate(R.layout.healthy_item,parent,false));
    }

    @Override
    public void onBindViewHolder(HealthyReportHolder holder, int position) {
        holder.tv.setText(items.get(position));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HealthyReportHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public HealthyReportHolder(View itemView) {
            super(itemView);
            tv= (TextView) itemView.findViewById(R.id.num);
        }
    }
}
