package com.softtek.lai.module.mygrades.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.mygrades.model.OrderDataModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by julie.zhu on 5/17/2016.
 */
public class RankAdapter extends BaseAdapter{

    private Context context;
    private List<OrderDataModel> orderDataModelList;

    public RankAdapter(Context context, List<OrderDataModel> orderDataModelList) {
        this.context = context;
        this.orderDataModelList = orderDataModelList;
    }

    public void updateData(List<OrderDataModel> orderDataModelList) {
        this.orderDataModelList = orderDataModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return orderDataModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderDataModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ranklist_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        OrderDataModel orderDataModel = orderDataModelList.get(position);
        String path = AddressManager.get("photoHost", "http://172.16.98.167/UpFiles/");
        if (!TextUtils.isEmpty(orderDataModelList.get(position).getPhoto())) {
            Picasso.with(context).load(path + orderDataModelList.get(position).getPhoto()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(viewHolder.civ_header_image);
        } else {
            Picasso.with(context).load("www").placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(viewHolder.civ_header_image);
        }
        if ((position + 1) < 4) {
            viewHolder.rank.setTextColor(Color.parseColor("#FDB02B"));
        } else {
            viewHolder.rank.setTextColor(context.getResources().getColor(R.color.word3));
        }
        viewHolder.rank.setText(orderDataModelList.get(position).get_order() + "");
        viewHolder.name.setText(orderDataModelList.get(position).getUserName()+"");
        viewHolder.bushu.setText(orderDataModelList.get(position).getStepCount()+"");
        return convertView;
    }

    class ViewHolder {
        TextView rank;
        TextView name;
        TextView bushu;
        CircleImageView civ_header_image;

        public ViewHolder(View view) {
            rank = (TextView) view.findViewById(R.id.tv_rank);
            name = (TextView) view.findViewById(R.id.tv_name);
            bushu = (TextView) view.findViewById(R.id.tv_bushu);
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);
        }
    }
}
