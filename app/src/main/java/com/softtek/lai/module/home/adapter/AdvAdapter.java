package com.softtek.lai.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry.guan on 3/15/2016.
 */
public class AdvAdapter extends BaseAdapter{

    //private List<Integer> datas=new ArrayList();
    private static final int[] ids = {R.drawable.froyo,
            R.drawable.gingerbread, R.drawable.honeycomb, R.drawable.icecream };
    private Context context;

    public AdvAdapter(Context context){
        this.context=context;

    }

    @Override
    public int getCount() {
        return ids.length;
    }

    @Override
    public Object getItem(int position) {
        return ids[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.image_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.imgView.setBackgroundResource(ids[position]);
        return convertView;
    }

    static class ViewHolder{

        public ImageView imgView;

        public ViewHolder(View view){
            imgView= (ImageView) view.findViewById(R.id.imgView);
        }
    }
}
