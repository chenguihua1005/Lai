package com.softtek.lai.module.laisportmine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laisportmine.model.SelectPublicWewlfModel;
import com.softtek.lai.module.laisportmine.view.MyPublicwelfareActivity;
import com.softtek.lai.module.message2.model.NoticeModel;
import com.softtek.lai.utils.DateUtil;

import java.util.List;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class MyPublicWealfareAdapter extends BaseAdapter {
    private Context context;
    private List<SelectPublicWewlfModel> publicWewlfModelList;
    public boolean isDel = false;
    public int select_count;
    CheckBox cb;

    public MyPublicWealfareAdapter(Context context, List<SelectPublicWewlfModel> publicWewlfModelList, CheckBox cb) {
        this.context = context;
        this.cb = cb;
        this.publicWewlfModelList = publicWewlfModelList;
    }


    public void updateData(List<SelectPublicWewlfModel> publicWewlfModelList) {
        this.publicWewlfModelList = publicWewlfModelList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return publicWewlfModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return publicWewlfModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_my_publicwelfare, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.tv_public_date = (TextView) convertView.findViewById(R.id.tv_public_date);
            holder.tv_public_content = (TextView) convertView.findViewById(R.id.tv_public_content);
            holder.img_red = (ImageView) convertView.findViewById(R.id.img_red);
            holder.img_select = (ImageView) convertView.findViewById(R.id.img_select);
            holder.lin_item = (LinearLayout) convertView.findViewById(R.id.lin_item);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        if (isDel) {
            holder.img_select.setVisibility(View.VISIBLE);
        } else {
            holder.img_select.setVisibility(View.GONE);
        }
        NoticeModel publicWewlfModel = publicWewlfModelList.get(position).getPublicWewlfModel();
        final SelectPublicWewlfModel selectPublicWewlfModel=publicWewlfModelList.get(position);
        String date = DateUtil.getInstance().convertDateStr(publicWewlfModel.getSendTime(), "yyyy年MM月dd日");
        holder.tv_public_date.setText(date);
        holder.tv_public_content.setText(publicWewlfModel.getMsgContent());
        if (selectPublicWewlfModel.isSelect()) {
            holder.img_select.setImageResource(R.drawable.history_data_circled);
        } else {
            holder.img_select.setImageResource(R.drawable.history_data_circle);
        }

        if ("0".equals(publicWewlfModel.getIsRead())) {
            holder.img_red.setVisibility(View.VISIBLE);
        } else {
            holder.img_red.setVisibility(View.GONE);
        }
        holder.lin_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        holder.lin_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDel) {
                    if (selectPublicWewlfModel.isSelect()) {
                        select_count--;
                        selectPublicWewlfModel.setSelect(false);
                        holder.img_select.setImageResource(R.drawable.history_data_circle);
                    } else {
                        select_count++;
                        selectPublicWewlfModel.setSelect(true);
                        holder.img_select.setImageResource(R.drawable.history_data_circled);
                    }
                    if (select_count == publicWewlfModelList.size()) {
                        cb.setChecked(true);
                        MyPublicwelfareActivity.isSelsetAll=true;
                    } else {
                        MyPublicwelfareActivity.isSelsetAll=false;
                        cb.setChecked(false);
                    }
                }
            }
        });
        return convertView;
    }


    class ViewHolder {
        TextView tv_public_date;
        TextView tv_public_content;
        ImageView img_red;
        ImageView img_select;
        LinearLayout lin_item;
    }
}
