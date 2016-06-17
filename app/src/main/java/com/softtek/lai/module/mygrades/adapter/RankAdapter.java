package com.softtek.lai.module.mygrades.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.softtek.lai.R;
import com.softtek.lai.module.mygrades.model.OrderDataModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by julie.zhu on 5/17/2016.
 */
public class RankAdapter extends BaseAdapter {

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
        if (!TextUtils.isEmpty(orderDataModelList.get(position).getPhoto())) {
            Picasso.with(context).load(AddressManager.get("photoHost") + orderDataModel.getPhoto()).placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(viewHolder.civ_header_image);
        } else {
            Picasso.with(context).load("www").placeholder(R.drawable.img_default).fit().error(R.drawable.img_default).into(viewHolder.civ_header_image);
        }
        if ((position + 1) < 4) {
            viewHolder.rank.setTextColor(Color.parseColor("#FDB02B"));
        } else {
            viewHolder.rank.setTextColor(context.getResources().getColor(R.color.word3));
        }
        viewHolder.rank.setText(orderDataModelList.get(position).get_order() + "");
        //(姓名如果为空，手机号码前3后4中间4个*的)
        if (orderDataModelList.get(position).getUserName().isEmpty()) {
            String mobile = orderDataModelList.get(position).getMobile();
            String qian = mobile.substring(0, 3);
            String hou = mobile.substring(mobile.length() - 4, mobile.length());
            viewHolder.name.setText(qian + "****" + hou);
        } else {
            viewHolder.name.setText(orderDataModelList.get(position).getUserName() + "");
        }
        viewHolder.bushu.setText(orderDataModelList.get(position).getStepCount() + "");

        Float step = Float.parseFloat(orderDataModelList.get(0).getStepCount());
        Float stepper = 80 / step;

        if ((position + 1) < 2) {
            if (Integer.parseInt(orderDataModelList.get(0).getStepCount()) == 0) {
                viewHolder.prog.setProgress(0);
            } else {
                viewHolder.prog.setProgress(85);
            }
        } else {
            Float pro = Float.parseFloat(orderDataModelList.get(position).getStepCount()) * stepper;
            viewHolder.prog.setProgress(pro);
        }

        return convertView;
    }

    class ViewHolder {
        TextView rank;
        TextView name;
        TextView bushu;
        RoundCornerProgressBar prog;
        CircleImageView civ_header_image;

        public ViewHolder(View view) {
            rank = (TextView) view.findViewById(R.id.tv_rank);
            name = (TextView) view.findViewById(R.id.tv_name);
            bushu = (TextView) view.findViewById(R.id.tv_bushu);
            prog = (RoundCornerProgressBar) view.findViewById(R.id.rcpb_prog);
            civ_header_image = (CircleImageView) view.findViewById(R.id.civ_header_image);
        }
    }
}
