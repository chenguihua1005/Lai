package com.softtek.lai.module.tips.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
        String mask=StringUtils.isEmpty(model.getTips_TagTitle())?"":model.getTips_TagTitle().trim();
        holder.tv_mask.setText(mask);
        if("运动健身".equals(mask)){
            holder.tv_mask.setTextColor(Color.parseColor("#ffa300"));
            holder.iv_mask.setBackgroundResource(R.drawable.mask_org);
        }else if("营养课堂".equals(mask)){
            holder.tv_mask.setTextColor(Color.parseColor("#75ba2b"));
            holder.iv_mask.setBackgroundResource(R.drawable.mask_green);
        }else if("养生保健知识".equals(mask)){
            holder.tv_mask.setTextColor(Color.parseColor("#98dee6"));
            holder.iv_mask.setBackgroundResource(R.drawable.mask_blue);
        }else if("健康饮食".equals(mask)){
            holder.tv_mask.setTextColor(Color.parseColor("#cfdc3d"));
            holder.iv_mask.setBackgroundResource(R.drawable.mask_cyan);
        }else{
            holder.tv_mask.setTextColor(Color.parseColor("#ffa300"));
            holder.iv_mask.setBackgroundResource(R.drawable.mask_org);
        }
        if(position%2==0){
            holder.rl_item.setBackgroundColor(Color.parseColor("#ffffff"));
        }else {
            holder.rl_item.setBackgroundColor(Color.parseColor("#f1f1f1"));
        }
        holder.tv_content.setText(model.getTips_Title());
        if(StringUtils.isNotEmpty(model.getTips_Addr())){
            Picasso.with(context).load(AddressManager.get("photoHost")+model.getTips_Addr()).fit().placeholder(R.drawable.default_icon_rect)
                    .error(R.drawable.default_icon_rect).into(holder.iv_image);
        }else{
            Picasso.with(context).load(R.drawable.default_icon_rect).into(holder.iv_image);
        }
        return convertView;
    }

    static class ViewHolder{
        public TextView tv_content,tv_mask;
        public ImageView iv_image,iv_mask;
        public RelativeLayout rl_item;

        public ViewHolder(View view){
            tv_content= (TextView) view.findViewById(R.id.tv_content);
            tv_mask= (TextView) view.findViewById(R.id.tv_mask);
            iv_image= (ImageView) view.findViewById(R.id.iv_image);
            iv_mask= (ImageView) view.findViewById(R.id.mask);
            rl_item= (RelativeLayout) view.findViewById(R.id.rl_item);
        }
    }
}
