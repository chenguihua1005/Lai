package com.softtek.lai.module.laiClassroom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ggx.widgets.view.CheckTextView;
import com.github.snowdream.android.util.Log;
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
    //记录选择的原始状态
    private StringBuilder orginal;
    //记录选择后的状态，用来对比用
    private StringBuilder change;

    public FilterAdapter(Context context,List<FilteData.Filter> filters,int type) {
        this.filters = filters;
        this.mContext=context;
        this.type=type;
        orginal=new StringBuilder();
        change=new StringBuilder();
        for (FilteData.Filter filter:filters){
            orginal.append(filter.getSelected());
            change.append(filter.getSelected());
        }
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
                    //单选只有一个所以需要重置变换位
                    change.delete(0,change.length());
                    for (int i=0,j=filters.size(); i<j;i++) {
                        if(i!=position&&filters.get(i).getSelected()==1){
                            filters.get(i).setSelected(0);
                        }
                        change.append(filters.get(i).getSelected());
                    }
                    notifyDataSetChanged();
                }else {
                    if(filter.getID().equals("0")){//表示全选类型的选择,其他的都标记不选中
                        filter.setSelected(1);
                        for (int i=0,j=filters.size(); i<j;i++) {
                            if (!filters.get(i).getID().equals("0")){
                                filters.get(i).setSelected(0);
                                change.setCharAt(i, (char) (filters.get(i).getSelected()+48));
                            }

                        }
                    }else {//否则取消全部类型的选择
                        //设置选择条件的状态
                        filter.setSelected(filter.getSelected()==0?1:0);
                        for (int i=0,j=filters.size(); i<j;i++) {
                            if (filters.get(i).getID().equals("0")){
                                filters.get(i).setSelected(0);
                                change.setCharAt(i, (char) (filters.get(i).getSelected()+48));
                                break;
                            }
                        }
                    }
                    //多选只需要改变相应位置的状态即可
                    change.setCharAt(position, (char) (filter.getSelected()+48));
                    if(Long.parseLong(change.toString())==0){
                        change.setCharAt(0, (char) (1+48));
                        filters.get(0).setSelected(1);
                    }
                    notifyDataSetChanged();
                }
                Log.i("本次选择="+change.toString());
            }
        });
    }

    //获取选择是否真的发生变化
    public boolean isChange(){
        return Long.parseLong(orginal.toString())!=Long.parseLong(change.toString());
    }
    //获取选中的关键字
    public String getKey(){
        //如果有异常则返回默认的值
        if(filters==null||filters.isEmpty()){
            return type==SINGLE?"1":"0";
        }
        StringBuilder keys=new StringBuilder();
        for (FilteData.Filter filter:filters){
            if(filter.getSelected()==1){
                keys.append(filter.getID());
                keys.append(",");
            }
        }
        if(keys.length()>0){
            return keys.deleteCharAt(keys.length()-1).toString();
        }
        return type==SINGLE?"1":"0";
    }

    //将新的改变记录更新给原始记录
    public void doUpdate(){
        orginal.delete(0,orginal.length());
        orginal.append(change);
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
