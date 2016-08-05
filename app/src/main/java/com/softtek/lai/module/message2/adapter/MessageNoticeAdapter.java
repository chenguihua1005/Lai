/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softtek.lai.R;
import com.softtek.lai.common.BaseActivity;
import com.softtek.lai.common.UserInfoModel;
import com.softtek.lai.contants.Constants;
import com.softtek.lai.module.bodygame2.view.BodyGameSPActivity;
import com.softtek.lai.module.bodygame2pc.view.BodyGamePCActivity;
import com.softtek.lai.module.bodygame2sr.view.BodyGameSRActivity;
import com.softtek.lai.module.counselor.presenter.AssistantImpl;
import com.softtek.lai.module.counselor.presenter.IAssistantPresenter;
import com.softtek.lai.module.message.model.MeasureRemindInfo;
import com.softtek.lai.module.message2.model.NoticeMsgModel;
import com.softtek.lai.module.message2.model.OperateMsgModel;
import com.softtek.lai.module.message2.model.SelectNoticeMsgModel;
import com.softtek.lai.module.message2.view.MessageOperatorActivity;
import com.softtek.lai.module.message2.view.NoticeFC2Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public class MessageNoticeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List<SelectNoticeMsgModel> list;
    private BaseActivity context;
    private IAssistantPresenter assistantPresenter;
    String type;
    public boolean isDel = false;

    public int select_count;
    CheckBox cb;

    /**
     * 构造函数
     */
    public MessageNoticeAdapter(BaseActivity context, List<SelectNoticeMsgModel> list, String type, CheckBox cb) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        this.type = type;
        this.cb = cb;
        assistantPresenter = new AssistantImpl(context);
        Log.e("jarvis", list.toString());
    }

    @Override
    public int getCount() {
        return list.size();//返回数组的长度
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 书中详细解释该方法
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        //观察convertView随ListView滚动情况
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.message_2_remind_item, null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            holder.text_value = (TextView) convertView.findViewById(R.id.text_value);
            holder.text_title = (TextView) convertView.findViewById(R.id.text_title);
            holder.img_red = (ImageView) convertView.findViewById(R.id.img_red);
            holder.rel_more = (RelativeLayout) convertView.findViewById(R.id.rel_more);
            holder.view_1 = (View) convertView.findViewById(R.id.view_1);
            holder.lin_item = (LinearLayout) convertView.findViewById(R.id.lin_item);
            holder.img_select = (ImageView) convertView.findViewById(R.id.img_select);

            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        /**设置TextView显示的内容，即我们存放在动态数组中的数据*/
        final SelectNoticeMsgModel selectNoticeMsgModel = list.get(position);
        NoticeMsgModel noticeMsgModel = list.get(position).getNoticeMsgModel();
        String time = noticeMsgModel.getSendTime();
        if (!"".equals(time)) {
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
            holder.text_title.setText("服务窗");
            holder.rel_more.setVisibility(View.GONE);
            holder.view_1.setVisibility(View.GONE);
        } else if ("fc".equals(type)) {
            holder.text_title.setText("复测提醒");
            holder.rel_more.setVisibility(View.VISIBLE);
            holder.view_1.setVisibility(View.VISIBLE);
        } else if ("xzs".equals(type)) {
            String msg_type = noticeMsgModel.getMessageType();
            if ("0".equals(msg_type)) {
                holder.text_title.setText("助教申请");
            } else if ("1".equals(msg_type)) {
                holder.text_title.setText("助教移除");
            } else if ("2".equals(msg_type)) {
                holder.text_title.setText("助教邀请");
            } else if ("3".equals(msg_type)) {
                holder.text_title.setText("确认参赛");
            }
            holder.rel_more.setVisibility(View.VISIBLE);
            holder.view_1.setVisibility(View.VISIBLE);
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
                        NoticeFC2Activity.isSelsetAll=true;
                    } else {
                        NoticeFC2Activity.isSelsetAll=false;
                        cb.setChecked(false);
                    }
                } else {
                    if ("fc".equals(type)) {
                        turnToFCActivity();
                    } else if ("notice".equals(type)) {
                        System.out.println("notice--------------");
                    } else if ("xzs".equals(type)) {
                        OperateMsgModel model = NoticeFC2Activity.operatList.get(position);
                        turnToXZSActivity(model);
                    }
                }
            }
        });
        return convertView;
    }

    private void turnToXZSActivity(OperateMsgModel model) {
        Intent intent = new Intent(context, MessageOperatorActivity.class);
        intent.putExtra("model", model);
        context.startActivityForResult(intent, 0);
    }

    private void turnToFCActivity() {
        String userrole = UserInfoModel.getInstance().getUser().getUserrole();
        if (String.valueOf(Constants.PC).equals(userrole)) {
            Intent intent = new Intent(context, BodyGamePCActivity.class);
            intent.putExtra("type", 3);
            context.startActivity(intent);
        } else if (String.valueOf(Constants.SR).equals(userrole)) {
            //助教身份跳转复测页面
            Intent intent = new Intent(context, BodyGameSRActivity.class);
            intent.putExtra("type", 3);
            context.startActivity(intent);

        } else if (String.valueOf(Constants.SP).equals(userrole)) {
            //顾问身份跳转复测页面
            Intent intent = new Intent(context, BodyGameSPActivity.class);
            intent.putExtra("type", 3);
            context.startActivity(intent);

        }
    }

    /**
     * 存放控件
     */
    public class ViewHolder {
        public TextView text_time;
        public TextView text_value;
        public TextView text_title;
        public ImageView img_red;
        public ImageView img_select;
        public View view_1;
        public RelativeLayout rel_more;
        public LinearLayout lin_item;
    }
}



