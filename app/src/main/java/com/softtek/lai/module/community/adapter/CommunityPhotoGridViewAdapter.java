package com.softtek.lai.module.community.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.softtek.lai.R;
import com.softtek.lai.module.lossweightstory.model.UploadImage;

import java.util.List;

/**
 * Created by jerry.guan on 4/16/2016.
 */
public class CommunityPhotoGridViewAdapter extends BaseAdapter{

    private List<UploadImage> images;
    private Context context;

    public CommunityPhotoGridViewAdapter(List<UploadImage> images, Context context) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.gridview_photo_list_1,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        UploadImage file=images.get(position);
        holder.image.setImageBitmap(file.getBitmap());
        return convertView;
    }

    static class ViewHolder{
        public ImageView image;

        public ViewHolder(View view){
            image= (ImageView) view.findViewById(R.id.iv);
        }
    }
}
