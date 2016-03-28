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
public class RankAdapter extends ArrayAdapter<Rank>{

    private int resourceId;

    public RankAdapter(Context context, int textViewResourceId, List<Rank> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Rank rank=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView ran=(TextView) view.findViewById(R.id.tv_rank);
        TextView name=(TextView) view.findViewById(R.id.tv_name);
        TextView LossAfter=(TextView) view.findViewById(R.id.tv_LossAfter);
        TextView LossBefore=(TextView) view.findViewById(R.id.tv_LossBefore);
        TextView LossWeight=(TextView) view.findViewById(R.id.tv_LossWeight);
        ran.setText(rank.getRan());
        name.setText(rank.getName());
        LossAfter.setText(rank.getLossAfter()+"");
        LossBefore.setText(rank.getLossBefore()+"");
        LossWeight.setText(rank.getLossWeight()+"");
        return view;
    }
}
