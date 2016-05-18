package com.softtek.lai.module.mygrades.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.mygrades.model.RankSelectModel;

import java.util.List;

/**
 * Created by julie.zhu on 5/18/2016.
 */
public class RankInfoAdapter extends BaseAdapter {

    private Context context;
    private List<RankSelectModel> rankSelectModelList;

    public RankInfoAdapter(Context context, List<RankSelectModel> rankSelectModelList) {
        this.context = context;
        this.rankSelectModelList = rankSelectModelList;
    }

    public void updateData(List<RankSelectModel> rankSelectModelList) {
        this.rankSelectModelList = rankSelectModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return rankSelectModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return rankSelectModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.rankinfo_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RankSelectModel rankSelectModel = rankSelectModelList.get(position);
        viewHolder.name.setText(rankSelectModelList.get(position).getName()+"");
        return convertView;
    }

    class ViewHolder {
        TextView name;
        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}
