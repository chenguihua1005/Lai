/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.bodygame2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.bodygame2.model.CompetitionModel;
import com.softtek.lai.utils.DisplayUtil;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by jerry.guan on 3/22/2016.
 */
public class SaiKuangAdapter extends BaseAdapter {

    private List<CompetitionModel> competitionModels;
    private Context context;
    private int px;
    public SaiKuangAdapter(Context context,List<CompetitionModel> competitionModels) {
        this.context=context;
        this.competitionModels=competitionModels;
        px= DisplayUtil.dip2px(context,150);
    }

    @Override
    public int getCount() {
        return competitionModels.size();
    }

    @Override
    public Object getItem(int position) {
        return competitionModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.bg2_dasai_item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        CompetitionModel model=competitionModels.get(position);
        holder.tv_zubie.setText(model.getGroupName());
        holder.tv_name.setText(model.getUserName());
        holder.tv_classname.setText(model.getClassName());
        holder.tv_zhujiao.setText(model.getPCUserName());
        holder.tv_jianzhong.setText("减重"+model.getLoseWeight()+"斤");
        if(StringUtils.isNotEmpty(model.getPCPhoto())){
            Picasso.with(context).load(AddressManager.get("photoHost")+model.getPCPhoto()).resize(px,px).centerCrop().placeholder(R.drawable.default_icon_square)
                    .error(R.drawable.default_icon_square).into(holder.iv_picture);
        }else {
            Picasso.with(context).load(R.drawable.default_icon_square).into(holder.iv_picture);
        }
        return convertView;
    }


    static class ViewHolder {

        TextView tv_zubie,tv_name,tv_jianzhong,tv_zhujiao,tv_classname;
        ImageView iv_picture;
        public ViewHolder(View view){
            tv_zubie= (TextView) view.findViewById(R.id.tv_zubie);
            tv_name= (TextView) view.findViewById(R.id.tv_name);
            tv_jianzhong= (TextView) view.findViewById(R.id.tv_jianzhong);
            tv_zhujiao= (TextView) view.findViewById(R.id.tv_zhujiao);
            tv_classname= (TextView) view.findViewById(R.id.tv_classname);
            iv_picture= (ImageView) view.findViewById(R.id.iv_picture);
        }
    }
}
