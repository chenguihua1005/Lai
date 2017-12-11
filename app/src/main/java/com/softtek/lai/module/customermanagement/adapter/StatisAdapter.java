package com.softtek.lai.module.customermanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.customermanagement.model.TimeAxisItemModel;

import java.util.List;

/**
 * Created by jessica.zhang on 11/21/2017.
 */

public class StatisAdapter extends BaseAdapter {
    private Context context;
    private List<TimeAxisItemModel> modelList;

    public StatisAdapter(Context context, List<TimeAxisItemModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.statistic_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TimeAxisItemModel model = modelList.get(position);
        String[] arr = model.getCreatedTime().split("年");
        viewHolder.tv_year.setText(arr[0] + "年");
        viewHolder.tv_day.setText(arr[1]);
        viewHolder.tv_track.setText(model.getDescription());


        return convertView;
    }

    class ViewHolder {
        TextView tv_year;
        TextView tv_day;
        TextView tv_track;

        public ViewHolder(View view) {
            this.tv_year = view.findViewById(R.id.tv_year);
            this.tv_day = view.findViewById(R.id.tv_day);
            this.tv_track = view.findViewById(R.id.tv_track);

        }
    }
}
