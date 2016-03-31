/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.softtek.lai.R;
import com.softtek.lai.module.jingdu.model.Rank;

import java.util.List;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class RankAdapter extends ArrayAdapter<Rank> {

    private int resourceId;
    private List<Rank> rankList;


    public RankAdapter(Context context, int textViewResourceId, List<Rank> rankList) {
        super(context, textViewResourceId, rankList);
        resourceId = textViewResourceId;
        this.rankList = rankList;
    }

    public void updateData(List<Rank> rankList) {
        this.rankList = rankList;
        notifyDataSetChanged();
        ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Rank rank = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView ran = (TextView) view.findViewById(R.id.tv_rank);
        TextView name = (TextView) view.findViewById(R.id.tv_name);
        TextView LossAfter = (TextView) view.findViewById(R.id.tv_LossAfter);
        TextView LossBefore = (TextView) view.findViewById(R.id.tv_LossBefore);
        TextView LossWeight = (TextView) view.findViewById(R.id.tv_LossWeight);
        ran.setText(rank.getOrderNum() + "");
        name.setText(rank.getUserName());
        LossAfter.setText(rank.getLossAfter() + "");
        LossBefore.setText(rank.getLossBefor() + "");
        LossWeight.setText(rank.getLossAfter() + "");
        return view;
    }
}
