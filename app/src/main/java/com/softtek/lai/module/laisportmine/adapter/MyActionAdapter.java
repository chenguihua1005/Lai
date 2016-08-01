package com.softtek.lai.module.laisportmine.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.module.laisportmine.model.ActionModel;
import com.softtek.lai.utils.DateUtil;

import java.util.List;

/**
 * Created by lareina.qiao on 5/12/2016.
 */
public class MyActionAdapter extends BaseAdapter {
    private Context context;
    private List<ActionModel> actionModelList;
    private LayoutInflater inflater;
    private boolean isDel = false;
    CheckBox cb_all;
    int account = 0;

    public MyActionAdapter(Context context, List<ActionModel> actionModelList, boolean isDel, CheckBox cb_all) {
        this.context = context;
        this.isDel = isDel;
        this.cb_all = cb_all;
        inflater = LayoutInflater.from(context);
        this.actionModelList = actionModelList;
    }


    public void updateData(List<ActionModel> actionModelList) {
        this.actionModelList = actionModelList;
        notifyDataSetChanged();
        ;
    }


    @Override
    public int getCount() {
        return actionModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return actionModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_my_action, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ActionModel actionModel = actionModelList.get(position);
        String date = DateUtil.getInstance().convertDateStr(actionModel.getSendTime(), "yyyy年MM月dd日");
        viewHolder.tv_action_date.setText(date);
        viewHolder.tv_action_content.setText(actionModel.getContent());
        viewHolder.tv_action_name.setText(actionModel.getActTitle());

        if (actionModel.isselect()) {
            viewHolder.iv_checked.setImageResource(R.drawable.history_data_circled);
        } else {
            viewHolder.iv_checked.setImageResource(R.drawable.history_data_circle);
        }
        if ("0".equals(actionModel.getIsRead())) {
            viewHolder.img_red.setVisibility(View.VISIBLE);
        } else {
            viewHolder.img_red.setVisibility(View.GONE);
        }
        if (isDel) {
            viewHolder.iv_checked.setVisibility(View.VISIBLE);
        } else {
            viewHolder.iv_checked.setVisibility(View.GONE);
        }
        final ViewHolder finalViewHolder = viewHolder;
//
        if (isDel) {
            viewHolder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!actionModel.isselect()) {
                        finalViewHolder.iv_checked.setImageResource(R.drawable.history_data_circled);
                        actionModel.setIsselect(true);
                        account++;
                    } else if (actionModel.isselect()) {
                        finalViewHolder.iv_checked.setImageResource(R.drawable.history_data_circle);
                        actionModel.setIsselect(false);
                        account--;
                    }
                    if (account == actionModelList.size()) {
                        cb_all.setChecked(true);
                    } else {
                        cb_all.setChecked(false);
                    }
                }
            });
        }
        return convertView;
    }


    public class ViewHolder {
        TextView tv_action_date;
        TextView tv_action_content;
        TextView tv_action_name;
        ImageView iv_checked;
        LinearLayout ll_item;
        ImageView img_red;

        public ViewHolder(View view) {
            tv_action_date = (TextView) view.findViewById(R.id.tv_action_date);
            tv_action_content = (TextView) view.findViewById(R.id.tv_action_content);
            tv_action_name = (TextView) view.findViewById(R.id.tv_action_name);
            iv_checked = (ImageView) view.findViewById(R.id.iv_checked);
            img_red = (ImageView) view.findViewById(R.id.img_red);
            ll_item = (LinearLayout) view.findViewById(R.id.ll_item);

        }
    }
}
