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
import com.softtek.lai.module.jingdu.model.RankModel;

import java.util.List;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class RankAdapter extends ArrayAdapter<RankModel> {

    private int resourceId;
    private List<RankModel> rankList;


    public RankAdapter(Context context, int textViewResourceId, List<RankModel> rankList) {
        super(context, textViewResourceId, rankList);
        resourceId = textViewResourceId;
        this.rankList = rankList;
    }

    public void updateData(List<RankModel> rankList) {
        this.rankList = rankList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RankModel rank = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView ranid = (TextView) view.findViewById(R.id.tv_rank);
        TextView Username = (TextView) view.findViewById(R.id.tv_name);
        TextView AfterWeight = (TextView) view.findViewById(R.id.tv_LossAfter);
        TextView BeforeWeight = (TextView) view.findViewById(R.id.tv_LossBefore);
        TextView LossWeight = (TextView) view.findViewById(R.id.tv_LossWeight);

        ranid.setText(rank.getAccountId() + "");
        Username.setText(rank.getUserName());
        AfterWeight.setText(rank.getAfterWeight() + "");
        BeforeWeight.setText(rank.getBeforeWight() + "");
        LossWeight.setText(rank.getLoseWeight()+ "");
        return view;
    }
}
