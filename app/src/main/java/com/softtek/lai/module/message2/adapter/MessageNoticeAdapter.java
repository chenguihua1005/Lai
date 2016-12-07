/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.adapter;

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
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.module.message2.model.NoticeMsgModel;
import com.softtek.lai.module.message2.model.SelectNoticeMsgModel;
//import com.softtek.lai.module.message2.view.NoticeFC2Activity;

import java.util.List;

/**
 * 复测消息，操作消息，通知消息adapter
 */
public class MessageNoticeAdapter extends BaseAdapter {
    private List<SelectNoticeMsgModel> list;
    private BaseActivity context;
    private String type;
    public boolean isDel = false;

    public int select_count;
    private CheckBox cb;

    /**
     * 构造函数
     */
    public MessageNoticeAdapter(BaseActivity context, List<SelectNoticeMsgModel> list, String type, CheckBox cb) {
        this.context = context;
        this.list = list;
        this.type = type;
        this.cb = cb;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_2_remind_item, parent,false);
            holder = new ViewHolder();
            holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.text_title = (TextView) convertView.findViewById(R.id.text_title);
            holder.img_red = (ImageView) convertView.findViewById(R.id.img_red);
            holder.lin_more = (LinearLayout) convertView.findViewById(R.id.lin_more);
            holder.lin_item = (LinearLayout) convertView.findViewById(R.id.lin_item);
            holder.img_select = (ImageView) convertView.findViewById(R.id.img_select);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final SelectNoticeMsgModel selectNoticeMsgModel = list.get(position);
        NoticeMsgModel noticeMsgModel = list.get(position).getNoticeMsgModel();
        String time = noticeMsgModel.getSendTime();
        if (!TextUtils.isEmpty(time)) {
            String[] str1 = time.split(" ");
            String[] str = str1[0].split("-");
            holder.text_time.setText(str[0] + "年" + str[1] + "月" + str[2] + "日");
        } else {
            holder.text_time.setText("");
        }
        holder.text_value.setText(noticeMsgModel.getContent());
        if (isDel) {
            holder.img_select.setVisibility(View.VISIBLE);
        } else {
            holder.img_select.setVisibility(View.GONE);
        }
        if ("0".equals(noticeMsgModel.getIsRead())) {
            holder.img_red.setVisibility(View.VISIBLE);
        } else {
            holder.img_red.setVisibility(View.GONE);
        }
        if (selectNoticeMsgModel.isSelect()) {
            holder.img_select.setImageResource(R.drawable.history_data_circled);
        } else {
            holder.img_select.setImageResource(R.drawable.history_data_circle);
        }
        if ("notice".equals(type)) {
            holder.text_title.setText("系统通知");
            holder.lin_more.setVisibility(View.GONE);
        } else if ("fc".equals(type)) {
            holder.text_title.setText("复测提醒");
            holder.lin_more.setVisibility(View.VISIBLE);
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
                    if (selectNoticeMsgModel.isSelect()) {
                        select_count--;
                        selectNoticeMsgModel.setSelect(false);
                        holder.img_select.setImageResource(R.drawable.history_data_circle);
                    } else {
                        select_count++;
                        selectNoticeMsgModel.setSelect(true);
                        holder.img_select.setImageResource(R.drawable.history_data_circled);
                    }
                    if (select_count == list.size()) {
                        cb.setChecked(true);
                        //NoticeFC2Activity.isSelsetAll = true;
                    } else {
                        //NoticeFC2Activity.isSelsetAll = false;
                        cb.setChecked(false);
                    }
                } else {
                    if ("fc".equals(type)) {
                        turnToFCActivity();
                    } else if ("notice".equals(type)) {
                    }
                }
            }
        });
        return convertView;
    }

    private void turnToFCActivity() {
        String userrole = UserInfoModel.getInstance().getUser().getUserrole();
//        if (String.valueOf(Constants.PC).equals(userrole)) {
//            Intent intent = new Intent(context, BodyGamePCActivity.class);
//            intent.putExtra("type", 3);
//            context.startActivity(intent);
//        } else if (String.valueOf(Constants.SR).equals(userrole)) {
//            //助教身份跳转复测页面
//            Intent intent = new Intent(context, BodyGameSRActivity.class);
//            intent.putExtra("type", 3);
//            context.startActivity(intent);
//
//        } else if (String.valueOf(Constants.SP).equals(userrole)) {
//            //顾问身份跳转复测页面
//            Intent intent = new Intent(context, BodyGameSPActivity.class);
//            intent.putExtra("type", 3);
//            context.startActivity(intent);
//
//        }
    }

    /**
     * 存放控件
     */
    private static class ViewHolder {
        public TextView text_time;
        public TextView text_value;
        public TextView text_title;
        public ImageView img_red;
        public ImageView img_select;
        public LinearLayout lin_more;
        public LinearLayout lin_item;
    }
}



