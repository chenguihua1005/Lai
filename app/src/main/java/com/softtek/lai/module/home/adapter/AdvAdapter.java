package com.softtek.lai.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.home.model.HomeInfo;
import com.squareup.picasso.Picasso;

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
    private List<HomeInfo> infos;

    public AdvAdapter(Context context, List<HomeInfo> infos){
        this.context=context;
        this.infos=infos;

    }

    @Override
    public int getCount() {
        return infos.size();
    }

    @Override
    public Object getItem(int position) {
        return infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        HomeInfo info=infos.get(position);
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.image_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(info.getImg_Addr()).into(holder.imgView);
        //holder.imgView.setBackgroundResource(ids[position]);
        return convertView;
    }

    static class ViewHolder{

        public ImageView imgView;

        public ViewHolder(View view){
            imgView= (ImageView) view.findViewById(R.id.imgView);
        }
    }
}
