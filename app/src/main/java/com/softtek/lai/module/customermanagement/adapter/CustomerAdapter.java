package com.softtek.lai.module.customermanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.customermanagement.model.CustomerModel;
import com.softtek.lai.widgets.CircleImageView;

import java.util.List;

/**
 * Created by jessica.zhang on 11/16/2017.
 */

public class CustomerAdapter extends BaseAdapter {
    private Context context;
    private List<CustomerModel> modelList;

    public CustomerAdapter(Context context, List<CustomerModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.customer_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private final class ViewHolder {
        public ViewHolder(View view) {
            head_image = view.findViewById(R.id.head_image);
            name_tv = view.findViewById(R.id.name_tv);
            desc_tv = view.findViewById(R.id.desc_tv);
        }

        CircleImageView head_image;
        TextView name_tv;
        TextView desc_tv;
    }
}
