/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.softtek.lai.R;
import com.softtek.lai.module.newmemberentry.view.model.Pargrade;

import java.util.List;

/**
 * Created by julie.zhu on 3/26/2016.
 */
public class MemberAdapter extends ArrayAdapter<Pargrade> {

    private int resourceId;
    private Context context;
    private List<Pargrade> pargradeList;
    private LayoutInflater inflater;

    public MemberAdapter(Context context, int textViewResourceId, List<Pargrade> pargradeList) {
        super(context, textViewResourceId, pargradeList);
        resourceId = textViewResourceId;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.pargradeList = pargradeList;
    }


    public void updateData(List<Pargrade> pargradeList) {
        this.pargradeList = pargradeList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return pargradeList.size();
    }


    @Override
    public Pargrade getItem(int position) {
        return pargradeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.member_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Pargrade pargrade = pargradeList.get(position);
        //viewHolder.pargrade.setText(pargrade.getClassId());
        viewHolder.pargrade.setText(pargrade.getClassName());
        return convertView;
    }


    class ViewHolder {
        TextView pargrade;

        public ViewHolder(View view) {
            pargrade = (TextView) view.findViewById(R.id.tv_pargrade);
            //  pargrade=(TextView)view.findViewById(R.id.tv_pargrade);
        }
    }
}
