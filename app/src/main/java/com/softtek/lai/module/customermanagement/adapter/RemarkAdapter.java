package com.softtek.lai.module.customermanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.customermanagement.model.RemarkModel;
import com.softtek.lai.widgets.CircleImageView;

import java.util.List;

/**
 * Created by jessica.zhang on 11/21/2017.
 */

public class RemarkAdapter extends BaseAdapter {
    private List<RemarkModel> modelList;
    private Context context;

    public RemarkAdapter(List<RemarkModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public RemarkModel getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.remark_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        RemarkModel model = modelList.get(position);
        if (model != null) {
            viewHolder.remark_tv.setText(model.getRemark());
            viewHolder.name_tv.setText(model.getName());
            viewHolder.time_tv.setText(model.getTime());
        }
        return convertView;
    }

    class ViewHolder {
        CircleImageView imageView;
        TextView name_tv;
        TextView remark_tv;
        TextView time_tv;

        public ViewHolder(View view) {
            this.imageView = view.findViewById(R.id.head_img);
            this.name_tv = view.findViewById(R.id.name_tv);
            this.remark_tv = view.findViewById(R.id.remark_tv);
            this.time_tv = view.findViewById(R.id.time_tv);
        }
    }
}
