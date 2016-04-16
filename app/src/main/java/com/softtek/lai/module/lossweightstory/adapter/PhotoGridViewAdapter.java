package com.softtek.lai.module.lossweightstory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.softtek.lai.R;

import java.util.List;

/**
 * Created by jerry.guan on 4/16/2016.
 */
public class PhotoGridViewAdapter extends BaseAdapter{

    private List<String> images;
    private Context context;

    public PhotoGridViewAdapter(List<String> images, Context context) {
        this.images = images;
        this.context = context;
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
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.gridview_photo_list,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder{
        public ImageView image;

        public ViewHolder(View view){
            image= (ImageView) view.findViewById(R.id.iv);
        }
    }
}
