package com.softtek.lai.module.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry.guan on 3/15/2016.
 */
public class AdvAdapter extends BaseAdapter{

    private List datas=new ArrayList();
    private Context context;

    public AdvAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    static class ViewHolder{

    }
}
