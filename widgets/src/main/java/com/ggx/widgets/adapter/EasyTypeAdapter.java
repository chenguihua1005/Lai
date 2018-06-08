package com.ggx.widgets.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的视图数据适配器
 * @author jerry.Guan
 * created by 2016/11/3
 */

public abstract class EasyTypeAdapter<T> extends BaseAdapter{

    private List<T> datas;//数据集合

    public EasyTypeAdapter(List<T> datas) {
        if(datas==null){
            datas=new ArrayList<>(0);
        }
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    public List<T> getDatas() {
        return datas;
    }

    public void setList(List<T> datas) {
        if(datas==null){
            datas=new ArrayList<>(0);
        }
        this.datas = datas;
        notifyDataSetChanged();
    }
}
