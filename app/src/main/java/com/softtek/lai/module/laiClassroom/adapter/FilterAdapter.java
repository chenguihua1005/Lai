package com.softtek.lai.module.laiClassroom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softtek.lai.R;

import java.util.List;

/**
 * Created by jerry.guan on 3/10/2017.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterHolder>{

    private Context mContext;
    private List<String> filters;

    public FilterAdapter(Context context,List<String> filters) {
        this.filters = filters;
        this.mContext=context;
    }

    @Override
    public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.filter,parent,false);
        return new FilterHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterHolder holder, int position) {
        holder.text.setText(filters.get(position));
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }

    public static class FilterHolder extends RecyclerView.ViewHolder{
        TextView text;
        public FilterHolder(View itemView) {
            super(itemView);
            text= (TextView) itemView.findViewById(R.id.tv1);
        }
    }

}
