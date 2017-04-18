/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.home.model.ModelName;

import java.util.List;

/**
 * Created by jerry.guan on 3/22/2016.
 */
public class ModelAdapter extends BaseAdapter {

    private Context context;
    private List<ModelName> modelNames;

    private int[] icons = {R.drawable.tiguansai2, R.drawable.laiyundong2, R.drawable.laiketang2, R.drawable.laichen1, R.drawable.kaifa};

    public ModelAdapter(Context context, List<ModelName> modelNames) {
        this.context=context;
        this.modelNames=modelNames;
    }

    @Override
    public int getCount() {
        return modelNames.size();
    }

    @Override
    public Object getItem(int position) {
        return modelNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderModel holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_item, parent, false);
            holder = new ViewHolderModel(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderModel) convertView.getTag();
        }
        ModelName modelName=modelNames.get(position);
        holder.name_model.setText(modelName.modelName);
        holder.ci_icon.setBackgroundResource(icons[position]);
        if(modelName.unreadNum>0){
            holder.tv_unread.setVisibility(View.VISIBLE);
            if (modelName.unreadNum > 99) {
                holder.tv_unread.setText("99+");
            } else {
                holder.tv_unread.setText(String.valueOf(modelName.unreadNum));
            }
        }else {
            holder.tv_unread.setVisibility(View.GONE);
        }

        return convertView;
    }



    static class ViewHolderModel {
        TextView name_model;
        ImageView ci_icon;
        TextView tv_unread;

        public ViewHolderModel(View view) {
            name_model = (TextView) view.findViewById(R.id.tv_name);
            ci_icon = (ImageView) view.findViewById(R.id.iv_icon);
            tv_unread = (TextView) view.findViewById(R.id.tv_unread);
        }
    }
}
