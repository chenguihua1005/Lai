package com.softtek.lai.module.mygrades.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.mygrades.model.XunZhangModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by julie.zhu on 5/17/2016.
 */
public class XuZhangNullAdapter extends BaseAdapter{

    private Context context;
    private List<XunZhangModel> xunZhangModelList;
    List<String> content=new ArrayList<String>();
    List<Integer> images=new ArrayList<Integer>();
    private int account;

    public XuZhangNullAdapter(Context context, List<XunZhangModel> xunZhangModelList,int account,List<Integer> images,List<String> content) {
        this.context = context;
        this.xunZhangModelList = xunZhangModelList;
        this.images=images;
        this.account=account;
        this.content=content;
    }

    public void updateData(List<XunZhangModel> xunZhangModelList,List<Integer> images,List<String> content) {
        this.xunZhangModelList = xunZhangModelList;
        this.images=images;
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
        XunZhangModel xunZhangModel = xunZhangModelList.get(position);
        viewHolder.tab_bushu.setText(content.get(position));
        viewHolder.lab2.setImageResource(images.get(position));

//        switch (position)
//        {
//            case 0:
//                if (xunZhangModelList.get(position).getThreeDays().equals("0")) {
//
//                    viewHolder.tab_bushu.setText("连续3天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.three);
//
//                }
//                else if (xunZhangModelList.get(position).getSevenDays().equals("0"))
//                {
//                    viewHolder.tab_bushu.setText("连续7天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.senven);
//                }
//                else if (xunZhangModelList.get(position).getTwentyOneDays().equals("0"))
//                {
//                    viewHolder.tab_bushu.setText("连续21天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.twenty_one);
//                }
//                else if (xunZhangModelList.get(position).getThirtyDays().equals("0"))
//                {
//                    viewHolder.tab_bushu.setText("连续30天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.thirty);
//                }
//                else if (xunZhangModelList.get(position).getOneHundredDays().equals("0"))
//                {
//                    viewHolder.tab_bushu.setText("连续100天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.one_hundred);
//                }
//                else if (xunZhangModelList.get(position).getTwoHundredyDays().equals("0"))
//                {
//                    viewHolder.tab_bushu.setText("连续200天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.day200);
//                }
//                else if (xunZhangModelList.get(position).getOneYearDays().equals("0"))
//                {
//                    viewHolder.tab_bushu.setText("连续365天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.day365);
//                }
//                break;
//            case 1:
//
//                if (xunZhangModelList.get(position).getSevenDays().equals("0")) {
//
//                    viewHolder.tab_bushu.setText("连续7天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.senven);
//                }
//                else if (xunZhangModelList.get(position).getTwentyOneDays().equals("0"))
//                {
//                    viewHolder.tab_bushu.setText("连续21天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.twenty_one);
//                }
//                else if (xunZhangModelList.get(position).getThirtyDays().equals("0"))
//                {
//                    viewHolder.tab_bushu.setText("连续30天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.thirty);
//                }
//                else if (xunZhangModelList.get(position).getOneHundredDays().equals("0"))
//                {
//                    viewHolder.tab_bushu.setText("连续100天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.one_hundred);
//                }
//                else if (xunZhangModelList.get(position).getTwoHundredyDays().equals("0"))
//                {
//                    viewHolder.tab_bushu.setText("连续200天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.day200);
//                }
//                else if (xunZhangModelList.get(position).getOneYearDays().equals("0"))
//                {
//                    viewHolder.tab_bushu.setText("连续365天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.day365);
//                }
//                break;
//            case 2:
//                if (xunZhangModelList.get(position).getTwentyOneDays().equals("0")) {
//
//                    viewHolder.tab_bushu.setText("连续20天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.twenty_one);
//                }
//                break;
//            case 3:
//                if (xunZhangModelList.get(position).getThirtyDays().equals("0")) {
//
//                    viewHolder.tab_bushu.setText("连续30天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.thirty);
//                }
//                break;
//            case 4:
//                if (xunZhangModelList.get(position).getOneHundredDays().equals("0")) {
//
//                    viewHolder.tab_bushu.setText("连续100天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.senven);
//                }
//                else {
//                    viewHolder.re_lay.setVisibility(View.GONE);
//                }
//                break;
//            case 5:
//                if (xunZhangModelList.get(position).getTwoHundredyDays().equals("0")) {
//
//                    viewHolder.tab_bushu.setText("连续200天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.senven);
//                }
//                break;
//            case 6:
//                if (xunZhangModelList.get(position).getOneYearDays().equals("0")) {
//
//                    viewHolder.tab_bushu.setText("连续365天一万步");
//                    viewHolder.lab2.setImageResource(R.drawable.senven);
//                }
//                break;
//        }





        return convertView;
    }

    class ViewHolder {
        ImageView lab2;
        TextView tab_bushu;
        RelativeLayout re_lay;


        public ViewHolder(View view) {
            lab2 = (ImageView) view.findViewById(R.id.lab2);
            tab_bushu = (TextView) view.findViewById(R.id.tab_bushu);
            re_lay= (RelativeLayout) view.findViewById(R.id.re_lay);

        }
    }
}
