package com.softtek.lai.module.studetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.softtek.lai.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by John on 2016/4/3.
 */
public class LogDetailGridAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private List<String> images;
    private Context context;

    public LogDetailGridAdapter(Context context, List<String> images){
        this.context=context;
        inflater=LayoutInflater.from(context);
        this.images =images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LogDetailGrid holder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.grid_log_detail_item,parent,false);
            holder=new LogDetailGrid(convertView);
            convertView.setTag(holder);
        }else {
            holder= (LogDetailGrid) convertView.getTag();
        }
        try {
            Picasso.with(context).load(images.get(position)).fit().placeholder(R.drawable.default_pic)
                    .error(R.drawable.default_pic).into(holder.iv_image);
        }catch (Exception e){}
        return convertView;
    }

    private static class LogDetailGrid {
        public ImageView iv_image;

        public LogDetailGrid(View view){
            iv_image= (ImageView) view.findViewById(R.id.iv_image);
        }
    }
}
