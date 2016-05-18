package com.softtek.lai.module.tips.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.tips.model.AskHealthyModel;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jerry.guan on 4/27/2016.
 */
public class AskHealthyAdapter extends BaseAdapter{

    private List<AskHealthyModel> models;
    private Context context;

    public AskHealthyAdapter(List<AskHealthyModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.tip_ask_healthy_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        AskHealthyModel model=models.get(position);
        holder.tv_mask.setText(model.getTips_Title());
        holder.tv_content.setText(model.getTips_Content());
        if(StringUtils.isNotEmpty(model.getTips_Addr())){
            Picasso.with(context).load(AddressManager.get("photoHost")+model.getTips_Addr()).fit().placeholder(R.drawable.default_icon_rect)
                    .error(R.drawable.default_icon_rect).into(holder.iv_image);
        }
        return convertView;
    }

    static class ViewHolder{
        public TextView tv_content,tv_mask;
        public ImageView iv_image;

        public ViewHolder(View view){
            tv_content= (TextView) view.findViewById(R.id.tv_content);
            tv_mask= (TextView) view.findViewById(R.id.tv_mask);
            iv_image= (ImageView) view.findViewById(R.id.iv_image);
        }
    }
}
