package com.softtek.lai.module.community.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.softtek.lai.R;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by John on 2016/4/3.
 */
public class GridAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private List<String> images;
    private Context context;

    public GridAdapter(Context context, List<String> images){
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
        int px= DisplayUtil.dip2px(context,100);
        if(StringUtils.isNotEmpty(images.get(position))){
            Picasso.with(context).load(AddressManager.get("photoHost")+images.get(position)).resize(px,px).centerCrop().placeholder(R.drawable.default_icon_square)
                    .error(R.drawable.default_icon_square).into(holder.iv_image);
        }else{
            Picasso.with(context).load(R.drawable.default_icon_square).into(holder.iv_image);
        }
        return convertView;
    }

    private static class LogDetailGrid {
        public ImageView iv_image;

        public LogDetailGrid(View view){
            iv_image= (ImageView) view.findViewById(R.id.iv_image);
        }
    }
}
