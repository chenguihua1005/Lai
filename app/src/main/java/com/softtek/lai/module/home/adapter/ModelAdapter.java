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

/**
 * Created by jerry.guan on 3/22/2016.
 */
public class ModelAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private String[] models_name;
    private int[] icons = {R.drawable.tiguansai, R.drawable.laiyundong, R.drawable.laiketang, R.drawable.laibiaoge, R.drawable.laigou};

    public ModelAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        models_name = context.getResources().getStringArray(R.array.models);
    }

    @Override
    public int getCount() {
        return models_name.length;
    }

    @Override
    public Object getItem(int position) {
        return models_name[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderModel holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gridview_item, parent, false);
            holder = new ViewHolderModel(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderModel) convertView.getTag();
        }
        holder.name_model.setText(models_name[position]);
        holder.ci_icon.setBackgroundResource(icons[position]);
        return convertView;
    }

    static class ViewHolderModel {
        TextView name_model;
        ImageView ci_icon;

        public ViewHolderModel(View view) {
            name_model = (TextView) view.findViewById(R.id.tv_name);
            ci_icon = (ImageView) view.findViewById(R.id.iv_icon);
        }
    }
}
