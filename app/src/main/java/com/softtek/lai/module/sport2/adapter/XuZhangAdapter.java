package com.softtek.lai.module.sport2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julie.zhu on 5/17/2016.
 */
public class XuZhangAdapter extends BaseAdapter{

    private Context context;
    List<Integer> images1=new ArrayList<>();
    List<String> content1=new ArrayList<>();

    public XuZhangAdapter(Context context, List<Integer> images1,List<String> content1) {
        this.context = context;
        this.images1=images1;
        this.content1=content1;

    }

    public void updateData(List<Integer> images1,List<String> content1) {

        this.images1=images1;
        this.content1=content1;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return images1.size();
    }

    @Override
    public Object getItem(int position) {
        return images1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_my_xuzhang, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tab_bushu.setText(content1.get(position));
        viewHolder.lab2.setImageResource(images1.get(position));
        return convertView;
    }

    class ViewHolder {
        ImageView lab2;
        TextView tab_bushu;
        TextView tv_content;


        public ViewHolder(View view) {
            lab2 = (ImageView) view.findViewById(R.id.lab2);
            tab_bushu = (TextView) view.findViewById(R.id.tab_bushu);
            tv_content = (TextView) view.findViewById(R.id.tv_content);

        }
    }
}
