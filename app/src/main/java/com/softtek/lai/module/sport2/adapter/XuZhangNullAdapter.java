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
public class XuZhangNullAdapter extends BaseAdapter{

    private Context context;
    List<String> content=new ArrayList<String>();
    List<Integer> images=new ArrayList<Integer>();
//    List<String> imgagecontent=new ArrayList<String>();
    private int account=0;

    public XuZhangNullAdapter(Context context,List<Integer> images,List<String> content) {
        this.context = context;
//        this.imgagecontent=imgagecontent;
        this.images=images;
        this.content=content;
    }

    public void updateData(List<Integer> images,List<String> content) {

//        this.imgagecontent=imgagecontent;
        this.content=content;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
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
//        XunZhangModel xunZhangModel = xunZhangModelList.get(position);
        viewHolder.tab_bushu.setText(content.get(position));
        viewHolder.lab2.setImageResource(images.get(position));
//        if (position==(images.size()-imgagecontent.size()+account))
//        {
//            viewHolder.tv_ima_content.setText(imgagecontent.get(account++));
//        }


        return convertView;
    }

    class ViewHolder {
        ImageView lab2;
        TextView tab_bushu;
        TextView tv_ima_content;



        public ViewHolder(View view) {
            lab2 = (ImageView) view.findViewById(R.id.lab2);
            tab_bushu = (TextView) view.findViewById(R.id.tab_bushu);
            tv_ima_content = (TextView) view.findViewById(R.id.tv_ima_content);


        }
    }
}
