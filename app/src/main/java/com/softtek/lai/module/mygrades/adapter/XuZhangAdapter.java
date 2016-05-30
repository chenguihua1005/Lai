package com.softtek.lai.module.mygrades.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.softtek.lai.R;
import com.softtek.lai.module.mygrades.model.OrderDataModel;
import com.softtek.lai.module.mygrades.model.XunZhangModel;
import com.softtek.lai.widgets.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import zilla.libcore.file.AddressManager;

/**
 * Created by julie.zhu on 5/17/2016.
 */
public class XuZhangAdapter extends BaseAdapter{

    private Context context;
    private List<XunZhangModel> xunZhangModelList1;
    List<Integer> images1=new ArrayList<Integer>();
    List<String> content1=new ArrayList<String>();
    public XuZhangAdapter(Context context, List<XunZhangModel> xunZhangModelList1,List<Integer> images1,List<String> content1) {
        this.context = context;
        this.images1=images1;
        this.content1=content1;
        this.xunZhangModelList1 = xunZhangModelList1;
    }

    public void updateData(List<XunZhangModel> xunZhangModelList,List<Integer> images1,List<String> content1) {
        this.xunZhangModelList1 = xunZhangModelList;
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
        XunZhangModel xunZhangModel = xunZhangModelList1.get(position);
        viewHolder.tab_bushu.setText(content1.get(position));
        viewHolder.lab2.setImageResource(images1.get(position));

        return convertView;
    }

    class ViewHolder {
        ImageView lab2;
        TextView tab_bushu;


        public ViewHolder(View view) {
            lab2 = (ImageView) view.findViewById(R.id.lab2);
            tab_bushu = (TextView) view.findViewById(R.id.tab_bushu);

        }
    }
}
