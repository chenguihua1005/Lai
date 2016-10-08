package com.softtek.lai.module.community.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.softtek.lai.R;
import com.softtek.lai.module.lossweightstory.model.UploadImage;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jerry.guan on 4/16/2016.
 *
 */
public class PhotosAdapter extends BaseAdapter{

    private List<String> images;
    private Context context;
    private int px;

    public PhotosAdapter(List<String> images, Context context) {
        this.images = images;
        this.context = context;
        px = DisplayUtil.dip2px(context.getApplicationContext(), 79);
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
            convertView= LayoutInflater.from(context).inflate(R.layout.photo_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        //加载图片
        String path = AddressManager.get("photoHost");
        Picasso.with(context).load(path+images.get(position)).resize(px, px).centerCrop()
                .placeholder(R.drawable.default_icon_square)
                .error(R.drawable.default_icon_square)
                .into(holder.image);
        return convertView;
    }

    static class ViewHolder{
        public ImageView image;

        public ViewHolder(View view){
            image= (ImageView) view.findViewById(R.id.iv);
        }
    }
}
