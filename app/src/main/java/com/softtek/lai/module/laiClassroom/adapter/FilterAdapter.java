package com.softtek.lai.module.laiClassroom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ggx.widgets.view.CheckTextView;
import com.softtek.lai.R;
import com.softtek.lai.module.laiClassroom.model.FilteData;

import java.util.List;

/**
 * Created by jerry.guan on 3/10/2017.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterHolder>{

    public static final int SINGLE=1;
    public static final int MULTI=2;

    private Context mContext;
    private List<FilteData.Filter> filters;
    private int type;

    public FilterAdapter(Context context,List<FilteData.Filter> filters,int type) {
        this.filters = filters;
        this.mContext=context;
        this.type=type;
    }


    @Override
    public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.filter,parent,false);
        return new FilterHolder(view);
    }

    @Override
    public void onBindViewHolder(FilterHolder holder, final int position) {
        final FilteData.Filter filter=filters.get(position);
        holder.text.setText(filters.get(position).getName());
        holder.text.setChecked(filter.getSelected()==1);
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type==SINGLE){
                    if(filter.getSelected()!=1){
                        filter.setSelected(1);
                    }
                    for (int i=0,j=filters.size(); i<j;i++) {
                        if(i!=position&&filters.get(i).getSelected()==1){
                            filters.get(i).setSelected(0);
                        }
                    }
                    notifyDataSetChanged();
                }else {
                    filter.setSelected(filter.getSelected()==0?1:0);
                    notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filters==null?0:filters.size();
    }

    public static class FilterHolder extends RecyclerView.ViewHolder{
        CheckTextView text;
        public FilterHolder(View itemView) {
            super(itemView);
            text= (CheckTextView) itemView.findViewById(R.id.tv1);
        }
    }

}
