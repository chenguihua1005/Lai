package com.softtek.lai.module.mygrades.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.mygrades.model.XunZhangModel;

import java.util.List;

/**
 * Created by julie.zhu on 5/17/2016.
 */
public class XuZhangNullAdapter extends BaseAdapter{

    private Context context;
    private List<XunZhangModel> xunZhangModelList;

    public XuZhangNullAdapter(Context context, List<XunZhangModel> xunZhangModelList) {
        this.context = context;
        this.xunZhangModelList = xunZhangModelList;
    }

    public void updateData(List<XunZhangModel> xunZhangModelList) {
        this.xunZhangModelList = xunZhangModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return xunZhangModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return xunZhangModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_my_xuzhang_im, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        XunZhangModel xunZhangModel = xunZhangModelList.get(position);
        if (xunZhangModelList.get(position).getThirtyDays().equals("0")) {

            viewHolder.tab_bushu1.setText("连续3天一万步");
            viewHolder.lab1.setImageResource(R.drawable.three);
        }
        if (xunZhangModelList.get(position).getSevenDays().equals("0")) {

            viewHolder.tab_bushu.setText("连续7天一万步");
            viewHolder.lab2.setImageResource(R.drawable.senven);
        }
        if (xunZhangModelList.get(position).getTwentyOneDays().equals("0")) {

            viewHolder.tab_bushu3.setText("连续20天一万步");
            viewHolder.lab3.setImageResource(R.drawable.twenty_one);
        }
        if (xunZhangModelList.get(position).getThirtyDays().equals("0")) {

            viewHolder.tab_bushu.setText("连续30天一万步");
            viewHolder.lab2.setImageResource(R.drawable.thirty);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView lab2;
        ImageView lab1;
        ImageView lab3;
        TextView tab_bushu;
        TextView tab_bushu1;
        TextView tab_bushu3;


        public ViewHolder(View view) {
            lab2 = (ImageView) view.findViewById(R.id.lab2);
            lab1 = (ImageView) view.findViewById(R.id.lab1);
            lab3 = (ImageView) view.findViewById(R.id.lab3);
            tab_bushu = (TextView) view.findViewById(R.id.tab_bushu);
            tab_bushu1 = (TextView) view.findViewById(R.id.tab_bushu1);
            tab_bushu3 = (TextView) view.findViewById(R.id.tab_bushu3);

        }
    }
}
