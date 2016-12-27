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
    private int tinum;
    private int lainum;
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
        if (position == 0) {
            holder.tv_unread.setText(String.valueOf(tinum));
            if (tinum > 0 && tinum <= 99) {
                holder.tv_unread.setVisibility(View.VISIBLE);
            } else if (tinum > 99) {
                holder.tv_unread.append("+");
                holder.tv_unread.setVisibility(View.VISIBLE);
            } else {
                holder.tv_unread.setVisibility(View.GONE);
            }
        }
        if (position == 1) {
            holder.tv_unread.setText(String.valueOf(lainum));
            if (lainum > 0 && lainum <= 99) {
                holder.tv_unread.setVisibility(View.VISIBLE);
            } else if (lainum > 99) {
                holder.tv_unread.append("+");
                holder.tv_unread.setVisibility(View.VISIBLE);
            } else {
                holder.tv_unread.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    public void updateNum(int tinum, int lainum) {
        this.tinum = tinum;
        this.lainum = lainum;
        notifyDataSetChanged();
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
