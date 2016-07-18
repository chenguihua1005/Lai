/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.jingdu.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.jingdu.model.PaimingModel;
import com.softtek.lai.module.jingdu.model.Table1Model;
import com.softtek.lai.utils.StringUtil;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by julie.zhu on 3/25/2016.
 */
public class RankAdapter extends BaseAdapter {
    private Context context;
    private List<Table1Model> table1ModelList;
    private List<PaimingModel> paimingModelList;

    public RankAdapter(Context context, List<Table1Model> table1ModelList, List<PaimingModel> paimingModelList) {
        this.context = context;
        this.table1ModelList = table1ModelList;
        this.paimingModelList = paimingModelList;
    }

    public void updateData(List<Table1Model> table1ModelList, List<PaimingModel> paimingModelList) {
        this.table1ModelList = table1ModelList;

        this.paimingModelList = paimingModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return table1ModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return table1ModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.rank_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String path = AddressManager.get("photoHost");
        Table1Model model=table1ModelList.get(position);
        if (!TextUtils.isEmpty(model.getPhoto())) {
            Picasso.with(context).load(path + model.getPhoto()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(viewHolder.civ_header_image);
        } else {
            Picasso.with(context).load(R.drawable.img_default).into(viewHolder.civ_header_image);

        }
        //前三名颜色为黄色FDB02B
        if ((position + 1) < 4) {
            viewHolder.ranid.setTextColor(Color.parseColor("#FDB02B"));
        } else {
            viewHolder.ranid.setTextColor(ContextCompat.getColor(context,R.color.word3));
        }
        viewHolder.ranid.setText(paimingModelList.get(position).getPaiming() + "");
        viewHolder.Username.setText(model.getUserName());
        viewHolder.AfterWeight.setText(model.getAfterWeight());
        viewHolder.BeforeWeight.setText(model.getBeforeWight());
        DecimalFormat format=new DecimalFormat("#0.00");
        double value=Double.parseDouble(StringUtil.getFloatValue(model.getLoseWeight()));
        viewHolder.LossWeight.setText(format.format(value));
        return convertView;
    }

    class ViewHolder {
        TextView ranid;
        TextView Username;
        TextView AfterWeight;
        TextView BeforeWeight;
        TextView LossWeight;
        CircleImageView civ_header_image;

        public ViewHolder(View view) {
            ranid = (TextView) view.findViewById(R.id.tv_rank);
            Username = (TextView) view.findViewById(R.id.tv_name);
            AfterWeight = (TextView) view.findViewById(R.id.tv_LossAfter);
            BeforeWeight = (TextView) view.findViewById(R.id.tv_LossBefore);
            LossWeight = (TextView) view.findViewById(R.id.tv_LossWeight);
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);
        }
    }

}
