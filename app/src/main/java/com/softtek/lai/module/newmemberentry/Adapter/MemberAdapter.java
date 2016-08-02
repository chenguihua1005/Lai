/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.newmemberentry.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.softtek.lai.R;
import com.softtek.lai.module.newmemberentry.model.PargradeModel;

import java.util.List;

/**
 * Created by julie.zhu on 3/26/2016.
 */
public class MemberAdapter extends ArrayAdapter<PargradeModel> {

    private int resourceId;
    private Context context;
    private List<PargradeModel> pargradeModelList;
    private LayoutInflater inflater;

    public MemberAdapter(Context context, int textViewResourceId, List<PargradeModel> pargradeModelList) {
        super(context, textViewResourceId, pargradeModelList);
        resourceId = textViewResourceId;
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.pargradeModelList = pargradeModelList;
    }


    public void updateData(List<PargradeModel> pargradeModelList) {
        this.pargradeModelList = pargradeModelList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return pargradeModelList.size();
    }


    @Override
    public PargradeModel getItem(int position) {
        return pargradeModelList.get(position);
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
        PargradeModel pargradeModel = pargradeModelList.get(position);
        //viewHolder.pargradeModel.setText(pargradeModel.getClassId());
        viewHolder.pargrade.setText(pargradeModel.getClassName());
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
